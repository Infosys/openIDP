/*********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 ***********************************************************************************************/

package idp.stages

import idp.utils.*

/**
 *  This class has the method to trigger the deploy job
 */

class DeployStage implements Serializable {
    def script

    /*
     * This method intializes the deploy stage execution
     */

    DeployStage(script) {
        this.script = script
    }

    /*
     * This method invokes the build job in jenkins
     */

    def execute(jsonData, customWS, nodeObject, jobBuildId, buildLabel, basePath, transReq) {

        if (jsonData["deploy"]) {



            def moduleList1 = 'NA'
            def branchOrtag = 'NA'
            

            if (jsonData["deploy"]["subModule"])
                moduleList1 = ';' + jsonData["deploy"]["subModule"].join(';').toString() + ';'

            /*Deploy SCm Job start Here */
            SCMStage scmStage = new SCMStage(this.script)
            scmStage.deploySCMExecution(jsonData, customWS, nodeObject, jobBuildId, buildLabel, basePath)

            /*Deploy SCm Job start Here */

            def path = ''
            def nugetVersion = ''
            def artifactId = ''
            def artifactVersion = ''
            def branchNexus = ''
            if (jsonData["branchOrTagValue"] && (!jsonData["branchOrTagValue"].equalsIgnoreCase(""))) {
                branchNexus = jsonData["branchOrTagValue"]
            } else
                branchNexus = 'default'
            if (jsonData["deploy"]["deployArtifact"]) {


                if (jsonData["deploy"]["deployArtifact"]["downloadURL"]) {
                    path = jsonData["deploy"]["deployArtifact"]["downloadURL"]
                    nugetVersion = path.split('/')[path.split('/').size() - 2]


                } else {
                    path = "http://" + jsonData["deploy"]["deployArtifact"]["nexusURL"] + "/repository/" + jsonData["deploy"]["deployArtifact"]["repoName"] + "/" + jsonData["applicationName"] + "/" + jsonData["pipelineName"] + "/" + jsonData["releaseNumber"] + "-" + branchNexus + "-${this.script.BUILD_NUMBER}/" + jsonData["pipelineName"] + "-" + jsonData["releaseNumber"] + "-" + branchNexus + "-${this.script.BUILD_NUMBER}.zip"
                    nugetVersion = jsonData["releaseNumber"] + "-" + branchNexus + "-" + this.script.BUILD_NUMBER

                }

            }
            if (jsonData["artifactorySelected"] == "on") {

                if (jsonData["nugetPackaging"] == true) {
                    branchOrtag = jsonData["branchOrTagValue"]
                    if (branchOrtag == null || branchOrtag.equalsIgnoreCase('') || branchOrtag.equalsIgnoreCase('NA')) {
                        artifactId = jsonData["build"]["branchSelected"]
                    } else {
                        artifactId = branchOrtag
                    }
                    artifactVersion = nugetVersion
                } else {
                    artifactId = jsonData["deploy"]["deployArtifact"]["artifactID"]
                    artifactVersion = jsonData["releaseNumber"] + "-" + branchNexus + "-" + jsonData["deploy"]["deployArtifact"]["version"]
					if(jsonData["deploy"]["deployArtifact"]!=null &&  jsonData["deploy"]["deployArtifact"]["artifactID"] && jsonData["deploy"]["deployArtifact"]["artifactID"]!=null){
                        if((jsonData["deploy"]["deployArtifact"]["version"]).indexOf("-")!=-1){
                            artifactVersion = jsonData["deploy"]["deployArtifact"]["version"]
                        }
                    }
                }

                this.script.build job: basePath + "_ArtifactDownload",
                        parameters: [[$class: 'StringParameterValue', name: 'ARTIFACT_VERSION', value: artifactVersion],
                                     [$class: 'StringParameterValue', name: 'ARTIFACT_ID', value: artifactId],
                                     [$class: 'StringParameterValue', name: 'PIPELINE_NAME', value: basePath],
                                     [$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                     [$class: 'StringParameterValue', name: 'RELEASE_NO', value: jsonData["releaseNumber"]],
                                     [$class: 'StringParameterValue', name: 'trigger_id', value: jsonData["triggerId"].toString()],
                                     [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                     [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                     [$class: 'StringParameterValue', name: 'NEXUS_URL', value: path],
                                     [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                     [$class: 'StringParameterValue', name: 'BRANCH_NEXUS', value: branchNexus]]
            }

            def moduleList = 'NA'
            if (jsonData["build"] && jsonData["build"]["module"]) moduleList = ';' + jsonData["build"]["module"].join(';').toString() + ';'

            IDPJobInfo idpJobInfo = new IDPJobInfo(this.script)
            def names = idpJobInfo.jobNames(basePath + "/" + basePath + "_Deploy_" + jsonData.envSelected)

            def relNo
            if (jsonData["usePreviousArtifact"] != null && jsonData["usePreviousArtifact"] == "on") {
                relNo = jsonData["buildartifactNumber"]
            } else {
                relNo = jsonData["releaseNumber"]
            }
            if (jsonData["technology"].equalsIgnoreCase("fileNet")) {

                for (int j = 0; j < jsonData["deploy"]["deployStep"].size(); j++) {
                    def tempJobName = basePath + "/" + basePath + "_Deploy_" + jsonData.envSelected + "/" + basePath + "_Deploy_" + jsonData.envSelected + "_" + jsonData["deploy"]["deployStep"][j]


                    this.script.build job: tempJobName,
                            parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                         [$class: 'StringParameterValue', name: 'RELEASE_NUMBER', value: relNo],
                                         [$class: 'StringParameterValue', name: 'trigger_id', value: jsonData["triggerId"].toString()],
                                         [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                         [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                         [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                         [$class: 'StringParameterValue', name: 'PIPELINE_ID', value: jsonData["pipId"]],
                                         [$class: 'StringParameterValue', name: 'APPLICATION_ID', value: jsonData["appId"]],
                                         [$class: 'StringParameterValue', name: 'Source_Environment', value: jsonData["deploy"]["srcEnv"]],
                                         [$class: 'StringParameterValue', name: 'Environment_Name', value: jsonData["envSelected"]],
                                         [$class: 'StringParameterValue', name: 'ManifestFile_Path', value: jsonData["deploy"]["manifestFileName"]],

                                         [$class: 'StringParameterValue', name: 'Pair_Name', value: jsonData["deploy"]["pairName"]]]


                }
            } else {
                def tempDeployStepURLList = []
                if (jsonData["technology"] && !jsonData["technology"].equalsIgnoreCase("SapNonCharm")) {
                    if (jsonData["deploy"]["deployStep"] && jsonData["deploy"]["deployStep"].size() != 0) {
					
                        for (int j = 0; j < jsonData["deploy"]["deployStep"].size(); j++) {
						
                            def tempJobName = basePath + "/" + basePath + "_Deploy_" + jsonData.envSelected + "/" + basePath + "_Deploy_" + jsonData.envSelected + "_" + jsonData["deploy"]["deployStep"][j];
							
							
                            if (!tempDeployStepURLList.contains(tempJobName)) {
                                tempDeployStepURLList.add(tempJobName)
                            }
                        }
                    }
                }

                for (int i = 0; i < names.size(); i++) {
					
                    if (jsonData["rmAssemblies"] != null && jsonData["rmAssemblies"] != '') {
						
                        this.script.build job: names[i],
                                parameters: [[$class: 'BooleanParameterValue', name: 'REMOVE_PREVIOUS_ASSEMBLIES', value: true],
                                             [$class: 'StringParameterValue', name: 'PREVIOUS_VERSION', value: jsonData["rmAssemblies"]],
                                             [$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                             [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                             [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: relNo],
                                             [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                             [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                             [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                             [$class: 'StringParameterValue', name: 'SUBMODULE_LIST', value: moduleList1]]
                    }



                    if (jsonData["technology"] && !jsonData["technology"].equalsIgnoreCase("SapNonCharm")
                            && !jsonData["technology"].equalsIgnoreCase("DBDEPLOY") && !jsonData["technology"].equalsIgnoreCase("general")) {
							
                        if (jsonData["deploy"]["deployStep"] && jsonData["deploy"]["deployStep"].size() != 0) {
                            /* for(int j=0 ; j < jsonData["deploy"]["deployStep"].size() ; j++)
                            {
                                def tempJobName = basePath + "/" + basePath + "_Deploy_" + jsonData.envSelected + "/" + basePath + "_Deploy_" + jsonData.envSelected + "_" + jsonData["deploy"]["deployStep"][j]
                                 */
                            if (tempDeployStepURLList.contains(names[i])) {
                                def tempJobName = names[i]
                                if (jsonData["technology"].equalsIgnoreCase("dbDeployDelphix")) {
                                    this.script.build job: tempJobName,
                                            parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                                         [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                                         [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: relNo],
                                                         [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                                         [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                                         [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                                         [$class: 'StringParameterValue', name: 'DB_OPERATION', value: jsonData["dbOperations"]],
                                                         [$class: 'StringParameterValue', name: 'DEPLOY_DATABASE', value: jsonData["deployDB"]],
                                                         [$class: 'StringParameterValue', name: 'RESTORE_STATUS', value: jsonData["restoreDB"]],
                                                         [$class: 'StringParameterValue', name: 'PIPELINE_ID', value: jsonData["pipId"]],
                                                         [$class: 'StringParameterValue', name: 'APPLICATION_ID', value: jsonData["appId"]],
                                                         [$class: 'StringParameterValue', name: 'ENVIRONMENT', value: jsonData["envSelected"]]]

                                } else if (jsonData["technology"].equalsIgnoreCase("bigdata")) {
                                    def bdModuleList
                                    if (jsonData["deploy"]["deployModule"] && jsonData["deploy"]["deployModule"].size() > 0) {
                                        bdModuleList = ';' + jsonData["deploy"]["deployModule"].join(';').toString() + ';'
                                    } else {
                                        bdModuleList = ';' + jsonData["build"]["module"].join(';').toString() + ';'
                                    }

                                    this.script.build job: tempJobName,
                                            parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                                         [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                                         [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: relNo],
                                                         [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                                         [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                                         [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                                         [$class: 'StringParameterValue', name: 'SUBMODULE_LIST', value: moduleList1],
                                                         [$class: 'StringParameterValue', name: 'BDMODULE_LIST', value: bdModuleList]]
                                } else {
                                    this.script.build job: tempJobName,
                                            parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                                         [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                                         [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: relNo],
 							[$class: 'StringParameterValue', name: 'ARTIFACT_VERSION', value: artifactVersion],					                                         [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                                         [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                                         [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                                         [$class: 'StringParameterValue', name: 'SUBMODULE_LIST', value: moduleList1]]
                                }
                            }

                            /* } */
                        } else {
                            this.script.build job: names[i],
                                    parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                                 [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                                 [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: relNo],
                                                 [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                                 [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                                 [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel]]

                        }

                    } else if ((jsonData["technology"] && jsonData["technology"].equalsIgnoreCase("DBDEPLOY"))) {
                        def rollbackType = 'NA'
                        def rollbackValue = 'NA'
                        if (jsonData["deploy"]["dbDeployRollbackType"] && !jsonData["deploy"]["dbDeployRollbackType"].equalsIgnoreCase("")) {
                            rollbackType = jsonData["deploy"]["dbDeployRollbackType"]
							
                        }

                        if (jsonData["deploy"]["dbDeployRollbackValue"] && !jsonData["deploy"]["dbDeployRollbackValue"].equalsIgnoreCase("")) {
                            rollbackValue = jsonData["deploy"]["dbDeployRollbackValue"]
                        }

                        if ("UPDATE".equalsIgnoreCase(jsonData["deploy"]["dbDeployOperation"]) && !names[i].endsWith("_ROLLBACK")) {
                            try {
								for(int k=0; k < tempDeployStepURLList.size(); k++){
								if(names[i].contains(tempDeployStepURLList[k])){
								this.script.echo "update job execution"
								this.script.echo names[i]
                                this.script.build job: names[i],
                                        parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                                     [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                                     [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: relNo],
                                                     [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                                     [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                                     [$class: 'StringParameterValue', name: 'DBOPERATION', value: jsonData["deploy"]["dbDeployOperation"]],
                                                     [$class: 'StringParameterValue', name: 'ROLLBACK_STRATEGY', value: rollbackType],
                                                     [$class: 'StringParameterValue', name: 'ROLLBACK_VALUE', value: rollbackValue],
                                                     [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel]]
                            }
							}
							}
                            catch (Exception e) {
                                this.script.echo 'DBDeployment Exception Handling Mechanism'

                                this.script.build job: names[i] + "_ROLLBACK",
                                        parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                                     [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                                     [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: relNo],
                                                     [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                                     [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                                     [$class: 'StringParameterValue', name: 'DBOPERATION', value: jsonData["deploy"]["dbDeployOperation"]],
                                                     [$class: 'StringParameterValue', name: 'ROLLBACK_STRATEGY', value: rollbackType],
                                                     [$class: 'StringParameterValue', name: 'ROLLBACK_VALUE', value: rollbackValue],
                                                     [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel]]
                            }
                        } else if ("ROLLBACK".equalsIgnoreCase(jsonData["deploy"]["dbDeployOperation"]) && names[i].endsWith("_ROLLBACK")) {
						for(int k=0; k < tempDeployStepURLList.size(); k++){
							if(names[i].contains(tempDeployStepURLList[k])){
						
                            this.script.build job: names[i],
                                    parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                                 [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                                 [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: relNo],
                                                 [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                                 [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                                 [$class: 'StringParameterValue', name: 'DBOPERATION', value: jsonData["deploy"]["dbDeployOperation"]],
                                                 [$class: 'StringParameterValue', name: 'ROLLBACK_STRATEGY', value: rollbackType],
                                                 [$class: 'StringParameterValue', name: 'ROLLBACK_VALUE', value: rollbackValue],
                                                 [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel]]
												 }
                        }
						}

                    } else if ((jsonData["technology"] && jsonData["technology"].equalsIgnoreCase("general"))) {
                        def deploySteps = 'NA'
                        if (jsonData["deploy"]["deployStep"] && jsonData["deploy"]["deployStep"].size() != 0) {
                            deploySteps = ';' + jsonData["deploy"]["deployStep"].join(';').toString() + ';'
                        }
                        this.script.build job: names[i],
                                parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                             [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                             [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: relNo],
                                             [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                             [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                             [$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
                                             [$class: 'StringParameterValue', name: 'STEP_LIST', value: deploySteps],
                                             [$class: 'StringParameterValue', name: 'USER_INFO', value: jsonData["userName"]]]

                    }

                    if ((jsonData["technology"] && jsonData["technology"].equalsIgnoreCase("SapNonCharm"))) {
                       
                        if ((names[i].endsWith("Import") && jsonData["deploy"]["deploymentType"].equalsIgnoreCase("Import")) || (names[i].endsWith("Release") && jsonData["deploy"]["deploymentType"].equalsIgnoreCase("Release"))) {
                            def SAPDeployStatus = 'NA'
                            def jobObj
                            try {
                                jobObj = this.script.build job: names[i],
                                        parameters: [[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS],
                                                     [$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
                                                     [$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: jsonData["releaseNumber"]],
													 [$class: 'StringParameterValue', name: 'trigger_id', value: jsonData["triggerId"].toString()],
                                                     [$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
                                                     [$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
                                                     [$class: 'StringParameterValue', name: 'APP_SERVER', value: jsonData["systemName"]],
                                                     [$class: 'StringParameterValue', name: 'INSTANCE', value: jsonData["instance"]],
                                                     [$class: 'StringParameterValue', name: 'CLIENT', value: jsonData["client"]],
                                                     [$class: 'StringParameterValue', name: 'SYSTEM_ID', value: jsonData["systemId"]],
                                                     [$class: 'StringParameterValue', name: 'SAP_USERID', value: jsonData["sapUserName"]],
                                                     [$class: 'StringParameterValue', name: 'SAP_PASSWORD', value: jsonData["password"]],
                                                     [$class: 'StringParameterValue', name: 'LANGUAGE', value: jsonData["language"]],
                                                     [$class: 'StringParameterValue', name: 'TRANS_REQ', value: transReq],
                                                     [$class: 'BooleanParameterValue', name: 'COPY', value: jsonData["copyTR"]],
                                                     [$class: 'StringParameterValue', name: 'RESTORE_PARAM', value: jsonData["restoreTRParams"]],
                                                     [$class: 'BooleanParameterValue', name: 'RESTORE_FLAG', value: jsonData["restoreTRFlag"]]]
                            }
                            catch (Exception e) {
                                throw e
                            }
                            finally {
                                if (jobObj != null)
                                    SAPDeployStatus = jobObj.result

                            }
                        }
                    }
                }
            }
        }
    }
}

