package bdd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StreamDAOImpl implements GenericDAO<Stream, String> {

    private MysqlConnection mysqlConnection = null;

    private PreparedStatement selectStatement = null;
    private PreparedStatement findStatement = null;
    private PreparedStatement insertStatement = null;
    private PreparedStatement updateStatement = null;
    private PreparedStatement deleteStatement = null;
    private PreparedStatement streamsOfUser = null;
    private PreparedStatement associatePublication = null;
    private PreparedStatement associateUser = null;
    private PreparedStatement selectStreamsWithoutPersonal = null;
    private PreparedStatement hasUserSubscribeToStream = null;
    private PreparedStatement selectPublicationAssociateToStream = null;
    private PreparedStatement selectStreamsForComment = null;

    public StreamDAOImpl() throws DAOException {
	mysqlConnection = MysqlConnection.getInstance();
	Connection connection = mysqlConnection.getConnection();
	try {
	    selectStatement = connection
		    .prepareStatement("SELECT * FROM Stream");
	    findStatement = connection
		    .prepareStatement("SELECT * FROM Stream WHERE url = ?");
	    insertStatement = connection
		    .prepareStatement("INSERT INTO Stream VALUES (?, ?, ?, ?)");
	    deleteStatement = connection
		    .prepareStatement("DELETE FROM Stream WHERE url = ?");

	    streamsOfUser = connection
		    .prepareStatement("SELECT * FROM Stream s WHERE s.url IN (SELECT (sub.stream_url) FROM Subscribe sub WHERE sub.user_mail = ?)");

	    associatePublication = connection
		    .prepareStatement("INSERT INTO Propose VALUES (?, ?)");

	    associateUser = connection
		    .prepareStatement("INSERT INTO Subscribe VALUES (?, ?, CURDATE())");

	    selectStreamsWithoutPersonal = connection
		    .prepareStatement("SELECT * FROM Stream s WHERE s.url NOT IN (SELECT u.personal_stream_url FROM User u)");

	    hasUserSubscribeToStream = connection
		    .prepareStatement("SELECT COUNT(*) FROM Subscribe WHERE user_mail = ? AND stream_url = ?");

	    selectPublicationAssociateToStream = connection
		    .prepareStatement("SELECT COUNT(*) FROM Propose WHERE stream_url = ? AND publication_url = ?");

	    selectStreamsForComment = connection
		    .prepareStatement("SELECT * FROM Stream s WHERE s.url IN (SELECT u.personal_stream_url FROM User u, Propose prop WHERE (u.mail IN (SELECT f1.mail_sender FROM Friendship f1 WHERE f1.mail_receiver = ? AND f1.status = TRUE) OR u.mail IN (SELECT f2.mail_receiver FROM Friendship f2 WHERE f2.mail_sender = ? AND f2.status = TRUE)) AND u.personal_stream_url = prop.stream_url AND prop.publication_url = ?) AND s.url NOT IN (SELECT com.stream_url FROM Comment com WHERE com.publication_url = ? AND com.user_mail = ?)");

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    public List<Stream> selectAll() throws DAOException {
	ResultSet res = null;
	try {
	    res = selectStatement.executeQuery();
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
	return convertResultSetToStream(res);
    }

    @Override
    public Stream find(String url) throws DAOException {
	ResultSet res = null;
	try {
	    findStatement.setString(1, url);
	    res = findStatement.executeQuery();
	    if (res.next()) {
		Stream stream = new Stream();
		stream.setUrl(res.getString("url"));
		stream.setName(res.getString("name"));
		stream.setWebLink(res.getString("webLink"));
		stream.setDescription(res.getString("description"));
		return stream;
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

    private List<Stream> convertResultSetToStream(ResultSet res) {
	List<Stream> streams = new ArrayList<Stream>();
	try {
	    while (res.next()) {
		Stream stream = new Stream();
		stream.setUrl(res.getString("url"));
		stream.setName(res.getString("name"));
		stream.setWebLink(res.getString("webLink"));
		stream.setDescription(res.getString("description"));
		streams.add(stream);
	    }

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    try {
		res.close();
	    } catch (SQLException e) {
		throw new DAOException(e);
	    }
	}
	return streams;
    }

    @Override
    public boolean insert(Stream stream) throws DAOException {
	try {

	    int i = 1;

	    insertStatement.setString(i++, stream.getUrl());
	    insertStatement.setString(i++, stream.getName());
	    insertStatement.setString(i++, stream.getWebLink());
	    insertStatement.setString(i++, stream.getDescription());

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
    public boolean update(Stream stream) throws DAOException {

	try {
	    int i = 1;

	    updateStatement.setString(i++, stream.getUrl());
	    updateStatement.setString(i++, stream.getName());
	    updateStatement.setString(i++, stream.getWebLink());
	    updateStatement.setString(i++, stream.getDescription());

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
    public boolean delete(Stream stream) throws DAOException {
	try {
	    deleteStatement.setString(1, stream.getUrl());
	    int affectedRows = deleteStatement.executeUpdate();
	    if (affectedRows == 0) {
		return false;
	    }
	    return true;
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public List<Stream> streamsOfUser(User user) {
	ResultSet res = null;
	try {
	    streamsOfUser.setString(1, user.getMail());
	    res = streamsOfUser.executeQuery();
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
	return convertResultSetToStream(res);
    }

    public boolean associatePublication(String streamUrl, String publicationUrl) {
	try {

	    int i = 1;

	    associatePublication.setString(i++, streamUrl);
	    associatePublication.setString(i++, publicationUrl);

	    int affectedRows = associatePublication.executeUpdate();
	    if (affectedRows == 0) {
		return false;
	    }
	    return true;

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public boolean associateUser(String streamUrl, String userMail) {
	try {

	    int i = 1;

	    associateUser.setString(i++, userMail);
	    associateUser.setString(i++, streamUrl);

	    int affectedRows = associateUser.executeUpdate();
	    if (affectedRows == 0) {
		return false;
	    }
	    return true;

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public List<Stream> getStreamsWithoutPersonal() {
	ResultSet res = null;
	try {
	    res = selectStreamsWithoutPersonal.executeQuery();
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
	return convertResultSetToStream(res);
    }

    public boolean hasUserSubscribeToStream(String userMail, String streamUrl) {
	boolean hasSubscribe = false;
	try {
	    hasUserSubscribeToStream.setString(1, userMail);
	    hasUserSubscribeToStream.setString(2, streamUrl);
	    ResultSet res = hasUserSubscribeToStream.executeQuery();

	    if (res.next()) {
		if (res.getInt(1) == 0) {
		    hasSubscribe = false;
		} else {
		    hasSubscribe = true;
		}
	    } else {
		System.out.println("Probleme pour récupérer le count");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
	return hasSubscribe;
    }

    public boolean isPublicationAssociateWithStream(String streamUrl,
	    String publicationUrl) {
	boolean exists = false;
	try {
	    selectPublicationAssociateToStream.setString(1, streamUrl);
	    selectPublicationAssociateToStream.setString(2, publicationUrl);
	    ResultSet res = selectPublicationAssociateToStream.executeQuery();

	    if (res.next()) {
		if (res.getInt(1) == 0) {
		    exists = false;
		} else {
		    exists = true;
		}
	    } else {
		System.out.println("Probleme pour récupérer le count");
	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
	return exists;
    }

    public List<Stream> streamsForComment(String userMail, String publicationUrl) {
	ResultSet res = null;
	try {
	    selectStreamsForComment.setString(1, userMail);
	    selectStreamsForComment.setString(2, userMail);
	    selectStreamsForComment.setString(3, publicationUrl);
	    selectStreamsForComment.setString(4, publicationUrl);
	    selectStreamsForComment.setString(5, userMail);
	    res = selectStreamsForComment.executeQuery();
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
	return convertResultSetToStream(res);
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
	    if (streamsOfUser != null) {
		streamsOfUser.close();
	    }
	    if (associatePublication != null) {
		associatePublication.close();
	    }
	    if (associateUser != null) {
		associateUser.close();
	    }

	} catch (Throwable t) {
	}
    }
}