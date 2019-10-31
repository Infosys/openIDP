/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class CastReport {
	protected Logger logger = LoggerFactory.getLogger(CastReport.class);

	public String readCastReport(String sapWarName) {
		String path = "http://idposs:8080/" + sapWarName + "/rest/AAD/results?select=evolutionSummary";
		String username = "guest";
		String password = "my_password";
		String output = null;
		try {
			Client client = Client.create();
			String authString = username + ":" + password;
			String authStringEnc = new String(new Base64().encode(authString.getBytes()));
			WebResource webResource = client.resource(path);
			ClientResponse resp = webResource.accept("application/json")
					.header("Authorization", "Basic " + authStringEnc).get(ClientResponse.class);
			logger.info("response code is: " + resp.getStatus());
			if (resp.getStatus() != 200) {
				logger.info("Unable to connect to the server");
			}
			output = resp.getEntity(String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
}
