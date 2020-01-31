/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.businessapi;

import java.util.List;

import org.infy.idp.dataapi.services.DeploymentDL;
import org.infy.idp.entities.jobs.Names;
import org.infy.idp.entities.jobs.Pipeline;
import org.infy.idp.entities.jobs.Pipelines;
import org.infy.idp.utils.TriggerBuilds;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.infy.idp.utils.OrchestratorConnector;
import org.infy.idp.entities.jobs.IDPJob;
import com.google.gson.Gson;
/**
 * The class DeploymentBL provides methods for updating jobs and pipelines
 * 
 * @author Infosys
 */
@Component
public class DeploymentBL {

	private static final String UPDATE_JOBS = "UPDATE_JOBS";
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DeploymentBL.class);

	
	@Autowired
	private JobsAdditionalInfo jobsaddInfo;
	@Autowired
	private DeploymentDL deploymentDL;
	
	@Autowired
	private OrchestratorConnector orchConn;
	
	@Autowired
	private TriggerBuilds triggerBuilds;

	/**
	 * Updates jobs for the specified user
	 * 
	 * @param userName
	 * @return String
	 */
	public String updateExistingJobs(String userName) {
		List<String> permissions = jobsaddInfo.getAllPermission(userName);
		if (!permissions.contains(UPDATE_JOBS)) {
			logger.info("No Access");

			return "NO ACCESS";
		}
		updatePipelies();
		return "SUCESS";
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean updatePipelies() {
		Pipelines pipelines = deploymentDL.getPipelineDetails();
		int pipelinesSize = pipelines.getPipelines().size();

		for (int i = 0; i < pipelinesSize; i++) {
			updateJob(pipelines.getPipelines().get(i));
		}
		return true;
	}

	/**
	 * 
	 * @param pipeList
	 * @param userName
	 * @return String
	 */
	public String updateExistingProvidedJobs(Names pipeList, String userName) {
		List<String> permissions = jobsaddInfo.getAllPermission(userName);
		logger.info("User: " + userName + " with permissions: " + permissions);
		if (!permissions.contains(UPDATE_JOBS)) {
			logger.info("No Access to update Jobs");

			return "NO ACCESS";
		}
		updateProvidedPipelies(pipeList);
		return "SUCESS";
	}

	/**
	 * 
	 * @param pipeList
	 * @return boolean
	 */
	public boolean updateProvidedPipelies(Names pipeList) {
		Pipelines pipelines = deploymentDL.getPipelineDetails();

		int pipelinesSize = pipelines.getPipelines().size();
		for (int i = 0; i < pipelinesSize; i++) {
			Pipeline pipeline = pipelines.getPipelines().get(i);
			String pipelineName = pipeline.getApplicationName() + "_" + pipeline.getPipelineName();
			if (pipeList.getNames().contains(pipelineName)) {
				logger.debug("Pipeline to update: " + pipelineName);
				updateJob(pipeline);
			}
		}
		return true;
	}

	/**
	 * 
	 * @param pipeline
	 */

	public void updateJob(Pipeline pipeline) {
		logger.info("Pipeline " + pipeline.getApplicationName() + "_" + pipeline.getPipelineName() + "_Main"
				+ " is getting updated.");
		try {
			Gson gson = new Gson();
			orchConn.sendKafkaMessage(
					"{\"action\": \"create\",\"targetplatform\":\"jenkins\",\"idpjson\":" + gson.toJson(pipeline.getPipelineJson(), IDPJob.class).toString() + ",\"jenkinsURL\":\"" + pipeline.getApplicationName() + "\"}");

		} catch (Exception e) {
			logger.info(e.getMessage());
		}

	}
}
