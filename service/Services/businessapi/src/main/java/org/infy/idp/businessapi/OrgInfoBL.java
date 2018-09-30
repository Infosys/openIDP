/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.businessapi;

import org.infy.idp.dataapi.services.JobDetailsInsertionService;
import org.infy.idp.entities.subscription.OrgInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * 
 * The class OrgInfoBL contains methods for updating organization details
 * @author Infosys
 */
@Component
public class OrgInfoBL {
	
	@Autowired
	private JobDetailsInsertionService jobDetailsInsertionService;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	protected  Logger logger= LoggerFactory.getLogger(OrgInfoBL.class);
	/**
	 * 
	 * Updates organization info
	 * @param message
	 */
	public void updateOrgInfo(String message)
	{
		Gson gson = new Gson();
		
		OrgInfo orgInfo =gson.fromJson(message, OrgInfo.class);
		
		try {
			Long orgId= jobDetailsInsertionService.insertOrgInfo(orgInfo.getOrgName(),orgInfo.getDomain());
			kafkaTemplate.send("idpoauth", orgInfo.getOrgAdmin()+",;"+orgInfo.getOrgName());
			jobDetailsInsertionService.insertUsers(orgInfo.getOrgAdmin(),orgInfo.getOrgAdmin()+"@"+orgInfo.getDomain() ,true,orgId,"IDP_ADMIN");
			
		} catch ( NullPointerException e) {
			logger.error(e.getMessage());
		}
		
	}

}
