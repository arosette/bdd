package bdd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StreamDAOImpl extends ConnectionMYSQL implements
	GenericDAO<Stream, String> {

    private PreparedStatement selectStatment = null;
    private PreparedStatement findStatement = null;
    private PreparedStatement insertStatement = null;
    private PreparedStatement deleteStatement = null;

    public StreamDAOImpl(ConnectionProvider connectionProvider)
	    throws DAOException {
	super(connectionProvider);
	Connection connection = null;
	try {
	    connection = getConnection();
	    selectStatment = connection.prepareStatement("SELECT * FROM Stream");
	    findStatement = connection
		    .prepareStatement("SELECT * FROM Stream WHERE url = ? ;");
	    insertStatement = connection
		    .prepareStatement("INSERT INTO Stream VALUES (?, ?, ?, ?)");
	    deleteStatement = connection
		    .prepareStatement("DELETE FROM Stream WHERE url = ?");

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
    public List<Stream> selectAll() throws DAOException {
	ResultSet res = null;
	List<Stream> streams = new ArrayList<Stream>();
	try {
	    res = selectStatment.executeQuery();
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
    public Stream find(String url) throws DAOException {
	ResultSet res = null;
	try {
	    selectStatment.setString(1, url);
	    res = selectStatment.executeQuery();
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