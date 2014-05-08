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
	registerListeners();
    }

    private void registerListeners() {
	loginView.addConnectionListener(new ConnectionListener());
    }
    
    /**
     * Method to check if email text field is in correct email format
     * 
     * @return true if email field is in "someone@example.com" format
     */
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

	    // dialogBox = new DialogBox("Erreur",
	    // "Format de mail incorrect");
	    // dialogBox.setVisible(true);

	    if (fieldOk) {
		loginView.dispose();
		
		UserDAOImpl userDAO = new UserDAOImpl();
		User user = userDAO.find(loginView.getEmail());
		// TODO vérification du mot de passe
		
		new MainFrameController(user);
	    } else {
		dialogBox = new DialogBox("Erreur", "dans le format de mail");
		dialogBox.setVisible(true);
	    }
	}
    }
}
