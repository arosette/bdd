package bdd.view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AddXmlStreamView extends JDialog {
    private JTextField urlTextfield;
    private JButton addButton;

    /**
     * Create the dialog.
     */
    public AddXmlStreamView() {
	setTitle("Ajouter un flux RSS depuis un fichier xml");
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
    
    public void addListenerToAddButton(ActionListener actionListener) {
	addButton.addActionListener(actionListener);
    }
    
    public String getUrl() {
	return urlTextfield.getText();
    }

}
