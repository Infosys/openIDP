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
import java.util.List;

import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.entities.getjob.GetJob;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.SlavesDetail;
import org.infy.idp.entities.jobs.code.JobParam;
import org.infy.idp.entities.triggerparameter.TriggerParameters;
import org.infy.idp.utils.EncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * The class contains methods related to jobs, additional parameters
 * 
 * @author Infosys
 */
@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class JobDetailsInsertionService {
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	@Autowired
	private JobDetailsDL getJobDetails;

	@Autowired
	private JobInfoDL jobInfoDL;

	private static final Logger logger = LoggerFactory.getLogger(JobDetailsInsertionService.class);

	private static final String INSERT_CLAUSE = "INSERT INTO  ";
	private static final String UPDATE_CLAUSE = "UPDATE  ";
	private static final String WHERE_CLAUSE = " WHERE ";
	private static final String SET_CLAUSE = " SET ";
	private static final String WORKFLOW = "workflow";
	private static final String SELECT_CLAUSE = "SELECT  ";
	private JobDetailsInsertionService() {

	}

	/**
	 * 
	 * @param ApplicationNmae
	 * @param pipelineName
	 * @param buildnumber
	 * @return integer
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	public Integer insertBuildNumber(String appName, String pipelineName, String buildnumber) throws SQLException {

		String queryStatement = "INSERT INTO tdevops_info VALUES (?, ?, ?);";
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			preparedStatement.setObject(1, appName);
			preparedStatement.setString(2, pipelineName);
			preparedStatement.setString(3, buildnumber);
			preparedStatement.executeUpdate();
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the basic info data in session_job_details:", e);
			throw e;

		}

		return 1;
	}
	public Integer getLatestBuildNumber(String applicationName,String pipelineName)
	{
		logger.info("getting latest buildno ");
		Integer buildNo=0;
		String tablename = " tnotification_info ";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(" max(pipeline_build_number) from ");
		queryStatement.append(tablename);
		queryStatement.append(" where pipeline_name=? and application_name=?; ");
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, pipelineName);
			preparedStatement.setString(2, applicationName);
			ResultSet rs=preparedStatement.executeQuery();
			while(rs.next())
			{
				buildNo=rs.getInt(1);
			}
			
		} catch (Exception e) {
			logger.error("Postgres Error while getting data", e);
		}
		return buildNo;
	}

	/**
	 * insert pipeline info
	 * 
	 * @param pipelineName    the String
	 * @param applicationName the String
	 * 
	 * @return pipelineId the Long
	 */
	public Long insertPipelineInfo(String pipelineName, String applicationName) throws SQLException {

		String tableName = "tpipeline_info";
		Long pipelineId = -1l;
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(INSERT_CLAUSE + " " + tableName + " (pipeline_name, application_id) VALUES (?,?))");
		queryStatement.append("ON CONFLICT (pipeline_name,application_id) DO UPDATE ");
		queryStatement.append("SET pipeline_name=?,");
		queryStatement.append("application_id=?");
		queryStatement.append("returning pipeline_id;");
		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, pipelineName);
			preparedStatement.setLong(2, jobInfoDL.getApplicationId(applicationName));
			preparedStatement.setString(3, pipelineName);
			preparedStatement.setLong(4, jobInfoDL.getApplicationId(applicationName));
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				pipelineId = rs.getLong(1);
			}
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the data in pipeline_info:", e);
			throw e;

		} finally {
			if (rs != null) {
				rs.close();
			}
		}

		return pipelineId;
	}

	/**
	 * insert pipeline json details
	 * 
	 * @param idp the IDPJob
	 * 
	 * @return Integer
	 */
	public Integer insertPipelineJsonDetails(IDPJob idp, String active) {
		logger.info("Inserting data in pipeline_json_details");
		String tableName = "tpipeline_info";
		StringBuilder queryStatement = new StringBuilder();

		Gson gson = new Gson();

		queryStatement.append(INSERT_CLAUSE + " " + tableName);

		queryStatement
				.append(" (pipeline_name, entity_info, application_id,technology,build_tool) VALUES (?,?,?,?,?) ");
		queryStatement.append("ON CONFLICT (pipeline_name,application_id) DO UPDATE ");
		queryStatement.append("SET entity_info=? , technology=?, build_tool=? , active = " + active + ";");
		Long applicationId = -1l;
		String applicationName = "";
		applicationName = idp.getBasicInfo().getApplicationName();
		applicationId = jobInfoDL.getApplicationId(applicationName);
		String idpJob = gson.toJson(idp);
		String ecryptedJson = EncryptionUtil.encrypt(idpJob);

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, idp.getBasicInfo().getPipelineName());
			preparedStatement.setBytes(2, ecryptedJson.getBytes());
			preparedStatement.setLong(3, applicationId);
			if (idp.getBasicInfo().getMasterSequence() != null
					&& WORKFLOW.equalsIgnoreCase(idp.getBasicInfo().getMasterSequence())) {
				preparedStatement.setString(4, idp.getBasicInfo().getMasterSequence());
			} else {
				preparedStatement.setString(4, idp.getCode().getTechnology());
			}
			if (idp.getBasicInfo().getMasterSequence() != null
					&& WORKFLOW.equalsIgnoreCase(idp.getBasicInfo().getMasterSequence())) {
				preparedStatement.setString(5, idp.getBasicInfo().getMasterSequence());
			} else {
				preparedStatement.setString(5, idp.getBuildInfo().getBuildtool());
			}
			preparedStatement.setObject(6, ecryptedJson.getBytes());
			if (idp.getBasicInfo().getMasterSequence() != null
					&& WORKFLOW.equalsIgnoreCase(idp.getBasicInfo().getMasterSequence())) {
				preparedStatement.setString(7, idp.getBasicInfo().getMasterSequence());
			} else {
				preparedStatement.setString(7, idp.getCode().getTechnology());
			}
			if (idp.getBasicInfo().getMasterSequence() != null
					&& WORKFLOW.equalsIgnoreCase(idp.getBasicInfo().getMasterSequence())) {
				preparedStatement.setString(8, idp.getBasicInfo().getMasterSequence());
			} else {
				preparedStatement.setString(8, idp.getBuildInfo().getBuildtool());
			}
			preparedStatement.executeUpdate();

		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the data in pipeline_json_details:", e);

		}

		return 1;
	}

	/**
	 * insert application details
	 * 
	 * @param appInfo the ApplicationInfo
	 * 
	 * @return Integer
	 */
	public Integer insertApplicationDetails(ApplicationInfo appInfo) {

		String tableName = "tapplication_info";
		StringBuilder queryStatement = new StringBuilder();
		ResultSet rs = null;
		int applicationId = 0;
		Gson gson = new Gson();

		queryStatement.append(INSERT_CLAUSE + " " + tableName);
		queryStatement.append(
				" (application_name,platform ,entity_info) VALUES (?,?,cast(? as json)) returning application_id;");
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, appInfo.getApplicationName());
			preparedStatement.setObject(2, Config.IDP_APPLICATION);
			preparedStatement.setObject(3, gson.toJson(appInfo));
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				applicationId = rs.getInt(1);
			}
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the data in tapplication_info:", e);

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("Postgres Error while closing resultset", e);
				}
			}
		}

		return applicationId;
	}

	public Integer updateapplicationDetail(ApplicationInfo appInfo) {
		String query = "UPDATE tapplication_info SET  entity_info=cast(? as json) WHERE application_name=? returning application_id";
		Gson gson = new Gson();
		ResultSet rs = null;
		int applicationId = 0;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setObject(1, gson.toJson(appInfo));
			preparedStatement.setString(2, appInfo.getApplicationName());

			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				applicationId = rs.getInt(1);
			}

		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while updating the data in tapplication_info:", e);

		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("Issue in closing resultset:", e);
				}
			}
		}

		return applicationId;

	}

	/**
	 * insert slave details
	 * 
	 * @param slave   the SlaveDetail
	 * @param appName the String
	 * 
	 * @return Integer
	 */
	public Integer insertSlaveDetails(SlavesDetail slave, String appName) {

		String tableName = "tslave_detials";
		StringBuilder queryStatement = new StringBuilder();
		boolean updateFlag = getJobDetails.getUpdateFlag(slave, appName);

		if (updateFlag) {
			updateSlaveDetails(slave, appName);
		} else {
			queryStatement.append(INSERT_CLAUSE + " " + tableName
					+ " (application_id, slave_name, \"slaveOS\", usage, build, deploy, test, labels");
			queryStatement.append(") VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
				preparedStatement.setLong(1, jobInfoDL.getApplicationId(appName));
				preparedStatement.setString(2, slave.getSlaveName());
				preparedStatement.setString(3, slave.getBuildServerOS());
				preparedStatement.setString(4, slave.getSlaveUsage());
				preparedStatement.setString(5, slave.getBuild());
				preparedStatement.setString(6, slave.getDeploy());
				preparedStatement.setString(7, slave.getTest());
				preparedStatement.setString(8, slave.getLabels());
				preparedStatement.executeUpdate();
			}

			catch (SQLException | NullPointerException e) {
				logger.error("Postgres Error while inserting the data in tslave_detials:", e);
			}

		}

		return 1;
	}

	/**
	 * update slave details
	 * 
	 * @param slave   the SlaveDetail
	 * @param appName the String
	 * 
	 * @return Integer
	 */
	public Integer updateSlaveDetails(SlavesDetail slave, String appName) {

		String tableName = "tslave_detials";
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(UPDATE_CLAUSE + " " + tableName + " " + SET_CLAUSE
				+ "  \"slaveOS\"= ?, usage = ?, build = ?, deploy = ?, test = ?, labels = ?");
		queryStatement.append(" " + WHERE_CLAUSE + " application_id=? and  slave_name=?");
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, slave.getBuildServerOS());
			preparedStatement.setString(2, slave.getSlaveUsage());
			preparedStatement.setString(3, slave.getBuild());
			preparedStatement.setString(4, slave.getDeploy());
			preparedStatement.setString(5, slave.getTest());
			preparedStatement.setString(6, slave.getLabels());
			preparedStatement.setLong(7, jobInfoDL.getApplicationId(appName));
			preparedStatement.setString(8, slave.getSlaveName());
			preparedStatement.executeUpdate();

		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while updating the data in tslave_detials:", e);

		}

		return 1;
	}

	/**
	 * insert trigger history
	 * 
	 * @param userId  the String
	 * @param emailId the String
	 * @param status  the Boolean
	 * 
	 * @return Integer
	 */
	public Integer insertTriggerHistory(TriggerParameters triggerParameters) {
		Gson gson = new Gson();
		Integer productId = null;
		String tableName = "ttrigger_history";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(INSERT_CLAUSE + " " + tableName
				+ " (pipeline_id, trigger_entity, release_number) VALUES (?, cast(? as json), ?);");
		ResultSet resultSet = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString(),
						new String[] { "trigger_id" })) {
			preparedStatement.setLong(1, jobInfoDL.getPipelineId(triggerParameters.getPipelineName(),
					triggerParameters.getApplicationName()));
			preparedStatement.setString(2, gson.toJson(triggerParameters));
			preparedStatement.setString(3, triggerParameters.getReleaseNumber());

			preparedStatement.executeUpdate();

			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				productId = (int) resultSet.getLong(1);
			}
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the data in ttrigger_history:", e);
			return -1;
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					logger.error("Postgres Error while closing resultset:", e);
				}
			}
		}

		return productId;

	}

	/**
	 * insert Organization Info
	 * 
	 * @param userId  the String
	 * @param emailId the String
	 * @param status  the Boolean
	 * 
	 * @return Integer
	 */
	public Long insertOrgInfo(String orgName, String domain) {
		String tableName = "torg_info";
		Long orgId = 0L;
		ResultSet rs = null;
		StringBuilder queryStatement = new StringBuilder();
		queryStatement
				.append(INSERT_CLAUSE + " " + tableName + " (org_name, org_domain) VALUES (?, ?) returning org_id;");
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, orgName);
			preparedStatement.setString(2, domain);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				orgId = rs.getLong(1);
			}
		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the data in torg_info:", e);
			return -1L;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					logger.error(e2.getMessage(), e2);
				}
			}

		}
		return orgId;
	}

	/**
	 * insert users
	 * 
	 * @param userId  the String
	 * @param emailId the String
	 * @param status  the Boolean
	 * 
	 * @return Integer
	 */
	public Integer insertUsers(String userId, String emailId, Boolean status, Long orgId) {
		String tableName = "tuser_info";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(INSERT_CLAUSE + " " + tableName + " (user_id, email_id, enabled, org_id) VALUES (?, ?, ?, ?);");
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, userId.toLowerCase());
			preparedStatement.setString(2, emailId);
			preparedStatement.setBoolean(3, status);
			preparedStatement.setFloat(4, orgId);
			preparedStatement.executeUpdate();
		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the data in tuser_info:", e);
			return -1;
		}
		return 1;
	}

	/**
	 * insert users
	 * 
	 * @param userId   the String
	 * @param emailId  the String
	 * @param status   the Boolean
	 * @param roleName the String
	 * 
	 * @return Integer
	 */
	public Integer insertUsers(String userId, String emailId, Boolean status, Long orgId, String roleName) {
		String tableName = "tuser_info";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(INSERT_CLAUSE + " " + tableName
				+ " (user_id, email_id, enabled,base_role_id,org_id) VALUES (?, ?, ?,?,?);");
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, emailId);
			preparedStatement.setBoolean(3, status);
			preparedStatement.setLong(4, jobInfoDL.getRoleId(roleName));
			preparedStatement.setLong(5, orgId);
			preparedStatement.executeUpdate();
		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the data in tuser_info:", e);
			return -1;
		}
		return 1;
	}

	/**
	 * insert application roles
	 * 
	 * @param userId  the String
	 * @param role    the String
	 * @param appName the String
	 * 
	 * @return Integer
	 */
	public Integer insertApplicationRoles(String userId, String role, String appName) {

		String tableName = "tapplication_roles";
		StringBuilder queryStatement = new StringBuilder();

		queryStatement
				.append(INSERT_CLAUSE + " " + tableName + " (application_id, role_id, user_id) VALUES (?, ?, ?);");
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setLong(1, jobInfoDL.getApplicationId(appName));
			preparedStatement.setLong(2, jobInfoDL.getRoleId(role));
			preparedStatement.setString(3, userId);
			preparedStatement.executeUpdate();

		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the data in tapplication_roles:", e);
			return -1;
		}

		return 1;
	}

	/**
	 * Insert roles for app
	 * 
	 * @param userIdList
	 * @param role
	 * @param appName
	 * @return integer
	 */
	public Integer insertMultipleApplicationRoles(List<String> userIdList, String role, String appName) {

		String tableName = "tapplication_roles";
		StringBuilder queryStatement = new StringBuilder();
		try {

			Long applicationId = jobInfoDL.getApplicationId(appName);
			Long roleId = jobInfoDL.getRoleId(role);

			for (String userId : userIdList) {
				queryStatement.append(INSERT_CLAUSE + " " + tableName + " (application_id, role_id, user_id) VALUES (\'"
						+ applicationId + "\', \'" + roleId + "\' , \'" + userId + "\');");
			}

			try (Connection connection = postGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
				preparedStatement.executeUpdate();
			}
		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the data in tapplication_roles:", e);
			return -1;
		}

		return 1;
	}

	/**
	 * Updates job status
	 * 
	 * @param getJob
	 * @param ca
	 * @param ct
	 * @return integer
	 */
	public Integer updateJobdetailsStatus(GetJob getJob, Long ca, Long ct) {

		String queryStatement = "UPDATE public.tdevops_build_details   SET latest_ca_build_number=?, latest_ct_build_number=? WHERE application_name=? and  pipeline_name=? and build_number=? ";

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			preparedStatement.setLong(1, ca);
			preparedStatement.setLong(2, ct);
			preparedStatement.setString(3, getJob.getApplicationName());
			preparedStatement.setString(4, getJob.getPipelineName());
			preparedStatement.setLong(5, Long.parseLong(getJob.getBuildNumber()));
			preparedStatement.executeUpdate();
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while updating the data in tdevops_build_details:", e);
			logger.error(e.getMessage(), e);
			return -1;
		}

		return 1;
	}

	/**
	 * Inserts job status details
	 * 
	 * @param getJob
	 * @param ca
	 * @param ct
	 * @return integer
	 */
	public Integer insertJobdetailsStatus(GetJob getJob, Long ca, Long ct) {

		String queryStatement = "INSERT INTO public.tdevops_build_details (application_name, pipeline_name,  latest_ca_build_number, latest_ct_build_number,build_number) VALUES (?, ?, ?, ?, ?);";

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			preparedStatement.setString(1, getJob.getApplicationName());
			preparedStatement.setString(2, getJob.getPipelineName());
			preparedStatement.setLong(3, ca);
			preparedStatement.setLong(4, ct);
			preparedStatement.setLong(5, Long.parseLong(getJob.getBuildNumber()));
			preparedStatement.executeUpdate();

		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the data in tuser_info:", e);
			return -1;
		}

		return 1;
	}

	/**
	 * insert Additional Job Parameter details
	 * 
	 * @param jobParam     the additional job parameter details
	 * @param appName      the String
	 * @param pipelineName the String
	 * 
	 * @return Integer
	 */
	public Integer insertAdditionalJobParamDetails(JobParam jobParam, String appName, String pipelineName) {

		String tableName = "tadditional_job_param_details";
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(INSERT_CLAUSE + " " + tableName
				+ " (application_id, pipeline_id, param_name, param_value, param_type, static ");
		queryStatement.append(") VALUES (?, ?, ?, ?, ?, ?) ");
		queryStatement.append("ON CONFLICT (pipeline_id,param_name) DO UPDATE ");
		queryStatement.append("SET param_value=? ,param_type=?,static=? ");
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setLong(1, jobInfoDL.getApplicationId(appName));
			preparedStatement.setLong(2, jobInfoDL.getPipelineId(pipelineName, appName));
			preparedStatement.setString(3, jobParam.getJobParamName());
			preparedStatement.setString(4, jobParam.getJobParamValue());
			preparedStatement.setString(5, jobParam.getJobType());

			if (null != jobParam.getJobParamSatic()) {
				preparedStatement.setBoolean(6, true);
			} else {
				preparedStatement.setBoolean(6, false);
			}

			preparedStatement.setString(7, jobParam.getJobParamValue());
			preparedStatement.setString(8, jobParam.getJobType());

			if (null != jobParam.getJobParamSatic()) {
				preparedStatement.setBoolean(9, true);
			} else {
				preparedStatement.setBoolean(9, false);
			}
			preparedStatement.executeUpdate();
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the data in tadditional_joobparam:", e);

		}

		return 1;
	}

	/**
	 * Updates pipeline trigger history
	 * 
	 * @param id
	 * @param version
	 * @param artifactName
	 * @return integer
	 */
	public Integer updateTriggerHistory(int id, String version, String artifactName) {
		Integer productId = null;
		String queryStatement = "Update ttrigger_history set version=? , artifact_name = ? where trigger_id=?";
		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			preparedStatement.setString(1, version);
			preparedStatement.setString(2, artifactName);
			preparedStatement.setInt(3, id);
			preparedStatement.executeUpdate();

			rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				productId = (int) rs.getLong(1);
			}
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while updating the data in ttrigger_history:", e);
			return -1;
		}finally {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("Postgres Error while closing stream:", e);
			}
		}

		return productId;

	}

	/**
	 * 
	 * Deletes job param details
	 * 
	 * @param pipelineId
	 */
	public void deleteAdditionalJobParamDetails(long pipelineId) {

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append("DELETE FROM ");
		queryStatement.append(" tadditional_job_param_details ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" pipeline_id = ? ;");

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setLong(1, pipelineId);
			preparedStatement.executeUpdate();

		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while Deleting custom job parameters:", e);

		}
	}

	/**
	 * Add notification for pipeline
	 * 
	 * @param pipelineName
	 * @param status
	 * @param user
	 */
	public void addNotification(String pipelineName, String status, String user) {
		logger.info("pipeline insertion into notification");
		String tableName = "tnotification_info";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(INSERT_CLAUSE);
		queryStatement.append(" ");
		queryStatement.append(tableName);
		queryStatement.append(" (status,pipeline_name,username)");
		queryStatement.append(" VALUES (?, ?, ?) ");
		logger.info("query s");

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, status);
			preparedStatement.setString(2, pipelineName);
			preparedStatement.setString(3, user);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			logger.error("Postgres Error while inserting tsap_rebase_details", e);
		}

	}


}
