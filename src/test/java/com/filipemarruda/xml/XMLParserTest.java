package com.filipemarruda.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class XMLParserTest {

	@Test
	public void getStringFromXML_1() throws Exception{
		
		String result = XMLParser.getStringFromXML("//userid", createMockXML());
		assertEquals(result , "userId");
		
	}
	
	@Test
	public void getStringFromXML_2() throws Exception{
		
		String result = XMLParser.getStringFromXML("//bla", createMockXML());
		assertEquals(result, "");
		
	}
	
	@Test(expected=org.xml.sax.SAXParseException.class)
	public void getStringFromXML_3() throws Exception{
		
		String result = XMLParser.getStringFromXML("//userid", createFailMockXML());
		assertEquals(result , "userId");
		
	}
	
	private String createMockXML(){
		return "<?xml version='1.0'?><query><userid>userId</userid><userkey>userKey</userkey><action>GetMediaInfo</action><mediaid>mediaId</mediaid></query>";
	}
	
	private String createFailMockXML(){
		return "<?xml version='1.0'?><query><userid>userId</userid><userkey>userKey</userkey><action>GetMediaInfo</action><mediaid>mediaId</mediaid></query";
	}
}
