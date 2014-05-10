package bdd.view.renderer;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import bdd.model.Comment;

public class CommentRenderer implements ListCellRenderer<Comment> {

    @Override
    public Component getListCellRendererComponent(
	    JList<? extends Comment> list, Comment value, int index,
	    boolean isSelected, boolean cellHasFocus) {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	
	JLabel authorLabel = new JLabel(value.getUserMail());
	panel.add(authorLabel);
	
	JLabel contentLabel = new JLabel(value.getContent());
	panel.add(contentLabel);
	
	JLabel dateLabel = new JLabel(value.getDate());
	panel.add(dateLabel);
	
	
	return panel;
    }

}
