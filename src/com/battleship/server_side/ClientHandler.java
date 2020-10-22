package com.battleship.server_side;
import java.net.*;
import java.beans.*;
import java.io.*;
import java.util.*;


import com.battleship.utilities.*;

/**
 * Creates a separate thread for two players to start a game
 * @author Armaan Singh Klair
 *
 */
public class ClientHandler extends Thread implements PropertyChangeListener{
	/**
	 * Private Data Fields
	 */
	private Socket s1,s2;
	private ObjectOutputStream oos1, oos2;
	private InputListener il1, il2;
	private int readyCount;
	
	/**
	 * Constructors
	 * @param s1 Sockets corresponding to player1
	 * @param s2 Sockets corresponding to player2
	 */
	public ClientHandler(Socket s1, Socket s2) {
		this.s1 = s1;
		this.s2 =s2;
		this.il1 = new InputListener(s1, this, "0");
		this.il2 = new InputListener(s2, this, "1");
		this.readyCount = 0;
		
		try {
			this.oos1 = new ObjectOutputStream(s1.getOutputStream());
			this.oos2 = new ObjectOutputStream(s2.getOutputStream());
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method runs when Thread is started
	 */
	public void run() {
		
		try {
			Thread t1 = new Thread(this.il1);
			t1.start();
			Thread t2 = new Thread(this.il2);
			t2.start();

			Message initialMsg = new Message("Opponent Found, You can start!!",">",new Date());
			
			oos1.writeObject(initialMsg);
			oos2.writeObject(initialMsg);
			
			while(s1.isConnected() && s2.isConnected());
			
			
			
			oos1.close();
			oos2.close();
			s1.close();
			s2.close();
		
			
		} catch(SocketException e) {
			System.out.println("Error Creating Socket");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Listens to other objects for any changes and responds correspondingly 
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String sender = ((InputListener) evt.getSource()).getListenerIdentifier();
		String propName = evt.getPropertyName();
		String msg="";
		if(propName.equals("progressMessage")) {
			msg = ((ProgressMsg)evt.getNewValue()).getMsg();
		}
		try {
			if(sender.compareTo("0") == 0) {
				oos2.writeObject(evt.getNewValue());
			} else {
				oos1.writeObject(evt.getNewValue());
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// Checks to see if both clients are ready
			if(propName.compareTo("progressMessage")==0) {
				ProgressMsg pmInitial1 = new ProgressMsg("turn()",">",new Date());
				ProgressMsg pmInitial2 = new ProgressMsg("noTurn()",">",new Date());
				
				if(msg.compareTo("ready()")==0) {
					this.readyCount++;
					
					if(this.readyCount == 2) {
						

						try {
							oos1.writeObject(pmInitial1);
							oos2.writeObject(pmInitial2);

						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} 
		
		
			}
		
				
		
		
		
	}
}
