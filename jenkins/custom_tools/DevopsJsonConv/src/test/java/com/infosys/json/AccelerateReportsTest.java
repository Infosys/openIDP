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
/*This class provides Test cases for Accelerate Reports*/
public class AccelerateReportsTest {

	@Test
	public void UrlTest(){
		AccelerateReports ar = new AccelerateReports();
		ar.setURL("www.infosys.com");
		
		Assert.assertEquals("www.infosys.com", ar.getURL());
	}
}