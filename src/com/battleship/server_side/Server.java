package com.battleship.server_side;

import java.beans.*;
import java.io.IOException;
import java.net.*;
import java.util.*;
import com.battleship.gui.*;

/**
 * This is implementation of server. It listens for players connecting to it and the pairs them up using ClientHandler
 * @author Armaan Singh Klair
 *
 */

public class Server {
	/**
	 * Private Data Fields
	 */
	private int port = 5555;
	private List<PropertyChangeListener> listeners =  new ArrayList();
	private int totalGamesCreated;
	
	/**
	 * Constructur no-arg
	 */
	public Server() {
		this.totalGamesCreated = 0;
		ServerGUI sgui = new ServerGUI();
		this.listeners.add(sgui);
		this.connect();
	}
	
	/**
	 * Starts the server and start listening and pairing
	 */
	public void connect() {
		List<Socket> sockets = new ArrayList<>();
		ServerSocket ss = null;
		Socket s = null;
		
		try {
			ss = new ServerSocket(5555);
			System.out.println("SERVER CONNECTED");
			String msg = "\n"+new Date()+"\nStarting Server at port "+this.port;
			this.notifyListeners(msg);
			while(true) {
				
				s = ss.accept();
				
				sockets.add(s);
				
				msg = "\n\n"+new Date()+"  ::  New Player Added";
				this.notifyListeners(msg);

				if(sockets.size() == 2) {
					
					msg = "\n\n"+new Date()+"  ::  Two Players Assigned new Battleship Session";
					this.notifyListeners(msg);
					
					this.totalGamesCreated += 1;
					
					msg = "\n\n"+new Date()+"  ::  Total Games Created Count :: "+ this.totalGamesCreated;
					this.notifyListeners(msg);
					
					ClientHandler ch = new ClientHandler(sockets.get(0), sockets.get(1));
					sockets.clear();
					ch.start();
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Notifies objects listening to it that something has change
	 * @param o The new value of property that has changed
	 */
	private void notifyListeners(Object o) {
		String propName="";
		if(o instanceof String){
			propName = "newGame" ;
		}
		
		for(PropertyChangeListener pcl: this.listeners) {
			pcl.propertyChange(new PropertyChangeEvent(this, propName, null, o));;
		}
	}
	public static void main(String[] args) {
		
		new Server();
		
	}
}
;
