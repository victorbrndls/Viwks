package com.harystolho.controllers;

import com.harystolho.Main;
import com.harystolho.application.PageDownloader;
import com.harystolho.task.Task;
import com.harystolho.task.TaskUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

public class TaskController implements Controller {

	@FXML
	private TextField urlField;

	@FXML
	private Button loadPageButton;

	@FXML
	private TextField selectorField;

	@FXML
	private TextField intervalField;

	@FXML
	private MenuButton unitButton;

	@FXML
	private Text valueText;

	@FXML
	private MenuButton valueSelectorButton;

	@FXML
	private TextField taskNameField;

	@FXML
	private Button saveButton;

	@FXML
	private Button closeButton;

	@FXML
	private ToggleButton enableClassButton;

	@FXML
	private ToggleButton enableIdButton;

	@FXML
	private ListView<?> list;

	private PageDownloader page;
	private Task currentTask;

	@FXML
	void initialize() {
		Main.getGUI().setTaskController(this);

		if (currentTask == null)
			currentTask = createDefaultTask();

		loadTask();

		loadEventListeners();
	}

	/**
	 * Adds Event Listeners for this View
	 */
	private void loadEventListeners() {

		closeButton.setOnMouseClicked((e) -> {
			Main.getGUI().getMainController().loadTasks();
			Main.getGUI().setScene(Main.getGUI().getMainScene());
		});

		saveButton.setOnMouseClicked((e) -> {
			TaskUtils.saveTask(currentTask);
		});

		loadPageButton.setOnMouseClicked((e) -> {

			if (isURLValid(urlField.getText())) {
				page = new PageDownloader(urlField.getText());
			} else {
				// TODO show pop up
				return;
			}

			page.downloadPage();

		});

		unitButton.getItems().forEach((item) -> {
			item.setOnAction((e) -> {
				unitButton.setText(((MenuItem) e.getTarget()).getText());
			});
		});

		valueSelectorButton.getItems().forEach((item) -> {
			item.setOnAction((e) -> {
				valueSelectorButton.setText(((MenuItem) e.getTarget()).getText());
			});
		});

	}

	/**
	 * Creates a new task with default configuration
	 * 
	 * @return {@link com.harystolho.task.Task Task}
	 */
	public Task createDefaultTask() {

		Task task = new Task.TaskBuilder().build();

		return task;

	}

	/**
	 * Loads data from {@link #currentTask} and displays it in the application
	 */
	public void loadTask() {

		if (currentTask.getURL() != null) {
			urlField.setText(currentTask.getURL().toString());
			// Moves the cursor to the end of the string
			urlField.selectEnd();
			urlField.forward();
		}

		taskNameField.setText(currentTask.getName());

		selectorField.setText(currentTask.getSelector());

		valueSelectorButton.setText(currentTask.getSelector());

		intervalField.setText(currentTask.getInterval() + "");

		unitButton.setText(currentTask.getUnit().getName());

		if (!(boolean) currentTask.getConfigs().get(Task.conf.ENABLE_CLASS)) {
			enableClassButton.setSelected(true);
		}

		if (!(boolean) currentTask.getConfigs().get(Task.conf.ENABLE_ID)) {
			enableIdButton.setSelected(true);
		}

	}

	public void setTask(Task task) {
		currentTask = task;
	}

	public Task getTask() {
		return currentTask;
	}

	/**
	 * Checks if it URL is valid.
	 * 
	 * @param url
	 * @return true if it's valid
	 */
	private boolean isURLValid(String url) {
		return true; // TODO check if the url is valid
	}

}
