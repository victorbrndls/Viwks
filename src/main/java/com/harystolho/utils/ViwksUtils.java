package com.harystolho.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
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
	private static Properties configuration;

	/**
	 * Initializes components
	 */
	public static void init() {

		logger = Logger.getLogger("Viwks");
		executor = Executors.newFixedThreadPool(5);
		configuration = new Properties();
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

	public static void loadConfiguration() {

		File config = new File("configs");

		if (!config.exists()) {
			try {
				config.createNewFile();
				createAndSaveConfiguration(config);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Couldn't create a configuration file.");
			}

			return;
		}

		try {
			configuration.load(new FileInputStream(config));
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "Couldn't load configurations from file.");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "An exception occured when loading the configuration file.");
		}
	}

	private static void createAndSaveConfiguration(File config) {

		try {
			configuration.store(new FileOutputStream(config), "");
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "Couldn't save configurations to file.");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "An exception occured when saving the configuration file.");
		}

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
