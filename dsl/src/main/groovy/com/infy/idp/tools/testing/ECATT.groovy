/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

 package com.infy.idp.tools.testing

import com.infy.idp.creators.*
import com.infy.idp.customtools.*
import com.infy.idp.tools.*
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*
import com.infy.idp.plugins.publishers.*
import com.infy.idp.plugins.wrappers.*
import com.infy.idp.plugins.buildsteps.*
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper

class ECATT {

	static String basepath

	private static void addSteps(context, jsonData, envIndex, stepIndex) {

		String idpWS = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS)) ? '%IDP_WS%/' : '$IDP_WS/'
		String appServer = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))? '%APP_SERVER%' : '$APP_SERVER'

		String instance = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))? '%INSTANCE%' : '$INSTANCE'

		String client = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS)) ? '%CLIENT%' : '$CLIENT'

		String sapUserId = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))? '%SAP_USERID%' : '$SAP_USERID'

		String sapPassword = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))? '%SAP_PASSWORD%' : '$SAP_PASSWORD'

		String language = (jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS))? '%LANGUAGE%' : '$LANGUAGE'

		String command ='';
		if(jsonData.basicInfo.buildServerOS.equalsIgnoreCase(Constants.WINDOWSOS)){
			command ='D: \n' + 'cd '
		}
		else {
			command ='cd '
		}
		def modulesArr = jsonData.buildInfo.modules
		def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
		def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test
		def testConfig = testStepElem.testSuiteName

		for(int i=0; i < modulesArr.size(); i++) {
			def importCommand;
			context.with {
				steps {
					String custom_tool = '%IDP_WS%/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN,Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN,'SAPTEST')
					importCommand = 'java -jar "' +custom_tool+ '"'+' ' + appServer +' ' +instance  +' ' +client +' ' + sapUserId  +' ' + sapPassword  +' ' +language+' '+ testConfig  +' ' + idpWS +'SAP_REPORTS'
					ExecuteCmd.invokeCmd(delegate, importCommand, jsonData.basicInfo.buildServerOS)
				}
			}
		}
	}
	
	public static void addWrappers(context) {
		def toolList = getListOfTools()
		context.with {
			wrappers {
				(new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(delegate, toolList)
			}
		}
	}

	private static String getListOfTools() {
		String toolList = 'SAP_TEST_HOME'
		return toolList
	}

	public static void eCATTJobCreation(context, jsonData, envIndex, stepIndex) {
		context.with {
			configure { it / canRoam(false) }
			concurrentBuild(true)

			//addProperties(delegate)
			addSteps(context, jsonData, envIndex, stepIndex)
			addWrappers(context)
			
			//Optional settings of job
			String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
			if(customWS)	customWorkspace('$IDP_WS')
		}
	}
	
}



