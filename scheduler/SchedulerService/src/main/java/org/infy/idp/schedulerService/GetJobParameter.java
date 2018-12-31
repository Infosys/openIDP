/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.schedulerservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.infy.idp.configure.PostGreSqlDbContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetJobParameter {

	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;

	private static final String WHERE_CLAUSE = " WHERE ";
	private static final String AND_CLAUSE = " AND ";
	private static final String SELECT_CLAUSE = " SELECT ";
	private static final String FROM_CLAUSE = " FROM ";
	private static final String APPLICATION_NAME = " application_name LIKE ? ";
	private static final String PIPELINE_NAME = " pipeline_name LIKE ? ";
	private static final String APPLICATION_ID = " tpipeline_info.application_id=tapplication_info.application_id ";
	private static final String ACTIVE_PIPELINE = " and active = true ";
	protected final String ERROR1 = "Postgres Error while fetching user details:";
	private static final String INSERT_CLAUSE = "INSERT INTO  ";
	protected Logger logger=  LoggerFactory.getLogger(GetJobParameter.class);
	@Autowired
	private ArtifactHistory artifactHistory;

	public void getJobParameter(String jobName, String buildId) throws JSONException {
		JSONObject jsonInput = null;
		
		jsonInput  = new JSONObject(jobName);
		try {
			insertTriggerHistory(jsonInput , buildId);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * method getPipelineId
	 * 
	 * @param pipelineName    the String
	 * @param applicationName the String
	 * 
	 * @return pipelineId the Long
	 */
	public Long getPipelineId(String applicationName, String pipelineName) throws SQLException {
		String tableName = "tpipeline_info,tapplication_info";
		String column = "pipeline_id";
		StringBuilder queryStatement = new StringBuilder();
		Long pipelineId = -1l;

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE + tableName);
		queryStatement.append(WHERE_CLAUSE + APPLICATION_ID);
		queryStatement.append(AND_CLAUSE + APPLICATION_NAME);
		queryStatement.append(AND_CLAUSE + PIPELINE_NAME);

		queryStatement.append(ACTIVE_PIPELINE);

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, applicationName);
			preparedStatement.setString(2, pipelineName);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				pipelineId = rs.getLong(1);

			}
			return pipelineId;
		}

		catch (SQLException | NullPointerException e) {
			throw e;

		}

		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * insert trigger history
	 * 
	 * @param userId  the String
	 * @param emailId the String
	 * @param status  the Boolean
	 * 
	 * @return Integer
	 * @throws JSONException
	 */
	public Integer insertTriggerHistory(JSONObject jsonObj, String version) throws JSONException {
		long pid = 0;
		String artifactName = "";

		try {
			pid = getPipelineId(jsonObj.getString("applicationName"), jsonObj.getString("pipelineName"));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Integer latestVersion = getMaxVersion(pid);
		ResultSet rs =null;
		if (Integer.parseInt(version) > latestVersion) {

			Integer triggerId = null;
			String tableName = "ttrigger_history";
			StringBuilder queryStatement = new StringBuilder();
			queryStatement.append(INSERT_CLAUSE + " " + tableName
					+ " (pipeline_id, trigger_entity, release_number,version) VALUES (?, cast(? as json), ?,?);");
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString(),
							new String[] { "trigger_id" })) {
				preparedStatement.setLong(1, pid);
				preparedStatement.setString(2, jsonObj.toString());
				preparedStatement.setString(3, jsonObj.getString("releaseNumber"));
				preparedStatement.setString(4, version);
				preparedStatement.executeUpdate();

				System.out.println("versionsdasd");
				rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					triggerId = (int) rs.getLong(1);
				}
				String buildObj = artifactHistory.getJobJSON(
						jsonObj.getString("applicationName") + "_" + jsonObj.getString("pipelineName"),
						"nextBuild_Pipeline");

				net.sf.json.JSONObject buildJson = net.sf.json.JSONObject.fromObject(buildObj);

				if (jsonObj.has("build"))
					artifactName = jsonObj.getString("applicationName") + "_" + jsonObj.getString("pipelineName") + "_"
							+ buildJson.getString("nextBuildNumber");
				artifactHistory.updateTriggerHistory(triggerId, artifactName);

				if (!"SapNonCharm".equals(jsonObj.getString("technology"))
						&& (jsonObj.has("deploy") || jsonObj.has("build"))) {
					artifactHistory.insertArtifact(jsonObj, buildJson, jsonObj.getString("userName"));

				}

			}

			catch (SQLException | NullPointerException e) {
				e.printStackTrace();
				return -1;
			}  finally{
		   		if(rs != null){
		   			try{
		   			rs.close();
		   			}
		   			catch(SQLException e){
		   				logger.error("Postgres Error while closing resultset :",e);
		   			}
		   		}
		   	}

			return 1;
		}
		return 1;

	}

	public Integer getMaxVersion(long pid) {
		String tableName = "ttrigger_history";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE + " max(version::int)" + " " + FROM_CLAUSE + " " + tableName + " "
				+ WHERE_CLAUSE + " pipeline_id = " + pid);
		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			rs = preparedStatement.executeQuery();
			if (rs.next() && rs.getString("max") != null) {
				Integer version = Integer.parseInt(rs.getString("max"));
				return version;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
	   		if(rs != null){
	   			try{
	   			rs.close();
	   			}
	   			catch(SQLException e){
	   				logger.error("Postgres Error while closing resultset :",e);
	   			}
	   		}
	   	}
		return -1;
	}

}
