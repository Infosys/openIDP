/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.testing


import com.infy.idp.utils.Constants;
import com.infy.idp.creators.*
import com.infy.idp.customtools.*
import com.infy.idp.tools.*
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*
import com.infy.idp.plugins.publishers.*
import com.infy.idp.plugins.wrappers.*
import com.infy.idp.plugins.buildsteps.*


class RPT {

	static String basepath

	private static void addSteps(context, jsonData,envIndex, stepIndex, envVar) {
		def testEnvElem = null
		def testStepElem = null
		testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
		testStepElem = testEnvElem.testSteps.getAt(stepIndex).test
		def os = jsonData.basicInfo.buildServerOS
		basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
		def projectName = ''
		def wsPath = ''
		if (testStepElem.projectName.contains('/')) {
			int index = testStepElem.projectName.lastIndexOf('/')
			projectName = testStepElem.projectName[index + 1..testStepElem.projectName.size() - 1]
			wsPath = '/' + testStepElem.projectName[0..index - 1]
		} else {
			projectName = testStepElem.projectName
		}
		prepareImportEclipseFile(jsonData, envVar, os, Constants.RPTDUMMYPROJECT, '/eclipse_import_dummy.xml', '_RPT_dummy_build.xml', wsPath)
		prepareImportEclipseFile(jsonData, envVar, os, projectName, '/eclipse_import.xml', '_RPT_build.xml', wsPath)

		String command = ''
		if (os.equalsIgnoreCase(Constants.WINDOWSOS)) {
			command = 'cd /d'
		} else {
			command = 'cd '
		}
		String sleepCmd = '\n' + 'sleep 30'
		String exitCmd = ' || exit 0'
		Map<String, String> prop = new HashMap()
		prop.put('IDP_WS', '$IDP_WS')
		context.with {
			steps {
				Ant.invokeAnt(delegate, 'eclipse-import', '$IDP_WS/' + basepath + '_RPT_dummy_build.xml', prop)
				Ant.invokeAnt(delegate, 'eclipse-import', '$IDP_WS/' + basepath + '_RPT_build.xml', prop)

				String dummyEclipseCommand = command + ' "%RPT_ECLIPSE_DIR%"' + '\n' + 'eclipse -nosplash -data "%IDP_WS%' + wsPath + '" -product ' + Constants.ECLIPSEPRODUCT + ' -application  ' + Constants.RUNNERPLUGIN + ' -buildfile "%IDP_WS%' + wsPath + '/eclipse_import_dummy.xml"' + sleepCmd
				String projectEclipseCommand = command + ' "%RPT_ECLIPSE_DIR%"' + '\n' + 'eclipse -nosplash -data "%IDP_WS%' + wsPath + '" -product ' + Constants.ECLIPSEPRODUCT + ' -application  ' + Constants.RUNNERPLUGIN + ' -buildfile "%IDP_WS%' + wsPath + '/eclipse_import.xml"' + sleepCmd
				ExecuteCmd.invokeCmd(delegate, dummyEclipseCommand, jsonData.basicInfo.buildServerOS)
				ExecuteCmd.invokeCmd(delegate, projectEclipseCommand, jsonData.basicInfo.buildServerOS)

				String dummyRPTCommand = command + ' "%RPT_CMD_EXEC%"' + '\n' + 'cmdline.bat  -workspace "%IDP_WS%' + wsPath + '" -eclipsehome "%RPT_ECLIPSE_DIR%" -plugins "%RPT_CMD_PLUGIN%" -project ' + Constants.RPTDUMMYPROJECT + ' -schedule ' + Constants.RPTDUMMYSCHEDULEPATH + ' -results Reports/dummy_result -exportstats "%IDP_WS%' + wsPath + '"' + exitCmd
				String projectRPTCommand = command + ' "%RPT_CMD_EXEC%"' + '\n' + 'cmdline.bat  -workspace "%IDP_WS%' + wsPath + '" -eclipsehome "%RPT_ECLIPSE_DIR%" -plugins "%RPT_CMD_PLUGIN%" -project ' + projectName + ' -schedule ' + testStepElem.testSuiteName + ' -results Reports/result -exportstats "%IDP_WS%' + wsPath + '"' + exitCmd
				ExecuteCmd.invokeCmd(delegate, dummyRPTCommand, jsonData.basicInfo.buildServerOS)
				ExecuteCmd.invokeCmd(delegate, projectRPTCommand, jsonData.basicInfo.buildServerOS)
			}
		}
	}

	public static void addWrappers(context,jsonData) {
		basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
		def tools = 'ANT_HOME,ECLIPSE_IMPORTER_HOME,GROOVYJAR_HOME'
		def copyPattern = basepath + '_RPT_build.xml,'+basepath + '_RPT_dummy_build.xml,'
		HashMap data=new HashMap()
		data.put("includePattern", '*.metadata')
		data.put("deleteDirectories", true)

		(new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(context, tools)

		CopyToSlave.invokeCopyToSlave(context, copyPattern)
		WrapperAdder.addbuildTimeoutWrapper(context)
		InvokePreBuildCleanup.invokepreBuildCleanup(context,data)
	}

	public static void addPublishers(context, jsonData, envIndex, stepIndex) {

		ArchiveArtifacts archiveArtifacts = new ArchiveArtifacts()
		archiveArtifacts.setPattern('**/result*.trc*')
		archiveArtifacts.add(context, jsonData)
	}
	public static void RPTJobCreation_ICTP(context, jsonData, processIndex, stepIndex, envVar) {

		context.with {
			//Standard IDP Settings
			configure { it / canRoam(false) }
			concurrentBuild(true)

			addSteps(context, jsonData, processIndex, stepIndex, envVar)

			//Optional settings of job
			String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
			if (customWS) customWorkspace('$IDP_WS')
		}
	}
	public static void RPTJobCreation(context, jsonData,envIndex, stepIndex, envVar) {
		context.with {
			//Standard IDP Settings
			configure { it / canRoam(false) }
			concurrentBuild(true)

			//Optional settings of job
			String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
			if(customWS)	customWorkspace('$IDP_WS')
		}
	}

	private static void prepareImportEclipseFile(jsonData, envVar, os, projectName, wsFileName, jenkinsFileName, wsPath) {
		String idpWS='${IDP_WS}';
		String CTPath = idpWS+"/..//../" + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH)
		basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName

		String fileContent = ReadFile.run('/ant_templates/RPT_build.xml')
		fileContent = fileContent.replace('$PROJECTNAME', basepath + '_RPT')
		fileContent = fileContent.replace('$ECLIPSEIMPORTERJAR', CTPath + PropReader.readProperty(Constants.CUSTOMTOOLFN,'PROJECT_IMPORTER'))
		fileContent = fileContent.replace('$RPTPROJECTNAME', projectName)
		fileContent = fileContent.replace('$GROOVYCLASSPATH', CTPath + PropReader.readProperty(Constants.CUSTOMTOOLFN,'GROOVY_JAR'))
		fileContent = fileContent.replace('$OLDXMLPath', CTPath + PropReader.readProperty(Constants.CUSTOMTOOLFN,'OLDXMLPATH'))
		fileContent = fileContent.replace('$NEWXMLPath', idpWS + wsPath + wsFileName)

		def jHome = envVar.JENKINS_HOME
		WriteFile.run(jHome + '/userContent/' + basepath + jenkinsFileName, fileContent)
	}
}
