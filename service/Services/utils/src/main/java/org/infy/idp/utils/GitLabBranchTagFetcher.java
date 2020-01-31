/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * The class GitHubBranchTagFetcher contains method to fetch branch and branch
 * details from GitLab
 * 
 * @author Infosys
 *
 */
@Component
public class GitLabBranchTagFetcher {
	private static final Logger logger = LoggerFactory.getLogger(GitLabBranchTagFetcher.class);

	/**
	 * Returns id of repository
	 * 
	 * @param privateToken
	 * @param repoUrl
	 * @param projectUrl
	 * @return int
	 */

	public int getId(String privateToken, String repoUrl, String projectUrl) {
		int id = 0;

		HttpURLConnection conn = null;
		BufferedReader in = null;
		try {
			String[] urlArray =  projectUrl.split("/");
			String repoOwner="";
			String repoName="";
			try {
				repoOwner = URLEncoder.encode(urlArray[3], "UTF-8");
				 repoName =  URLEncoder.encode((urlArray[4].split("\\."))[0], "UTF-8");
				System.out.println(repoOwner+"  "+repoName);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String sUrl = repoUrl + "/api/v4/projects/"+repoOwner+"%2F"+repoName+"/?access_token=" + privateToken;
			System.out.println("fecthing id from url : " + sUrl);
			URL url = new URL(sUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);

			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuilder input = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				input.append(inputLine);
			}

			JSONObject js = JSONObject.fromObject(input.toString());

			id = js.getInt("id");

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (JSONException e) {

			logger.error(e.getMessage());
		} catch (IOException e) {

			logger.error(e.getMessage(), e);
		} catch (NullPointerException e) {
			logger.error(e.getMessage(), e);
		}

		finally {
			try {
				in.close();
				conn.disconnect();
			} catch (IOException e) {
				logger.error(e.getMessage());
			} catch (NullPointerException e) {
				logger.error(e.getMessage(), e);
			}

		}
		return id;
	}

	/**
	 * Returns branch details of specified repository
	 * 
	 * @param id
	 * @param repoUrl
	 * @param accessToken
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getBranch(int id, String repoUrl, String accessToken) {

		String sUrl = repoUrl + "/api/v4/projects/" + id + "/repository/branches";
		HttpURLConnection conn = null;
		BufferedReader in = null;

		ArrayList<String> branch = new ArrayList<String>();
		try {
			URL url = new URL(sUrl);

			logger.info("fecthing branches from url : " + sUrl);
			conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("ACCESS-TOKEN", accessToken);

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

			in.close();
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
				logger.error(e.getMessage());
			}
		}

		return branch;

	}

	/**
	 * Returns private token to access repository
	 * 
	 * @param repoUrl
	 * @param username
	 * @param pwd
	 * @return String
	 */
	public String getPrivateToken(String repoUrl, String username, String pwd) {

		String auth_token = null;

		HttpURLConnection conn = null;
		BufferedReader rd = null;
		InputStream is = null;
		DataOutputStream wr = null;
		try {

			String url = repoUrl + "/oauth/token";

			String urlParameters = "grant_type=" + URLEncoder.encode("password", "UTF-8") + "&username="
					+ URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(pwd, "UTF-8");

			URL oracle = new URL(url);
			conn = (HttpURLConnection) oracle.openConnection();

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
			conn.setRequestProperty("Content-Language", "en-US");

			conn.setUseCaches(false);

			wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			is = conn.getInputStream();
			rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}

			JSONObject js = JSONObject.fromObject(response.toString());
			auth_token = js.getString("access_token");

			logger.info("got access_token : " + auth_token);

		} catch (JSONException | IOException | NullPointerException e) {
			logger.error(e.getMessage(), e);
		} finally {

			try {
				if (wr != null) {
					wr.close();
				}
				if (is != null) {
					is.close();
				}
				if (rd != null) {
					rd.close();
				}
				if (null != conn) {
					conn.disconnect();
				}

			} catch (IOException e) {
				logger.error(e.getMessage());

			} catch (NullPointerException e) {

				logger.error(e.getMessage(), e);

			}

		}
		return auth_token;
	}


	/**
	 * Returns tag for the specified repository
	 * 
	 * @param id
	 * @param repoUrl
	 * @param accessToken
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getTag(int id, String repoUrl, String accessToken) {

		String sUrl = repoUrl + "/api/v4/projects/" + id + "/repository/tags";
		ArrayList<String> branch = new ArrayList<String>();
		HttpURLConnection conn = null;
		BufferedReader in = null;
		try {
			URL url = new URL(sUrl);

			logger.info("fecthing tags from url : " + sUrl);
			conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("ACCESS-TOKEN", accessToken);

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

			in.close();
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
				logger.error(e.getMessage());
			}

		}

		return branch;

	}

}
