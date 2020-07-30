/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.testing

import com.infy.idp.plugins.buildsteps.HPRunFromFileBuilder
import com.infy.idp.plugins.publishers.HPRunResultRecorder

/**
 *
 * This class has the method to add HpUftFT testing tool
 *
 */
public class HpUftFT {

    /*
    * This method is used to add the steps to configure HpUftFT
    */

    public static void addSteps(context, jsonData, envIndex, stepIndex) {

        context.with {

            steps {

                def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex);
                def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test
                HPRunFromFileBuilder hpUft = new HPRunFromFileBuilder()


                hpUft.setTestScriptPath(testStepElem.testSuiteName)
                hpUft.setTimeout(testStepElem.timeout)

                hpUft.add(delegate, jsonData)

            }
        }
    }

    /*
     * This method is used to add required publishers
     */

    public static void addPublishers(context, jsonData) {
        context.with {
            publishers {

                HPRunResultRecorder hpUftReporter = new HPRunResultRecorder();
                hpUftReporter.add(delegate, jsonData)

            }
        }
    }
}
