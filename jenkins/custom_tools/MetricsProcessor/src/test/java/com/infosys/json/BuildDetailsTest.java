package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class BuildDetailsTest {


	@Test
	public void buildtest(){
		BuildDetails bd = new BuildDetails();
		
		bd.setBuildTime("10");
		Assert.assertEquals("10", bd.getBuildTime());
		
	}
	@Test
	public void detailstest(){
		BuildDetails bd = new BuildDetails();
		
		bd.setBuiltStatus("success");
		Assert.assertEquals("success", bd.getBuiltStatus());
		
	}
	
	@Test
	public void lastidtest(){
		BuildDetails bd = new BuildDetails();
		
		bd.setLastBuildId("1");
		Assert.assertEquals("1", bd.getLastBuildId());
		
	}
	
	@Test
	public void failedidtest(){
		BuildDetails bd = new BuildDetails();
		
		bd.setLastFailedBuildId("10");
		Assert.assertEquals("10", bd.getLastFailedBuildId());
		
	}

}
