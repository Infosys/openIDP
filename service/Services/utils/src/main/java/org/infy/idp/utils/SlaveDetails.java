/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

/**
 * The class SlaveDetails contains methods to check slave status
 * 
 * @author Infosys
 *
 */
@Component
public class SlaveDetails {

	@Autowired
	private ConfigurationManager configManager;
	private static final Logger logger = LoggerFactory.getLogger(SlaveDetails.class);

	private static final String sBasic = "Basic ";
	private static final String sAuthorization = "Authorization";

	/**
	 * Returns slave status
	 * 
	 * @param slaveName
	 * @return String
	 * @throws IOException
	 */
	public String getSlaveStatus(String slaveName) throws IOException {

		HttpURLConnection conn = null;
		BufferedReader in = null;

		try {

			String surl;
			String jenkinsURL = configManager.getJenkinsurl();
			surl = jenkinsURL + "/label/" + slaveName + "/api/json";
			String username = configManager.getJenkinsuserid();
			String password = configManager.getJenkinspassword();
			URL url = new URL(surl);
			conn = (HttpURLConnection) url.openConnection();
			String userpass = username + ":" + password;
			String basicAuth = sBasic + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

			conn.setRequestProperty(sAuthorization, basicAuth);

			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			String input = "";
			while ((inputLine = in.readLine()) != null) {

				input = input + inputLine;
			}
			JSONObject js = JSONObject.fromObject(input);
			String status = js.getString("offline");

			if (status.equalsIgnoreCase("false"))
				return "Online";
			else
				return "Offline";

		} catch (IOException e) {
			logger.error("Error in while getting slave status");
			throw e;
		} finally {
			if (null != in) {
				in.close();
			}
		}

	}

}
