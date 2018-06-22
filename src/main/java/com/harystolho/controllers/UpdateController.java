package com.harystolho.controllers;

import com.harystolho.Main;
import com.harystolho.utils.ViwksUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

public class UpdateController implements Controller {

	@FXML
	private Pane pane;

	@FXML
	private ProgressBar progressBar;

	@FXML
	private Label currentVersion;

	@FXML
	private Label lastVersion;

	@FXML
	private Button updateButton;

	@FXML
	void initialize() {

		setCurrentVersion();

		loadEventListeners();

	}

	private void setCurrentVersion() {

		String crntVersion;

		if ((crntVersion = ViwksUtils.getConfiguration().getProperty("version")) != null) {
			currentVersion.setText(crntVersion);
		}

	}

	private void loadEventListeners() {

	}

}