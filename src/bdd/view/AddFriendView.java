package bdd.view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AddFriendView extends JDialog {
    private JTextField mailTextfield;
    private JButton addButton;

    /**
     * Create the dialog.
     */
    public AddFriendView() {
	setTitle("Ajouter un ami");
	setBounds(100, 100, 452, 108);
	getContentPane().setLayout(
		new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

	JLabel userMail = new JLabel("Mail de l'ami à ajouter");
	getContentPane().add(userMail);

	mailTextfield = new JTextField();
	getContentPane().add(mailTextfield);
	mailTextfield.setColumns(10);

	addButton = new JButton("Ajouter");
	getContentPane().add(addButton);

    }

    public void addListenerToAddButton(ActionListener actionListener) {
	addButton.addActionListener(actionListener);
    }

    public String getUserMail() {
	return mailTextfield.getText();
    }

}
