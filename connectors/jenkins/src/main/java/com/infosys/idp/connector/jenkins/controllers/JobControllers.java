/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.idp.connector.jenkins.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.triggerparameter.ApproveBuildParams;
import org.infy.idp.entities.triggerparameter.TriggerParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infosys.idp.connector.jenkins.utils.Client;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "job")
public class JobControllers {
	protected Logger logger = LoggerFactory.getLogger(JobControllers.class);
	
	@Autowired
	Client jenkins;

	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public ResponseEntity<String> createJob(@RequestBody IDPJob idpjson){
		boolean result = jenkins.createNewJob(idpjson);
		if(result == true) {
			jenkins.buildByJobName(idpjson);
			return new ResponseEntity<>("Success", HttpStatus.OK);
		}
		return new ResponseEntity<>("Failure", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "build", method = RequestMethod.POST)
	public ResponseEntity<String> buildByJobName(@RequestBody TriggerParameters idp){
		jenkins.buildByJobName(idp);
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	
	@RequestMapping(value = "disableJob", method = RequestMethod.POST)
	public ResponseEntity<Integer> disableJob(@RequestBody String json){
		try {
			Gson gson = new Gson();
			Map<String,String> map = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
			return new ResponseEntity<>(jenkins.disableJob(map.get("jobName"), map.get("jobConfigFile")), HttpStatus.OK);
		} catch (IOException | InterruptedException e) {
			logger.error("Error Occured while disabling job", e);
			return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "approve", method = RequestMethod.POST)
	public ResponseEntity<?> apprRejectJobs(@RequestBody String json){
		try {
			Gson gson = new Gson();
			Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
			logger.info("THE MAPPED JSON VALUE:::::::::::::::::: "+map);
			ApproveBuildParams approveBuildParams = gson.fromJson(gson.toJson(map.get("approveBuildParams")), ApproveBuildParams.class);
			return new ResponseEntity<>(jenkins.apprRejectJobs(approveBuildParams, (String)map.get("to")), HttpStatus.OK);
		} catch (InterruptedException | IOException e) {
			logger.error("Error Occured while approve/reject job", e);
			return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "get/status", method = RequestMethod.POST)
	public ResponseEntity<?> getBuildStatus(@RequestBody String json){
		try {
			Gson gson = new Gson();
			Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
			return new ResponseEntity<>(jenkins.getBuildStatus((String)map.get("jobName")
					, (int)Math.round((double)map.get("buildnumber"))
					, (long)Math.round((double)map.get("whenToTimeout")))
					, HttpStatus.OK);
		} catch (URISyntaxException e) {
			logger.error("Error Occured while getting build status", e);
			return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}