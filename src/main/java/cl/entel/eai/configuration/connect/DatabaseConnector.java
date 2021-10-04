package cl.entel.eai.configuration.connect;

import cl.entel.eai.constants.DAOError;
import cl.entel.eai.exception.DAOException;
import oracle.jdbc.pool.OracleDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseConnector {
    protected String url;
    protected String username;
    protected String password;
    protected Connection connection;

    public DatabaseConnector(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    public void connect() throws DAOException {

        if (!this.isConnected()) {
            try {

                OracleDataSource dataSource = new OracleDataSource();
                dataSource.setURL(this.url);
                dataSource.setUser(this.username);
                dataSource.setPassword(this.password);
                dataSource.setImplicitCachingEnabled(true);
                dataSource.setFastConnectionFailoverEnabled(true);
                this.connection = dataSource.getConnection();

            } catch (SQLException e) {
                throw new DAOException(DAOError.ERROR_DB_NOT_CONNECTED, e.getMessage());
            }
        }
    }

    public void close(Statement statement) throws DAOException {
        try {
            closeConnection();
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e){
            throw new DAOException(DAOError.ERROR_DB_UNAVAILABLE_DISCONNECTION, e.getMessage());
        } catch (Exception e){
            throw new DAOException(DAOError.ERROR_DB_UNKNOWN_ERROR, e.getMessage());
        }
    }

    public void closeConnection() throws DAOException {
        try {
            this.connection.close();
            this.connection = null;
        } catch (SQLException e) {
            throw new DAOException(DAOError.ERROR_DB_UNAVAILABLE_DISCONNECTION, e.getMessage());
        } catch (Exception e) {
            throw new DAOException(DAOError.ERROR_UNKNOWN_ERROR, e.getMessage());
        }
    }

    public static void releaseResources(Statement statement) {
        if (statement != null) {
            try { statement.close(); } catch (SQLException ignore) { };
        }
    }
}
