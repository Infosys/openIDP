/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.dataapi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.infy.idp.entities.CoverageDetails;
import org.infy.idp.utils.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Class GetJobDetails to update Codecoverage table in DB.
 */

@Component
public class CodeCoverageDL {
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	@Autowired
	private ConfigurationManager configmgr;
	protected Logger logger = LoggerFactory.getLogger(CodeCoverageDL.class);

	/**
	 * Constructor
	 * 
	 * 
	 * @param postGreSqlDbContext
	 *            the PostGreSqlDbContext
	 * 
	 */
	private CodeCoverageDL() {

	}

	/**
	 * Get permissions to the user for perticular application
	 * 
	 * 
	 * @param userName
	 *            the String
	 * @param applicationName
	 *            the String
	 * 
	 * @return permissions the List<String>
	 * 
	 */
	public int insertcoveragedetails(List<CoverageDetails> coverageDetailsList, int appid, int buildid, int pipelineno)
			throws SQLException {
		String queryStatement = "INSERT INTO codecoverage (appid,buildid,pipelineno,linecoverage,classname,category,packagename) VALUES (?,?,?,?,?,?,?)";
		int count = 0;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			connection.setAutoCommit(false);
			for (CoverageDetails coverageDetails : coverageDetailsList) {
				preparedStatement.setInt(1, appid);
				preparedStatement.setInt(2, buildid);
				preparedStatement.setInt(3, pipelineno);
				preparedStatement.setInt(4, Double.valueOf(coverageDetails.getLineCoverage()).intValue());
				preparedStatement.setString(5, coverageDetails.getClassName());
				preparedStatement.setString(6, coverageDetails.getCategory());
				preparedStatement.setString(7, coverageDetails.getPckage());
				preparedStatement.addBatch();
				if (++count % Integer.parseInt(configmgr.getBatchSize()) == 0) {
					preparedStatement.executeBatch();
				}

			}
			preparedStatement.executeBatch(); // insert remaining records
			
			connection.commit();
		}

		catch (SQLException e) {
			logger.error("Postgres Error while inserting the insertappdetails:", e);
			throw e;

		}

		return 1;
	}
}
