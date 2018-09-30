/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.testing

import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.ReadFile
import com.infy.idp.utils.fs.WriteFile

/**
 *
 * This class has the method to add RFT testing tool
 *
 */
class RFT {

    /*
     * This method is used to add required wrappers
     */

    public static void addWrappers(context, jsonData, envIndex, stepIndex) {

        def basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test
        def tools
        def filesToCopy

        if (isValid(testStepElem.projectName) && isValid(testStepElem.testCase)) {

            tools = 'TG_RFTCUSTOMTOOL_HOME,ANT_HOME'
            filesToCopy = basepath + '_' + testEnvElem.envName + '_' + testEnvElem.testSteps.getAt(stepIndex).stepName + '_rft.xml'
        }

        if (tools) {
            (new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(context, tools)

        }
        if (filesToCopy) {
            CopyToSlave.invokeCopyToSlave(context, filesToCopy)
        }

    }

    /*
    * This method is used to add the steps to configure RFT
    */

    public static void addSteps(context, jsonData, envIndex, stepIndex, envVar) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test

        if (isValid(testStepElem.projectName) && isValid(testStepElem.testCase)) {

            def basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
            def iterationCount

            if (isValid(testStepElem.fullIteration) && !testStepElem.fullIteration.equalsIgnoreCase("off")) {
                iterationCount = "All"
            } else if (isValid(testStepElem.iteration)) {
                iterationCount = testStepElem.iteration
            } else {
                iterationCount = "1"
            }
            def executionCmd = 'java -jar "%IBM_RATIONAL_RFT_INSTALL_DIR%/rational_ft.jar" -datastore ' + '%IDP_WS%/' + testStepElem.projectName + ' -rt.bring_up_logviewer false -iterationCount ' + iterationCount + ' -playback ' + testStepElem.testCase
            prepareRFTAntXml(jsonData, envIndex, stepIndex, envVar)
            Ant.invokeAnt(context, 'RFTCompile', basepath + '_' + testEnvElem.envName + '_' + testEnvElem.testSteps.getAt(stepIndex).stepName + '_rft.xml')
            ExecuteCmd.invokeCmd(context, '"%IBM_RATIONAL_RFT_ECLIPSE_DIR%/jdk/jre/bin/java" -classpath "%IBM_RATIONAL_RFT_INSTALL_DIR%/rational_ft.jar" com.rational.test.ft.rational_ft -datastore' + ' "%IDP_WS%/' + testStepElem.projectName + '" -compile ' + testStepElem.testCase, jsonData.basicInfo.buildServerOS)
            ExecuteCmd.invokeCmd(context, executionCmd, jsonData.basicInfo.buildServerOS)

        }
    }

    public static boolean isValid(field) {
        return (field && field != '')
    }

    /*
     * This method is used to prepare RFT Xml file
     */

    private static String prepareRFTAntXml(jsonData, envIndex, stepIndex, envVar) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test

        String fileContent = ReadFile.run('/ant_templates/rft_ant.xml')

        def rftJarPath = '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.RFTJARNM)
        def projectpath = '${IDP_WS}/' + testStepElem.projectName

        fileContent = fileContent.replace('$PNAME', testStepElem.projectName)
        fileContent = fileContent.replace('$RFTCustomtool', rftJarPath)
        fileContent = fileContent.replace('$DirPath', projectpath)

        def jHome = envVar.JENKINS_HOME
        def basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + testEnvElem.envName + '_' + testEnvElem.testSteps.getAt(stepIndex).stepName + '_rft.xml', fileContent)
    }

}
