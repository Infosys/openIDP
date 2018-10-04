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

public class JobDetails {
	

	private static final Logger logger = Logger.getLogger(CreateChangeLog.class);

	private JobDetails() {
	}
	public static void createJobDetails(String server, String username, String password, String jobName,
			String destPath, String time, String appName) {
		String lastBuildId = null;
		String lastSuccessfulBuildId = null;
		String lastCompletedBuildId = null;
		String lastUnstableBuildId = null;
		String lastUnsuccessfulBuildId = null;
		String lastFailedBuildId = null;
		String score = null;
		try {
			String tempJobName = jobName.replaceAll("/", "/job/").replaceAll(" ", "%20");
			String webPage = server + "/job/" + tempJobName + "/api/xml";
			String path = null;
			String authString = username + ":" + password;
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			InputStream is = urlConnection.getInputStream();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(is);
			// parsing Last Build Id.
			Element root = document.getDocumentElement();
			NodeList lastBuildList = root.getElementsByTagName("lastSuccessfulBuild");
			for (int i = 0; i < lastBuildList.getLength(); i++) {
				Node node = lastBuildList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					if (element.getElementsByTagName("number").item(0) != null)
						lastSuccessfulBuildId = element.getElementsByTagName("number").item(0).getTextContent();
					else
						lastSuccessfulBuildId = "";
				}
			}
			// parsing last CompleteBuild Id..
			NodeList lastCompletedBuildList = root.getElementsByTagName("lastCompletedBuild");
			for (int i = 0; i < lastCompletedBuildList.getLength(); i++) {
				Node node = lastCompletedBuildList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					lastCompletedBuildId = element.getElementsByTagName("number").item(0).getTextContent();
				}
			}
			// parsing last UnstableBuild Id..
			NodeList lastUnstableBuildList = root.getElementsByTagName("lastUnstableBuild");
			for (int i = 0; i < lastUnstableBuildList.getLength(); i++) {
				Node node = lastUnstableBuildList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					lastUnstableBuildId = element.getElementsByTagName("number").item(0).getTextContent();
				}
			}
			// parsing last UnstableBuild Id..
			NodeList lastUnsuccessfulBuildList = root.getElementsByTagName("lastUnsuccessfulBuild");
			for (int i = 0; i < lastUnsuccessfulBuildList.getLength(); i++) {
				Node node = lastUnsuccessfulBuildList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					lastUnsuccessfulBuildId = element.getElementsByTagName("number").item(0).getTextContent();
				}
			}
			// parsing last UnstableBuild Id..
			NodeList lastFailedBuildList = root.getElementsByTagName("lastFailedBuild");
			for (int i = 0; i < lastFailedBuildList.getLength(); i++) {
				Node node = lastFailedBuildList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					lastFailedBuildId = element.getElementsByTagName("number").item(0).getTextContent();
				}
			}
			// parsing health score.
			NodeList list1 = root.getElementsByTagName("score");
			if (list1 != null && list1.getLength() > 0) {
				score = list1.item(0).getTextContent();
			}
			// creating a xml file
			if (lastBuildId != null) {
				path = destPath + "//" + appName + "_" + "jobDetails" + "_id_" + lastBuildId + "_" + time + ".xml";
			} else {
				path = destPath + "//" + appName + "_" + "jobDetails" + "_" + time + ".xml";
			}
			// NodeList children =
			// root.getElementsByTagName("lastSuccessfulBuild");
			NodeList children = root.getElementsByTagName("lastBuild");
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
				Element ts2 = doc2.createElement("lastSuccessfulBuild");
				ts2.setTextContent(lastSuccessfulBuildId);
				root2.appendChild(ts2);
				Element ts3 = doc2.createElement("lastCompletedBuildId");
				ts3.setTextContent(lastCompletedBuildId);
				root2.appendChild(ts3);
				Element ts4 = doc2.createElement("lastUnstableBuildId");
				ts4.setTextContent(lastUnstableBuildId);
				root2.appendChild(ts4);
				Element ts5 = doc2.createElement("lastUnsuccessfulBuildId");
				ts5.setTextContent(lastUnsuccessfulBuildId);
				root2.appendChild(ts5);
				Element ts7 = doc2.createElement("lastFailedBuildId");
				ts7.setTextContent(lastFailedBuildId);
				root2.appendChild(ts7);
				Element ts6 = doc2.createElement("score");
				ts6.setTextContent(score);
				root2.appendChild(ts6);
				DOMSource source1 = new DOMSource(doc2);
				StreamResult result2 = new StreamResult(f);
				tr.transform(source1, result2);
				logger.info("JobDetails created..!!");
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
