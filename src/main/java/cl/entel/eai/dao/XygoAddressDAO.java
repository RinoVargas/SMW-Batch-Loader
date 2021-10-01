package cl.entel.eai.dao;

import cl.entel.eai.configuration.connect.FACTUNIFDatabaseConnector;
import cl.entel.eai.constants.IMGISError;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.model.XygoAddress;
import cl.entel.eai.util.GeometryUtil;
import oracle.spatial.geometry.JGeometry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class XygoAddressDAO {

    private static final Log logger = LogFactory.getLog(XygoAddressDAO.class);

    @Autowired
    private FACTUNIFDatabaseConnector factunifConnector;

    public List<XygoAddress> getXygoAddressChuck (long offset, int chunkSize) throws IMGISException {
        PreparedStatement statement;
        ResultSet resultSet;
        List<XygoAddress> result = new ArrayList<>();
        String sql;

        try {
            this.factunifConnector.connect();
            sql = "SELECT ID_XYGO, LONGITUDE, LATITUDE " +
                    "FROM FU_XYGO " +
                    "WHERE PARENT_ID_XYGO IS NULL AND " +
                        "LONGITUDE IS NOT NULL AND " +
                        "LATITUDE IS NOT NULL " +
                    "OFFSET ? ROWS " +
                    "FETCH NEXT ? ROWS ONLY";

            statement = this.factunifConnector.getConnection().prepareStatement(sql);
            statement.setLong(1, offset);
            statement.setInt(2, chunkSize);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                XygoAddress xygoAddress = new XygoAddress();

                xygoAddress.setIdXygo(resultSet.getInt(1));
                xygoAddress.setLongitude(resultSet.getString(2));
                xygoAddress.setLatitude(resultSet.getString(3));

                result.add(xygoAddress);
            }
        } catch (SQLException e) {
            throw new IMGISException(IMGISError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new IMGISException(IMGISError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }

        return result;
    }

    public Integer getRecordCount() throws IMGISException{
        PreparedStatement statement;
        ResultSet resultSet;
        Integer result = null;
        String sql;

        try {
            if (!this.factunifConnector.isConnected()) {
                this.factunifConnector.connect();
            }
            sql = "SELECT COUNT(*) " +
                    "FROM FU_XYGO ";

            statement = this.factunifConnector.getConnection().prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new IMGISException(IMGISError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new IMGISException(IMGISError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }

        return result;
    }

    public void createGeoXygoAddress(List<XygoAddress> xygoAddresses) throws IMGISException{
        PreparedStatement statement;
        String sql;
        try {

            sql = "INSERT INTO FU_GEO_XYGO(ID_XYGO, GEOMETRY) VALUES(?, ?)";

            statement = this.factunifConnector.getConnection().prepareStatement(sql);

            for (XygoAddress xygoAddress : xygoAddresses) {
                statement.setLong(1, xygoAddress.getIdXygo());
                JGeometry geometry = GeometryUtil.createPointFromLatLong(xygoAddress.getLongitude(), xygoAddress.getLatitude());
                statement.setObject(2, JGeometry.storeJS(this.factunifConnector.getConnection(), geometry));
                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            throw new IMGISException(IMGISError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new IMGISException(IMGISError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }
    }

    public void closeConnection() throws IMGISException{
        if(this.factunifConnector != null){
            if(this.factunifConnector.isConnected()) {
                this.factunifConnector.closeConnection();
            }
        }
    }
}

