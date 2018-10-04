/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.subscription.entities.licencekeymanagement;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * 
 * @author Infosys
 *
 */
public class Subscription {
	@SerializedName("service")
    @Expose
	private List<Service> subscriptionTypes;

	public List<Service> getSubscriptionTypes() {
		return subscriptionTypes;
	}

	public void setSubscriptionTypes(List<Service> subscriptionTypes) {
		this.subscriptionTypes = subscriptionTypes;
	}
	

}
