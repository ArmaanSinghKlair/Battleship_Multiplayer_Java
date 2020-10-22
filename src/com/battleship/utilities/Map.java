package com.battleship.utilities;

import java.util.*;
import javax.swing.*;
import java.awt.*;

/**
 * This is super class of both player and opponent Maps
 * @author Armaan Singh Klair
 *
 */
public class Map extends JPanel{
	/**
	 * Private Data Fields
	 */
	
	// For Storing state
	private char[][] ships;
	private JButton mapCell;
	
	//For Storing result of state
	private JButton[][] mapCells;
	private int gridSize;
	private int gridHeight;
	private int cellHeight;
	protected ImageIcon normal;
	protected ImageIcon miss;
	protected ImageIcon hit;
	protected ImageIcon ship;
	protected ImageIcon hover;
	
	/**
	 * Constructor
	 * @param size Grid Size
	 * @param height Height of Grid
	 */
	public Map(int size, int height) {
		this.ships = new char[size][size];
		this.mapCells = new JButton[size][size];
		this.gridSize = size;
		this.gridHeight = height+34;
		this.cellHeight = (height/size)-4;
		this.normal = this.getIconFromState('-');
		this.miss = this.getIconFromState('M');
		this.hit = this.getIconFromState('H');
		this.ship = this.getIconFromState('X');
		this.hover = this.getIconFromState('h');
		this.createMap();
		
		
	}
	
	/**
	 * This method actually creates Map
	 */
	public void createMap() {
		this.setPreferredSize(new Dimension(this.gridHeight,this.gridHeight));

		this.setLayout(new FlowLayout());
		
		//Setting States
		for(int j=0; j< this.gridSize; j++) {
			for(int k=0; k< this.gridSize; k++) {
				this.ships[j][k] = '-';
			}
		}
		
		
		// Setting actual buttons
		for(int j=0; j< this.gridSize; j++) {
			for(int k=0; k< this.gridSize; k++) {
				
				mapCell = new JButton();
				
				mapCell.setPreferredSize(new Dimension(this.cellHeight, this.cellHeight));
				
				mapCell.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
				
				mapCell.setOpaque(true);
				
				mapCells[j][k] = mapCell;
					
			}
		}
		
		
		
		
	}
	
	/**
	 * Returns Icon to button depending upon state
	 * @param state State of position on grid
	 * @return The Image corresponding to state
	 */
	protected ImageIcon getIconFromState(char state) {
		String path;
		if(state == '-') {
			path = "res\\ocean.jpg";
		} else if(state == 'M') {
			path = "res\\miss.jpg";
		} else if(state == 'H') {
			path = "res\\hit.jpg";
		}  else {
			path = "res\\ship.png";
		}
		return this.getScaledImage(path, this.cellHeight, this.cellHeight);
			
	}
	
	public ImageIcon getScaledImage(String path, int height, int width) {
		ImageIcon ii = new ImageIcon(path);
		Image i = ii.getImage();
		i = i.getScaledInstance(height, width, Image.SCALE_SMOOTH);
		ii = new ImageIcon(i);
		return ii;
	}
	
	protected boolean placeShipOnGrid(int[] coord, int length, char direction,char selectedShip) {
		
		if(!checkShip(coord ,length, direction)) {
			return false;
		} else {
		
			int[] directionCoord;
			
			if(direction == 'V') {
				directionCoord = new int[] {0,1};
			} else {
				directionCoord = new int[] {1,0};
			}
			
			for(int i=0; i<length; i++) {
				this.ships[coord[0]+(i*directionCoord[0])][coord[1]+(i*directionCoord[1])] = selectedShip;		
			}
			return true;
		}
		
		
		
	}
	
	/**
	 * Checks if ship is present at desired position
	 * @param coord The desired coordinates to place ships
	 * @param length The length of ship to be placed
	 * @param direction The direction of ships to be placed
	 * @return true/false depending upon whether ship is there or not
	 */
	protected boolean checkShip(int[] coord, int length, char direction) {
		int[] directionCoord;
		
		if(direction == 'V') {
			directionCoord = new int[] {0,1};
		} else {
			directionCoord = new int[] {1,0};
		}
		
		for(int i=0; i<length; i++) {
			// Checks whether the coordinate is not inside grid or ship already present
			if( (!( (coord[0]+(i*directionCoord[0]) ) < this.gridSize && (coord[1]+(i*directionCoord[1])) < this.gridSize && ships[coord[0]+(i*directionCoord[0])][coord[1]+(i*directionCoord[1])] == '-' )) ) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Updates Grid based on states
	 */
	public void updateGrid() {
		for(int i=0;i<this.gridSize;i++) {
			
			for(int j=0;j<this.gridSize;j++) {
				JButton b = this.mapCells[i][j];
				if((ImageIcon)
						b.getIcon() != this.getIconFromState(this.ships[i][j])) {
					b.setIcon(this.getIconFromState(this.ships[i][j]));
				}
				this.mapCells[i][j] =b;
				
			}
		}
	}
	public int getGridSize() {
		return gridSize;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

	public int getCellHeight() {
		return cellHeight;
	}

	public void setCellHeight(int cellHeight) {
		this.cellHeight = cellHeight;
	}
	
	

	public JButton[][] getMapCells() {
		return mapCells;
	}

	public void setMapCells(JButton[][] mapCells) {
		this.mapCells = mapCells;
	}

	
	public char[][] getShips() {
		return ships;
	}

	public void setShips(char[][] ships) {
		this.ships = ships;
	}

	
	@Override
	public String toString() {
		return "Map [mapCell=" + mapCell + ", gridSize=" + gridSize + ", gridHeight=" + gridHeight + ", cellHeight="
				+ cellHeight + "]";
	}
	
	
	
}
