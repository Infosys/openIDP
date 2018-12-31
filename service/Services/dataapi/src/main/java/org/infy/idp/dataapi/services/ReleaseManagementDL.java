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

import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The class ReleaseManagementDL contains methods related to release and path
 * sequence of pipelines
 * 
 * @author Infosys
 *
 */
@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class ReleaseManagementDL {

	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	@Autowired
	private JobInfoDL jobInfoDL;

	protected Logger logger = LoggerFactory.getLogger(ReleaseDetails.class);
	private static final String WHERE_CLAUSE = " WHERE ";
	private static final String AND_CLAUSE = " AND ";
	private static final String SELECT_CLAUSE = " SELECT ";
	private static final String FROM_CLAUSE = " FROM ";
	private static final String ORDER_BY = " ORDER BY ";
	protected static final String ERROR1 = "Postgres Error while fetching user details:";
	private static final String INSERT_CLAUSE = " INSERT INTO ";
	/**
	 * 
	 * @param releaseId
	 * @throws SQLException
	 */
	public void deletePathSequence(long releaseId) throws SQLException {

		/*
		 * // DELETE FROM public.trelease_path_config // WHERE EXISTS (SELECT * // FROM
		 * public.trelease_path_config // WHERE release_id = 1);
		 */
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append("DELETE FROM ");
		queryStatement.append(" public.trelease_path_config ");
		queryStatement.append(WHERE_CLAUSE);
		queryStatement.append(" release_id = ? ;");

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setLong(1, releaseId);
			preparedStatement.executeUpdate();

		} catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while fetching permissions:", e);
			throw e;
		}

	}
	
	
}
