package bdd.view.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	if ((index % 2) == 1) {
	    panel.setBackground(Color.white);
	}
	if (isSelected) {
	    panel.setBackground(Color.cyan);
	}

	String mailOfFriend = new String("Error");

	if (value.getSenderMail().equals(currentUser.getMail())) {
	    mailOfFriend = value.getReceiverMail();
	} else if (value.getReceiverMail().equals(currentUser.getMail())) {
	    mailOfFriend = value.getSenderMail();
	} else {
	    System.out
		    .println("Problème : l'utilisateur actuel ne se trouve pas dans la relation d'amitié");
	}

	panel.add(new JLabel(mailOfFriend));

	String status = new String("Status : ");

	if (value.getStatus()) {
	    status += "accepte";
	} else {
	    status += "en attente";
	}

	panel.add(new JLabel(status));

	return panel;
    }

}
