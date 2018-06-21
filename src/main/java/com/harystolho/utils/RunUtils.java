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

import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.harystolho.Main;
import com.harystolho.page.PageDownloader;
import com.harystolho.task.Task;

/**
 * TODO desbribe
 * 
 * @author Harystolho
 *
 */
public class RunUtils {

	public static boolean running = false;

	public static Phaser phaser;

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
				System.out.println("Sleeping");
				Thread.sleep(task.getIntervalMilli());
			} catch (InterruptedException e) {
				running = false;
				Main.getGUI().getMainController().setRunningThread(null);
				Main.getGUI().getMainController().updateRunButton();
				e.printStackTrace();
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
		Elements els = doc.select(task.getSelected());

		if (els.size() <= 0)
			return;

		Element el = els.get(0);

		String result = "";

		switch (task.getSelector()) {
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

			File file = new File(task.getOutputFolder().getPath() + "/" + task.getName() + "-" + generateFileName());

			if (!file.exists()) {
				file.createNewFile();
			}

			Files.write(Paths.get(file.getPath()), (result + "\r\n").getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String generateFileName() {
		LocalDateTime time = LocalDateTime.now();
		return time.getYear() + "-" + time.getMonthValue() + "-" + time.getDayOfMonth() + ".txt";
	}

	/**
	 * When this method is called
	 */
	public static void stop() {
		running = false;
	}

}
