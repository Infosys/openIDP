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

/*This class provides Test cases for Build Test*/
public class BuildTest {
	@Test
	public void timetest(){
		BuildDetail bd = new BuildDetail();
		bd.setBuildTime("10:00");
		Assert.assertEquals("10:00", bd.getBuildTime());
	}
	
	@Test
	public void statustest(){
		BuildDetail bd = new BuildDetail();
		bd.setBuiltStatus("Success");
		Assert.assertEquals("Success", bd.getBuiltStatus());
	}
	
	@Test
	public void buildidtest(){
		BuildDetail bd = new BuildDetail();
		bd.setLastBuildId("10");
		Assert.assertEquals("10", bd.getLastBuildId());
	}
	
	@Test
	public void completedbuildidtest(){
		BuildDetail bd = new BuildDetail();
		bd.setLastCompletedBuildId("9");
		Assert.assertEquals("9", bd.getLastCompletedBuildId());
	}
	
	@Test
	public void failedbuildidtest(){
		BuildDetail bd = new BuildDetail();
		bd.setLastFailedBuildId("9");
		Assert.assertEquals("9", bd.getLastFailedBuildId());
	}
	@Test
	public void lastsuccessfulbuildid(){
		BuildDetail bd = new BuildDetail();
		bd.setLastSuccessfulBuildId("9");
		Assert.assertEquals("9", bd.getLastSuccessfulBuildId());
	}
	
	@Test
	public void lastunstabletest(){
		BuildDetail bd = new BuildDetail();
		bd.setLastUnstableBuildId("9");
		Assert.assertEquals("9", bd.getLastUnstableBuildId());
	}
	
	@Test
	public void lastunsuccessfultest(){
		BuildDetail bd = new BuildDetail();
		bd.setLastUnsuccessfulBuildId("9");
		Assert.assertEquals("9", bd.getLastUnsuccessfulBuildId());
	}
	
	@Test
	public void scoretest(){
		BuildDetail bd = new BuildDetail();
		bd.setScore("9");
		Assert.assertEquals("9", bd.getScore());
	}
	
	@Test
	public void stagenametest(){
		BuildDetail bd = new BuildDetail();
		bd.setStageName("9");
		Assert.assertEquals("9", bd.getStageName());
	}
	
	@Test
	public void timestamptest(){
		BuildDetail bd = new BuildDetail();
		bd.setTimestamp("9:00");
		Assert.assertEquals("9:00", bd.getTimestamp());
	}
}
