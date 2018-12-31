/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.dataapi.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.Names;
import org.infy.idp.entities.jobs.Pipeline;
import org.infy.idp.entities.jobs.Pipelines;
import org.infy.idp.utils.EncryptionUtil;
import org.infy.idp.utils.TriggerBuilds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * The DeploymentDL class contains method for getting pipelines related to app
 * 
 * @author Infosys
 * 
 */
@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class DeploymentDL {

	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;

	@Autowired
	private DeploymentDL deploymentDL;
	
	@Autowired
	private TriggerBuilds triggerBuilds;
	
	private static final Logger logger = LoggerFactory.getLogger(DeploymentDL.class);
	private static final String WHERE_CLAUSE = " WHERE ";
	private static final String SELECT_CLAUSE = " SELECT ";
	private static final String FROM_CLAUSE = " FROM ";
	protected static final String ERROR1 = "Postgres Error while fetching user details:";

	/**
	 *
	 * @return pipelines
	 */
	public Pipelines getPipelineDetails() {
		Pipelines pipelines = new Pipelines();
		List<Pipeline> pips = new ArrayList<>();
		Pipeline pipeline;
		Gson gson = new Gson();
		String tableName = "public.tpipeline_info,public.tapplication_info ";
		String column = " pipeline_name, application_name , public.tpipeline_info.entity_info ";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("public.tapplication_info.application_id = public.tpipeline_info.application_id ; ");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				pipeline = new Pipeline();
				pipeline.setPipelineName(rs.getString("pipeline_name"));
				pipeline.setApplicationName(rs.getString("application_name"));
				byte[] encryptedIDP = rs.getBytes(3);
				String decryptedIDP = EncryptionUtil.decrypt(new String(encryptedIDP));
				pipeline.setPipelineJson(gson.fromJson(decryptedIDP, IDPJob.class));
				pips.add(pipeline);
			}

			pipelines.setPipelines(pips);
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching user details:", e);

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		return pipelines;
	}
	
	public boolean updatePipelies() {
		Pipelines pipelines = deploymentDL.getPipelineDetails();
		int pipelinesSize = pipelines.getPipelines().size();
		
		for (int i = 0; i < pipelinesSize; i++) {
			updateJob(pipelines.getPipelines().get(i));
		}
		return true;
	}
	
	
	public void updateJob(Pipeline pipeline) {
		logger.info("Pipeline " + pipeline.getApplicationName() + "_" + pipeline.getPipelineName() + "_Main"
				+ " is getting updated.");
		try {
			triggerBuilds.buildByJobName(pipeline.getPipelineJson());
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

	}
	
	
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


}
