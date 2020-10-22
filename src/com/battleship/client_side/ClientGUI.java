package com.battleship.client_side;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.battleship.client_side.*;
import com.battleship.utilities.*;
import java.io.*;

/**
 * This is the Client GUI containing the chat and game play area
 * @author Armaan Singh Klair
 * @version 10/06/2020
 *
 */
public class ClientGUI extends JFrame implements PropertyChangeListener{
	/**
	 * Private Data Fields and uid
	 */
	private static final long serialVersionUID = -7893443289091369344L;

	private JPanel chatArea;
	private JScrollPane chatRecieverPane;
	private JPanel gameArea;
	private JPanel gameAreaHeader;
	private JLabel gameAreaHeaderHeading;
	private JLabel gameAreaHeaderStatus;
	private JButton gameAreaHeaderQuit;
	private JButton gameAreaHeaderReset;
	private JPanel gameAreaBody;
	private JPanel gameAreaFooter;
	private JPanel gameAreaFooterShipsContainer;
	private JButton aircraft;
	private JButton battleship;
	private JButton cruiser;
	private JButton submarine;
	private JButton destroyer;
	private JLabel gameAreaFooterScores;
	private JTextArea chatReciever;
	private JPanel chatSender;
	private JTextField chatTF;
	private JButton chatButton;
	private JLabel message;
	private PlayerMapHandler playerMap;
	private OpponentMapHandler opponentMap;
	private char selectedShip='X';
	private char shipDirection='X';
	private ArrayList<Character> availableShips;
	private UserInformation playerInfo;
	private String hr = "_________________________________";
	private ObjectOutputStream oos;
	private ClientGUI cgui;
	private Socket s;
	private InputListener il;
	private boolean myTurn;
	private int playerScore;
	private int opponentScore;
	
	public ClientGUI() {
		this.myTurn = false;
		this.cgui = this;
		this.selectedShip= 'X';
		this.playerScore=0;
		this.opponentScore=0;
		Character[] avlShipsChar = new Character[] {'A','B','C', 'S','D'};
		this.availableShips = new ArrayList<>(Arrays.asList(avlShipsChar));
		///
		PlayerJoinFrame pjf = new PlayerJoinFrame(this);
		/// REMOVE for actual testing
		///this.createClientGui();

		
	}
	
	/**
	 * Main method
	 */
	public static void main(String[] args) {
		new ClientGUI();
		
	}
	
	/**
	 * Creates the main JPanel containing the chat and game area
	 */
	public void createClientGui() {
		this.setPreferredSize(new Dimension(1500,750));
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill= GridBagConstraints.VERTICAL;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.gridx=0;
		gbc.gridy = 0;
		gbc.ipadx = 210;
		this.createChatArea();
		this.add(chatArea,gbc);
		
		gbc.fill= GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.weightx = 1;
		gbc.gridx=1;
		gbc.gridy = 0;
		gbc.ipadx = 0;
		this.createGameArea();
		this.setResizable(false);
		this.add(gameArea,gbc);
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * This creates the chat Area
	 */
	public void createChatArea() {
		chatArea = new JPanel();
		chatArea.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill= GridBagConstraints.BOTH;

		gbc.gridx=0;
		gbc.gridy = 0;
		gbc.weighty=1;
		gbc.weightx = 0;
		chatReciever = new JTextArea("");
		
		chatReciever.setLineWrap(true);
		chatReciever.setForeground(Color.white);
		chatReciever.setEditable(false);
		chatReciever.setFont(new Font("sans-serif", Font.BOLD, 14));
		chatReciever.setBackground(new Color(44,44,45));

		// Making it scrollable
		chatRecieverPane = new JScrollPane(chatReciever);
		chatRecieverPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatRecieverPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		chatArea.add(chatRecieverPane,gbc);
		
		gbc.fill= GridBagConstraints.BOTH;
		
		gbc.gridx=0;
		gbc.gridy =1;
		gbc.weighty=0;
		gbc.weightx = 1;
		
		chatSender = new JPanel();
		
		this.createChatSender();
		
		chatSender.setBackground(Color.LIGHT_GRAY);
		chatArea.add(chatSender,gbc);
		
	}
	
	/**
	 * This creates the Game Area
	 */
	public void createGameArea() {
		gameArea = new JPanel();
		gameArea.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		
		gameAreaHeader = new JPanel();
		gameAreaHeader.setLayout(new BorderLayout(40, 0));
		gameAreaHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.creategameAreaHeader();

		gameArea.add(gameAreaHeader,gbc);
		
		gbc.weighty = 1;
		gbc.gridy = 1;
		
		gameAreaBody = new JPanel();
		gameAreaBody.setBackground(Color.white);
		
		this.creategameAreaBody();
		gameArea.add(gameAreaBody,gbc);
		
		gbc.weighty = 0;
		gbc.gridy = 2;
		
		gameAreaFooter = new JPanel();
		this.createAreaFooter();
		gameArea.add(gameAreaFooter,gbc);
		
	}
	
	/**
	 * This creates the Area Footer containing the ship choosing buttons and scores
	 */
	private void createAreaFooter() {
		gameAreaFooter.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill= GridBagConstraints.BOTH;
		gbc.weightx=1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gameAreaFooterShipsContainer = new JPanel();
		gameAreaFooterShipsContainer.setLayout(new GridLayout());
		this.createShipsContainer();
		gameAreaFooter.add(gameAreaFooterShipsContainer);

		gbc.weightx = 0;
		gameAreaFooterScores = new JLabel("");
		this.updateScore();
		gameAreaFooterScores.setFont(new Font("sans-serif",Font.PLAIN, 18));
		gameAreaFooterScores.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 1),"Scores"));
		gameAreaFooter.add(gameAreaFooterScores);
		
		gameAreaFooter.setBackground(new Color(240,240,240));
	}
	
	/**
	 * Creates the buttons for selecting ships
	 */
	private void createShipsContainer() {
		int bWidth = 90;
		int bHeight = 75;
		aircraft = new JButton("Aircraft 5");
		aircraft.addActionListener(new ShipSelectListener());
		aircraft.setIcon(this.playerMap.getScaledImage("res\\aircraft.jpg", bWidth, bHeight));
		gameAreaFooterShipsContainer.add(aircraft);

		battleship = new JButton("Battleship 4");
		battleship.addActionListener(new ShipSelectListener());
		battleship.setIcon(this.playerMap.getScaledImage("res\\battleship.jpg", bWidth, bHeight));
		gameAreaFooterShipsContainer.add(battleship);
		
		cruiser = new JButton("Cruiser 3");
		cruiser.addActionListener(new ShipSelectListener());
		cruiser.setIcon(this.playerMap.getScaledImage("res\\cruiser.jpg", bWidth, bHeight));
		gameAreaFooterShipsContainer.add(cruiser);
		
		submarine = new JButton("Submarine 3");
		submarine.addActionListener(new ShipSelectListener());
		submarine.setIcon(this.playerMap.getScaledImage("res\\submarine.jpg", bWidth, bHeight));
		gameAreaFooterShipsContainer.add(submarine);
		
		destroyer = new JButton("Destroyer 2");
		destroyer.addActionListener(new ShipSelectListener());
		destroyer.setIcon(this.playerMap.getScaledImage("res\\destroyer.jpg", bWidth, bHeight));
		gameAreaFooterShipsContainer.add(destroyer);
		
	}
	
	/**
	 * Creates header for game including the instructions label and the quit, reset buttons
	 */
	private void creategameAreaHeader() {
		
		///
		gameAreaHeaderHeading = new JLabel(this.playerInfo.getUsername());
		
		//gameAreaHeaderHeading = new JLabel("Test_Username");
		
		gameAreaHeaderHeading.setFont(new Font("sans-serif", Font.BOLD, 25));
		gameAreaHeader.add(gameAreaHeaderHeading, BorderLayout.WEST);
		
		Border status = BorderFactory.createTitledBorder((Border)BorderFactory.createLineBorder(Color.black, 1),"Game Status Here");
		gameAreaHeaderStatus = new JLabel();
		this.setGameStatus("Place All Ships Once & Wait for Opponent...");
		gameAreaHeaderStatus.setHorizontalAlignment(SwingConstants.CENTER);
		gameAreaHeaderStatus.setFont(new Font("sans-serif", Font.PLAIN, 17));
		gameAreaHeaderStatus.setBorder(status);
		
		gameAreaHeader.add(gameAreaHeaderStatus, BorderLayout.CENTER);
		
		
		JPanel helperPanel = new JPanel();
		helperPanel.setLayout(new GridLayout(2,1));
		
		gameAreaHeaderQuit = new JButton("QUIT");
		gameAreaHeaderQuit.setBackground(new Color(	44,44,45));
		gameAreaHeaderQuit.setForeground(Color.white);
		gameAreaHeaderQuit.setFont(new Font("sans-serif",Font.BOLD, 16));
		gameAreaHeaderQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				cgui.disconnect(true);
			}
			
		});
		
		gameAreaHeaderReset = new JButton("RESET");
		gameAreaHeaderReset.setBackground(new Color(44,44,45));
		gameAreaHeaderReset.setForeground(Color.white);
		gameAreaHeaderReset.setFont(new Font("sans-serif",Font.BOLD, 16));
		
		
		helperPanel.add(gameAreaHeaderQuit);
		helperPanel.add(gameAreaHeaderReset);
		
		gameAreaHeader.add(helperPanel, BorderLayout.EAST);
		
		
		//Setting the color of GameArea Header
		gameAreaHeader.setBackground(new Color(	38, 172, 255));
		
	}

	/**
	 * Creates the containers for the player and opponent maps
	 */
	private void creategameAreaBody() {
		gameAreaBody.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx=1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.playerMap = new PlayerMapHandler(10,400, this.cgui, this.gameAreaHeaderReset);
	
		JPanel playerMapContainer = new JPanel();
		playerMapContainer.setBackground(Color.white);
		playerMapContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
		playerMapContainer.add(playerMap);
		
		gameAreaBody.add(playerMapContainer,gbc);
		
		gbc.gridx = 1;
		
		this.opponentMap = new OpponentMapHandler(10, 400, this.cgui);
		JPanel opponentMapContainer = new JPanel();
		opponentMapContainer.setBackground(Color.white);
		opponentMapContainer.setLayout(new FlowLayout());
		opponentMapContainer.add(opponentMap);
		
		gameAreaBody.add(opponentMapContainer,gbc);
	}

	
		
		
	
	/**
	 * Creates the chat Sender including the textfield and send button
	 */
	public void createChatSender() {
		chatTF = new JTextField(15);
		chatTF.setFont(new Font("sansserif", Font.PLAIN,18));
		chatButton = new JButton();
		
		//Scaling the image
		ImageIcon chatSendImageIcon = new ImageIcon("res\\sendButtonImage.png");
		Image chatSendImage = chatSendImageIcon.getImage();
		chatSendImage = chatSendImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		chatSendImageIcon = new ImageIcon(chatSendImage);
		
		chatButton.setIcon(chatSendImageIcon);
		chatButton.setOpaque(true);
		chatButton.setBackground(new Color(	38, 172, 255));
		chatButton.addActionListener(new chatSendActionListener());
		chatSender.add(chatTF);
		chatSender.add(chatButton);
	}
	
	
	/**
	 * The functions listens for changes in other object and correspondingly responds to them
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		// For Initial Login of player

		if(evt.getPropertyName().compareTo("PlayerInfo") == 0) {
			
			///
			this.playerInfo =( (UserInformation) evt.getNewValue());
			///
			this.createClientGui();
			///
			this.chatReciever.append("\n"+this.hr+ "\n>> Hello "+ this.playerInfo.getUsername()+" !! \nMessages here... \n"+hr);
			///
			this.connect();
			 
			this.setVisible(true);
		
		} 
			
		// For Chat Messages
		else if(evt.getPropertyName().compareTo("chatMessage")==0) {
			
			Message m = (Message) evt.getNewValue();
			
			if(m.getMsg().compareTo("disconnecting...")==0) {
				
				int shouldClose = JOptionPane.showConfirmDialog(null, "Opponent has left the game.\nDo you want to quit?","Batteship", JOptionPane.YES_NO_OPTION);
				
				try {
					this.s.close();
					this.oos.close();
					this.il = null;
					
					if(shouldClose==0)
						this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
					else
						this.connect();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				

			} else {
				this.chatReciever.append("\n"+this.hr+ "\n"+m.getSender()+"> "+m.getMsg()+ "\n"+ m.getTimestamp());
			}
		} 
		
		// For Game Messages - ie. if a ship has been, if player has won/lost etc.
		
		else if(evt.getPropertyName().compareTo("progressMessage")==0) {
			ProgressMsg pm = (ProgressMsg) evt.getNewValue();
			String msg = pm.getMsg();
			char[][] shipState;
			
			if(msg.compareTo("turn()")==0){
				this.gameAreaHeaderStatus.setText(this.gameAreaHeaderStatus.getText()+"  |  ITS YOUR TURN NOW !");
				this.setMyTurn(true);
				
			} else if(msg.compareTo("noTurn()")==0) {
				this.gameAreaHeaderStatus.setText(this.gameAreaHeaderStatus.getText()+"  |  OPPONENT's TURN !");
				this.setMyTurn(false);
			}
			
			// If Opponent HITS your ship
			else if(msg.compareTo("hit()")==0) {
				shipState = this.playerMap.getShips();
				char testShip = shipState[pm.getCoords()[0]][pm.getCoords()[1]];
				
				if(testShip == '-' ) {
					ProgressMsg result = new ProgressMsg("hitResult(miss)",cgui.getPlayerInfo().getUsername(), new Date(), pm.getCoords());
					try {
						this.oos.writeObject(result);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					shipState[pm.getCoords()[0]][pm.getCoords()[1]] = 'M';
					this.playerMap.updateGrid();
					this.gameAreaHeaderStatus.setText("Opponent Missed Your Ship !");
					this.gameAreaHeaderStatus.setText(this.gameAreaHeaderStatus.getText()+"  |  ITS YOUR TURN NOW !");
					this.setMyTurn(true);

				} else {
					boolean shipSunk=true;
					shipState[pm.getCoords()[0]][pm.getCoords()[1]] = 'H';
					this.playerMap.updateGrid();

					// Checking if Ship is Sunk
					ShipSunkOuterLoop:
					for(int i=0; i<this.playerMap.getGridSize();i++) {
						for(int j=0;j<this.playerMap.getGridSize();j++) {
							if(shipState[i][j]==testShip) {
								shipSunk = false;
								break ShipSunkOuterLoop;
							}
						}
					}
					
					if(shipSunk) {
							boolean shipsLeft=false;
							
							// Checking If any ships LEFT
							ShipsLeftOuterLoop:
							for(int i=0; i<this.playerMap.getGridSize();i++) {
								for(int j=0;j<this.playerMap.getGridSize();j++) {
									if(shipState[i][j] != '-' && shipState[i][j] != 'M' && shipState[i][j] != 'H' ) {
										shipsLeft = true;
										break ShipsLeftOuterLoop;
									}
								}
							}
							
							if(shipsLeft) {
								ProgressMsg result = new ProgressMsg("hitResult(sunk)",cgui.getPlayerInfo().getUsername(), new Date(), pm.getCoords());
								try {
									this.oos.writeObject(result);
								} catch (IOException e) {
									e.printStackTrace();
								}
								this.opponentScore += 1;
								this.updateScore();
								this.gameAreaHeaderStatus.setText("Opponent SUNK Your Ship !");
							}
							// If NO SHIPS LEFT
							else {
								ProgressMsg result = new ProgressMsg("hitResult(win)",cgui.getPlayerInfo().getUsername(), new Date(), pm.getCoords());
								try {
									this.oos.writeObject(result);
								} catch (IOException e) {
									e.printStackTrace();
								}
								this.opponentScore += 1;
								this.updateScore();
								this.gameAreaHeaderStatus.setText("Opponent WINS :(");
								this.myTurn = false;
								this.confirmDisconnect();
							}
							
					}else {
						ProgressMsg result = new ProgressMsg("hitResult(hit)",cgui.getPlayerInfo().getUsername(), new Date(), pm.getCoords());
						try {
							this.oos.writeObject(result);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						this.gameAreaHeaderStatus.setText("Opponent HIT Your Ship !");
				}
					//Finally

				}
			} 
			// IF player HIT a ship
			else if(msg.compareTo("hitResult(hit)")==0) {
				shipState = this.opponentMap.getShips();
				shipState[pm.getCoords()[0]][pm.getCoords()[1]] = 'H';
				this.gameAreaHeaderStatus.setText("You HIT Opponent's Ship !");
				this.opponentMap.updateGrid();
			} 
			// IF player SINKS a ship
			else if(msg.compareTo("hitResult(sunk)")==0) {
				shipState = this.opponentMap.getShips();
				shipState[pm.getCoords()[0]][pm.getCoords()[1]] = 'H';
				this.gameAreaHeaderStatus.setText("You SUNK Opponent's Ship !");
				this.playerScore += 1;
				this.updateScore();
				this.opponentMap.updateGrid();
			} // IF player wins
			else if(msg.compareTo("hitResult(win)")==0) {
				shipState = this.opponentMap.getShips();
				shipState[pm.getCoords()[0]][pm.getCoords()[1]] = 'H';
				this.gameAreaHeaderStatus.setText("You WIN :):) !!!");
				this.playerScore += 1;
				this.updateScore();
				this.opponentMap.updateGrid();
				this.myTurn = true;
				this.confirmDisconnect();
			} 
			// IF player misses
			else if(msg.compareTo("hitResult(miss)")==0) {
				shipState = this.opponentMap.getShips();
				shipState[pm.getCoords()[0]][pm.getCoords()[1]] = 'M';
				this.gameAreaHeaderStatus.setText("You Missed!");
				this.gameAreaHeaderStatus.setText(this.gameAreaHeaderStatus.getText()+"  |  OPPONENT's TURN !");
				this.setMyTurn(false);
				this.opponentMap.updateGrid();
				
			} else if(msg.compareTo("rematch()")==0) {
				this.gameAreaHeaderReset.doClick();
				this.opponentMap.reset();
				this.getGameAreaHeaderReset().setEnabled(true);
				this.gameAreaHeaderStatus.setText("Reset board and Rematch! Let's Gooo!");
				
			} else if(msg.compareTo("noRematch()")==0) {
				this.gameAreaHeaderReset.doClick();
				this.opponentMap.reset();
				this.playerScore=0;
				this.opponentScore=0;
				this.updateScore();
				this.gameAreaHeaderStatus.setText("Opponent has left, Finding another player!!");
				this.gameAreaHeaderReset.setEnabled(true);
				// This creates
				try {
					this.s.close();
					this.oos.close();
					this.il = null;
					
					this.connect();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}

	}
	// If player wants to continue or not
	/**
	 * This methods asks for user's choice on whether he/she wants to continue playing after winning/losing the game
	 */
	private void confirmDisconnect() {
		
		int choice = JOptionPane.showConfirmDialog(null, "Do you want to quit ?", "Just Confirming...", JOptionPane.YES_NO_OPTION);
		
		while(choice == JOptionPane.CANCEL_OPTION) {
			choice = JOptionPane.showConfirmDialog(null, "Do you want to quit ?", "Just Confirming...", JOptionPane.YES_NO_OPTION);
		}
		
		try {
			
		if(choice == JOptionPane.YES_OPTION) {
			
			ProgressMsg confirm = new ProgressMsg("noRematch()",this.playerInfo.getUsername(), new Date());
			this.oos.writeObject(confirm);
			
			//Closes the connection
			this.s.close();
			this.oos.close();
			this.il = null;
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));	
			
		} else {
			
			ProgressMsg confirm = new ProgressMsg("rematch()",this.playerInfo.getUsername(), new Date());
			this.oos.writeObject(confirm);
			
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This sets the game status in the header
	 * @param status The current Status of game
	 */
	private void setGameStatus(String status) {
		this.gameAreaHeaderStatus.setText(status);
	}
	
	/**
	 * This method connects the player to the server
	 */
	private void connect() {
		try {
			//Socket s = new Socket(this.playerInfo.getServer(), this.playerInfo.getPort());
			s = new Socket("localhost",5555);
			this.oos = new ObjectOutputStream(s.getOutputStream());
			this.chatReciever.append("\n Connected, Waiting for opponent...");
			il = new InputListener(s, this);
			Thread t = new Thread(il);
			t.start();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * This method closes all connections and disconnects the player
	 * @param shouldClose Determines whether the JFrame should be closed
	 */
	private void disconnect(boolean shouldClose) {
		try {
			Message m = new Message("disconnecting...",this.playerInfo.getUsername(), new java.util.Date());
			oos.writeObject(m);
			
			this.s.close();
			this.oos.close();
			this.il = null;
			
			if(shouldClose)
				this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.chatReciever.append("You are now Disconnected!!");
	}

	// Private Classes
	/**
	 * Helps in sending chat messages
	 * @author Armaan Singh Klair
	 */
	private class chatSendActionListener implements ActionListener{

		/**
		 * Overriden methods specifies what to do when chat send button clicked
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(cgui.chatTF.getText().trim().equals("")){
				JOptionPane.showMessageDialog(null, "Please Enter a message!");
			} else {
				Message m = new Message(cgui.chatTF.getText(), cgui.playerInfo.getUsername(), new java.util.Date());
				cgui.chatReciever.append("\n"+cgui.hr + "\nYou:: "+ cgui.chatTF.getText());
				cgui.chatTF.setText("");

				try {
					oos.writeObject(m);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
			
		}
		
	}
	
	/**
	 * This class helps in responding to ship button clicks
	 * @author Armaan Singh Klair
	 *
	 */
	private class ShipSelectListener implements ActionListener{

		/**
		 * Specifies what to do when ship selected
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			char selectedShip='X';
			if(e.getSource() == cgui.aircraft) {
				selectedShip = 'A';
			} else if(e.getSource() == cgui.battleship) {
				selectedShip = 'B';
			} else if(e.getSource() == cgui.cruiser) {
				selectedShip = 'C';
			} else if(e.getSource() == cgui.submarine) {
				selectedShip = 'S';
			} else if(e.getSource() == cgui.destroyer) {
				selectedShip = 'D';
			}
			
			if((! cgui.availableShips.contains((Character) selectedShip) ) && selectedShip != 'X') {
				JOptionPane.showMessageDialog(null, "You have already chosen that ship!!!");
			} else {
				cgui.selectedShip = selectedShip;
			}
		}
		
	}
	
	/**
	 * Updates the current Score
	 */
	private void updateScore() {
		this.gameAreaFooterScores.setText("You: "+this.playerScore+"  |  Opponent: "+this.opponentScore);
	}
	
	//Getters & Setters

	public char getSelectedShip() {
		return selectedShip;
	}

	public void setSelectedShip(char selectedShip) {
		this.selectedShip = selectedShip;
	}

	public char getShipDirection() {
		return shipDirection;
	}

	public void setShipDirection(char shipDirection) {
		this.shipDirection = shipDirection;
	}

	public ArrayList<Character> getAvailableShips() {
		return availableShips;
	}

	public void setAvailableShips(ArrayList<Character> availableShips) {
		this.availableShips = availableShips;
	}

	public JButton getGameAreaHeaderReset() {
		return this.gameAreaHeaderReset;
	}

	public void setGameAreaHeaderReset(JButton gameAreaHeaderReset) {
		this.gameAreaHeaderReset = gameAreaHeaderReset;
	}

	public JTextArea getChatReciever() {
		return chatReciever;
	}

	public void setChatReciever(JTextArea chatReciever) {
		this.chatReciever = chatReciever;
	}

	public UserInformation getPlayerInfo() {
		return playerInfo;
	}

	public void setPlayerInfo(UserInformation playerInfo) {
		this.playerInfo = playerInfo;
	}

	public ObjectOutputStream getOos() {
		return oos;
	}

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

	public boolean isMyTurn() {
		return myTurn;
	}

	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}

	public JLabel getGameAreaHeaderStatus() {
		return gameAreaHeaderStatus;
	}

	public void setGameAreaHeaderStatus(JLabel gameAreaHeaderStatus) {
		this.gameAreaHeaderStatus = gameAreaHeaderStatus;
	}

	public String getHr() {
		return hr;
	}

	public void setHr(String hr) {
		this.hr = hr;
	}
	
	
	
	
	
	
}
