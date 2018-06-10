package com.harystolho.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.harystolho.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
		System.out.println(Main.getGUI().getMainController());
	}

}
