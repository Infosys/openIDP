/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.basicinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * AdditionalMailRecipientsTest is a test class for AdditionalMailRecipients
 *
 * @see org.infy.idp.entities.jobs.basicinfo.AdditionalMailRecipients
 * 
 */
public class AdditionalMailRecipientsTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public AdditionalMailRecipientsTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method AdditionalMailRecipients().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see AdditionalMailRecipients#AdditionalMailRecipients()
	 * 
	 * 
	 */
	@Test
	public void testAdditionalMailRecipientsValues() throws Throwable {
		AdditionalMailRecipients testedObject = new AdditionalMailRecipients();
		testedObject.setApplicationTeam("applicationTeam12");
		testedObject.setEmailIds("emailIds11");
		assertEquals("emailIds11", testedObject.getEmailIds());
		assertEquals("applicationTeam12", testedObject.getApplicationTeam());
	}

	/**
	 * Test for method AdditionalMailRecipients().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see AdditionalMailRecipients#AdditionalMailRecipients()
	 * 
	 * 
	 */
	@Test
	public void testAdditionalMailRecipientsValues2() throws Throwable {
		AdditionalMailRecipients testedObject = new AdditionalMailRecipients();
		testedObject.setApplicationTeam("applicationTeam0");
		testedObject.setEmailIds((String) null);
		assertEquals(null, testedObject.getEmailIds());
		assertEquals("applicationTeam0", testedObject.getApplicationTeam());
	}

	/**
	 * Test for method AdditionalMailRecipients().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see AdditionalMailRecipients#AdditionalMailRecipients()
	 * 
	 * 
	 */
	@Test
	public void testAdditionalMailRecipientsNull() throws Throwable {
		AdditionalMailRecipients testedObject = new AdditionalMailRecipients();
		assertNull(testedObject.getEmailIds());
		assertNull(testedObject.getApplicationTeam());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java AdditionalMailRecipientsTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.basicInfo.AdditionalMailRecipientsTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<AdditionalMailRecipients> getTestedClass() {
		return AdditionalMailRecipients.class;
	}
}
