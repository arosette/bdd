package bdd.view;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

import bdd.model.Stream;

public class AddCommentView extends JDialog {
    private JButton commentButton;
    private JTextArea commentTextArea;
    private JComboBox streamsComboBox;
    private JLabel streamLabel;

    public AddCommentView() {
	setTitle("Ajouter un commentaire");
	setBounds(100, 100, 596, 176);
	getContentPane().setLayout(
		new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

	JLabel commentLabel = new JLabel("Commmentaire");
	getContentPane().add(commentLabel);

	commentTextArea = new JTextArea();
	getContentPane().add(commentTextArea);

	streamLabel = new JLabel("Flux a commenter");
	getContentPane().add(streamLabel);

	streamsComboBox = new JComboBox();
	getContentPane().add(streamsComboBox);

	commentButton = new JButton("Commenter");
	getContentPane().add(commentButton);
    }

    public void addCommentListener(ActionListener actionListener) {
	commentButton.addActionListener(actionListener);
    }

    public String getComment() {
	return commentTextArea.getText();
    }
    
    public Stream getSelectedStream() {
	return (Stream) streamsComboBox.getSelectedItem();
    }

    public void loadStreams(List<Stream> streams) {
	DefaultComboBoxModel<Stream> streamComboBoxModel = new DefaultComboBoxModel<>();
	for (Stream stream : streams) {
	    streamComboBoxModel.addElement(stream);
	}
	streamsComboBox.setModel(streamComboBoxModel);
    }

}
