/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.idp.connector.jenkins.controllers;

import java.io.IOException;
import java.util.Map;

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
@RequestMapping(value = "config")
public class ConfigControllers {
	protected Logger logger = LoggerFactory.getLogger(ConfigControllers.class);
	
	@Autowired
	Client jenkins;
	
	@RequestMapping(value = "add/alm/config", method = RequestMethod.POST)
	public ResponseEntity<?> addALMConfig(@RequestBody String json){
		try {
			Gson gson = new Gson();
			Map<String,String> map = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
			return new ResponseEntity<>(jenkins.addALMConfig(map.get("inputalmServerName")
					, map.get("almServerUrl")), HttpStatus.OK);
		} catch (IOException e) {
			logger.error("Error Occured while adding ALM config", e);
			return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (InterruptedException e) {
			logger.error("Error Occured while adding ALM config", e);
			return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "get/stageviewurl", method = RequestMethod.POST)
	public ResponseEntity<?> getStageViewUrl(@RequestBody String json){
		try {
			Gson gson = new Gson();
			Map<String,String> map = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
			return new ResponseEntity<>(jenkins.getStageViewUrl(map.get("appName"), map.get("pipelineName"))
					, HttpStatus.OK);
		} catch (IOException e) {
			logger.error("Error Occured while fetching stage view url", e);
			return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			logger.error("Error Occured while fetching stage view url", e);
			return new ResponseEntity<>(-2, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "get/jobjson", method = RequestMethod.POST)
	public ResponseEntity<?> getJobJSON(@RequestBody String json){
		try {
			Gson gson = new Gson();
			Map<String,String> map = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
			return new ResponseEntity<>(jenkins.getJobJSON(map.get("jobName")
					, map.get("param"))
					, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error Occured while fetching stage view url", e);
			return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
