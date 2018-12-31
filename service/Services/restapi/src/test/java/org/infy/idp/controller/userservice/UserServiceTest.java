/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.controller.userservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.infy.idp.businessapi.JobsAdditionalInfo;
import org.infy.idp.businessapi.JobsBL;
import org.infy.idp.businessapi.JobsManagementBL;
import org.infy.idp.entities.models.ResourceResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = UserService.class)
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ActiveProfiles("mvc")
public class UserServiceTest {

	@Autowired
	@InjectMocks
	private UserService userService;

	@Mock
	private JobsBL jobsBL;
	@Mock
	private JobsManagementBL jobsmgmtBL;
	@Mock
	private JobsAdditionalInfo jobsaddInfo;
	@Mock
	private OAuth2Authentication authBean;

	@Test
	public void testGetUserRolePermissonForApp_forException() {
		String applicationName = "appName";
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsaddInfo.getRolesForApp(applicationName, authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn(null);
		ResourceResponse<String> response = userService.getUserRolePermissonForApp("RajaprabuUnitTest", authBean);
		assertNotNull(response);
		assertNull(response.getResource());
		assertEquals("FAILURE", response.getStatus());
		assertNotNull(response.getErrorMessage());
	}

	@Test
	public void testGetUserRolePermissonForApp_forValidInputs() throws Throwable {
		String applicationName = "appName";
		List<String> userRoles = new ArrayList<String>();
		List<String> userBaseRoles = new ArrayList<String>();
		List<String> pipelinepermission = new ArrayList<String>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsaddInfo.getRolesForApp(applicationName, authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn(userRoles);
		Mockito.when(jobsmgmtBL.getBaseRoles(authBean.getPrincipal().toString().toLowerCase())).thenReturn(userBaseRoles);
		Mockito.when(jobsaddInfo.getPipelinePermissionforApplication(applicationName,
				authBean.getPrincipal().toString().toLowerCase())).thenReturn(pipelinepermission);

		ResourceResponse<String> response = userService.getUserRolePermissonForApp(applicationName, authBean);
		assertNotNull(response);
		assertNotNull(response.getResource());
		assertEquals("SUCCESS", response.getStatus());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testGetUserRolePermisson_forException() {
		ResourceResponse<String> response = userService.getUserRolePermisson("RajaprabuUnitTest", authBean);
		assertNotNull(response);
		assertNull(response.getResource());
		assertEquals("FAILURE", response.getStatus());
		assertNotNull(response.getErrorMessage());
	}

	@Test
	public void testGetUserRolePermisson_forValidInputs() {
		List<String> userRoles = new ArrayList<String>();
		List<String> userBaseRoles = new ArrayList<String>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsmgmtBL.getRoles(authBean.getPrincipal().toString().toLowerCase())).thenReturn(userRoles);
		Mockito.when(jobsmgmtBL.getBaseRoles(authBean.getPrincipal().toString().toLowerCase())).thenReturn(userBaseRoles);

		ResourceResponse<String> response = userService.getUserRolePermisson("1", authBean);
		assertNotNull(response);
		assertNotNull(response.getResource());
		assertEquals("SUCCESS", response.getStatus());
		assertNull(response.getErrorMessage());
	}

}