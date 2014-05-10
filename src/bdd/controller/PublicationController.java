package bdd.controller;

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
	this.publicationView.setVisible(true);
    }
}
