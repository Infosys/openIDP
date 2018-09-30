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

/*This class is for testing sci*/
public class sciTest {
	@Test
	public void hightest(){
		
		Sci s = new Sci();
		
		s.setHigh(0);
		
		Assert.assertEquals((Integer) 0, s.getHigh());
	}
	
	@Test
	public void midtest(){
		
		Sci s = new Sci();
		
		s.setMedium(0);

		Assert.assertEquals((Integer) 0, s.getMedium());
	}
	
	@Test
	public void lowtest(){
		
		Sci s = new Sci();
		
		s.setLow(0);
		
		Assert.assertEquals((Integer) 0, s.getLow());
	}
}
