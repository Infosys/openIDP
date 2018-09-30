

package org.infy.idp.dataapi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.infy.idp.entities.TestCaseResult;
import org.infy.idp.utils.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * The Class GetJobDetails has methods to update testanalysis table in DB.
 */

@Component
public class TestAnalysisDL {
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	@Autowired
	private ConfigurationManager configmgr;
	  protected Logger logger= LoggerFactory.getLogger(TestAnalysisDL.class);
	 
	  /**
	   * Constructor

	   * 
	   * @param postGreSqlDbContext the PostGreSqlDbContext

	   */
	 private TestAnalysisDL(){
		 
	 }
	  
	  /**
	   * Get permissions to the user for perticular application

	   * 
	   * @param userName the String
	   * @param applicationName the String
	   * 
	   * @return permissions the List<String>

	   */
	 public int insertTestAnalysis(int appid, int pipelineno,int buildid,
				List<TestCaseResult> testcaseList) throws SQLException
	  {
		   String queryStatement ="INSERT INTO testanalysis (appid,buildid,pipelineno,packagename,message,testsuitename,category,status,duration) VALUES (?,?,?,?,?,?,?,?,?)"; 
		   int count = 0;
		   
		   try (Connection connection = postGreSqlDbContext.getConnection();
		        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			   connection.setAutoCommit(false);
				Double temp;
			for (TestCaseResult testCaseResult : testcaseList) {
				temp = 0.0;
				if (!testCaseResult.getDuration().equalsIgnoreCase("none")) {
					temp = Double.parseDouble(testCaseResult.getDuration());
				}

				preparedStatement.setInt(1, appid);
				preparedStatement.setInt(2, buildid);
				preparedStatement.setInt(3, pipelineno);
				preparedStatement.setString(4, testCaseResult.getId());
				preparedStatement.setString(5, testCaseResult.getMessage());
				preparedStatement.setString(6, testCaseResult.gettestSuiteName());
				preparedStatement.setString(7, testCaseResult.getCategory());
				preparedStatement.setString(8, testCaseResult.getStatus());
				preparedStatement.setDouble(9, temp);
				preparedStatement.addBatch();
				if (++count % Integer.parseInt(configmgr.getBatchSize()) == 0) {
					preparedStatement.executeBatch();
				}

			}
			preparedStatement.executeBatch(); // insert remaining records
			
			connection.commit();
		     }		     
		    
		     catch (SQLException e) {
		      logger.error("Postgres Error while inserting the insertappdetails:",e);
		      throw e;
		      
		    }
		    
		  return 1; 
	  }
	 	}
