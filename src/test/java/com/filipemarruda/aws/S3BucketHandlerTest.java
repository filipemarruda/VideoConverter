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
package com.filipemarruda.aws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.junit.Test;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.filipemarruda.bundle.Properties;


/**
 * JUnit test for S3BucketHandler.
 */
public class S3BucketHandlerTest {

	
	/** The access key. */
	private String accessKey = Properties.getString("AWSAccessKey");
	
	/** The secret key. */
	private String secretKey = Properties.getString("AWSSecretKey");
	
	/**
	 * Test case for constructor.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void s3BucketHandler_1()
		throws Exception {

		final S3BucketHandler result = new S3BucketHandler(accessKey, secretKey);

		assertNotNull(result);
		assertEquals(accessKey, result.getAccessKey());
		assertEquals(secretKey, result.getSecretKey());
	}

	/**
	 * Test case for method putObject.
	 *
	 * @throws Exception the exception
	 */
	@Test(expected = com.amazonaws.AmazonClientException.class)
	public void putObject_1()
		throws Exception {
		
		final S3BucketHandler fixture = new S3BucketHandler("", "");
		final String bucket = "";
		final File file = createSampleFile();

		fixture.putObject(bucket, file, ".txt");

	}
	
	/**
	 * Test case for method putObject.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void putObject_2()
		throws Exception {
		
		final S3BucketHandler fixture = new S3BucketHandler(accessKey, secretKey);
		final String bucket = "filipemarruda-s3-bucket";
		final File file = createSampleFile();
		final String key = fixture.putObject(bucket, file, ".txt");
		
		assertNotNull(key);

		S3Object object = fixture.getObject(bucket, key + ".txt");
		
		assertNotNull(object);
		
		fixture.deleteObject(bucket, key + ".txt");
		
		object = null;
		
		try{
			
			object = fixture.getObject(bucket, key + ".txt");
			
		}catch(AmazonS3Exception e){
			
			assertTrue(e.getMessage().contains("The specified key does not exist."));
			
		}

	}
	
    /**
     * Creates a sample file.
     *
     * @return the file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static File createSampleFile() throws IOException {

    	final File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();
        
        final Writer writer = new OutputStreamWriter(new FileOutputStream(file));

        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }
	
}