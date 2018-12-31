/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package idp.stages

/**
 *  This class has the method to trigger the SCM job
 */
class SCMStage implements Serializable {
    def script

    /*
     * This method intializes the SCM stage execution
     */

    SCMStage(script) {
        this.script = script
    }

    /*
     * This method intializes the SCM regular stage execution
     */

    def regularBuild(jsonData, customWS, nodeObject, jobBuildId, buildLabel, basePath) {
        if (!(jsonData["technology"] && jsonData["technology"].equalsIgnoreCase("SapNonCharm"))) {
            this.scmJobExecution(jsonData, "_SCM", customWS, buildLabel, jobBuildId, basePath, nodeObject)
        }
    }

    /*
     * This method intializes the SCM Deploy stage execution
     */

    def deploySCMExecution(jsonData, customWS, nodeObject, jobBuildId, buildLabel, basePath) {
        if (!(jsonData["technology"] && jsonData["technology"].equalsIgnoreCase("SapNonCharm"))) {
            this.scmJobExecution(jsonData, "_SCM_DEPLOY", customWS, buildLabel, jobBuildId, basePath, nodeObject)
        }
    }

    /*
     * This method intializes the SCM Test stage execution
     */

    def testSCMExecution(jsonData, customWS, nodeObject, jobBuildId, buildLabel, basePath) {
        this.scmJobExecution(jsonData, "_SCM_TEST", customWS, buildLabel, jobBuildId, basePath, nodeObject)
    }

    /*
     * This method intializes the SCM parent job execution
     */

    def scmJobExecution(jsonData, stage, customWS, buildLabel, jobBuildId, basePath, nodeObject) {
        def branchOrtag = 'NA'
        //if(!(jsonData["technology"] && jsonData["technology"].equalsIgnoreCase("SapNonCharm")))
        //{
        if (jsonData["pipelineName"]) {

            def paramConfig_SCM = this.createParamConfig(jsonData)

            this.script.node(nodeObject.slaveName) {
                this.script.fileOperations([this.script.folderCreateOperation('CustomJobParm')])
                def St1 = customWS + "/CustomJobParm.properties"
                this.script.fileOperations([this.script.fileCreateOperation(fileContent: paramConfig_SCM, fileName: St1)])
            }

            def params = [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                          [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                          [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                          [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                          [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel]]

            if (jsonData["branchOrTagValue"] && (!jsonData["branchOrTagValue"].equalsIgnoreCase(""))) {
                branchOrtag = jsonData["branchOrTagValue"]

                def paramsize = params.size()

                this.script.echo "${params}"
                this.script.echo "${paramsize}"



                if (jsonData["branchOrTagList"] && jsonData["branchOrTagList"].size() != 0) {
                    def size = jsonData["branchOrTagList"].size();

                    def x = paramsize
                    for (int i = 0; i < size; i++) {

                        if (jsonData["branchOrTagList"][i] == true) {
                            params[x] = [$class: 'StringParameterValue', name: 'SCM_BRANCH_' + i, value: branchOrtag]
                            x++
                        }

                    }
                }
                def paramsize1 = params.size()

                this.script.echo "${params}"
                this.script.echo "${paramsize1}"

            }

            this.script.build job: basePath + stage,
                    parameters: params

        }

        //}
    }


    def createParamConfig(jsonData) {

        def paramConfig = ""
        def jobparamList = jsonData["jobParam"]
        def lenght = jobparamList.size()

        for (int j = 0; j < lenght; j++) {
            def envparname = jsonData['jobParam'][j]['jobParamName']
            def envparvalue = jsonData['jobParam'][j]['jobParamValue']
            paramConfig += envparname + "=" + envparvalue + "\n"

        }
        return paramConfig
    }
}
