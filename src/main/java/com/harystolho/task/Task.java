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
	 * Task configuration enum
	 */
	public enum conf {
		ENABLE_CLASS, ENABLE_ID
	};

	private int id;
	private String name;
	private URL url;
	private int interval;
	private TaskUnits unit;
	private String cssSelector;
	private String displaySelector;
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
		private TaskUnits unit;
		private String cssSelector;
		private String displaySelector;
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
			// URL is not set
			this.interval = 1;
			this.unit = TaskUnits.MINUTE;
			this.cssSelector = "";
			this.displaySelector = "innerHTML";
			this.outputFolder = new File("/");
			this.configs = new Properties();
			this.configs.put(conf.ENABLE_CLASS, true);
			this.configs.put(conf.ENABLE_ID, true);
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

		public TaskBuilder setUnit(TaskUnits unit) {
			this.unit = unit;
			return this;
		}

		public TaskBuilder setCssSelector(String selected) {
			this.cssSelector = selected;
			return this;
		}

		public TaskBuilder setDisplaySelector(String selector) {
			this.displaySelector = selector;
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
			task.cssSelector = this.cssSelector;
			task.displaySelector = this.displaySelector;
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

	public long getIntervalMilli() {
		return interval * unit.multiplier;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public TaskUnits getUnit() {
		return unit;
	}

	public void setUnit(TaskUnits unit) {
		this.unit = unit;
	}

	public String getCssSelector() {
		return this.cssSelector;
	}

	public void setCssSelector(String selected) {
		this.cssSelector = selected;
	}

	public String getDisplaySelector() {
		return displaySelector;
	}

	public void setDisplaySelector(String selector) {
		this.displaySelector = selector;
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

	/**
	 * This implementation doesn't compare the URL's
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == null)
			return false;

		if (!(obj instanceof Task)) {
			return false;
		}

		Task task = (Task) obj;

		if (this.getName() != task.getName() || this.getId() != task.getId() || this.getInterval() != task.getInterval()
				|| this.getUnit() != task.getUnit() || this.getCssSelector() != task.getCssSelector()
				|| this.getDisplaySelector() != task.getDisplaySelector()
				|| this.getOutputFolder().toString() != task.getOutputFolder().toString()) {
			return false;
		}

		return true;
	}

}
