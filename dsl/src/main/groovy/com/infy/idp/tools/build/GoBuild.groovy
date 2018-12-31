/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.build

import com.infy.idp.creators.*
import com.infy.idp.plugins.buildsteps.SonarRunner
import com.infy.idp.plugins.publishers.*
import com.infy.idp.plugins.reporters.*
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.tools.JUnitArchive
import com.infy.idp.tools.reportpublishers.*
import com.infy.idp.utils.*
import com.infy.idp.utils.Constants
import com.infy.idp.utils.fs.*

/**
 *
 * This class has the method to create the build job for the DotNet technology
 *
 */

class GoBuild {

    static String basepath

    /*
     * This method is used to add properties for parameterized build
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
     * this method is used to create build job for GO technology
     */

    public static void goBuildJobCreation(context, jsonData, envVar, basepath) {
        this.basepath = basepath
        context.with {
            //Standard IDP Settings
            configure { it / canRoam(false) }
            concurrentBuild(true)
            addProperties(delegate, jsonData)
            addSteps(delegate, jsonData, envVar)

            // Post Build Actions
            addPublishers(delegate, jsonData)

            addWrappers(delegate, jsonData)

            //Optional settings of job
            String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
            if (customWS) {
                customWorkspace('$IDP_WS')
            }
        }
    }

    /*
     * This method is used to add the steps like prebuild and post build
     */

    private static void addSteps(context, jsonData, envVar) {

        def modulesArr = jsonData.buildInfo.modules


        context.with {

            steps {

                for (int i = 0; i < modulesArr.size(); i++) {

                    String projectPath = modulesArr.getAt(i).relativePath
                    String proxy = ''
                    String unsetProxy = ''

                    def pathTokens
                    String absoluteBasePath
                    String absoluteFullPath
                    String baseProjectPath
                    String projectFolderName
//					String pipelineName = jsonData.basicInfo.applicationName  + '_' + jsonData.basicInfo.pipelineName
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

                    if (projectPath.contains("/")) {
                        pathTokens = projectPath.split('/')
                        baseProjectPath = pathTokens.dropRight(1).join("/")
                        if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
                            absoluteBasePath = '%IDP_WS%/' + baseProjectPath
                            absoluteFullPath = '%IDP_WS%/' + projectPath
                        } else {
                            absoluteBasePath = '$IDP_WS/' + baseProjectPath
                            absoluteFullPath = '$IDP_WS/' + projectPath
                        }
                        projectFolderName = pathTokens[-1]
                    } else {
                        if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
                            absoluteBasePath = '%IDP_WS%'
                            absoluteFullPath = '%IDP_WS%/' + projectPath
                        } else {
                            absoluteBasePath = '$IDP_WS'
                            absoluteFullPath = '$IDP_WS/' + projectPath
                        }
                        projectFolderName = projectPath
                        baseProjectPath = projectPath
                    }

                    if (modulesArr.getAt(i).compile == 'on') {
                        String glideInstallCmd;
                        if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
                            glideInstallCmd = 'xcopy "' + absoluteBasePath + '" "%GOPATH%/src" /s /y\n'
                            glideInstallCmd += 'cd /d "%GOPATH%/src/' + projectFolderName + '"\n'
                            glideInstallCmd += 'glide create --non-interactive\n'
                            //glideInstallCmd += 'n\n'
                            glideInstallCmd += proxy + '\n'
                            glideInstallCmd += 'glide install\n'
                            glideInstallCmd += 'xcopy "%GOPATH%/src/' + projectFolderName + '/vendor" "%GOPATH%/src" /s /y'
                        } else {
                            glideInstallCmd = 'cp -r ' + absoluteBasePath + '/* $GOPATH/src \n'
                            glideInstallCmd += 'cd "$GOPATH/src/' + projectFolderName + '"\n'
                            glideInstallCmd += 'export PATH=$PATH:$GOPATH/bin:$GOROOT/bin\n'
                            glideInstallCmd += 'glide create --non-interactive\n'
                            //glideInstallCmd += 'n\n'
                            glideInstallCmd += 'glide install\n'
                            glideInstallCmd += 'cp -r $GOPATH/src/' + projectFolderName + '/vendor/* $GOPATH/src'
                        }

                        String goBuildCmd = 'cd "' + absoluteFullPath + '"\n'

                        new ExecuteCmd().invokeCmd(delegate, glideInstallCmd, jsonData.basicInfo.buildServerOS)

                        if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
                            goBuildCmd += 'go build'
                        } else {
                            goBuildCmd += 'export PATH=$PATH:$GOPATH/bin:$GOROOT/bin\n'
                            goBuildCmd += 'go build'
                        }

                        new ExecuteCmd().invokeCmd(delegate, goBuildCmd, jsonData.basicInfo.buildServerOS)
                    }



                    if (modulesArr.getAt(i).codeAnalysis.size() > 0) {

                        if (modulesArr.getAt(i).codeAnalysis[0] != null) {
                            addGoCodeAnalysis(delegate, jsonData.basicInfo.buildServerOS, modulesArr.getAt(i), absoluteFullPath, absoluteBasePath, envVar)
                        }
                        if (modulesArr.getAt(i).codeAnalysis[1] != null) {
                            addGoSonarCodeAnalysis(delegate, jsonData, absoluteBasePath)
                        }
                        //CheckStyle Report conversion Pending
                        //Sonar Pending
                    }

                    if (modulesArr.getAt(i).unitTesting == 'on') {

                        String unitTestPath = modulesArr.getAt(i).unitTestDir
                        String goUnitTestCmd = 'cd "' + absoluteFullPath + '/' + unitTestPath + '"\n'
                        if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) != 0) {
                            goUnitTestCmd += 'export PATH=$PATH:$GOPATH/bin:$GOROOT/bin\n'
                        }
                        goUnitTestCmd += 'go test -v ./... | go-junit-report > "' + absoluteBasePath + '/JUnit_Test.xml"'
                        new ExecuteCmd().invokeCmd(delegate, goUnitTestCmd, jsonData.basicInfo.buildServerOS)

                        if (modulesArr.getAt(i).codeCoverage == 'on') {
                            addGoCodeCoverage(delegate, jsonData.basicInfo.buildServerOS, modulesArr.getAt(i), absoluteFullPath, absoluteBasePath)
                        }
                    }
                }
            }
        }
    }

    /*
     * this method is used to add code analysis tools fopr GO technology
     */

    private static void addGoCodeAnalysis(context, os, modulesObj, absoluteFullPath, absoluteBasePath, envVar) {
        String goCodeAnalysisCmd
        if (os.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
            goCodeAnalysisCmd = 'cd "' + absoluteFullPath + '"\n'
            goCodeAnalysisCmd += 'gometalinter --checkstyle > "' + absoluteBasePath + '/checkstyle-result.xml"\n'
            goCodeAnalysisCmd += 'exit /b 0'
        } else {
            goCodeAnalysisCmd = 'cd "' + absoluteFullPath + '"\n'
            goCodeAnalysisCmd += 'export PATH=$PATH:$GOPATH/bin:$GOROOT/bin\n'
            goCodeAnalysisCmd += 'gometalinter --checkstyle > ' + absoluteBasePath + '/checkstyle-result.xml || true\n'
            //goCodeAnalysisCmd += 'exit /b 0'
        }

        new ExecuteCmd().invokeCmd(context, goCodeAnalysisCmd, os)

        prepareCheckStyleAntXml(modulesObj.moduleName, absoluteBasePath.replace('%IDP_WS%', '${IDP_WS}').replace('$IDP_WS', '${IDP_WS}'), envVar)
        Ant.invokeAnt(context, ['checkstyle-report'], basepath + '_' + modulesObj.moduleName + '_checkstyle_build.xml', [module_name: modulesObj.moduleName])
    }

    /*
     * this method is used to add sonar details for GO technology
     */

    private static void addGoSonarCodeAnalysis(context, jsonData, absoluteBasePath) {
        SonarRunner sonarRunner = new SonarRunner()
        String properties;
        String pipelineName = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName

        if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
            properties = 'sonar.projectKey=' + jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName + '\n'
            properties += 'sonar.projectName=' + pipelineName + '\n'
            properties += 'sonar.projectVersion=%RELEASE_IDENTIFIER%\n'
            properties += 'sonar.golint.reportPath=' + absoluteBasePath + '/checkstyle-result.xml\n'
            properties += 'sonar.sources=./'
        } else {
            properties = 'sonar.projectKey=' + jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName + '\n'
            properties += 'sonar.projectName=' + pipelineName + '\n'
            properties += 'sonar.projectVersion=$RELEASE_IDENTIFIER\n'
            properties += 'sonar.golint.reportPath=' + absoluteBasePath + '/checkstyle-result.xml\n'
            properties += 'sonar.sources=./'
        }

        sonarRunner.setProperties(properties)
        sonarRunner.add(context, jsonData)
    }

    /*
     * This method is used to add code coverage tools for go
     */

    private static void addGoCodeCoverage(context, os, modulesObj, absoluteFullPath, absoluteBasePath) {
        String goCodeCoverageCmd = 'cd "' + absoluteFullPath + '"\n'

        if (os.compareToIgnoreCase(Constants.WINDOWSOS) != 0) {
            goCodeCoverageCmd += 'export PATH=$PATH:$GOPATH/bin:$GOROOT/bin\n'
        }

        goCodeCoverageCmd += 'gocov test "' + modulesObj.unitTestDir + '" | gocov-xml >"' + absoluteBasePath + '/coverage.xml"'
        new ExecuteCmd().invokeCmd(context, goCodeCoverageCmd, os)
    }

    /*
     * This method is used to add required publishers
     */

    public static void addPublishers(context, jsonData) {

        def modulesArr = jsonData.buildInfo.modules
        JUnitArchive jUnitArchive = new JUnitArchive()
        CoberturaPublisher coberturaPublisher = new CoberturaPublisher()
        CheckStylePublisher checkStylePublisher = new CheckStylePublisher()
        ArchiveArtifacts archiveArtifacts = new ArchiveArtifacts()
        def csFlag = false
        def cobFlag = false
        def junitFlag = false
        String strArtifactArchive = ''

        for (int i = 0; i < modulesArr.size(); i++) {

            if (modulesArr.getAt(i).unitTesting == 'on') {
                jUnitArchive.updatePattern('**/JUnit_Test.xml')
                strArtifactArchive += '**/JUnit_Test.xml,'
                junitFlag = true
            }
            if (modulesArr.getAt(i).codeCoverage == 'on') {
                coberturaPublisher.updatePattern('**/coverage.xml')
                strArtifactArchive += '**/coverage.xml,'
                cobFlag = true
            }
            if (modulesArr.getAt(i).codeAnalysis.size() > 0 && modulesArr.getAt(i).codeAnalysis.contains(Constants.CHECKSTYLE)) {
                checkStylePublisher.updatePattern('**/checkstyle-result.xml')
                strArtifactArchive += '**/checkstyle-result.xml,**/checkstyle_report.html'
                csFlag = true
            }
        }

        context.with {

            publishers {

                if (junitFlag)
                    jUnitArchive.invokeJUnitArchive(delegate,jsonData)

                if (cobFlag)
                    coberturaPublisher.add(delegate, jsonData)

                if (csFlag)
                    checkStylePublisher.add(delegate, jsonData)

                if (junitFlag || cobFlag || csFlag) {
                    archiveArtifacts.setPattern(strArtifactArchive)
                    archiveArtifacts.add(delegate, jsonData)
                }
            }
        }
    }

    /*
     * This method is used to add required wrappers
     */

    public static void addWrappers(context, jsonData) {

        def toolList = getListOfTools(jsonData.buildInfo.modules)
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
     * This method is used to get the list of tools to be added
     */

    private static String getListOfTools(modulesArr) {

        String toolList = ''

        def checkstyleFlag = false


        for (int i = 0; i < modulesArr.size(); i++) {

            if (modulesArr.getAt(i).codeAnalysis && modulesArr.getAt(i).codeAnalysis[0] != null)
                checkstyleFlag = true

        }

        if (checkstyleFlag) {
            toolList += ',ANT_HOME'
            toolList += ',CHECKSTYLE_HOME'
        }

        return toolList
    }

    /*
     * this method give the string of files to be copied from jenkins master to slave workspace
     */

    private static String filesToCopy(modulesArr) {

        String copyPattern = ''

        for (int i = 0; i < modulesArr.size(); i++) {

            if (modulesArr.getAt(i).codeAnalysis && modulesArr.getAt(i).codeAnalysis[0] != null)
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_checkstyle_build.xml,')

        }
        return copyPattern
    }

    /*
     * This methos prepares the Checkstyle Ant XMl file
     */

    private static void prepareCheckStyleAntXml(moduleName, absoluteBasePath, envVar) {

        String fileContent = ReadFile.run('/ant_templates/java_checkstyle_go.xml')

        fileContent = fileContent.replace('$PNAME', moduleName)
        fileContent = fileContent.replace('$CheckStyleReports', absoluteBasePath)
        fileContent = fileContent.replace('$CheckStyleReportConvert', '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + '/' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CSCONVERT))


        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + moduleName + '_checkstyle_build.xml', fileContent)
    }
}
