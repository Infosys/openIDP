/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.entities.subscription.Service;
import org.infy.idp.entities.subscription.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The class SubscriptionDL contains methods to modify subscription info
 * 
 * @author Infosys
 *
 */
@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class SubscriptionDL {

	@Autowired
	private PostGreSqlDbContext postGreSqlDbContext;

	protected Logger logger = LoggerFactory.getLogger(SubscriptionDL.class);

	/**
	 * Constructor
	 * 
	 */
	private SubscriptionDL() {

	}

	public Integer deleteOldSubscriptions(Subscription subscription) {
		String queryStatement = "DELETE FROM public.tsubscription_info where org_name = ?;";
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			for (Service sub : subscription.getService()) {
				preparedStatement.setString(1, sub.getOrgName());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} catch (SQLException | NullPointerException e) {
			logger.error(e.getMessage());
		}
		return 1;
	}

	/**
	 * 
	 * @param subscription
	 * @return Integer
	 * @throws SQLException
	 */
	public Integer updateSubscription(Subscription subscription) throws SQLException {

		String queryStatement = "INSERT INTO public.tsubscription_info( subscription_type,expiry_date,org_name) VALUES (?,?,?);";
		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(queryStatement)) {
			for (Service sub : subscription.getService()) {
				preparedStatement.setString(1, sub.getServiceIdentity().getServiceName());
				preparedStatement.setDate(2, sub.getExpiryDate());
				preparedStatement.setString(3, sub.getOrgName());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
		} catch (SQLException | NullPointerException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return 1;
	}

	/**
	 * Get subscription methods method getBaseRoles
	 * 
	 * 
	 * 
	 *
	 * 
	 * @return List<String>
	 */

	public List<String> getSubscriptionPermission(String orgName) {
		List<String> permissions = new ArrayList<>();
		String rolesQuery1 = "SELECT permission " + "  FROM public.tsubscription_info,public.tsubscription_master "
				+ "  WHERE tsubscription_info.subscription_type = tsubscription_master.subscription_type "
				+ "AND expiry_date>=current_date " + "AND org_name like ? ;";

		ResultSet rs = null;

		try (Connection connection = postGreSqlDbContext.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(rolesQuery1)) {
			preparedStatement.setString(1, orgName);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				permissions.add(rs.getString("permission"));
			}

		}

		catch (SQLException | NullPointerException e) {

			logger.error("Postgres Error while fetching base role details from tuser_info or trole_permissionss:", e);

		}

		finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.info(e.getMessage(), e);
				}
			}
		}

		return permissions;
	}

}
