package bdd.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bdd.model.Publication;
import bdd.model.PublicationDAOImpl;
import bdd.model.Stream;
import bdd.model.StreamDAOImpl;
import bdd.model.Comment;
import bdd.model.CommentDAOImpl;
import bdd.model.User;
import bdd.model.UserDAOImpl;
import bdd.view.AddCommentView;
import bdd.view.DialogBox;

public class AddCommentController {
    private AddCommentView addCommentView;
    private User user;

    public AddCommentController(User user, Publication publication, Stream stream) {
	this.user = user;
	addCommentView = new AddCommentView();
	addCommentView.setVisible(true);
	addCommentView.addCommentListener(new AddCommentListener());
	addCommentView.addCancelListener(new AddCommentListener());
    }

    private class AddCommentListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {

	}
    }
}
