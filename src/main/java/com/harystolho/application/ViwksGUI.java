package com.harystolho.application;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Class that handles the GUI.
 * 
 * @author Harystolho
 *
 */

public class ViwksGUI extends Application {

	private WebView view;
	private WebEngine engine;

	/**
	 * Creates a new window and loads the main components.
	 */
	private void loadGUI(Stage window) {
		window.setTitle("Viwks");
		window.setHeight(500);
		window.setWidth(700);

		Scene scene = createMainScene();
		window.setScene(scene);

		loadWebDocument();

		window.show();
	}

	private void loadWebDocument() {
		engine = view.getEngine();

		engine.loadContent(loadHTML());

	}

	private String loadHTML() {
		try {
			return FileUtils.readFileToString(new File("config"), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	private Scene createMainScene() {
		view = new WebView();
		return new Scene(view);
	}

	@Override
	public void start(Stage window) throws Exception {
		loadGUI(window);
	}

	public void init(String... args) {
		launch(args);
	}

}
