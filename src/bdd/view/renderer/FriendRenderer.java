package bdd.view.renderer;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import bdd.model.Friendship;
import bdd.model.User;

public class FriendRenderer implements ListCellRenderer<Friendship> {

    private User currentUser;

    public FriendRenderer(User currentUser) {
	this.currentUser = currentUser;
    }

    @Override
    public Component getListCellRendererComponent(
	    JList<? extends Friendship> list, Friendship value, int index,
	    boolean isSelected, boolean cellHasFocus) {

	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

	
	String mailOfFriend = new String("Error");
	
	if (value.getMailUser1() == currentUser.getMail()) {
	    mailOfFriend = value.getMailUser2();
	} else if (value.getMailUser2() == currentUser.getMail()) {
	    mailOfFriend = value.getMailUser1();
	} else {
	    System.out
		    .println("Probleme : l'utilisateur actuel ne se trouve pas dans la relation d'amitié");
	}
	
	panel.add(new JLabel(mailOfFriend));
	
	String status = new String("Status : ");
	
	if(value.getStatus()) {
	    status += "accepte";
	}
	else {
	    status += "en attente";
	}
	
	panel.add(new JLabel(status));
	
	
	JButton acceptButton = new JButton("Accepter");
	boolean isButtonEnabled;
	
	if ((value.getMailUser2() == currentUser.getMail()) && !value.getStatus()) {
	    isButtonEnabled = true;
	}
	else {
	    isButtonEnabled = false;
	}
	
	acceptButton.setEnabled(isButtonEnabled);
	panel.add(acceptButton);

	return panel;
    }
}
