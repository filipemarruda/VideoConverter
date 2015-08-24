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
package com.filipemarruda.http.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.amazonaws.AmazonClientException;
import com.filipemarruda.aws.S3BucketHandler;
import com.filipemarruda.bundle.Properties;
import com.filipemarruda.encoding.MediaWorkflow;
import com.filipemarruda.file.FileHandler;
import com.filipemarruda.http.RequestHelper;

/**
 * The Class Convert.
 */
@WebServlet("/convert.do")
@MultipartConfig
public class Convert extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** The s3. */
	private final S3BucketHandler s3 = new S3BucketHandler(Properties.getString("AWSAccessKey"),
			Properties.getString("AWSSecretKey"));

	/** The MediaWorkflow. */
	private final MediaWorkflow mW = new MediaWorkflow(Properties.getString("EncodingEndpoint"),
			Properties.getString("EncodingUserId"), Properties.getString("EncodingUserKey"));

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Convert() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final PrintWriter out = response.getWriter();
		final String mediaId = request.getParameter("mediaId");

		try {

			if (mediaId != null && !"".equals(mediaId)) {

				String status = mW.getStatus(mediaId);

				if ("Ready to process".equals(status)) {

					status = mW.process(mediaId);

				}

				String destination = null;

				if ("Finished".equals(status)) {

					destination = mW.getDestination(mediaId);

				}

				out.append(" {\"status\" : \"" + status + "\", \"destination\" : \"" + destination + "\"}");

			}

		} catch (XPathExpressionException | ParserConfigurationException | SAXException | InterruptedException e) {
			out.write("{ \"error\" : \"" + e.getMessage() + "\" }");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final PrintWriter out = response.getWriter();
		final String bucketName = Properties.getString("S3Bucket");
		final String bucketUrl = Properties.getString("S3Bucket") + ".s3.amazonaws.com";
		final Part filePart = request.getPart("videoFile");
		final String filename = RequestHelper.extractFileName(filePart);
		final String extension = filename.substring(filename.lastIndexOf("."));

		try (InputStream fileContent = filePart.getInputStream()) {

			final String s3File = s3.putObject(bucketName, FileHandler.inputStreamToFile(fileContent), extension);
			final String source = s3.createFileEndpoint(bucketUrl, s3File + extension);
			final String destination = s3.createFileEndpoint(bucketUrl, s3File + ".mp4");
			final String mediaId = mW.start(source, destination);
			out.write("{ \"mediaId\" : " + mediaId + "}");

		} catch (AmazonClientException | InterruptedException | XPathExpressionException | ParserConfigurationException
				| SAXException e) {
			out.write("{ \"error\" : \"" + e.getMessage() + "\" }");
		}

	}

}
