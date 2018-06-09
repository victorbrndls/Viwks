package com.harystolho.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MainController implements Controller {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ListView<?> list;

	@FXML
	private TextField urlInput;

	@FXML
	void initialize() {
		assert list != null : "fx:id=\"list\" was not injected: check your FXML file 'main.fxml'.";
		assert urlInput != null : "fx:id=\"urlInput\" was not injected: check your FXML file 'main.fxml'.";

	}

}
