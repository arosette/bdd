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
    private PreparedStatement findStatementA = null;
    private PreparedStatement findStatementB = null;
    private PreparedStatement insertStatement = null;
    private PreparedStatement updateStatement = null;
    private PreparedStatement deleteStatement = null;
    private PreparedStatement getFriendshipsOfUser = null;

    public FriendshipDAOImpl() throws DAOException {
	mysqlConnection = MysqlConnection.getInstance();
	Connection connection = mysqlConnection.getConnection();
	try {
	    selectStatement = connection
		    .prepareStatement("SELECT * FROM Friendship");
	    findStatementA = connection
		    .prepareStatement("SELECT * FROM Friendship WHERE mail_sender = ?");
	    findStatementB = connection
		    .prepareStatement("SELECT * FROM Friendship WHERE mail_sender = ? AND mail_receiver = ?");
	    insertStatement = connection
		    .prepareStatement("INSERT INTO Friendship VALUES (?, ?, ?, CURDATE())");
	    deleteStatement = connection
		    .prepareStatement("DELETE FROM Friendship WHERE mail_sender = ? AND mail_receiver = ?");
	    getFriendshipsOfUser = connection
		    .prepareStatement("SELECT * FROM Friendship WHERE mail_sender = ? OR mail_receiver = ?");
	    updateStatement = connection
		    .prepareStatement("UPDATE Friendship SET status = ? WHERE mail_sender = ? AND mail_receiver = ?");

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
	    findStatementA.setString(1, mail_sender);
	    res = findStatementA.executeQuery();
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

    public Friendship find(String mail_sender, String mail_receiver)
	    throws DAOException {
	ResultSet res = null;
	try {
	    findStatementB.setString(1, mail_sender);
	    findStatementB.setString(2, mail_receiver);
	    res = findStatementB.executeQuery();
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
    public boolean insert(Friendship friendship) throws DAOException {
	try {

	    int i = 1;

	    insertStatement.setString(i++, friendship.getSenderMail());
	    insertStatement.setString(i++, friendship.getReceiverMail());
	    insertStatement.setBoolean(i++, friendship.getStatus());

	    int affectedRows = insertStatement.executeUpdate();
	    if (affectedRows == 0) {
		return false;
	    }
	    return true;

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    public boolean update(Friendship friendship) throws DAOException {

	try {
	    int i = 1;

	    updateStatement.setBoolean(i++, friendship.getStatus());
	    updateStatement.setString(i++, friendship.getSenderMail());
	    updateStatement.setString(i++, friendship.getReceiverMail());

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

    public List<Friendship> getFriendshipsOfUser(User user) {
	ResultSet res = null;
	List<Friendship> friendships = new ArrayList<Friendship>();
	try {
	    getFriendshipsOfUser.setString(1, user.getMail());
	    getFriendshipsOfUser.setString(2, user.getMail());
	    res = getFriendshipsOfUser.executeQuery();
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
    protected void finalize() throws Throwable {
	super.finalize();
	try {
	    if (selectStatement != null) {
		selectStatement.close();
	    }
	    if (findStatementA != null) {
		findStatementA.close();
	    }
	    if (findStatementB != null) {
		findStatementB.close();
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