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
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * The Class GetJobDetails has methods to update appinfo table in DB.
 */

@Component
public class PipelineDL {
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	  
	  protected Logger logger= LoggerFactory.getLogger(PipelineDL.class);
	 
	  /**
	   * Constructor

	   * 
	   * @param postGreSqlDbContext the PostGreSqlDbContext

	   */
	 private PipelineDL(){
		 
	 }
	  
	  /**
	   * Get permissions to the user for perticular application

	   * 
	   * @param userName the String
	   * @param applicationName the String
	   * 
	   * @return permissions the List<String>

	   */
	 public int insertappdetails(String applicationName, String pipelineName) throws SQLException
	  {
		  String queryStatement ="INSERT INTO appinfo (application_name,pipeline_name) VALUES (?,?) ON CONFLICT(application_name,pipeline_name) DO UPDATE set created_at = current_timestamp RETURNING id"; 
	    int appid=0;   	   
		  
		   try (Connection connection = postGreSqlDbContext.getConnection();
		        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
		    	preparedStatement.setObject(1,applicationName);
		    	preparedStatement.setString(2,pipelineName);
		    	if (preparedStatement.execute()) {
		    		ResultSet rs=preparedStatement.getResultSet();
		    	if(rs.next()){
		    	   appid=rs.getInt(1);
		    	}
		    	}
		    	
		      
		     }		     
		    
		     catch (SQLException e) {
		      logger.error("Postgres Error while inserting the insertappdetails:",e);
		      throw e;
		      
		    }
		    
		  return appid; 
	  }
	  
	  
	  
	}
