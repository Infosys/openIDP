/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.customtools

import com.infy.idp.utils.*

/**
 *
 * This class includes the method for adding MetricsProcessor customtool
 *
 */

class MetricsProcessor{

	/*
     * This function is used to add the commands to run MetricsProcessor
     */

	public static void invokeTool(context, jsonData) {
		HashMap<String, String> data = performMapping(jsonData)
		def command
		if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
		command = """java -jar "${data.get('CUSTOM_TOOL_JAR')}" "${data.get('DASHBOARD_SERVICE_URL')}" "${data.get('DASHBOARD_SERVICE_UNAME')}" %DASHBOARD_SERVICE_PWD% "${data.get('APP_NAME')}" "${data.get('PIPELINE_NAME')}" "${data.get('JSON_PATH')}" """
		} else {
		command = """java -jar "${data.get('CUSTOM_TOOL_JAR')}" "${data.get('DASHBOARD_SERVICE_URL')}" "${data.get('DASHBOARD_SERVICE_UNAME')}" \$DASHBOARD_SERVICE_PWD "${data.get('APP_NAME')}" "${data.get('PIPELINE_NAME')}" "${data.get('JSON_PATH')}" """
		}
		ExecuteCmdPostBuild.invokeCmd(context, command, jsonData.basicInfo.buildServerOS)
	}

	/*
     * This function is used to perform mapping for adding MetricsProcessor
     */

	private static Map<String,String> performMapping(jsonData) {
	HashMap<String, String> data = new HashMap<String, String>()
	if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0 && jsonData.basicInfo.platform==null) {
		data.put('CUSTOM_TOOL_JAR','%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'METRICSPROCESSOR'))
		data.put('JSON_PATH','%IDP_WS%/../../Devops_Json') 
	}
	else if(jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0 && jsonData.basicInfo.platform!=null && jsonData.basicInfo.platform=="ictp"){
		data.put('CUSTOM_TOOL_JAR','%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'METRICSPROCESSOR_Test'))
		data.put('JSON_PATH','%IDP_WS%/../../Devops_Json')
	}
	else if(jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) != 0 && jsonData.basicInfo.platform!=null &&jsonData.basicInfo.platform=="ictp"){
		data.put('CUSTOM_TOOL_JAR','$IDP_WS/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'METRICSPROCESSOR_Test'))
		data.put('JSON_PATH','$IDP_WS/../../Devops_Json')
	}
	else{
		data.put('CUSTOM_TOOL_JAR','$IDP_WS/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'METRICSPROCESSOR'))
		data.put('JSON_PATH','$IDP_WS/../../Devops_Json')
	}
	data.put('DASHBOARD_SERVICE_URL',PropReader.readProperty(Constants.CONFIGFN,'IDP_DASHBOARD_SERVICEURL'))  
	data.put('DASHBOARD_SERVICE_UNAME',PropReader.readProperty(Constants.CONFIGFN,'IDP_DASHBOARD_SERVICEUNAME'))  
	data.put('DASHBOARD_SERVICE_PWD',PropReader.readProperty(Constants.CONFIGFN,'IDP_DASHBOARD_SERVICEPWD'))  
	data.put('APP_NAME',jsonData.basicInfo.applicationName)
	data.put('PIPELINE_NAME',jsonData.basicInfo.pipelineName)
	
    return data
	}
	
}
