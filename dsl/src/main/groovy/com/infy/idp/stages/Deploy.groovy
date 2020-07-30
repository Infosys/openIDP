/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.stages

import com.infy.idp.creators.*
import com.infy.idp.plugins.buildsteps.*
import com.infy.idp.plugins.wrappers.BuildNameSetter
import com.infy.idp.tools.NiaIntegStage
import com.infy.idp.tools.deploy.*
import com.infy.idp.tools.deploy.ArtifactoryDownload
import com.infy.idp.tools.deploy.NexusDownload
import com.infy.idp.utils.*
import com.infy.idp.plugins.wrappers.BuildEnv
import com.infy.idp.plugins.wrappers.BuildEnvIIS

/**
 *
 * This class has the method to create the deploy job based on the technology of the project
 *
 */

class Deploy {

    String basepath

    /*
     * This method is used to call other child jobs to create a deploy job with all the configuration
     */

    void run(factory, jsonData, envVar) {
        deployJobCreation(factory, jsonData, envVar)
    }

    /*
     * this method is used to create the deploy job in jenkins
     */

    void deployJobCreation(factory, jsonData, envVar) {
        basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
        if (jsonData.deployInfo && jsonData.deployInfo.deployEnv) {
            for (int i = 0; i < jsonData.deployInfo.deployEnv.size(); i++) {
                baseFolderCreation(factory, basepath + '/' + basepath + Constants.DEPLOYJOBNAMEPOSTFIX + '_' + jsonData.deployInfo.deployEnv[i].envName);
                def envObj =  jsonData.deployInfo.deployEnv[i];

                if(jsonData.code.technology.equalsIgnoreCase(Constants.SAP)) {
                    new BaseJobCreator(
                            jobName: basepath + '/' + basepath + Constants.DEPLOYJOBNAMEPOSTFIX + '_' + envObj.envName + '/' + basepath + Constants.DEPLOYJOBNAMEPOSTFIX + '_' + envObj.envName + '_' + 'Import',
                            jobDescription: ''
                    ).create(factory).with {
                        configure { it / canRoam(false) }
                        concurrentBuild(true)

                        keepDependencies(false)

                        //Configurable Settings of Job
                        addProperties(delegate, jsonData)
                        addWrappers(delegate,jsonData)
                        NiaIntegStage.run(delegate,jsonData,Constants.DEPLOY);
                    }

                    new BaseJobCreator(
                            jobName: basepath + '/' + basepath + Constants.DEPLOYJOBNAMEPOSTFIX + '_' + envObj.envName + '/' + basepath + Constants.DEPLOYJOBNAMEPOSTFIX + '_' + envObj.envName + '_' + 'Release',
                            jobDescription: ''
                    ).create(factory).with {

                        //Standard IDP Settings
                        configure { it / canRoam(false) }
                        concurrentBuild(true)
                        keepDependencies(false)

                        //Configurable Settings of Job
                        addProperties(delegate, jsonData)
                        addWrappers(delegate,jsonData)
                        NiaIntegStage.run(delegate,jsonData,Constants.DEPLOY);
                    }
                }
                else if(jsonData.deployInfo.deployEnv[i].deploySteps && jsonData.deployInfo.deployEnv[i].deploySteps.size() > 0)
                    addDeploySteps(factory, jsonData, i, envVar)
            }


        }
    }

    /*
     * This method is used to create the base folder for each environment in jenkins
     */

    private void baseFolderCreation(factory, name) {
        new BaseFolderCreator(folderName: name).create(factory)
    }

    /*
     * This method is used to add deploy steps in deploy job of jenkins
     */

    private void addDeploySteps(factory, jsonData, index, envVar) {
        def envObj = jsonData.deployInfo.deployEnv[index];
        if ((jsonData.buildInfo && jsonData.buildInfo.artifactToStage
                && (jsonData.buildInfo.artifactToStage.artifactRepoName == "nexus" || jsonData.buildInfo.artifactToStage.artifactRepoName == "jfrog" || jsonData.buildInfo.artifactToStage.artifactRepoName == "docker"))
                || (jsonData.code.technology.equalsIgnoreCase('J2EE/Ant'))) {
            new BaseJobCreator(
                    jobName: basepath + '/' + basepath + '_ArtifactDownload',
                    jobDescription: ''
            ).create(factory).with {
                configure { it / canRoam(false) }
                concurrentBuild(true)
                addProperties(delegate, jsonData)
                environmentVariables {
                    propertiesFile('$IDP_WS/CustomJobParm.properties')
                    keepBuildVariables(true)
                }
                keepDependencies(false)
                String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
                if (customWS) customWorkspace('$IDP_WS')
                if (jsonData.buildInfo.artifactToStage.artifactRepoName == "nexus") {
                    NexusDownload nexus = new NexusDownload()
                    nexus.nexusDownloadJobCreation(delegate, jsonData, envVar)
                }
                if (jsonData.buildInfo.artifactToStage.artifactRepoName == "jfrog") {
                    ArtifactoryDownload artifactory = new ArtifactoryDownload()
                    artifactory.artifactoryDownloadJobCreation(delegate, jsonData, envVar)
                }
                if (jsonData.buildInfo.artifactToStage.artifactRepoName == "docker") {
                    addWrapperForDocker(delegate,jsonData)
                    if(jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) {
                        DockerDownload.addStepsDRWindows(delegate, jsonData, envVar)
                    }else{
                        DockerDownload.addStepsDRLinux(delegate, jsonData, envVar)
                    }
                }
            }
        }
        if (jsonData.code.technology.equalsIgnoreCase(Constants.GENERAL)) {
            new BasePipelineCreator(
                    pipelineName: basepath + '/' + basepath + Constants.DEPLOYJOBNAMEPOSTFIX + '_' + envObj.envName + '/' + basepath + Constants.DEPLOYJOBNAMEPOSTFIX + '_' + envObj.envName
            ).create(factory).with {
                configure { it / canRoam(false) }
                concurrentBuild(true)
                keepDependencies(false)
                CatalogDeploy.createPipelineScript(basepath, jsonData, index, envVar)
                def jHome = envVar.JENKINS_HOME
                addProperties(delegate, jsonData)
                if(jsonData.deployInfo.deployEnv[index].envFlag == 'on'){
                    definition {
                        cps {

                            script(factory.readFileFromWorkspace(jHome + '/userContent/' + basepath + '_' + jsonData.deployInfo.deployEnv.getAt(index).envName + '_ps.groovy'))

                            sandbox(true)

                        }

                    }
                }
            }
        } else if (!jsonData.code.technology.equalsIgnoreCase(Constants.FILENET)) {
            if (envObj.deploySteps != '' && envObj.deploySteps != null) {
                for (int i = 0; i < envObj.deploySteps.size(); i++) {
                    new BaseJobCreator(
                            jobName: basepath + '/' + basepath + Constants.DEPLOYJOBNAMEPOSTFIX + '_' + envObj.envName + '/' + basepath + Constants.DEPLOYJOBNAMEPOSTFIX + '_' + envObj.envName + '_' + envObj.deploySteps[i].stepName,
                            jobDescription: ''
                    ).create(factory).with {

                        //Standard IDP Settings
                        configure { it / canRoam(false) }
                        concurrentBuild(true)
                            environmentVariables {
                                propertiesFile('$IDP_WS/CustomJobParm.properties')
                                keepBuildVariables(true)
                            }

                        keepDependencies(false)

                        //Configurable Settings of Job
                        addProperties(delegate, jsonData)
                        addWrappers(delegate, jsonData)

                        if (envObj.deploySteps[i].runScript) RunScript.add(delegate, envObj.deploySteps[i].runScript);
						if(envObj.deploySteps[i].envProv)EnvProv.add(delegate,envObj.deploySteps[i].envProv)
                        if (envObj.deploySteps[i].deployToContainer && envObj.deploySteps[i].deployToContainer.containerName && envObj.deploySteps[i].deployToContainer.containerName != Constants.IISDEPLOY)
                            DeployToContainer.add(delegate, jsonData, index, i)

                        if (envObj.deploySteps[i].deployToContainer && envObj.deploySteps[i].deployToContainer.containerName && envObj.deploySteps[i].deployToContainer.containerName == Constants.IISDEPLOY)
                            DeployToIIS.add(delegate, jsonData, index, i)
                        if (envObj.deploySteps[i].dbServName)
                            DBDeployObject.add(delegate, jsonData, index, i)
                        if (envObj.deploySteps[i].dockerFilePath)
                            DockerDeploy.addSteps(delegate, jsonData, index, i, envVar)

                        if (envObj.deploySteps[i].dockerComposePath)
                        {
                            if(jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) {
                               DockerSwarmDeploy.addStepsDRWindows(delegate, jsonData, index, i, envVar)
                           }else{
                                DockerSwarmDeploy.addStepsDRLinux(delegate, jsonData, index, i, envVar)
                            }

                        }
                        NiaIntegStage.run(delegate, jsonData, Constants.DEPLOY);

                        //Optional settings of job
                        String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
                        if (customWS) customWorkspace('$IDP_WS')
                    }



                }
            }

        }

    }

    /*
     * This method is used to add the required wrappers
     */

    void addWrappers(context, jsonData) {
        context.with {
            wrappers {
                BuildNameSetter buildname1 = new BuildNameSetter()
                buildname1.setTemplate('${BUILD_LABEL}' + '_' + '${build_number}')
                buildname1.add(delegate, jsonData)
				BuildEnv env = new BuildEnv()
				env.add(delegate, jsonData)
            }
        }
    }

    void addWrapperForDocker(context, jsonData){
        def password = jsonData.buildInfo.artifactToStage.artifactRepo.passwordDR;
       context.with{
            wrappers{
                BuildEnvIIS env = new BuildEnvIIS()
                env.setName("DR_PASSWORD")
                env.setPswd(password)
                env.add(delegate, jsonData)
            }
       }
    }

    /*
     * this method is used to add string or password parameters in jenkins job
     */

    private void addProperties(context, jsonData) {
        context.with {
            properties {
                PropertiesAdder.addGitLabConnection(delegate)
                PropertiesAdder.addRebuild(delegate)
                PropertiesAdder.addThrottleJobProperty(delegate)
                PropertiesAdder.addBuildDiscarder(delegate)
            }
            configure {
                it / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions' {
                    if (!jsonData.code.technology.equalsIgnoreCase(Constants.FILENET)) {
                        PropertiesAdder.addStringParam(delegate, 'trigger_id', 'NA', 'This is trigger id')
                        PropertiesAdder.addStringParam(delegate, 'IDP_WS', 'NA', 'CUSTOM Workspace')
                        PropertiesAdder.addStringParam(delegate, 'RELEASE_IDENTIFIER', 'NA', 'Release Number for trigger')
                        PropertiesAdder.addStringParam(delegate, 'ARTIFACT_VERSION', 'NA', 'This will tell which has version has to be uploaded')
                        PropertiesAdder.addStringParam(delegate, 'ARTIFACT_ID', 'NA', 'This will tell in which folder it has to be uploaded')
                        //Adding pipeline build id
                        PropertiesAdder.addStringParam(delegate, 'PIPELINE_BUILD_ID', 'NA', '')
                        PropertiesAdder.addStringParam(delegate, 'BUILD_LABEL', 'NA', '')
                        PropertiesAdder.addStringParam(delegate, 'ARTIFACT_NAME', 'NA', '')
                        //Adding job build  id
                        PropertiesAdder.addStringParam(delegate, 'JOB_BUILD_ID', 'NA', '')
                        PropertiesAdder.addNodeParam(delegate, 'SLAVE_NODE', ['ldaproleapp_Jmvn1_14Mr1_Slave'], '')
                        PropertiesAdder.addStringParam(delegate, 'SUBMODULE_LIST', 'NA', '')
                    }

                    if (jsonData.code.technology.toString().equalsIgnoreCase(Constants.GENERAL)) {
                        PropertiesAdder.addStringParam(delegate, 'STEP_LIST', 'NA', '')
                        PropertiesAdder.addStringParam(delegate, 'USER_INFO', 'NA', '')
                    }
                }
            }
        }
    }


}
