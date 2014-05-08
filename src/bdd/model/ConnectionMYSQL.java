package bdd.model;

import bdd.model.ConnectionProvider;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Handles the connections provided by the pool.
 */

public class ConnectionMYSQL {

    private Connection connection;
    private ConnectionProvider connectionProvider;

    /**
     * Instantiates a {@link ConnectionSQLite}.
     * 
     * @param connectionProvider
     *            the connection provider ensures the fact that will get one of
     *            the connections from the pool.
     */
    public ConnectionMYSQL(ConnectionProvider connectionProvider) {
	this.connectionProvider = connectionProvider;
    }

    /**
     * Gets connection.
     * 
     * @return the connection in order to provide interaction with the database
     *         layer.
     * @throws SQLException
     * 
     */
    protected Connection getConnection() throws SQLException {
	if (connection != null && connection.isClosed() == true) {
	    connection = null;
	}
	if (connection == null) {
	    return connectionProvider.getConnection();
	} else {
	    return connection;
	}

    }

}