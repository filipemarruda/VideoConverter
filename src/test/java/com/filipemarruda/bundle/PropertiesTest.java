package com.filipemarruda.bundle;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>PropertiesTest</code> contains tests for the class <code>{@link Properties}</code>.
 *
 * @generatedBy CodePro at 20/08/15 23:25
 * @author F28870B
 * @version $Revision: 1.0 $
 */
public class PropertiesTest {
	/**
	 * Run the String getString(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 20/08/15 23:25
	 */
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

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 20/08/15 23:25
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 20/08/15 23:25
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 20/08/15 23:25
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(PropertiesTest.class);
	}
}