/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.controller.appservice;

import java.util.ArrayList;
import java.util.List;

import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.TokenUtils;
import org.infy.idp.businessapi.EmailSender;
import org.infy.idp.businessapi.JobsAdditionalInfo;
import org.infy.idp.businessapi.JobsBL;
import org.infy.idp.businessapi.JobsManagementBL;
import org.infy.idp.controller.BaseResource;
import org.infy.idp.entities.jobs.AppNames;
import org.infy.idp.entities.jobs.EnvName;
import org.infy.idp.entities.jobs.Names;
import org.infy.idp.entities.jobs.SubApplication;
import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.Applications;
import org.infy.idp.entities.jobs.applicationinfo.ReleaseNumber;
import org.infy.idp.entities.models.ResourceResponse;
import org.infy.idp.utils.EncryptUtilUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "applicationService")
public class ApplicationServices extends BaseResource {
	protected static final String modelId = "Model Id: ";
	@Autowired
	private JobsBL jobsBL;
	

	@Autowired
	private JobsAdditionalInfo jobsaddInfo;

	@Autowired
	private JobsManagementBL jobsmgmtBL;
	@Autowired
	private EmailSender emailSender;

	/** The logger. */
	protected final static Logger logger = LoggerFactory.getLogger(ApplicationServices.class);

	/**
	 * Returns environment names
	 *
	 * @param appName the String
	 * @param taskid  the String
	 * @param auth    the OAuth2Authentication
	 * @return the resource response
	 * 
	 * 
	 */

	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{id}/getEnvironmentNames", method = RequestMethod.POST)
	public ResourceResponse<String> getEnvironmentNames(@PathVariable("id") String taskid, @RequestBody String appName,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving environment names");
			Gson gson = new Gson();
			EnvName envs = jobsBL.getEnvironmentNames(appName, auth.getPrincipal().toString().toLowerCase());
			if (null == envs.getEnvNames() || envs.getEnvNames().isEmpty()) {
				resourceResponse.setResource("No access");
			} else {
				resourceResponse.setResource(gson.toJson(envs));
			}
			resourceResponse.setStatus("SUCCESS");
		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(modelId + taskid + ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Returns encrypted job
	 * 
	 * @param idp
	 * @param auth
	 * @return API exposed for encrypting and decrypting string values
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/updateRelease/encrypt", method = RequestMethod.POST)
	public ResourceResponse<String> encryptString(@RequestBody String idp, OAuth2Authentication auth) {
		String encryptedData = null;
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			encryptedData = EncryptUtilUI.encrypt(idp);
			resourceResponse.setResource(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
			resourceResponse.setResource(e.getMessage());
		}

		return resourceResponse;

	}

	/**
	 * Returns decrypted IDP job json
	 * 
	 * @param idp
	 * @param auth
	 * @return API exposed for decrypting string values
	 */

	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/updateRelease/decrypt", method = RequestMethod.POST)
	public ResourceResponse<String> decryptString(@RequestBody String idp, OAuth2Authentication auth) {
		String decryptedData = null;
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			decryptedData = EncryptUtilUI.decrypt(idp);
			resourceResponse.setResource(decryptedData);
		} catch (Exception e) {

			e.printStackTrace();
			resourceResponse.setResource(e.getMessage());
		}

		return resourceResponse;

	}

	/**
	 * Returns pipelines of the specified app
	 * 
	 * @param applicationName
	 * @param releaseNumber
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/environment/pipelines/{releaseNumber}/{applicationName}", method = RequestMethod.GET)
	public ResourceResponse<String> getpipelines(@PathVariable("applicationName") String applicationName,
			@PathVariable("releaseNumber") String releaseNumber, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			List<String> pipelineNames = new ArrayList<>();
			pipelineNames = jobsBL.getPipelines(applicationName, releaseNumber);
			if (pipelineNames.size() == 0) {
				resourceResponse.setStatus("FAILURE");
			} else {
				Gson gson = new Gson();
				EnvName env = new EnvName();
				env.setEnvNames(pipelineNames);
				resourceResponse.setResource(gson.toJson(env));
			}

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());

		}
		return resourceResponse;
	}

	/**
	 * Returns Existing Apps for Organization.
	 *
	 * @param taskid     the taskid
	 * @param jobdetails the jobdetails
	 * @param auth       the OAuth2Authentication
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "{platformName}/{id}/getExistingApps", method = RequestMethod.POST)
	public ResourceResponse<String> getExistingApps(@PathVariable String platformName,
			@PathVariable("id") String taskid, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving existing applications");
			Gson gson = new Gson();
			Applications apps = jobsBL.getExistingApps(auth.getPrincipal().toString().toLowerCase(), platformName);
			if (null == apps.getApplications() || apps.getApplications().isEmpty()) {
				resourceResponse.setResource("No Application to Show");
			} else {
				resourceResponse.setResource(gson.toJson(apps, Applications.class));
			}
			resourceResponse.setStatus("SUCCESS");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(modelId + taskid + ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Returns existing app for all organization
	 *
	 * @param auth the OAuth2Authentication
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@ApiOperation(value = "Gives all the application names for all organizations.", notes = " This method is accessible to IDP admin.This method will be used to check duplication in application names.")
	@RequestMapping(value = "/{platformName}/applications/names", method = RequestMethod.GET)
	public ResourceResponse<String> getExistingAppNames(OAuth2Authentication auth, @PathVariable String platformName) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving existing applications");
			Gson gson = new Gson();
			Applications apps = jobsBL.getExistingAppNames(auth.getPrincipal().toString().toLowerCase(), platformName);
			if (null == apps.getApplications() || apps.getApplications().isEmpty()) {
				resourceResponse.setResource("No Application to Show");
			} else {
				resourceResponse.setResource(gson.toJson(apps, Applications.class));
			}
			resourceResponse.setStatus("SUCCESS");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Returns Release info of job
	 * 
	 * @param appName
	 * @param auth
	 * @return release info
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{appName}/getReleaseInfo", method = RequestMethod.POST)
	public ResourceResponse<String> getReleaseNumber(@PathVariable("appName") String appName,
			OAuth2Authentication auth) {
		List<String> releaseNumber = new ArrayList<>();
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info(("Retrieving existing releases for the application"));
			Gson gson = new Gson();
			releaseNumber = jobsBL.getReleaseInfo(appName);
			if (releaseNumber.size() == 0) {
				resourceResponse.setResource("No Release for this application");
				resourceResponse.setStatus("FAILURE");
			} else {

				ReleaseNumber release = new ReleaseNumber();
				release.setReleaseNumbers(releaseNumber);
				resourceResponse.setResource(gson.toJson(release));
				resourceResponse.setStatus("SUCCESS");
			}

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Returns Existing application names for the organization of the logged in user
	 *
	 * @param auth the OAuth2Authentication
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@ApiOperation(value = "Gives all the application names for the organization of the logged in user.", notes = " This method is accessible to IDP admin.")
	@RequestMapping(value = "/{platformName}/org/applications/names", method = RequestMethod.GET)
	public ResourceResponse<String> getExistingAppNamesForOrg(OAuth2Authentication auth,
			@PathVariable String platformName) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving existing applications");
			Gson gson = new Gson();
			Applications apps = jobsBL.getExistingAppNames(auth.getPrincipal().toString().toLowerCase(),
					TokenUtils.getOrganization(auth), platformName);
			if (null == apps.getApplications() || apps.getApplications().isEmpty()) {
				resourceResponse.setResource("No Application to Show");
			} else {
				resourceResponse.setResource(gson.toJson(apps, Applications.class));
			}
			resourceResponse.setStatus("SUCCESS");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Creates Application
	 * 
	 * @param taskid  the String
	 * @param appInfo the ApplicationInfo
	 * @param auth    the OAuth2Authentication
	 * 
	 * @return the resource response
	 * 
	 * 
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{id}/createApplication", method = RequestMethod.POST)

	public ResourceResponse<String> createApplication(@PathVariable("id") String taskid,
			@RequestBody ApplicationInfo appInfo, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Creating Application");
			resourceResponse.setResource(jobsmgmtBL.createApplication(appInfo,
					auth.getPrincipal().toString().toLowerCase(), TokenUtils.getOrganization(auth)));
			resourceResponse.setStatus("SUCCESS");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(modelId + taskid + ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Returns application info
	 * 
	 * @param taskid
	 * @param appname
	 * @param auth
	 * @return ResourceResponse<String>
	 */

	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{id}/getAppInfo", method = RequestMethod.POST)

	public ResourceResponse<String> getAppInfo(@PathVariable("id") String taskid, @RequestBody String appname,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Gson gson = new Gson();
		try {
			logger.info("fetching Application Info");
			ApplicationInfo ap = jobsBL.getApplicationInfo(appname, auth.getPrincipal().toString().toLowerCase());
			if (null == ap.getApplicationName() || "".equalsIgnoreCase(ap.getApplicationName())) {
				resourceResponse.setResource("No Application!!");
			} else {
				resourceResponse.setResource(gson.toJson(ap, ApplicationInfo.class));
			}
			resourceResponse.setStatus("SUCCESS");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(modelId + taskid + ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Returns application info
	 * 
	 * @param taskid
	 * @param appname
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{id}/updateAppinfo", method = RequestMethod.POST)

	public ResourceResponse<String> updateAppinfo(@PathVariable("id") String taskid,
			@RequestBody ApplicationInfo appInfo, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		List<String> permission = jobsaddInfo.getAllPermission(auth.getPrincipal().toString().toLowerCase());
		if (!permission.contains("EDIT_APPLICATION")) {
			resourceResponse.setStatus("SUCCESS");
			resourceResponse.setErrorMessage("No Access");
			return resourceResponse;
		}
		try {
			logger.info("Update info");

			jobsmgmtBL.deleteRole(appInfo);
			jobsmgmtBL.updateInfo(appInfo, auth.getPrincipal().toString().toLowerCase(),
					TokenUtils.getOrganization(auth));

			resourceResponse.setResource(STATUS_SUCCESS);
			resourceResponse.setStatus("SUCCESS");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(modelId + taskid + ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * 
	 * Returns all application name
	 * 
	 * @param taskid the String
	 * @param auth   the OAuth2Authentication
	 * @return the resource response
	 * 
	 * 
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "{platform}/{id}/getApplication", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResourceResponse<String> getApplication(@PathVariable("platform") String platform,
			@PathVariable("id") String taskid, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Fetching application details");
			Gson gson = new Gson();
			AppNames apps = jobsmgmtBL.getApplications(auth.getPrincipal().toString().toLowerCase(), platform);

			if (null == apps.getApplicationNames() || apps.getApplicationNames().isEmpty()) {
				resourceResponse.setResource("No Application");
			} else {
				resourceResponse.setResource(gson.toJson(apps, AppNames.class));
			}
			resourceResponse.setStatus("SUCCESS");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * 
	 * Fetches Applications based on filter
	 * 
	 * @param taskid the String
	 * @param auth   the OAuth2Authentication
	 * @return the resource response
	 * 
	 * 
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{platform}/{id}/filter/Application/{filterString}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResourceResponse<String> getFilteredApplication(@PathVariable("platform") String platform,
			@PathVariable("id") String taskid, @PathVariable("filterString") String filterString,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving application");
			Gson gson = new Gson();
			AppNames apps = jobsmgmtBL.getFilteredApplications(filterString,
					auth.getPrincipal().toString().toLowerCase(), platform);
			if (null == apps.getApplicationNames() || apps.getApplicationNames().isEmpty()) {
				resourceResponse.setResource("No Application");
			} else {
				resourceResponse.setResource(gson.toJson(apps, AppNames.class));
			}
			resourceResponse.setStatus("SUCCESS");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Sends app creation mail
	 * 
	 * @param triggerJobName
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/applications/mail", method = RequestMethod.POST)
	public ResourceResponse<String> appCreationSuccessMail(@RequestBody TriggerJobName triggerJobName,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("application Creation Mail");

			boolean status = emailSender.appCreationSuccessMail(triggerJobName,
					auth.getPrincipal().toString().toLowerCase());

			if (status) {
				resourceResponse.setResource(STATUS_SUCCESS);
			} else {
				resourceResponse.setResource(STATUS_ERROR);
			}
			resourceResponse.setStatus("SUCCESS");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error("Model Id: " + ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * 
	 * Returns Application Details
	 * 
	 * @param taskid  the String
	 * @param appName the String
	 * @param auth    the OAuth2Authentication
	 * @return the resource response
	 * 
	 * 
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{id}/getApplicationDetails", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResourceResponse<String> getApplicationDetails(@PathVariable("id") String taskid,
			@RequestBody String appName, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving application details");
			Gson gson = new Gson();
			Application app = jobsmgmtBL.getApplicationDetails(appName, auth.getPrincipal().toString().toLowerCase());
			if (null == app.getApplicationName() || "".equalsIgnoreCase(app.getApplicationName())) {
				resourceResponse.setResource("No Application");
			} else {
				resourceResponse.setResource(gson.toJson(app, Application.class));
			}
			resourceResponse.setStatus("SUCCESS");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * 
	 * Returns Application Name For Release Manager
	 * 
	 * @param auth the OAuth2Authentication
	 * @return the resource response
	 * 
	 * 
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{platformName}/applications/release/list", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResourceResponse<String> getApplicationNameForReleaseManager(OAuth2Authentication auth,
			@PathVariable String platformName) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving application details");
			Gson gson = new Gson();
			Names names = jobsaddInfo.getApplicationNameForReleaseManager(auth.getPrincipal().toString().toLowerCase(),
					platformName);
			if (null == names.getNames() || names.getNames().isEmpty()) {
				resourceResponse.setResource("No Application");
			} else {
				resourceResponse.setResource(gson.toJson(names, Names.class));
			}
			resourceResponse.setStatus("SUCCESS");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/***
	 * Returns sub application for app
	 * 
	 * @param appName
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/getSubApplication", method = RequestMethod.GET)
	public ResourceResponse<String> subApplication(String appName, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving application");
			Gson gson = new Gson();
			SubApplication subApps = jobsaddInfo.getSubApplications(appName);

			if (null == subApps || subApps.getSubApps().isEmpty()) {
				resourceResponse.setResource("No Sub Application");
			} else {
				resourceResponse.setResource(gson.toJson(subApps, SubApplication.class));
			}
			resourceResponse.setStatus("SUCCESS");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

}
