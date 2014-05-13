package bdd.view.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import bdd.model.Stream;

public class StreamRenderer implements ListCellRenderer<Stream> {
    
    private static final int maxLength = 30;

    @Override
    public Component getListCellRendererComponent(JList<? extends Stream> list,
	    Stream value, int index, boolean isSelected, boolean cellHasFocus) {

	JPanel panel = new JPanel();

	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

	String name = value.getName();
	if (name.length() >= maxLength) {
	    name = name.substring(0, maxLength) + "...";
	}
	JLabel streamTitle = new JLabel(name);
	streamTitle.setFont(new Font(streamTitle.getName(), Font.BOLD, 20));
	panel.add(streamTitle);

	String description = value.getDescription();
	if (description.length() >= maxLength) {
	    description = description.substring(0, maxLength) + "...";
	}
	JLabel streamDescription = new JLabel(description);
	streamDescription.setForeground(Color.gray);
	panel.add(streamDescription);

	String url = value.getUrl();
	if (url.length() >= maxLength) {
	    url = url.substring(0, maxLength) + "...";
	}
	JLabel streamUrl = new JLabel(url);
	streamUrl.setForeground(Color.blue);
	panel.add(streamUrl);

	if (isSelected) {
	    panel.setBackground(Color.cyan);
	}

	return panel;
    }

}
