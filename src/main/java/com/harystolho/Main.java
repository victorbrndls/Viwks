package com.harystolho;

import com.harystolho.application.ViwksGUI;
import com.harystolho.utils.ViwksUtils;

/**
 * A Simple JavaFX application at the moment.
 * 
 * @author Harystolho
 */
public class Main {

	private static ViwksGUI viwks;

	public static void main(String[] args) {

		// Initializes the logger and a thread pool
		ViwksUtils.init();

		// Initializes the GUI
		viwks = new ViwksGUI();
		viwks.init(args);

	}
}
