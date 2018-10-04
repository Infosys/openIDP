
package com.infosys.utilities.nunit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class EnvironmentTest {

	@Test
	public void testEquals()
	{
		Environment environment=new Environment();
		
		environment.setClrVersion("value");
		environment.setCulture("value");
		environment.setCwd("value");
		environment.setFrameworkVersion("value");
		environment.setMachineName("value");
		environment.setOsArchitecture("value");
		environment.setOsVersion("value");
		environment.setPlatform("value");
		environment.setUiculture("value");
		environment.setUser("value");
		environment.setUserDomain("value");
		environment.setValue("value");
		
		assertEquals("value",environment.getClrVersion());
		assertEquals("value",environment.getCulture());
		assertEquals("value",environment.getCwd());
		assertEquals("value",environment.getFrameworkVersion());
		assertEquals("value",environment.getMachineName());
		assertEquals("value",environment.getOsArchitecture());
		assertEquals("value",environment.getOsVersion());
		assertEquals("value",environment.getPlatform());
		assertEquals("value",environment.getUiculture());
		assertEquals("value",environment.getUser());
		assertEquals("value",environment.getUserDomain());
		assertEquals("value",environment.getValue());
		
	}
	
	@Test
	public void testNotEquals()
	{
		Environment environment=new Environment();
		
		environment.setClrVersion("value");
		environment.setCulture("value");
		environment.setCwd("value");
		environment.setFrameworkVersion("value");
		environment.setMachineName("value");
		environment.setOsArchitecture("value");
		environment.setOsVersion("value");
		environment.setPlatform("value");
		environment.setUiculture("value");
		environment.setUser("value");
		environment.setUserDomain("value");
		environment.setValue("value");
		
		assertNotEquals("value1",environment.getClrVersion());
		assertNotEquals("value1",environment.getCulture());
		assertNotEquals("value1",environment.getCwd());
		assertNotEquals("value1",environment.getFrameworkVersion());
		assertNotEquals("value1",environment.getMachineName());
		assertNotEquals("value1",environment.getOsArchitecture());
		assertNotEquals("value1",environment.getOsVersion());
		assertNotEquals("value1",environment.getPlatform());
		assertNotEquals("value1",environment.getUiculture());
		assertNotEquals("value1",environment.getUser());
		assertNotEquals("value1",environment.getUserDomain());
		assertNotEquals("value1",environment.getValue());
		
	}
}
