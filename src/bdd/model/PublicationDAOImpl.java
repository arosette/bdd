package bdd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublicationDAOImpl implements GenericDAO<Publication, String> {

    private MysqlConnection mysqlConnection = null;

    private PreparedStatement selectStatement = null;
    private PreparedStatement findStatement = null;
    private PreparedStatement insertStatement = null;
    private PreparedStatement updateStatement = null;
    private PreparedStatement deleteStatement = null;
    private PreparedStatement publicationsOfUser = null;

    public PublicationDAOImpl() throws DAOException {
	mysqlConnection = MysqlConnection.getInstance();
	Connection connection = mysqlConnection.getConnection();
	try {
	    selectStatement = connection
		    .prepareStatement("SELECT * FROM Publication");
	    findStatement = connection
		    .prepareStatement("SELECT * FROM Publication WHERE url = ?");
	    insertStatement = connection
		    .prepareStatement("INSERT INTO Publication VALUES (?, ?, ?, ?, ?)");
	    deleteStatement = connection
		    .prepareStatement("DELETE FROM Publication WHERE url = ?");
	    publicationsOfUser = connection
		    .prepareStatement("SELECT * FROM Publication pub WHERE pub.url IN ("
			    + "SELECT DISTINCT FROM Propose prop WHERE prop.stream_url IN ("
			    + "SELECT s.url FROM Stream s WHERE s.url IN ("
			    + "SELECT (sub.stream_url) FROM Subscribe sub WHERE sub.user_mail = <user>)))");

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    @Override
    public List<Publication> selectAll() throws DAOException {
	ResultSet res = null;
	List<Publication> publications = new ArrayList<Publication>();
	try {
	    res = selectStatement.executeQuery();
	    while (res.next()) {
		Publication publication = new Publication();
		publication.setUrl(res.getString("link"));
		publication.setTitle(res.getString("title"));
		publication.setDescription(res.getString("date"));
		publication.setDate(res.getString("description"));
		publications.add(publication);
	    }
	    return publications;
	} catch (SQLException e) {
	    throw new DAOException(e);
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
		return publication;
	    }
	    return null;

	} catch (SQLException e) {
	    throw new DAOException(e);
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
    public boolean update(Publication publication) throws DAOException {

	try {
	    int i = 1;

	    updateStatement.setString(i++, publication.getUrl());
	    updateStatement.setString(i++, publication.getTitle());
	    updateStatement.setString(i++, publication.getDate());
	    updateStatement.setString(i++, publication.getDescription());
	    updateStatement.setBoolean(i++, publication.isRead());

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
    
    public List<Publication> publicationsOfUser(User user) {
	ResultSet res = null;
	List<Publication> publications = new ArrayList<Publication>();
	try {
	    res = selectStatement.executeQuery();
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
	    if (updateStatement != null) {
		updateStatement.close();
	    }
	    if (deleteStatement != null) {
		deleteStatement.close();
	    }
	    if (publicationsOfUser != null) {
		publicationsOfUser.close();
	    }

	} catch (Throwable t) {
	}
    }
}