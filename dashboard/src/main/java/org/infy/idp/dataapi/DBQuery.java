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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.infy.idp.entities.Column;
import org.infy.idp.entities.QueryResponse;
import org.infy.idp.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * Class DBQuery to handle all connection with database
 */
@Component
public class DBQuery {

	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	@Autowired
	private IDPPostGreSqlDbContext idpPostGreSqlDbContext;
	@Autowired
	private DBConstants dbConstants;
	@Autowired
	private DBTableConstants dbTableConstants;
	
	protected Logger logger = LoggerFactory.getLogger(DBQuery.class);
	public static final String DASHBOARD="DASHBOARD";
	public static final String IDP="IDP";
	
	/**
	 * Method to handle timeseries type request
	 * 
	 * @param queryName
	 * @return
	 */
	public List<QueryResponse> runQuery(String queryName)
	{
		List<QueryResponse> resultsresponses = new ArrayList<>();
		QueryResponse response;
		HashMap<String, String> results = new HashMap();
		
		String[] queryParams  = queryName.split("\\+");
		List<String> query = dbConstants.getQuery(queryParams[0]);
		
		try (Connection connection = getConnection(query.get(1));
				PreparedStatement preparedStatement = connection.prepareStatement(query.get(0));
				ResultSet rs = preparedStatement.executeQuery();) {
			if(queryParams.length>1)
			{
				for(int i=1;i<queryParams.length;i++)
				{
					if(NumberUtils.isNumber(queryParams[i]))
					{
						preparedStatement.setInt(i,Integer.parseInt(queryParams[i]));
					}
					else
					{
						preparedStatement.setString(i,queryParams[i]);
					}
				}
			}
			
			
			while (rs.next()) {
				results.put(rs.getString(1), rs.getString(2));
			}
			
			logger.info("runQuery result set");
			results.forEach((k, v) -> logger.info("value : " + k + " Timstamp : " + v));
			int index = 0;
			double[][] array = new double[results.size()][2];
			for (Map.Entry<String, String> entry : results.entrySet()) {
				array[index][0] = Utils.convertString(entry.getKey());
				array[index][1] = Utils.convertDate(entry.getValue());
				index++;
			}
			response = new QueryResponse();
			response.setDatapoints(array);
			response.setType("timeserie");
			resultsresponses.add(response);
			return resultsresponses;

		} catch (Exception e) {
			logger.error("Exception in runQuery ", e);
			
		}
		
		return resultsresponses;
	}
	/**
	 * Method to handle table type request
	 * 
	 * @param queryName
	 * @return
	 */
	public QueryResponse runTableQuery(String queryName)
	{
		QueryResponse response = new QueryResponse();
		List<String> singleRow = null;
		List<List<String>> rows = new ArrayList();
		String[] queryParams = queryName.split("\\+");
		
		List<String> query = dbTableConstants.getQuery(queryParams[0]);
		Column c;
		List<Column> columns = new ArrayList();
		
		try (Connection connection = getConnection(query.get(1));
				PreparedStatement preparedStatement = connection.prepareStatement(query.get(0));
				ResultSet rs = preparedStatement.executeQuery();) {
			if(queryParams.length>1)
			{
				for(int i=1;i<queryParams.length;i++)
				{
					if(NumberUtils.isNumber(queryParams[i]))
					{
						preparedStatement.setInt(i,Integer.parseInt(queryParams[i]));
					}
					else
					{
						preparedStatement.setString(i,queryParams[i]);
					}
				}
			}
			
			
			ResultSetMetaData metars = rs.getMetaData();
			if (metars.getColumnCount() != 0) {
				for (int i = 1; i <= metars.getColumnCount(); i++) {
					c = new Column();
					c.setText(metars.getColumnName(i));
					c.setType(metars.getColumnTypeName(i));
					columns.add(c);
				}
				c = new Column();
				c.setText("Details");
				c.setType("String");
				columns.add(c);
				while (rs.next()) {
					singleRow = new ArrayList<>();
					for (int j = 1; j <= metars.getColumnCount(); j++) {
						singleRow.add(rs.getString(j));
					}
					singleRow.add("ArtifactDetails");
					rows.add(singleRow);
				}
				
			}
			response.setColumns(columns);
			response.setRows(rows);

			response.setType("table");

		} catch (Exception e) {
			logger.error("Exception in runQuery ", e);
			
		}
		return response;
	}
	
	
	
	public Connection getConnection(String type)
	{
		Connection connection ;
		switch (type) {
			case DASHBOARD:
				connection = postGreSqlDbContext.getConnection();
				break;
			case IDP:
				connection = idpPostGreSqlDbContext.getConnection();
				break;
			default:
				connection = postGreSqlDbContext.getConnection();
				break;
		}
		return connection;
	}
}
