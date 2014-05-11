package bdd.controller;

import bdd.model.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import bdd.model.User;
import bdd.view.DialogBox;
import bdd.view.SubscriptionView;

public class SubscriptionController {

    private SubscriptionView subscriptionView;
    private DialogBox dialogBox;

    public SubscriptionController() {
	subscriptionView = new SubscriptionView();
	okListener();
	cancelListener();
    }

    private void okListener() {
	subscriptionView.addOkListener(new okListener());
    }

    private void cancelListener() {
	subscriptionView.addCancelListener(new CancelListener());
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

    private String getDate() {
	Date currentDate = new Date();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String date = dateFormat.format(currentDate);

	return date;
    }

    private class okListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {

	    boolean fieldOk = isEmailFieldOk();

	    if (fieldOk) {

		UserDAOImpl userDAO = new UserDAOImpl();
		User user = userDAO.find(subscriptionView.getEmail());
		if (user == null) {
		    User userToAdd = new User();
		    Stream streamToAdd = new Stream();
		    StreamDAOImpl streamDAO = new StreamDAOImpl();

		    streamToAdd.setUrl("http://personal_stream/"
			    + subscriptionView.getEmail());
		    streamToAdd.setName(subscriptionView.getStreamName());
		    streamToAdd.setDescription(subscriptionView
			    .getStreamDescription());
		    streamToAdd.setWebLink(subscriptionView.getStreamWebLink());

		    userToAdd.setMail(subscriptionView.getEmail());
		    userToAdd.setSurname(subscriptionView.getSurname());
		    userToAdd.setPassword(subscriptionView.getPassword());
		    userToAdd.setAvatar(subscriptionView.getAvatar());
		    userToAdd.setCountry(subscriptionView.getCountry());
		    userToAdd.setCity(subscriptionView.getCity());
		    userToAdd.setBiography(subscriptionView.getBiography());
		    userToAdd.setDate(getDate());
		    userToAdd.setPersonalStream("http://personal_stream/"
			    + subscriptionView.getEmail());
		    if (streamDAO.insert(streamToAdd) && userDAO.insert(userToAdd)) {
			subscriptionView.dispose();
			dialogBox = new DialogBox("Enregistrement",
				"vos informations ont ete sauvegardees avec succes !");
			dialogBox.setVisible(true);
		    } else {
			dialogBox = new DialogBox("Erreur",
				"Un probleme est survenu lors de l'enregistrement !");
			dialogBox.setVisible(true);
		    }
		    
		} else {
		    dialogBox = new DialogBox("Erreur",
			    "L'adresse mail existe deja dans la base de donnees !");
		    dialogBox.setVisible(true);
		}

	    } else {
		dialogBox = new DialogBox("Erreur", "Le format de mail est incorrect !");
		dialogBox.setVisible(true);
	    }
	}
    }

    private class CancelListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    subscriptionView.dispose();
	}
    }
}