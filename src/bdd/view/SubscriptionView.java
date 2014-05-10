package bdd.view;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SubscriptionView {
    
    private JPanel mainPanel;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField emailTextField;
    private JTextField surnameTextField;
    private JTextField passwordTextField;
    private JTextField avatarTextField;
    private JTextField countryTextField;
    private JTextField cityTextField;
    
    public SubscriptionView() {
	build();
    }
    
    public void build() {
	this.mainPanel = new JPanel();
	
	emailTextField = new JTextField(10);
	surnameTextField = new JTextField(10);
	passwordTextField = new JPasswordField(10);
	avatarTextField = new JTextField(20);
	countryTextField = new JTextField(10);
	cityTextField = new JTextField(10);
	okButton = new JButton("Ok");
	cancelButton = new JButton("Ok");
	
    }
    
    public String getEmail() {
	return emailTextField.getText();
    }
    
    public String getSurname() {
	return surnameTextField.getText();
    }
    
    public String getPassword() {
	return passwordTextField.getText();
    }
    
    public String getAvatar() {
	return avatarTextField.getText();
    }
    
    public String getCountry() {
	return countryTextField.getText();
    }
    
    public String getCity() {
	return cityTextField.getText();
    }
    
    public void setEmailFieldBackground(Color color) {
	emailTextField.setBackground(color);
    }
    
    public void addOkListener(ActionListener okListener) {
	okButton.addActionListener(okListener);
    }
    
    public void addCancelListener(ActionListener cancelListener) {
	cancelButton.addActionListener(cancelListener);
    }
}
