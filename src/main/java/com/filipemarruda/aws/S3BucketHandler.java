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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.filipemarruda.bundle.Properties;

/**
 * The Class S3BucketHandler.
 */
public class S3BucketHandler {

	/** The tm. */
	private final TransferManager tm;

	/** The s3. */
	private final AmazonS3 s3;

	/** The credentials. */
	private final AWSCredentials credentials;

	/** The access key. */
	private final String accessKey;

	/** The secret key. */
	private final String secretKey;

	/**
	 * Creates the file endpoint.
	 *
	 * @param bucketUrl
	 *            the bucket url
	 * @param fileName
	 *            the file name
	 * @return the file endpoint
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public String createFileEndpoint(final String bucketUrl, final String fileName)
			throws UnsupportedEncodingException {

		final String source = String.format(Properties.getString("S3FileEndpoint"),
				URLEncoder.encode(accessKey, "UTF-8"), URLEncoder.encode(secretKey, "UTF-8"), bucketUrl, fileName);

		return source;

	}

	/**
	 * Instantiates a new s3 bucket handler.
	 *
	 * @param accessKey
	 *            the access key
	 * @param secretKey
	 *            the secret key
	 */
	public S3BucketHandler(final String accessKey, final String secretKey) {

		this.accessKey = accessKey;
		this.secretKey = secretKey;
		this.credentials = new BasicAWSCredentials(accessKey, secretKey);
		this.tm = new TransferManager(credentials);
		this.s3 = tm.getAmazonS3Client();
		Region usWest2 = Region.getRegion(Regions.US_WEST_2);
		this.s3.setRegion(usWest2);

	}

	/**
	 * Put object.
	 *
	 * @param bucket
	 *            the bucket
	 * @param file
	 *            the file
	 * @param extension
	 *            the extension
	 * @return the generated file key
	 * @throws AmazonServiceException
	 *             the amazon service exception
	 * @throws AmazonClientException
	 *             the amazon client exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws FileNotFoundException
	 *             the file not found exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public String putObject(final String bucket, final File file, final String extension) throws AmazonServiceException,
			AmazonClientException, InterruptedException, FileNotFoundException, IOException {

		final String key = UUID.randomUUID().toString();
		final Upload upload = this.tm.upload(new PutObjectRequest(bucket, key + extension, file));
		upload.waitForCompletion();
		return key;

	}

	/**
	 * Gets the object.
	 *
	 * @param bucket
	 *            the bucket
	 * @param key
	 *            the key
	 * @return the object
	 */
	public S3Object getObject(final String bucket, final String key) {

		return this.s3.getObject(new GetObjectRequest(bucket, key));

	}

	/**
	 * Delete object.
	 *
	 * @param bucket
	 *            the bucket
	 * @param key
	 *            the key
	 */
	public void deleteObject(final String bucket, final String key) {
		this.s3.deleteObject(bucket, key);
	}

	/**
	 * Gets the access key.
	 *
	 * @return the access key
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * Gets the secret key.
	 *
	 * @return the secret key
	 */
	public String getSecretKey() {
		return secretKey;
	}

}
