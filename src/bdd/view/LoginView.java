package bdd.view;
import bdd.view.DialogBox;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JDialog {

    private JPanel mainPanel;
    private JButton connectionButton;
    private JTextField emailTextField;
    private JTextField passwordTextField;
    private DialogBox dialogBox;

    public LoginView() {
	this.setTitle("Fenêtre d'authentification");
	this.setSize(300, 200);
	this.build();
	this.createEvent();
    }

    public void build() {
	this.mainPanel = new JPanel();
	this.mainPanel.setLayout(new GridBagLayout());

	GridBagConstraints gbc = new GridBagConstraints();

	gbc.gridx = 0;
	gbc.gridy = 0;

	gbc.gridheight = 1;
	gbc.gridwidth = 1;

	emailTextField = new JTextField(10);
	mainPanel.add(new JLabel("Adresse mail : "), gbc);
	gbc.gridx = 1;
	mainPanel.add(emailTextField, gbc);
	gbc.gridx = 0;
	gbc.gridy = 1;

	passwordTextField = new JPasswordField(10);
	mainPanel.add(new JLabel("Mot de passe : "), gbc);
	gbc.gridx = 1;
	mainPanel.add(passwordTextField, gbc);

	gbc.gridx = 0;
	gbc.gridy = 2;
	gbc.gridwidth = 2;
	connectionButton = new JButton("Connexion");
	mainPanel.add(connectionButton, gbc);
	this.setContentPane(this.mainPanel);
	this.setVisible(true);
    }

    /**
     * Method to check if email text field is in correct email format
     * 
     * @return true if email field is in "someone@example.com" format
     */
    public boolean checkIfEmailFieldOk() {
	String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	emailTextField.setBackground(new Color(255, 255, 255));
	if (emailTextField.getText().matches(EMAIL_REGEX)) {
	    return true;
	}

	else {
	    emailTextField.setBackground(new Color(200, 0, 0));
	    return false;
	}
    }

    /**
     * Method that will catch any user interaction with the window
     */
    public void createEvent() {
	connectionButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {

		boolean fieldOk = checkIfEmailFieldOk();
		
		// dialogBox = new DialogBox("Erreur", "Format de mail incorrect");
		// dialogBox.setVisible(true);
		
		if (fieldOk) {
		    System.out.println("Correct e-mail format");
		}
		else {
		    dialogBox = new DialogBox("Erreur", "dans le format de mail");
                    dialogBox.setVisible(true);
		}
	    }
	});
    }
}