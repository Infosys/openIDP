/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.controller.triggerservice;

import org.infy.idp.businessapi.JobsAdditionalInfo;
import org.infy.idp.entities.models.ResourceResponse;
import org.infy.idp.utils.SlaveDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "trigger")
public class TriggerService {

	@Autowired
	private SlaveDetails slaveDetails;
	@Autowired
	private JobsAdditionalInfo jobsAdditionalInfo;
	private Logger logger = LoggerFactory.getLogger(TriggerService.class);

	/**
	 * Returns status of the specified slave
	 * 
	 * @param slaveName
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/slavestatus/{slavename}", method = RequestMethod.GET)
	public ResourceResponse<String> getJobs(@PathVariable("slavename") String slaveName, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving slave status details");

			String status = slaveDetails.getSlaveStatus(slaveName);
			resourceResponse.setResource(status);
			resourceResponse.setStatus("SUCCESS");

		} catch (Exception ex) {
			resourceResponse.setStatus("FAILURE");
			resourceResponse.setErrorMessage(ex.toString());

		}
		return resourceResponse;
	}

}
