package com.filipemarruda.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.filipemarruda.MockUtil;

public class XMLParserTest {

	@Test
	public void getMediaId() throws Exception{
		
		String result = XMLParser.getMediaId(MockUtil.createGetMediaIdMockXML());
		assertEquals(result , "mediaId");
		
	}
	
	@Test(expected=org.xml.sax.SAXParseException.class)
	public void getMediaId_2() throws Exception{
		
		String result = XMLParser.getMediaId(MockUtil.createFailGetMediaIdMockXML());
		assertEquals(result , "mediaId");
		
	}
	
	@Test
	public void getMediaFormatFromResponse() throws Exception{
		
		String result = XMLParser.getMediaFormatFromResponse(MockUtil.createMediaFormatResponseMockXML());
		assertNotNull(result);
		
	}
	
	@Test
	public void getMediaStatus() throws Exception{
		
		String result = XMLParser.getMediaStatus(MockUtil.createMediaStatusResponseMockXML());
		assertEquals(result, "Processing");
		
	}
	
	@Test
	public void getDestination() throws Exception{
		
		String result = XMLParser.getDestination(MockUtil.createMediaStatusResponseMockXML());
		assertEquals(result, "Destination");
		
	}
	
	@Test
	public void getProcessMediaMessage() throws Exception{
		
		String result = XMLParser.getProcessMediaMessage(MockUtil.createProcessMediaResponseMockXML());
		assertEquals(result, "Ok");
		
	}
	
	
	
	
}
