package com.harystolho.application;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

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

	/**
	 * Creates a new window and loads the main components.
	 */
	private void loadGUI(Stage stage) {
		window = stage;
		window.setTitle("Viwks");
		window.setHeight(820);
		window.setWidth(1280);

		// Creates a new Scene from a fxml file
		Scene scene = loadGUIFromFXML();

		scene.getStylesheets().add(ViwksUtils.RESOURCES + "style.css");

		window.setScene(scene);

		window.show();
	}

	/**
	 * Loads the fxml from the file
	 * 
	 * @return Scene
	 */
	private Scene loadGUIFromFXML() {

		Scene scene = null;

		try {
			BorderPane pane = FXMLLoader.load(new URL(ViwksUtils.RESOURCES + "main.fxml"));
			scene = new Scene(pane);
		} catch (IOException e) {
			ViwksUtils.getLogger().log(Level.SEVERE, "Couldn't load the main.fxml file");
			e.printStackTrace();
		}

		return scene;
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
