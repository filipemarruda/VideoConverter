package com.filipemarruda.encoding;

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
	public void start() throws UnsupportedEncodingException, IOException, XPathExpressionException, ParserConfigurationException, SAXException{
		
		final String source = createSouceMock();
		final String destination = createDestinationMock();
		// setup mocks
		when(encodingHandler.addMediaBenchmark(source, destination))
			.thenReturn("<?xml version='1.0'?><response><message>Added</message><MediaID>42348081</MediaID></response>");
		
		when(encodingHandler.getMediaInfo("42348081"))
			.thenReturn("<response><bitrate>2050k</bitrate><duration>7.58</duration><audio_bitrate>64.0k</audio_bitrate><audio_duration>7.522</audio_duration><video_duration>7.522</video_duration><video_codec>wmv2 (WMV2 / 0x32564D57)</video_codec><size>1920x1080</size><audio_codec>wmav2</audio_codec><audio_sample_rate>44100</audio_sample_rate><audio_channels>2</audio_channels><frame_rate>30</frame_rate><format>windows media</format></response>");

		when(encodingHandler.processMedia("42348081", "<bitrate>2050k</bitrate><duration>7.58</duration><audio_bitrate>64.0k</audio_bitrate><audio_duration>7.522</audio_duration><video_duration>7.522</video_duration><video_codec>wmv2 (WMV2 / 0x32564D57)</video_codec><size>1920x1080</size><audio_codec>wmav2</audio_codec><audio_sample_rate>44100</audio_sample_rate><audio_channels>2</audio_channels><frame_rate>30</frame_rate><format>windows media</format>"))
			.thenReturn("Success");

		
		final MediaWorkflow mediaWorkflow = createMediaWorkflowMock();
		// injecting mock
		mediaWorkflow.setEncodingHandler(encodingHandler);
		mediaWorkflow.start(source, destination);
		
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
