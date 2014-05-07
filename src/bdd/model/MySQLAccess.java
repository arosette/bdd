package bdd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLAccess {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String MYSQL = "jdbc:mysql://localhost/bdd?"
	    + "user=test2user";

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement selectStatement = null;
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

    public User findUser(String mail) throws SQLException {
	ResultSet res = null;
	try {
	    selectStatement = connect
		    .prepareStatement("SELECT * FROM User WHERE mail = ? ;");
	    selectStatement.setString(1, mail);
	    res = selectStatement.executeQuery();
	    if (res.next()) {
		User user = new User();
		user.setMail(res.getString("mail"));
		user.setSurname(res.getString("surname"));
		user.setPassword(res.getString("password"));
		user.setAvatar(res.getString("avatar"));
		user.setCountry(res.getString("country"));
		user.setCity(res.getString("city"));
		user.setBiography(res.getString("biography"));
		user.setRegistrationDate(res.getString("registrationDate"));
		return user;
	    }
	    return null;

	} catch (SQLException e) {
	    throw e;
	} finally {
	    try {
		res.close();
	    } catch (SQLException e) {
		throw e;
	    }
	}
    }

    public List<User> selectAllUsers() throws SQLException {

	ResultSet res = null;
	List<User> users = new ArrayList<User>();
	selectStatement = connect.prepareStatement("SELECT * FROM User");

	try {
	    res = selectStatement.executeQuery();
	    while (res.next()) {
		User user = new User();
		user.setMail(res.getString("mail"));
		user.setSurname(res.getString("surname"));
		user.setPassword(res.getString("password"));
		user.setAvatar(res.getString("avatar"));
		user.setCountry(res.getString("country"));
		user.setCity(res.getString("city"));
		user.setBiography(res.getString("biography"));
		user.setRegistrationDate(res.getString("registrationDate"));
		users.add(user);
	    }
	    return users;
	} catch (SQLException e) {
	    throw e;
	} finally {
	    try {
		res.close();
	    } catch (SQLException e) {
		throw e;
	    }
	}
    }

    public void insertUser(User user) throws SQLException {
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

    public Publication findPublication(String link) throws SQLException {
	ResultSet res = null;
	try {
	    selectStatement = connect
		    .prepareStatement("SELECT * FROM Publication WHERE link = ? ;");
	    selectStatement.setString(1, link);
	    res = selectStatement.executeQuery();
	    if (res.next()) {
		Publication publication = new Publication();
		publication.setUrl(res.getString("link"));
		publication.setTitle(res.getString("title"));
		publication.setDescription(res.getString("date"));
		publication.setDate(res.getString("description"));
		return publication;
	    }
	    return null;

	} catch (SQLException e) {
	    throw e;
	} finally {
	    try {
		res.close();
	    } catch (SQLException e) {
		throw e;
	    }
	}
    }

    public List<Publication> selectAllPublications() throws SQLException {
	ResultSet res = null;
	List<Publication> publications = new ArrayList<Publication>();
	selectStatement = connect.prepareStatement("SELECT * FROM Publication");
	try {
	    res = selectStatement.executeQuery();
	    while (res.next()) {
		Publication publication = new Publication();
		publication.setUrl(res.getString("link"));
		publication.setTitle(res.getString("title"));
		publication.setDescription(res.getString("date"));
		publication.setDate(res.getString("description"));
		publications.add(publication);
	    }
	    return publications;
	} catch (SQLException e) {
	    throw e;
	} finally {
	    try {
		res.close();
	    } catch (SQLException e) {
		throw e;
	    }
	}
    }

    public void insertPublication(Publication publication) throws SQLException {
	try {
	    insertStatement = connect
		    .prepareStatement("INSERT INTO Publication VALUES (?, ?, ?, ?)");

	    int i = 1;

	    insertStatement.setString(i++, publication.getUrl());
	    insertStatement.setString(i++, publication.getTitle());
	    insertStatement.setString(i++, publication.getDescription());
	    insertStatement.setString(i++, publication.getDate());

	    insertStatement.executeUpdate();

	} catch (SQLException e) {
	    throw e;
	}
    }

    public void deletePublication(Publication publication) throws SQLException {
	try {
	    deleteStatement = connect
		    .prepareStatement("DELETE FROM bdd.Publication WHERE link = ? ;");
	    deleteStatement.setString(1, publication.getUrl());
	    deleteStatement.executeUpdate();
	} catch (SQLException e) {
	    throw e;
	}
    }

    public Stream findStream(String url) throws SQLException {
	ResultSet res = null;
	try {
	    selectStatement = connect
		    .prepareStatement("SELECT * FROM Stream WHERE url = ? ;");
	    selectStatement.setString(1, url);
	    res = selectStatement.executeQuery();
	    if (res.next()) {
		Stream stream = new Stream();
		stream.setUrl(res.getString("url"));
		stream.setName(res.getString("name"));
		stream.setDescription(res.getString("description"));
		stream.setWebLink(res.getString("webLink"));
		return stream;
	    }
	    return null;

	} catch (SQLException e) {
	    throw e;
	} finally {
	    try {
		res.close();
	    } catch (SQLException e) {
		throw e;
	    }
	}
    }

    public List<Stream> selectAllStreams() throws SQLException {
	ResultSet res = null;
	List<Stream> streams = new ArrayList<Stream>();
	selectStatement = connect.prepareStatement("SELECT * FROM Stream");
	try {
	    res = selectStatement.executeQuery();
	    while (res.next()) {
		Stream stream = new Stream();
		stream.setUrl(res.getString("url"));
		stream.setName(res.getString("name"));
		stream.setDescription(res.getString("description"));
		stream.setWebLink(res.getString("webLink"));
		streams.add(stream);
	    }
	    return streams;
	} catch (SQLException e) {
	    throw e;
	} finally {
	    try {
		res.close();
	    } catch (SQLException e) {
		throw e;
	    }
	}
    }

    public void insertStream(Stream stream) throws SQLException {
	try {
	    insertStatement = connect
		    .prepareStatement("INSERT INTO Stream VALUES (?, ?, ?, ?)");

	    int i = 1;

	    insertStatement.setString(i++, stream.getUrl());
	    insertStatement.setString(i++, stream.getName());
	    insertStatement.setString(i++, stream.getDescription());
	    insertStatement.setString(i++, stream.getWebLink());

	    insertStatement.executeUpdate();

	} catch (SQLException e) {
	    throw e;
	}
    }

    public void deleteStream(Stream stream) throws SQLException {
	try {
	    deleteStatement = connect
		    .prepareStatement("DELETE FROM bdd.Stream WHERE url = ? ;");
	    deleteStatement.setString(1, stream.getUrl());
	    deleteStatement.executeUpdate();
	} catch (SQLException e) {
	    throw e;
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

    public void insertComment(Comment comment) throws SQLException {
	try {
	    insertStatement = connect
		    .prepareStatement("INSERT INTO Comment VALUES (?, ?, ?, ?)");

	    int i = 1;

	    insertStatement.setString(i++, comment.getMail());
	    insertStatement.setString(i++, comment.getLink());
	    insertStatement.setString(i++, comment.getContent());
	    insertStatement.setString(i++, comment.getDate());

	    insertStatement.executeUpdate();

	} catch (SQLException e) {
	    throw e;
	}
    }

    public void deleteComment(Comment comment) throws SQLException {
	try {
	    deleteStatement = connect
		    .prepareStatement("DELETE FROM bdd.Comment WHERE mail = ? AND link = ?;");
	    deleteStatement.setString(1, comment.getMail());
	    deleteStatement.setString(1, comment.getLink());
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