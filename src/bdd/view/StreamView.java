package bdd.view;

import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

import bdd.model.Stream;
import java.awt.Component;

public class StreamView extends JDialog {
	private JTextField urlTextfield;
	private JTextField nameTextfield;
	private JTextField weblinkTextfield;
	private JTextArea descriptionTextArea;
	private JButton openUrlButton;

    /**
     * Create the dialog.
     */
    public StreamView() {
    	setTitle("Flux");
	setBounds(100, 100, 450, 300);
	getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
	
	JLabel urlLabel = new JLabel("Url");
	getContentPane().add(urlLabel);
	
	urlTextfield = new JTextField();
	urlTextfield.setEditable(false);
	getContentPane().add(urlTextfield);
	urlTextfield.setColumns(30);
	
	openUrlButton = new JButton("Ouvrir le lien dans le navigateur");
	openUrlButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	getContentPane().add(openUrlButton);
	
	JLabel nameLabel = new JLabel("Nom");
	getContentPane().add(nameLabel);
	
	nameTextfield = new JTextField();
	nameTextfield.setEditable(false);
	getContentPane().add(nameTextfield);
	nameTextfield.setColumns(30);
	
	JLabel weblinkLabel = new JLabel("Lien web");
	getContentPane().add(weblinkLabel);
	
	weblinkTextfield = new JTextField();
	weblinkTextfield.setEditable(false);
	getContentPane().add(weblinkTextfield);
	weblinkTextfield.setColumns(10);
	
	JLabel descriptionLabel = new JLabel("Description");
	getContentPane().add(descriptionLabel);
	
	descriptionTextArea = new JTextArea();
	descriptionTextArea.setEditable(false);
	descriptionTextArea.setColumns(50);
	descriptionTextArea.setRows(10);
	descriptionTextArea.setLineWrap(true);
	descriptionTextArea.setWrapStyleWord(true);
	getContentPane().add(descriptionTextArea);

    }
    
    public void loadStream(Stream stream) {
	urlTextfield.setText(stream.getUrl());
	nameTextfield.setText(stream.getName());
	weblinkTextfield.setText(stream.getWebLink());
	descriptionTextArea.setText(stream.getDescription());
    }
    
    public void addListenerToOpenUrlButton(ActionListener actionListener) {
	openUrlButton.addActionListener(actionListener);
    }

}
