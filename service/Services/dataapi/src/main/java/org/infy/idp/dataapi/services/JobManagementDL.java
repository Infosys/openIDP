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

import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.entities.getjob.GetJob;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.Pipeline;
import org.infy.idp.entities.jobs.PipelineDetail;
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
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class JobManagementDL {
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
	private JobManagementDL() {

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

				if (jobInfoDL.getPipelinePermissionForApplication(pipelineDetail.getApplicationName(), userId).size() > 0
						&& jobInfoDL.getPipelinePermission(pipelineDetail.getApplicationName(), pipelineDetail.getPipelineName(),
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

			if (rs.next()) {
				pipeline.setPipelineName(tiggerJobName.getPipelineName());
				byte[] encryptedIDP = rs.getBytes(1);

				String decryptedIDP = EncryptionUtil.decrypt(new String(encryptedIDP));
				pipeline.setPipelineJson(gson.fromJson(decryptedIDP, IDPJob.class));
			}
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
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
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
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
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
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
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
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
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

}
