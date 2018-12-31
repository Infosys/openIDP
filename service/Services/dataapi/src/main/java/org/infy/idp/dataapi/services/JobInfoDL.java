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
import java.util.HashMap;
import java.util.List;

import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.Pipeline;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.code.JobParam;
import org.infy.idp.utils.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * The class ReleaseDetails contains methods related to application, pipelines
 * and trigger jobs
 * 
 * @author Infosys
 */

@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class JobInfoDL {
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	private static final Logger logger = LoggerFactory.getLogger(JobDetailsDL.class);
	private static final String WHERE_CLAUSE = " WHERE ";
	private static final String AND_CLAUSE = " AND ";
	private static final String SELECT_CLAUSE = " SELECT ";
	private static final String FROM_CLAUSE = " FROM ";
	private static final String APPLICATION_NAME = " application_name LIKE ? ";
	private static final String PIPELINE_NAME = " pipeline_name LIKE ? ";
	private static final String APPLICATION_ID = " tpipeline_info.application_id=tapplication_info.application_id ";
	private static final String ORDER_BY = " ORDER BY ";
	private static final String ACTIVE_PIPELINE = " and active = true ";
	protected static final String ERROR1 = "Postgres Error while fetching user details:";
	private static final String SUB_APPLICATION_NAME = " sub_application LIKE ? ";
	private static final String TECHNOLOGY_NAME = " and technology LIKE ? ";

	private static final String WORKFLOW = "workflow";

	/**
	 * Constructor
	 * 
	 * 
	 * @param postGreSqlDbContext the PostGreSqlDbContext
	 * 
	 */
	private JobInfoDL() {

	}
	/**
	 * Returns role id of the specified role name
	 * 
	 * 
	 * @param roleName
	 * 
	 * @return roleId
	 * 
	 */
	public Long getRoleId(String roleName) throws SQLException {

		String tableName = "troles";
		String column = " role_id ";
		Long roleId = -1l;
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE + column + FROM_CLAUSE + tableName + WHERE_CLAUSE + "role_name like ?;");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, roleName);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				roleId = rs.getLong(1);
			}
			return roleId;
		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching data from troles:", e);
			throw e;

		}

		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

	}
	/**
	 * Returns specified app info for the specified app name
	 * 
	 * @param applicationName the String
	 * 
	 * @return app the ApplicationInfo
	 */
	public ApplicationInfo getApplication(String applicationName) throws SQLException {

		logger.info("Getting application details");

		String tableName = "public.tapplication_info";
		String column = "public.tapplication_info.entity_info";
		ApplicationInfo app = new ApplicationInfo();
		Gson gson = new Gson();
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE + column + FROM_CLAUSE + tableName + WHERE_CLAUSE + APPLICATION_NAME);

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, applicationName);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				app = gson.fromJson(rs.getObject(1).toString(), ApplicationInfo.class);

			}

			return app;
		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching ApplicationInfo entity values :", e);
			throw e;

		}

		finally {
			if (rs != null)
				rs.close();
		}

	}
	/**
	 * Returns related domain name of organization of the specified user
	 * 
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public String getDomainName(String userName) {

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append("org_domain ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(" tuser_info,torg_info ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" tuser_info.org_id=torg_info.org_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(" user_id like ? ");

		queryStatement.append(";");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {
			preparedStatement.setString(1, userName);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				return rs.getString(1);
			}

		}

		catch (SQLException | NullPointerException e)
		{
			logger.error("Postgres Error while fetching permissions:", e);
		}

		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return "";

	}




	/**
	 * Returns pipeline name list for app
	 * 
	 * @param appName
	 * @param workflowString
	 * @return list of string
	 */
	public List<String> pipelineNamesForApplication(String appName, String workflowString) {

		List<String> pipelines = new ArrayList<>();
		String tableName = " tpipeline_info,tapplication_info  ";
		String column = " pipeline_name ";
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE + column);
		queryStatement.append(FROM_CLAUSE + tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" tpipeline_info.application_id= tapplication_info.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(APPLICATION_NAME);
		if (workflowString != null && workflowString.equalsIgnoreCase(WORKFLOW)) {
			queryStatement.append(AND_CLAUSE);
			queryStatement.append(" tpipeline_info.technology != 'workflow' ");

		}
		queryStatement.append(ACTIVE_PIPELINE);
		queryStatement.append(ORDER_BY);
		queryStatement.append("pipeline_name");
		queryStatement.append(";");
		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, appName);
			logger.debug(preparedStatement.toString());
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				if (null == rs.getString(1))
					continue;

				pipelines.add(rs.getString(1));
			}
			logger.debug("Pipeline names for the application " + appName + " is : " + pipelines.toString());
		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching data from tapplication_info :", e);
			return null;

		}

		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		return pipelines;

	}

	/**
	 * Returns dependent pipeline
	 * 
	 * 
	 * @param appName the String
	 * 
	 * @return pips the List<Pipeline>
	 * 
	 */

	public List<Pipeline> getDependencyPipelines(String appName) throws SQLException {

		Pipeline pipeline;
		List<Pipeline> pips = new ArrayList<>();
		Gson gson = new Gson();
		IDPJob jobJson = new IDPJob();
		String tableName = "public.tpipeline_info,public.tapplication_info ";
		String column = " pipeline_name, public.tpipeline_info.entity_info ";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("public.tapplication_info.application_id = public.tpipeline_info.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("application_name like ?");

		queryStatement.append(ACTIVE_PIPELINE);
		queryStatement.append(";");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, appName);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				pipeline = new Pipeline();
				byte[] encryptedIDP = rs.getBytes(2);
				String decryptedIDP = EncryptionUtil.decrypt(new String(encryptedIDP));
				jobJson = gson.fromJson(decryptedIDP, IDPJob.class);
				if ((jobJson.getBasicInfo().getMasterSequence() != null
						&& WORKFLOW.equalsIgnoreCase(jobJson.getBasicInfo().getMasterSequence()))
						|| !(jobJson.getCode().getTechnology().equalsIgnoreCase("dbDeployDelphix"))) {
					pipeline.setPipelineName(rs.getString("pipeline_name"));
					pipeline.setPipelineJson(gson.fromJson(decryptedIDP, IDPJob.class));
					pips.add(pipeline);
				}

			}
		} catch (SQLException | NullPointerException e) {
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
		return pips;
	}

	/**
	 * Fetch sub application for given application name.
	 * 
	 * @param the appName
	 * 
	 * @return the list of sub applications
	 * 
	 */
	public List<String> getSubAppDetails(String appName) {

		List<String> subApps = new ArrayList<>();
		String tableName = " tdbdeploy_operation as tdo, tapplication_info as tapp ";
		String columnName = "sub_application ";
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE + columnName);
		queryStatement.append(FROM_CLAUSE + tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("tdo.application_id = tapp.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(APPLICATION_NAME);
		queryStatement.append(";");
		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, appName);
			logger.debug(preparedStatement.toString());
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				if (null == rs.getString(1))
					continue;

				subApps.add(rs.getString(1));
			}
			logger.debug("Sub Applications names for the application " + appName + " is : " + subApps.toString());
		} catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching data from tdbdeploy_operation :", e);
			return null;

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		return subApps;
	}

	/**
	 * Fetch operations for Data base deployment
	 * 
	 * @param subAppName the sub application name
	 * 
	 * @return String of operations separated by semicolon(;).
	 */
	public String getDBDeployOperation(String subAppName, String appName) {
		StringBuilder subApps = new StringBuilder();
		String tableName = " tdbdeploy_operation as td, tapplication_info as ta  ";
		String columnName = " td.operations  ";
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE + columnName);
		queryStatement.append(FROM_CLAUSE + tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" ta.application_id = td.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(APPLICATION_NAME);
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(SUB_APPLICATION_NAME);
		queryStatement.append(";");
		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, appName);
			preparedStatement.setString(2, subAppName);
			logger.debug(preparedStatement.toString());
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				if (null == rs.getString(1))
					continue;
				subApps.append(rs.getString(1));
			}
			logger.debug("DB deploy operations for the sub application " + subAppName + " is : " + subApps.toString());
		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching data from tdbdeploy_operation :", e);
			return null;

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		return subApps.toString();
	}


	/**
	 * Returns pipeline id of the specified application
	 * 
	 * @param pipelineName
	 * @param applicationName
	 * 
	 * @return pipelineId
	 */

	public Long getPipelineId(String pipelineName, String applicationName) throws SQLException {
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
			logger.error("Postgres Error while fetching IDPJob entity values :", e);
			throw e;
		}

		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

	}

	
	/**
	 * Returns job parma details
	 * 
	 * @param appName
	 * @param pipelineName
	 * @return list of jobparam
	 */
	public List<JobParam> getJobParamDetails(String appName, String pipelineName) {

		String tableName = "tadditional_job_param_details";
		StringBuilder queryStatement = new StringBuilder();
		ResultSet rs = null;
		queryStatement.append(SELECT_CLAUSE + "*");
		queryStatement.append(FROM_CLAUSE + tableName);
		queryStatement.append(WHERE_CLAUSE + "pipeline_id=? ");
		List<JobParam> jobParamaList = new ArrayList<JobParam>();

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setLong(1, getPipelineId(pipelineName, appName));
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				JobParam jParam = new JobParam();
				jParam.setJobParamName(rs.getString("param_name"));
				jParam.setJobParamValue(rs.getString("param_value"));
				jParam.setJobType(rs.getString("param_type"));
				jParam.setJobParamSatic(rs.getBoolean("static"));

				jobParamaList.add(jParam);

			}

		} catch (SQLException e) {
			logger.info(e.getMessage(), e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
		}

		return jobParamaList;

	}

	/**
	 * Returns Release number and branch details for the specified pipeline
	 * 
	 * @param appName
	 * @param pipelineName
	 * @return hashmap
	 * @throws Exception
	 */
	public HashMap<String, List<String>> getReleaseNumberAndBranches(String appName, String pipelineName)
			throws Exception {
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append("release_number,branch_list ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append("public.tpipeline_info,tapplication_info,trelease_info ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("tapplication_info.application_id = tpipeline_info.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tpipeline_info.application_id = trelease_info.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tpipeline_info.pipeline_id = trelease_info.pipeline_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tapplication_info.application_name LIKE ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tpipeline_info.pipeline_name LIKE ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("status LIKE ");
		queryStatement.append(" \'on\' ");
		queryStatement.append(ORDER_BY);
		queryStatement.append("tpipeline_info.pipeline_id");

		ResultSet rs = null;

		HashMap<String, List<String>> releaseNumbersAndBranches = new HashMap<String, List<String>>();
		String releaseNumber;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, appName);
			preparedStatement.setString(2, pipelineName);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				releaseNumber = rs.getString("release_number");
				String branch_list = rs.getString("branch_list");

				List<String> branch = new ArrayList<>();

				if (null != branch_list && !branch_list.equals("")) {
					String[] branchList = branch_list.split(",");

					for (String branchname : branchList)
						branch.add(branchname);

				}
				releaseNumbersAndBranches.put(releaseNumber, branch);
			}

			return releaseNumbersAndBranches;
		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching ApplicationInfo entity values :", e);
			throw e;

		}

		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * Returns pipeline permission of the specified job
	 * 
	 * @param applicationName
	 * @param pipelineName
	 * @param userId
	 * @return list of string
	 */
	public List<String> getPipelinePermission(String applicationName, String pipelineName, String userId) {

		StringBuilder queryStatement = new StringBuilder();
		ResultSet rs = null;
		List<String> permission = new ArrayList<String>();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(
				" distinct permission_key from public.tpipeline_roles as pr join public.trole_permissions as rp on pr.role_id=rp.role_id ");
		queryStatement.append(WHERE_CLAUSE + "pr.pipeline_id=? and user_id=?");
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setLong(1, getPipelineId(pipelineName, applicationName));
			preparedStatement.setString(2, userId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				permission.add(rs.getString(1));
			}
			logger.info("applicationName " + applicationName + "/t" + pipelineName + "/t" + userId + "/t" + permission);
			return permission;
		} catch (SQLException e) {
			logger.error("Error occured while retriving permission for pipeline  : " + e.getMessage());
			return permission;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
	/**
	 * Returns application id of the specified app name
	 * 
	 * 
	 * @param applicationName the String
	 * 
	 * @return applicationId the Long
	 * 
	 */
	public Long getApplicationId(String applicationName) {

		String tableName = "tapplication_info";
		String column = " application_id ";
		Long applicationId = -1l;
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE + column + FROM_CLAUSE + tableName + WHERE_CLAUSE + APPLICATION_NAME);

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, applicationName);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				applicationId = rs.getLong(1);
			}

		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching data from tapplication_info:", e);
		}

		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		return applicationId;

	}
	/**
	 * Get permissions to the user for particular application
	 * 
	 * 
	 * @param userName        the String
	 * @param applicationName the String
	 * 
	 * @return permissions the List<String>
	 * 
	 */
	public List<String> getPermissions(String userName, String applicationName) throws SQLException {

		final String permissionKey = "permission_key";

		List<String> permissions = new ArrayList<>();
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append("DISTINCT permission_key ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(" tapplication_info,tapplication_roles,troles,trole_permissions ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" tapplication_info.application_id = tapplication_roles.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(" tapplication_roles.role_id = troles.role_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(" troles.role_id = trole_permissions.role_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("user_id like ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(APPLICATION_NAME);
		queryStatement.append(";");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, applicationName);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				permissions.add(rs.getString(permissionKey));
			}
			return permissions;
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

	}


	/**
	 * Returns app level permission of the specified user
	 * 
	 * @param applicationName
	 * @param userId
	 * @return list of string
	 */
	public List<String> getPipelinePermissionForApplication(String applicationName, String userId) {
		StringBuilder queryStatement = new StringBuilder();
		ResultSet rs = null;
		List<String> permission = new ArrayList<String>();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(
				" distinct permission_key from public.tpipeline_roles as pr join public.trole_permissions as rp on pr.role_id=rp.role_id ");
		queryStatement.append(WHERE_CLAUSE + "pr.app_id=? and user_id=?");
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setLong(1, getApplicationId(applicationName));
			preparedStatement.setString(2, userId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				permission.add(rs.getString(1));
			}
			return permission;
		} catch (SQLException e) {
			logger.error("Error occured while retriving permission for pipeline  : " + e.getMessage());
			return permission;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}


	
}
