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
import java.util.ArrayList;
import java.util.List;

import org.infy.idp.entities.VSTSDataBean;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * class InsertFetchVSTS has methods for handling VSTS related queries in DB
 *
 */


@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class InsertFetchVSTS {
	
	protected Logger logger=LoggerFactory.getLogger(InsertFetchVSTS.class);
	
	public static final String BEANSET="Bean set ";
	
	@Autowired
	private IDPPostGreSqlDbContext idpPostGreSqlDbContext;
	
	/**
	 * Returns trigger entity for a particular triggerId
	 * @param triggerId
	 * @return
	 */
	public JSONObject getTriggerJSON(Integer triggerId) {
		
		StringBuilder queryStatement = new StringBuilder();
		logger.info("executing query :select trigger_entity from ttrigger_history where trigger_id=?");
		queryStatement.append("select trigger_entity from ttrigger_history where trigger_id=?");
	
		try (Connection connection = idpPostGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {
			
			preparedStatement.setInt(1,triggerId);

			ResultSet result = preparedStatement.executeQuery();
			JSONObject obj = null;
			 if(result.next()) {
				 
				 obj = new JSONObject(result.getString("trigger_entity"));          	 
            	
				 logger.info("trigger enitity fetched successfully");
                 logger.info("trigger enitity : "+obj);
             }
             
             
			
			
             return obj;
             
		}catch(SQLException|JSONException |NullPointerException e){
			logger.error("Postgres Error while fetching trigger json",e);
		}
		
		return null;
	}

	/**
	 * Updates ttrigger_history table in DB 
	 * 
	 * @param vstsBean
	 * @param tfsWorkItem
	 * @param jiraProjectKey
	 * @param userStoryString
	 * @param triggerId
	 * @return
	 */
	public String updateJobStatus(VSTSDataBean vstsBean, String tfsWorkItem,String jiraProjectKey,String userStoryString, Integer triggerId) {
		
		String workitem = "";
		String tableName = "ttrigger_history";
        StringBuilder queryStatement = new StringBuilder();
        logger.info("executing query "+"update"+" "+tableName+" "+"set"+"build_status= ?, tfs_workitem=?"+"where"+"trigger_id=?");
        queryStatement.append("update"+" "+tableName+" "+"set"+" build_status= ?, tfs_workitem=?,execution_no_link=?,scm_branch=?,environment=?,deploy_status=?,test_status=?,artifact_link=?,build_triggered=?,deploy_triggered=?,test_triggered=?,artifact_name=?,version=?,jiraProjectKey=?,userStoryString=?"+" where "+" trigger_id=? ");
        try (Connection connection = idpPostGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString());) {
			
        	preparedStatement.setString(1,vstsBean.getBldstatus());
            preparedStatement.setString(2, tfsWorkItem);
            preparedStatement.setString(3, vstsBean.getExecNoLink());
            preparedStatement.setString(4, vstsBean.getScmBranch());
            preparedStatement.setString(5, vstsBean.getEnv());                                            
            preparedStatement.setString(6, vstsBean.getDepStatus());
            preparedStatement.setString(7, vstsBean.getTstStatus());
            preparedStatement.setString(8, vstsBean.getArtilink());
            preparedStatement.setString(9, vstsBean.getBuild());
            preparedStatement.setString(10, vstsBean.getDeploy());   
            preparedStatement.setString(11, vstsBean.getTest());   
            preparedStatement.setString(12, vstsBean.getArtivalue());   
            preparedStatement.setString(13, vstsBean.getExecNo());
            preparedStatement.setString(14, jiraProjectKey);
            preparedStatement.setString(15, userStoryString);
            preparedStatement.setInt(16, triggerId);
            preparedStatement.executeUpdate();
            
            
            logger.info("table updated"+ triggerId);      

            if(tfsWorkItem!=null && !"".equalsIgnoreCase(tfsWorkItem) && !"NA".equalsIgnoreCase(tfsWorkItem)) {
				workitem = tfsWorkItem;
            }
           
			
            
            return workitem;
             
		}catch(SQLException|NullPointerException e){
		    logger.error("Postgres Error while updating trigger history ",e);
		}
		return null;
	}
	
	/**
	 * Returns data from ttrigger_histtory for specific workItem
	 * 
	 * @param workItem
	 * @return
	 */
	public List<VSTSDataBean> getPipelinesForWorkitem(String workItem) {

		
		List<VSTSDataBean> vstsDataList = new ArrayList<>();
		StringBuilder queryStatement = new StringBuilder();
		logger.info("executing query "+"select * from ttrigger_history where tfs_workitem like '%?%'");
		queryStatement.append("select trigger_id,((trigger_entity::json -> 'applicationName') || '_' || (trigger_entity::json -> 'pipelineName')) as pipeline,"
				+ "trigger_entity::json ->'userName' as userName,version,execution_no_link,scm_branch,artifact_name,artifact_link,environment,build_status,"
				+ "deploy_status,test_status,trigger_time from ttrigger_history where tfs_workitem like ? ");
		try (	Connection connection = idpPostGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setString(1,"%"+workItem+"%");                         

			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()) {
								
				VSTSDataBean vstsBean = new VSTSDataBean();
				vstsBean.setPipelineName(result.getString("pipeline").replace("\"", ""));
				vstsBean.setExecNo(result.getString("version").replace("\"", ""));
				vstsBean.setExecNoLink(result.getString("execution_no_link").replace("\"", ""));
				vstsBean.setUser(result.getString("userName").replace("\"", ""));				
				vstsBean.setScmBranch(result.getString("scm_branch")!=null?result.getString("scm_branch").replace("\"", ""):"NA");
				vstsBean.setArtivalue(result.getString("artifact_name")!=null?result.getString("artifact_name").replace("\"", ""):"NA");
				vstsBean.setArtilink(result.getString("artifact_link")!=null?result.getString("artifact_link").replace("\"", ""):"NA");
				vstsBean.setEnv(result.getString("environment")!=null?result.getString("environment").replace("\"", ""):"NA");
				vstsBean.setBldstatus(result.getString("build_status")!=null?result.getString("build_status").replace("\"", ""):"NA");
				vstsBean.setDepStatus(result.getString("deploy_status")!=null?result.getString("deploy_status").replace("\"", ""):"NA");
				vstsBean.setTstStatus(result.getString("test_status")!=null?result.getString("test_status").replace("\"", ""):"NA");
				vstsBean.setInsertTimestamp(result.getTimestamp("trigger_time"));
				
				vstsDataList.add(vstsBean);
				logger.info("Bean set for pipeline: "+vstsBean.getPipelineName());
				logger.info(BEANSET+vstsBean.getExecNo());
				logger.info(BEANSET+vstsBean.getExecNoLink());
				logger.info(BEANSET+vstsBean.getUser());
				logger.info(BEANSET+vstsBean.getScmBranch());
				logger.info(BEANSET+(vstsBean.getArtivalue()));
				logger.info(BEANSET+(vstsBean.getArtilink()));
				logger.info(BEANSET+(vstsBean.getEnv()));
				logger.info(BEANSET+(vstsBean.getBldstatus()));
				logger.info(BEANSET+(vstsBean.getDepStatus()));
				logger.info(BEANSET+(vstsBean.getTstStatus()));
				logger.info(BEANSET+vstsBean.getInsertTimestamp());
			}  




			
			
			return vstsDataList;


		}                              

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the data ",e);                  

		}

		return vstsDataList;
	
	
	}
	
	

}
