/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.businessapi;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.List;

import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.dataapi.services.JobDetailsDL;
import org.infy.idp.dataapi.services.ReleaseDetails;
import org.infy.idp.entities.releasemanagerinfo.ReleaseManager;
import org.infy.idp.utils.ConfigurationManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jtest.AppContext;

/**
 * ReleaseBLTest is a test class for ReleaseBL
 *
 * @see org.infy.idp.businessapi.ReleaseBL
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class ReleaseBLTest {

	@Mock
	private ReleaseDetails releaseDetails;

	@InjectMocks
	private JobDetailsDL getJobDetails;

	@Mock
	private EmailSender emailSender;
	@InjectMocks
	private ReleaseBL releaseBL;
	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@Spy
	@Autowired
	private ConfigurationManager configurationManager;

	public ReleaseBLTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Used to set up the test. This method is called by JUnit before each of the
	 * tests are executed.
	 * 
	 */

	@Test
	public void testGetReleaseNumber() throws Throwable {
		ReleaseManager releaseManager = releaseBL.getReleaseNumber("DemoAppT", "TC1_Maven");
		assertNotNull(releaseManager);
	}

	@Test
	public void testGetReleaseInfo() throws Throwable {
		ReleaseManager relManager = releaseBL.getReleaseInfo("DemoAppT", "TC1_Maven", "approved", "idpadmin");
		assertNotNull(relManager);
	}

	@Test
	public void testGetEnvironmentList() throws Throwable {
		List<String> list = releaseBL.getEnvironmentList("DemoAppT");
		assertNotNull(list);
	}

	@Before
	public void setUp() throws Exception {
		/*
		 * Add any necessary initialization code here (e.g., open a socket). Call
		 * Repository.putTemporary() to provide initialized instances of objects to be
		 * used when testing.
		 */

		try {

			MockitoAnnotations.initMocks(this);
			Method postConstruct = PostGreSqlDbContext.class.getDeclaredMethod("init", null);
			postConstruct.setAccessible(true);
			postConstruct.invoke(postGreSqlDbContext);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java ReleaseBLTest
	 * 
	 * @param args command line arguments are not needed
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.businessapi.ReleaseBLTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 */
	public Class getTestedClass() {
		return ReleaseBL.class;
	}
}
