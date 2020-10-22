package com.battleship.utilities;

/**
 * Stores the information of player that connects to server
 * @author Armaan Singh Klair
 *
 */
public class UserInformation {
	private String server;
	private int port;
	private String username;
	
	public UserInformation(String server, int port, String username) {
		super();
		this.server = server;
		this.port = port;
		this.username = username;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "UserInformation [server=" + server + ", port=" + port + ", username=" + username + "]";
	}
	
	
	
}
