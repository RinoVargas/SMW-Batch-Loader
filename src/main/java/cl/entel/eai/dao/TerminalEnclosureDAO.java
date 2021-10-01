package cl.entel.eai.dao;

import cl.entel.eai.configuration.connect.IMGISDatabaseConnector;
import cl.entel.eai.constants.DAOError;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.model.TerminalEnclosure;
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


public class TerminalEnclosureDAO {

    private static final Log logger = LogFactory.getLog(TerminalEnclosureDAO.class);

    @Autowired
    private IMGISDatabaseConnector imgisConnector;

    public List<TerminalEnclosure> getTerminalEnclosureChuck (long offset, int chunkSize) throws IMGISException {
        PreparedStatement statement;
        ResultSet resultSet;
        List<TerminalEnclosure> result = new ArrayList<>();
        String sql;

        try {
            sql = "SELECT ID, X_COORD_EG, Y_COORD_EG " +
                    "FROM MIT_TERMINAL_ENCLOSURE " +
                    "OFFSET ? ROWS " +
                    "FETCH NEXT ? ROWS ONLY";

            statement = this.imgisConnector.getConnection().prepareStatement(sql);
            statement.setLong(1, offset);
            statement.setInt(2, chunkSize);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TerminalEnclosure terminalEnclosure = new TerminalEnclosure();

                terminalEnclosure.setId(resultSet.getInt(1));
                terminalEnclosure.setXCoordEg(resultSet.getString(2));
                terminalEnclosure.setYCoordEg(resultSet.getString(3));

                result.add(terminalEnclosure);
            }
        } catch (SQLException e) {
            throw new IMGISException(DAOError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new IMGISException(DAOError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }

        return result;
    }

    public Integer getRecordCount() throws IMGISException{
        PreparedStatement statement;
        ResultSet resultSet;
        Integer result = null;
        String sql;

        try {
            if (!this.imgisConnector.isConnected()) {
                this.imgisConnector.connect();
            }
            sql = "SELECT COUNT(*) " +
                    "FROM MIT_TERMINAL_ENCLOSURE ";

            statement = this.imgisConnector.getConnection().prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new IMGISException(DAOError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new IMGISException(DAOError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }

        return result;
    }

    public void createGeoTerminalEnclosure(List<TerminalEnclosure> terminalEnclosures) throws IMGISException{
        PreparedStatement statement;
        String sql;

        try {

            sql = "INSERT INTO GEO_MIT_TERMINAL_ENCLOSURE(ID, GEOMETRY) VALUES(?, ?)";

            statement = this.imgisConnector.getConnection().prepareStatement(sql);

            for (TerminalEnclosure terminalEnclosure : terminalEnclosures) {
                statement.setLong(1, terminalEnclosure.getId());
                JGeometry geometry = GeometryUtil.createPointFromLatLong(terminalEnclosure.getXCoordEg(), terminalEnclosure.getYCoordEg());
                statement.setObject(2, JGeometry.storeJS(this.imgisConnector.getConnection(), geometry));
                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            throw new IMGISException(DAOError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new IMGISException(DAOError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }
    }

    public void closeConnection() throws IMGISException{
        if(this.imgisConnector != null){
            if(this.imgisConnector.isConnected()) {
                this.imgisConnector.closeConnection();
            }
        }
    }

    public void cleanGeometryTable() throws IMGISException{
        PreparedStatement statement;
        String sql;
        try {
            this.imgisConnector.connect();
            sql = "DELETE FROM GEO_MIT_TERMINAL_ENCLOSURE";

            statement = this.imgisConnector.getConnection().prepareStatement(sql);
            statement.execute();

        } catch (SQLException e) {
            throw new IMGISException(DAOError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new IMGISException(DAOError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }
    }
}

