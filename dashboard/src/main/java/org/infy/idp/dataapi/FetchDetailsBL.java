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
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.infy.idp.entities.Column;
import org.infy.idp.entities.QueryResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

/**
 * The Class FetchDetailsBL to handle timeseries,table,search request from grafana.
 */

@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class FetchDetailsBL {
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	@Autowired
	private IDPPostGreSqlDbContext idpPostGreSqlDbContext;

	@Autowired
	private DBQuery dbQuery;

	
	public static final String VALUES="values are";
	protected Logger logger = LoggerFactory.getLogger(FetchDetailsBL.class);

	/**
	 * Constructor
	 * 
	 * 
	 * @param postGreSqlDbContext
	 *            the PostGreSqlDbContext
	 * 
	 */
	private FetchDetailsBL() {

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

	

	/**
	 * 
	 * Method to handle timeseries type requests
	 * @param query
	 * @return
	 */
	public List<QueryResponse> runQuery(String inputquery)

	{

		String query=inputquery.replace("\\", "");
		logger.info("Executing " , query);
		///
		
		
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(query);
		return dbQuery.runQuery(query);

		

	}

	/**
	 * Method to handle table type requests
	 * 
	 * @param query
	 * @return
	 */

	public QueryResponse runTableQuery(String inputQuery) {
		String query=inputQuery.replace("\\", "");
		
		///
		logger.info("Executing table query : %s", query);
		
		
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(query);
		QueryResponse response = new QueryResponse();
		response.setTarget(query);
		return dbQuery.runTableQuery(query);
		
	}


	/**
	 * 
	 * @param query
	 * @return
	 */
	
		public QueryResponse runTableQuerySCM(String query){
		logger.info("Executing table query ", query);
		List<Column> columns = new ArrayList<>();
		List<List<String>> rows = new ArrayList<>();
		Column c;
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(query);
		QueryResponse response = new QueryResponse();
		response.setTarget(query);
		List<String> singleRow = null;

		try(Connection connectionNew1 = postGreSqlDbContext.getConnection();PreparedStatement preparedStatement = connectionNew1.prepareStatement(queryStatement.toString(),ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = preparedStatement.executeQuery();)  {
			
			
			ResultSetMetaData metars = rs.getMetaData();
			if (metars.getColumnCount() != 0) {
				for (int i = 1; i <= metars.getColumnCount(); i++) {
					c = new Column();
					c.setText(metars.getColumnName(i));
					c.setType(metars.getColumnTypeName(i));
					columns.add(c);
				}

				while (rs.next()) {
					singleRow = new ArrayList<>();
					for (int j = 1; j <= metars.getColumnCount(); j++) {
						singleRow.add(rs.getString(j));
					}
					rows.add(singleRow);
				}
				logger.info(VALUES);
				if (singleRow != null) {
					for (String string : singleRow) {
						logger.info(string);
					}
				}
				response.setColumns(columns);
				response.setRows(rows);

				response.setType("table");

			}
			return response;
		} catch (Exception e) {
			logger.error("Exception in runTableQuery ", e);
			return response;
		}

		
	}
	
		/**
		 * method to handle templating/variable based queries
		 * 
		 * @param applicationname
		 * @param pipelinename
		 * @return
		 */
	public String runFetchQuery(String applicationname,String pipelinename) {
		
		String appid = "";
		try (Connection restconnection1 = postGreSqlDbContext.getConnection();
				PreparedStatement restStatement1 = restconnection1.prepareStatement("select id from appinfo where application_name='"+applicationname+"' and pipeline_name='"+pipelinename+"'");
				ResultSet rs2 = restStatement1.executeQuery();) {
			
			if (rs2.next()) {
				appid = rs2.getString(1);
				logger.info("appid is: %s",appid);
				
			}
		} catch (Exception e) {
			logger.info("Exception Occured for query fetch details icqa:->");
			logger.error("Exception is :->", e);
			return appid;
		}
		return appid;
	}
	
	public List<String> runSearchQuery(String inputQuery,String userid) { 

		String query=inputQuery.replace("\\", "");
		logger.info("Executing %s", query);
		List<String> results = new ArrayList<>();
		if(query.equalsIgnoreCase("select application_name from appinfo")){
			query="select application_name from apporg ao, userorg uo where ao.org_name=uo.org_name and uo.user_name='"+userid+"'";
		}
		
		
		StringBuilder queryStatement = new StringBuilder();
		queryStatement.append(query);
		
		if(query.toLowerCase().contains("tapplication_info") || query.toLowerCase().contains("ttrigger_history"))
		{

			try (Connection connection = idpPostGreSqlDbContext.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(query);
					ResultSet rs = preparedStatement.executeQuery();) {
				
				while (rs.next()) {
					results.add(rs.getString(1));
				}
				logger.info(VALUES);
				results.forEach(logger::info);

				return results;
			} catch (Exception e) {

				logger.error("Exception in runSearchQuery ", e);
				return new ArrayList();
			}
		}
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());
				ResultSet rs = preparedStatement.executeQuery();) {
			
			while (rs.next()) {
				results.add(rs.getString(1));
			}
			logger.info(VALUES);
			results.forEach(logger::info);

			return results;
		} catch (Exception e) {

			logger.error("Exception in runSearchQuery ", e);
			return new ArrayList();
		}

	}

}//public.scminfo
