package bdd.controller;

import javax.swing.JFrame;

import bdd.model.Publication;
import bdd.view.PublicationView;

public class PublicationController {
    private PublicationView publicationView;
    private Publication pulication;
    
    public PublicationController(Publication publication, JFrame parentFrame) {
	this.pulication = publication;
	this.publicationView = new PublicationView(parentFrame);
    }
}
