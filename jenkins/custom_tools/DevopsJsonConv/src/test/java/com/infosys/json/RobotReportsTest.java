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

/*This class provides Test cases for Url*/

public class RobotReportsTest {

	@Test
	public void urlTest(){
		
		RobotReports rr = new RobotReports();
		
		rr.setURL("infosys.com");
		
		Assert.assertEquals("infosys.com", rr.getURL());
	}
}
