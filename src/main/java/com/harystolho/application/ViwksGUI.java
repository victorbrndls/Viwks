package com.harystolho.application;

import org.w3c.dom.Document;

import com.harystolho.html.HTMLLoader;
import com.harystolho.html.JavaBridge;
import com.harystolho.utils.ViwksUtils;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Box;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

/**
 * This class creates the GUI and loads the index.html file into it.
 * 
 * @author Harystolho
 *
 */

public class ViwksGUI extends Application {

	private Stage window;
	// The page the user chooses
	private WebView pageView;
	// The application interface view
	private WebView applicationView;
	private WebEngine appWebEngine;
	private WebEngine pageWebEngine;
	private Document appDocument;

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
		appWebEngine = applicationView.getEngine();
		pageWebEngine = pageView.getEngine();

		// Loads the application interface
		appWebEngine.loadContent(HTMLLoader.loadHTML("index.html"));
		// Loads CSS
		appWebEngine.setUserStyleSheetLocation(ViwksUtils.STYLE_CSS + "style.css");
		appWebEngine.setUserStyleSheetLocation(ViwksUtils.STYLE_CSS + "bootstrap.css");

		// Loads an empty website
		pageWebEngine.load("https://www.google.com/");

		addAppEngineListener();

	}

	/**
	 * This listener is called when the LoadWorker is changed, after the page loads
	 * it calls the method {@code window.show();}
	 */
	private void addAppEngineListener() {
		appWebEngine.getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) -> {

			if (newValue == Worker.State.SUCCEEDED) {
				window.show();
			}

			// Retrieves the window object from the DOM
			JSObject DOMWindow = (JSObject) appWebEngine.executeScript("window");

			DOMWindow.setMember("java", new JavaBridge());

			// Executes the init() method in the page
			appWebEngine.executeScript("init();");

		});
	}

	/**
	 * Creates the main Web Scene, where the content is displayed.
	 * 
	 * @return a {@link Scene}
	 */
	private Scene createMainScene() {
		pageView = new WebView();
		applicationView = new WebView();

		// Divides the window in 2
		HBox box = new HBox();

		box.getChildren().addAll(pageView, applicationView);

		pageView.setMinWidth(0.7 * window.getWidth());

		applicationView.setMinWidth(0.3 * window.getWidth());

		return new Scene(box);
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
