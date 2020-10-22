package com.battleship.utilities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * Message object that is send back and forth players to facilitate actual hitting and missing of ships on board
 * @author 839645
 *
 */
public class ProgressMsg implements Serializable {
	/**
	 * Private Data fields
	 */
	private String msg;
	private String sender;
	private Date timestamp;
	private int[] coords;
	
	/**
	 * Constructor
	 * @param msg Actual message to be sent
	 * @param sender Sender identifier
	 * @param timestamp Timestamp
	 */
	public ProgressMsg(String msg, String sender, Date timestamp) {
		super();
		this.msg = msg;
		this.sender = sender;
		this.timestamp = timestamp;
	}
	
	public ProgressMsg(String msg, String sender, Date timestamp, int[] coords) {
		super();
		this.msg = msg;
		this.sender = sender;
		this.timestamp = timestamp;
		this.coords = coords;
	}
	
	// Getter and setter
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public int[] getCoords() {
		return coords;
	}

	public void setCoords(int[] coords) {
		this.coords = coords;
	}

	@Override
	public String toString() {
		return "ProgressMsg [msg=" + msg + ", sender=" + sender + ", timestamp=" + timestamp + ", coords="
				+ Arrays.toString(coords) + "]";
	}
	
	
}
