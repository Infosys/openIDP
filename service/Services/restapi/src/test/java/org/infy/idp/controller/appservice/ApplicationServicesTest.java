/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.controller.appservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.infy.idp.TokenUtils;
import org.infy.idp.businessapi.EmailSender;
import org.infy.idp.businessapi.JobsAdditionalInfo;
import org.infy.idp.businessapi.JobsBL;
import org.infy.idp.businessapi.JobsManagementBL;
import org.infy.idp.businessapi.SubscriptionBL;
import org.infy.idp.entities.jobs.AppNames;
import org.infy.idp.entities.jobs.EnvName;
import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.Applications;
import org.infy.idp.entities.models.ResourceResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = ApplicationServices.class)
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
@ActiveProfiles("mvc")
public class ApplicationServicesTest {

	@Autowired
	@InjectMocks
	private ApplicationServices applicationServices;

	@Mock
	private JobsBL jobsBL;
	@Mock
	private JobsManagementBL jobsmgmtBL;
	@Mock
	private JobsAdditionalInfo jobsaddInfo;
	
	@Mock
	private SubscriptionBL subscriptionBL;

	@Mock
	private EmailSender emailSender;

	@Mock
	private OAuth2Authentication authBean;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetEnvironmentNamesForException() {
		ResourceResponse<String> response = applicationServices.getEnvironmentNames("taskid", "RajaprabuUnitTest",
				authBean);
		assertNotNull(response);
		assertNull(response.getResource());
		assertEquals("FAILURE", response.getStatus());
		assertNotNull(response.getErrorMessage());
	}

	@Test
	public void testGetEnvironmentNamesWhenNoEnvironmentsAreAvailable() {
		String AppName = "AppName";
		EnvName envs = new EnvName();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBL.getEnvironmentNames(AppName, authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn(envs);

		ResourceResponse<String> response = applicationServices.getEnvironmentNames("1", AppName, authBean);
		assertNotNull(response);
		assertEquals("No access", response.getResource());
		assertEquals("SUCCESS", response.getStatus());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testGetEnvironmentNamesWhenEnvironmentsAreAvailable() {
		String AppName = "AppName";
		EnvName envs = new EnvName();
		List<String> envNames = new ArrayList<String>();
		envNames.add("Env1");
		envs.setEnvNames(envNames);
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBL.getEnvironmentNames(AppName, authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn(envs);

		ResourceResponse<String> response = applicationServices.getEnvironmentNames("1", AppName, authBean);
		assertNotNull(response);
		assertNotEquals("No access", response.getResource());
		assertEquals("SUCCESS", response.getStatus());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testGetExistingAppsForException() {
		ResourceResponse<String> response = applicationServices.getExistingApps("IDP", "taskid", authBean);
		assertNotNull(response);
		assertNull(response.getResource());
		assertEquals("FAILURE", response.getStatus());
		assertNotNull(response.getErrorMessage());
	}

	@Test
	public void testGetExistingAppsForNoAvailableApplications() {
		Applications apps = new Applications();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBL.getExistingApps(authBean.getPrincipal().toString().toLowerCase())).thenReturn(apps);

		ResourceResponse<String> response = applicationServices.getExistingApps("IDP", "1", authBean);
		assertNotNull(response);
		assertEquals("No Application to Show", response.getResource());
		assertEquals("SUCCESS", response.getStatus());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testGetExistingAppsForAvailableApplications() {
		Applications apps = new Applications();
		List<Application> appList = new ArrayList<>();
		Application app = new Application();
		appList.add(app);
		apps.setApplications(appList);
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBL.getExistingApps(authBean.getPrincipal().toString().toLowerCase())).thenReturn(apps);

		ResourceResponse<String> response = applicationServices.getExistingApps("IDP", "1", authBean);
		assertNotNull(response);
		assertNotEquals("No Application to Show", response.getResource());
		assertEquals("SUCCESS", response.getStatus());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testGetExistingAppNamesForException() {
		ResourceResponse<String> response = applicationServices.getExistingAppNames(authBean, "IDP");
		assertNotNull(response);
		assertNull(response.getResource());
		assertEquals("FAILURE", response.getStatus());
		assertNotNull(response.getErrorMessage());
	}

	@Test
	public void testGetExistingAppNamesForNoAvailableApplications() {
		Applications apps = new Applications();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBL.getExistingAppNames(authBean.getPrincipal().toString().toLowerCase(), "IDP"))
				.thenReturn(apps);

		ResourceResponse<String> response = applicationServices.getExistingAppNames(authBean, "IDP");
		assertNotNull(response);
		assertEquals("No Application to Show", response.getResource());
		assertEquals("SUCCESS", response.getStatus());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testGetExistingAppNamesForAvailableApplications() {
		Applications apps = new Applications();
		List<Application> appList = new ArrayList<>();
		Application app = new Application();
		appList.add(app);
		apps.setApplications(appList);
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBL.getExistingAppNames(authBean.getPrincipal().toString().toLowerCase(), "IDP"))
				.thenReturn(apps);

		ResourceResponse<String> response = applicationServices.getExistingAppNames(authBean, "IDP");
		assertNotNull(response);
		assertNotEquals("No Application to Show", response.getResource());
		assertEquals("SUCCESS", response.getStatus());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testCreateApplicationForException() {
		ResourceResponse<String> response = applicationServices.createApplication("taskid", null, authBean);
		assertNotNull(response);
		assertNull(response.getResource());
		assertEquals("FAILURE", response.getStatus());
		assertNotNull(response.getErrorMessage());
	}

	@Test
	public void testCreateApplicationForValidCase() {
		ApplicationInfo appInfo = new ApplicationInfo();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsmgmtBL.createApplication(appInfo, authBean.getPrincipal().toString().toLowerCase(), "INFOSYS"))
				.thenReturn("");
		List<String> permission = new ArrayList<>();
		permission.add("CREATE_APPLICATION");
		Mockito.when(jobsaddInfo.getAllPermission(authBean.getPrincipal().toString().toLowerCase())).thenReturn(permission);
		Mockito.spy(TokenUtils.class);
		OAuth2AuthenticationDetails oAuth = Mockito.mock(OAuth2AuthenticationDetails.class);
		Mockito.when(authBean.getDetails()).thenReturn(oAuth);
		Mockito.when(oAuth.getDecodedDetails()).thenReturn(new HashMap<String, Object>() {
			{
				put("organization", "INFOSYS");
			}
		});
		ResourceResponse<String> response = applicationServices.createApplication("1", appInfo, authBean);
		assertNotNull(response);
		assertNotNull(response.getResource());
		assertEquals("SUCCESS", response.getStatus());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testGetAppInfoForException() {
		ResourceResponse<String> response = applicationServices.getAppInfo("taskid", "RajaprabuUnitTest", authBean);
		assertNotNull(response);
		assertNull(response.getResource());
		assertEquals("FAILURE", response.getStatus());
		assertNotNull(response.getErrorMessage());
	}

	@Test
	public void testGetAppInfoWhenNoApplicationsAreAvailable() {
		String AppName = "AppName";
		ApplicationInfo ap = new ApplicationInfo();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBL.getApplicationInfo(AppName, authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn(ap);

		ResourceResponse<String> response = applicationServices.getAppInfo("1", AppName, authBean);
		assertNotNull(response);
		assertEquals("No Application!!", response.getResource());
		assertEquals("SUCCESS", response.getStatus());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testGetAppInfoWhenApplicationsAreAvailable() {
		String AppName = "AppName";
		ApplicationInfo ap = new ApplicationInfo();
		ap.setApplicationName("App1");
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsBL.getApplicationInfo(AppName, authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn(ap);

		ResourceResponse<String> response = applicationServices.getAppInfo("1", AppName, authBean);
		assertNotNull(response);
		assertNotEquals("No Application!!", response.getResource());
		assertEquals("SUCCESS", response.getStatus());
		assertNull(response.getErrorMessage());
	}

	@Test(expected = NullPointerException.class)
	public void testUpdateAppinfoForException() {
		ResourceResponse<String> response = applicationServices.updateAppinfo("taskid", (ApplicationInfo) null,
				authBean);
		assertNotNull(response);
		assertNull(response.getResource());
		assertEquals("FAILURE", response.getStatus());
		assertNotNull(response.getErrorMessage());
	}

	@Test
	public void testUpdateAppinfoWhenNoPermissionsAreGiven() {
		ApplicationInfo ap = new ApplicationInfo();
		List<String> permission = new ArrayList<>();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsaddInfo.getAllPermission(authBean.getPrincipal().toString().toLowerCase())).thenReturn(permission);

		ResourceResponse<String> response = applicationServices.updateAppinfo("1", ap, authBean);
		assertNotNull(response);
		assertNull(response.getResource());
		assertEquals("SUCCESS", response.getStatus());
		assertEquals("No Access", response.getErrorMessage());
	}

	@Test
	public void testUpdateAppinfoWhenPermissionsAreGiven() {
		ApplicationInfo ap = new ApplicationInfo();
		List<String> permission = new ArrayList<>();
		permission.add("EDIT_APPLICATION");
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsaddInfo.getAllPermission(authBean.getPrincipal().toString().toLowerCase())).thenReturn(permission);
		Mockito.spy(TokenUtils.class);
		OAuth2AuthenticationDetails oAuth = Mockito.mock(OAuth2AuthenticationDetails.class);
		Mockito.when(authBean.getDetails()).thenReturn(oAuth);
		Mockito.when(oAuth.getDecodedDetails()).thenReturn(new HashMap<String, Object>() {
			{
				put("organization", "INFOSYS");
			}
		});

		ResourceResponse<String> response = applicationServices.updateAppinfo("1", ap, authBean);
		assertNotNull(response);
		assertEquals("SUCCESS", response.getStatus());
		assertEquals("success", response.getResource());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testGetApplicationForException() {
		ResourceResponse<String> response = applicationServices.getApplication("IDP", "taskid", authBean);
		assertNotNull(response);
		assertNull(response.getResource());
		assertEquals("FAILURE", response.getStatus());
		assertNotNull(response.getErrorMessage());
	}

	@Test
	public void testGetApplicationForNoApplications() throws Throwable {
		AppNames apps = new AppNames();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsmgmtBL.getApplications(authBean.getPrincipal().toString().toLowerCase(), "IDP")).thenReturn(apps);

		ResourceResponse<String> response = applicationServices.getApplication("IDP", "1", authBean);
		assertNotNull(response);
		assertEquals("SUCCESS", response.getStatus());
		assertEquals("No Application", response.getResource());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testGetApplicationForAvailableApplications() throws Throwable {
		AppNames apps = new AppNames();
		List<String> applicationNames = new ArrayList<>();
		applicationNames.add("Application1");
		apps.setApplicationNames(applicationNames);
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsmgmtBL.getApplications(authBean.getPrincipal().toString().toLowerCase(), "IDP")).thenReturn(apps);

		ResourceResponse<String> response = applicationServices.getApplication("IDP", "1", authBean);
		assertNotNull(response);
		assertEquals("SUCCESS", response.getStatus());
		assertNotEquals("No Application", response.getResource());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testGetApplicationDetailsForException() {
		ResourceResponse<String> response = applicationServices.getApplicationDetails("taskid", "appName", authBean);
		assertNotNull(response);
		assertNull(response.getResource());
		assertEquals("FAILURE", response.getStatus());
		assertNotNull(response.getErrorMessage());
	}

	@Test
	public void testGetApplicationDetailsForNoAvailableApplications() throws Throwable {
		String appName = "AppName";
		Application apps = new Application();
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsmgmtBL.getApplicationDetails(appName, authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn(apps);

		ResourceResponse<String> response = applicationServices.getApplicationDetails("1", appName, authBean);
		assertNotNull(response);
		assertEquals("No Application", response.getResource());
		assertEquals("SUCCESS", response.getStatus());
		assertNull(response.getErrorMessage());
	}

	@Test
	public void testGetApplicationDetailsForAvailableApplications() throws Throwable {
		String appName = "AppName";
		Application apps = new Application();
		apps.setApplicationName("applicationNames");
		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.when(jobsmgmtBL.getApplicationDetails(appName, authBean.getPrincipal().toString().toLowerCase()))
				.thenReturn(apps);

		ResourceResponse<String> response = applicationServices.getApplicationDetails("1", appName, authBean);
		assertNotNull(response);
		assertNotEquals("No Application", response.getResource());
		assertEquals("SUCCESS", response.getStatus());
		assertNull(response.getErrorMessage());
	}

}