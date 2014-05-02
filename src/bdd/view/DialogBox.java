package bdd.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class DialogBox extends JDialog {

    protected final JPanel contentPanel = new JPanel();
    protected JButton okButton;

    public DialogBox(String title, String text) {
	this.build(title, text);
	this.createEvent();
    }
    
    public void build(String title, String text) {
	this.setTitle(title);
	setBounds(100, 100, 500, 150);
	getContentPane().setLayout(new BorderLayout());
	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
	getContentPane().add(contentPanel, BorderLayout.CENTER);

	JTextPane txtpnAreYouSure = new JTextPane();
	txtpnAreYouSure.setEditable(false);
	txtpnAreYouSure.setText(text);
	GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
	gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(
		GroupLayout.Alignment.LEADING).addGroup(
		gl_contentPanel
			.createSequentialGroup()
			.addContainerGap()
			.addComponent(txtpnAreYouSure,
				GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
			.addContainerGap()));
	gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(
		GroupLayout.Alignment.LEADING)
		.addGroup(
			gl_contentPanel
				.createSequentialGroup()
				.addContainerGap()
				.addComponent(txtpnAreYouSure,
					GroupLayout.DEFAULT_SIZE, 180,
					Short.MAX_VALUE)));
	contentPanel.setLayout(gl_contentPanel);
	{
	    JPanel buttonPane = new JPanel();
	    buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    getContentPane().add(buttonPane, BorderLayout.SOUTH);
	    {
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	    }
	}
    }
    
    public void createEvent() {
	okButton.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		dispose();
	    }
	});
    }
}
