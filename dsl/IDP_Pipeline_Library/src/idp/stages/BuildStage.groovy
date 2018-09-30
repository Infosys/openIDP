/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package idp.stages

/**
 *  This class has the method to trigger the build job
 */
class BuildStage implements Serializable {
    def script
    /*
     * This method intializes the build stage executin
     */

    BuildStage(script) {
        println "initialization BuildStage"
        this.script = script
    }

    /*
     * This method invokes the build job in jenkins
     */

    def regularBuild(jsonData, customWS, nodeObject, jobBuildId, buildLabel, basePath, transReq) {
        println "invoking regular build"
        def branchOrtag = 'NA'
        def moduleList = 'NA'
        def flag = '1'
        def unitTesting = '0'
        def codeAnalysis = '0'

        if (jsonData["build"]["module"])
            moduleList = ';' + jsonData["build"]["module"].join(';').toString() + ';'

        try {
            if (!(jsonData["technology"] && (jsonData["technology"].equalsIgnoreCase("SapNonCharm") || jsonData["technology"].equalsIgnoreCase("dbDeployDelphix") || jsonData["technology"].equalsIgnoreCase("fileNet") || jsonData["technology"].equalsIgnoreCase("general")))) {
                try {
                    this.script.build job: basePath + "_Build",
                            parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                         [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                         [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: jsonData["releaseNumber"]],
                                         [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                         [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                         [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                                         [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                         [$class: 'StringParameterValue', name: 'ARTIFACT_NAME', value: jsonData["artifactName"]]]

                }
                catch (Exception e) {
                    if (e.toString().contains("FAILURE")) {
                        flag = '0'
                        throw e
                    } else {
                    }

                }
                finally {
                    if (flag == '1') {
                        if (jsonData["artifactorySelected"] == 'on') {
                            if (jsonData["branchOrTagValue"] && (!jsonData["branchOrTagValue"].equalsIgnoreCase(""))) {
                                branchOrtag = jsonData["branchOrTagValue"]
                            } else {
                                branchOrtag = 'default'
                            }
                            println "invoking upload job"
                            this.script.build job: basePath + "_ArtifactUpload",
                                    parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                                 [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                                 [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: jsonData["releaseNumber"]],
                                                 [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                                 [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                                 [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                                                 [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                                 [$class: 'StringParameterValue', name: 'SCM_BRANCH', value: branchOrtag],
                                                 [$class: 'StringParameterValue', name: 'ARTIFACT_NAME', value: jsonData["artifactName"]]]

                        }
                    }
                }

            }
            if (jsonData["technology"].equalsIgnoreCase("general")) {
                try {
                    this.script.build job: basePath + "_Build",
                            parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                         [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                         [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: jsonData["releaseNumber"]],
                                         [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                         [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                         [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                                         [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                         [$class: 'StringParameterValue', name: 'USER_INFO', value: jsonData["userName"]],
                                         [$class: 'StringParameterValue', name: 'ARTIFACT_NAME', value: jsonData["artifactName"]]]
                }
                catch (Exception e) {
                    if (e.toString().contains("FAILURE")) {
                        flag = '0'
                        throw e
                    } else {
                    }

                }
                finally {
                    if (flag == '1') {
                        if (jsonData["artifactorySelected"] == 'on') {
                            if (jsonData["branchOrTagValue"] && (!jsonData["branchOrTagValue"].equalsIgnoreCase(""))) {
                                branchOrtag = jsonData["branchOrTagValue"]
                            } else {
                                branchOrtag = 'default'
                            }
                            println "invoking upload job"
                            this.script.build job: basePath + "_ArtifactUpload",
                                    parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                                 [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                                 [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: jsonData["releaseNumber"]],
                                                 [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                                 [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                                 [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                                                 [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                                 [$class: 'StringParameterValue', name: 'SCM_BRANCH', value: branchOrtag],
                                                 [$class: 'StringParameterValue', name: 'ARTIFACT_NAME', value: jsonData["artifactName"]]]

                        }
                    }
                }

            }
            if ((jsonData["technology"] && jsonData["technology"].equalsIgnoreCase("fileNet"))) {

                try {
                    this.script.build job: basePath + "_Build",
                            parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                         [$class: 'StringParameterValue', name: 'trigger_id', value: jsonData["triggerId"].toString()],
                                         [$class: 'StringParameterValue', name: 'ManifestFile_Path', value: jsonData.build.manifestFileName],
                                         [$class: 'StringParameterValue', name: 'Source_Environment', value: jsonData.build.srcEnv],
                                         [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: jsonData["releaseNumber"]],
                                         [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                         [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                         [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                                         [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                         [$class: 'StringParameterValue', name: 'ARTIFACT_NAME', value: jsonData["artifactName"]]]
                }
                catch (Exception e) {
                    this.script.echo e.toString() + ""
                    if (e.toString().contains("FAILURE")) {
                        flag = '0'
                        throw e
                    } else {
                    }

                }
                finally {
                    if (flag == '1') {
                        if (jsonData["artifactorySelected"] == 'on') {
                            if (jsonData["branchOrTagValue"] && (!jsonData["branchOrTagValue"].equalsIgnoreCase(""))) {

                                branchOrtag = jsonData["branchOrTagValue"]
                            } else {

                                branchOrtag = 'NA'
                            }
                            println "invoking upload job"
                            build job: basePath + "_ArtifactUpload",
                                    parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                                 [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                                 [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: jsonData["releaseNumber"]],
                                                 [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                                 [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: BUILD_NUMBER],
                                                 [$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
                                                 [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                                 [$class: 'StringParameterValue', name: 'SCM_BRANCH', value: branchOrtag],
                                                 [$class: 'StringParameterValue', name: 'ARTIFACT_NAME', value: jsonData["artifactName"]]]

                        }
                    }
                }


            }

        }
        catch (Exception e) {
            throw e
        }
    }
}
