/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.controller.jobservice;

import java.io.IOException;
import java.util.List;

import org.infy.entities.triggerinputs.Steps;
import org.infy.entities.triggerinputs.TriggerInputs;
import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.businessapi.EmailSender;
import org.infy.idp.businessapi.JobsAdditionalInfo;
import org.infy.idp.businessapi.JobsBL;
import org.infy.idp.businessapi.JobsManagementBL;
import org.infy.idp.businessapi.SubscriptionBL;
import org.infy.idp.controller.BaseResource;
import org.infy.idp.entities.jobs.DownloadArtifactInputs;
import org.infy.idp.entities.jobs.History;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.JobsBuilds;
import org.infy.idp.entities.jobs.Names;
import org.infy.idp.entities.jobs.Pipeline;
import org.infy.idp.entities.jobs.Pipelines;
import org.infy.idp.entities.jobs.TestPlans;
import org.infy.idp.entities.jobs.TestSuits;
import org.infy.idp.entities.jobs.UserRolesPermissions;
import org.infy.idp.entities.jobs.applicationinfo.Applications;
import org.infy.idp.entities.jobs.code.JobParam;
import org.infy.idp.entities.models.ResourceResponse;
import org.infy.idp.entities.triggerparameter.ApproveBuildParams;
import org.infy.idp.entities.triggerparameter.TriggerParameters;
import org.infy.idp.utils.EncryptUtilUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

@RestController
@RequestMapping(value = "jobService")
public class JobService extends BaseResource {
	protected static final String MODELID = "Model Id: ";
	private static final String FAILURE = "FAILURE";
	private static final String SUCCESS = "SUCCESS";
	private static final String NOACCESS = "No Access";

	@Autowired
	private JobsBL jobsBL;
	@Autowired
	private JobsManagementBL jobsmgmtBL;
	@Autowired
	private JobsAdditionalInfo jobsaddInfo;
	@Autowired
	private EmailSender emailSender;
	@Autowired
	private SubscriptionBL subscriptionBL;

	/** The logger. */
	protected final Logger logger = LoggerFactory.getLogger(JobService.class);

	/**
	 * Submits job.
	 *
	 * @param taskid     the taskid
	 * @param jobdetails the jobdetails
	 * @param idp        the IDPJob
	 * @param auth       the OAuth2Authentication
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{platformName}/{id}/submitJob", method = RequestMethod.POST)
	public ResourceResponse<String> submitJob(@PathVariable("id") String taskid,
			@PathVariable("platformName") String platformName, @RequestBody String idp, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			String decryptedString = EncryptUtilUI.decrypt(idp);

			logger.info("Submitting Job");
			IDPJob idpData = new IDPJob();

			GsonBuilder gb = new GsonBuilder();
			gb.registerTypeAdapter(Integer.class, new TypeAdapter<Integer>() {

				@Override
				public Integer read(JsonReader reader) throws IOException {
					if (reader.peek() == JsonToken.NULL) {
						reader.nextNull();
						return null;
					}
					String stringValue = reader.nextString();
					try {
						Integer value = Integer.valueOf(stringValue);
						return value;
					} catch (NumberFormatException e) {
						return null;
					}
				}

				@Override
				public void write(JsonWriter writer, Integer value) throws IOException {
					if (value == null) {
						writer.nullValue();
						return;
					}
					writer.value(value);
				}

			});

			Gson g = gb.create();

			idpData = g.fromJson(decryptedString, IDPJob.class);
			logger.info(idpData.toString());
			String status = jobsBL.submitJob(idpData, auth.getPrincipal().toString().toLowerCase());
			resourceResponse.setStatus(SUCCESS);
			resourceResponse.setResource(status);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(MODELID + taskid + ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Get jobs.
	 *
	 * @param taskid          the taskid
	 * @param applicationName the String
	 * @param appName         the String
	 * @param auth            the OAuth2Authentication
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{platform}/{id}/getJobs", method = RequestMethod.POST)
	public ResourceResponse<String> getJobs(@PathVariable("platform") String platform,
			@PathVariable("id") String taskid, @RequestBody String appName, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving job details");
			Gson gson = new Gson();

			Pipelines pips = jobsBL.getpipelinesByAppNameAndUser(appName, auth.getPrincipal().toString().toLowerCase());
			String str = gson.toJson(pips, Pipelines.class);
			String encrypted = "";
			if (platform.equalsIgnoreCase("IDP")) {
				encrypted = EncryptUtilUI.encrypt(str);
			} else {

				encrypted = str;
			}
			if (null == pips.getPipelines() || pips.getPipelines().isEmpty()) {
				resourceResponse.setResource("No pipeline to show");
			} else {
				resourceResponse.setResource(encrypted);
			}
			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(MODELID + taskid + ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Approve/Reject jobs.
	 *
	 * @param triggerParameters the TriggerParameters
	 * @param auth              the OAuth2Authentication
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{id}/approveJobs", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })

	public ResourceResponse<String> approveJobs(@RequestBody ApproveBuildParams approveBuildParams,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Triggering job");
			Gson gson = new Gson();
			logger.info("Input trigger parameters" + gson.toJson(approveBuildParams));

			jobsBL.apprRejectJobs(approveBuildParams, auth.getPrincipal().toString().toLowerCase());

			resourceResponse.setStatus(SUCCESS);
			resourceResponse.setResource(STATUS_SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;

	}

	/**
	 * Returns Existing application
	 *
	 * @param taskid     the taskid
	 * @param jobdetails the jobdetails
	 * @param auth       the OAuth2Authentication
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "{platformName}/{id}/getExistingApps", method = RequestMethod.POST)
	public ResourceResponse<String> getExistingApps(@PathVariable("platformName") String platformName,
			@PathVariable("id") String taskid, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving existing applications");
			Gson gson = new Gson();
			Applications apps = jobsBL.getExistingApps(auth.getPrincipal().toString().toLowerCase(), platformName);
			if (null == apps.getApplications() || apps.getApplications().isEmpty())
				resourceResponse.setResource("No Application found!!");
			else
				resourceResponse.setResource(gson.toJson(apps, Applications.class));
			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(MODELID + taskid + ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Triggers jobs
	 *
	 * @param triggerParameters the TriggerParameters
	 * @param auth              the OAuth2Authentication
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{id}/triggerJobs", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })

	public ResourceResponse<String> triggerJobs(@RequestBody TriggerParameters triggerParameters,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Triggering job");
			Gson gson = new Gson();
			logger.info("Input trigger parameters" + gson.toJson(triggerParameters));

			jobsBL.triggerJobs(triggerParameters, auth.getPrincipal().toString().toLowerCase());

			resourceResponse.setStatus(SUCCESS);
			resourceResponse.setResource(STATUS_SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;

	}

	/**
	 * Submits job with interval
	 *
	 * @param triggerInterval the TriggerInterval
	 * @param auth            the OAuth2Authentication
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/trigger/{applicationName}/{pipelineName}/{userName}/triggerInterval", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })

	public ResourceResponse<String> triggerInterval(
			@RequestBody org.infy.idp.entities.jobs.basicinfo.TriggerInterval triggerInterval,@PathVariable("applicationName") String applicationName,@PathVariable("pipelineName") String pipelineName,@PathVariable("userName")String userName,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Submitting Job");

			String status = jobsBL.submitInterval(triggerInterval,applicationName,pipelineName,userName,auth.getPrincipal().toString().toLowerCase());
			resourceResponse.setStatus("SUCCESS");
			resourceResponse.setResource(status);

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
		}
		return resourceResponse;
	}

	/**
	 * Checks Available Jobs To Trigger
	 * 
	 * @param auth the OAuth2Authentication
	 * @return the resource response
	 * 
	 */

	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/checkAvailableJobsToTrigger", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResourceResponse<String> checkAvailableJobsToTrigger(OAuth2Authentication auth,
			@RequestBody String platformName) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Checking available jobs to trigger");
			Gson gson = new Gson();

			History history = jobsmgmtBL.checkAvailableJobsToTrigger(auth.getPrincipal().toString().toLowerCase(),
					platformName);
			if (null == history.getPipelineDetails() || history.getPipelineDetails().isEmpty()) {
				resourceResponse.setResource("No pipelines to trigger");
			} else {
				resourceResponse.setResource(gson.toJson(history, History.class));
			}
			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.getMessage());
		}
		return resourceResponse;
	}

	/**
	 * Returns inputs to trigger
	 *
	 * @param tiggerJobName the TriggerJobName
	 * @param auth          the OAuth2Authentication
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{id}/TriggerInputs", method = RequestMethod.POST, consumes = {
			MediaType.APPLICATION_JSON_VALUE })

	public ResourceResponse<String> triggerInputs(@RequestBody TriggerJobName tiggerJobName,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving inputs to trigger");
			Gson gson = new Gson();
			TriggerInputs triggerInputs = jobsmgmtBL.fecthTriggerOptions(tiggerJobName);
			resourceResponse.setResource(gson.toJson(triggerInputs, TriggerInputs.class));
			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;

	}

	/**
	 * Returns number of builds of job
	 *
	 * @param taskid  the taskid
	 * @param jobName the TriggerJobName
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{id}/getNumberOfBuilds", method = RequestMethod.POST)
	public ResourceResponse<String> getNumberOfBuilds(@PathVariable("id") String taskid,
			@RequestBody TriggerJobName jobName, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("Retrieving no of builds");
			Gson gson = new Gson();
			JobsBuilds jobsBuilds = jobsmgmtBL.getBuildJobs(jobName);
			if (null == jobsBuilds.getJobName() || jobsBuilds.getJobName().isEmpty()) {
				resourceResponse.setResource(NOACCESS);
			} else {
				resourceResponse.setResource(gson.toJson(jobsBuilds, JobsBuilds.class));
			}
			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(MODELID + taskid + ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * 
	 * Returns Pipeline Details
	 * 
	 * @param taskid        the String
	 * @param tiggerJobName the TriggerJobName
	 * @param auth          the OAuth2Authentication
	 * @return the resource response
	 * 
	 * 
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "{platform}/{id}/getPipelineDetails", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResourceResponse<String> getPipelineDetails(@PathVariable("id") String taskid,
			@PathVariable("platform") String platform, @RequestBody TriggerJobName tiggerJobName,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving pipeline details");
			Gson gson = new Gson();
			Pipeline pipeline = jobsmgmtBL.getPipelineDetails(tiggerJobName);
			String str = gson.toJson(pipeline, Pipeline.class);
			String encrypted = "";
			if (platform.equalsIgnoreCase("IDP"))
				encrypted = EncryptUtilUI.encrypt(str);
			else
				encrypted = str;
			if (null == pipeline.getPipelineName() || "".equalsIgnoreCase(pipeline.getPipelineName()))
				resourceResponse.setResource(NOACCESS);
			else {
				resourceResponse.setResource(encrypted);
			}
			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Returns download url of artifacts
	 *
	 * @param taskid  the taskid
	 * @param jobName the String
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{id}/downloadArtifact", method = RequestMethod.POST)
	public ResourceResponse<String> downloadArtifact(@PathVariable("id") String taskid,
			@RequestBody DownloadArtifactInputs downloadArtifactsInputs, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Downloading artifacts");
			String response = jobsaddInfo.downloadArtifacts(downloadArtifactsInputs);
			resourceResponse.setStatus(SUCCESS);
			resourceResponse.setResource(response);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error("Model Id: " + taskid + ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Deletes Pipeline
	 *
	 * @param taskid        the taskid
	 * @param tiggerJobName the TiggerJobName
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{id}/deletePipeline", method = RequestMethod.POST)
	public ResourceResponse<String> deletePipeline(@PathVariable("id") String taskid,
			@RequestBody TriggerJobName tiggerJobName, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Downloading artifacts");
			boolean status = jobsaddInfo.deletePipeline(tiggerJobName);

			if (status) {
				resourceResponse.setResource(STATUS_SUCCESS);
			} else {
				resourceResponse.setResource(STATUS_ERROR);
			}
			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error("Model Id: " + taskid + ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Sends job creation success mail
	 *
	 * @param taskid   the taskid
	 * @param mailBody the String
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{id}/jobCreationSuccessMail", method = RequestMethod.POST)
	public ResourceResponse<String> jobCreationSuccessMail(@PathVariable("id") String taskid,
			@RequestBody TriggerJobName triggerJobName, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Job Creation Mail");
			boolean status = emailSender.jobCreationSuccessMail(triggerJobName,
					auth.getPrincipal().toString().toLowerCase());

			if (status) {
				resourceResponse.setResource(STATUS_SUCCESS);
			} else {
				resourceResponse.setResource(STATUS_ERROR);
			}
			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error("Model Id: " + taskid + ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Returns pipeline names for application
	 *
	 * @param appName the String
	 * 
	 * @return the resource response
	 */

	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/pipelines/list/{application_name}/{workflow_string}", method = RequestMethod.GET)
	public ResourceResponse<String> getPipelineNamesForApplication(@PathVariable("application_name") String appName,
			@PathVariable("workflow_string") String workflowString, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("getting pipeline list for application  " + appName);
			Gson gson = new Gson();
			Names names = jobsaddInfo.pipelineNamesForApplication(appName, workflowString,
					auth.getPrincipal().toString().toLowerCase());
			if (null == names || null == names.getNames() || names.getNames().isEmpty())
				resourceResponse.setResource("No Pipelines");
			else
				resourceResponse.setResource(gson.toJson(names, Names.class));

			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Returns stage view url of application
	 * 
	 * @param appName
	 * @param pipelineName
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/stageview/url/{application_name}/{pipeline_name}", method = RequestMethod.GET)
	public ResourceResponse<String> getStageViewUrl(@PathVariable("application_name") String appName,
			@PathVariable("pipeline_name") String pipelineName, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("getting stageviewUrl");

			String url = jobsaddInfo.getStageViewUrl(appName, pipelineName);
			resourceResponse.setStatus(SUCCESS);
			resourceResponse.setResource(url);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Return dependent jobs
	 * 
	 * @param taskid
	 * @param appName
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{id}/getDependencyJobs", method = RequestMethod.POST)
	public ResourceResponse<String> getDependencyJobs(@PathVariable("id") String taskid, @RequestBody String appName,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving job details");
			Gson gson = new Gson();

			Pipelines pips = jobsaddInfo.getDependencyPipelines(appName, auth.getPrincipal().toString().toLowerCase());
			String str = gson.toJson(pips, Pipelines.class);
			String encrypted = EncryptUtilUI.encrypt(str);
			if (null == pips.getPipelines() || pips.getPipelines().isEmpty()) {
				resourceResponse.setResource("No pipeline to show");
			} else {
				resourceResponse.setResource(encrypted);
			}
			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(MODELID + taskid + ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Returns trigger steps for pipeline of the specified env
	 *
	 * @param appName the Application Name
	 * @param envName the Environment Selected
	 * @param auth    the OAuth2Authentication
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/trigger/steps/{application_name}/{pipeline_name}/{env_name}", method = RequestMethod.GET)

	public ResourceResponse<String> triggerSteps(@PathVariable("application_name") String appName,
			@PathVariable("pipeline_name") String pipelineName, @PathVariable("env_name") String envName,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving STEPS to trigger");
			Gson gson = new Gson();
			Steps steps = jobsaddInfo.fecthTriggerSteps(appName, pipelineName, envName);

			resourceResponse.setResource(gson.toJson(steps, Steps.class));
			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;

	}

	/**
	 * Return job parameter details
	 *
	 * @param appName      the Application Name
	 * @param pipelineName the Pipeline Name
	 * @param auth         the OAuth2Authentication
	 * @return the resource response
	 */

	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/getJobParmDetails/{application_name}/{pipeline_name}", method = RequestMethod.GET)
	public ResourceResponse<String> getJobParamDetails(@PathVariable("application_name") String appName,
			@PathVariable("pipeline_name") String pipelineName, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving JobParameter details of pipeline");
			Gson gson = new Gson();
			List<JobParam> jobparamList = jobsmgmtBL.getJobParamDetails(appName, pipelineName);

			resourceResponse.setResource(gson.toJson(jobparamList));
			resourceResponse.setStatus(SUCCESS);
		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;

	}

	/**
	 * Returns MTM Test Plans
	 *
	 * 
	 * 
	 * @return the resource response
	 */

	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/testPlan/{application_name}/{pipeline_name}", method = RequestMethod.GET)
	public ResourceResponse<String> getMTMTestPlans(@PathVariable("application_name") String appName,
			@PathVariable("pipeline_name") String pipelineName, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("getting Test Plans  ");
			Gson gson = new Gson();
			List<TestPlans> testPlansList = jobsaddInfo.fetchMTMTestPlans(appName, pipelineName);
			resourceResponse.setResource(gson.toJson(testPlansList));
			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Returns MTM Test suits
	 *
	 * @param testPlanId the Integer
	 * 
	 * @return the resource response
	 */

	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/testPlan/{test_plan_id}/{application_name}/{pipeline_name}", method = RequestMethod.GET)
	public ResourceResponse<String> getMTMTestSuits(@PathVariable("test_plan_id") Integer testPlanId,
			@PathVariable("application_name") String appName, @PathVariable("pipeline_name") String pipelineName,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("getting Test Suit  ");
			Gson gson = new Gson();
			List<TestSuits> testSuitList = jobsaddInfo.fetchMTMTestSuits(testPlanId, appName, pipelineName);
			logger.info("MytestPlans:-----" + gson.toJson(testSuitList));
			resourceResponse.setResource(gson.toJson(testSuitList));

			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * getNumberOfBuilds.
	 *
	 * @param taskid  the taskid
	 * @param jobName the TriggerJobName
	 * @return the resource response
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/pairName", method = RequestMethod.POST)
	public ResourceResponse<String> getPairName(@RequestBody TriggerJobName jobName, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info("getPairNames");
			Gson gson = new Gson();
			jobName.setUserName(auth.getPrincipal().toString().toLowerCase());
			Names names = jobsmgmtBL.getPairName(jobName);
			resourceResponse.setResource(gson.toJson(names));
			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * 
	 * @param applicationName
	 * @param pipelineName
	 * @param userName
	 * @param auth
	 * @return ResourceResponse<String>
	 */

	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/pipelinepermission", method = RequestMethod.POST)
	public ResourceResponse<String> getPipelineRoles(String applicationName, String pipelineName, String userName,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();

		try {
			logger.info(
					"Retrieving PipelinePermission details of pipeline " + pipelineName + applicationName + userName);
			Gson gson = new Gson();
			UserRolesPermissions user = new UserRolesPermissions();
			user.setUserId(auth.getPrincipal().toString().toLowerCase());
			// user.setRoles(userRoles);

			List<String> pipelinePermissions = jobsaddInfo.getPipelinePermission(applicationName, pipelineName, userName);
			user.setPermissions(pipelinePermissions);
			String encrypted = EncryptUtilUI.encrypt(gson.toJson(user, UserRolesPermissions.class));
			resourceResponse.setResource(encrypted);
			resourceResponse.setStatus(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * 
	 * @param idp
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/updateSubScriptionList", method = RequestMethod.POST)
	public ResourceResponse<String> updateSubScriptionList(@RequestBody String idp, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			subscriptionBL.updateSubScriptionList(idp);
			resourceResponse.setStatus(SUCCESS);
			resourceResponse.setResource(SUCCESS);

		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

}
