/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.customtools

import com.infy.idp.utils.*


class TGSonarCustomTool{

	public static void invokeTool(context, jsonData) {
		HashMap<String, String> data = performMapping(jsonData)
		def command
		command = """java -jar  ${data.get('CUSTOM_TOOL_PATH')} ${data.get('CUSTOM_TOOL_PROP')} ${data.get('SONAR_DB_TYPE')} ${data.get('SONAR_URL')} ${data.get('SONAR_JDBC_URL')}
${data.get('SONAR_USERNAME')} ${data.get('SONAR_PASSWORD')} ${data.get('CUSTOM_TOOL_PATH')}"""
		ExecuteCmd.invokeCmd(context, command, jsonData.basicInfo.buildServerOS)
	}
	
	private static Map<String,String> performMapping(jsonData) {
	HashMap<String, String> data = new HashMap<String, String>()
	data.put('CUSTOM_TOOL_PATH','%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH)+ PropReader.readProperty(Constants.CUSTOMTOOLFN,'SONARRUNNEREDITOR'))
	data.put('CUSTOM_TOOL_PROP','%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH)+ PropReader.readProperty(Constants.CUSTOMTOOLFN,'SONARRUNNERPROP'))
	data.put('SONAR_DB_TYPE','') //JSON
	data.put('SONAR_URL','') //JSON
	data.put('SONAR_JDBC_URL','') //JSON
	data.put('SONAR_USERNAME','') //JSON
	data.put('SONAR_PASSWORD','') //JSON
    return data
	}
	
}