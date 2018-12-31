
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


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
	
	@Test
	public void versiontest(){
		
		JsonClass jc = new JsonClass();
		jc.setVersionInfo(null);
		
		Assert.assertEquals(null, jc.getVersionInfo());
	}
	
	@Test
	public void testcasetest(){
		
		JsonClass jc = new JsonClass();
		jc.setTestCaseResult(null);
		
		Assert.assertEquals(null, jc.getTestCaseResult());
	}
	
	@Test
	public void sonartest(){
		
		JsonClass jc = new JsonClass();
		jc.setSonarDetails(null);;
		
		Assert.assertEquals(null, jc.getSonarDetails());
	}
	
	@Test
	public void servicetest(){
		
		JsonClass jc = new JsonClass();
		jc.setServiceTest(null);
		
		Assert.assertEquals(null, jc.getServiceTest());
	}
	
	@Test
	public void securitytest(){
		
		JsonClass jc = new JsonClass();
		jc.setSecurityTest(null);
		
		Assert.assertEquals(null, jc.getSecurityTest());
	}
	
	@Test
	public void scminfotest(){
		
		JsonClass jc = new JsonClass();
		jc.setScmInfo(null);
		
		Assert.assertEquals(null, jc.getScmInfo());
	}
	
	@Test
	public void ruletest(){
		
		JsonClass jc = new JsonClass();
		jc.setRuleSet(null);
		
		Assert.assertEquals(null, jc.getRuleSet());
	}
	
	@Test
	public void buildownertest(){
		
		JsonClass jc = new JsonClass();
		jc.setBuildOwners(null);
		
		Assert.assertEquals(null, jc.getBuildOwners());
	}
	
	@Test
	public void builddetailstest(){
		
		JsonClass jc = new JsonClass();
		jc.setBuildDetails(null);
		
		Assert.assertEquals(null, jc.getBuildDetails());
	}
	
	@Test
	public void analysistest(){
		
		JsonClass jc = new JsonClass();
		jc.setCodeAnalysis(null);
		
		Assert.assertEquals(null, jc.getCodeAnalysis());
	}
	
	@Test
	public void codecoveragetest(){
		
		JsonClass jc = new JsonClass();
		jc.setCodecoverage(null);
		
		Assert.assertEquals(null, jc.getCodecoverage());
	}
	
	@Test
	public void codemetrictest(){
		
		JsonClass jc = new JsonClass();
		jc.setCodeMetric(null);
		
		Assert.assertEquals(null, jc.getCodeMetric());
	}
	
	@Test
	public void qualitytest(){
		
		JsonClass jc = new JsonClass();
		jc.setCodeQuality(null);
		
		Assert.assertEquals(null, jc.getCodeQuality());
	}
	

	
	@Test
	public void filenettest(){
		
		JsonClass jc = new JsonClass();
		jc.setFileNet(null);
		
		Assert.assertEquals(null, jc.getFileNet());
	}
	
	@Test
	public void functest(){
		
		JsonClass jc = new JsonClass();
		jc.setFunctionalTest(null);
		
		Assert.assertEquals(null, jc.getFunctionalTest());
	}
	
	@Test
	public void performancetest(){
		
		JsonClass jc = new JsonClass();
		jc.setPerformanceTest(null);
		
		Assert.assertEquals(null, jc.getPerformanceTest());
	}
	@Test
	public void logtest(){
		
		JsonClass jc = new JsonClass();
		jc.setLog("log");
		
		Assert.assertEquals("log", jc.getLog());
	}
	
	@Test
	public void pipelinetest(){
		
		JsonClass jc = new JsonClass();
		jc.setPipelineName("java");
		
		Assert.assertEquals("java", jc.getPipelineName());
	}
	
	@Test
	public void rulesettest(){
		
		JsonClass jc = new JsonClass();
		jc.setRuleSet(null);
		
		Assert.assertEquals(null, jc.getRuleSet());
	}
	
}
