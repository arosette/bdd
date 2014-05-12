package bdd.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import bdd.model.Publication;
import bdd.model.Stream;
import bdd.model.Comment;
import bdd.model.CommentDAOImpl;
import bdd.model.User;
import bdd.view.AddCommentView;
import bdd.view.DialogBox;

public class AddCommentController {
    private AddCommentView addCommentView;
    private User user;
    private Publication publication;
    private Stream stream;

    public AddCommentController(User user, Publication publication,
	    Stream stream) {
	this.user = user;
	this.publication = publication;
	this.stream = stream;
	addCommentView = new AddCommentView();
	addCommentView.setVisible(true);
	addCommentView.addCommentListener(new AddCommentListener());
	addCommentView.addCancelListener(new AddCommentListener());
    }

    private String getDate() {
	Date currentDate = new Date();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String date = dateFormat.format(currentDate);

	return date;
    }

    private class AddCommentListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
	    CommentDAOImpl commentDAO = new CommentDAOImpl();
	    Comment comment = commentDAO.find(user.getMail(),
		    publication.getUrl(), stream.getUrl());

	    // Si le commentaire sur la publication du flux
	    // concerné n'existe pas, on l'ajoute en bdd
	    if (comment == null) {

		comment = new Comment();
		comment.setUserMail(user.getMail());
		comment.setPublicationUrl(publication.getUrl());
		comment.setStreamUrl(stream.getUrl());
		comment.setContent(addCommentView.getComment());
		comment.setDate(getDate());

		commentDAO.insert(comment);
	    } else {
		new DialogBox("Erreur",
			"Il y a déjà un commentaire de votre part sur cette publication");
	    }
	}
    }
}