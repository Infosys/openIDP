/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.build

import com.infy.idp.creators.*
import com.infy.idp.customtools.*
import com.infy.idp.plugins.buildsteps.*
import com.infy.idp.plugins.publishers.*
import com.infy.idp.plugins.wrappers.*
import com.infy.idp.tools.*
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*

class PythonBuild {

    static String basepath

    public static void pythonBuildJobCreation(context, jsonData, envVar, basepathArg) {

        basepath = basepathArg

        context.with {
            //Standard IDP Settings
            configure { it / canRoam(false) }
            concurrentBuild(true)

            //Configurable Settings of Job
            addWrappers(delegate, jsonData)
            addSteps(delegate, jsonData, envVar)

            ///////////////////////////////////////////////
            // Need to verify publisher is req or not    //
            ///////////////////////////////////////////////
            //      \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            //		 \\  	addPublishers(delegate, jsonData)     \\
            //        \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            //Optional settings of job
            String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
            if (customWS) customWorkspace('$IDP_WS')
        }
    }

    /**
     * add Builder steps
     * @param context the context
     * @param jsonData the Json data
     * @param envVar the environment variable
     */
    private static void addSteps(context, jsonData, envVar) {

        context.with {

            steps {
                prepareZippingAntXml(envVar)
                Ant.invokeAnt(delegate, ['zipFile'], basepath + '_zipping_build.xml')

                //Get git projName
                String gitRepoName = ExtractRepoName(jsonData.code)

                //Sonar runner
                if (jsonData.buildInfo.modules.getAt(0).codeAnalysis && jsonData.buildInfo.modules.getAt(0).codeAnalysis.contains(Constants.SONAR)) {
                    SonarRunner sonar = new SonarRunner()
                   // sonar.setProject(gitRepoName + '/' + jsonData.buildInfo.modules.getAt(0).relativePath + '/sonar-project.properties')
                    sonar.setProperties('sonar.projectKey=' + jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName +
                            '\nsonar.projectName=' + jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName +
                            '\nsonar.projectVersion=$RELEASE_IDENTIFIER' + '\nsonar.sources=.')
                    sonar.add(delegate, jsonData)
                }
                SshBuilder sshBuilder = new SshBuilder();
                //Create ssh for running UT
                String commandUT = '';
                String remoteDir = ''
                if (jsonData.buildInfo.modules.getAt(0).unitTestDir) {

                    def serverName = jsonData.buildInfo.modules.getAt(0).hostName + '_' + jsonData.buildInfo.modules.getAt(0).userName
                    //Constructing remoteDir value
                    if (jsonData.buildInfo.modules.getAt(0).remoteDir) {
                        remoteDir += jsonData.buildInfo.modules.getAt(0).remoteDir + '/'
                    }
                    remoteDir += basepath

                    //Create command
                    String commandStr = 'cd ' + remoteDir + '-$PIPELINE_BUILD_ID/Uploaded_PyScripts\n';
                    commandStr += 'unzip -o python_scripts.zip';

                    String key = jsonData.buildInfo.modules.getAt(0).privateKey
                    //transferring files and unzipping those files
                    sshBuilder.invokeSshBuilder(delegate, serverName, jsonData.buildInfo.modules.getAt(0).userName, '', 'Uploaded_PyScripts/',
                            remoteDir + '-$PIPELINE_BUILD_ID/', commandStr, '', key)

                    //Creating command for Unit testing
                    commandUT += 'cd ' + remoteDir + '-$PIPELINE_BUILD_ID/Uploaded_PyScripts/';
                    commandUT += gitRepoName + '/' + jsonData.buildInfo.modules.getAt(0).relativePath +
                            '/' + jsonData.buildInfo.modules.getAt(0).unitTestDir + '\n';
                    commandUT += 'mkdir ' + jsonData.buildInfo.modules.getAt(0).report + '\nfor i in *; do\n cd $i\n for f in *.py; do  spark-submit "$f"; done\n cd ../\n done';
                    //Executing unit testing
                    sshBuilder.invokeSshBuilder(delegate, serverName, jsonData.buildInfo.modules.getAt(0).userName, '', '',
                            remoteDir + '-$PIPELINE_BUILD_ID/', commandUT, '', key)
                }

                //Added commands to copy from hadoop to slave machine
                if (jsonData.buildInfo.modules.getAt(0).unitTestDir) {
                    //Fetching ppk name form complete ppk path
                    String ppkKey = jsonData.buildInfo.modules.getAt(0).privateKey;
                    def ppkKeyList = ppkKey.split("/")
//					int arrSize = ppkKeyList.size()
                    //ppkKeys[-1] -> last build
                    if (ppkKeyList.size() > 0 &&
                            (ppkKeyList[-1].endsWith(".ppk") || ppkKeyList[-1].endsWith(".PPK"))) {
                        ppkKey = ppkKeyList[-1]
                    }

                    String fieName = ppkKey.split("\\.")[0]
                    def copyCommand = '';
                    if (jsonData.basicInfo.buildServerOS.equalsIgnoreCase("windows")) {
                        copyCommand += 'cd ../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/check_out_from_linux_to_windows/check_out_from_linux_to_windows\n';
                        copyCommand += 'pscp -r -i %IDP_WS%/' + gitRepoName + '/' + jsonData.buildInfo.modules.getAt(0).relativePath + '/' + ppkKey + ' ' +
                                jsonData.buildInfo.modules.getAt(0).userName + '@' + jsonData.buildInfo.modules.getAt(0).hostName + ':' +
                                remoteDir + '-%PIPELINE_BUILD_ID%/' + '/Uploaded_PyScripts/' + gitRepoName + '/' + jsonData.buildInfo.modules.getAt(0).relativePath +
                                '/' + jsonData.buildInfo.modules.getAt(0).unitTestDir + '/' + jsonData.buildInfo.modules.getAt(0).report +
                                ' ./../../../../workspace/' + basepath

                    } else {
                        copyCommand += 'scp  -i ' + fieName + '.pem -r ' +
                                jsonData.buildInfo.modules.getAt(0).userName + '@' + jsonData.buildInfo.modules.getAt(0).hostName + ':' +
                                remoteDir + '-$PIPELINE_BUILD_ID/' + 'Uploaded_PyScripts/' + gitRepoName + '/' + jsonData.buildInfo.modules.getAt(0).relativePath +
                                '/' + jsonData.buildInfo.modules.getAt(0).unitTestDir + '/' + jsonData.buildInfo.modules.getAt(0).report +
                                ' .'
                    }



                    ExecuteCmd.invokeCmd(delegate, copyCommand, jsonData.basicInfo.buildServerOS)
                }
            }
        }
    }

    public static void addWrappers(context, jsonData) {

        def toolList = getListOfTools(jsonData)
        def copyPattern = filesToCopy(jsonData.buildInfo.modules)

        context.with {

            wrappers {

                (new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(delegate, toolList)
               // if (copyPattern != '')
                    CopyToSlave.invokeCopyToSlave(delegate, copyPattern)
            }
        }
    }

    private static String filesToCopy(modulesArr) {
        String copyPattern = ''
       // if (modulesArr.getAt(0).unitTestDir) {
            copyPattern += (basepath + '_zipping_build.xml')
            //return copyPattern
     //   }
        return copyPattern
    }

    /**
     * Get List of custom tool used
     * @param modulesArr
     * @return
     */
    private static String getListOfTools(jsonData) {
        String toolList = 'ANT_HOME'

        if (jsonData.buildInfo.modules.getAt(0).unitTestDir &&
                jsonData.basicInfo.buildServerOS.equalsIgnoreCase("windows")) {
            toolList += ',CHECK_OUT_FROM_LINUX_TO_WINDOWS_HOME'
        }
        return toolList;
    }

    /**
     * Get repo name from complete git url
     */
    private static String ExtractRepoName(code) {
        //Add code to ger GIT repo name
        def scmSection = code.scm.getAt(0);
        String repoName
        def repoNameList = scmSection.url.split("/")
        //						int arrSize = repoNameList.size()
        if (repoNameList.size() > 0 && repoNameList[-1].endsWith(".git")) {
            repoName = repoNameList[-1].split("\\.")[0]
        }

    }

    //	private static String prepareZippingAntXml(moduleName,  relativePath, envVar) {
    private static String prepareZippingAntXml(envVar) {


        String fileContent = ReadFile.run('/ant_templates/python_zip_ant.xml')
        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_zipping_build.xml', fileContent)
    }
}

