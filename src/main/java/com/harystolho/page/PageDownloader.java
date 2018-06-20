package com.harystolho.page;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.harystolho.Main;
import com.harystolho.controllers.TaskController;

import javafx.application.Platform;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class PageDownloader {

	private static final Logger logger = Logger.getLogger(PageDownloader.class.getName());

	private String url;

	private Document page;

	public PageDownloader(String url) {
		this.url = url;
	}

	/**
	 * Downloads the web page, lists all the nodes and put them in the
	 * {@link TaskController} ListView.
	 */
	private void afterPageDownload() {
		TaskController controller = Main.getGUI().getTaskController();

		getAllElements(page.select("head").get(0), controller);
		getAllElements(page.select("body").get(0), controller);
	}

	private void getAllElements(Element e, TaskController controller) {

		if (filterTag(e.tagName())) {
			CustomTag tag = new CustomTag(handleHtmlTag(e.outerHtml()), e.cssSelector());
			// Add element's classes
			tag.addClasses(e.classNames());
			// Add element's id
			tag.setId(e.id());
			// Adds the element to the list
			controller.addTagToSelectorList(tag);
		}

		if (e.children().size() > 0) {
			for (Element child : e.children()) {
				getAllElements(child, controller);
			}
		}

	}

	/**
	 * Filter Nodes.
	 * 
	 * @param tagName
	 *            the tag
	 * @return true if the node is allowed
	 */
	private boolean filterTag(String tagName) {
		// TODO Tag Filter
		return true;
	}

	private String handleHtmlTag(String outerHtml) {
		return StringUtils.splitPreserveAllTokens(outerHtml, ">")[0] + ">";
	}

	/**
	 * Downloads the web page and return a {@link Document} containing it.
	 * 
	 * @return a {@link Document} containing the page.
	 */
	public void downloadPage() {
		if (url == null) {
			showAlert("Invalid URL", "The URL is not valid");
			logger.log(Level.INFO, "Can't download page because the URL is null");
			return;
		}

		// TODO close after 15 seconds
		Platform.runLater(() -> {

			WebView view = new WebView();
			Stage stage = new Stage();

			stage.setTitle("Close this window when the page has loaded.");
			stage.setMaxWidth(1280);
			stage.setMaxHeight(720);

			Scene sc = new Scene(view);
			stage.setScene(sc);

			view.getEngine().getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) -> {

				if (newValue == State.RUNNING) {
					stage.show();
				}

				if (newValue == State.SUCCEEDED) {

					Alert alert = new Alert(AlertType.CONFIRMATION);

					alert.setTitle("Info");
					alert.setContentText("Close the window below when the page has finished loading.");
					alert.show();

					stage.setOnCloseRequest((e) -> {
						page = Jsoup
								.parse((String) view.getEngine().executeScript("document.documentElement.outerHTML"));
						afterPageDownload();
					});

				}

			});

			view.getEngine().load(url);

		});

	}

	private void showAlert(String title, String msg) {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(title);
			alert.setContentText(msg);
			alert.show();
		});
	}

	public Document getDocument() {
		return page;
	}

}
