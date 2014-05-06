package bdd.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import bdd.model.Stream;

public class StreamRenderer extends JPanel implements ListCellRenderer<Stream> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Stream> list,
	    Stream value, int index, boolean isSelected, boolean cellHasFocus) {
	
	this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	
	
	JLabel streamTitle = new JLabel(value.getName());
	streamTitle.setFont(new Font(streamTitle.getName(), Font.BOLD, 20));
	this.add(streamTitle);
	
	JLabel streamDescription = new JLabel(value.getDescription());
	streamDescription.setForeground(Color.gray);
	this.add(streamDescription);
	
	return this;
    }
    
}