package com.harystolho;

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

		// TODO add a logo

		// Creates a GUI instance
		viwks = new ViwksGUI();
		// Initialize the GUI
		viwks.init(args);

	}

	/**
	 * Sets {@link #viwks} to the gui parameter
	 * 
	 * @param gui
	 */
	public static void setGUI(ViwksGUI gui) {
		viwks = gui;
	}

	public static ViwksGUI getGUI() {
		if (viwks == null) {
			throw new NullPointerException("The GUI object is null.");
		}
		return viwks;
	}
}
