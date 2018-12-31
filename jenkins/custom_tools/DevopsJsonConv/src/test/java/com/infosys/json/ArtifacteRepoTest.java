package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class ArtifacteRepoTest {

	@Test
	public void nametest(){
		ArtifactRepo ar = new ArtifactRepo();
		
		ar.setRepoName("repo name");
		
		Assert.assertEquals("repo name", ar.getRepoName());
	}
	
	@Test
	public void pwdtest(){
		ArtifactRepo ar = new ArtifactRepo();
		
		ar.setRepoPassword("pwd");
		
		Assert.assertEquals("pwd", ar.getRepoPassword());
	}
	
	@Test
	public void repotest(){
		ArtifactRepo ar = new ArtifactRepo();
		
		ar.setRepoURL("infosys.com");
		
		Assert.assertEquals("infosys.com", ar.getRepoURL());
	}
	
	@Test
	public void usernametest(){
		ArtifactRepo ar = new ArtifactRepo();
		
		ar.setRepoUsername("uname");
		
		Assert.assertEquals("uname", ar.getRepoUsername());
	}
}
