package bdd.controller;

import bdd.model.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bdd.model.User;
import bdd.view.DialogBox;
import bdd.view.LoginView;

public class LoginController {

    private LoginView loginView;
    private DialogBox dialogBox;

    public LoginController() {
	loginView = new LoginView();
	connectionListener();
	registrationListener();
    }

    private void connectionListener() {
	loginView.addConnectionListener(new ConnectionListener());
    }
    
    private void registrationListener() {
	loginView.addRegistrationListener(new RegistrationListener());
    }

    private boolean isEmailFieldOk() {
	String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	loginView.setEmailFieldBackground(new Color(255, 255, 255));
	if (loginView.getEmail().matches(EMAIL_REGEX)) {
	    return true;
	}

	else {
	    loginView.setEmailFieldBackground(new Color(200, 0, 0));
	    return false;
	}
    }

    private class ConnectionListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {

	    boolean fieldOk = isEmailFieldOk();

	    if (fieldOk) {

		UserDAOImpl userDAO = new UserDAOImpl();
		User user = userDAO.find(loginView.getEmail());
		if (user != null) {
		    if (loginView.getPassword().equals(user.getPassword())) {
			loginView.dispose();
			new MainFrameController(user);
		    } else {
			dialogBox = new DialogBox("Erreur",
				"Mot de passe incorrect");
			dialogBox.setVisible(true);
		    }
		} else {
		    dialogBox = new DialogBox("Erreur",
			    "Adresse mail pas en bdd");
		    dialogBox.setVisible(true);
		}

	    } else {
		dialogBox = new DialogBox("Erreur", "Format de mail incorrect");
		dialogBox.setVisible(true);
	    }
	}
    }
    
    private class RegistrationListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    new SubscriptionController();
	}
    }
}