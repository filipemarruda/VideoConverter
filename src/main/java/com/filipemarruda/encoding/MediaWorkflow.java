package com.filipemarruda.encoding;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.filipemarruda.xml.XMLParser;

public class MediaWorkflow {
	
	private EncodingHandler encodingHandler;

	public MediaWorkflow(final String encodingEndpoint, final String userId, final String userKey){
		encodingHandler = new EncodingHandler(encodingEndpoint, userId, userKey);
	}
	
	public String start(final String source, final String destination) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException{
		
		final String addMediaBenchmarkResponse =  encodingHandler.addMediaBenchmark(source, destination);
		final String mediaId = XMLParser.getMediaId(addMediaBenchmarkResponse);
		final String mediaInfoResponse = encodingHandler.getMediaInfo(mediaId);
		final String format = XMLParser.getMediaFormatFromResponse(mediaInfoResponse);
		encodingHandler.processMedia(mediaId, format);
		
		return mediaId;
		
	}
	
	protected EncodingHandler getEncodingHandler() {
		return encodingHandler;
	}

	// just for testing
	protected void setEncodingHandler(EncodingHandler encodingHandler) {
		this.encodingHandler = encodingHandler;
	}
	
}
