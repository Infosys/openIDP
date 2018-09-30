
/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/


package com.infosys.json;

import java.util.ArrayList;

import org.junit.Test;

import junit.framework.Assert;

/*This class provides Test cases for Build Owner*/
public class BuildOwnerTest {
	
	
	
	
	
	@Test
	public void abc(){
		ArrayList<String> al = new ArrayList<String>();
		al.add("firstdata");
		al.add("seconddata");
		al.add("thirddata");
		BuildOwner bo = new BuildOwner();
		
		bo.setId(al);
		
		Assert.assertEquals(al, bo.getId());
	}
}
