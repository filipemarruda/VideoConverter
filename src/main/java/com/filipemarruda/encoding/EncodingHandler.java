package com.filipemarruda.encoding;

import java.io.IOException;
import java.net.URLEncoder;

import com.filipemarruda.bundle.Properties;
import com.filipemarruda.http.HttpConnector;
import com.filipemarruda.http.IHttpConnector;

public class EncodingHandler {

	private final String encodingEndpoint;
	private final String userId;
	private final String userKey;
	private IHttpConnector httpConnector;

	public EncodingHandler(final String encodingEndpoint, final String userId, final String userKey) {
		this.encodingEndpoint = encodingEndpoint;
		this.userId = userId;
		this.userKey = userKey;
	}

	public String getMediaInfo(final String mediaId) throws IOException {

		final String xml = String.format(Properties.getString("EncodingGetMediaInfoXML"), userId, userKey, mediaId);
		return performAPICall(xml);

	}

	public String addMedia(final String source, final String destination) throws IOException {

		final String xml = String.format(Properties.getString("EncodingAddMediaXML"), userId, userKey, source,
				destination);
		return performAPICall(xml);

	}

	public String addMediaBenchmark(final String source, final String destination) throws IOException {

		final String xml = String.format(Properties.getString("EncodingAddMediaBenchmarkXML"), userId, userKey, source,
				destination);
		return performAPICall(xml);

	}

	public String processMedia(final String mediaId, final String format) throws IOException {

		final String xml = String.format(Properties.getString("EncodingProcessMediaXML"), userId, userKey, mediaId,
				format);
		return performAPICall(xml);

	}
	
	public String getStatus(final String mediaId) throws IOException {

		final String xml = String.format(Properties.getString("EncodingGetStatusXML"), userId, userKey, mediaId);
		return performAPICall(xml);
		
	}
	
	private String performAPICall(final String xml) throws IOException{
		
		final String payload = "xml=" + URLEncoder.encode(xml, "UTF8");
		return getHttpConnector().simplePost(encodingEndpoint, payload);
	}

	protected IHttpConnector getHttpConnector() {
		if (httpConnector == null) {
			this.httpConnector = new HttpConnector();
		}
		return httpConnector;
	}

	// Just for testing
	protected void setHttpConnector(IHttpConnector httpConnector) {
		this.httpConnector = httpConnector;
	}

}
