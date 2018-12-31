package com.infosys.jsonschema;

import org.junit.Test;

import junit.framework.Assert;

public class VersionInfoTest {

	@Test
	public void idtest() {
		VersionInfo vi = new VersionInfo();
		
		vi.setCommitId("2");
		
		Assert.assertEquals("2", vi.getCommitId());
	}
	
	@Test
	public void msgtest() {
		VersionInfo vi = new VersionInfo();
		
		vi.setCommitMessage("success");
		
		Assert.assertEquals("success", vi.getCommitMessage());
	}
	
	
	@Test
	public void setidtest() {
		VersionInfo vi = new VersionInfo();
		
		vi.setid("2");
		
		Assert.assertEquals("2", vi.getid());
	}
	
	@Test
	public void modifiedtest() {
		VersionInfo vi = new VersionInfo();
		
		vi.setLastModified("today");
		
		Assert.assertEquals("today", vi.getLastModified());
	}
	
	@Test
	public void modifiedbytest() {
		VersionInfo vi = new VersionInfo();
		
		vi.setLastModifiedBy("admin");
		
		Assert.assertEquals("admin", vi.getLastModifiedBy());
	}
	
	@Test
	public void filevertest() {
		VersionInfo vi = new VersionInfo();
		
		vi.setLatestFileVersion("1.0.0");
		
		Assert.assertEquals("1.0.0", vi.getLatestFileVersion());
	}
}
