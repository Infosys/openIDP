/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * UserRolesPermissionsTest is a test class for UserRolesPermissions
 *
 * @see org.infy.idp.entities.jobs.UserRolesPermissions
 * 
 */
public class UserRolesPermissionsTest {

	/**
	 * Constructor for test class.
	 *
	 * 
	 */
	public UserRolesPermissionsTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	/**
	 * Test for method getPermissions().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see UserRolesPermissions#getPermissions()
	 * 
	 * 
	 */
	@Test
	public void testGetPermissionsNull() throws Throwable {
		UserRolesPermissions testedObject = new UserRolesPermissions();
		List<String> result = testedObject.getPermissions();
		assertNull(result);
	}

	/**
	 * Test for method getRoles().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see UserRolesPermissions#getRoles()
	 * 
	 * 
	 */
	@Test
	public void testGetRolesNull() throws Throwable {
		UserRolesPermissions testedObject = new UserRolesPermissions();
		List<String> result = testedObject.getRoles();
		assertNull(result);
	}

	/**
	 * Test for method UserRolesPermissions().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see UserRolesPermissions#UserRolesPermissions()
	 * 
	 * 
	 */
	@Test
	public void testUserRolesPermissionsValues() throws Throwable {
		UserRolesPermissions testedObject = new UserRolesPermissions();
		testedObject.setUserId("userId11");
		List<String> roles = new ArrayList<String>();
		testedObject.setRoles(roles);
		List<String> permissions = new ArrayList<>();
		testedObject.setPermissions(permissions);

		assertEquals(roles, testedObject.getPermissions());
		assertEquals(roles, testedObject.getRoles());
		assertEquals("userId11", testedObject.getUserId());
	}

	/**
	 * Test for method UserRolesPermissions().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see UserRolesPermissions#UserRolesPermissions()
	 * 
	 * 
	 */
	@Test
	public void testUserRolesPermissionsValues2() throws Throwable {
		UserRolesPermissions testedObject = new UserRolesPermissions();
		testedObject.setUserId("userId0");
		testedObject.setRoles((List<String>) null);
		testedObject.setPermissions((List<String>) null);
		assertNull(testedObject.getPermissions());
		assertNull(testedObject.getRoles());
		assertEquals("userId0", testedObject.getUserId());
	}

	/**
	 * Test for method UserRolesPermissions().
	 * 
	 * @throws Throwable Tests may throw any Throwable
	 *
	 * @see UserRolesPermissions#UserRolesPermissions()
	 * 
	 * 
	 */
	@Test
	public void testUserRolesPermissionsNull() throws Throwable {
		UserRolesPermissions testedObject = new UserRolesPermissions();

		assertEquals(null, testedObject.getPermissions());
		assertEquals(null, testedObject.getRoles());
		assertEquals(null, testedObject.getUserId());
	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java UserRolesPermissionsTest
	 * 
	 * @param args command line arguments are not needed
	 * 
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.idp.entities.jobs.UserRolesPermissionsTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 */
	public Class<UserRolesPermissions> getTestedClass() {
		return UserRolesPermissions.class;
	}
}
