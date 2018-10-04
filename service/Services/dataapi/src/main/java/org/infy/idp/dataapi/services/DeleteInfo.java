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
 * The class contains method to delete roles
 * @author Infosys
 */

@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class DeleteInfo {
	@Autowired
	private PostGreSqlDbContext postgresqlcontext;

	private static final Logger logger = LoggerFactory.getLogger(DeleteInfo.class);

	private static final String WHERE_CLAUSE = " WHERE ";

	private static final String DELETE_CLAUSE = "DELETE FROM ";

	private DeleteInfo() {

	}

	/**
	 * 
	 * Deletes roles with specified id
	 * 
	 * @param id
	 * @return int
	 */
	public int deleteRoles(Long id) {

		String tableName = "tapplication_roles";
		StringBuilder queryStatement = new StringBuilder();

		int status = 0;

		queryStatement.append(DELETE_CLAUSE + " " + tableName + WHERE_CLAUSE + "application_id=? ;");

		try (Connection connection = postgresqlcontext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {

			preparedStatement.setLong(1, id);
			status = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return status;
	}
}
