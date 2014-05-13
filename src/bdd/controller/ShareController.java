package bdd.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bdd.model.Publication;
import bdd.view.ShareView;

public class ShareController {
    private ShareView shareView;
    private Publication publication;

    public ShareController(Publication publication) {
	this.publication = publication;
	this.shareView = new ShareView();

	this.shareView.loadData(this.publication.getUrl(),
		this.publication.getTitle());
	this.registerListeners();
	this.shareView.setVisible(true);
    }
    
    public void registerListeners() {
	this.shareView.addListenerToShareButton(new SharePublication());
    }
    
    private class SharePublication implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
	    System.out.println("Share : ok");
	}
	
    }
}
