package com.harystolho.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.Phaser;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.harystolho.Main;
import com.harystolho.page.PageDownloader;
import com.harystolho.task.Task;

/**
 * This class is used to access webpages and to save the selected tag in a
 * folder.
 * 
 * @author Harystolho
 *
 */
public class RunUtils {

	private static final Logger logger = Logger.getLogger(RunUtils.class.getName());

	public static boolean running = false;

	public static Runnable getRunnable(Task task) {
		return () -> {
			run(task);
		};
	}

	/**
	 * Downloads the page and gets the specified HTML tag from the page.
	 * 
	 * @param task
	 */
	public static void run(Task task) {

		running = true;

		while (running) {
			PageDownloader page = new PageDownloader(task.getURL().toString());

			page.downloadPage(false, (doc) -> {
				processPage(doc, task);
			});

			try {
				Thread.sleep(task.getIntervalMilli());
			} catch (InterruptedException e) {
				running = false;
				Main.getGUI().getMainController().setRunningThread(null);
				Main.getGUI().getMainController().updateRunButton();
				logger.log(Level.SEVERE, "Thread was interrupted.");
			}

		}

	}

	/**
	 * This method is called after the page is downloaded.
	 * 
	 * @param doc
	 *            the {@link Document} containing the page's HTML.
	 * @param task
	 */
	private static void processPage(Document doc, Task task) {
		Elements els = doc.select(task.getCssSelector());

		if (els.size() <= 0) {
			logger.log(Level.WARNING, "Couldn't find specified tag.");
			return;
		}

		Element el = els.get(0);

		String result = "";

		switch (task.getDisplaySelector()) {
		case "innerHTML":
			result = el.text();
			break;
		case "value":
			result = el.val();
			break;
		default:
			break;
		}

		try {

			// Format: Task #1234-YYYY-MM-DD.txt
			File file = new File(task.getOutputFolder().getPath() + "/" + task.getName() + "-" + generateFileName());

			if (!file.exists())
				file.createNewFile();

			Files.write(Paths.get(file.getPath()), (result + "\r\n").getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Format: YYYY-MM-DD.txt
	 * 
	 * @return
	 */
	private static String generateFileName() {
		LocalDateTime time = LocalDateTime.now();
		return time.getYear() + "-" + time.getMonthValue() + "-" + time.getDayOfMonth() + ".txt";
	}

	/**
	 * Tells the running thread to stop. It will finish a cycle before exiting.
	 */
	public static void stop() {
		running = false;
	}

}
