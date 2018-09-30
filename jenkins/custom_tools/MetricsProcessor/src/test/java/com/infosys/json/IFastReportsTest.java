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

/*This class is for testing IFast Reports*/
public class IFastReportsTest {

	@Test
	public void test(){
		IFastReports ifr = new IFastReports();
		
		ifr.setURL("infosys.com");
		
		Assert.assertEquals("infosys.com", ifr.getURL());
	}
}
