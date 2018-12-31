/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.businessapi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.infy.entities.triggerinputs.Steps;
import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.dataapi.services.JobAdditionalDetailsDL;
import org.infy.idp.dataapi.services.JobInfoDL;
import org.infy.idp.dataapi.services.JobManagementDL;
import org.infy.idp.dataapi.services.ReleaseDetails;
import org.infy.idp.dataapi.services.UpdateJobDetails;
import org.infy.idp.entities.jobs.DownloadArtifactInputs;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.Names;
import org.infy.idp.entities.jobs.Pipeline;
import org.infy.idp.entities.jobs.Pipelines;
import org.infy.idp.entities.jobs.SubApplication;
import org.infy.idp.entities.jobs.TestPlans;
import org.infy.idp.entities.jobs.TestSuits;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.testinfo.TestEnv;
import org.infy.idp.entities.jobs.testinfo.TestStep;
import org.infy.idp.entities.models.GitHubBrachModel;
import org.infy.idp.utils.ConfigurationManager;
import org.infy.idp.utils.JenkinsCLI;
import org.infy.idp.utils.TestPlanDetailsFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class JobsAdditionalInfo {
	protected Logger logger = LoggerFactory.getLogger(JobsAdditionalInfo.class);
	private static final String AUTHORIZATION = "Authorization";
	private static final String BASIC = "Basic ";
	private static final String APPLICATION_NAME = "Application Names : ";
	private static final String JOB_URL = "/job/";
	@Autowired
	private ConfigurationManager configmanager;
	@Autowired
	private TriggerDetailBL getTriggerDetails;
	@Autowired
	private TriggerAdditionalBL triggerAddBl;

	@Autowired
	private JobManagementDL jobManagementDL;
	@Autowired
	private JobAdditionalDetailsDL jobAddDetailDL;

	@Autowired
	private JobInfoDL jobInfoDL;
	@Autowired
	private UpdateJobDetails updateJobDetails;
	@Autowired
	private JenkinsCLI cli;
	@Autowired
	private TestPlanDetailsFetcher testPlanDetailsFetcher;
	@Autowired
	private ReleaseDetails releaseDetailsDL;

	private JobsAdditionalInfo() {
	}

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
		jobUrl.append("/artifactarchive.zip");
		String line = "";
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

	public List<String> getAllPermissionforApp(String appName, String userId) {
		logger.info("Getting All permission for appliaction");
		List<String> permissions = jobManagementDL.getBasePermission(userId);
		permissions.addAll(jobManagementDL.getPermissionForApplications(userId, appName));
		logger.info("permissions : " + permissions.toString());
		return permissions;
	}

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

	public List<String> getRolesForApp(String userId, String appName) {
		logger.debug("Getting roles");
		List<String> userRoles = jobManagementDL.getRolesasApp(userId, appName);
		logger.debug("roles : " + userRoles);
		return userRoles;
	}

	public List<String> getPermissionForApplications(String applicationName, String userName) {
		logger.debug("getting permissions");
		List<String> userPermissions = jobManagementDL.getPermissionForApplications(userName, applicationName);
		try {
			userPermissions.addAll(getPipelinePermissionforApplication(applicationName, userName));
		} catch (SQLException e) {
			logger.error("error while getting pipeline permission for application %s", e.getMessage());
		}
		logger.debug("Permissions:" + userPermissions);
		return userPermissions;
	}

	public String getStageViewUrl(String appName, String pipelineName) throws Exception {
		String url = "";
		String jenkinsUrl = configmanager.getJenkinsstageviewurl();
		String appNameWS = appName.replaceAll(" ", "%20");
		String pipelineNameWS = pipelineName.replaceAll(" ", "%20");
		String jobName = appNameWS + "_" + pipelineNameWS;
		url = jenkinsUrl + JOB_URL + jobName + JOB_URL + jobName + "_Pipeline/";
		return url;
	}

	public List<String> getAllPermission(String userId) {
		logger.info("Getting All permission");
		List<String> permissions = jobManagementDL.getBasePermission(userId);
		permissions.addAll(jobManagementDL.getPermission(userId));
		return permissions;
	}

	public Names getApplicationNameForReleaseManager(String userName, String platformName) {
		List<String> permissions = getAllPermission(userName);
		Names names = new Names();
		if (null == permissions || !permissions.contains("RELEASE")) {
			return names;
		}
		Gson gson = new Gson();
		List<String> applications = jobManagementDL.getApplicationNameForReleaseManager(userName, platformName);
		names.setNames(applications);
		logger.debug(APPLICATION_NAME + gson.toJson(names, Names.class));
		return names;
	}

	public Names pipelineNamesForApplication(String applicationName, String workflowString, String userName) {
		List<String> permissions = getAllPermission(userName);
		Names names = new Names();
		if (null == permissions || permissions.isEmpty()) {
			return names;
		}
		Gson gson = new Gson();
		List<String> pipelines = jobInfoDL.pipelineNamesForApplication(applicationName, workflowString);
		names.setNames(pipelines);
		logger.debug("pipeline Names for the application " + applicationName + " : " + gson.toJson(names, Names.class));
		return names;
	}

	public Steps fecthTriggerSteps(String appName, String pipelineName, String envName) throws SQLException {
		Steps steps = triggerAddBl.fecthTriggerSteps(appName, pipelineName, envName);
		logger.debug("Fetch trigger options");
		return steps;
	}

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
			pips = jobInfoDL.getDependencyPipelines(appName);
		} catch (SQLException e) {
			logger.error("Existing Apps Error!!\n" + e.getMessage());
		}
		Pipelines pipelines = new Pipelines();
		pipelines.setPipelines(pips);
		logger.debug("pipelines : " + gson.toJson(pipelines, Pipelines.class));
		return pipelines;
	}

	public SubApplication getSubApplications(String applicationName) {
		SubApplication subApplication = new SubApplication();
		subApplication.setSubApps(jobInfoDL.getSubAppDetails(applicationName));
		logger.debug("getting application");
		return subApplication;
	}


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
					} else if (idpjob.getCode().getScm().get(i).getType().equalsIgnoreCase("git")) {
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
						} else if (idpjob.getCode().getScm().get(i).getRepositoryBrowser().equalsIgnoreCase("github")) {
							String username = idpjob.getCode().getScm().get(i).getUserName();
							String pwd = idpjob.getCode().getScm().get(i).getPassword();
							logger.info(idpjob.getCode().getScm().toString());
							String projectUrl = idpjob.getCode().getScm().get(i).getUrl();
							String proxy = idpjob.getCode().getScm().get(i).getProxy();
							String port = idpjob.getCode().getScm().get(i).getProxyPort();
							String s[] = projectUrl.split("/");
							String repoUrl = s[0] + "//" + s[2];
							List<ArrayList<String>> branchTagList = triggerAddBl.gitHubBranchesTagsFetcher(
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
							List<ArrayList<String>> branchTagList = triggerAddBl.bitBucketbranchesTagsFetcher(repoUrl,
									username, pwd, projectUrl, proxy, port);
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
							if (!status) {
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
							if (!status) {
								list.add(false);
							}
						}
						if (i > 0 && branchOrTag.equalsIgnoreCase("tag")) {
							list.add(false);
						}
					} else if (idpjob.getCode().getScm().get(i).getType().equalsIgnoreCase("tfs")) {
						String repoUrl = idpjob.getCode().getScm().get(i).getUrl();
						String projPath = idpjob.getCode().getScm().get(i).getProjPath();
						String username = idpjob.getCode().getScm().get(i).getUserName();
						String pwd = idpjob.getCode().getScm().get(i).getPassword();
						branchList = triggerAddBl.branchFetcher(repoUrl, projPath, username, pwd);
						if (branchOrTag.equalsIgnoreCase("branch")) {
							boolean f = false;
							for (int j = 0; j < branchList.size(); j++) {
								if (branchList.get(j).equals(branchOrTagValue)) {
									list.add(true);
									f = true;
									break;
								}
							}
							if (!f) {
								list.add(false);
							}
						} else {
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

	public String getApplicationID(String applicationName) {
		Long applicationId = jobInfoDL.getApplicationId(applicationName);
		return applicationId.toString();
	}

	public String getPipelineID(String applicationName, String pipelineName) {
		Long pipelineId = null;
		try {
			pipelineId = jobInfoDL.getPipelineId(pipelineName, applicationName);
		} catch (SQLException e) {
			logger.error("Postgres Error while fetching pipelineId :", e);
		}
		return pipelineId.toString();
	}

	public List<TestPlans> fetchMTMTestPlans(String appName, String pipelineName) {
		List<TestPlans> testPlansList = new ArrayList<>();
		try {
			IDPJob idpjob = jobAddDetailDL.getPipelineInfo(appName, pipelineName);
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

	public List<TestSuits> fetchMTMTestSuits(Integer planID, String appName, String pipelineName) {
		List<TestSuits> testSuitsList = new ArrayList<>();
		try {
			IDPJob idpjob = jobAddDetailDL.getPipelineInfo(appName, pipelineName);
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

	public List<String> getPipelinePermission(String appname, String pipelineName, String userId) throws SQLException {
		return jobInfoDL.getPipelinePermission(appname, pipelineName, userId);
	}

	public List<String> getPipelinePermissionforApplication(String appname, String userId) throws SQLException {
		return jobInfoDL.getPipelinePermissionForApplication(appname, userId);
	}

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
