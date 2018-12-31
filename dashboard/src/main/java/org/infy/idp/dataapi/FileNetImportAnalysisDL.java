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

import org.infy.idp.entities.FileNet;
import org.infy.idp.entities.FileNetImport;
import org.infy.idp.utils.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * class FileNetExportAnalysisDL has methods related to filenet import in IDP
 */
@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class FileNetImportAnalysisDL {

	
	@Autowired
	private IDPPostGreSqlDbContext idpPostGreSqlDbContext;

	@Autowired
	private ConfigurationManager configmgr;

	private static final Logger logger = LoggerFactory.getLogger(FileNetImportAnalysisDL.class);

	
	private FileNetImportAnalysisDL() {

	}

	 /**
	  * 
	  * @param fileNet
	  * @return
	  * @throws SQLException
	  */
	public int insertFileNetImportAnalysisDetails( FileNet fileNet)
			throws SQLException {
		String queryStatement ="INSERT INTO public.filenet_import(" + 
				"	triggerid, env_source, env_destination)" + 
				"	VALUES (?, ?, ?);";
		
		
		int count = 0;	
		try (Connection connection = idpPostGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			connection.setAutoCommit(false);
			FileNetImport fileNetImport=fileNet.getFileNetImport();
			if(fileNetImport.getDestination()!=null || fileNetImport.getSource()!=null)
			{
			
				preparedStatement.setInt(1, fileNetImport.getTriggerId());
				preparedStatement.setString(2, fileNetImport.getSource());
				preparedStatement.setString(3, fileNetImport.getDestination());
				logger.info("While inserting log value" +"Source "+fileNetImport.getDestination()+ "Destinaon" +fileNetImport.getSource());
				
						
				preparedStatement.addBatch();
				if (++count % Integer.parseInt(configmgr.getBatchSize()) == 0) {
					preparedStatement.executeBatch();
				}
				logger.info("insertFileNetImortAnalysisDetails"+",triggerID" +fileNetImport.getTriggerId()+
						", Source " +fileNetImport.getSource() +
						", Destination: "+fileNetImport.getDestination());
			
			preparedStatement.executeBatch(); // insert remaining records

			connection.commit();
			}
		}

		catch (SQLException e) {
			logger.error("Postgres Error while inserting the insertFileNetExportAnalysisDetails:", e);
			throw e;

		}

		return 1;
	}
}
