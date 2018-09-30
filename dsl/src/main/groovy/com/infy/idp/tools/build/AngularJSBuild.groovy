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
import com.infy.idp.plugins.buildsteps.SonarRunner
import com.infy.idp.plugins.publishers.*
import com.infy.idp.plugins.wrappers.*
import com.infy.idp.tools.*
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*

/**
 *
 * This class has the method to create the build job based for the AngularJS technology
 *
 */

class AngularJSBuild {

    static String basepath

    /*
     * This method is used to add the properties for AngularJS
     */

    public static void addProperties(context, jsonData) {

        if (jsonData.buildInfo.modules.getAt(0).npmProxyPassword == null || jsonData.buildInfo.modules.getAt(0).npmProxyPassword == '')
            return

        def proxyPwd = jsonData.buildInfo.modules.getAt(0).npmProxyPassword

        context.with {

            configure {

                it / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions' << {

                    PropertiesAdder.addPasswordParam(delegate, 'PROXY_PWD', URLEncoder.encode(proxyPwd, "UTF-8"), 'Proxy Password')

                }
            }


        }
    }
    /*
     * This method is used to add publishsers required for AngularJS
     */

    public static void addPublishers(context, jsonData) {


        Boolean isUnitTestSelected = false;
        Boolean isCCSelected = false
        String jsonPathvalue = ''
        def modulesArr = jsonData.buildInfo.modules
        for (int i = 0; i < modulesArr.size(); i++) {

            if (modulesArr.getAt(i).unitTesting == 'on' && modulesArr.getAt(i).unitTestTool.contains('karma/jasmine')) {
                isUnitTestSelected = true;
                jsonPathvalue = modulesArr.getAt(i).jsonPath
            }

            if (modulesArr.getAt(i).codeCoverage == 'on' && modulesArr.getAt(i).codeCoverageTool.contains('istanbul')) {
                isCCSelected = true;
            }
        }
        context.with {

            publishers {


                HtmlPublisher htmlPublisher = new HtmlPublisher()

                //Call for checkstyle analysis
                if (isUnitTestSelected) {
                    htmlPublisher.updateReportDir(jsonPathvalue + '/tests')
                    htmlPublisher.updateReportFile('units.html')
                    htmlPublisher.updateReportName('Test Report')

                }
                if (isCCSelected) {
                    htmlPublisher.updateReportDir(jsonPathvalue + '/coverage')
                    htmlPublisher.updateReportFile('index.html')
                    htmlPublisher.updateReportName('Coverage Report')

                }
                if (isUnitTestSelected) {
                    htmlPublisher.add(delegate, jsonData)

                }

            }
        }
    }

    /*
     * This method is used to configure prebuild and postbuild option in jenkins job
     */

    private static void addSteps(context, jsonData) {


        String command = 'cd ';
        def modulesArr = jsonData.buildInfo.modules
        context.with {
            steps {
                for (int i = 0; i < modulesArr.size(); i++) {
                    SonarRunner sonarRunner = new SonarRunner()
                    String proxy = ''
                    String unsetProxy = ''
                    def relativePath = modulesArr.getAt(i).relativePath.replace('\\', '/')
                    def moduleDir
                    if (relativePath.contains('/')) {
                        moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
                    } else {
                        moduleDir = relativePath
                    }

                    if (modulesArr.getAt(i).npmProxy != null) {

                        def proxyUrl = modulesArr.getAt(i).npmProxy

                        if (modulesArr.getAt(i).npmProxyPassword && modulesArr.getAt(i).npmProxyUserName && modulesArr.getAt(i).npmProxyPassword != '' && modulesArr.getAt(i).npmProxyUserName != '') {

                            def proto = proxyUrl.substring(0, proxyUrl.indexOf('://') + '://'.length())
                            def urlWoProto = proxyUrl.substring(proxyUrl.indexOf('://') + '://'.length())
                            def encodedCreds = URLEncoder.encode(modulesArr.getAt(i).npmProxyUserName, "UTF-8") + ':' + ((jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) ? '%PROXY_PWD%' : '$PROXY_PWD') + '@'
                            proxyUrl = proto + encodedCreds + urlWoProto
                        }

                        if (jsonData.basicInfo.buildServerOS == Constants.WINDOWSOS) {
                            proxy = 'set http_proxy=' + proxyUrl + '\n' + 'set https_proxy=' + proxyUrl + '\n'
                        } else {
                            proxy = 'export http_proxy="' + proxyUrl + '"' + '\n' + 'export https_proxy="' + proxyUrl + '"' + '\n'
                            unsetProxy = '\n' + 'unset http_proxy' + '\n' + 'unset https_proxy'
                        }
                    }
                    String npmInstallCommands = '';
                    if (!(modulesArr.getAt(i).jsonPath == null)) {
                        npmInstallCommands = proxy + command + modulesArr.getAt(i).jsonPath + '\n' + 'npm install' + unsetProxy;
                        ExecuteCmd.invokeCmd(delegate, npmInstallCommands, jsonData.basicInfo.buildServerOS)
                    }
                    if (modulesArr.getAt(i).unitTesting == 'on' && modulesArr.getAt(i).unitTestTool.contains('karma/jasmine')) {
                        String npmCommands = ''
                        npmCommands += proxy + command + modulesArr.getAt(i).jsonPath + '\n' + 'npm install karma-jasmine karma-phantomjs-launcher karma-jasmine-html-reporter karma-coverage-istanbul-reporter karma-htmlfile-reporter karma-junit-reporter' + unsetProxy
                        ExecuteCmd.invokeCmd(delegate, npmCommands, jsonData.basicInfo.buildServerOS)
                    }

                    String buildCommand = '';
                    if (!(modulesArr.getAt(i).angularBuildCommand == null) && !(modulesArr.getAt(i).angularBuildCommand == '')) {

                        buildCommand = command + modulesArr.getAt(i).relativePath + '\n' + modulesArr.getAt(i).angularBuildCommand
                        ExecuteCmd.invokeCmd(delegate, buildCommand, jsonData.basicInfo.buildServerOS)
                    }
                    String testCommand = '';
                    if (modulesArr.getAt(i).unitTesting == 'on' && modulesArr.getAt(i).unitTestTool.contains('karma/jasmine') && modulesArr.getAt(i).codeCoverage == 'on' && modulesArr.getAt(i).codeCoverageTool.contains('istanbul')) {
                        testCommand = command + modulesArr.getAt(i).unitTestProjectName + '\n' + 'ng test --single-run --code-coverage'
                        if (modulesArr.getAt(i).allUnitTestPass == 'off') {
                            testCommand += ' || exit 0'
                        }

                        ExecuteCmd.invokeCmd(delegate, testCommand, jsonData.basicInfo.buildServerOS)

                    }
                    if (modulesArr.getAt(i).unitTesting == 'on' && modulesArr.getAt(i).unitTestTool.contains('karma/jasmine') && modulesArr.getAt(i).codeCoverage != 'on') {
                        testCommand = command + modulesArr.getAt(i).unitTestProjectName + '\n' + 'ng test --single-run'
                        if (modulesArr.getAt(i).allUnitTestPass == 'off') {
                            testCommand += ' || exit 0'
                        }
                        ExecuteCmd.invokeCmd(delegate, testCommand, jsonData.basicInfo.buildServerOS)
                    }
                    if (modulesArr.getAt(i).codeAnalysis.contains('tsLint') && modulesArr.getAt(i).noViolations == 'off') {
                        String codeAnalysisCommand = command + modulesArr.getAt(i).jsonPath + '\n' + 'ng lint > result.txt || exit 0'
                        ExecuteCmd.invokeCmd(delegate, codeAnalysisCommand, jsonData.basicInfo.buildServerOS)
                    }
                    if (modulesArr.getAt(i).codeAnalysis.contains('tsLint') && modulesArr.getAt(i).noViolations == 'on') {
                        String codeAnalysisCommand = command + modulesArr.getAt(i).jsonPath + '\n' + 'ng lint > result.txt'
                        ExecuteCmd.invokeCmd(delegate, codeAnalysisCommand, jsonData.basicInfo.buildServerOS)
                    }
                    if (modulesArr.getAt(i).codeAnalysis.contains('sonar')) {
                        def moduleName = modulesArr.getAt(i).moduleName
                        sonarRunner.setProject(moduleDir + '/sonar-project.properties')
                        sonarRunner.setProperties('sonar.projectKey=' + jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName + '\nsonar.projectName=' + moduleName + '\nsonar.projectVersion=$RELEASE_IDENTIFIER' + '\nsonar.sources=' + moduleDir)
                        sonarRunner.add(delegate, jsonData)
                    }
                }
            }
        }
    }

    /*
     * this method is the entry point for AngularJS build job Creation
     */

    public static void angularjsBuildJobCreation(context, jsonData, basepathArg) {
        basepath = basepathArg
        context.with {
            //Standard IDP Settings
            configure { it / canRoam(false) }
            concurrentBuild(true)

            addProperties(delegate, jsonData)
            addSteps(delegate, jsonData)
            addPublishers(delegate, jsonData)
            //addWrappers(delegate,jsonData)

            //Optional settings of job
            String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
            if (customWS) customWorkspace('$IDP_WS')
        }
    }


}	
	


