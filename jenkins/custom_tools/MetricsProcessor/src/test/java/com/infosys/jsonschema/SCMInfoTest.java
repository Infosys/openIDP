package com.infosys.jsonschema;

import org.junit.Test;

import junit.framework.Assert;

public class SCMInfoTest {

	@Test
	public void msgtest() {
		SCMInfo scm = new SCMInfo();
		
		scm.setCommitMessage("success");
		
		Assert.assertEquals("success", scm.getCommitMessage());
	}
	
	@Test
	public void idtest() {
		SCMInfo scm = new SCMInfo();
		
		scm.setId("1");
		
		Assert.assertEquals("1", scm.getId());
	}
	
	@Test
	public void modifiedtest() {
		SCMInfo scm = new SCMInfo();
		
		scm.setLastModified("last");
		
		Assert.assertEquals("last", scm.getLastModified());
	}
	
	@Test
	public void modifiedbytest() {
		SCMInfo scm = new SCMInfo();
		
		scm.setLastModifiedBy("admin");
		
		Assert.assertEquals("admin", scm.getLastModifiedBy());
	}
	
	@Test
	public void vertest() {
		SCMInfo scm = new SCMInfo();
		
		scm.setLatestFileVersion("10");
		
		Assert.assertEquals("10", scm.getLatestFileVersion());
	}
	
	@Test
	public void urltest() {
		SCMInfo scm = new SCMInfo();
		
		scm.setRemoteUrl("remoteurl.com");
		
		Assert.assertEquals("remoteurl.com", scm.getRemoteUrl());
	}
}
