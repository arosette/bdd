package bdd.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AddCommentView extends JDialog {

    private JTextField commentTextField;
    private JButton commentButton;
    private JButton cancelButton;

    public AddCommentView() {
	setTitle("Ajouter un commentaire");
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
