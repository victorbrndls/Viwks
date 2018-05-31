package com.harystolho.application;

import com.harystolho.html.HTMLLoader;

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

	private Stage window;
	private WebView view;
	private WebEngine engine;

	/**
	 * Creates a new window and loads the main components.
	 */
	private void loadGUI(Stage stage) {
		window = stage;
		window.setTitle("Viwks");
		window.setHeight(500);
		window.setWidth(700);

		Scene scene = createMainScene();
		window.setScene(scene);

		loadWebDocument();

		window.show();
	}

	/**
	 * Creates the engine and displays the HTML
	 */
	private void loadWebDocument() {
		engine = view.getEngine();

		// Loads HTML into page
		engine.loadContent(HTMLLoader.loadHTML("index.html"));
		// Loads CSS
		engine.setUserStyleSheetLocation("file:src/main/resources/css/style.css");
	}

	/**
	 * Creates the main Web Scene, where the content is displayed.
	 * 
	 * @return a {@link Scene}
	 */
	private Scene createMainScene() {
		view = new WebView();
		return new Scene(view);
	}

	@Override
	public void start(Stage window) throws Exception {
		loadGUI(window);
	}

	/**
	 * Method used to start the application from another class
	 * 
	 * @param args
	 */
	public void init(String... args) {
		launch(args);
	}

}
