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

	    // resultSet gets the result of the SQL query
	    resultSet = statement.executeQuery("SELECT * from bdd.User");
	    displayUserInfo(resultSet);

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

    public void displayUserInfo(ResultSet resultSet) throws SQLException {
	try {
	    while (resultSet.next()) {

		System.out.println("mail = " + resultSet.getString("mail"));
		System.out.println("surname = "
			+ resultSet.getString("surname"));
		System.out.println("password = "
			+ resultSet.getString("password"));
		System.out.println("avatar = " + resultSet.getString("avatar"));
		System.out.println("country = "
			+ resultSet.getString("country"));
		System.out.println("city = " + resultSet.getString("city"));
		System.out.println("biography = "
			+ resultSet.getString("biography"));
		System.out.println("registrationDate = "
			+ resultSet.getString("registrationDate"));
	    }
	} catch (SQLException e) {
	    throw e;
	}
    }

    public void InsertUser(User user) throws SQLException {
	try {
	    insertStatement = connect
		    .prepareStatement("INSERT INTO User VALUES (?, ?, ?, ?, ? , ?, ?, ?)");

	    int i = 1;

	    insertStatement.setString(i++, user.getMail());
	    insertStatement.setString(i++, user.getSurname());
	    insertStatement.setString(i++, user.getPassword());
	    insertStatement.setString(i++, user.getAvatar());
	    insertStatement.setString(i++, user.getCountry());
	    insertStatement.setString(i++, user.getCity());
	    insertStatement.setString(i++, user.getBiography());
	    insertStatement.setString(i++, user.getRegistrationDate());

	    insertStatement.executeUpdate();

	} catch (SQLException e) {
	    throw e;
	}
    }

    public void deleteUser(User user) throws SQLException {
	try {
	    deleteStatement = connect
		    .prepareStatement("DELETE FROM bdd.User WHERE mail = ? ;");
	    deleteStatement.setString(1, user.getMail());
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