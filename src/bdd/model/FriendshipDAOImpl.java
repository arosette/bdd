package bdd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendshipDAOImpl implements GenericDAO<Friendship, String> {

    private MysqlConnection mysqlConnection = null;

    private PreparedStatement selectStatement = null;
    private PreparedStatement findStatement = null;
    private PreparedStatement insertStatement = null;
    private PreparedStatement updateStatement = null;
    private PreparedStatement deleteStatement = null;

    public FriendshipDAOImpl() throws DAOException {
	mysqlConnection = MysqlConnection.getInstance();
	Connection connection = mysqlConnection.getConnection();
	try {
	    selectStatement = connection
		    .prepareStatement("SELECT * FROM Friendship");
	    findStatement = connection
		    .prepareStatement("SELECT * FROM Friendship WHERE mail_sender = ?");
	    insertStatement = connection
		    .prepareStatement("INSERT INTO Friendship VALUES (?, ?, ?, ?)");
	    deleteStatement = connection
		    .prepareStatement("DELETE FROM Friendship WHERE mail_sender = ? AND mail_receiver = ?");

	} catch (SQLException e) {
	    throw new DAOException(e);
	} 
    }

    @Override
    public List<Friendship> selectAll() throws DAOException {
	ResultSet res = null;
	List<Friendship> friendships = new ArrayList<Friendship>();
	try {
	    res = selectStatement.executeQuery();
	    while (res.next()) {
		Friendship friendship = new Friendship();
		friendship.setSenderMail(res.getString("mail_sender"));
		friendship.setReceiverMail(res.getString("mail_receiver"));
		friendship.setStatus(res.getBoolean("status"));
		friendship.setDate(res.getString("date"));
		friendships.add(friendship);
	    }
	    return friendships;
	} catch (SQLException e) {
	    throw new DAOException(e);
	} 
    }

    @Override
    public Friendship find(String mail_sender) throws DAOException {
	ResultSet res = null;
	try {
	    findStatement.setString(1, mail_sender);
	    res = findStatement.executeQuery();
	    if (res.next()) {
		Friendship friendship = new Friendship();
		friendship.setSenderMail(res.getString("mail_sender"));
		friendship.setReceiverMail(res.getString("mail_receiver"));
		friendship.setStatus(res.getBoolean("status"));
		friendship.setDate(res.getString("date"));
		return friendship;
	    }
	    return null;

	} catch (SQLException e) {
	    throw new DAOException(e);
	} 
    }

    @Override
    public void insert(Friendship friendship) throws DAOException {
	try {

	    int i = 1;

	    insertStatement.setString(i++, friendship.getSenderMail());
	    insertStatement.setString(i++, friendship.getReceiverMail());
	    insertStatement.setBoolean(i++, friendship.getStatus());
	    insertStatement.setString(i++, friendship.getDate());

	    insertStatement.executeUpdate();

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    public boolean update(Friendship friendship) throws DAOException {

	try {
	    int i = 1;

	    insertStatement.setString(i++, friendship.getSenderMail());
	    insertStatement.setString(i++, friendship.getReceiverMail());
	    updateStatement.setBoolean(i++, friendship.getStatus());
	    updateStatement.setString(i++, friendship.getDate());

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
    public boolean delete(Friendship friendship) throws DAOException {
	try {
	    deleteStatement.setString(1, friendship.getSenderMail());
	    deleteStatement.setString(2, friendship.getReceiverMail());
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
	    if (deleteStatement != null) {
		deleteStatement.close();
	    }

	} catch (Throwable t) {
	}
    }
}