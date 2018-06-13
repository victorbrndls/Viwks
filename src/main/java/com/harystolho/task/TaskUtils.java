package com.harystolho.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.harystolho.task.Task.conf;

public class TaskUtils {

	private static final Logger logger = Logger.getLogger(TaskUtils.class.getName());

	/**
	 * Saves a task to the folder
	 * 
	 * @param task
	 */
	public static void saveTask(Task task) {

		File folder = createSaveFolder();

		try (FileOutputStream fos = new FileOutputStream(
				new File(folder.getAbsolutePath() + "/" + UUID.randomUUID() + ".json"))) {

			// Generates a JSON object containing the task's fields
			fos.write(TaskUtils.generateJSON(task).toString().getBytes());
			fos.flush();

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * Loads .json files from the /task folder and creates {@link Task} from it
	 * 
	 * @return {@link Task}
	 */
	public static List<Task> loadTasks() {
		List<Task> tasks = new ArrayList<>();

		createSaveFolder();

		File[] taskFile = new File("tasks/").listFiles();

		for (File f : taskFile) {
			logger.log(Level.INFO, "Loading file: " + f.getName());

			StringBuffer sb = new StringBuffer();

			int l;
			byte[] b = new byte[1024];

			try (FileInputStream fis = new FileInputStream(f)) {

				while ((l = fis.read(b)) != -1) {
					sb.append(new String(b));
				}

				tasks.add(createTaskFromJSON(new JSONObject(sb.toString())));

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return tasks;
	}

	/**
	 * Generates a JSON object containing all the tasks's fields
	 * 
	 * @return JSON object
	 */
	public static JSONObject generateJSON(Task task) {
		JSONObject json = new JSONObject();

		json.put("id", task.getId());
		json.put("name", task.getName());
		json.put("url", task.getURL());
		json.put("interval", task.getInterval());
		json.put("unit", task.getUnit().getName());
		json.put("selector", task.getSelector());
		json.put("output", task.getOutputFolder());

		JSONArray jsonConfigs = new JSONArray();

		JSONObject enableClass = new JSONObject();
		JSONObject enableId = new JSONObject();

		enableClass.put("key", "enableClass");
		enableClass.put("enabled", (boolean) task.getConfigs().get(conf.ENABLE_CLASS));

		enableId.put("key", "enableId");
		enableId.put("enabled", (boolean) task.getConfigs().get(conf.ENABLE_ID));

		jsonConfigs.put(enableClass);
		jsonConfigs.put(enableId);

		json.put("configuration", jsonConfigs);

		return json;
	}

	/**
	 * Takes a {@link JSONObject} and turns it into a {@link Task}
	 * 
	 * @param json
	 *            {@link JSONObject
	 * @return {@link Task}
	 */
	public static Task createTaskFromJSON(JSONObject json) {
		Task task = new Task.TaskBuilder(json.getInt("id")).build();

		// Name
		task.setName(json.getString("name"));

		// URL
		try {
			task.setURL(new URL(json.getString("url")));
		} catch (MalformedURLException | JSONException e) {
			logger.log(Level.SEVERE, "Couldn't create URL from:" + json.getString("url"));
			// TODO set url to default
		}

		// Interval
		task.setInterval(json.getInt("interval"));

		// Unit
		switch (json.getString("unit")) {
		case "Second(s):":
			task.setUnit(TaskUnit.SECOND);
			break;
		case "Minute(s)":
			task.setUnit(TaskUnit.MINUTE);
			break;
		case "Hour(s)":
			task.setUnit(TaskUnit.HOUR);
			break;
		case "Day(s)":
			task.setUnit(TaskUnit.DAY);
			break;
		default:
			task.setUnit(TaskUnit.MINUTE);
			break;
		}

		// Selector
		task.setSelector(json.getString("selector"));

		// Output Folder
		task.setOutputFolder(new File(json.getString("output")));

		// Configuration
		JSONArray configuration = json.getJSONArray("configuration");

		JSONObject enableClass = configuration.getJSONObject(0);
		JSONObject enableId = configuration.getJSONObject(1);

		task.getConfigs().put(Task.conf.ENABLE_CLASS, enableClass.getBoolean("enabled"));
		task.getConfigs().put(Task.conf.ENABLE_ID, enableId.getBoolean("enabled"));

		// TODO handle if things don't work out
		// TODO write tests

		return task;
	}

	/**
	 * Creates a /tasks folder if it doesn't exist and returns it. Or if the folder
	 * exists returns it
	 * 
	 * @return {@link File}
	 */
	public static File createSaveFolder() {
		File folder = new File("tasks");
		if (!folder.exists()) {
			folder.mkdirs();
		}

		return folder;
	}

}
