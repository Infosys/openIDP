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

/*This class is for testing json class*/
public class JsonClassTest {

	
	@Test
	public void applicationtest(){
		
		JsonClass jc = new JsonClass();
		
		jc.setApplication("infosys");
		
		Assert.assertEquals("infosys", jc.getApplication());
	}
	
	@Test
	public void baseurltest(){
		
		JsonClass jc = new JsonClass();
		
		jc.setBaseURL("infosys.com");
		
		Assert.assertEquals("infosys.com", jc.getBaseURL());
	}
	
	@Test
	public void buildidtest(){
		
		JsonClass jc = new JsonClass();
		jc.setbuildId("1");
		
		Assert.assertEquals("1", jc.getbuildId());
	}
	
	@Test
	public void groupidtest(){
		
		JsonClass jc = new JsonClass();
		jc.setGroupId("1");
		
		Assert.assertEquals("1", jc.getGroupId());
	}
	
	@Test
	public void groupNametest(){
		
		JsonClass jc = new JsonClass();
		jc.setGroupName("1");
		
		Assert.assertEquals("1", jc.getGroupName());
	}
	
	@Test
	public void jobbuildidtest(){
		
		JsonClass jc = new JsonClass();
		jc.setJobBuildId("1");
		
		Assert.assertEquals("1", jc.getJobBuildId());
	}
	
	@Test
	public void ssoidtest(){
		
		JsonClass jc = new JsonClass();
		jc.setSsoId("1");
		
		Assert.assertEquals("1", jc.getSsoId());
	}
}
