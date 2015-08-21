package com.filipemarruda.encoding;

import java.io.IOException;
import java.net.URLEncoder;

import com.filipemarruda.bundle.Properties;
import com.filipemarruda.http.HttpConnector;

public class EcodingHandler {
	
	private final String encodingEndpoint;
	private final String userId;
	private final String userKey;
	
	public EcodingHandler(final String encodingEndpoint, final String userId, final String userKey){
		this.encodingEndpoint = encodingEndpoint;
		this.userId = userId;
		this.userKey = userKey;
	}
	
	public String getMediaInfo(final String mediaId) throws IOException{
		
		String xml = String.format(
			Properties.getString("EncodingGetMediaInfoXML"),
			userId,
			userKey,
			mediaId
		);
			
		String payload = "xml=" + URLEncoder.encode(xml, "UTF8");
		
		return HttpConnector.simplePost(encodingEndpoint, payload);
		
	}
	
}
