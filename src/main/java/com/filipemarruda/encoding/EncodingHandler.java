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
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import com.filipemarruda.bundle.Properties;
import com.filipemarruda.http.HttpConnector;
import com.filipemarruda.http.IHttpConnector;

/**
 * The Class EncodingHandler.
 */
public class EncodingHandler {

	/** The encoding endpoint. */
	private final String encodingEndpoint;

	/** The user id. */
	private final String userId;

	/** The user key. */
	private final String userKey;

	/** The http connector. */
	private IHttpConnector httpConnector;

	/**
	 * Instantiates a new encoding handler.
	 *
	 * @param encodingEndpoint
	 *            the encoding endpoint
	 * @param userId
	 *            the user id
	 * @param userKey
	 *            the user key
	 */
	public EncodingHandler(final String encodingEndpoint, final String userId, final String userKey) {
		this.encodingEndpoint = encodingEndpoint;
		this.userId = userId;
		this.userKey = userKey;
	}

	/**
	 * Gets the media info.
	 *
	 * @param mediaId
	 *            the media id
	 * @return the media info
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public String getMediaInfo(final String mediaId) throws IOException, InterruptedException {

		final String xml = String.format(Properties.getString("EncodingGetMediaInfoXML"), userId, userKey, mediaId);
		return performAPICall(xml);

	}

	/**
	 * Adds the media.
	 *
	 * @param source
	 *            the source
	 * @param destination
	 *            the destination
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public String addMedia(final String source, final String destination) throws IOException, InterruptedException {

		final String xml = String.format(Properties.getString("EncodingAddMediaXML"), userId, userKey, source,
				destination);
		return performAPICall(xml);

	}

	/**
	 * Adds the media benchmark.
	 *
	 * @param source
	 *            the source
	 * @param destination
	 *            the destination
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public String addMediaBenchmark(final String source, final String destination)
			throws IOException, InterruptedException {

		final String xml = String.format(Properties.getString("EncodingAddMediaBenchmarkXML"), userId, userKey, source,
				destination);
		return performAPICall(xml);

	}

	/**
	 * Process media.
	 *
	 * @param mediaId
	 *            the media id
	 * @param format
	 *            the format
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public String processMedia(final String mediaId, final String format) throws IOException, InterruptedException {

		final String xml = String.format(Properties.getString("EncodingProcessMediaXML"), userId, userKey, mediaId,
				format);
		return performAPICall(xml);

	}

	/**
	 * Gets the status.
	 *
	 * @param mediaId
	 *            the media id
	 * @return the status
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public String getStatus(final String mediaId) throws IOException, InterruptedException {

		final String xml = String.format(Properties.getString("EncodingGetStatusXML"), userId, userKey, mediaId);
		return performAPICall(xml);

	}

	/**
	 * Perform api call.
	 *
	 * @param xml
	 *            the xml
	 * @return the string returned from server
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	private String performAPICall(final String xml) throws IOException, InterruptedException {

		// confirm 1 request per second in encoding.com, free accounts
		// avoiding 421 error from API
		TimeUnit.SECONDS.sleep(1);
		final String payload = "xml=" + URLEncoder.encode(xml, "UTF8");
		return getHttpConnector().simplePost(encodingEndpoint, payload);
	}

	/**
	 * Gets the http connector.
	 *
	 * @return the http connector
	 */
	protected IHttpConnector getHttpConnector() {
		if (httpConnector == null) {
			this.httpConnector = new HttpConnector();
		}
		return httpConnector;
	}

	/**
	 * Just for testing : Sets the http connector.
	 *
	 * @param httpConnector
	 *            the new http connector
	 */
	protected void setHttpConnector(IHttpConnector httpConnector) {
		this.httpConnector = httpConnector;
	}

}
