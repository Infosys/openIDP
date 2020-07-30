/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/
package com.infy.idp.plugins.wrappers

//import java.util.HashMap;
import org.infy.idp.entities.jobs.IDPJob
import com.infy.idp.interfaces.IPluginBase;
import com.infy.idp.utils.*

class BuildEnvIIS implements IPluginBase{

	public String name = ""
	public String pswd = ""
	public String jenkinsName = ""
	public String jenkinsPswd = ""
	public String dashboardName = ""
	public String dashboardPswd = ""
	public String dname = ""
	public String dpswd = ""
	
	public String getJenkinsName() {
		return jenkinsName
	}
	public void setJenkinsName(String jenkinsName) {
		this.jenkinsName = jenkinsName
	}
	public void setJenkinsPswd(String jenkinsPswd) {
		this.jenkinsPswd = jenkinsPswd
	}
	public String getJenkinsPswd() {
		return jenkinsPswd
	}
	
	public void setDashboardPswd(String dashboardPswd) {
		this.dashboardPswd = dashboardPswd
	}
	public String getDashboardPswd() {
		return dashboardPswd
	}
	
	public void setDashboardName(String dashboardName) {
		this.dashboardName = dashboardName
	}
	public String getDashboardName() {
		return dashboardName
	}
	
	public String getName() {
		return name
	}
	public void setName(String name) {
		this.name = name
	}

	public void setPswd(String pswd) {
		this.pswd = pswd
	}
	public String getPswd() {
		return pswd
	}

	public String getDname() {
		return dname
	}
	public void setDname(String Dname) {
		this.dname = dname
	}

	public void setDpswd(String dpswd) {
		this.dpswd = dpswd
	}
	public String getDpswd() {
		return dpswd
	} 
  @Override
  public void add(context, IDPJob dataObj) {
    this.addOptions(context, this.performMapping(dataObj));
  }
  
   

  @Override
  public HashMap<String, String> performMapping(IDPJob dataObj) {
    HashMap<String, String> data = new HashMap<String, String>()
	data.put("dashboardName", "DASHBOARD_SERVICE_PWD");
	data.put("dashboardPswd", PropReader.readProperty(Constants.CONFIGFN, 'IDP_DASHBOARD_SERVICEPWD'));
	data.put("jenkinsName", "JENKINS_PASSWORD");
	data.put("jenkinsPswd", PropReader.readProperty(Constants.CONFIGFN, 'JENKINS_PASSWORD'))
    data.put("name","iisPswd");
	data.put("pswd",this.pswd);
	return data
  }

  @Override
  public void addOptions(context, HashMap<String, String> data) {
    context.with{
      maskPasswordsBuildWrapper {
        BuildEnvIIS.addGeneralBuildSettings(delegate, data)
      }
    }
  }
  
  
  private static void addGeneralBuildSettings(context,data){
    context.with{
		varPasswordPairs{
		varPasswordPair{
			var(data.get("name"));
			password(data.get("pswd"));
		}		
		varPasswordPair{
			var(data.get("jenkinsName"));
			password(data.get("jenkinsPswd"));
		}
		varPasswordPair{
			var(data.get("dashboardName"));
			password(data.get("dashboardPswd"));
		}
		}
		varMaskRegexes {
			varMaskRegex {
			regex("");
		}
	}   
    }
  }
  
  
  
 
}
