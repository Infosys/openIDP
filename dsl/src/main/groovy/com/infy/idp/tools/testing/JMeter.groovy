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
import com.infy.idp.utils.Constants
import com.infy.idp.utils.XShell


/**
 *
 * This class has the method to add JMeter testing tool
 *
 */

class JMeter {

    /*
    * This method is used to add the steps to configure JMeter
    */

    public static void invokeJMeter(context, testPlan, os, propertyFilePath) {

        def executable

        if (os == Constants.WINDOWSOS) {
            executable = '"%JMETER_HOME%/bin/jmeter.bat"'
        } else {
            executable = '"$JMETER_HOME/bin/jmeter.sh"'
        }

        if (propertyFilePath) {
            XShell.invokeXShell(context, executable + ' -q ' + propertyFilePath + ' -Jjmeter.save.saveservice.output_format=xml  -n -t "%IDP_WS%/' + testPlan + '" -l "%IDP_WS%/' + testPlan + '_result.jtl"')
        } else {
            XShell.invokeXShell(context, executable + ' -Jjmeter.save.saveservice.output_format=xml  -n -t "%IDP_WS%/' + testPlan + '" -l "%IDP_WS%/' + testPlan + '_result.jtl"')
        }
    }

    /*
    * This method is used to add required publishers
    */

    public static void addPublishers(context, jsonData, envIndex, stepIndex) {

        def testEnvElem = null
        def testStepElem = null

        testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        testStepElem = testEnvElem.testSteps.getAt(stepIndex).test


        JmeterPublisher jmeterPublisher = new JmeterPublisher();
        jmeterPublisher.setJmeterReportFile('**/' + testStepElem.testPlan + '_result.jtl')
        jmeterPublisher.add(context, jsonData)

        ArchiveArtifacts archiveArtifacts = new ArchiveArtifacts()

        archiveArtifacts.setPattern('**/' + testStepElem.testPlan + '_result.jtl')
        archiveArtifacts.add(context, jsonData)
    }
}
