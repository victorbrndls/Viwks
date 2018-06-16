package com.harystolho.controllers;

import java.io.File;

import com.harystolho.Main;
import com.harystolho.application.ViwksGUI;
import com.harystolho.task.Task;
import com.harystolho.task.TaskUtils;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class MainController implements Controller {

	@FXML
	private Pane pane;

	@FXML
	private Button openTaskButton;

	@FXML
	private ListView<Task> taskList;

	@FXML
	private Button runButton;

	@FXML
	private Button deleteButton;

	@FXML
	private Button saveButton;

	@FXML
	private Text intervalField;

	@FXML
	private TextField folderField;

	@FXML
	private Button changeFolderButton;

	@FXML
	private Button editButton;

	@FXML
	private Label taskNameField;

	@FXML
	private Label unitField;

	private File outputFolder;
	private Task currentTask;

	@FXML
	void initialize() {
		Main.getGUI().setMainController(this);

		loadTasks();

		loadEventListeners();
	}

	private void loadEventListeners() {

		openTaskButton.setOnMouseClicked((e) -> {
			openTaskWindow();
		});

		changeFolderButton.setOnMouseClicked((e) -> {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Choose a output folder");

			// TODO check if the directory is valid
			outputFolder = chooser.showDialog(Main.getGUI().getWindow());

			folderField.setText(outputFolder.getAbsolutePath());

		});

		taskList.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			currentTask = (Task) newValue;
			updateTaskDisplay(currentTask);
		});

	}

	private void updateTaskDisplay(Task task) {

		taskNameField.setText(task.getName());

		intervalField.setText(task.getInterval() + "");
		unitField.setText(task.getUnit().getName());

		folderField.setText(task.getOutputFolder().toString());

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
