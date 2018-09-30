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
 * The Class GetJobDetails to update buildinfo table in DB
 */

@Component
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
	  
	 public int insertbuilddetails(int appid,double buildtime,String buildstatus,int buildid,int lastcompletebuildid,int lastsuccessfulbuildid,int lastunstablebuildid,int lastunsuccessfulbuildid,int lastfailedbuildid,int pipelinebuildno,double score,String stagename) throws SQLException
	  {
		  
		
		   String queryStatement ="INSERT INTO buildinfo (appid,buildtime,buildstatus,buildid,lastcompletebuildid,lastsuccessfulbuildid,lastunstablebuildid,lastunsuccessfulbuildid,lastfailedbuildid,pipelineno,score,stagename) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"; 
	      
		  
		   try (Connection connection = postGreSqlDbContext.getConnection();
		        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
		    	preparedStatement.setInt(1,appid);
		    	preparedStatement.setDouble(2,buildtime);
		    	preparedStatement.setString(3,buildstatus);
		    	preparedStatement.setInt(4,buildid);
		    	preparedStatement.setInt(5,lastcompletebuildid);
		    	preparedStatement.setInt(6,lastsuccessfulbuildid);
		    	preparedStatement.setInt(7,lastunstablebuildid);
		    	preparedStatement.setInt(8,lastunsuccessfulbuildid);
		    	preparedStatement.setInt(9,lastfailedbuildid);
		    	preparedStatement.setInt(10,pipelinebuildno);
		    	preparedStatement.setDouble(11,score);
		    	preparedStatement.setString(12,stagename);
		    	preparedStatement.executeUpdate();
		    	
		      
		     }		     
		    
		     catch (SQLException e) {
		      logger.error("Postgres Error while inserting the insertappdetails:",e);
		      throw e;
		      
		    }
		    
		  return 1; 
	  }
	  
	}
