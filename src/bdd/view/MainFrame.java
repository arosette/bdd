package bdd.view;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import bdd.model.Publication;
import bdd.model.Stream;

public class MainFrame extends JFrame implements Observer {

    private JPanel mainPanel;
    private JList<Stream> streamList;
    private JList<Publication> publicationList;

    public MainFrame() {
	this.setTitle("Flux RSS");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setMinimumSize(new Dimension(800, 600));
	this.setResizable(true);
	this.build();
	this.populateWithTest();
    }

    private void populateWithTest() {
	Stream s1 = new Stream();
	s1.setName("RTBF");
	s1.setDescription("Le flux d'info de la RTBF");
	s1.setUrl("http://rtbf.be");

	Stream s2 = new Stream();
	s2.setName("RTL");
	s2.setDescription("Le flux d'info de RTL");
	s2.setUrl("http://rtl.be");

	Stream s3 = new Stream();
	s3.setName("DH");
	s3.setDescription("Le flux d'info de la DH");
	s3.setUrl("http://dh.be");

	ArrayList<Stream> streams = new ArrayList<Stream>();

	streams.add(s1);
	streams.add(s2);
	streams.add(s3);

	loadStreams(streams);

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
	gbc.insets = new Insets(0, 50, 0, 50);

	// Construction de la liste contenant les flux à gauche de la fenetre

	// Le titre
	mainPanel.add(new JLabel("Flux"), gbc);

	// La liste
	gbc.gridy = 1;
	DefaultListModel<Stream> streamListModel = new DefaultListModel<Stream>();
	streamList = new JList<Stream>(streamListModel);
	streamList.setCellRenderer(new StreamRenderer());
	streamList.addMouseListener(new PopupMenuMouseAdapter());
	mainPanel.add(new JScrollPane(streamList), gbc);

	// Construction de la liste contenant les publications au centre de la
	// fenetre
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.gridx = 1;
	gbc.gridy = 0;

	// Le titre
	mainPanel.add(new JLabel("Publications"), gbc);

	// La liste
	gbc.gridy = 1;
	DefaultListModel<Publication> publicationListModel = new DefaultListModel<Publication>();
	publicationList = new JList<Publication>(publicationListModel);
	publicationList.setCellRenderer(new PublicationRenderer());
	mainPanel.add(new JScrollPane(publicationList), gbc);

	this.setContentPane(this.mainPanel);
	this.setVisible(true);
    }

    public void loadStreams(List<Stream> streams) {

	DefaultListModel<Stream> streamListModel = new DefaultListModel<Stream>();

	for (Stream stream : streams) {
	    streamListModel.addElement(stream);
	}

	streamList.setModel(streamListModel);

    }

    public void loadPublications(List<Publication> publications) {

	DefaultListModel<Publication> streamListModel = new DefaultListModel<Publication>();
	for (Publication publication : publications) {
	    streamListModel.addElement(publication);
	}
	publicationList.setModel(streamListModel);

    }

    private class PopupMenuMouseAdapter extends MouseAdapter {
	private JPopupMenu menu;

	public PopupMenuMouseAdapter() {
	    menu = new JPopupMenu();
	    JMenuItem openLink = new JMenuItem("Ouvirir le lien");
	    openLink.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    System.out.println(streamList.getSelectedValue().getUrl());
		    try {
			Desktop.getDesktop().browse(new URI(streamList.getSelectedValue().getUrl()));
		    } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    } catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
		}
	    });
	    menu.add(openLink);

	}

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

    @Override
    public void update(Observable o, Object arg) {
	// TODO Auto-generated method stub

    }

}
