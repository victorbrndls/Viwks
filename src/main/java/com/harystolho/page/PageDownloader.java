package com.harystolho.page;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.harystolho.Main;
import com.harystolho.controllers.TaskController;
import com.harystolho.utils.ViwksUtils;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PageDownloader {

	private static final Logger logger = Logger.getLogger(PageDownloader.class.getName());
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:61.0) Gecko/20100101 Firefox/61.0";

	private String url;

	private Document page;

	public PageDownloader(String url) {
		this.url = url;
	}

	/**
	 * Downloads the page
	 */
	public void handlePageDownload() {

		if ((page = downloadPage()) == null) {
			return;
		}

		TaskController controller = Main.getGUI().getTaskController();

		for (Element e : page.getAllElements()) {
			
			CustomTag tag = new CustomTag(handleHtmlTag(e.outerHtml()), e.cssSelector());
			controller.addToSelectorList(tag);
			
		}
		
	}

	private String handleHtmlTag(String outerHtml) {
		return StringUtils.splitPreserveAllTokens(outerHtml, ">")[0] + ">";
	}

	private Document downloadPage() {
		if (url == null) {
			showAlert("Invalid URL", "The URL is not valid");
			logger.log(Level.INFO, "Can't download page because the URL is null");
			return null;
		}

		FutureTask<Document> doc = new FutureTask<Document>(() -> {
			return Jsoup.connect(url).userAgent(USER_AGENT).get();
		});
		ViwksUtils.getExecutor().submit(doc);

		try {
			return doc.get(15, TimeUnit.MINUTES);
		} catch (ExecutionException e) {
			showAlert("Invalid URL", "The URL is not valid");
			return null;
		} catch (Exception e) {
			showAlert("Error", "Something went wrong");
			logger.log(Level.SEVERE, "Couldn't get the page from FutureTask");
			return null;
		}
	}

	private void showAlert(String title, String msg) {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(title);
			alert.setContentText(msg);
			alert.show();
		});
	}

}
