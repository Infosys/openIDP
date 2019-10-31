/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.tools.getreports;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CodeCoverage {
	private static final Logger logger = Logger.getLogger(CreateChangeLog.class);
	

	private CodeCoverage() {
		// TODO Auto-generated constructor stub
	}


	public static void createcodeCoverage(String server, String username, String password, String jobName,
			String destPath, String time, String appName) {
		String branchCoverage = null;
		String classCoverage = null;
		String complexityScore = null;
		String instructionCoverage = null;
		String lineCoverage = null;
		String methodCoverage = null;
		try {
			String tempJobName = jobName.replaceAll("/", "/job/").replaceAll(" ", "%20");
			String webPage = server + "/job/" + tempJobName + "/lastBuild/jacoco/api/xml";
			String path = null;
			String authString = username + ":" + password;
			String stage = tempJobName.substring(tempJobName.lastIndexOf("_") + 1);
			System.out.println("Stage is " + stage);
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			InputStream is = urlConnection.getInputStream();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(is);
			Element root = document.getDocumentElement();
			NodeList branchCoverageList = root.getElementsByTagName("branchCoverage");
			for (int i = 0; i < branchCoverageList.getLength(); i++) {
				Node node = branchCoverageList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					branchCoverage = element.getElementsByTagName("percentageFloat").item(0).getTextContent();
				}
			}
			NodeList classCoverageList = root.getElementsByTagName("classCoverage");
			for (int i = 0; i < classCoverageList.getLength(); i++) {
				Node node = classCoverageList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					classCoverage = element.getElementsByTagName("percentageFloat").item(0).getTextContent();
				}
			}
			NodeList complexityScoreList = root.getElementsByTagName("complexityScore");
			for (int i = 0; i < complexityScoreList.getLength(); i++) {
				Node node = complexityScoreList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					complexityScore = element.getElementsByTagName("percentageFloat").item(0).getTextContent();
				}
			}
			NodeList instructionCoverageList = root.getElementsByTagName("instructionCoverage");
			for (int i = 0; i < instructionCoverageList.getLength(); i++) {
				Node node = instructionCoverageList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					instructionCoverage = element.getElementsByTagName("percentageFloat").item(0).getTextContent();
				}
			}
			NodeList lineCoverageList = root.getElementsByTagName("lineCoverage");
			for (int i = 0; i < lineCoverageList.getLength(); i++) {
				Node node = lineCoverageList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					lineCoverage = element.getElementsByTagName("percentageFloat").item(0).getTextContent();
				}
			}
			NodeList methodCoverageList = root.getElementsByTagName("methodCoverage");
			for (int i = 0; i < methodCoverageList.getLength(); i++) {
				Node node = methodCoverageList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					methodCoverage = element.getElementsByTagName("percentageFloat").item(0).getTextContent();
				}
			}
			// creating a xml file
			if (branchCoverage != null) {
				path = destPath + "//" + appName + "_" + "codecheck" + "_" + time + ".xml";
			} else {
				path = destPath + "//" + appName + "_" + "codecheck" + "_" + time + ".xml";
			}
			NodeList children = root.getElementsByTagName("previousResult");
			Transformer tr;
			try {
				tr = TransformerFactory.newInstance().newTransformer();
				tr.setOutputProperty(OutputKeys.INDENT, "yes");
				File f = new File(path);
				StreamResult result = new StreamResult(f);
				DOMSource source = new DOMSource(document);
				source.setNode(children.item(0));
				tr.transform(source, result);
				File f1 = new File(path);
				Document doc2 = builder.parse(f1);
				Element root2 = doc2.getDocumentElement();
				Element ts2 = doc2.createElement("branchCoverage");
				ts2.setTextContent(branchCoverage);
				root2.appendChild(ts2);
				Element ts3 = doc2.createElement("classCoverage");
				ts3.setTextContent(classCoverage);
				root2.appendChild(ts3);
				Element ts4 = doc2.createElement("complexityScore");
				ts4.setTextContent(complexityScore);
				root2.appendChild(ts4);
				Element ts5 = doc2.createElement("instructionCoverage");
				ts5.setTextContent(instructionCoverage);
				root2.appendChild(ts5);
				Element ts6 = doc2.createElement("lineCoverage");
				ts6.setTextContent(lineCoverage);
				root2.appendChild(ts6);
				Element ts7 = doc2.createElement("methodCoverage");
				ts7.setTextContent(methodCoverage);
				root2.appendChild(ts7);
				DOMSource source1 = new DOMSource(doc2);
				StreamResult result2 = new StreamResult(f);
				tr.transform(source1, result2);
				logger.info("Code Coverage Report created..!!");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (SAXException e1) {
			logger.error(e1.getMessage());
		} catch (ParserConfigurationException e1) {
			logger.error(e1.getMessage());
		}
	}
}
