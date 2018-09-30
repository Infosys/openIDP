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

/*This class provides Test cases for Code Analysis*/
public class CodeAnalysisTest {

	@Test
	public void idtest(){
		CodeAnalysis ca = new CodeAnalysis();
		
		ca.setId("1");
		
		Assert.assertEquals("1", "1");
	}
}
