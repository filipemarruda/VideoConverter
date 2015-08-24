package com.filipemarruda.http;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.http.Part;

import org.junit.Test;

public class RequestHelperTest {

	@Test
	public void extractFileName(){
		Part part = new Part() {
			
			@Override
			public void write(String arg0) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public long getSize() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public InputStream getInputStream() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<String> getHeaders(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<String> getHeaderNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getHeader(String arg0) {
				String result = null;
				if("content-disposition".equals(arg0)){
					result = "name=\"bla\";filename=\"filename\";";
				}
				return result;
			}
			
			@Override
			public String getContentType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void delete() throws IOException {
				// TODO Auto-generated method stub
				
			}
		};
		
		final String filename = RequestHelper.extractFileName(part);
		assertEquals(filename, "filename");
	}
	
}
