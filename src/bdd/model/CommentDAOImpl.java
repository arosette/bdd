package bdd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAOImpl implements GenericDAO<Comment, String> {

    private MysqlConnection mysqlConnection = null;

    private PreparedStatement selectStatement = null;
    private PreparedStatement findStatementA = null;
    private PreparedStatement findStatementB = null;
    private PreparedStatement insertStatement = null;
    private PreparedStatement updateStatement = null;
    private PreparedStatement deleteStatement = null;
    private PreparedStatement commentForUserForPublication = null;

    public CommentDAOImpl() throws DAOException {
	mysqlConnection = MysqlConnection.getInstance();
	Connection connection = mysqlConnection.getConnection();
	try {
	    selectStatement = connection
		    .prepareStatement("SELECT * FROM Comment");
	    findStatementA = connection
		    .prepareStatement("SELECT * FROM Comment WHERE user_mail = ?");
	    findStatementB = connection
		    .prepareStatement("SELECT * FROM Comment WHERE user_mail = ? AND publication_url = ? AND stream_url = ?");
	    insertStatement = connection
		    .prepareStatement("INSERT INTO Comment VALUES (?, ?, ?, ?, CURDATE())");
	    deleteStatement = connection
		    .prepareStatement("DELETE FROM Comment WHERE user_mail = ? AND publication_url = ? AND stream_url = ?");
	    commentForUserForPublication = connection
		    .prepareStatement("SELECT * FROM Comment com WHERE com.stream_url IN (SELECT sub.stream_url FROM Subscribe sub WHERE sub.user_mail = ? AND sub.stream_url IN (SELECT u.personal_stream_url FROM User u WHERE u.mail IN (SELECT f1.mail_sender FROM Friendship f1 WHERE f1.mail_receiver = ? AND f1.status = TRUE) OR u.mail IN (SELECT f2.mail_receiver FROM Friendship f2 WHERE f2.mail_sender = ? AND f2.status = TRUE))) AND com.publication_url = ?");

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    public List<Comment> selectAll() throws DAOException {
	ResultSet res = null;
	List<Comment> comments = new ArrayList<Comment>();
	try {
	    res = selectStatement.executeQuery();
	    while (res.next()) {
		Comment comment = new Comment();
		comment.setUserMail(res.getString("user_mail"));
		comment.setPublicationUrl(res.getString("publication_url"));
		comment.setStreamUrl(res.getString("stream_url"));
		comment.setContent(res.getString("content"));
		comment.setDate(res.getString("date"));
		comments.add(comment);
	    }
	    return comments;
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    public Comment find(String user_mail) throws DAOException {
	ResultSet res = null;
	try {
	    findStatementA.setString(1, user_mail);
	    res = findStatementA.executeQuery();
	    if (res.next()) {
		Comment comment = new Comment();
		comment.setUserMail(res.getString("user_mail"));
		comment.setPublicationUrl(res.getString("publication_url"));
		comment.setStreamUrl(res.getString("stream_url"));
		comment.setContent(res.getString("content"));
		comment.setDate(res.getString("date"));
		return comment;
	    }
	    return null;

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public Comment find(String user_mail, String publication_url,
	    String stream_url) throws DAOException {
	ResultSet res = null;
	try {
	    findStatementB.setString(1, user_mail);
	    findStatementB.setString(2, publication_url);
	    findStatementB.setString(3, stream_url);
	    res = findStatementB.executeQuery();
	    if (res.next()) {
		Comment comment = new Comment();
		comment.setUserMail(res.getString("user_mail"));
		comment.setPublicationUrl(res.getString("publication_url"));
		comment.setStreamUrl(res.getString("stream_url"));
		comment.setContent(res.getString("content"));
		comment.setDate(res.getString("date"));
		return comment;
	    }
	    return null;

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    public boolean insert(Comment comment) throws DAOException {
	try {

	    int i = 1;

	    insertStatement.setString(i++, comment.getUserMail());
	    insertStatement.setString(i++, comment.getPublicationUrl());
	    insertStatement.setString(i++, comment.getStreamUrl());
	    insertStatement.setString(i++, comment.getContent());

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
    public boolean update(Comment comment) throws DAOException {

	try {
	    int i = 1;

	    insertStatement.setString(i++, comment.getUserMail());
	    insertStatement.setString(i++, comment.getPublicationUrl());
	    insertStatement.setString(i++, comment.getStreamUrl());
	    insertStatement.setString(i++, comment.getContent());
	    insertStatement.setString(i++, comment.getDate());

	    int affectedRows = updateStatement.executeUpdate();
	    if (affectedRows == 0) {
		return false;
	    }
	    return true;
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public List<Comment> commentsAssociateWithUserAndPublication(
	    String userMail, String publicationUrl) {
	ResultSet res = null;
	List<Comment> comments = new ArrayList<Comment>();
	try {
	    commentForUserForPublication.setString(1, userMail);
	    commentForUserForPublication.setString(2, userMail);
	    commentForUserForPublication.setString(3, userMail);
	    commentForUserForPublication.setString(4, publicationUrl);
	    res = commentForUserForPublication.executeQuery();
	    while (res.next()) {
		Comment comment = new Comment();
		comment.setUserMail(res.getString("user_mail"));
		comment.setPublicationUrl(res.getString("publication_url"));
		comment.setStreamUrl(res.getString("stream_url"));
		comment.setContent(res.getString("content"));
		comment.setDate(res.getString("date"));
		comments.add(comment);
	    }

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
	return comments;
    }

    @Override
    public boolean delete(Comment comment) throws DAOException {
	try {
	    deleteStatement.setString(1, comment.getUserMail());
	    deleteStatement.setString(1, comment.getPublicationUrl());
	    deleteStatement.setString(1, comment.getStreamUrl());
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