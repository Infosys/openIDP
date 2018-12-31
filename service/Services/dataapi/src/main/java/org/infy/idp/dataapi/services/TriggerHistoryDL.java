package org.infy.idp.dataapi.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.entities.triggerparameter.TriggerParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class TriggerHistoryDL {
	/**
	 * 
	 * @param jiraProjectKey
	 * @return list of string
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String WHERE_CLAUSE = " WHERE ";
	private static final String SELECT_CLAUSE = " SELECT ";
	private static final String FROM_CLAUSE = " FROM ";
	
	
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;

	public List<String> getRecordsJira(String jiraProjectKey) {

		Gson gson = new Gson();
		String tableName = "ttrigger_history";
		String column = "trigger_entity,build_status,deploy_status,test_status,userstorystring,version,trigger_time,artifact_name,artifact_link";
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append(column);
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append(tableName);
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append("jiraprojectkey like ?");
		queryStatement.append(";");

		List<String> resultValue = new ArrayList<>();
		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1, jiraProjectKey);
			rs = preparedStatement.executeQuery();

			String data = "";
			while (rs.next()) {
				data = "";
				TriggerParameters trigerParam = new TriggerParameters();
				String nexuslLink = "NA";
				String artifactName = "NA";
				trigerParam = gson.fromJson(rs.getString(1), TriggerParameters.class);

				if (trigerParam.getArtifactorySelected().equalsIgnoreCase("on")) {
					nexuslLink = rs.getString(9);
					if (rs.getString(8) != null)
						artifactName = rs.getString(8);
				}
				data = trigerParam.getApplicationName() + "::" + trigerParam.getPipelineName() + "::" + rs.getString(2)
						+ "::" + rs.getString(3) + "::" + rs.getString(4) + "::" + rs.getString(5) + "::"
						+ rs.getString(6) + "::" + artifactName + "|" + nexuslLink;

				resultValue.add(data);
			}

		} catch (SQLException | NullPointerException e) {
			logger.info(e.getMessage(), e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info("inside getRecordsJira");
					logger.info(e.getMessage(), e);
				}
			}
		}

		return resultValue;

	}

}
