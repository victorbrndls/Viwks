package com.harystolho.controllers;

import java.io.File;
import java.util.ListIterator;

import com.harystolho.Main;
import com.harystolho.ViwksGUI;
import com.harystolho.task.Task;
import com.harystolho.task.TaskUtils;
import com.harystolho.utils.RunUtils;
import com.harystolho.utils.ViwksUtils;

import javafx.application.Platform;
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
	private Text intervalField;

	@FXML
	private TextField folderField;

	@FXML
	private Button changeFolderButton;

	@FXML
	private TextField customDelayField;

	@FXML
	private Button editButton;

	@FXML
	private Label taskNameField;

	@FXML
	private Label unitField;

	private File outputFolder;
	private Task currentTask;

	private Thread runningThread;

	@FXML
	void initialize() {
		Main.getGUI().setMainController(this);

		loadTasks();

		loadEventListeners();
	}

	private void loadEventListeners() {

		changeFolderButton.setOnMouseClicked((e) -> {
			DirectoryChooser chooser = new DirectoryChooser();
			chooser.setTitle("Choose a output folder");

			// TODO if the directory was already chosen, open it
			outputFolder = chooser.showDialog(Main.getGUI().getWindow());

			if (outputFolder != null) {
				folderField.setText(outputFolder.getAbsolutePath());
				updateAndSaveTask();
			}
		});

		editButton.setOnAction((e) -> {

			if (currentTask != null) {
				openTaskWindow();
				Main.getGUI().getTaskController().loadTask(currentTask);
			}

		});

		deleteButton.setOnMouseClicked((e) -> {
			if (currentTask != null) {
				TaskUtils.deleteTask(currentTask);
				currentTask = null;
				loadTasks();
			}

			if (taskList.getItems().size() > 0) {
				currentTask = taskList.getItems().get(0);
				taskList.getSelectionModel().selectIndices(0);

				updateTaskDisplay(currentTask);
			} else {
				clearTaskDisplay();
			}

		});

		runButton.setOnAction((e) -> {

			if (runningThread != null) {
				runningThread.interrupt();
				runningThread = null;
			} else {
				if (currentTask != null) {
					runningThread = new Thread(RunUtils.getRunnable(currentTask));
					runningThread.start();
				}
			}

			updateRunButton();

		});

		openTaskButton.setOnMouseClicked((e) -> {
			openTaskWindow();
		});

		taskList.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				currentTask = (Task) newValue;
				updateTaskDisplay(currentTask);
			}
		});

	}

	public void updateRunButton() {
		Platform.runLater(() -> {
			if (runningThread == null) {
				runButton.setText("Run");
			} else {
				runButton.setText("Stop");
			}
		});
	}

	/**
	 * Updates the display on the right
	 * 
	 * @param task
	 */
	private void updateTaskDisplay(Task task) {

		taskNameField.setText(task.getName());

		intervalField.setText(task.getInterval() + "");
		unitField.setText(task.getUnit().getName());

		folderField.setText(task.getOutputFolder().toString());

	}

	/**
	 * Clears the display box
	 */
	private void clearTaskDisplay() {

		taskNameField.setText("Task #");

		intervalField.setText("0");
		unitField.setText("Second(s)");

		folderField.setText("");
	}

	/**
	 * Updates the {@link #currentTask} and saves it
	 */
	private void updateAndSaveTask() {
		currentTask.setOutputFolder(outputFolder);
		TaskUtils.saveTask(currentTask);
	}

	public void setCurrentTask(Task task) {
		currentTask = task;
	}

	/**
	 * Loads task from file and displays it
	 */
	public void loadTasks() {

		taskList.getItems().clear();

		TaskUtils.loadTasks().forEach((item) -> {
			taskList.getItems().add(item);
		});

		// Selects the task that was selected before.
		if (currentTask == null) {
			return;
		}

		ListIterator<Task> iterator = taskList.getItems().listIterator();
		while (iterator.hasNext()) {
			Task t = iterator.next();
			if (currentTask.getId() == t.getId()) {
				taskList.getSelectionModel().select(t);
				break;
			}
		}

	}

	public void setRunningThread(Thread t) {
		runningThread = t;
	}

	/**
	 * Opens a new Window where you can edit a {@link Task}
	 */
	private void openTaskWindow() {
		Scene taskScene = new Scene(ViwksGUI.loadFXML("taskCreator.fxml"));
		taskScene.getStylesheets().add(ViwksUtils.RESOURCES + "style.css");

		Main.getGUI().setScene(taskScene);

	}

}
