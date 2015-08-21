package com.filipemarruda.http;

import java.io.IOException;

public interface IHttpConnector {

	public String simplePost(final String url, final String payload) throws IOException;
	
}
