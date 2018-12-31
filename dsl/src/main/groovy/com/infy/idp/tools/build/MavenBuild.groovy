/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.build

import com.infy.idp.creators.*
import com.infy.idp.customtools.TGMavenPOMEditor
//import com.infy.idp.plugins.buildsteps.Checkmarx
import com.infy.idp.plugins.publishers.*
import com.infy.idp.plugins.publishers.ArchiveArtifacts
import com.infy.idp.plugins.reporters.*
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.tools.reportpublishers.*
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*

/**
 *
 * This class has the method to create the build job for the Maven technology
 *
 */

class MavenBuild {

    private static def goal = ''
    private static def coberturaCommand = ''

    static String basepath

    /*
     * This method is used to cresate Maven build job
     */

    public static void mavenBuildJobCreation(context, jsonData, envVar, basepath) {

        this.basepath = basepath
        context.with {
            //Standard IDP Settings
            configure { it / canRoam(false) }
            concurrentBuild(true)
            incrementalBuild(false)
            archivingDisabled(false)
            siteArchivingDisabled(false)
            resolveDependencies(false)

            //Configurable Settings of Job
            PostBuildersAdder.addPostBuilders(delegate)
            if (jsonData.code != null && jsonData.code != '' && jsonData.code.jobParam != null && jsonData.code.jobParam != '') {
                if (jsonData.buildInfo.modules.getAt(0).relativePath.getAt(0).compareToIgnoreCase('%') == 0) {
                    def rootPomPath = '${'
                    for (int i = 1; i < jsonData.buildInfo.modules.getAt(0).relativePath.size() - 1; i++) {
                        rootPomPath += jsonData.buildInfo.modules.getAt(0).relativePath.getAt(i)
                    }
                    rootPomPath += '}'

                    rootPOM(rootPomPath)

                } else {
                    rootPOM(jsonData.buildInfo.modules.getAt(0).relativePath)
                }


            } else {
                rootPOM(jsonData.buildInfo.modules.getAt(0).relativePath)
            }
            mavenGoalSetter(delegate, jsonData)
            addPreBuildSteps(delegate, jsonData)
            addPostBuildSteps(delegate, jsonData, envVar)

            // Post Build Actions
            addReporters(delegate, jsonData)

            //Publishers
            addPublishers(delegate, jsonData)

            addWrappers(delegate, jsonData)

            //Optional settings of job
            String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
            if (customWS) customWorkspace('$IDP_WS')
        }
    }

    /*
    * This method is used to add publishsers required for AngularJS
    */

    public static void addPublishers(context, jsonData) {
        context.with {
            publishers {

                def modulesArr = jsonData.buildInfo.modules
                def caArr = jsonData.buildInfo.modules.getAt(0).codeAnalysis
                String archiveArtifactsStr = ''

                if (codeQualityOn(caArr, Constants.CHECKSTYLE))
                    archiveArtifactsStr += '**/target/checkstyle-result.html,**/target/checkstyle-result.xml,'

                if (codeQualityOn(caArr, Constants.PMD) || codeQualityOn(caArr, Constants.ICQA))
                    archiveArtifactsStr += '**/target/PMD_Report.html,**/target/pmd.xml,'

                if (codeQualityOn(caArr, Constants.FINDBUGS))
                    archiveArtifactsStr += '**/target/findbugs-default.html,**/target/findbugsXml.xml,'

                if (jsonData.buildInfo.modules.getAt(0).codeCoverage && jsonData.buildInfo.modules.getAt(0).codeCoverage.toString().compareToIgnoreCase("on") == 0) {
                    archiveArtifactsStr += '**/target/site/cobertura/**,'
                    CoberturaPublisher coberturaPublisher = new CoberturaPublisher()
                    coberturaPublisher.updatePattern('**/target/site/cobertura/coverage.xml')
                    coberturaPublisher.add(delegate, jsonData)
                }

                if (jsonData.buildInfo.modules.getAt(0).unitTesting && jsonData.buildInfo.modules.getAt(0).unitTesting.toString().compareToIgnoreCase('on') == 0)
                    archiveArtifactsStr += '**/target/surefire-reports/*.xml,'

                if (modulesArr.getAt(0).pafFilePath && modulesArr.getAt(0).serverMachine) {

                    archiveArtifactsStr += '//*report*.html,'
                }





                for (mod in modulesArr) {
                    if (mod.team) {

                        archiveArtifactsStr += '**/Checkmarx/Reports/**,'
                        break;
                    } else {
                        continue
                    }
                }
                if (archiveArtifactsStr != '') {
                    ArchiveArtifacts archiveArtifacts = new ArchiveArtifacts()
                    archiveArtifacts.setPattern(archiveArtifactsStr)
                    archiveArtifacts.add(delegate, jsonData)
                }
            }
        }
    }

    /*
     * This method is used to check code quality tools
     */

    public static def codeQualityOn(caArr, tool) {
        caArr.contains(tool)
    }

    /*
     * This method is used to add reporters for different tools
     */

    public static void addReporters(context, jsonData) {

        def caArr = jsonData.buildInfo.modules.getAt(0).codeAnalysis

        if (codeQualityOn(caArr, Constants.CHECKSTYLE))
            (new MavenCheckStyleReporter()).add(context, jsonData)

        if (codeQualityOn(caArr, Constants.PMD) || codeQualityOn(caArr, Constants.ICQA))
            (new MavenPmdReporter()).add(context, jsonData)

        if (codeQualityOn(caArr, Constants.FINDBUGS))
            (new MavenFindBugsReporter()).add(context, jsonData)
    }

    /*
     * This metohd is used to add maven goals
     */

    public static def mavenGoalSetter(context, jsonData) {

        if (jsonData.buildInfo.modules.getAt(0).clean.toString().compareToIgnoreCase('on') == 0)
            goal += 'clean '

        if (jsonData.buildInfo.modules.getAt(0).compile.toString().compareToIgnoreCase('on') == 0 || (jsonData.buildInfo.modules.getAt(0).multiModule && jsonData.buildInfo.modules.getAt(0).multiModule.equalsIgnoreCase("on")))
            goal += 'compile '

        if (jsonData.buildInfo.modules.getAt(0).unitTesting && jsonData.buildInfo.modules.getAt(0).unitTesting.toString().compareToIgnoreCase('on') == 0) {
            //if(!goal.contains('cobertura:cobertura'))
            goal += 'test '
        } else {
            goal += '-Dmaven.test.skip=true '
        }

        if (jsonData.buildInfo.modules.getAt(0).install.toString().compareToIgnoreCase('on') == 0)
            goal += 'install '

        if (jsonData.buildInfo.modules.getAt(0).args)
            goal += (jsonData.buildInfo.modules.getAt(0).args + ' ')

        if (jsonData.buildInfo.modules.getAt(0).codeAnalysis) {

            def codeAnaArr = jsonData.buildInfo.modules.getAt(0).codeAnalysis

            for (int i = 0; i < codeAnaArr.size(); i++) {

                def tool = codeAnaArr.getAt(i)
                if (tool != null && tool != 'off' && tool != 'icqa') {
                    tool = tool.toLowerCase();
                    goal += (tool + ':' + tool + ' ')
                    if (tool == 'sonar') {
                        if (null != jsonData.buildInfo.sonarUserName && !''.equalsIgnoreCase(jsonData.buildInfo.sonarUserName) && null != jsonData.buildInfo.sonarPassword && !''.equalsIgnoreCase(jsonData.buildInfo.sonarPassword)) {
                            goal += ' -Dsonar.login=' + jsonData.buildInfo.sonarUserName + ' -Dsonar.password=' + jsonData.buildInfo.sonarPassword
                        }
                        goal += ' -Dsonar.host.url=' + jsonData.buildInfo.sonarUrl + ' -Dsonar.projectKey=' + jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName + ' -Dsonar.branch=' + jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName + ' '
                    }
                }
            }
        }


        if (jsonData.buildInfo.modules.getAt(0).codeCoverage && jsonData.buildInfo.modules.getAt(0).codeCoverage.toString().compareToIgnoreCase("on") == 0) {

            def relativePath = jsonData.buildInfo.modules.getAt(0).relativePath
            relativePath = relativePath.replace('\\', '/')
            def moduleDir
            if (relativePath.contains('/')) {
                moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
            } else {
                moduleDir = relativePath
            }

            coberturaCommand = 'cd ' + moduleDir + '\n'
            coberturaCommand += 'mvn cobertura:cobertura'
        }



        context.with {
            goals(goal)
        }
    }

    /*
     * This method is used to add pre build steps
     */

    private static void addPreBuildSteps(context, jsonData) {
        context.with {
            preBuildSteps {
                //if(jsonData.buildInfo.modules.getAt(0).pafFilePath && jsonData.buildInfo.modules.getAt(0).serverMachine) {
                if (coberturaCommand != null && coberturaCommand != '')
                    goal += 'cobertura:cobertura '
                TGMavenPOMEditor.invokeTool(delegate, jsonData, 0, goal)

                //}
            }
        }
    }

    /*
    * This method is used to add post build steps
    */

    private static void addPostBuildSteps(context, jsonData, envVar) {
        context.with {
            postBuildSteps('UNSTABLE') {

                if (coberturaCommand != null && coberturaCommand != '')
                    ExecuteCmd.invokeCmd(delegate, coberturaCommand, jsonData.basicInfo.buildServerOS)

                if (jsonData.buildInfo.modules.getAt(0).mailRecipients) {
                    OSSIntegration.invokeTool(delegate, jsonData)
                }
                def modulesArr = jsonData.buildInfo.modules
                def username = jsonData.basicInfo.userName;
                username = username + '@domain.com'
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
                    checkmarx.add(delegate)
                }*/


                if (modulesArr.getAt(0).codeAnalysis && modulesArr.getAt(0).codeAnalysis.contains(Constants.CHECKSTYLE)) {
                    prepareCheckStyleAntXml(jsonData.buildInfo.modules.getAt(0).moduleName, jsonData.buildInfo.modules.getAt(0).relativePath, envVar)
                    Ant.invokeAnt(delegate, ['checkstyle-report'], basepath + '_' + jsonData.buildInfo.modules.getAt(0).moduleName + '_checkstyle_build.xml', [module_name: jsonData.buildInfo.modules.getAt(0).moduleName])
                }

                if (modulesArr.getAt(0).codeAnalysis && (modulesArr.getAt(0).codeAnalysis.contains(Constants.PMD) || modulesArr.getAt(0).codeAnalysis.contains(Constants.ICQA))) {
                    preparePMDAntXml(jsonData.buildInfo.modules.getAt(0).moduleName, jsonData.buildInfo.modules.getAt(0).relativePath, envVar)
                    Ant.invokeAnt(delegate, ['ICQA-pmd'], basepath + '_' + jsonData.buildInfo.modules.getAt(0).moduleName + '_pmd_build.xml', [module_name: jsonData.buildInfo.modules.getAt(0).moduleName])
                }

                if (modulesArr.getAt(0).codeAnalysis && modulesArr.getAt(0).codeAnalysis.contains(Constants.FINDBUGS)) {
                    prepareFindBugsAntXml(jsonData.buildInfo.modules.getAt(0).moduleName, jsonData.buildInfo.modules.getAt(0).relativePath, envVar)
                    Ant.invokeAnt(delegate, ['findbugs-report'], basepath + '_' + jsonData.buildInfo.modules.getAt(0).moduleName + '_findbugs_build.xml', [module_name: jsonData.buildInfo.modules.getAt(0).moduleName])
                }

                if (modulesArr.getAt(0).ossMailRecipients) {


                    prepareOssComplianceXml(modulesArr.getAt(0).moduleName, modulesArr.getAt(0).relativePath, modulesArr.getAt(0).ossMailRecipients, modulesArr.getAt(0).ossDistributionType, modulesArr.getAt(0).ossAnalysisType, username, envVar)
                    Ant.invokeAnt(delegate, 'OSSCheck', basepath + '_' + modulesArr.getAt(0).moduleName + '_oss_build.xml')
                }
            }
        }
    }

    /*
     * this method is used to add required wrappers
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
     * This method is used to get the list of required customtools
     */

    private static String getListOfTools(modulesArr) {

        String toolList = 'TG_MAVEN_POM_EDITOR_HOME'

        def checkstyleFlag = false
        def pmdFlag = false
        def findbugsFlag = false
        def ossCheck = false;

        for (int i = 0; i < modulesArr.size(); i++) {

            if (modulesArr.getAt(i).codeAnalysis && modulesArr.getAt(i).codeAnalysis.contains(Constants.CHECKSTYLE))
                checkstyleFlag = true
            if (modulesArr.getAt(i).codeAnalysis && (modulesArr.getAt(i).codeAnalysis.contains(Constants.PMD) || modulesArr.getAt(i).codeAnalysis.contains(Constants.ICQA)))
                pmdFlag = true
            if (modulesArr.getAt(i).codeAnalysis && modulesArr.getAt(i).codeAnalysis.contains(Constants.FINDBUGS))
                findbugsFlag = true
            if (modulesArr.getAt(i).ossMailRecipients)
                ossCheck = true
        }

        if (checkstyleFlag || pmdFlag || findbugsFlag) {
            toolList += ',ANT_HOME'
        }
        if (checkstyleFlag) {
            toolList += ',CHECKSTYLE_HOME'
        }
        if (pmdFlag) {
            toolList += ',PMD5_0_4_HOME'
        }
        if (findbugsFlag) {
            toolList += ',FINDBUGS_HOME'
        }
        if (ossCheck)
            toolList += ',OSS_HOME'

        return toolList
    }

    /*
     * This method is used to add the files to copy from jenkins master usercontent to slave workspace
     */

    private static String filesToCopy(modulesArr) {

        String copyPattern = ''

        for (int i = 0; i < modulesArr.size(); i++) {

            if (modulesArr.getAt(i).codeAnalysis && modulesArr.getAt(i).codeAnalysis.contains(Constants.CHECKSTYLE))
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_checkstyle_build.xml,')
            if (modulesArr.getAt(i).codeAnalysis && (modulesArr.getAt(i).codeAnalysis.contains(Constants.PMD) || modulesArr.getAt(i).codeAnalysis.contains(Constants.ICQA)))
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_pmd_build.xml,')
            if (modulesArr.getAt(i).codeAnalysis && modulesArr.getAt(i).codeAnalysis.contains(Constants.FINDBUGS))
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_findbugs_build.xml,')
            if (modulesArr.getAt(i).ossMailRecipients)
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_oss_build.xml,')

        }
        return copyPattern
    }

    /*
    * this method is used to prepare Checkstyle Ant xml file for runnig Checkstyle analysis
    */

    private static void prepareCheckStyleAntXml(moduleName, relativePath, envVar) {

        String fileContent = ReadFile.run('/ant_templates/java_checkstyle_maven.xml')

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }

        fileContent = fileContent.replace('$PNAME', moduleName)
        fileContent = fileContent.replace('$CheckStyleReports', '${IDP_WS}/' + moduleDir + '/target')
        fileContent = fileContent.replace('$CheckStyleReportConvert', '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + '/' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CSCONVERT))


        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + moduleName + '_checkstyle_build.xml', fileContent)
    }

    /*
   * this method is used to prepare PMD Ant xml file for runnig PMD analysis
   */

    private static void preparePMDAntXml(moduleName, relativePath, envVar) {

        String fileContent = ReadFile.run('/ant_templates/java_pmd_maven.xml')

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }

        fileContent = fileContent.replace('$PNAME', moduleName)
        fileContent = fileContent.replace('$LMHOMESRC', '${IDP_WS}/' + moduleDir)
        fileContent = fileContent.replace('$PMDHOME', '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.PMDCTNM))
        fileContent = fileContent.replace('$PMDReportPath', '${IDP_WS}/' + moduleDir + '/target')


        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + moduleName + '_pmd_build.xml', fileContent)
    }

    /*
   * this method is used to prepare findbugs Ant xml file for runnig findbugs analysis
   */

    private static void prepareFindBugsAntXml(moduleName, relativePath, envVar) {

        String fileContent = ReadFile.run('/ant_templates/java_findbugs_maven.xml')

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }

        fileContent = fileContent.replace('$PNAME', moduleName)
        fileContent = fileContent.replace('$FindBugsHome', '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.FBCTNM))
        fileContent = fileContent.replace('$FindBugsReports', '${IDP_WS}/' + moduleDir + '/target')


        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + moduleName + '_findbugs_build.xml', fileContent)
    }

    /*
     * This method is used to run OSS compliance check
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
