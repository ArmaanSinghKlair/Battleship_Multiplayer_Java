package com.battleship.utilities;

import javax.swing.*;

/**
 * Some methods that are commonly used
 * @author Armaan Singh Klair
 *
 */
public abstract class CommonMethods {
	
	/**
	 * Checks whether textfield is empty or not
	 * @param o Textfield
	 * @return true/false depending on whether its empty or not
	 */
	public static boolean isEmpty(JTextField o) {
		if(o.getText().trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}
}
