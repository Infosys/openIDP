/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.utils;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * SlaveDetailsTest is a test class for SlaveDetails
 *
 * @see org.infy.idp.utils.SlaveDetails
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class SlaveDetailsTest {

	@InjectMocks
	private SlaveDetails testedObject;
	@InjectMocks
	private JenkinsCLI jenkinsCLI;

	@Spy
	private ConfigurationManager configmanager;

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public SlaveDetailsTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testGetSlaveStatus() {
		String status = null;
		try {
			status = testedObject.getSlaveStatus("WinSlave");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(status);
	}

	/**
	 * Used to set up the test. This method is called by JUnit before each of the
	 * tests are executed.
	 * 
	 * 
	 */
	@Before
	public void setUp() throws Exception {
		/*
		 * Add any necessary initialization code here (e.g., open a socket). Call
		 * Repository.putTemporary() to provide initialized instances of objects to be
		 * used when testing.
		 */

		configmanager.setJenkinsurl("http://localhost:8085");
		configmanager.setJenkinsuserid("idpadmin");
		configmanager.setJenkinspassword("dummyuser");
		configmanager.setSharePath("D:\\idpdata\\dsldata");
		configmanager.setPipelineScriptPath("D:\\idpdata\\dsldata\\src\\main\\groovy\\pipeline_sequence");
		MockitoAnnotations.initMocks(this);
		Method postConstruct = JenkinsCLI.class.getDeclaredMethod("init", null); // methodName,parameters
		postConstruct.setAccessible(true);
		postConstruct.invoke(jenkinsCLI);

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java JenkinsCLITest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.utils.JenkinsCLITest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class getTestedClass() {
		return SlaveDetails.class;
	}
}
