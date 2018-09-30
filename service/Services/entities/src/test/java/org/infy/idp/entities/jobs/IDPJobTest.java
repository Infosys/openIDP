/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.infy.idp.entities.jobs.basicinfo.BasicInfo;
import org.infy.idp.entities.jobs.buildinfo.BuildInfo;
import org.infy.idp.entities.jobs.code.Code;
import org.infy.idp.entities.jobs.deployinfo.DeployInfo;
import org.infy.idp.entities.jobs.testinfo.TestInfo;
import org.infy.idp.entities.triggerparameter.ApplicationDetails;
import org.junit.Test;

/**
 * IDPJobTest is a test class for IDPJob
 *
 * @see org.infy.idp.entities.jobs.IDPJob
 * 
 */
public class IDPJobTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public IDPJobTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getBasicInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see IDPJob#getBasicInfo()
	 * 
	 * 
	 */
	@Test
	public void testGetBasicInfoNull() throws Throwable {
		IDPJob testedObject = new IDPJob();
		BasicInfo result = testedObject.getBasicInfo();
		assertNull(result);
	}

	/**
	 * Test for method getBuildInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see IDPJob#getBuildInfo()
	 * 
	 * 
	 */
	@Test
	public void testGetBuildInfoNull() throws Throwable {
		IDPJob testedObject = new IDPJob();
		BuildInfo result = testedObject.getBuildInfo();
		assertNull(result);
	}

	/**
	 * Test for method getCode().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see IDPJob#getCode()
	 * 
	 * 
	 */
	@Test
	public void testGetCodeNull() throws Throwable {
		IDPJob testedObject = new IDPJob();
		Code result = testedObject.getCode();
		assertNull(result);
	}

	/**
	 * Test for method getDeployInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see IDPJob#getDeployInfo()
	 * 
	 * 
	 */
	@Test
	public void testGetDeployInfoNull() throws Throwable {
		IDPJob testedObject = new IDPJob();
		DeployInfo result = testedObject.getDeployInfo();
		assertNull(result);
	}

	/**
	 * Test for method getTestInfo().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see IDPJob#getTestInfo()
	 * 
	 * 
	 */
	@Test
	public void testGetTestInfoNull() throws Throwable {
		IDPJob testedObject = new IDPJob();
		TestInfo result = testedObject.getTestInfo();
		assertNull(result);
	}

	/**
	 * Test for method IDPJob().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see IDPJob#IDPJob()
	 * 
	 * 
	 */
	@Test
	public void testIDPJobValues() throws Throwable {
		IDPJob testedObject = new IDPJob();
		testedObject.setSsoName("ssoName21");
		testedObject.setSsoId("ssoId21");
		testedObject.setErrorCode("errorCode21");
		BasicInfo basicInfo = new BasicInfo();
		testedObject.setBasicInfo(basicInfo);
		Code code = new Code();
		testedObject.setCode(code);
		BuildInfo buildInfo = new BuildInfo();
		testedObject.setBuildInfo(buildInfo);
		DeployInfo deployInfo = new DeployInfo();
		testedObject.setDeployInfo(deployInfo);
		TestInfo testInfo = new TestInfo();
		testedObject.setTestInfo(testInfo);

		List<ApplicationDetails> appDetList = new ArrayList<ApplicationDetails>();
		ApplicationDetails apdObj = new ApplicationDetails();
		appDetList.add(apdObj);
		testedObject.setPipelines(appDetList);

		assertEquals(buildInfo, testedObject.getBuildInfo());
		assertEquals(code, testedObject.getCode());
		assertEquals(deployInfo, testedObject.getDeployInfo());
		assertEquals(testInfo, testedObject.getTestInfo());
		assertEquals(basicInfo, testedObject.getBasicInfo());
		assertEquals("errorCode21", testedObject.getErrorCode());
		assertEquals("ssoId21", testedObject.getSsoId());
		assertEquals("ssoName21", testedObject.getSsoName());
		assertEquals(appDetList, testedObject.getPipelines());

	}

	/**
	 * Test for method IDPJob().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see IDPJob#IDPJob()
	 * 
	 * 
	 */
	@Test
	public void testIDPJobValues2() throws Throwable {
		IDPJob testedObject = new IDPJob();
		testedObject.setSsoName("ssoName0");
		testedObject.setSsoId("ssoId0");
		testedObject.setErrorCode("errorCode0");
		testedObject.setBasicInfo((BasicInfo) null);
		testedObject.setCode((Code) null);
		testedObject.setBuildInfo((BuildInfo) null);
		testedObject.setDeployInfo((DeployInfo) null);
		testedObject.setTestInfo((TestInfo) null);
		testedObject.setPipelines((List<ApplicationDetails>) null);

		assertNull(testedObject.getBuildInfo());
		assertNull(testedObject.getCode());
		assertNull(testedObject.getDeployInfo());
		assertNull(testedObject.getTestInfo());
		assertNull(testedObject.getBasicInfo());
		assertEquals("errorCode0", testedObject.getErrorCode());
		assertEquals("ssoId0", testedObject.getSsoId());
		assertEquals("ssoName0", testedObject.getSsoName());
		assertNull(testedObject.getPipelines());
	}

	/**
	 * Test for method IDPJob().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see IDPJob#IDPJob()
	 * 
	 * 
	 */
	@Test
	public void testIDPJobNull() throws Throwable {
		IDPJob testedObject = new IDPJob();

		assertNull(testedObject.getBuildInfo());
		assertNull(testedObject.getCode());
		assertNull(testedObject.getDeployInfo());
		assertNull(testedObject.getTestInfo());
		assertNull(testedObject.getBasicInfo());
		assertNull(testedObject.getErrorCode());
		assertNull(testedObject.getSsoId());
		assertNull(testedObject.getSsoName());
		assertNull(testedObject.getPipelines());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java IDPJobTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.IDPJobTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<IDPJob> getTestedClass() {
		return IDPJob.class;
	}
}
