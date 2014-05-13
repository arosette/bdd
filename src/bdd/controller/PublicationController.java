package bdd.controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import bdd.model.Publication;
import bdd.view.PublicationView;

public class PublicationController {
    private PublicationView publicationView;
    private Publication publication;

    public PublicationController(Publication publication, JFrame parentFrame) {
	this.publication = publication;
	this.publicationView = new PublicationView();
	this.publicationView.loadPublication(publication);
	this.registerListeners();
	this.publicationView.setVisible(true);
    }

    public void registerListeners() {
	publicationView
		.addListenerToOpenLinkButton(new OpenLinkInBrowserListener());
    }

    private class OpenLinkInBrowserListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    try {
		Desktop.getDesktop().browse(new URI(publication.getUrl()));
	    } catch (IOException e1) {
		e1.printStackTrace();
	    } catch (URISyntaxException e1) {
		e1.printStackTrace();
	    }
	}
    }
}
