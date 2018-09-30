/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.controller.deploymentservice;

import org.infy.idp.businessapi.DeploymentBL;
import org.infy.idp.controller.BaseResource;
import org.infy.idp.entities.jobs.Names;
import org.infy.idp.entities.models.ResourceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;


@RestController
@RequestMapping(value = "deploymentService")
public class DeploymentService extends BaseResource {
	@Autowired
	private DeploymentBL deploymentBL;

	/** The logger. */
	protected final static Logger logger = LoggerFactory.getLogger(DeploymentService.class);

	/**
	 * Updates existing job details
	 * 
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/update/jobs", method = RequestMethod.POST)
	public ResourceResponse<String> updateExistingJobs(OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("updating existing jobs");
			deploymentBL.updateExistingJobs(auth.getPrincipal().toString());
			resourceResponse.setStatus("SUCCESS");
		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * Updates multiple pipeline jobs
	 * 
	 * @param pipelineJson
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/update/given/jobs", method = RequestMethod.POST)
	public ResourceResponse<String> updateExistingProvidedJobs(@RequestBody String pipelineJson,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("updating existing jobs");
			Gson g = new Gson();
			Names pipeList = new Names();
			pipeList = g.fromJson(pipelineJson, Names.class);
			logger.info("Pipeline List to update: " + pipeList.getNames().toString());
			String status = deploymentBL.updateExistingProvidedJobs(pipeList, auth.getPrincipal().toString());
			resourceResponse.setStatus(status);
		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

}
