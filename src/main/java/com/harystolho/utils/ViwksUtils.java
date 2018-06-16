package com.harystolho.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

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
