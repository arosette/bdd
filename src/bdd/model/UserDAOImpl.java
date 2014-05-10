package bdd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements GenericDAO<User, String> {

    private MysqlConnection mysqlConnection = null;

    private PreparedStatement selectStatement = null;
    private PreparedStatement findStatement = null;
    private PreparedStatement friendStatement = null;
    private PreparedStatement insertStatement = null;
    private PreparedStatement updateStatement = null;
    private PreparedStatement deleteStatement = null;

    public UserDAOImpl() throws DAOException {
	mysqlConnection = MysqlConnection.getInstance();
	Connection connection = mysqlConnection.getConnection();
	try {
	    selectStatement = connection.prepareStatement("SELECT * FROM User");
	    findStatement = connection
		    .prepareStatement("SELECT * FROM User WHERE mail = ?");
	    friendStatement = connection
		    .prepareStatement("SELECT * FROM Friendship WHERE Status = TRUE AND (mail_sender = ? OR mail_receiver = ?)");
	    insertStatement = connection
		    .prepareStatement("INSERT INTO User VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
	    deleteStatement = connection
		    .prepareStatement("DELETE FROM User WHERE mail = ?");

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    public List<User> selectAll() throws DAOException {
	ResultSet res = null;
	List<User> users = new ArrayList<User>();
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
		user.setDate(res.getString("date"));
		user.setPersonalStream(res.getString("personal_stream_url"));
		users.add(user);
	    }
	    return users;
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public List<User> listFriend(User user) throws DAOException {
	ResultSet res = null;
	List<User> friends = new ArrayList<User>();
	try {
	    friendStatement.setString(1, user.getMail());
	    friendStatement.setString(2, user.getMail());
	    res = friendStatement.executeQuery();
	    if (res.next()) {
		User friend = new User();
		friend.setMail(res.getString("mail"));
		friend.setSurname(res.getString("surname"));
		friend.setPassword(res.getString("password"));
		friend.setAvatar(res.getString("avatar"));
		friend.setCountry(res.getString("country"));
		friend.setCity(res.getString("city"));
		friend.setBiography(res.getString("biography"));
		friend.setDate(res.getString("date"));
		friend.setPersonalStream(res.getString("personal_stream_url"));
		friends.add(friend);
	    }
	    return friends;

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }
    
    @Override
    public User find(String mail) throws DAOException {
	ResultSet res = null;
	try {
	    findStatement.setString(1, mail);
	    res = findStatement.executeQuery();
	    if (res.next()) {
		User user = new User();
		user.setMail(res.getString("mail"));
		user.setSurname(res.getString("surname"));
		user.setPassword(res.getString("password"));
		user.setAvatar(res.getString("avatar"));
		user.setCountry(res.getString("country"));
		user.setCity(res.getString("city"));
		user.setBiography(res.getString("biography"));
		user.setDate(res.getString("date"));
		user.setPersonalStream(res.getString("personal_stream_url"));
		return user;
	    }
	    return null;

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }
    
    @Override
    public void insert(User user) throws DAOException {
	try {

	    int i = 1;

	    insertStatement.setString(i++, user.getMail());
	    insertStatement.setString(i++, user.getSurname());
	    insertStatement.setString(i++, user.getPassword());
	    insertStatement.setString(i++, user.getAvatar());
	    insertStatement.setString(i++, user.getCountry());
	    insertStatement.setString(i++, user.getCity());
	    insertStatement.setString(i++, user.getBiography());
	    insertStatement.setString(i++, user.getDate());
	    insertStatement.setString(i++, user.getPersonalStream());

	    insertStatement.executeUpdate();

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    public boolean update(User user) throws DAOException {

	try {
	    int i = 1;

	    updateStatement.setString(i++, user.getMail());
	    updateStatement.setString(i++, user.getSurname());
	    updateStatement.setString(i++, user.getPassword());
	    updateStatement.setString(i++, user.getAvatar());
	    updateStatement.setString(i++, user.getCountry());
	    updateStatement.setString(i++, user.getCity());
	    updateStatement.setString(i++, user.getBiography());
	    updateStatement.setString(i++, user.getDate());
	    insertStatement.setString(i++, user.getPersonalStream());

	    int affectedRows = updateStatement.executeUpdate();
	    if (affectedRows == 0) {
		return false;
	    }
	    return true;
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    public boolean delete(User user) throws DAOException {
	try {

	    deleteStatement.setString(1, user.getMail());
	    int affectedRows = deleteStatement.executeUpdate();
	    if (affectedRows == 0) {
		return false;
	    }
	    return true;
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    protected void finalize() throws Throwable {
	super.finalize();
	try {
	    if (selectStatement != null) {
		selectStatement.close();
	    }
	    if (findStatement != null) {
		findStatement.close();
	    }
	    if (insertStatement != null) {
		insertStatement.close();
	    }
	    if (updateStatement != null) {
		updateStatement.close();
	    }
	    if (deleteStatement != null) {
		deleteStatement.close();
	    }

	} catch (Throwable t) {
	}
    }
}