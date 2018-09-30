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
 *class InsertFetchJira has method to fetch jira details from ttrigger_history table
 *
 */
@Component
public class InsertFetchJira {
	
	protected Logger logger=LoggerFactory.getLogger(InsertFetchJira.class);
	
	@Autowired
	private IDPPostGreSqlDbContext idpPostGreSqlDbContext;

	public String getDetailsFromDB(Integer trigegrid) {
		logger.info("in getDetailsFromDB");
		 StringBuilder queryStatement = new StringBuilder();
		 ResultSet rs= null;
		 String resultVal=null;

		 queryStatement.append("select jiraprojectkey,userstorystring from ttrigger_history where trigger_id=? ");
		 try (Connection connection = idpPostGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {
			 logger.info("inside get details from db try");
			 
			 logger.info("before set");
			 preparedStatement.setInt(1,trigegrid);
			 logger.info("after set");
			 logger.info("this is trigger  id   "+trigegrid+"");
			 rs=preparedStatement.executeQuery();
			 logger.info("after get details from db try");
			 while(rs.next()) {
				 
				 logger.info("inside while");
				 logger.info(rs.getString(1));
				 logger.info(rs.getString(2));
				 logger.info("in getDetailsFromDB");
				 resultVal=rs.getString(1)+":"+rs.getString(2);
				 
				 
			 }
			 
			 
		 }
		 catch(SQLException|NullPointerException e){
			    e.printStackTrace();
				logger.error("Postgres Error while updating trigger history ",e.getMessage());
			}
	
	return resultVal;

}
}