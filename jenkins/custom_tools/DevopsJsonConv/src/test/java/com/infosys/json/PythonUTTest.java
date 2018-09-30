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

/*This class provides Test cases for python test*/
public class PythonUTTest {

	@Test
	public void errortest(){
		PythonUT pu = new PythonUT();
		
		pu.setError(0);
		
		Assert.assertEquals(0, pu.getError());
	}
	
	@Test
	public void failedtest(){
		PythonUT pu = new PythonUT();
		
		pu.setFailed(0);
		
		Assert.assertEquals(0, pu.getFailed());
	}
	
	@Test
	public void passedtest(){
		PythonUT pu = new PythonUT();
		
		pu.setPassed(0);
		
		Assert.assertEquals(0, pu.getPassed());
	}
	
	@Test
	public void skippedtest(){
		PythonUT pu = new PythonUT();
		
		pu.setSkipped(0);
		
		Assert.assertEquals(0, pu.getSkipped());
	}
	
	@Test
	public void totaltest(){
		PythonUT pu = new PythonUT();
		
		pu.setTotal(0);
		
		Assert.assertEquals(0, pu.getTotal());
	}
}
