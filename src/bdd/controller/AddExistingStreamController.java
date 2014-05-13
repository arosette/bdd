package bdd.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import bdd.model.Publication;
import bdd.model.PublicationDAOImpl;
import bdd.model.Stream;
import bdd.model.StreamDAOImpl;
import bdd.model.User;
import bdd.parser.RssParser;
import bdd.view.AddExistingStreamView;
import bdd.view.AddXmlStreamView;
import bdd.view.DialogBox;

public class AddExistingStreamController {
    private AddExistingStreamView streamView;
    private User user;

    public AddExistingStreamController(User user) {
	this.user = user;
	streamView = new AddExistingStreamView();
	streamView.setVisible(true);
	streamView.addListenerToAddButton(new AddStreamListener());
    }

    private class AddStreamListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

	    StreamDAOImpl streamDAO = new StreamDAOImpl();
	    Stream stream = streamDAO.find(streamView.getUrl());

	    // Si le flux n'existe pas, on l'ajoute en bdd
	    if (stream != null) {
		// Ajout du flux au flux de l'utilisateur
		streamDAO.associateUser(stream.getUrl(), user.getMail());
		streamView.dispose();
	    } else {
		new DialogBox("Erreur",
			"Le flux n'existe pas en base de donnees");
	    }

	}
    }
}
