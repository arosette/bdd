package bdd.view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AddStreamView extends JDialog {
    private JTextField urlTextfield;
    private JButton addButton;

    /**
     * Create the dialog.
     */
    public AddStreamView() {
	setTitle("Ajouter un flux RSS");
	setBounds(100, 100, 300, 88);
	getContentPane().setLayout(
		new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

	JLabel urlLabel = new JLabel("Url");
	getContentPane().add(urlLabel);

	urlTextfield = new JTextField();
	getContentPane().add(urlTextfield);
	urlTextfield.setColumns(50);

	addButton = new JButton("Ajouter");
	getContentPane().add(addButton);

    }

}
