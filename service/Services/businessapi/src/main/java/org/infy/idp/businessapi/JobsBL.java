/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.businessapi;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.infy.entities.triggerinputs.Steps;
import org.infy.entities.triggerinputs.TriggerInputs;
import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.dataapi.services.DeleteInfo;
import org.infy.idp.dataapi.services.EnvironmentDetails;
import org.infy.idp.dataapi.services.JobDetailsDL;
import org.infy.idp.dataapi.services.JobDetailsInsertionService;
import org.infy.idp.dataapi.services.ReleaseDetails;
import org.infy.idp.dataapi.services.UpdateJobDetails;
import org.infy.idp.entities.jobs.AppNames;
import org.infy.idp.entities.jobs.DBDeployOperations;
import org.infy.idp.entities.jobs.DownloadArtifactInputs;
import org.infy.idp.entities.jobs.EnvName;
import org.infy.idp.entities.jobs.History;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.JobBuilds;
import org.infy.idp.entities.jobs.JobsBuilds;
import org.infy.idp.entities.jobs.Names;
import org.infy.idp.entities.jobs.Pipeline;
import org.infy.idp.entities.jobs.PipelineDetail;
import org.infy.idp.entities.jobs.Pipelines;
import org.infy.idp.entities.jobs.SubApplication;
import org.infy.idp.entities.jobs.TestPlans;
import org.infy.idp.entities.jobs.TestSuits;
import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.Applications;
import org.infy.idp.entities.jobs.applicationinfo.EnvironmentOwnerDetail;
import org.infy.idp.entities.jobs.applicationinfo.SlavesDetail;
import org.infy.idp.entities.jobs.code.JobParam;
import org.infy.idp.entities.jobs.common.Notification;
import org.infy.idp.entities.jobs.deployinfo.DeployEnv;
import org.infy.idp.entities.jobs.testinfo.TestEnv;
import org.infy.idp.entities.jobs.testinfo.TestStep;
import org.infy.idp.entities.models.BuildStatus;
import org.infy.idp.entities.models.GitHubBrachModel;
import org.infy.idp.entities.triggerparameter.ApproveBuildParams;
import org.infy.idp.entities.triggerparameter.TestStepInfo;
import org.infy.idp.entities.triggerparameter.TriggerParameters;
import org.infy.idp.utils.BuildService;
import org.infy.idp.utils.ConfigurationManager;
import org.infy.idp.utils.JenkinsCLI;
import org.infy.idp.utils.TestPlanDetailsFetcher;
import org.infy.idp.utils.TriggerBuilds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.offbytwo.jenkins.JenkinsServer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * The class JobBL contains methods related to jobs, applications and artifacts
 * 
 * @author Infosys
 */

@Component
public class JobsBL {
	protected Logger logger = LoggerFactory.getLogger(JobsBL.class);

	private static final String AUTHORIZATION = "Authorization";
	private static final String CREATE_PIPELINE = "CREATE_PIPELINE";
	private static final String EDIT_PIPELINE = "EDIT_PIPELINE";

	private static final String SUCCESS = "SUCCESS";
	private static final String CREATE = "CREATE";
	private static final String COPY = "COPY";
	private static final String BASIC = "Basic ";
	private static final String TRUE = "true";
	private static final String FALSE = "FALSE";
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
	private FetchJobDetails fetchJobDetails;
	@Autowired
	private ConfigurationManager configmanager;
	@Autowired
	private DeleteInfo delinfo;
	@Autowired
	private TriggerDetailBL getTriggerDetails;
	@Autowired
	private JobDetailsDL jobDetailsDL;
	@Autowired
	private JobDetailsInsertionService jobDetailsInsertion;
	@Autowired
	private UpdateJobDetails updateJobDetails;
	@Autowired
	private BuildService buildService;
	@Autowired
	private TriggerBuilds triggerBuilds;
	@Autowired
	private JenkinsCLI cli;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private EnvironmentDetails environmentDetails;
	@Autowired
	private EnvironmentBL environmentBL;
	@Autowired
	private ReleaseDetails releaseDetails;
	@Autowired
	private TestPlanDetailsFetcher testPlanDetailsFetcher;
	@Autowired
	private EmailSender emailSender;

	private JobsBL() {
		// constructor
	}

	/**
	 * Process job and create
	 *
	 * @param idpjson the idpjson
	 */
	public void processJobs(IDPJob idpjson) {
		buildService.createNewJob(idpjson);

	}

	/**
	 * submits the specified Job
	 * 
	 * @param idp the IDPJob
	 * 
	 */

	public String submitJob(IDPJob idp, String user) {
		Gson gson = new Gson();
		logger.debug("submit job start");

		List<String> permissions = getPermissionForApplications(idp.getBasicInfo().getApplicationName(), user);
		Long orgId = 0L;
		try {
			orgId = jobDetailsDL.getOrgId(user);
			if (!getPipelinePermissionforApplication(idp.getBasicInfo().getApplicationName(), user).isEmpty())
				permissions.add(EDIT_PIPELINE);
		} catch (SQLException e1) {
			logger.error("Error while adding pipeline permission for application ", e1);
		}
		if (!(permissions.contains(CREATE_PIPELINE) || permissions.contains(EDIT_PIPELINE))) {
			return "User Does not have permission to create pipeline";
		}
		IDPJob a = new IDPJob();
		Gson g = new Gson();
		String newidp = g.toJson(idp);
		a = g.fromJson(newidp, IDPJob.class);
		logger.info(newidp);
		if (idp.getBuildInfo() != null && idp.getBuildInfo().getArtifactToStage() != null) {
			logger.info(idp.getBuildInfo().getArtifactToStage().getArtifactRepoName());
			if (idp.getBuildInfo().getArtifactToStage().getArtifactRepoName() == null
					|| idp.getBuildInfo().getArtifactToStage().getArtifactRepoName().equalsIgnoreCase("")) {
				try {
					ApplicationInfo ap = jobDetailsDL.getApplication(idp.getBasicInfo().getApplicationName());
					if (ap.getArtifactToStage() != null) {

						ap.getArtifactToStage().setArtifact(idp.getBuildInfo().getArtifactToStage().getArtifact());
						ap.getArtifactToStage().setFlattenFileStructure(
								idp.getBuildInfo().getArtifactToStage().getFlattenFileStructure());
						idp.getBuildInfo().setArtifactToStage(ap.getArtifactToStage());
					}
				} catch (SQLException e) {

					logger.error(e.getMessage(), e);
				}
			} else if (idp.getBuildInfo().getArtifactToStage().getArtifactRepoName().equalsIgnoreCase("jfrog")) {
				try {
					cli.addArtifactoryRepoGlobConf(
							idp.getBuildInfo().getArtifactToStage().getArtifactRepo().getRepoURL(),
							idp.getBuildInfo().getArtifactToStage().getArtifactRepo().getRepoUsername(),
							idp.getBuildInfo().getArtifactToStage().getArtifactRepo().getRepoPassword(),
							idp.getBuildInfo().getArtifactToStage().getArtifactRepo().getRepoUsername().toLowerCase()
									+ "_" + idp.getBuildInfo().getArtifactToStage().getArtifactRepo().getRepoName());
				} catch (Exception e) {
					logger.error("error while adding artifcatory repo", e.getMessage());
				}
			}
		}

		idp = fetchJobDetails.getSonarInfo(idp);
		logger.info("IDP JSON " + gson.toJson(idp, IDPJob.class).toString());
		logger.debug("IDP JSON " + gson.toJson(idp, IDPJob.class).toString());

		buildService.createNewJob(idp);

		logger.info(
				idp.getBasicInfo().getApplicationName() + "_" + idp.getBasicInfo().getPipelineName() + " Job created.");

		IDPJob idpLocal = idp;
		IDPJob aLocal = a;
		new Thread(new Runnable() {

			@Override
			public void run() {
				triggerBuilds.buildByJobName(idpLocal);
				String jenkinsURL = configmanager.getJenkinsurl();
				String userName = configmanager.getJenkinsuserid();
				String password = configmanager.getJenkinspassword();

				addALMServer(idpLocal);
				try {
					JenkinsServer server = new JenkinsServer(new URI(jenkinsURL), userName, password);
					BuildStatus buildStatus = buildService.getBuildStatus(server,
							idpLocal.getBasicInfo().getApplicationName() + "_"
									+ idpLocal.getBasicInfo().getPipelineName() + "_Main",
							0, 120000);
					logger.info("creating seed jobs.");
					if (buildStatus.getState().equalsIgnoreCase(SUCCESS)) {
						jobDetailsInsertion.insertPipelineJsonDetails(aLocal, TRUE);
					} else {
						if (idpLocal.getBasicInfo().getPipelineStatus().toString().equalsIgnoreCase(CREATE)
								|| idpLocal.getBasicInfo().getPipelineStatus().toString().equalsIgnoreCase(COPY)) {
							jobDetailsInsertion.insertPipelineJsonDetails(aLocal, FALSE);
						}
					}

					// for IDP only
					if (idpLocal.getCode() != null) {
						if (buildStatus.getState().equalsIgnoreCase(SUCCESS)) {

							long pipelineId = jobDetailsDL.getPipelineId(idpLocal.getBasicInfo().getPipelineName(),
									idpLocal.getBasicInfo().getApplicationName());
							jobDetailsInsertion.deleteAdditionalJobParamDetails(pipelineId);
							for (JobParam jobParam : aLocal.getCode().getJobParam()) {

								jobDetailsInsertion.insertAdditionalJobParamDetails(jobParam,
										idpLocal.getBasicInfo().getApplicationName(),
										idpLocal.getBasicInfo().getPipelineName());

							}
						}
					}
					if (buildStatus.getState().equalsIgnoreCase("FAILURE")) {
						jobDetailsInsertion.addNotification(idpLocal.getBasicInfo().getPipelineName(), "FAILURE", user);
					} else {
						jobDetailsInsertion.addNotification(idpLocal.getBasicInfo().getPipelineName(), "SUCCESS", user);
					}
					logger.info("Pipeline information is inserted in the database");

				} catch (URISyntaxException | SQLException e) {
					jobDetailsInsertion.addNotification(idpLocal.getBasicInfo().getPipelineName(), "FAILURE", user);
					logger.error("Erorr in submitJob: ", e);
					logger.info(e.getMessage(), e);
				} catch (Exception e) {
					jobDetailsInsertion.addNotification(idpLocal.getBasicInfo().getPipelineName(), "FAILURE", user);
					logger.error("Erorr in submitJob: ", e);
					logger.info(e.getMessage(), e);
				}

			}
		}).start();

		return "SUCCESS";
	}

	/**
	 * submits the trigger interval
	 * 
	 * @param idp the IDPJob
	 * 
	 */

	public String submitInterval(org.infy.idp.entities.jobs.basicinfo.TriggerInterval idp, String user) {
		TriggerJobName triggerJobName = new TriggerJobName();
		triggerJobName.setApplicationName(idp.getInterval().get(0).getDetails().getApplicationName());
		triggerJobName.setPipelineName(idp.getInterval().get(0).getDetails().getPipelineName());
		triggerJobName.setUserName(idp.getInterval().get(0).getDetails().getUserName());
		Pipeline pipeline = jobDetailsDL.getPipelineDetail(triggerJobName);
		idp = setEmails(idp, pipeline, user);
		IDPJob idpjson = pipeline.getPipelineJson();
		idpjson.getBasicInfo().setCustomTriggerInterval(idp);
		submitJob(idpjson, triggerJobName.getUserName());
		return "SUCCESS";
	}

	/**
	 * Sets the email recipients for triggered job
	 * 
	 * @param triggerInterval
	 * @param pipeline
	 * @param user
	 * @return
	 */

	public org.infy.idp.entities.jobs.basicinfo.TriggerInterval setEmails(
			org.infy.idp.entities.jobs.basicinfo.TriggerInterval triggerInterval, Pipeline pipeline, String user) {
		TriggerJobName triggerJobName = new TriggerJobName();
		triggerJobName.setApplicationName(triggerInterval.getInterval().get(0).getDetails().getApplicationName());
		triggerJobName.setPipelineName(triggerInterval.getInterval().get(0).getDetails().getPipelineName());
		triggerJobName.setUserName(user);
		List<String> users = emailSender.getUsersFromApplication(triggerJobName, user);

		String emailIds = String.join(",", users);
		logger.info("email Ids : " + emailIds);
		int intervalListSize = triggerInterval.getInterval().size();
		for (int i = 0; i < intervalListSize; i++) {
			triggerInterval.getInterval().get(i).getDetails().setEmailed(emailIds);
		}
		return triggerInterval;
	}

	/**
	 * Returns last build details
	 * 
	 * @param webpage
	 * @param username
	 * @param password
	 * @return String
	 */
	public String lastbuild(String webpage, String username, String password) {
		String buildId = null;
		String authString = username + ":" + password;
		InputStream is = null;
		InputStreamReader isr = null;

		String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
		try {
			URL url = new URL(webpage);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty(AUTHORIZATION, BASIC + authStringEnc);
			is = urlConnection.getInputStream();
			isr = new InputStreamReader(is);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(is);

			Element root = document.getDocumentElement();
			NodeList lastBuildList = root.getElementsByTagName("lastBuild");

			int lastBuildListLength = lastBuildList.getLength();
			for (int i = 0; i < lastBuildListLength; i++) {
				Node node = lastBuildList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					if (element.getElementsByTagName("number").item(0) != null)
						buildId = element.getElementsByTagName("number").item(0).getTextContent();
					else
						buildId = null;
				}

			}

		}

		catch (IOException | ParserConfigurationException | SAXException e) {

			logger.debug(e.getMessage());

		} finally {
			if (null != isr) {
				try {
					isr.close();
					isr = null;
				} catch (IOException e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
		}
		return buildId;
	}

	/**
	 * Return pipeline names for the specified app and release
	 * 
	 * @param applicationName
	 * @param releaseNumber
	 * @return String list
	 */
	public List<String> getPipelines(String applicationName, String releaseNumber) {
		List<String> pipelineNames = jobDetailsDL.getPipelines(applicationName, releaseNumber);
		return pipelineNames;
	}

	/**
	 * 
	 * Returns release list of the specified app
	 *
	 * @param userName the String
	 * 
	 * @return applications the Applications
	 */
	public List<String> getReleaseInfo(String appName) {

		List<String> releaseNumber = new ArrayList<>();

		try {
			logger.info("inside getreleaseinfo");
			Integer appid = jobDetailsDL.getAppId(appName);
			logger.info("after get app id");
			releaseNumber = jobDetailsDL.getAppReleaseNo(appid);
			logger.info("after get app releaseno");
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
		}

		return releaseNumber;
	}

	/**
	 * 
	 * @param releaseNumber
	 * @param applicationName
	 * @param env
	 */
	public void deletePlan(String releaseNumber, String applicationName, String env) {
		logger.info("inside deleteing");
		try {
			jobDetailsDL.deletePlan(releaseNumber, applicationName, env);
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * Returns app after checking permission of the specified user
	 * 
	 * @param userName
	 * @return Applications
	 */
	public Applications getExistingApps(String userName) {

		Gson gson = new Gson();
		List<Application> apps = new ArrayList<>();

		try {
			if (jobDetailsDL.checkCreateAppPermission(userName)) {
				logger.info("The user is having access to create application");
				Applications applications = new Applications();
				List<String> permissions = getPermission(userName);
				if (permissions.isEmpty())
					return applications;
				apps = jobDetailsDL.getExistingAppDetails();

				applications.setApplications(apps);
				logger.debug("Existing applications : " + gson.toJson(applications, Applications.class).toString());
			}
		} catch (SQLException e) {
			logger.error("Existing Apps could not found!!\n" + e.getMessage(), e);
		}
		Applications applications = new Applications();
		applications.setApplications(apps);
		return applications;
	}

	/**
	 * Returns existing apps for the specified user
	 * 
	 * @param userName
	 * @return Applications
	 */
	public Applications getExistingApps(String userName, String platformName) {

		Gson gson = new Gson();
		List<Application> apps = new ArrayList<>();

		try {
			if (jobDetailsDL.checkCreateAppPermission(userName)) {
				logger.info("The user is having access to create application");
				Applications applications = new Applications();
				List<String> permissions = getPermission(userName);
				if (permissions.isEmpty())
					return applications;
				apps = jobDetailsDL.getExistingAppList(platformName);

				applications.setApplications(apps);
				logger.debug("Existing applications : " + gson.toJson(applications, Applications.class).toString());
			}
		} catch (SQLException e) {
			logger.error("Existing Apps could not found!!\n" + e.getMessage(), e);
		}
		Applications applications = new Applications();
		applications.setApplications(apps);
		return applications;
	}

	/**
	 * Fetches notification
	 * 
	 * @param username
	 * @return
	 */
	public List<Notification> getNotifications(String username) {
		List<Notification> notifList = jobDetailsDL.getNotifications(username);
		return notifList;
	}

	/**
	 * getExistingApps.
	 *
	 * @param userName the String
	 * 
	 * @return applications the Applications
	 */

	public Applications getExistingAppNames(String userName, String platformName) {

		Gson gson = new Gson();
		List<Application> apps = new ArrayList<>();

		try {
			Applications applications = new Applications();
			List<String> permissions = getAllPermission(userName);
			if (permissions.isEmpty())
				return applications;
			apps = jobDetailsDL.getExistingAppNames(platformName);

			applications.setApplications(apps);
			logger.debug("Existing applications : " + gson.toJson(applications, Applications.class).toString());
		} catch (SQLException e) {
			logger.error("Existing Apps could not found!!\n" + e.getMessage(), e);
		}
		Applications applications = new Applications();
		applications.setApplications(apps);
		return applications;
	}

	/**
	 * getExistingApps for organization.
	 *
	 * @param userName the String
	 * 
	 * @param orgName  the String
	 * 
	 * @return applications the Applications
	 */

	public Applications getExistingAppNames(String userName, String orgName, String platformName) {

		Gson gson = new Gson();
		List<Application> apps = new ArrayList<>();

		try {
			logger.info("The user is having access to create application");
			Applications applications = new Applications();
			List<String> permissions = getAllPermission(userName);
			if (permissions.isEmpty())
				return applications;
			apps = jobDetailsDL.getExistingAppNames(orgName, platformName);

			applications.setApplications(apps);
			logger.debug("Existing applications : " + gson.toJson(applications, Applications.class).toString());
		} catch (SQLException e) {
			logger.error("Existing Apps could not found!!\n" + e.getMessage(), e);
		}
		Applications applications = new Applications();
		applications.setApplications(apps);
		return applications;
	}

	/**
	 * Returns application info
	 * 
	 * @param appname
	 * @param userId
	 * @return ApplicationInfo
	 */
	public ApplicationInfo getApplicationInfo(String appname, String userId) {

		ApplicationInfo ap = null;

		List<String> permissions = getAllPermissionforApp(appname, userId);

		if (permissions.isEmpty()) {
			return new ApplicationInfo();
		}
		try {

			ap = jobDetailsDL.getApplication(appname);
		} catch (SQLException e) {
			logger.error("Problem in fetching App info", e);
			logger.debug(e.getMessage());
		}
		return ap;
	}

	/**
	 * getExistingPipelines.
	 *
	 * @param userName the String
	 * 
	 * @return applications the Applications
	 */

	public Pipelines getExistingPipelines(String appName) {

		Gson gson = new Gson();
		Pipelines pipelines = new Pipelines();
		List<Pipeline> pipelinesList = new ArrayList<>();
		try {

			pipelinesList = jobDetailsDL.getExistingPipelines(appName);

			pipelines.setPipelines(pipelinesList);
			logger.debug("Existing applications : " + gson.toJson(pipelines, Pipelines.class).toString());

		} catch (SQLException e) {
			logger.error("Existing Apps could not found!!\n" + e.getMessage(), e);
		}

		return pipelines;
	}

	/**
	 * Return pipelines of the specified app
	 *
	 * @param appName the String
	 * @return Pipelines
	 */

	public Pipelines getpipelines(String appName, String user) {

		Gson gson = new Gson();
		List<Pipeline> pips = new ArrayList<>();
		try {
			logger.info("Getting pipelines for application ");
			logger.debug(appName);

			List<String> permissions = getAllPermissionforApp(appName, user);

			if (permissions.isEmpty()) {
				Pipelines pipelines = new Pipelines();
				pipelines.setPipelines(pips);
				return pipelines;
			}
			pips = jobDetailsDL.getPipelines(appName);

		} catch (SQLException e) {
			logger.error("Existing Apps Error!!\n" + e.getMessage());
		}
		Pipelines pipelines = new Pipelines();
		pipelines.setPipelines(pips);
		logger.debug("pipelines : " + gson.toJson(pipelines, Pipelines.class));
		return pipelines;
	}

	/**
	 * Return env details of specified app
	 *
	 * @param appName the String
	 * 
	 * @return env the EnvName
	 */

	public EnvName getEnvironmentNames(String appName, String user) {

		Gson gson = new Gson();

		EnvName env = new EnvName();
		List<String> permissions = getPermissionForApplications(appName, user);
		if (permissions.isEmpty()) {
			return env;
		}
		Application apps;
		try {
			logger.debug("getting environments names");
			apps = jobDetailsDL.getExistingAppDetails(appName);
			logger.debug("AppDetails : " + gson.toJson(apps, Application.class));
			env.setEnvNames(fetchJobDetails.getEnvironmentsforPipelineCreation(apps.getAppJson(), user));
			logger.debug("Env Names : " + gson.toJson(env, EnvName.class));
		} catch (SQLException e) {
			logger.error("Existing Apps Error!!\n" + e.getMessage(), e);
		}
		return env;
	}

	/**
	 * Triggers job with specified parameter.
	 *
	 * @param triggerparameters the TriggerParameters
	 */

	public String triggerJobs(TriggerParameters triggerparameters, String userName) {
		logger.debug("Triggering jobs");

		IDPJob idpjob = null;
		Gson g = new Gson();
		String artifactName = "";
		try {
			idpjob = jobDetailsDL.getPipelineInfo(triggerparameters.getApplicationName(),
					triggerparameters.getPipelineName());
			logger.debug(g.toJson(idpjob, IDPJob.class));

		} catch (SQLException e) {
			logger.debug(e.getMessage());
			logger.error("Could not trigger the pipeline " + triggerparameters.getApplicationName() + "_"
					+ triggerparameters.getPipelineName());

		}
		if (!validateTestStep(idpjob, triggerparameters.getTestStep())) {
			throw new IllegalStateException("Invalid Test Step");
		}

		// Populating test step details
		ArrayList<TestStepInfo> testStepInfoList = new ArrayList<>();
		if (idpjob != null && null != idpjob.getTestInfo() && idpjob.getTestInfo().getTestEnv() != null
				&& idpjob.getTestInfo().getTestEnv().size() > 0) {
			for (TestEnv testenv : idpjob.getTestInfo().getTestEnv()) {
				if (testenv != null && testenv.getTestSteps() != null && testenv.getTestSteps().size() > 0) {
					for (String testStepName : triggerparameters.getTestStep()) {
						for (TestStep testStep : testenv.getTestSteps()) {
							if (testStepName.equalsIgnoreCase(testStep.getStepName())) {

								TestStepInfo testStepInfo = new TestStepInfo();
								testStepInfo.setTestStepName(testStep.getStepName());
								testStepInfo.setTestStepTool(testStep.getTest().getTestTypeName());

								testStepInfoList.add(testStepInfo);
							}
						}
					}
				}
			}
		}

		triggerparameters.setTestStepDetails(testStepInfoList);
		triggerparameters.setLanscapeName(triggerparameters.getEnvSelected());
		triggerparameters.setDashBoardLink(configmanager.getDashboardurl());

		triggerparameters = setDbDeployDetails(triggerparameters, idpjob);

		// Modularized email ids
		List<String> users = emailSender.getUsersFromApplication(triggerparameters.getApplicationName(),
				triggerparameters.getPipelineName(), triggerparameters.getUserName());

		String emailIds = String.join(",", users);
		triggerparameters.setEmailed(emailIds);
		triggerparameters.setSonardashBoardLink(configmanager.getSonardashboardurl());
		logger.info(g.toJson(triggerparameters));

		if (idpjob.getBuildInfo() != null && null != idpjob.getBuildInfo().getArtifactToStage().getnuspecFilePath()
				&& !(idpjob.getBuildInfo().getArtifactToStage().getnuspecFilePath().equals(""))) {
			triggerparameters.setNugetPackaging(true);
		} else {
			triggerparameters.setNugetPackaging(false);
		}

		// setting scm branch

		triggerparameters = setSCMBranch(triggerparameters, idpjob);

		Integer triggerId = jobDetailsInsertion.insertTriggerHistory(triggerparameters);
		triggerparameters.setTriggerId(triggerId);
		// The pipeline is getting triggered here

		String buildObj = getJobJSON(triggerparameters.getApplicationName() + "_" + triggerparameters.getPipelineName(),
				"nextBuild_Pipeline");
		JSONObject buildJson = JSONObject.fromObject(buildObj);

		if (triggerparameters.getBuild() != null) {
			if (triggerparameters.getDeploy() != null
					&& triggerparameters.getArtifactorySelected().equalsIgnoreCase("on")) {
				triggerparameters.getDeploy().getDeployArtifact().setVersion(buildJson.getString("nextBuildNumber"));
				String scm = triggerparameters.getBranchOrTagValue();
				if (scm == null) {
					scm = "default";
				}
				artifactName = triggerparameters.getApplicationName() + "_" + triggerparameters.getPipelineName() + "_"
						+ triggerparameters.getReleaseNumber() + "-" + scm + "-"
						+ buildJson.getString("nextBuildNumber");

			} else {
				artifactName = triggerparameters.getApplicationName() + "_" + triggerparameters.getPipelineName() + "_"
						+ buildJson.getString("nextBuildNumber");
			}

		}

		jobDetailsInsertion.updateTriggerHistory(triggerId, buildJson.getString("nextBuildNumber"), artifactName);
		if (idpjob.getBuildInfo() != null && null != idpjob.getBuildInfo().getArtifactToStage().getnuspecFilePath()
				&& !(idpjob.getBuildInfo().getArtifactToStage().getnuspecFilePath().equals(""))) {
			triggerparameters.setNugetPackaging(true);
		} else {
			triggerparameters.setNugetPackaging(false);
		}

		logger.info("nuget packaging is set to:" + triggerparameters.getNugetPackaging());

		if ((triggerparameters.getDeploy() != null || triggerparameters.getBuild() != null)) {
			insertArtifact(triggerparameters, buildJson, userName);

		}
		triggerBuilds.buildByJobName(triggerparameters);

		logger.info("Job " + triggerparameters.getApplicationName() + "_" + triggerparameters.getPipelineName()
				+ "is triggered.");
		return "SUCESS";
	}

	/**
	 * Sets SCM Branch specified job
	 * 
	 * @param triggerparameters
	 * @param idpjob
	 * @return trigger parameters
	 */
	public TriggerParameters setSCMBranch(TriggerParameters triggerparameters, IDPJob idpjob) {

		if (idpjob.getCode() != null && idpjob.getCode().getScm() != null && idpjob.getCode().getScm().get(0) != null
				&& idpjob.getCode().getScm().get(0).getUrl() != null) {
			logger.info(idpjob.getCode().getScm().get(0).getUrl());
			String giturl = idpjob.getCode().getScm().get(0).getUrl();
			String url = giturl.substring(giturl.lastIndexOf('/') + 1).replace(".git", "");
			triggerparameters.setMtmProjectName(url);
			if (triggerparameters.getBranchOrTagValue() != null
					&& !triggerparameters.getBranchOrTagValue().equals("")) {
				triggerparameters.setScmBranch(triggerparameters.getBranchOrTagValue());
			} else {
				if (idpjob.getCode().getScm().get(0) != null && idpjob.getCode().getScm().get(0).getBranch() != null
						&& !idpjob.getCode().getScm().get(0).getBranch().equals(""))
					triggerparameters.setScmBranch(idpjob.getCode().getScm().get(0).getBranch());
			}
		} else {
			triggerparameters.setScmBranch("");
		}
		if (triggerparameters.getBranchOrTagValue() != null
				&& !triggerparameters.getBranchOrTagValue().equalsIgnoreCase("")
				&& (triggerparameters.getBranchOrTag().equalsIgnoreCase("branch")
						|| triggerparameters.getBranchOrTag().equalsIgnoreCase("tag"))) {
			List<Boolean> branchTagValues = setBranchTagPresentValue(idpjob, triggerparameters.getBranchOrTagValue(),
					triggerparameters.getBranchOrTag());
			triggerparameters.setBranchOrTagList(branchTagValues);
			logger.debug("length of list" + branchTagValues.size());
			logger.debug("list" + branchTagValues);
		}
		return triggerparameters;
	}

	/**
	 * Sets deploy details for specified job
	 * 
	 * @param triggerparameters
	 * @param idpjob
	 * @return trigger parameters
	 */
	public TriggerParameters setDbDeployDetails(TriggerParameters triggerparameters, IDPJob idpjob) {
		if (triggerparameters.getDeploy() != null
				&& !"SapNonCharm".equalsIgnoreCase(triggerparameters.getTechnology())) {
			int idpJobDeployEnvSize = idpjob.getDeployInfo().getDeployEnv().size();
			for (int i = 0; i < idpJobDeployEnvSize; i++) {
				DeployEnv deployEnv = idpjob.getDeployInfo().getDeployEnv().get(i);

				if (deployEnv.getEnvName().equalsIgnoreCase(triggerparameters.getEnvSelected())) {
					String dbDeployOwners = "";
					String dbDeployPipelineList = "";

					if (null != triggerparameters.getDeploy().getDeployStep()
							&& triggerparameters.getDeploy().getDeployStep().size() > 0) {
						for (int j = 0; j < deployEnv.getDeploySteps().size(); j++) {

							if (triggerparameters.getDeploy().getDeployStep()
									.contains(deployEnv.getDeploySteps().get(j).getStepName())
									&& null != deployEnv.getDeploySteps().get(j).getDeployToContainer()
									&& null != deployEnv.getDeploySteps().get(j).getDeployToContainer()
											.getDbDeployPipelineList()
									&& deployEnv.getDeploySteps().get(j).getDeployToContainer()
											.getDbDeployPipelineList().size() > 0) {
								dbDeployOwners += deployEnv.getDeploySteps().get(j).getDeployToContainer()
										.getDbDeployDBOwners() + ",";
								dbDeployPipelineList += deployEnv.getDeploySteps().get(j).getDeployToContainer()
										.getDbDeployPipelineList().toString().replace("[", "").replace("]", "") + ",";
							}

						}
					} else {
						for (int j = 0; j < deployEnv.getDeploySteps().size(); j++) {
							if (null != deployEnv.getDeploySteps().get(j).getDeployToContainer()
									&& null != deployEnv.getDeploySteps().get(j).getDeployToContainer()
											.getDbDeployPipelineList()
									&& deployEnv.getDeploySteps().get(j).getDeployToContainer()
											.getDbDeployPipelineList().size() > 0) {
								dbDeployOwners += deployEnv.getDeploySteps().get(j).getDeployToContainer()
										.getDbDeployDBOwners() + ",";
								dbDeployPipelineList += deployEnv.getDeploySteps().get(j).getDeployToContainer()
										.getDbDeployPipelineList().toString().replace("[", "").replace("]", "") + ",";
							}

						}
					}

					triggerparameters.getDeploy().setDbDeployPipelineOwners(dbDeployOwners);
					triggerparameters.getDeploy().setDbDeployPipelineList(dbDeployPipelineList);
				}
			}
		}
		return triggerparameters;
	}

	/**
	 * Sets artifact details in env details
	 * 
	 * @param triggerparameters
	 * @param buildJson
	 * @param userName
	 */

	public void insertArtifact(TriggerParameters triggerparameters, JSONObject buildJson, String userName) {
		try {
			int envId = 0;
			int releaseId = releaseDetails.getReleaseId(triggerparameters.getApplicationName(),
					triggerparameters.getPipelineName(), triggerparameters.getReleaseNumber(), "on");
			int applicationId = jobDetailsDL.getApplicationId(triggerparameters.getApplicationName()).intValue();
			if (triggerparameters.getEnvSelected() != null && !(triggerparameters.getEnvSelected().equals("")))
				envId = environmentDetails.getEnvironmentId(triggerparameters.getEnvSelected(), applicationId);

			String artifactName;
			if (triggerparameters.getBuild() != null) {
				artifactName = triggerparameters.getApplicationName() + "_" + triggerparameters.getPipelineName() + "_"
						+ triggerparameters.getReleaseNumber() + "-";
				if (triggerparameters.getBranchOrTag() != null && !(triggerparameters.getBranchOrTag().equals(""))) {
					artifactName = artifactName.concat(triggerparameters.getBranchOrTag());
				} else {
					artifactName = artifactName.concat("default");

				}
				artifactName = artifactName.concat("-");
				artifactName = artifactName.concat(buildJson.getString("nextBuildNumber"));
			} else
				artifactName = triggerparameters.getDeploy().getDeployArtifact().getArtifactName();
			environmentDetails.insertArtifact(envId, releaseId, artifactName, "imported");

			int artifacID = environmentDetails.getArtifactId(artifactName);

			if (triggerparameters.getBuild() != null)
				environmentDetails.insertArtifactDetails(artifacID, "Build", "Built Through Trigger Page",
						triggerparameters.getEnvSelected(), userName);

			if (triggerparameters.getDeploy() != null)
				environmentDetails.insertArtifactDetails(artifacID, "Deploy", "Deployed Through Trigger Page",
						triggerparameters.getEnvSelected(), userName);

			triggerparameters.setArtifactName(artifactName);

		} catch (SQLException e) {
			logger.error(e.getMessage());

		}
	}

	/**
	 * Approves specified jobs
	 * 
	 * @param approveBuildParams
	 * @param to
	 * @return String
	 */
	public String apprRejectJobs(ApproveBuildParams approveBuildParams, String to) {

		String jobName = approveBuildParams.getApplicationName() + "_" + approveBuildParams.getPipelineName();

		StringBuilder jobUrl = new StringBuilder();
		jobUrl.append(configmanager.getJenkinsurl());
		jobUrl.append(JOB_URL);
		jobUrl.append(jobName);
		logger.debug("Base Job Url:" + jobUrl);
		StringBuilder jobUrl_temp = new StringBuilder(jobUrl);

		Integer buildNum = -1;
		if ("BUILD".equalsIgnoreCase(approveBuildParams.getJobType())) {
			jobUrl.append(JOB_URL);
			jobUrl.append(jobName + "_Build");
			jobUrl_temp.append(JOB_URL);
			jobUrl_temp.append(jobName + "_Build");
			buildNum = getBuildNumber(jobUrl, approveBuildParams.getApprBuildNo());
			logger.info("Build Number for Build: " + buildNum);

		} else {
			jobUrl.append(JOB_URL);
			String workEnv = approveBuildParams.getEnvName();
			jobUrl.append(jobName + "_Deploy_" + workEnv + "/job/" + jobName + "_Deploy_" + workEnv);
			jobUrl_temp.append(JOB_URL);
			jobUrl_temp.append(jobName + "_Deploy_" + workEnv + "/job/" + jobName + "_Deploy_" + workEnv);
			buildNum = getBuildNumber(jobUrl, approveBuildParams.getApprBuildNo());
			logger.info("Build Number for Deploy: " + buildNum);
		}

		if (approveBuildParams.getApprComment() == null || "".equalsIgnoreCase(approveBuildParams.getApprComment())) {
			approveBuildParams.setApprComment("No Comments Recorded");
		}
		String description = approveBuildParams.getUserName() + "  Comments: " + approveBuildParams.getApprComment();

		boolean appReject = false;

		jobUrl.append("/" + buildNum + "/input/IDPApproval/submit");
		if ("approved".equalsIgnoreCase(approveBuildParams.getApprInput())) {
			jobUrl.append("?proceed=Proceed&json=" + URLEncoder
					.encode("{\"parameter\": [{\"name\":\"APPR_INFO\", \"value\":\"" + description + "\"}]}"));
		} else if ("rejected".equalsIgnoreCase(approveBuildParams.getApprInput())) {
			jobUrl.append("?abort=Abort");
			appReject = true;
		}
		BufferedReader in = null;
		BufferedReader in2 = null;
		try {
			URL urldemo = new URL(jobUrl.toString());
			logger.info("URL for approve/reject: " + urldemo);

			HttpURLConnection yc = (HttpURLConnection) urldemo.openConnection();
			yc.setRequestMethod("POST");
			yc.setDoOutput(true);
			yc.setInstanceFollowRedirects(false);
			String userpass = configmanager.getJenkinsuserid() + ":" + configmanager.getJenkinspassword();
			String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes());
			yc.setRequestProperty("Authorization", basicAuth);
			yc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

			if (appReject) {
				logger.info("before sleep");
				Thread.sleep(20000);
				logger.info("after sleep");
				jobUrl_temp.append("/" + buildNum + "/input/IDPDefault/submit?" + "proceed=Proceed&json=" + URLEncoder
						.encode("{\"parameter\": [{\"name\":\"APPR_INFO\", \"value\":\"" + description + "\"}]}"));
				urldemo = new URL(jobUrl_temp.toString());
				logger.info("URL for reject: " + urldemo);
				yc = (HttpURLConnection) urldemo.openConnection();
				yc.setRequestMethod("POST");
				yc.setDoOutput(true);
				yc.setRequestProperty("Authorization", basicAuth);
				yc.setInstanceFollowRedirects(false);
				yc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				in2 = new BufferedReader(new InputStreamReader(yc.getInputStream()));

			}
		} catch (MalformedURLException e) {
			logger.error("malformed url");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			logger.error("Interrupt Exeption!!");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
			if (in2 != null) {
				try {
					in2.close();
				} catch (Exception e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
		}
		logger.info("Job " + approveBuildParams.getApplicationName() + "_" + approveBuildParams.getPipelineName()
				+ "is " + approveBuildParams.getApprInput() + " by " + description);
		return "SUCCESS";
	}

	/**
	 * 
	 * Return build number for specified job
	 * 
	 * @param jobUrl
	 * @param apprBuildNo
	 * @return integer
	 */
	private Integer getBuildNumber(StringBuilder jobUrl, String apprBuildNo) {

		String json = getFullJobJSON(jobUrl);
		Integer num = null;
		if (!"".equalsIgnoreCase(json)) {
			JSONObject jo = JSONObject.fromObject(json);
			if (jo.has("builds")) {
				JSONArray builds = jo.getJSONArray("builds");
				int buildsSize = builds.size();
				for (int i = 0; i < buildsSize; i++) {
					JSONObject temp = (JSONObject) builds.get(i);
					StringBuilder url = new StringBuilder(temp.getString("url"));
					if (url != null && !"".equalsIgnoreCase(url.toString())) {
						String buildjson = getFullJobJSON(url);
						JSONObject buildJo = JSONObject.fromObject(buildjson);
						if (buildJo.has("actions")) {
							JSONArray actionJsonArr = buildJo.getJSONArray("actions");
							int actionJsonArrSize = actionJsonArr.size();
							for (int j = 0; j < actionJsonArrSize; j++) {
								JSONObject jk = (JSONObject) actionJsonArr.get(j);
								if (jk.has("parameters")) {
									JSONArray parametersJsonArr = jk.getJSONArray("parameters");
									int parametersJsonArrSize = parametersJsonArr.size();
									for (int k = 0; k < parametersJsonArrSize; k++) {
										JSONObject parameterJson = (JSONObject) parametersJsonArr.get(k);
										if ("PIPELINE_BUILD_ID".equalsIgnoreCase((String) parameterJson.get("name"))) {
											String pipelineBuildNum = (String) parameterJson.get("value");
											if (Integer.parseInt(pipelineBuildNum) == Integer.parseInt(apprBuildNo)) {
												num = buildJo.getInt("number");
												break;
											}

										}

									}

								}
								if (num != null)
									break;
							}
						}
					}
					if (num != null)
						break;
				}
			}

		}

		return num;

	}

	/**
	 * Return job JSON
	 * 
	 * @param jobUrl
	 * @return string
	 */
	private String getFullJobJSON(StringBuilder jobUrl) {
		StringBuilder json = new StringBuilder();
		try {
			URL url = new URL(jobUrl.toString() + "/api/json?pretty=true");
			logger.info(jobUrl.toString());

			String line = "";
			String s = configmanager.getJenkinsuserid() + ":" + configmanager.getJenkinspassword();
			String encoding = Base64.getEncoder().encodeToString(s.getBytes());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(false);
			connection.setRequestProperty(AUTHORIZATION, BASIC + encoding);
			InputStream content = (InputStream) connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(content));

			try {
				while ((line = (in.readLine())) != null) {
					logger.info("jobs are :" + line);
					json.append(line);

				}
				return json.toString();

			} finally {
				in.close();
				content.close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			logger.error("Input/Output excetion :", e.getMessage());
		}
		return "";
	}

	/**
	 * Validates if test steps have valid details
	 * 
	 * @param idpjson
	 * @param testSteps
	 * @return boolean
	 */
	private Boolean validateTestStep(IDPJob idpjson, ArrayList<String> testSteps) {
		try {

			boolean status = false;
			if (testSteps.isEmpty()) {
				return true;
			}
			if (idpjson != null && idpjson.getTestInfo().getTestEnv() != null
					&& idpjson.getTestInfo().getTestEnv().size() > 0) {
				for (TestEnv testenv : idpjson.getTestInfo().getTestEnv()) {
					if (testenv != null && testenv.getTestSteps() != null && testenv.getTestSteps().size() > 0) {
						for (String testStepName : testSteps) {
							if (testenv.getTestSteps().stream()
									.filter(t -> t != null && !StringUtils.isEmpty(t.getStepName())
											&& testStepName.equalsIgnoreCase(t.getStepName()))
									.count() > 0) {
								status = true;
							} else {
								status = false;
							}
						}
						if (status) {
							return true;
						}

					}
				}
			}
			return false;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return true;
	}

	/**
	 * Converts Java bean to string
	 * 
	 * @param object
	 * @return string
	 * @throws IOException
	 */
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

	/**
	 * Returns release number for specified app & pipeline
	 * 
	 * @param appname
	 * @param piplineName
	 * @param pipelineName
	 * @return string set
	 */
	public Set<String> getReleaseNumber(String appname, String piplineName, List<String> pipelineName) {

		HashMap<String, Set<String>> releaseNum = new HashMap<String, Set<String>>();

		IDPJob idp = null;
		try {
			idp = jobDetailsDL.getPipelineInfo(appname, piplineName);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		for (String name : pipelineName) {
			Set<String> rlnum = new HashSet<String>();

			List<String> buildnumber = jobDetailsDL.getbuildnum(appname, name);
			for (String build : buildnumber) {

				List<String> status = jobDetailsDL.getStatus(appname, name, build);
				boolean st = true;
				for (String buildstatus : status) {
					if (!buildstatus.equalsIgnoreCase(SUCCESS)) {
						st = false;
						break;
					}
				}

				if (st) {
					String releasenum = jobDetailsDL.getreleaseNum(appname, name, build);
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

	/**
	 * 
	 * check Available Jobs To Trigger
	 * 
	 * @param userName the String
	 * @return history the History
	 * 
	 */

	public History checkAvailableJobsToTrigger(String userName, String platformName) {
		History history = new History();
		List<String> permissions = getPermission(userName);
		if (permissions.isEmpty())
			return history;
		Gson gson = new Gson();
		logger.debug("check available jobs to trigger");
		List<PipelineDetail> pipelineDetails = jobDetailsDL.getApplicationDetails(userName, platformName);
		if (platformName.equalsIgnoreCase("IDP")) {
			pipelineDetails.addAll(jobDetailsDL.getPipelinesCustomPipelineadmin(userName));
		}
		history.setPipelineDetails(pipelineDetails);
		history.setUserName(userName);
		logger.debug("History : " + gson.toJson(history, History.class));
		return history;
	}

	public int deleteRole(ApplicationInfo appinfo) {
		logger.info("Inside delete");

		return delinfo.deleteRoles(jobDetailsDL.getApplicationId(appinfo.getApplicationName()));

	}

	/**
	 * 
	 * create Application
	 * 
	 * @param appInfo the ApplicationInfo
	 * 
	 * 
	 */
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
			addArtifctoryRepository(appInfo);
		}

		logger.info("Slaves Created");

		return SUCCESS;

	}

	/**
	 * Updates app info
	 * 
	 * @param appInfo
	 * @param user
	 * @param orgName
	 */
	public void updateInfo(ApplicationInfo appInfo, String user, String orgName) {

		logger.debug("Update application Details");
		updateApp(appInfo, user, orgName);
		logger.info("Application Updated");
		// creating list of slaves in Jenkins
		createSlaves(appInfo);

		logger.info("Slaves Created");
	}

	/**
	 * 
	 * InsertApplicationDetails
	 * 
	 * @param appInfo the ApplicationInfo
	 * 
	 * 
	 */

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
								if (!jobDetailsDL.userExists(temp))

								{
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
				cli.createRole("IDP_User", usersList.toString(), appName + ".*_Slave", "GLOBAL", PIPELINE_READER);
				status = cli.createRole(appName + "_Reader", usersList.toString(), sPipelineAdminReaderPattern,
						"PROJECT", PIPELINE_READER);
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

	/**
	 * 
	 * insertApplicationDetails
	 * 
	 * @param appInfo the ApplicationInfo
	 * @param user    the String
	 * @param orgName the String
	 * 
	 * @return TriggerInputs
	 */

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
							if (!jobDetailsDL.userExists(temp))

							{
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
								if (!jobDetailsDL.userExists(temp))

								{
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
								if (!jobDetailsDL.userExists(temp))

								{
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
				cli.createRole("IDP_User", usersList.toString(), appName + ".*_Slave", "GLOBAL", PIPELINE_READER);
				status = cli.createRole(appName + "_Reader", usersList.toString(), sPipelineAdminReaderPattern,
						"PROJECT", PIPELINE_READER);
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

	/**
	 * 
	 * Fetch Trigger Information
	 * 
	 * @param triggerJobName the TriggerJobName
	 * 
	 * @return TriggerInputs
	 */

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

	/**
	 * Returns job pair name
	 * 
	 * @param triggerJobName
	 * @return names
	 */
	public Names getPairName(TriggerJobName triggerJobName) {
		logger.info("inside jobsBL");
		Gson gson = new Gson();
		Names names = new Names();
		try {
			Pipeline pipeline = getPipelineDetails(triggerJobName);
			logger.info("pipeline : " + gson.toJson(pipeline));
			List<String> nameList = getTriggerDetails.getPairNames(pipeline.getPipelineJson(),
					triggerJobName.getEnvName());

			names.setNames(nameList);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return names;

	}

	/**
	 * Returns Application details
	 * 
	 * @param userId
	 * @param platform
	 * @return appNames the AppNames
	 * @throws SQLException
	 */
	public AppNames getApplications(String userId, String platform) throws SQLException {
		AppNames appNames = new AppNames();
		List<String> permissions = getPermission(userId);
		if (null == permissions || permissions.isEmpty()) {
			return appNames;
		}
		Gson gson = new Gson();
		List<String> apps = jobDetailsDL.getApplications(userId, platform);

		logger.debug("getting application");
		appNames.setApplicationNames(apps);
		logger.debug(APPLICATION_NAME + gson.toJson(appNames, AppNames.class));
		return appNames;
	}

	/**
	 * 
	 * Returns Filtered Application
	 * 
	 * @param userId the String
	 * 
	 * @return appNames the AppNames
	 */

	public AppNames getFilteredApplications(String filterString, String userId, String platform) throws SQLException {
		AppNames appNames = new AppNames();
		List<String> permissions = getPermission(userId);
		if (null == permissions || permissions.isEmpty()) {
			return appNames;
		}
		Gson gson = new Gson();
		List<String> nonFilteredApps = jobDetailsDL.getApplications(userId, platform);
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

	/**
	 * Return roles list for specified user in specified app
	 * 
	 * @param uname
	 * @param AppName
	 * @return list of roles
	 */
	public List<String> getRolesApp(String uname, String appName) {

		return jobDetailsDL.getRolesasApp(uname, appName);

	}

	/**
	 * Returns release no. for specified pipeline and app
	 * 
	 * @param pipelinename
	 * @param appName
	 * @return release no
	 */
	public String getReleaseNo(String pipelineName, String appName) {

		return jobDetailsDL.getReleaseNo(pipelineName, appName);

	}

	/**
	 * 
	 * Returns Application Details
	 * 
	 * @param appName the String
	 * 
	 * @return app the Application
	 */

	public Application getApplicationDetails(String appName, String userName) throws SQLException {

		List<String> permissions = getAllPermission(userName);

		Application app = null;
		if (null == permissions || permissions.isEmpty()) {
			app = new Application();
			return app;
		}
		Gson gson = new Gson();
		app = jobDetailsDL.getApplicationDetail(appName);
		logger.debug(APPLICATION_NAME + gson.toJson(app, Application.class));
		return app;
	}

	/**
	 * 
	 * Returns user Roles
	 * 
	 * @param userId the String
	 * 
	 * @return userRoles the String
	 */
	public List<String> getRoles(String userId) {

		logger.debug("Getting roles");
		List<String> userRoles = jobDetailsDL.getRoles(userId);
		logger.debug("roles : " + userRoles);
		return userRoles;
	}

	/**
	 * 
	 * Returns user Permissions
	 * 
	 * @param userId the String
	 * 
	 * @return userPermissions the String
	 */
	public List<String> getPermission(String userId) {

		logger.debug("getting permissions");
		List<String> userPermissions = jobDetailsDL.getPermission(userId);
		logger.debug("Permissions:" + userPermissions);
		return userPermissions;
	}

	/**
	 * 
	 * Returns user Base Role
	 * 
	 * @param userId the String
	 * 
	 * @return userBaseRoles the String
	 */
	public List<String> getBaseRoles(String userId) {

		logger.debug("Getting base Role");
		List<String> userBaseRoles = jobDetailsDL.getBaseRoles(userId);
		logger.debug("Base Role : " + userBaseRoles);
		return userBaseRoles;
	}

	/**
	 * 
	 * Returns user Base permission
	 * 
	 * @param userId the String
	 * @return userBasePermission the String
	 */
	public List<String> getBasePermission(String userId) {

		logger.debug("Getting base permission");

		return jobDetailsDL.getBasePermission(userId);
	}

	/**
	 * 
	 * 
	 * Returns user all permission
	 * 
	 * @param userId the String
	 * @return List<String>
	 */
	public List<String> getAllPermission(String userId) {

		logger.info("Getting All permission");
		List<String> permissions = jobDetailsDL.getBasePermission(userId);

		permissions.addAll(jobDetailsDL.getPermission(userId));
		return permissions;
	}

	/**
	 * 
	 * Returns user all permission for app
	 * 
	 * @param userId the String
	 * @return userBasePermission the String
	 */
	public List<String> getAllPermissionforApp(String appName, String userId) {

		logger.info("Getting All permission for appliaction");
		List<String> permissions = jobDetailsDL.getBasePermission(userId);

		permissions.addAll(jobDetailsDL.getPermissionForApplications(userId, appName));

		logger.info("permissions : " + permissions.toString());

		return permissions;
	}

	/**
	 * Create create list of slaves for application
	 * 
	 * @param appInfo the ApplicationInfo
	 * 
	 */

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
					cli.copySlave(slave.getSlaveName(), slave.getWorkspacePath(), slave.getLabels(),
							slave.getSSHKeyPath());
				} catch (Exception e) {
					logger.error("Error in slave creation!!", e);

				}
			}

		}

	}

	/**
	 * 
	 * Returns build for the given jobs
	 * 
	 * @param jobName the TriggerJobName
	 * 
	 */

	public JobsBuilds getBuildJobs(TriggerJobName jobName) {
		JobsBuilds jobsBuilds = new JobsBuilds();
		List<String> permissions = getPermissionForApplications(jobName.getApplicationName(), jobName.getUserName());
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

	/**
	 * Return job JSON
	 * 
	 * @param jobName
	 * @param param   - job type
	 * @return
	 */
	public String getJobJSON(String jobName, String param) {

		StringBuilder jobUrl = new StringBuilder();
		jobUrl.append(configmanager.getJenkinsurl());
		jobUrl.append(JOB_URL);
		jobUrl.append(jobName);
		logger.debug("Base Job Url:" + jobUrl);
		if ("job".equalsIgnoreCase(param)) {

			jobUrl.append("/api/json?tree=jobs[name]");
			logger.debug("job url " + jobUrl);
		} else if ("builds".equalsIgnoreCase(param)) {

			jobUrl.append("/api/json?tree=builds[number]");
			logger.debug("builds url " + jobUrl);
		} else if ("builds_Pipeline".equalsIgnoreCase(param)) {

			jobUrl.append(JOB_URL + jobName + "_Pipeline/api/json?tree=builds[number]");
			logger.debug("builds url " + jobUrl);
		} else if ("lastBuild".equalsIgnoreCase(param)) {

			jobUrl.append(JOB_URL + jobName + "_Pipeline/api/json?tree=lastBuild[number]");
			logger.debug("last build url " + jobUrl);
		} else if ("nextBuild".equalsIgnoreCase(param)) {

			jobUrl.append(JOB_URL + jobName + "_Pipeline/api/json?pretty=true");
			logger.debug("next build url " + jobUrl);
		} else if ("nextBuild_Pipeline".equalsIgnoreCase(param)) {
			jobUrl.append(JOB_URL + jobName + "_Pipeline/api/json?pretty=true");
			logger.debug("nextBuild_Pipeline url " + jobUrl);
		} else if ("buildable".equalsIgnoreCase(param)) {

			jobUrl.append("_Main/api/json?pretty=true");
			logger.debug("buildable url " + jobUrl);
		} else if (param.contains("ApprovalCheck")) {
			jobUrl.append(JOB_URL + param.split(";")[2] + param.split(";")[0] + APPR_STEP);
			logger.debug("buildable url " + jobUrl);

		} else if (param.contains("apprNext;")) {
			if ("BUILD".equalsIgnoreCase(param.split(";")[1])) {
				jobUrl.append(JOB_URL + jobName + "_Build");
			} else if ("DEPLOY".equalsIgnoreCase(param.split(";")[1])) {
				String workenv = param.split(";")[2];
				jobUrl.append(JOB_URL + jobName + "_Deploy_" + workenv);
				jobUrl.append(JOB_URL + jobName + "_Deploy_" + workenv);

			}
			jobUrl.append("/api/json?tree=nextBuildNumber");
			logger.debug("buildable url " + jobUrl);
		} else if (param.contains("getJson;")) {
			jobUrl.append("/job/" + param.split(";")[2] + param.split(";")[0]);
			jobUrl.append("/api/json");
			logger.debug("buildable url " + jobUrl);
		}

		try {
			URL url = new URL(jobUrl.toString());
			logger.info(jobUrl.toString());
			StringBuilder json = new StringBuilder();
			String line = "";
			String s = configmanager.getJenkinsuserid() + ":" + configmanager.getJenkinspassword();
			String encoding = Base64.getEncoder().encodeToString(s.getBytes());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(false);
			connection.setRequestProperty(AUTHORIZATION, BASIC + encoding);
			InputStream content = (InputStream) connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(content));

			try {
				while ((line = (in.readLine())) != null) {
					json.append(line);
				}
				return json.toString();
			} finally {
				in.close();
				content.close();
			}
		} catch (MalformedURLException e) {
			logger.error("malformed url", e.getMessage());
		} catch (IOException e) {
			logger.error("IO Exeption!!", e);
		}
		return "";

	}

	/**
	 * 
	 * Returns Pipeline Details
	 * 
	 * @param triggerJobName the TriggerJobName
	 * 
	 * @return pipeline the Pipeline
	 */

	public Pipeline getPipelineDetails(TriggerJobName triggerJobName) throws SQLException {

		Gson gson = new Gson();

		List<String> permissions = getAllPermissionforApp(triggerJobName.getApplicationName(),
				triggerJobName.getUserName());

		if (permissions.isEmpty()) {
			return new Pipeline();
		}
		Pipeline pipeline = jobDetailsDL.getPipelineDetail(triggerJobName);
		pipeline.setMethod(triggerJobName.getMethod());
		String newPipelineName = pipeline.getPipelineName() + "_Copy";
		if ("copy".equalsIgnoreCase(triggerJobName.getMethod())) {
			pipeline.setPipelineName(newPipelineName);
			pipeline.getPipelineJson().getBasicInfo().setPipelineName(newPipelineName);

		}

		logger.debug("Pipeline Names : " + gson.toJson(pipeline, Pipeline.class));
		return pipeline;
	}

	/**
	 * 
	 * Returns JobParam Details
	 * 
	 * @param triggerJobName the TriggerJobName
	 * 
	 * @return pipeline the Pipeline
	 */

	public List<JobParam> getJobParamDetails(String appName, String pipelineName) throws SQLException {
		List<JobParam> jobParamList = new ArrayList<>();
		jobParamList = jobDetailsDL.getJobParamDetails(appName, pipelineName);
		return jobParamList;

	}

	/**
	 * 
	 * Returns download url for Artifacts
	 * 
	 * @param downloadArtifactInputs the DownloadArtifactInputs
	 * 
	 * @return String
	 * 
	 *
	 */

	public String downloadArtifacts(DownloadArtifactInputs downloadArtifactInputs) {
		StringBuilder jobUrl = new StringBuilder();
		if ("SelectNumber".equalsIgnoreCase(downloadArtifactInputs.getBuildNumber())
				|| ("SelectJob").equalsIgnoreCase(downloadArtifactInputs.getSubJobName())) {
			logger.error("Build number or job is not correct!!");
			return null;

		}
		jobUrl.append(configmanager.getJenkinsurl());
		jobUrl.append(JOB_URL);
		jobUrl.append(downloadArtifactInputs.getJobName());
		jobUrl.append(JOB_URL);
		jobUrl.append(downloadArtifactInputs.getSubJobName());
		jobUrl.append("/");
		jobUrl.append(downloadArtifactInputs.getBuildNumber());
		jobUrl.append("/artifact/*zip*/archive.zip");
		String line = new String();
		FileOutputStream outputStream = null;
		InputStream in = null;
		try {
			URL url = new URL(jobUrl.toString());
			String s = configmanager.getJenkinsuserid() + ":" + configmanager.getJenkinspassword();
			String encoding = Base64.getEncoder().encodeToString(s.getBytes());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(false);
			connection.setRequestProperty(AUTHORIZATION, BASIC + encoding);

			in = connection.getInputStream();
			outputStream = new FileOutputStream(downloadArtifactInputs.getJobName() + ".zip");
			int bytesRead = -1;
			byte[] buffer = new byte[4096];

			while ((bytesRead = in.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
				logger.debug("No artifacts present");
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return line;
	}

	/**
	 * 
	 * Deletes Pipeline
	 * 
	 * @param triggerJobName the TriggerJobName
	 * 
	 * @return boolean
	 * 
	 *
	 */

	public boolean deletePipeline(TriggerJobName triggerJobName) {
		List<String> permissions = getAllPermissionforApp(triggerJobName.getApplicationName(),
				triggerJobName.getUserName());
		if (!permissions.contains("DELETE_PIPELINE")) {
			return false;
		}
		boolean status = updateJobDetails.deletePipeline(triggerJobName);
		if (status) {
			logger.info("deleted job from database");
			return status;
		}

		try {
			cli.disableJob(triggerJobName.getApplicationName() + "_" + triggerJobName.getPipelineName(),
					"randomString");
		} catch (IOException e) {
			logger.error("could not disable the job", e);
		}
		return status;

	}

	/**
	 * 
	 * Returns user Roles for App
	 * 
	 * @param userId  the String
	 * @param appName the String
	 * 
	 * @return userRoles the String
	 */

	public List<String> getRolesForApp(String userId, String appName) {

		logger.debug("Getting roles");
		List<String> userRoles = jobDetailsDL.getRolesasApp(userId, appName);
		logger.debug("roles : " + userRoles);
		return userRoles;
	}

	/**
	 * 
	 * Returns user Permissions for given roles
	 * 
	 * @param roles the String
	 * 
	 * @return userPermissions the String
	 */

	public List<String> getPermissionForApplications(String applicationName, String userName) {

		logger.debug("getting permissions");

		List<String> userPermissions = jobDetailsDL.getPermissionForApplications(userName, applicationName);
		try {
			userPermissions.addAll(getPipelinePermissionforApplication(applicationName, userName));
		} catch (SQLException e) {
			logger.error("error while getting pipeline permission for application ", e.getMessage());
		}
		logger.debug("Permissions:" + userPermissions);
		return userPermissions;
	}

	/**
	 * Add ALM server in Jenkins configuration
	 * 
	 * @param idp Json object
	 * @since April 2018
	 */
	private void addALMServer(IDPJob idp) {

		String serverUrl = null;
		String serverName = null;

		if (idp != null && idp.getTestInfo() != null && idp.getTestInfo().getTestEnv() != null
				&& idp.getTestInfo().getTestEnv().size() > 0) {
			for (TestEnv testenv : idp.getTestInfo().getTestEnv()) {
				if (testenv != null && testenv.getTestSteps() != null && testenv.getTestSteps().size() > 0) {
					for (TestStep testStep : testenv.getTestSteps()) {
						if ("hpAlm".equalsIgnoreCase(testStep.getTest().getTestTypeName())) {

							serverUrl = testStep.getTest().getServerName();
							if (serverUrl.contains("qcbin")) {

							} else {
								serverUrl += "/qcbin/";
							}
							serverName = idp.getBasicInfo().getPipelineName() + "_ALM";
							try {
								serverName = cli.addALMConfig(serverName, serverUrl);
								testStep.getTest().setServerName(serverName);
							} catch (IOException e) {
								logger.error(e.getMessage(), e);
							}

						}

					}
				}
			}
		}

	}

	/**
	 * Returns stage view URL
	 * 
	 * @param appName
	 * @param pipelineName
	 * @return string
	 * @throws Exception
	 */

	public String getStageViewUrl(String appName, String pipelineName) throws Exception {

		String url = "";

		String jenkinsUrl = configmanager.getJenkinsstageviewurl();
		String appNameWS = appName.replaceAll(" ", "%20");
		String pipelineNameWS = pipelineName.replaceAll(" ", "%20");
		String jobName = appNameWS + "_" + pipelineNameWS;

		url = jenkinsUrl + JOB_URL + jobName + JOB_URL + jobName + "_Pipeline/";

		return url;

	}

	/**
	 * Returns Application Name For Release Manager
	 * 
	 * @param userName
	 * @return names the Names
	 */

	public Names getApplicationNameForReleaseManager(String userName, String platformName) {

		List<String> permissions = getAllPermission(userName);

		Names names = new Names();
		if (null == permissions || !permissions.contains("RELEASE")) {

			return names;
		}
		Gson gson = new Gson();
		List<String> applications = jobDetailsDL.getApplicationNameForReleaseManager(userName, platformName);
		names.setNames(applications);
		logger.debug(APPLICATION_NAME + gson.toJson(names, Names.class));
		return names;
	}

	/**
	 * Returns pipeline name for application
	 * 
	 * @param applicationName
	 * @param workflowString
	 * @param userName
	 * @return names
	 */
	public Names pipelineNamesForApplication(String applicationName, String workflowString, String userName) {

		List<String> permissions = getAllPermission(userName);

		Names names = new Names();
		if (null == permissions || permissions.isEmpty()) {
			return names;
		}

		Gson gson = new Gson();
		List<String> pipelines = jobDetailsDL.pipelineNamesForApplication(applicationName, workflowString);
		names.setNames(pipelines);
		logger.debug("pipeline Names for the application " + applicationName + " : " + gson.toJson(names, Names.class));
		return names;
	}

	/**
	 * 
	 * Fetch Trigger Steps
	 * 
	 * @param appName the Application Name
	 * @param envName the Environment Selected
	 * 
	 * @return deploy and Test Steps
	 */

	public Steps fecthTriggerSteps(String appName, String pipelineName, String envName) throws SQLException {

		Steps steps = getTriggerDetails.fecthTriggerSteps(appName, pipelineName, envName);

		logger.debug("Fetch trigger options");
		return steps;

	}

	/**
	 * Returns dependent Pipelines.
	 *
	 * @param appName the String
	 * @return Pipelines
	 */

	public Pipelines getDependencyPipelines(String appName, String user) {

		Gson gson = new Gson();
		List<Pipeline> pips = new ArrayList<>();
		try {
			logger.info("Getting pipelines for application ");
			logger.debug(appName);
			List<String> permissions = getPermissionForApplications(appName, user);
			if (permissions.isEmpty()) {
				Pipelines pipelines = new Pipelines();
				pipelines.setPipelines(pips);
				return pipelines;
			}
			pips = jobDetailsDL.getDependencyPipelines(appName);

		} catch (SQLException e) {
			logger.error("Existing Apps Error!!\n" + e.getMessage());
		}
		Pipelines pipelines = new Pipelines();
		pipelines.setPipelines(pips);
		logger.debug("pipelines : " + gson.toJson(pipelines, Pipelines.class));
		return pipelines;
	}

	/**
	 * Fetch sub application for given application name.
	 * 
	 * @param the appName
	 * 
	 * @return the list of sub applications
	 * 
	 */
	public SubApplication getSubApplications(String applicationName) {

		SubApplication subApplication = new SubApplication();
		subApplication.setSubApps(jobDetailsDL.getSubAppDetails(applicationName));

		logger.debug("getting application");
		return subApplication;
	}

	/**
	 * Fetch database deployment operations
	 * 
	 * @param subApplicationName the sub application name
	 * 
	 * @return the String of operations
	 */
	public DBDeployOperations getDBDeployOperations(String subApplicationName, String appName) {

		String subApps = jobDetailsDL.getDBDeployOperation(subApplicationName, appName);
		List<String> operations = new ArrayList<>();
		String[] opt = subApps.split(";");

		for (String string : opt) {
			operations.add(string);
		}

		DBDeployOperations dbDeployOperations = new DBDeployOperations();
		dbDeployOperations.setOperations(operations);
		logger.debug("getting Database deployment opeartionas ");
		return dbDeployOperations;
	}

	/**
	 * 
	 * Returns Pipeline List for DBDeploy for Particular Application
	 * 
	 * @param appName  the Application Name
	 * @param userName the user
	 * 
	 * @return dbdeploy pipeline list of selected application
	 */
	public Names dbDeployPipelineNamesForApplication(String applicationName, String userName) {

		List<String> permissions = getAllPermission(userName);

		Names names = new Names();
		if (null == permissions || permissions.isEmpty()) {
			return names;
		}

		Gson gson = new Gson();
		List<String> pipelines = jobDetailsDL.dbDeployPipelineNamesForApplication(applicationName);
		names.setNames(pipelines);
		logger.debug("pipeline Names for the application " + applicationName + " : " + gson.toJson(names, Names.class));
		return names;
	}

	/**
	 * Setting true or false if the branches are present in other SCM to checkout
	 * the branches provides by the user on trigger page from the SCMs
	 * 
	 * @param idpjob
	 * @param branchOrTagValue
	 * @param branchOrTag
	 * @return list of booleans
	 */
	public List<Boolean> setBranchTagPresentValue(IDPJob idpjob, String branchOrTagValue, String branchOrTag) {

		List<Boolean> list = new ArrayList<>();

		try {
			if (idpjob.getCode() != null && idpjob.getCode().getScm() != null && idpjob.getCode().getScm().size() > 0) {

				int codeScmSize = idpjob.getCode().getScm().size();
				for (int i = 0; i < codeScmSize; i++) {

					List<String> branchList = new ArrayList<>();
					List<String> tagList = new ArrayList<>();
					if (!(idpjob.getCode().getScm().get(i).getType().equalsIgnoreCase("git")
							|| idpjob.getCode().getScm().get(i).getType().equalsIgnoreCase("tfs"))) {
						list.add(false);
					}

					else if (idpjob.getCode().getScm().get(i).getType().equalsIgnoreCase("git")) {
						if (idpjob.getCode().getScm().get(i).getRepositoryBrowser().equalsIgnoreCase("gitlab")) {
							String repoUrl = idpjob.getCode().getScm().get(i).getBrowserUrl();
							String username = idpjob.getCode().getScm().get(i).getUserName();
							String pwd = idpjob.getCode().getScm().get(i).getPassword();
							String projectUrl = idpjob.getCode().getScm().get(i).getUrl();
							ArrayList<ArrayList<String>> branchTagList = getTriggerDetails
									.gitLabbranchesTagsFetcher(repoUrl, username, pwd, projectUrl);
							if (branchTagList != null) {
								if (branchTagList.size() != 0) {
									branchList = branchTagList.get(0);
								}

								if (i == 0 && branchTagList.size() > 1) {
									tagList = branchTagList.get(1);
								}

							}

						}

						else if (idpjob.getCode().getScm().get(i).getRepositoryBrowser().equalsIgnoreCase("github")) {

							String username = idpjob.getCode().getScm().get(i).getUserName();
							String pwd = idpjob.getCode().getScm().get(i).getPassword();
							logger.info(idpjob.getCode().getScm().toString());
							String projectUrl = idpjob.getCode().getScm().get(i).getUrl();
							String proxy = idpjob.getCode().getScm().get(i).getProxy();
							String port = idpjob.getCode().getScm().get(i).getProxyPort();

							String s[] = projectUrl.split("/");
							String repoUrl = s[0] + "//" + s[2];

							List<ArrayList<String>> branchTagList = getTriggerDetails.gitHubBranchesTagsFetcher(
									new GitHubBrachModel(repoUrl, username, pwd, projectUrl, proxy, port));
							if (branchTagList != null) {
								if (branchTagList.size() != 0) {
									branchList = branchTagList.get(0);
								}

								if (branchTagList.size() > 1) {
									tagList = branchTagList.get(1);
								}

							}

						} else if (idpjob.getCode().getScm().get(i).getRepositoryBrowser()
								.equalsIgnoreCase("bitbucket")) {

							String repoUrl = idpjob.getCode().getScm().get(i).getBrowserUrl();
							String username = idpjob.getCode().getScm().get(i).getUserName();
							String pwd = idpjob.getCode().getScm().get(i).getPassword();
							logger.info(idpjob.getCode().getScm().toString());
							String projectUrl = idpjob.getCode().getScm().get(i).getUrl();
							String proxy = idpjob.getCode().getScm().get(i).getProxy();
							String port = idpjob.getCode().getScm().get(i).getProxyPort();
							logger.info(port);
							logger.info(idpjob.getCode().getScm().get(i).getProxyPort());
							List<ArrayList<String>> branchTagList = getTriggerDetails
									.bitBucketbranchesTagsFetcher(repoUrl, username, pwd, projectUrl, proxy, port);
							logger.info("sjowing branchTag");
							logger.info(branchTagList.toString());
							if (branchTagList != null) {
								if (branchTagList.size() != 0) {
									branchList = branchTagList.get(0);
								}

								if (branchTagList.size() > 1) {
									tagList = branchTagList.get(1);
								}

							}

						}

						if (i == 0 && branchOrTag.equalsIgnoreCase("tag")) {
							boolean status = false;
							for (int j = 0; j < tagList.size(); j++) {
								if (tagList.get(j).equals(branchOrTagValue)) {
									list.add(true);
									status = true;
									break;
								}
							}

							if (status == false) {
								list.add(false);
							}
						}

						if (branchOrTag.equalsIgnoreCase("branch")) {
							boolean status = false;
							for (int j = 0; j < branchList.size(); j++) {
								if (branchList.get(j).equals(branchOrTagValue)) {
									list.add(true);
									status = true;
									break;
								}
							}

							if (status == false) {
								list.add(false);
							}
						}

						if (i > 0 && branchOrTag.equalsIgnoreCase("tag")) {
							list.add(false);

						}
					}

					else if (idpjob.getCode().getScm().get(i).getType().equalsIgnoreCase("tfs")) {

						String repoUrl = idpjob.getCode().getScm().get(i).getUrl();
						String projPath = idpjob.getCode().getScm().get(i).getProjPath();
						String username = idpjob.getCode().getScm().get(i).getUserName();
						String pwd = idpjob.getCode().getScm().get(i).getPassword();

						branchList = getTriggerDetails.branchFetcher(repoUrl, projPath, username, pwd);

						if (branchOrTag.equalsIgnoreCase("branch")) {
							boolean f = false;
							for (int j = 0; j < branchList.size(); j++) {
								if (branchList.get(j).equals(branchOrTagValue)) {
									list.add(true);
									f = true;
									break;
								}
							}

							if (f == false) {
								list.add(false);
							}
						}

						else {
							list.add(false);
						}

					}
				}
			}
		} catch (NullPointerException e) {

			logger.error(e.getMessage());
		}

		return list;
	}

	/**
	 * Fetch applicationId for given Application Name
	 * 
	 * @param applicationName the Application Name
	 * @return the application Id
	 */
	public String getApplicationID(String applicationName) {

		Long applicationId = jobDetailsDL.getApplicationId(applicationName);

		return applicationId.toString();
	}

	/**
	 * Fetch pipelineId for given Pipeline Name
	 * 
	 * @param applicationName the application name
	 * @param pipelineName    the Pipeline Name
	 * @return the pipeline Id
	 */
	public String getPipelineID(String applicationName, String pipelineName) {
		Long pipelineId = null;
		try {
			pipelineId = jobDetailsDL.getPipelineId(pipelineName, applicationName);
		} catch (SQLException e) {
			logger.error("Postgres Error while fetching pipelineId :", e);
		}

		return pipelineId.toString();
	}

	/**
	 * // getting MTM Test Plans
	 * 
	 * @param appName
	 * @param pipelineName
	 * @return list of test plans
	 */
	public List<TestPlans> fetchMTMTestPlans(String appName, String pipelineName) {

		List<TestPlans> testPlansList = new ArrayList<>();

		try {
			IDPJob idpjob = jobDetailsDL.getPipelineInfo(appName, pipelineName);
			List<TestEnv> testEnv = idpjob.getTestInfo().getTestEnv();
			String projNam = "";
			if (null != testEnv) {
				int testEnvSize = testEnv.size();
				for (int i = 0; i < testEnvSize; i++) {
					String envFlag = testEnv.get(i).getEnvFlag();
					if (!(envFlag != null && envFlag.equalsIgnoreCase("on"))) {
						continue;
					} else {
						List<TestStep> testSteps = testEnv.get(i).getTestSteps();
						if (null != testSteps) {

							int testStepsSize = testSteps.size();
							for (int j = 0; j < testStepsSize; j++) {
								if (null != testSteps.get(j).getTest()
										&& null != testSteps.get(j).getTest().getTestTypeName()
										&& testSteps.get(j).getTest().getTestTypeName().equals("mtm")) {
									projNam = testSteps.get(j).getTest().getProjectName();
									break;
								}
							}
						}
					}
				}
			}

			testPlansList = testPlanDetailsFetcher.getTestPlan(projNam);
		} catch (NullPointerException e) {

			logger.error(e.getMessage());

		} catch (SQLException e) {

			logger.error(e.getMessage());
		}

		return testPlansList;

	}

	/**
	 * // getting MTM Test Suits
	 * 
	 * @param planID
	 * @param appName
	 * @param pipelineName
	 * @return list of testsuits
	 */
	public List<TestSuits> fetchMTMTestSuits(Integer planID, String appName, String pipelineName) {

		List<TestSuits> testSuitsList = new ArrayList<>();
		try {
			IDPJob idpjob = jobDetailsDL.getPipelineInfo(appName, pipelineName);
			List<TestEnv> testEnv = idpjob.getTestInfo().getTestEnv();
			String projNam = "";
			if (null != testEnv) {

				int testEnvSize = testEnv.size();
				for (int i = 0; i < testEnvSize; i++) {
					String envFlag = testEnv.get(i).getEnvFlag();
					if (!(envFlag != null && envFlag.equalsIgnoreCase("on"))) {
						continue;
					} else {
						List<TestStep> testSteps = testEnv.get(i).getTestSteps();
						if (null != testSteps) {
							int testStepsSize = testSteps.size();
							for (int j = 0; j < testStepsSize; j++) {
								if (null != testSteps.get(j).getTest()
										&& null != testSteps.get(j).getTest().getTestTypeName()
										&& testSteps.get(j).getTest().getTestTypeName().equals("mtm")) {
									projNam = testSteps.get(j).getTest().getProjectName();
									break;
								}
							}
						}
					}
				}
			}

			testSuitsList = testPlanDetailsFetcher.getTestSuits(planID, projNam);
		} catch (NullPointerException e) {

			logger.error(e.getMessage());

		} catch (SQLException e) {

			logger.error(e.getMessage());
		}

		return testSuitsList;
	}

	/**
	 * 
	 * Returns permission for pipeline
	 * 
	 * @param appName the Application Name
	 * 
	 * @return pipelineName the Pipeline Name
	 */

	public List<String> getPipelinePermission(String appname, String pipelineName, String userId) throws SQLException {

		List<String> permissionList = jobDetailsDL.getPipelinePermission(appname, pipelineName, userId);

		return permissionList;

	}

	/**
	 * 
	 * Returns Pipeline permission for application
	 * 
	 * @param appName the Application Name
	 * 
	 * @return pipelineName the Pipeline Name
	 */

	public List<String> getPipelinePermissionforApplication(String appname, String userId) throws SQLException {

		return jobDetailsDL.getPipelinePermissionForApplication(appname, userId);
	}

	/**
	 * 
	 * Method is used to add artifactory in global configuration of jenkins
	 * 
	 * @param appInfo the ApplicationInfo
	 * 
	 */

	public void addArtifctoryRepository(ApplicationInfo appInfo) {
		int i;
		String repoName = appInfo.getArtifactToStage().getArtifactRepoName();
		if (repoName.equalsIgnoreCase("jfrog")) {
			try {
				i = cli.addArtifactoryRepoGlobConf(appInfo.getArtifactToStage().getArtifactRepo().getRepoURL(),
						appInfo.getArtifactToStage().getArtifactRepo().getRepoUsername(),
						appInfo.getArtifactToStage().getArtifactRepo().getRepoPassword(),

						appInfo.getArtifactToStage().getArtifactRepo().getRepoUsername().toLowerCase() + "_"
								+ appInfo.getArtifactToStage().getArtifactRepo().getRepoName());

				logger.info("Artifcatory added successfully. Status : " + i);
			} catch (Exception e) {
				logger.error("Error in slave creation!!", e);
			}
		}
	}
}
