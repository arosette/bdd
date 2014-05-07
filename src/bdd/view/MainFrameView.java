package bdd.view;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
import bdd.view.renderer.PublicationRenderer;
import bdd.view.renderer.StreamRenderer;

public class MainFrameView extends JFrame implements Observer {

    private JPanel mainPanel;
    private JList<Stream> streamJList;
    private JList<Publication> publicationJList;

    public MainFrameView() {
	this.setTitle("Flux RSS");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setMinimumSize(new Dimension(800, 600));
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
	gbc.insets = new Insets(0, 50, 0, 50);

	// Construction de la liste contenant les flux à gauche de la fenetre

	// Le titre
	mainPanel.add(new JLabel("Flux"), gbc);

	// La liste
	gbc.gridy = 1;
	DefaultListModel<Stream> streamListModel = new DefaultListModel<Stream>();
	streamJList = new JList<Stream>(streamListModel);
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
	DefaultListModel<Publication> publicationListModel = new DefaultListModel<Publication>();
	publicationJList = new JList<Publication>(publicationListModel);
	publicationJList.setCellRenderer(new PublicationRenderer());
	mainPanel.add(new JScrollPane(publicationJList), gbc);

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

	DefaultListModel<Publication> streamListModel = new DefaultListModel<Publication>();
	for (Publication publication : publications) {
	    streamListModel.addElement(publication);
	}
	publicationJList.setModel(streamListModel);

    }

    public Stream getSelectedStream() {
	return streamJList.getSelectedValue();
    }
    
    public void addListenerToStreamJList(MouseListener mouseListener) {
	streamJList.addMouseListener(mouseListener);
    }

    @Override
    public void update(Observable o, Object arg) {
	// TODO Auto-generated method stub

    }

}
