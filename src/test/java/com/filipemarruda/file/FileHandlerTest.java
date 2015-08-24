package com.filipemarruda.file;

import static org.junit.Assert.*;

import java.io.File;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import org.junit.Test;

public class FileHandlerTest {

	@Test
	public void testInputStreamToFile()
		throws Exception {
		InputStream is = new ByteArrayInputStream(new byte[10]);

		File result = FileHandler.inputStreamToFile(is);
		assertNotNull(result);
	}

	
}