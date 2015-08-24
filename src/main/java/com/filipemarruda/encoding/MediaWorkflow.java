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

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.filipemarruda.xml.XMLParser;

/**
 * The Class MediaWorkflow.
 */
public class MediaWorkflow {

	/** The encoding handler. */
	private EncodingHandler encodingHandler;

	/**
	 * Instantiates a new media workflow.
	 *
	 * @param encodingEndpoint
	 *            the encoding endpoint
	 * @param userId
	 *            the user id
	 * @param userKey
	 *            the user key
	 */
	public MediaWorkflow(final String encodingEndpoint, final String userId, final String userKey) {
		encodingHandler = new EncodingHandler(encodingEndpoint, userId, userKey);
	}

	/**
	 * Start.
	 *
	 * @param source
	 *            the source
	 * @param destination
	 *            the destination
	 * @return the media id
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
	public String start(final String source, final String destination) throws IOException, XPathExpressionException,
			ParserConfigurationException, SAXException, InterruptedException {

		final String addMediaBenchmarkResponse = encodingHandler.addMediaBenchmark(source, destination);
		final String mediaId = XMLParser.getMediaId(addMediaBenchmarkResponse);

		return mediaId;

	}

	/**
	 * Gets the status.
	 *
	 * @param mediaId
	 *            the media id
	 * @return the status
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public String getStatus(final String mediaId) throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException, InterruptedException {

		final String getStatusResponse = encodingHandler.getStatus(mediaId);
		return XMLParser.getMediaStatus(getStatusResponse);

	}

	/**
	 * Gets the destination.
	 *
	 * @param mediaId
	 *            the media id
	 * @return the destination
	 * @throws XPathExpressionException
	 *             the x path expression exception
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public String getDestination(final String mediaId) throws XPathExpressionException, ParserConfigurationException,
			SAXException, IOException, InterruptedException {

		final String mediaInfoResponse = encodingHandler.getStatus(mediaId);
		String destination = XMLParser.getDestination(mediaInfoResponse);
		destination = "http://" + destination.substring(destination.lastIndexOf("@") + 1);

		return destination;

	}

	/**
	 * Process.
	 *
	 * @param mediaId
	 *            the media id
	 * @return the message returned from server
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
	public String process(final String mediaId) throws IOException, XPathExpressionException,
			ParserConfigurationException, SAXException, InterruptedException {

		final String mediaInfoResponse = encodingHandler.getMediaInfo(mediaId);
		final String format = XMLParser.getMediaFormatFromResponse(mediaInfoResponse);
		final String processMediaResponse = encodingHandler.processMedia(mediaId, format);
		return XMLParser.getProcessMediaMessage(processMediaResponse);

	}

	/**
	 * Gets the encoding handler.
	 *
	 * @return the encoding handler
	 */
	protected EncodingHandler getEncodingHandler() {
		return encodingHandler;
	}

	/**
	 * Just for testing : Sets the encoding handler.
	 *
	 * @param encodingHandler
	 *            the new encoding handler
	 */
	protected void setEncodingHandler(EncodingHandler encodingHandler) {
		this.encodingHandler = encodingHandler;
	}

}
