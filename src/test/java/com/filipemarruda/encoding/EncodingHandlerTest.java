package com.filipemarruda.encoding;

import static org.junit.Assert.*;

import java.net.URLEncoder;

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
	
	@Test(expected=java.io.IOException.class)
	public void addMedia_1() throws Exception{
		
		final String source = "";
		final String destination = "";
		
		EcodingHandler eH = new EcodingHandler(
				"",
				"",
				"");
		eH.addMedia(source, destination);
		
	}
	
	@Test
	public void addMedia_2() throws Exception{
		
		EcodingHandler eH = new EcodingHandler(
				Properties.getString("EncodingEndpoint"),
				Properties.getString("EncodingUserId"),
				Properties.getString("EncodingUserKey"));
		
		String response = eH.addMedia("", "");;
		
		assertTrue(response.contains("error"));
		
	}
	
	@Test
	public void addMedia_3() throws Exception{
		
		final String source = String.format(
				Properties.getString("S3FileEndpoint"),
				URLEncoder.encode(Properties.getString("AWSAccessKey"), "UTF-8"),
				URLEncoder.encode(Properties.getString("AWSSecretKey"), "UTF-8"),
				Properties.getString("S3Bucket"),
				"20150820_135352.wmv");
		
		final String destination = String.format(
				Properties.getString("S3FileEndpoint"),
				URLEncoder.encode(Properties.getString("AWSAccessKey"), "UTF-8"),
				URLEncoder.encode(Properties.getString("AWSSecretKey"), "UTF-8"),
				Properties.getString("S3Bucket"),
				"20150820_135352.mp4");
		
		EcodingHandler eH = new EcodingHandler(
				Properties.getString("EncodingEndpoint"),
				Properties.getString("EncodingUserId"),
				Properties.getString("EncodingUserKey"));
		
		String response = eH.addMedia(source, destination);;
		System.out.println(response);
		assertFalse(response.contains("error"));
		
	}
	
}
