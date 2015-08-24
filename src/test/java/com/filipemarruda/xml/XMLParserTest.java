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
package com.filipemarruda.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.filipemarruda.MockUtil;

/**
 * JUnit test for XMLParser.
 */
public class XMLParserTest {

	/**
	 * Test case for method getMediaId.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getMediaId() throws Exception {

		String result = XMLParser.getMediaId(MockUtil.createGetMediaIdMockXML());
		assertEquals(result, "mediaId");

	}

	/**
	 * Test case for method getMediaId.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test(expected = org.xml.sax.SAXParseException.class)
	public void getMediaId_2() throws Exception {

		String result = XMLParser.getMediaId(MockUtil.createFailGetMediaIdMockXML());
		assertEquals(result, "mediaId");

	}

	/**
	 * Test case for method getMediaFormatFromResponse.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getMediaFormatFromResponse() throws Exception {

		String result = XMLParser.getMediaFormatFromResponse(MockUtil.createMediaFormatResponseMockXML());
		assertNotNull(result);

	}

	/**
	 * Test case for method getMediaStatus.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getMediaStatus() throws Exception {

		String result = XMLParser.getMediaStatus(MockUtil.createMediaStatusResponseMockXML());
		assertEquals(result, "Processing");

	}

	/**
	 * Test case for method getDestination.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getDestination() throws Exception {

		String result = XMLParser.getDestination(MockUtil.createMediaStatusResponseMockXML());
		assertEquals(result, "Destination");

	}

	/**
	 * Test case for method getProcessMediaMessage.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getProcessMediaMessage() throws Exception {

		String result = XMLParser.getProcessMediaMessage(MockUtil.createProcessMediaResponseMockXML());
		assertEquals(result, "Ok");

	}

}
