package org.infy.idp.entities;

import org.infy.idp.entities.FileNet;
import org.infy.idp.entities.FileNetImport;
import org.infy.idp.entities.FileNetReportEntity;
import org.junit.Test;

import junit.framework.Assert;

public class FileNetReportEntityTest {

	@Test
	public void statustest(){
		FileNetReportEntity fnr = new FileNetReportEntity();
		
		fnr.setBuildStatus("success");
		
		Assert.assertEquals("success", fnr.getBuildStatus());
	}
	
	@Test
	public void deploytest(){
		FileNetReportEntity fnr = new FileNetReportEntity();
		
		fnr.setDeployStatus("success");
		
		Assert.assertEquals("success", fnr.getDeployStatus());
	}
	
	@Test
	public void envtest(){
		FileNetReportEntity fnr = new FileNetReportEntity();
		
		fnr.setEnviornment("dev");
		
		Assert.assertEquals("dev", fnr.getEnviornment());
	}
	
	@Test
	public void configtest(){
		FileNetReportEntity fnr = new FileNetReportEntity();
		
		fnr.setPairConfig("success");
		
		Assert.assertEquals("success", fnr.getPairConfig());
	}
	
	@Test
	public void pipelineid(){
		FileNetReportEntity fnr = new FileNetReportEntity();
		
		fnr.setPipelineId("1");
		
		Assert.assertEquals("1", fnr.getPipelineId());
	}
	
	@Test
	public void tstatustest(){
		FileNetReportEntity fnr = new FileNetReportEntity();
		
		fnr.setTestStatus("success");
		
		Assert.assertEquals("success", fnr.getTestStatus());
	}
	
	@Test
	public void triggertest(){
		FileNetReportEntity fnr = new FileNetReportEntity();
		
		fnr.setTriggerId("1");
		
		Assert.assertEquals("1", fnr.getTriggerId());
	}
	
	@Test
	public void triggertimetest(){
		FileNetReportEntity fnr = new FileNetReportEntity();
		
		fnr.setTriggerTime("10");
		
		Assert.assertEquals("10", fnr.getTriggerTime());
	}
	
	@Test
	public void versiontest(){
		FileNetReportEntity fnr = new FileNetReportEntity();
		
		fnr.setVersion("1.1");
		
		Assert.assertEquals("1.1", fnr.getVersion());
	}
}
