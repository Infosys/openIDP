/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.customtools

import com.infy.idp.tools.NiaIntegStage;
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*
import javaposse.jobdsl.dsl.DslFactory
import com.infy.idp.creators.*
import com.infy.idp.plugins.wrappers.*

class CastJobCreation{

	String basepath

	/*
	 * This method triggers the Cast job creation
	 */
	void run(DslFactory factory, jsonData, envVar) {
		basepath = jsonData.basicInfo.applicationName  + '_' + jsonData.basicInfo.pipelineName

		castJobCreation(factory, jsonData, envVar)
	}

	/*
	 * This method create the Cast job
	 */
	void castJobCreation(DslFactory factory, jsonData,envVar) {
		basepath = jsonData.basicInfo.applicationName  + '_' + jsonData.basicInfo.pipelineName
		new BaseJobCreator(

				jobName: basepath+ '/' + basepath + Constants.CASTJOBNAMEPOSTFIX,
				jobDescription: ''

				).create(factory).with {
					prepareCastBat(jsonData,envVar)
					if(jsonData.code.technology.toString().equalsIgnoreCase(Constants.SAP)) {
						invokeToolSAP(delegate, jsonData, envVar)
						addWrappers(delegate,jsonData)
					}
					else {
						invokeTool(delegate, jsonData, envVar)
					}
					addProperties(delegate, jsonData)
					addSettings(delegate)
					NiaIntegStage.run(delegate,jsonData, Constants.CAST);
				}
	}
	
	void addSettings(context) {
		context.with {
			configure { it / canRoam(false) }
			concurrentBuild(true)
		}
	}
	
	/*
	 * This method invokes the command line scripts
	 */
	void invokeTool(context, jsonData, envVar) {

		def moveCommand;
		moveCommand = 'XCOPY /Y "%IDP_WS%/*" "' + jsonData.buildInfo.castAnalysis.srcPath + '" /E /I'

		def runCommand;
		runCommand = 'cast_automation.bat %CURRENT_DATE_TIME% %OLD_VERSION% %NEW_VERSION%'

		prepareCastBat(jsonData, envVar)
		context.with {
			steps {
				ExecuteCmd.invokeCmd(delegate, moveCommand, jsonData.basicInfo.buildServerOS)
				ExecuteCmd.invokeCmd(delegate, runCommand, jsonData.basicInfo.buildServerOS)
			}
		}
	}

	/*
	 * This method created entire job configuration of SAP
	 */
	void invokeToolSAP(context, jsonData, envVar) {

		def moveCommand;
		String appServer = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))
				? '%APP_SERVER%' : '$APP_SERVER'

		String instance = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))
				? '%INSTANCE%' : '$INSTANCE'

		String client = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))
				? '%CLIENT%' : '$CLIENT'

		String sId = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))
				? '%SYSTEM_ID%' : '$SYSTEM_ID'

		String sapUserId = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))
				? '%SAP_USERID%' : '$SAP_USERID'

		String sapPassword = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))
				? '%SAP_PASSWORD%' : '$SAP_PASSWORD'

		String language = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))
				? '%LANGUAGE%' : '$LANGUAGE'

		String transReq = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))
				? '%TRANS_REQ%' : '$TRANS_REQ'

		String oldVersion = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))
				? '%OLD_VERSION%' : '$OLD_VERSION'

		String newVersion = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))
				? '%NEW_VERSION%' : '$NEW_VERSION'

		String currentDate = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))
				? '%CURRENT_DATE_TIME%' : '$CURRENT_DATE_TIME'

		String CUSTOM_TOOL_JAR=''
		if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
			CUSTOM_TOOL_JAR= '%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'SAPCAST')
		}
		else{
			CUSTOM_TOOL_JAR= '$IDP_WS/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'SAPCAST')
		}

		moveCommand = 'java -jar ' + CUSTOM_TOOL_JAR +' ' +appServer +' '+instance+' ' +client+' ' +sapUserId +' '+sapPassword +' '+language +' ' +'C:\\unzip\\' + ' ' +transReq + ' ' + jsonData.buildInfo.castAnalysis.srcPath

		def runCommand;
		runCommand =basepath + '_cast_automation.bat ' +currentDate+' ' +oldVersion + ' ' +newVersion

		prepareCastBat(jsonData, envVar)

		context.with{
			steps{
				ExecuteCmd.invokeCmd(delegate, moveCommand, jsonData.basicInfo.buildServerOS)
				ExecuteCmd.invokeCmd(delegate, runCommand, jsonData.basicInfo.buildServerOS)
			}
		}
	}

	/*
	 * This method invokes the command line scripts
	 */
	void prepareCastBat(jsonData,envVar) {

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
			fileContent = fileContent.replace('$BackupSchemaName',schemaName)
		}

		fileContent = fileContent.replace('$SchemaBackupPath','%IDP_WS%');

		WriteFile.run( envVar.JENKINS_HOME + '/userContent/' + basepath + '_'+ 'cast_automation.bat',fileContent)
	}

	public static void addWrappers(context, jsonData) {

		def toolList = getListOfTools(jsonData.buildInfo.modules)
		String basepath = jsonData.basicInfo.applicationName  + '_' + jsonData.basicInfo.pipelineName
		context.with {

			wrappers {

				(new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(delegate, toolList)

				CopyToSlave.invokeCopyToSlave(delegate,basepath + '_cast_automation.bat')
			}
		}
	}



	private static String getListOfTools(modulesArr) {

		String toolList = 'SAP_CAST_HOME'

		return toolList
	}

	void addProperties(context, jsonData) {

		context.with {

			properties {

				PropertiesAdder.addBuildDiscarder(delegate)
			}

			configure {

				it / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions' {

					PropertiesAdder.addStringParam(delegate, 'IDP_WS', 'NA', 'CUSTOM Workspace')
					PropertiesAdder.addStringParam(delegate, 'MODULE_LIST', 'NA', '')
					PropertiesAdder.addStringParam(delegate, 'RELEASE_IDENTIFIER', 'NA', '')
					PropertiesAdder.addStringParam(delegate, 'PIPELINE_BUILD_ID', 'NA', '')
					PropertiesAdder.addStringParam(delegate, 'JOB_BUILD_ID', 'NA', '')
					PropertiesAdder.addNodeParam(delegate, 'SLAVE_NODE', ['ldaproleapp_Jmvn1_14Mr1_Slave'],'')
					PropertiesAdder.addStringParam(delegate, 'CURRENT_DATE_TIME', 'NA', '')
					PropertiesAdder.addStringParam(delegate, 'OLD_VERSION', 'NA', '')
					PropertiesAdder.addStringParam(delegate, 'NEW_VERSION', 'NA', '')

					if(jsonData.code.technology.toString().equalsIgnoreCase(Constants.SAP)) {
						PropertiesAdder.addStringParam(delegate, 'APP_SERVER', 'NA', 'This parameter tells the application server name')
						PropertiesAdder.addStringParam(delegate, 'INSTANCE', 'NA', 'This parameter tells the instance name')
						PropertiesAdder.addStringParam(delegate, 'CLIENT', 'NA', 'This parameter tells the client name')
						PropertiesAdder.addStringParam(delegate, 'SYSTEM_ID', 'NA', 'This parameter tells the SID')
						PropertiesAdder.addStringParam(delegate, 'SAP_USERID', 'NA', 'This parameter tells the SAP User Name')
						PropertiesAdder.addStringParam(delegate, 'SAP_PASSWORD', 'NA', 'This parameter tells the SAP password')
						PropertiesAdder.addStringParam(delegate, 'LANGUAGE', 'NA', 'This parameter tells which ')
						PropertiesAdder.addStringParam(delegate, 'TRANS_REQ', 'NA', 'This parameter tells which artifacts has to be downloaded')
					}
				}
			}
		}
	}
}