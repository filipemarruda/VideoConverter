package com.filipemarruda.http.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import org.junit.*;
import static org.junit.Assert.*;

public class ConvertTest {

	@Test
	public void testConvert()
		throws Exception {

		Convert result = new Convert();

		// add additional test code here
		assertNotNull(result);
		assertEquals(null, result.getServletConfig());
		assertEquals("", result.getServletInfo());
	}

	@Test(expected=java.lang.IllegalArgumentException.class)
	public void testDoGet()
		throws Exception {
		Convert fixture = new Convert();
		HttpServletRequest request = new HttpServletRequestWrapper((HttpServletRequest) null);
		HttpServletResponse response = new HttpServletResponseWrapper((HttpServletResponse) null);

		fixture.doGet(request, response);

	}
	
	@Test(expected=java.lang.IllegalArgumentException.class)
	public void testDoPost()
		throws Exception {
		Convert fixture = new Convert();
		HttpServletRequest request = new HttpServletRequestWrapper((HttpServletRequest) null);
		HttpServletResponse response = new HttpServletResponseWrapper((HttpServletResponse) null);

		fixture.doPost(request, response);

	}
	
}