package com.harystolho.task;

import java.io.File;
import java.net.URL;
import java.util.Properties;
import java.util.Random;

/**
 * Class to store information about a Task.
 * 
 * @author Harystolho
 *
 */
public class Task {

	/**
	 * Task configurations
	 */
	private enum conf {
		ENABLE_CLASS, ENABLE_ID
	};

	private int id;
	private String name;
	private URL url;
	private int interval;
	private TaskUnit unit;
	private File outputFolder;
	Properties configs;

	public static class TaskBuilder {
		private int id;
		private String name;
		private URL url;
		private int interval;
		private TaskUnit unit;
		private File outputFolder;
		Properties configs;

		public TaskBuilder() {
			this.id = new Random().nextInt(5000);
			this.configs = new Properties();
			this.configs.put(conf.ENABLE_CLASS, false);
			this.configs.put(conf.ENABLE_ID, false);
		}

		public int getId() {
			return id;
		}

		public TaskBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public TaskBuilder setURL(URL url) {
			this.url = url;
			return this;
		}

		public TaskBuilder setInterval(int interval) {
			this.interval = interval;
			return this;
		}

		public TaskBuilder setUnit(TaskUnit unit) {
			this.unit = unit;
			return this;
		}

		public TaskBuilder setOutputFolder(File outputFolder) {
			this.outputFolder = outputFolder;
			return this;
		}

		public TaskBuilder setConfigs(conf configuration, boolean state) {
			this.configs.put(configuration, state);
			return this;
		}

		public Task build() {

			Task task = new Task();

			task.name = this.name;
			task.url = this.url;
			task.interval = this.interval;
			task.unit = this.unit;
			task.outputFolder = this.outputFolder;
			task.configs = this.configs;

			return task;
		}

	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URL getURL() {
		return url;
	}

	public void setURL(URL url) {
		this.url = url;
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
