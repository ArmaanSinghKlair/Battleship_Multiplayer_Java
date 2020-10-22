package com.battleship.utilities;

import java.beans.*;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.battleship.client_side.*;
/**
 * Creates the opponent Map
 * @author Armaan Singh Klair
 *
 */
public class OpponentMapHandler extends Map implements PropertyChangeListener{
	/**
	 * Private Data Fields
	 */
	private char shipSelected;
	// Needed for private Classes
	private OpponentMapHandler thisObject;
	private ClientGUI cgui;
	
	public OpponentMapHandler(int size, int height, ClientGUI cgui) {
		super(size, height);
		this.shipSelected = 'X';
		this.thisObject = this;
		this.cgui = cgui;
		this.initializeMap();
		super.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1),"Opponent Area"));

	}
	/**
	 * This method initializes player map
	 */
	public void initializeMap() {
		this.initializeGrid();
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
	
	/**
	 * Updates grid depending upon state
	 */
	public void updateGrid() {
		for(int i=0;i<super.getGridSize();i++) {
			
			for(int j=0;j<super.getGridSize();j++) {
				JButton b = super.getMapCells()[i][j];
				b.setIcon(super.getIconFromState(super.getShips()[i][j]));
				
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
		private int[] coords;
		
		public mapCellActionListener(int[] coors) {
			this.coords = coors;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!cgui.isMyTurn()) {
				JOptionPane.showMessageDialog(null, "Wait for your turn !");
			} else {
				ProgressMsg pm = new ProgressMsg("hit()",cgui.getPlayerInfo().getUsername(), new Date(), this.coords);
				try {
					cgui.getOos().writeObject(pm);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				((JButton)e.getSource()).setEnabled(false);
			}
		}
		
	}
	/**
	 * Resets the grid
	 */
	public void reset() {
		for(int i=0;i<super.getGridSize();i++) {
					
					for(int j=0;j<super.getGridSize();j++) {
						super.getMapCells()[i][j].setEnabled(true);
						super.getShips()[i][j] = '-';
					}
				}
		
		super.updateGrid();
	}
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		
	}


	
}
