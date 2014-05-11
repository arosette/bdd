package bdd.controller;

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
import bdd.model.Publication;
import bdd.model.PublicationDAOImpl;
import bdd.model.Stream;
import bdd.model.StreamDAOImpl;
import bdd.model.User;
import bdd.parser.RssParser;

public class MainFrameController {
    private MainFrameView mainFrameView;
    private List<Publication> publications;
    private List<Stream> streams;
    private List<Friendship> friendships;
    private User currentUser;

    public MainFrameController(User loggedUser) {
	mainFrameView = new MainFrameView(loggedUser);
	publications = new ArrayList<Publication>();
	streams = new ArrayList<Stream>();
	friendships = new ArrayList<Friendship>();
	this.currentUser = loggedUser;
	publications = retrievePublicationsFromBdd();
	streams = retrieveStreamsFromBdd();
	friendships = createFriendship();
	registerListeners();
	mainFrameView.loadStreams(streams);
	mainFrameView.loadPublications(publications);
	mainFrameView.loadFriendships(friendships);
    }

    private void registerListeners() {
	mainFrameView.addListenerToStreamJList(new StreamPopupMenuListener());
	mainFrameView
		.addListenerToPublicationJList(new PublicationPopupMenuListener());
	mainFrameView.addListenerToAddStreamItem(new AddStreamItemListener());
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

    private List<Friendship> createFriendship() {
	Friendship f1 = new Friendship();
	f1.setSenderMail(currentUser.getMail());
	f1.setReceiverMail("lol@lol.com");
	f1.setStatus(false);
	f1.setDate("2014-05-08");

	Friendship f2 = new Friendship();
	f2.setReceiverMail(currentUser.getMail());
	f2.setSenderMail("hahah@coucou.com");
	f2.setStatus(false);
	f2.setDate("2014-05-08");

	Friendship f3 = new Friendship();
	f3.setReceiverMail(currentUser.getMail());
	f3.setSenderMail("tit@tutu.com");
	f3.setStatus(true);
	f3.setDate("2014-05-08");

	ArrayList<Friendship> friendships = new ArrayList<Friendship>();
	friendships.add(f1);
	friendships.add(f2);
	friendships.add(f3);

	return friendships;
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

    private class AddStreamItemListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    new AddStreamController(currentUser);
	}

    }

    private class RefreshButtonListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    streams = retrieveStreamsFromBdd();
	    publications = retrievePublicationsFromBdd();

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
		    publicationDAO.insert(publication);
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
