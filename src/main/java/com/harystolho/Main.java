package com.harystolho;

import com.harystolho.application.ViwksGUI;
import com.harystolho.utils.ViwksUtils;

/**
 * An application to get information from web pages.
 * 
 * @author Harystolho
 */
public class Main {

	private static ViwksGUI viwks;

	public static void main(String[] args) {

		// Initializes the logger and a thread pool
		ViwksUtils.init();

		// Creates a GUI instance
		viwks = new ViwksGUI();
		// Initialize the GUI
		viwks.init(args);

	}
}
