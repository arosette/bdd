package bdd.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JDialog {
	
	private JPanel mainPanel;
	
	public LoginView() {
		this.setTitle("Fenêtre d'authentification");
		this.setSize(300, 200);
		this.build();
		this.setContentPane(this.mainPanel);
		this.setVisible(true);
	}
	
	public void build() {
		this.mainPanel = new JPanel();
		this.mainPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		
		mainPanel.add(new JLabel("Adresse mail : "), gbc);
		gbc.gridx = 1;
		mainPanel.add(new JTextField(10), gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		mainPanel.add(new JLabel("Mot de passe : "), gbc);
		gbc.gridx = 1;
		mainPanel.add(new JPasswordField(10), gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		mainPanel.add(new JButton("Connexion"), gbc);
	}
}
