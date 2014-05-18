package bdd.parser;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bdd.model.Friendship;
import bdd.model.FriendshipDAOImpl;
import bdd.model.Publication;
import bdd.model.PublicationDAOImpl;
import bdd.model.Stream;
import bdd.model.StreamDAOImpl;
import bdd.model.User;
import bdd.model.UserDAOImpl;
import bdd.view.DialogBox;

public class DataParser {
    private NodeList users;
    private NodeList streams;
    private NodeList friends;
    private NodeList subscriptions;

    public DataParser(String filename) {
	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder = null;
	try {
	    dBuilder = dbFactory.newDocumentBuilder();
	} catch (ParserConfigurationException e) {
	    e.printStackTrace();
	}
	try {
	    Document doc = dBuilder.parse(filename);

	    users = doc.getDocumentElement().getElementsByTagName("user");
	    streams = doc.getDocumentElement().getElementsByTagName("stream");
	    friends = doc.getDocumentElement().getElementsByTagName("friend");
	    subscriptions = doc.getDocumentElement().getElementsByTagName(
		    "subscription");

	} catch (SAXException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void populateDb() {
	parseStreams();
	parseUsers();
	parseFriends();
	parseSubscription();
    }

    private void parseStreams() {

	for (int i = 0; i < streams.getLength(); ++i) {
	    Element streamElement = (Element) streams.item(i);
	    String streamUrl = ((Element) streamElement.getElementsByTagName(
		    "url").item(0)).getTextContent();

	    StreamDAOImpl streamDAO = new StreamDAOImpl();
	    Stream stream = streamDAO.find(streamUrl);

	    // Si le flux n'existe pas, on l'ajoute en bdd
	    if (stream == null) {
		RssParser rssParser = new RssParser(streamUrl);

		stream = new Stream();
		stream.setUrl(streamUrl);
		stream.setName(rssParser.getTitle());
		stream.setWebLink(rssParser.getLink());
		stream.setDescription(rssParser.getDescription());
		streamDAO.insert(stream);

		List<Publication> publications = rssParser.getPulications();
		for (int j = 0; j < 10 && j < publications.size(); ++j) {

		    Publication publication = publications.get(j);
		    PublicationDAOImpl publicationDAO = new PublicationDAOImpl();

		    // Si la publication n'existe pas en bdd, on l'ajoute
		    if (publicationDAO.find(publication.getUrl()) == null) {
			publicationDAO.insert(publication);
		    }

		    streamDAO.associatePublication(stream.getUrl(),
			    publication.getUrl());
		}
	    }

	}
    }

    private void parseUsers() {
	for (int i = 0; i < users.getLength(); ++i) {
	    Element userElement = (Element) users.item(i);

	    String userMail = ((Element) userElement.getElementsByTagName(
		    "mail").item(0)).getTextContent();
	    String streamName = ((Element) userElement.getElementsByTagName(
		    "streamname").item(0)).getTextContent();
	    String streamDescription = ((Element) userElement
		    .getElementsByTagName("streamdescription").item(0))
		    .getTextContent();
	    String streamWeblink = ((Element) userElement.getElementsByTagName(
		    "streamweblink").item(0)).getTextContent();
	    String userSurname = ((Element) userElement.getElementsByTagName(
		    "surname").item(0)).getTextContent();
	    String userPassword = ((Element) userElement.getElementsByTagName(
		    "password").item(0)).getTextContent();
	    String userAvatar = ((Element) userElement.getElementsByTagName(
		    "avatar").item(0)).getTextContent();
	    String userCountry = ((Element) userElement.getElementsByTagName(
		    "country").item(0)).getTextContent();
	    String userCity = ((Element) userElement.getElementsByTagName(
		    "city").item(0)).getTextContent();
	    String userBiography = ((Element) userElement.getElementsByTagName(
		    "biography").item(0)).getTextContent();
	    String userDate = ((Element) userElement.getElementsByTagName(
		    "date").item(0)).getTextContent();

	    UserDAOImpl userDAO = new UserDAOImpl();
	    User user = userDAO.find(userMail);
	    if (user == null) {
		User userToAdd = new User();
		Stream streamToAdd = new Stream();
		StreamDAOImpl streamDAO = new StreamDAOImpl();

		streamToAdd.setUrl("http://personal_stream/" + userMail);
		streamToAdd.setName(streamName);
		streamToAdd.setDescription(streamDescription);
		streamToAdd.setWebLink(streamWeblink);

		userToAdd.setMail(userMail);
		userToAdd.setSurname(userSurname);
		userToAdd.setPassword(userPassword);
		userToAdd.setAvatar(userAvatar);
		userToAdd.setCountry(userCountry);
		userToAdd.setCity(userCity);
		userToAdd.setBiography(userBiography);
		userToAdd.setDate(userDate);
		userToAdd.setPersonalStream("http://personal_stream/"
			+ userMail);
		streamDAO.insert(streamToAdd);
		userDAO.insert(userToAdd);

	    }
	}
    }

    private void parseFriends() {
	for (int i = 0; i < friends.getLength(); ++i) {
	    Element friendElement = (Element) friends.item(i);

	    String mailSender = ((Element) friendElement.getElementsByTagName(
		    "mailsender").item(0)).getTextContent();
	    String mailReceiver = ((Element) friendElement
		    .getElementsByTagName("mailreceiver").item(0))
		    .getTextContent();
	    String status = ((Element) friendElement.getElementsByTagName(
		    "status").item(0)).getTextContent();
	    String date = ((Element) friendElement.getElementsByTagName("date")
		    .item(0)).getTextContent();

	    FriendshipDAOImpl friendshipDAO = new FriendshipDAOImpl();
	    Friendship friendship = friendshipDAO
		    .find(mailSender, mailReceiver);
	    if (friendship == null) {
		friendship = new Friendship();
		friendship.setSenderMail(mailSender);
		friendship.setReceiverMail(mailReceiver);
		boolean newStatus;
		if (status.equals("TRUE")) {
		    newStatus = true;

		} else {
		    newStatus = false;
		}
		friendship.setStatus(newStatus);
		friendship.setDate(date);
		friendshipDAO.insert(friendship);

		if (newStatus) {
		    StreamDAOImpl streamDAO = new StreamDAOImpl();
		    UserDAOImpl userDAO = new UserDAOImpl();
		    User sender = userDAO.find(mailSender);
		    User receiver = userDAO.find(mailReceiver);
		    streamDAO.associateUser(sender.getPersonalStream(),
			    mailReceiver);
		    streamDAO.associateUser(receiver.getPersonalStream(),
			    mailSender);
		}
	    }
	}
    }

    private void parseSubscription() {
	for (int i = 0; i < subscriptions.getLength(); ++i) {
	    Element subscriptionElement = (Element) subscriptions.item(i);

	    String streamUrl = ((Element) subscriptionElement
		    .getElementsByTagName("streamurl").item(0))
		    .getTextContent();
	    String userMail = ((Element) subscriptionElement
		    .getElementsByTagName("usermail").item(0)).getTextContent();
	    String date = ((Element) subscriptionElement.getElementsByTagName(
		    "date").item(0)).getTextContent();

	    StreamDAOImpl streamDAO = new StreamDAOImpl();
	    streamDAO.associateUser(streamUrl, userMail);

	}
    }

}
