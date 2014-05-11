package bdd.controller;

import bdd.view.DialogBox;
import bdd.view.MainFrameView;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import bdd.model.Friendship;
import bdd.model.FriendshipDAOImpl;
import bdd.model.Publication;
import bdd.model.PublicationDAOImpl;
import bdd.model.Stream;
import bdd.model.StreamDAOImpl;
import bdd.model.User;
import bdd.model.UserDAOImpl;
import bdd.parser.RssParser;

public class MainFrameController {
    private MainFrameView mainFrameView;
    private List<Publication> publications;
    private List<Stream> streams;
    private List<Friendship> friendships;
    private User currentUser;

    public MainFrameController(User loggedUser) {
	// Creation de la vue
	mainFrameView = new MainFrameView(loggedUser);

	// Creation des modeles
	publications = new ArrayList<Publication>();
	streams = new ArrayList<Stream>();
	friendships = new ArrayList<Friendship>();
	this.currentUser = loggedUser;

	// Chargement des donnees depuis la bdd
	publications = retrievePublicationsFromBdd();
	streams = retrieveStreamsFromBdd();
	friendships = retrieveFriendshipsFromBdd();

	// Enregistrement des listeners
	registerListeners();

	// Affichage des modeles dans la vue
	mainFrameView.loadStreams(streams);
	mainFrameView.loadPublications(publications);
	mainFrameView.loadFriendships(friendships);
    }

    private void registerListeners() {
	mainFrameView.addListenerToStreamJList(new StreamPopupMenuListener());
	mainFrameView
		.addListenerToPublicationJList(new PublicationPopupMenuListener());
	mainFrameView
		.addListenerToFriendJList(new FriendshipPopupMenuListener());
	mainFrameView.addListenerToAddStreamItem(new AddStreamItemListener());
	mainFrameView.addListenerToAddFriendItem(new AddFriendItemListener());
	mainFrameView.addListenerToRefreshButton(new RefreshButtonListener());
	mainFrameView
		.addListenerToLoadNewPublicationsButton(new LoadNewPublicationsListener());
    }

    private List<Stream> retrieveStreamsFromBdd() {
	StreamDAOImpl streamDAO = new StreamDAOImpl();
	return streamDAO.streamsOfUser(currentUser);
    }

    private List<Publication> retrievePublicationsFromBdd() {
	PublicationDAOImpl publicationDAO = new PublicationDAOImpl();
	return publicationDAO.publicationsOfUser(currentUser);
    }

    private List<Friendship> retrieveFriendshipsFromBdd() {
	FriendshipDAOImpl friendshipDAO = new FriendshipDAOImpl();
	return friendshipDAO.getFriendshipsOfUser(currentUser);
    }

    private class StreamPopupMenuListener extends MouseAdapter {
	private JPopupMenu menu;

	public StreamPopupMenuListener() {
	    menu = new JPopupMenu();
	    JMenuItem openLink = new JMenuItem("Ouvrir le lien");

	    // Creation du listener sur l'item qui permet d'ouvrir le lien sur
	    // le navigateur
	    openLink.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    try {
			Desktop.getDesktop().browse(
				new URI(mainFrameView.getSelectedStream()
					.getUrl()));
		    } catch (IOException e1) {
			e1.printStackTrace();
		    } catch (URISyntaxException e1) {
			e1.printStackTrace();
		    }
		}
	    });
	    menu.add(openLink);

	}

	// Ouverture du menu avec clic droit
	@Override
	public void mousePressed(MouseEvent e) {
	    if (SwingUtilities.isRightMouseButton(e)) {
		JList jlist = (JList) e.getSource();
		int row = jlist.locationToIndex(e.getPoint());
		jlist.setSelectedIndex(row);
		menu.show(jlist, e.getX(), e.getY());
	    }
	}
    }

    private class PublicationPopupMenuListener extends MouseAdapter {
	private JPopupMenu menu;

	public PublicationPopupMenuListener() {
	    menu = new JPopupMenu();

	    // Ouverture de la publication
	    JMenuItem openLink = new JMenuItem("Ouvrir...");

	    // Creation du listener sur l'item qui permet d'ouvrir la
	    // publication
	    openLink.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

		    Publication selectedPublication = mainFrameView
			    .getSelectedPublication();
		    if (!selectedPublication.isRead()) {
			PublicationDAOImpl publicationDAO = new PublicationDAOImpl();
			publicationDAO.makePublicationRead(currentUser,
				selectedPublication);
			selectedPublication.setRead(true);
		    }

		    new PublicationController(selectedPublication,
			    mainFrameView);
		}
	    });
	    menu.add(openLink);

	    // Partage de la publication

	}

	// Ouverture du menu avec clic droit
	@Override
	public void mousePressed(MouseEvent e) {
	    if (SwingUtilities.isRightMouseButton(e)) {
		JList jlist = (JList) e.getSource();
		int row = jlist.locationToIndex(e.getPoint());
		jlist.setSelectedIndex(row);
		menu.show(jlist, e.getX(), e.getY());
	    }
	}
    }

    private class FriendshipPopupMenuListener extends MouseAdapter {
	private JPopupMenu menu;
	private Friendship selectedFriendship;

	public FriendshipPopupMenuListener() {
	    menu = new JPopupMenu();

	    JMenuItem acceptFriend = new JMenuItem("Accepter");

	    // Creation du listener sur l'item qui permet d'ouvrir la
	    // publication
	    acceptFriend.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {

		    selectedFriendship = mainFrameView.getSelectedFriendship();

		    if (selectedFriendship.getReceiverMail().equals(
			    currentUser.getMail())
			    && !selectedFriendship.getStatus()) {
			// On accepte la demande
			FriendshipDAOImpl friendshipDAO = new FriendshipDAOImpl();
			selectedFriendship.setStatus(true);
			if (friendshipDAO.update(selectedFriendship)) {
			    // On abonne les 2 utilisateurs à leur flux perso
			    // respectif
			    UserDAOImpl userDAO = new UserDAOImpl();
			    User sender = userDAO.find(selectedFriendship
				    .getSenderMail());
			    User receiver = userDAO.find(selectedFriendship
				    .getReceiverMail());

			    StreamDAOImpl streamDAO = new StreamDAOImpl();

			    // Ajout du flux de l'emetteur au recepteur
			    if (!streamDAO.hasUserSubscribeToStream(
				    receiver.getMail(),
				    sender.getPersonalStream())) {

				streamDAO.associateUser(
					sender.getPersonalStream(),
					receiver.getMail());
			    }

			    // Ajout du flux du recepteur a l'emetteur
			    if (!streamDAO.hasUserSubscribeToStream(
				    sender.getMail(),
				    receiver.getPersonalStream())) {

				streamDAO.associateUser(
					receiver.getPersonalStream(),
					sender.getMail());
			    }
			}

		    } else {
			new DialogBox(
				"Erreur",
				"Vous ne pouvez pas ajouter cet utilisateur comme ami car il est deja votre ami ou c'est à lui d'accepter");
		    }
		}
	    });
	    menu.add(acceptFriend);

	}

	// Ouverture du menu avec clic droit
	@Override
	public void mousePressed(MouseEvent e) {
	    if (SwingUtilities.isRightMouseButton(e)) {
		JList jlist = (JList) e.getSource();
		int row = jlist.locationToIndex(e.getPoint());
		jlist.setSelectedIndex(row);
		menu.show(jlist, e.getX(), e.getY());
	    }
	}
    }

    private class AddStreamItemListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    new AddStreamController(currentUser);
	}

    }

    private class AddFriendItemListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    new AddFriendController(currentUser);
	}

    }

    private class RefreshButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    streams = retrieveStreamsFromBdd();
	    publications = retrievePublicationsFromBdd();
	    friendships = retrieveFriendshipsFromBdd();

	    mainFrameView.loadStreams(streams);
	    mainFrameView.loadPublications(publications);
	    mainFrameView.loadFriendships(friendships);
	}

    }

    private class LoadNewPublicationsListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    StreamDAOImpl streamDAO = new StreamDAOImpl();

	    // Pour chaque flux qui n'est pas un flux perso, on telecharge les
	    // nouvelles publications
	    for (Stream stream : streamDAO.getStreamsWithoutPersonal()) {
		RssParser parser = new RssParser(stream.getUrl());
		PublicationDAOImpl publicationDAO = new PublicationDAOImpl();
		// Cette liste n'est pas triée
		List<Publication> publicationsInBdd = publicationDAO
			.getPublicationOfStream(stream);
		// Cette liste est triée
		List<Publication> publicationsInInternet = parser
			.getPulications();

		List<Publication> newPublications = getOnlyNewPublication(
			publicationsInBdd, publicationsInInternet);

		System.out.println("Les nouvelles publications : "
			+ newPublications.size());

		for (Publication publication : newPublications) {
		    if (publicationDAO.find(publication.getUrl()) == null) {
			publicationDAO.insert(publication);
		    }

		    streamDAO.associatePublication(stream, publication);
		}
	    }
	}

	private List<Publication> getOnlyNewPublication(
		List<Publication> bddPublications,
		List<Publication> internetPublications) {
	    int beginIndex = 0;
	    int lastIndex = internetPublications.size();

	    for (Publication pub : bddPublications) {
		int currentIndex = indexOfPublication(internetPublications,
			pub.getUrl());
		if (currentIndex != -1) {
		    if (currentIndex < lastIndex) {
			lastIndex = currentIndex;
		    }
		}
	    }

	    return internetPublications.subList(beginIndex, lastIndex);
	}

	private int indexOfPublication(List<Publication> publications,
		String publicationUrl) {
	    int index = -1;
	    for (int i = 0; i < publications.size(); ++i) {
		if (publications.get(i).getUrl().equals(publicationUrl)) {
		    index = i;
		    break;
		}
	    }
	    return index;
	}

    }

}
