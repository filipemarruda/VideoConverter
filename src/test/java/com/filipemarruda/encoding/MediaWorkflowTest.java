/*****************************************************************
 * Copyright (c) 2015, Filipe Mendes
 *
 * Video Converter
 * 
 * This project consists in a simple video converter made in order 
 * to the people from SambaTech evaluate my programming skills
 * 
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * 1. Redistributions of source code must retain the above 
 * copyright notice, this list of conditions and the following 
 * disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above 
 * copyright notice, this list of conditions and the following 
 * disclaimer in the documentation and/or other materials provided 
 * with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of 
 * its contributors may be used to endorse or promote products 
 * derived from this software without specific prior written 
 * permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND 
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *****************************************************************/
package com.filipemarruda.encoding;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.xml.sax.SAXException;

import com.filipemarruda.MockUtil;
import com.filipemarruda.bundle.Properties;

/**
 * JUnit test for MediaWorkflow.
 */
public class MediaWorkflowTest {

	/** The source. */
	private String source;

	/** The destination. */
	private String destination;

	/** The MediaWorkflow. */
	private MediaWorkflow mediaWorkflow;

	/** The EncodingHandler. */
	@Mock
	private EncodingHandler encodingHandler;

	/**
	 * Initiate the tests scenarios.
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Before
	public void setUp() throws IOException, InterruptedException {

		encodingHandler = mock(EncodingHandler.class);
		source = MockUtil.createFileEndpointMock("20150820_135352.wmv");
		destination = MockUtil.createFileEndpointMock("20150820_135352.mp4");
		mediaWorkflow = createMediaWorkflowMock();

		// setup mocks
		when(encodingHandler.addMediaBenchmark(source, destination)).thenReturn(
				"<?xml version='1.0'?><response><message>Added</message><MediaID>42348081</MediaID></response>");

		when(encodingHandler.getMediaInfo("42348081")).thenReturn(
				"<response><bitrate>2050k</bitrate><duration>7.58</duration><audio_bitrate>64.0k</audio_bitrate><audio_duration>7.522</audio_duration><video_duration>7.522</video_duration><video_codec>wmv2 (WMV2 / 0x32564D57)</video_codec><size>1920x1080</size><audio_codec>wmav2</audio_codec><audio_sample_rate>44100</audio_sample_rate><audio_channels>2</audio_channels><frame_rate>30</frame_rate><format>windows media</format></response>");

		when(encodingHandler.processMedia("42348081",
				"<output>mp4</output><bitrate>2050k</bitrate><duration>7.58</duration><size>0x360</size><bitrate>512k</bitrate><audio_bitrate>64k</audio_bitrate><preset>2</preset>"))
						.thenReturn("<?xml version='1.0'?><response><message>Ok</message></response>");

		when(encodingHandler.getStatus("42348081")).thenReturn(
				"<response><id>42348081</id><userid>49677</userid><sourcefile>SourceFile</sourcefile><status>Processing</status><format><id>136610241</id><status>Processing</status><output>mp4</output><destination>Destination</destination></format><queue_time>0</queue_time></response>");

		// inject mocks
		mediaWorkflow.setEncodingHandler(encodingHandler);

	}

	/**
	 * Test case for method start.
	 *
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Test
	public void start() throws UnsupportedEncodingException, IOException, XPathExpressionException,
			ParserConfigurationException, SAXException, InterruptedException {

		final String source = MockUtil.createFileEndpointMock("20150820_135352.wmv");
		final String destination = MockUtil.createFileEndpointMock("20150820_135352.mp4");
		final String mediaId = mediaWorkflow.start(source, destination);
		assertEquals(mediaId, "42348081");

	}

	/**
	 * Test case for method process.
	 *
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Test
	public void process() throws UnsupportedEncodingException, IOException, XPathExpressionException,
			ParserConfigurationException, SAXException, InterruptedException {

		final String processMediaMessage = mediaWorkflow.process("42348081");
		assertEquals(processMediaMessage, "Ok");

	}

	/**
	 * Test case for method getStatus.
	 *
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Test
	public void getStatus() throws UnsupportedEncodingException, IOException, XPathExpressionException,
			ParserConfigurationException, SAXException, InterruptedException {

		final String status = mediaWorkflow.getStatus("42348081");
		assertEquals(status, "Processing");

	}

	/**
	 * Test case for method getDestination.
	 *
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Test
	public void getDestination() throws UnsupportedEncodingException, IOException, XPathExpressionException,
			ParserConfigurationException, SAXException, InterruptedException {

		final String destination = mediaWorkflow.getDestination("42348081");
		assertEquals(destination, "http://Destination");

	}

	/**
	 * Creates the media workflow mock.
	 *
	 * @return the media workflow
	 */
	private MediaWorkflow createMediaWorkflowMock() {

		final MediaWorkflow mediaWorkflow = new MediaWorkflow(Properties.getString("EncodingEndpoint"),
				Properties.getString("EncodingUserId"), Properties.getString("EncodingUserKey"));
		return mediaWorkflow;

	}

}
