package com.filipemarruda.encoding;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.xml.sax.SAXException;

import com.filipemarruda.bundle.Properties;


public class MediaWorkflowTest {
	
	@Mock
	private EncodingHandler encodingHandler;
	
	@Before
	public void setUp() {
		encodingHandler = mock(EncodingHandler.class);
	}
	
	@Test
	public void start() throws UnsupportedEncodingException, IOException, XPathExpressionException, ParserConfigurationException, SAXException, InterruptedException{
		
		final String source = createSouceMock();
		final String destination = createDestinationMock();
		// setup mocks
		when(encodingHandler.addMediaBenchmark(source, destination))
			.thenReturn("<?xml version='1.0'?><response><message>Added</message><MediaID>42348081</MediaID></response>");
		
		final MediaWorkflow mediaWorkflow = createMediaWorkflowMock();
		// injecting mock
		mediaWorkflow.setEncodingHandler(encodingHandler);
		final String mediaId = mediaWorkflow.start(source, destination);
		assertEquals(mediaId, "42348081");
		
	}
	
	@Test
	public void process() throws UnsupportedEncodingException, IOException, XPathExpressionException, ParserConfigurationException, SAXException, InterruptedException{
		
		// setup mocks
		when(encodingHandler.getMediaInfo("42348081"))
			.thenReturn("<response><bitrate>2050k</bitrate><duration>7.58</duration><audio_bitrate>64.0k</audio_bitrate><audio_duration>7.522</audio_duration><video_duration>7.522</video_duration><video_codec>wmv2 (WMV2 / 0x32564D57)</video_codec><size>1920x1080</size><audio_codec>wmav2</audio_codec><audio_sample_rate>44100</audio_sample_rate><audio_channels>2</audio_channels><frame_rate>30</frame_rate><format>windows media</format></response>");

		when(encodingHandler.processMedia("42348081", "<output>mp4</output><bitrate>2050k</bitrate><duration>7.58</duration><size>0x360</size><bitrate>512k</bitrate><audio_bitrate>64k</audio_bitrate><preset>2</preset>"))
			.thenReturn("<?xml version='1.0'?><response><message>Ok</message></response>");

		
		final MediaWorkflow mediaWorkflow = createMediaWorkflowMock();
		// injecting mock
		mediaWorkflow.setEncodingHandler(encodingHandler);
		final String processMediaMessage = mediaWorkflow.process("42348081");
		assertEquals(processMediaMessage, "Ok");
		
	}
	
	@Test
	public void getStatus() throws UnsupportedEncodingException, IOException, XPathExpressionException, ParserConfigurationException, SAXException, InterruptedException{
		
		// setup mocks
		when(encodingHandler.getStatus("42348081"))
			.thenReturn("<response><id>42348081</id><userid>49677</userid><sourcefile>SourceFile</sourcefile><status>Processing</status><format><id>136610241</id><status>Processing</status><output>mp4</output><destination>Destination</destination></format><queue_time>0</queue_time></response>");

		final MediaWorkflow mediaWorkflow = createMediaWorkflowMock();
		// injecting mock
		mediaWorkflow.setEncodingHandler(encodingHandler);
		final String status = mediaWorkflow.getStatus("42348081");
		assertEquals(status, "Processing");
		
	}
	
	@Test
	public void getDestination() throws UnsupportedEncodingException, IOException, XPathExpressionException, ParserConfigurationException, SAXException, InterruptedException{
		
		// setup mocks
		when(encodingHandler.getStatus("42348081"))
			.thenReturn("<response><id>42348081</id><userid>49677</userid><sourcefile>SourceFile</sourcefile><status>Processing</status><format><id>136610241</id><status>Processing</status><output>mp4</output><destination>Destination</destination></format><queue_time>0</queue_time></response>");

		final MediaWorkflow mediaWorkflow = createMediaWorkflowMock();
		// injecting mock
		mediaWorkflow.setEncodingHandler(encodingHandler);
		final String destination = mediaWorkflow.getDestination("42348081");
		assertEquals(destination, "http://Destination");
		
	}
	
	private MediaWorkflow createMediaWorkflowMock() {

		final MediaWorkflow mediaWorkflow = new MediaWorkflow(Properties.getString("EncodingEndpoint"),
				Properties.getString("EncodingUserId"), Properties.getString("EncodingUserKey"));
		return mediaWorkflow;

	}
	
	private String createSouceMock() throws UnsupportedEncodingException {

		final String source = String.format(Properties.getString("S3FileEndpoint"),
				URLEncoder.encode(Properties.getString("AWSAccessKey"), "UTF-8"),
				URLEncoder.encode(Properties.getString("AWSSecretKey"), "UTF-8"), Properties.getString("S3Bucket"),
				"20150820_135352.wmv");

		return source;

	}

	private String createDestinationMock() throws UnsupportedEncodingException {

		final String destination = String.format(Properties.getString("S3FileEndpoint"),
				URLEncoder.encode(Properties.getString("AWSAccessKey"), "UTF-8"),
				URLEncoder.encode(Properties.getString("AWSSecretKey"), "UTF-8"), Properties.getString("S3Bucket"),
				"20150820_135352.mp4");

		return destination;

	}
	
}
