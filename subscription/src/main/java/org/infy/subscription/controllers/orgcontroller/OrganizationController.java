/**
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.”
*
**/
package org.infy.subscription.controllers.orgcontroller;

import java.util.List;

import org.infy.subscription.Constants;
import org.infy.subscription.business.EmailSender;
import org.infy.subscription.business.OrgBL;
import org.infy.subscription.entities.licencekeymanagement.OrganisationInfo;
import org.infy.subscription.entities.models.ResourceResponse;
import org.infy.subscription.entities.models.org.OrgInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for registration of organization
 */
@RestController
@RequestMapping("/org/registrationServices")
public class OrganizationController {
	
	@Autowired
	OrgBL orgRegistrationBL;
	
	@Autowired
	private EmailSender emailSender;
	
	/**
	 * @param orgInfo
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value="/register",method = RequestMethod.PUT)
    public ResourceResponse<String> registeration(@RequestBody OrgInfo orgInfo,OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		String response;
		response=orgRegistrationBL.registerOrg(orgInfo);
		resourceResponse.setStatus(Constants.SUCCESS);
		resourceResponse.setResource(response);
		return resourceResponse;
    }
	
	/**
	 * getOrganizations will return all the available organizations in IDP
	 * @param orgInfo
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value="/organizations",method = RequestMethod.GET)
    public ResourceResponse<List<OrgInfo>> getOrganizations(OAuth2Authentication auth) {
		ResourceResponse<List<OrgInfo>> resourceResponse = new ResourceResponse<>();
		List<OrgInfo> response = null;
		response = orgRegistrationBL.getOrgList();
		resourceResponse.setStatus(Constants.SUCCESS);
		resourceResponse.setResource(response);
		return resourceResponse;
    }
	
	/**
	 * 
	 * @param orgInfo
	 * @return ResourceResponse<Integer>
	 */	
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value="/update",method = RequestMethod.PUT)
    public ResourceResponse<String> updateOrganization(@RequestBody OrgInfo orgInfo,OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		String response = "0";
		response = String.valueOf(orgRegistrationBL.updateOrg(orgInfo));
		resourceResponse.setStatus(Constants.SUCCESS);
		resourceResponse.setResource(response);
		return resourceResponse;
    }
	
	/**
	 * 
	 * @param orgInfo
	 * @return ResourceResponse<Integer>
	 */	
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value="/mail",method = RequestMethod.POST)
    public ResourceResponse<String> sendMail(@RequestBody OrganisationInfo orgInfo,OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		String response = "0";
		boolean status = emailSender.orgCreationSuccessMail(orgInfo);
		if (status) {
			resourceResponse.setResource("success");
		} else {
			resourceResponse.setResource("error");
		}
		resourceResponse.setStatus(Constants.SUCCESS);
		resourceResponse.setResource(response);
		return resourceResponse;
    }


	/**
	 * 
	 * @param orgInfo
	 * @return ResourceResponse<Integer>
	 */	
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value="license/mail",method = RequestMethod.POST)
    public ResourceResponse<String> sendLicenseMail(@RequestBody OrganisationInfo orgInfo,OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		String response = "0";
		boolean status = emailSender.licenseCreationSuccessMail(orgInfo);
		if (status) {
			resourceResponse.setResource("success");
		} else {
			resourceResponse.setResource("error");
		}
		resourceResponse.setStatus(Constants.SUCCESS);
		resourceResponse.setResource(response);
		return resourceResponse;
    }
}
