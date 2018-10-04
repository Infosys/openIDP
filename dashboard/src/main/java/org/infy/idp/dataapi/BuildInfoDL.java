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

import org.infy.idp.entities.BuildInfoDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * The Class GetJobDetails to update buildinfo table in DB
 */

@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class BuildInfoDL {
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	  
	  protected Logger logger= LoggerFactory.getLogger(BuildInfoDL.class);
	 
	  /**
	   * Constructor

	   * 
	   * @param postGreSqlDbContext the PostGreSqlDbContext

	   */
	 private BuildInfoDL(){
		 
	 }
	  
	 public int insertbuilddetails(BuildInfoDetails buildInfoDetails) throws SQLException
	  {
		  
		
		   String queryStatement ="INSERT INTO buildinfo (appid,buildtime,buildstatus,buildid,lastcompletebuildid,lastsuccessfulbuildid,lastunstablebuildid,lastunsuccessfulbuildid,lastfailedbuildid,pipelineno,score,stagename) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"; 
	      
		  
		   try (Connection connection = postGreSqlDbContext.getConnection();
		        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
		    	preparedStatement.setInt(1,buildInfoDetails.getAppid());
		    	preparedStatement.setDouble(2,buildInfoDetails.getBuildtime());
		    	preparedStatement.setString(3,buildInfoDetails.getBuildstatus());
		    	preparedStatement.setInt(4,buildInfoDetails.getBuildid());
		    	preparedStatement.setInt(5,buildInfoDetails.getLastcompletebuildid());
		    	preparedStatement.setInt(6,buildInfoDetails.getLastsuccessfulbuildid());
		    	preparedStatement.setInt(7,buildInfoDetails.getLastunstablebuildid());
		    	preparedStatement.setInt(8,buildInfoDetails.getLastunsuccessfulbuildid());
		    	preparedStatement.setInt(9,buildInfoDetails.getLastfailedbuildid());
		    	preparedStatement.setInt(10,buildInfoDetails.getPipelinebuildno());
		    	preparedStatement.setDouble(11,buildInfoDetails.getScore());
		    	preparedStatement.setString(12,buildInfoDetails.getStagename());
		    	preparedStatement.executeUpdate();
		    	
		      
		     }		     
		    
		     catch (SQLException e) {
		      logger.error("Postgres Error while inserting the insertappdetails:",e);
		      throw e;
		      
		    }
		    
		  return 1; 
	  }
	  
	}
