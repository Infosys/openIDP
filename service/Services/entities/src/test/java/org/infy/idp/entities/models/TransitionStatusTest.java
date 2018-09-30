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

import java.sql.Date;

import org.junit.Test;

/**
 * TransitionStatusTest is a test class for TransitionStatus
 *
 * @see org.infy.idp.entities.models.TransitionStatus
 * 
 */
public class TransitionStatusTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public TransitionStatusTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getEndDate().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TransitionStatus#getEndDate()
	 * 
	 * 
	 */
	@Test
	public void testGetEndDateNull() throws Throwable {
		TransitionStatus testedObject = new TransitionStatus();
		Date result = testedObject.getEndDate();
		assertNull(result);
	}

	/**
	 * Test for method getStartDate().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TransitionStatus#getStartDate()
	 * 
	 * 
	 */
	@Test
	public void testGetStartDateNull() throws Throwable {
		TransitionStatus testedObject = new TransitionStatus();
		Date result = testedObject.getStartDate();
		assertNull(result);
	}

	/**
	 * Test for method TransitionStatus().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TransitionStatus#TransitionStatus()
	 * 
	 * 
	 */
	@Test
	public void testTransitionStatusValues() throws Throwable {
		TransitionStatus testedObject = new TransitionStatus();
		testedObject.setId(100);
		testedObject.setTrack("track12");
		Date startDate = new Date(5L);
		testedObject.setStartDate(startDate);
		Date endDate = new Date(-10L);
		testedObject.setEndDate(endDate);
		testedObject.setStatus("status12");
		testedObject.setRemarks("remarks11");

		assertEquals(startDate, testedObject.getStartDate());
		assertEquals(endDate, testedObject.getEndDate());
		assertEquals("status12", testedObject.getStatus());
		assertEquals("track12", testedObject.getTrack());
		assertEquals("remarks11", testedObject.getRemarks());
		assertEquals(100, testedObject.getId());
	}

	/**
	 * Test for method TransitionStatus().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TransitionStatus#TransitionStatus()
	 * 
	 * 
	 */
	@Test
	public void testTransitionStatusValues2() throws Throwable {
		TransitionStatus testedObject = new TransitionStatus();
		testedObject.setId(-10);
		testedObject.setTrack("track1");
		testedObject.setStartDate((Date) null);
		testedObject.setEndDate((Date) null);
		testedObject.setStatus("status1");
		testedObject.setRemarks("remarks0");

		assertNull(testedObject.getStartDate());
		assertNull(testedObject.getEndDate());
		assertEquals("status1", testedObject.getStatus());
		assertEquals("track1", testedObject.getTrack());
		assertEquals("remarks0", testedObject.getRemarks());
		assertEquals(-10, testedObject.getId());
	}

	/**
	 * Test for method TransitionStatus().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TransitionStatus#TransitionStatus()
	 * 
	 * 
	 */
	@Test
	public void testTransitionStatusNull() throws Throwable {
		TransitionStatus testedObject = new TransitionStatus();
		assertNull(testedObject.getStartDate());
		assertNull(testedObject.getEndDate());
		assertNull(testedObject.getStatus());
		assertNull(testedObject.getTrack());
		assertNull(testedObject.getRemarks());
		assertEquals(0, testedObject.getId());
	}

	/**
	 * Test for method updateQuery().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TransitionStatus#updateQuery()
	 * 
	 * 
	 */
	@Test
	public void testupdateQueryValues() throws Throwable {
		TransitionStatus testedObject = new TransitionStatus();
		Date startDate = new Date(2147483648L);
		Date endDate = new Date(-1000L);
		testedObject.setId(1000);
		testedObject.setTrack("track3");
		testedObject.setStartDate(startDate);
		testedObject.setEndDate(endDate);
		testedObject.setStatus("status3");
		testedObject.setRemarks("remarks3");
		String result = testedObject.updateQuery();
		assertEquals(
				"Update ttransitionstatus SET startdate = '1970-01-26', enddate='1970-01-01', Remarks='remarks3', Status='status3' where id=1000;",
				result);
		assertEquals(startDate, testedObject.getStartDate());
		assertEquals(endDate, testedObject.getEndDate());
		assertEquals("status3", testedObject.getStatus());
		assertEquals("track3", testedObject.getTrack());
		assertEquals("remarks3", testedObject.getRemarks());
		assertEquals(1000, testedObject.getId());
	}

	/**
	 * Test for method updateQuery().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TransitionStatus#updateQuery()
	 * 
	 * 
	 */
	@Test
	public void testupdateQueryValues2() throws Throwable {
		TransitionStatus testedObject = new TransitionStatus();
		Date startDate = new Date(100L);
		Date endDate = new Date(1000L);
		testedObject.setId(-10);
		testedObject.setTrack("track1");
		testedObject.setStartDate(startDate);
		testedObject.setEndDate(endDate);
		testedObject.setStatus("status1");
		testedObject.setRemarks("remarks1");
		String result = testedObject.updateQuery();
		assertEquals(
				"Update ttransitionstatus SET startdate = '1970-01-01', enddate='1970-01-01', Remarks='remarks1', Status='status1' where id=-10;",
				result);
		assertEquals(startDate, testedObject.getStartDate());
		assertEquals(endDate, testedObject.getEndDate());
		assertEquals("status1", testedObject.getStatus());
		assertEquals("track1", testedObject.getTrack());
		assertEquals("remarks1", testedObject.getRemarks());
		assertEquals(-10, testedObject.getId());
	}

	/**
	 * Test for method updateQuery().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see TransitionStatus#updateQuery()
	 * 
	 * 
	 */
	@Test
	public void testupdateQueryNull() throws Throwable {
		TransitionStatus testedObject = new TransitionStatus();
		String result = testedObject.updateQuery();
		assertEquals(
				"Update ttransitionstatus SET startdate = 'null', enddate='null', Remarks='null', Status='null' where id=0;",
				result);
		assertNull(testedObject.getStartDate());
		assertNull(testedObject.getEndDate());
		assertNull(testedObject.getStatus());
		assertNull(testedObject.getTrack());
		assertNull(testedObject.getRemarks());
		assertEquals(0, testedObject.getId());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java TransitionStatusTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.models.TransitionStatusTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */
	public Class<TransitionStatus> getTestedClass() {
		return TransitionStatus.class;
	}
}
