/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.businessapi;

import java.lang.reflect.Method;

import org.infy.entities.triggerinputs.TriggerInputs;
import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.dataapi.services.JobDetailsDL;
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
 * GetTriggerDetailsTest is a test class for GetTriggerDetails
 *
 * @see org.infy.idp.businessapi.TriggerDetailBL
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class TriggerDetailBLTest {

	@Spy
	@InjectMocks
	private TriggerDetailBL testedObject;

	@Spy
	@InjectMocks
	private FetchJobDetails fetchJobDetails;

	@Spy
	@InjectMocks
	private JobDetailsDL getJobDetails;
	@Spy
	@InjectMocks
	private JobsBL jobsBL;
	@Spy
	@InjectMocks
	private TriggerJobName tjb;

	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@Spy
	@Autowired
	private ConfigurationManager configurationManager;

	public TriggerDetailBLTest() {
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

	@Test(expected = NullPointerException.class)
	public void fetchTriggerTest() throws Throwable {

		tjb.setApplicationName("firstApp");
		tjb.setPipelineName("job01");
		tjb.setUserName("idpadmin");
		TriggerInputs ti = testedObject.fecthTriggerOptions(tjb);
		TriggerInputs t2 = new TriggerInputs();
		t2.setRepoName("na");
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

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java GetTriggerDetailsTest
	 * 
	 * @param args command line arguments are not needed
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.businessapi.GetTriggerDetailsTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 */
	public Class getTestedClass() {
		return TriggerDetailBL.class;
	}
}
