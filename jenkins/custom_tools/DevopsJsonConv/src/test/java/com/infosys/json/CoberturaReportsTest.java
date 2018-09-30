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

/*This class provides Test cases for Cobertura Reports*/

public class CoberturaReportsTest {
	@Test
	public void urltest(){
		CoberturaReports cr = new CoberturaReports();
		cr.setURL("infosys.com");
		
		Assert.assertEquals("infosys.com", cr.getURL());
	}
}
