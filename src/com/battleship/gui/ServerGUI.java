package com.battleship.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import com.battleship.server_side.*;
/**
 * This class builds the GUI for the server
 * @author Armaan Singh Klair
 *
 */
public class ServerGUI extends JFrame implements PropertyChangeListener{
	private JPanel mainPanel;
	private JLabel heading;
	private JTextArea log;
	private Server s;
	private ServerGUI sgui;
	
	public ServerGUI() {
		this.createGui();
		this.add(mainPanel);
		this.setBackground(Color.black);
		this.setTitle("Server Status");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.sgui = this;
		
	}
	
	/**
	 * Creates the main GUI
	 */
	private void createGui() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc =new GridBagConstraints();
		mainPanel.setPreferredSize(new Dimension(800,700));
		mainPanel.setBackground(new Color(44,44,45));
		
		gbc.weightx=1;
		gbc.weighty=0;
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.fill = GridBagConstraints.BOTH;
		
		heading = new JLabel("SERVER STATUS");
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
		heading.setForeground(Color.green);
		heading.setFont(new Font("sans-serif",Font.BOLD, 30));
		
		mainPanel.add(heading,gbc);
		gbc.weighty=1;
		gbc.gridy=1;
		gbc.ipady=100;
		log = new JTextArea("Game Traffic Status here...");
		log.setBackground(Color.black);
		log.setFont(new Font("monospace",Font.PLAIN, 18));
		log.setForeground(Color.green);
		JScrollPane scroll =new JScrollPane(log);
		log.setLineWrap(true);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBorder(BorderFactory.createLineBorder(Color.black, 20));
		mainPanel.add(scroll, gbc);
		
	
	}

	/**
	 * Listens to other objects for any changes and responds correspondingly 
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("newGame")) {
			this.log.append((String)evt.getNewValue());
		}
	}
	
	
	
}

