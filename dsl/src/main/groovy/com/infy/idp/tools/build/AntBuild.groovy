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
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.tools.*
import com.infy.idp.utils.*
import com.infy.idp.utils.Constants
import com.infy.idp.utils.fs.*

/**
 *
 * This class has the method to create the build job based for the Java/J2EE (Ant based)s technology
 *
 */

class AntBuild {

    static String basepath

    /*
    * This method is used to add publishsers required for AngularJS
    */

    public static void addPublishers(context, jsonData) {

        context.with {

            publishers {

                def modulesArr = jsonData.buildInfo.modules
                CheckStylePublisher checkStylePublisher = new CheckStylePublisher()
                checkStylePublisher.generatePattern(jsonData)
                checkStylePublisher.add(delegate, jsonData)

                PmdPublisher pmdPublisher = new PmdPublisher()
                pmdPublisher.generatePattern(jsonData)
                pmdPublisher.add(delegate, jsonData)

                FindBugsPublisher findBugsPublisher = new FindBugsPublisher()
                findBugsPublisher.generatePattern(jsonData)
                findBugsPublisher.add(delegate, jsonData)

                String pattern = generatePattern(jsonData)

                if (pattern != '') {
                    ArchiveArtifacts archiveArtifacts = new ArchiveArtifacts()
                    archiveArtifacts.setPattern(pattern)
                    archiveArtifacts.add(delegate, jsonData)
                }

                def cobFlag = false
                for (mod in jsonData.buildInfo.modules) {
                    if (cobFlag)
                        break
                    if (mod.codeCoverage && mod.codeCoverage == 'on')
                        cobFlag = true
                }
                if (cobFlag) {
                    def coberturaPublisher = new CoberturaPublisher()
                    coberturaPublisher.updatePattern('**/*coverage.xml,**/*coverage-summary.xml')
                    coberturaPublisher.add(delegate, jsonData)
                }

                invokeViolationsPublisher(delegate, jsonData)

                //archiveJUnit
                String junitPattern = ''
                for (mod in modulesArr) {
                    if (mod.unitTesting != 'on')
                        continue

                    def relativePath = mod.relativePath.replace('\\', '/')
                    def moduleDir
                    if (relativePath.contains('/')) {
                        moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
                    } else {
                        moduleDir = relativePath
                    }

                    junitPattern += (moduleDir + '/Junit_Report/*.xml,')
                }
                if (junitPattern != '') {
                    JUnitArchive jUnitArchive = new JUnitArchive()
                    jUnitArchive.setPattern(junitPattern)
                    jUnitArchive.invokeJUnitArchive(delegate,jsonData)
                }

                //Call for Archive Artifacts
                for (mod in modulesArr) {
                    if (mod.team) {
                        ArchiveArtifacts archiveArtifactsCheck = new ArchiveArtifacts()
                        archiveArtifactsCheck.setPattern('**/Checkmarx/Reports/**')
                        archiveArtifactsCheck.add(delegate, jsonData)
                        break
                    } else {
                        continue
                    }
                }

            }
        }
    }

    /*
     * This method is used to generate the pattern for conditional builds
     */

    public static String generatePattern(jsonData) {

        String pattern = ''

        for (int i = 0; i < jsonData.buildInfo.modules.size(); i++) {

            def relativePath = jsonData.buildInfo.modules.getAt(i).relativePath.replace('\\', '/')
            def moduleDir
            if (relativePath.contains('/')) {
                moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
            } else {
                moduleDir = relativePath
            }

            if (jsonData.buildInfo.modules.getAt(i).codeAnalysis.contains(Constants.CHECKSTYLE)) {
                pattern += (',' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.CSREPORTNAME) + '/*.xml')
                pattern += (',' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.CSREPORTNAME) + '/*.html')
            }

            if (jsonData.buildInfo.modules.getAt(i).codeAnalysis.contains(Constants.PMD)) {
                pattern += (',' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.PMDREPORTNAME) + '/*.xml')
                pattern += (',' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.PMDREPORTNAME) + '/*.html')
            }

            if (jsonData.buildInfo.modules.getAt(i).codeAnalysis.contains(Constants.FINDBUGS)) {
                pattern += (',' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.FBREPORTNAME) + '/*.xml')
                pattern += (',' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.FBREPORTNAME) + '/*.html')
            }

            if (jsonData.buildInfo.modules.getAt(i).unitTesting && jsonData.buildInfo.modules.getAt(i).unitTesting == 'on' && jsonData.buildInfo.modules.getAt(i).unitTestDir && jsonData.buildInfo.modules.getAt(i).unitTestDir != '') {
                pattern += (',' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.JUNITREPORTNAME) + '/*')
            }

            if (jsonData.buildInfo.modules.getAt(i).codeCoverage && jsonData.buildInfo.modules.getAt(i).codeCoverage == 'on') {
                pattern += (',' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.COBERTURAXMLREPORT) + '/*.xml')
                pattern += (',' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.COBERTURAHTMLREPORT) + '/*')
            }
        }

        return pattern
    }

    /*
     * this method is used to configure conditional steps
     */

    private static void addSteps(context, jsonData, envVar) {

        context.with {

            steps {

                addConditionalSteps(delegate, jsonData, envVar);
            }
        }
    }

    /*
     * this method adds the conditonal steps
     */

    private static void addConditionalSteps(context, jsonData, envVar) {

        SonarRunner sonarRunner = new SonarRunner()
        def modulesArr = jsonData.buildInfo.modules
        def username = jsonData.basicInfo.userName;
        username = username + '@xyz.com'
        context.with {

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
                    runner { fail() }

                    conditionalbuilders {

                        def targets
                        def buildFile
                        def relativePath = modulesArr.getAt(i).relativePath.replace('\\', '/')
                        def moduleDir
                        if (relativePath.contains('/')) {
                            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
                        } else {
                            moduleDir = relativePath
                        }


                        if (modulesArr.getAt(i).compile == 'on') {

                            targets = 'compile'
                            buildFile = basepath + '_' + modulesArr.getAt(i).moduleName + '_compile_build.xml'
                            prepareCompileAntXml(modulesArr.getAt(i).moduleName, modulesArr.getAt(i).relativePath, envVar)

                        } else {

                            targets = modulesArr.getAt(i).args
                            buildFile = modulesArr.getAt(i).customBuildXml
                        }

                        if (isValidField(targets) && isValidField(buildFile))
                            ConditionalAnt.invokeConditionalAnt(delegate, targets, buildFile, '', 'module_name=' + modulesArr.getAt(i).moduleName)

                        if (codeQltyOn(modulesArr.getAt(i).codeAnalysis, Constants.SONAR)) {

                            def moduleName = modulesArr.getAt(i).moduleName

        if (null != jsonData.buildInfo.sonarUserName && !''.equalsIgnoreCase(jsonData.buildInfo.sonarUserName) && null != jsonData.buildInfo.sonarPassword && !''.equalsIgnoreCase(jsonData.buildInfo.sonarPassword)) {
            sonarRunner.setProperties('sonar.login=' + jsonData.buildInfo.sonarUserName + '\nsonar.password=' + jsonData.buildInfo.sonarPassword + '\nsonar.host.url=' + jsonData.buildInfo.sonarUrl + '\nsonar.projectKey=' + jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName + '\nsonar.projectName=' + moduleName + '\nsonar.projectVersion=$RELEASE_IDENTIFIER' + '\nsonar.sources=' + moduleDir)
        } else {
            sonarRunner.setProperties('sonar.host.url=' + jsonData.buildInfo.sonarUrl + '\nsonar.projectKey=' + jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName + '\nsonar.projectName=' + moduleName + '\nsonar.projectVersion=$RELEASE_IDENTIFIER' + '\nsonar.sources=' + moduleDir)
        }
        sonarRunner.add(delegate, jsonData)
                        }

                        if (codeQltyOn(modulesArr.getAt(i).codeAnalysis, Constants.PMD)) {

                            preparePMDAntXml(modulesArr.getAt(i).moduleName, modulesArr.getAt(i).relativePath, envVar)
                            ConditionalAnt.invokeConditionalAnt(delegate, 'pmd ICQA-pmd', basepath + '_' + modulesArr.getAt(i).moduleName + '_pmd_build.xml', '', 'module_name=' + modulesArr.getAt(i).moduleName)
                        }


                        if (codeQltyOn(modulesArr.getAt(i).codeAnalysis, Constants.CHECKSTYLE)) {

                            prepareCheckStyleAntXml(modulesArr.getAt(i).moduleName, modulesArr.getAt(i).relativePath, envVar)
                            ConditionalAnt.invokeConditionalAnt(delegate, 'checkstyle-report', basepath + '_' + modulesArr.getAt(i).moduleName + '_checkstyle_build.xml', '', 'module_name=' + modulesArr.getAt(i).moduleName)
                        }

                        if (codeQltyOn(modulesArr.getAt(i).codeAnalysis, Constants.FINDBUGS)) {

                            prepareFindBugsAntXml(modulesArr.getAt(i).moduleName, modulesArr.getAt(i).relativePath, envVar)
                            ConditionalAnt.invokeConditionalAnt(delegate, 'findbugs', basepath + '_' + modulesArr.getAt(i).moduleName + '_findbugs_build.xml', '', 'module_name=' + modulesArr.getAt(i).moduleName)
                        }


                        if (modulesArr.getAt(i).unitTesting && modulesArr.getAt(i).unitTesting == 'on' && modulesArr.getAt(i).unitTestDir && modulesArr.getAt(i).unitTestDir != '') {
                            invokeUnitTesting(delegate, modulesArr, i, envVar)

                        }



                        if (modulesArr.getAt(i).codeCoverage && modulesArr.getAt(i).codeCoverage == 'on') {
                            prepareCoberturaAntXml(modulesArr.getAt(i).moduleName, modulesArr.getAt(i).relativePath, envVar)
                            ConditionalAnt.invokeConditionalAnt(delegate, 'cobcoverage', basepath + '_' + modulesArr.getAt(i).moduleName + '_cobertura_build.xml', '', 'module_name=' + modulesArr.getAt(i).moduleName)
                        }

                        if (modulesArr.getAt(i).pafFilePath && modulesArr.getAt(i).servMachine) {
                            prepareAppscanFiles(modulesArr.getAt(i).relativePath, envVar)
                            prepareAppscanCliFiles(modulesArr.getAt(i).pafFilePath, modulesArr.getAt(i).relativePath, modulesArr.getAt(i).moduleName, modulesArr.getAt(i).servMachine, envVar)
                            ExecuteCmd.invokeCmd(delegate, "cd " + basepath + "\n powershell -command .\\rep.ps1", jsonData.basicInfo.buildServerOS)
                            ConditionalAnt.invokeConditionalAnt(delegate, 'ounceCli', basepath + '/build.xml', '-DAppScanInstallationFolder=$APPSCAN_INSTALLATION_FOLDER', 'module_name=' + modulesArr.getAt(i).moduleName)
                        }


                        if (modulesArr.getAt(i).javaMainClass) {

                            prepareJarAntXml(modulesArr.getAt(i).moduleName, modulesArr.getAt(i).relativePath, modulesArr.getAt(i).javaMainClass, envVar)
                            ConditionalAnt.invokeConditionalAnt(delegate, 'jar', basepath + '_' + modulesArr.getAt(i).moduleName + '_jar_build.xml', '', 'module_name=' + modulesArr.getAt(i).moduleName)

                        }
                        if (modulesArr.getAt(i).ejbDescriptor) {

                            prepareEjbAntXml(modulesArr.getAt(i).moduleName, modulesArr.getAt(i).relativePath, modulesArr.getAt(i).ejbDescriptor, envVar)
                            ConditionalAnt.invokeConditionalAnt(delegate, 'ejbjar', basepath + '_' + modulesArr.getAt(i).moduleName + '_ejb_build.xml', '', 'module_name=' + modulesArr.getAt(i).moduleName)

                        }



                        if (modulesArr.getAt(i).warPackaging) {

                            prepareWARAntXml(modulesArr.getAt(i).moduleName, modulesArr.getAt(i).relativePath, modulesArr.getAt(i).warPackaging, envVar)
                            ConditionalAnt.invokeConditionalAnt(delegate, 'war', basepath + '_' + modulesArr.getAt(i).moduleName + '_war_build.xml', '', 'module_name=' + modulesArr.getAt(i).moduleName)
                        }


                        if (modulesArr.getAt(i).team) {

                            checkMark(jsonData, modulesArr, i)
                        }

                        if (modulesArr.getAt(i).ossMailRecipients) {


                            prepareOssComplianceXml(modulesArr.getAt(i).moduleName, modulesArr.getAt(i).relativePath, modulesArr.getAt(i).ossMailRecipients, modulesArr.getAt(i).ossDistributionType, modulesArr.getAt(i).ossAnalysisType, username, envVar)
                            ConditionalAnt.invokeConditionalAnt(delegate, 'OSSCheck', basepath + '_' + modulesArr.getAt(i).moduleName + '_oss_build.xml', '', 'module_name=' + modulesArr.getAt(i).moduleName)
                        }

                    }
                }
            }

        }

    }

    /*
     * This method is used to add the checkMark tool
     */

    /* private void checkMark(jsonData, modulesArr, i) {
         Checkmarx checkmarx = new Checkmarx();
         checkmarx.setProjectKey(jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName)
         checkmarx.setUser(modulesArr.getAt(i).userName)
         checkmarx.setPass(modulesArr.getAt(i).password)
         checkmarx.setTeamName(modulesArr.getAt(i).team)
         checkmarx.setScanPreset(modulesArr.getAt(i).preset)
         checkmarx.setDirectory(modulesArr.getAt(i).exclude)
         checkmarx.setIncremental(modulesArr.getAt(i).incrementalScan)

         if (modulesArr.getAt(i).interval) {
             checkmarx.setScaneSchedule(true)
             checkmarx.setScanCycle(modulesArr.getAt(i).interval.toInteger())
         } else {
             checkmarx.setScaneSchedule(false)
             checkmarx.setScanCycle("10".toInteger())
         }
         checkmarx.add(delegate)
     }*/

    

    /*
     * This method is used to check codequality tools selection
     */

    private static def codeQltyOn(ca, tool) {
        (ca.contains(tool))
    }

    /*
     * This method is used to cresate ant build job
     */

    public static void antBuildJobCreation(context, jsonData, envVar, basepathArg) {

        basepath = basepathArg

        context.with {
            //Standard IDP Settings
            configure { it / canRoam(false) }
            concurrentBuild(true)

            //Configurable Settings of Job
            addWrappers(delegate, jsonData)
            addSteps(delegate, jsonData, envVar)

            // Post Build Actions
            addPublishers(delegate, jsonData)

            //Optional settings of job
            String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
            if (customWS) customWorkspace('$IDP_WS')
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
     * This method is used to check the field is valid or not
     */

    private static boolean isValidField(field) {

        return (field && field != '')
    }

    /*
     * This method is used to add unit testing options
     */

    private static invokeUnitTesting(context, modulesArr, index, envVar) {
        prepareJunitAntXml(modulesArr.getAt(index).moduleName, modulesArr.getAt(index).relativePath, modulesArr.getAt(index).unitTestDir, envVar)
        if (modulesArr.getAt(index).unitTestDir.endsWith(".java")) {
            ConditionalAnt.invokeConditionalAnt(context, 'junitone', basepath + '_' + modulesArr.getAt(index).moduleName + '_junit_build.xml', '', 'module_name=' + modulesArr.getAt(index).moduleName)
        } else {
            ConditionalAnt.invokeConditionalAnt(context, 'junitall', basepath + '_' + modulesArr.getAt(index).moduleName + '_junit_build.xml', '', 'module_name=' + modulesArr.getAt(index).moduleName)
        }
    }

    /*
     * This method is used to get the list of required customtools
     */

    private static String getListOfTools(modulesArr) {

        String toolList = 'ANT_HOME'
        def csFlag = false
        def fbFlag = false
        def pmdFlag = false
        def jUnitFlag = false
        def coberturaFlag = false
        def ossCheck = false

        for (int i = 0; i < modulesArr.size(); i++) {

            if (csFlag && fbFlag && pmdFlag && jUnitFlag && coberturaFlag)
                break

            if (codeQltyOn(modulesArr.getAt(i).codeAnalysis, Constants.PMD))
                pmdFlag = true

            if (codeQltyOn(modulesArr.getAt(i).codeAnalysis, Constants.CHECKSTYLE))
                csFlag = true

            if (codeQltyOn(modulesArr.getAt(i).codeAnalysis, Constants.FINDBUGS))
                fbFlag = true

            if (modulesArr.getAt(i).compile == 'on' || (modulesArr.getAt(i).unitTesting && modulesArr.getAt(i).unitTesting == 'on' && modulesArr.getAt(i).unitTestDir && modulesArr.getAt(i).unitTestDir != ''))
                jUnitFlag = true

            if (modulesArr.getAt(i).codeCoverage && modulesArr.getAt(i).codeCoverage == 'on')
                coberturaFlag = true

            if (modulesArr.getAt(i).ossMailRecipients)
                ossCheck = true

        }

        if (pmdFlag)
            toolList += ',PMD5_0_4_HOME'
        if (csFlag)
            toolList += ',CHECKSTYLE_HOME'
        if (fbFlag)
            toolList += ',FINDBUGS_HOME'
        if (jUnitFlag)
            toolList += ',JUNIT_HOME'
        if (coberturaFlag)
            toolList += ',COBERTURA_HOME'
        if (ossCheck)
            toolList += ',OSS_HOME'
        return toolList

    }

    /*
     * this method is used to add violations publisher
     */

    private static void invokeViolationsPublisher(context, jsonData) {

        ViolationPublisher violationPublisher = new ViolationPublisher()
        def flag = false

        for (mod in jsonData.buildInfo.modules) {


            def relativePath = mod.relativePath.replace('\\', '/')
            def moduleDir
            if (relativePath.contains('/')) {
                moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
            } else {
                moduleDir = relativePath
            }

            if (codeQltyOn(mod.codeAnalysis, Constants.PMD)) {
                violationPublisher.setPmdPattern(violationPublisher.getPmdPattern() + ',' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.PMDREPORTNAME) + '/*.xml')
                flag = true
            }

            if (codeQltyOn(mod.codeAnalysis, Constants.CHECKSTYLE)) {
                violationPublisher.setCsPattern(violationPublisher.getCsPattern() + ',' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.CSREPORTNAME) + '/*.xml')
                flag = true
            }

            if (codeQltyOn(mod.codeAnalysis, Constants.FINDBUGS)) {
                violationPublisher.setFbPattern(violationPublisher.getFbPattern() + ',' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.FBREPORTNAME) + '/*.xml')
                flag = true
            }
        }

        if (flag)
            violationPublisher.add(context, jsonData)

    }

    /*
     * This method is used to add the files to copy from jenkins master usercontent to slave workspace
     */

    private static String filesToCopy(modulesArr) {

        String copyPattern = ''

        for (int i = 0; i < modulesArr.size(); i++) {

            if (modulesArr.getAt(i).pafFilePath && modulesArr.getAt(i).servMachine)
                copyPattern += (basepath + '/ant.xml,' + basepath + '/antcliscript.txt,' + basepath + '/rep.ps1,')

            if (modulesArr.getAt(i).compile == 'on')
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_compile_build.xml,')

            if (modulesArr.getAt(i).unitTesting && modulesArr.getAt(i).unitTesting == 'on' && modulesArr.getAt(i).unitTestDir && modulesArr.getAt(i).unitTestDir != '')
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_junit_build.xml,')

            if (modulesArr.getAt(i).codeCoverage && modulesArr.getAt(i).codeCoverage == 'on')
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_cobertura_build.xml,')

            if (codeQltyOn(modulesArr.getAt(i).codeAnalysis, Constants.PMD))
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_pmd_build.xml,')

            if (codeQltyOn(modulesArr.getAt(i).codeAnalysis, Constants.CHECKSTYLE))
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_checkstyle_build.xml,')

            if (codeQltyOn(modulesArr.getAt(i).codeAnalysis, Constants.FINDBUGS))
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_findbugs_build.xml,')

            if (modulesArr.getAt(i).warPackaging)
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_war_build.xml,')


            if (modulesArr.getAt(i).javaMainClass)
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_jar_build.xml,')


            if (modulesArr.getAt(i).ejbDescriptor)
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_ejb_build.xml,')

            if (modulesArr.getAt(i).ossMailRecipients)
                copyPattern += (basepath + '_' + modulesArr.getAt(i).moduleName + '_oss_build.xml,')
        }

        return copyPattern
    }

    /*
     * This method is used to prepare Compile Ant xml for code compilation
     */

    private static String prepareCompileAntXml(moduleName, relativePath, envVar) {

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }
        String fileContent = ReadFile.run('/ant_templates/java_compile_ant.xml')

        fileContent = fileContent.replace('$LMHOMESRC', '${IDP_WS}/' + relativePath)
        fileContent = fileContent.replace('$LIB', '${IDP_WS}')
        fileContent = fileContent.replace('$JUNITLIB', '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, 'JUNIT'))
        fileContent = fileContent.replace('$BIN', '${IDP_WS}/' + moduleDir + '/bin')
        fileContent = fileContent.replace('$PNAME', moduleName)

        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + moduleName + '_compile_build.xml', fileContent)
    }

    /*
     * this method is used to prepareJunit Ant xml file for runnig junit test cases
     */

    private static String prepareJunitAntXml(moduleName, relativePath, junitSrcPath, envVar) {

        String fileContent = ReadFile.run('/ant_templates/junit_ant.xml')

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }

        fileContent = fileContent.replace('$PNAME', moduleName)
        fileContent = fileContent.replace('$LMHOMESRC', '${IDP_WS}/' + relativePath)
        fileContent = fileContent.replace('$JUNIT_REPORT', '${IDP_WS}/' + moduleDir + '/Junit_Report')
        fileContent = fileContent.replace('$JUnitHomeLocation', '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, 'JUNIT'))

        fileContent = fileContent.replace('$BIN', '${IDP_WS}/' + moduleDir + "/bin")
        junitSrcPath = junitSrcPath.replace('\\', '/')

        if (junitSrcPath.endsWith(".java")) {
            fileContent = fileContent.replace('$TESTONE', junitSrcPath.substring(0, junitSrcPath.length() - 5).replace("/", "."))
        } else {
            fileContent = fileContent.replace('$TESTALL', junitSrcPath)
        }

        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + moduleName + '_junit_build.xml', fileContent)
    }

    /*
    * this method is used to prepare Cobertura Ant xml file for runnig Cobertura
    */

    private static String prepareCoberturaAntXml(moduleName, relativePath, envVar) {

        String fileContent = ReadFile.run('/ant_templates/java_cobertura_ant.xml')

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }

        fileContent = fileContent.replace('$PNAME', moduleName)
        fileContent = fileContent.replace('$LMHOMESRC', '${IDP_WS}/' + relativePath)

        fileContent = fileContent.replace('$JUNIT_REPORT', '${IDP_WS}/' + moduleDir + '/Junit_Report')

        fileContent = fileContent.replace('$COBDIR', '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, 'COBERTURA'))
        fileContent = fileContent.replace('$INSTRDIR', '${IDP_WS}/' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, 'COBERTURAINSTR'))
        fileContent = fileContent.replace('$COBCOVXML', '${IDP_WS}/' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, 'COBERTURAXMLREPORTS'))
        fileContent = fileContent.replace('$COBCOVSUMXML', '${IDP_WS}/' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, 'COBERTURASUMMARYXML'))
        fileContent = fileContent.replace('$COBCOVHTMLDIR', '${IDP_WS}/' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, 'COBERTURAHTMLREPORTS'))

        fileContent = fileContent.replace('$JUnitHomeLocation', '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, 'JUNIT'))
        fileContent = fileContent.replace('$BIN', '${IDP_WS}/' + moduleDir + "/bin")

        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + moduleName + '_cobertura_build.xml', fileContent)
    }

    /*
    * this method is used to prepare AppScan Ant xml file for runnig AppScan
    */

    private static String prepareAppscanFiles(relativePath, envVar) {

        String fileContent = ReadFile.run('/ant_templates/Appscan_Ant.xml')

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }

        fileContent = fileContent.replace('$SRC', relativePath)
        fileContent = fileContent.replace('$base', basepath)



        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '/ant.xml', fileContent)
        fileContent = ReadFile.run('/ant_templates/rep.ps1')
        WriteFile.run(jHome + '/userContent/' + basepath + '/rep.ps1', fileContent)
    }

    /*
     * This method is used to prepare AppScan clil files to run appscan through cli
     */

    private static String prepareAppscanCliFiles(pafFile, relativePath, projName, servname, envVar) {

        String fileContent = ReadFile.run('/ant_templates/antcliscript.txt')

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }

        fileContent = fileContent.replace('$pafName', pafFile)
        fileContent = fileContent.replace('$projName', projName)
        fileContent = fileContent.replace('$Serv', servname)




        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '/antcliscript.txt', fileContent)
    }

    /*
    * this method is used to prepare PMD Ant xml file for runnig PMD analysis
    */

    private static void preparePMDAntXml(moduleName, relativePath, envVar) {

        String fileContent = ReadFile.run('/ant_templates/java_pmd_ant.xml')

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }

        fileContent = fileContent.replace('$PNAME', moduleName)
        fileContent = fileContent.replace('$LMHOMESRC', '${IDP_WS}/' + relativePath)
        fileContent = fileContent.replace('$PMDHOME', '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.PMDCTNM))
        fileContent = fileContent.replace('$PMDReportPath', '${IDP_WS}/' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.PMDREPORTNAME))


        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + moduleName + '_pmd_build.xml', fileContent)
    }

    /*
    * this method is used to prepare Checkstyle Ant xml file for runnig Checkstyle analysis
    */

    private static void prepareCheckStyleAntXml(moduleName, relativePath, envVar) {

        String fileContent = ReadFile.run('/ant_templates/java_checkstyle_ant.xml')

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }

        fileContent = fileContent.replace('$PNAME', moduleName)
        fileContent = fileContent.replace('$LMHOMESRC', '${IDP_WS}/' + relativePath)
        fileContent = fileContent.replace('$CheckStyleHome', '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CSCTNM))
        fileContent = fileContent.replace('$CheckStyleReports', '${IDP_WS}/' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.CSREPORTNAME))
        fileContent = fileContent.replace('$CheckStyleReportConvert', '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + '/' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CSCONVERT))


        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + moduleName + '_checkstyle_build.xml', fileContent)
    }

    /*
   * this method is used to prepare findbugs Ant xml file for runnig findbugs analysis
   */

    private static void prepareFindBugsAntXml(moduleName, relativePath, envVar) {

        String fileContent = ReadFile.run('/ant_templates/java_findbugs_ant.xml')

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }

        fileContent = fileContent.replace('$PNAME', moduleName)
        fileContent = fileContent.replace('$LMHOMESRC', '${IDP_WS}/' + relativePath)
        fileContent = fileContent.replace('$FindBugsHome', '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.FBCTNM))
        fileContent = fileContent.replace('$FindBugsReports', '${IDP_WS}/' + moduleDir + PropReader.readProperty(Constants.REPORTNAMESFN, Constants.FBREPORTNAME))
        fileContent = fileContent.replace('$BIN', '${IDP_WS}/' + moduleDir + "/bin")


        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + moduleName + '_findbugs_build.xml', fileContent)
    }

    /*
     * this method is used to generate war file for the artifacts
     */

    private static void prepareWARAntXml(moduleName, relativePath, warPackaging, envVar) {

        String fileContent = ReadFile.run('/ant_templates/java_war_ant.xml')

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }

        fileContent = fileContent.replace('$PNAME', moduleName)
        fileContent = fileContent.replace('$LMHOME', '${IDP_WS}')
        fileContent = fileContent.replace('$WARDIR', '${IDP_WS}/' + moduleDir + "/WAR")
        fileContent = fileContent.replace('$WARPath', '${IDP_WS}/' + moduleDir + "/WAR/" + moduleName + ".war")

        def warFileSetArr, warFileSet, libPathArr, libPath


        if (warPackaging.contains("WEB-INF")) {
            warFileSetArr = warPackaging.split("/WEB-INF");
            warFileSet = warFileSetArr[0];
            libPathArr = warPackaging.split("/web.xml");
            libPath = libPathArr[0];
        }

        fileContent = fileContent.replace('$WebXmlPath', warPackaging)
        fileContent = fileContent.replace('$WARFILESET', warFileSet)
        fileContent = fileContent.replace('$LibPath', libPath)
        fileContent = fileContent.replace('$BIN', '${IDP_WS}/' + moduleDir + "/bin")

        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + moduleName + '_war_build.xml', fileContent)
    }

    /*
     * this method is used to generate Jar file for the artifacts
     */

    private static void prepareJarAntXml(moduleName, relativePath, mainClass, envVar) {

        String fileContent = ReadFile.run('/ant_templates/java_jar_ant.xml')

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }

        fileContent = fileContent.replace('$PNAME', moduleName)
        fileContent = fileContent.replace('$JARDIR', '${IDP_WS}/' + moduleDir + "/Jar")
        fileContent = fileContent.replace('$JARFOLDER', '${IDP_WS}/' + moduleDir + "/Jar/" + moduleName + ".jar")
        fileContent = fileContent.replace('$BIN', '${IDP_WS}/' + moduleDir + "/bin")
        fileContent = fileContent.replace('$MAINCLASS', mainClass)

        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + moduleName + '_jar_build.xml', fileContent)
    }

    /*
     * this method is used to generate EAR file for the artifacts
     */

    private static void prepareEjbAntXml(moduleName, relativePath, ejbDescriptor, envVar) {

        String fileContent = ReadFile.run('/ant_templates/java_ejb_ant.xml')

        relativePath = relativePath.replace('\\', '/')
        def moduleDir
        if (relativePath.contains('/')) {
            moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
        } else {
            moduleDir = relativePath
        }

        fileContent = fileContent.replace('$PNAME', moduleName)
        fileContent = fileContent.replace('$EJBDIR', '${IDP_WS}/' + moduleDir + "/EJB")
        fileContent = fileContent.replace('$EJBJARXML', '${IDP_WS}/' + moduleDir + "/" + ejbDescriptor)

        fileContent = fileContent.replace('$BIN', '${IDP_WS}/' + moduleDir + "/bin")

        def jHome = envVar.JENKINS_HOME
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + moduleName + '_ejb_build.xml', fileContent)
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
}//userName
