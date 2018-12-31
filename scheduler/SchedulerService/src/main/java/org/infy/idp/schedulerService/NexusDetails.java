/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.schedulerservice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.infy.idp.configure.PostGreSqlDbContext;
import org.infy.idp.entities.DeployArtifact;
import org.infy.idp.entities.Nexus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

@Component
public class NexusDetails {

	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;

	@Autowired
	private ArtifactHistory artifactHistory;

	private static final String WHERE_CLAUSE = " WHERE ";
	private static final String AND_CLAUSE = " AND ";
	private static final String SELECT_CLAUSE = " SELECT ";
	private static final String FROM_CLAUSE = " FROM ";
	private static final String APPLICATION_NAME = " application_name LIKE ? ";
	private static final String PIPELINE_NAME = " pipeline_name LIKE ? ";
	private static final String APPLICATION_ID = " tpipeline_info.application_id=tapplication_info.application_id ";
	private static final String ACTIVE_PIPELINE = " and active = true ";
	protected final String ERROR1 = "Postgres Error while fetching user details:";

	protected Logger logger = LoggerFactory.getLogger(NexusDetails.class);

	public String getPipelineInfo(String applicationName, String pipelineName) throws SQLException, NullPointerException

	{
		logger.debug("get pipeline info");
		String tableName = "tpipeline_info,tapplication_info";
		String column = "tpipeline_info.entity_info";

		String decryptedIDP = null;
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
				logger.debug("encrypted json============" + Arrays.toString(encryptedIDP));
				decryptedIDP = EncryptionUtil.decrypt(new String(encryptedIDP));
				logger.debug("decrypted json==========" + decryptedIDP);

			}
			return decryptedIDP;
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
					e.printStackTrace();
				}
			}
		}

	}

	public List<DeployArtifact> getNexus(String idpData, String appData, String applicationName, String pipelineName) {

		boolean artifact = false;
		String userName = null;
		String passWord = null;
		String nexusURL = null;
		String repoName = null;
		List<DeployArtifact> arList = new ArrayList();
		System.out.println("2");
		JSONObject idp = JSONObject.fromObject(idpData);
		JSONObject app = JSONObject.fromObject(appData);
		System.out.println("ok" + idp);
		System.out.println("ok1" + app);

		try {
			if (idp.has("buildInfo") && idp.getJSONObject("buildInfo").has("artifactToStage")
					&& idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").has("artifactRepoName")
					&& !idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").getString("artifactRepoName")
							.equalsIgnoreCase("na")
					&& !idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").getString("artifactRepoName")
							.equalsIgnoreCase("")) {
				userName = idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").getJSONObject("artifactRepo")
						.getString("repoUsername");
				passWord = idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").getJSONObject("artifactRepo")
						.getString("repoPassword");
				nexusURL = idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").getJSONObject("artifactRepo")
						.getString("repoURL");
				repoName = idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").getJSONObject("artifactRepo")
						.getString("repoName");
				System.out.println("3");
				artifact = true;

			} else if ((idp.has("buildInfo") && idp.getJSONObject("buildInfo").has("artifactToStage")
					&& (!idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").has("artifactRepoName")
							|| idp.getJSONObject("buildInfo").getJSONObject("artifactToStage")
									.getString("artifactRepoName").equalsIgnoreCase(" "))
					|| (idp.has("buildInfo") && !idp.getJSONObject("buildInfo").has("artifactToStage")))
					&& (app.has("artifactToStage") && app.getJSONObject("artifactToStage").has("artifactRepoName")
							&& !app.getJSONObject("artifactToStage").getString("artifactRepoName")
									.equalsIgnoreCase("na")
							&& !app.getJSONObject("artifactToStage").getString("artifactRepoName")
									.equalsIgnoreCase(""))) {

				userName = idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").getJSONObject("artifactRepo")
						.getString("repoUsername");
				passWord = idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").getJSONObject("artifactRepo")
						.getString("repoPassword");
				nexusURL = idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").getJSONObject("artifactRepo")
						.getString("repoURL");
				repoName = idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").getJSONObject("artifactRepo")
						.getString("repoName");
				System.out.println("4");
				artifact = true;

			}
			if (idp.getJSONObject("code").getString("technology").equalsIgnoreCase("J2EE/Ant")
					&& app.has("artifactToStage") && app.getJSONObject("artifactToStage").has("artifactRepo")
					&& !app.getJSONObject("artifactToStage").getString("artifactRepoName").equalsIgnoreCase("na")
					&& !app.getJSONObject("artifactToStage").getString("artifactRepoName").equalsIgnoreCase("")) {
				userName = idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").getJSONObject("artifactRepo")
						.getString("repoUsername");
				passWord = idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").getJSONObject("artifactRepo")
						.getString("repoPassword");
				nexusURL = idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").getJSONObject("artifactRepo")
						.getString("repoURL");
				repoName = idp.getJSONObject("buildInfo").getJSONObject("artifactToStage").getJSONObject("artifactRepo")
						.getString("repoName");
				System.out.println("6");
				artifact = true;

			}
			if (artifact) {
				String urlToHit = "http://" + nexusURL + "/service/siesta/rest/beta/search?repository=" + repoName
						+ "&group=" + applicationName + "&name=" + pipelineName;

				String response = this.getInputStream(userName, passWord, urlToHit);
				logger.info("abcdefg" + response);
				Gson gson = new Gson();
				Nexus nexus = gson.fromJson(response, Nexus.class);
				logger.debug(gson.toJson(nexus));
				logger.info(gson.toJson(nexus));
				logger.info(gson.toJson(nexus));
				if (nexus != null && nexus.getItems() != null && nexus.getItems().size() != 0) {
					for (int i = 0; i < nexus.getItems().size(); i++) {
						DeployArtifact d1 = new DeployArtifact();
						d1.setArtifactID(nexus.getItems().get(i).getName());
						d1.setGroupId(nexus.getItems().get(i).getGroup());
						d1.setVersion(nexus.getItems().get(i).getVersion());
						d1.setDownloadURL(nexus.getItems().get(i).getAssets().get(0).getDownloadUrl());
						d1.setArtifactName(nexus.getItems().get(i).getGroup() + "_" + nexus.getItems().get(i).getName()
								+ "_" + nexus.getItems().get(i).getVersion());

						arList.add(d1);

					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}

		return arList;
	}

	public String getApplication(String applicationName) throws SQLException {

		logger.info("Getting application details");
		String app = null;
		String tableName = "public.tapplication_info";
		String column = "public.tapplication_info.entity_info";
		// ApplicationInfo app = new ApplicationInfo();

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE + column + FROM_CLAUSE + tableName + WHERE_CLAUSE + APPLICATION_NAME);

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, applicationName);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				app = rs.getObject(1).toString();

			}

			return app;
		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching ApplicationInfo entity values :", e);
			throw e;

		}

		finally {
			try {
				rs.close();
				rs = null;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

	}

	/**
	 * 
	 * @param userName
	 * @param passWord
	 * @param urltohit
	 * @return String
	 */
	public String getInputStream(String userName, String passWord, String urltohit) {

		System.out.println("7");
		String urlToHit = urltohit;

		String authString = userName + ":" + passWord;

		String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
		URL url;
		try {
			url = new URL(urlToHit);

			URLConnection urlconnection = url.openConnection();
			urlconnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			InputStream is = urlconnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			String response = "";
			String line = br.readLine();
			while (line != null) {
				response += line + "\n";
				line = br.readLine();
			}
			return response;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;

	}

	/**
	 * 
	 * @param artifactInputs
	 * @return TriggerDeployArtifact
	 * @throws SQLException
	 */
	public List<DeployArtifact> getArtifact(String applicationName, String pipelineName, String releaseNumber,
			String environmentName) throws SQLException {

		try {

			// TriggerDeployArtifact triggerDeployArtifact = new TriggerDeployArtifact();

			String idpData = getPipelineInfo(applicationName, pipelineName);
			String appData = getApplication(applicationName);
			List<DeployArtifact> nexusArtifact = getNexus(idpData, appData, applicationName, pipelineName);
			int applicationId = artifactHistory.getApplicationId(applicationName).intValue();
			int envId = artifactHistory.getEnvironmentId(environmentName, applicationId);
			int releaseId = artifactHistory.getReleaseId(applicationName, pipelineName, releaseNumber, "on");
			System.out.println("DAsdas" + envId + "adsd" + releaseId);
			int pathCount = getPathCount(releaseId);

			if (pathCount == 0) {
				// triggerDeployArtifact.setArtifactList(nexuxArtifact);
				return nexusArtifact;
			}

			List<Integer> prevEnvIdList = getParentEnvId(envId, releaseId);
			List<String> envArtifact = new ArrayList<>();
			for (int prevEnvId : prevEnvIdList) {
				if (prevEnvId != 0) {
					List<String> artifactList = getArtifactList(prevEnvId, releaseId, "approved");
					envArtifact = ListUtils.union(envArtifact, artifactList);
				} else {
					// triggerDeployArtifact.setArtifactList(nexuxArtifact);
					return nexusArtifact;
				}
			}

			List<DeployArtifact> envFilterArtifact = new ArrayList<>();

			for (DeployArtifact deployArtifact : nexusArtifact) {

				if (envArtifact.stream().anyMatch(str -> str.equalsIgnoreCase(deployArtifact.getArtifactName()))) {

					envFilterArtifact.add(deployArtifact);
				}

			}

			// triggerDeployArtifact.setArtifactList(envFilterArtifact);

			return envFilterArtifact;

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}

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
		}finally{
	   		if(rs != null){
	   			try{
	   			rs.close();
	   			}
	   			catch(SQLException e){
	   				logger.error("Postgres Error while closing resultset :",e);
	   			}
	   		}
	   	}
		
	}

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
		}finally{
	   		if(rs != null){
	   			try{
	   			rs.close();
	   			}
	   			catch(SQLException e){
	   				logger.error("Postgres Error while closing resultset :",e);
	   			}
	   		}
	   	}

	}

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
		System.out.println("yooyy" + queryStatement);
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
		}finally{
	   		if(rs != null){
	   			try{
	   			rs.close();
	   			}
	   			catch(SQLException e){
	   				logger.error("Postgres Error while closing resultset :",e);
	   			}
	   		}
	   	}

	}

}
