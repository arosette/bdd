package bdd.model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlConnection {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/bdd?";
    private static final String USER = "projet_bdd";
    private static final String PASSWORD = "projet_bdd";

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    static MysqlConnection instance;

    public Connection getConnection() {
	return connection;
    }

    public static MysqlConnection getInstance() {
	if (instance == null) {
	    try {
		instance = new MysqlConnection(USER, PASSWORD);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
	return instance;
    }

    public MysqlConnection(String user, String password) throws Exception {
	try {
	    Class.forName(DRIVER);
	    connection = DriverManager.getConnection(URL, USER, PASSWORD);

	} catch (Exception e) {
	    throw e;
	}
    }

    public void dataBaseExists() {
	try {
	    DatabaseMetaData databaseMetaData = connection.getMetaData();
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
		createTables(connection);
		createUsers(connection);
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    private void createTables(Connection connection) throws DAOException {

	try {
	    Statement stat = connection.createStatement();

	    String tabStream = "CREATE TABLE Stream (url VARCHAR(100) NOT NULL, name VARCHAR(40) NOT NULL, webLink VARCHAR (40), description TEXT, PRIMARY KEY(url))";
	    stat.executeUpdate(tabStream);

	    String tabUser = "CREATE TABLE User (mail VARCHAR(100) NOT NULL, surname VARCHAR(40) NOT NULL, password VARCHAR(40), avatar VARCHAR(100), country VARCHAR(40), city VARCHAR(40), biography TEXT, date DATE, personal_stream_url VARCHAR(100) NOT NULL, PRIMARY KEY(mail), FOREIGN KEY(personal_stream_url) REFERENCES Stream(url))";
	    stat.executeUpdate(tabUser);

	    String tabPublication = "CREATE TABLE Publication (url VARCHAR(100) NOT NULL, title VARCHAR(40) NOT NULL, date DATE, description TEXT, `read` BOOLEAN, PRIMARY KEY(url))";
	    stat.executeUpdate(tabPublication);

	    String tabFriendship = "CREATE TABLE Friendship (mail_sender VARCHAR(100) NOT NULL, mail_receiver VARCHAR(100) NOT NULL, status BOOLEAN, date DATE, PRIMARY KEY(mail_sender, mail_receiver), FOREIGN KEY(mail_sender) REFERENCES User(mail), FOREIGN KEY(mail_receiver) REFERENCES User(mail))";
	    stat.executeUpdate(tabFriendship);

	    String tabComment = "CREATE TABLE Comment (user_mail VARCHAR(100) NOT NULL, publication_url VARCHAR(100) NOT NULL, stream_url VARCHAR(100), content TEXT, date DATE, PRIMARY KEY(user_mail, publication_url, stream_url), FOREIGN KEY(user_mail) REFERENCES User(mail), FOREIGN KEY(publication_url) REFERENCES Publication(url), FOREIGN KEY(stream_url) REFERENCES Stream(url))";
	    stat.executeUpdate(tabComment);

	    String tabRead = "CREATE TABLE `Read` (user_mail VARCHAR(100) NOT NULL, publication_url VARCHAR(100) NOT NULL, date DATE, PRIMARY KEY(user_mail, publication_url), FOREIGN KEY(user_mail) REFERENCES User(mail), FOREIGN KEY(publication_url) REFERENCES Publication(url))";
	    stat.executeUpdate(tabRead);

	    String tabSubscribe = "CREATE TABLE Subscribe (user_mail VARCHAR(100) NOT NULL, stream_url VARCHAR(100) NOT NULL, date DATE, PRIMARY KEY(user_mail, stream_url), FOREIGN KEY(user_mail) REFERENCES User(mail), FOREIGN KEY(stream_url) REFERENCES Stream(url))";
	    stat.executeUpdate(tabSubscribe);

	    String tabPropose = "CREATE TABLE Propose (stream_url VARCHAR(100) NOT NULL, user_mail VARCHAR(100) NOT NULL, PRIMARY KEY(stream_url, user_mail), FOREIGN KEY(stream_url) REFERENCES Stream(url), FOREIGN KEY(user_mail) REFERENCES User(mail))";
	    stat.executeUpdate(tabPropose);

	    stat.close();
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public void createUsers(Connection connection) {
	try {
	    Statement stat = connection.createStatement();

	    String personalStream1 = "INSERT INTO Stream (url, name, webLink, description) VALUES (\"http://personal_stream/arosette@ulb.ac.be\", \"Flux personnel de arosette\", \"http://personal_stream\", \"Voici le flux personnel de arosette, c'est ici qu'il partage les publications qu'il aime\")";
	    stat.executeUpdate(personalStream1);

	    String user1 = "INSERT INTO User (mail, surname, password, avatar, country, city, biography, personal_stream_url) VALUES (\"arosette@ulb.ac.be\", \"arosette\", \"arosette\", \"~/Images/arosette.jpg\", \"Belgique\", \"Theux\", \"Je suis un etudiant qui etudie à l'ulb et vit à Theux\", \"http://personal_stream/arosette@ulb.ac.be\")";
	    stat.executeUpdate(user1);

	    String personalStream2 = "INSERT INTO Stream (url, name, webLink, description) VALUES (\"http://personal_stream/nomer@ulb.ac.be\", \"Flux personnel de nomer\", \"http://personal_stream\", \"Voici le flux personnel de nomer, c'est ici qu'il partage les publications qu'il aime\")";
	    stat.executeUpdate(personalStream2);

	    String user2 = "INSERT INTO User (mail, surname, password, avatar, country, city, biography, personal_stream_url) VALUES (\"nomer@ulb.ac.be\", \"nomer\", \"nomer\", \"~/Images/nomer.jpg\", \"Belgique\", \"Bruxelles\", \"Je suis un etudiant qui etudie à l'ulb et vit à Bruxelles\", \"http://personal_stream/nomer@ulb.ac.be\")";
	    stat.executeUpdate(user2);

	    stat.close();
	}

	catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public void deleteDataBase() throws DAOException {

	try {
	    Statement delStatement = connection.createStatement();

	    String delProposeQuery = "DROP TABLE Propose";
	    delStatement.executeUpdate(delProposeQuery);

	    String delSubscribeQuery = "DROP TABLE Subscribe";
	    delStatement.executeUpdate(delSubscribeQuery);

	    String delReadQuery = "DROP TABLE `Read`";
	    delStatement.executeUpdate(delReadQuery);

	    String delCommentQuery = "DROP TABLE Comment";
	    delStatement.executeUpdate(delCommentQuery);

	    String delFriendshipQuery = "DROP TABLE Friendship";
	    delStatement.executeUpdate(delFriendshipQuery);

	    String delPublicationQuery = "DROP TABLE Publication";
	    delStatement.executeUpdate(delPublicationQuery);

	    String delUserQuery = "DROP TABLE User";
	    delStatement.executeUpdate(delUserQuery);

	    String delStreamQuery = "DROP TABLE Stream";
	    delStatement.executeUpdate(delStreamQuery);

	    delStatement.close();
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public void showDataBase() throws DAOException {
	try {
	    statement = connection.createStatement();
	    ResultSet result = statement.executeQuery("SHOW TABLES");

	    while (result.next()) {
		String table = result.getString(1);
		readTable(table);
	    }

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public void readTable(String table) throws DAOException {
	try {
	    statement = connection.createStatement();

	    resultSet = statement.executeQuery("SELECT * from bdd." + table);
	    displayMetaData(resultSet);

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public void displayMetaData(ResultSet resultSet) throws SQLException {

	System.out.println("\nTable : "
		+ resultSet.getMetaData().getTableName(1));
	for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
	    System.out.println("Column " + i + " "
		    + resultSet.getMetaData().getColumnName(i));
	}
    }

    public void close() throws SQLException {
	resultSet.close();
	statement.close();
	connection.close();
    }
}