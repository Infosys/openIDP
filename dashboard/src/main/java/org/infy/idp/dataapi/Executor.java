/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.infy.idp.utils.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Executor class to update postgres database
 */
@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class Executor {
  private static final  Logger logger= LoggerFactory.getLogger(Executor.class);
  @Autowired
  private PostGreSqlDbContext postGreSqlDbContext;
  @Autowired
  private ConfigurationManager configmanager;

  private Executor(){
  }

/**
 * inserts user-organization details in DB
 * 
 * @param userOrg
 * @return
 * @throws SQLException
 * @throws IOException
 */
  public Integer insertUsers(String userOrg) throws SQLException, IOException {
	  String userList= userOrg.split(";")[0];
	  String orgName = userOrg.split(";")[1];
    String queryStatement = "INSERT INTO userorg (user_name,org_name) VALUES (?,?) ON CONFLICT(user_name) DO NOTHING";
    int count = 0;
    try (Connection connection = postGreSqlDbContext.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
      connection.setAutoCommit(false);
      for (String user : userList.split(",")) {
        preparedStatement.setString(1, user.toLowerCase()+ "@" + orgName.toLowerCase() + ".com");
        preparedStatement.setString(2, orgName);
        preparedStatement.addBatch();
        if (++count % Integer.parseInt(configmanager.getBatchSize()) == 0) {
          preparedStatement.executeBatch();
        }
      }
      preparedStatement.executeBatch(); // insert remaining records
      
      connection.commit();
    }
    catch (SQLException e) {
      logger.error("Postgres Error while inserting User details:", e);
      throw e;
    }
    catch (Exception e){
      logger.error("Error while inserting User details:", e);
      throw e;
    }
    return 1;
  }
  
  /**
   * Inserts application-organization details in DB
   * 
   * @param appOrg
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public Integer insertApplication(String appOrg) throws SQLException, IOException {
	  String appList = appOrg.split(";")[0];
	  String orgName = appOrg.split(";")[1];
    String queryStatement = "INSERT INTO apporg (application_name,org_name) VALUES (?,?) ON CONFLICT(application_name) DO NOTHING";
    int count = 0;
    try (Connection connection = postGreSqlDbContext.getConnection();
      PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
      connection.setAutoCommit(false);
      for (String app : appList.split(",")) {
        preparedStatement.setString(1, app);
        preparedStatement.setString(2, orgName);
        preparedStatement.addBatch();
        if (++count % Integer.parseInt(configmanager.getBatchSize()) == 0) {
          preparedStatement.executeBatch();
        }
      }
      preparedStatement.executeBatch(); // insert remaining records
      
      connection.commit();
    }
    catch (SQLException e) {
      logger.error("Postgres Error while inserting User details:", e);
      throw e;
    }
    catch (Exception e){
      logger.error("Error while inserting User details:", e);
      throw e;
    }
    return 1;
  }
  
  /**
   * Inserts application details in DB
   * 
   * @param id
   * @param appName
   * @param data
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public Integer insertAppDetails(int id,String appName, String data) throws SQLException, IOException{
	  String tableName = "tapplication_details";
		StringBuilder queryStatement = new StringBuilder();

		queryStatement.append("INSERT INTO " + tableName);
		queryStatement.append(" (application_id,application_name, entity_info) VALUES (?, ?, cast(? as json)) ");
		queryStatement.append("ON CONFLICT (application_name) DO UPDATE ");
		queryStatement.append("SET entity_info=cast(? as json) ");
		
		
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement.toString())) {
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, appName);
			preparedStatement.setObject(3, data);
			preparedStatement.setObject(4, data);
			
			preparedStatement.executeUpdate();
			
		}

		catch (SQLException | NullPointerException e) {
			logger.error("Postgres Error while inserting the data in tapplication_details:", e);

		}

		return 1;
  }
  
  /**
   * Inserts ICQA details in DB
   * 
   * @param appid
   * @param className
   * @param mi
   * @param cp
   * @param dp
   * @return
   * @throws SQLException
   * @throws IOException
   */
  public Integer insertICQAdetails(int appid,String className,String cbo,String cr,String doth,String loc,String locom1,String mnol,String mnop,String noa,String noam,String nocon,String noo,String noom,String norm,String rfc,String cc,String wmpc1,String mi,String cp,String dp) throws SQLException, IOException{
	  
	   String queryStatement ="INSERT INTO icqa (appid,classname,\"CBO\",\"CR\",\"DOTH\",\"LOC\",\"LOCOM1\",\"MNOL\",\"MNOP\",\"NOA\",\"NOAM\",\"NOCON\",\"NOO\",\"NOOM\",\"NORM\",\"RFC\",\"CC\",\"WMPC1\",\"mi\",\"cp\",\"dp\") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
      logger.info("icqa details");
	  
	   try (Connection connection = postGreSqlDbContext.getConnection();
	        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
	    	preparedStatement.setInt(1,appid);
	    	preparedStatement.setString(2,className);
	    	preparedStatement.setString(3,cbo);
	    	preparedStatement.setString(4,cr);
	    	preparedStatement.setString(5,doth);
	    	preparedStatement.setString(6,loc);
	    	preparedStatement.setString(7,locom1);
	    	preparedStatement.setString(8,mnol);
	    	preparedStatement.setString(9,mnop);
	    	preparedStatement.setString(10,noa);
	    	preparedStatement.setString(11,noam);
	    	preparedStatement.setString(12,nocon);
	    	preparedStatement.setString(13,noo);
	    	preparedStatement.setString(14,noom);
	    	preparedStatement.setString(15,norm);
	    	preparedStatement.setString(16,rfc);
	    	preparedStatement.setString(17,cc);
	    	preparedStatement.setString(18,wmpc1);
	    	preparedStatement.setString(19,mi);
	    	preparedStatement.setString(20,cp);
	    	preparedStatement.setString(21,dp);
	    	preparedStatement.executeUpdate();
	    	
	      
	     }		     
	    
	     catch (SQLException e) {
	      logger.error("Postgres Error while inserting the insertHistoryInfo:",e);
	      throw e;
	      
	    }
	    
	  return 1;
}
  
  
}
