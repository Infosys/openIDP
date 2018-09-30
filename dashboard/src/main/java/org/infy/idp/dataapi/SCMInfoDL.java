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

import org.infy.idp.jsonschema.SCMInfo;
import org.infy.idp.utils.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * class SCMInfoDL has methods to update SCMinfo table in Db
 *
 */
@Component
public class SCMInfoDL {
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	@Autowired
	private ConfigurationManager configurationManager;
	protected Logger logger = LoggerFactory.getLogger(SCMInfoDL.class);
	public int insertSCMInfo(int appid, int pipelineno, List<SCMInfo> scminfolist) throws SQLException
	{
		
		String queryStatement ="INSERT INTO scminfo (appid,pipelineno,lastmodifiedon,lastmodifiedby,commitmessage,affectedpath) VALUES(?,?,?,?,?,?)"; 
		   int count = 0;
		   try (Connection connection = postGreSqlDbContext.getConnection();
		        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			   	connection.setAutoCommit(false);
				for (SCMInfo scmInfo : scminfolist) {
		    	preparedStatement.setInt(1,appid);
		    	preparedStatement.setInt(2,pipelineno);
		    	preparedStatement.setString(3,scmInfo.getLastModified());
		    	preparedStatement.setString(4,scmInfo.getLastModifiedBy());
		    	preparedStatement.setString(5,scmInfo.getCommitMessage());
		    	
		    	preparedStatement.setString(6,scmInfo.getGetAffectedPath().substring(1, scmInfo.getGetAffectedPath().length()-1));
		    	logger.info("Last Mdified data :" + scmInfo.getGetAffectedPath());
		    	logger.info("Last RemoteUrl data :" + scmInfo.getRemoteUrl());

		    	preparedStatement.addBatch();
		    	if (++count % Integer.parseInt(configurationManager.getBatchSize()) == 0) {
					preparedStatement.executeBatch();
				}

			}
			preparedStatement.executeBatch(); // insert remaining records
			
			connection.commit();
		     }		     
		    
		     catch (SQLException e) {
		      logger.error("Postgres Error while inserting the scm details: ",e);
		      
		      throw e;
		    }
		    
		  return 1; 
	  
	}
}
