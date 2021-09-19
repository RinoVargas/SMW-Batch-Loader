package cl.entel.eai.config;

import cl.entel.eai.constants.IMGISError;
import cl.entel.eai.exception.IMGISException;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseConnector {
    protected String url;
    protected String username;
    protected String password;
    protected Connection connection;

        Log logger = LogFactory.getLog(DatabaseConnector.class);

    public DatabaseConnector(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean connect() throws SQLException{
        logger.info("Conectando a la base de datos...");

        boolean isConnected = false;
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL(this.url);
        dataSource.setUser(this.username);
        dataSource.setPassword(this.password);
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        this.connection = dataSource.getConnection();
        isConnected = true;

        logger.info("Conexión exitosa a la base de datos!");
        return isConnected;
    }

    public void close(Statement statement) throws IMGISException {
        try {
            closeConnection();
            if (statement != null) {
                logger.info("Liberando recursos...");
                statement.close();
            }
        } catch (SQLException e){
            logger.error(e.getMessage());
            throw new IMGISException(IMGISError.ERROR_DB_UNAVAILABLE_DISCONNECTION, e.getMessage());
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new IMGISException(IMGISError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        }
    }

    public void closeConnection() throws IMGISException {
        try {
            logger.info("Cerrando conexiones con la base de datos...");
            this.connection.close();
            this.connection = null;
            logger.info("Conexiones cerradas.");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new IMGISException(IMGISError.ERROR_DB_UNAVAILABLE_DISCONNECTION, e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new IMGISException(IMGISError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }
    }
}
