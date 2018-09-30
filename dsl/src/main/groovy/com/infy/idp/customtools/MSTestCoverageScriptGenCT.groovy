/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.customtools

import com.infy.idp.utils.PropReader
import com.infy.idp.utils.ExecuteCmd
import com.infy.idp.utils.Constants

/**
 *
 * This class includes the method for adding MSTestCoverageScriptGenCT customtool
 *
 */

public class MSTestCoverageScriptGenCT {

	/*
     * This function is used to add the commands to run MSTestCoverageScriptGen
     */

	public static void invokeTool(context, jsonData, moduleIndex) {

		HashMap<String, String> data = performMapping(jsonData,moduleIndex)
		def command = """ java -jar "${data.get('COVERAGE_JAR')}" "${data.get('UNIT_PROJ')}" "${data.get('UNIT_PROJ_NEW')}" "${data.get('BUILDTASKDLL')}" "${data.get('SOL_FOLDER')}" "${data.get('SOL_FOLDER')}" "${data.get('SOL_FOLDER')}" "${data.get('CODECOVERAGE_EXE')}" """
		ExecuteCmd.invokeCmd(context, command, jsonData.basicInfo.buildServerOS)
	}

	/*
     * This function is used to perform mapping for adding MSTestCoverageScriptGen
     */

	private static Map<String,String> performMapping(jsonData,moduleIndex) {
		
		HashMap<String, String> data = new HashMap<String, String>()
				
		def solDir = jsonData.buildInfo.modules.getAt(moduleIndex).relativePath.replace('\\', '/')				
		solDir = solDir.contains('/') ? solDir.substring(0, solDir.lastIndexOf('/')) : ''
		def testPrjctName = jsonData.buildInfo.modules.getAt(moduleIndex).unitTestProjectName
		def projFileExt
		if(jsonData.code.technology == Constants.CSDOTNETTECH)
			projFileExt = '.csproj'
		if(jsonData.code.technology == Constants.VBDOTNETTECH)
			projFileExt = '.vbproj'
				
		data.put('COVERAGE_JAR', '%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'COVERAGEJAR'))
		data.put('UNIT_PROJ', '%IDP_WS%/' + solDir + '/' + testPrjctName + '/' + testPrjctName + projFileExt)
		data.put('UNIT_PROJ_NEW', '%IDP_WS%/' + solDir + '/' + testPrjctName + '/Modified_' + testPrjctName + projFileExt)
		data.put('BUILDTASKDLL', '%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'BUILDTASKDLL'))
		data.put('SOL_FOLDER', '%IDP_WS%/' + solDir)
		data.put('CODECOVERAGE_EXE','%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'CODECOVERAGEEXE')) 
		return data
	}	
}
