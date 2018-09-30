/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.auth.datalayer;

import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.infy.idp.auth.datalayer.PostGreSqlDbContext;
import org.infy.idp.config.entities.LoginRequest;
import org.infy.idp.config.utils.ConfigurationManager;
import org.infy.idp.config.utils.KeyCloakManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Infosys
 */
@Component
public class Executor {
	private static final Logger logger = LoggerFactory.getLogger(Executor.class);
	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;
	@Autowired
	private ConfigurationManager configmanager;
	@Autowired
	private KeyCloakManagement kcmgmt;

	private Executor() {
	}

	/**
	 * insertUsers method
	 * 
	 * @param userList as String
	 *
	 * @return int object
	 * @throws IOException
	 */
	public String getOrgName(String user) {
		String queryStatement = "SELECT org_name FROM idpoauth.tuser_info WHERE user_id like ? ;";
		ResultSet rs = null;
		String orgName = "";
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			preparedStatement.setString(1, user);

			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				orgName = rs.getString(1);
			}

		} catch (SQLException e) {
			logger.error("Postgres Error while fetching org_name :", e);

		} catch (Exception e) {
			logger.error("KeyCloak Error while fetching org_name", e);

		} finally {
			if (null != rs) {
				try {
					rs.close();
				} catch (SQLException e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
		}
		return orgName;
	}

	/**
	 * 
	 * @param userOrg
	 * @return integer
	 * @throws SQLException
	 * @throws IOException
	 */

	public Integer insertUsers(String userOrg) throws SQLException, IOException {
		String userList = userOrg.split(";")[0];
		String orgName = userOrg.split(";")[1];
		String queryStatement = "INSERT INTO idpoauth.tuser_info (user_id,org_name) VALUES (?,?) ON CONFLICT(user_id) DO UPDATE set created_at = current_timestamp";
		int count = 0;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			connection.setAutoCommit(false);
			List<String> newUsers = checkExistingUsers(userList);
			for (String user : newUsers) {
				if (configmanager.getAuthProvider().toLowerCase().equals("keycloak")) {
					if (!kcmgmt.addUser(user, "firstlogon@idp", true, orgName)) {
						continue;
					}
				}
				preparedStatement.setString(1, user.toLowerCase());
				preparedStatement.setString(2, orgName);
				preparedStatement.addBatch();
				if (++count % Integer.parseInt(configmanager.getBatchSize()) == 0) {
					preparedStatement.executeBatch();
				}
			}
			preparedStatement.executeBatch(); // insert remaining records
			connection.commit();
		} catch (SQLException e) {
			logger.error("Postgres Error while inserting the insertuserdetails:", e);
			throw e;
		} catch (Exception e) {
			logger.error("KeyCloak Error while inserting the insertuserdetails:", e);
			throw e;
		}
		return 1;
	}

	/**
	 * performUserCheck method
	 * 
	 * @param logindetails as LoginRequest
	 *
	 * @return int object
	 */
	public Integer performUserCheck(LoginRequest logindetails) {
		String queryStatement = "Select user_id from idpoauth.tuser_info where user_id = (?)";
		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			preparedStatement.setString(1, logindetails.getUsername().toLowerCase());
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return 0;
			}
			return 1;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return 2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 3;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 
	 * @param userlist
	 * @return List<String>
	 */
	public List<String> checkExistingUsers(String userlist) {
		List<String> returnList = new ArrayList<String>();
		String queryStatement = "select * from unnest(?) as user_id where user_id NOT IN (SELECT user_id FROM idpoauth.tuser_info)";
		ResultSet rs = null;
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			Array array = connection.createArrayOf("VARCHAR", userlist.split(","));
			preparedStatement.setArray(1, array);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				returnList.add(rs.getString(1));
			}
			rs.close();
			return returnList;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return returnList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return returnList;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
