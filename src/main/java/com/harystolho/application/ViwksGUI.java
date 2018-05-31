package com.harystolho.application;

import org.w3c.dom.Document;

import com.harystolho.html.HTMLLoader;
import com.harystolho.utils.ViwksUtils;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * This class creates the GUI and loads the index.html file into it.
 * 
 * @author Harystolho
 *
 */

public class ViwksGUI extends Application {

	private Stage window;
	private WebView view;
	private WebEngine engine;
	private Document document;

	/**
	 * Creates a new window and loads the main components.
	 */
	private void loadGUI(Stage stage) {
		window = stage;
		window.setTitle("Viwks");
		window.setHeight(720);
		window.setWidth(1280);

		Scene scene = createMainScene();
		window.setScene(scene);

		loadWebDocument();
	}

	/**
	 * Creates the engine and displays the HTML
	 */
	private void loadWebDocument() {
		engine = view.getEngine();
		document = engine.getDocument();

		// Loads the main HTML into the engine
		engine.loadContent(HTMLLoader.loadHTML("index.html"));
		// Loads CSS
		engine.setUserStyleSheetLocation(ViwksUtils.STYLE_CSS);

		addEngineListener();

	}

	/**
	 * This listener is called when the LoadWorker is changed, after it loads it
	 * calls the method {@code window.show();}
	 */
	private void addEngineListener() {
		engine.getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue == Worker.State.SUCCEEDED) {
				window.show();
			}
		});
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

	public Stage getWindow() {
		return this.window;
	}

	@Override
	public void start(Stage window) throws Exception {
		loadGUI(window);
	}

	/**
	 * Method used to start the application from another class. It will call the
	 * {@link com.harystolho.application.ViwksGUI#start(Stage) start(Stage stage}
	 * method.
	 * 
	 * @param args
	 */
	public void init(String... args) {
		launch(args);
	}

}
