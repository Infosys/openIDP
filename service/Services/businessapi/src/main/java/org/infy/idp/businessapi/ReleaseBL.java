/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.businessapi;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.dataapi.services.JobAdditionalDetailsDL;
import org.infy.idp.dataapi.services.JobInfoDL;
import org.infy.idp.dataapi.services.ReleaseDetails;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.EnvironmentOwnerDetail;
import org.infy.idp.entities.models.GitHubBrachModel;
import org.infy.idp.entities.releasemanagerinfo.Release;
import org.infy.idp.entities.releasemanagerinfo.ReleaseManager;
import org.infy.idp.entities.releasemanagerinfo.ReleasePipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The class ReleaseBL contains methods related to releases of pipeline
 * 
 * @author Infosys
 */
@Component
public class ReleaseBL {

	protected Logger logger = LoggerFactory.getLogger(ReleaseBL.class);

	@Autowired
	private ReleaseDetails releaseDetails;
	@Autowired
	private JobAdditionalDetailsDL jobAddInfoDL;
	@Autowired
	private JobInfoDL jobInfoDL;
	@Autowired
	private EmailSender emailSender;
	@Autowired
	private FetchJobDetails fetchJobDetails;
	@Autowired
	private TriggerAdditionalBL triggerAdditionalBL;
	@Autowired
	private TriggerDetailBL triggerDetailBL;

	/**
	 * Returns release details for specified pipeline
	 * 
	 * @param appName
	 * @param pipelineNames
	 * @return ReleaseManager
	 * @throws SQLException
	 */
	public ReleaseManager getReleaseNumber(String appName, String pipelineNames) throws SQLException {

		ReleaseManager releaseManager = new ReleaseManager();

		if (null == appName || null == pipelineNames)
			return releaseManager;
		try {

			String[] pipelineNamesArray = pipelineNames.split(",");
			List<String> pipelineNamesList = new ArrayList<String>();
			for (String pipelineName : pipelineNamesArray) {
				pipelineNamesList.add(pipelineName);
			}

			Map<String, List<String>> releaseMap = releaseDetails.getReleaseNum(appName, pipelineNamesList);
			Set set = releaseMap.entrySet();
			Iterator releaseItr = set.iterator();

			List<ReleasePipeline> releasePipelines = new ArrayList<>();
			List<Release> releaseList = new ArrayList<>();
			Release release = null;
			while (releaseItr.hasNext()) {

				Map.Entry<String, List<String>> releaseEntry = (Map.Entry) releaseItr.next();
				String pipelineName = releaseEntry.getKey();
				List<String> releaseNumList = releaseEntry.getValue();

				ReleasePipeline releasePipeline = new ReleasePipeline();
				releasePipeline.setPipelineName(pipelineName);
				releaseList.clear();
				for (String releaseNum : releaseNumList) {
					release = new Release();
					release.setReleaseNumber(releaseNum);
					releaseList.add(release);
				}
				releasePipeline.setRelease(releaseList);
				releasePipelines.add(releasePipeline);

			}

			releaseManager.setApplicationName(appName);
			releaseManager.setReleasePipeline(releasePipelines);

			return releaseManager;

		} catch (SQLException e) {
			logger.error(e.toString(), e);
			throw e;
		}

	}

	/**
	 * 
	 * Remove Env planning
	 * Returns env slots for specified environment
	 * 
	 * @param appName
	 * @param environment
	 * @return slot
	 */
//	public Slot getEnvSlots(String appName, String environment) {
//		Slot s = releaseDetails.getEnvSlots(appName, environment);
//		return s;
//
//	}

	/**
	 * 
	 * Returns existing slots for giving app and env
	 * 
	 * @param appName
	 * @param releaseNumber
	 * @param environment
	 * @return slot
	 */
//	public Slot getExistingSlots(String appName, String releaseNumber, String environment) {
//		return releaseDetails.getExistingslots(appName, releaseNumber, environment);
//	}

	/**
	 * 
	 * @param appName
	 * @param pipelineName
	 * @param status
	 * @param username
	 * @return ReleaseManager
	 * @throws Exception
	 */
	public ReleaseManager getReleaseInfo(String appName, String pipelineName, String status, String username)
			throws Exception {

		try {

			ReleaseManager releaseManager = new ReleaseManager();
			ReleasePipeline releasePipeline = releaseDetails.getReleasePipelineInfo(appName, pipelineName, status);


			// for setting git branches

			releasePipeline.setScmBranches(getSCMBranches(appName, releasePipeline.getPipelineName()));
			releasePipeline.setScmType(getSCMType(appName, releasePipeline.getPipelineName()));

			List<String> envList = getEnvironmentList(appName);
			ApplicationInfo app = jobInfoDL.getApplication(appName);
			List<String> accessEnvList = fetchJobDetails.getUserEnvironment(app, username);
			List<ReleasePipeline> releasePipelineList = new ArrayList<>();
			releasePipelineList.add(releasePipeline);
			releaseManager.setApplicationName(appName);
			releaseManager.setEnvironmentList(envList);
			releaseManager.setAccessEnvironmentList(accessEnvList);
			releaseManager.setReleasePipeline(releasePipelineList);

			return releaseManager;

		} catch (SQLException e) {
			logger.error("SQL Exception in ReleaseBL.getReleaseInfo", e);
			throw e;
		} catch (Exception e1) {
			logger.error("common Exception in ReleaseBL.getReleaseInfo", e1);
			throw e1;
		}
	}

	/**/
	/**
	 * 
	 * @param appName
	 * @param pipelineName
	 * @return String
	 */
	private String getSCMType(String appName, String pipelineName) {

		String scmType = "";
		try {
			IDPJob idp = jobAddInfoDL.getPipelineInfo(appName, pipelineName);
			if (idp.getCode().getScm() != null && idp.getCode().getScm().size() != 0
					&& idp.getCode().getScm().get(0) != null && idp.getCode().getScm().get(0).getType() != null) {

				scmType = idp.getCode().getScm().get(0).getType();

			}
		} catch (NullPointerException e) {
			logger.error(e.getMessage(), e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return scmType;
	}

	/**
	 * 
	 * @param appName
	 * @param pipelineName
	 * @return List<String>
	 */
	private List<String> getSCMBranches(String appName, String pipelineName) {

		List<String> branches = new ArrayList<String>();

		try {
			IDPJob idp = jobAddInfoDL.getPipelineInfo(appName, pipelineName);
			if (idp.getCode().getScm() != null && idp.getCode().getScm().size() != 0
					&& idp.getCode().getScm().get(0) != null && idp.getCode().getScm().get(0).getType() != null) {

				String scmType = idp.getCode().getScm().get(0).getType();

				if (scmType.equalsIgnoreCase("git")) {

					String repoBrowser = idp.getCode().getScm().get(0).getRepositoryBrowser();
					if (repoBrowser.equalsIgnoreCase("gitlab")) {
						String repoUrl = idp.getCode().getScm().get(0).getBrowserUrl();
						String username = idp.getCode().getScm().get(0).getUserName();

						String pwd = idp.getCode().getScm().get(0).getPassword();

						String projectUrl = idp.getCode().getScm().get(0).getUrl();

						ArrayList<ArrayList<String>> branchTagList = triggerDetailBL.gitLabbranchesTagsFetcher(repoUrl,
								username, pwd, projectUrl);

						if (branchTagList != null) {
							if (branchTagList.size() != 0) {
								branches = branchTagList.get(0);
							}

						}

					}

					else if (repoBrowser.equalsIgnoreCase("github")) {

						String username = idp.getCode().getScm().get(0).getUserName();
						String pwd = idp.getCode().getScm().get(0).getPassword();
						logger.info(idp.getCode().getScm().toString());
						String projectUrl = idp.getCode().getScm().get(0).getUrl();
						String proxy = idp.getCode().getScm().get(0).getProxy();
						String port = idp.getCode().getScm().get(0).getProxyPort();

						String s[] = projectUrl.split("/");
						String repoUrl = s[0] + "//" + s[2];

						List<ArrayList<String>> branchTagList = triggerAdditionalBL.gitHubBranchesTagsFetcher(
								new GitHubBrachModel(repoUrl, username, pwd, projectUrl, proxy, port));
						if (branchTagList != null && branchTagList.size() != 0 )  {
							
								branches = branchTagList.get(0);

						}

					}

					else if (repoBrowser.equalsIgnoreCase("bitbucket")) {

						String repoUrl = idp.getCode().getScm().get(0).getBrowserUrl();

						String username = idp.getCode().getScm().get(0).getUserName();
						String pwd = idp.getCode().getScm().get(0).getPassword();
						logger.info(idp.getCode().getScm().toString());
						String projectUrl = idp.getCode().getScm().get(0).getUrl();
						String proxy = idp.getCode().getScm().get(0).getProxy();
						String port = idp.getCode().getScm().get(0).getProxyPort();
						logger.info(port);
						logger.info(idp.getCode().getScm().get(0).getProxyPort());

						List<ArrayList<String>> branchTagList = triggerAdditionalBL
								.bitBucketbranchesTagsFetcher(repoUrl, username, pwd, projectUrl, proxy, port);
						logger.info("showing branchTag");
						logger.info(branchTagList.toString());
						if (branchTagList != null && branchTagList.size() != 0) {
								branches = branchTagList.get(0);
						//	}

						}

					}

				}

			}
		} catch (NullPointerException e) {
			logger.error(e.getMessage(), e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return branches;
	}

	/**
	 * Set release pipeline details
	 * 
	 * @param release
	 * @param pipelineName
	 * @param releaseManager
	 */
	public void setReleasePipelines(Release release, String pipelineName, ReleaseManager releaseManager) {
		List<ReleasePipeline> releasePipelines = new ArrayList<ReleasePipeline>();
		List<Release> releaseList = new ArrayList<>();
		releaseList.add(release);
		String[] pipelineNames = pipelineName.split(",");
		ReleasePipeline releasePipeline = null;
		for (String pipName : pipelineNames) {
			releasePipeline = new ReleasePipeline();
			releasePipeline.setPipelineName(pipName);
			releasePipeline.setRelease(releaseList);
			releasePipelines.add(releasePipeline);
		}

		releaseManager.setReleasePipeline(releasePipelines);
	}

	/**
	 * 
	 * @param releaseManager
	 * @param release
	 * @param pipelineName
	 * @param appName
	 * @param userName
	 */
	public void sendReleaseEmail(Release release, String pipelineName, String appName, String userName) {
		try {
		String applicationTeam = "";
		String email = "";
		String[] pipelineNames = pipelineName.split(",");
		if (null != release && null != release.getAdditionalMailRecipients()) {
			if (null != release.getAdditionalMailRecipients().getApplicationTeam()
					&& !"".equals(release.getAdditionalMailRecipients().getApplicationTeam())) {
				applicationTeam = release.getAdditionalMailRecipients().getApplicationTeam();
				logger.info("ApplicationTeam: " + applicationTeam);
			}
			if (null != release.getAdditionalMailRecipients().getEmailIds()
					&& !"".equals(release.getAdditionalMailRecipients().getEmailIds())) {
				email = release.getAdditionalMailRecipients().getEmailIds();
				logger.info("emails: " + email);
			}
		}

		TriggerJobName triggerJobName = new TriggerJobName();
		for (String pipName : pipelineNames) {
			triggerJobName.setApplicationName(appName);
			triggerJobName.setPipelineName(pipName);
			if (null != release) {
				triggerJobName.setReleaseNumber(release.getReleaseNumber());
			}
			triggerJobName.setMethod("add");
			triggerJobName.setUserName(userName);

			emailSender.releaseUpdateSuccessMail(triggerJobName, userName, applicationTeam, email);
		}
		} catch (Exception e1) {
			logger.error("Common Exception in ReleaseBL.insertReleaseInfo", e1);
			
		}
	}

	/**
	 * Inserts release information for specified app and pipeline
	 * 
	 * @param appName
	 * @param pipelineName
	 * @param release
	 * @param userName
	 * @throws Exception
	 */
	public void insertReleaseInfo(String appName, String pipelineName, Release release, String userName)
			throws Exception {

		try {

			ReleaseManager releaseManager = new ReleaseManager();
			releaseManager.setApplicationName(appName);
			setReleasePipelines(release, pipelineName, releaseManager);
			insertRelease(releaseManager);
			sendReleaseEmail(release, pipelineName, appName, userName);
		} catch (SQLException e) {
			logger.error("SQLException in ReleaseBL.insertReleaseInfo", e);
			throw e;
		} catch (Exception e1) {
			logger.error("Common Exception in ReleaseBL.insertReleaseInfo", e1);
			throw e1;
		}

	}

	/**
	 * Updates release info including user details
	 * 
	 * @param releaseManager
	 * @param userName
	 * @throws Exception
	 */
	public void updateReleaseInfo(ReleaseManager releaseManager, String userName) throws Exception {

		try {
			releaseManager.setApplicationId(jobInfoDL.getApplicationId(releaseManager.getApplicationName()));
			// geting pipeline ids
			setPipelineId(releaseManager);

			updateRelease(releaseManager);
			String appName = releaseManager.getApplicationName();
			List<ReleasePipeline> releasePipelines = releaseManager.getReleasePipeline();
			ReleasePipeline releasePipeline = null;
			if (null != releasePipelines) {
				releasePipeline = releasePipelines.get(0);
				String piplineName = releasePipeline.getPipelineName();
				List<Release> releases = releasePipeline.getRelease();

				if (null != releases) {

					TriggerJobName triggerJobName = new TriggerJobName();
					triggerJobName.setApplicationName(appName);
					triggerJobName.setPipelineName(piplineName);
					triggerJobName.setUserName(userName);
					triggerJobName.setMethod("update");

					for (Release release : releases) {

						String applicationTeam = "";
						String email = "";
						if (null != release && null != release.getAdditionalMailRecipients()) {
							if (null != release.getAdditionalMailRecipients().getApplicationTeam()) {
								applicationTeam = release.getAdditionalMailRecipients().getApplicationTeam();
							}
							if (null != release.getAdditionalMailRecipients().getEmailIds()) {
								email = release.getAdditionalMailRecipients().getEmailIds();
							}
						}

						if (null != release)
							triggerJobName.setReleaseNumber(release.getReleaseNumber());

						try {
							emailSender.releaseUpdateSuccessMail(triggerJobName, userName, applicationTeam, email);
						} catch (Exception e) {
							logger.info("Email cannot be sent");
						}

					}

				}

			}
		} catch (SQLException e) {
			logger.error("SQLException in ReleaseBL.updateReleaseInfo", e);
			throw e;
		} catch (Exception e1) {
			logger.error("Common Exception in ReleaseBL.updateReleaseInfo", e1);
			throw e1;
		}

	}


	/**
	 * Updates release information
	 * 
	 * @param releaseManager
	 */
	public void updateRelease(ReleaseManager releaseManager) {
		List<ReleasePipeline> releasePipelineList = releaseManager.getReleasePipeline();
		String remarks;
		for (ReleasePipeline releasePipeline : releasePipelineList) {
			List<Release> releaseList = releasePipeline.getRelease();
			for (Release release : releaseList) {
				remarks = releaseDetails.getRemarks(release, releaseManager.getApplicationId(),
						releasePipeline.getPipelineId());
				releaseDetails.updateRelease(release, releaseManager.getApplicationId(), remarks,
						releasePipeline.getPipelineId());
			}
		}
	}

	/**
	 * Inserts release details
	 * 
	 * @param releasemanager
	 * @throws SQLException
	 */
	public void insertRelease(ReleaseManager releasemanager) throws SQLException {
		// getting application id
		releasemanager.setApplicationId(jobInfoDL.getApplicationId(releasemanager.getApplicationName()));
		// geting pipeline ids
		setPipelineId(releasemanager);
		// insert release info
		releaseDetails.insertReleaseInfo(releasemanager);
	}

	/**
	 * 
	 * @param releasemanager
	 * @throws SQLException
	 */
	public void setPipelineId(ReleaseManager releasemanager) throws SQLException {
		int releasePipelineSize = releasemanager.getReleasePipeline().size();
		for (int i = 0; i < releasePipelineSize; i++) {
			releasemanager.getReleasePipeline().get(i).setPipelineId(getPipelineId(
					releasemanager.getReleasePipeline().get(i).getPipelineName(), releasemanager.getApplicationName()));
		}
	}

	/**
	 * 
	 * Returns pipeline id for specified app and pipeline name
	 * 
	 * @param pipelineName
	 * @param applicationName
	 * @return long
	 * @throws SQLException
	 */
	public long getPipelineId(String pipelineName, String applicationName) throws SQLException {
		return jobInfoDL.getPipelineId(pipelineName, applicationName);
	}

	/**
	 * Return env list for specified app
	 * 
	 * @param appName
	 * @return
	 */
	public List<String> getEnvironmentList(String appName) {

		Application app = jobAddInfoDL.getApplicationDetail(appName);

		List<String> envList = new ArrayList<String>();

		if (null != app && null != app.getAppJson() && null != app.getAppJson().getEnvironmentOwnerDetails()) {

			List<EnvironmentOwnerDetail> environmentOwnerDetails = app.getAppJson().getEnvironmentOwnerDetails();

			for (EnvironmentOwnerDetail environmentOwnerDetail : environmentOwnerDetails) {

				if (null != environmentOwnerDetail.getEnvironmentName()) {
					envList.add(environmentOwnerDetail.getEnvironmentName());
				}
			}

		}

		return envList;

	}


}
