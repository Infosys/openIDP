/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.stages

import com.infy.idp.creators.*
import com.infy.idp.tools.*
import com.infy.idp.tools.testing.*
import com.infy.idp.utils.*
import javaposse.jobdsl.dsl.DslFactory

/**
 *
 * This class has the method to create the test job
 *
 */

class Test {

    String basepath

    /*
    * This method is used to call other child jobs to create a test job with all the configuration
    */

    void run(DslFactory factory, jsonData, envVar) {
        basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
        testJobCreation(factory, jsonData, envVar)
    }

    /*
     * this method is used to create the test job in jenkins
     */

    private void testJobCreation(factory, jsonData, envVar) {

        if (!jsonData.testInfo || jsonData.testInfo.toString().equals("{}") || !jsonData.testInfo.testEnv || jsonData.testInfo.testEnv.size() == 0)
            return

        def testInfoArr = jsonData.testInfo.testEnv

        for (int i = 0; i < testInfoArr.size(); i++) {

            createTestEnvFolder(factory, testInfoArr.getAt(i).envName)
            def testStepsArr = testInfoArr.getAt(i).testSteps

            if (!testStepsArr || testStepsArr.size() == 0)
                continue

            for (int j = 0; j < testStepsArr.size(); j++) {

                new BaseJobCreator(
                        jobName: basepath + '/' + basepath + Constants.TESTJOBNAMEPOSTFIX + '_' + testInfoArr.getAt(i).envName + '/' + basepath + Constants.TESTJOBNAMEPOSTFIX + '_' + testInfoArr.getAt(i).envName + '_' + testStepsArr.getAt(j).stepName,
                        jobDescription: ''
                ).create(factory).with {
                    if (jsonData.buildInfo && jsonData.buildInfo.buildtool != '' && !'SapNonCharm'.equalsIgnoreCase(jsonData.buildInfo.buildtool)) {
                        environmentVariables {
                            propertiesFile('$IDP_WS/CustomJobParm.properties')
                            keepBuildVariables(true)
                        }
                    }
                    TestJobCreator.testJobCreator(delegate, jsonData, i, j, envVar)
                    //if(!(jsonData.code.technology.equalsIgnoreCase(Constants.SAP)))
                    //{
                    NiaIntegStage.run(delegate, jsonData, Constants.TEST);
                    //}
                }
            }
        }
    }

    /*
     * This method is used to create a folder for each environment in an application
     */

    private void createTestEnvFolder(factory, env) {

        def fName = basepath + '/' + basepath + Constants.TESTJOBNAMEPOSTFIX + '_' + env
        new BaseFolderCreator(folderName: fName).create(factory)
    }


}
