/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.code;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JobParamTest is a test class for JobParam
 *
 * @see org.infy.idp.entities.jobs.code.Code
 * 
 */
public class JobParamTest {

	public JobParamTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testJobParamValues() throws Throwable {
		JobParam testedObject = new JobParam();
		testedObject.setJobParamName("ParameterName");
		testedObject.setJobParamSatic(true);
		testedObject.setJobParamValue("ParameterValue");
		testedObject.setJobType("jobType");

		assertEquals("ParameterName", testedObject.getJobParamName());
		assertEquals("ParameterValue", testedObject.getJobParamValue());
		assertEquals("jobType", testedObject.getJobType());
		assertTrue(testedObject.getJobParamSatic());
	}

	@Test
	public void testJobParamNullValues() throws Throwable {
		JobParam testedObject = new JobParam();
		testedObject.setJobParamName(null);
		testedObject.setJobParamSatic(null);
		testedObject.setJobParamValue(null);
		testedObject.setJobType(null);

		assertNull(testedObject.getJobParamName());
		assertNull(testedObject.getJobParamValue());
		assertNull(testedObject.getJobType());
		assertNull(testedObject.getJobParamSatic());
	}

	@Test
	public void testJobParamNull() throws Throwable {
		JobParam testedObject = new JobParam();
		assertNull(testedObject.getJobParamName());
		assertNull(testedObject.getJobParamValue());
		assertNull(testedObject.getJobType());
		assertNull(testedObject.getJobParamSatic());
	}

	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.code.JobParamTest");
	}

	public Class<JobParam> getTestedClass() {
		return JobParam.class;
	}
}