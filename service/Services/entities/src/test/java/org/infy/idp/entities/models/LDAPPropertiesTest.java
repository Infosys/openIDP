/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * LDAPPropertiesTest is a test class for LDAPProperties
 *
 * @see org.infy.idp.entities.models.LDAPProperties
 * 
 */
public class LDAPPropertiesTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public LDAPPropertiesTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method LDAPProperties().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see LDAPProperties#LDAPProperties()
	 * 
	 * 
	 */
	@Test
	public void testLDAPPropertiesValues() throws Throwable {
		LDAPProperties testedObject = new LDAPProperties();
		testedObject.setLDAPBase("LDAPBase12");
		testedObject.setLDAPUrl("LDAPUrl12");
		testedObject.setLDAPSuffix("LDAPSuffix11");
		assertEquals("LDAPBase12", testedObject.getLDAPBase());
		assertEquals("LDAPUrl12", testedObject.getLDAPUrl());
		assertEquals("LDAPSuffix11", testedObject.getLDAPSuffix());
	}

	/**
	 * Test for method LDAPProperties().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see LDAPProperties#LDAPProperties()
	 * 
	 * 
	 */
	@Test
	public void testLDAPPropertiesValues2() throws Throwable {
		LDAPProperties testedObject = new LDAPProperties();
		testedObject.setLDAPBase("LDAPBase0");
		testedObject.setLDAPUrl("LDAPUrl0");
		testedObject.setLDAPSuffix((String) null);
		assertEquals("LDAPBase0", testedObject.getLDAPBase());
		assertEquals("LDAPUrl0", testedObject.getLDAPUrl());
		assertNull(testedObject.getLDAPSuffix());
	}

	/**
	 * Test for method LDAPProperties().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see LDAPProperties#LDAPProperties()
	 * 
	 * 
	 */
	@Test
	public void testLDAPPropertiesNull() throws Throwable {
		LDAPProperties testedObject = new LDAPProperties();
		assertEquals(null, testedObject.getLDAPBase());
		assertEquals(null, testedObject.getLDAPUrl());
		assertEquals(null, testedObject.getLDAPSuffix());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java LDAPPropertiesTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.models.LDAPPropertiesTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<LDAPProperties> getTestedClass() {
		return LDAPProperties.class;
	}
}
