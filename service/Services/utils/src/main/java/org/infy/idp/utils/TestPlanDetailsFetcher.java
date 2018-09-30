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
import java.util.Base64;
import java.util.List;

import org.infy.idp.entities.jobs.TestPlans;
import org.infy.idp.entities.jobs.TestSuits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * The class TestPlanDetailsFetcher contains methods related to test plans and suits related to mtm project
 * 
 * @author Infosys
 *
 */
@Component
public class TestPlanDetailsFetcher {

	private static final Logger logger = LoggerFactory.getLogger(TestPlanDetailsFetcher.class);

	@Autowired
	private ConfigurationManager configmanager;

	
	/**
	 * Returns list of project plan for the specified project
	 * @param projNam
	 * @return
	 */
	public List<TestPlans> getTestPlan(String projNam) {
		List<TestPlans> testPlansList = new ArrayList<>();

		try {
			String serverURL = configmanager.getServerURL();

			String apiString = "test/plans?includePlanDetails=true&";
			String api = configmanager.getApiVersion();
			String proxyip = configmanager.getProxyip();
			Integer port = Integer.valueOf(configmanager.getProxyport());
			String userId = configmanager.getUserID();
			String pass = configmanager.getPassword();

			URL url = new URL(serverURL + "/" + projNam + "/_apis/" + apiString + "api-version=" + api);
			logger.info(
					"server url for mtm:" + serverURL + "/" + projNam + "/_apis/" + apiString + "api-version=" + api);

			String userpass = userId + ":" + pass;
			logger.info("userpass: " + userpass);
			String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userpass.getBytes());

			HttpURLConnection conn;
			if (proxyip != null && !"".equalsIgnoreCase(proxyip)) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyip, port));
				conn = (HttpURLConnection) url.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) url.openConnection();
			}
			conn.setRequestProperty("Authorization", basicAuth);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String inputLine;
			String input = "";
			while ((inputLine = in.readLine()) != null) {

				input = input + inputLine;

			}
			logger.info("result for mtm : " + input);
			JSONObject js = JSONObject.fromObject(input);
			Integer countPlans = js.optInt("count", -1);

			logger.info("Count Plans: " + countPlans);

			if (countPlans == -1) {
				throw new Exception("No test plans present in VSTS GET request");
			}

			JSONArray testPlansJArr = js.optJSONArray("value");

			for (int i = 0; i < countPlans; i++) {

				TestPlans testPlan = new TestPlans();

				JSONObject testPlanJson = testPlansJArr.getJSONObject(i);

				Integer id = testPlanJson.optInt("id");
				String name = testPlanJson.optString("name");
				testPlan.setTestPlanId(id);
				testPlan.setTestPlanName(name);

				testPlansList.add(testPlan);

			}
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {

			logger.error(e.getMessage(), e);
		} catch (NullPointerException e) {

			logger.error(e.getMessage(), e);
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
		}

		return testPlansList;
	}

	/**
	 * Returns test suit details of the specified project
	 * 
	 * @param planID
	 * @param mtmProject
	 * @return TestSuits list
	 */
	public List<TestSuits> getTestSuits(Integer planID, String mtmProject) {
		List<TestSuits> testSuitsList = new ArrayList<>();

		try {
			String serverURL = configmanager.getServerURL();
			String apiString = "test/plans/" + planID + "/suites?asTreeView=true&includeChildSuites=true&";
			String api = configmanager.getApiVersion();
			String proxyip = configmanager.getProxyip();
			Integer port = Integer.valueOf(configmanager.getProxyport());
			String userId = configmanager.getUserID();
			String pass = configmanager.getPassword();

			URL url = new URL(serverURL + "/" + mtmProject + "/_apis/" + apiString + "api-version=" + api);
			String userpass = userId + ":" + pass;
			logger.info("userpass: " + userpass);

			String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userpass.getBytes());
			logger.info(serverURL + "/" + mtmProject + "/_apis/" + apiString + "api-version=" + api);

			HttpURLConnection conn;

			if (proxyip != null && !"".equalsIgnoreCase(proxyip)) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyip, port));
				conn = (HttpURLConnection) url.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) url.openConnection();
			}

			conn.setRequestProperty("Authorization", basicAuth);
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			String input = "";
			while ((inputLine = in.readLine()) != null) {

				input = input + inputLine;
			}
			logger.info(input);
			JSONObject suitesJson = JSONObject.fromObject(input);
			Integer countPlans = suitesJson.optInt("count", -1);

			logger.info("Count Suits: " + countPlans);

			if (countPlans == -1) {
				throw new Exception("No test suites present for Test plan: " + planID + "; in VSTS GET request");
			}

			JSONArray testSuitesJArr = suitesJson.optJSONArray("value");

			for (int i = 0; i < countPlans; i++) {
				logger.info("count" + i);

				TestSuits testSuit = new TestSuits();

				JSONObject testSuitesJson = testSuitesJArr.getJSONObject(i);
				logger.info("Suit" + testSuitesJson);
				Integer sid = testSuitesJson.optInt("id");
				String sname = testSuitesJson.optString("name");
				JSONObject sparent = testSuitesJson.optJSONObject("parent");

				testSuit.setTestSuitId(sid);
				testSuit.setTestSuitName(sname);
				if (sparent != null)
					testSuit.setTestSuitParent(sparent.toString());
				else {
					testSuit.setTestSuitParent("na");
				}
				testSuitsList.add(testSuit);

			}
		} catch (JSONException e) {

			logger.error(e.getMessage(), e);
		} catch (IOException e) {

			logger.error(e.getMessage(), e);
		} catch (NullPointerException e) {

			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return testSuitsList;
	}

}
