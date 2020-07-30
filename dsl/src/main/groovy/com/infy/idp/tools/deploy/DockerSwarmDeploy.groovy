/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.deploy

import com.infy.idp.utils.Constants
import com.infy.idp.utils.ExecuteCmd
import com.infy.idp.utils.fs.*
import com.infy.idp.plugins.publishers.ArchiveArtifacts
import com.infy.idp.plugins.publishers.*

/**
 *
 * This class has the method to create the deployment job for docker deployment
 *
 */

class DockerSwarmDeploy {

    /*
	 * This methos add steps or confiuration in deploy job
	 */

    public static void addStepsDRWindows(context, jsonData, envIndex, stepIndex, envVar) {


        String idpWS = (jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) ? '%IDP_WS%/' : '$IDP_WS/'
        String command = ''
        if (jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) {
            command = 'D: \n' + 'cd '
        } else {
            command = 'cd '
        }

        def modulesArr = jsonData.deployInfo.deployEnv[envIndex].deploySteps[stepIndex]
        context.with {

            steps {
                String artID = '%ARTIFACT_VERSION%'

                if (artID.toString().trim().replaceAll("[\n\r]", "").endsWith("-")) {
                    artID = artID + '%PIPELINE_BUILD_ID%'
                }
                // def tagName=jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName;
                def tagNameDR = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName+"_"+artID


                def repoNameDR = jsonData.buildInfo.artifactToStage.artifactRepo.repoNameDR.toLowerCase();
                def dockerRegistryUrlDR  = jsonData.buildInfo.artifactToStage.artifactRepo.dockerRegistryUrlDR 


                def dockerUrlWithoutProtocol = dockerRegistryUrlDR
                if(dockerRegistryUrlDR.startsWith("http")){
                    dockerUrlWithoutProtocol = dockerRegistryUrlDR.substring(dockerRegistryUrlDR.indexOf("//")+2)

                }
                def imageName = dockerUrlWithoutProtocol+'/'+ repoNameDR+":"+tagNameDR
                String runCommand = ''

                runCommand += command + idpWS + '\n'
                runCommand += 'SET IMAGE_NAME='+imageName + '\n'
//                 runCommand += 'docker stack rm ' + modulesArr.stackName + '\n'
//                 runCommand += 'sleep 15 \n'
                runCommand += 'docker stack deploy -c ' + modulesArr.dockerComposePath +' ' + modulesArr.stackName

                ExecuteCmd.invokeCmd(delegate, runCommand, jsonData.basicInfo.buildServerOS)


            }
            addPublishers(delegate,jsonData,envIndex, stepIndex)
        }
    }
    public static void addStepsDRLinux(context, jsonData, envIndex, stepIndex, envVar) {


        String idpWS = '$IDP_WS/'
        String command = ''
        if (jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) {
            command = 'D: \n' + 'cd '
        } else {
            command = 'cd '
        }
        context.with {
            steps {

                String artID = '${ARTIFACT_VERSION}'

                if (artID.toString().trim().replaceAll("[\n\r]", "").endsWith("-")) {
                    artID = artID + '${PIPELINE_BUILD_ID}'
                }
                def tagNameDR = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName+"_"+artID
                def repoNameDR = jsonData.buildInfo.artifactToStage.artifactRepo.repoNameDR.toLowerCase();
                def dockerRegistryUrlDR  = jsonData.buildInfo.artifactToStage.artifactRepo.dockerRegistryUrlDR 
                def modulesArr = jsonData.deployInfo.deployEnv[envIndex].deploySteps[stepIndex]

                def dockerUrlWithoutProtocol = dockerRegistryUrlDR
                if(dockerRegistryUrlDR.startsWith("http")){
                    dockerUrlWithoutProtocol = dockerRegistryUrlDR.substring(dockerRegistryUrlDR.indexOf("//")+2)
                }

                def imageName = dockerUrlWithoutProtocol+'/'+ repoNameDR+":"+tagNameDR
                String runCommand = ''
                runCommand += command + idpWS + '\n'
                runCommand += 'export IMAGE_NAME='+imageName + '\n'
//                runCommand += 'docker stack rm ' + modulesArr.stackName + '\n'
//                runCommand += 'sleep 15 \n'
                runCommand += 'docker stack deploy -c ' + modulesArr.dockerComposePath + ' ' + modulesArr.stackName
                ExecuteCmd.invokeCmd(delegate, runCommand, jsonData.basicInfo.buildServerOS)



            }
            addPublishers(delegate,jsonData,envIndex, stepIndex)
        }
    }

    private static void addPublishers(context,jsonData,envIndex, stepIndex){
        def modulesArr = jsonData.deployInfo.deployEnv[envIndex].deploySteps[stepIndex]
        context.with{
            publishers{
                ArchiveArtifacts arch=new ArchiveArtifacts();
                arch.setPattern(modulesArr.dockerComposePath)
                arch.setAllowEmpty("true")
                arch.add(delegate,jsonData)


            }
        }
    }
}
