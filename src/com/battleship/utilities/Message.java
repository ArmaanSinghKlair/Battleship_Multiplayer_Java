package com.battleship.utilities;

import java.io.*;
import java.util.*;

/**
 * Basic message object that is sent back and forth during gameplay for chatting
 * @author Armaan Singh Klair
 *
 */
public class Message implements Serializable {

	/**
	 * Private Data Fields
	 */
	private static final long serialVersionUID = -2126175700536270657L;
	private String msg;
	private String sender;
	private Date timestamp;
	
	public Message(String msg, String sender, Date timestamp) {
		super();
		this.msg = msg;
		this.sender = sender;
		this.timestamp = timestamp;
	}
	
	//Getters & setters
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
	
	@Override
	public String toString() {
		return "Message [msg=" + msg + ", sender=" + sender + ", timestamp=" + timestamp + "]";
	}
	
	
}
