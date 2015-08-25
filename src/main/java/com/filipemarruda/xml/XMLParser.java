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

/**
 * The Class XMLParser.
 */
public class XMLParser {

	/**
	 * Private Constructor will prevent the instantiation of this class directly
	 */
	private XMLParser() {
	};

	/**
	 * Gets the media id.
	 *
	 * @param xml
	 *            the xml
	 * @return the media id
	 * @throws XPathExpressionException
	 *             the xpath expression exception
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String getMediaId(final String xml)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		return getStringFromXML("//MediaID", xml);
	}

	/**
	 * Gets the media status.
	 *
	 * @param xml
	 *            the xml
	 * @return the media status
	 * @throws XPathExpressionException
	 *             the xpath expression exception
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String getMediaStatus(final String xml)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		String status = getStringFromXML("//status", xml);
		final String progress = getStringFromXML("//progress", xml);
		if(!"New".equals(status) && !"Ready to process".equals(status) && !"Finished".equals(status) && progress != null && !"".equals(progress)){
			status = status + "("+progress+")";
		}
		return status;
	}

	/**
	 * Gets the destination.
	 *
	 * @param xml
	 *            the xml
	 * @return the destination
	 * @throws XPathExpressionException
	 *             the xpath expression exception
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String getDestination(final String xml)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		return getStringFromXML("//destination", xml);
	}

	/**
	 * Gets the process media message.
	 *
	 * @param xml
	 *            the xml
	 * @return the process media message
	 * @throws XPathExpressionException
	 *             the xpath expression exception
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String getProcessMediaMessage(final String xml)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		return getStringFromXML("//message", xml);
	}

	/**
	 * Gets the media format from response.
	 *
	 * @param xml
	 *            the xml
	 * @return the media format from response
	 * @throws XPathExpressionException
	 *             the xpath expression exception
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String getMediaFormatFromResponse(final String xml)
			throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {

		final StringBuilder format = new StringBuilder();

		format.append("<output>mp4</output>");
		format.append("<bitrate>" + getStringFromXML("//bitrate", xml) + "</bitrate>");
		format.append("<duration>" + getStringFromXML("//duration", xml) + "</duration>");
		format.append("<size>0x360</size>");
		format.append("<bitrate>512k</bitrate>");
		format.append("<audio_bitrate>64k</audio_bitrate>");
		format.append("<preset>2</preset>");

		return format.toString();
	}

	/**
	 * Gets the string from xml.
	 *
	 * @param xpath
	 *            the xpath
	 * @param xml
	 *            the xml
	 * @return the string from xml
	 * @throws ParserConfigurationException
	 *             the parser configuration exception
	 * @throws SAXException
	 *             the SAX exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws XPathExpressionException
	 *             the xpath expression exception
	 */
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
