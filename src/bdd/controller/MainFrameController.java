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

import bdd.model.Publication;
import bdd.model.Stream;

public class MainFrameController {
    private MainFrameView mainFrameView;
    private List<Publication> publications;
    private List<Stream> streams;

    public MainFrameController() {
	mainFrameView = new MainFrameView();
	publications = new ArrayList<Publication>();
	streams = new ArrayList<Stream>();
	streams = createStreams();
	registerListeners();
	mainFrameView.loadStreams(streams);
	mainFrameView.loadPublications(publications);
    }
    
    private void registerListeners() {
	mainFrameView.addListenerToStreamJList(new StreamPopupMenuListener());
    }

    private List<Stream> createStreams() {
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

	return streams;

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
		    System.out.println(mainFrameView.getSelectedStream()
			    .getUrl());
		    try {
			Desktop.getDesktop().browse(
				new URI(mainFrameView.getSelectedStream()
					.getUrl()));
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
}
