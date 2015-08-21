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


public class S3BucketHandlerTest {

	
	@Test
	public void testS3BucketHandler_1()
		throws Exception {
		String accessKey = Properties.getString("AWSAccessKey");
		String secretKey = Properties.getString("AWSSecretKey");

		S3BucketHandler result = new S3BucketHandler(accessKey, secretKey);

		assertNotNull(result);
		assertEquals(accessKey, result.getAccessKey());
		assertEquals(secretKey, result.getSecretKey());
	}

	@Test(expected = com.amazonaws.AmazonClientException.class)
	public void testPutObject_1()
		throws Exception {

		String accessKey = "";
		String secretKey = "";
		
		S3BucketHandler fixture = new S3BucketHandler(accessKey, secretKey);
		String bucket = "";
		File file = createSampleFile();

		fixture.putObject(bucket, file);

	}
	
	@Test
	public void testPutObject_2()
		throws Exception {

		String accessKey = Properties.getString("AWSAccessKey");
		String secretKey = Properties.getString("AWSSecretKey");
		
		S3BucketHandler fixture = new S3BucketHandler(accessKey, secretKey);
		String bucket = "filipemarruda-s3-bucket";
		File file = createSampleFile();

		String key = fixture.putObject(bucket, file);
		
		assertNotNull(key);

		S3Object object = fixture.getObject(bucket, key);
		
		assertNotNull(object);
		
		fixture.deleteObject(bucket, key);
		
		object = null;
		
		try{
			
			object = fixture.getObject(bucket, key);
			
		}catch(AmazonS3Exception e){
			
			assertTrue(e.getMessage().contains("The specified key does not exist."));
			
		}

	}
	

    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }
	
}