package com.harystolho.task;

import java.io.File;

public class Task {

	private String name;
	private int interval;
	private TaskUnit unit;
	private File outputFolder;

	public Task(String name, int interval, TaskUnit unit) {
		this.name = name;
		this.interval = interval;
		this.unit = unit;
		this.outputFolder = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public TaskUnit getUnit() {
		return unit;
	}

	public void setUnit(TaskUnit unit) {
		this.unit = unit;
	}

	public File getOutputFolder() {
		return outputFolder;
	}

	public void setOutputFolder(File outputFolder) {
		this.outputFolder = outputFolder;
	}

}
