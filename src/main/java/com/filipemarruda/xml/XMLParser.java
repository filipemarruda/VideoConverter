package com.filipemarruda.xml;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLParser {

	private XMLParser() {
		/*
		 * Private Constructor will prevent the instantiation of this class
		 * directly
		 */
	};

	public static String getMediaId(final String xml)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		return getStringFromXML("//MediaID", xml);
	}
	
	public static String getMediaStatus(final String xml)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		return getStringFromXML("//status", xml);
	}
	
	public static String getDestination(final String xml)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		return getStringFromXML("//destination", xml);
	}
	
	public static String getProcessMediaMessage(final String xml)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		return getStringFromXML("//message", xml);
	}
	
	public static String getMediaFormatFromResponse(final String xml)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		
		final StringBuilder format = new StringBuilder();
		
		format.append("<output>mp4</output>");
		format.append("<bitrate>"+getStringFromXML("//bitrate", xml)+"</bitrate>");
		format.append("<duration>"+getStringFromXML("//duration", xml)+"</duration>");
		format.append("<size>0x360</size>");
		format.append("<bitrate>512k</bitrate>");
		format.append("<audio_bitrate>64k</audio_bitrate>");
		format.append("<preset>2</preset>");
		
		return format.toString();
	}

	private static String getStringFromXML(final String xpath, final String xml)
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document = builder.parse(new InputSource(new StringReader(xml)));
		final XPathFactory xPathfactory = XPathFactory.newInstance();
		final XPath xpathObj = xPathfactory.newXPath();
		final XPathExpression expr = xpathObj.compile(xpath);

		return expr.evaluate(document, XPathConstants.STRING).toString();

	}

}
