package com.filipemarruda.http;

import org.junit.Before;
import org.junit.Test;

public class HttpConnectorTest {
	
	private IHttpConnector httpConnector;
	
	@Before
	public void setUp(){
		httpConnector = new HttpConnector();
	}
	
	@Test(expected=java.io.IOException.class)
	public void simplePost_1() throws Exception{
		httpConnector.simplePost("", "");
	}
	
}
