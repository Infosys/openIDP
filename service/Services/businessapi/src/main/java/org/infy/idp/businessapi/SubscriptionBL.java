/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.businessapi;

import java.sql.SQLException;
import java.util.List;

import org.infy.idp.dataapi.services.SubscriptionDL;
import org.infy.idp.entities.jobs.Names;
import org.infy.idp.entities.subscription.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * The class SubscriptionBL contains methods related to subscription of IDP
 * @author Infosys
 */

@Component
public class SubscriptionBL {
	@Autowired
	private SubscriptionDL subscriptionDL;

	protected Logger logger = LoggerFactory.getLogger(SubscriptionBL.class);

	/**
	 * 
	 * @param message
	 */
	public void updateSubScriptionList(String message) {
		Gson gson = new Gson();

		Subscription subscription = gson.fromJson(message, Subscription.class);
		try {
			subscriptionDL.deleteOldSubscriptions(subscription);
			subscriptionDL.updateSubscription(subscription);
		} catch (SQLException | NullPointerException e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * 
	 * @return List<SubscriptionType>
	 */

	public Names getSubscriptionPermission(String orgName) {
		Names names = new Names();
		List<String> subscriptions = subscriptionDL.getSubscriptionPermission(orgName);
		names.setNames(subscriptions);

		return names;
	}

}
