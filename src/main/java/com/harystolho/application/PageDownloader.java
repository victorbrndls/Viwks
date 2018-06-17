package com.harystolho.application;

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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.harystolho.Main;
import com.harystolho.controllers.TaskController;
import com.harystolho.task.TaskUnit;
import com.harystolho.utils.ViwksUtils;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PageDownloader {

	private static final Logger logger = Logger.getLogger(PageDownloader.class.getName());

	private String url;

	private Document page;

	private String title;
	private Date downloadDate;

	public PageDownloader(String url) {
		this.url = url;
	}

	/**
	 * Downloads the page
	 */
	public void downloadPage() {
		if (url == null) {
			logger.log(Level.INFO, "Can't download page because the URL is null");
			return;
		}

		FutureTask<Document> doc = new FutureTask<Document>(() -> {
			return Jsoup.connect(url).get();
		});
		ViwksUtils.getExecutor().submit(doc);

		try {
			page = doc.get(15, TimeUnit.MINUTES);
		} catch (ExecutionException e) {
			showAlert("Invalid URL", "The URL is not valid");
			return;
		} catch (Exception e) {
			showAlert("Error", "Something went wrong");
			logger.log(Level.SEVERE, "Couldn't get the page from FutureTask");
			return;
		}

		if (page == null) {
			showAlert("Invalid URL", "The URL is not valid");
			logger.log(Level.SEVERE, "Couldn't retrieve page or page is null");
			return;
		}

		TaskController controller = Main.getGUI().getTaskController();

		controller.addToSelectorList(url);

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
