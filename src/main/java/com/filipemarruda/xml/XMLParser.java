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

	
	private XMLParser(){
		/*Private Constructor will prevent 
	       * the instantiation of this class directly*/
	};
	
	public static String getStringFromXML(final String xpath, final String xml) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
		
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document = builder.parse(new InputSource(new StringReader(xml)));
		final XPathFactory xPathfactory = XPathFactory.newInstance();
		final XPath xpathObj = xPathfactory.newXPath();
		final XPathExpression expr = xpathObj.compile(xpath);
		
		return expr.evaluate(document, XPathConstants.STRING).toString();
		
	}
	
	
}
