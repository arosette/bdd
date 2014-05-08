package bdd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLAccess {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String MYSQL = "jdbc:mysql://localhost/bdd?"
	    + "user=test2user";

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement insertStatement = null;
    private PreparedStatement deleteStatement = null;
    private ResultSet resultSet = null;

    public MySQLAccess() throws Exception {
	try {
	    Class.forName(DRIVER);
	    connect = DriverManager.getConnection(MYSQL);
	} catch (Exception e) {
	    throw e;
	}
    }

    public void readDataBase() throws Exception {
	try {
	    // statements allow to issue SQL queries to the database
	    statement = connect.createStatement();

	    resultSet = statement.executeQuery("SELECT * from bdd.User");
	    displayMetaData(resultSet);

	} catch (Exception e) {
	    throw e;
	}
    }

    public void displayMetaData(ResultSet resultSet) throws SQLException {

	System.out.println("The columns in the table are : ");
	System.out
		.println("Table : " + resultSet.getMetaData().getTableName(1));
	for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
	    System.out.println("Column " + i + " "
		    + resultSet.getMetaData().getColumnName(i));
	}
    }

    public void insertFriendship(Friendship friendship) throws SQLException {
	try {
	    insertStatement = connect
		    .prepareStatement("INSERT INTO Friendship VALUES (?, ?, ?, ?, ?)");

	    int i = 1;

	    insertStatement.setString(i++, friendship.getMailUser1());
	    insertStatement.setString(i++, friendship.getMailUser2());
	    insertStatement.setBoolean(i++, friendship.getStatus());
	    insertStatement.setString(i++, friendship.getDate());
	    insertStatement.setString(i++, friendship.getAsker());

	    insertStatement.executeUpdate();

	} catch (SQLException e) {
	    throw e;
	}
    }

    public void deleteFriendship(Friendship friendship) throws SQLException {
	try {
	    deleteStatement = connect
		    .prepareStatement("DELETE FROM bdd.Friendship WHERE mailUser1 = ? AND mailUser2 = ?;");
	    deleteStatement.setString(1, friendship.getMailUser1());
	    deleteStatement.setString(1, friendship.getMailUser2());
	    deleteStatement.executeUpdate();
	} catch (SQLException e) {
	    throw e;
	}
    }

    public void close() throws SQLException {
	resultSet.close();
	statement.close();
	connect.close();
    }
}