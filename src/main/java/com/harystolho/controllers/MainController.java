package com.harystolho.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.harystolho.Main;
import com.harystolho.application.ViwksGUI;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class MainController implements Controller {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button openTask;

	@FXML
	void initialize() {
		loadEventListeners();

		Main.getGUI().setMainController(this);
	}

	private void loadEventListeners() {

		openTask.setOnMouseClicked((e) -> {
			openTaskWindow();
		});

	}

	private void openTaskWindow() {

		Scene taskScene = new Scene(ViwksGUI.loadFXML("taskCreator.fxml"));
		
		Main.getGUI().setScene(taskScene);

	}

}
