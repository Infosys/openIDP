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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infosys.idp.connector.jenkins.utils.Client;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "slave")
public class SlaveControllers {
	protected Logger logger = LoggerFactory.getLogger(SlaveControllers.class);
	
	@Autowired
	Client jenkins;
	
	@RequestMapping(value = "copy", method = RequestMethod.POST)
	public ResponseEntity<Integer> copySlave(@RequestBody String json){
		try {
			Gson gson = new Gson();
			Map<String,String> map = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
			return new ResponseEntity<>(jenkins.copySlave(map.get("slaveName")
					, map.get("workspace")
					, map.get("slaveLabel")
					, map.get("sshKeyPath")
					), HttpStatus.OK);
		} catch (IOException | InterruptedException e) {
			logger.error("Error Occured while copying slave", e);
			return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "get/status", method = RequestMethod.POST)
	public ResponseEntity<?> getSlaveStatus(@RequestBody String json){
		try {
			Gson gson = new Gson();
			Map<String,String> map = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
			return new ResponseEntity<>(jenkins.getSlaveStatus(map.get("slaveName"))
					, HttpStatus.OK);
		} 		
		catch (ResourceAccessException | HttpClientErrorException  e) {
			logger.error("Error Occured while copying slave", e);
			return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
