package com.battleship.client_side;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;

import com.battleship.utilities.*;

/**
 * This class creates login form
 * @author Armaan Singh Klair
 *
 */
public class PlayerJoinFrame extends JFrame{
	
	/**
	 * Private Data Fields
	 */
	private static final long serialVersionUID = 3698056107776642632L;
	private java.util.List<PropertyChangeListener> 	listeners = new ArrayList<>();
	private PropertyChangeListener pcl;
	private PlayerJoinFrame pjf;
	private JPanel bannerPanel;
	private JLabel bannerLabel;
	
	private JPanel userInfoPanel;
	private JLabel userInfoHeading;
	private JLabel userInfoServer;
	private JLabel userInfoPort;
	private JLabel userInfoUsername;
	private JTextField userInfoServerTF;
	private JTextField userInfoPortTF;
	private JTextField userInfoUsernameTF;
	
	private JButton userInfoSubmit;
	
	/**
	 * Constructor
	 * @param pcl The Object that listens to this class
	 */
	public PlayerJoinFrame(PropertyChangeListener pcl) {
		
		this.listeners.add(pcl);
		this.pjf = this;
		this.pcl = pcl;
		
		this.setTitle("Play Battleship!");
		this.setPreferredSize(new Dimension(900,750));
		this.setLayout(new GridLayout(1,2));
		
		this.createBannerPanel();
		this.add(bannerPanel);
		
		this.createPlayerJoinPanel();
		this.add(userInfoPanel);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * Creates Banner
	 */
	public void createBannerPanel() {
		bannerPanel = new JPanel();
		bannerPanel.setLayout(new BorderLayout());
		
		bannerLabel = new JLabel();
		
		// Gets ImageIcon --> Resizes as Image  --> Goes back to ImageIcon
		ImageIcon battleImageIcon = new ImageIcon("res\\banner.png");
		
		Image battleImage = battleImageIcon.getImage();
		battleImage = battleImage.getScaledInstance(500,600, java.awt.Image.SCALE_SMOOTH);
		battleImageIcon = new ImageIcon(battleImage);
		
		bannerLabel.setIcon(battleImageIcon);
		
		
		bannerLabel.setOpaque(true);
		bannerLabel.setBackground(new Color(44,44,45));
		bannerPanel.add(bannerLabel);
		
		
	}
	
	/**
	 * Creates the Player Info Panel
	 */
	public void createPlayerJoinPanel() {
		userInfoPanel = new JPanel();
		userInfoPanel.setBackground(new Color(44,44,45));
		userInfoPanel.setLayout(new BorderLayout());
		

		userInfoPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		
		userInfoHeading = new JLabel("Tell us what Username you want.", SwingConstants.CENTER);
		userInfoHeading.setForeground(Color.white);
		userInfoHeading.setFont(new Font("sans-serif",Font.BOLD, 22));
		userInfoPanel.add(userInfoHeading, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 50, 10, 50);
		gbc.gridwidth = 2;
		
		userInfoUsername = new JLabel("Username", SwingConstants.LEFT);
		userInfoUsername.setForeground(Color.LIGHT_GRAY);
		userInfoUsername.setFont(new Font("sans-serif",Font.BOLD, 16));
		userInfoPanel.add(userInfoUsername, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.ipady = 10;
		gbc.insets = new Insets(10, 50, 10, 50);

		gbc.gridwidth = 2;
		
		userInfoUsernameTF = new JTextField();
		userInfoUsernameTF.setBorder(null);
		userInfoUsernameTF.setForeground(Color.DARK_GRAY);
		userInfoUsernameTF.setFont(new Font("sans-serif",Font.BOLD, 16));
		userInfoPanel.add(userInfoUsernameTF, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.ipady = 0;
		gbc.insets = new Insets(10, 50, 10, 50);
		gbc.gridwidth = 2;
		
		userInfoServer = new JLabel("Server", SwingConstants.LEFT);
		userInfoServer.setForeground(Color.LIGHT_GRAY);
		userInfoServer.setFont(new Font("sans-serif",Font.BOLD, 16));
		userInfoPanel.add(userInfoServer, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.ipady = 10;
		gbc.insets = new Insets(10, 50, 10, 50);

		gbc.gridwidth = 2;
		
		userInfoServerTF = new JTextField();
		userInfoServerTF.setBorder(null);
		userInfoServerTF.setForeground(Color.DARK_GRAY);
		userInfoServerTF.setFont(new Font("sans-serif",Font.BOLD, 16));
		userInfoServerTF.setText("localhost");
		userInfoServerTF.setEnabled(false);
		userInfoPanel.add(userInfoServerTF, gbc);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.ipady = 0;
		gbc.insets = new Insets(10, 50, 10, 50);
		gbc.gridwidth = 2;
		
		userInfoPort = new JLabel("Port", SwingConstants.LEFT);
		userInfoPort.setForeground(Color.LIGHT_GRAY);
		userInfoPort.setFont(new Font("sans-serif",Font.BOLD, 16));
		userInfoPanel.add(userInfoPort, gbc);
		
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.ipady = 10;
		gbc.insets = new Insets(10, 50, 10, 50);

		gbc.gridwidth = 2;
		
		userInfoPortTF = new JTextField();
		userInfoPortTF.setBorder(null);
		userInfoPortTF.setForeground(Color.DARK_GRAY);
		userInfoPortTF.setFont(new Font("sans-serif",Font.BOLD, 16));
		userInfoPortTF.setText("5555");
		userInfoPortTF.setEnabled(false);
		userInfoPanel.add(userInfoPortTF, gbc);
		
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.ipady = 20;
		gbc.insets = new Insets(10, 50, 10, 50);

		gbc.gridwidth = 2;
		userInfoSubmit = new JButton("Login & Play");
		userInfoSubmit.setOpaque(true);
		userInfoSubmit.setBackground(new Color(	38, 172, 255));
		userInfoSubmit.setForeground(Color.white);
		userInfoSubmit.setFont(new Font("sans-serif",Font.BOLD, 16));
		userInfoSubmit.addActionListener( new SubmitListener());
		
		userInfoPanel.add(userInfoSubmit,gbc);
		
	}
	
	/**
	 * This method notifies its listeners that its properties have changed
	 */
	public void notifyListeners() {
		for(PropertyChangeListener pcl: this.listeners) {
			pcl.propertyChange(new PropertyChangeEvent(this, "PlayerInfo", null, new UserInformation(userInfoServerTF.getText().trim(), Integer.parseInt(userInfoPortTF.getText().trim()), userInfoUsernameTF.getText().trim() )));
		}
	}
	
	
	/**
	 * This handles click of submit button
	 * @author Armaan Singh Klair
	 *
	 */
	private class SubmitListener implements ActionListener{


		private static final long serialVersionUID = 596469722003310143L;
		
		/**
		 * Specifies what to do on click of submit button
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(CommonMethods.isEmpty(userInfoServerTF) || CommonMethods.isEmpty(userInfoPortTF) || CommonMethods.isEmpty(userInfoUsernameTF) || !userInfoPortTF.getText().trim().matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null, "PLEASE ENTER ALL FIELDS CORRECTLY.");
			} else {
				pjf.notifyListeners();
				pjf.dispatchEvent(new WindowEvent(pjf, WindowEvent.WINDOW_CLOSING));
			}
		}
		
	}
	
}
