package bdd.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.print.attribute.standard.SheetCollate;

import bdd.model.Comment;
import bdd.model.CommentDAOImpl;
import bdd.model.Publication;
import bdd.model.StreamDAOImpl;
import bdd.model.User;
import bdd.view.DialogBox;
import bdd.view.ShareView;

public class ShareController {
    private ShareView shareView;
    private Publication publication;
    private User user;

    public ShareController(Publication publication, User user) {
	this.publication = publication;
	this.user = user;
	this.shareView = new ShareView();

	this.shareView.loadData(this.publication.getUrl(),
		this.publication.getTitle());
	this.registerListeners();
	this.shareView.setVisible(true);
    }

    public void registerListeners() {
	this.shareView.addListenerToShareButton(new SharePublication());
    }

    private class SharePublication implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    StreamDAOImpl streamDAO = new StreamDAOImpl();
	    boolean associationExists = streamDAO
		    .isPublicationAssociateWithStream(user.getPersonalStream(),
			    publication.getUrl());

	    if (!associationExists) {
		streamDAO.associatePublication(user.getPersonalStream(),
			publication.getUrl());
		// Si on a coche la case qui active les commentaires, on ajoute
		// le commentaire
		if (shareView.isCommented()) {
		    Comment comment = new Comment();
		    comment.setUserMail(user.getMail());
		    comment.setPublicationUrl(publication.getUrl());
		    comment.setStreamUrl(user.getPersonalStream());
		    comment.setContent(shareView.getComment());
		    CommentDAOImpl commentDAO = new CommentDAOImpl();
		    commentDAO.insert(comment);
		    
		}
		shareView.dispose();
	    } else {
		new DialogBox("Erreur", "Cette publication a deja ete partagee");
	    }
	}
    }
}
