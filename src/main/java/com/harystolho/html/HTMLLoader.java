package com.harystolho.html;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class HTMLLoader {

	private static final String EMPTY_HTML = "<html><head></head><body><p>Couldn't find the file</p></body></html>";

	/**
	 * Reads the HTML page from a file
	 * 
	 * @return the HTML file that was read
	 */
	public static String loadHTML(String name) {
		try {
			return FileUtils.readFileToString(new File("src/main/resources/html/" + name), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return EMPTY_HTML;
		}
	}

}
