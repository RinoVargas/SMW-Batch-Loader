package cl.entel.eai.dao;

import cl.entel.eai.configuration.connect.IMGISDatabaseConnector;
import cl.entel.eai.constants.DAOError;
import cl.entel.eai.exception.DAOException;
import cl.entel.eai.model.Hub;
import cl.entel.eai.util.GeometryUtil;
import oracle.spatial.geometry.JGeometry;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HubDAO {

    @Autowired
    private IMGISDatabaseConnector imgisConnector;

    public List<Hub> getHubChuck (long offset, int chunkSize) throws DAOException {
        PreparedStatement statement;
        ResultSet resultSet;
        List<Hub> result = new ArrayList<>();
        String sql;
        try {
            this.imgisConnector.connect();
            sql = "SELECT ID, X_COORD_EG, Y_COORD_EG " +
                    "FROM MIT_HUB " +
                    "OFFSET ? ROWS " +
                    "FETCH NEXT ? ROWS ONLY";

            statement = this.imgisConnector.getConnection().prepareStatement(sql);
            statement.setLong(1, offset);
            statement.setInt(2, chunkSize);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Hub hub = new Hub();

                hub.setId(resultSet.getInt(1));
                hub.setXCoordEg(resultSet.getString(2));
                hub.setYCoordEg(resultSet.getString(3));

                result.add(hub);
            }
        } catch (SQLException e) {
            throw new DAOException(DAOError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new DAOException(DAOError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }

        return result;
    }

    public Integer getRecordCount() throws DAOException {
        PreparedStatement statement;
        ResultSet resultSet;
        Integer result = null;
        String sql;

        try {
            this.imgisConnector.connect();
            sql = "SELECT COUNT(*) " +
                    "FROM MIT_HUB ";

            statement = this.imgisConnector.getConnection().prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(DAOError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new DAOException(DAOError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }

        return result;
    }

    public void createGeoHubs(List<Hub> hubs) throws DAOException {
        PreparedStatement statement;
        String sql;
        try {
            if (!this.imgisConnector.isConnected()) {
                this.imgisConnector.connect();
            }
            sql = "INSERT INTO GEO_MIT_HUB(ID, GEOMETRY) VALUES(?, ?)";

            statement = this.imgisConnector.getConnection().prepareStatement(sql);

            for (Hub hub : hubs) {
                statement.setLong(1, hub.getId());
                JGeometry geometry = GeometryUtil.createPointFromLatLong(hub.getXCoordEg(), hub.getYCoordEg());
                statement.setObject(2, JGeometry.storeJS(this.imgisConnector.getConnection(), geometry));
                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            throw new DAOException(DAOError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new DAOException(DAOError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }
    }

    public void closeConnection() throws DAOException {
        if(this.imgisConnector != null){
            if(this.imgisConnector.isConnected()) {
                this.imgisConnector.closeConnection();
            }
        }
    }

    public void cleanGeometryTable() throws DAOException {
        PreparedStatement statement;
        String sql;
        try {
            this.imgisConnector.connect();
            sql = "DELETE FROM GEO_MIT_HUB";

            statement = this.imgisConnector.getConnection().prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException(DAOError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new DAOException(DAOError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }
    }
}

