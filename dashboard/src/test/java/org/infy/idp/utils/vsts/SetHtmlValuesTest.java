package org.infy.idp.utils.vsts;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.infy.idp.entities.VSTSDataBean;
import org.junit.Test;
import org.mockito.InjectMocks;

public class SetHtmlValuesTest {

	@InjectMocks
	private SetHtmlValues setHtmlValues;
	
	@InjectMocks
	private GetHtmlStrings getHtmlStrings;
	
	@Test
	public void setNewTableValuesTest()
	{
		
		String inputHtmlNew = getHtmlStrings.getPipelineTableHtml();
		//String fileName = "src/newTable.html";
		
		Date date=new Date();
		VSTSDataBean vstsDataBean=new VSTSDataBean();
		vstsDataBean.setArtilink("artilink");
		vstsDataBean.setArtivalue("artivalue");
		vstsDataBean.setBldstatus("bldstatus");
		vstsDataBean.setBuild("build");
		vstsDataBean.setDeploy("deploy");
		vstsDataBean.setDepStatus("depStatus");
		vstsDataBean.setEnv("env");
		vstsDataBean.setExecNo("execNo");
		vstsDataBean.setExecNoLink("execNoLink");
		vstsDataBean.setInsertTimestamp(date);
		vstsDataBean.setPass("pass");
		vstsDataBean.setPipelineName("pipelineName");
		vstsDataBean.setReleaseName("releaseName");
		vstsDataBean.setScmBranch("scmBranch");
		vstsDataBean.setTest("test");
		vstsDataBean.setTstStatus("tstStatus");
		vstsDataBean.setUrl("url");
		vstsDataBean.setUser("user");
		vstsDataBean.setUserName("userName");
		vstsDataBean.setWiNum("wiNum");
		
		
		
		String temp=setHtmlValues.setNewTableValues(getHtmlStrings.getRowHtml(),vstsDataBean,getHtmlStrings.getPipelineTableHtml());
		assertNotNull(temp);
	}
	
	@Test
	public void addEntireHTMLValuesTest()
	{
		
		String inputHtmlNew = getHtmlStrings.getPipelineTableHtml();
		//String fileName = "src/newTable.html";
		
		Date date=new Date();
		VSTSDataBean vstsDataBean=new VSTSDataBean();
		vstsDataBean.setArtilink("artilink");
		vstsDataBean.setArtivalue("artivalue");
		vstsDataBean.setBldstatus("bldstatus");
		vstsDataBean.setBuild("build");
		vstsDataBean.setDeploy("deploy");
		vstsDataBean.setDepStatus("depStatus");
		vstsDataBean.setEnv("env");
		vstsDataBean.setExecNo("execNo");
		vstsDataBean.setExecNoLink("execNoLink");
		vstsDataBean.setInsertTimestamp(date);
		vstsDataBean.setPass("pass");
		vstsDataBean.setPipelineName("pipelineName");
		vstsDataBean.setReleaseName("releaseName");
		vstsDataBean.setScmBranch("scmBranch");
		vstsDataBean.setTest("test");
		vstsDataBean.setTstStatus("tstStatus");
		vstsDataBean.setUrl("url");
		vstsDataBean.setUser("user");
		vstsDataBean.setUserName("userName");
		vstsDataBean.setWiNum("wiNum");
		
		
		
		String temp=setHtmlValues.addEntireHTMLValues(vstsDataBean,getHtmlStrings.getPipelineTableHtml());
		assertNotNull(temp);
	}
	
	@Test
	public void addOrUpdateRowTest1()
	{
		
		String inputHtmlNew = getHtmlStrings.getPipelineTableHtml();
		//String fileName = "src/newTable.html";
		
		Date date=new Date();
		VSTSDataBean vstsDataBean=new VSTSDataBean();
		vstsDataBean.setArtilink("artilink");
		vstsDataBean.setArtivalue("artivalue");
		vstsDataBean.setBldstatus("bldstatus");
		vstsDataBean.setBuild("build");
		vstsDataBean.setDeploy("deploy");
		vstsDataBean.setDepStatus("depStatus");
		vstsDataBean.setEnv("env");
		vstsDataBean.setExecNo("1");
		vstsDataBean.setExecNoLink("execNoLink");
		vstsDataBean.setInsertTimestamp(date);
		vstsDataBean.setPass("pass");
		vstsDataBean.setPipelineName("PipelineName1");
		vstsDataBean.setReleaseName("releaseName");
		vstsDataBean.setScmBranch("scmBranch");
		vstsDataBean.setTest("test");
		vstsDataBean.setTstStatus("tstStatus");
		vstsDataBean.setUrl("url");
		vstsDataBean.setUser("user");
		vstsDataBean.setUserName("userName");
		vstsDataBean.setWiNum("wiNum");
		
		
		
		String temp=setHtmlValues.addOrUpdateRow(getHtmlStrings.getPipelineTableHtml(),vstsDataBean,getHtmlStrings.getRowHtml());
		assertNotNull(temp);
	}
	
	@Test
	public void addOrUpdateRowTest2()
	{
		
		String inputHtmlNew = getHtmlStrings.getPipelineTableHtml();
		//String fileName = "src/newTable.html";
		
		Date date=new Date();
		VSTSDataBean vstsDataBean=new VSTSDataBean();
		vstsDataBean.setArtilink("artilink");
		vstsDataBean.setArtivalue("artivalue");
		vstsDataBean.setBldstatus("bldstatus");
		vstsDataBean.setBuild("build");
		vstsDataBean.setDeploy("deploy");
		vstsDataBean.setDepStatus("depStatus");
		vstsDataBean.setEnv("env");
		vstsDataBean.setExecNo("execno");
		vstsDataBean.setExecNoLink("execNoLink");
		vstsDataBean.setInsertTimestamp(date);
		vstsDataBean.setPass("pass");
		vstsDataBean.setPipelineName("PipelineName1");
		vstsDataBean.setReleaseName("releaseName");
		vstsDataBean.setScmBranch("scmBranch");
		vstsDataBean.setTest("test");
		vstsDataBean.setTstStatus("tstStatus");
		vstsDataBean.setUrl("url");
		vstsDataBean.setUser("user");
		vstsDataBean.setUserName("userName");
		vstsDataBean.setWiNum("wiNum");
		
		
		
		String temp=setHtmlValues.addOrUpdateRow(getHtmlStrings.getPipelineTableHtml(),vstsDataBean,getHtmlStrings.getRowHtml());
		assertNotNull(temp);
	}
}
