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
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.infy.entities.artifact.Artifact;
import org.infy.entities.artifact.ArtifactDetails;
import org.infy.entities.artifact.ArtifactList;
import org.infy.entities.triggerinputs.DeployArtifact;
import org.infy.entities.triggerinputs.TriggerInputs;
import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.dataapi.services.EnvironmentDetails;
import org.infy.idp.dataapi.services.JobAdditionalDetailsDL;
import org.infy.idp.dataapi.services.JobDetailsInsertionService;
import org.infy.idp.dataapi.services.JobInfoDL;
import org.infy.idp.dataapi.services.ReleaseDetails;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.Applications;
import org.infy.idp.entities.jobs.applicationinfo.EnvironmentOwnerDetail;
import org.infy.idp.entities.jobs.applicationinfo.SlavesDetail;
import org.infy.idp.entities.nexus.ArtifactInputs;
import org.infy.idp.entities.nexus.TriggerDeployArtifact;
import org.infy.idp.entities.packagecontent.PackageContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * 
 * The class EnvironmentBL consists of method to modify env details of pipeline
 * & artifacts related methods
 * 
 * @author Infosys
 */
@Component
public class EnvironmentBL {

	private static final String APPROVED = "approved";
	@Autowired
	private EnvironmentDetails environmentDetails;
	@Autowired
	private JobInfoDL getJobDetails;

	@Autowired
	private JobAdditionalDetailsDL jobAdditionalDL;

	@Autowired
	private ReleaseDetails releaseDetails;
	@Autowired
	private JobsBL jobsBL;

	@Autowired
	private TriggerDetailBL getTriggerDetails;
	@Autowired
	private JobDetailsInsertionService jobDetailsInsertion;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	protected Logger logger = LoggerFactory.getLogger(EnvironmentBL.class);

	/**
	 * Method is used insert env details of specified app
	 * 
	 * @param appInfo
	 * @throws SQLException
	 */
	public void insertEnvironmentDetails(ApplicationInfo appInfo) throws SQLException {

		int applicationId = getJobDetails.getApplicationId(appInfo.getApplicationName()).intValue();
		int envCount = environmentDetails.getEnvCount();
		if (envCount == 0) {
			environmentDetails.insertEnvironment("ParentEnvironment", applicationId);
		}
		if (appInfo.getEnvironmentOwnerDetails() != null) {
			List<EnvironmentOwnerDetail> environmentOwnerDetails = appInfo.getEnvironmentOwnerDetails();
			for (EnvironmentOwnerDetail environmentOwnerDetail : environmentOwnerDetails) {

				environmentDetails.insertEnvironment(environmentOwnerDetail.getEnvironmentName(), applicationId);
				int environmentId = environmentDetails.getEnvironmentId(environmentOwnerDetail.getEnvironmentName(),
						applicationId);
				environmentDetails.deleteEnvironmentOwners(environmentId);
				environmentDetails.insertEnvironmentOwners(environmentId, environmentOwnerDetail.getEnvironmentOwners(),
						"DEV");
				if (environmentOwnerDetail.getdBOwners() != null)
					environmentDetails.insertEnvironmentOwners(environmentId, environmentOwnerDetail.getdBOwners(),
							"QA");

			}
		}

	}

	/**
	 * Returns Deploy artifact details based on artifact inputs
	 * 
	 * @param artifactInputs
	 * @return TriggerDeployArtifact
	 * @throws SQLException
	 */
	public TriggerDeployArtifact getArtifact(ArtifactInputs artifactInputs) throws SQLException {

		try {

			TriggerDeployArtifact triggerDeployArtifact = new TriggerDeployArtifact();

			List<DeployArtifact> nexuxArtifact = artifactInputs.getArtifactList();
			int applicationId = getJobDetails.getApplicationId(artifactInputs.getApplicationName()).intValue();
			int envId = environmentDetails.getEnvironmentId(artifactInputs.getEnvironmentName(), applicationId);
			int releaseId = releaseDetails.getReleaseId(artifactInputs.getApplicationName(),
					artifactInputs.getPipelineName(), artifactInputs.getReleaseNumber(), "on");

			int pathCount = environmentDetails.getPathCount(releaseId);

			if (pathCount == 0) {
				triggerDeployArtifact.setArtifactList(nexuxArtifact);
				return triggerDeployArtifact;
			}

			List<Integer> prevEnvIdList = environmentDetails.getParentEnvId(envId, releaseId);
			List<String> envArtifact = new ArrayList<>();
			for (int prevEnvId : prevEnvIdList) {
				if (prevEnvId != 0) {
					List<String> artifactList = environmentDetails.getArtifactList(prevEnvId, releaseId, APPROVED);
					envArtifact = ListUtils.union(envArtifact, artifactList);
				} else {
					triggerDeployArtifact.setArtifactList(nexuxArtifact);
					return triggerDeployArtifact;
				}
			}

			List<DeployArtifact> envFilterArtifact = new ArrayList<>();

			for (DeployArtifact deployArtifact : nexuxArtifact) {

				if (envArtifact.stream().anyMatch(str -> str.equalsIgnoreCase(deployArtifact.getArtifactName()))) {

					envFilterArtifact.add(deployArtifact);
				}

			}

			triggerDeployArtifact.setArtifactList(envFilterArtifact);

			return triggerDeployArtifact;

		} catch (SQLException e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		}

	}

	/**
	 * Returns artifact list based on the status of artifact
	 * 
	 * @param artifactList
	 * @return ArtifactList
	 * @throws SQLException
	 */

	public ArtifactList getArtifactList(ArtifactList artifactList) throws SQLException {

		try {

			int applicationId = getJobDetails.getApplicationId(artifactList.getApplicationName()).intValue();
			int envId = environmentDetails.getEnvironmentId(artifactList.getEnvironmentName(), applicationId);
			int releaseId = releaseDetails.getReleaseId(artifactList.getApplicationName(),
					artifactList.getPipelineName(), artifactList.getReleaseNumber(), "on");

			int pathCount = environmentDetails.getPathCount(releaseId);

			List<Artifact> importedArtifact = new ArrayList<>();
			List<Artifact> approvedArtifact = new ArrayList<>();

			List<String> importedArtifactNames;
			List<String> approvedArtifactNames;

			if (pathCount >= 1) {
				importedArtifactNames = environmentDetails.getArtifactList(envId, releaseId, "imported");
				approvedArtifactNames = environmentDetails.getArtifactList(envId, releaseId, APPROVED);

				TriggerJobName triggerJobName = new TriggerJobName();
				triggerJobName.setApplicationName(artifactList.getApplicationName());
				triggerJobName.setPipelineName(artifactList.getPipelineName());
				TriggerInputs triggerInputs = new TriggerInputs();
				IDPJob idp = jobAdditionalDL.getPipelineInfo(triggerJobName.getApplicationName(),
						triggerJobName.getPipelineName());
				ApplicationInfo app = getJobDetails.getApplication(triggerJobName.getApplicationName());

				triggerInputs = getTriggerDetails.getNexus(triggerJobName, triggerInputs, idp, app,
						triggerJobName.getPipelineName());

				List<DeployArtifact> nexusArtifact = triggerInputs.getArtifactList();

				importedArtifactNames = getIntersectionArtifact(nexusArtifact, importedArtifactNames);
				approvedArtifactNames = getIntersectionArtifact(nexusArtifact, approvedArtifactNames);

				for (String artifactName : importedArtifactNames) {

					Artifact artifact = environmentDetails.getArtifactDetails(artifactName);
					importedArtifact.add(artifact);
				}

				for (String artifactName : approvedArtifactNames) {

					Artifact artifact = environmentDetails.getArtifactDetails(artifactName);
					approvedArtifact.add(artifact);
				}

			}

			artifactList.setImportedArtifact(importedArtifact);
			artifactList.setApprovedArtifact(approvedArtifact);

			return artifactList;
		} catch (SQLException e) {
			throw e;
		}

	}

	/**
	 * Used to update artifact status
	 * 
	 * @param artifactList
	 * @throws SQLException
	 */

	public void updateArtifactStatus(ArtifactList artifactList, String user) throws SQLException {

		try {

			int applicationId = getJobDetails.getApplicationId(artifactList.getApplicationName()).intValue();
			int envId = environmentDetails.getEnvironmentId(artifactList.getEnvironmentName(), applicationId);
			int releaseId = releaseDetails.getReleaseId(artifactList.getApplicationName(),
					artifactList.getPipelineName(), artifactList.getReleaseNumber(), "on");
			List<Artifact> importedArtifact = artifactList.getImportedArtifact();
			List<Artifact> approvedArtifact = artifactList.getApprovedArtifact();

			for (Artifact artifact : importedArtifact) {

				environmentDetails.updateArtifactStatus(envId, releaseId, artifact.getArtifactName(), APPROVED);
				int artifacID = environmentDetails.getArtifactId(artifact.getArtifactName());
				ArtifactDetails artifactDetails = artifact.getArtifactDetails().get(0);
				environmentDetails.insertArtifactDetails(artifacID, artifactDetails.getStatus(),
						artifactDetails.getRemark(), artifactList.getEnvironmentName(), user);

			}
			for (Artifact artifact : approvedArtifact) {

				environmentDetails.updateArtifactStatus(envId, releaseId, artifact.getArtifactName(), "imported");
				int artifacID = environmentDetails.getArtifactId(artifact.getArtifactName());
				ArtifactDetails artifactDetails = artifact.getArtifactDetails().get(0);
				environmentDetails.insertArtifactDetails(artifacID, artifactDetails.getStatus(),
						artifactDetails.getRemark(), artifactList.getEnvironmentName(), user);
			}

		} catch (SQLException e) {
			throw e;
		}

	}

	/**
	 * Updates slave details
	 * 
	 * @param username
	 * @throws Exception
	 */

	public void updateSlave(String username) throws SQLException  {

		Applications apps = jobsBL.getExistingApps(username);
		List<Application> appList = apps.getApplications();
		for (Application app : appList) {

			ApplicationInfo appInfo = app.getAppJson();
			logger.info("Updating slave details for " + appInfo.getApplicationName());
			updateSlaveDetails(appInfo);

		}

	}

	/**
	 * update Slave details in DB for older pipelines before IDP Release 3.2.7
	 * 
	 * @param appInfo
	 * @throws SQLException
	 */
	public void updateSlaveDetails(ApplicationInfo appInfo)  {

		List<SlavesDetail> slaveDetailsList = appInfo.getSlavesDetails();
		if (slaveDetailsList != null) {
			int lenslave = slaveDetailsList.size();
			String stageOnString = "on";
			logger.info("Total slave details list size: " + lenslave);
			for (SlavesDetail slave : slaveDetailsList) {
				if (slave.getBuild() == null) {
					slave.setBuild(stageOnString);
				}
				if (slave.getDeploy() == null) {
					slave.setDeploy(stageOnString);
				}
				if (slave.getTest() == null) {
					slave.setTest(stageOnString);
				}
				String slaveLabels = slave.getLabels().trim().replaceAll(",", " ");
				logger.debug("slave Labels for slave(" + slave.getSlaveName() + "): " + slaveLabels);
				slave.setLabels(slaveLabels);
			}
			logger.debug("updating application details in DB ");
			int id = jobDetailsInsertion.updateapplicationDetail(appInfo);
			Gson gsonObj = new Gson();
			String appString = gsonObj.toJson(appInfo, ApplicationInfo.class);
			kafkaTemplate.send("idpAppDetails", id + ";" + appString);

			for (SlavesDetail slave : slaveDetailsList) {
				logger.debug("Adding slave details in DB for slave" + slave.getSlaveName());
				jobDetailsInsertion.insertSlaveDetails(slave, appInfo.getApplicationName());
			}

			logger.info(
					"All Slaves for the Application(" + appInfo.getApplicationName() + ") records are updated in DB");

		} else {
			logger.info("Slave details null for application: " + appInfo.getApplicationName());
		}

	}

	/**
	 * 
	 * @param nexusArtifact
	 * @param envArtifact
	 * @return List<String>
	 */
	public List<String> getIntersectionArtifact(List<DeployArtifact> nexusArtifact, List<String> envArtifact) {

		List<String> envFilterArtifact = new ArrayList<>();

		for (DeployArtifact deployArtifact : nexusArtifact) {

			if (envArtifact.stream().anyMatch(str -> str.equalsIgnoreCase(deployArtifact.getArtifactName()))) {

				envFilterArtifact.add(deployArtifact.getArtifactName());
			}

		}
		return envFilterArtifact;
	}

	/**
	 * 
	 * @param packageContent
	 * @throws SQLException
	 * @throws Exception
	 */
	public void insertPackageContent(PackageContent packageContent) throws SQLException {

		int artifacID = environmentDetails.getArtifactId(packageContent.getArtifactName());
		environmentDetails.updateArtifactContent(artifacID, packageContent);
	}

	/**
	 * 
	 * @param artifactName
	 * @return artifact
	 * @throws Exception
	 */
	public Artifact getLatestArtifactDetails(String artifactName) throws Exception {

		Artifact artifact = environmentDetails.getArtifactDetails(artifactName);
		PackageContent packageContent = environmentDetails.getPackageContent(artifactName);
		artifact.setPackageContent(packageContent);

		return artifact;

	}

}
