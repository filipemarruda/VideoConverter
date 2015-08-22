package com.filipemarruda.xml;

import static org.junit.Assert.*;

import org.junit.Test;

public class XMLParserTest {

	@Test
	public void getMediaId() throws Exception{
		
		String result = XMLParser.getMediaId(createMockXML());
		assertEquals(result , "mediaId");
		
	}
	
	@Test(expected=org.xml.sax.SAXParseException.class)
	public void getMediaId_2() throws Exception{
		
		String result = XMLParser.getMediaId(createFailMockXML());
		assertEquals(result , "mediaId");
		
	}
	
	@Test
	public void getMediaFormatFromResponse() throws Exception{
		
		String result = XMLParser.getMediaFormatFromResponse(createResponseMockXML());
		assertNotNull(result);
		
	}
	
	private String createMockXML(){
		return "<?xml version='1.0'?><query><userid>userId</userid><userkey>userKey</userkey><action>GetMediaInfo</action><MediaID>mediaId</MediaID></query>";
	}
	
	private String createFailMockXML(){
		return "<?xml version='1.0'?><query><userid>userId</userid><userkey>userKey</userkey><action>GetMediaInfo</action><MediaID>mediaId</MediaID></query";
	}
	
	private String createResponseMockXML(){
		return "<?xml version='1.0'?><response><bitrate>2050k</bitrate><duration>7.58</duration><audio_bitrate>64.0k</audio_bitrate><audio_duration>7.522</audio_duration><video_duration>7.522</video_duration><video_codec>wmv2 (WMV2 / 0x32564D57)</video_codec><size>1920x1080</size><audio_codec>wmav2</audio_codec><audio_sample_rate>44100</audio_sample_rate><audio_channels>2</audio_channels><frame_rate>30</frame_rate><format>windows media</format></response>";
	}
}
