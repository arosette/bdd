package bdd.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JDialog {

    private JPanel mainPanel;
    private JButton connectionButton;
    private JButton registerButton;
    private JTextField emailTextField;
    private JTextField passwordTextField;

    public LoginView() {
	super();
	this.setTitle("Fenetre d'authentification");
	this.setSize(300, 200);
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	this.build();
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

    public String getEmail() {
	return emailTextField.getText();
    }
    
    public String getPassword() {
	return passwordTextField.getText();
    }

    public void setEmailFieldBackground(Color color) {
	emailTextField.setBackground(color);
    }

    public void addConnectionListener(ActionListener connectionListener) {
	connectionButton.addActionListener(connectionListener);
    }
    
    public void addRegistrationListener(ActionListener registrationListener) {
	registerButton.addActionListener(registrationListener);
    }
}