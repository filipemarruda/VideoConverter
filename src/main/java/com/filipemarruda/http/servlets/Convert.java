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

import com.amazonaws.AmazonClientException;
import com.filipemarruda.aws.S3BucketHandler;
import com.filipemarruda.bundle.Properties;
import com.filipemarruda.encoding.EncodingHandler;
import com.filipemarruda.file.FileHandler;
import com.filipemarruda.http.RequestHelper;

@WebServlet("/convert.do")
@MultipartConfig
public class Convert extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Convert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath()); //$NON-NLS-1$
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
		
		
		final S3BucketHandler s3 = new S3BucketHandler(Properties.getString("AWSAccessKey"), Properties.getString("AWSSecretKey"));
		final EncodingHandler eH = new EncodingHandler(Properties.getString("EncodingEndpoint"), Properties.getString("EncodingUserId"), Properties.getString("EncodingUserKey"));
	    
	    try( InputStream fileContent = filePart.getInputStream()) {
	    	
	    	final String s3File = s3.putObject(bucketName, FileHandler.inputStreamToFile(fileContent), extension);
	    	final String source = s3.createFileEndpoint(bucketUrl, s3File + extension);
	    	final String destination = s3.createFileEndpoint(bucketUrl, s3File + ".wmv");
	    	
	    	System.out.println("Source: " + source);
	    	System.out.println("Destination: " + destination);
	    	
	    	final String mediaId = eH.addMedia(source, destination);
	    	out.write("MediaId : "+mediaId);
	    	
	    } catch (AmazonClientException | InterruptedException e) {
			e.printStackTrace();
		}    
	    
	}
	
}
