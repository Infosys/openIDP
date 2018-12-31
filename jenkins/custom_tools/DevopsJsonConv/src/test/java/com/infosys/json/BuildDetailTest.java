package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class BuildDetailTest {


	@Test
	public void buildtest(){
		
		BuildDetail bd = new BuildDetail();
		
		bd.setBuildTime("10:00");
		
		Assert.assertEquals("10:00", bd.getBuildTime());
	}
	
	@Test
	public void buildstatustest(){
		
		BuildDetail bd = new BuildDetail();
		
		bd.setBuiltStatus("success");
		
		Assert.assertEquals("success", bd.getBuiltStatus());
	}
	
	@Test
	public void buildidtest(){
		
		BuildDetail bd = new BuildDetail();
		
		bd.setLastBuildId("10");
		
		Assert.assertEquals("10", bd.getLastBuildId());
	}
	
	@Test
	public void completebuildidtest(){
		
		BuildDetail bd = new BuildDetail();
		
		bd.setLastCompletedBuildId("9");
		
		Assert.assertEquals("9", bd.getLastCompletedBuildId());
	}
	
	@Test
	public void buildfailedidtest(){
		
		BuildDetail bd = new BuildDetail();
		
		bd.setLastFailedBuildId("8");
		
		Assert.assertEquals("8", bd.getLastFailedBuildId());
	}
	
	@Test
	public void buildsuccessidtest(){
		
		BuildDetail bd = new BuildDetail();
		
		bd.setLastSuccessfulBuildId("11");
		
		Assert.assertEquals("11", bd.getLastSuccessfulBuildId());
	}
	
	@Test
	public void buildunstableidtest(){
		
		BuildDetail bd = new BuildDetail();
		
		bd.setLastUnstableBuildId("10");
		
		Assert.assertEquals("10", bd.getLastUnstableBuildId());
	}
	
	@Test
	public void buildunsuccessfulidtest(){
		
		BuildDetail bd = new BuildDetail();
		
		bd.setLastUnsuccessfulBuildId("10");
		
		Assert.assertEquals("10", bd.getLastUnsuccessfulBuildId());
	}
	
	@Test
	public void scoretest(){
		
		BuildDetail bd = new BuildDetail();
		
		bd.setScore("10");
		
		Assert.assertEquals("10", bd.getScore());
	}
	
	@Test
	public void stagetest(){
		
		BuildDetail bd = new BuildDetail();
		
		bd.setScore("build");
		
		Assert.assertEquals("build", bd.getScore());
	}
	
	@Test
	public void timetest(){
		
		BuildDetail bd = new BuildDetail();
		
		bd.setTimestamp("10");
		
		Assert.assertEquals("10", bd.getTimestamp());
	}
}
