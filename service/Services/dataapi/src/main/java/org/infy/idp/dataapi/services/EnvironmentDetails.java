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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.infy.entities.artifact.Artifact;
import org.infy.entities.artifact.ArtifactDetails;
import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.entities.packagecontent.PackageContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * The class EnvironmentDetails contains methods related to environment details
 * and owners
 * 
 * @author Infosys
 *
 */
@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class EnvironmentDetails {

	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;

	protected Logger logger = LoggerFactory.getLogger(EnvironmentDetails.class);
	private static final String WHERE_CLAUSE = " WHERE ";
	private static final String INSERT_CLAUSE = " INSERT INTO ";
	private static final String AND_CLAUSE = " AND ";
	private static final String SELECT_CLAUSE = " SELECT ";
	private static final String FROM_CLAUSE = " FROM ";
	private static final String ORDER_BY = " ORDER BY ";

	/**
	 * Inserts env for application
	 * 
	 * @param environmentName
	 * @param applicationId
	 * @throws SQLException
	 */
	public void insertEnvironment(String environmentName, int applicationId) throws SQLException {

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(INSERT_CLAUSE);
		queryStatement.append(" public.tenvironment_master( ");
		queryStatement.append(" environment_name, application_id) ");
		queryStatement.append(" VALUES ( ?, ?) ");
		queryStatement.append(" ON CONFLICT (environment_name , application_id) DO UPDATE ");
		queryStatement.append(" SET environment_name = ? ;");

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setString(1, environmentName);
			preparedStatement.setInt(2, applicationId);
			preparedStatement.setString(3, environmentName);
			preparedStatement.executeUpdate();

		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		}

	}

	/**
	 * Inserts env owners and types
	 * 
	 * @param environmentId
	 * @param owners
	 * @param type
	 * @throws SQLException
	 */
	public void insertEnvironmentOwners(int environmentId, String owners, String type) throws SQLException {

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(INSERT_CLAUSE);
		queryStatement.append(" public.tenvironment_owner( ");
		queryStatement.append(" env_id, owner_name, owner_type) ");
		queryStatement.append(" VALUES ( ? , ? , ?); ");

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			String[] envOwners = owners.split(",");

			for (String envOwner : envOwners) {

				preparedStatement.setInt(1, environmentId);
				preparedStatement.setString(2, envOwner);
				preparedStatement.setString(3, type);
				preparedStatement.executeUpdate();

			}

		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		}

	}

	/**
	 * Returns env list of specified app owned by specified owner
	 * 
	 * @param ownerName
	 * @param ownerType
	 * @param appName
	 * @return list of strings
	 * @throws SQLException
	 */
	public List<String> getEnvironmentOwners(String ownerName, String ownerType, String appName) throws SQLException {

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(" SELECT environment_name ");
		queryStatement
				.append(" FROM public.tenvironment_master, public.tenvironment_owner , public.tapplication_info ");
		queryStatement.append(" WHERE public.tenvironment_master.env_id = public.tenvironment_owner.env_id ");
		queryStatement
				.append(" AND public.tenvironment_master.application_id = public.tapplication_info.application_id ");
		queryStatement.append(" AND owner_name = ? ");
		queryStatement.append(" AND application_name = ? ");
		queryStatement.append(" AND owner_type = ? ");

		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setString(1, ownerName);
			preparedStatement.setString(2, appName);
			preparedStatement.setString(3, ownerType);
			rs = preparedStatement.executeQuery();

			List<String> envList = new ArrayList<>();
			while (rs.next()) {
				String envName = rs.getString("environment_name");
				envList.add(envName);
			}

			return envList;

		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}

	/**
	 * Deletes env owners 
	 * 
	 * @param environmentId
	 * @throws SQLException
	 */
	public void deleteEnvironmentOwners(int environmentId) throws SQLException {
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append("DELETE FROM ");
		queryStatement.append(" public.tenvironment_owner ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" env_id = ? ;");

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setInt(1, environmentId);
			preparedStatement.executeUpdate();

		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		}

	}

	/**
	 * Returns env id of the specified env name and app id
	 * @param environmentName
	 * @param applicationId
	 * @return int
	 * @throws SQLException
	 */
	public int getEnvironmentId(String environmentName, long applicationId) throws SQLException {

		int envId = -1;
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(" env_id ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(" public.tenvironment_master ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" environment_name LIKE ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(" application_id = ? ; ");

		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setString(1, environmentName);
			preparedStatement.setLong(2, applicationId);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				envId = rs.getInt("env_id");
			}

			return envId;
		} catch (SQLException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();

			}
		}

	}

	

	/**
	 *  
	 * @param releaseId
	 * @return int
	 * @throws SQLException
	 */
	public int getPathCount(int releaseId) throws SQLException {

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append("SELECT COUNT(DISTINCT path_id) ");
		queryStatement.append(" FROM trelease_path_config ");
		queryStatement.append(" WHERE release_id = ? ");
		int count = 0;
		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setInt(1, releaseId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}

			return count;

		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();

			}
		}
	}

	

	/**
	 *  Return artifact list
	 *  
	 * @param envId
	 * @param releaseId
	 * @param status
	 * @return list of strings
	 * @throws SQLException
	 */
	public List<String> getArtifactList(int envId, int releaseId, String status) throws SQLException {

		List<String> artifactList = new ArrayList<>();

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(" artifact_name ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(" public.tartifact_approval ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" env_id = ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(" release_id = ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(" status LIKE ? ");
		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setInt(1, envId);
			preparedStatement.setInt(2, releaseId);
			preparedStatement.setString(3, status);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				String artifactName = rs.getString("artifact_name");
				artifactList.add(artifactName);

			}

			return artifactList;

		} catch (SQLException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();

			}
		}

	}

	/**
	 * Return artifact list of specified artifact name
	 * 
	 * @param artifactName
	 * @return artifact
	 * @throws SQLException
	 */
	public Artifact getArtifactDetails(String artifactName) throws SQLException {

		Artifact artifact = new Artifact();
		ResultSet rs = null;
		List<ArtifactDetails> artifactDetailsList = new ArrayList<>();
		artifact.setArtifactName(artifactName);
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(" tartifact_history.status, remark, environment, env_owner, action_time ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(" public.tartifact_history , public.tartifact_approval ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" tartifact_history.artifact_id = tartifact_approval.artifact_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(" tartifact_approval.artifact_name LIKE ? ");
		queryStatement.append(ORDER_BY);
		queryStatement.append(" seq_id ASC ");

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setString(1, artifactName);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				ArtifactDetails artifactDetails = new ArtifactDetails();
				artifactDetails.setEnvironment(rs.getString("environment"));
				artifactDetails.setOwner(rs.getString("env_owner"));
				artifactDetails.setRemark(rs.getString("remark"));
				artifactDetails.setStatus(rs.getString("status"));
				artifactDetails.setActionTime(rs.getString("action_time"));

				artifactDetailsList.add(artifactDetails);

			}

			artifact.setArtifactDetails(artifactDetailsList);

			return artifact;

		} catch (SQLException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();

			}
		}

	}

	/**
	 * Returns package details of specified artifact
	 * 
	 * @param artifactName
	 * @return package content
	 * @throws Exception
	 */
	public PackageContent getPackageContent(String artifactName) throws Exception {

		PackageContent packageContent = new PackageContent();
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(" package_content ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(" public.tartifact_approval ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" tartifact_approval.artifact_name LIKE ? ");
		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setString(1, artifactName);
			Gson gson = new Gson();
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				if (rs.getObject(1) != null)
					packageContent = gson.fromJson(rs.getObject(1).toString(), PackageContent.class);
			}

			return packageContent;

		} catch (SQLException | NullPointerException e) {
			throw e;
		} finally {
			if (rs != null) {
				rs.close();

			}
		}

	}

	/**
	 * Returns parent env id of specified env id and release id
	 * 
	 * @param envId
	 * @param releaseId
	 * @return list of integer
	 * @throws SQLException
	 */
	public List<Integer> getParentEnvId(int envId, int releaseId) throws SQLException {

		List<Integer> envList = new ArrayList<>();

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(" parent_env_id ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(" public.trelease_path_config ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" env_id = ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(" release_id = ? ");
		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setInt(1, envId);
			preparedStatement.setInt(2, releaseId);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				int parentId = rs.getInt("parent_env_id");
				envList.add(parentId);

			}

			return envList;

		} catch (SQLException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();

			}
		}
	}

	/**
	 * Returns distinct env names of specified release
	 * 
	 * @param releaseId
	 * @return list of string
	 * @throws SQLException
	 */
	public List<String> getFirstEnv(int releaseId) throws SQLException {

		List<String> envList = new ArrayList<>();

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(" DISTINCT environment_name ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(" public.trelease_path_config,tenvironment_master ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" trelease_path_config.env_id =tenvironment_master.env_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(" parent_env_id = 0 ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append(" release_id = ? ");
		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setInt(1, releaseId);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				String env = rs.getString("environment_name");
				envList.add(env);

			}

			return envList;

		} catch (SQLException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}

	/**
	 * Inserts artifact with provided details
	 * 
	 * @param envId
	 * @param releaseId
	 * @param artifactName
	 * @param status
	 * @throws SQLException
	 */
	public void insertArtifact(int envId, int releaseId, String artifactName, String status) throws SQLException {

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append("INSERT INTO public.tartifact_approval( ");
		queryStatement.append(" artifact_name, release_id, env_id, status) ");
		queryStatement.append(" VALUES ( ? , ?, ?, ? ) ");
		queryStatement.append(" ON CONFLICT (artifact_name , release_id , env_id ) DO UPDATE ");
		queryStatement.append(" SET artifact_name = ? ; ");

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setString(1, artifactName);
			preparedStatement.setInt(2, releaseId);
			preparedStatement.setInt(3, envId);
			preparedStatement.setString(4, status);
			preparedStatement.setString(5, artifactName);
			preparedStatement.executeUpdate();

		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		}

	}

	/**
	 * Updates artifact status
	 * 
	 * @param envId
	 * @param releaseId
	 * @param artifactName
	 * @param status
	 * @throws SQLException
	 */
	public void updateArtifactStatus(int envId, int releaseId, String artifactName, String status) throws SQLException {

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append("UPDATE public.tartifact_approval ");
		queryStatement.append(" SET status = ? ");
		queryStatement.append(" WHERE artifact_name = ? ");
		queryStatement.append(" AND release_id = ? ");
		queryStatement.append(" AND env_id = ? ; ");

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setString(1, status);
			preparedStatement.setString(2, artifactName);
			preparedStatement.setInt(3, releaseId);
			preparedStatement.setInt(4, envId);

			preparedStatement.executeUpdate();

		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		}

	}

	/**
	 * Return env count
	 * 
	 * @return int
	 * @throws SQLException
	 */
	public int getEnvCount() throws SQLException {

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(" count(*) ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(" tenvironment_master ");

		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			rs = preparedStatement.executeQuery();
			int count = 0;

			while (rs.next()) {
				count = rs.getInt(1);
			}

			return count;

		} catch (SQLException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();

			}
		}

	}

	/**
	 * Returns artifact id of specified artifact name
	 * 
	 * @param artifactName
	 * @return int
	 * @throws SQLException
	 */
	public int getArtifactId(String artifactName) throws SQLException {

		int artifactId = -1;
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(" artifact_id ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(" public.tartifact_approval ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" artifact_name LIKE ? ;");

		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setString(1, artifactName);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				artifactId = rs.getInt("artifact_id");
			}

			return artifactId;
		} catch (SQLException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();

			}
		}

	}

	/**
	 * Inserts artifact details
	 * 
	 * @param artifactId
	 * @param status
	 * @param remark
	 * @param environment
	 * @param env_owner
	 * @throws SQLException
	 */
	public void insertArtifactDetails(int artifactId, String status, String remark, String environment,
			String env_owner) throws SQLException {

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append("INSERT INTO public.tartifact_history( ");
		queryStatement.append(" artifact_id, status, remark, environment, env_owner, action_time) ");
		queryStatement.append(" VALUES ( ?, ?, ?, ?, ?, ?) ");

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setInt(1, artifactId);
			preparedStatement.setString(2, status);
			preparedStatement.setString(3, remark);
			preparedStatement.setString(4, environment);
			preparedStatement.setString(5, env_owner);
			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
			preparedStatement.setTimestamp(6, currentTime);
			preparedStatement.executeUpdate();

		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		}

	}

	/**
	 * Updates artifact contents
	 * 
	 * @param artifactId
	 * @param packageContent
	 * @throws SQLException
	 */
	public void updateArtifactContent(int artifactId, PackageContent packageContent) throws SQLException {

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append("UPDATE public.tartifact_approval ");
		queryStatement.append(" SET package_content = cast(? as json) ");
		queryStatement.append(" WHERE artifact_id = ? ");

		Gson gson = new Gson();

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setObject(1, gson.toJson(packageContent));
			preparedStatement.setInt(2, artifactId);
			preparedStatement.executeUpdate();

		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		}

	}

}
