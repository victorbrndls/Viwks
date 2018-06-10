package com.harystolho.application;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PageDownloader {

	private static final Logger logger = Logger.getLogger(PageDownloader.class.getName());

	private URL url;
	private String title;

	public PageDownloader(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			logger.log(Level.SEVERE, "Invalid URL", e);
			// TODO show pop up
		}
	}

	/**
	 * Downloads the page using Jsoup
	 */
	public void downloadPage() {
		if (url == null) {
			return;
		}

	}

}
