package bdd.view.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import bdd.model.Publication;

public class PublicationRenderer implements ListCellRenderer<Publication> {

    private static final int maxLength = 30;

    @Override
    public Component getListCellRendererComponent(
	    JList<? extends Publication> list, Publication value, int index,
	    boolean isSelected, boolean cellHasFocus) {

	JPanel panel = new JPanel();

	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	if ((index % 2) == 1) {
	    panel.setBackground(Color.white);
	}

	if (isSelected) {
	    panel.setBackground(Color.cyan);
	}

	// Lecture
	JCheckBox publicationRed = new JCheckBox("Lue");
	publicationRed.setSelected(value.isRead());
	publicationRed.setOpaque(false);
	panel.add(publicationRed);

	// Titre
	String title = value.getTitle();
	if (title.length() >= maxLength) {
	    title = title.substring(0, maxLength) + "...";
	}
	JLabel publicationTitle = new JLabel(title);
	publicationTitle.setFont(new Font(publicationTitle.getName(),
		Font.BOLD, 14));
	panel.add(publicationTitle);

	// Contenu
	String content = value.getDescription();
	if (content.length() >= maxLength) {
	    content = content.substring(0, maxLength) + "...";
	}
	JLabel publicationDescription = new JLabel(content);
	publicationDescription.setForeground(Color.gray);
	panel.add(publicationDescription);

	return panel;
    }

}
