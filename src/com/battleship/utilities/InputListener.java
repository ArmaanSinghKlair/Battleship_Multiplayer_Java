package com.battleship.utilities;

import java.io.*;
import java.net.*;
import java.beans.*;
import java.util.*;

import javax.swing.JOptionPane;

import com.battleship.utilities.*;
/**
 * This class listens for others classes on their behalf
 * @author Armaan Singh Klair
 *
 */
public class InputListener implements Runnable{
	/**
	 * Private Data Fields
	 */
	private Socket socket;
	private ObjectInputStream ois;
	private String listenerIdentifier;
	
	private java.util.List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();
	
	/**
	 * Constructor
	 * @param s Socket to listen to
	 * @param pcl The Object that listens to this class
	 */
	public InputListener(Socket s, PropertyChangeListener pcl) {
		this.socket = s;
		this.listenerIdentifier = "Player";
		this.listeners.add(pcl);
	}
	
	/**
	 * Constructor
	 * @param s Socket to listen to
	 * @param pcl The Object that listens to this class
	 * @param listenerIdentifier The identifies the socket that this class listens to
	 */
	
	public InputListener(Socket s, PropertyChangeListener pcl, String listenerIdentifier) {
		this.socket = s;
		this.listenerIdentifier = listenerIdentifier;
		this.listeners.add(pcl);
	}
	
	/**
	 * This method is called when thread is start
	 */
	public void run() {
		try {
			ois = new ObjectInputStream(socket.getInputStream());

		while(true) {
			
			

				Object o = ois.readObject();
				notifyListeners(o);
				// Following section is mostly for ClientHandler who cannot close itself
				if( o instanceof Message) {
					Message m = (Message) o;
					
					// It stops listening because ClientHandler has already closed all output streams
					
					if(m.getMsg().trim().compareTo("disconnecting...") == 0) {
						this.socket.close();
						this.ois.close();
						break;

					}
				}
				
		}	
		} catch(SocketException e) {
				
				System.out.println("Error: From "+ this.listenerIdentifier+" :: Socket Closed from Server");
				
			} catch(EOFException e) {
				
				System.out.println("Error: From "+ this.listenerIdentifier+" :: Cannot Find Stream from Server");
				
			}
			
			catch (IOException e) {
				e.printStackTrace();
			}
			catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		
	}
	
	/**
	 * This method adds an observer for the class
	 * @param pcl The Object that listens to this class
	 */
	public void addObserver(PropertyChangeListener pcl) {
		this.listeners.add(pcl);
	}
	
	/**
	 * Notifies listeners that some property has changed
	 * @param o The changed value
	 */
	public void notifyListeners(Object o) {
		String propertyName="";
		if(o instanceof Message) {
			propertyName = "chatMessage";
		}
		
		if(o instanceof ProgressMsg) {
			propertyName= "progressMessage";
		}
		
		for(PropertyChangeListener pcl: this.listeners) {
			pcl.propertyChange(new PropertyChangeEvent(this, propertyName, null, o));
		}
	}

	//Getters and setters
	public String getListenerIdentifier() {
		return listenerIdentifier;
	}

	public void setListenerIdentifier(String listenerIdentifier) {
		this.listenerIdentifier = listenerIdentifier;
	}
	
	
}
