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
import java.util.List;

import org.infy.idp.entities.models.GitHubBrachModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * The class GitHubBranchTagFetcher contains method to fetch branch and branch
 * details from Github
 * 
 * @author Infosys
 *
 */
@Component
public class GitHubBranchTagFetcher {

	private static final Logger logger = LoggerFactory.getLogger(GitHubBranchTagFetcher.class);

	/**
	 * Return brnach list for the specified repo details
	 * @param branchModel
	 * @return branch and tag list
	 */
	public List<ArrayList<String>> getBranchList(GitHubBrachModel branchModel) {

		logger.info("Trying to fetch Branch list for project");
		List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		try {

			list.add(getBranch(branchModel));
			list.add(getTag(branchModel));
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
	 * @param branchModel
	 * @return branch list
	 */
	public ArrayList<String> getBranch(GitHubBrachModel branchModel) {
		String rootUrl = branchModel.getRepoUrl();
		String userName = branchModel.getUsername();
		String password = branchModel.getPwd();
		String githubUrl = branchModel.getProjectUrl();

		String sProxyServer = branchModel.getProxy();
		Integer sProxyPort = null;
		if (branchModel.getPort() != null && !branchModel.getPort().equals("")) {
			sProxyPort = Integer.parseInt(branchModel.getPort());
		}
		ArrayList<String> branch = new ArrayList<String>();
		HttpURLConnection conn = null;
		BufferedReader in = null;
		try {
			if (rootUrl.equalsIgnoreCase("http://github.com") || rootUrl.equalsIgnoreCase("https://github.com")) {

				String sRepo_Slug = githubUrl.substring(githubUrl.lastIndexOf('/') + 1).replace(".git", "");

				String sApiUrl = rootUrl.replace("https://", "https://api.");

				String s[] = githubUrl.split("/");
				String teamName = s[s.length - 2];

				String finalUrl = sApiUrl + "/repos/" + teamName + "/" + sRepo_Slug + "/branches";

				URL url = new URL(finalUrl);

				if (sProxyPort != null) {
					Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(sProxyServer, sProxyPort));
					conn = (HttpURLConnection) url.openConnection(proxy);

				} else {
					conn = (HttpURLConnection) url.openConnection();
				}

				String userpass = userName + ":" + password;
				String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
				conn.setRequestProperty("Authorization", basicAuth);
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				String input = "";
				while ((inputLine = in.readLine()) != null) {

					input = input + inputLine;
				}

				JSONArray js = JSONArray.fromObject(input);

				for (int i = 0; i < js.size(); i++) {
					JSONObject js3 = js.getJSONObject(i);
					branch.add(js3.getString("name"));
				}
			} else {
				String sRepo_Slug = githubUrl.substring(githubUrl.lastIndexOf('/') + 1).replace(".git", "");
				String[] urlFixes = githubUrl.split("/");
			
				String finalUrl = urlFixes[0] + "//" + urlFixes[2] + "/api/v3/repos/" + urlFixes[3] + "/" + sRepo_Slug
						+ "/branches";
				branch = new ArrayList<String>();
				URL url = new URL(finalUrl);

				if (sProxyPort != null) {
					Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(sProxyServer, sProxyPort));
					conn = (HttpURLConnection) url.openConnection(proxy);
				} else {
					conn = (HttpURLConnection) url.openConnection();
				}

				String userpass = userName + ":" + password;
				String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
				conn.setRequestProperty("Authorization", basicAuth);
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				String input = "";
				while ((inputLine = in.readLine()) != null) {
					input = input + inputLine;
				}
				JSONArray js = JSONArray.fromObject(input);
				int jsArraySize = js.size();
				for (int i = 0; i < jsArraySize; i++) {
					JSONObject js3 = js.getJSONObject(i);
					branch.add(js3.getString("name"));
				}

			}
		} catch (JSONException | IOException | NullPointerException e) {
			logger.error(e.getMessage(), e);
		} finally {

			try {
				if (in != null) {
					in.close();
				}
				if (null != conn) {
					conn.disconnect();
				}

			} catch (IOException | NullPointerException e) {
				logger.error(e.getMessage(), e);
			}

		}

		return branch;

	}

	/**
	 * 
	 * @param root_url
	 * @param userName
	 * @param password
	 * @param githubUrl
	 * @param sProxyServer
	 * @param sPorxyPort
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getTag(GitHubBrachModel branchModel) {

		String rootUrl = branchModel.getRepoUrl();
		String userName = branchModel.getUsername();
		String password = branchModel.getPwd();
		String githubUrl = branchModel.getProjectUrl();

		String sProxyServer = branchModel.getProxy();
		Integer sProxyPort = null;
		if (branchModel.getPort() != null && !branchModel.getPort().equals("")) {
			sProxyPort = Integer.parseInt(branchModel.getPort());
		}

		ArrayList<String> branch = new ArrayList<>();
		HttpURLConnection conn = null;
		BufferedReader in = null;

		try {
			if (rootUrl.equalsIgnoreCase("http://github.com") || rootUrl.equalsIgnoreCase("https://github.com")) {

				String sRepoSlug = githubUrl.substring(githubUrl.lastIndexOf('/') + 1).replace(".git", "");

				String sApiUrl = rootUrl.replace("https://", "https://api.");

				String s[] = githubUrl.split("/");
				String teamName = s[s.length - 2];

				String finalUrl = sApiUrl + "/repos/" + teamName + "/" + sRepoSlug + "/tags";

				URL url = new URL(finalUrl);

				if (sProxyPort != null) {
					Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(sProxyServer, sProxyPort));
					conn = (HttpURLConnection) url.openConnection(proxy);

				} else {
					conn = (HttpURLConnection) url.openConnection();
				}

				String userpass = userName + ":" + password;
				String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
				conn.setRequestProperty("Authorization", basicAuth);
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				String input = "";
				while ((inputLine = in.readLine()) != null) {

					input = input + inputLine;
				}

				JSONArray js = JSONArray.fromObject(input);

				int jsArraySize = js.size();
				for (int i = 0; i < jsArraySize; i++) {
					JSONObject js3 = js.getJSONObject(i);
					branch.add(js3.getString("name"));
				}

			} else {
				String sRepoSlug = githubUrl.substring(githubUrl.lastIndexOf('/') + 1).replace(".git", "");
				String[] urlFixes = githubUrl.split("/");
				String finalUrl = urlFixes[0] + "//" + urlFixes[2] + "/api/v3/repos/" + urlFixes[3] + "/" + sRepoSlug
						+ "/tags";
				branch = new ArrayList<String>();
				URL url = new URL(finalUrl);

				if (sProxyPort != null) {
					Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(sProxyServer, sProxyPort));
					conn = (HttpURLConnection) url.openConnection(proxy);
				} else {
					conn = (HttpURLConnection) url.openConnection();
				}

				String userpass = userName + ":" + password;
				String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
				conn.setRequestProperty("Authorization", basicAuth);
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				String input = "";
				while ((inputLine = in.readLine()) != null) {
					input = input + inputLine;
				}

				JSONArray js = JSONArray.fromObject(input);

				for (int i = 0; i < js.size(); i++) {
					JSONObject js3 = js.getJSONObject(i);
					branch.add(js3.getString("name"));
				}

			}
		} catch (IOException | JSONException | NullPointerException e) {
			logger.error(e.getMessage(), e);
		} finally {

			try {
				if (in != null) {
					in.close();
				}
				if (null != conn) {
					conn.disconnect();
				}
			} catch (IOException | NullPointerException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return branch;

	}

}
