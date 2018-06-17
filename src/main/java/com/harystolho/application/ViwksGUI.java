package com.harystolho.application;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;

import com.harystolho.Main;
import com.harystolho.controllers.Controller;
import com.harystolho.controllers.MainController;
import com.harystolho.controllers.TaskController;
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

	private Scene mainScene;

	private MainController mainController;
	private TaskController taskController;

	/**
	 * Creates a new window and loads the main components.
	 */
	private void loadGUI(Stage stage) {
		// I'm not sure why I need to do this but if I don't it doesn't work.
		Main.setGUI(this);
		
		window = stage;
		window.setTitle("Viwks");

		window.setWidth(1200);
		window.setHeight(700);

		// Creates the main menu scene
		mainScene = createMainScene();
		mainScene.getStylesheets().add(ViwksUtils.RESOURCES + "style.css");

		setScene(mainScene);

		window.setOnCloseRequest((e) -> {
			ViwksUtils.close();
		});

		window.show();
	}

	/**
	 * Creates the main scene for the application.
	 * 
	 * @return Scene
	 */
	private Scene createMainScene() {

		Scene scene = new Scene(loadFXML("main.fxml"));

		return scene;
	}

	/**
	 * Shows this scene in the window.
	 * 
	 * @param scene
	 *            the scene to be shown.
	 */
	public void setScene(Scene scene) {
		window.setScene(scene);
	}

	/**
	 * Loads a FXML class from the file.
	 * 
	 * @param name
	 *            the name of the FXML file you want to load.
	 * @return a {@link Parent} object.
	 * @throws NullPointerException
	 *             if it can't find the file.
	 */
	@SuppressWarnings("static-access")
	public static Parent loadFXML(String name) {

		Parent p = null;

		try {
			FXMLLoader loader = new FXMLLoader();
			p = loader.load(new URL(ViwksUtils.RESOURCES + name));
		} catch (IOException e) {
			ViwksUtils.getLogger().log(Level.SEVERE, "Couldn't load the " + name + ".fxml file");
			e.printStackTrace();
		}

		if (p == null) {
			throw new NullPointerException("Couldn't find the specified FXML file. file=" + name);
		}

		return p;
	}

	public Scene getMainScene() {
		if (mainScene == null) {
			throw new NullPointerException("The main Scene is null.");
		}
		return mainScene;
	}

	public void setMainController(MainController controller) {
		mainController = controller;
	}

	public MainController getMainController() {
		if (mainController != null) {
			return mainController;
		}
		throw new NullPointerException("The Main controller object is null.");
	}

	public void setTaskController(TaskController controller) {
		taskController = controller;
	}

	public TaskController getTaskController() {
		if (taskController != null) {
			return taskController;
		}
		throw new NullPointerException("The Task controller object is null.");
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
