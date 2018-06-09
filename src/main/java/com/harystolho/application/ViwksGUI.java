package com.harystolho.application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import com.harystolho.controllers.MainController;
import com.harystolho.utils.ViwksUtils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Creates the GUI interface for this application
 * 
 * @author Harystolho
 *
 */

public class ViwksGUI extends Application {

	private Stage window;

	private MainController controller;

	/**
	 * Creates a new window and loads the main components.
	 */
	private void loadGUI(Stage stage) {
		window = stage;
		window.setTitle("Viwks");

		window.setWidth(1200);
		window.setHeight(700);

		// Creates a new Scene from a fxml file
		Scene scene = loadGUIFromFXML();

		scene.getStylesheets().add(ViwksUtils.RESOURCES + "style.css");

		window.setScene(scene);

		window.show();
	}

	/**
	 * Loads the FXML class from the file
	 * 
	 * @return Scene
	 */
	@SuppressWarnings("static-access")
	private Scene loadGUIFromFXML() {

		Scene scene = null;

		try {
			FXMLLoader loader = new FXMLLoader();
			BorderPane pane = loader.load(new URL(ViwksUtils.MAIN_FXML));
			controller = loader.getController();
			
			scene = new Scene(pane);
			
		} catch (IOException e) {
			ViwksUtils.getLogger().log(Level.SEVERE, "Couldn't load the main.fxml file");
			e.printStackTrace();
		}

		return scene;
	}

	public MainController getController() {
		if (this.controller != null) {
			return this.controller;
		}
		throw new NullPointerException("The controller object is null");
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
