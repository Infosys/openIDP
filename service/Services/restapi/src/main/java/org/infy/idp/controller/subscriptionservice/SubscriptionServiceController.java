/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.controller.subscriptionservice;

import org.infy.idp.TokenUtils;
import org.infy.idp.businessapi.SubscriptionBL;
import org.infy.idp.controller.BaseResource;
import org.infy.idp.entities.jobs.Names;
import org.infy.idp.entities.models.ResourceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping(value = "subscriptionService")
public class SubscriptionServiceController extends BaseResource {

	@Autowired
	private SubscriptionBL subscriptionBL;

	/** The logger. */
	protected final static Logger logger = LoggerFactory.getLogger(SubscriptionServiceController.class);

	/**
	 * Returns subscription permission for the specific organization
	 * 
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/subscriptions", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResourceResponse<String> getSubscriptionPermission(OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("getting subscription list");
			Gson gson = new Gson();
			Names names = subscriptionBL.getSubscriptionPermission(TokenUtils.getOrganization(auth));
			resourceResponse.setStatus(gson.toJson(names));
		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

}
