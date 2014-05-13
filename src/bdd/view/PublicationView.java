package bdd.view;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import java.awt.Insets;

import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JList;

import bdd.model.Comment;
import bdd.model.Publication;
import bdd.view.renderer.CommentRenderer;

import javax.swing.JButton;

public class PublicationView extends JDialog {

    private JCheckBox publicationReadCheckBox;
    private JTextArea descriptionTextArea;
    private JTextField urlTextfield;
    private JTextField dateTextfield;
    private JTextField titleTextfield;
    private JList<Comment> commentList;
    private JButton openLinkButton;

    /**
     * Create the dialog.
     */
    public PublicationView() {
    	setTitle("Publication");
	setBounds(100, 100, 694, 452);
	GridBagLayout gridBagLayout = new GridBagLayout();
	gridBagLayout.columnWidths = new int[] { 119, 348, 0 };
	gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
	gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
		0.0, 0.0, 1.0, Double.MIN_VALUE };
	getContentPane().setLayout(gridBagLayout);

	publicationReadCheckBox = new JCheckBox("Lue");
	publicationReadCheckBox.setForeground(Color.BLACK);
	publicationReadCheckBox.setEnabled(false);
	GridBagConstraints gbc_publicationReadCheckBox = new GridBagConstraints();
	gbc_publicationReadCheckBox.anchor = GridBagConstraints.WEST;
	gbc_publicationReadCheckBox.gridwidth = 2;
	gbc_publicationReadCheckBox.insets = new Insets(0, 0, 5, 0);
	gbc_publicationReadCheckBox.gridx = 0;
	gbc_publicationReadCheckBox.gridy = 0;
	getContentPane().add(publicationReadCheckBox,
		gbc_publicationReadCheckBox);

	JLabel titleLabel = new JLabel("Titre : ");
	GridBagConstraints gbc_titleLabel = new GridBagConstraints();
	gbc_titleLabel.anchor = GridBagConstraints.WEST;
	gbc_titleLabel.insets = new Insets(0, 0, 5, 5);
	gbc_titleLabel.gridx = 0;
	gbc_titleLabel.gridy = 2;
	getContentPane().add(titleLabel, gbc_titleLabel);

	titleTextfield = new JTextField();
	titleTextfield.setEditable(false);
	GridBagConstraints gbc_titleTextfield = new GridBagConstraints();
	gbc_titleTextfield.insets = new Insets(0, 0, 5, 0);
	gbc_titleTextfield.fill = GridBagConstraints.HORIZONTAL;
	gbc_titleTextfield.gridx = 1;
	gbc_titleTextfield.gridy = 2;
	getContentPane().add(titleTextfield, gbc_titleTextfield);
	titleTextfield.setColumns(10);

	JLabel descriptionLabel = new JLabel("Description : ");
	GridBagConstraints gbc_descriptionLabel = new GridBagConstraints();
	gbc_descriptionLabel.insets = new Insets(0, 0, 5, 0);
	gbc_descriptionLabel.gridwidth = 2;
	gbc_descriptionLabel.gridx = 0;
	gbc_descriptionLabel.gridy = 3;
	getContentPane().add(descriptionLabel, gbc_descriptionLabel);

	descriptionTextArea = new JTextArea();
	descriptionTextArea.setEditable(false);
	descriptionTextArea.setColumns(50);
	descriptionTextArea.setRows(10);
	descriptionTextArea.setLineWrap(true);
	descriptionTextArea.setWrapStyleWord(true);
	GridBagConstraints gbc_descriptionTextArea = new GridBagConstraints();
	gbc_descriptionTextArea.insets = new Insets(0, 0, 5, 0);
	gbc_descriptionTextArea.gridwidth = 2;
	gbc_descriptionTextArea.gridx = 0;
	gbc_descriptionTextArea.gridy = 4;
	getContentPane().add(descriptionTextArea, gbc_descriptionTextArea);

	JLabel urlLabel = new JLabel("Url : ");
	GridBagConstraints gbc_urlLabel = new GridBagConstraints();
	gbc_urlLabel.anchor = GridBagConstraints.WEST;
	gbc_urlLabel.insets = new Insets(0, 0, 5, 5);
	gbc_urlLabel.gridx = 0;
	gbc_urlLabel.gridy = 5;
	getContentPane().add(urlLabel, gbc_urlLabel);

	urlTextfield = new JTextField();
	urlTextfield.setEditable(false);
	GridBagConstraints gbc_urlTextfield = new GridBagConstraints();
	gbc_urlTextfield.insets = new Insets(0, 0, 5, 0);
	gbc_urlTextfield.fill = GridBagConstraints.HORIZONTAL;
	gbc_urlTextfield.gridx = 1;
	gbc_urlTextfield.gridy = 5;
	getContentPane().add(urlTextfield, gbc_urlTextfield);
	urlTextfield.setColumns(10);
	
	openLinkButton = new JButton("Ouvrir le lien dans le navigateur");
	GridBagConstraints gbc_openLinkButton = new GridBagConstraints();
	gbc_openLinkButton.insets = new Insets(0, 0, 5, 0);
	gbc_openLinkButton.gridx = 1;
	gbc_openLinkButton.gridy = 6;
	getContentPane().add(openLinkButton, gbc_openLinkButton);

	JLabel dateLabel = new JLabel("Date : ");
	GridBagConstraints gbc_dateLabel = new GridBagConstraints();
	gbc_dateLabel.anchor = GridBagConstraints.WEST;
	gbc_dateLabel.insets = new Insets(0, 0, 5, 5);
	gbc_dateLabel.gridx = 0;
	gbc_dateLabel.gridy = 7;
	getContentPane().add(dateLabel, gbc_dateLabel);

	dateTextfield = new JTextField();
	dateTextfield.setEditable(false);
	GridBagConstraints gbc_dateTextfield = new GridBagConstraints();
	gbc_dateTextfield.insets = new Insets(0, 0, 5, 0);
	gbc_dateTextfield.fill = GridBagConstraints.HORIZONTAL;
	gbc_dateTextfield.gridx = 1;
	gbc_dateTextfield.gridy = 7;
	getContentPane().add(dateTextfield, gbc_dateTextfield);
	dateTextfield.setColumns(10);

	JLabel commentLabel = new JLabel("Commentaires : ");
	GridBagConstraints gbc_commentLabel = new GridBagConstraints();
	gbc_commentLabel.insets = new Insets(0, 0, 5, 0);
	gbc_commentLabel.gridwidth = 2;
	gbc_commentLabel.gridx = 0;
	gbc_commentLabel.gridy = 8;
	getContentPane().add(commentLabel, gbc_commentLabel);

	commentList = new JList();
	commentList.setCellRenderer(new CommentRenderer());
	GridBagConstraints gbc_commentList = new GridBagConstraints();
	gbc_commentList.insets = new Insets(0, 50, 0, 0);
	gbc_commentList.gridwidth = 2;
	gbc_commentList.fill = GridBagConstraints.BOTH;
	gbc_commentList.gridx = 0;
	gbc_commentList.gridy = 9;
	getContentPane().add(new JScrollPane(commentList), gbc_commentList);

    }

    public void loadPublication(Publication publication) {
	this.publicationReadCheckBox.setSelected(publication.isRead());
	this.titleTextfield.setText(publication.getTitle());
	this.descriptionTextArea.setText(publication.getDescription());
	this.urlTextfield.setText(publication.getUrl());
	this.dateTextfield.setText(publication.getDate());
	DefaultListModel<Comment> commentListModel = new DefaultListModel<Comment>();
	if (publication.getComments() != null) {
	    for (Comment comment : publication.getComments()) {
		commentListModel.addElement(comment);
	    }
	}
	this.commentList.setModel(commentListModel);
    }
    
    public void addListenerToOpenLinkButton(ActionListener actionListener) {
	openLinkButton.addActionListener(actionListener);
    }
}
