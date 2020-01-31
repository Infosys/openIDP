/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.businessapi;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.StringWriter;

import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.infy.idp.utils.OrchestratorConnector;
import org.infy.entities.triggerinputs.TriggerInputs;
import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.dataapi.services.DeleteInfo;
import org.infy.idp.dataapi.services.JobAdditionalDetailsDL;
import org.infy.idp.dataapi.services.JobDetailsDL;
import org.infy.idp.dataapi.services.JobDetailsInsertionService;
import org.infy.idp.dataapi.services.JobInfoDL;
import org.infy.idp.dataapi.services.JobManagementDL;
import org.infy.idp.entities.jobs.AppNames;
import org.infy.idp.entities.jobs.History;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.JobBuilds;
import org.infy.idp.entities.jobs.JobsBuilds;
import org.infy.idp.entities.jobs.Names;
import org.infy.idp.entities.jobs.Pipeline;
import org.infy.idp.entities.jobs.PipelineDetail;
import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.EnvironmentOwnerDetail;
import org.infy.idp.entities.jobs.applicationinfo.SlavesDetail;
import org.infy.idp.entities.jobs.code.JobParam;
import org.infy.idp.entities.response.ProductKey;
import org.infy.idp.utils.ConfigurationManager;
import org.infy.idp.utils.JenkinsCLI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class JobsManagementBL {
	protected Logger logger = LoggerFactory.getLogger(JobsManagementBL.class);
	private static final String AUTHORIZATION = "Authorization";
	private static final String SUCCESS = "SUCCESS";
	private static final String BASIC = "Basic ";
	private static final String CREATE_APPLICATION = "CREATE_APPLICATION";
	private static final String PIPELINE_ADMIN = "PIPELINE_ADMIN";
	private static final String DEVELOPER = "DEVELOPER";
	private static final String RELEASE_MANAGER = "RELEASE_MANAGER";
	private static final String ENVIRONMENT_OWNER = "ENVIRONMENT_OWNER";
	private static final String PIPELINE_READER = "PIPELINE_READER";
	private static final String DB_OWNER = "DB_OWNER";
	private static final String QA = "QA";
	private static final String APPLICATION_NAME = "Application Names : ";
	private static final String JOB_URL = "/job/";
	private static final String APPR_STEP = "/input/IDPApproval/";
	@Autowired
	private JobsAdditionalInfo jobsaddInfo;
	@Autowired
	private TriggerAdditionalBL triggerAddBl;
	
	@Autowired
	private ConfigurationManager configmanager;
	@Autowired
	private DeleteInfo delinfo;
	@Autowired
	private TriggerDetailBL getTriggerDetails;
	@Autowired
	private JobDetailsDL jobDetailsDL;
	@Autowired
	private JobManagementDL jobManagementDL;
	@Autowired
	private JobAdditionalDetailsDL jobAddDetailDL;

	@Autowired
	private JobInfoDL jobInfoDL;
	@Autowired
	private JobDetailsInsertionService jobDetailsInsertion;
	@Autowired
	private JenkinsCLI cli;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private EnvironmentBL environmentBL;
	@Autowired
	private OrchestratorConnector orchConn;
	
	private JobsManagementBL() {
	}

	public String beanToString(Object object) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		StringWriter stringEmp = null;
		String stringEmpStr = null;
		try {
			stringEmp = new StringWriter();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.writeValue(stringEmp, object);
			stringEmpStr = stringEmp.toString();
		} finally {
			if (stringEmp != null)
				stringEmp.close();
		}
		return stringEmpStr;
	}

	public Set<String> getReleaseNumber(String appname, String piplineName, List<String> pipelineName) {
		HashMap<String, Set<String>> releaseNum = new HashMap<String, Set<String>>();
		try {
			jobAddDetailDL.getPipelineInfo(appname, piplineName);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		for (String name : pipelineName) {
			Set<String> rlnum = new HashSet<String>();
			List<String> buildnumber = jobManagementDL.getbuildnum(appname, name);
			for (String build : buildnumber) {
				List<String> status = jobManagementDL.getStatus(appname, name, build);
				boolean st = true;
				for (String buildstatus : status) {
					if (!buildstatus.equalsIgnoreCase(SUCCESS)) {
						st = false;
						break;
					}
				}
				if (st) {
					String releasenum = jobManagementDL.getreleaseNum(appname, name, build);
					rlnum.add(releasenum);
				}
			}
			releaseNum.put(name, rlnum);
		}
		List<Set<String>> liststr = new ArrayList<>();
		Iterator it = releaseNum.entrySet().iterator();
		while (it.hasNext()) {
			HashMap.Entry pair = (HashMap.Entry) it.next();
			liststr.add((Set<String>) pair.getValue());
			it.remove(); // avoids a ConcurrentModificationException
		}
		if (liststr.size() == 0) {
			Set<String> common = new HashSet<String>();
			return common;
		}
		if (liststr.size() == 1) {
			return liststr.get(0);
		} else {
			Set<String> common = new HashSet<String>(liststr.get(0));
			int liststrSize = liststr.size();
			for (int i = 1; i < liststrSize; i++) {
				common.retainAll(liststr.get(i));
			}
			return common;
		}
	}

	public History checkAvailableJobsToTrigger(String userName, String platformName) {
		History history = new History();
		List<String> permissions = getPermission(userName);
		if (permissions.isEmpty())
			return history;
		Gson gson = new Gson();
		logger.debug("check available jobs to trigger");
		List<PipelineDetail> pipelineDetails = jobAddDetailDL.getApplicationDetails(userName, platformName);
		List<PipelineDetail> customPermissionPipelineDetails = new ArrayList<>();
		if (platformName.equalsIgnoreCase("IDP")) {
			for(PipelineDetail p1:  jobManagementDL.getPipelinesCustomPipelineadmin(userName)) {
				boolean flag = true;
				for(PipelineDetail p2 : pipelineDetails ) {
					if (p2.getPipelineName().equals(p1.getPipelineName())) {
						flag = false;
						break;
					}
				}
				if(flag) {
					customPermissionPipelineDetails.add(p1);
				}
			}
		}
		pipelineDetails.addAll(customPermissionPipelineDetails);
		history.setPipelineDetails(pipelineDetails);
		history.setUserName(userName);
		logger.debug("History : " + gson.toJson(history, History.class));
		return history;
	}

	public int deleteRole(ApplicationInfo appinfo) {
		logger.info("Inside delete");
		return delinfo.deleteRoles(jobInfoDL.getApplicationId(appinfo.getApplicationName()));
	}

	public String createApplication(ApplicationInfo appInfo, String user, String orgName) {
		// insert application and their related details in database
		logger.debug("Insert application Details");
		List<String> permission = getAllPermission(user);
		if (!permission.contains(CREATE_APPLICATION)) {
			return "No Access";
		}
		insertApplicationDetails(appInfo, user, orgName);
		logger.info("Application Created");
		// creating list of slaves in Jenkins
		createSlaves(appInfo);
		if (appInfo.getArtifactToStage().getArtifactRepoName().equalsIgnoreCase("jfrog")) {
			jobsaddInfo.addArtifctoryRepository(appInfo);
		}
		logger.info("Slaves Created");
		return SUCCESS;
	}

	public void updateInfo(ApplicationInfo appInfo, String user, String orgName) {
		logger.debug("Update application Details");
		updateApp(appInfo, user, orgName);
		logger.info("Application Updated");
		// creating list of slaves in Jenkins
		createSlaves(appInfo);
		logger.info("Slaves Created");
	}

	public void updateApp(ApplicationInfo appInfo, String user, String orgName) {
		int i;
		int status;
		String allUsers = "";
		try {
			Long orgId = jobDetailsDL.getOrgId(user);
			List<SlavesDetail> slavesList = appInfo.getSlavesDetails();
			int slavesSize = slavesList.size();
			for (i = 0; i < slavesSize; i++) {
				String slaveLabels = slavesList.get(i).getLabels().trim().replaceAll(",", " ");
				logger.debug("slave Labels for slave(" + slavesList.get(i).getSlaveName() + "): " + slaveLabels);
				slavesList.get(i).setLabels(slaveLabels);
			}
			int id = jobDetailsInsertion.updateapplicationDetail(appInfo);
			environmentBL.insertEnvironmentDetails(appInfo);
			System.out.println("updates the application with id: " + id);
			Gson gsonObj = new Gson();
			String appString = gsonObj.toJson(appInfo, ApplicationInfo.class);
			kafkaTemplate.send("idpAppDetails", id + ";" + appString);
			String[] pipelineAdmin;
			String[] developers;
			String[] releaseManagers;
			String[] envOwners;
			String[] dbOwners;
			String[] qas;
			List<String> users = new ArrayList<>();
			String temp;
			String appName = appInfo.getApplicationName();
			String sPipelineAdminReaderPattern = "(" + appName + "[^/]*)|(" + appName + ".*/" + appName + ".*)|("
					+ appName + ".*/" + appName + ".*/.*)";
			StringBuilder usersList = new StringBuilder();
			if (appInfo.getPipelineAdmins() != null) {
				pipelineAdmin = appInfo.getPipelineAdmins().toLowerCase().split(",");
				// inserting pipeline Admin details
				if (pipelineAdmin.length > 0) {
					allUsers = allUsers + appInfo.getPipelineAdmins().toLowerCase().trim() + ",";
				}
				List<String> pipelineIDList = new ArrayList<>();
				for (i = 0; i < pipelineAdmin.length; i++) {
					temp = pipelineAdmin[i].split("@")[0];
					temp = temp.replaceAll("/s", "");
					pipelineIDList.add(temp);
					if (!users.contains(temp)) {
						if (!jobDetailsDL.userExists(temp)) {
							jobDetailsInsertion.insertUsers(temp, pipelineAdmin[i], true, orgId);
						}
						users.add(temp);
						usersList.append(temp);
						usersList.append(",");
					}
				}
				jobDetailsInsertion.insertMultipleApplicationRoles(pipelineIDList, PIPELINE_ADMIN,
						appInfo.getApplicationName());
			}
			if (appInfo.getDevelopers() != null) {
				developers = appInfo.getDevelopers().toLowerCase().split(",");
				// inserting developers Details
				if (developers.length > 0) {
					allUsers = allUsers + appInfo.getDevelopers().toLowerCase().trim() + ",";
				}
				List<String> developersIDList = new ArrayList<>();
				for (i = 0; i < developers.length; i++) {
					temp = developers[i].split("@")[0];
					temp = temp.replaceAll("/s", "");
					developersIDList.add(temp);
					if (!users.contains(temp)) {
						if (!jobDetailsDL.userExists(temp)) {
							jobDetailsInsertion.insertUsers(temp, developers[i], true, orgId);
						}
						users.add(temp);
						usersList.append(temp);
						usersList.append(",");
					}
				}
				jobDetailsInsertion.insertMultipleApplicationRoles(developersIDList, DEVELOPER,
						appInfo.getApplicationName());
			}
			if (appInfo.getReleaseManager() != null) {
				releaseManagers = appInfo.getReleaseManager().toLowerCase().split(",");
				// inserting release manager Details
				if (releaseManagers.length > 0) {
					allUsers = allUsers + appInfo.getReleaseManager().toLowerCase().trim() + ",";
				}
				List<String> releaseManagerList = new ArrayList<>();
				for (i = 0; i < releaseManagers.length; i++) {
					temp = releaseManagers[i].split("@")[0];
					temp = temp.replaceAll("/s", "");
					releaseManagerList.add(temp);
					if (!users.contains(temp)) {
						if (!jobDetailsDL.userExists(temp)) {
							jobDetailsInsertion.insertUsers(temp, releaseManagers[i], true, orgId);
						}
						users.add(temp);
						usersList.append(temp);
						usersList.append(",");
					}
				}
				jobDetailsInsertion.insertMultipleApplicationRoles(releaseManagerList, RELEASE_MANAGER,
						appInfo.getApplicationName());
			}
			if (appInfo.getEnvironmentOwnerDetails() != null) {
				List<EnvironmentOwnerDetail> envOwnerDetails = appInfo.getEnvironmentOwnerDetails();
				int envOwnerDetailsSize = envOwnerDetails.size();
				for (i = 0; i < envOwnerDetailsSize; i++) {
					envOwners = envOwnerDetails.get(i).getEnvironmentOwners().toLowerCase().split(",");
					if (envOwners.length > 0) {
						allUsers = allUsers + envOwnerDetails.get(i).getEnvironmentOwners().trim() + ",";
					}
					for (int j = 0; j < envOwners.length; j++) {
						temp = envOwners[j].split("@")[0];
						temp = temp.replaceAll("/s", "");
						if (!users.contains(temp)) {
							if (!jobDetailsDL.userExists(temp)) {
								jobDetailsInsertion.insertUsers(temp, envOwners[j], true, orgId);
							}
							users.add(temp);
							usersList.append(temp);
							usersList.append(",");
						}
						jobDetailsInsertion.insertApplicationRoles(temp, ENVIRONMENT_OWNER,
								appInfo.getApplicationName());
					}
					if (null != envOwnerDetails.get(i).getdBOwners()
							&& !"".equals(envOwnerDetails.get(i).getdBOwners())) {
						dbOwners = envOwnerDetails.get(i).getdBOwners().toLowerCase().split(",");
						for (int j = 0; j < dbOwners.length; j++) {
							temp = dbOwners[j].split("@")[0];
							temp = temp.replaceAll("/s", "");
							if (!users.contains(temp)) {
								if (!jobDetailsDL.userExists(temp)) {
									jobDetailsInsertion.insertUsers(temp, dbOwners[j], true, orgId);
								}
								users.add(temp);
								usersList.append(temp);
								usersList.append(",");
							}
							jobDetailsInsertion.insertApplicationRoles(temp, DB_OWNER, appInfo.getApplicationName());
						}
					}
					if (null != envOwnerDetails.get(i).getQa() && !"".equals(envOwnerDetails.get(i).getQa())) {
						qas = envOwnerDetails.get(i).getQa().toLowerCase().split(",");
						allUsers = allUsers + envOwnerDetails.get(i).getQa().trim() + ",";
						for (int j = 0; j < qas.length; j++) {
							temp = qas[j].split("@")[0];
							temp = temp.replaceAll("/s", "");
							if (!users.contains(temp)) {
								if (!jobDetailsDL.userExists(temp)) {
									jobDetailsInsertion.insertUsers(temp, qas[j], true, orgId);
								}
								users.add(temp);
								usersList.append(temp);
								usersList.append(",");
							}
							jobDetailsInsertion.insertApplicationRoles(temp, QA, appInfo.getApplicationName());
						}
					}
				}
			}
			allUsers += ";" + orgName;
			kafkaTemplate.send("idpoauth", allUsers);
			try {
				orchConn.createRole("IDP_User", usersList.toString(), appName + ".*_Slave", "GLOBAL", PIPELINE_READER,
						configmanager.getJenkinsurl());
				status = orchConn.createRole(appName + "_Reader", usersList.toString(), sPipelineAdminReaderPattern,
						"PROJECT", PIPELINE_READER, configmanager.getJenkinsurl());
				if (status == 0)
					logger.info("Pipeline Reader role added successfully.");
			} catch (IOException e) {
				logger.error("IO Exception for role creation");
				logger.debug(e.getMessage());
			}
			List<SlavesDetail> slaves = appInfo.getSlavesDetails();
			slavesSize = slaves.size();
			for (i = 0; i < slavesSize; i++) {
				jobDetailsInsertion.insertSlaveDetails(slaves.get(i), appInfo.getApplicationName());
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}
	}

	public void insertApplicationDetails(ApplicationInfo appInfo, String user, String orgName) {
		int i;
		int status;
		String allUsers = "";
		try {
			Long orgId = jobDetailsDL.getOrgId(user);
			List<SlavesDetail> slavesList = appInfo.getSlavesDetails();
			int slavesListSize = slavesList.size();
			for (i = 0; i < slavesListSize; i++) {
				String slaveLabels = slavesList.get(i).getLabels().trim().replaceAll(",", " ");
				logger.debug("slave Labels for slave(" + slavesList.get(i).getSlaveName() + "): " + slaveLabels);
				slavesList.get(i).setLabels(slaveLabels);
			}
			int id = jobDetailsInsertion.insertApplicationDetails(appInfo);
			environmentBL.insertEnvironmentDetails(appInfo);
			Gson gsonObj = new Gson();
			String appString = gsonObj.toJson(appInfo, ApplicationInfo.class);
			kafkaTemplate.send("idpAppDetails", id + ";" + appString);
			String[] pipelineAdmin;
			String[] developers;
			String[] releaseManagers;
			String[] envOwners;
			String[] dbOwners;
			String[] qas;
			List<String> users = new ArrayList<>();
			String temp;
			String appName = appInfo.getApplicationName();
			String sPipelineAdminReaderPattern = "(" + appName + "[^/]*)|(" + appName + ".*/" + appName + ".*)|("
					+ appName + ".*/" + appName + ".*/.*)";
			StringBuilder usersList = new StringBuilder();
			if (appInfo.getPipelineAdmins() != null && !appInfo.getPipelineAdmins().isEmpty()) {
				pipelineAdmin = appInfo.getPipelineAdmins().toLowerCase().split(",");
				if (pipelineAdmin.length > 0) {
					allUsers = allUsers + appInfo.getPipelineAdmins().toLowerCase().trim() + ",";
				}
				// inserting pipeline Admin details
				List<String> pipelineIDList = new ArrayList<>();
				for (i = 0; i < pipelineAdmin.length; i++) {
					temp = pipelineAdmin[i].split("@")[0];
					temp = temp.replaceAll("/s", "");
					pipelineIDList.add(temp);
					if (!users.contains(temp)) {
						if (!jobDetailsDL.userExists(temp)) {
							jobDetailsInsertion.insertUsers(temp, pipelineAdmin[i], true, orgId);
						}
						users.add(temp);
						usersList.append(temp);
						usersList.append(",");
					}
				}
				jobDetailsInsertion.insertMultipleApplicationRoles(pipelineIDList, PIPELINE_ADMIN,
						appInfo.getApplicationName());
			}
			if (appInfo.getDevelopers() != null && !appInfo.getDevelopers().isEmpty()) {
				developers = appInfo.getDevelopers().toLowerCase().split(",");
				if (developers.length > 0) {
					allUsers = allUsers + appInfo.getDevelopers().toLowerCase().trim() + ",";
				}
				// inserting developers Details
				List<String> developersIDList = new ArrayList<>();
				for (i = 0; i < developers.length; i++) {
					temp = developers[i].split("@")[0];
					temp = temp.replaceAll("/s", "");
					developersIDList.add(temp);
					if (!users.contains(temp)) {
						if (!jobDetailsDL.userExists(temp)) {
							jobDetailsInsertion.insertUsers(temp, developers[i], true, orgId);
						}
						users.add(temp);
						usersList.append(temp);
						usersList.append(",");
					}
				}
				jobDetailsInsertion.insertMultipleApplicationRoles(developersIDList, DEVELOPER,
						appInfo.getApplicationName());
			}
			if (appInfo.getReleaseManager() != null && !appInfo.getReleaseManager().isEmpty()) {
				releaseManagers = appInfo.getReleaseManager().toLowerCase().split(",");
				if (releaseManagers.length > 0) {
					allUsers = allUsers + appInfo.getReleaseManager().toLowerCase().trim() + ",";
				}
				// inserting release manager Details
				List<String> releaseManagerList = new ArrayList<>();
				for (i = 0; i < releaseManagers.length; i++) {
					temp = releaseManagers[i].split("@")[0];
					temp = temp.replaceAll("/s", "");
					releaseManagerList.add(temp);
					if (!users.contains(temp)) {
						if (!jobDetailsDL.userExists(temp)) {
							jobDetailsInsertion.insertUsers(temp, releaseManagers[i], true, orgId);
						}
						users.add(temp);
						usersList.append(temp);
						usersList.append(",");
					}
				}
				jobDetailsInsertion.insertMultipleApplicationRoles(releaseManagerList, RELEASE_MANAGER,
						appInfo.getApplicationName());
			}
			if (appInfo.getEnvironmentOwnerDetails() != null && !appInfo.getEnvironmentOwnerDetails().isEmpty()) {
				List<EnvironmentOwnerDetail> envOwnerDetails = appInfo.getEnvironmentOwnerDetails();
				int envOwnerDetailsSize = envOwnerDetails.size();
				for (i = 0; i < envOwnerDetailsSize; i++) {
					envOwners = envOwnerDetails.get(i).getEnvironmentOwners().toLowerCase().split(",");
					if (envOwners.length > 0) {
						allUsers = allUsers + envOwnerDetails.get(i).getEnvironmentOwners().trim() + ",";
					}
					for (int j = 0; j < envOwners.length; j++) {
						temp = envOwners[j].split("@")[0];
						temp = temp.replaceAll("/s", "");
						if (!users.contains(temp)) {
							if (!jobDetailsDL.userExists(temp)) {
								jobDetailsInsertion.insertUsers(temp, envOwners[j], true, orgId);
							}
							users.add(temp);
							usersList.append(temp);
							usersList.append(",");
						}
						jobDetailsInsertion.insertApplicationRoles(temp, ENVIRONMENT_OWNER,
								appInfo.getApplicationName());
					}
					logger.info("Inside Environment Owners before DBOwners" + envOwnerDetails.get(i).getdBOwners());
					if (null != envOwnerDetails.get(i).getdBOwners()
							&& !"".equals(envOwnerDetails.get(i).getdBOwners())) {
						logger.info("Inside Environment Owners inside DBOwners" + envOwnerDetails.get(i).getdBOwners());
						dbOwners = envOwnerDetails.get(i).getdBOwners().toLowerCase().split(",");
						allUsers = allUsers + envOwnerDetails.get(i).getdBOwners().trim() + ",";
						for (int j = 0; j < dbOwners.length; j++) {
							temp = dbOwners[j].split("@")[0];
							temp = temp.replaceAll("/s", "");
							if (!users.contains(temp)) {
								if (!jobDetailsDL.userExists(temp)) {
									jobDetailsInsertion.insertUsers(temp, dbOwners[j], true, orgId);
								}
								users.add(temp);
								usersList.append(temp);
								usersList.append(",");
							}
							jobDetailsInsertion.insertApplicationRoles(temp, DB_OWNER, appInfo.getApplicationName());
						}
					}
					logger.info("Inside Environment Owners after DBOwners");
					if (null != envOwnerDetails.get(i).getQa() && !"".equals(envOwnerDetails.get(i).getQa())) {
						qas = envOwnerDetails.get(i).getQa().toLowerCase().split(",");
						allUsers = allUsers + envOwnerDetails.get(i).getQa().trim() + ",";
						for (int j = 0; j < qas.length; j++) {
							temp = qas[j].split("@")[0];
							temp = temp.replaceAll("/s", "");
							if (!users.contains(temp)) {
								if (!jobDetailsDL.userExists(temp)) {
									jobDetailsInsertion.insertUsers(temp, qas[j], true, orgId);
								}
								users.add(temp);
								usersList.append(temp);
								usersList.append(",");
							}
							jobDetailsInsertion.insertApplicationRoles(temp, QA, appInfo.getApplicationName());
						}
					}
				}
			}
			allUsers += ";" + orgName;
			kafkaTemplate.send("idpoauth", allUsers);
			kafkaTemplate.send("idpdashboard_users", allUsers);
			kafkaTemplate.send("idpdashboard_app", appInfo.getApplicationName() + ";" + orgName);
			try {
				orchConn.createRole("IDP_User", usersList.toString(), appName + ".*_Slave", "GLOBAL", PIPELINE_READER,
						configmanager.getJenkinsurl());
				status = orchConn.createRole(appName + "_Reader", usersList.toString(), sPipelineAdminReaderPattern,
						"PROJECT", PIPELINE_READER, configmanager.getJenkinsurl());
				if (status == 0)
					logger.info("Pipeline Reader role added successfully.");
				else
					logger.error("Could not create roles");
			} catch (IOException e) {
				logger.error("IO Exception for role creation");
				logger.debug(e.getMessage());
			}
			List<SlavesDetail> slaves = appInfo.getSlavesDetails();
			int slavesSize = slaves.size();
			for (i = 0; i < slavesSize; i++) {
				jobDetailsInsertion.insertSlaveDetails(slaves.get(i), appInfo.getApplicationName());
			}
		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}
	}

	public TriggerInputs fecthTriggerOptions(TriggerJobName triggerJobName) throws SQLException {
		TriggerInputs triggerInputs = null;
		logger.debug("Fetch trigger options");
		try {
			triggerInputs = getTriggerDetails.fecthTriggerOptions(triggerJobName);
		} catch (SQLException e) {
			logger.error("Error in fetcing trigger options");
		}
		return triggerInputs;
	}

	public Names getPairName(TriggerJobName triggerJobName) {
		logger.info("inside jobsBL");
		Gson gson = new Gson();
		Names names = new Names();
		try {
			Pipeline pipeline = getPipelineDetails(triggerJobName);
			logger.info("pipeline : " + gson.toJson(pipeline));
			List<String> nameList = triggerAddBl.getPairNames(pipeline.getPipelineJson(),
					triggerJobName.getEnvName());
			names.setNames(nameList);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return names;
	}

	public AppNames getApplications(String userId, String platform) throws SQLException {
		AppNames appNames = new AppNames();
		List<String> permissions = getPermission(userId);
		if (null == permissions || permissions.isEmpty()) {
			return appNames;
		}
		Gson gson = new Gson();
		List<String> apps = jobAddDetailDL.getApplications(userId, platform);
		logger.debug("getting application");
		appNames.setApplicationNames(apps);
		logger.debug(APPLICATION_NAME + gson.toJson(appNames, AppNames.class));
		return appNames;
	}

	public AppNames getFilteredApplications(String filterString, String userId, String platform) throws SQLException {
		AppNames appNames = new AppNames();
		List<String> permissions = getPermission(userId);
		if (null == permissions || permissions.isEmpty()) {
			return appNames;
		}
		Gson gson = new Gson();
		List<String> nonFilteredApps = jobAddDetailDL.getApplications(userId, platform);
		List<String> filteredApps = new ArrayList<>();
		if (filterString != null) {
			for (String app : nonFilteredApps) {
				filteredApps.add(app);
			}
		} else {
			filteredApps = nonFilteredApps;
		}
		logger.debug("getting application filtered: " + filteredApps);
		appNames.setApplicationNames(filteredApps);
		logger.debug(APPLICATION_NAME + gson.toJson(appNames, AppNames.class));
		return appNames;
	}
	public IDPJob setProductKey(IDPJob idp) {
		if (null != idp.getBuildInfo() && null != idp.getBuildInfo().getModules()
				&& idp.getBuildInfo().getModules().size() > 0
				&& null != idp.getBuildInfo().getModules().get(0).getArchName()) {

			String urlToHit = "http://" + idp.getBuildInfo().getModules().get(0).getAppServ() + ":"
					+ idp.getBuildInfo().getModules().get(0).getAppPort()
					+ "/prweb/PRRestService/DevopsIntegration/Int-DevopsIntegration-REST-Product/DevopsIntegration/"
					+ idp.getBuildInfo().getModules().get(0).getProPar();

			String authString = idp.getBuildInfo().getModules().get(0).getSerUname() + ":"
					+ idp.getBuildInfo().getModules().get(0).getServPass();

			String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
			URL url;
			BufferedReader br = null;
			try {
				url = new URL(urlToHit);

				URLConnection urlconnection = url.openConnection();
				urlconnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

				br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));

				String response = "";
				String line = br.readLine();

				while (line != null) {
					response += line + "\n";
					line = br.readLine();

				}

				Gson g = new Gson();
				ProductKey pk = g.fromJson(response, ProductKey.class);
				idp.getBuildInfo().getModules().get(0).setPegaProductKey(pk.getProdutRuleKey());
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		return idp;

	}


	public List<String> getRolesApp(String uname, String appName) {
		return jobManagementDL.getRolesasApp(uname, appName);
	}

	public String getReleaseNo(String pipelineName, String appName) {
		return jobManagementDL.getReleaseNo(pipelineName, appName);
	}

	public Application getApplicationDetails(String appName, String userName) throws SQLException {
		List<String> permissions = getAllPermission(userName);
		Application app = null;
		if (null == permissions || permissions.isEmpty()) {
			app = new Application();
			return app;
		}
		Gson gson = new Gson();
		app = jobAddDetailDL.getApplicationDetail(appName);
		logger.debug(APPLICATION_NAME + gson.toJson(app, Application.class));
		return app;
	}

	public List<String> getRoles(String userId) {
		logger.debug("Getting roles");
		List<String> userRoles = jobManagementDL.getRoles(userId);
		logger.debug("roles : " + userRoles);
		return userRoles;
	}

	public List<String> getPermission(String userId) {
		logger.debug("getting permissions");
		List<String> userPermissions = jobManagementDL.getPermission(userId);
		logger.debug("Permissions:" + userPermissions);
		return userPermissions;
	}

	public List<String> getBaseRoles(String userId) {
		logger.debug("Getting base Role");
		List<String> userBaseRoles = jobManagementDL.getBaseRoles(userId);
		logger.debug("Base Role : " + userBaseRoles);
		return userBaseRoles;
	}

	public List<String> getBasePermission(String userId) {
		logger.debug("Getting base permission");
		return jobManagementDL.getBasePermission(userId);
	}

	public List<String> getAllPermission(String userId) {
		logger.info("Getting All permission");
		List<String> permissions = jobManagementDL.getBasePermission(userId);
		permissions.addAll(jobManagementDL.getPermission(userId));
		return permissions;
	}

	public void createSlaves(ApplicationInfo appInfo) {
		int i;
		List<SlavesDetail> slaves = appInfo.getSlavesDetails();
		// iterating through all the slaves
		int slavesSize = slaves.size();
		for (i = 0; i < slavesSize; i++) {
			SlavesDetail slave = slaves.get(i);
			if ("on".equalsIgnoreCase(slave.getCreateNewSlave())) {
				try {
					// executing jenkins copy-slave plugin to create slave
					if (null == slave.getSSHKeyPath() || "".equalsIgnoreCase(slave.getSSHKeyPath())) {
						slave.setSSHKeyPath(slave.getWorkspacePath());
					}

					orchConn.copySlave(slave.getSlaveName(), slave.getWorkspacePath(), slave.getLabels(),
							slave.getSSHKeyPath(),configmanager.getJenkinsurl());
				} catch (Exception e) {
					logger.error("Error in slave creation!!", e);
				}
			}
		}
	}

	public JobsBuilds getBuildJobs(TriggerJobName jobName) {
		JobsBuilds jobsBuilds = new JobsBuilds();
		List<String> permissions = jobsaddInfo.getPermissionForApplications(jobName.getApplicationName(),
				jobName.getUserName());
		if (permissions.isEmpty()) {
			return jobsBuilds;
		}
		String job = jobName.getApplicationName() + "_" + jobName.getPipelineName();
		String jsonString = getJobJSON(job, "job");
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		JSONArray jsonArray = jsonObj.getJSONArray("jobs");
		List<JobBuilds> jobBuildsArray = new ArrayList<>();
		JobBuilds jobBuilds;
		int jsonArraySize = jsonArray.size();
		for (int i = 0; i < jsonArraySize; i++) {
			jobBuilds = new JobBuilds();
			jobBuilds.setJobName(jsonArray.getJSONObject(i).getString("name"));
			String buildObj = getJobJSON(job + JOB_URL + jsonArray.getJSONObject(i).getString("name"), "builds");
			jobBuilds.setBuildJSON(buildObj);
			jobBuildsArray.add(jobBuilds);
		}
		jobsBuilds.setJobBuilds(jobBuildsArray);
		return jobsBuilds;
	}

	public String getJobJSON(String jobName, String param) {

		return orchConn.getJobJSON(jobName, param, configmanager.getJenkinsurl());
	}

	public Pipeline getPipelineDetails(TriggerJobName triggerJobName) throws SQLException {
		Gson gson = new Gson();
		List<String> permissions = jobsaddInfo.getAllPermissionforApp(triggerJobName.getApplicationName(),
				triggerJobName.getUserName());
		// custom pipeline admin fix
		permissions.addAll(jobInfoDL.getPipelinePermission(triggerJobName.getApplicationName(),
				triggerJobName.getPipelineName(), triggerJobName.getUserName()));
		if (permissions.isEmpty()) {
			return new Pipeline();
		}
		Pipeline pipeline = jobManagementDL.getPipelineDetail(triggerJobName);
		pipeline.setMethod(triggerJobName.getMethod());
		String newPipelineName = pipeline.getPipelineName() + "_Copy";
		if ("copy".equalsIgnoreCase(triggerJobName.getMethod())) {
			pipeline.setPipelineName(newPipelineName);
			pipeline.getPipelineJson().getBasicInfo().setPipelineName(newPipelineName);
		}
		if (pipeline.getApplicationName() == null || pipeline.getApplicationName().equals("")) {
			pipeline.setApplicationName(triggerJobName.getApplicationName());
		}
		logger.debug("Pipeline Names : " + gson.toJson(pipeline, Pipeline.class));
		return pipeline;
	}

	public List<JobParam> getJobParamDetails(String appName, String pipelineName) throws SQLException {
		List<JobParam> jobParamList = new ArrayList<>();
		jobParamList = jobInfoDL.getJobParamDetails(appName, pipelineName);
		return jobParamList;
	}
}
