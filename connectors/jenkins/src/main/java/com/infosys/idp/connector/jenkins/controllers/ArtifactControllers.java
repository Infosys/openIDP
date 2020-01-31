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
@RequestMapping(value = "artifacts")
public class ArtifactControllers {
	protected Logger logger = LoggerFactory.getLogger(ArtifactControllers.class);
		
	@Autowired
	Client jenkins;
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public ResponseEntity<Integer> addArtifactoryRepoGlobConf(@RequestBody String json){
		try {
			Gson gson = new Gson();
			Map<String,String> map = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
			return new ResponseEntity<>(jenkins.addArtifactoryRepoGlobConf(map.get("repoUrl")
					, map.get("user"), map.get("pwd"), map.get("repoName")), HttpStatus.OK);
		} catch (IOException | InterruptedException e) {
			logger.error("Error Occured while disabling job", e);
			return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "download", method = RequestMethod.POST)
	public ResponseEntity<?> downloadArtifacts(@RequestBody String json){
		Gson gson = new Gson();
		Map<String,String> map = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
		return new ResponseEntity<>(jenkins.downloadArtifacts(map.get("daiJobName"), map.get("daiBuildNumber"), 
				map.get("daiSubJobName")), HttpStatus.OK);
	}
}
