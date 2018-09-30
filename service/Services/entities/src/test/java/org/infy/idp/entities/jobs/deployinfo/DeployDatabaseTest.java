/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.deployinfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class DeployDatabaseTest {

	public DeployDatabaseTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testDeployDatabase() throws Throwable {
		DeployDatabase db = new DeployDatabase();
		db.setDbpassword("dummy");
		db.setDbusername("admin");
		db.setRestorpassword("dummy");
		db.setRestorusername("admin");
		db.setScript("script");

		assertEquals("dummy", db.getDbpassword());
		assertEquals("admin", db.getDbusername());
		assertEquals("dummy", db.getRestorpassword());
		assertEquals("admin", db.getRestorusername());
		assertEquals("script", db.getScript());
	}

	@Test
	public void testNullDeployDatabase() throws Throwable {
		DeployDatabase db = new DeployDatabase();
		db.setDbpassword(null);
		db.setDbusername(null);
		db.setRestorpassword(null);
		db.setRestorusername(null);
		db.setScript(null);

		assertNull(db.getDbpassword());
		assertNull(db.getDbusername());
		assertNull(db.getRestorpassword());
		assertNull(db.getRestorusername());
		assertNull(db.getScript());
	}

	public static void main(String[] args) {
		
		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.deployInfo.DeployDatabaseTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 * 
	 */

	@SuppressWarnings("rawtypes")
	public Class getTestedClass() {
		return DeployDatabase.class;
	}
}
