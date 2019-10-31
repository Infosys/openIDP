/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.checkmarx;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ChevkMarkzParser {

	private ChevkMarkzParser(){}

	public static void main(String[] args) {
		File jtlFile = new File("ScanReport.xml");
		DocumentBuilder dBuilder;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(jtlFile);
			doc.getDocumentElement().normalize();
			NodeList sampleList = doc.getElementsByTagName("CxXMLResults").item(0).getChildNodes();
			int high = 0;
			int medium = 0;
			int low = 0;
			for (int temp = 0; temp < sampleList.getLength(); temp++) {
				Node httpSampleNode = sampleList.item(temp);
				if (httpSampleNode != null && httpSampleNode.getNodeName() != null
						&& httpSampleNode.getNodeType() == Node.ELEMENT_NODE && (httpSampleNode.hasAttributes())) {
					NodeList childList = httpSampleNode.getChildNodes();
					for (int j = 0; j < childList.getLength(); j++) {
						Node childNode = childList.item(j);
						String childNodeName = childNode.getNodeName();
						if (childNodeName.equalsIgnoreCase("Result")) {
							NamedNodeMap childAttribute = childNode.getAttributes();
							Node severityAttr = childAttribute.getNamedItem("Severity");
							if (severityAttr.getNodeValue().equalsIgnoreCase("High")) {
								high++;
							}
							if ("Medium".equalsIgnoreCase(severityAttr.getNodeValue())) {
								medium++;
							}
							if ("Low".equalsIgnoreCase(severityAttr.getNodeValue())) {
								low++;
							}
						}
					}
				}
			}
			System.out.println("total high-->" + high);
			System.out.println("total medium-->" + medium);
			System.out.println("total Low-->" + low);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
