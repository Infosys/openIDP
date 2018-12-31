package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class SiebelTest {

	@Test
	public void repotest(){
		
		Siebel s = new Siebel();
		
		s.setNonRepoList(null);
		
		Assert.assertEquals(null, s.getNonRepoList());
	}

	@Test
	public void test(){
		
		Siebel s = new Siebel();
		
		s.setRepoList(null);
		
		Assert.assertEquals(null, s.getRepoList());
	}
}
