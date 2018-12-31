package org.infy.idp.utils.vsts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.infy.idp.entities.VSTSDataBean;
import org.junit.Test;
import org.mockito.InjectMocks;

public class UpdateVSTSTest {

	@InjectMocks
	private UpdateVSTS updateVSTS;
	
	@InjectMocks
	private ReadVSTSProperties readVSTSProperties;
	
	@Test
	public void restServiceUpdateTest()
	{
		VSTSDataBean vstsDataBean=new VSTSDataBean();
		
		Date date=new Date();
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
		
		List<VSTSDataBean> vstsDataList=new ArrayList<>();
		vstsDataList.add(vstsDataBean);
		updateVSTS.restServiceUpdate( vstsDataList, "406");
	}
}
