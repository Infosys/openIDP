/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.controller.release;

import java.util.Calendar;
import java.util.TimeZone;

import org.infy.entities.artifact.Artifact;
import org.infy.entities.artifact.ArtifactList;
import org.infy.idp.businessapi.EmailSender;
import org.infy.idp.businessapi.EnvironmentBL;
import org.infy.idp.businessapi.JobsManagementBL;
import org.infy.idp.businessapi.ReleaseBL;
import org.infy.idp.controller.BaseResource;
import org.infy.idp.entities.models.ResourceResponse;
import org.infy.idp.entities.nexus.ArtifactInputs;
import org.infy.idp.entities.nexus.TriggerDeployArtifact;
import org.infy.idp.entities.packagecontent.PackageContent;
import org.infy.idp.entities.releasemanagerinfo.Release;
import org.infy.idp.entities.releasemanagerinfo.ReleaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping(value = "releaseService")
public class ReleaseService extends BaseResource {

	@Autowired
	private ReleaseBL releaseBL;
	@Autowired
	private EnvironmentBL environmentBL;


	@Autowired
	private JobsManagementBL jobsManagementBL;

	@Autowired
	private EmailSender emailSender;

	/** The logger. */
	protected final Logger logger = LoggerFactory.getLogger(ReleaseService.class);

	/**
	 * Returns release number for specified pipeline and application
	 * 
	 * @param appName
	 * @param pipelineNames
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/release/number/{application_name}/{pipeline_names}", method = RequestMethod.GET)
	public ResourceResponse<String> getReleaseNumber(@PathVariable("application_name") String appName,
			@PathVariable("pipeline_names") String pipelineNames, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("getting ReleaseNo for Pipeline");

			Gson gson = new Gson();
			ReleaseManager releaseManager = releaseBL.getReleaseNumber(appName, pipelineNames);

			resourceResponse.setStatus("SUCCESS");
			resourceResponse.setResource(gson.toJson(releaseManager, ReleaseManager.class));

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Returns release info for specified pipeline
	 * 
	 * @param appName
	 * @param pipelineName
	 * @param status
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/release/info/{application_name}/{pipeline_name}/{status}", method = RequestMethod.GET)
	public ResourceResponse<String> getReleaseInfo(@PathVariable("application_name") String appName,
			@PathVariable("pipeline_name") String pipelineName, @PathVariable("status") String status,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("getting ReleaseInfo for Pipeline");
			Gson gson = new Gson();

			ReleaseManager releaseManager = releaseBL.getReleaseInfo(appName, pipelineName, status,
					auth.getPrincipal().toString().toLowerCase());

			resourceResponse.setStatus("SUCCESS");
			resourceResponse.setResource(gson.toJson(releaseManager, ReleaseManager.class));

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Insert release details of pipeline
	 * 
	 * @param appName
	 * @param pipelineNames
	 * @param release
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/release/insert/{application_name}/{pipeline_names}", method = RequestMethod.POST)
	public ResourceResponse<String> insertReleaseDetails(@PathVariable("application_name") String appName,
			@PathVariable("pipeline_names") String pipelineNames, @RequestBody Release release,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("inserting ReleaseManager Details");

			releaseBL.insertReleaseInfo(appName, pipelineNames, release, auth.getPrincipal().toString().toLowerCase());
			resourceResponse.setStatus("SUCCESS");
			resourceResponse.setResource("Successfully inserted");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Updates release details
	 * 
	 * @param releaseManager
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/release/update", method = RequestMethod.POST)
	public ResourceResponse<String> updateReleaseDetails(@RequestBody ReleaseManager releaseManager,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("update ReleaseManager Details");

			releaseBL.updateReleaseInfo(releaseManager, auth.getPrincipal().toString().toLowerCase());
			resourceResponse.setStatus("SUCCESS");
			resourceResponse.setResource("Successfully Updated");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Returns server timezone
	 * 
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/serverTimeZone", method = RequestMethod.GET)
	public ResourceResponse<String> getServerTimeZone(OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		// get Calendar instance
		TimeZone tz = Calendar.getInstance().getTimeZone();

		resourceResponse.setStatus(tz.getID() + " (" + tz.getDisplayName() + ")");
		return resourceResponse;
	}

	/**
	 * Return artifact details
	 * 
	 * @param artifactInputs
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/getArtifactDetails", method = RequestMethod.POST)
	public ResourceResponse<String> getArtifactDetails(@RequestBody ArtifactInputs artifactInputs,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("getting ReleaseNo for Pipeline");

			Gson gson = new Gson();
			TriggerDeployArtifact triggerDeployArtifact = environmentBL.getArtifact(artifactInputs);

			resourceResponse.setStatus("SUCCESS");
			resourceResponse.setResource(gson.toJson(triggerDeployArtifact, TriggerDeployArtifact.class));

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}


	/**
	 * Updates artifact details including status of artifact
	 * 
	 * @param artifactList
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/update/artifact", method = RequestMethod.POST)
	public ResourceResponse<String> updateArtifactDetails(@RequestBody ArtifactList artifactList,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("getting ReleaseNo for Pipeline");

			environmentBL.updateArtifactStatus(artifactList, auth.getPrincipal().toString().toLowerCase());

			resourceResponse.setStatus("SUCCESS");
			resourceResponse.setResource("Updated Successfully!!!");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * 
	 * Updates slave details
	 * @param password
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/update/existing/details/{password}", method = RequestMethod.POST)
	public ResourceResponse<String> updateExistingDetails(@PathVariable("password") String password,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("getting ReleaseNo for Pipeline");

			if (password.equalsIgnoreCase("idp2018changes")) {
				environmentBL.updateSlave(auth.getPrincipal().toString().toLowerCase());

				resourceResponse.setStatus("SUCCESS");
				resourceResponse.setResource("Updated Successfully!!!");
			} else {
				resourceResponse.setStatus("FAILURE");
				resourceResponse.setResource("Password Incorrect");
			}

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * 
	 * @param packageContent
	 * @param password
	 * @return ResourceResponse<String>
	 */
	@RequestMapping(value = "/release/insert/package{password}", method = RequestMethod.POST)
	public ResourceResponse<String> insertPackageContent(@RequestBody PackageContent packageContent,
			@PathVariable("password") String password) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("inserting ReleaseManager Details");

			if (password.equals("&dummy")) {
				environmentBL.insertPackageContent(packageContent);
				resourceResponse.setStatus("SUCCESS");
				resourceResponse.setResource("Successfully inserted");
			}

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Returns all artifact version details of the specified artifact
	 * 
	 * @param artifactName
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/latest/artifact/details/{artifactname:.+}", method = RequestMethod.GET)
	public ResourceResponse<String> getLatestArtifactDetails(@PathVariable("artifactname") String artifactName,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("getting latest artifact details for artifact :: " + artifactName);

			Gson gson = new Gson();
			Artifact artifact = environmentBL.getLatestArtifactDetails(artifactName);

			resourceResponse.setStatus("SUCCESS");
			resourceResponse.setResource(gson.toJson(artifact, Artifact.class));

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	
	/**
	 * 
	 * @param artifactInputs
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/getApprovedArtifact", method = RequestMethod.POST)
	public ResourceResponse<String> getApprovedArtifact(@RequestBody ArtifactList artifactInputs,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("getting ReleaseNo for Pipeline");

			Gson gson = new Gson();
			ArtifactList artifactList = environmentBL.getArtifactList(artifactInputs);

			resourceResponse.setStatus("SUCCESS");
			resourceResponse.setResource(gson.toJson(artifactList, ArtifactList.class));

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}
}
