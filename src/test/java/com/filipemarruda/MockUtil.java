package com.filipemarruda;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.filipemarruda.bundle.Properties;

public class MockUtil {

	public static String createGetMediaIdMockXML(){
		return "<?xml version='1.0'?><query><userid>userId</userid><userkey>userKey</userkey><action>GetMediaInfo</action><MediaID>mediaId</MediaID></query>";
	}
	
	public static String createFailGetMediaIdMockXML(){
		return "<?xml version='1.0'?><query><userid>userId</userid><userkey>userKey</userkey><action>GetMediaInfo</action><MediaID>mediaId</MediaID></query";
	}
	
	public static String createMediaFormatResponseMockXML(){
		return "<?xml version='1.0'?><response><bitrate>2050k</bitrate><duration>7.58</duration><audio_bitrate>64.0k</audio_bitrate><audio_duration>7.522</audio_duration><video_duration>7.522</video_duration><video_codec>wmv2 (WMV2 / 0x32564D57)</video_codec><size>1920x1080</size><audio_codec>wmav2</audio_codec><audio_sample_rate>44100</audio_sample_rate><audio_channels>2</audio_channels><frame_rate>30</frame_rate><format>windows media</format></response>";
	}
	
	public static String createMediaStatusResponseMockXML(){
		return "<?xml version='1.0'?><response><id>42346299</id><userid>49677</userid><sourcefile>SourceFile</sourcefile><status>Processing</status><format><id>136610241</id><status>Processing</status><output>mp4</output><destination>Destination</destination></format><queue_time>0</queue_time></response>";
	}
	
	public static String createProcessMediaResponseMockXML(){
		return "<?xml version='1.0'?><response><message>Ok</message></response>";
	}

	public static String createSouceMock() throws UnsupportedEncodingException {

		final String source = String.format(Properties.getString("S3FileEndpoint"),
				URLEncoder.encode(Properties.getString("AWSAccessKey"), "UTF-8"),
				URLEncoder.encode(Properties.getString("AWSSecretKey"), "UTF-8"), Properties.getString("S3Bucket"),
				"20150820_135352.wmv");

		return source;

	}

	public static String createDestinationMock() throws UnsupportedEncodingException {

		final String destination = String.format(Properties.getString("S3FileEndpoint"),
				URLEncoder.encode(Properties.getString("AWSAccessKey"), "UTF-8"),
				URLEncoder.encode(Properties.getString("AWSSecretKey"), "UTF-8"), Properties.getString("S3Bucket"),
				"20150820_135352.mp4");

		return destination;

	}

	public static String createMockPayload(final String baseXML) throws UnsupportedEncodingException {

		String xml = String.format(baseXML, Properties.getString("EncodingUserId"),
				Properties.getString("EncodingUserKey"), createSouceMock(), createDestinationMock());

		return "xml=" + URLEncoder.encode(xml, "UTF8");
	}
}
