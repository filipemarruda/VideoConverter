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
package com.filipemarruda.http;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The Class HttpConnector.
 */
public class HttpConnector implements IHttpConnector {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.filipemarruda.http.IHttpConnector#simplePost(java.lang.String,
	 * java.lang.String)
	 */
	public String simplePost(final String url, final String payload) throws IOException {

		String result = null;

		final URL server = new URL(url);
		final HttpURLConnection urlConnection = (HttpURLConnection) server.openConnection();
		urlConnection.setRequestMethod("POST");
		urlConnection.setDoOutput(true);
		urlConnection.setConnectTimeout(60000);
		urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		try (final java.io.BufferedWriter out = new java.io.BufferedWriter(
				new java.io.OutputStreamWriter(urlConnection.getOutputStream()))) {

			out.write(payload);
			out.flush();

		}

		urlConnection.connect();

		try (final java.io.InputStream is = urlConnection.getInputStream()) {

			final StringBuffer strbuf = new StringBuffer();
			final byte[] buffer = new byte[1024 * 4];
			int n = 0;

			while (-1 != (n = is.read(buffer))) {

				strbuf.append(new String(buffer, 0, n));

			}

			result = strbuf.toString();

		}

		return result;

	}

}
