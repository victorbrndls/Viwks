package com.harystolho.html;

import java.util.logging.Level;

import com.harystolho.utils.ViwksUtils;

/**
 * This class is used to call java methods from the window using JavaScript
 * 
 * @author Harystolho
 *
 */

public class JavaBridge {
	
	public void log(String log) {
		System.out.println(log);
	}

	public void logger(String log) {
		ViwksUtils.getLogger().log(Level.INFO, log);
	}

}
