package bdd.model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLAccess {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String MYSQL = "jdbc:mysql://localhost/bdd?"
	    + "user=test2user";

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public MySQLAccess() throws Exception {
	try {
	    Class.forName(DRIVER);
	    connect = DriverManager.getConnection(MYSQL);
	} catch (Exception e) {
	    throw e;
	}
    }

    public void dataBaseExists() {
	try {
	    DatabaseMetaData databaseMetaData = connect.getMetaData();
	    String catalog = null;
	    String schemaPattern = null;
	    String tableNamePattern = null;
	    String[] types = { "TABLE" };

	    boolean tablesNotExist = true;

	    ResultSet result = databaseMetaData.getTables(catalog,
		    schemaPattern, tableNamePattern, types);

	    while (result.next()) {
		String tableName = result.getString("TABLE_NAME");
		if ((tableName.equals("User") == true)
			|| (tableName.equals("Comment") == true)
			|| (tableName.equals("Friendship") == true)
			|| (tableName.equals("Publication") == true)
			|| (tableName.equals("Stream") == true)) {

		    tablesNotExist = false;
		}
	    }
	    if (tablesNotExist) {
		createTable(connect);
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    private void createTable(Connection connection) throws DAOException {

	try {
	    Statement stat = connection.createStatement();

	    String tabUser = "CREATE TABLE User (mail VARCHAR(100) NOT NULL, surname VARCHAR(40), password VARCHAR(40), avatar VARCHAR(100), country VARCHAR(40), city VARCHAR(40), biography TEXT, date DATE, PRIMARY KEY(mail))";
	    stat.executeUpdate(tabUser);

	    String tabStream = "CREATE TABLE Stream (url VARCHAR(100) NOT NULL, name VARCHAR(40), webLink VARCHAR (40), description TEXT, PRIMARY KEY(url))";
	    stat.executeUpdate(tabStream);

	    String tabPublication = "CREATE TABLE Publication (url VARCHAR(100) NOT NULL, title VARCHAR(40), description TEXT, date DATE, PRIMARY KEY(url))";
	    stat.executeUpdate(tabPublication);

	    String tabFriendship = "CREATE TABLE Friendship (mail_user_1 VARCHAR(100) NOT NULL, mail_user_2 VARCHAR(100) NOT NULL, status BOOLEAN, date DATE, asker VARCHAR(40), PRIMARY KEY(mail_user_1, mail_user_2))";
	    stat.executeUpdate(tabFriendship);

	    String tabComment = "CREATE TABLE Comment (mail VARCHAR(100) NOT NULL, link VARCHAR(100) NOT NULL, content TEXT, date DATE, PRIMARY KEY(mail, link))";
	    stat.executeUpdate(tabComment);

	    stat.close();
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public void deleteDatabase() throws DAOException {

	try {
	    Statement delStatement = connect.createStatement();

	    String delUserQuery = "DROP TABLE User";
	    delStatement.executeUpdate(delUserQuery);

	    String delStreamQuery = "DROP TABLE Stream";
	    delStatement.executeUpdate(delStreamQuery);

	    String delPublicationQuery = "DROP TABLE Publication";
	    delStatement.executeUpdate(delPublicationQuery);

	    String delFriendshipQuery = "DROP TABLE Friendship";
	    delStatement.executeUpdate(delFriendshipQuery);

	    String delCommentQuery = "DROP TABLE Comment";
	    delStatement.executeUpdate(delCommentQuery);

	    delStatement.close();
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public void showDataBase() throws DAOException {
	try {
	    statement = connect.createStatement();
	    // TODO
	    statement.executeQuery("SHOW TABLES");

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public void readDataBase() throws DAOException {
	try {
	    statement = connect.createStatement();

	    resultSet = statement.executeQuery("SELECT * from bdd.User");
	    displayMetaData(resultSet);

	} catch (SQLException e) {
	    throw new DAOException(e);
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

    public void close() throws SQLException {
	resultSet.close();
	statement.close();
	connect.close();
    }
}