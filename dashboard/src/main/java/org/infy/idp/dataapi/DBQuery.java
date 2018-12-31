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
	public static final String DASHBOARD = "DASHBOARD";
	public static final String IDP = "IDP";

	/**
	 * Method to handle timeseries type request
	 * 
	 * @param queryName
	 * @return
	 */
	public List<QueryResponse> runQuery(String queryName) {
		List<QueryResponse> resultsresponses = new ArrayList<>();
		QueryResponse response;
		HashMap<String, String> results = new HashMap();

		String[] queryParams = queryName.split("\\+");
		List<String> query = dbConstants.getQuery(queryParams[0]);

		try (Connection connection = getConnection(query.get(1));
				PreparedStatement preparedStatement = connection.prepareStatement(query.get(0));
				) {
			if (queryParams.length > 1) {
				for (int i = 1; i < queryParams.length; i++) {
					if (NumberUtils.isNumber(queryParams[i])) {
						preparedStatement.setInt(i, Integer.parseInt(queryParams[i]));
					} else {
						preparedStatement.setString(i, queryParams[i]);
					}
				}
			}
			ResultSet rs = preparedStatement.executeQuery();
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
	public QueryResponse runTableQuery(String queryName,String userid) {
		QueryResponse response = new QueryResponse();
		List<String> singleRow = null;
		List<List<String>> rows = new ArrayList();
		String[] queryParams = queryName.split("\\+");

		if("sparkEnvHome".equals(queryParams[0]))
		{
			return getdummyEnvHome(userid);
		}
		
		List<String> query = dbTableConstants.getQuery(queryParams[0]);
		Column c;
		List<Column> columns = new ArrayList();

		try (Connection connection = getConnection(query.get(1));
				PreparedStatement preparedStatement = connection.prepareStatement(query.get(0));
				) {
			if (queryParams.length > 1) {
				for (int i = 1; i < queryParams.length; i++) {
					if (NumberUtils.isNumber(queryParams[i])) {
						preparedStatement.setInt(i, Integer.parseInt(queryParams[i]));
					} else {
						preparedStatement.setString(i, queryParams[i]);
					}
				}
			}
			ResultSet rs = preparedStatement.executeQuery();
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

	
	QueryResponse getdummyEnvHome(String userid)
	{

		int rowCount=0;
		int flag=0;
		int flag1=0;
		QueryResponse response = new QueryResponse();
		Column c;
		List<Column> columns = new ArrayList();
		List<List<String>> rows = new ArrayList();
		List<String> singleRow = null;
		
		String query1="select distinct application_name from public.tapplication_info JOIN public.tenvironment_master on public.tenvironment_master.application_id=public.tapplication_info.application_id";
		try (Connection connection = idpPostGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query1);ResultSet rs = preparedStatement.executeQuery();) {

			
			List<String> app=new ArrayList<>();
			List<String> resultsi2p=new ArrayList<>();

			while(rs.next())app.add(rs.getString(1));
			
			try (Connection restconnection1 = postGreSqlDbContext.getConnection();
					PreparedStatement restStatement1 = restconnection1.prepareStatement("select application_name from apporg ao, userorg uo where ao.org_name=uo.org_name and uo.user_name='"+userid+"'");ResultSet rs2 = restStatement1.executeQuery();) {
				
				while (rs2.next()) {
					resultsi2p.add(rs2.getString(1));
				}
				
			} catch (Exception e) {
				logger.error("Exception in runTableQuery "+e, e);
				return response;
			}
			
			app.retainAll(resultsi2p);
			
			for(String appName:app)
			{
				
				query1="select environment_name from public.tapplication_info JOIN public.tenvironment_master on public.tenvironment_master.application_id=public.tapplication_info.application_id where application_name='" + appName + "'";
				
				try (Connection connectionNew = idpPostGreSqlDbContext.getConnection();
						PreparedStatement preparedStatementNew = connectionNew.prepareStatement(query1);ResultSet rs1 = preparedStatementNew.executeQuery();) {

					
					int temp=0;
					flag1=0;
					while (rs1.next()){
						
						
						temp++;

						  
						 
						String env=rs1.getString(1);
						
						

						String query2=" select buildstatus,stageName from  public.appinfo,public.buildinfo where ( (public.buildinfo.appid = public.appinfo.id) and stageName like '%Deploy%' and (application_name='"+ appName+"')) order by appinfo.created_at DESC";


						try (Connection connectionNew1 = postGreSqlDbContext.getConnection();
								PreparedStatement preparedStatementNew1 = connectionNew1.prepareStatement(query2,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);ResultSet rs2 = preparedStatementNew1.executeQuery();) {
							
							

							
							
							rs2.last();
							int a = rs2.getRow();
							rs2.beforeFirst();

							if ( a <=0) { 
								
								logger.info("0 rows");
							}
							
							else{
								while(rs2.next())
								{
									
									String env1=rs2.getString(2);
									if(env1.contains(env))
									{
										flag1=1;
										

										
										break;
									}
								}
							}
							

						} catch (Exception e) {
							
							logger.error("Exception in nested runTableQuery "+e, e);
							return response;
						}
					
					}
					if(flag1==1){

						rowCount=Math.max(temp, rowCount);
					}
					
					
					
				} catch (Exception e) {
					
					logger.error("Exception in runTableQuery "+e, e);
					return response;
				}
				
				

				
			}
			
			c = new Column();
			c.setText("Application_name");
			c.setType("String");
			columns.add(c);
			for(int i=0;i<rowCount;i++)
			{
				c = new Column();
				c.setText("EnvironmentName");
				c.setType("String");
				columns.add(c);
				c = new Column();
				c.setText("EnvironmentStatus");
				c.setType("String");
				columns.add(c);
			}
			int currmax=0;
			for(String appName:app)
			{
				flag=0;
				singleRow=new ArrayList<>();
				
				query1="select environment_name from public.tapplication_info JOIN public.tenvironment_master on public.tenvironment_master.application_id=public.tapplication_info.application_id where application_name='" + appName + "'";
				
				try (Connection connectionNew = idpPostGreSqlDbContext.getConnection();
						PreparedStatement preparedStatementNew = connectionNew.prepareStatement(query1,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
						ResultSet rs1 = preparedStatementNew.executeQuery();)
				{

					
					
					singleRow.add(appName);
					rs1.last();
					rs1.beforeFirst();

					int temp=0;
					while (rs1.next()) {
					  
						 
						String env=rs1.getString(1);
						int flag2=0;
						singleRow.add(env);
						temp++;

						String query2=" select buildstatus,stageName from  public.appinfo,public.buildinfo where ( (public.buildinfo.appid = public.appinfo.id) and stageName like '%Deploy%' and (application_name='"+ appName+"')) order by buildinfo.created_at DESC";


						try (Connection connectionNew1 = postGreSqlDbContext.getConnection();
								PreparedStatement preparedStatementNew1 = connectionNew1.prepareStatement(query2,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);ResultSet rs2 = preparedStatementNew1.executeQuery();) {
							
							

							
							
							rs2.last();
							int a = rs2.getRow();
							rs2.beforeFirst();

							if ( a <=0) { 
								
								logger.info("");
							}
							
							else{
								while(rs2.next())
								{


									String env1=rs2.getString(2);
									if(env1.contains(env))
									{
										flag=1;
										flag2=1;

										String s=rs2.getString(1);
										if(s.equalsIgnoreCase("success"))singleRow.add("<font color='green'>SUCCESS</font>");
										else singleRow.add("<font color='red'>FAILURE</font>");
										break;
									}
								}
							}
							if(flag2==0)singleRow.add("<font color='yellow'>NA</font>");

						} catch (Exception e) {
							
							logger.error("Exception in nested runTableQuery "+e, e);
							return response;
						}
					}

					currmax=Math.max(temp,currmax);
					if(flag==1)rows.add(singleRow);	

					
				} catch (Exception e) {
					
					logger.error("Exception in runTableQuery1 "+e, e);
					return response;
				}

			}
			response.setColumns(columns);
			response.setRows(rows);

			response.setType("table");
			return response;
		} catch (Exception e) {
			
			logger.error("Exception in runTableQuery2 ", e);
			return response;
		}
		


	}
	
	
	public Connection getConnection(String type) {
		Connection connection;
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
