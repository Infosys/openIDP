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

class BuildEnv implements IPluginBase{

	public String name = ""
	public String pswd = ""
	public String jenkinsName = ""
	public String jenkinsPswd = ""
	
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

  @Override
  public void add(context, IDPJob dataObj) {
    this.addOptions(context, this.performMapping(dataObj));
  }
  
   

  @Override
  public HashMap<String, String> performMapping(IDPJob dataObj) {
    HashMap<String, String> data = new HashMap<String, String>()
    data.put("name","DASHBOARD_SERVICE_PWD");
	data.put("pswd",PropReader.readProperty(Constants.CONFIGFN, 'IDP_DASHBOARD_SERVICEPWD'));
	data.put("jenkinsName", "JENKINS_PASSWORD");
	data.put("jenkinsPswd", PropReader.readProperty(Constants.CONFIGFN, 'JENKINS_PASSWORD'))
	return data
  }

  @Override
  public void addOptions(context, HashMap<String, String> data) {
    context.with{
      maskPasswordsBuildWrapper {
        BuildEnv.addGeneralBuildSettings(delegate, data)
      }
    }
  }
   
  private static void addGeneralBuildSettings(context,data){
    context.with{
      if(data.containsKey("name") && data.containsKey("pswd"))
		varPasswordPairs{
		varPasswordPair{
			var(data.get("name"));
			password(data.get("pswd"));
		}
		varPasswordPair{
			var(data.get("jenkinsName"));
			password(data.get("jenkinsPswd"));
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
