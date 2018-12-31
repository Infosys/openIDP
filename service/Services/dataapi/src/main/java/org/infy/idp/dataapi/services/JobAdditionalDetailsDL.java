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
import org.infy.idp.entities.jobs.PipelineDetail;
import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
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
public class JobAdditionalDetailsDL {
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
	private static final String USER_ID = " user_id LIKE ? ";
	private static final String APPLICATION_ID = " tpipeline_info.application_id=tapplication_info.application_id ";
	private static final String ORDER_BY = " ORDER BY ";
	private static final String ACTIVE_PIPELINE = " and active = true ";
	protected static final String ERROR1 = "Postgres Error while fetching user details:";
	/**
	 * Constructor
	 * 
	 * 
	 * @param postGreSqlDbContext the PostGreSqlDbContext
	 * 
	 */
	private JobAdditionalDetailsDL() {

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
			preparedStatement.setLong(1, jobInfoDL.getApplicationId(applicationName));
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

			if (rs.next()) {
				app.setApplicationName(appName);
				app.setAppJson(gson.fromJson(rs.getObject(1).toString(), ApplicationInfo.class));
			}
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
				permission.addAll(jobInfoDL.getPermissions(userId, pipelineDetail.getApplicationName()));
				//add custom pipeline admin permission
				permission.addAll(jobInfoDL.getPipelinePermission(rs.getString("application_name"),rs.getString("pipeline_name"),userId));
				
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
	
	
	public List<String> getCustomPipelineAdminApplications(String userId, String platform) throws SQLException {

		String tableName = "tapplication_info,tpipeline_roles";
		String column = " application_name ";
		List<String> apps = new ArrayList<>();
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(" DISTINCT ");
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("tapplication_info.application_id=tpipeline_roles.app_id ");
		queryStatement.append(AND_CLAUSE + "platform = " + Config.platformType(platform) + " ");
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

}
