/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp;

import org.infy.idp.bl.InsertInfoBL;

import org.infy.idp.entities.JsonClass;
import org.infy.idp.entities.RebaseData;
import org.infy.idp.utils.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * Class UpdateController to update postgres Database
 *
 */
@Controller
public class UpdateController {
	/** The logger. */
	protected Logger logger;
	@Autowired
	private ConfigurationManager configmanager;
	
	@Autowired
    private InsertInfoBL insertbl;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	/**

	 * constructor FooController
	 *
	 */
	private static String success="success";
	
    public UpdateController() {
        super();
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    // API - read
    

	/**

	 * method findById
	 * 
	 *@param auth the OAuth2Authentication
	 *@param id the long
	 *
	 *@return Foo
	 */
    
    @RequestMapping(method = RequestMethod.POST, value = "/{appname}/{pipname}/{pipbuildno}/update")
    @ResponseBody
    public String updateInfo(@PathVariable("appname") final String appname,@PathVariable("pipname") final String pipelinename,@PathVariable("pipbuildno") final int pipelinebuildno,@RequestBody JsonClass json) {
    	insertbl.processInfo(json, appname, pipelinename, pipelinebuildno);
    	return success;
    }
    
    
    //add for vsts or jira upload in db
    
    @RequestMapping(method = RequestMethod.POST, value = "/{triggerId}/{buildNum}")
    @ResponseBody
    public String updateVSTSData(@PathVariable("triggerId") String triggerId,@PathVariable("buildNum") String buildNum) {
    	
    	logger.info("Updating for triggerId="+triggerId+" and build no = "+buildNum);
    	insertbl.updateVSTSJiraDb(triggerId, buildNum);
    	
    	return success;
    }

	@RequestMapping(method = RequestMethod.POST, value = "/{triggerId}/{buildNum}/fileNet")
    @ResponseBody
    public String updateFileNetData(@PathVariable("triggerId") String triggerId,@PathVariable("buildNum") String buildNum,@RequestBody JsonClass json) {
    	logger.info("Updating for triggerId="+triggerId+" and build no = "+buildNum);
    	insertbl.updateFileNetDB(triggerId, json);
    	return success;
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/SAP/Rebase")
    @ResponseBody
    public String insertSAPRebaseData(@RequestBody RebaseData rebaseData) {
    	logger.info("Updating rebase data in IDP db");
    	Gson gson = new Gson();
    	String str = gson.toJson(rebaseData);
    	kafkaTemplate.send("SAPRebaseInfo", str);
    	return success;
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/{appid}/{classname}/{mi}/{cp}/{dp}/update")
    @ResponseBody
    public String updateInfo(@PathVariable("appid") final int appid,@PathVariable("classname") final String classname,@PathVariable("mi") final String mi,@PathVariable("mi") final String cp,@PathVariable("mi") final String dp) {
    	insertbl.dataInfo(appid, classname, mi, cp, dp);
    	return success;
    }
}
