package com.harystolho.task;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class to store information about a Task.
 * 
 * @author Harystolho
 *
 */
public class Task {

	/**
	 * Task configuration enum
	 */
	public enum conf {
		ENABLE_CLASS, ENABLE_ID
	};

	private int id;
	private String name;
	private URL url;
	private int interval;
	private TaskUnit unit;
	private String selected;
	private String selector;
	private File outputFolder;
	Properties configs;

	private Task() {

	}

	/**
	 * A Builder for the Task Class
	 */
	public static class TaskBuilder {
		private int id;
		private String name;
		private URL url;
		private int interval;
		private TaskUnit unit;
		private String selected;
		private String selector;
		private File outputFolder;
		Properties configs;

		/**
		 * Initialize the Builder with default values.
		 */
		public TaskBuilder() {
			this(new Random().nextInt(5000));
		}

		public TaskBuilder(int id) {
			this.id = id;
			this.name = "Task #" + id;
			// url is not set
			this.interval = 1;
			this.unit = TaskUnit.MINUTE;
			this.selected = "";
			this.selector = "value";
			this.outputFolder = new File("/");
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

		public TaskBuilder setSelected(String selected) {
			this.selected = selected;
			return this;
		}

		public TaskBuilder setSelector(String selector) {
			this.selector = selector;
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

			task.id = this.id;
			task.name = this.name;
			task.url = this.url;
			task.interval = this.interval;
			task.unit = this.unit;
			task.selected = this.selected;
			task.selector = this.selector;
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
		if (url != null) {
			return url;
		}
		return null;
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

	public String getSelected() {
		return this.selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

	public File getOutputFolder() {
		return outputFolder;
	}

	public void setOutputFolder(File outputFolder) {
		this.outputFolder = outputFolder;
	}

	public Properties getConfigs() {
		return configs;
	}

	public void setConfigs(Properties configs) {
		this.configs = configs;
	}

	public String toString() {
		return this.name;
	}

}
