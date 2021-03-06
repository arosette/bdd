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
    private PreparedStatement isPublicationRead = null;
    private PreparedStatement makePublicationRead = null;
    private PreparedStatement publicationsOfStream = null;
    private PreparedStatement streamOfPublication = null;

    public PublicationDAOImpl() throws DAOException {
	mysqlConnection = MysqlConnection.getInstance();
	Connection connection = mysqlConnection.getConnection();
	try {
	    selectStatement = connection
		    .prepareStatement("SELECT * FROM Publication");
	    findStatement = connection
		    .prepareStatement("SELECT * FROM Publication WHERE url = ?");
	    insertStatement = connection
		    .prepareStatement("INSERT INTO Publication VALUES (?, ?, ?, ?)");
	    deleteStatement = connection
		    .prepareStatement("DELETE FROM Publication WHERE url = ?");
	    publicationsOfUser = connection
		    .prepareStatement("SELECT * FROM Publication pub WHERE pub.url IN ("
			    + "SELECT DISTINCT prop.publication_url FROM Propose prop WHERE prop.stream_url IN ("
			    + "SELECT s.url FROM Stream s WHERE s.url IN ("
			    + "SELECT (sub.stream_url) FROM Subscribe sub WHERE sub.user_mail = ?)))");
	    isPublicationRead = connection
		    .prepareStatement("SELECT COUNT(*) FROM `Read` WHERE user_mail = ? AND publication_url = ?");
	    makePublicationRead = connection
		    .prepareStatement("INSERT INTO `Read` VALUES (?, ?, CURDATE())");
	    publicationsOfStream = connection
		    .prepareStatement("SELECT * FROM Publication pub WHERE pub.url IN (SELECT prop.publication_url FROM Propose prop WHERE prop.stream_url = ?)");
	    streamOfPublication = connection
		    .prepareStatement("SELECT p.stream_url FROM Propose p WHERE p.publication_url = ?");

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
		publication.setUrl(res.getString("url"));
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
    public boolean insert(Publication publication) throws DAOException {
	try {

	    int i = 1;

	    insertStatement.setString(i++, publication.getUrl());
	    insertStatement.setString(i++, publication.getTitle());
	    insertStatement.setString(i++, publication.getDate());
	    insertStatement.setString(i++, publication.getDescription());

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
    public boolean update(Publication publication) throws DAOException {

	try {
	    int i = 1;

	    updateStatement.setString(i++, publication.getUrl());
	    updateStatement.setString(i++, publication.getTitle());
	    updateStatement.setString(i++, publication.getDate());
	    updateStatement.setString(i++, publication.getDescription());

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
	List<Publication> publications = new ArrayList<Publication>();
	try {
	    ResultSet res = null;
	    publicationsOfUser.setString(1, user.getMail());
	    res = publicationsOfUser.executeQuery();
	    while (res.next()) {
		Publication publication = new Publication();
		publication.setUrl(res.getString("url"));
		publication.setTitle(res.getString("title"));
		publication.setDate(res.getString("date"));
		publication.setDescription(res.getString("description"));

		// Publication lue ?
		isPublicationRead.setString(1, user.getMail());
		isPublicationRead.setString(2, publication.getUrl());
		ResultSet res2 = isPublicationRead.executeQuery();

		if (res2.next()) {
		    if (res2.getInt(1) == 0) {
			publication.setRead(false);
		    } else {
			publication.setRead(true);
		    }
		} else {
		    System.out.println("Probleme pour récupérer le count");
		}

		// Recuperation des commentaires
		CommentDAOImpl commentDAO = new CommentDAOImpl();
		List<Comment> comments = commentDAO
			.commentsAssociateWithUserAndPublication(
				user.getMail(), publication.getUrl());
		publication.setComments(comments);

		publications.add(publication);

	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
	return publications;
    }

    public boolean makePublicationRead(User user, Publication publication) {
	try {

	    int i = 1;

	    makePublicationRead.setString(i++, user.getMail());
	    makePublicationRead.setString(i++, publication.getUrl());

	    int affectedRows = makePublicationRead.executeUpdate();
	    if (affectedRows == 0) {
		return false;
	    }
	    return true;

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
    }

    public List<Publication> getPublicationOfStream(Stream stream) {
	List<Publication> publications = new ArrayList<Publication>();
	try {
	    ResultSet res = null;
	    publicationsOfStream.setString(1, stream.getUrl());
	    res = publicationsOfStream.executeQuery();
	    while (res.next()) {
		Publication publication = new Publication();
		publication.setUrl(res.getString("url"));
		publication.setTitle(res.getString("title"));
		publication.setDate(res.getString("date"));
		publication.setDescription(res.getString("description"));

		// On ne recupere pas read de la bdd.
		publication.setRead(false);

		publications.add(publication);

	    }
	} catch (SQLException e) {
	    throw new DAOException(e);
	}
	return publications;
    }

    public Stream getStreamOfPublication(Publication publication) {
	Stream stream = new Stream();
	try {
	    ResultSet res = null;
	    streamOfPublication.setString(1, publication.getUrl());
	    res = streamOfPublication.executeQuery();
	    while (res.next()) {
		stream.setUrl(res.getString("url"));
		stream.setName(res.getString("name"));
		stream.setWebLink(res.getString("webLink"));
		stream.setDescription(res.getString("description"));
	    }

	} catch (SQLException e) {
	    throw new DAOException(e);
	}
	return stream;
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
	    if (isPublicationRead != null) {
		isPublicationRead.close();
	    }

	} catch (Throwable t) {
	}
    }
}