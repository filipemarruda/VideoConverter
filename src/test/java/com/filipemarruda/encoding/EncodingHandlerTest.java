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

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.filipemarruda.MockUtil;
import com.filipemarruda.bundle.Properties;
import com.filipemarruda.http.HttpConnector;
import com.filipemarruda.http.IHttpConnector;

/**
 * JUnit test for EncodingHandler.
 */
public class EncodingHandlerTest {

	/** The EncodingHandler with wrong parameters. */
	final EncodingHandler eHWrong = new EncodingHandler("", "", "");

	/** The http connector. */
	@Mock
	private IHttpConnector httpConnector;

	/**
	 * Initiate the tests scenarios.
	 */
	@Before
	public void setUp() {
		httpConnector = mock(HttpConnector.class);
	}

	/**
	 * Test case for method getMediaInfo.
	 *
	 * @throws Exception the exception
	 */
	@Test(expected = java.io.IOException.class)
	public void getMediaInfo_1() throws Exception {

		final String mediaId = "";
		eHWrong.getMediaInfo(mediaId);

	}

	/**
	 * Test case for method processMedia.
	 *
	 * @throws Exception the exception
	 */
	@Test(expected=java.net.MalformedURLException.class)
	public void processMedia_1() throws Exception {

		final String mediaId = "";
		final String format = "";
		eHWrong.processMedia(mediaId, format);

	}

	/**
	 * Test case for method getStatus.
	 *
	 * @throws Exception the exception
	 */
	@Test(expected = java.io.IOException.class)
	public void getStatus_1() throws Exception {

		final String mediaId = "";
		eHWrong.getStatus(mediaId);

	}

	/**
	 * Test case for method addMedia.
	 *
	 * @throws Exception the exception
	 */
	@Test(expected = java.io.IOException.class)
	public void addMedia_1() throws Exception {

		final String source = "";
		final String destination = "";

		eHWrong.addMedia(source, destination);

	}

	/**
	 * Test case for method addMedia.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void addMedia_3() throws Exception {

		// setup mocks
		when(httpConnector.simplePost(Properties.getString("EncodingEndpoint"),
				MockUtil.createMockPayload(Properties.getString("EncodingAddMediaXML")))).thenReturn("Success");
		final EncodingHandler eH = createEncodingHandlerMock();
		// injecting mock
		eH.setHttpConnector(httpConnector);

		final String source = MockUtil.createSouceMock();
		final String destination = MockUtil.createDestinationMock();
		final String response = eH.addMedia(source, destination);

		assertFalse(response.contains("error"));

	}

	/**
	 * Test case for method addMediaBenchmark.
	 *
	 * @throws Exception the exception
	 */
	@Test(expected = java.io.IOException.class)
	public void addMediaBenchmark_1() throws Exception {

		final String source = "";
		final String destination = "";

		eHWrong.addMediaBenchmark(source, destination);

	}

	/**
	 * Test case for method addMediaBenchmark.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void addMediaBenchmark_3() throws Exception {

		// setup mocks
		when(httpConnector.simplePost(Properties.getString("EncodingEndpoint"),
				MockUtil.createMockPayload(Properties.getString("EncodingAddMediaBenchmarkXML")))).thenReturn("Success");
		final EncodingHandler eH = createEncodingHandlerMock();
		// injecting mock
		eH.setHttpConnector(httpConnector);

		final String source = MockUtil.createSouceMock();
		final String destination = MockUtil.createDestinationMock();
		final String response = eH.addMediaBenchmark(source, destination);

		assertFalse(response.contains("error"));

	}


	
	public static EncodingHandler createEncodingHandlerMock() {

		final EncodingHandler eH = new EncodingHandler(Properties.getString("EncodingEndpoint"),
				Properties.getString("EncodingUserId"), Properties.getString("EncodingUserKey"));
		return eH;

	}
	

}
