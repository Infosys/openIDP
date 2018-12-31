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
import java.util.List;

import org.infy.idp.entities.FileNet;
import org.infy.idp.entities.FileNetExport;
import org.infy.idp.entities.FileNetExportClassDefinitionType;
import org.infy.idp.entities.FileNetExportOtherType;
import org.infy.idp.entities.FileNetExportPropertyType;
import org.infy.idp.utils.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * class FileNetExportAnalysisDL has methods related to filenet export in IDP
 */
@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class FileNetExportAnalysisDL {

	@Autowired
	private IDPPostGreSqlDbContext idpPostGreSqlDbContext;

	@Autowired
	private ConfigurationManager configmgr;

	public static final String FILENETEXPORTID =", filenet_export_id: "; 
	public static final String OBEJECTSOURCE =", objectSource: ";
	
	protected Logger logger = LoggerFactory.getLogger(FileNetExportAnalysisDL.class);

	private FileNetExportAnalysisDL() {

	}

	/**
	 * 
	 * @param fileNetExport
	 * @param fileNet
	 * @param connection
	 * @param preparedStatement
	 * @param enviornment
	 * @throws SQLException
	 */
	public void insertPropertyTemplateList(FileNetExport fileNetExport, FileNet fileNet, Connection connection,
			PreparedStatement preparedStatement, String enviornment) throws SQLException {
		List<FileNetExportPropertyType> listPropertyType = fileNetExport.getPropertyTypeList();
		int count = 0;
		for (FileNetExportPropertyType fileNetExportPropertyType : listPropertyType) {
			logger.info("in filenetexport....");
			logger.info("in filenetexport....enviornment"+ enviornment);
			preparedStatement.setString(1, fileNetExportPropertyType.getObjectStore());
			preparedStatement.setString(2, fileNetExportPropertyType.getName());
			

			preparedStatement.setString(3, fileNetExportPropertyType.getObjectType());

			preparedStatement.setInt(4, fileNet.getTriggerId());
			preparedStatement.setString(5, enviornment);
			logger.info("enviornment" +enviornment);

			preparedStatement.addBatch();
			if (++count % Integer.parseInt(configmgr.getBatchSize()) == 0) {
				preparedStatement.executeBatch();
			}
			logger.info("insertPropertyList; name: " + fileNetExportPropertyType.getName() + FILENETEXPORTID
					+ fileNetExportPropertyType.getId() + OBEJECTSOURCE
					+ fileNetExportPropertyType.getObjectStore() + "objectStoreType"
					+ fileNetExportPropertyType.getObjectType());
		}
		preparedStatement.executeBatch(); // insert remaining records

		connection.commit();
	}

	/**
	 * 
	 * @param fileNetExport
	 * @param fileNet
	 * @param connection
	 * @param preparedStatement
	 * @param enviornment
	 * @throws SQLException
	 */
	public void insertcalssDefinationList(FileNetExport fileNetExport, FileNet fileNet, Connection connection,
			PreparedStatement preparedStatement, String enviornment) throws SQLException {
		List<FileNetExportClassDefinitionType> listcalssDefination = fileNetExport.getClassDefinitionTypeList();
		int count = 0;
		logger.info("Class defination list size=" + listcalssDefination.size());
		for (FileNetExportClassDefinitionType fileNetExportClassDefinitionType : listcalssDefination) {
			preparedStatement.setString(1, fileNetExportClassDefinitionType.getObjectStore());
			preparedStatement.setString(2, fileNetExportClassDefinitionType.getName());
			preparedStatement.setString(3, fileNetExportClassDefinitionType.getObjectType());
			preparedStatement.setInt(4, fileNet.getTriggerId());
			preparedStatement.setString(5, enviornment);
			preparedStatement.addBatch();
			if (++count % Integer.parseInt(configmgr.getBatchSize()) == 0) {
				preparedStatement.executeBatch();
			}
			logger.info("insertclassdefinition name: " + fileNetExportClassDefinitionType.getName()
					+ FILENETEXPORTID + fileNetExportClassDefinitionType.getId() + OBEJECTSOURCE
					+ fileNetExportClassDefinitionType.getObjectStore());
		}
		preparedStatement.executeBatch(); // insert remaining records

		connection.commit();
	}

	/**
	 * 
	 * @param fileNetExport
	 * @param fileNet
	 * @param connection
	 * @param preparedStatement
	 * @param enviornment
	 * @throws SQLException
	 */
	public void insertOthersList(FileNetExport fileNetExport, FileNet fileNet, Connection connection,
			PreparedStatement preparedStatement, String enviornment) throws SQLException {
		List<FileNetExportOtherType> listOtherType = fileNetExport.getOtherTypeList();
		int count = 0;
		for (FileNetExportOtherType fileNetExportOtherType : listOtherType) {
			preparedStatement.setString(1, fileNetExportOtherType.getObjectStore());
			preparedStatement.setString(2, fileNetExportOtherType.getName());
			preparedStatement.setString(3, fileNetExportOtherType.getObjectType());
			preparedStatement.setInt(4, fileNet.getTriggerId());
			preparedStatement.setString(5, enviornment);
			preparedStatement.addBatch();
			if (++count % Integer.parseInt(configmgr.getBatchSize()) == 0) {
				preparedStatement.executeBatch();
			}
			logger.info("insertPropertyList; name: " + fileNetExportOtherType.getName() + FILENETEXPORTID
					+ fileNetExportOtherType.getId() + OBEJECTSOURCE + fileNetExportOtherType.getObjectStore());
		}
		preparedStatement.executeBatch(); // insert remaining records

		connection.commit();
	}

	/**
	 * 
	 * @param triggerID
	 * @return
	 */
	public String getEnvironment(int triggerID) {
		String queryStatement = "select trigger_entity::json ->> 'envSelected' from ttrigger_history where trigger_id='"
				+ triggerID + "';";
		String environment = "";
		try (Connection connection = idpPostGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement);
				ResultSet fetchEnviornmentResultSet = preparedStatement.executeQuery();) {

			

			while (fetchEnviornmentResultSet.next()) {
				environment = fetchEnviornmentResultSet.getString(1);
				logger.info("getEnvironment; enviornment=" + environment);
			}

		} catch (SQLException e) {
			logger.error("Postgres Error while inserting the insertFileNetExportAnalysisDetails:", e);

		}
		return environment;

	}

	/**
	 * 
	 * @param triggerId
	 * @param fileNet
	 * @return
	 * @throws SQLException
	 */
	public int insertFileNetExportAnalysisDetails(int triggerId, FileNet fileNet) throws SQLException {
		String queryStatement = "INSERT INTO public.filenet_export("
				+ "	object_id, object_name, object_type, triggerid, env)" + "	VALUES (?, ?, ?, ?, ?);";
		logger.info("in expAnalysis:" + getEnvironment(triggerId));
		String enviornment = getEnvironment(triggerId);
		

		try (Connection connection = idpPostGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			connection.setAutoCommit(false);
			FileNetExport fileNetExport = fileNet.getFileNetExport();

			insertcalssDefinationList(fileNetExport, fileNet, connection, preparedStatement, enviornment);
			insertPropertyTemplateList(fileNetExport, fileNet, connection, preparedStatement, enviornment);
			insertOthersList(fileNetExport, fileNet, connection, preparedStatement, enviornment);
			

		}

		catch (SQLException e) {
			logger.error("Postgres Error while inserting the insertFileNetExportAnalysisDetails:", e);
			throw e;

		}

		return 1;
	}


}
