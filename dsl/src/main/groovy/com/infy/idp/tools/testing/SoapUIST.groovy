/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.testing

import com.infy.idp.plugins.publishers.PerformancePublisher
import com.infy.idp.utils.Constants
import com.infy.idp.utils.ExecuteCmd

//import com.infy.idp.utils.XShell
/**
 *
 * This class has the method to add SoapUIST testing tool
 *
 */
class SoapUIST {

    /*
     * This method is used to add the steps to configure SoapUIST
     */

    public static void addSteps(context, jsonData, envIndex, stepIndex) {
        def testEnvElem = null
        def testStepElem = null


        testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        testStepElem = testEnvElem.testSteps.getAt(stepIndex).test


        String executableFileName

        if (jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) {
            executableFileName = '"%SOAPUI_HOME%/testrunner.bat"'
        } else {
            executableFileName = '"${SOAPUI_HOME}/testrunner.sh"'
        }

        String cmd = executableFileName + ' -s "' + testStepElem.testSuiteName +
                '" -r -a -j -f "$IDP_WS/Reports" "' + testStepElem.projectLocation + '"'

        ExecuteCmd.invokeCmd(context, cmd, jsonData.basicInfo.buildServerOS)
    }

    /*
     * This method is used to add required publishers
     */

    public static void addPublishers(context, jsonData) {

        PerformancePublisher pp = new PerformancePublisher()
        pp.setPattern('**/TEST-*.xml')
        pp.add(context, jsonData)
    }

}
