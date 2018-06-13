package com.harystolho.controllers;

import com.harystolho.Main;
import com.harystolho.application.ViwksGUI;
import com.harystolho.task.Task;
import com.harystolho.task.TaskUtils;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MainController implements Controller {

	@FXML
	private Button openTask;

	@FXML
	private ListView<Task> taskList;

	@FXML
	private Text taskNameField;

	@FXML
	private Button playButton;

	@FXML
	private Button deleteButton;

	@FXML
	private Button editButton;

	@FXML
	private Text intervalField;

	@FXML
	private Text unitField;

	@FXML
	private TextField folderField;

	@FXML
	private Button changeFolderButton;

	@FXML
	void initialize() {
		Main.getGUI().setMainController(this);

		loadTasks();

		loadEventListeners();
	}

	private void loadEventListeners() {

		openTask.setOnMouseClicked((e) -> {
			openTaskWindow();
		});
		
		
		
	}

	/**
	 * Loads task from file and displays it
	 */
	private void loadTasks() {

		taskList.getItems().clear();

		TaskUtils.loadTasks().forEach((item) -> {
			taskList.getItems().add(item);
		});
	}

	private void openTaskWindow() {
		// TODO This may cause a memory leak problem
		Scene taskScene = new Scene(ViwksGUI.loadFXML("taskCreator.fxml"));

		Main.getGUI().setScene(taskScene);

	}

}
