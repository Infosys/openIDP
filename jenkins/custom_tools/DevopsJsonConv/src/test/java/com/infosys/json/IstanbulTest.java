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

/*This class provides Test cases for Istanbul Line coverage*/
public class IstanbulTest {

	@Test
	public void test(){
		Istanbul il = new Istanbul();
		
		il.setLineCoverage("100");
		
		Assert.assertEquals("100", il.getLineCoverage());
	}
}
