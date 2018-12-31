package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class PackageContentTest {

	
	@Test
	public void anttest(){
		PackageContent pc = new PackageContent();
		
		pc.setAnt(null);
		
		Assert.assertEquals(null, pc.getAnt());
	}
	
	@Test
	public void artifacttest(){
		PackageContent pc = new PackageContent();
		
		pc.setArtifactName("arname");
		
		Assert.assertEquals("arname", pc.getArtifactName());
	}
	
	@Test
	public void bigdatatest(){
		PackageContent pc = new PackageContent();
		
		pc.setBigData(null);
		
		Assert.assertEquals(null, pc.getBigData());
	}
	
	@Test
	public void dotnettest(){
		PackageContent pc = new PackageContent();
		
		pc.setDotNet(null);
		
		Assert.assertEquals(null, pc.getDotNet());
	}
	
	@Test
	public void infotest(){
		PackageContent pc = new PackageContent();
		
		pc.setInformatica(null);
		
		Assert.assertEquals(null, pc.getInformatica());
	}
	
	@Test
	public void pegatest(){
		PackageContent pc = new PackageContent();
		
		pc.setPega(null);
		
		Assert.assertEquals(null, pc.getPega());
	}
	
	@Test
	public void siebeltest(){
		PackageContent pc = new PackageContent();
		
		pc.setSiebel(null);
		
		Assert.assertEquals(null, pc.getSiebel());
	}
}
