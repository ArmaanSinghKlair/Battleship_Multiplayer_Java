package com.battleship.utilities;

import java.beans.*;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.battleship.client_side.*;
/**
 * This creates player map
 * @author Armaan Singh Klair
 *
 */
public class PlayerMapHandler extends Map {
	
	/**
	 * Private Data Fields
	 */
	private static final long serialVersionUID = 3055642626124910392L;
	private char shipSelected;
	
	// Needed for private Classes
	private PlayerMapHandler thisObject;
	private ClientGUI cgui;
	private JButton resetButton;
	
	public PlayerMapHandler(int size, int height, ClientGUI cgui,JButton reset) {
		super(size, height);
		this.shipSelected = 'X';
		this.thisObject = this;
		this.cgui = cgui;
		thisObject.resetButton = reset;
		this.initializeMap();
		super.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1),"Player Area"));

	}
	
	/**
	 * This method initializes player map
	 */
	public void initializeMap() {
		this.initializeGrid();
		this.resetButton.addActionListener(new ResetActionListener());
	}
	
	/**
	 * Sets up the actual button
	 */
	public void initializeGrid() {
		for(int i=0;i<super.getGridSize();i++) {
					
					for(int j=0;j<super.getGridSize();j++) {
						JButton b = super.getMapCells()[i][j];
						b.setIcon(super.getIconFromState(super.getShips()[i][j]));
						
						b.addMouseListener(new MouseAdapter() {
							//Adds new image on hover
							public void mouseEntered(MouseEvent e) {
								((JButton)e.getSource()).setBorder(BorderFactory.createLineBorder(Color.white, 3));
							}
							public void mouseExited(MouseEvent e) {
								((JButton)e.getSource()).setBorder(null);					}
								
						});
						b.addActionListener(new mapCellActionListener(new int[] {i,j}));
						super.add(b);
						
					}
				}
	}
	
	
	
	// Private Classes
	
	/**
	 * This class handles the click on any ship
	 * @author Armaan Singh Klair
	 *
	 */
	private class mapCellActionListener implements ActionListener{
		private int[] coord;
		
		/**
		 * Constructors
		 * @param coors The coordinates of the specific ship
		 */
		public mapCellActionListener(int[] coors) {
			this.coord = coors;
		}
		
		/**
		 * This class handles the click on any ship
		 * @author Armaan Singh Klair
		 *
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if(cgui.getSelectedShip() == 'X') {
				JOptionPane.showMessageDialog(null, "Please SELECT a ship first!");
				return;
			} else if(! cgui.getAvailableShips().contains(cgui.getSelectedShip())){
				JOptionPane.showMessageDialog(null, "You have already selected that Ship!!");
				return;
			}else{
				int shipSize=0;
				char direction;
				
				int userDirectionChoice = JOptionPane.showConfirmDialog(null, "YES = Place Vertically | NO = Place Horizontally","Choose Direction", JOptionPane.YES_NO_OPTION);
				
				if(userDirectionChoice == 1) {
					direction = 'V';
				} else {
					direction = 'H';
				}
				
				switch(cgui.getSelectedShip()){
				case 'A':
					shipSize = 5;
					break;
				case 'B':
					shipSize = 4;
					break;
				case 'C':
					shipSize = 3;
					break;
				case 'S':
					shipSize = 3;
					break;
				case 'D':
					shipSize = 2;
					break;
				}
				
				if(thisObject.placeShipOnGrid(coord, shipSize, direction, cgui.getSelectedShip())) {
					thisObject.updateGrid();
					cgui.getAvailableShips().remove((Character)cgui.getSelectedShip());
					
					if(cgui.getAvailableShips().size() == 0) {
						thisObject.cgui.getGameAreaHeaderReset().setEnabled(false);
						thisObject.cgui.getChatReciever().append("\n"+thisObject.cgui.getHr());
						thisObject.cgui.getChatReciever().append("\nGame Board Set! Waiting for Opponent");
						thisObject.cgui.getChatReciever().append("\nYou cannot reset the board.");
						
						ProgressMsg pm = new ProgressMsg("ready()", cgui.getPlayerInfo().getUsername(), new Date());
						
						try {
							cgui.getOos().writeObject(pm);
							
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					return;
				} else {
					JOptionPane.showMessageDialog(null, "Ships Intersecting OR Ship Placed Outside Map\n Please try again!");
				}
				
				
			}
		}
		
	}

	/**
	 * This class resets the board
	 * @author Armaan Singh Klair
	 *
	 */
	private class ResetActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i=0;i< thisObject.getGridSize();i++) {
				for(int j=0;j <thisObject.getGridSize();j++) {
					thisObject.getShips()[i][j]='-';
				}
			}
			
			thisObject.updateGrid();
			
			Character[] avlShipsChar = new Character[] {'A','B','C', 'S','D'};
			cgui.setAvailableShips(new ArrayList<>(Arrays.asList(avlShipsChar)));
		}
		
		
		
	}
	


	
	
}
