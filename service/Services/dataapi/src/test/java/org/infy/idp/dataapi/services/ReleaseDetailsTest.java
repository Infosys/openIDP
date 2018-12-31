/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.dataapi.services;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.entities.releasemanagerinfo.Release;
import org.infy.idp.entities.releasemanagerinfo.ReleasePipeline;
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
 * ReleaseDetailsTest is a test class for ReleaseDetails
 *
 * @see org.infy.idp.dataapi.services.ReleaseDetails
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class ReleaseDetailsTest {

	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@Spy
	@Autowired
	private ConfigurationManager configurationManager;

	@InjectMocks
	private ReleaseDetails testedObject;

	/**
	 * Constructor for test class.
	 *
	 */
	public ReleaseDetailsTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testGetReleaseNum() throws Throwable {
		List<String> pipelines = new ArrayList<>();
		pipelines.add("JFrogTest2");
		Map<String, List<String>> map = testedObject.getReleaseNum("JFrogTest", pipelines);
		assertNotNull(map);
	}


	@Test
	public void testGetReleasePipelineInfo() throws Throwable {
		ReleasePipeline rp = testedObject.getReleasePipelineInfo("JFrogTest", "JFrogTest2", "approved");
		assertNotNull(rp);
	}

	@Test
	public void testGetReleaseId() throws Throwable {
		int id = testedObject.getReleaseId("JFrogTest", "JFrogTest2", "1.0.0", "approved");
		assertNotNull(id);
	}

	@Test
	public void testGetRemarks() throws Throwable {
		Release release = new Release();
		release.setReleaseNumber("1.0.0");
		String remark = testedObject.getRemarks(release, 1L, 1L);
		assertNotNull(remark);
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
	 * Usage: java ReleaseDetailsTest
	 * 
	 * @param args command line arguments are not needed
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.dataapi.services.ReleaseDetailsTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 */
	public Class getTestedClass() {
		return ReleaseDetails.class;
	}
}
