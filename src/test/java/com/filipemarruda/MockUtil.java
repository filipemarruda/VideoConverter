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
package com.filipemarruda;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.filipemarruda.bundle.Properties;

/**
 * The Class MockUtil.
 */
public class MockUtil {

	/**
	 * Creates the get media id mock xml.
	 *
	 * @return the string
	 */
	public static String createGetMediaIdMockXML() {
		return "<?xml version='1.0'?><query><userid>userId</userid><userkey>userKey</userkey><action>GetMediaInfo</action><MediaID>mediaId</MediaID></query>";
	}

	/**
	 * Creates the fail get media id mock xml.
	 *
	 * @return the string
	 */
	public static String createFailGetMediaIdMockXML() {
		return "<?xml version='1.0'?><query><userid>userId</userid><userkey>userKey</userkey><action>GetMediaInfo</action><MediaID>mediaId</MediaID></query";
	}

	/**
	 * Creates the media format response mock xml.
	 *
	 * @return the string
	 */
	public static String createMediaFormatResponseMockXML() {
		return "<?xml version='1.0'?><response><bitrate>2050k</bitrate><duration>7.58</duration><audio_bitrate>64.0k</audio_bitrate><audio_duration>7.522</audio_duration><video_duration>7.522</video_duration><video_codec>wmv2 (WMV2 / 0x32564D57)</video_codec><size>1920x1080</size><audio_codec>wmav2</audio_codec><audio_sample_rate>44100</audio_sample_rate><audio_channels>2</audio_channels><frame_rate>30</frame_rate><format>windows media</format></response>";
	}

	/**
	 * Creates the media status response mock xml.
	 *
	 * @return the string
	 */
	public static String createMediaStatusResponseMockXML() {
		return "<?xml version='1.0'?><response><id>42346299</id><userid>49677</userid><sourcefile>SourceFile</sourcefile><status>Processing</status><format><id>136610241</id><status>Processing</status><output>mp4</output><destination>Destination</destination></format><queue_time>0</queue_time></response>";
	}

	public static String createProcessMediaResponseMockXML() {
		return "<?xml version='1.0'?><response><message>Ok</message></response>";
	}

	/**
	 * Creates the souce mock.
	 *
	 * @return the string
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public static String createSouceMock() throws UnsupportedEncodingException {

		final String source = String.format(Properties.getString("S3FileEndpoint"),
				URLEncoder.encode(Properties.getString("AWSAccessKey"), "UTF-8"),
				URLEncoder.encode(Properties.getString("AWSSecretKey"), "UTF-8"), Properties.getString("S3Bucket"),
				"20150820_135352.wmv");

		return source;

	}

	/**
	 * Creates the destination mock.
	 *
	 * @return the string
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public static String createDestinationMock() throws UnsupportedEncodingException {

		final String destination = String.format(Properties.getString("S3FileEndpoint"),
				URLEncoder.encode(Properties.getString("AWSAccessKey"), "UTF-8"),
				URLEncoder.encode(Properties.getString("AWSSecretKey"), "UTF-8"), Properties.getString("S3Bucket"),
				"20150820_135352.mp4");

		return destination;

	}

	/**
	 * Creates the mock payload.
	 *
	 * @param baseXML
	 *            the base xml
	 * @return the string
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public static String createMockPayload(final String baseXML) throws UnsupportedEncodingException {

		String xml = String.format(baseXML, Properties.getString("EncodingUserId"),
				Properties.getString("EncodingUserKey"), createSouceMock(), createDestinationMock());

		return "xml=" + URLEncoder.encode(xml, "UTF8");
	}
}
