package com.harystolho.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.Parent;

/**
 * A Utility class for the application
 * 
 * @author Harystolho
 *
 */

public class ViwksUtils {

	public static final String RESOURCES = "file:src/main/resources/";

	private static Logger logger;
	private static ExecutorService executor;

	/**
	 * Initializes the logger and a thread pool
	 */
	public static void init() {

		logger = Logger.getLogger("Viwks");
		executor = Executors.newFixedThreadPool(5);
	}

	/**
	 * Adds a class to an element for some time and the removes it
	 * 
	 * @param element
	 *            The element
	 * @param cssClass
	 *            The class
	 * @param time
	 *            the time in milliseconds
	 */
	public static void addCssEffect(Parent element, String cssClass, long time) {
		ViwksUtils.getExecutor().submit(() -> {
			element.getStyleClass().add(cssClass);
			try {
				Thread.sleep(time);
			} catch (InterruptedException e1) {
				logger.log(Level.SEVERE, "Couldn't add the '" + cssClass + "' to the element: " + element);
				e1.printStackTrace();
			} finally {
				element.getStyleClass().remove(cssClass);
			}
		});
	}

	public static Logger getLogger() {
		return logger;
	}

	public static ExecutorService getExecutor() {
		return executor;
	}

	public static void close() {
		executor.shutdown();
	}

}
