package bdd.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bdd.model.Friendship;
import bdd.model.Publication;
import bdd.model.Stream;
import bdd.model.User;
import bdd.view.renderer.FriendRenderer;
import bdd.view.renderer.PublicationRenderer;
import bdd.view.renderer.StreamRenderer;

public class MainFrameView extends JFrame {

    private JPanel mainPanel;
    private JList<Stream> streamJList;
    private JList<Publication> publicationJList;
    private JList<Friendship> friendJList;
    private User currentUser;
    private JMenuItem addStreamItem;
    private JMenuItem addFriendItem;
    private JButton refreshButton;
    private JButton loadNewPublicationsButton;
    private FriendRenderer friendRenderer;

    public MainFrameView(User currentUser) {
	super();
	this.currentUser = currentUser;
	this.setTitle("Flux RSS");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setMinimumSize(new Dimension(1200, 700));
	this.setResizable(true);
	this.build();
    }

    public void build() {
	this.mainPanel = new JPanel();
	this.mainPanel.setLayout(new GridBagLayout());

	GridBagConstraints gbc = new GridBagConstraints();

	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridheight = 1;
	gbc.gridwidth = 1;
	gbc.anchor = GridBagConstraints.WEST;
	gbc.fill = GridBagConstraints.NONE;
	gbc.insets = new Insets(0, 10, 0, 10);

	// Construction de la liste contenant les flux à gauche de la fenetre

	// Le titre
	mainPanel.add(new JLabel("Flux"), gbc);

	// La liste
	gbc.gridy = 1;
	streamJList = new JList<Stream>();
	streamJList.setCellRenderer(new StreamRenderer());
	mainPanel.add(new JScrollPane(streamJList), gbc);

	// Construction de la liste contenant les publications au centre de la
	// fenetre
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.gridx = 1;
	gbc.gridy = 0;

	// Le titre
	mainPanel.add(new JLabel("Publications"), gbc);

	// La liste
	gbc.gridy = 1;
	publicationJList = new JList<Publication>();
	publicationJList.setCellRenderer(new PublicationRenderer());
	mainPanel.add(new JScrollPane(publicationJList), gbc);

	gbc.anchor = GridBagConstraints.EAST;
	gbc.gridx = 2;
	gbc.gridy = 0;
	mainPanel.add(new JLabel("Amis"), gbc);

	gbc.gridy = 1;
	friendJList = new JList<Friendship>();
	friendRenderer = new FriendRenderer(currentUser);
	friendJList.setCellRenderer(friendRenderer);
	mainPanel.add(friendJList, gbc);

	// Bouton de refresh
	gbc.gridx = 0;
	gbc.gridy = 2;
	refreshButton = new JButton("Refresh");
	mainPanel.add(refreshButton, gbc);

	// Bouton qui permet de récupérer les nouvelles publications à partir
	// des flux RSS
	gbc.gridx = 1;
	loadNewPublicationsButton = new JButton(
		"Telecharger les nouvelles publications");
	mainPanel.add(loadNewPublicationsButton, gbc);

	// Menu
	JMenuBar menuBar = new JMenuBar();
	JMenu streamMenu = new JMenu("Flux");
	addStreamItem = new JMenuItem("Ajouter un flux...");
	JMenu friendMenu = new JMenu("Amis");
	addFriendItem = new JMenuItem("Ajouter un ami...");

	streamMenu.add(addStreamItem);
	menuBar.add(streamMenu);

	friendMenu.add(addFriendItem);
	menuBar.add(friendMenu);

	this.setJMenuBar(menuBar);
	this.setContentPane(this.mainPanel);
	this.setVisible(true);
    }

    public void loadStreams(List<Stream> streams) {

	DefaultListModel<Stream> streamListModel = new DefaultListModel<Stream>();

	for (Stream stream : streams) {
	    streamListModel.addElement(stream);
	}

	streamJList.setModel(streamListModel);

    }

    public void loadPublications(List<Publication> publications) {

	DefaultListModel<Publication> publicationListModel = new DefaultListModel<Publication>();
	for (Publication publication : publications) {
	    publicationListModel.addElement(publication);
	}
	publicationJList.setModel(publicationListModel);

    }

    public void loadFriendships(List<Friendship> friendships) {

	DefaultListModel<Friendship> friendshipListModel = new DefaultListModel<Friendship>();
	for (Friendship publication : friendships) {
	    friendshipListModel.addElement(publication);
	}
	friendJList.setModel(friendshipListModel);

    }

    public Stream getSelectedStream() {
	return streamJList.getSelectedValue();
    }

    public void addListenerToStreamJList(MouseListener mouseListener) {
	streamJList.addMouseListener(mouseListener);
    }

    public Publication getSelectedPublication() {
	return publicationJList.getSelectedValue();
    }

    public void addListenerToPublicationJList(MouseListener mouseListener) {
	publicationJList.addMouseListener(mouseListener);
    }
    
    public Friendship getSelectedFriendship() {
	return friendJList.getSelectedValue();
    }
    
    public void addListenerToFriendJList(MouseListener mouseListener) {
	friendJList.addMouseListener(mouseListener);
    }

    public void addListenerToAddStreamItem(ActionListener actionListener) {
	addStreamItem.addActionListener(actionListener);
    }
    
    

    public void addListenerToAddFriendItem(ActionListener actionListener) {
	addFriendItem.addActionListener(actionListener);
    }

    public void addListenerToRefreshButton(ActionListener actionListener) {
	refreshButton.addActionListener(actionListener);
    }

    public void addListenerToLoadNewPublicationsButton(
	    ActionListener actionListener) {
	loadNewPublicationsButton.addActionListener(actionListener);
    }

}
