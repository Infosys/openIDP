/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.controller.userservice;

import java.util.List;

import org.infy.idp.TokenUtils;
import org.infy.idp.businessapi.JobsBL;
import org.infy.idp.controller.BaseResource;
import org.infy.idp.entities.jobs.UserRolesPermissions;
import org.infy.idp.entities.jobs.common.Notification;
import org.infy.idp.entities.jobs.common.NotificationList;
import org.infy.idp.entities.models.ResourceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping(value = "userService")
public class UserService extends BaseResource {
	private static final String FAILURE = "FAILURE";
	private static final String SUCCESS = "SUCCESS";

	/**
	 * 
	 * Get Roles and Permissions for User
	 * 
	 * @param taskid the String
	 * @param auth   the OAuth2Authentication
	 * @return the resource response
	 * 
	 * 
	 */
	@Autowired
	private JobsBL jobsBL;

	/** The logger. */
	protected final Logger logger = LoggerFactory.getLogger(UserService.class);

	/**
	 * Returns role permission for the user
	 * 
	 * @param taskid
	 * @param auth
	 * @return ResourceResponse<String>
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/{id}/getUserRolesPermissions", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResourceResponse<String> getUserRolePermisson(@PathVariable("id") String taskid, OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving user role permission");
			Gson gson = new Gson();

			List<String> userRoles = jobsBL.getRoles(auth.getPrincipal().toString().toLowerCase());
			userRoles.addAll(jobsBL.getBaseRoles(auth.getPrincipal().toString().toLowerCase()));
			List<String> userPermissions = jobsBL.getAllPermission(auth.getPrincipal().toString().toLowerCase());

			UserRolesPermissions user = new UserRolesPermissions();
			user.setUserId(auth.getPrincipal().toString().toLowerCase());
			user.setRoles(userRoles);
			user.setPermissions(userPermissions);
			user.setOrgName(TokenUtils.getOrganization(auth));
			resourceResponse.setResource(gson.toJson(user, UserRolesPermissions.class));
			resourceResponse.setStatus(SUCCESS);
		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * 
	 * Get Roles and Permissions for User for an application
	 * 
	 * @param taskid the String
	 * @param auth   the OAuth2Authentication
	 * @return the resource response
	 * 
	 * 
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/notification", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResourceResponse<String> getNotifications(OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving notification");
			Gson gson = new Gson();
			List<Notification> notificationList = jobsBL.getNotifications(auth.getPrincipal().toString().toLowerCase());
			NotificationList notifications = new NotificationList();
			notifications.setNotificationList(notificationList);
			resourceResponse.setResource(gson.toJson(notifications));
			resourceResponse.setStatus(SUCCESS);
		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

	/**
	 * 
	 * Get Roles and Permissions for User for an application
	 * 
	 * @param taskid the String
	 * @param auth   the OAuth2Authentication
	 * @return the resource response
	 * 
	 * 
	 */
	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value = "/applications/roles/{application_name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResourceResponse<String> getUserRolePermissonForApp(@PathVariable("application_name") String applicationName,
			OAuth2Authentication auth) {
		ResourceResponse<String> resourceResponse = new ResourceResponse<>();
		try {
			logger.info("Retrieving user role permission");
			Gson gson = new Gson();
			List<String> userRoles = jobsBL.getRolesForApp(applicationName,
					auth.getPrincipal().toString().toLowerCase());
			userRoles.addAll(jobsBL.getBaseRoles(auth.getPrincipal().toString().toLowerCase()));
			List<String> userPermissions = jobsBL.getPermissionForApplications(applicationName,
					auth.getPrincipal().toString().toLowerCase());
			List<String> pipelinepermission = jobsBL.getPipelinePermissionforApplication(applicationName,
					auth.getPrincipal().toString().toLowerCase());
			userPermissions.addAll(pipelinepermission);

			UserRolesPermissions user = new UserRolesPermissions();
			user.setUserId(auth.getPrincipal().toString().toLowerCase());
			user.setRoles(userRoles);
			user.setPermissions(userPermissions);
			resourceResponse.setResource(gson.toJson(user, UserRolesPermissions.class));
			resourceResponse.setStatus(SUCCESS);
		} catch (Exception ex) {
			resourceResponse.setStatus(FAILURE);
			resourceResponse.setErrorMessage(ex.toString());
			logger.error(ex.toString(), ex);
		}
		return resourceResponse;
	}

}
