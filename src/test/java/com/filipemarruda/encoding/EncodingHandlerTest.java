package com.filipemarruda.encoding;

import static org.junit.Assert.*;

import org.junit.Test;

import com.filipemarruda.bundle.Properties;

public class EncodingHandlerTest {

	@Test(expected=java.io.IOException.class)
	public void getMediaInfo_1() throws Exception{
		
		final String mediaId = "";
		EcodingHandler eH = new EcodingHandler(
				"",
				"",
				"");
		eH.getMediaInfo(mediaId);
		
	}
	
	@Test
	public void getMediaInfo_2() throws Exception{
		
		final String mediaId = "";
		EcodingHandler eH = new EcodingHandler(
				Properties.getString("EncodingEndpoint"),
				Properties.getString("EncodingUserId"),
				Properties.getString("EncodingUserKey"));
		
		String response = eH.getMediaInfo(mediaId);
		
		assertTrue(response.contains("error"));
		
	}
	
}
