/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.triggerparameter.TriggerParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.FolderJob;
import com.offbytwo.jenkins.model.JobWithDetails;

@Component
public class TriggerBuilds {
	private static final Logger logger = LoggerFactory.getLogger(TriggerBuilds.class);

	@Autowired
	private ConfigurationManager configmanager;

	private TriggerBuilds() {

	}

	/**
	 * Builds the by job name.
	 *
	 * @param idpjson the idpjson
	 */
	public void buildByJobName(IDPJob idp) {
		JenkinsServer jenkins;
		try {
			String jenkinsURL = configmanager.getJenkinsurl();
			String userName = configmanager.getJenkinsuserid();
			String password = configmanager.getJenkinspassword();
			jenkins = new JenkinsServer(new URI(jenkinsURL), userName, password);
			HashMap<String, String> parameters = new HashMap<>();
			Gson gson = new Gson();
			parameters.put("JSON_Input", gson.toJson(idp));
			StringBuilder jobName = new StringBuilder();
			jobName.append(idp.getBasicInfo().getApplicationName());
			jobName.append("_");
			jobName.append(idp.getBasicInfo().getPipelineName());
			jobName.append("_Main");
			JobWithDetails job = jenkins.getJob(jobName.toString());
			job.build(parameters);
		} catch (NullPointerException ex) {
			logger.info(ex.getMessage(), ex);
		} catch (URISyntaxException ex) {
			logger.info(ex.getMessage(), ex);

		} catch (IOException ex) {
			logger.info(ex.getMessage(), ex);
			throw new IllegalStateException("Backend Server is down", ex);
		}

	}

	/**
	 * Build job for trigger parameter
	 * 
	 * @param idp
	 */
	public void buildByJobName(TriggerParameters idp) {
		JenkinsServer jenkins;
		try {
			String jenkinsURL = configmanager.getJenkinsurl();
			String userName = configmanager.getJenkinsuserid();
			String password = configmanager.getJenkinspassword();
			jenkins = new JenkinsServer(new URI(jenkinsURL), userName, password);
			HashMap<String, String> parameters = new HashMap<>();
			Gson gson = new Gson();

			if (null != idp.getBuild()) {
				idp.getBuild().setBranchSelected("master");

			}
			parameters.put("JSON_Input", gson.toJson(idp));

			logger.info("Pipeline Job to be triggered Name : " + idp.getApplicationName() + "_" + idp.getPipelineName()
					+ "_Pipeline");
			FolderJob folder = new FolderJob(idp.getApplicationName() + "_" + idp.getPipelineName(),
					jenkinsURL + "/job/" + idp.getApplicationName() + "_" + idp.getPipelineName() + "/");
			JobWithDetails job = jenkins.getJob(folder,
					idp.getApplicationName() + "_" + idp.getPipelineName() + "_Pipeline");
			logger.info("Pipeline Job to be triggered Name : " + idp.getApplicationName() + "_" + idp.getPipelineName()
					+ "_Pipeline");
			logger.info("Debug Trigger Parameters:" + parameters);
			job.build(parameters);
		} catch (IOException | URISyntaxException e) {
			throw new IllegalStateException("Jenkins Server is down");
		}

	}

}
