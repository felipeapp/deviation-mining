/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.util;

import javax.swing.JOptionPane;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class SwingUtil {
	
	public static String readSystemVersion() {
		return JOptionPane.showInputDialog(null, "Enter System Version ");
	}

	/**
	 * @return
	 */
	public static boolean readTypeExecution() {
		try{
			return Boolean.parseBoolean(JOptionPane.showInputDialog(null, "Execute Mining? true or false ? "));
		}catch(Exception ex){
			return false;
		}
	}

}
