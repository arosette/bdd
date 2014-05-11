package bdd.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bdd.model.User;
import bdd.view.AddFriendView;

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

	}

    }
}
