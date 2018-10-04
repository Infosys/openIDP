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
import java.sql.SQLException;

import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * The class contains method to delete pipeline
 * @author Infosys
 *
 */
@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class UpdateJobDetails {
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;

	private static final Logger logger = LoggerFactory.getLogger(UpdateJobDetails.class);
	private static final String WHERE_CLAUSE = " WHERE ";
	private static final String UPDATE_CLAUSE = " UPDATE ";
	private static final String SET_CLAUSE = " SET ";
	private static final String AND_CLAUSE = " AND ";
	private static final String SELECT_CLAUSE = " SELECT ";
	private static final String FROM_CLAUSE = " FROM ";
	private static final String APPLICATION_NAME = " application_name LIKE ? ";
	private static final String PIPELINE_NAME = " pipeline_name LIKE ? ";

	protected static final String ERROR1 = "Postgres Error while fetching user details:";

	private UpdateJobDetails() {

	}

	/**
	 * 
	 * Delete pipeline of specified job
	 * 
	 * @param triggerJobName
	 * @return
	 */
	public boolean deletePipeline(TriggerJobName triggerJobName) {
		
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append(UPDATE_CLAUSE);
		queryStatement.append("public.tpipeline_info");
		queryStatement.append(SET_CLAUSE);
		queryStatement.append("active = FALSE");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(PIPELINE_NAME);
		queryStatement.append(AND_CLAUSE);
		queryStatement.append("application_id = (");
		queryStatement.append(SELECT_CLAUSE);
		queryStatement.append("application_id ");
		queryStatement.append(FROM_CLAUSE);
		queryStatement.append("tapplication_info");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(APPLICATION_NAME);
		queryStatement.append(");");
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {
			preparedStatement.setString(1, triggerJobName.getPipelineName());
			preparedStatement.setString(2, triggerJobName.getApplicationName());
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			return false;
		}

	}

}
