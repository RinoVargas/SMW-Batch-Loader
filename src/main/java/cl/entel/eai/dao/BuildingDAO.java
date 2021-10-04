package cl.entel.eai.dao;

import cl.entel.eai.configuration.connect.IMGISDatabaseConnector;
import cl.entel.eai.constants.DAOError;
import cl.entel.eai.exception.DAOException;
import cl.entel.eai.model.Building;
import cl.entel.eai.util.GeometryUtil;
import oracle.spatial.geometry.JGeometry;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BuildingDAO {

    @Autowired
    private IMGISDatabaseConnector imgisConnector;

    public List<Building> getBuildingChuck (long offset, int chunkSize) throws DAOException {
        PreparedStatement statement;
        ResultSet resultSet;
        List<Building> result = new ArrayList<>();
        String sql;

        try {
            this.imgisConnector.connect();
            sql = "SELECT ID, X_COORD_EG, Y_COORD_EG " +
                    "FROM BUILDING " +
                    "OFFSET ? ROWS " +
                    "FETCH NEXT ? ROWS ONLY";

            statement = this.imgisConnector.getConnection().prepareStatement(sql);
            statement.setLong(1, offset);
            statement.setInt(2, chunkSize);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Building building = new Building();

                building.setId(resultSet.getInt(1));
                building.setXCoordEg(resultSet.getString(2));
                building.setYCoordEg(resultSet.getString(3));

                result.add(building);
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
            if (!this.imgisConnector.isConnected()) {
                this.imgisConnector.connect();
            }
            sql = "SELECT COUNT(*) " +
                    "FROM BUILDING ";

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

    public void createGeoBuilding(List<Building> buildings) throws DAOException {
        PreparedStatement statement;
        String sql;
        try {
            sql = "INSERT INTO GEO_BUILDING(ID, GEOMETRY) VALUES(?, ?)";

            statement = this.imgisConnector.getConnection().prepareStatement(sql);

            for (Building building : buildings) {
                statement.setLong(1, building.getId());
                JGeometry geometry = GeometryUtil.createPointFromLatLong(building.getXCoordEg(), building.getYCoordEg());
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
            sql = "DELETE FROM GEO_BUILDING";

            statement = this.imgisConnector.getConnection().prepareStatement(sql);
            statement.execute();

        } catch (SQLException e) {
            throw new DAOException(DAOError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new DAOException(DAOError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }
    }
}

