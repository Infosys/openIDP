/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.dataapi.services;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.entities.jobs.basicinfo.AdditionalMailRecipients;
import org.infy.idp.entities.releasemanagerinfo.Release;
import org.infy.idp.entities.releasemanagerinfo.ReleaseManager;
import org.infy.idp.entities.releasemanagerinfo.ReleasePipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * The class ReleaseDetails contains methods related to release of pipelines
 * 
 * @author Infosys
 *
 */
@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class ReleaseDetails {

	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;

	@Autowired
	private DateFormatter datefunctions;
	
	protected Logger logger = LoggerFactory.getLogger(ReleaseDetails.class);
	private static final String WHERE_CLAUSE = " WHERE ";
	private static final String AND_CLAUSE = " AND ";
	private static final String SELECT_CLAUSE = " SELECT ";
	private static final String FROM_CLAUSE = " FROM ";
	private static final String ORDER_BY = " ORDER BY ";
	protected static final String ERROR1 = "Postgres Error while fetching user details:";

	/**
	 * Returns release details for speciifed pipelines
	 * 
	 * @param appName
	 * @param pipelineNames
	 * @return map
	 * @throws SQLException
	 */
	public Map<String, List<String>> getReleaseNum(String appName, List<String> pipelineNames) throws SQLException {

		final String releaseNumber = "release_number";
		final String pipelineName = "pipeline_name";
		String pipName;
		String relNumber;
		Map<String, List<String>> releasePipeline = new LinkedHashMap<>();

		if (null == appName || null == pipelineNames || pipelineNames.isEmpty())
			return releasePipeline;

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append("pipeline_name , release_number ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append("public.tpipeline_info,tapplication_info,trelease_info ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("tapplication_info.application_id = tpipeline_info.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tpipeline_info.application_id = trelease_info.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tpipeline_info.pipeline_id = trelease_info.pipeline_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tapplication_info.application_name LIKE ");
		queryStatement.append(" \'" + appName + "\' ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tpipeline_info.pipeline_name in (");

		int pipelineSize = pipelineNames.size();
		for (int pipIndex = 0; pipIndex < pipelineSize - 1; pipIndex++) {
			queryStatement.append(" \'" + pipelineNames.get(pipIndex) + "\' ,");
		}

		queryStatement.append(" \'" + pipelineNames.get(pipelineNames.size() - 1) + "\' ");
		queryStatement.append(")");
		queryStatement.append(ORDER_BY);
		queryStatement.append("tpipeline_info.pipeline_id");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				pipName = rs.getString(pipelineName);
				relNumber = rs.getString(releaseNumber);

				if (!releasePipeline.containsKey(pipName))
					releasePipeline.put(pipName, new ArrayList<String>());

				releasePipeline.get(pipName).add(relNumber);

			}
			return releasePipeline;
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}

	/**
	 * Returns release pipeline information
	 * 
	 * @param appName
	 * @param pipelineName
	 * @param status
	 * @return release pipeline
	 * @throws SQLException
	 */
	public ReleasePipeline getReleasePipelineInfo(String appName, String pipelineName, String status)
			throws SQLException {

		ReleasePipeline releasePipeline = new ReleasePipeline();

		if (null == appName || null == pipelineName || null == status)
			return releasePipeline;

		StringBuilder queryStatement = new StringBuilder();		

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append("pipeline_name , release_number, vsts_release_number, ");
		queryStatement.append(" expected_start_date, expected_end_date, actaul_start_date, actual_end_date, ");
		queryStatement.append("remarks, branch_list, release_id, status , email ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append("public.tpipeline_info,tapplication_info,trelease_info ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("tapplication_info.application_id = tpipeline_info.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tpipeline_info.application_id = trelease_info.application_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tpipeline_info.pipeline_id = trelease_info.pipeline_id ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tapplication_info.application_name LIKE ");
		queryStatement.append(" \'" + appName + "\' ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("tpipeline_info.pipeline_name LIKE");
		queryStatement.append(" \'" + pipelineName + "\' ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("status LIKE ");
		queryStatement.append(" \'" + status + "\' ");
		queryStatement.append(ORDER_BY);
		queryStatement.append("trelease_info.release_id");

		ResultSet rs = null;

		releasePipeline.setPipelineName(pipelineName);

		List<Release> releaseList = new ArrayList<>();

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {

			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				Release release = new Release();
				if (null != rs.getString("actual_end_date")) {
					String[] spiltDate = rs.getString("actual_end_date").split(" ");
					release.setActualEndDate(spiltDate[0]);
				}
				if (null != rs.getString("actaul_start_date")) {
					String[] spiltDate = rs.getString("actaul_start_date").split(" ");
					release.setActualStartDate(spiltDate[0]);
				}
				if (null != rs.getString("expected_start_date")) {
					String[] spiltDate = rs.getString("expected_start_date").split(" ");
					release.setExpectedStartDate(spiltDate[0]);
				}
				if (null != rs.getString("expected_end_date")) {
					String[] spiltDate = rs.getString("expected_end_date").split(" ");
					release.setExpectedEndDate(spiltDate[0]);
				}

				List<String> branch = new ArrayList<>();

				String branch_list = rs.getString("branch_list");
				if (null != branch_list) {
					String[] branchList = rs.getString("branch_list").split(",");

					for (String branchname : branchList)
						branch.add(branchname);

					release.setBranchList(branch);
				}

				String application_email = rs.getString("email");
				if (null != application_email) {
					String[] applicationEmail = rs.getString("email").split("#");

					AdditionalMailRecipients additionalMailRecipients = new AdditionalMailRecipients();
					if (applicationEmail.length >= 1)
						additionalMailRecipients.setApplicationTeam(applicationEmail[0]);
					if (applicationEmail.length == 2)
						additionalMailRecipients.setEmailIds(applicationEmail[1]);

					release.setAdditionalMailRecipients(additionalMailRecipients);
				}

				release.setStatus(rs.getString("status"));
				release.setReleaseNumber(rs.getString("release_number"));
				release.setHistoryRemarks(rs.getString("remarks"));
				release.setVstsReleaseName(rs.getString("vsts_release_number"));

				releaseList.add(release);

			}

			releasePipeline.setRelease(releaseList);

			return releasePipeline;
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

	}

	/**
	 * Returns release id based on provided details
	 * 
	 * @param appName
	 * @param pipelineName
	 * @param releaseNumber
	 * @param status
	 * @return int
	 * @throws SQLException
	 */
	public int getReleaseId(String appName, String pipelineName, String releaseNumber, String status)
			throws SQLException {

		int releaseId = 0;

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append("pipeline_name , release_number, vsts_release_number, ");
		queryStatement.append(" expected_start_date, expected_end_date, actaul_start_date, actual_end_date, ");
		queryStatement.append("remarks, branch_list, release_id, status , email ");
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
		queryStatement.append("status LIKE ? ");
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("release_number LIKE ? ");
		queryStatement.append(";");

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {

			preparedStatement.setString(1, appName);
			preparedStatement.setString(2, pipelineName);
			preparedStatement.setString(3, status);
			preparedStatement.setString(4, releaseNumber);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				releaseId = rs.getInt("release_id");
			}
		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching release_ID:", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

		return releaseId;

	}

	/**
	 * Inserts release information
	 * 
	 * @param releasemanager
	 * @throws SQLException
	 */
	public void insertReleaseInfo(ReleaseManager releasemanager) throws SQLException {

		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append("INSERT INTO public.trelease_info (");
		queryStatement.append("release_number, vsts_release_number, expected_start_date, expected_end_date, ");
		queryStatement.append("email, remarks , pipeline_id, application_id , status");
		queryStatement.append(") VALUES ( ? , ? ,  ? ,  ? ,  ? ,  ? , ? , ? , ? )");

		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		try (Connection connection = postGreSqlDbContext.getConnection();) {
			List<ReleasePipeline> releasePipelineList = releasemanager.getReleasePipeline();

			final int batchSize = 50;
			int count = 0;

			preparedStatement = connection.prepareStatement(queryStatement.toString());
			for (ReleasePipeline releasePipeline : releasePipelineList) {

				preparedStatement.setString(1, releasePipeline.getRelease().get(0).getReleaseNumber());
				preparedStatement.setString(2, releasePipeline.getRelease().get(0).getVstsReleaseName());
				preparedStatement.setDate(3, Date.valueOf(releasePipeline.getRelease().get(0).getExpectedStartDate()));
				preparedStatement.setDate(4, Date.valueOf(releasePipeline.getRelease().get(0).getExpectedEndDate()));
				String email = releasePipeline.getRelease().get(0).getAdditionalMailRecipients().getApplicationTeam()
						+ "#" + releasePipeline.getRelease().get(0).getAdditionalMailRecipients().getEmailIds();
				preparedStatement.setString(5, email);
				String currentTime = String.valueOf(new Timestamp(System.currentTimeMillis()));
				String remarksWithTs = releasePipeline.getRelease().get(0).getRemarks() + "[TS:{" + currentTime
						+ "}]\n";
				preparedStatement.setString(6, remarksWithTs);
				preparedStatement.setLong(7, releasePipeline.getPipelineId());
				preparedStatement.setLong(8, releasemanager.getApplicationId());
				preparedStatement.setString(9, "on");

				preparedStatement.addBatch();
				++count;
				// Execute batch insert if batch insert size goes above batchSize
				if (count == batchSize) {
					preparedStatement.executeBatch();
					count = 0;
				}
			}
			preparedStatement.executeBatch();

		} catch (BatchUpdateException e) {
			logger.error("Batch Release details insert failed :", e);
			throw e;
		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}

	}

	/**
	 * Returns relase remark of specified release and app
	 * 
	 * @param release
	 * @param applicationId
	 * @param pipelineId
	 * @return string
	 */
	public String getRemarks(Release release, Long applicationId, Long pipelineId) {

		ResultSet rs = null;
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append("SELECT remarks ");
		queryStatement.append("FROM public.trelease_info ");
		queryStatement.append("WHERE application_id = ? ");
		queryStatement.append("AND pipeline_id = ? ");
		queryStatement.append("AND release_number = ? ");
		queryStatement.append(";");
		String historyRemarks = "";

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {
			preparedStatement.setLong(1, applicationId);
			preparedStatement.setLong(2, pipelineId);
			preparedStatement.setString(3, release.getReleaseNumber());
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				historyRemarks = rs.getString(1);
			}

		}

		catch (SQLException e) {
			logger.error("Postgres Error while fetching permissions:", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					logger.error(e2.getMessage(), e2);
				}
			}

		}

		return historyRemarks;

	}

	/**
	 * Updates release information
	 * 
	 * @param release
	 * @param applicationId
	 * @param historyRemarks
	 * @param pipelineId
	 */

	public void updateRelease(Release release, Long applicationId, String historyRemarks, Long pipelineId) {

		ResultSet rs = null;
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append("UPDATE public.trelease_info SET ");
		if (null != release.getVstsReleaseName())
			queryStatement.append("vsts_release_number = \'" + release.getVstsReleaseName() + "\' , ");
		if (null != release.getActualStartDate())
			queryStatement.append("actaul_start_date = \'" + release.getActualStartDate() + "\' , ");
		if (null != release.getActualEndDate() && !("".equalsIgnoreCase(release.getActualEndDate().trim())))
			queryStatement.append("actual_end_date = \'" + release.getActualEndDate() + "\' , ");
		if (null != release.getRemarks()) {
			String currentTime = String.valueOf(new Timestamp(System.currentTimeMillis()));
			String remarksWithTs = historyRemarks + release.getRemarks() + "[TS:{" + currentTime + "}]\n";
			queryStatement.append("remarks = \'" + remarksWithTs + "\' , ");
		}
		if (null != release.getBranchList()) {
			String branchList = String.join(",", release.getBranchList());
			queryStatement.append("branch_list = \'" + branchList + "\' , ");
		}
		if (null != release.getStatus()) {
			String status="on";
			String today_date=datefunctions.DateFormater();
			if(release.getActualEndDate() != null) {
				if(release.getActualEndDate().compareTo(today_date) > 0) {
					status="on";
				}
				else {
					status="off";
				}
			}
			queryStatement.append("status = \'" + status + "\' , ");
		}
		if (null != release.getReleaseNumber())
			queryStatement.append("release_number = \'" + release.getReleaseNumber() + "\' , ");

		if (queryStatement.substring(queryStatement.length() - 3, queryStatement.length()).equalsIgnoreCase(" , ")) {
			queryStatement.replace(queryStatement.length() - 3, queryStatement.length() - 1, " ");
		}

		queryStatement.append("WHERE application_id = ? ");
		queryStatement.append("AND pipeline_id = ? ");
		queryStatement.append("AND release_number = ? ");
		queryStatement.append(";");
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {
			preparedStatement.setLong(1, applicationId);
			preparedStatement.setLong(2, pipelineId);
			preparedStatement.setString(3, release.getReleaseNumber());
			preparedStatement.executeUpdate();

		}

		catch (SQLException e) {
			logger.error("Postgres Error while fetching permissions:", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
					logger.error(e2.getMessage(), e2);
				}
			}

		}

	}

}
