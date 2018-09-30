/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.utils.jira;


import org.infy.idp.dataapi.InsertFetchJira;
import org.infy.idp.utils.ConfigurationManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 
 * The class DetailsForJira
 *
 */
@Component
public class DetailsForJira {
	protected Logger logger=LoggerFactory.getLogger(DetailsForJira.class);
	
	@Autowired
	InsertFetchJira insertFetchJira;
	
	@Autowired 
	ConfigurationManager configurationManager;
	
public String getFromDatabase(Integer trigegrid){
	String jiraprojectKeyUserStories=null;
	logger.info("in getdfromdatabase");
	try {
		jiraprojectKeyUserStories=insertFetchJira.getDetailsFromDB(trigegrid);
		
		
		}
	
	catch(Exception e) {
		logger.error("Error: ",e);
	
	
	
		
	}	
	return jiraprojectKeyUserStories;
	

}
}
