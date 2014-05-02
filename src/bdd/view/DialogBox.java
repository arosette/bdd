package bdd.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DialogBox extends JDialog {

    protected JPanel contentPanel = new JPanel();
    protected JButton okButton;

    public DialogBox(String title, String text) {
	
	this.build(title, text);
	this.createEvent();
    }

    public void build(String title, String text) {
	
	this.setTitle(title);
	this.setSize(200, 100);
	GridBagConstraints gbc = new GridBagConstraints();
	
	this.contentPanel = new JPanel();
	this.contentPanel.setLayout(new GridBagLayout());

	contentPanel.add(new JLabel(text), gbc);
	gbc.gridx = 1;
	
	gbc.gridx = 0;
	gbc.gridy = 2;
	gbc.gridwidth = 2;
	okButton = new JButton("OK");
	contentPanel.add(okButton, gbc);
	this.setContentPane(this.contentPanel);
	this.setVisible(true);
    }

    public void createEvent() {
	okButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		dispose();
	    }
	});
    }
}
