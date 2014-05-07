package bdd.view.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import bdd.model.Publication;

public class PublicationRenderer implements ListCellRenderer<Publication> {

    @Override
    public Component getListCellRendererComponent(
	    JList<? extends Publication> list, Publication value, int index,
	    boolean isSelected, boolean cellHasFocus) {

	JPanel panel = new JPanel();

	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

	JLabel streamTitle = new JLabel(value.getTitle());
	streamTitle.setFont(new Font(streamTitle.getName(), Font.BOLD, 20));
	panel.add(streamTitle);

	JLabel streamDescription = new JLabel(value.getDescription());
	streamDescription.setForeground(Color.gray);
	panel.add(streamDescription);

	return panel;
    }

}
