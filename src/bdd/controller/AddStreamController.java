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
import bdd.view.AddStreamView;

public class AddStreamController {
    private AddStreamView streamView;
    private User user;

    public AddStreamController(User user) {
	this.user = user;
	streamView = new AddStreamView();
	streamView.setVisible(true);
	streamView.addListenerToAddButton(new AddStreamListener());
    }

    private class AddStreamListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

	    StreamDAOImpl streamDAO = new StreamDAOImpl();
	    Stream stream = streamDAO.find(streamView.getUrl());

	    // Si le flux n'existe pas, on l'ajoute en bdd
	    if (stream == null) {
		RssParser rssParser = new RssParser(streamView.getUrl());

		stream = new Stream();
		stream.setUrl(streamView.getUrl());
		stream.setName(rssParser.getTitle());
		stream.setWebLink(rssParser.getLink());
		stream.setDescription(rssParser.getDescription());
		streamDAO.insert(stream);

		List<Publication> publications = rssParser.getPulications();
		for (int i = 0; i < 10 && i < publications.size(); ++i) {
		    // TODO Attention, ici, il faut peut etre verifier que la
		    // publication n'existe pas
		    Publication publication = publications.get(i);
		    PublicationDAOImpl publicationDAO = new PublicationDAOImpl();
		    publicationDAO.insert(publication);
		    streamDAO.associatePublication(stream, publication);
		}
	    }
	    
	    // Ajout du flux au flux de l'utilisateur
	    streamDAO.associateUser(stream, user);
	    streamView.dispose();
	}
    }
}