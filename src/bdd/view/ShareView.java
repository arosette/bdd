package bdd.view;

import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class ShareView extends JDialog {
	private JTextField urlTextfield;
	private JTextField titleTextfield;
	private JCheckBox writeCommentCheckBox;
	private JTextArea commentTextArea;
	private JButton shareButon;

    /**
     * Create the dialog.
     */
    public ShareView() {
    	setTitle("Partager une publication");
	setBounds(100, 100, 450, 300);
	getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
	
	JLabel urlLabel = new JLabel("Url");
	getContentPane().add(urlLabel);
	
	urlTextfield = new JTextField();
	urlTextfield.setEditable(false);
	getContentPane().add(urlTextfield);
	urlTextfield.setColumns(30);
	
	JLabel titleLabel = new JLabel("Titre");
	getContentPane().add(titleLabel);
	
	titleTextfield = new JTextField();
	titleTextfield.setEditable(false);
	getContentPane().add(titleTextfield);
	titleTextfield.setColumns(10);
	
	writeCommentCheckBox = new JCheckBox("Commentaire");
	getContentPane().add(writeCommentCheckBox);
	
	commentTextArea = new JTextArea();
	getContentPane().add(commentTextArea);
	
	shareButon = new JButton("Partager");
	getContentPane().add(shareButon);

    }
    
    public void loadData(String publicationUrl, String publicationTitle) {
	urlTextfield.setText(publicationUrl);
	titleTextfield.setText(publicationTitle);
    }
    
    public boolean isCommented() {
	return writeCommentCheckBox.isSelected();
    }
    
    public String getComment() {
	return commentTextArea.getText();
    }
    
    public void addListenerToShareButton(ActionListener actionListener) {
	shareButon.addActionListener(actionListener);
    }

}
