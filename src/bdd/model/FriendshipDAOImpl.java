package bdd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendshipDAOImpl extends ConnectionMYSQL implements
	GenericDAO<Friendship, String> {

    private PreparedStatement selectStatment = null;
    private PreparedStatement findStatement = null;
    private PreparedStatement insertStatement = null;
    private PreparedStatement deleteStatement = null;

    public FriendshipDAOImpl(ConnectionProvider connectionProvider)
	    throws DAOException {
	super(connectionProvider);
	Connection connection = null;
	try {
	    connection = getConnection();
	    selectStatment = connection.prepareStatement("SELECT * FROM Friendship");
	    findStatement = connection
		    .prepareStatement("SELECT * FROM Friendship WHERE mailUser1 = ? ;");
	    insertStatement = connection
		    .prepareStatement("INSERT INTO Friendship VALUES (?, ?, ?, ?, ?)");
	    deleteStatement = connection
		    .prepareStatement("DELETE FROM Friendship WHERE mailUser1 = ?");

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

    @Override
    public List<Friendship> selectAll() throws DAOException {
	ResultSet res = null;
	List<Friendship> friendships = new ArrayList<Friendship>();
	try {
	    res = selectStatment.executeQuery();
	    while (res.next()) {
		Friendship friendship = new Friendship();
		friendship.setMailUser1(res.getString("mailUser1"));
		friendship.setMailUser2(res.getString("mailUser2"));
		friendship.setStatus(res.getBoolean("status"));
		friendship.setDate(res.getString("date"));
		friendship.setAsker(res.getString("asker"));
		friendships.add(friendship);
	    }
	    return friendships;
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
    public Friendship find(String mailUser1) throws DAOException {
	ResultSet res = null;
	try {
	    findStatement.setString(1, mailUser1);
	    res = findStatement.executeQuery();
	    if (res.next()) {
		Friendship friendship = new Friendship();
		friendship.setMailUser1(res.getString("mailUser1"));
		friendship.setMailUser2(res.getString("mailUser2"));
		friendship.setStatus(res.getBoolean("status"));
		friendship.setDate(res.getString("date"));
		friendship.setAsker(res.getString("asker"));
		return friendship;
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
    public void insert(Friendship friendship) throws DAOException {
	try {

	    int i = 1;

	    insertStatement.setString(i++, friendship.getMailUser1());
	    insertStatement.setString(i++, friendship.getMailUser2());
	    insertStatement.setBoolean(i++, friendship.getStatus());
	    insertStatement.setString(i++, friendship.getDate());
	    insertStatement.setString(i++, friendship.getAsker());
	    
	    insertStatement.executeUpdate();

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    public boolean delete(Friendship friendship) throws DAOException {
	try {

	    deleteStatement.setString(1, friendship.getMailUser1());
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
	    if (selectStatment != null) {
		selectStatment.close();
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