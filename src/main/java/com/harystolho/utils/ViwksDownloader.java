package com.harystolho.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.harystolho.controllers.UpdateController;

/**
 * A class to download the newer versions of this application
 * 
 * @author Harystolho
 *
 */
public class ViwksDownloader {

	private static final Logger logger = Logger.getLogger(ViwksDownloader.class.getName());

	/**
	 * Connects to github to download a new version of the application, and
	 * downloads it to the same folder.
	 * 
	 * @param updateController
	 */
	public static void downloadNewVersion(UpdateController controller) {

		Document doc = connect("https://github.com/Harystolho/Viwks/releases/latest");

		if (doc == null)
			return;

		String downloadUrl = "https://github.com" + doc.select("li.py-2:nth-child(1) > a:nth-child(1)").attr("href");

		try (InputStream is = new URL(downloadUrl).openStream()) {
			Files.copy(is, Paths.get(getJarName(downloadUrl)), StandardCopyOption.REPLACE_EXISTING);

			controller.notifyDownload();

		} catch (MalformedURLException e1) {
			logger.log(Level.SEVERE, "The download URL is not valid.");
		} catch (IOException e1) {
			logger.log(Level.SEVERE, "An error occurred when downloading the file.");
		}

	}

	private static Document connect(String url) {
		try {
			Document doc = Jsoup.connect(url).get();

			if (doc != null) {
				return doc;
			}

			logger.log(Level.SEVERE, "Document is null.");
			return null;

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Couldn't connect to github.");
			return null;
		}

	}

	/**
	 * Takes the download URL and gets the jar name using it.
	 * 
	 * @param jarURL
	 * @return
	 */
	private static String getJarName(String jarURL) {
		String[] temp = jarURL.split("/");
		return temp[temp.length - 1];
	}

}
