/**
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.”
*
**/
package org.infy.subscription.controllers;

import org.infy.subscription.Constants;
import org.infy.subscription.TokenUtils;
import org.infy.subscription.business.LicenseServiceBL;
import org.infy.subscription.entities.licencekeymanagement.Licenses;
import org.infy.subscription.entities.licencekeymanagement.Subscription;
import org.infy.subscription.entities.models.ResourceResponse;

import org.infy.subscription.exception.InvalidKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

/**
 * controller for license services
 *
 */

@RestController
@RequestMapping("/licenseService")
public class LicenseServiceController {
	@Autowired
	LicenseServiceBL licenseServiceBL;
	
	private static final Logger logger = LoggerFactory.getLogger(LicenseServiceController.class);
		
	/**
	 * 
	 * @param licenseKey
	 * @return
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value="/license/add",method = RequestMethod.PUT)
    public ResourceResponse<String> addLicense(@RequestBody String licenseKey,OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		String invalidKey = "Invalid License Key";
		boolean response=false;
		try {
			response=licenseServiceBL.addLicense(licenseKey, TokenUtils.getOrganization(auth));
			if(response)
			{
				resourceResponse.setResource(Constants.SUCCESS);
			}
		} 
		catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
			resourceResponse.setErrorMessage(invalidKey); 
			resourceResponse.setResource(Constants.FAILURE);
		}
		resourceResponse.setStatus(Constants.SUCCESS);
		return resourceResponse;
    }
	/**
	 * 
	 * @param licenseKey
	 * @return
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value="/license/validate",method = RequestMethod.POST)
    public ResourceResponse<String> validateLicense(@RequestBody String licenseKey) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		
		boolean result=licenseServiceBL.validateLicense(licenseKey);
		if(result)
		{
			resourceResponse.setResource(Constants.SUCCESS);
		}
		else 
		{
			resourceResponse.setResource(Constants.FAILURE);
		}
		resourceResponse.setStatus(Constants.SUCCESS);
		
		return resourceResponse;
    }
	

	
	/**
	 * 
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value="/license/service/active",method = RequestMethod.POST)
    public ResourceResponse<String> getAllActiveServices(OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Gson gson = new Gson();
		Subscription subscription=licenseServiceBL.getSubscriptions(TokenUtils.getOrganization(auth));
		String response = gson.toJson(subscription);
		resourceResponse.setStatus(response);
		resourceResponse.setResource(Constants.SUCCESS);
		return resourceResponse;
    }
	
	
	/**
	 * 
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value="/license/active",method = RequestMethod.POST)
    public ResourceResponse<String> getAllActiveLicense(OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		Gson gson = new Gson();
		Licenses licenses=licenseServiceBL.getAllActiveLicenses(TokenUtils.getOrganization(auth));
		String response = gson.toJson(licenses);
		resourceResponse.setStatus(response);
		resourceResponse.setResource(Constants.SUCCESS);
		return resourceResponse;
    }
}
