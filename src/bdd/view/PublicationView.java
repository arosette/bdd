package bdd.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PublicationView extends JDialog {

    private JPanel mainPanel;
    private JCheckBox publicationRed;
    private JTextField associatedStreamName;
    private JTextArea publicationDescription;
    private JTextField publicationUrl;
    private JTextField publicationDate;
    
    public PublicationView(JFrame parent) {
	super(parent);
	setTitle("Publication");
	setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	setSize(800, 600);
	build();
    }
    
    private void build() {
	int defaultColumnLength = 20;
	int defaultRowLength = 10;
	
	
	this.mainPanel = new JPanel();
	this.mainPanel.setLayout(new GridBagLayout());
	
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridheight = 1;
	gbc.gridwidth = 2;
	
	publicationRed = new JCheckBox("Lue");
	publicationRed.setOpaque(false);
	publicationRed.setEnabled(false);
	this.mainPanel.add(publicationRed, gbc);
	
	gbc.gridy = 1;
	gbc.gridwidth = 1;
	this.mainPanel.add(new JLabel("Flux : "), gbc);
	
	gbc.gridx = 1;
	associatedStreamName = new JTextField(defaultColumnLength);
	associatedStreamName.setEditable(false);
	associatedStreamName.setBackground(Color.white);
	this.mainPanel.add(associatedStreamName, gbc);
	
	gbc.gridx = 0;
	gbc.gridy = 2;
	gbc.gridwidth = 2;
	this.mainPanel.add(new JLabel("Description : "), gbc);
	
	gbc.gridy = 3;
	publicationDescription = new JTextArea(defaultRowLength, defaultColumnLength);
	publicationDescription.setEditable(false);
	publicationDescription.setBackground(Color.white);
	this.mainPanel.add(publicationDescription, gbc);
	
	gbc.gridwidth = 1;
	gbc.gridx = 0;
	gbc.gridy = 4;
	this.mainPanel.add(new JLabel("Url : "), gbc);
	
	gbc.gridx = 1;
	publicationUrl = new JTextField(defaultColumnLength);
	publicationUrl.setEditable(false);
	publicationUrl.setBackground(Color.white);
	this.mainPanel.add(publicationUrl, gbc);
	
	gbc.gridx = 0;
	gbc.gridy = 5;
	this.mainPanel.add(new JLabel("Date : "), gbc);
	
	gbc.gridx = 1;
	publicationDate = new JTextField(defaultColumnLength);
	publicationDate.setEditable(false);
	publicationDate.setBackground(Color.white);
	this.mainPanel.add(publicationDate, gbc);
	
	
	
	
	this.setContentPane(this.mainPanel);
	this.setVisible(true);
    }
}
