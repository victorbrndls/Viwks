package com.harystolho.controllers;

import java.net.MalformedURLException;
import java.net.URL;

import com.harystolho.Main;
import com.harystolho.application.PageDownloader;
import com.harystolho.task.Task;
import com.harystolho.task.TaskUnit;
import com.harystolho.task.TaskUtils;
import com.harystolho.utils.ViwksUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	private Label valueText;

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
			Main.getGUI().getMainController().setCurrentTask(currentTask);
			// Loads new tasks
			Main.getGUI().getMainController().loadTasks();
			// Sets this Controller to null
			Main.getGUI().setTaskController(null);
			// Change to main Scene
			Main.getGUI().setScene(Main.getGUI().getMainScene());
		});

		saveButton.setOnMouseClicked((e) -> {

			updateTask();
			TaskUtils.saveTask(currentTask);

			ViwksUtils.addCssEffect(saveButton, "button-pressed", 250);

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
	 * Updates the {@link #currentTask} object using fields in the gui
	 */
	private void updateTask() {

		try {
			currentTask.setURL(new URL(urlField.getText()));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		currentTask.setName(taskNameField.getText());
		currentTask.setSelected(selectorField.getText());
		currentTask.setInterval(Integer.valueOf(intervalField.getText()));
		currentTask.setUnit(getUnitButtonUnit());
		currentTask.setSelector(valueSelectorButton.getText());

		if (enableClassButton.isSelected()) {
			currentTask.getConfigs().put(Task.conf.ENABLE_CLASS, true);
		} else {
			currentTask.getConfigs().put(Task.conf.ENABLE_CLASS, false);
		}

		if (enableIdButton.isSelected()) {
			currentTask.getConfigs().put(Task.conf.ENABLE_ID, true);
		} else {
			currentTask.getConfigs().put(Task.conf.ENABLE_ID, false);
		}

	}

	private TaskUnit getUnitButtonUnit() {

		switch (unitButton.getText()) {
		case "Second(s)":
			return TaskUnit.SECOND;
		case "Minute(s)":
			return TaskUnit.MINUTE;
		case "Hour(s)":
			return TaskUnit.HOUR;
		case "Day(s)":
			return TaskUnit.DAY;
		default:
			return TaskUnit.MINUTE;
		}

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
		loadTask(currentTask);
	}

	public void loadTask(Task task) {

		if (task.getURL() != null) {
			urlField.setText(currentTask.getURL().toString());
			// Moves the cursor to the end of the string
			urlField.selectEnd();
			urlField.forward();
		}

		taskNameField.setText(task.getName());

		selectorField.setText(task.getSelected());

		valueSelectorButton.setText(task.getSelector());

		intervalField.setText(task.getInterval() + "");

		unitButton.setText(task.getUnit().getName());

		valueSelectorButton.setText(task.getSelector());

		if ((boolean) task.getConfigs().get(Task.conf.ENABLE_CLASS)) {
			enableClassButton.setSelected(true);
		} else {
			enableClassButton.setSelected(false);
		}

		if ((boolean) task.getConfigs().get(Task.conf.ENABLE_ID)) {
			enableIdButton.setSelected(true);
		} else {
			enableIdButton.setSelected(false);
		}

		currentTask = task;

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
