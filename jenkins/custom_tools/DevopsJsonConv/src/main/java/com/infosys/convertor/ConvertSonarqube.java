/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.infosys.codeanalysisrecommendation.RecommendationByWebAPI;
import com.infosys.json.CodeAnalysis;
import com.infosys.json.SonarDetails;
import com.infosys.json.SonarDetailsLOCjson;
import com.infosys.json.SonarDetailsLocmeasures;
import com.infosys.json.SonarIssues;
import com.infosys.json.SonarJson;

public class ConvertSonarqube {
	private static final Logger logger = Logger.getLogger(ConvertSonarqube.class);
	private static final String TEXTRANGE = "textRange";
	private static final String NAMESPACECLASSMAP = "/NamespaceClassMap.csv";
	private static int major;
	private static int minor;
	private static int blocker;
	private static int critical;
	private static int info;

	private ConvertSonarqube() {
	}

	public static List<CodeAnalysis> convert(String pathToCsvDir, List<CodeAnalysis> ca, String sonarPrjctKey,
			String sonarHost, String prefixForId, SonarDetails sonardetailsobj) {
		try {
			String severities = readSonarSeverities();
			waitForAnalysis(sonarHost, sonarPrjctKey,sonardetailsobj);
			String sonarIssuesURL = generateSonarIssuesURL(sonarPrjctKey, sonarHost, severities);
			getSonarIssuesList(sonarIssuesURL, sonardetailsobj.getSonarUserName(), sonardetailsobj.getSonarPassword(),
					ca, sonarHost, prefixForId, pathToCsvDir.replaceAll("\\\\", "/"));
			//
			String sonarBUGURL = generateSonarInfoURL(sonarPrjctKey, sonarHost, "bg");
			String sonarVulURL = generateSonarInfoURL(sonarPrjctKey, sonarHost, "vul");
			String sonarCSURL = generateSonarInfoURL(sonarPrjctKey, sonarHost, "cs");
			SonarDetails sonarDetailsobj1=new SonarDetails();
			
			sonarDetailsobj1 = getSonarDollarValue(sonarBUGURL, sonarVulURL, sonarCSURL, sonardetailsobj);
			String sonarLOCURL = generateSonarLOCURL(sonarPrjctKey, sonarHost);
			getSonarLOC(sonarDetailsobj1, sonarLOCURL);
			//
			logger.info("Sonarqube Report Generated..!!");
		} catch (Exception e) {
			System.out.println("Error in converting sonar reports");
		}
		return ca;
	}

	private static void waitForAnalysis(String sonarHost, String sonarPrjctKey, SonarDetails sonardetailsobj) {
		String prjctQueueUrl = sonarHost + "api/ce/component?componentKey=" + sonarPrjctKey;
		// loop untill analysis is running
		String jsonResp="";
		while (true) {
			if ((sonardetailsobj.getSonarUserName()!=null && sonardetailsobj.getSonarUserName() !="NA") && 
					sonardetailsobj.getSonarPassword() != null && sonardetailsobj.getSonarPassword() != "NA" )
			{
				jsonResp = hitSonarWebServiceWithAuthentication(prjctQueueUrl , sonardetailsobj.getSonarUserName() ,sonardetailsobj.getSonarPassword());
			}
			else
			{
				jsonResp = hitSonarWebService(prjctQueueUrl);
			}
			JSONParser parser = new JSONParser();
			try {
				JSONObject json = (JSONObject) parser.parse(jsonResp);
				JSONArray queueArr = (JSONArray) json.get("queue");
				if (queueArr != null && queueArr.isEmpty())
					break;
			} catch (org.json.simple.parser.ParseException e) {
				logger.error(e);
			}
		}
		logger.info("Sonar analysis is ready to be consumed");
	}
			

	private static SonarDetails getSonarLOC(SonarDetails sonardetailsobj, String sonarLOCURL) {
		try {
			String locresponse = "";
			if ("NA".equalsIgnoreCase(sonardetailsobj.getSonarUserName())
					|| "NA".equalsIgnoreCase(sonardetailsobj.getSonarPassword())) {
				locresponse = hitSonarWebService(sonarLOCURL);
			} else {
				locresponse = hitSonarWebServiceWithAuthentication(sonarLOCURL , sonardetailsobj.getSonarUserName() ,sonardetailsobj.getSonarPassword());
			}
			Gson g = new Gson();
			SonarDetailsLOCjson sl = g.fromJson(locresponse, SonarDetailsLOCjson.class);
			List<SonarDetailsLocmeasures> a = sl.getComponent().getMeasures();
			if (a.get(0).getMetric().equalsIgnoreCase("ncloc")) {
				sonardetailsobj.setLoc(a.get(0).getValue());
			}
			return sonardetailsobj;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	private static String generateSonarLOCURL(String sonarPrjctKey, String sonarHost) {
		try {
			return sonarHost + "api/measures/component?componentKey=" + sonarPrjctKey + "&metricKeys=ncloc";
		} catch (Exception e) {
			System.out.println("Error in generation of sonar LOC URL");
		}
		return null;
	}

	public static String readSonarrateperhour() {
		Properties p = new Properties();
		try {
			p.load(ConvertSonarqube.class.getClassLoader().getResourceAsStream("sonar.properties"));
			return p.getProperty("hourlyrate");
		} catch (IOException e1) {
			logger.error(e1);
		}
		return null;
	}

	private static String readSonarSeverities() {
		Properties p = new Properties();
		try {
			p.load(ConvertSonarqube.class.getClassLoader().getResourceAsStream("sonar.properties"));
			return p.getProperty("severities");
		} catch (IOException e1) {
			logger.error(e1);
		}
		return null;
	}

	private static String generateSonarInfoURL(String sonarPrjctKey, String sonarHost, String val) {
		try {
			if (val.equalsIgnoreCase("bg"))
				return sonarHost + "api/issues/search?componentRoots=" + sonarPrjctKey + "&types=BUG";
			else if (val.equalsIgnoreCase("cs"))
				return sonarHost + "api/issues/search?componentRoots=" + sonarPrjctKey + "&types=CODE_SMELL";
			else if (val.equalsIgnoreCase("vul"))
				return sonarHost + "api/issues/search?componentRoots=" + sonarPrjctKey + "&types=VULNERABILITY";
		} catch (Exception e) {
			System.out.println("Error in generation of sonar URL");
		}
		return null;
	}

	private static String generateSonarIssuesURL(String sonarPrjctKey, String sonarHost, String severities) {
		if (severities != null) {
			return sonarHost + "api/issues/search?componentRoots=" + sonarPrjctKey + "&severities=" + severities
					+ "&statuses=OPEN" + "&pageIndex=";
		} else {
			return sonarHost + "api/issues/search?componentRoots=" + sonarPrjctKey + "&statuses=OPEN" + "&pageIndex=";
		}
	}

	private static List<CodeAnalysis> getSonarIssuesList(String sonarPrjctIssuesURL, String sonarUserName,
			String sonarPassword, List<CodeAnalysis> caList, String sonarHost, String prefixForId,
			String pathToCsvDir) {
		int nextPageIndex = 1;
		String jsonResp;
		while (nextPageIndex != -1) {
			if ("NA".equalsIgnoreCase(sonarUserName) || "NA".equalsIgnoreCase(sonarPassword) || sonarUserName == null
					|| sonarPassword == null) {
				jsonResp = hitSonarWebService(sonarPrjctIssuesURL + String.valueOf(nextPageIndex));
			} else {
				jsonResp = hitSonarWebServiceWithAuthentication(sonarPrjctIssuesURL + String.valueOf(nextPageIndex) , sonarUserName ,sonarPassword);
			}
			nextPageIndex = parseIssuesJson(jsonResp, caList, sonarHost, prefixForId, pathToCsvDir);
		}
		return caList;
	}

	private static SonarDetails getSonarDollarValue(String sonarBGURL, String sonarVULURL, String sonarCSURL,
			SonarDetails sobj) {
		try {
			String bugjsonResp;
			String vuljsonResp;
			String csmjsonResp;
			JSONParser parser = new JSONParser();
			if ("NA".equalsIgnoreCase(sobj.getSonarUserName()) || "NA".equalsIgnoreCase(sobj.getSonarPassword())) {
				bugjsonResp = hitSonarWebService(sonarBGURL);
				csmjsonResp = hitSonarWebService(sonarCSURL);
				vuljsonResp = hitSonarWebService(sonarVULURL);
			} else {
				bugjsonResp = hitSonarWebServiceWithAuthentication(sonarBGURL , sobj.getSonarUserName() ,sobj.getSonarPassword());
				csmjsonResp = hitSonarWebServiceWithAuthentication(sonarCSURL , sobj.getSonarUserName() ,sobj.getSonarPassword());
				vuljsonResp = hitSonarWebServiceWithAuthentication(sonarVULURL, sobj.getSonarUserName() ,sobj.getSonarPassword());
			}
			double bugEffort;
			double vulEffort;
			double csmEffort;
			double totalEffort;
			JSONObject bjson = (JSONObject) parser.parse(bugjsonResp);
			JSONObject cjson = (JSONObject) parser.parse(csmjsonResp);
			JSONObject vjson = (JSONObject) parser.parse(vuljsonResp);
			Gson g = new Gson();
			SonarJson j = g.fromJson(bugjsonResp, SonarJson.class);
			bugEffort = getEffortDuration(j);
			j = g.fromJson(vuljsonResp, SonarJson.class);
			vulEffort = getEffortDuration(j);
			j = g.fromJson(csmjsonResp, SonarJson.class);
			csmEffort = getEffortDuration(j);
			totalEffort = bugEffort + csmEffort + vulEffort;
			totalEffort = totalEffort / 8;
			sobj.setBugs(bjson.get("total").toString());
			sobj.setCodesmells(cjson.get("total").toString());
			sobj.setVulnerabilities(vjson.get("total").toString());
			double totalDebt = Double.parseDouble(sobj.getRateperhour()) * totalEffort;
			sobj.setTechnicalDebt(String.valueOf(java.lang.Math.ceil(totalDebt)));
		} catch (Exception e) {
			System.out.println("Error in generation of Dollar value");
		}
		return sobj;
	}

	private static double getEffortDuration(SonarJson j) {
		if (j != null) {
			String temp = "";
			double duration = 0;
			if (j.getIssues() != null) {
				ArrayList<SonarIssues> i = j.getIssues();
				for (SonarIssues s : i) {
					if (s.getDebt() != null) {
						temp = s.getDebt();
						temp = temp.replaceAll("min", "");
						duration += Integer.parseInt(temp);
					}
				}
				duration = duration / 60;
			}
			return duration;
		}
		return 0;
	}

	private static String hitSonarWebService(String sonarPrjctIssuesURL) {
		String jsonResponse = null;
		BufferedReader in = null;
		try {
			URL url = new URL(sonarPrjctIssuesURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(false);
			InputStream content = connection.getInputStream();
			in = new BufferedReader(new InputStreamReader(content));
			jsonResponse = in.readLine();
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return jsonResponse;
	}

	private static String hitSonarWebServiceWithAuthentication(String sonarPrjctIssuesURL ,String sonarUserName, String sonarPassword) {
		String jsonResponse = null;
		BufferedReader in = null;
		try {
			URL url = new URL(sonarPrjctIssuesURL);
			String s = sonarUserName + ":" + sonarPassword;
			String encoding = Base64.getEncoder().encodeToString(s.getBytes());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Authorization", "Basic " + encoding);
			connection.setRequestMethod("GET");
			connection.setDoOutput(false);
			InputStream content = connection.getInputStream();
			in = new BufferedReader(new InputStreamReader(content));
			jsonResponse = in.readLine();
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return jsonResponse;
	}

	private static int parseIssuesJson(String jsonResp, List<CodeAnalysis> caList,
			String sonarHost, String prefixForId, String pathToCsvDir) {
		int nextPageIndex = -1;
		int currPageIndex = -1;
		int totRec = -1;
		int pageSize;
		JSONParser parser = new JSONParser();
		RecommendationByWebAPI recomObj = new RecommendationByWebAPI(sonarHost);
		HashMap<String, String> nsClassMapForDotNet = mapNsClass(pathToCsvDir);
		try {
			JSONObject json = (JSONObject) parser.parse(jsonResp);
			JSONArray issuesArr = (JSONArray) json.get("issues");
			// displaying the issues.
			for (int i = 0; i < issuesArr.size(); i++) {
				CodeAnalysis ca = getCodeAnalysisObject();
				ca.setSeverity(mapSonarSeverity(((JSONObject) issuesArr.get(i)).get("severity").toString()));
				countSeverity(((JSONObject) issuesArr.get(i)).get("severity").toString());
				ca.setcategory("sonarqube");
				ca.setruleName(((JSONObject) issuesArr.get(i)).get("rule").toString());
				if (((JSONObject) issuesArr.get(i)).get("line") != null) {
					ca.setLine((((JSONObject) issuesArr.get(i)).get("line")).toString());
				} else if (((JSONObject) issuesArr.get(i)).get(TEXTRANGE) != null
						&& ((JSONObject) (((JSONObject) issuesArr.get(i)).get("textRange"))).get("startLine") != null) {
					ca.setLine(((JSONObject) (((JSONObject) issuesArr.get(i)).get(TEXTRANGE))).get("startLine")
							.toString());
				}
				ca.setRecommendation(
						recomObj.getRecommendationByRule(((JSONObject) issuesArr.get(i)).get("rule").toString()));
				String component = ((JSONObject) issuesArr.get(i)).get("component").toString();
				ca.setClassName(component);
				//
				String id = extractId(component, nsClassMapForDotNet);
				ca.setId(prefixForId + id);
				ca.setMessage(((JSONObject) issuesArr.get(i)).get("message").toString());
				ca.setProject(((JSONObject) issuesArr.get(i)).get("project").toString());
				ca.setType(((JSONObject) issuesArr.get(i)).get("type").toString());
				caList.add(ca);
			}
			currPageIndex = Integer.parseInt(json.get("p").toString());
			totRec = Integer.parseInt(json.get("total").toString());
			pageSize = Integer.parseInt(json.get("ps").toString());
			if (currPageIndex != 100 && (pageSize * currPageIndex) < totRec) {
				nextPageIndex = currPageIndex + 1;
			}
		} catch (org.json.simple.parser.ParseException e) {
			logger.error(e);
		}
		return nextPageIndex;
	}

	private static void countSeverity(String sonarSeverity) {
		if (sonarSeverity.equalsIgnoreCase("blocker")) {
			blocker++;
		}
		if (sonarSeverity.equalsIgnoreCase("critical")) {
			critical++;
		} else if (sonarSeverity.equalsIgnoreCase("major")) {
			major++;
		} else if (sonarSeverity.equalsIgnoreCase("minor")) {
			minor++;
		} else if (sonarSeverity.equalsIgnoreCase("info")) {
			info++;
		}
	}

	private static CodeAnalysis getCodeAnalysisObject() {
		return new CodeAnalysis();
	}

	private static HashMap<String, String> mapNsClass(String pathToCsvDir) {
		HashMap<String, String> nsClassMapForDotNet = null;
		if (new File(pathToCsvDir.split("/IDP_DevopsJSON_Integration/Jenkins_Reports")[0] + NAMESPACECLASSMAP)
				.isFile()) {
			nsClassMapForDotNet = readCsvFileInWs(
					pathToCsvDir.split("/IDP_DevopsJSON_Integration/Jenkins_Reports")[0] + NAMESPACECLASSMAP);
		} else if (new File(
				pathToCsvDir.split("\\\\IDP_DevopsJSON_Integration\\\\Jenkins_Reports")[0] + NAMESPACECLASSMAP)
						.isFile()) {
			nsClassMapForDotNet = readCsvFileInWs(
					pathToCsvDir.split("\\\\IDP_DevopsJSON_Integration\\\\Jenkins_Reports")[0] + NAMESPACECLASSMAP);
		}
		return nsClassMapForDotNet;
	}

	private static HashMap<String, String> readCsvFileInWs(String filePath) {
		HashMap<String, String> nsClassMapForDotNet = new HashMap<>();
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {
				String[] kv = line.split(cvsSplitBy);
				nsClassMapForDotNet.put(processKey(kv[0]), kv[1]);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return nsClassMapForDotNet;
	}

	private static String processKey(String rawKey) {
		if (rawKey.indexOf("\\") == -1) {
			return rawKey;
		}
		String rawKeyNew = rawKey.substring(1);
		if (rawKeyNew.indexOf("\\") == -1) {
			return rawKeyNew;
		}
		return rawKeyNew.substring(rawKeyNew.indexOf("\\") + 1).replace("\\", "/");
	}

	private static String extractId(String component, Map<String, String> nsClassMapForDotNet) {
		String[] srcPathArr = component.split(":");
		String srcPath = srcPathArr[srcPathArr.length - 1];
		String[] fNameWoExt = srcPath.split("\\.");
		String id;
		if (fNameWoExt[fNameWoExt.length - 1].equals("java")) {
			if (fNameWoExt[0].contains("src/main/java/")) {
				id = fNameWoExt[0].split("src.main.java.")[1].replace("/", "_");
			} else if (fNameWoExt[0].contains("src/test/java/")) {
				id = fNameWoExt[0].split("src.test.java.")[1].replace("/", "_");
			} else if (fNameWoExt[0].contains("src/")) {
				id = fNameWoExt[0].split("src.")[1].replace("/", "_");
			} else {
				id = fNameWoExt[0].replace("/", "_");
			}
		} else if (fNameWoExt[fNameWoExt.length - 1].equals("cs")) {
			String fileNameWoCS = srcPath.substring(0, srcPath.lastIndexOf(".cs"));
			String justFileName = fileNameWoCS;
			if (fileNameWoCS.endsWith(".aspx")) {
				justFileName = fileNameWoCS.substring(0, fileNameWoCS.lastIndexOf(".aspx"));
			}
			String[] fullName = justFileName.split("/");
			if (nsClassMapForDotNet != null && nsClassMapForDotNet.containsKey(srcPath)
					&& !nsClassMapForDotNet.get(srcPath).equals("")) {
				id = nsClassMapForDotNet.get(srcPath);
			} else if (nsClassMapForDotNet != null && nsClassMapForDotNet.containsKey(srcPath)) {
				id = fullName[fullName.length - 1];
			} else {
				id = "DefaultPackage_" + fullName[fullName.length - 1];
			}
		} else {
			id = fNameWoExt[0].replace("/", "_");
		}
		return id;
	}

	public static String mapSonarSeverity(String sonarSeverity) {
		if (sonarSeverity.equalsIgnoreCase("blocker") || sonarSeverity.equalsIgnoreCase("critical")) {
			return "high";
		} else if (sonarSeverity.equalsIgnoreCase("major")) {
			return "medium";
		} else if (sonarSeverity.equalsIgnoreCase("minor") || sonarSeverity.equalsIgnoreCase("info")) {
			return "low";
		} else {
			return "none";
		}
	}

	public static List<Integer> getSeverityDetails() {
		List<Integer> severityCount = new ArrayList<>();
		severityCount.add(critical);
		severityCount.add(blocker);
		severityCount.add(major);
		severityCount.add(minor);
		severityCount.add(info);
		critical = 0;
		blocker = 0;
		major = 0;
		minor = 0;
		info = 0;
		return severityCount;
	}
}
