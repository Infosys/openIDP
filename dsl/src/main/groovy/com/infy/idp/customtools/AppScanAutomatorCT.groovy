/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.customtools

import com.infy.idp.creators.*
import com.infy.idp.plugins.buildsteps.*
import com.infy.idp.plugins.publishers.*
import com.infy.idp.plugins.wrappers.*
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.tools.*
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*

/**
 *
 * This class includes the method for adding AppScanAutomatorCT customtool
 *
 */

class AppScanAutomatorCT {

    /*
     * This function is used to add the wrapper for AppScanAutomator, ANT and Driver.
     */

    public static void addWrappers(context, jsonData, envIndex, stepIndex) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex)
        String basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
        def copyPattern = basepath + '_' + testEnvElem.envName + '_' + testStepElem.stepName + '_appScan_test.xml'
        def tools

        tools = 'ANT_HOME,APPSCANAUTOMATOR_HOME,IEDRIVERSERVER_HOME'

        if (tools) {
            (new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(context, tools)
            CopyToSlave.invokeCopyToSlave(context, copyPattern)
        }

    }

    /*
	 *	 This function is used to invoke AppScanAutomatorCT customtool of IDP.
	 *	 It will add the commands according to Build server OS( Windows/Linux) and add the customtool for the job
	 */

    public
    static void invokeAppScanAutomatorCT(context, folderUrl, userName, password, jobName, jsonData, envVar, envIndex, stepIndex) {

        String fileContent = ReadFile.run('/ant_templates/appScanEnterpriseAutomator.xml')

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex)

        fileContent = fileContent.replace('$PNAME', "step1")
        fileContent = fileContent.replace('$url', folderUrl)
        fileContent = fileContent.replace('$username', userName)
        fileContent = fileContent.replace('$password', password)
        fileContent = fileContent.replace('$job', jobName)

        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName + '_' + testEnvElem.envName + '_' + testStepElem.stepName + '_appScan_test.xml', fileContent)


        Ant.invokeAnt(context, 'AppScanEnterpriseAutomator', jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName + '_' + testEnvElem.envName + '_' + testStepElem.stepName + '_appScan_test.xml')
    }


}
