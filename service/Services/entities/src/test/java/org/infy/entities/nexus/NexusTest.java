/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.entities.nexus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.infy.idp.entities.nexus.Item;
import org.infy.idp.entities.nexus.Nexus;
import org.junit.Test;

public class NexusTest {
	public NexusTest() {

	}

	/**
	 * 
	 * Test for get and set
	 * 
	 */
	@Test
	public void testNexusValues() throws Throwable {

		Nexus testObject = new Nexus();
		testObject.setContinuationToken("wdfawf");
		Item a = new Item();
		a.setRepository("server1");
		List<Item> as = new ArrayList<Item>();
		as.add(a);
		testObject.setItems(as);

		assertEquals("server1", testObject.getItems().get(0).getRepository());
		assertEquals("wdfawf", testObject.getContinuationToken());
	}

	@Test
	public void testNexusNull() throws Throwable {

		Nexus testObject = new Nexus();

		assertNull(testObject.getItems());
		assertNull(testObject.getContinuationToken());
	}

	@Test
	public void testItem() throws Throwable {

		Nexus testObject = new Nexus();
		Item a = new Item();
		a.setRepository("server1");
		List<Item> as = new ArrayList<Item>();
		as.add(a);
		testObject.setItems(as);

		assertNotNull(testObject.getItems());
		assertEquals("server1", testObject.getItems().get(0).getRepository());
		assertNull(testObject.getItems().get(0).getFormat());
		assertNull(testObject.getItems().get(0).getGroup());
		assertNull(testObject.getItems().get(0).getId());
		assertNull(testObject.getItems().get(0).getName());
		assertNull(testObject.getItems().get(0).getVersion());
		assertNull(testObject.getItems().get(0).getAssets());

	}

	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java TestTest
	 * 
	 * @param args command line arguments are not needed
	 */
	public static void main(String[] args) {
		

		org.junit.runner.JUnitCore.main("org.infy.entities.nexus.NexusTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 */
	public Class<Nexus> getTestedClass() {
		return Nexus.class;
	}
}
