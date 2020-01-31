/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.idp.connector.jenkins.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infosys.idp.connector.jenkins.businesslogic.RoleBl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "role")
public class RoleControllers {
	protected Logger logger = LoggerFactory.getLogger(RoleControllers.class);
	
	
	@Autowired
	RoleBl roleBl;
	
	@PostMapping(value = "create")
	public ResponseEntity<Integer> createRole(@RequestBody String json){
		try {
			Gson gson = new Gson();
			Map<String,String> map = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());
			return new ResponseEntity<>(roleBl.createRole(map.get("roleName")
					, map.get("usersList")
					, map.get("pattern")
					, map.get("roleType")
					), HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error Occured while creating roles", e);
			return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
