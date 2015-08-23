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

@WebServlet("/convert.do")
@MultipartConfig
public class Convert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final S3BucketHandler s3 = new S3BucketHandler(Properties.getString("AWSAccessKey"), Properties.getString("AWSSecretKey"));
	private final MediaWorkflow mW = new MediaWorkflow(Properties.getString("EncodingEndpoint"), Properties.getString("EncodingUserId"), Properties.getString("EncodingUserKey"));
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Convert() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final PrintWriter out = response.getWriter();
		final String mediaId = request.getParameter("mediaId");
		
		try {
			
			if(mediaId != null && !"".equals(mediaId)){

				String status = mW.getStatus(mediaId);
				
				if("Ready to process".equals(status)){

					status = mW.process(mediaId);
					
				}
				
				String destination = null;
				
				if("Finished".equals(status)){
					
					destination = mW.getDestination(mediaId);
					
				}
				
				out.append(" {\"status\" : \""+status+"\", \"destination\" : \""+destination+"\"}");
				
			}
			
			
			
		} catch (XPathExpressionException | ParserConfigurationException | SAXException | InterruptedException e) {
			out.write("{ \"error\" : \"" + e.getMessage() + "\" }");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final PrintWriter out = response.getWriter();
		final String bucketName = Properties.getString("S3Bucket");
		final String bucketUrl = Properties.getString("S3Bucket") + ".s3.amazonaws.com";
		final Part filePart = request.getPart("videoFile");
		final String filename = RequestHelper.extractFileName(filePart);
		final String extension = filename.substring(filename.lastIndexOf("."));
		
	    try( InputStream fileContent = filePart.getInputStream()) {
	    	
	    	final String s3File = s3.putObject(bucketName, FileHandler.inputStreamToFile(fileContent), extension);
	    	final String source = s3.createFileEndpoint(bucketUrl, s3File + extension);
	    	final String destination = s3.createFileEndpoint(bucketUrl, s3File + ".mp4");
	    	final String mediaId = mW.start(source, destination);
	    	out.write("{ \"mediaId\" : " + mediaId + "}");
	    	
	    } catch (AmazonClientException | InterruptedException | XPathExpressionException | ParserConfigurationException | SAXException e) {
	    	out.write("{ \"error\" : \"" + e.getMessage() + "\" }");
		}    
	    
	}
	
}
