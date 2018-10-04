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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CreateChangeLog {
	private static final Logger logger = Logger.getLogger(CreateChangeLog.class);

	private CreateChangeLog() {
	}

	public static String createChangeLog(String server, String username, String password, String jobName,
			String destPath, String time, String appName) {
		String id = null;
		String status = null;
		String duration = null;
		String timestamp = null;
		PrintWriter out = null;
		String remoteUrl = null;
		try {
			String tempJobName = jobName.replaceAll("/", "/job/").replaceAll(" ", "%20");
			String webPage = server + "/job/" + tempJobName + "/lastBuild/api/xml";
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
			out = new PrintWriter(destPath + "/temp.xml");
			out.println(getStringFromDocument(document));
			Element root = document.getDocumentElement();
			NodeList remoteUrlNode = root.getElementsByTagName("remoteUrl");
			if (remoteUrlNode != null && remoteUrlNode.getLength() > 0) {
				remoteUrl = remoteUrlNode.item(0).getTextContent();
				System.out.println("Remote Url1 is " + remoteUrl);
			}
			NodeList list = root.getElementsByTagName("id");
			// getting build Id
			if (list != null && list.getLength() > 0) {
				id = list.item(0).getTextContent();
			}
			List<String> names = new ArrayList<>();
			NodeList list1 = root.getElementsByTagName("culprit");
			if (list1 != null && list1.getLength() > 0) {
				for (int i = 0; i < list1.getLength(); i++) {
					NodeList list2 = list1.item(i).getChildNodes();
					if (list2 != null && list2.getLength() > 0) {
						for (int j = 0; j < list2.getLength(); j++) {
							if (list2.item(j).getNodeName().equals("fullName")) {
								names.add(list2.item(j).getTextContent());
							}
						}
					}
				}
			}
			NodeList list2 = root.getElementsByTagName("result");
			if (list2 != null && list2.getLength() > 0) {
				status = list2.item(0).getTextContent();
			}
			NodeList list3 = root.getElementsByTagName("estimatedDuration");
			if (list3 != null && list3.getLength() > 0) {
				duration = list3.item(0).getTextContent();
			}
			NodeList list4 = root.getElementsByTagName("timestamp");
			if (list4 != null && list4.getLength() > 0) {
				timestamp = list4.item(0).getTextContent();
			}
			if (id != null) {
				path = destPath + "//" + appName + "_" + "changelog" + "_id_" + id + "_" + time + ".xml";
			} else {
				path = destPath + "//" + appName + "_" + "changelog" + "_" + time + ".xml";
			}
			NodeList children = root.getElementsByTagName("changeSet");
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
				Element ts1 = doc2.createElement("buildId");
				ts1.setTextContent(id);
				root2.appendChild(ts1);
				Element ts2 = doc2.createElement("appName");
				ts2.setTextContent(appName);
				root2.appendChild(ts2);
				Element ts5 = doc2.createElement("buildStatus");
				ts5.setTextContent(status);
				root2.appendChild(ts5);
				Element ts3 = doc2.createElement("culprits");
				if (!names.isEmpty()) {
					for (String n : names) {
						Element ts4 = doc2.createElement("name");
						ts4.setTextContent(n);
						ts3.appendChild(ts4);
					}
				}
				root2.appendChild(ts3);
				Element ts8 = doc2.createElement("scmurl");
				ts8.setTextContent(remoteUrl);
				root2.appendChild(ts8);
				Element ts6 = doc2.createElement("duration");
				ts6.setTextContent(duration);
				root2.appendChild(ts6);
				Element ts7 = doc2.createElement("timestamp");
				ts7.setTextContent(timestamp);
				root2.appendChild(ts7);
				DOMSource source1 = new DOMSource(doc2);
				StreamResult result2 = new StreamResult(f);
				tr.transform(source1, result2);
				logger.info("Changelog created..!!");
			} catch (TransformerConfigurationException e) {
				logger.error(e.getMessage(), e);
			} catch (TransformerFactoryConfigurationError e) {
				logger.error(e.getMessage(), e);
			} catch (TransformerException e) {
				logger.error(e.getMessage(), e);
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (SAXException e1) {
			logger.error(e1.getMessage(), e1);
		} catch (ParserConfigurationException e1) {
			logger.error(e1.getMessage(), e1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return id;
	}

	public static String getStringFromDocument(Document doc) {
		StringWriter writer = null;
		try {
			DOMSource domSource = new DOMSource(doc);
			writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			return writer.toString();
		} catch (TransformerException ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
}
