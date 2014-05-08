package bdd.view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTextArea;

public class PublicationView extends JDialog {
	private JTextField associatedStreamTextfield;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    PublicationView dialog = new PublicationView();
		    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		    dialog.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the dialog.
     */
    public PublicationView() {
	setBounds(100, 100, 451, 480);
	GridBagLayout gridBagLayout = new GridBagLayout();
	gridBagLayout.columnWidths = new int[]{0, 0, 0};
	gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
	gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
	gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
	getContentPane().setLayout(gridBagLayout);
	
	JCheckBox publicationReadCheckBox = new JCheckBox("Lue");
	publicationReadCheckBox.setForeground(Color.BLACK);
	publicationReadCheckBox.setEnabled(false);
	GridBagConstraints gbc_publicationReadCheckBox = new GridBagConstraints();
	gbc_publicationReadCheckBox.anchor = GridBagConstraints.WEST;
	gbc_publicationReadCheckBox.gridwidth = 2;
	gbc_publicationReadCheckBox.insets = new Insets(0, 0, 5, 0);
	gbc_publicationReadCheckBox.gridx = 0;
	gbc_publicationReadCheckBox.gridy = 0;
	getContentPane().add(publicationReadCheckBox, gbc_publicationReadCheckBox);
	
	JLabel streamLabel = new JLabel("Flux : ");
	GridBagConstraints gbc_streamLabel = new GridBagConstraints();
	gbc_streamLabel.insets = new Insets(0, 0, 5, 5);
	gbc_streamLabel.anchor = GridBagConstraints.WEST;
	gbc_streamLabel.gridx = 0;
	gbc_streamLabel.gridy = 1;
	getContentPane().add(streamLabel, gbc_streamLabel);
	
	associatedStreamTextfield = new JTextField();
	associatedStreamTextfield.setEditable(false);
	GridBagConstraints gbc_associatedStreamTextfield = new GridBagConstraints();
	gbc_associatedStreamTextfield.insets = new Insets(0, 0, 5, 0);
	gbc_associatedStreamTextfield.fill = GridBagConstraints.HORIZONTAL;
	gbc_associatedStreamTextfield.gridx = 1;
	gbc_associatedStreamTextfield.gridy = 1;
	getContentPane().add(associatedStreamTextfield, gbc_associatedStreamTextfield);
	associatedStreamTextfield.setColumns(10);
	
	JLabel descriptionLabel = new JLabel("Description : ");
	GridBagConstraints gbc_descriptionLabel = new GridBagConstraints();
	gbc_descriptionLabel.insets = new Insets(0, 0, 5, 0);
	gbc_descriptionLabel.gridwidth = 2;
	gbc_descriptionLabel.gridx = 0;
	gbc_descriptionLabel.gridy = 2;
	getContentPane().add(descriptionLabel, gbc_descriptionLabel);
	
	JTextArea textArea = new JTextArea();
	textArea.setColumns(10);
	textArea.setRows(5);
	GridBagConstraints gbc_textArea = new GridBagConstraints();
	gbc_textArea.gridwidth = 2;
	gbc_textArea.fill = GridBagConstraints.BOTH;
	gbc_textArea.gridx = 0;
	gbc_textArea.gridy = 3;
	getContentPane().add(textArea, gbc_textArea);

    }
}
