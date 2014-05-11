package bdd.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bdd.model.Friendship;
import bdd.model.FriendshipDAOImpl;
import bdd.model.User;
import bdd.model.UserDAOImpl;
import bdd.view.AddFriendView;
import bdd.view.DialogBox;

public class AddFriendController {
    private AddFriendView addFriendView;
    private User user;

    public AddFriendController(User user) {
	this.user = user;
	addFriendView = new AddFriendView();
	addFriendView.setVisible(true);
	addFriendView.addListenerToAddButton(new AddFriendListener());
    }

    private class AddFriendListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    UserDAOImpl userDAO = new UserDAOImpl();
	    User newFriend = userDAO.find(addFriendView.getUserMail());
	    if (newFriend != null) {
		FriendshipDAOImpl friendshipDAO = new FriendshipDAOImpl();
		// Si il n'y a pas déjà eu une demande
		if (friendshipDAO.find(user.getMail(), newFriend.getMail()) == null
			&& friendshipDAO.find(newFriend.getMail(),
				user.getMail()) == null) {
		    Friendship newFriendship = new Friendship();
		    newFriendship.setSenderMail(user.getMail());
		    newFriendship.setReceiverMail(newFriend.getMail());
		    newFriendship.setStatus(false);
		    friendshipDAO.insert(newFriendship);
		    addFriendView.dispose();
		} else {
		    new DialogBox("Erreur",
			    "Il y a deja une demande d'ami (envoyee ou recue) pour cet utilisateur");
		}
	    } else {
		new DialogBox("Erreur",
			"L'utilisateur que vous voulez ajouter en tant qu'ami n'existe pas");
	    }
	}

    }
}
