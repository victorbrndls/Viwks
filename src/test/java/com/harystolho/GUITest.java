package com.harystolho;

import static org.junit.Assert.*;

import org.junit.Test;

import com.harystolho.html.HTMLLoader;

/**
 * Class to test the GUI methods
 */
public class GUITest {

	@Test
	public void mainHTMLExists() {
		assertNotEquals(HTMLLoader.loadHTML("index.html"), HTMLLoader.EMPTY_HTML);
	}

}
