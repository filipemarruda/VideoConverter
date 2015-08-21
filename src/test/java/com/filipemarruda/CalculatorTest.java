package com.filipemarruda;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>CalculatorTest</code> contains tests for the class <code>{@link Calculator}</code>.
 *
 * @generatedBy CodePro at 20/08/15 22:54
 * @author F28870B
 * @version $Revision: 1.0 $
 */
public class CalculatorTest {
	/**
	 * Run the int sum(int,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 20/08/15 22:54
	 */
	@Test
	public void testSum_1()
		throws Exception {
		Calculator fixture = new Calculator();
		int a = 1;
		int b = 1;

		int result = fixture.sum(a, b);

		// add additional test code here
		assertEquals(2, result);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 20/08/15 22:54
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
	 * @generatedBy CodePro at 20/08/15 22:54
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
	 * @generatedBy CodePro at 20/08/15 22:54
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(CalculatorTest.class);
	}
}