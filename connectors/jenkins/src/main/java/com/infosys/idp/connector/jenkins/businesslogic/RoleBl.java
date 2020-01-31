/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.idp.connector.jenkins.businesslogic;


import java.util.Base64;


import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.infosys.idp.connector.jenkins.utils.ConfigurationManager;

@Component
public class RoleBl {

	private static final String GLOBAL_ROLE = "globalRoles";
	private static final String PROJECT_ROLE = "projectRoles";

	private static final String ACCEPT = "Accept";
	private static final String ROLENAME = "roleName";
	private static final String TYPE = "type";
	private static final String PERMISSIONIDS = "permissionIds";
	private static final String PATTERN = "pattern";
	private static final String OVERWRITE = "overwrite";
	private static final String SIDS = "sids";
	private static final String SID = "sid";
	private static final String AUTHORIZATION = "Authorization";
	private static final String BASIC = "Basic ";

	private static final int STATUS_SUCCESS  = 0;
	private static final Logger logger = LoggerFactory.getLogger(RoleBl.class);

	@Autowired
	private ConfigurationManager configmanager;

	public HttpHeaders getHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		String s = configmanager.getJenkinsuserid() + ":" + configmanager.getJenkinspassword();
		String encoding = Base64.getEncoder().encodeToString(s.getBytes());
		headers.set(AUTHORIZATION, BASIC + encoding);
		return headers;
	}

	public HttpEntity<String> getRoles(String roleName, String roleType) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = getHeader();
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(configmanager.getJenkinsurl() + "/role-strategy/strategy/getRole")
				.queryParam(TYPE, roleType).queryParam(ROLENAME, roleName);

		HttpEntity<?> entity = new HttpEntity<>(headers);
		return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
	}

	public HttpEntity<String> addRoles(String roleName, String pattern, String roleType, String permissions) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = getHeader();

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(TYPE, roleType);
		map.add(ROLENAME, roleName);
		map.add(PERMISSIONIDS, permissions);
		map.add(OVERWRITE, "true");
		map.add(PATTERN, pattern);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		return restTemplate.postForEntity(configmanager.getJenkinsurl() + "/role-strategy/strategy/addRole", request,
				String.class);

	}

	public HttpEntity<String> assignRoles(String roleName, String user, String roleType) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = getHeader();

		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add(TYPE, roleType);
		map.add(ROLENAME, roleName);
		map.add(SID, user);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		return restTemplate.postForEntity(configmanager.getJenkinsurl() + "/role-strategy/strategy/assignRole", request,
				String.class);

	}

	public void createGlobalRole(String roleName, String usersList, String pattern) {

		HttpEntity<String> response;
		String globalRolePermission = "hudson.model.Item.Discover,hudson.model.Hudson.Read,hudson.model.Computer.Connect";

		// check IDP_User global only once if it created or not. if not it will create.
		// getRole of IDP_user Global

		response = getRoles(roleName, GLOBAL_ROLE);
		// if empty , create

		String[] newUsers = usersList.split(",");		
		try {
			JSONObject jsonObject = new JSONObject(response.getBody());
			if (!(jsonObject.has(PERMISSIONIDS) && jsonObject.has(SIDS))) {
				// Create Role:
				logger.info("create global role {}" , roleName);
				response = addRoles(roleName, pattern, GLOBAL_ROLE, globalRolePermission);
				logger.info("response Body: {}", response.getBody());

			} 
		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}

		for (String user : newUsers) {
			response = assignRoles(roleName, user, GLOBAL_ROLE);
			logger.info("response Body: {}", response.getBody());
		}
	}

	public void createProjectRole(String roleName, String usersList, String pattern) {
		HttpEntity<String> response;
		String projectRolesPermission = "hudson.model.Item.Read,hudson.model.Item.Build,hudson.model.Item.Configure,hudson.model.Item.Workspace";

		// check <appName>_Reader item is available or not

		response = getRoles(roleName, PROJECT_ROLE);
		// if empty , create

		String[] newUsers = usersList.split(",");

		try {
			JSONObject jsonObject = new JSONObject(response.getBody());
			if (!(jsonObject.has(PERMISSIONIDS) && jsonObject.has(SIDS))) {
				// Create Role:

				response = addRoles(roleName, pattern, PROJECT_ROLE, projectRolesPermission);
				logger.info("response Body : {}", response.getBody());

			}


		} catch (JSONException e) {
			logger.error(e.getMessage(), e);
		}

		// assign user to <appName>_Reader item role.
		for (String user : newUsers) {

			response = assignRoles(roleName, user, PROJECT_ROLE);

			logger.info("response Body : {}", response.getBody());
		}
	}

	public int createRole(String roleName, String usersList, String pattern, String roleType) {
		try {
			if (roleType.equalsIgnoreCase("GLOBAL")) {

				createGlobalRole(roleName, usersList, pattern);

			} else if (roleType.equalsIgnoreCase("PROJECT")) {

				createProjectRole(roleName, usersList, pattern);

			}

			return STATUS_SUCCESS;
		} catch (Exception e) {
			logger.error("Exception Thrown while creating roles : {}", e.getMessage(), e);
			throw e;
		}

	}
}
