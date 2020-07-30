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
 * This class includes the method for adding TGMavenPOMEditor customtool
 *
 */

class TGMavenPOMEditor{

	/*
    * This function is used to add the commands to run TGMavenPOMEditor customtool
    */

	public static void invokeTool(context, jsonData,moduleIndex,goal) {
		HashMap<String, String> data = performMapping(jsonData,moduleIndex,goal)
		def command
		if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
			command = """java -jar ${data.get('CUSTOM_TOOL_JAR')} ${data.get('POM_PATH')} ${data.get('OPTIONS')} ${data.get('SONAR_FLAG')} ${data.get('SONAR_JURL')} ${data.get('SONAR_JDRIVER')} ${data.get('SONAR_USERNAME')} ${data.get('SONAR_PASSWORD')} ${data.get('SONAR_HURL')} ${data.get('JLINE_TOOL_PATH')} "%IDP_WS%/${data.get('MAVEN_PROJ_NAME')}" ${data.get('MAVEN_PAF_FILE')} "%IDP_WS%/${data.get('MAVEN_PROJ_NAME')}-assessment.xml" "%IDP_WS%/${data.get('MAVEN_PROJ_NAME')}/report.html" ${data.get('NEXUS_REPO_ID')} ${data.get('NEXUS_REPO_URL')}"""
		}
		else{
			command = """java -jar ${data.get('CUSTOM_TOOL_JAR')} ${data.get('POM_PATH')} ${data.get('OPTIONS')} ${data.get('SONAR_FLAG')} ${data.get('SONAR_JURL')} ${data.get('SONAR_JDRIVER')} ${data.get('SONAR_USERNAME')} ${data.get('SONAR_PASSWORD')} ${data.get('SONAR_HURL')} ${data.get('JLINE_TOOL_PATH')} "\$IDP_WS/${data.get('MAVEN_PROJ_NAME')}" ${data.get('MAVEN_PAF_FILE')} "\$IDP_WS/${data.get('MAVEN_PROJ_NAME')}-assessment.xml" "\$IDP_WS/${data.get('MAVEN_PROJ_NAME')}/report.html" ${data.get('NEXUS_REPO_ID')} ${data.get('NEXUS_REPO_URL')}"""
		}
		
		ExecuteCmd.invokeCmd(context, command, jsonData.basicInfo.buildServerOS)
	}

	/*
	* This function is used to perform mapping for adding TGMavenPOMEditor customtool
	*/

	private static Map<String,String> performMapping(jsonData,moduleIndex,goal) {
		HashMap<String, String> data = new HashMap<String, String>()
		if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
			data.put('CUSTOM_TOOL_JAR','%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'POMEDITOR'))
		}
		else{
			data.put('CUSTOM_TOOL_JAR','$IDP_WS/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'POMEDITOR'))
		}
		
		if(jsonData.buildInfo.modules[moduleIndex].multiModule && jsonData.buildInfo.modules[moduleIndex].multiModule.equalsIgnoreCase("on"))
			goal += 'multi '
			
		data.put('OPTIONS','"' + goal + '"')
		data.put('POM_PATH','"' + jsonData.buildInfo.modules[moduleIndex].relativePath + '"')
		
		if(jsonData.buildInfo.modules[moduleIndex].codeAnalysis.contains('sonar')){
			data.put('SONAR_FLAG','On')
		}
		else{
			data.put('SONAR_FLAG','off')
		}
			
		data.put('SONAR_JURL','""') // Master Props
		data.put('SONAR_JDRIVER','""') // Master Props
		data.put('SONAR_USERNAME','""') // Master Props
		data.put('SONAR_PASSWORD','""') // Master Props
		data.put('SONAR_HURL','""') // Master Props
		data.put('JLINE_TOOL_PATH','""')
		data.put('MAVEN_PROJ_NAME',jsonData.buildInfo.modules[moduleIndex].moduleName)
		
		if(jsonData.buildInfo.modules[moduleIndex].pafFilePath){
			data.put('MAVEN_PAF_FILE','"' + jsonData.buildInfo.modules[moduleIndex].pafFilePath + '"')
		}
		else{
			data.put('MAVEN_PAF_FILE','""')
		}
		
		data.put('NEXUS_REPO_ID','""')  // Master Props
		data.put('NEXUS_REPO_URL','""') // Master Props
		
		return data
	}		
}
