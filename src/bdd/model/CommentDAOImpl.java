package bdd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAOImpl extends ConnectionMYSQL implements
	GenericDAO<Comment, String> {

    private PreparedStatement selectStatment = null;
    private PreparedStatement findStatement = null;
    private PreparedStatement insertStatement = null;
    private PreparedStatement deleteStatement = null;

    public CommentDAOImpl(ConnectionProvider connectionProvider)
	    throws DAOException {
	super(connectionProvider);
	Connection connection = null;
	try {
	    connection = getConnection();
	    selectStatment = connection.prepareStatement("SELECT * FROM Comment");
	    findStatement = connection
		    .prepareStatement("SELECT * FROM Comment WHERE mail = ?");
	    insertStatement = connection
		    .prepareStatement("INSERT INTO Comment VALUES (?, ?, ?, ?)");
	    deleteStatement = connection
		    .prepareStatement("DELETE FROM Comment WHERE mail = ?");

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
    public List<Comment> selectAll() throws DAOException {
	ResultSet res = null;
	List<Comment> comments = new ArrayList<Comment>();
	try {
	    res = selectStatment.executeQuery();
	    while (res.next()) {
		Comment comment = new Comment();
		comment.setMail(res.getString("mail"));
		comment.setUrl(res.getString("url"));
		comment.setContent(res.getString("content"));
		comment.setDate(res.getString("date"));
		comments.add(comment);
	    }
	    return comments;
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
    public Comment find(String mail) throws DAOException {
	ResultSet res = null;
	try {
	    findStatement.setString(1, mail);
	    res = findStatement.executeQuery();
	    if (res.next()) {
		Comment comment = new Comment();
		comment.setMail(res.getString("mail"));
		comment.setUrl(res.getString("url"));
		comment.setContent(res.getString("content"));
		comment.setDate(res.getString("date"));
		return comment;
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
    public void insert(Comment comment) throws DAOException {
	try {

	    int i = 1;

	    insertStatement.setString(i++, comment.getMail());
	    insertStatement.setString(i++, comment.getUrl());
	    insertStatement.setString(i++, comment.getContent());
	    insertStatement.setString(i++, comment.getDate());
	   
	    insertStatement.executeUpdate();

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    public boolean delete(Comment comment) throws DAOException {
	try {
	    deleteStatement.setString(1, comment.getMail());
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