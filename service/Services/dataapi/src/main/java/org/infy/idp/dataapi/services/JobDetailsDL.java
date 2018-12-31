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
import java.util.List;

import org.infy.entities.triggerinputs.DeployArtifact;
import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.entities.jobs.Pipeline;
import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.SlavesDetail;
import org.infy.idp.entities.jobs.common.Notification;
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
public class JobDetailsDL {
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;

	@Autowired
	private JobInfoDL jobInfoDL;

	private static final Logger logger = LoggerFactory.getLogger(JobDetailsDL.class);
	private static final String WHERE_CLAUSE = " WHERE ";
	private static final String AND_CLAUSE = " AND ";
	private static final String SELECT_CLAUSE = " SELECT ";
	private static final String FROM_CLAUSE = " FROM ";
	private static final String APPLICATION_NAME = " application_name LIKE ? ";
	private static final String PIPELINE_NAME = " pipeline_name LIKE ? ";
	private static final String APPLICATION = "application_name";
	private static final String ORDER_BY = " ORDER BY ";
	private static final String ACTIVE_PIPELINE = " and active = true ";
	protected static final String ERROR1 = "Postgres Error while fetching user details:";
	protected static final String ERROR2 = "Postgres Error while fetching permissions:";

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
			preparedStatement.setLong(1, jobInfoDL.getApplicationId(appName));
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
		queryStatement.append(APPLICATION_NAME);
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
			if (rs.next()) {
				count = rs.getInt(1);
			}

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
			logger.error(ERROR2, e);
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
			if (rs.next()) {
				return rs.getLong(1);
			}

		}

		catch (SQLException | NullPointerException e) {
			logger.error(ERROR2, e);

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

			if (rs.next() && rs.getInt(1) == 1) {
				return true;
			}
		}

		catch (SQLException | NullPointerException e) {
			logger.error(ERROR2, e);
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
			logger.error(ERROR1, e);

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
		queryStatement.append(APPLICATION_NAME);
		queryStatement.append(";");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, appName);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				app = new Application();
				app.setApplicationName(rs.getString(APPLICATION));

				app.setAppJson(gson.fromJson(rs.getObject(2).toString(), ApplicationInfo.class));
				return app;
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
		queryStatement.append(APPLICATION_NAME);

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
				app.setApplicationName(rs.getString(APPLICATION));
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
				app.setApplicationName(rs.getString(APPLICATION));
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
