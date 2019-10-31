/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.tools.getreports;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GetBuildId {
	private static final Logger logger = Logger.getLogger(GetBuildId.class);
	
	

	private GetBuildId() {
		// TODO Auto-generated constructor stub
	}



	public static String getId(String server, String username, String password, String name) {
		try {
			String webPage = server + "/job/" + name + "/lastBuild/api/xml";
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
			Element root = document.getDocumentElement();
			NodeList list = root.getElementsByTagName("id");
			if (list != null && list.getLength() > 0) {
				return list.item(0).getTextContent();
			}
			return null;
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage());
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			logger.error(e1.getMessage());
		}
		return null;
	}
}
