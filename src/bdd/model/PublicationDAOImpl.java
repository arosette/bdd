package bdd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublicationDAOImpl extends ConnectionMYSQL implements
	GenericDAO<Publication, String> {

    private PreparedStatement selectStatment = null;
    private PreparedStatement findStatement = null;
    private PreparedStatement insertStatement = null;
    private PreparedStatement deleteStatement = null;

    public PublicationDAOImpl(ConnectionProvider connectionProvider)
	    throws DAOException {
	super(connectionProvider);
	Connection connection = null;
	try {
	    connection = getConnection();
	    selectStatment = connection.prepareStatement("SELECT * FROM Publication");
	    findStatement = connection
		    .prepareStatement("SELECT * FROM Publication WHERE url = ? ;");
	    insertStatement = connection
		    .prepareStatement("INSERT INTO Publication VALUES (?, ?, ?, ?, ?)");
	    deleteStatement = connection
		    .prepareStatement("DELETE FROM Publication WHERE url = ?");

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
    public List<Publication> selectAll() throws DAOException {
	ResultSet res = null;
	List<Publication> publications = new ArrayList<Publication>();
	try {
	    res = selectStatment.executeQuery();
	    while (res.next()) {
		Publication publication = new Publication();
		publication.setUrl(res.getString("link"));
		publication.setTitle(res.getString("title"));
		publication.setDescription(res.getString("date"));
		publication.setDate(res.getString("description"));
		publication.setRead(res.getBoolean("read"));
		publications.add(publication);
	    }
	    return publications;
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
    public Publication find(String link) throws DAOException {
	ResultSet res = null;
	try {
	    findStatement.setString(1, link);
	    res = findStatement.executeQuery();
	    if (res.next()) {
		Publication publication = new Publication();
		publication.setUrl(res.getString("url"));
		publication.setTitle(res.getString("title"));
		publication.setDescription(res.getString("date"));
		publication.setDate(res.getString("description"));
		publication.setRead(res.getBoolean("read"));
		return publication;
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
    public void insert(Publication publication) throws DAOException {
	try {

	    int i = 1;

	    insertStatement.setString(i++, publication.getUrl());
	    insertStatement.setString(i++, publication.getTitle());
	    insertStatement.setString(i++, publication.getDate());
	    insertStatement.setString(i++, publication.getDescription());
	    insertStatement.setBoolean(i++, publication.isRead());
	   
	    insertStatement.executeUpdate();

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    public boolean delete(Publication publication) throws DAOException {
	try {
	    deleteStatement.setString(1, publication.getUrl());
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