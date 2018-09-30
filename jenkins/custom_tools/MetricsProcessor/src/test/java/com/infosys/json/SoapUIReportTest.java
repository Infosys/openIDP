/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

/*This class is for testing soapUI*/
public class SoapUIReportTest {

	@Test
	public void errortest(){
		SoapUIReport sui = new SoapUIReport();
		
		sui.setErrors("none");
		
		Assert.assertEquals("none", sui.getErrors());
	}
	
	@Test
	public void failtest(){
		SoapUIReport sui = new SoapUIReport();
		
		sui.setFailures("none");
		
		Assert.assertEquals("none", sui.getFailures());
	}
	
	@Test
	public void nametest(){
		SoapUIReport sui = new SoapUIReport();
		
		sui.setName("none");
		
		Assert.assertEquals("none", sui.getName());
	}
	
	@Test
	public void testtest(){
		SoapUIReport sui = new SoapUIReport();
		
		sui.setTests("none");
		
		Assert.assertEquals("none", sui.getTests());
	}
	
	@Test
	public void timetest(){
		SoapUIReport sui = new SoapUIReport();
		
		sui.setTime("10");
		
		Assert.assertEquals("10", sui.getTime());
	}
}
