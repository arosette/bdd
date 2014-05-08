package bdd.model;

import java.sql.Connection;

/**
 * Interface that provides a method to be implemented in order to get a
 * connection.
 * 
 */

public interface ConnectionProvider {

    /**
     * Get the connection that is used to interact with the database.
     * 
     * @return the connection for works with the stored data.
     * @throws DAOException
     *             If something fails at database level.
     */

    Connection getConnection() throws DAOException;
}