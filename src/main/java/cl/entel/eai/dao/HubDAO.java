package cl.entel.eai.dao;

import cl.entel.eai.config.IMGISDatabaseConnector;
import cl.entel.eai.constants.IMGISError;
import cl.entel.eai.exception.IMGISException;
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
    private IMGISDatabaseConnector connector;

    public List<Hub> getHubChuck (long offset, int chunkSize) throws IMGISException {
        PreparedStatement statement;
        ResultSet resultSet;
        List<Hub> result = new ArrayList<>();

        try {
            if (this.connector.connect()) {
                String sql = "SELECT ID, X_COORD_EG, Y_COORD_EG " +
                        "FROM MIT_HUB " +
                        "OFFSET ? ROWS " +
                        "FETCH NEXT ? ROWS ONLY";

                statement = this.connector.getConnection().prepareStatement(sql);
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

            } else {
                throw new IMGISException(IMGISError.ERROR_DB_NOT_CONNECTED);
            }
        } catch (SQLException e) {
            throw new IMGISException(IMGISError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IMGISException(IMGISError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }

        return result;
    }

    public Integer getRecordCount() throws IMGISException{
        PreparedStatement statement;
        ResultSet resultSet;
        Integer result = null;

        try {
            if (this.connector.connect()) {
                String sql = "SELECT COUNT(*) " +
                        "FROM MIT_HUB ";

                statement = this.connector.getConnection().prepareStatement(sql);
                resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    result = resultSet.getInt(1);
                }

            } else {
                throw new IMGISException(IMGISError.ERROR_DB_NOT_CONNECTED);
            }
        } catch (SQLException e) {
            throw new IMGISException(IMGISError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IMGISException(IMGISError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }

        return result;
    }

    public void createGeoHubs(List<Hub> hubs) throws IMGISException{
        PreparedStatement statement;

        try {
            if (this.connector.connect()) {
                String sql = "INSERT INTO GEO_MIT_HUB(ID, GEOMETRY) VALUES(?, ?)";

                statement = this.connector.getConnection().prepareStatement(sql);

                for (Hub hub : hubs) {
                    statement.setLong(1, hub.getId());
                    JGeometry geometry = GeometryUtil.createPointFromLatLong(hub.getXCoordEg(), hub.getYCoordEg());
                    statement.setObject(2, JGeometry.storeJS(this.connector.getConnection(), geometry));
                    statement.addBatch();
                }

                statement.executeBatch();

            } else {
                throw new IMGISException(IMGISError.ERROR_DB_NOT_CONNECTED);
            }
        } catch (SQLException e) {
            throw new IMGISException(IMGISError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new IMGISException(IMGISError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }
    }
}

