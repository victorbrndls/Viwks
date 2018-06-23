package com.harystolho.controllers;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.internal.runners.statements.RunAfters;

import com.harystolho.utils.VersionComparator;
import com.harystolho.utils.ViwksUtils;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

public class UpdateController implements Controller {

	@FXML
	private Pane pane;

	@FXML
	private ProgressBar progressBar;

	@FXML
	private Label checkForUpdates;

	@FXML
	private Label currentVersion;

	@FXML
	private Label lastVersion;

	@FXML
	private Button updateButton;

	@FXML
	void initialize() {

		setCurrentVersion();
		loadEventListeners();

	}

	private void loadEventListeners() {

		checkForUpdates.setOnMouseClicked((e) -> {
			checkForUpdates.setUnderline(false);
			checkForUpdates.setText("Checking for updates");
			checkForUpdates.setCursor(Cursor.DEFAULT);

			progressBar.setProgress(0.4);

			ViwksUtils.getExecutor().submit(() -> {
				checkNewVersion();
			});
		});

		updateButton.setOnAction((e) -> {
<<<<<<< HEAD

=======
			
>>>>>>> 620492b... Checking for new version in another thread
		});

	}

	private void setCurrentVersion() {

		String crntVersion;

		if ((crntVersion = ViwksUtils.getConfiguration().getProperty("version")) != null) {
			currentVersion.setText(crntVersion);
		}

	}

	/**
	 * Connects to github and checks if a new version is available for download.
	 * 
	 * @return
	 */
	private boolean checkNewVersion() {

		Document doc = null;
		try {
			doc = Jsoup.connect("https://github.com/Harystolho/Viwks/releases/latest").get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (doc == null)
			return false;

		VersionComparator vc = new VersionComparator();

		String ltsttVersion = doc.getElementsByClass("release-title").get(0).getElementsByTag("a").attr("href")
				.split("tag/")[1].substring(1);
		String currentVersion = ViwksUtils.getConfiguration().getProperty("version");

		executeInApplicationThread(() -> {
			lastVersion.setText(ltsttVersion);
			progressBar.setProgress(1);
		});

		int result = vc.compare(ltsttVersion, currentVersion);

		if (result == 1) {
			updateButton.setDisable(false);
		}

		return true;
	}

	/**
	 * Executes the runnable object in the application thread. Use this when you
	 * need to updates something in the UI.
	 * 
	 * @param runnable
	 */
	private void executeInApplicationThread(Runnable runnable) {
		Platform.runLater(runnable);
	}

}