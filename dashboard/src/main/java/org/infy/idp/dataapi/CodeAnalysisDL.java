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

import org.infy.idp.entities.CodeAnalysis;
import org.infy.idp.utils.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Class GetJobDetails to update CodeAnalysis table in DB
 */

@Component
public class CodeAnalysisDL {
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;

	@Autowired
	private ConfigurationManager configmgr;

	protected Logger logger = LoggerFactory.getLogger(CodeAnalysisDL.class);

	/**
	 * Constructor
	 * 
	 * 
	 * @param postGreSqlDbContext
	 *            the PostGreSqlDbContext
	 * 
	 */
	private CodeAnalysisDL() {

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
	public int insertCodeAnalysisDetails(int appid, int pipelineno, int buildid, List<CodeAnalysis> codeAnalysisList)
			throws SQLException {
		String queryStatement = "INSERT INTO codeanalysis (appid,buildid,pipelineno,severity,line,packagename,rulename,category,recommendation) VALUES (?,?,?,?,?,?,?,?,?)";
		int count = 0;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			connection.setAutoCommit(false);
			for (CodeAnalysis codeAnalysis : codeAnalysisList) {
				preparedStatement.setInt(1, appid);
				preparedStatement.setInt(2, buildid);
				preparedStatement.setInt(3, pipelineno);
				preparedStatement.setString(4, codeAnalysis.getSeverity());
				preparedStatement.setInt(5, Integer.parseInt(codeAnalysis.getLine()));
				preparedStatement.setString(6, codeAnalysis.getClassName());
				preparedStatement.setString(7, codeAnalysis.getruleName());
				preparedStatement.setString(8, codeAnalysis.getcategory());
				preparedStatement.setString(9, codeAnalysis.getRecommendation());
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
