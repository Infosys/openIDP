/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.dataapi.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.infy.entities.triggerinputs.DeployArtifact;
import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.entities.getjob.GetJob;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.Pipeline;
import org.infy.idp.entities.jobs.PipelineDetail;
import org.infy.idp.entities.jobs.applicationinfo.Application;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.SlavesDetail;
import org.infy.idp.entities.jobs.code.JobParam;
import org.infy.idp.utils.ConfigurationManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jtest.AppContext;

/**
 * JobDetailsDLTest is a test class for JobDetailsDL
 *
 * @see org.infy.idp.dataapi.services.JobDetailsDL
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class JobDetailsDLTest   {

	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@Spy
	@Autowired
	private ConfigurationManager configurationManager;

	@InjectMocks
	private JobDetailsDL testedObject;
	
	@InjectMocks
	private JobAdditionalDetailsDL jobAdditionalDL;
	@InjectMocks
	private JobInfoDL jobInfoDL;
	@InjectMocks
	private JobManagementDL jobManagementDL;

	/**
	 * Constructor for test class.
	 *
	 */
	public JobDetailsDLTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testGetUpdateFlag() {
		SlavesDetail slaveDetail = new SlavesDetail();
		slaveDetail.setLabels("LinSlave");
		slaveDetail.setSlaveName("LinSlave");
		boolean status = testedObject.getUpdateFlag(slaveDetail, "DemoAppT");
		assert status;
	}

	@Test
	public void testGetAppId() {
		int id = testedObject.getAppId("DemoAppT");
		assertEquals(id, 1);

	}

	@Test
	public void testGetPipelines() {
		List<String> pipelinesList = testedObject.getPipelines("DemoAppT", "1.0");
		assertNotNull(pipelinesList);
		assertNotEquals(pipelinesList.size(), 0);

	}

	@Test
	public void testGetAppReleaseNo() {
		List<String> releaseList = testedObject.getAppReleaseNo(1);
		assertNotNull(releaseList);
		assertNotEquals(releaseList.size(), 0);

	}

	@Test
	public void testGetPermissions() {
		List<String> permissionList = null;
		try {
			permissionList = jobInfoDL.getPermissions("idpadmin", "DemoAppT");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(permissionList);
		assertNotEquals(permissionList.size(), 0);

	}

	@Test
	public void testGetTriggerCount() {
		int count = -1;
		try {
			count = testedObject.getTriggerCount("JFrogTest1", "DemoAppT");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotEquals(-1, count);
	}

	@Test
	public void testGetArtifactDetails() {
		List<DeployArtifact> deployArtifacts = null;
		try {
			deployArtifacts = testedObject.getArtifactDetails("JFrogTest1", "DemoAppT");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(deployArtifacts);
		assertNotEquals(deployArtifacts.size(), 0);
	}

	@Test
	public void testGetOrgId() {
		try {
			long orgId = testedObject.getOrgId("idpadmin");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetDomainName() {
		String orgId = jobInfoDL.getDomainName("idpadmin");
	}

	@Test
	public void testUserExists() {
		try {
			boolean user = testedObject.userExists("idpadmin");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetExistingAppDetails() {
		try {
			Application app = testedObject.getExistingAppDetails("DemoAppT");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetExistingPipelines() {
		List<Pipeline> list = null;
		try {
			list = testedObject.getExistingPipelines("DemoAppT");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}

	@Test
	public void testGetExistingAppList() {
		List<Application> list = null;
		try {
			list = testedObject.getExistingAppList("IDP");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}

	@Test
	public void testGetExistingAppDetails_List_Success() {
		List<Application> list = null;
		try {
			list = testedObject.getExistingAppDetails();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}

	@Test
	public void testGetExistingAppNames() {
		List<Application> list = null;
		try {
			list = jobAdditionalDL.getExistingAppNames("Infosys", "IDP");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}

	@Test
	public void testGetExistingAppNamesPlatformSuccess() {
		List<Application> list = null;
		try {
			list = jobAdditionalDL.getExistingAppNames("IDP");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}

	@Test
	public void testGetApplications() {
		List<Application> list = null;
		try {
			list = jobAdditionalDL.getExistingAppNames("idpadmin", "IDP");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}

	@Test
	public void testGetBuildnumber() {
		List<Application> list = null;
		try {
			list = jobAdditionalDL.getExistingAppNames("DemoAppT", "JFrogTest");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}

	@Test(expected = NullPointerException.class)
	public void testGetSlaveDetailsFailure() {
		List<String> list = null;
		try {
			list = jobAdditionalDL.getSlaveDetails("DemoAppT", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetPipelineInfo() {
		IDPJob job = null;
		try {
			job = jobAdditionalDL.getPipelineInfo("DemoAppT", "TC1_Maven");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertNotNull(job);
	}

	@Test
	public void testGetReleaseNumber() {
		List<String> list = null;
		try {
			list = jobAdditionalDL.getReleaseNumber("DemoAppT", "TC1_Maven");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}

	@Test
	public void testGetPipelinesCustomPipelineadmin() {
		List<PipelineDetail> list = null;
		list = jobManagementDL.getPipelinesCustomPipelineadmin("idpadmin");
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}
	
	@Test
	public void testGetbuildnum() {
		List<String> list = null;
		list = jobManagementDL.getbuildnum("DemoAppT","TC1_Maven");
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}
	
	@Test
	public void testGetreleaseNum() {
		String rNo  = jobManagementDL.getreleaseNum("DemoAppT","TC1_Maven","1");
		assertNotNull(rNo);
	}


	@Test
	public void testGetTriggerEntitytime() {
		List<String> list = null;
		list = jobManagementDL.getTriggerEntitytime("DemoAppT","TC1_Maven","2");
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}
	
	@Test
	public void testGetTriggerEntity() {
		List<List> list = null;
		list = jobManagementDL.getTriggerEntity("DemoAppT","TC1_Maven","2");
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}
	

	@Test
	public void testGetStatus() {
		List<String> list = null;
		list = jobManagementDL.getStatus("DemoAppT","TC1_Maven","2");
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}
	
	
	@Test
	public void testGetApplicationNameForReleaseManager() {
		List<String> list = null;
		list = jobManagementDL.getApplicationNameForReleaseManager("idpadmin","IDP");
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}
	
	
	@Test
	public void testPipelineNamesForApplication() {
		List<String> list = null;
		list = jobManagementDL.getApplicationNameForReleaseManager("idpadmin","workflow");
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}
	
	@Test
	public void testGetSubAppDetails() {
		List<String> list = null;
		list = jobInfoDL.getSubAppDetails("DemoAppT");
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}
	
	
	@Test
	public void testGetJobParamDetails() {
		List<JobParam> list = null;
		list = jobInfoDL.getJobParamDetails("DemoAppT","TC1_Maven");
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}
	
	
	@Test
	public void testGetReleaseNumberAndBranches() {
		HashMap<String, List<String>> map = null;
		try {
			map = jobInfoDL.getReleaseNumberAndBranches("DemoAppT","TC1_Maven");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(map);
		assertNotEquals(map.size(), 0);
	}
	

	@Test
	public void testGetPipelinePermission() {
		List<String> list = null;
		list = jobInfoDL.getPipelinePermission("DemoAppT","TC1_Maven","idpadmin");
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}
	
	@Test
	public void testGetDBDeployOperation() {
		String opert = null;
		opert  = jobInfoDL.getDBDeployOperation("DemoAppT","TC1_Maven");
		
	}

//	
//	@Test
//	public void testdbDeployPipelineNamesForApplication() {
//		List<String> list = null;
//		list = jobInfoDL.dbDeployPipelineNamesForApplication("DemoAppT");
//		assertNotNull(list);
//		assertNotEquals(list.size(), 0);
//	}
	
	
	@Test
	public void testGetNotifications() {
		List<org.infy.idp.entities.jobs.common.Notification> list = null;
		list = testedObject.getNotifications("idpadmin");
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}
//	@Test
//	public void testDeletePipelineRoles() {
//		 jobInfoDL.deletePipelineRoles(1L);
//		
//	}
	
	@Test
	public void testGetPipelinePermissionForApp() {
		List<String> list = null;
		list = jobInfoDL.getPipelinePermissionForApplication("DemoAppT","idpadmin");
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}
	
	
	@Test
	public void testCheckCreateAppPermission() {
		boolean user = testedObject.checkCreateAppPermission("idpadmin");
	}

	@Test
	public void testGetPermissionForApplications_Success() throws Throwable {
		List result = jobManagementDL.getPermissionForApplications("abc", "testapp");
	}
	


	@Test
	public void testGetPermissionForApplications_Failure() throws Throwable {

		List result = jobManagementDL.getPermissionForApplications("idpadmin", "applicationName");

	}

	/**
	 * Test for method getPipelineInfo(java.lang.String,java.lang.String).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see JobDetailsDL#getPipelineInfo(java.lang.String,java.lang.String)
	 * 
	 */


	@Test
	public void testGetPipelines_Failure() throws Throwable {

		List result = jobAdditionalDL.getPipelines("appName");

	}

	/**
	 * Test for method getReleaseNo(java.lang.String,java.lang.String).
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see JobDetailsDL#getReleaseNo(java.lang.String,java.lang.String)
	 * 
	 */
	@Test
	public void testGetReleaseNo0() throws Throwable {

		String result = jobManagementDL.getReleaseNo("pipelineName", "appName");

	}

	/**
	 * Test for method getRoleId
	 * 
	 */
	@Test
	public void testGetRoleId_Failure() throws Throwable {

		Long result = jobInfoDL.getRoleId("developer");

	}


	@Test
	public void testGetRoles_Failure() throws Throwable {

		jobAdditionalDL.getRoles("userName", "applicationName");

	}

	/**
	 * Test for method getRoles(java.lang.String).
	 */
	@Test
	public void testGetRoles1_Success() throws Throwable {

		List result = jobManagementDL.getRoles("idpadmin");

	}

	@Test
	public void testGetRoles1_Failure() throws Throwable {

		List result = jobManagementDL.getRoles("idpadmin");

	}

	/**
	 * Test for method getRolesasApp(java.lang.String,java.lang.String).
	 */
	@Test
	public void testGetRolesasApp_Success() throws Throwable {

		List result = jobManagementDL.getRolesasApp("idpadmin", "Demo_Appln");

	}

	@Test
	public void testGetRolesasApp_Failure() throws Throwable {

		List result = jobManagementDL.getRolesasApp("idpadmin", "appName");

	}

	/**
	 * Test for method getSessionJobDetails(java.lang.String,java.lang.String).
	 * 
	 */
	@Test(expected = Exception.class)
	public void testGetSessionJobDetails0() throws Throwable {

		Integer result = jobAdditionalDL.getSessionJobDetails("sessionId", "key");

	}

	/**
	 * Test for method getSessionJobDetails(java.lang.String).
	 * 
	 */
	@Test(expected = Exception.class)
	public void testGetSessionJobDetails1() throws Throwable {

		HashMap result = jobAdditionalDL.getSessionJobDetails("sessionId");

	}


	@Test
	public void testGetSlaveDetails_Failure() throws Throwable {

		List result = jobAdditionalDL.getSlaveDetails("applicationName", "usage");

	}


	@Test(expected = Exception.class)
	public void testUpdateBuildnumber0() throws Throwable {

		int result = jobAdditionalDL.updateBuildnumber("ApplicationNmae", "pipelineName", "newBuildId");

	}

	/**
	 * Test for method userExists(java.lang.String).
	 * 
	 */
	@Test
	public void testUserExists0() throws Throwable {

		boolean result = testedObject.userExists("userName");

	}

	/**
	 * Test for method checkCreateAppPermission(java.lang.String).

	 */
	@Test
	public void testCheckCreateAppPermission_Success() throws Throwable {

		boolean result = testedObject.checkCreateAppPermission("idpadmin");
		assertTrue(result);

	}


	/**
	 * Test for method
	 * getPipelineDetail(org.infy.entities.triggerinputs.TriggerJobName).
	 * 
	 */

	@Test
	public void testGetPipelineDetail_Success() throws Throwable {
		TriggerJobName tiggerJobName = new TriggerJobName();
		tiggerJobName.setMailBody("tiggerJobName");
		tiggerJobName.setMethod("tiggerJobName_1");
		tiggerJobName.setApplicationName("Demo_Appln");
		tiggerJobName.setPipelineName("maven1_Copy");
		tiggerJobName.setUserName("tiggerJobName_4");

		Pipeline result = jobManagementDL.getPipelineDetail(tiggerJobName);

	}

	@Test
	public void testGetPipelineDetail_Failure() throws Throwable {
		TriggerJobName tiggerJobName = new TriggerJobName();
		tiggerJobName.setMailBody("tiggerJobName");
		tiggerJobName.setMethod("tiggerJobName_1");
		tiggerJobName.setApplicationName("Fakedata");
		tiggerJobName.setPipelineName("Fakedata");
		tiggerJobName.setUserName("tiggerJobName_4");

		Pipeline result = jobManagementDL.getPipelineDetail(tiggerJobName);

	}

	@Test
	public void testGetPipelineId_Failure() throws Throwable {

		Long result = jobInfoDL.getPipelineId("pipelineName", "applicationName");

	}


	@Test
	public void testGetApplication_Failure() throws Throwable {

		ApplicationInfo result = jobInfoDL.getApplication("applicationName");

	}

	/**
	 * Test for method getApplicationDetail(java.lang.String).
	 */
	
	@Test
	public void testGetApplicationDetail_Success() throws Throwable {

		Application result = jobAdditionalDL.getApplicationDetail("testing");

	}

	@Test
	public void testGetApplicationDetail_Failure() throws Throwable {

		Application result = jobAdditionalDL.getApplicationDetail("appName");

	}

	/**
	 * Test for method getApplicationDetails(java.lang.String).
	 */
	@Test
	public void testGetApplicationDetails_Success() throws Throwable {

		List result = jobAdditionalDL.getApplicationDetails("idpadmin", "IDP");

	}

	@Test
	public void testGetApplicationDetails_Failure() throws Throwable {

		List result = jobAdditionalDL.getApplicationDetails("idpadmin", "IDP");

	}

	@Test
	public void testCheckCreateAppPermission_Failure() throws Throwable {

		boolean result = testedObject.checkCreateAppPermission("username");
		assertFalse(result);

	}

	/**
	 * Test for method getApplicationId(java.lang.String).
	 * 
	 */
	@Test
	public void testGetApplicationId_Success() throws Throwable {

		Long result = jobInfoDL.getApplicationId("testing");

	}

	@Test
	public void testGetApplicationId_Failure() throws Throwable {

		Long result = jobInfoDL.getApplicationId("applicationName");

	}


	/**
	 * Test for method getArtifactDetails(java.lang.String,java.lang.String).
	 * 
	 * 
	 */
	@Test(expected = Exception.class)
	public void testGetArtifactDetails0() throws Throwable {

		List result = testedObject.getArtifactDetails("pipelineName", "applicationName");

	}

	/**
	 * Test for method getBasePermission(java.lang.String).
	 */
	@Test
	public void testGetBasePermission_Success() throws Throwable {

		List result = jobManagementDL.getBasePermission("idpadmin");

	}

	@Test
	public void testGetBasePermission_Failure() throws Throwable {

		List result = jobManagementDL.getBasePermission("idpadmin");

	}

	/**
	 * Test for method getBaseRoles(java.lang.String).
	 * 
	 */
	@Test
	public void testGetBaseRoles_Success() throws Throwable {

		List result = jobManagementDL.getBaseRoles("idpadmin");

	}

	@Test
	public void testGetBaseRoles_Failure() throws Throwable {

		List result = jobManagementDL.getBaseRoles("idpadmin");

	}

	/**
	 * Test for method getBuildnumber(java.lang.String,java.lang.String).
	 */
	@Test
	public void testGetBuildnumber_Success() throws Throwable {

		String result = jobAdditionalDL.getBuildnumber("ApplicationName", "pipelineName");

	}

	@Test
	public void testGetBuildnumber_Failure() throws Throwable {

		String result = jobAdditionalDL.getBuildnumber("ApplicationName", "pipelineName");

	}

	/**
	 * Test for method getExistingAppDetails(java.lang.String).
	 */
	@Test
	public void testGetExistingAppDetails_Success() throws Throwable {

		Application result = testedObject.getExistingAppDetails("testing");

	}

	@Test
	public void testGetExistingAppDetails_Failure() throws Throwable {

		Application result = testedObject.getExistingAppDetails("appName");

	}

	/**
	 * Test for method getExistingPipelines(java.lang.String).
	 */
	@Test
	public void testGetExistingPipelines_Success() throws Throwable {

		List result = testedObject.getExistingPipelines("testing");

	}

	@Test
	public void testGetExistingPipelines_Failure() throws Throwable {

		List result = testedObject.getExistingPipelines("appName");

	}

	/**
	 * Test for method getJobBuildDetails(org.infy.idp.entities.getJob.GetJob).
	 * 
	 */
	@Test
	public void testGetJobBuildDetails0() throws Throwable {
		GetJob getjob = new GetJob();
		getjob.setJenkinsBuildNumber("getjob");
		getjob.setBuildNumber("5");
		getjob.setApplicationName("getjob_2");
		getjob.setPipelineName("getjob_3");

		List result = jobManagementDL.getJobBuildDetails(getjob);

	}

	/**
	 * Test for method getLatestBuild(org.infy.idp.entities.getJob.GetJob).
	 * 
	 */
	@Test
	public void testGetLatestBuild0() throws Throwable {
		GetJob getjob = new GetJob();
		getjob.setJenkinsBuildNumber("getjob");
		getjob.setBuildNumber("getjob_1");
		getjob.setApplicationName("getjob_2");
		getjob.setPipelineName("getjob_3");

		Long result = jobManagementDL.getLatestBuild(getjob);

	}

	/**
	 * Test for method getPermission(java.lang.String).
	 * 
	 */
	@Test
	public void testGetPermission_Success() throws Throwable {

		List result = jobManagementDL.getPermission("idpadmin");

	}

	@Test
	public void testGetPermission_Failure() throws Throwable {

		List result = jobManagementDL.getPermission("idpadmin");

	}

	/**
	 * Used to set up the test. This method is called by JUnit before each of the
	 * tests are executed.
	 * 
	 */
	@Before
	public void setUp() throws Exception {
		/*
		 * Add any necessary initialization code here (e.g., open a socket). Call
		 * Repository.putTemporary() to provide initialized instances of objects to be
		 * used when testing.
		 */

		try {

			MockitoAnnotations.initMocks(this);
			Method postConstruct = PostGreSqlDbContext.class.getDeclaredMethod("init", null); // methodName,parameters
			postConstruct.setAccessible(true);
			postConstruct.invoke(postGreSqlDbContext);

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java JobDetailsDLTest
	 * 
	 * @param args command line arguments are not needed
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.dataapi.services.JobDetailsDLTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 */
	public Class getTestedClass() {
		return JobDetailsDL.class;
	}
}
