package com.filipemarruda.aws;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

public class S3BucketHandler {

	private final TransferManager tm;
	private final AmazonS3 s3;

	private final AWSCredentials credentials;
	private final String accessKey;
	private final String secretKey;
	
	public S3BucketHandler(final String accessKey, final String secretKey){
		
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.credentials = new BasicAWSCredentials(accessKey, secretKey);
		this.tm = new TransferManager(credentials);
        this.s3 = tm.getAmazonS3Client();
        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        this.s3.setRegion(usWest2);
		
	}
	
	public String putObject(final String bucket, final File file, final String extension) throws AmazonServiceException, AmazonClientException, InterruptedException, FileNotFoundException, IOException{

		final String key = UUID.randomUUID().toString() + extension;
		final Upload upload = this.tm.upload(new PutObjectRequest(bucket, key, file));
		upload.waitForCompletion();
		return key;
		
	}
	
	public S3Object getObject(final String bucket, final String key){
		
		return this.s3.getObject(new GetObjectRequest(bucket, key));
		
	}
	
	public void deleteObject(final String bucket, final String key){
		this.s3.deleteObject(bucket, key);
	}
	
	public String getAccessKey() {
		return accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}
	
	
}
