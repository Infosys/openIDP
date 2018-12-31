/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package idp.stages

import idp.utils.*

/**
 *  This class has the method to execute test jobs in jenkins
 */
class TestStage implements Serializable {
    def script

    /*
    * This method intializes the test stage execution
    */

    TestStage(script) {
        this.script = script
    }

    /*
     * This method invokes the test job in jenkins
     */

    def execute(jsonData, customWS, nodeObject, jobBuildId, buildLabel, basePath) {
        if (jsonData["testSelected"] && jsonData["testSelected"].equalsIgnoreCase("on")) {
            def flag = true
            if (jsonData["technology"] && jsonData["technology"].equalsIgnoreCase("SapNonCharm") && !(jsonData["sapTestName"] && jsonData["sapTestName"].equalsIgnoreCase("rpt"))) {
                flag = false
            }

            /*TEST SCm Job start Here */
            if (flag) {
                SCMStage scmStage = new SCMStage(this.script)
                scmStage.testSCMExecution(jsonData, customWS, nodeObject, jobBuildId, buildLabel, basePath)
            }

            /*TEST SCm Job end Here */

            IDPJobInfo idpJobInfo = new IDPJobInfo(this.script)
            def names = idpJobInfo.jobNames(basePath + "/" + basePath + "_Test_" + jsonData.envSelected)

            def tempTestStepURLList = []
            if (jsonData["testStep"] && jsonData["testStep"].size() != 0) {

                for (int j = 0; j < jsonData["testStep"].size(); j++) {
                    //def tempJobName = basePath + "/" + basePath + "_Deploy_" + jsonData.envSelected + "/" + basePath + "_Deploy_" + jsonData.envSelected + "_" + jsonData["deploy"]["deployStep"][j];
                    def tempJobName = basePath + "/" + basePath + "_Test_" + jsonData.envSelected + "/" + basePath + "_Test_" + jsonData.envSelected + "_" + jsonData["testStep"][j]
                    if (!tempTestStepURLList.contains(tempJobName)) {
                        tempTestStepURLList.add(tempJobName);
                    }
                }

            }
            try {
                for (int i = 0; i < names.size(); i++) {
                    try {
                        if (!(jsonData["technology"] && jsonData["technology"].equalsIgnoreCase("SapNonCharm"))) {
                            this.executeNonSapJobs(jsonData, i, basePath, customWS, nodeObject, jobBuildId, buildLabel, names, tempTestStepURLList)
                        }

                        if ((jsonData["technology"] && jsonData["technology"].equalsIgnoreCase("SapNonCharm"))) {
                            this.sapNonCharmExecution(jsonData, i, basePath, nodeObject, jobBuildId, customWS, names, tempTestStepURLList)
                        }
                    } catch (Exception e) {
                        throw e
                    }

                }
            }
            finally {
                if (!(jsonData["technology"] && jsonData["technology"].equalsIgnoreCase("SapNonCharm"))) {
                    // service virtualization stop service
                    def tempServJobName1 = basePath + "/" + basePath + "_Test_" + jsonData.envSelected + "/" + basePath + "_Test_" + jsonData.envSelected + "_" + jsonData["virtualTestStep"]
                    if (jsonData["virtualTestStep"] && jsonData["virtualServicesList"] && names.contains(tempServJobName1)) {
                        this.script.echo "Building Service Virtualization test job with Action: Stop "
                        def virtualServicesStr = '["' + jsonData["virtualServicesList"].join('","') + '"]'
                        this.script.echo "Virtual service list: " + virtualServicesStr
                        this.script.build job: tempServJobName1,
                                parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                             [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                             [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                             [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                                             [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                             [$class: 'StringParameterValue', name: 'ACTION_STRING', value: actionStr],
                                             [$class: 'StringParameterValue', name: 'SERVICES_LIST', value: virtualServicesStr]]
                    }
                }
            }


        }
    }

    /*
     * This method invokes the nonSAP jobs in jenkins
     */

    def executeNonSapJobs(jsonData, i, basePath, customWS, nodeObject, jobBuildId, buildLabel, names, tempTestStepURLList) {

        if (jsonData["testStep"] && jsonData["testStep"].size() != 0) {
            def j = tempTestStepURLList.indexOf(names[i])
            if (tempTestStepURLList.contains(names[i])) {
                if (jsonData["mtmStepName"] && jsonData["testStep"][j].equalsIgnoreCase(jsonData["mtmStepName"]) && jsonData["testPlanId"]) {

                    this.script.echo "Building MTM test job"
                    this.script.echo jsonData["mtmStepName"]

                    this.script.echo jsonData["testPlanId"]
                    this.script.echo jsonData["envSelected"]

                    this.script.build job: names[i],
                            parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                         [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                         [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                         [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                                         [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                         [$class: 'StringParameterValue', name: 'MTM_TESTSUITE_ID', value: jsonData["testSuitId"]],
                                         [$class: 'StringParameterValue', name: 'MTM_TEST_PLAN_ID', value: jsonData["testPlanId"]],
                                         [$class: 'StringParameterValue', name: 'ENVIRONMENT', value: jsonData["envSelected"]],
                                         [$class: 'StringParameterValue', name: 'IDP_PROJECT_NAME', value: jsonData["mtmProjectName"]],
                                         [$class: 'StringParameterValue', name: 'BUILD_NO', value: this.script.BUILD_NUMBER],
                                         [$class: 'StringParameterValue', name: 'RELEASE_NO', value: jsonData["releaseNumber"]]]
                } else if (jsonData["virtualTestStep"] && jsonData["testStep"][j].equalsIgnoreCase(jsonData["virtualTestStep"]) && jsonData["virtualServicesList"]) {

                    this.script.echo "Building Service Virtualization test jobs with Action: Start "
                    this.script.echo "Virtual Service Test Step: " + jsonData["virtualTestStep"]
                    def actionStr = 'start'
                    //this.script.echo "Virtual service list: "+jsonData["virtualServicesList"].toString()
                    def virtualServicesStr = '["' + jsonData["virtualServicesList"].join('","') + '"]'
                    this.script.echo "Virtual service list: " + virtualServicesStr

                    this.script.build job: names[i],
                            parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                         [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                         [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                         [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                                         [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                         [$class: 'StringParameterValue', name: 'ENVIRONMENT', value: jsonData["envSelected"]],
                                         [$class: 'StringParameterValue', name: 'RELEASE_NO', value: jsonData["releaseNumber"]],
                                         [$class: 'StringParameterValue', name: 'ACTION_STRING', value: actionStr],
                                         [$class: 'StringParameterValue', name: 'SERVICES_LIST', value: virtualServicesStr]]
                } else if (jsonData["ibmRQMTestSuiteId"] && jsonData["testStepDetails"][j]["testStepTool"].equalsIgnoreCase("ibmrqm")) {

                    this.script.echo "Building IBM RQM test job with Action: Start "
                    this.script.echo "IBMRQM Test Step: " + jsonData["testStepDetails"][j]["testStepName"]
                    def actionStr = 'start'

                    this.script.build job: names[i],
                            parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                         [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                         [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                         [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                                         [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                         [$class: 'StringParameterValue', name: 'SERVER_URL', value: jsonData["ibmRQMServerDetails"]["serverURL"]],
                                         [$class: 'StringParameterValue', name: 'USERNAME', value: jsonData["ibmRQMServerDetails"]["userName"]],
                                         [$class: 'StringParameterValue', name: 'PASSWORD', value: jsonData["ibmRQMServerDetails"]["password"]],
                                         [$class: 'StringParameterValue', name: 'TSER_ID', value: jsonData["ibmRQMTestSuiteId"]]]
                } else {
                    this.script.echo "Building other test jobs"

                    this.script.build job: names[i],
                            parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                         [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                         [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                         [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                                         [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel]]
                }
            }
            

        } else {
            this.script.build job: names[i],
                    parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                 [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                 [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                 [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                                 [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel]]
        }
    }


    def sapNonCharmExecution(jsonData, i, basePath, nodeObject, jobBuildId, customWS, names, tempTestStepURLList) {

        if (jsonData["testStep"] && jsonData["testStep"].size() != 0) {
            if (tempTestStepURLList.contains(names[i])) {
                this.script.build job: names[i],
                        parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                     [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                     [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                     [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                                     [$class: 'StringParameterValue', name: 'APP_SERVER', value: jsonData["systemName"]],
                                     [$class: 'StringParameterValue', name: 'INSTANCE', value: jsonData["instance"]],
                                     [$class: 'StringParameterValue', name: 'CLIENT', value: jsonData["client"]],
                                     [$class: 'StringParameterValue', name: 'SYSTEM_ID', value: jsonData["systemId"]],
                                     [$class: 'StringParameterValue', name: 'SAP_USERID', value: jsonData["sapUserName"]],
                                     [$class: 'StringParameterValue', name: 'SAP_PASSWORD', value: jsonData["password"]],
                                     [$class: 'StringParameterValue', name: 'LANGUAGE', value: jsonData["language"]]]
            }

        } else {
            this.script.build job: names[i],
                    parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                 [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                 [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                 [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                                 [$class: 'StringParameterValue', name: 'APP_SERVER', value: jsonData["systemName"]],
                                 [$class: 'StringParameterValue', name: 'INSTANCE', value: jsonData["instance"]],
                                 [$class: 'StringParameterValue', name: 'CLIENT', value: jsonData["client"]],
                                 [$class: 'StringParameterValue', name: 'SYSTEM_ID', value: jsonData["systemId"]],
                                 [$class: 'StringParameterValue', name: 'SAP_USERID', value: jsonData["sapUserName"]],
                                 [$class: 'StringParameterValue', name: 'SAP_PASSWORD', value: jsonData["password"]],
                                 [$class: 'StringParameterValue', name: 'LANGUAGE', value: jsonData["language"]]]
        }
    }
}
