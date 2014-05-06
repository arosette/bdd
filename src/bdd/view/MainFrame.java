package bdd.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import bdd.model.Stream;

public class MainFrame extends JFrame {

    private JPanel mainPanel;

    public MainFrame() {
	this.setTitle("Flux RSS");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setMinimumSize(new Dimension(800, 600));
	this.setResizable(true);
	this.build();
    }

    public void build() {
	this.mainPanel = new JPanel();
	this.mainPanel.setLayout(new GridBagLayout());

	GridBagConstraints gbc = new GridBagConstraints();

	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.gridheight = 1;
	gbc.gridwidth = 1;

	DefaultListModel<Stream> streamListModel = new DefaultListModel<Stream>();
	Stream testStream = new Stream();
	testStream.setName("Test");
	streamListModel.addElement(testStream);

	JList<Stream> streamList = new JList<Stream>(streamListModel);
	mainPanel.add(new JScrollPane(streamList));

	this.setContentPane(this.mainPanel);
	this.setVisible(true);
    }

}
