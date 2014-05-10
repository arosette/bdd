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
		stream.setDescription(res.getString("description"));
		stream.setWebLink(res.getString("webLink"));
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
		stream.setDescription(res.getString("description"));
		stream.setWebLink(res.getString("webLink"));
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
    public void insert(Stream stream) throws DAOException {
	try {

	    int i = 1;

	    insertStatement.setString(i++, stream.getUrl());
	    insertStatement.setString(i++, stream.getName());
	    insertStatement.setString(i++, stream.getDescription());
	    insertStatement.setString(i++, stream.getWebLink());

	    insertStatement.executeUpdate();

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
	    updateStatement.setString(i++, stream.getDescription());
	    updateStatement.setString(i++, stream.getWebLink());

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

	} catch (Throwable t) {
	}
    }
}