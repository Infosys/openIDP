/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/
 
package com.infy.idp.customtools

import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*


class CAST{

	/*
	 *	 This function is used to invoke cast customtool of IDP.
	 *	 It will add the commands according to Build server OS( Windows/Linux) and add the customtool for the job
	 */
	public static void invokeTool(context, jsonData, envVar) {
		
		def moveCommand;
		moveCommand = 'XCOPY /Y "%IDP_WS%/*" "' + jsonData.buildInfo.castAnalysis.srcPath + '" /E /I'
		
		def runCommand;
		runCommand = 'cast_automation.bat %CURRENT_DATE_TIME% %OLD_VERSION% %NEW_VERSION%'
		
		prepareCastBat(jsonData, envVar)
		
		ExecuteCmd.invokeCmd(context, moveCommand, jsonData.basicInfo.buildServerOS)
		ExecuteCmd.invokeCmd(context, runCommand, jsonData.basicInfo.buildServerOS)
	}
	
	/*
	 *	 This function will make the bat script run for Cast
	 */
	private static void prepareCastBat(jsonData,envVar) {
		
		String fileContent = ReadFile.run('/ant_templates/cast_analysis_automation_script.bat')
		String basepath = jsonData.basicInfo.applicationName  + '_' + jsonData.basicInfo.pipelineName
		String schemaName = jsonData.buildInfo.castAnalysis.schemaName
		
		fileContent = fileContent.replace('$AppName',jsonData.buildInfo.castAnalysis.applicationName)
		fileContent = fileContent.replace('$ConnProfile',jsonData.buildInfo.castAnalysis.connectionProfile)
		
		if (schemaName.endsWith("_central")) {
			fileContent = fileContent.replace('$SchemaName', schemaName)
		
		} else {
			fileContent = fileContent.replace('$SchemaName',schemaName + "_central")
		}
	
		if (schemaName.endsWith("_central")) {			
			String[] splitWithColen = schemaName.split("_central")
			String backupSchema = splitWithColen[0]
			
			fileContent = fileContent.replace('$BackupSchemaName',backupSchema);
		}
		else {
			fileContent = fileContent.replace('$BackupSchemaName',schemaName);
		}
			
		fileContent = fileContent.replace('$SchemaBackupPath','%IDP_WS%');
						
		def jHome = envVar.JENKINS_HOME
		WriteFile.run(jHome + '/jobs/' + basepath + '/jobs/' + basepath + Constants.BUILDJOBNAMEPOSTFIX + '/cast_automation.bat', fileContent)
	}
}