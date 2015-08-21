package com.filipemarruda.encoding;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.filipemarruda.bundle.Properties;
import com.filipemarruda.http.HttpConnector;
import com.filipemarruda.http.IHttpConnector;

public class EncodingHandlerTest {
	
	@Mock
	private IHttpConnector httpConnector;
	
	@Before
	public void setUp(){
		httpConnector = mock(HttpConnector.class);
	}
	
	@Test(expected=java.io.IOException.class)
	public void getMediaInfo_1() throws Exception{
		
		final String mediaId = "";
		final EncodingHandler eH = new EncodingHandler("","","");
		eH.getMediaInfo(mediaId);
		
	}
	
	@Test
	public void getMediaInfo_2() throws Exception{
		
		final String mediaId = "";
		final EncodingHandler eH = createEncodingHandlerMock();
		final String response = eH.getMediaInfo(mediaId);
		
		assertTrue(response.contains("error"));
		
	}
	
	@Test(expected=java.io.IOException.class)
	public void addMedia_1() throws Exception{
		
		final String source = "";
		final String destination = "";
		final EncodingHandler eH = new EncodingHandler("","","");
		
		eH.addMedia(source, destination);
		
	}
	
	@Test
	public void addMedia_2() throws Exception{
		
		final EncodingHandler eH = createEncodingHandlerMock();
		final String response = eH.addMedia("", "");
		
		assertTrue(response.contains("error"));
		
	}
	
	@Test
	public void addMedia_3() throws Exception{
		
		// setup mocks
		when(httpConnector.simplePost(Properties.getString("EncodingEndpoint"), createMockPayload()))
			.thenReturn("Success");
		final EncodingHandler eH = createEncodingHandlerMock();
		// injecting mock
		eH.setHttpConnector(httpConnector);
		
		final String source = createSouceMock();
		final String destination = createDestinationMock();
		final String response = eH.addMedia(source, destination);
		
		assertFalse(response.contains("error"));
		
	}
	
	private EncodingHandler createEncodingHandlerMock(){
		
		final EncodingHandler eH = new EncodingHandler(
				Properties.getString("EncodingEndpoint"),
				Properties.getString("EncodingUserId"),
				Properties.getString("EncodingUserKey"));
		return eH;
		
	}
	
	private String createSouceMock() throws UnsupportedEncodingException{
		
		final String source = String.format(
				Properties.getString("S3FileEndpoint"),
				URLEncoder.encode(Properties.getString("AWSAccessKey"), "UTF-8"),
				URLEncoder.encode(Properties.getString("AWSSecretKey"), "UTF-8"),
				Properties.getString("S3Bucket"),
				"20150820_135352.wmv");
		
		return source;
		
	}
	
	private String createDestinationMock() throws UnsupportedEncodingException{
		
		final String destination = String.format(
				Properties.getString("S3FileEndpoint"),
				URLEncoder.encode(Properties.getString("AWSAccessKey"), "UTF-8"),
				URLEncoder.encode(Properties.getString("AWSSecretKey"), "UTF-8"),
				Properties.getString("S3Bucket"),
				"20150820_135352.mp4");
		
		return destination;
		
	}
	
	private String createMockPayload() throws UnsupportedEncodingException{
		
		String xml = String.format(
			Properties.getString("EncodingAddMediaXML"),
			Properties.getString("EncodingUserId"),
			Properties.getString("EncodingUserKey"),
			createSouceMock(),
			createDestinationMock()
		);
				
		return "xml=" + URLEncoder.encode(xml, "UTF8");
	}
	
}
