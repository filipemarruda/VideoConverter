package com.filipemarruda.aws;

import java.io.File;
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

	private TransferManager tm;
	private AmazonS3 s3;

	private AWSCredentials credentials = null;
	private String accessKey;
	private String secretKey;
	
	public S3BucketHandler(final String accessKey, final String secretKey){
		
		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.credentials = new BasicAWSCredentials(accessKey, secretKey);
		
	}
	
	public String putObject(final String bucket, final File file) throws AmazonServiceException, AmazonClientException, InterruptedException{
		
		final String key = UUID.randomUUID().toString();
		Upload upload = getTm().upload(new PutObjectRequest(bucket, key, file));
		upload.waitForCompletion();
		return key;
		
	}
	
	public S3Object getObject(final String bucket, final String key){
		
		return getS3().getObject(new GetObjectRequest(bucket, key));
		
	}

	public void deleteObject(final String bucket, final String key){
		getS3().deleteObject(bucket, key);
	}

	private TransferManager getTm() {
		
		if(tm == null){
			
			this.tm = new TransferManager(credentials);
	        this.s3 = tm.getAmazonS3Client();
	        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
	        this.s3.setRegion(usWest2);
	        
		}
		
		return tm;
	}
	
	private AmazonS3 getS3(){
		
		if(this.s3 == null){
			this.s3 = getTm().getAmazonS3Client();
		}
		
		return this.s3;
		
	}
	
	public String getAccessKey() {
		return accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}
	
	
}
