package com.harystolho;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.harystolho.utils.ViwksUtils;

public class UtilsTest {

	@Before
	public void init() {
		ViwksUtils.init();
	}

	@Test
	public void loggerIsNotNull() {

		assertNotNull(ViwksUtils.getLogger());

	}

	@Test
	public void threadPoolIsNotNull() {
		assertNotNull(ViwksUtils.getExecutor());
	}

}
