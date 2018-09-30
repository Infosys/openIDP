/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.triggerparameter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class JobParamTriggerTest {

	public JobParamTriggerTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testJobParamTrigger() throws Throwable {
		JobParamTrigger jp = new JobParamTrigger();
		jp.setJobParamName("job");
		jp.setJobParamSatic(true);
		jp.setJobParamValue("value");
		jp.setJobType("type");

		assertEquals("job", jp.getJobParamName());
		assertTrue(jp.getJobParamSatic());
		assertEquals("value", jp.getJobParamValue());
		assertEquals("type", jp.getJobType());
	}

	@Test
	public void testNullJobParamTrigger() throws Throwable {
		JobParamTrigger jp = new JobParamTrigger();
		jp.setJobParamName(null);
		jp.setJobParamSatic(null);
		jp.setJobParamValue(null);
		jp.setJobType(null);

		assertNull(jp.getJobParamName());
		assertNull(jp.getJobParamSatic());
		assertNull(jp.getJobParamValue());
		assertNull(jp.getJobType());
	}

	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.triggerparameter.JobParamTriggerTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */

	@SuppressWarnings("rawtypes")
	public Class getTestedClass() {
		return JobParamTrigger.class;
	}

}
