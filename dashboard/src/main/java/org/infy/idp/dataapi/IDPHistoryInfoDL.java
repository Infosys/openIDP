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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * The Class GetJobDetails has method to update dashboard_info table in DB.
 */

@Component
public class IDPHistoryInfoDL {
	@Autowired
	private IDPPostGreSqlDbContext postGreSqlDbContext;
	  
	  protected Logger logger= LoggerFactory.getLogger(IDPHistoryInfoDL.class);
	 
	  /**
	   * Constructor

	   * 
	   * @param postGreSqlDbContext the PostGreSqlDbContext

	   */
	 private IDPHistoryInfoDL(){
		 
	 }
	  
	 public int insertHistoryInfo(String appName,String pipName,String stage, String pipelineId,String status) throws SQLException
	  {
		  

		   String queryStatement ="INSERT INTO Dashboard_Info (Application_Name,Pipeline_Name,Stage,Pipeline_ID,Status) VALUES (?,?,?,?,?)"; 
	      
		   try (Connection connection = postGreSqlDbContext.getConnection();
		        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
		    	preparedStatement.setString(1,appName);
		    	preparedStatement.setString(2,pipName);
		    	preparedStatement.setString(3,stage);
		    	preparedStatement.setString(4,pipelineId);
		    	preparedStatement.setString(5,status);
		    	preparedStatement.executeUpdate();
		    	
		      
		     }		     
		    
		     catch (SQLException e) {
		      logger.error("Postgres Error while inserting the insertHistoryInfo:",e);
		      throw e;
		      
		    }
		    
		  return 1; 
	  }
	  
	}
