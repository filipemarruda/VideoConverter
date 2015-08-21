package com.filipemarruda.http;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnector implements IHttpConnector {
	
	public String simplePost(final String url, final String payload) throws IOException{
		
		String result = null;
			
		final URL server = new URL(url);		
		final HttpURLConnection urlConnection = (HttpURLConnection) server.openConnection();
		urlConnection.setRequestMethod("POST");			
		urlConnection.setDoOutput(true);			
		urlConnection.setConnectTimeout(60000);			
		urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		
		try(final java.io.BufferedWriter out = new java.io.BufferedWriter(new java.io.OutputStreamWriter( urlConnection.getOutputStream() ))){
			
			out.write(payload);			
			out.flush();
			
		}
		
		urlConnection.connect();	
		
		try (final java.io.InputStream is = urlConnection.getInputStream()){	

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
