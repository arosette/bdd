package bdd.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddCommentView extends JDialog {

    private JTextField commentTextField;
    private JButton commentButton;
    private JButton cancelButton;

    public AddCommentView() {
	setTitle("Ajouter un commentaire");
	setBounds(100, 100, 300, 88);
	getContentPane().setLayout(
		new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

	JLabel commentLabel = new JLabel("Commment");
	getContentPane().add(commentLabel);

	commentTextField = new JTextField();
	getContentPane().add(commentTextField);
	commentTextField.setColumns(50);

	commentButton = new JButton("Commenter");
	getContentPane().add(commentButton);
	
	cancelButton = new JButton("Annuler");
	getContentPane().add(cancelButton);
    }

    public void addCommentListener(ActionListener actionListener) {
	commentButton.addActionListener(actionListener);
    }
    
    public void addCancelListener(ActionListener cancelListener) {
	cancelButton.addActionListener(cancelListener);
    }

    public String getComment() {
	return commentTextField.getText();
    }
    
}
