
/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.entities;

import java.util.Date;

public class VSTSDataBean implements Comparable<VSTSDataBean>{
	
	// private member variable
			// VSTS URL detail with collection name in path
			private String url = null;
			// VSTS User name
			private String userName = null;
			// VSTS Password
			private String pass = null;
			
			// workitem number
			private String wiNum = null;
			// Execution Number
			private String execNo = "0";
			// Execution Number Link
			private String execNoLink = null;
			// User Value
			private String user = null;
			// Build Value
			private String build = "N";
			// Deploy Value
			private String deploy = "N";
			// Test Value
			private String test = "N";
			// SCM Branch Value
			private String scmBranch = "NA";
			// Environment Value
			private String env = "NA";
			// Build Status Value
			private String bldstatus = "NA";
			// deploy Status Value
			private String depStatus = "NA";
			// test Status Value
			private String tstStatus = "NA";
			// Build/Deploy Artifact Value
			private String artivalue = "NA";
			// Build/Deploy Artifact link
			private String artilink = "NA";
			
			// Pipeline name
			private String pipelineName = null;
			
			// Release Name
			private String releaseName = null;
			
			// time stamp 
			private Date insertTimestamp;
			
			//getters and setters
			public String getReleaseName() {
				return releaseName;
			}
			public void setReleaseName(String releaseName) {
				this.releaseName = releaseName;
			}
			public Date getInsertTimestamp() {
				return insertTimestamp;
			}
			public void setInsertTimestamp(Date insertTimestamp) {
				this.insertTimestamp = insertTimestamp;
			}
			public String getPipelineName() {
				return pipelineName;
			}
			public void setPipelineName(String pipelineName) {
				this.pipelineName = pipelineName;
			}
			public String getUrl() {
				return url;
			}
			public void setUrl(String url) {
				this.url = url;
			}
			public String getUserName() {
				return userName;
			}
			public void setUserName(String userName) {
				this.userName = userName;
			}
			public String getPass() {
				return pass;
			}
			public void setPass(String pass) {
				this.pass = pass;
			}
			public String getWiNum() {
				return wiNum;
			}
			public void setWiNum(String wiNum) {
				this.wiNum = wiNum;
			}
			public String getExecNo() {
				return execNo;
			}
			public void setExecNo(String execNo) {
				this.execNo = execNo;
			}
			public String getExecNoLink() {
				return execNoLink;
			}
			public void setExecNoLink(String execNoLink) {
				this.execNoLink = execNoLink;
			}
			public String getUser() {
				return user;
			}
			public void setUser(String user) {
				this.user = user;
			}
			public String getBuild() {
				return build;
			}
			public void setBuild(String build) {
				this.build = build;
			}
			public String getDeploy() {
				return deploy;
			}
			public void setDeploy(String deploy) {
				this.deploy = deploy;
			}
			public String getTest() {
				return test;
			}
			public void setTest(String test) {
				this.test = test;
			}
			public String getScmBranch() {
				return scmBranch;
			}
			public void setScmBranch(String scmBranch) {
				this.scmBranch = scmBranch;
			}
			public String getEnv() {
				return env;
			}
			public void setEnv(String env) {
				this.env = env;
			}
			public String getBldstatus() {
				return bldstatus;
			}
			public void setBldstatus(String bldstatus) {
				this.bldstatus = bldstatus;
			}
			public String getDepStatus() {
				return depStatus;
			}
			public void setDepStatus(String depStatus) {
				this.depStatus = depStatus;
			}
			public String getTstStatus() {
				return tstStatus;
			}
			public void setTstStatus(String tstStatus) {
				this.tstStatus = tstStatus;
			}
			public String getArtivalue() {
				return artivalue;
			}
			public void setArtivalue(String artivalue) {
				this.artivalue = artivalue;
			}
			public String getArtilink() {
				return artilink;
			}
			public void setArtilink(String artilink) {
				this.artilink = artilink;
			}

	@Override
	public int compareTo(VSTSDataBean obj) {
		Integer thisExecNo = Integer.parseInt(this.execNo) ;
		Integer objExecNo = Integer.parseInt(obj.getExecNo());
		if(thisExecNo < objExecNo){
			return -1;
		}else if(thisExecNo > objExecNo){
			return 1;
		}
		return 0;
	}

}
