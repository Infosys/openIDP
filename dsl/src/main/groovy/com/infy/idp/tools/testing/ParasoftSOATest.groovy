/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.testing

import com.infy.idp.plugins.publishers.ArchiveArtifacts
import com.infy.idp.plugins.publishers.JmeterPublisher
import com.infy.idp.utils.ExecuteCmd

/**
 *
 * This class has the method to add ParasoftSOATest testing tool
 *
 */
class ParasoftSOATest {

    /*
   * This method is used to add the steps to configure JMeter
   */

    public static void addSteps(context, jsonData, envIndex, stepIndex) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test
        def importProject = 'soatestcli -import "' + testStepElem.projectLocation + '" -data "' + testStepElem.projectLocation + '"'



        def executeCmd = 'soatestcli -data "' + testStepElem.projectLocation + '" -resource "' + testStepElem.scriptPath +
                '" -report "' + testStepElem.projectLocation + '/ParasoftSOATest.html "'

        if (testStepElem.testConfig && null != testStepElem.testConfig && testStepElem.testConfig != '') {
            executeCmd += ' -config "' + testStepElem.testConfig + '"'
        } else {
            executeCmd += ' -config "builtin://Demo Configuration"'
        }

        ExecuteCmd.invokeCmd(context, importProject, jsonData.basicInfo.buildServerOS)
        ExecuteCmd.invokeCmd(context, executeCmd, jsonData.basicInfo.buildServerOS)
    }

    /*
     * This method is used to add required publishers
     */

    public static void addPublishers(context, jsonData, envIndex, stepIndex) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test

        JmeterPublisher jmeterPublisher = new JmeterPublisher();
        jmeterPublisher.setJmeterReportFile('**/' + testStepElem.testPlan + '_result.jtl')
        jmeterPublisher.add(context, jsonData)

        ArchiveArtifacts archiveArtifacts = new ArchiveArtifacts()
        //archiveArtifacts.setPattern('workspace/' + jsonData.basicInfo.applicationName  + '_' + jsonData.basicInfo.pipelineName + '/' + jsonData.testInfo.testEnv.getAt(envIndex).testSteps.getAt(stepIndex).test.path  + '/Reports/results/**')
        archiveArtifacts.setPattern('**/' + testStepElem.testPlan + '_result.jtl')
        archiveArtifacts.add(context, jsonData)
    }
}
