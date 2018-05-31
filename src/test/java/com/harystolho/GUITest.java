package com.harystolho;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.harystolho.html.HTMLLoader;

/**
 * Class to test the GUI methods
 */
public class GUITest {

	@Test
	public void checkIfMainHtmlExists() {
		assertNotEquals(HTMLLoader.loadHTML("index.html"), HTMLLoader.EMPTY_HTML);
	}
	
	
	
}
