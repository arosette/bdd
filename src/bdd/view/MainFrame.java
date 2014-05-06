package bdd.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
    }

    public void build() {
	this.mainPanel = new JPanel();
	this.mainPanel.setLayout(new GridBagLayout());

	GridBagConstraints gbc = new GridBagConstraints();

	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridheight = 1;
	gbc.gridwidth = 1;

	// Construction de la liste contenant les flux à gauche de la fenetre
	DefaultListModel<Stream> streamListModel = new DefaultListModel<Stream>();

	streamList = new JList<Stream>(streamListModel);
	streamList.setCellRenderer(new StreamRenderer());
	mainPanel.add(new JScrollPane(streamList), gbc);

	// Construction de la liste contenant les publications au centre de la
	// fenetre
	DefaultListModel<Publication> publicationListModel = new DefaultListModel<Publication>();

	publicationList = new JList<Publication>(publicationListModel);
	publicationList.setCellRenderer(new PublicationRenderer());
	gbc.gridx = 1;
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

    @Override
    public void update(Observable o, Object arg) {
	// TODO Auto-generated method stub

    }

}
