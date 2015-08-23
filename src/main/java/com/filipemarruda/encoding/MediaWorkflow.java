package com.filipemarruda.encoding;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.filipemarruda.xml.XMLParser;

public class MediaWorkflow {

	private EncodingHandler encodingHandler;

	public MediaWorkflow(final String encodingEndpoint, final String userId, final String userKey) {
		encodingHandler = new EncodingHandler(encodingEndpoint, userId, userKey);
	}

	public String start(final String source, final String destination)
			throws IOException, XPathExpressionException, ParserConfigurationException, SAXException, InterruptedException {

		final String addMediaBenchmarkResponse = encodingHandler.addMediaBenchmark(source, destination);
		final String mediaId = XMLParser.getMediaId(addMediaBenchmarkResponse);

		return mediaId;

	}

	public String getStatus(final String mediaId)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, InterruptedException {

		final String getStatusResponse = encodingHandler.getStatus(mediaId);
		return XMLParser.getMediaStatus(getStatusResponse);

	}

	public String getDestination(final String mediaId)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException, InterruptedException {

		final String mediaInfoResponse = encodingHandler.getStatus(mediaId);
		String destination = XMLParser.getDestination(mediaInfoResponse);
		destination = "http://" + destination.substring(destination.lastIndexOf("@")+1);
		
		return destination;

	}

	public String process(final String mediaId)
			throws IOException, XPathExpressionException, ParserConfigurationException, SAXException, InterruptedException {

		final String mediaInfoResponse = encodingHandler.getMediaInfo(mediaId);
		final String format = XMLParser.getMediaFormatFromResponse(mediaInfoResponse);
		final String processMediaResponse = encodingHandler.processMedia(mediaId, format);
		return XMLParser.getProcessMediaMessage(processMediaResponse);

	}

	protected EncodingHandler getEncodingHandler() {
		return encodingHandler;
	}

	// just for testing
	protected void setEncodingHandler(EncodingHandler encodingHandler) {
		this.encodingHandler = encodingHandler;
	}

}
