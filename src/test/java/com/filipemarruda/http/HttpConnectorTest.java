package com.filipemarruda.http;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Before;
import org.junit.Test;

import com.filipemarruda.bundle.Properties;

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
	
	@Test
	public void simplePost_2() throws Exception{
		String response = httpConnector.simplePost(Properties.getString("EncodingEndpoint"), "");
		assertTrue(response.contains("error"));
	}
	
	@Test
	public void simplePost_3() throws Exception{
		String response = httpConnector.simplePost(Properties.getString("EncodingEndpoint"), mockGetInfoPayload());
		assertFalse(response.contains("error"));
	}
	
	private String mockGetInfoPayload() throws UnsupportedEncodingException{
		
		String xml = String.format(
			Properties.getString("EncodingGetMediaInfoXML"),
			Properties.getString("EncodingUserId"),
			Properties.getString("EncodingUserKey"),
			"42282952"
		);
		
		return "xml=" + URLEncoder.encode(xml, "UTF8");
	}
}
