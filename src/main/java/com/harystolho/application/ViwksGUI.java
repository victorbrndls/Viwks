package com.harystolho.application;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

import com.harystolho.controllers.Controller;
import com.harystolho.controllers.MainController;
import com.harystolho.utils.ViwksUtils;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Creates the GUI interface for this application
 * 
 * @author Harystolho
 *
 */

public class ViwksGUI extends Application {

	private Stage window;

	private MainController mainController;

	/**
	 * Creates a new window and loads the main components.
	 */
	private void loadGUI(Stage stage) {
		window = stage;
		window.setTitle("Viwks");

		window.setWidth(1200);
		window.setHeight(700);

		// Creates a new Scene from a fxml file
		Scene scene = createMainScene();

		scene.getStylesheets().add(ViwksUtils.RESOURCES + "style.css");

		window.setScene(scene);

		window.show();
	}

	/**
	 * Creates the main Scene for the application.
	 * 
	 * @return Scene
	 */
	private Scene createMainScene() {

		Scene scene = new Scene(loadFXML("main.xml"));

		return scene;
	}

	/**
	 * Loads a FXML class from the file. Returns null if it can't find the file.
	 * 
	 * @param name
	 *            the name of the FXML file you want to load.
	 * @return a {@link Parent} object.
	 */
	@SuppressWarnings("static-access")
	private Parent loadFXML(String name) {

		Parent p = null;

		try {
			FXMLLoader loader = new FXMLLoader();
			p = loader.load(new URL(ViwksUtils.MAIN_FXML));
			mainController = loader.getController();
		} catch (IOException e) {
			ViwksUtils.getLogger().log(Level.SEVERE, "Couldn't load the " + name + ".fxml file");
			e.printStackTrace();
		}

		if (p == null) {
			throw new NullPointerException("Couldn't find the specified FXML file. file=" + name);
		}

		return p;
	}

	public Controller getMainController() {
		if (this.mainController != null) {
			return this.mainController;
		}
		throw new NullPointerException("The controller object is null.");
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
