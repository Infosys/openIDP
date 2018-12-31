package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class ArtifacteToStageTest {

	
	@Test
	public void artitest(){
		ArtifactToStage as = new ArtifactToStage();
		
		as.setArtifact("arti");
		
		Assert.assertEquals("arti", as.getArtifact());
	}
	@Test
	public void repotest(){
		ArtifactToStage as = new ArtifactToStage();
		
		as.setArtifactRepo(null);
		
		Assert.assertEquals(null, as.getArtifactRepo());
	}
	
	@Test
	public void nametest(){
		ArtifactToStage as = new ArtifactToStage();
		
		as.setArtifactRepoName("nexus");
		
		Assert.assertEquals("nexus", as.getArtifactRepoName());
	}
	
	@Test
	public void Flattentest(){
		ArtifactToStage as = new ArtifactToStage();
		
		as.setFlattenFileStructure("fs");
		
		Assert.assertEquals("fs", as.getFlattenFileStructure());
	}
	
	@Test
	public void nexustest(){
		ArtifactToStage as = new ArtifactToStage();
		
		as.setnexusAPIKey("nexuskey");
		
		Assert.assertEquals("nexuskey", as.getnexusAPIKey());
	}
	
	@Test
	public void pathtest(){
		ArtifactToStage as = new ArtifactToStage();
		
		as.setnuspecFilePath("path");
		
		Assert.assertEquals("path", as.getnuspecFilePath());
	}
}
