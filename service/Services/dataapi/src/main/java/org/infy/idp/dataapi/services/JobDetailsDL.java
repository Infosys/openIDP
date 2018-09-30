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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.infy.entities.triggerinputs.DeployArtifact;
import org.infy.entities.triggerinputs.ReleaseTransportInfo;
import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.entities.getjob.GetJob;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.Pipeline;
import org.infy.idp.entities.jobs.PipelineDetail;
import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.SlavesDetail;
import org.infy.idp.entities.jobs.code.JobParam;
import org.infy.idp.entities.jobs.common.Notification;
import org.infy.idp.entities.triggerparameter.TriggerParameters;
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
public class JobDetailsDL {
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	@Autowired
	private JobDetailsDL getJobDetails;

	private static final Logger logger = LoggerFactory.getLogger(JobDetailsDL.class);
	private static final String WHERE_CLAUSE = " WHERE ";
	private static final String AND_CLAUSE = " AND ";
	private static final String SELECT_CLAUSE = " SELECT ";
	private static final String FROM_CLAUSE = " FROM ";
	private static final String APPLICATION_NAME = " application_name LIKE ? ";
	private static final String PIPELINE_NAME = " pipeline_name LIKE ? ";
	private static final String USER_ID = " user_id LIKE ? ";
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
	private JobDetailsDL() {

	}

	/**
	 * Returns updateFlag of slave
	 * 
	 * @param slave
	 * @param appName
	 * @return boolean
	 */
	public boolean getUpdateFlag(SlavesDetail slave, String appName) {
		ResultSet rs = null;
		boolean updateFlag = false;

		String tableName = "tslave_detials";
		StringBuilder queryStatementSelect = new StringBuilder();
		queryStatementSelect.append(SELECT_CLAUSE + " count(*) from " + tableName + " " + WHERE_CLAUSE
				+ " application_id =? and slave_name =? ");
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatementSelect.toString())) {
			preparedStatement.setLong(1, getJobDetails.getApplicationId(appName));
			preparedStatement.setString(2, slave.getSlaveName());
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				updateFlag = (rs.getInt(1) > 0) ? true : false;
				logger.debug("Existing record(s) for slave:" + slave.getSlaveName() + " application:" + appName
						+ " in tslave_detials:" + updateFlag);
			}

		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the data in tslave_detials:", e);

		} finally {
			if (null != rs) {
				try {
					rs.close();
				} catch (Exception e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
		}

		logger.debug("Update flag for existing record(s) in tslave_detials:" + updateFlag);
		return updateFlag;
	}

	/**
	 * Returns app id of the specified app name
	 * 
	 * @param appName
	 * @return integer
	 */
	public Integer getAppId(String appName) {

		String tableName = "tapplication_info";
		String column = "application_id";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("application_name like ?");
		queryStatement.append(";");
		ResultSet rs = null;
		Integer appId = null;
		logger.info("query statement " + queryStatement);
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			logger.info("inside try of get app id");
			preparedStatement.setString(1, appName);
			logger.info("after set of get app id");
			rs = preparedStatement.executeQuery();
			logger.info("after execution get app id");
			while (rs.next()) {
				appId = rs.getInt(1);

			}

		} catch (SQLException e) {

			logger.info(e.getMessage(), e);

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		return appId;
	}

	/**
	 * Return pipeline name list of specified app
	 * 
	 * @param applicationName
	 * @param releaseNumber
	 * @return list of string
	 */
	public List<String> getPipelines(String applicationName, String releaseNumber) {

		List<String> pipelineNames = new ArrayList<>();
		String query = "select pipeline_name from trelease_info,tapplication_info,tpipeline_info where trelease_info.application_id=tapplication_info.application_id "
				+ " AND tpipeline_info.pipeline_id =trelease_info.pipeline_id " + " AND application_name like ? "
				+ " AND release_number like ? ;";

		logger.info(query);
		ResultSet rs3 = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setString(1, applicationName);
			preparedStatement.setString(2, releaseNumber);
			rs3 = preparedStatement.executeQuery();
			while (rs3.next()) {
				logger.info(rs3.getString(1));
				pipelineNames.add(rs3.getString(1));
			}
		} catch (SQLException e) {

			logger.info(e.getMessage(), e);
		} finally {
			if (rs3 != null) {
				try {
					rs3.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		logger.info(pipelineNames.toString());

		return pipelineNames;
	}

	/**
	 * 
	 * Removes delete plan
	 * 
	 * @param releaseNumber
	 * @param applicationName
	 * @param env
	 */
	public void deletePlan(String releaseNumber, String applicationName, String env) {
		logger.info("inside delete plan function");
		String tableName = "tenvironment_planning";
		StringBuilder queryStatement = new StringBuilder();

		String query = "delete from " + tableName + " where release_no=" + "'" + releaseNumber + "'"
				+ " and application_name=" + "'" + applicationName + "'" + " and environment=" + "'" + env + "'";
		ResultSet rs = null;
		queryStatement.append(query);
		logger.info("query statement for delete " + queryStatement);
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			logger.info("inside try of delete");
			rs = preparedStatement.executeQuery();

		} catch (SQLException e) {

			logger.info(e.getMessage(), e);
		} finally {
			try {
				if (null != rs) {
					rs.close();
				}
			} catch (SQLException e1) {
				logger.error(e1.getMessage(), e1);
			}
		}

	}

	/**
	 * 
	 * Get app release no of specified app
	 * 
	 * @param appid
	 * @return list of string
	 */
	public List<String> getAppReleaseNo(Integer appid) {
		logger.info("inside get release numbeer");
		final String releaseNo = "release_number";
		List<String> releaseNumber = new ArrayList<>();
		String tableName = "trelease_info";
		String column = "release_number";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("application_id= ?");
		queryStatement.append(";");
		logger.info("query statement " + queryStatement);

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			logger.info("inside try of get release number");
			preparedStatement.setInt(1, appid);
			logger.info("after set of get relase number");
			rs = preparedStatement.executeQuery();
			logger.info("after execute of get release number");

			while (rs.next()) {
				releaseNumber.add(rs.getString(releaseNo));
			}

		} catch (SQLException e) {

			logger.info(e.getMessage(), e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		return releaseNumber;
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
	 * get trigger count
	 * 
	 * 
	 * @param pipelineName    the String
	 * @param applicationName the String
	 * 
	 * @return Integer
	 * 
	 */
	public Integer getTriggerCount(String pipelineName, String applicationName) throws SQLException {

		Integer count = 0;

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append("count(trigger_id) ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(" ttrigger_history,tpipeline_info,tapplication_info ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" tpipeline_info.application_id = tapplication_info.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(PIPELINE_NAME);
		queryStatement.append(AND_CLAUSE);

		queryStatement.append(APPLICATION_NAME);
		queryStatement.append(ACTIVE_PIPELINE);
		queryStatement.append(";");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {
			preparedStatement.setString(1, pipelineName);
			preparedStatement.setString(2, applicationName);

			rs = preparedStatement.executeQuery();
			rs.next();
			count = rs.getInt(1);

			return count;
		}

		catch (SQLException | NullPointerException e)

		{
			logger.error("Postgres Error while fetching trigger history :", e);
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
	 * Get Artifact Details
	 * 
	 * 
	 * @param pipelineName    the String
	 * @param applicationName the String
	 * 
	 * @return permissions the List<String>
	 * 
	 */
	public List<DeployArtifact> getArtifactDetails(String pipelineName, String applicationName) throws SQLException {

		List<DeployArtifact> deployArtifacts = new ArrayList<>();
		DeployArtifact deployArtifact;
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append("version ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(" ttrigger_history,tpipeline_info,tapplication_info ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" tpipeline_info.application_id = tapplication_info.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(" tapplication_roles.role_id = troles.role_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(PIPELINE_NAME);
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(APPLICATION_NAME);

		queryStatement.append(ACTIVE_PIPELINE);
		queryStatement.append(";");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {
			preparedStatement.setString(1, pipelineName);
			preparedStatement.setString(2, applicationName);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				deployArtifact = new DeployArtifact();
				deployArtifact.setArtifactID(applicationName + "_" + pipelineName);
				deployArtifact.setVersion(rs.getString(1));
				deployArtifacts.add(deployArtifact);
			}

			return deployArtifacts;
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
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
	 * Returns organization id
	 * 
	 * @param userName
	 * @return String
	 * @throws SQLException
	 */
	public Long getOrgId(String userName) throws SQLException {

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append("org_id ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(" tuser_info ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" user_id like ? ");

		queryStatement.append(";");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {
			preparedStatement.setString(1, userName);

			rs = preparedStatement.executeQuery();

			rs.next();
			return rs.getLong(1);

		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);

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
		return -1L;

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

			rs.next();
			return rs.getString(1);

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
	 * checks if user exists
	 * 
	 * 
	 * @param userName the String
	 * 
	 * @return boolean
	 * 
	 */
	public boolean userExists(String userName) throws SQLException {

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append("count(user_id) ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(" public.tuser_info ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" user_id like ? ");

		queryStatement.append(";");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {
			preparedStatement.setString(1, userName);

			rs = preparedStatement.executeQuery();

			rs.next();
			if (rs.getInt(1) == 1)
				return true;

		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
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
		return false;

	}

	/**
	 * Check if user has permission to create app
	 * 
	 * 
	 * @param userName the String
	 * @return Boolean
	 * 
	 */
	public Boolean checkCreateAppPermission(String userName) {

		String tableName = "public.tuser_info,troles,trole_permissions";
		String column = " count(*) ";
		Integer count = 0;
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("base_role_id = troles.role_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("troles.role_id = trole_permissions.role_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("permission_key like 'CREATE_APPLICATION' ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("user_id like ?;");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, userName);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
			}
			if (count == 1)
				return true;
			return false;
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

		return false;

	}

	/**
	 * Return app details of the specified application
	 * 
	 * 
	 * @param appName the String
	 * 
	 * @return app the Application
	 * 
	 */

	public Application getExistingAppDetails(String appName) throws SQLException {

		Application app = new Application();
		Gson gson = new Gson();
		String tableName = "tapplication_info";
		String column = " application_name, entity_info ";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("application_name like ?");
		queryStatement.append(";");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, appName);
			rs = preparedStatement.executeQuery();

			rs.next();
			app = new Application();
			app.setApplicationName(rs.getString("application_name"));

			app.setAppJson(gson.fromJson(rs.getObject(2).toString(), ApplicationInfo.class));
			return app;
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

		return app;

	}

	/**
	 * Returns pipelines list of the specified app
	 * 
	 * 
	 * @param appName the String
	 * 
	 * @return app the Application
	 * 
	 */

	public List<Pipeline> getExistingPipelines(String appName) throws SQLException {

		List<Pipeline> pipelines = new ArrayList<>();
		Pipeline pip = new Pipeline();
		String tableName = "tapplication_info,tpipeline_info";
		String column = " pipeline_name ";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("tapplication_info.application_id = tpipeline_info.pipeline_id ");
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
				pip = new Pipeline();
				pip.setPipelineName(rs.getString("pipeline_name"));
				pipelines.add(pip);
			}
			return pipelines;
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

	/**
	 * Returns application list
	 * 
	 * @return list of applications
	 */

	public List<Application> getExistingAppList(String platformName) throws SQLException {

		List<Application> apps = new ArrayList<>();
		Application app;
		Gson gson = new Gson();
		String tableName = "tapplication_info";
		String column = " application_name, entity_info ";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("platform = " + Config.platformType(platformName) + " ");
		queryStatement.append(ORDER_BY);
		queryStatement.append(" application_name ");
		queryStatement.append(";");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				app = new Application();
				app.setApplicationName(rs.getString("application_name"));
				app.setAppJson(gson.fromJson(rs.getObject("entity_info").toString(), ApplicationInfo.class));
				apps.add(app);
			}

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

		return apps;

	}

	/**
	 * 
	 * Returns application list
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Application> getExistingAppDetails() throws SQLException {

		List<Application> apps = new ArrayList<>();
		Application app;
		Gson gson = new Gson();
		String tableName = "tapplication_info";
		String column = " application_name, entity_info ";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(ORDER_BY);
		queryStatement.append(" application_name ");
		queryStatement.append(";");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				app = new Application();
				app.setApplicationName(rs.getString("application_name"));
				app.setAppJson(gson.fromJson(rs.getObject("entity_info").toString(), ApplicationInfo.class));
				apps.add(app);
			}

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

		return apps;

	}

	/**
	 * Returns app list related to the specified organization
	 * 
	 * @param orgName
	 * @return list of application
	 * @throws SQLException
	 */

	public List<Application> getExistingAppNames(String orgName, String platformName) throws SQLException {

		List<Application> apps = new ArrayList<>();
		Application app;
		String tableName = "tapplication_info,tapplication_roles,tuser_info,torg_info";
		String column = " application_name ";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(" DISTINCT ");
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("tapplication_info.application_id = tapplication_roles.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tapplication_roles.user_id= tuser_info.user_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("platform = " + Config.platformType(platformName) + " ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tuser_info.org_id = torg_info.org_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("lower(org_name) like lower(?) ");
		queryStatement.append(ORDER_BY);
		queryStatement.append(" application_name ");
		queryStatement.append(";");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, orgName);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				app = new Application();
				app.setApplicationName(rs.getString("application_name"));
				apps.add(app);
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

		return apps;

	}

	/**
	 * Returns application list
	 * 
	 * @return list of application
	 * @throws SQLException
	 */
	public List<Application> getExistingAppNames(String platformName) throws SQLException {

		List<Application> apps = new ArrayList<>();
		Application app;
		String tableName = "tapplication_info";
		String column = " application_name ";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("platform = " + Config.platformType(platformName) + " ");

		queryStatement.append(ORDER_BY);
		queryStatement.append(" application_name ");
		queryStatement.append(";");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				app = new Application();
				app.setApplicationName(rs.getString("application_name"));
				apps.add(app);
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

		return apps;

	}

	/**
	 * Returns pipeline list
	 * 
	 * @param appName
	 * @return
	 * @throws SQLException
	 */

	public List<Pipeline> getPipelines(String appName) throws SQLException {

		List<Pipeline> pips = new ArrayList<>();
		Pipeline pipeline;
		Gson gson = new Gson();
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
				pipeline.setPipelineName(rs.getString("pipeline_name"));
				byte[] encryptedIDP = rs.getBytes(2);
				String decryptedIDP = EncryptionUtil.decrypt(new String(encryptedIDP));

				pipeline.setPipelineJson(gson.fromJson(decryptedIDP, IDPJob.class));

				pips.add(pipeline);
			}

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

		return pips;

	}

	/**
	 * Returns session job details.
	 * 
	 * 
	 * @param sessionId the String
	 * @param key       the String
	 * 
	 * @return count the Integer
	 * 
	 */
	public Integer getSessionJobDetails(String sessionId, String key) throws SQLException {

		String tableName = "session_job_details";
		String column = " count(*) ";
		Integer count = 0;
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("session_id like ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tab_key like ?;");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, sessionId);
			preparedStatement.setString(2, key);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
			}

			return count;
		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching session details:", e);
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
	 * Returns application name list that can be accessed by the specified user
	 * 
	 * @param userId the String
	 * 
	 * @return apps the List<String>
	 * 
	 */
	public List<String> getApplications(String userId, String platform) throws SQLException {

		String tableName = "tapplication_info,tapplication_roles,troles,trole_permissions";
		String column = " application_name ";
		List<String> apps = new ArrayList<>();
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(" DISTINCT ");
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("tapplication_info.application_id=tapplication_roles.application_id ");
		queryStatement.append(AND_CLAUSE + "tapplication_roles.role_id=troles.role_id");
		queryStatement.append(AND_CLAUSE + "troles.role_id = trole_permissions.role_id");
		queryStatement.append(AND_CLAUSE + "platform = " + Config.platformType(platform) + " ");
		queryStatement.append(AND_CLAUSE + "permission_key in ('CREATE_PIPELINE','COPY_PIPELINE','EDIT_PIPELINE')");
		queryStatement.append(AND_CLAUSE + USER_ID);
		queryStatement.append(ORDER_BY);
		queryStatement.append("application_name ;");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, userId);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				apps.add(rs.getString(1));
			}

			return apps;
		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching applications:", e);
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
	 * 
	 * Returns build number for the specified app & pipeline
	 * 
	 * @param applicationName
	 * @param pipelineName
	 * @return string
	 * @throws SQLException
	 */
	public String getBuildnumber(String applicationName, String pipelineName) throws SQLException {

		List<String> apps = new ArrayList<>();
		String queryStatement = "SELECT  build_number FROM public.tdevops_info where \"Application_name\"=? and \"Pipeline_name\"= ?";

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			preparedStatement.setString(1, applicationName);
			preparedStatement.setString(2, pipelineName);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				apps.add(rs.getString(1));
			}

			return apps.get(0);
		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching applications:", e);
			return null;

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
	 * 
	 * Updates build number details
	 * 
	 * @param applicationName
	 * @param pipelineName
	 * @param newBuildId
	 * @return int
	 * @throws SQLException
	 */
	public int updateBuildnumber(String applicationName, String pipelineName, String newBuildId) throws SQLException {

		String queryStatement = "UPDATE public.tdevops_info set build_number=?  where \"Application_name\"=? and \"Pipeline_name\"= ?";

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			preparedStatement.setString(1, newBuildId);
			preparedStatement.setString(2, applicationName);
			preparedStatement.setString(3, pipelineName);
			int rs = preparedStatement.executeUpdate();
			return rs;
		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching applications:", e);
			throw e;

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
	 * Return role of the user in the specified application
	 * 
	 * @param userName
	 * @param applicationName
	 * @return
	 * @throws SQLException
	 */
	public List<String> getRoles(String userName, String applicationName) throws SQLException {

		String tableName = "public.tapplication_roles,troles,tapplication_info";
		String column = " role_name ";
		List<String> roles = new ArrayList<>();
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE + "tapplication_roles.role_id= troles.role_id");
		queryStatement.append(AND_CLAUSE + "tapplication_roles.application_id = tapplication_info.application_id");
		queryStatement.append(AND_CLAUSE + APPLICATION_NAME + " ");
		queryStatement.append(AND_CLAUSE + "user_id like ?");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, applicationName);
			preparedStatement.setString(2, userName);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				roles.add(rs.getString(1));
			}
			return roles;
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
	 * 
	 * Returns slave details of the specified application and usage
	 * 
	 * @param applicationName
	 * @param usage
	 * @return
	 * @throws SQLException
	 */

	public List<String> getSlaveDetails(String applicationName, String usage) throws SQLException {

		String tableName = "tslave_detials";
		String column = " application_id, slave_name,usage ";
		StringBuilder queryStatement = new StringBuilder();
		List<String> slaves = new ArrayList<>();

		queryStatement.append(SELECT_CLAUSE + column + FROM_CLAUSE + tableName + WHERE_CLAUSE + "application_id = ? "
				+ AND_CLAUSE + " usage in (?,?);");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setLong(1, getApplicationId(applicationName));
			preparedStatement.setString(2, "both");
			preparedStatement.setString(3, usage);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				slaves.add(rs.getString(2));

			}
			return slaves;
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching data from tslave_details:", e);
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
	 * 
	 * Returns session details
	 * 
	 * method getSessionJobDetails
	 * 
	 * @param sessionId the String
	 * 
	 * @return map the HashMap<String,Object>
	 */

	public HashMap<String, Object> getSessionJobDetails(String sessionId) throws SQLException {

		String tableName = "tsession_info";
		String column = " entity_info,entity_key ";
		String key = "";
		Object json;
		HashMap<String, Object> map = new HashMap<>();
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE + column + FROM_CLAUSE + tableName + WHERE_CLAUSE + "session_id like ? ;");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, sessionId);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				key = rs.getString("entity_key");
				json = rs.getObject("entity_info");
				map.put(key, json);
			}
			return map;
		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while inserting the data in session_job_details:", e);
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
	 * 
	 * Returns job details of the specified pipeline
	 * 
	 * @param applicationName
	 * @param pipelineName
	 * @return idpJson the IDPJob
	 */

	public IDPJob getPipelineInfo(String applicationName, String pipelineName) throws SQLException {

		logger.debug("get pipeline info");
		String tableName = "tpipeline_info,tapplication_info";
		String column = "tpipeline_info.entity_info";
		IDPJob idpJson = new IDPJob();
		Gson gson = new Gson();
		StringBuilder queryStatement = new StringBuilder();

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
			logger.debug("Query " + preparedStatement.toString());

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				byte[] encryptedIDP = rs.getBytes(1);
				String decryptedIDP = EncryptionUtil.decrypt(new String(encryptedIDP));
				idpJson = gson.fromJson(decryptedIDP, IDPJob.class);

			}
			return idpJson;
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
	 * 
	 * Returns release number of specified app
	 * 
	 * 
	 * @param applicationName
	 * @param pipelineName
	 * 
	 * @return releaseNumber
	 */

	public List<String> getReleaseNumber(String appName, String pipelineName) throws SQLException {
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append("release_number ");
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

		List<String> releaseNumbers = new ArrayList<>();
		String releaseNumber;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, appName);
			preparedStatement.setString(2, pipelineName);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				releaseNumber = rs.getString(1);
				releaseNumbers.add(releaseNumber);
			}

			return releaseNumbers;
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
	 * Returns application details of the specified app
	 * 
	 * @param appName
	 *
	 * 
	 * @return app
	 */

	public Application getApplicationDetail(String appName) {

		String tableName = "tapplication_info";
		String column = "entity_info ";
		StringBuilder queryStatement = new StringBuilder();
		Application app = new Application();
		queryStatement.append(SELECT_CLAUSE + column);
		queryStatement.append(FROM_CLAUSE + tableName + WHERE_CLAUSE + "application_name like ?; ");

		ResultSet rs = null;

		Gson gson = new Gson();
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, appName);

			rs = preparedStatement.executeQuery();

			rs.next();
			app.setApplicationName(appName);
			app.setAppJson(gson.fromJson(rs.getObject(1).toString(), ApplicationInfo.class));
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
					logger.info(e.getMessage(), e);
				}
			}
		}

		return app;

	}

	/**
	 * Returns pipeline application list of the specified user
	 * 
	 * 
	 * @param userId
	 * @param platformName
	 * 
	 * @return pipelineDetails list
	 */

	public List<PipelineDetail> getApplicationDetails(String userId, String platformName) {
		String tableName = "tpipeline_info,tapplication_info,tapplication_roles ";
		String column = "pipeline_name,application_name,creation_date,technology,build_tool ";
		Integer count = 1;
		PipelineDetail pipelineDetail;
		List<PipelineDetail> pipelineDetails = new ArrayList<>();
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE + " DISTINCT " + column);
		queryStatement.append(FROM_CLAUSE + tableName + WHERE_CLAUSE + APPLICATION_ID);
		queryStatement.append(AND_CLAUSE + " tapplication_info.application_id=tapplication_roles.application_id");
		queryStatement.append(AND_CLAUSE + "tapplication_roles.user_id LIKE ? ");
		queryStatement.append(AND_CLAUSE + "platform= ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("active = true;");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, userId);
			preparedStatement.setInt(2, Config.IDP_APPLICATION);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				pipelineDetail = new PipelineDetail();
				pipelineDetail.setSrNumber(count);
				pipelineDetail.setApplicationName(rs.getString("application_name"));
				pipelineDetail.setPipelineName(rs.getString("pipeline_name"));
				pipelineDetail.setCreationDate(rs.getString("creation_date"));
				List<String> permission = new ArrayList<>();
				pipelineDetail.setPermissions(permission);
				permission.addAll(getPermissions(userId, pipelineDetail.getApplicationName()));
				pipelineDetail.setBuildTool(rs.getString("build_tool"));
				if ("DBDEPLOY".equalsIgnoreCase(rs.getString("technology"))) {
					if (pipelineDetail.getPermissions().contains("DATABASE_DEPLOY")) {
						pipelineDetails.add(pipelineDetail);
					}
				} else {
					pipelineDetails.add(pipelineDetail);
				}

				count++;
			}

		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching data from tpipeline_info :", e);

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

		return pipelineDetails;

	}

	/**
	 * Returns pipeline admin details
	 * 
	 * 
	 * @param userId the String
	 *
	 * 
	 * @return pipelineDetails the List<PipelineDetail>
	 */

	public List<PipelineDetail> getPipelinesCustomPipelineadmin(String userId) {
		String tableName = "tpipeline_info,tpipeline_roles,tapplication_info ";
		String column = "pipeline_name,application_name,creation_date,technology,build_tool ";
		Integer count = 1;
		PipelineDetail pipelineDetail;
		List<PipelineDetail> pipelineDetails = new ArrayList<>();
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE + " DISTINCT " + column);
		queryStatement.append(FROM_CLAUSE + tableName + WHERE_CLAUSE + APPLICATION_ID);
		queryStatement.append(AND_CLAUSE + " tpipeline_info.pipeline_id=tpipeline_roles.pipeline_id");
		queryStatement.append(AND_CLAUSE + "tpipeline_roles.user_id LIKE ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("active = true;");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, userId);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				pipelineDetail = new PipelineDetail();
				pipelineDetail.setSrNumber(count);
				pipelineDetail.setApplicationName(rs.getString("application_name"));
				pipelineDetail.setPipelineName(rs.getString("pipeline_name"));
				pipelineDetail.setCreationDate(rs.getString("creation_date"));
				List<String> permission = new ArrayList<>();

				if (getPipelinePermissionForApplication(pipelineDetail.getApplicationName(), userId).size() > 0
						&& getPipelinePermission(pipelineDetail.getApplicationName(), pipelineDetail.getPipelineName(),
								userId).size() > 0) {
					permission.add("EDIT_PIPELINE");
				}
				pipelineDetail.setPermissions(permission);
				pipelineDetail.setBuildTool(rs.getString("build_tool"));
				if ("DBDEPLOY".equalsIgnoreCase(rs.getString("technology"))) {
					if (pipelineDetail.getPermissions().contains("DATABASE_DEPLOY")) {
						pipelineDetails.add(pipelineDetail);
					}
				} else {
					pipelineDetails.add(pipelineDetail);
				}

				count++;
			}
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching data from tpipeline_info :", e);
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
		return pipelineDetails;
	}

	/**
	 * Returns distinct roles of the user
	 * 
	 * @param userId
	 * @return Roles list
	 */
	public List<String> getRoles(String userId) {
		List<String> userRoles = new ArrayList<>();
		String rolesQuery1 = "SELECT distinct role_name FROM tapplication_roles INNER JOIN troles ON tapplication_roles.role_id = troles.role_id where user_id = ? ";

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(rolesQuery1)) {

			preparedStatement.setString(1, userId);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				userRoles.add(rs.getString("role_name"));

			}

		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching role details from tuser_info or trole_permissionss:", e);

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

		return userRoles;
	}

	/**
	 * 
	 * Returns roles list of the specified user in the specified app
	 * 
	 * @param userId
	 * @param appName
	 * @return list of string
	 */
	public List<String> getRolesasApp(String userId, String appName) {
		List<String> userRoles = new ArrayList<>();
		String rolesQuery1 = "SELECT distinct role_name FROM tapplication_roles INNER JOIN troles ON tapplication_roles.role_id = troles.role_id INNER JOIN tapplication_info on tapplication_roles.application_id = tapplication_info.application_id where user_id = ? and tapplication_info.application_name= ? ";

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(rolesQuery1)) {

			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, appName);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				userRoles.add(rs.getString("role_name"));
			}
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching role details from tuser_info or trole_permissionss:", e);
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

		return userRoles;
	}

	/**
	 * Returns release number of the specified pipeline and app
	 * 
	 * @param pipelineName
	 * @param appName
	 * @return string
	 */
	public String getReleaseNo(String pipelineName, String appName) {
		String version = null;
		String rolesQuery1 = "SELECT  version FROM ttrigger_history INNER JOIN tpipeline_info "
				+ "ON ttrigger_history.pipeline_id = tpipeline_info.pipeline_id "
				+ "INNER JOIN tapplication_info on tapplication_info.application_id = tpipeline_info.application_id " +

				"where tpipeline_info.pipeline_name = ? and tapplication_info.application_name=? and active = true and "
				+ "ttrigger_history.trigger_id=(SELECT  max(trigger_id) FROM ttrigger_history INNER JOIN tpipeline_info "
				+ "ON ttrigger_history.pipeline_id = tpipeline_info.pipeline_id "
				+ "INNER JOIN tapplication_info on tapplication_info.application_id = tpipeline_info.application_id "
				+ "where tpipeline_info.pipeline_name = ? and tapplication_info.application_name=? and active = true)";

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(rolesQuery1)) {

			preparedStatement.setString(1, pipelineName);
			preparedStatement.setString(2, appName);
			preparedStatement.setString(3, pipelineName);
			preparedStatement.setString(4, appName);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				version = rs.getString("version");

			}
		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching role details from tuser_info or trole_permissionss:", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		return version;
	}

	/**
	 * Returns list of permission given to the specified user
	 * 
	 * @param userId
	 * @return list of permission
	 */
	public List<String> getPermission(String userId) {
		List<String> userPermissions = new ArrayList<>();
		String rolesQuery1 = "SELECT distinct permission_key FROM tapplication_roles INNER JOIN trole_permissions ON tapplication_roles.role_id = trole_permissions.role_id where user_id = ? ";

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(rolesQuery1)) {

			preparedStatement.setString(1, userId);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				userPermissions.add(rs.getString("permission_key"));
			}

		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching permissions deatails from tuser_info or trole_permissionss:",
					e);

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		return userPermissions;
	}

	/**
	 * Returns roles list of the specified user
	 * 
	 * @param userId
	 * @return list of roles
	 */
	public List<String> getBaseRoles(String userId) {
		List<String> userBaseRole = new ArrayList<>();
		String rolesQuery1 = "select role_name from troles inner join tuser_info on role_id = base_role_id where user_id = ? ";

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(rolesQuery1)) {

			preparedStatement.setString(1, userId);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				userBaseRole.add(rs.getString("role_name"));
			}
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching base role details from tuser_info or trole_permissionss:", e);
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

		return userBaseRole;
	}

	/**
	 * 
	 * Returns base permission from list given to the specified user
	 * 
	 * @param userId
	 * @return permission list
	 * 
	 */
	public List<String> getBasePermission(String userId) {
		List<String> userBasePermission = new ArrayList<>();
		String rolesQuery1 = "select permission_key from trole_permissions inner join tuser_info on role_id = base_role_id where user_id = ? ";

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(rolesQuery1)) {

			preparedStatement.setString(1, userId);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				userBasePermission.add(rs.getString("permission_key"));
			}

		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching permissions deatails from tuser_info or trole_permissionss:",
					e);

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		return userBasePermission;
	}

	/**
	 * Returns pipeline details of the specified trigger job
	 * 
	 * 
	 * @param tiggerJobName
	 *
	 * 
	 * @return pipeline
	 */

	public Pipeline getPipelineDetail(TriggerJobName tiggerJobName) {

		String tableName = "tapplication_info,tpipeline_info";
		String column = "tpipeline_info.entity_info ";
		StringBuilder queryStatement = new StringBuilder();
		Pipeline pipeline = new Pipeline();
		queryStatement.append(SELECT_CLAUSE + column);
		queryStatement.append(FROM_CLAUSE + tableName + WHERE_CLAUSE
				+ "tapplication_info.application_id=tpipeline_info.application_id  ");
		queryStatement.append(AND_CLAUSE + "tapplication_info.application_name like ? ");
		queryStatement.append(AND_CLAUSE + "tpipeline_info.pipeline_name like ? ");

		queryStatement.append(ACTIVE_PIPELINE);
		Gson gson = new Gson();

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, tiggerJobName.getApplicationName());
			preparedStatement.setString(2, tiggerJobName.getPipelineName());

			rs = preparedStatement.executeQuery();

			rs.next();
			pipeline.setPipelineName(tiggerJobName.getPipelineName());
			byte[] encryptedIDP = rs.getBytes(1);

			String decryptedIDP = EncryptionUtil.decrypt(new String(encryptedIDP));
			pipeline.setPipelineJson(gson.fromJson(decryptedIDP, IDPJob.class));
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching data from tapplication_info and tpipeline_info :", e);

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		return pipeline;

	}

	/**
	 * Returns job build list of the specified job
	 * 
	 * @param getjob
	 * @return job build list
	 */
	public List<Long> getJobBuildDetails(GetJob getjob) {
		List<Long> jobDetails = new ArrayList<>();
		String rolesQuery1 = "SELECT  latest_ca_build_number, latest_ct_build_number , build_number FROM public.tdevops_build_details where application_name=? and pipeline_name=? and build_number=? ;";

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(rolesQuery1)) {

			preparedStatement.setString(1, getjob.getApplicationName());
			preparedStatement.setString(2, getjob.getPipelineName());
			preparedStatement.setLong(3, Long.parseLong(getjob.getBuildNumber()));

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				jobDetails.add(0, rs.getLong("latest_ca_build_number"));
				jobDetails.add(1, rs.getLong("latest_ct_build_number"));
				jobDetails.add(2, rs.getLong("build_number"));
			}
		}

		catch (SQLException | NullPointerException e) {
			return jobDetails;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		return jobDetails;
	}

	/**
	 * 
	 * Returns build number of the specified job
	 * 
	 * @param applicationName
	 * @param pipelineName
	 * @return list of strings
	 */
	public List<String> getbuildnum(String applicationName, String pipelineName) {

		List<String> a = new ArrayList<>();
		String queryStatement = "select version from public.ttrigger_history where pipeline_id =(SELECT tpipeline_info.pipeline_id FROM tpipeline_info,tapplication_info WHERE tpipeline_info.application_id=tapplication_info.application_id AND  application_name LIKE ?  AND  pipeline_name LIKE ?)";

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, applicationName);
			preparedStatement.setString(2, pipelineName);

			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				a.add(rs.getString("version"));

			}
		}

		catch (SQLException e) {
			logger.error("Postgres Error while inserting the data in tuser_info:", e);
			logger.info(e.getMessage(), e);
			return a;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return a;
	}

	/**
	 * Returns release number of the specified build
	 * 
	 * @param applicationName
	 * @param pipelineName
	 * @param buildnum
	 * @return release number
	 */
	public String getreleaseNum(String applicationName, String pipelineName, String buildnum) {

		String rNo = null;
		String queryStatement = "select release_number from public.ttrigger_history where version like ? AND pipeline_id =(SELECT tpipeline_info.pipeline_id FROM tpipeline_info,tapplication_info WHERE tpipeline_info.application_id=tapplication_info.application_id AND  application_name LIKE ?  AND  pipeline_name LIKE ?)";

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, buildnum);
			preparedStatement.setString(2, applicationName);
			preparedStatement.setString(3, pipelineName);

			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				rNo = rs.getString("release_number");

			}
			rs.close();

		}

		catch (SQLException e) {
			logger.error("Postgres Error while inserting the data in tuser_info:", e);
			logger.info(e.getMessage(), e);
			return rNo;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return rNo;
	}

	/**
	 * Returns release number of the specified build
	 * 
	 * @param applicationName
	 * @param pipelineName
	 * @param buildnum
	 * @return list
	 */
	public List getTriggerEntitytime(String applicationName, String pipelineName, String buildnum) {

		TriggerParameters a = null;
		String queryStatement = "select trigger_entity,trigger_time from public.ttrigger_history where version like ? AND pipeline_id =(SELECT tpipeline_info.pipeline_id FROM tpipeline_info,tapplication_info WHERE tpipeline_info.application_id=tapplication_info.application_id AND  application_name LIKE ?  AND  pipeline_name LIKE ?)";

		List triggerEntityTimeDetail = null;
		Gson gson = new Gson();
		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, buildnum);
			preparedStatement.setString(2, applicationName);
			preparedStatement.setString(3, pipelineName);

			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				triggerEntityTimeDetail = new ArrayList();
				a = gson.fromJson(rs.getObject(1).toString(), TriggerParameters.class);
				triggerEntityTimeDetail.add(0, a);
				triggerEntityTimeDetail.add(1, rs.getTimestamp("trigger_time").toString());

			}

		}

		catch (SQLException e) {
			logger.error("Postgres Error while inserting the data in tuser_info:", e);
			logger.info(e.getMessage(), e);
			return triggerEntityTimeDetail;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return triggerEntityTimeDetail;
	}

	/**
	 * 
	 * Returns trigger details entity
	 * 
	 * @param applicationName
	 * @param pipelineName
	 * @param buildnum
	 * @return list of list
	 */
	public List<List> getTriggerEntity(String applicationName, String pipelineName, String buildnum) {

		TriggerParameters tParamJson = null;
		String queryStatement = "select trigger_entity,trigger_time from public.ttrigger_history where pipeline_id =(SELECT tpipeline_info.pipeline_id FROM tpipeline_info,tapplication_info WHERE tpipeline_info.application_id=tapplication_info.application_id AND  application_name LIKE ?  AND  pipeline_name LIKE ?) order by trigger_time DESC";

		List triggerEntity = new ArrayList();
		Gson gson = new Gson();
		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, applicationName);
			preparedStatement.setString(2, pipelineName);

			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				List tParam = new ArrayList();
				tParamJson = gson.fromJson(rs.getObject(1).toString(), TriggerParameters.class);
				tParam.add(0, tParamJson);
				tParam.add(1, rs.getTimestamp("trigger_time").toString());
				triggerEntity.add(tParam);
			}

		}

		catch (SQLException e) {
			logger.error("Postgres Error while inserting the data in tuser_info:", e);
			logger.info(e.getMessage(), e);
			return triggerEntity;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		return triggerEntity;
	}

	/**
	 * Returns status of build number
	 * 
	 * @param applicationName
	 * @param pipelineName
	 * @param buildnum
	 * @return list of status
	 */
	public List<String> getStatus(String applicationName, String pipelineName, String buildnum) {

		List<String> statusList = new ArrayList<String>();
		String queryStatement = "SELECT  \"status\" FROM public.\"dashboard_info\" where \"application_name\" like ? AND \"pipeline_id\" like ? AND \"pipeline_name\" like ?;";

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			preparedStatement.setString(1, applicationName);
			preparedStatement.setString(2, buildnum);
			preparedStatement.setString(3, pipelineName);

			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				statusList.add(rs.getString("status"));
			}
		}

		catch (SQLException e) {
			logger.error("Postgres Error while inserting the data in tuser_info:", e);
			logger.info(e.getMessage(), e);
			return statusList;
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		return statusList;
	}

	/**
	 * Returns latest build details
	 * 
	 * @param getjob
	 * @return long
	 */
	public Long getLatestBuild(GetJob getjob) {
		Long buildDetails = null;
		String rolesQuery1 = "select max(build_number) from public.tdevops_build_details where application_name=? and pipeline_name=?";

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(rolesQuery1)) {

			preparedStatement.setString(1, getjob.getApplicationName());
			preparedStatement.setString(2, getjob.getPipelineName());

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				buildDetails = rs.getLong(1);

			}

		}

		catch (SQLException | NullPointerException e) {
			return null;

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		return buildDetails;
	}

	/**
	 * 
	 * Returns permission for app
	 * 
	 * @param userId
	 * @param applicationName
	 * @return list of string
	 */
	public List<String> getPermissionForApplications(String userId, String applicationName) {
		List<String> userPermissions = new ArrayList<>();

		StringBuilder query = new StringBuilder();
		query.append(SELECT_CLAUSE);
		query.append("DISTINCT permission_key");
		query.append(FROM_CLAUSE);
		query.append("trole_permissions,tapplication_roles,tapplication_info ");
		query.append(WHERE_CLAUSE);
		query.append("trole_permissions.role_id = tapplication_roles.role_id");
		query.append(AND_CLAUSE);
		query.append("tapplication_info.application_id = tapplication_roles.application_id");
		query.append(AND_CLAUSE);
		query.append(APPLICATION_NAME);
		query.append(AND_CLAUSE);
		query.append(USER_ID);

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

			preparedStatement.setString(1, applicationName);
			preparedStatement.setString(2, userId);

			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				userPermissions.add(rs.getString("permission_key"));
			}
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions deatails from tuser_info or trole_permissionss:",
					e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		return userPermissions;
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
	 * Returns Application Name For ReleaseManage user
	 * 
	 * 
	 * @param userName the String
	 *
	 * 
	 * @return List<String>
	 */

	public List<String> getApplicationNameForReleaseManager(String userName, String platformName) {

		List<String> applicationNames = new ArrayList<>();
		String tableName = " public.tapplication_info,tapplication_roles,troles,trole_permissions ";
		String column = "DISTINCT application_name ";
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE + column);
		queryStatement.append(FROM_CLAUSE + tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" tapplication_roles.role_id = troles.role_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tapplication_info.application_id= tapplication_roles.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(" troles.role_id= trole_permissions.role_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("platform = " + Config.platformType(platformName) + " ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(" permission_key like 'RELEASE' ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(USER_ID);
		queryStatement.append(ORDER_BY);
		queryStatement.append("application_name");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, userName);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				if (null == rs.getString(1))
					continue;

				applicationNames.add(rs.getString(1));
			}
			logger.debug(
					"Application names for the release manager " + userName + " is : " + applicationNames.toString());
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

		return applicationNames;

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
				byte[] encryptedIDP = rs.getBytes(2);
				String decryptedIDP = EncryptionUtil.decrypt(new String(encryptedIDP));
				jobJson = gson.fromJson(decryptedIDP, IDPJob.class);

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
	 * Returns dbDeploy technology app
	 * 
	 * 
	 * @param appName the String
	 *
	 * 
	 * @return dbdeploy pipeline list of specific application
	 */

	public List<String> dbDeployPipelineNamesForApplication(String appName) {

		List<String> pipelines = new ArrayList<>();
		String tableName = " tpipeline_info,tapplication_info  ";
		String column = " pipeline_name ";
		String technology = "dbDeploy";
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE + column);
		queryStatement.append(FROM_CLAUSE + tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" tpipeline_info.application_id= tapplication_info.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(APPLICATION_NAME);
		queryStatement.append(ACTIVE_PIPELINE);
		queryStatement.append(TECHNOLOGY_NAME);
		queryStatement.append(ORDER_BY);
		queryStatement.append("pipeline_name");
		queryStatement.append(";");
		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, appName);
			preparedStatement.setString(2, technology);
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

	/**
	 * Delete pipeline roles for the specified pipeline
	 * 
	 * @param pipelineId
	 */
	public void deletePipelineRoles(Long pipelineId) {

		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append("DELETE FROM public.tpipeline_roles ");
		queryStatement.append(" WHERE pipeline_id =?; ");

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {
			preparedStatement.setLong(1, pipelineId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Postgres Error while deleting pipeline roles :", e);

		}
	}

	/**
	 * Returns release Transport Info
	 * 
	 * 
	 * @param applicationName
	 * @param pipelineName
	 *
	 * 
	 * @return List of ReleaseTransportInfo the String
	 */

	public List<ReleaseTransportInfo> getReleaseTransportInfo(String appName, String pipelineName) throws SQLException {
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append("release_number, transport_request ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append("public.tpipeline_info,tapplication_info,ttrigger_history,tsap_deploy_details ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("tapplication_info.application_id = tpipeline_info.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("ttrigger_history.trigger_id = tsap_deploy_details.trigger_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tpipeline_info.pipeline_id = ttrigger_history.pipeline_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tapplication_info.application_name LIKE ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tpipeline_info.pipeline_name LIKE ? ");
		queryStatement.append(ORDER_BY);
		queryStatement.append("release_number");

		ResultSet rs = null;
		String releaseNumber;
		String prevReleaseNumber = "";
		String transportRequest;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, appName);
			preparedStatement.setString(2, pipelineName);

			rs = preparedStatement.executeQuery();

			List<ReleaseTransportInfo> releaseTransportInfoList = new ArrayList<>();
			List<String> transportRequestList = new ArrayList<>();
			ReleaseTransportInfo releaseTransportInfo;
			while (rs.next()) {
				releaseNumber = rs.getString(1);
				transportRequest = rs.getString(2);

				if (!prevReleaseNumber.equals(releaseNumber)) {
					transportRequestList = new ArrayList<>();
					releaseTransportInfo = new ReleaseTransportInfo();

					releaseTransportInfo.setReleaseNumber(releaseNumber);
					transportRequestList.add(transportRequest);
					releaseTransportInfo.setTransportList(transportRequestList);

					releaseTransportInfoList.add(releaseTransportInfo);
				} else {
					transportRequestList.add(transportRequest);
				}

				prevReleaseNumber = releaseNumber;
			}

			return releaseTransportInfoList;
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching ReleaseTransportInfo entity values :", e);
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
	 * Returns notification for the specified user
	 * 
	 * @param username
	 * @return
	 */
	public List<Notification> getNotifications(String username) {
		StringBuilder queryStatement = new StringBuilder();
		ResultSet rs = null;
		List<Notification> notifList = new ArrayList<Notification>();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(" * from public.tnotification_info");
		queryStatement.append(WHERE_CLAUSE + "username=? order by creation_date DESC");
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, username);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				notifList.add(new Notification(new Date(rs.getTimestamp("creation_date").getTime()).toString(),
						rs.getString("status"), rs.getString("pipeline_name")));
			}
			return notifList;
		} catch (SQLException e) {
			logger.error("Error occured while retriving notification : " + e.getMessage());
			return notifList;
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
