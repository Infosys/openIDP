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
 * This class includes the method for adding MSTestCoverageMSXSL customtool
 *
 */

class MSTestCoverageMSXSL {

	/*
     * This function is used to add the commands to run MSTestCoverageMSXSL
     */

	public static void invokeTool(context, jsonData, moduleIndex) {
		
		HashMap<String, String> data = performMapping(jsonData,moduleIndex)
		def command = """ "${data.get('MSXSL_EXE')}" "${data.get('XML_PATH')}" "${data.get('XSL_FILE')}" -o "${data.get('OP_FILE')}" """
		ExecuteCmd.invokeCmd(context, command, jsonData.basicInfo.buildServerOS)
	}

	/*
     * This function is used to perform mapping for adding MSTestCoverageMSXSL
     */

	private static Map<String,String> performMapping(jsonData, moduleIndex) {
		
		HashMap<String, String> data = new HashMap<String, String>()
		
		def solDir = jsonData.buildInfo.modules.getAt(moduleIndex).relativePath.replace('\\', '/')
	 
		solDir = solDir.contains('/') ? solDir.substring(0, solDir.lastIndexOf('/')) + '/' : ''
			
		data.put('XML_PATH', '%IDP_WS%/' + solDir + 'data.xml')
		data.put('MSXSL_EXE', '%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'MSXSL'))
		data.put('XSL_FILE', '%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'EMMAXSL'))
		data.put('OP_FILE', '%IDP_WS%/' + solDir + 'Coverage.xml')
		
		return data
	}	
}
