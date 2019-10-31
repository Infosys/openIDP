/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.stages

import com.infy.idp.creators.*
import com.infy.idp.customtools.*
import com.infy.idp.plugins.wrappers.BuildNameSetter
import com.infy.idp.tools.NiaIntegStage
import com.infy.idp.tools.build.*
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*
import javaposse.jobdsl.dsl.DslFactory

import com.infy.idp.plugins.wrappers.BuildEnv
import com.infy.idp.plugins.wrappers.BuildEnvIIS
import com.infy.idp.plugins.wrappers.*
import com.infy.idp.plugins.buildsteps.*

/**
 *
 * This class has the method to create the build job based on the technology of the project
 *
 */

class Build {

    String basepath

    /*
     * This method is used to call other child jobs to create a build job with all the configuration
     */

    void run(DslFactory factory, jsonData, envVar) {
        basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
        buildJobCreation(factory, jsonData, envVar)
	}
    /*
     * this method is used to create the build job in jenkins
     */

    void buildJobCreation(factory, jsonData, envVar) {
        if (!jsonData.buildInfo) return

        //Map all Maven Invoking Jobs Here else it will be created in freestyle template
        String[] MavenJobs = [Constants.JAVAMAVENTECH];
        basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName

        for (String tech in MavenJobs) {
            if (jsonData.code.technology.toString() == tech) {
                new BaseMavenJobCreator(
                        jobName: basepath + '/' + basepath + Constants.BUILDJOBNAMEPOSTFIX,
                        jobDescription: ''
                ).create(factory).with {

                    // Pre - build

                    environmentVariables {
                        propertiesFile('$IDP_WS/CustomJobParm.properties')
                        keepBuildVariables(true)
                    }


                    if (jsonData.code.buildScript && jsonData.code.buildScript.size() > 0 && jsonData.code.buildScript.getAt(0).scriptType && jsonData.code.buildScript.getAt(0).scriptType == Constants.PREBUILD)
                        RunScript.preBuild(delegate, jsonData.code.buildScript.getAt(0))
                    if (jsonData.code.buildScript && jsonData.code.buildScript.size() > 1 && jsonData.code.buildScript.getAt(1).scriptType && jsonData.code.buildScript.getAt(1).scriptType == Constants.PREBUILD)
                        RunScript.preBuild(delegate, jsonData.code.buildScript.getAt(1))

                    addProperties(delegate, jsonData)
                    addWrappers(delegate, jsonData)
                    if (jsonData.buildInfo.modules && jsonData.buildInfo.modules.size() > 0) {
                        switch (jsonData.code.technology.toString()) {
                            case Constants.JAVAMAVENTECH:
                                MavenBuild.mavenBuildJobCreation(delegate, jsonData, envVar, basepath)
                                break

                        }
                    }
                    // fortify in postbuild step   
                    if(jsonData.buildInfo.securityAnalysisTool != null && jsonData.buildInfo.securityAnalysisTool != '' ){
                        if(jsonData.buildInfo.securityAnalysisTool.equalsIgnoreCase("fortify")){
                            Fortify fortify = new Fortify()
                            fortify.add(delegate, jsonData)
                            def commandN = "ReportGenerator -format xml -f fortifyxmlreport.xml -source fortifyxmlreport.fpr"
                            ExecuteCmdPostBuild.invokeCmd(delegate, commandN, jsonData.basicInfo.buildServerOS)
                        }
                    }


                    NiaIntegStage.run(delegate, jsonData, Constants.BUILD)

                   if (jsonData.buildInfo && jsonData.buildInfo.artifactToStage && (jsonData.buildInfo.artifactToStage.artifactRepoName == 'jfrog' || jsonData.buildInfo.artifactToStage.artifactRepoName == 'nexus' ||  jsonData.buildInfo.artifactToStage.artifactRepoName == 'docker')
                    ) {
                        artifacttostage(factory, jsonData, envVar)
                    }
                    // Post - build
                    if (jsonData.code.buildScript && jsonData.code.buildScript.size() > 0 && jsonData.code.buildScript.getAt(0).scriptType && jsonData.code.buildScript.getAt(0).scriptType == Constants.POSTBUILD)
                        RunScript.postBuild(delegate, jsonData.code.buildScript.getAt(0))
                    if (jsonData.code.buildScript && jsonData.code.buildScript.size() > 1 && jsonData.code.buildScript.getAt(1).scriptType && jsonData.code.buildScript.getAt(1).scriptType == Constants.POSTBUILD)
                        RunScript.postBuild(delegate, jsonData.code.buildScript.getAt(1))

                    // Post Build Module -- Build Section
                    if (jsonData.buildInfo.postBuildScript && !jsonData.buildInfo.postBuildScript.equals('{}'))
                        RunScript.postBuildModule(delegate, jsonData.buildInfo.postBuildScript)
                }
                return
            }
        }
        if (jsonData.code.technology.equalsIgnoreCase('general')) {


            new BasePipelineCreator(
                    pipelineName: basepath + '/' + basepath + Constants.BUILDJOBNAMEPOSTFIX
            ).create(factory).with {
                configure { it / canRoam(false) }
                concurrentBuild(true)
                keepDependencies(false)
                CatalogBuild.createPipelineScript(basepath, jsonData, envVar)
                def jHome = envVar.JENKINS_HOME
                addProperties(delegate, jsonData)

                definition {
                    cps {

                        script(factory.readFileFromWorkspace(jHome + '/userContent/' + basepath + '_pipeline_script.groovy'))
                        //script(factory.readFileFromWorkspace('src\\main\\groovy\\pipeline_sequence'))
                        sandbox(true)
                    }

                }
            }
            if (jsonData.buildInfo && jsonData.buildInfo.artifactToStage && (jsonData.buildInfo.artifactToStage.artifactRepoName == 'jfrog' || jsonData.buildInfo.artifactToStage.artifactRepoName == 'nexus' || jsonData.buildInfo.artifactToStage.artifactRepoName == 'docker')
            ) {
                new BaseJobCreator(
                        jobName: basepath + '/' + basepath + '_ArtifactUpload',
                        jobDescription: ''

                ).create(factory).with {
                    configure { it / canRoam(false) }
                    concurrentBuild(true)

                    if (jsonData.buildInfo && jsonData.buildInfo.buildtool != '' && !'SapNonCharm'.equalsIgnoreCase(jsonData.buildInfo.buildtool)) {
                        environmentVariables {
                            propertiesFile('$IDP_WS/CustomJobParm.properties')
                            keepBuildVariables(true)
                        }
                    }
                    keepDependencies(false)
                    String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
                    if (customWS) customWorkspace('$IDP_WS')
                    addProperties(delegate, jsonData)
                    addWrappers(delegate, jsonData)
                    if (jsonData.buildInfo.artifactToStage.artifactRepoName == "nexus") {
                        NexusUpload.freeStyleNexus(delegate, jsonData, envVar,'$JOB_BUILD_ID')
                    } else if (jsonData.buildInfo.artifactToStage.artifactRepoName == "jfrog") {
                        ArtifactoryUpload.freeStyleArtifactory(delegate, jsonData, envVar,'$JOB_BUILD_ID')
                    }
                    if (jsonData.buildInfo.artifactToStage.artifactRepoName == "docker") {
                        addWrapperForDocker(delegate, jsonData)
                        if(jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) {
                            DockerUpload.addStepsDRWindows(delegate, jsonData, envVar)
                        }else{
                            DockerUpload.addStepsDRLinux(delegate, jsonData, envVar)
                        }
                    }
                }
            }
        } else {
            if (!(jsonData.code.technology.equalsIgnoreCase('dbDeployDelphix'))) {

                new BaseJobCreator(
                        jobName: basepath + '/' + basepath + Constants.BUILDJOBNAMEPOSTFIX,
                        jobDescription: ''

                ).create(factory).with {

                    // Pre - build
                    if (jsonData.code.buildScript && jsonData.code.buildScript.size() > 0 && jsonData.code.buildScript.getAt(0).scriptType && jsonData.code.buildScript.getAt(0).scriptType == Constants.PREBUILD)
                        RunScript.inBuild(delegate, jsonData.code.buildScript.getAt(0))
                    if (jsonData.code.buildScript && jsonData.code.buildScript.size() > 1 && jsonData.code.buildScript.getAt(1).scriptType && jsonData.code.buildScript.getAt(1).scriptType == Constants.PREBUILD)
                        RunScript.inBuild(delegate, jsonData.code.buildScript.getAt(1))
                    if (jsonData.buildInfo && jsonData.buildInfo.buildtool != '' && !'SapNonCharm'.equalsIgnoreCase(jsonData.buildInfo.buildtool)) {
                        environmentVariables {
                            propertiesFile('$IDP_WS/CustomJobParm.properties')
                            keepBuildVariables(true)
                        }
                    }
                    addProperties(delegate, jsonData)
                    addWrappers(delegate, jsonData)
                    if (jsonData.buildInfo.modules && jsonData.buildInfo.modules.size() > 0) {
                        switch (jsonData.code.technology.toString()) {
                            case Constants.JAVAANTTECH:
                                AntBuild.antBuildJobCreation(delegate, jsonData, envVar, basepath)
                                break
                            case Constants.CSDOTNETTECH:
                            case Constants.VBDOTNETTECH:
                                DotnetBuild.dotnetBuildJobCreation(delegate, jsonData, envVar, basepath)
                                break

                            case Constants.NODEJSTECH:
                                NodeJSBuild.nodejsBuildJobCreation(delegate, jsonData, envVar, basepath)
                                break

                            case Constants.GODATATECH:
                                GoBuild.goBuildJobCreation(delegate, jsonData, envVar, basepath)
                                break

                            case Constants.ANGULARJSTECH:
                                AngularJSBuild.angularjsBuildJobCreation(delegate, jsonData, basepath)
                                break

                            case Constants.PYTHON:
                                PythonBuild.pythonBuildJobCreation(delegate, jsonData, envVar, basepath)
                                break

                            case Constants.JAVA_GRADLE_TECH:
                                JavaGradleBuild.javaGradleBuildJobCreation(delegate, jsonData, basepath)
                                break

                            default:
                                break
                        }
                    }
                    // fortify in postbuild step
                    if(jsonData.buildInfo.securityAnalysisTool != null && jsonData.buildInfo.securityAnalysisTool != '' ){
                        if(jsonData.buildInfo.securityAnalysisTool.equalsIgnoreCase("fortify")){
                            Fortify fortify = new Fortify()
                            fortify.add(delegate, jsonData)
                            def commandN = "ReportGenerator -format xml -f fortifyxmlreport.xml -source fortifyxmlreport.fpr"
                            ExecuteCmdPostBuild.invokeCmd(delegate, commandN, jsonData.basicInfo.buildServerOS)
                        }
                    }

                    NiaIntegStage.run(delegate, jsonData, Constants.BUILD)

                    // Post - build
                    if (jsonData.code.buildScript && jsonData.code.buildScript.size() > 0 && jsonData.code.buildScript.getAt(0).scriptType && jsonData.code.buildScript.getAt(0).scriptType == Constants.POSTBUILD)
                        RunScript.inBuild(delegate, jsonData.code.buildScript.getAt(0))
                    if (jsonData.code.buildScript && jsonData.code.buildScript.size() > 1 && jsonData.code.buildScript.getAt(1).scriptType && jsonData.code.buildScript.getAt(1).scriptType == Constants.POSTBUILD)
                        RunScript.inBuild(delegate, jsonData.code.buildScript.getAt(1))
                    // Post Build Module -- Build Section
                    if (jsonData.buildInfo.postBuildScript && !jsonData.buildInfo.postBuildScript.equals('{}'))
                        RunScript.inBuildModule(delegate, jsonData.buildInfo.postBuildScript)
                }
                if (jsonData.buildInfo && jsonData.buildInfo.artifactToStage && (jsonData.buildInfo.artifactToStage.artifactRepoName == 'jfrog' || jsonData.buildInfo.artifactToStage.artifactRepoName == 'nexus' || jsonData.buildInfo.artifactToStage.artifactRepoName == 'docker')
                ) {
                    new BaseJobCreator(
                            jobName: basepath + '/' + basepath + '_ArtifactUpload',
                            jobDescription: ''

                    ).create(factory).with {
                        configure { it / canRoam(false) }
                        concurrentBuild(true)

                        if (jsonData.buildInfo && jsonData.buildInfo.buildtool != '') {
                            environmentVariables {
                                propertiesFile('$IDP_WS/CustomJobParm.properties')
                                keepBuildVariables(true)
                            }
                        }
                        keepDependencies(false)
                        String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
                        if (customWS) customWorkspace('$IDP_WS')

                        if (jsonData.buildInfo && jsonData.buildInfo.buildtool != '') {
                            environmentVariables {
                                propertiesFile('$IDP_WS/CustomJobParm.properties')
                                keepBuildVariables(true)
                            }
                        }
                        addProperties(delegate, jsonData)
                        addWrappers(delegate, jsonData)
                        if (jsonData.buildInfo.artifactToStage.artifactRepoName == "nexus") {
                            NexusUpload.freeStyleNexus(delegate, jsonData, envVar, '$JOB_BUILD_ID')
                        } else if (jsonData.buildInfo.artifactToStage.artifactRepoName == "jfrog") {
                            ArtifactoryUpload.freeStyleArtifactory(delegate, jsonData, envVar, '$JOB_BUILD_ID')
                        }else if(jsonData.buildInfo.artifactToStage.artifactRepoName == "docker") {

                            addWrapperForDocker(delegate,jsonData)
                            if (jsonData.buildInfo.artifactToStage.artifactRepo.dockerFilePathDR) { 
                                if(jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) {
                                    DockerUpload.addStepsDRWindows(delegate, jsonData, envVar)
                                }else{
                                    DockerUpload.addStepsDRLinux(delegate, jsonData, envVar)
                                }
                            }

                        }

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
                buildname1.setTemplate('${BUILD_LABEL}' + '_' + '${BUILD_NUMBER}')
                buildname1.add(delegate, jsonData)
				BuildEnv env = new BuildEnv();
				env.add(delegate, jsonData);
            }
        }
    }

    /*
     * This method makes the name of the artifact upload job
     */

    void artifacttostage(factory, jsonData, envVar) {
        new BaseJobCreator(
                jobName: basepath + '/' + basepath + '_ArtifactUpload',
                jobDescription: ''
        ).create(factory).with {
            configure { it / canRoam(false) }
            concurrentBuild(true)


            environmentVariables {
                propertiesFile('$IDP_WS/CustomJobParm.properties')
                keepBuildVariables(true)
            }

            keepDependencies(false)
            String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
            if (customWS) customWorkspace('$IDP_WS')
            addProperties(delegate, jsonData)
            addWrappers(delegate, jsonData)
            if (jsonData.buildInfo.artifactToStage.artifactRepoName == "nexus") {
                NexusUpload.freeStyleNexus(delegate, jsonData, envVar,'$JOB_BUILD_ID')
            } else if (jsonData.buildInfo.artifactToStage.artifactRepoName == "jfrog") {
                ArtifactoryUpload.freeStyleArtifactory(delegate, jsonData, envVar,'$JOB_BUILD_ID')
            }else if(jsonData.buildInfo.artifactToStage.artifactRepoName == "docker") {
                /* if (jsonData.buildStr.dockerFilePath)
                     DockerUpload.addSteps(delegate, jsonData, envVar)*/
                addWrapperForDocker(delegate,jsonData)
                if (jsonData.buildInfo.artifactToStage.artifactRepo.dockerFilePathDR) {
                    if(jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) {
                        DockerUpload.addStepsDRWindows(delegate, jsonData, envVar)
                    }else{
                        DockerUpload.addStepsDRLinux(delegate, jsonData, envVar)
                    }
                }

            }
            
        }
    }
    void addWrapperForDocker(context, jsonData){
        def password = jsonData.buildInfo.artifactToStage.artifactRepo.passwordDR;
        context.with{
            wrappers{
                BuildEnvIIS env = new BuildEnvIIS();
                env.setName("DR_PASSWORD");
                env.setPswd(password);
                env.add(delegate, jsonData);
           }
        }
    }

    /*
     * this method is used to add string or password parameters in jenkins job
     */

    void addProperties(context, jsonData) {

        context.with {

            properties {

                PropertiesAdder.addBuildDiscarder(delegate)
            }

            configure {

                it / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions' {

                    PropertiesAdder.addStringParam(delegate, 'IDP_WS', 'NA', 'CUSTOM Workspace')
                    PropertiesAdder.addStringParam(delegate, 'MODULE_LIST', 'NA', '')
                    PropertiesAdder.addStringParam(delegate, 'RELEASE_IDENTIFIER', 'NA', '')
                    //Adding pipeline build id
                    PropertiesAdder.addStringParam(delegate, 'PIPELINE_BUILD_ID', 'NA', '')
                    //Adding job build  id
                    PropertiesAdder.addStringParam(delegate, 'JOB_BUILD_ID', 'NA', '')
                    PropertiesAdder.addNodeParam(delegate, 'SLAVE_NODE', ['ldaproleapp_Jmvn1_14Mr1_Slave'], '')
                    PropertiesAdder.addStringParam(delegate, 'BUILD_LABEL', 'NA', '')
                    PropertiesAdder.addStringParam(delegate, 'ARTIFACT_NAME', 'NA', '')
                    PropertiesAdder.addStringParam(delegate, 'SCM_BRANCH', 'default', 'SCm branch selected while trigger')
                    if (jsonData.buildInfo.artifactToStage && jsonData.buildInfo.artifactToStage.artifact) {
                        PropertiesAdder.addStringParam(delegate, 'ARTIFACT_VERSION', 'NA', 'This parameter tells which artifacts has to be downloaded')
                        PropertiesAdder.addStringParam(delegate, 'ARTIFACT_ID', 'NA', 'This will tell in which folder it has to be downloaded')
                        PropertiesAdder.addStringParam(delegate, 'RELEASE_IDENTIFIER', 'NA', 'This will tell in which folder it has to be downloaded')
                        PropertiesAdder.addStringParam(delegate, 'SCM_BRANCH', 'default', 'SCm branch selected while trigger')
                    }
                    if(jsonData.code != null && jsonData.code != '' && jsonData.code.jobParam != null && jsonData.code.jobParam != ''){
                       for(int i =0 ; i < jsonData.code.jobParam.size(); i++){
                           PropertiesAdder.addStringParam(delegate,jsonData.code.jobParam[i].jobParamName,jsonData.code.jobParam[i].jobParamValue,'');
                           
                       }
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
