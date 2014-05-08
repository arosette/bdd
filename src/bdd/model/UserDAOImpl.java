package bdd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends ConnectionMYSQL implements
	GenericDAO<User, String> {

    private PreparedStatement selectStatment = null;
    private PreparedStatement findStatement = null;
    private PreparedStatement insertStatement = null;
    private PreparedStatement deleteStatement = null;

    public UserDAOImpl(ConnectionProvider connectionProvider)
	    throws DAOException {
	super(connectionProvider);
	Connection connection = null;
	try {
	    connection = getConnection();
	    selectStatment = connection.prepareStatement("SELECT * FROM User");
	    findStatement = connection
		    .prepareStatement("SELECT * FROM User WHERE mail = ? ;");
	    insertStatement = connection
		    .prepareStatement("INSERT INTO User VALUES (?, ?, ?, ?, ? , ?, ?, ?)");
	    deleteStatement = connection
		    .prepareStatement("DELETE FROM USERS WHERE mail = ?");

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		throw new DAOException(e);
	    }
	}
    }

    // public void displayUserInfo(ResultSet resultSet) throws SQLException {
    // try {
    // while (resultSet.next()) {
    //
    // System.out.println("mail = " + resultSet.getString("mail"));
    // System.out.println("surname = "
    // + resultSet.getString("surname"));
    // System.out.println("password = "
    // + resultSet.getString("password"));
    // System.out.println("avatar = " + resultSet.getString("avatar"));
    // System.out.println("country = "
    // + resultSet.getString("country"));
    // System.out.println("city = " + resultSet.getString("city"));
    // System.out.println("biography = "
    // + resultSet.getString("biography"));
    // System.out.println("registrationDate = "
    // + resultSet.getString("registrationDate"));
    // }
    // } catch (SQLException e) {
    // throw new DAOException(e);
    // }
    // }

    @Override
    public List<User> selectAll() throws DAOException {
	ResultSet res = null;
	List<User> users = new ArrayList<User>();
	try {
	    res = selectStatment.executeQuery();
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
	    throw new DAOException(e);
	} finally {
	    try {
		res.close();
	    } catch (SQLException e) {
		throw new DAOException(e);
	    }
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
		user.setRegistrationDate(res.getString("registrationDate"));
		return user;
	    }
	    return null;

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    try {
		res.close();
	    } catch (SQLException e) {
		throw new DAOException(e);
	    }
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
	    insertStatement.setString(i++, user.getRegistrationDate());

	    insertStatement.executeUpdate();

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    public void delete(User user) throws DAOException {
	try {

	    deleteStatement.setString(1, user.getMail());
	    deleteStatement.executeUpdate();
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

}
