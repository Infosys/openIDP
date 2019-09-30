/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.infosys.codeanalysisrecommendation.RecommendationByCSV;
import com.infosys.convertor.ConvertBuildInfo;
import com.infosys.convertor.ConvertBuildLog;
import com.infosys.convertor.ConvertCheckstyle;
import com.infosys.convertor.ConvertCobertura;
import com.infosys.convertor.ConvertDSPriv;
import com.infosys.convertor.ConvertEcatt;
import com.infosys.convertor.ConvertFileNet;
import com.infosys.convertor.ConvertFindbugs;
import com.infosys.convertor.ConvertFxCop;
import com.infosys.convertor.ConvertFxCopCons;
//import com.infosys.convertor.ConvertICQAReport;
import com.infosys.convertor.ConvertIFast;
import com.infosys.convertor.ConvertIstanbul;
import com.infosys.convertor.ConvertJMeter;
import com.infosys.convertor.ConvertJShintCS;
import com.infosys.convertor.ConvertJUnit;
import com.infosys.convertor.ConvertJacoco;
import com.infosys.convertor.ConvertLintReport;
import com.infosys.convertor.ConvertNUnit;
import com.infosys.convertor.ConvertOATS;
import com.infosys.convertor.ConvertOracleAnalysis;
import com.infosys.convertor.ConvertPQM;
import com.infosys.convertor.ConvertParasoftSOATest;
import com.infosys.convertor.ConvertPmd;
import com.infosys.convertor.ConvertProtractor;
import com.infosys.convertor.ConvertPythonUT;
import com.infosys.convertor.ConvertSAPUnit;
import com.infosys.convertor.ConvertSCMInfo;
import com.infosys.convertor.ConvertSci;
import com.infosys.convertor.ConvertSoapUIReport;
import com.infosys.convertor.ConvertSonarqube;
import com.infosys.convertor.ConvertTRXNunit;
import com.infosys.convertor.ConvertTSLintReport;
import com.infosys.json.CodeAnalysis;
import com.infosys.json.CodeQuality;
import com.infosys.json.Codecoverage;
import com.infosys.json.CoverageDSPrivJson;
import com.infosys.json.CoverageDetails;
import com.infosys.json.FileNet;
import com.infosys.json.FindBugs;
import com.infosys.json.Functional;
import com.infosys.json.JUnit;
import com.infosys.json.JiraDeployObjects;
import com.infosys.json.JsonClass;
import com.infosys.json.Lint;
import com.infosys.json.PackageContent;
import com.infosys.json.ParasoftSOATest;
import com.infosys.json.Pega;
import com.infosys.json.Pmd;
import com.infosys.json.Protractor;
import com.infosys.json.PythonUT;
import com.infosys.json.Reports;
import com.infosys.json.RuleSet;
import com.infosys.json.Siebel;
import com.infosys.json.SoapUIReport;
import com.infosys.json.Sonar;
import com.infosys.json.SonarDetails;
import com.infosys.json.TSLint;
import com.infosys.json.TestCaseResult;
import com.infosys.jsonschema.Checkstyle;
import com.infosys.jsonschema.CodeMetric;
import com.infosys.jsonschema.VersionInfo;
import com.infosys.utilities.csvparser.CSVWriterUtility;
import com.opencsv.CSVReader;

public class EntryHome {
	@Autowired
	private static final String JSON = ".json";
	private static final Logger logger = Logger.getLogger(EntryHome.class);
	private static CodeQuality codeQuality = new CodeQuality();
	public static final String CONTENTTYPE="Content-Type";
	public static final String APPLICATIONJSON="application/json";
	private EntryHome() {
	}

	

	public static void main(String[] args) {
		SSLUtilities.trustAllHostnames();
		SSLUtilities.trustAllHttpsCertificates();
		logger.info("DevOps Json util Version Frozen");
		try {
			int flag = 1;
			if (flag == 1) {
				convertAllFiles(args);
			} else {
				convertChangeLog(args);
			}
			delFile(args[0]);
		} catch (IOException e1) {
			logger.error(e1);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public static void delFile(String path) {
		File file = new File(path.replaceAll("\\\\", "/"));
		if (file.exists()) {
			String[] entries = file.list();
			for (String s : entries) {
				File currentFile = getFileObject(file.getPath(), s);
				currentFile.delete();
			}
			file.delete();
		}
	}

	private static File getFileObject(String path, String s) {
		return new File(path, s);
	}

	public static void convertAllFiles(String[] args) throws IOException {
		File folder = new File(args[0].replaceAll("\\\\", "/"));
		File[] listOfFiles = folder.listFiles();
		JsonClass json = new JsonClass();
		json.setApplication(args[1]);
		json.setPipelineName(args[1] + "_" + args[5]);
		if (args.length > 6 && !args[6].equals("NA"))
			json.setGroupId(args[6]);
		if (args.length > 7 && !args[7].equals("NA"))
			json.setGroupName(args[7]);
		if (args.length > 8)
			json.setbuildId(args[8]);
		
		String technologyName="";	
		if (args.length > 18 && !args[18].equals("NA"))technologyName=args[18];
		String serviceUrl="";
		if (args.length > 14 && !args[14].equals("NA"))serviceUrl=args[14]+":8889";
		String artifactName="";
		if (args.length > 17 && !args[17].equals("NA"))artifactName=args[17];
		
		// For VSTS
		// Passing vsts flag and setting flag value accordingly
		boolean isVSTS = false;
		if (args.length > 20 && args[20] != null && !args[20].equals("NA") && args[20].equalsIgnoreCase("story")) {
			isVSTS = true;
		}
		logger.info("VSTS flag is set to " + isVSTS);
		List<File> list2 = new ArrayList<>();
		int index = findPQMFileIndex(listOfFiles, list2, args[1]);
		if (index != -1) {
			list2.add(listOfFiles[index]);
		}
		
		
		List<CoverageDetails> coverageDetails = null;
		FileNet fileNetJsonObj = null;
		boolean isFileNet = false;
		boolean isDeploy=false;
		
		logger.info(isFileNet);
		logger.info("FileNet flag is set to " + isFileNet);
		if (args.length > 20 && args[20] != null && !args[20].equals("NA") && args[20].equalsIgnoreCase("filenet")) {
			logger.info("inside if");
			isFileNet = true;
		}
		logger.info("FileNet flag is set to " + isFileNet);
		if (isFileNet) {
			logger.info("inside filenet");
			final int REST_URL_INDEX = 11;
			final int AUTH_NAME_INDEX = 12;
			final int AUTH_PASSWORD_INDEX = 13;
			final int TRIGGER_ID_INDEX = 21;
			final int BUILD_NUMBER_INDEX = 8;
			String restURL = args[REST_URL_INDEX];
			logger.info("RestURL" + restURL);
			String authName = args[AUTH_NAME_INDEX];
			logger.info("authName" + authName);
			String authPassword = args[AUTH_PASSWORD_INDEX];
			logger.info("authPassword" + authPassword);
			String triggerId = args[TRIGGER_ID_INDEX];
			logger.info("triggerId" + triggerId);
			String buildNumber = args[BUILD_NUMBER_INDEX];
			logger.info("buildNumber" + buildNumber);
			String expoertDataFilePath = args[22];
			logger.info("expoertDataFilePath" + expoertDataFilePath);
			String importDataFilePath = args[23];
			logger.info("importDataFilePath " + importDataFilePath);
			String exportFile = "ExportManifests/ExportData.xml";
			logger.info("exportFile" + exportFile);
			String importFile = "DEV-SIT/PairConfig.xml";
			logger.info("importFile" + importFile);
			String requestUrl = restURL + "/" + triggerId + "/" + buildNumber + "/fileNet";
			try {
				URL url = new URL(requestUrl);
				HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
				String userCredentials = authName + ":" + authPassword;
				String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
				basicAuth = basicAuth.replaceAll("\n", "");
				basicAuth = basicAuth.replaceAll("\r", "");
				httpConnection.setRequestProperty("Authorization", basicAuth);
				httpConnection.setDoInput(true);
				httpConnection.setDoOutput(true);
				httpConnection.setRequestMethod("POST");
				httpConnection.setRequestProperty(CONTENTTYPE, APPLICATIONJSON);
				fileNetJsonObj = new ConvertFileNet().convert(expoertDataFilePath, importDataFilePath, triggerId,
						exportFile, importFile);
				json.setFileNet(fileNetJsonObj);
				logger.info("FileNet Debug: " + json.getFileNet().getFileNetExport().toString());
				logger.info("FileNet Debug: " + json.getFileNet().getFileNetImport());
				logger.info("complete json");
				logger.info(json.toString());
				ObjectMapper objMapper = new ObjectMapper();
				DataOutputStream writer = new DataOutputStream(httpConnection.getOutputStream());
				logger.info("Dataoutput stream object createddd");
				logger.info("POST RequestURL: " + requestUrl + ", JSON=" + json);
				objMapper.writeValue(writer, json);
				logger.info("Write value");
				writer.flush();
				writer.close();
				httpConnection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					logger.info(line);
				}
				br.close();
				httpConnection.disconnect();
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		processVSTS(args, isVSTS);
		processSAPTRDeploy(args,isDeploy);
		
		PackageContent packageContent=new PackageContent();
		Siebel siebel=new Siebel();
		boolean isSiebel=false;
		
		for (int i = 0; i < list2.size(); i++) {
			if (list2.get(i).isFile() && list2.get(i).getName().toLowerCase().contains(args[1].toLowerCase())) {
				json = computeIDPJson(list2.get(i), json, args, coverageDetails);
			}
		}
		
		//Update Package Contents
		if(args.length>19 && args[19].toLowerCase().contains("build") && json.getBuildDetails().get(0).getBuiltStatus().toLowerCase().contains("fail")){
			packageContent.setArtifactName(artifactName);
			Gson gson = new Gson();
			String packageInString=gson.toJson(packageContent);
			
			sendPackageContent(packageInString,serviceUrl);
			logger.info("Updating package content to null as build failed");
		}
		else if(args.length>19 && args[19].toLowerCase().contains("build") && technologyName.toLowerCase().contains("siebel"))
		{
			packageContent.setArtifactName(artifactName);
			packageContent.setSiebel(siebel);
			Gson gson = new Gson();
			String packageInString=gson.toJson(packageContent);
			
			sendPackageContent(packageInString,serviceUrl);
			logger.info("Siebel package content parsed");
		}
		else if(technologyName.toLowerCase().contains("pega") && args.length>19 && args[19].toLowerCase().contains("build"))
		{
			String path=args[0].replaceAll("\\\\", "/");
			path=path.replace("IDP_DevopsJSON_Integration/Jenkins_Reports","Deploy");
			File pegaFile=new File(path);
			File[] listOfPegaFiles = pegaFile.listFiles();
			
			Pega pega=new Pega();
			List<String> pegaFileList=new ArrayList<>();
			for(int i=0;i<listOfPegaFiles.length;i++)
			{
				if(listOfPegaFiles[i].getPath().endsWith("zip") || listOfPegaFiles[i].getPath().endsWith("jar"))
				{
					
					//System.out.println(listOfPegaFiles[i].getName());
					pegaFileList.add(listOfPegaFiles[i].getName());
				}
			}
			pega.setPegaFileList(pegaFileList);
			packageContent.setArtifactName(artifactName);
			packageContent.setPega(pega);
			Gson gson = new Gson();
			String packageInString=gson.toJson(packageContent);
			
			sendPackageContent(packageInString,serviceUrl);
			logger.info("Pega package content parsed");
		}
		
		
		logger.info(json.getCodecoverage());
		String idPrefixFlag = args[4];
		String appName = args[1];
		String prefixForId = preparePrefixForId(idPrefixFlag, appName);
		
		if (!"NA".equals(args[2]) && !"NA".equals(args[3])) {
			try {
				SonarDetails sonarDetailsobj = new SonarDetails();
				sonarDetailsobj.setRateperhour(ConvertSonarqube.readSonarrateperhour());
				if (!args[3].endsWith("/")) {
					args[3] = args[3].concat("/");
				}
				List<CodeAnalysis> ca = new ArrayList<>();
				ConvertSonarqube.convert(args[0], ca, args[2], args[3], prefixForId, sonarDetailsobj);
				List<Integer> severity = ConvertSonarqube.getSeverityDetails();
				logger.info("try");
				json.setCodeAnalysis(ca);
				String[] sonarDetailsArray = args[2].split("#");
				sonarDetailsobj.setSonarPrjctKey(sonarDetailsArray[0]);
				sonarDetailsobj.setSonarUserName(sonarDetailsArray[1]);
				sonarDetailsobj.setSonarPassword(sonarDetailsArray[2]);
				sonarDetailsobj.setSonarServer("http://" + args[3]);
				json.setSonarDetails(sonarDetailsobj);
				Sonar sonar = new Sonar();
				sonar.setCritical(severity.get(0));
				sonar.setBlocker(severity.get(1));
				sonar.setMajor(severity.get(2));
				sonar.setMinor(severity.get(3));
				sonar.setInfo(severity.get(4));
				codeQuality.setSonar(sonar);
				json.setCodeQuality(codeQuality);
			} catch (Exception e) {
				logger.error("Error while reading sonar");
			}
		}
		// Populate RuleSet Section
		addToRuleSet(json);
		if (json.getCodeAnalysis().isEmpty()) {
			json.setCodeAnalysis(null);
		}
		// logic to search
		searchReports(args, json);
		//
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd'T'HH_mm_ss").format(new Date());
		String output = generateOpFilename(args, json, timeStamp);
		String outputPath = generateOutputPath(args, json, timeStamp);
		Gson gson = new Gson();
		Gson gson1 = new Gson();
		try (PrintWriter out = new PrintWriter(outputPath)) {
			out.println(gson1.toJson(json));
		}
		try (PrintWriter out = new PrintWriter(output)) {
			out.println(gson.toJson(json));
		}
	}
	
	private static void sendPackageContent(String packageContent,String serviceUrl)
	{
		try{
			String requestUrl="https://"+serviceUrl+"/idprest/releaseService/release/insert/package%26dummy";
	        System.out.println("service url for package content "+requestUrl);
	        
	        URL url = new URL(requestUrl);
	       
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
 //           System.out.println("asd");
//            String userCredentials = "admin"+":"+"admin";
//            String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
//            basicAuth= basicAuth.replaceAll("\n","");
//            basicAuth=basicAuth.replaceAll("\r","");
//            conn.setRequestProperty ("Authorization", basicAuth);
            
            OutputStream os = conn.getOutputStream();
            os.write(packageContent.getBytes("UTF-8"));
            os.close();
            
            conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer jsonString = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                    jsonString.append(line);
            }
            br.close();
            

            
            conn.disconnect();
            
            //System.out.println("done");
		}
		catch(Exception e)
		{
			logger.error("Exception while sending packageContent",e);
		}
	}
	
	private static void processSAPTRDeploy(String[] args, boolean bIsDeploy)
	{
		String buildTriggerID="";
		String dbUser="";
		String dbPort="";
		String dbPassword="";
		String technologyName=args[18];	
		String stage=args[19];
		boolean isDeploy=bIsDeploy;
		if(stage.equalsIgnoreCase("deploy") && technologyName.equalsIgnoreCase("sapnoncharm")){
			isDeploy=true;
			if(args.length>20 )buildTriggerID=args[20];
			dbUser="postgres";
			if(args.length>21 )dbPort=args[21];
			if(args.length>22 )dbPassword=args[22];
		}
		if(isDeploy && !"".equals(buildTriggerID) && !"".equals(dbPort) && !"".equals(dbPassword))
		{

			///////postgres connection
	
			String url = "jdbc:postgresql://"+args[14]+":"+dbPort+"/IDP";
		    String user = dbUser;
		    String password = dbPassword;
		    
		    String query="Select trigger_entity from ttrigger_history where trigger_id='"+buildTriggerID+"'";
			try(
					Connection connectionNew =DriverManager.getConnection(url, user, password);
					PreparedStatement preparedStatement = connectionNew.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
					ResultSet rs = preparedStatement.executeQuery();
					)
			{
				
				ResultSetMetaData metars = rs.getMetaData();
				if (metars.getColumnCount() != 0) {
					while(rs.next())
					{
						String jsonString=rs.getString(1);
						JSONObject applicationInfoJson=new JSONObject(jsonString);
						
						String appName=args[1];
						String pipelineName=args[5];
						String landscapeName;
						String userStory;
						String releaseNo;
						
						landscapeName=applicationInfoJson.getString("lanscapeName");
						userStory=applicationInfoJson.getString("userStories");
						releaseNo=applicationInfoJson.getString("releaseNumber");
						List<String> userStoryNameList=new ArrayList();
						JSONArray userStoryMapping= applicationInfoJson.getJSONArray("userStoryMapping");
						for(int i=0;i<userStoryMapping.length();i++)
						{
							String userStoryName=userStoryMapping.getJSONObject(i).getString("userstory");
							userStoryNameList.add(userStoryName);
						}
						
						//kafka connection
						JiraDeployObjects jiraDeployObjects=new JiraDeployObjects();
						jiraDeployObjects.setApplicationName(appName);
						jiraDeployObjects.setLandscapeName(landscapeName);
						jiraDeployObjects.setPipelineName(pipelineName);
						jiraDeployObjects.setReleaseName(releaseNo);
						jiraDeployObjects.setUserStoryName(userStory);
						//Modify to include Userstory list
						jiraDeployObjects.setUserStoryNameList(userStoryNameList);
						Gson gson = new Gson();
						String str = gson.toJson(jiraDeployObjects);
						
						///hitting rest api
						try{
							String rebaseReqUrlString=args[11]+"/SAP/JiraDetailUpdateForImport";
							URL rebaseUrl = new URL(rebaseReqUrlString);
							HttpURLConnection rebaseConn = (HttpURLConnection) rebaseUrl.openConnection();
							rebaseConn.setRequestMethod("POST");
							rebaseConn.setDoOutput(true);
							rebaseConn.setRequestProperty(CONTENTTYPE, APPLICATIONJSON);
							 OutputStreamWriter writer = new OutputStreamWriter(rebaseConn.getOutputStream());
							 writer.write(str);
						     writer.close();

						    logger.info("connected rest service");
							logger.info(rebaseConn.getResponseCode());
							
						}catch(Exception e){
							logger.error("Error in sending rebase data for insertion: "+e);
				
						}
						
					}
				}
			}
			catch(Exception e)
			{
				logger.error("Errors while populating applicationInfo",e);
			}

		}
	}
	
	private static void processVSTS(String[] args, boolean isVSTS) {
		// If VSTS is selected
		if (isVSTS) {
			// Called method to set value in JSON
			try {
				String restUrl = args[11];
				String authUname = args[12];
				String password = args[13];
				String triggerID = args[21];
				logger.info("printintg args 21 " + args[21]);
				String buildNum = args[8];
				String requestUrl = restUrl + triggerID + "/" + buildNum;
				URL url = new URL(requestUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				String userCredentials = authUname + ":" + password;
				String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
				basicAuth = basicAuth.replaceAll("\n", "");
				basicAuth = basicAuth.replaceAll("\r", "");
				connection.setRequestProperty("Authorization", basicAuth);
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setRequestProperty(CONTENTTYPE, APPLICATIONJSON);
				connection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					logger.info(line);
				}
				br.close();
				connection.disconnect();
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private static void searchReports(String[] args, JsonClass json) {
		try {
			ArrayList<String> fileNamesToSearch = new ArrayList<>();
			ArrayList<File> reportsList = new ArrayList<>();
			//
			fileNamesToSearch.add("checkstyle_report.html");// CheckStyleReports-done
			fileNamesToSearch.add("checkstyle-result.html");// CheckStyleReports-done
			fileNamesToSearch.add("report-jshint-checkstyle.html");// jshint
																	// checkstyle
			fileNamesToSearch.add("PMD_Report.html");// PMD-done
			fileNamesToSearch.add("findbugs-default.html");// Findbugs-done
			fileNamesToSearch.add("SummaryReport.html");// Qualitia
			fileNamesToSearch.add("Fastest.txt");// iFastReports
			fileNamesToSearch.add("report.html");// Checkmarx
			fileNamesToSearch.add("detailed-report.html");// Accelera
			fileNamesToSearch.add("coverage.xml");// Cobertura-done
			// FileNamesToSearch.add("testng-results.xml");//Selenium Junit
			// FileNamesToSearch.add("testng-results.xml");//Selenium Junit
			//
			fileNamesToSearch.add("Test_Report.pdf");// iTops done
			fileNamesToSearch.add("index.html");// Selenium TestNG
			fileNamesToSearch.add("log.html");// Robot-done
			fileNamesToSearch.add("JUnit_Test.xml");// Junit
			fileNamesToSearch.add("result.jtl");// JMeter-done
			//
			String temp1;
			String[] searchloc = args[0].replaceAll("\\\\", "/").split("/");
			String finalLoc = "";
			for (int i = 0; i < searchloc.length - 2; i++) {
				finalLoc += searchloc[i] + "/";
			}
			File file = new File(finalLoc);
			// Findfunction will populate the reports list with all the reports
			findFunction(fileNamesToSearch, file, reportsList);
			json.setReports(null);
			String temp = "";
			String finalURL = "";
			String[] tempList = null;
			boolean flag;
			Reports r = new Reports();
			// for junit publisher
			int t;
			String locuptobuildnumber = null;
			if (json.getBaseURL() != null) {
				t = json.getBaseURL().lastIndexOf('/');
				locuptobuildnumber = json.getBaseURL().substring(0, t);
			}
			if (validateUrl(locuptobuildnumber + "/testReport")) {
				r.addJUnitReports(locuptobuildnumber + "/testReport");
			}
			for (File x : reportsList) {
				finalURL = "";
				temp = x.getPath();
				temp1 = temp.replaceAll("\\\\", "/");
				tempList = temp1.split("/");
				flag = false;
				for (int i = 0; i <= tempList.length - 1; i++) {
					if (tempList[i].equals(args[1] + "_" + args[5])) {
						flag = true;
					}
					if (flag && (i < tempList.length - 1)) {
						finalURL += tempList[i + 1] + "/";
					}
				}
				// removing last slash
				finalURL = (finalURL.lastIndexOf('/') != -1) ? finalURL.substring(0, finalURL.lastIndexOf('/'))
						: finalURL;
				// replacing spaces in foldername with %20 for make a valid URL
				finalURL = finalURL.replaceAll(" ", "%20");
				if (finalURL.contains("checkstyle")) {
					if (validateUrl(json.getBaseURL() + "/" + finalURL)) {
						logger.info("CheckStyle report url added");
						r.addCheckStyleReport(json.getBaseURL() + "/" + finalURL);
					}
				} else if (finalURL.contains("PMD")) {
					if (validateUrl(json.getBaseURL() + "/" + finalURL)) {
						logger.info("PMD report url added");
						r.addPMDReport(json.getBaseURL() + "/" + finalURL);
					}
				} else if (finalURL.toLowerCase().contains("findbugs-default.html")) {
					if (validateUrl(json.getBaseURL() + "/" + finalURL)) {
						logger.info("Find Bugs report url added");
						r.addFindBugsReport(json.getBaseURL() + "/" + finalURL);
					}
				}
				//
				else if (finalURL.toLowerCase().contains("_result.jtl")) {
					if (json.getBaseURL() != null) {
						int sample = json.getBaseURL().lastIndexOf('/');
						String locuptobn = json.getBaseURL().substring(0, sample);
						int sample2 = locuptobn.lastIndexOf('/');
						String locfinal = json.getBaseURL().substring(0, sample2);
						if (validateUrl(locfinal + "/" + "performance")) {
							logger.info("JMeter report url added");
							r.addJMeterReports(locfinal + "/" + "performance");
						}
					}
				}
				//
				else if (finalURL.toLowerCase().toLowerCase().contains("coverage.xml")) {
					int sample = json.getBaseURL().lastIndexOf('/');
					String locuptobn = json.getBaseURL().substring(0, sample);
					if (validateUrl(locuptobn + "/cobertura")
							&& (finalURL.toLowerCase().toLowerCase().contains("cobertura"))) {
						logger.info("Cobertura report url added");
						r.addCoberturaReport(locuptobn + "/cobertura");
					}
				}
				//
				else if (finalURL.toLowerCase().contains("qualitia")) {
					if (validateUrl(json.getBaseURL() + "/" + finalURL)) {
						logger.info("Qualitia report url added");
						r.addQualitiaReports(json.getBaseURL() + "/" + finalURL);
					}
				} else if (finalURL.contains("Fastest.txt")) {
					if (validateUrl(json.getBaseURL() + "/" + finalURL)) {
						logger.info("iFast report url added");
						r.addiFastReports(json.getBaseURL() + "/" + finalURL);
					}
				} else if (finalURL.contains("Checkmarx") && finalURL.contains("report.html")) {
					if (validateUrl(json.getBaseURL() + "/" + finalURL)) {
						logger.info("Check Marx report url added");
						r.addCheckmarxReport(json.getBaseURL() + "/" + finalURL);
					}
				} else if (finalURL.contains("detailed-report.html")) {
					if (validateUrl(json.getBaseURL() + "/" + finalURL)) {
						logger.info("Accelerate report url added");
						r.addAccelerateReport(json.getBaseURL() + "/" + finalURL);
					}
				} else if (finalURL.contains("Test_Report.pdf")) {
					if (validateUrl(json.getBaseURL() + "/" + finalURL)) {
						logger.info("iTops report url added");
						r.addiTopsReport(json.getBaseURL() + "/" + finalURL);
					}
				} else if (finalURL.contains("log.html")) {
					if (finalURL.contains("reports") && (validateUrl(json.getBaseURL() + "/" + finalURL))) {
						logger.info("Robots report url added");
						r.addRobotReports(json.getBaseURL() + "/" + finalURL);
					}
				} else if (finalURL.contains("index.html")
						&& (finalURL.contains("test-output") && validateUrl(json.getBaseURL() + "/" + finalURL))) {
					logger.info("TestNG Selenium report url added");
					r.addSeleniumReport(json.getBaseURL() + "/" + finalURL);
				}
				if (r.getAccelerateReports() != null || r.getCheckmarxReports() != null || r.getCheckReports() != null
						|| r.getCoberturareports() != null || r.getFindBugsReports() != null
						|| r.getiFastReports() != null || r.getiTopsReps() != null || r.getjMeterReports() != null
						|| r.getJunitReports() != null || r.getPMDReports() != null || r.getQualitia() != null
						|| r.getRobotReports() != null || r.getSeleniumReports() != null) {
					json.setReports(r);
				}
			}
			json.setBaseURL(null);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private static boolean validateUrl(String url1) {
		URL u;
		try {
			logger.info(url1);
			u = new URL(url1.replaceAll(" ", "%20"));
			logger.info(u);
			HttpURLConnection huc = (HttpURLConnection) u.openConnection();
			Properties p = new Properties();
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream stream = loader.getResourceAsStream("config.properties");
			p.load(stream);
			String credentials = p.getProperty("JENKINS_UN") + ":" + p.getProperty("JENKINS_PW");
			String basicAuth = "Basic " + new String(Base64.encodeBase64(credentials.getBytes()));
			huc.setRequestProperty("Authorization", basicAuth);
			huc.connect();
			int code = huc.getResponseCode();
			if (code == 200)
				return true;
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	private static void findFunction(ArrayList<String> fileNamesToSearch, File file, ArrayList<File> reportsList) {
		File[] list = file.listFiles();
		if (list == null) {
			return;
		}
		for (File x : list) {
			if (x.isDirectory()) {
				findFunction(fileNamesToSearch, x, reportsList);
			} else {
				for (String y : fileNamesToSearch) {
					if (x.getName().contains(y) && !reportsList.contains(x)) {
						reportsList.add(x);
					}
				}
			}
		}
	}

	private static String generateOutputPath(String[] args, JsonClass json, String timeStamp) {
		String output;
		String path = makeDir(args[0].replaceAll("\\\\", "/"));
		if (json.getbuildId() != null) {
			output = path + "/" + args[1] + "_" + args[5] + "_" + json.getbuildId() + "_" + timeStamp + JSON;
		} else {
			output = path + "/" + args[1] + "_" + args[5] + "_" + timeStamp + JSON;
		}
		return output;
	}

	private static String makeDir(String path) {
		String folderPath = path + "/../../../../Devops_Json";
		if (new File(folderPath).mkdirs()) {
			logger.info("Directory is created!");
		} else {
			logger.info("Directory already exists!");
		}
		return folderPath;
	}

	private static JsonClass computeIDPJson(File reportfile, JsonClass jsonObj, String[] args,
			 List<CoverageDetails> cd) throws IOException {
		JsonClass json=jsonObj;
		String idPrefixFlag = args[4];
		String appName = args[1];
		String pipName = args[5];
		String prefixForId = preparePrefixForId(idPrefixFlag, appName);
		String tfsPath = null;
		String reportType = reportfile.getName().replaceFirst(appName + "_" + pipName, "");
		if (args.length > 11) {
			tfsPath = args[11];
		}
		if (reportfile.getName().toLowerCase().endsWith("txt") || reportfile.getName().toLowerCase().endsWith("xml")
				|| reportfile.getName().toLowerCase().endsWith("html")
				|| reportfile.getName().toLowerCase().endsWith("json")
				|| reportfile.getName().toLowerCase().endsWith("jtl")
				|| reportfile.getName().toLowerCase().endsWith("xls")
				|| reportfile.getName().toLowerCase().endsWith("trx")
				|| reportfile.getName().toLowerCase().endsWith("csv")) {
			logger.info("parsing => " + reportfile);
//			if ((reportfile.getName().toLowerCase().contains("pqm_report_pmd-existing"))
//					&& reportfile.getName().toLowerCase().endsWith(".txt")) {
//				ConvertICQAReport.convert(reportfile.getCanonicalPath(), args[1], args[5], args[11]);
//			}else
				if (reportType.toLowerCase().contains("parasoftsoatest")
					&& reportType.toLowerCase().endsWith("xml")) {
				List<TestCaseResult> listTR = json.getTestCaseResult();
				if (listTR == null)
					listTR = new ArrayList<>();
				ParasoftSOATest pst = ConvertParasoftSOATest.convert(reportfile.getCanonicalPath(), json, listTR);
				if (json.getFunctionalTest() != null) {
					Functional ft = json.getFunctionalTest();
					ft.setParasoftSOATest(pst);
					json.setFunctionalTest(ft);
					json.setTestCaseResult(listTR);
				} else {
					Functional ftNew = new Functional();
					ftNew.setParasoftSOATest(pst);
					json.setFunctionalTest(ftNew);
					json.setTestCaseResult(listTR);
				}
				logger.info("ParasoftSOATest report parsed");
			} else if (reportfile.getName().toLowerCase().contains("codeanalysisreports")
					&& reportfile.getName().toLowerCase().endsWith("xls")) {
				try {
					ConvertOracleAnalysis.convert(reportfile.getCanonicalPath(), json);
				} catch (Exception e) {
					logger.info("Exception in converting Oracle Analysis Report" + e.getMessage());
				}
			} else if (reportfile.getName().toLowerCase().contains("generalreport")
					&& reportfile.getName().toLowerCase().endsWith("xml")) {
				try {
					ConvertOATS.convert(reportfile.getPath(), json);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			} else if (reportfile.getName().toLowerCase().contains("pythontest")
					&& reportfile.getName().toLowerCase().endsWith("xml")) {
				try {
					List<TestCaseResult> tr ;
					tr = ConvertPythonUT.convert(reportfile.getCanonicalPath(), json, prefixForId);
					json.setTestCaseResult(tr);
					Functional ft = json.getFunctionalTest();
					PythonUT put = ConvertPythonUT.getPUnitSummary(json);
					if (ft == null && put != null)
						ft = new Functional();
					if (put != null) {
						ft.setPython(put);
						json.setFunctionalTest(ft);
					}
					logger.info("Python UT converted Successfully ");
				} catch (Exception e) {
					logger.error("Exception in parsing Python unit test reports");
				}
			} else if (reportfile.getName().toLowerCase().contains("result")
					&& reportfile.getName().toLowerCase().endsWith("txt")) {
				try {
					List<CodeAnalysis> ca ;
					ca = ConvertTSLintReport.convert(reportfile.getCanonicalPath());
					TSLint tsLint = new TSLint();
					ConvertTSLintReport.setTotalSeverities(ca);
					tsLint.setHighViolations(ConvertTSLintReport.getHighViolations());
					tsLint.setLowViolations(ConvertTSLintReport.getLowViolations());
					tsLint.setMediumViolations(ConvertTSLintReport.getMediumViolations());
					codeQuality.setTsLint(tsLint);
					json.setCodeQuality(codeQuality);
					json.setCodeAnalysis(ca);
				} catch (Exception e) {
					logger.error("Exception in converting file:" + reportfile.getName());
				}
			} else if (reportfile.getName().toLowerCase().contains("lintreport")
					&& reportfile.getName().toLowerCase().endsWith("xml")) {
				Lint lint = new Lint();
				List<CodeAnalysis> ca = new ArrayList<>();
				ConvertLintReport.convert(reportfile.getCanonicalPath(), lint, codeQuality, ca);
				json.setCodeAnalysis(ca);
				json.setCodeQuality(codeQuality);
			} else if (reportType.toLowerCase().contains("sui_test") && reportType.toLowerCase().endsWith("xml")) {
				List<TestCaseResult> listTR = json.getTestCaseResult();
				if (listTR == null)
					listTR = new ArrayList<>();
				SoapUIReport sr = ConvertSoapUIReport.convert(reportfile.getCanonicalPath(), listTR);
				if (json.getFunctionalTest() != null) {
					Functional ft = json.getFunctionalTest();
					ft.setSoapUIReport(sr);
					json.setFunctionalTest(ft);
					json.setTestCaseResult(listTR);
				} else {
					Functional ftNew = new Functional();
					ftNew.setSoapUIReport(sr);
					json.setFunctionalTest(ftNew);
					json.setTestCaseResult(listTR);
				}
				logger.info("SoapUI report parsed");
			} else if (reportType.toLowerCase().contains("protractorxml") && reportType.toLowerCase().endsWith("xml")) {
				List<TestCaseResult> tr = new ArrayList<>();
				Protractor pt = ConvertProtractor.convert(reportfile.getCanonicalPath(), json, tr);
				if (json.getFunctionalTest() != null) {
					Functional ft = json.getFunctionalTest();
					ft.setProtractor(pt);
					json.setFunctionalTest(ft);
				} else {
					Functional ftNew = new Functional();
					ftNew.setProtractor(pt);
					json.setFunctionalTest(ftNew);
				}
				logger.info("protractor report parsed");
			} else if (reportfile.getName().toLowerCase().contains("jshint-checkstyle")) {
				List<CodeAnalysis> ca = new ArrayList<>();
				Map<String, String> ruleToValueCheckStyle = new HashMap<>();
				ConvertJShintCS.convert(reportfile.getCanonicalPath(), ruleToValueCheckStyle, ca);
				json.setCodeAnalysis(ca);
				codeQuality.setCheckstyle(ConvertJShintCS.getCheckStyleCodeQuality(json));
				json.setCodeQuality(codeQuality);
			} else if (reportType.toLowerCase().contains("checkstyle")) {
				Map<String, String> ruleToValueCheckStyle = RecommendationByCSV.createMap("csv/checkstyle.csv",
						"Check");
				List<CodeAnalysis> ca ;
				ca = ConvertCheckstyle.convert(reportfile.getCanonicalPath(), ruleToValueCheckStyle, prefixForId);
				List<Integer> severity = ConvertCheckstyle.getcheckStyleSeverity();
				Checkstyle checkstyle = new Checkstyle();
				checkstyle.setCritical(severity.get(0));
				checkstyle.setBlocker(severity.get(1));
				checkstyle.setMajor(severity.get(2));
				checkstyle.setMinor(severity.get(3));
				checkstyle.setInfo(severity.get(4));
				//
				//
				codeQuality.setCheckstyle(checkstyle);
				json.setCodeQuality(codeQuality);
				json.setCodeAnalysis(ca);
			} else if (reportfile.getName().toLowerCase().contains("buildlog")) {
				ConvertBuildLog.convert(reportfile.getCanonicalPath(), json,args);
			} else if (reportType.toLowerCase().contains("findbugs")) {
				Map<String, String> ruleToValueCheckStyle = RecommendationByCSV.createMap("csv/findbugs.csv", "Check");
				List<CodeAnalysis> ca;
				ca = ConvertFindbugs.convert(reportfile.getCanonicalPath(), ruleToValueCheckStyle, prefixForId);
				List<Integer> severity = ConvertFindbugs.getFindBugsSeverity();
				FindBugs fb = new FindBugs();
				fb.setCritical(severity.get(0));
				fb.setBlocker(severity.get(1));
				fb.setMajor(severity.get(2));
				fb.setMinor(severity.get(3));
				fb.setInfo(severity.get(4));
				//
				//
				codeQuality.setFindbugs(fb);
				json.setCodeQuality(codeQuality);
				json.setCodeAnalysis(ca);
			} else if (reportType.toLowerCase().contains("pmd")) {
				Map<String, String> ruleToValuePMD = RecommendationByCSV.createMap("csv/pmd.csv", "");
				List<CodeAnalysis> ca ;
				ca = ConvertPmd.convert(reportfile.getCanonicalPath(), ruleToValuePMD, prefixForId);
				List<Integer> severity = ConvertPmd.getPmdSeverity();
				Pmd pmd = new Pmd();
				pmd.setCritical(severity.get(0));
				pmd.setBlocker(severity.get(1));
				pmd.setMajor(severity.get(2));
				pmd.setMinor(severity.get(3));
				pmd.setInfo(severity.get(4));
				codeQuality.setPmd(pmd);
				json.setCodeQuality(codeQuality);
				json.setCodeAnalysis(ca);
			} else if ((reportType.toLowerCase().contains("junit_") && reportType.toLowerCase().contains("lisa"))
					|| (reportType.toLowerCase().contains("junit_") && !reportType.toLowerCase().contains("junit_test"))
					|| reportType.toLowerCase().contains("android")) {
				// *********
				List<TestCaseResult> tr ;
				tr = ConvertJUnit.convert(reportfile.getCanonicalPath(), json, prefixForId);
				json.setTestCaseResult(tr);
				Functional ft = json.getFunctionalTest();
				JUnit ju = ConvertJUnit.getJUnitSummary(json);
				if (ft == null && ju != null)
					ft = new Functional();
				if (ju != null) {
					ft.setjUnit(ju);
					json.setFunctionalTest(ft);
				}
			} else if (reportType.toLowerCase().contains("goreportunit") && reportType.toLowerCase().endsWith("xml")) {
				json = ConvertJUnit.convertgo(reportfile.getPath(), json);
			} else if (reportType.toLowerCase().contains("nunit") && reportType.toLowerCase().endsWith("xml")) {
				List<TestCaseResult> tr ;
				tr = ConvertNUnit.convert(reportfile.getCanonicalPath(), json, prefixForId);
				json.setTestCaseResult(tr);
			} else if (reportType.toLowerCase().contains("nunit") && reportType.toLowerCase().endsWith("trx")) {
				ConvertTRXNunit.convert(reportfile.getCanonicalPath(), json);
			} else if (reportType.equalsIgnoreCase("findbugs")) {
				Map<String, String> ruleToValueFindBugs = RecommendationByCSV.createMap("csv/findbugs.csv", "");
				List<CodeAnalysis> ca;
				ca = ConvertFindbugs.convert(reportfile.getCanonicalPath(), ruleToValueFindBugs, prefixForId);
				json.setCodeAnalysis(ca);
			} else if (reportfile.getName().toLowerCase().contains("jacoco")) {
				ConvertJacoco.convert(reportfile.getCanonicalPath(), cd, json);
			} else if (reportfile.getName().toLowerCase().contains("istanbulcoverage")) {
				// For istanbul coverag
				ConvertIstanbul.convert(reportfile.getCanonicalPath(), json);
			} else if (reportType.toLowerCase().contains("coverage")) {
				ConvertCobertura.convert(reportfile.getCanonicalPath(), json);
			} else if (reportType.toLowerCase().contains("analysisresult")) {
				List<CodeAnalysis> ca ;
				ca = ConvertFxCop.convert(reportfile.getCanonicalPath());
				codeQuality.setFxCopReport(ConvertFxCopCons.convert(reportfile.getCanonicalPath()));
				json.setCodeAnalysis(ca);
			} else if (reportType.toLowerCase().contains("dspriv")) {
				Codecoverage cc = json.getCodecoverage();
				if (cc == null)
					cc = new Codecoverage();
				CoverageDSPrivJson cdsp = ConvertDSPriv.convert(reportfile.getCanonicalPath(), json);
				cc.setDsPriv(cdsp);
				json.setCodecoverage(cc);
			} else if (reportType.toLowerCase().contains("changelog")) {
				json = ConvertBuildInfo.convert(reportfile.getCanonicalPath(), json, prefixForId, tfsPath);
				json = ConvertSCMInfo.convert(reportfile.getCanonicalPath(), json, args[1]);
			} else if (reportType.toLowerCase().contains("jobdetails")) {
				json = ConvertBuildInfo.convertJobDetail(reportfile.getCanonicalPath(), json);
			} else if (reportType.toLowerCase().contains("codecheck")) {
				json = ConvertBuildInfo.convertCodeCoverage(reportfile.getCanonicalPath(), json);
			} else if (reportType.toLowerCase().contains("checkmarx")) {
				json = ConvertBuildInfo.convertCheckmarx(reportfile.getCanonicalPath(), json);
			} else if (reportType.toLowerCase().contains("summaryreport")) {
				// For Qualitia
				json = ConvertBuildInfo.converQualitia(reportfile.getCanonicalPath(), json);
			} else if (reportType.toLowerCase().contains("report") && !reportType.contains("modReport_ecatt")
					&& !reportType.contains("SAP_REPORTSconsoleSci") && !reportType.contains("SAP_REPORTSconsoleUnit")
					&& !reportType.toLowerCase().contains("general")) {
				json = ConvertBuildInfo.convertAcceleraTest(reportfile.getCanonicalPath(), json);
			} else if (reportType.toLowerCase().contains("itops")) {
				json = ConvertBuildInfo.convertItops(reportfile.getCanonicalPath(), json);
			} else if (reportType.toLowerCase().contains("selenium-testng")) {
				json = ConvertBuildInfo.converTestng(reportfile.getCanonicalPath(), json);
			} else if (reportType.toLowerCase().contains(".jmx_result") && reportType.toLowerCase().endsWith(".jtl")) {
				List<TestCaseResult> listTR = json.getTestCaseResult();
				if (listTR == null)
					listTR = new ArrayList<>();
				ConvertJMeter.convert(reportfile.getCanonicalPath(), json, listTR);
				json.setTestCaseResult(listTR);
			} else if (reportType.toLowerCase().contains("fastest")) {
				ConvertIFast.convert(reportfile.getCanonicalPath(), json, args[1], prefixForId, tfsPath);
			} else if (reportType.toLowerCase().contains("output")) {
				// For Robot
				json = ConvertBuildInfo.converRobot(reportfile.getCanonicalPath(), json);
			} else if (reportType.contains("modReport_ecatt")) {
				Functional fn = json.getFunctionalTest();
				List<TestCaseResult> tc = json.getTestCaseResult();
				if (tc == null)
					tc = new ArrayList<>();
				if (fn == null)
					fn = new Functional();
				ConvertEcatt.convert(reportfile.getCanonicalPath(), fn, tc);
				json.setFunctionalTest(fn);
				json.setTestCaseResult(tc);
			} else if (reportType.contains("SAP_REPORTSconsoleSci")) {
				List<CodeAnalysis> ca = new ArrayList<>();
				ConvertSci.convert(reportfile.getCanonicalPath(), ca, codeQuality);
				json.setCodeAnalysis(ca);
			} else if (reportType.contains("SAP_REPORTSconsoleUnit")) {
				List<TestCaseResult> tc = json.getTestCaseResult();
				if (tc == null)
					tc = new ArrayList<>();
				ConvertSAPUnit.convert(reportfile.getCanonicalPath(), tc);
				json.setTestCaseResult(tc);
			}
		} else if (reportType.toLowerCase().contains("pqm")) {
			List<CodeMetric> codeMetrics;
			codeMetrics = ConvertPQM.convert(reportfile.getCanonicalPath(), args[0], json, prefixForId);
			json.setCodeMetric(codeMetrics);
		}
		return json;
	}

	private static String preparePrefixForId(String idPrefixFlag, String appName) {
		String prefixForId = "";
		if (idPrefixFlag.equalsIgnoreCase("Y")) {
			prefixForId = appName + "_";
		}
		return prefixForId;
	}

	private static int findPQMFileIndex(File[] listOfFiles, List<File> list2, String args2) {
		int index = -1;
		for (int i = 0; listOfFiles != null && i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() && listOfFiles[i].getName().toLowerCase().contains(args2.toLowerCase())) {
				// pqm - Product Quality Management
				// We are seperating out
				if (listOfFiles[i].getName().toLowerCase().contains("pqm")) {
					index = i;
				} else {
					list2.add(listOfFiles[i]);
				}
			}
		}
		return index;
	}

	private static String generateOpFilename(String[] args, JsonClass json, String timeStamp) {
		String output;
		if (json.getbuildId() != null) {
			output = args[0].replaceAll("\\\\", "/") + "/" + args[1] + "_" + args[5] + "_" + json.getbuildId() + "_"
					+ timeStamp + JSON;
		} else {
			output = args[0].replaceAll("\\\\", "/") + "/" + args[1] + "_" + args[5] + "_" + timeStamp + JSON;
		}
		return output;
	}

	public static void convertChangeLog(String[] args) throws IOException {
		String path = args[0];
		int lastIndex = path.lastIndexOf('/') > path.lastIndexOf("\\\\") ? path.lastIndexOf('/')
				: path.lastIndexOf("\\\\");
		String newPath = path.substring(0, lastIndex) + "/ChangeLogs";
		File folder1 = new File(path);
		if (!folder1.isDirectory()) {
			return;
		}
		String p;
		File folder2 = new File(newPath);
		if (!folder2.exists()) {
			folder2.mkdir();
		}
		p = getPath(folder2, args[1]);
		File[] list2 = folder1.listFiles();
		JsonClass json = new JsonClass();
		json.setApplication(args[1]);
		json = cbi(list2, json, args);
		if (p == null) {
			FileWriter fw = null;
			try {
				String output = newPath + "/" + args[1] + "_output_changeSet" + ".csv";
				fw = new FileWriter(output);
				CSVWriterUtility.writeLine(fw,
						Arrays.asList("S.No.", "ID", "LastModified", "CommitMessage", "FileVersion"), ',', '"');
				int i = 0;
				for (VersionInfo v : json.getVersionInfo()) {
					i++;
					CSVWriterUtility.writeLine(fw, Arrays.asList(String.valueOf(i), v.getid(), v.getLastModified(),
							v.getCommitMessage(), v.getLatestFileVersion()), ',', '"');
				}
				fw.flush();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				if (fw != null) {
					fw.close();
				}
			}
		} else {
			FileReader fr = null;
			FileWriter fw = null;
			try {
				fr = new FileReader(p);
				CSVReader reader = new CSVReader(fr, ',');
				List<String[]> csvBody = reader.readAll();
				// get CSV row column and replace with by using row and column
				Integer i = csvBody.size();
				if (csvBody.get(i - 1)[0].matches("[0-9]+")) {
					i = Integer.parseInt(csvBody.get(i - 1)[0]);
				} else {
					i = i - 1;
				}
				reader.close();
				File nf = new File(p);
				fw = new FileWriter(nf, true);
				for (VersionInfo v : json.getVersionInfo()) {
					i++;
					CSVWriterUtility.writeLine(fw, Arrays.asList(String.valueOf(i), v.getid(), v.getLastModified(),
							v.getCommitMessage(), v.getLatestFileVersion()), ',', '"');
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				if (fr != null && fw != null) {
					fr.close();
					fw.close();
				}
			}
		}
	}

	private static JsonClass cbi(File[] list2, JsonClass inputjson, String[] args) throws IOException {
		JsonClass json=inputjson;
		String tfsPath = null;
		if (args.length > 11) {
			tfsPath = args[11];
		}
		for (File element : list2) {
			if (element.isFile() && element.getName().toLowerCase().contains(args[1].toLowerCase())
					&& element.getName().toLowerCase().contains("changelog") && element.getName().endsWith("xml")) {
				logger.info("Converting File " + element.getName() + " for app " + args[1]);
				json = ConvertBuildInfo.convert(element.getCanonicalPath(), json, preparePrefixForId(args[5], args[1]),
						tfsPath);
				break;
			}
		}
		return json;
	}

	private static String getPath(File folder2, String args2) throws IOException {
		File[] list1;
		String p = null;
		if (folder2.exists() && folder2.isDirectory() && folder2.listFiles().length > 0) {
			list1 = folder2.listFiles();
			for (File element : list1) {
				if (element.isFile() && element.getName().toLowerCase().contains(args2.toLowerCase())
						&& element.getName().toLowerCase().contains("changeset") && element.getName().endsWith("csv")) {
					p = element.getCanonicalPath();
				}
			}
		}
		return p;
	}

	public static JsonClass addToRuleSet(JsonClass jcObj) {
		if (jcObj.getCodeAnalysis().isEmpty()) {
			return jcObj;
		}
		Set<String> tempMap = new HashSet<>();
		List<RuleSet> rs = new ArrayList<>();
		for (CodeAnalysis ca : jcObj.getCodeAnalysis()) {
			if (!tempMap.contains(ca.getruleName())) {
				tempMap.add(ca.getruleName());
				rs.add(getRuleSetObject(ca.getruleName().replaceAll(":|-", "_"), ca.getSeverity(), ca.getMessage(),
						ca.getLine(), ca.getruleName(), ca.getcategory()));
			}
		}
		jcObj.setRuleSet(rs);
		return jcObj;
	}

	private static RuleSet getRuleSetObject(String replaceAll, String severity, String message, String line,
			String getruleName, String getcategory) {
		return new RuleSet(replaceAll, severity, message, line, getruleName, getcategory);
	}
}
