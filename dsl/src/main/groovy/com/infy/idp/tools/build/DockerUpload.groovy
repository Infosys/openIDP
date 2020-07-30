/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.build
/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

import com.infy.idp.utils.Constants
import com.infy.idp.utils.CopyToSlave
import com.infy.idp.utils.ExecuteCmd
import com.infy.idp.utils.fs.*

/**
 *
 * This class has the method to create the deployment job for docker deployment
 *
 */

class DockerUpload {

    /*
     * This method is used to add required wrappers
     */

    private static void addWrappers(context, envVar) {
        def jHome = envVar.JENKINS_HOME
        context.with {
            wrappers {
                String fileContent = ReadFile.run('/ant_templates/ServiceStop.sh')
                WriteFile.run(jHome + '/userContent/ServiceStop.sh', fileContent)
                CopyToSlave.invokeCopyToSlave(delegate, 'ServiceStop.sh')
            }
        }

    }

    /*
    * This methos add steps or confiuration in deploy job
    */

    public static void addSteps(context, jsonData,  envVar) {


        String idpWS = (jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) ? '%IDP_WS%/' : '$IDP_WS/'
        String command = ''
        if (jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) {
            command = 'D: \n' + 'cd '
        } else {
            command = 'cd '
        }

        context.with {
            addWrappers(delegate, envVar)
            steps {
                def repo = modulesArr.repoUrl.split('/')
                String artifact = ''
                if (modulesArr.artifact == '') {
                    ExecuteCmd.invokeCmd(delegate, command + idpWS + '\n' + 'mkdir -p artifacts', jsonData.basicInfo.buildServerOS)
                    artifact = idpWS + 'artifacts'
                } else {
                    artifact = modulesArr.artifact
                }
                if (modulesArr.tagName != null) {

                    String moveCommand = command + artifact + '\n' + 'cp ' + idpWS + modulesArr.dockerFilePath + ' .' + '\n'
                    if (modulesArr.artifactsToBeDeployed != null) {
                        for (int i = 0; i < modulesArr.artifactsToBeDeployed.size(); i++) {
                            moveCommand += 'cp ' + idpWS + modulesArr.artifactsToBeDeployed.getAt(i) + ' .' + '\n'
                        }
                    }

                    ExecuteCmd.invokeCmd(delegate, moveCommand, jsonData.basicInfo.buildServerOS)
                    String createImageCommand = '';
                    createImageCommand += command + artifact + '\n' + 'sudo docker build -t ' + userNameDR+ '/' + modulesArr.userName.toLowerCase() + '/' + modulesArr.tagName + ' .'
                    ExecuteCmd.invokeCmd(delegate, createImageCommand, jsonData.basicInfo.buildServerOS)
                }
                if (modulesArr.pushToRepo == 'on') {

                    if (modulesArr.password != null) {
                        String pushCommand = ''
                        pushCommand += command + artifact + '\n' + 'sudo docker login -u ' + modulesArr.userName + ' -p ' + modulesArr.password + ' ' + modulesArr.repoUrl + '\n' + 'sudo docker push ' + repo[2] + '/' + modulesArr.userName + '/' + modulesArr.tagName

                        ExecuteCmd.invokeCmd(delegate, pushCommand, jsonData.basicInfo.buildServerOS)
                    }
                    if (modulesArr.password == null) {
                        String pushCommand = ''
                        pushCommand += command + artifact + '\n' + 'sudo docker login ' + modulesArr.repoUrl + '\n' + 'sudo docker push ' + repo[2] + '/' + modulesArr.userName + '/' + modulesArr.tagName

                        ExecuteCmd.invokeCmd(delegate, pushCommand, jsonData.basicInfo.buildServerOS)
                    }

                }

            }
        }
    }

    /*
     * This methos add steps or confiuration in deploy job
     */

    public static void addStepsDRWindows(context, jsonData, envVar) {

        def dockerFilePathDR = jsonData.buildInfo.artifactToStage.artifactRepo.dockerFilePathDR;
        def passwordDR = jsonData.buildInfo.artifactToStage.artifactRepo.passwordDR;
        def userNameDR = jsonData.buildInfo.artifactToStage.artifactRepo.userNameDR;
        def repoNameDR = (jsonData.buildInfo.artifactToStage.artifactRepo.repoNameDR).toLowerCase();
        def tagNameDR =  jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName+'_%RELEASE_IDENTIFIER%-%SCM_BRANCH%-%PIPELINE_BUILD_ID%'

        def dockerRegistryUrlDR  = jsonData.buildInfo.artifactToStage.artifactRepo.dockerRegistryUrlDR 
        def dockerUrlWithoutProtocol = dockerRegistryUrlDR
        if(dockerRegistryUrlDR.startsWith("http")){
            dockerUrlWithoutProtocol = dockerRegistryUrlDR.substring(dockerRegistryUrlDR.indexOf("//")+2)
        }

        String idpWS = (jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) ? '%IDP_WS%/' : '$IDP_WS/'
        String command = ''
        if (jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) {
            command = 'D: \n' + 'cd '
        } else {
            command = 'cd '
        }

        //def modulesArr = jsonData.buildInfo.modules
//        def modulesArr = jsonData.deployInfo.deployEnv[envIndex].deploySteps[stepIndex]
        context.with {
            addWrappers(delegate, envVar)
            steps {

                String createImageCommand = '';
                createImageCommand += command + idpWS + '\n' + 'docker build -t ' + dockerUrlWithoutProtocol+'/'+ repoNameDR+":"+tagNameDR + ' -f ' + dockerFilePathDR+ ' .'
                ExecuteCmd.invokeCmd(delegate, createImageCommand, jsonData.basicInfo.buildServerOS)



                if (passwordDR != null) {
                    String pushCommand = ''
                    pushCommand += 'docker login -u ' + userNameDR + ' -p ' + passwordDR + ' ' +  dockerRegistryUrlDR + '\n' + 'docker push ' + dockerUrlWithoutProtocol + '/' + repoNameDR + ':' + tagNameDR

                    ExecuteCmd.invokeCmd(delegate, pushCommand, jsonData.basicInfo.buildServerOS)
                }
                if (passwordDR == null) {
                    String pushCommand = ''
                    pushCommand += 'docker login ' +  dockerRegistryUrlDR + '\n' + 'docker push ' +  dockerRegistryUrlDR + '/' + repoNameDR + ':' + tagNameDR

                    ExecuteCmd.invokeCmd(delegate, pushCommand, jsonData.basicInfo.buildServerOS)
                }


            }
        }
    }
    public static void addStepsDRLinux(context, jsonData,  envVar) {


        String idpWS = '$IDP_WS/'
        String command = ''


        def dockerFilePathDR = jsonData.buildInfo.artifactToStage.artifactRepo.dockerFilePathDR;
        def passwordDR = jsonData.buildInfo.artifactToStage.artifactRepo.passwordDR;
        def userNameDR = jsonData.buildInfo.artifactToStage.artifactRepo.userNameDR;
        def repoNameDR = (jsonData.buildInfo.artifactToStage.artifactRepo.repoNameDR).toLowerCase();
        def tagNameDR =  jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName+'_${RELEASE_IDENTIFIER}-${SCM_BRANCH}-${PIPELINE_BUILD_ID}'
        def dockerRegistryUrlDR  = jsonData.buildInfo.artifactToStage.artifactRepo.dockerRegistryUrlDR 
        def dockerUrlWithoutProtocol = dockerRegistryUrlDR
        if(dockerRegistryUrlDR.startsWith("http")){
            dockerUrlWithoutProtocol = dockerRegistryUrlDR.substring(dockerRegistryUrlDR.indexOf("//")+2)
        }
        command = 'cd '

        context.with {
            addWrappers(delegate, envVar)
            steps {

                String createImageCommand = '';
                createImageCommand += command + idpWS + '\n' + 'sudo docker build -t ' + dockerUrlWithoutProtocol+'/'+ repoNameDR+":"+tagNameDR + ' -f ' + dockerFilePathDR+ ' .'
                ExecuteCmd.invokeCmd(delegate, createImageCommand, jsonData.basicInfo.buildServerOS)



                if (passwordDR != null) {
                    String pushCommand = ''

                    pushCommand += 'docker login -u ' + userNameDR + ' -p ' + passwordDR + ' ' +  dockerRegistryUrlDR + '\n' + 'docker push ' + dockerUrlWithoutProtocol + '/' + repoNameDR + ':' + tagNameDR

                    ExecuteCmd.invokeCmd(delegate, pushCommand, jsonData.basicInfo.buildServerOS)
                }
                if (passwordDR == null) {
                    String pushCommand = ''

                    pushCommand += 'sudo docker login ' +  dockerRegistryUrlDR + '\n' + 'sudo docker push ' +  dockerUrlWithoutProtocol + '/' +  repoNameDR + ':' + tagNameDR

                    ExecuteCmd.invokeCmd(delegate, pushCommand, jsonData.basicInfo.buildServerOS)
                }



            }
        }
    }
}

