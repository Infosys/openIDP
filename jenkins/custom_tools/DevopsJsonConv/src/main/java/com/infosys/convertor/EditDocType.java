/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EditDocType {
	private static final Logger logger = Logger.getLogger(EditDocType.class);
	//private static ArrayList<String> messagesforPunit = new ArrayList<>();
	private static MessageObject messageObject = new MessageObject();
	private EditDocType() {
	}

	

	public static String getCharacterDataFromElement(Element e) {
		if ((Node) e.getFirstChild() instanceof CharacterData) {
			CharacterData cd = (CharacterData) e.getFirstChild();
			return cd.getData();
		}
		return "";
	}

	public static void edit(String filepath) {
		try {
			ArrayList<String> messagesforPunit = messageObject.getMessagesforPunit();
			Document xmlDoc = getDocument(filepath);
			if (filepath.contains("pythontest")) {
				NodeList l = xmlDoc.getElementsByTagName("failure");
				for (int i = 0; i < l.getLength(); i++)
					messagesforPunit.add(getCharacterDataFromElement((Element) (l.item(i))));
			}
			saveToXml(xmlDoc, filepath);
		} catch (Exception ex) { // For simplicity Exception is used instead of
			logger.error(ex);
		}
	}

	public static ArrayList<String> getMessagesforPunit() {
		return messageObject.getMessagesforPunit();
	}

	public static void setMessagesforPunit(ArrayList<String> messagesforPunit) {
		messageObject.setMessagesforPunit(messagesforPunit);
	}

	public static Document getDocument(String filePath) throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		docFactory.setCoalescing(true);
		docFactory.setValidating(false);
		docFactory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		docFactory.setAttribute("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		docFactory.setAttribute("http://xml.org/sax/features/namespaces", false);
		docFactory.setAttribute("http://xml.org/sax/features/validation", false);
		// docFactory.setAttribute("http://cobertura.sourceforge.net/xml/coverage-04.dtd",
		// false);
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document xmlDoc = null;
		try {
			xmlDoc = docBuilder.parse(filePath);
		} catch (SAXException se) {
			logger.error(se);
		} catch (IOException ie) {
			logger.error(ie);
		}
		return xmlDoc;
	}

	public static void saveToXml(Document xmlDoc, String filePath) throws TransformerException {
		DOMSource source = new DOMSource(xmlDoc);
		StreamResult result = new StreamResult(new File(filePath));
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(source, result);
	}
}
