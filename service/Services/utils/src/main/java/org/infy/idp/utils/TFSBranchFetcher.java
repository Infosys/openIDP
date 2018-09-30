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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


/**
 * 
 * The class TFSBranchFetcher contains methods related to TFS branches and its details
 * @author Infosys
 *
 */
@Component
public class TFSBranchFetcher {

	private static final Logger logger = LoggerFactory.getLogger(TFSBranchFetcher.class);

	/**
	 *
	 * 
	 * @param instanceDefaultCollection
	 * @param path
	 * @param apiVersion
	 * @param username
	 * @param password
	 * @return JSONObject
	 */
	public JSONObject connectToURL(String instanceDefaultCollection, String path, String apiVersion, String username,
			String password) {
		JSONObject jo = null;

		URLConnection urlConnection = null;
		InputStream content = null;
		BufferedReader output = null;

		try {

			String tfsURL = instanceDefaultCollection + "/_apis/tfvc/branches/" + path + "?" + "api-version="
					+ apiVersion;

			String webPage = tfsURL;
			String line = "";
			String jsonBranches = "";

			webPage += "[&includeChildren=true&includeParent=true]";

			String authString = username + ":" + password;
			byte[] authEncBytes = Base64.getUrlEncoder().encode(authString.getBytes());
			String authStringEnc = new String(authEncBytes);

			URL url = new URL(webPage);
			urlConnection = url.openConnection();

			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			content = urlConnection.getInputStream();

			output = new BufferedReader(new InputStreamReader(content));

			while ((line = output.readLine()) != null) {
				jsonBranches = line;
			}

			jo = JSONObject.fromObject(jsonBranches);

		} catch (IOException | JSONException | NullPointerException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (content != null) {
					content.close();
				}
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return jo;
	}

	/**
	 * 
	 * @param instance_default_collection
	 * @param path
	 * @param api_version
	 * @param username
	 * @param password
	 * @return ArrayList<String>
	 */

	public ArrayList<String> getAllBranches(String instance_default_collection, String path, String api_version,
			String username, String password) {

		ArrayList<String> list = new ArrayList<>();
		logger.info(username + ":" + password);
		try {
			JSONObject result = connectToURL(instance_default_collection, path, api_version, username, password);
			if (result == null) {
				return list;
			} else {

				JSONArray value2 = (JSONArray) result.get("children");

				int size = value2.size();
				for (int i = 0; i < size; i++) {
					JSONObject obj = value2.getJSONObject(i);
					String pathInfo = obj.getString("path");
					list.add(pathInfo);

				}

			}
		} catch (JSONException | NullPointerException e) {
			logger.error(e.getMessage(), e);
		}

		return list;

	}

}
