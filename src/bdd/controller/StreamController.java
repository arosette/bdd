package bdd.controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import bdd.model.Stream;
import bdd.view.StreamView;

public class StreamController {
    private StreamView streamView;
    private Stream stream;
    
    public StreamController(Stream stream) {
	this.stream = stream;
	this.streamView = new StreamView();
	this.streamView.loadStream(this.stream);
	this.registerListeners();
	this.streamView.setVisible(true);
    }
    
    public void registerListeners() {
	streamView.addListenerToOpenUrlButton(new OpenLinkInBrowserListener());
    }
    
    private class OpenLinkInBrowserListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    try {
		Desktop.getDesktop().browse(new URI(stream.getUrl()));
	    } catch (IOException e1) {
		e1.printStackTrace();
	    } catch (URISyntaxException e1) {
		e1.printStackTrace();
	    }
	}
    }
}
