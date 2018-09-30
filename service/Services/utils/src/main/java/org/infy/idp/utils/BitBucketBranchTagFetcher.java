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
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@Component
public class BitBucketBranchTagFetcher {

	private static final String sHttpURl = "https://";
	private static final String sHttpUrlApi = "https://api.";
	private static final String sRepositoryVersion = "/2.0/repositories/";
	private static final String sJSONObjKey = "values";
	private static final String sJSONSearchKey = "type";
	private static final String sJSONBranchName = "name";
	private static final String sBasic = "Basic ";
	private static final String sAuthorization = "Authorization";

	private static final Logger logger = LoggerFactory.getLogger(BitBucketBranchTagFetcher.class);

	/**
	 * 
	 * Returns branch list of the specified repository
	 * 
	 * @param url
	 * @param userName
	 * @param password
	 * @param rootUrl
	 * @param sProxyServer
	 * @param port
	 * @return List<ArrayList<String>>
	 */

	public List<ArrayList<String>> getBranchList(String url, String userName, String password, String rootUrl,
			String sProxyServer, String port) {

		Integer sProxyPort = null;
		if (port != null && !port.equalsIgnoreCase("")) {
			sProxyPort = Integer.parseInt(port);
		}
		logger.info("Trying to fetch Branch list for project");
		List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		try {

			list.add(getBranch(rootUrl, userName, password, url, "branch", "/refs/branches", sProxyServer, sProxyPort));
			list.add(getTag(rootUrl, userName, password, url, "tag", "/refs/tags", sProxyServer, sProxyPort));
			logger.debug("Branch list fetched successfully BRANCH LIST: " + list.get(0));
			logger.debug("Tag list fetched successfully Tag LIST: " + list.get(1));
		}

		catch (JSONException e) {
			logger.error(e.getMessage(), e);
		} catch (NullPointerException e) {

			logger.error(e.getMessage(), e);
		}

		return list;

	}

	/**
	 * Returns branch of specified repository with search key
	 * 
	 * @param giturl
	 * @param userName
	 * @param password
	 * @param rootUrl
	 * @param sJSONBranchType
	 * @param sBranchRefs
	 * @param sProxyServer
	 * @param sProxyPort
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getBranch(String giturl, String userName, String password, String rootUrl,
			String sJSONBranchType, String sBranchRefs, String sProxyServer, Integer sPorxyPort) {

		ArrayList<String> branch = new ArrayList<>();
		HttpURLConnection conn = null;
		BufferedReader in = null;

		try {
			String sRepo_Slug = giturl.substring(giturl.lastIndexOf('/') + 1).replace(".git", "");
			String sApiUrl = rootUrl.replace(sHttpURl, sHttpUrlApi);
			String s[] = giturl.split("/");
			String teamName = s[s.length - 2];

			String sUrl = sApiUrl + sRepositoryVersion + teamName + "/" + sRepo_Slug + sBranchRefs;

			boolean nextFlag = true;
			while (nextFlag) {
				URL url = new URL(sUrl);

				if (sPorxyPort != null) {
					Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(sProxyServer, sPorxyPort));
					conn = (HttpURLConnection) url.openConnection(proxy);
				} else {
					conn = (HttpURLConnection) url.openConnection();
				}
				String userpass = userName + ":" + password;
				String basicAuth = sBasic + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

				conn.setRequestProperty(sAuthorization, basicAuth);

				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				String input = "";
				while ((inputLine = in.readLine()) != null) {

					input = input + inputLine;
				}

				JSONObject js = JSONObject.fromObject(input);
				JSONArray array = js.getJSONArray(sJSONObjKey);

				int jsonArraySize = array.size();
				for (int i = 0; i < jsonArraySize; i++) {

					JSONObject productObj = array.getJSONObject(i);
					List<String> keys = getKeys(productObj);

					if (keys.contains(sJSONSearchKey)
							&& productObj.get(sJSONSearchKey).toString().equalsIgnoreCase(sJSONBranchType)) {

						String branchname = productObj.get(sJSONBranchName).toString();
						branch.add(branchname);
					}
				}

				logger.info("Branch from getBranch Function" + branch);

				if (js.has("next")) {

					sUrl = js.get("next").toString();
				} else {
					nextFlag = false;
				}

			}
		} catch (JSONException | NullPointerException | IOException e) {
			logger.error(e.getMessage(), e);
		} finally {

			try {
				in.close();
				conn.disconnect();
			} catch (IOException | NullPointerException e) {
				logger.error(e.getMessage(), e);

			}
		}
		return branch;

	}

	/**
	 * 
	 * Returns branch tag
	 * 
	 * @param giturl
	 * @param userName
	 * @param password
	 * @param rootUrl
	 * @param sJSONTagType
	 * @param sTagRefs
	 * @param sProxyServer
	 * @param sProxyPort
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getTag(String giturl, String userName, String password, String rootUrl,
			String sJSONTagType, String sTagRefs, String sProxyServer, Integer sProxyPort) {

		ArrayList<String> tag = new ArrayList<String>();

		HttpURLConnection conn = null;
		BufferedReader in = null;

		try {
			String sRepoSlug = giturl.substring(giturl.lastIndexOf('/') + 1).replace(".git", "");
			String sApiUrl = rootUrl.replace(sHttpURl, sHttpUrlApi);
			String s[] = giturl.split("/");
			String teamName = s[s.length - 2];
			String sUrl = sApiUrl + sRepositoryVersion + teamName + "/" + sRepoSlug + sTagRefs;

			boolean nextFlag = true;
			while (nextFlag) {
				URL url = new URL(sUrl);

				if (sProxyPort != null) {
					Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(sProxyServer, sProxyPort));
					conn = (HttpURLConnection) url.openConnection(proxy);
				} else {
					conn = (HttpURLConnection) url.openConnection();
				}
				String userpass = userName + ":" + password;
				String basicAuth = sBasic + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());

				conn.setRequestProperty(sAuthorization, basicAuth);

				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				String input = "";
				while ((inputLine = in.readLine()) != null) {

					input = input + inputLine;
				}
				logger.info("shjowing tag Input");
				logger.info(input);

				JSONObject js = JSONObject.fromObject(input);
				JSONArray array = js.getJSONArray(sJSONObjKey);

				int jsonArraySize = array.size();

				for (int i = 0; i < jsonArraySize; i++) {

					JSONObject productObj = array.getJSONObject(i);
					List<String> keys = getKeys(productObj);

					if (keys.contains(sJSONSearchKey)
							&& productObj.get(sJSONSearchKey).toString().equalsIgnoreCase(sJSONTagType)) {

						String branchname = productObj.get(sJSONBranchName).toString();
						tag.add(branchname);
					}
				}

				logger.info("Tag from getBranch Function" + tag);
				in.close();
				if (js.has("next")) {
					sUrl = js.get("next").toString();
				} else {
					nextFlag = false;
				}

			}
		} catch (JSONException | IOException | NullPointerException e) {

			logger.error(e.getMessage(), e);
		} finally {

			try {
				in.close();
				conn.disconnect();
			} catch (IOException | NullPointerException e) {
				logger.error(e.getMessage(), e);
			}

		}

		return tag;

	}

	/**
	 * Returns keys for the json
	 * 
	 * @param jsonObj
	 * @return List<String>
	 */
	public List<String> getKeys(JSONObject jsonObj) {
		List<String> keysList = new ArrayList<>();
		try {
			Iterator<String> keysIterator = jsonObj.keys();
			while (keysIterator.hasNext()) {
				String key = keysIterator.next();
				keysList.add(key);
			}
		} catch (JSONException | NullPointerException e) {
			logger.error(e.getMessage(), e);
		}

		return keysList;
	}

}
