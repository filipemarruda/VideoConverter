package com.filipemarruda.bundle;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class PropertiesTest {

	@Test
	public void testGetString_1()
		throws Exception {
		String key = "AWSSecretKey";

		String result = Properties.getString(key);

		// add additional test code here
		// An unexpected exception was thrown in user code while executing this test:
		//    java.lang.ExceptionInInitializerError
		assertNotNull(result);
	}
	
}