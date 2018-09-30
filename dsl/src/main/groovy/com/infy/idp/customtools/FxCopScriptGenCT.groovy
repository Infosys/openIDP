/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.customtools

import com.infy.idp.utils.Constants
import com.infy.idp.utils.ExecuteCmd
import com.infy.idp.utils.PropReader

/**
 *
 * This class includes the method for adding FxCopScriptGenCT customtool
 *
 */

class FxCopScriptGenCT {

	/*
     * This function is used to add the commands to run FxCopScriptGenCT
     */

	public static void invokeTool(context, jsonData, moduleIndex) {
		
		HashMap<String, String> data = performMapping(jsonData,moduleIndex)
		def command = """ java -jar "${data.get('DOTCOP_JAR')}" "${data.get('SOL_PATH')}" "${data.get('SCRIPT_OP_PATH')}" "${data.get('FXCOP_RULESET')}" "${data.get('CMD_ARGS')}" """
		ExecuteCmd.invokeCmd(context, command, jsonData.basicInfo.buildServerOS)
	}

	/*
     * This function is used to perform mapping for adding FxCopScriptGenCT
     */

	private static Map<String,String> performMapping(jsonData, moduleIndex) {
		
		HashMap<String, String> data = new HashMap<String, String>()
		
		data.put('DOTCOP_JAR','%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'DOTCOPJAR'))
		
		def solDir = jsonData.buildInfo.modules.getAt(moduleIndex).relativePath.replace('\\', '/')
		
		solDir = solDir.contains('/') ? '/' + solDir.substring(0, solDir.lastIndexOf('/')) : ''
			
		data.put('SOL_PATH', '%IDP_WS%' + solDir)
		data.put('SCRIPT_OP_PATH', '%IDP_WS%' + solDir)
		
		data.put('FXCOP_RULESET', '%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'FXCOP'))
		data.put('CMD_ARGS','/renaming:off /controlflow:low /encrypt:off')
		
		return data
	}	
}
