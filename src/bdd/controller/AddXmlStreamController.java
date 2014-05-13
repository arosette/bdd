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
import bdd.view.AddXmlStreamView;

public class AddXmlStreamController {
    private AddXmlStreamView streamView;
    private User user;

    public AddXmlStreamController(User user) {
	this.user = user;
	streamView = new AddXmlStreamView();
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

		    Publication publication = publications.get(i);
		    PublicationDAOImpl publicationDAO = new PublicationDAOImpl();

		    // Si la publication n'existe pas en bdd, on l'ajoute
		    if (publicationDAO.find(publication.getUrl()) == null) {
			publicationDAO.insert(publication);
		    }

		    streamDAO.associatePublication(stream.getUrl(),
			    publication.getUrl());
		}
	    }

	    // Ajout du flux au flux de l'utilisateur
	    streamDAO.associateUser(stream.getUrl(), user.getMail());
	    streamView.dispose();
	}
    }
}
