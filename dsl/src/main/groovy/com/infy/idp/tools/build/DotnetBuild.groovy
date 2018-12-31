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
import com.infy.idp.plugins.buildsteps.MSTestBuilder
import com.infy.idp.plugins.buildsteps.SonarRunner
import com.infy.idp.plugins.publishers.*
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*

/**
 *
 * This class has the method to create the build job for the DotNet technology
 *
 */

class DotnetBuild {

    static String basepath
    static boolean isCodeCoverageSel = false
    static boolean isMSTestSel = false
    static boolean isFxCopSel = false
    static boolean isObjectDB = false

    /*
     * This method is used to add the required publishers for DotNet technology
     */

    public static void addPublishers(context, jsonData) {

        context.with {

            publishers {

                def modulesArr = jsonData.buildInfo.modules
                for (int i = 0; i < modulesArr.size(); i++) {

                    if (jsonData.buildInfo.modules.getAt(i).nUnitprojPath != null && jsonData.buildInfo.modules.getAt(i).nUnitprojPath != '') {

                        nunit {
                            testResultsPattern('TestResults.xml')
                            failIfNoResults(false)
                        }

                    }


                }


                if (isFxCopSel) {

                    ViolationPublisher violationPublisher = new ViolationPublisher()
                    violationPublisher.setFxCopPattern('**/AnalysisResult.xml')
                    violationPublisher.add(delegate, jsonData)
                }



                if (isMSTestSel) {

                    MSTestPublisher msTestPublisher = new MSTestPublisher()
                    msTestPublisher.setPattern('**/*_TestResults.trx')
                    msTestPublisher.add(delegate, jsonData)
                }

                if (isCodeCoverageSel) {

                    EmmaPublisher emmaPublisher = new EmmaPublisher()
                    emmaPublisher.add(delegate, jsonData)
                }
            }
        }
    }

    /*
     * These method checks for the code analysis tools selection
     */

    private static def sonarOn(jsonData, i) {
        (jsonData.buildInfo.modules.getAt(i).codeAnalysis && jsonData.buildInfo.modules.getAt(i).codeAnalysis.contains(Constants.SONAR))
    }

    private static def fxCopOn(jsonData, i) {
        (jsonData.buildInfo.modules.getAt(i).codeAnalysis && jsonData.buildInfo.modules.getAt(i).codeAnalysis.contains(Constants.FXCOP))
    }

    private static def unitTestingOn(jsonData, i) {
        (jsonData.buildInfo.modules.getAt(i).unitTesting && jsonData.buildInfo.modules.getAt(i).unitTesting.toString().compareToIgnoreCase('on') == 0)
    }

    private static def codeCoverageOn(jsonData, i) {
        (jsonData.buildInfo.modules.getAt(i).codeCoverage && jsonData.buildInfo.modules.getAt(i).codeCoverage.toString().compareToIgnoreCase('on') == 0)
    }

    private static def isObjectDB(jsonData, i) {
        (jsonData.buildInfo.modules.getAt(i).projPath != null && jsonData.buildInfo.modules.getAt(i).projPath != "")
    }

    /*
     * This method adds the MSBuild option in jenkins job
     */

    private static void addMSBuild(context, msBuildVersion, solPath, cmdLineArg) {

        context.with {

            msbuild {

                //msBuildName('(Default)')
                msBuildName(msBuildVersion)
                msBuildFile(solPath)
                //cmdLineArgs('/v:diag\n/t:build')
                if (!cmdLineArg)
                    cmdLineArg = ''
                if (!cmdLineArg.toLowerCase().contains('/p:deployonbuild=true'))
                    cmdLineArg += ' /P:DeployOnBuild=true'
                cmdLineArgs(cmdLineArg)
                buildVariablesAsProperties(false)
                continueOnBuildFailure(false)
                unstableIfWarnings(false)


            }
        }
    }

    /*
     * this method is used to add FXCop
     */

    private static void addFXCop(context, jsonData, i) {

        FxCopScriptGenCT.invokeTool(context, jsonData, i)
        FxCopCmdCT.invokeTool(context, jsonData, i)
    }

    /*
     * This method is used to add conditional build steps
     */

    private static void addSteps(context, jsonData, envVar) {
        context.with {
            steps {
                addConditionalBuilder(delegate, jsonData, envVar)
            }
        }
    }

    /*
     * This method adds the conditional build steps
     */

    private static void addConditionalBuilder(context, jsonData, envVar) {

        context.with {

            def modulesArr = jsonData.buildInfo.modules

            for (int i = 0; i < modulesArr.size(); i++) {

                conditionalBuilder {

                    runCondition {

                        expressionCondition {

                            String mod = '(.)*(;' + modulesArr.getAt(i).moduleName + ';){1}(.)*'
                            // The regular expression used to match the label - Java regular expression syntax
                            expression(mod)

                            // The label that will be tested by the regular expression
                            label('$MODULE_LIST')
                        }
                    }

                    // If the evaluation of a run condition fails, should the build fail, be marked unstable, run the build step ...
                    runner {
                        fail()
                    }


                    conditionalbuilders {

                        def relativePath = modulesArr.getAt(i).relativePath.replace('\\', '/')
                        def moduleDir
                        def username = jsonData.basicInfo.userName;
                        username = username + '@domain.com'
                        if (relativePath.contains('/')) {
                            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
                        } else {
                            moduleDir = relativePath
                        }


                        DotnetBuild.addMSBuild(delegate, modulesArr.getAt(i).version, modulesArr.getAt(i).relativePath, modulesArr.getAt(i).args)
                        if (isObjectDB(jsonData, i)) {
                            isObjectDB = true

                            sampleExeFiles("../" + modulesArr.getAt(i).projPath + "/bin/Debug/" + modulesArr.getAt(i).moduleName + '.dacpac', modulesArr.getAt(i).srcSchName, modulesArr.getAt(i).tarSchName, envVar)
                            def path = modulesArr.getAt(i).projPath.replace('/', '\\')
                            def cmd = "copy " + path + "\\bin\\Debug\\" + modulesArr.getAt(i).moduleName + '.dacpac ' + modulesArr.getAt(i).moduleName + '%BUILD_NUMBER%.dacpac'
                            cmd += "\n cd " + basepath + "\n copy Sample.exe.config ..\\DBobject\\Sample.exe.config"
                            cmd += "\n cd .. \n cd DBobject"
                            cmd += "\n Sample.exe"
                            ExecuteCmd.invokeCmd(delegate, cmd, jsonData.basicInfo.buildServerOS)


                        }
                        if (fxCopOn(jsonData, i)) {
                            isFxCopSel = true
                            addFXCop(delegate, jsonData, i)
                        }

                        if (sonarOn(jsonData, i)) {

                            //msBuildSQRunnerEnd()
                            SonarRunner sonarRunner = new SonarRunner()
                            def moduleName = modulesArr.getAt(i).moduleName
                            if (null != jsonData.buildInfo.sonarUserName && !''.equalsIgnoreCase(jsonData.buildInfo.sonarUserName) && null != jsonData.buildInfo.sonarPassword && !''.equalsIgnoreCase(jsonData.buildInfo.sonarPassword)) {
                                sonarRunner.setProperties('sonar.login=' + jsonData.buildInfo.sonarUserName + '\nsonar.password=' + jsonData.buildInfo.sonarPassword + '\nsonar.host.url=' + jsonData.buildInfo.sonarUrl + '\nsonar.projectKey=' + jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName +
                                        '\nsonar.projectName=' + moduleName + '\nsonar.projectVersion=$RELEASE_IDENTIFIER' + '\nsonar.sources=' + moduleDir)
                            } else {
                                sonarRunner.setProperties('sonar.host.url=' + jsonData.buildInfo.sonarUrl + '\nsonar.projectKey=' + jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName + '\nsonar.projectName=' + moduleName + '\nsonar.projectVersion=$RELEASE_IDENTIFIER' + '\nsonar.sources=' + moduleDir)
                            }
                            sonarRunner.add(delegate, jsonData)

                        }

                       

                        if (codeCoverageOn(jsonData, i)) {

                            MSTestCoverageScriptGenCT.invokeTool(delegate, jsonData, i)
                            MSTestCoverageCmdCT.invokeTool(delegate, jsonData, i)
                            MSTestCoverageMSXSL.invokeTool(delegate, jsonData, i)
                            isCodeCoverageSel = true
                        }
						
						 if (unitTestingOn(jsonData, i)) {

                            MSTestBuilder msTestBuilder = new MSTestBuilder()

                            def testPrjctName = jsonData.buildInfo.modules.getAt(i).unitTestProjectName

                            def testPrjctCatagory = jsonData.buildInfo.modules.getAt(i).unitTestCategory
                            testPrjctCatagory = testPrjctCatagory == null ? '' : testPrjctCatagory
                            msTestBuilder.setTestCat(testPrjctCatagory)

                            def testSettingsFile = jsonData.buildInfo.modules.getAt(i).testSettingFilePath
                            testSettingsFile = testSettingsFile == null ? '' : '/runconfig:"' + testSettingsFile + '"'
							if(isCodeCoverageSel) {
                            msTestBuilder.setCmdArgs(testSettingsFile)
							}
	
                            def solDir = jsonData.buildInfo.modules.getAt(i).relativePath.replace('\\', '/')
                            solDir = solDir.contains('/') ? solDir.substring(0, solDir.lastIndexOf('/')) + '/' : ''

                            msTestBuilder.setTestFiles(solDir + testPrjctName + '/bin/Debug/' + testPrjctName + '.dll')
                            msTestBuilder.setTestOpFile(solDir + testPrjctName + '/' + testPrjctName + '_TestResults.trx')

                            msTestBuilder.add(delegate, jsonData)
                            isMSTestSel = true
                        }

                        if (modulesArr.getAt(i).ossMailRecipients) {


                            prepareOssComplianceXml(modulesArr.getAt(i).moduleName, modulesArr.getAt(i).relativePath, modulesArr.getAt(i).ossMailRecipients, modulesArr.getAt(i).ossDistributionType, modulesArr.getAt(i).ossAnalysisType, username, envVar)
                            ConditionalAnt.invokeConditionalAnt(delegate, 'OSSCheck', basepath + '_' + modulesArr.getAt(i).moduleName + '_oss_build.xml', '', 'module_name=' + modulesArr.getAt(i).moduleName)
                        }
                        if (jsonData.buildInfo.modules.getAt(i).nUnitprojPath != null && jsonData.buildInfo.modules.getAt(i).nUnitprojPath != '') {

                            def cmd = "..\\..\\tools\\com.cloudbees.jenkins.plugins.customtools.CustomTool\\PACKAGE\\packages\\NUnit.ConsoleRunner.3.8.0\\tools\\nunit3-console.exe " + jsonData.buildInfo.modules.getAt(i).nUnitprojPath

                            ExecuteCmd.invokeCmd(delegate, cmd, jsonData.basicInfo.buildServerOS)
                        }

                    }
                }
            }

        }
    }


    private static String sampleExeFiles(dacpacPath, srcDB, desDB, envVar) {

        String fileContent = ReadFile.run('/ant_templates/Sample.exe.config')


        fileContent = fileContent.replace('$dacpacPath', dacpacPath)
        fileContent = fileContent.replace('$sourceDB', srcDB)
        fileContent = fileContent.replace('$destDB', desDB)



        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '/Sample.exe.config', fileContent)

    }

    /*
     * This method is used to create dotnet build job
     */

    public static void dotnetBuildJobCreation(context, jsonData, envVar, basepathArg) {

        basepath = basepathArg

        context.with {

            //Standard IDP Settings
            configure { it / canRoam(false) }
            concurrentBuild(true)

            //Configurable Settings of Job
            addSteps(delegate, jsonData, envVar)

            addWrappers(delegate, jsonData)

            //Publishers
            addPublishers(delegate, jsonData)

            //Optional settings of job
            String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
            if (customWS) customWorkspace('$IDP_WS')
        }


    }

    /*
     * this method is used to add the wrappers
     */

    public static void addWrappers(context, jsonData) {

        def toolList = getListOfTools(jsonData.buildInfo.modules)
        def copyPattern = filesToCopy(jsonData.buildInfo.modules)



        context.with {

            wrappers {
                if (toolList != null && toolList != '')
                    (new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(delegate, toolList)
                if (copyPattern != null && copyPattern != '')
                    CopyToSlave.invokeCopyToSlave(delegate, copyPattern)
            }
        }
    }

    /*
     * This method is used to get the list of custom tools to be added
     */

    private static String getListOfTools(modulesArr) {

        String toolList = ''
        def ossCheck = false;
        def nUnit = false;
        if (isFxCopSel || isCodeCoverageSel)
            toolList = 'MSCUSTOMTOOLS_HOME'

        for (int i = 0; i < modulesArr.size(); i++) {
            if (modulesArr.getAt(i).ossMailRecipients)
                ossCheck = true
            if (modulesArr.getAt(i).nUnitprojPath != null && modulesArr.getAt(i).nUnitprojPath != '')
                nUnit = true
        }
        if (ossCheck)
            toolList += ',OSS_HOME'
        if (nUnit)
            toolList += ',PACKAGE_HOME'
        return toolList
    }

    /*
     * this method give the string of files to be copied from jenkins master to slave workspace
     */

    private static String filesToCopy(modulesArr) {

        String copyPattern = ''

        for (int i = 0; i < modulesArr.size(); i++) {


            if (modulesArr.getAt(i).projPath != null && modulesArr.getAt(i).projPath != "") {
                copyPattern += 'DBobject\\IFilterDeltaScript.exe,DBobject\\IFilterDeltaScript.exe.config,DBobject\\Sample.exe,'
                copyPattern += basepath + '/Sample.exe.config,'
            }
            if (modulesArr.getAt(i).ossMailRecipients)
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_oss_build.xml,')

        }

        return copyPattern
    }

    /*
     * This method is used to prepare OSS compliance cxml file
     */

    private
    static void prepareOssComplianceXml(moduleName, relativePath, ossRecipiants, ossDistributionType, ossAnalysisType, username, envVar) {
        String fileContent = ReadFile.run('/ant_templates/ossCompliance.xml')

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }
        fileContent = fileContent.replace('$ProjectName', moduleName)
        fileContent = fileContent.replace('$UserId', username
        )
        fileContent = fileContent.replace('$distributionType', ossDistributionType)
        fileContent = fileContent.replace('$recipiants', ossRecipiants)
        fileContent = fileContent.replace('$analysisType', ossAnalysisType)

        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + moduleName + '_oss_build.xml', fileContent)
    }
}
