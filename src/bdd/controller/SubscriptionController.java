package bdd.controller;

import bdd.model.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bdd.model.User;
import bdd.view.DialogBox;
import bdd.view.SubscriptionView;

public class SubscriptionController {

    private SubscriptionView subscriptionView;
    private DialogBox dialogBox;

    public SubscriptionController() {
	subscriptionView = new SubscriptionView();
	registerListeners();
    }

    private void registerListeners() {
	subscriptionView.addOkListener(new ConnectionListener());
	subscriptionView.addCancelListener(new ConnectionListener());
    }

    private boolean isEmailFieldOk() {
	String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	subscriptionView.setEmailFieldBackground(new Color(255, 255, 255));
	if (subscriptionView.getEmail().matches(EMAIL_REGEX)) {
	    return true;
	}

	else {
	    subscriptionView.setEmailFieldBackground(new Color(200, 0, 0));
	    return false;
	}
    }

    private class ConnectionListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {

	    boolean fieldOk = isEmailFieldOk();

	    if (fieldOk) {

		UserDAOImpl userDAO = new UserDAOImpl();
		User user = userDAO.find(subscriptionView.getEmail());
		if (user == null) {
		    User userToAdd = new User();
		    userToAdd.setMail(subscriptionView.getEmail());
		    userToAdd.setSurname(subscriptionView.getSurname());
		    userToAdd.setPassword(subscriptionView.getPassword());
		    userToAdd.setAvatar(subscriptionView.getAvatar());
		    userToAdd.setCountry(subscriptionView.getCountry());
		    userToAdd.setCity(subscriptionView.getCity());
		    userDAO.insert(userToAdd);

		} else {
		    dialogBox = new DialogBox("Erreur",
			    "l'adresse mail existe d�j� en bdd");
		    dialogBox.setVisible(true);
		}

	    } else {
		dialogBox = new DialogBox("Erreur", "Format de mail incorrect");
		dialogBox.setVisible(true);
	    }
	}
    }
}