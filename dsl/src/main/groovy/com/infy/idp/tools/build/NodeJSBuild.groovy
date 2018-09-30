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
//import com.infy.idp.plugins.buildsteps.Checkmarx
import com.infy.idp.plugins.buildsteps.SonarRunner
import com.infy.idp.plugins.publishers.*
import com.infy.idp.plugins.publishers.ArchiveArtifacts
import com.infy.idp.plugins.publishers.CheckStylePublisher
import com.infy.idp.plugins.publishers.CoberturaPublisher
import com.infy.idp.plugins.publishers.XUnitPublisher
import com.infy.idp.plugins.wrappers.*
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.tools.*
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*

/**
 *
 * This class has the method to create the build job for the NodeJS technology
 *
 */

class NodeJSBuild {

    static String basepath

    /*
     * This method is used to add the required publishers for DotNet technology
     */

    public static void addPublishers(context, jsonData) {

        Boolean isCheckStyleSelected = false;
        Boolean isUTSelected = false;
        Boolean isCCSelected = false;
        Boolean isCheckmarxSelected = false
        def modulesArr = jsonData.buildInfo.modules
        for (int i = 0; i < modulesArr.size(); i++) {

            if (modulesArr.getAt(i).team) {
                isCheckmarxSelected = true;
            }

            if (modulesArr.getAt(i).codeAnalysis.contains('checkStyle')) {
                isCheckStyleSelected = true;
            }

            if (modulesArr.getAt(i).unitTesting == 'on') {
                isUTSelected = true;
                if (modulesArr.getAt(i).codeCoverage == 'on') {
                    isCCSelected = true;
                }

            }
        }
        context.with {

            publishers {
                //Call for checkstyle analysis
                if (isCheckStyleSelected) {
                    CheckStylePublisher checkStylePublisher = new CheckStylePublisher()
//					checkStylePublisher.generatePattern(jsonData)
                    checkStylePublisher.updatePattern('**/report-jshint-checkstyle.xml')
                    checkStylePublisher.add(delegate, jsonData)
                }

                //Xunit publisher part
                if (isUTSelected) {
                    XUnitPublisher xUnitPublisher = new XUnitPublisher();
                    xUnitPublisher.setPattern('xunit.xml')
                    xUnitPublisher.add(delegate, jsonData)
                }

                //Call for coverage
                if (isCCSelected) {
                    CoberturaPublisher coberturaPublisher = new CoberturaPublisher()
                    coberturaPublisher.updatePattern('coverage/*.xml')
                    coberturaPublisher.add(delegate, jsonData)
                }

                //Call for Archive Atrifatcs
                ArchiveArtifacts archiveArtifacts = new ArchiveArtifacts()
                String pattern = generatePattern(jsonData)

                //Archive Artifacts for Checkmarx
                if (isCheckmarxSelected) {

                    pattern += ',**/Checkmarx/Reports/**'
                }

                if (pattern != '') {
                    archiveArtifacts.setPattern(pattern)
                    archiveArtifacts.add(delegate, jsonData)
                }

            }
        }
    }

    /*
     * This method is used to generate patterns for tet reports
     */

    private static String generatePattern(jsonData) {

        String pattern = ''
        Boolean isCheckStyleSelected = false;
        Boolean isUTSelected = false;
        Boolean isCCSelected = false;
        def modulesArr = jsonData.buildInfo.modules
        for (int i = 0; i < modulesArr.size(); i++) {

            if (modulesArr.getAt(i).codeAnalysis.contains('checkStyle')) {
                isCheckStyleSelected = true;
            }

            if (modulesArr.getAt(i).unitTesting == 'on') {
                isUTSelected = true;
                if (modulesArr.getAt(i).codeCoverage == 'on') {
                    isCCSelected = true;
                }

            }
        }
        if (isCheckStyleSelected)
            pattern += ('**/jshint-report/*.xml,**/jshint-report/*.html,')

        if (isCCSelected)
            pattern += ('coverage/*.xml,')

        if (isUTSelected)
            pattern += ('xunit.xml,')

        return pattern
    }

    /*
    * This method is used to add conditional build steps
    */

    private static void addSteps(context, jsonData, envVar) {

        def modulesArr = jsonData.buildInfo.modules
        def isLinux = false
        if (jsonData.basicInfo.buildServerOS.equalsIgnoreCase('linux')) {
            isLinux = true
        }
        context.with {
            steps {
                for (int i = 0; i < modulesArr.size(); i++) {

                    //Rename file to Gruntfile.js
                    if (modulesArr.getAt(i).codeAnalysis.contains('checkStyle') ||
                            modulesArr.getAt(i).codeFormatting == 'on' ||
                            modulesArr.getAt(i).unitTesting == 'on') {
                        Rename.run(delegate, jsonData.basicInfo.buildServerOS, basepath + '_grunt.js Gruntfile.js ')
                    }
                    //Copy file from root to directory which contain package.json
//					Copy.run(delegate,jsonData.basicInfo.buildServerOS, 'Gruntfile.js '+ modulesArr.getAt(i).jsonPath )

                    String npmInstallCommands = ''
                    String npmExecuteGrunt = ''
                    String npmExecuteJsHint = ''
                    String npmExecuteMocha = ''

                    if (isLinux) {
                        npmInstallCommands += Constants.SUDO + ' '
                        npmExecuteGrunt += Constants.SUDO + ' '
                        npmExecuteJsHint += Constants.SUDO + ' '
                        npmExecuteMocha += Constants.SUDO + ' '
                    }
                    npmInstallCommands += 'npm install '
                    npmExecuteGrunt += 'grunt '
                    npmExecuteJsHint += 'grunt --force '
                    npmExecuteMocha += ''


                    if (modulesArr.getAt(i).codeFormatting == 'on') {
                        npmInstallCommands += 'grunt-contrib-uglify '
                        npmExecuteGrunt += 'uglify '
                    }

                    if (modulesArr.getAt(i).codeAnalysis.contains('checkStyle')) {
                        npmInstallCommands += 'jshint grunt-contrib-jshint  jshint-jenkins-checkstyle-reporter jshint-html-reporter '
                        npmExecuteJsHint += 'jshint '
                    }

                    String pipelineName = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
                    String repoName

                    if (modulesArr.getAt(i).codeAnalysis.contains('sonar')) {
                        SonarRunner sonarRunner = new SonarRunner();
                        //get repo name
                        def scmSection = jsonData.code.scm.getAt(0);

                        def repoNameList = scmSection.url.split("/")
//						int arrSize = repoNameList.size()
                        if (repoNameList.size() > 0 && repoNameList[-1].endsWith(".git")) {
                            repoName = repoNameList[-1].split("\\.")[0]
                        }

                        //Create folders to exclude
                        String[] splitWithColen = []
                        if (modulesArr.getAt(i).excludeFolders)
                            splitWithColen = modulesArr.getAt(i).excludeFolders.split(",");
                        String execludeFolderStr = ''
                        for (int index = 0; index < splitWithColen.size(); index++) {
                            if (index != 0)
                                execludeFolderStr += ','

                            execludeFolderStr += "**/" + splitWithColen.getAt(index).trim() + "/**/*.*"
                        }

                        String propertyStr = 'sonar.projectKey=' + jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName + '\nsonar.projectName=' + pipelineName + '\nsonar.projectVersion=$RELEASE_IDENTIFIER' + '\nsonar.sources=.'
                        propertyStr += '\nsonar.exclusions=' + execludeFolderStr
                        sonarRunner.setProperties(propertyStr)
                        sonarRunner.add(delegate, jsonData)
                    }

                    //Checking for security analysis
                    /*if (modulesArr.getAt(i).team) {

                        Checkmarx checkmarx = new Checkmarx();
                        checkmarx.setProjectKey(jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName)
                        checkmarx.setUser(modulesArr.getAt(i).userName)
                        checkmarx.setPass(modulesArr.getAt(i).password)
                        checkmarx.setTeamName(modulesArr.getAt(i).team)
                        checkmarx.setScanPreset(modulesArr.getAt(i).preset)
                        checkmarx.setDirectory(modulesArr.getAt(i).exclude)
                        checkmarx.setIncremental(modulesArr.getAt(i).incrementalScan)
                        //modulesArr.getAt(i).interval empty if not selected
                        if (modulesArr.getAt(i).interval) {
                            checkmarx.setScaneSchedule(true)
                            checkmarx.setScanCycle(modulesArr.getAt(i).interval.toInteger())
                        } else {
                            checkmarx.setScaneSchedule(false)
                            checkmarx.setScanCycle("10".toInteger())
                        }
                        checkmarx.add(delegate, jsonData)
                    }*/


                    if (modulesArr.getAt(i).unitTesting == 'on') {
                        npmInstallCommands += ' grunt-node-mocha mocha xunit-file '
                        npmExecuteMocha += ' mocha ' + modulesArr.getAt(i).unitTestProjectName + ' -R xunit-file '


                        if (modulesArr.getAt(i).codeCoverage == 'on') {
                            npmExecuteGrunt += 'node_mocha:with_coverage '
                        }

                    }

                    //Execute npm intsll if compile is selected
                    if (modulesArr.getAt(i).jsonPath) {
                        //Cd command
                        String command = 'cd ' + modulesArr.getAt(i).jsonPath + '\n'
                        if (isLinux) {
                            command += Constants.SUDO + ' '
                        }
                        //add npm install
                        command += 'npm install\n'
                        ExecuteCmd.invokeCmd(delegate, addProxyIfEnabled(command, modulesArr.getAt(i).npmProxy, modulesArr.getAt(i).npmProxyUserName, modulesArr.getAt(i).npmProxyPassword, jsonData.basicInfo.buildServerOS), jsonData.basicInfo.buildServerOS);
                    }

//					//checking condition for execute script
                    if (modulesArr.getAt(i).executeScript == 'on') {
                        String batchCommand = 'start node ' + modulesArr.getAt(i).customScript;
                        ExecuteCmd.invokeCmd(delegate, batchCommand, jsonData.basicInfo.buildServerOS)
                    }

                    String gruntInstall = ''
                    String gruntInstallGlobal = ''
                    if (isLinux) {
                        gruntInstall += Constants.SUDO + ' '
                    }
                    gruntInstall += 'npm install grunt --save-dev'
                    if (isLinux) {
                        gruntInstallGlobal += Constants.SUDO + ' '
                    }
                    gruntInstallGlobal += 'npm install -g grunt'
                    if ((modulesArr.getAt(i).installGrunt != null &&
                            modulesArr.getAt(i).installGrunt.equalsIgnoreCase('on')) ||
                            isLinux) {
                        ExecuteCmd.invokeCmd(delegate, addProxyIfEnabled(gruntInstall, modulesArr.getAt(i).npmProxy, modulesArr.getAt(i).npmProxyUserName, modulesArr.getAt(i).npmProxyPassword, jsonData.basicInfo.buildServerOS), jsonData.basicInfo.buildServerOS)
                        ExecuteCmd.invokeCmd(delegate, addProxyIfEnabled(gruntInstallGlobal, modulesArr.getAt(i).npmProxy, modulesArr.getAt(i).npmProxyUserName, modulesArr.getAt(i).npmProxyPassword, jsonData.basicInfo.buildServerOS), jsonData.basicInfo.buildServerOS)
                    }

                    if (modulesArr.getAt(i).unitTesting == 'on' ||
                            modulesArr.getAt(i).codeAnalysis.contains('checkStyle') ||
                            modulesArr.getAt(i).codeFormatting == 'on') {
                        ExecuteCmd.invokeCmd(delegate, addProxyIfEnabled(npmInstallCommands, modulesArr.getAt(i).npmProxy, modulesArr.getAt(i).npmProxyUserName, modulesArr.getAt(i).npmProxyPassword, jsonData.basicInfo.buildServerOS), jsonData.basicInfo.buildServerOS)
                    }

                    //For  code formatter , check style and code coverage
                    if (modulesArr.getAt(i).codeFormatting == 'on'
                            || ('on'.equalsIgnoreCase(modulesArr.getAt(i).codeCoverage))) {
                        ExecuteCmd.invokeCmd(delegate, addProxyIfEnabled(npmExecuteGrunt, modulesArr.getAt(i).npmProxy, modulesArr.getAt(i).npmProxyUserName, modulesArr.getAt(i).npmProxyPassword, jsonData.basicInfo.buildServerOS), jsonData.basicInfo.buildServerOS)

                    }

                    if (modulesArr.getAt(i).codeAnalysis.contains('checkStyle')) {
                        ExecuteCmd.invokeCmd(delegate, addProxyIfEnabled(npmExecuteJsHint, modulesArr.getAt(i).npmProxy, modulesArr.getAt(i).npmProxyUserName, modulesArr.getAt(i).npmProxyPassword, jsonData.basicInfo.buildServerOS), jsonData.basicInfo.buildServerOS)
                    }

                    //For Unit testing
                    if (modulesArr.getAt(i).unitTesting == 'on') {
                        ExecuteCmd.invokeCmd(delegate, addProxyIfEnabled(npmExecuteMocha, modulesArr.getAt(i).npmProxy, modulesArr.getAt(i).npmProxyUserName, modulesArr.getAt(i).npmProxyPassword, jsonData.basicInfo.buildServerOS), jsonData.basicInfo.buildServerOS)
                    }


                    if (modulesArr.getAt(i).codeAnalysis.contains('checkStyle') ||
                            modulesArr.getAt(i).codeFormatting == 'on' ||
                            modulesArr.getAt(i).unitTesting == 'on') {
                        prepareGruntFile(modulesArr.getAt(i), envVar)
                    }

                    if (isLinux) {
                        String permissionCommands = 'sudo chmod -R 777 . || true'

                        ExecuteCmd.invokeCmd(delegate, permissionCommands, jsonData.basicInfo.buildServerOS)
                    }
                }

            }
        }
    }

    /*
     * This method is used to check code quality tools selection
     */

    private static def codeQltyOn(ca, tool) {
        (ca.contains(tool))
    }

    /*
     * This method is used to create NodeJS build job
     */

    public static void nodejsBuildJobCreation(context, jsonData, envVar, basepathArg) {
        basepath = basepathArg
        context.with {
            //Standard IDP Settings
            configure { it / canRoam(false) }
            concurrentBuild(true)

            addProperties(delegate, jsonData)
            addSteps(delegate, jsonData, envVar)
            addPublishers(delegate, jsonData)
            addWrappers(delegate, jsonData)

            //Optional settings of job
            String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
            if (customWS) customWorkspace('$IDP_WS')
        }
    }

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
     * this method is used to add the wrappers
     */

    public static void addWrappers(context, jsonData) {
        def toolList = getListOfTools(jsonData)
        def copyPattern = filesToCopy(jsonData.buildInfo.modules)
        context.with {
            wrappers {
                (new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(delegate, toolList)

                if (copyPattern != '')
                    CopyToSlave.invokeCopyToSlave(delegate, copyPattern)
            }
        }
    }

    /*
     * This method is used to get the list of custom tools to be added
     */

    private static String getListOfTools(jsonData) {

        String toolList = ''
        def modulesArr = jsonData.buildInfo.modules
        def csFlag = false
        def fbFlag = false
        def pmdFlag = false

        for (int i = 0; i < modulesArr.size(); i++) {

            if (csFlag && fbFlag && pmdFlag)
                break

            if (codeQltyOn(modulesArr.getAt(i).codeAnalysis, Constants.PMD))
                pmdFlag = true

            if (codeQltyOn(modulesArr.getAt(i).codeAnalysis, Constants.CHECKSTYLE))
                csFlag = true

            if (codeQltyOn(modulesArr.getAt(i).codeAnalysis, Constants.FINDBUGS))
                fbFlag = true
        }

        if (pmdFlag)
            toolList += ',PMD5_0_4_HOME'
        if (csFlag)
            toolList += ',CHECKSTYLE_HOME'
        if (fbFlag)
            toolList += ',FINDBUGS_HOME'

        return toolList

    }

    /*
     * This method is used to prepare grunt file
     */

    private static void prepareGruntFile(jsonData, envVar) {
        String fileContent = Constants.GRUNT_FUNC_START;
        Boolean ispreviousDependecyAdded = false
        if (jsonData.codeFormatting == 'on') {
            fileContent += Constants.GRUNT_UGLYFY
            ispreviousDependecyAdded = true
        }

        if (jsonData.codeAnalysis.contains('checkStyle')) {
            if (ispreviousDependecyAdded) {
                fileContent += ','
            }
            fileContent += '\n\n'
            fileContent += Constants.GRUNT_JSHINT
            ispreviousDependecyAdded = true
        }
        if (jsonData.unitTesting == 'on') {
            if (ispreviousDependecyAdded) {
                fileContent += ','
            }
            fileContent += '\n\n'
            fileContent += '\n' + Constants.GRUNT_MOCHE
        }
        fileContent += Constants.GRUNT_CONFIG_END

        if (jsonData.codeFormatting == 'on') {
            fileContent += Constants.GRUNT_UGLYFY_LOAD
        }

        if (jsonData.codeAnalysis.contains('checkStyle')) {
            fileContent += Constants.GRUNT_JSHIN_LOAD
        }
        if (jsonData.unitTesting == 'on') {
            fileContent += Constants.GRUNT_MOCHA_LOAD
        }
        fileContent += Constants.GRUNT_FUNC_END

        fileContent = fileContent.replace('$SRC_PATH_UGLIFY', jsonData.relativePath + '/**.js');
        //get all exclude folders
        String execludeFolderStr = ''
        String[] splitWithColen = []
        if (jsonData.excludeFolders)
            splitWithColen = jsonData.excludeFolders.split(",");

        for (int i = 0; i < splitWithColen.size(); i++) {
            execludeFolderStr += ",'**/" + splitWithColen.getAt(i).trim() + "/**/*.*'"
        }

        if (jsonData.unitTestProjectName)
            fileContent = fileContent.replace('$SRC_PATH_TEST', jsonData.unitTestProjectName);

        if (jsonData.jsonPath) {
            fileContent = fileContent.replace('$SRC_PATH_JS', jsonData.jsonPath + '/**.js');
            fileContent = fileContent.replace('$SRC_PATH_HTML', jsonData.jsonPath + '/**.html');
            fileContent = fileContent.replace('$SRC_NODE_PATH', "node_modules/**/*.js','" + jsonData.jsonPath + "/node_modules/**','" + jsonData.jsonPath + "/*.js'" + execludeFolderStr);
        } else {
            fileContent = fileContent.replace('$SRC_PATH_JS', jsonData.relativePath + '/**.js');
            fileContent = fileContent.replace('$SRC_PATH_HTML', jsonData.relativePath + '/**.html');
            fileContent = fileContent.replace('$SRC_NODE_PATH', "node_modules/**/*.js','" + jsonData.relativePath + "/*.js'" + execludeFolderStr);
        }

        WriteFile.run(envVar.JENKINS_HOME + '/userContent/' + basepath + '_' + 'grunt.js', fileContent)
    }

    /*
     * this method give the string of files to be copied from jenkins master to slave workspace
     */

    private static String filesToCopy(modulesArr) {

        String copyPattern = ''

        for (int i = 0; i < modulesArr.size(); i++) {

            if (modulesArr.getAt(i).codeAnalysis.contains('checkStyle') ||
                    modulesArr.getAt(i).codeFormatting == 'on' ||
                    modulesArr.getAt(i).unitTesting == 'on')
                copyPattern += basepath + '_grunt.js'
        }
        return copyPattern
    }

    /*
     * This method is used to configure proxy details in jenkins job
     */

    private static String addProxyIfEnabled(cmd, proxyUrl, uname, pwd, os) {

        if (proxyUrl == null || proxyUrl == '')
            return cmd

        def proxyWithCreds = proxyUrl

        if (pwd && uname && pwd != '' && uname != '') {

            def proto = proxyWithCreds.substring(0, proxyWithCreds.indexOf('://') + '://'.length())
            def urlWoProto = proxyWithCreds.substring(proxyWithCreds.indexOf('://') + '://'.length())
            def encodedCreds = URLEncoder.encode(uname, "UTF-8") + ':' + ((os == Constants.WINDOWSOS) ? '%PROXY_PWD%' : '$PROXY_PWD') + '@'
            proxyWithCreds = proto + encodedCreds + urlWoProto
        }

        if (os == Constants.WINDOWSOS)
            return 'set http_proxy=' + proxyWithCreds + '\nset https_proxy=' + proxyWithCreds + '\n' + cmd
        else {
            return 'export http_proxy="' + proxyWithCreds + '"\nexport https_proxy="' + proxyWithCreds + '"\n' + cmd + '\nunset http_proxy\nunset https_proxy'
        }
    }
}	
	


