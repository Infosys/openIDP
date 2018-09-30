/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.testing

import com.infy.idp.plugins.publishers.ArchiveArtifacts
import com.infy.idp.plugins.publishers.TestNG
import com.infy.idp.plugins.publishers.XUnitPublisher
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.ReadFile
import com.infy.idp.utils.fs.WriteFile

/**
 *
 * This class has the method to add SeleniumFT testing tool
 *
 */
class SeleniumFT {

    /*
     * This method is used to add required wrappers
     */

    public static void addWrappers(context, jsonData, envIndex, stepIndex) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test
        def tools
        def filesToCopy

        if (isValid(testStepElem.frameWork)) {

            def basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName

            if (testStepElem.frameWork == Constants.JUNIT) {

                tools = 'ANT_HOME,SELENIUM_HOME,JUNIT_HOME'
                filesToCopy = basepath + '_' + testEnvElem.envName + '_' + testEnvElem.testSteps.getAt(stepIndex).stepName + '_selenium_junit_build.xml'

            } else if (testStepElem.frameWork == Constants.TESTNG) {

                tools = 'ANT_HOME,SELENIUM_HOME,TESTNG_HOME'
                filesToCopy = basepath + '_' + testEnvElem.envName + '_' + testEnvElem.testSteps.getAt(stepIndex).stepName + '_selenium_testng_build.xml'

            }
        }

        if (tools && filesToCopy) {
            (new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(context, tools)
            CopyToSlave.invokeCopyToSlave(context, filesToCopy)
        }

    }

    /*
    * This method is used to add the steps to configure SeleniumFT
    */

    public static void addSteps(context, jsonData, envIndex, stepIndex, envVar) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test

        if (isValid(testStepElem.scriptPath) && isValid(testStepElem.targets)) {

            //Maven.invokeMaven(context, testStepElem.targets, testStepElem.scriptPath)

        } else {

            def basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName

            if (isValid(testStepElem.frameWork) && testStepElem.frameWork == Constants.JUNIT) {

                prepareSeleniumJUnitAntXml(jsonData, envIndex, stepIndex, envVar)
                Ant.invokeAnt(context, 'JunitTest', basepath + '_' + testEnvElem.envName + '_' + testEnvElem.testSteps.getAt(stepIndex).stepName + '_selenium_junit_build.xml')

            } else if (isValid(testStepElem.frameWork) && testStepElem.frameWork == Constants.TESTNG) {

                prepareSeleniumTestNGAntXml(jsonData, envIndex, stepIndex, envVar)
                Ant.invokeAnt(context, 'testNGTest', basepath + '_' + testEnvElem.envName + '_' + testEnvElem.testSteps.getAt(stepIndex).stepName + '_selenium_testng_build.xml')

            }
        }
    }


    public static boolean isValid(field) {
        return (field && field != '')
    }

    /*
     * This method is used to add required publishers
     */

    public static void addPublishers(context, jsonData, envIndex, stepIndex) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test
        ArchiveArtifacts archiveArtifacts = new ArchiveArtifacts()
        TestNG testNG = new TestNG()

        if (isValid(testStepElem.scriptPath) && isValid(testStepElem.targets)) {

            def relativePath = testStepElem.scriptPath
            relativePath = relativePath.replace('\\', '/')
            def moduleDir
            if (relativePath.contains('/')) {
                moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
            } else {
                moduleDir = relativePath
            }

            testNG.setPattern('**/TestNG_Report/*.xml')
            testNG.add(context, jsonData)

            //archiveArtifacts.setPattern('**/test-output/**')
            if (!moduleDir.contains('/') && moduleDir.equalsIgnoreCase('pom.xml')) {
                archiveArtifacts.setPattern('test-output/**')
            } else {
                archiveArtifacts.setPattern(moduleDir + '/test-output/**')
            }


        } else {

//			def basepath = jsonData.basicInfo.applicationName  + '_' + jsonData.basicInfo.pipelineName			

            if (isValid(testStepElem.frameWork) && testStepElem.frameWork == Constants.JUNIT) {

                XUnitPublisher xUnitPublisher = new XUnitPublisher()
                xUnitPublisher.setPattern('**/SeleniumJunit_Report/*.xml')
                xUnitPublisher.add(context, jsonData)

                //archiveArtifacts.setPattern('**/SeleniumJunit_Report/*.xml')
                archiveArtifacts.setPattern(testStepElem.projectName + '/SeleniumJunit_Report/*.xml')

            } else if (isValid(testStepElem.frameWork) && testStepElem.frameWork == Constants.TESTNG) {

                testNG.setPattern('**/TestNG_Report/*.xml')
                testNG.add(context, jsonData)

                //archiveArtifacts.setPattern('**/test-output/**')
                //archiveArtifacts.setPattern(testStepElem.projectName + '/test-output/**')
                archiveArtifacts.setPattern(testStepElem.projectName + '/TestNG_Report/**')

            }
        }

        archiveArtifacts.add(context, jsonData)

    }

    /*
     * This method is used to prepare Selenium TestNG Xml file
     */

    private static String prepareSeleniumTestNGAntXml(jsonData, envIndex, stepIndex, envVar) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test

        String fileContent = ReadFile.run('/ant_templates/selenium_testng_ant.xml')

        def selJarPath = '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, 'SELENIUMJAR')
        def testNGJarPath = '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, 'TESTNGJAR')

        fileContent = fileContent.replace('$PNAME', testStepElem.projectName)

        def prjctHome = ''
        if (testStepElem.projectName && testStepElem.projectName != '/')
            prjctHome = testStepElem.projectName

        def libPath = ''
        if (testStepElem.externalFilePath && testStepElem.externalFilePath != '')
            libPath = testStepElem.externalFilePath

        fileContent = fileContent.replace('$LMHOME', '${IDP_WS}/' + prjctHome)
        fileContent = fileContent.replace('$LMSRC', '${IDP_WS}/' + prjctHome + '/' + testStepElem.rootDir)
        fileContent = fileContent.replace('$LMLIB', '${IDP_WS}/' + prjctHome + '/' + libPath)

        fileContent = fileContent.replace('$SeleniumJarPath', selJarPath)
        fileContent = fileContent.replace('$SelJPath', selJarPath.substring(0, selJarPath.lastIndexOf('/')))

        fileContent = fileContent.replace('$TestngJarPath', testNGJarPath)
        fileContent = fileContent.replace('$TestngJPath', testNGJarPath.substring(0, testNGJarPath.lastIndexOf('/')))

        if (testStepElem.folderUrl && testStepElem.folderUrl != '') {
            fileContent = fileContent.replace('$AppUrl', testStepElem.folderUrl)
        } else {
            fileContent = fileContent.replace('$AppUrl', ' ')
        }

        fileContent = fileContent.replace('$TestCase', testStepElem.testSuiteName)

        def jHome = envVar.JENKINS_HOME
        def basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + testEnvElem.envName + '_' + testEnvElem.testSteps.getAt(stepIndex).stepName + '_selenium_testng_build.xml', fileContent)
    }

    /*
     * This method is used to prepare Selenium Junit Xml file
     */

    private static String prepareSeleniumJUnitAntXml(jsonData, envIndex, stepIndex, envVar) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test

        String fileContent = ReadFile.run('/ant_templates/selenium_junit_ant.xml')

        def selJarPath = '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, 'SELENIUMJAR')
        def jUnitJarPath = '${IDP_WS}/../..' + PropReader.readProperty(Constants.CUSTOMTOOLFN, Constants.CUSTOMTOOLPATH) + PropReader.readProperty(Constants.CUSTOMTOOLFN, 'JUNITJAR')

        fileContent = fileContent.replace('$PNAME', testStepElem.projectName)

        def prjctHome = ''
        if (testStepElem.projectName && testStepElem.projectName != '/')
            prjctHome = testStepElem.projectName

        def libPath = ''
        if (testStepElem.externalFilePath && testStepElem.externalFilePath != '')
            libPath = testStepElem.externalFilePath

        fileContent = fileContent.replace('$LMHOME', '${IDP_WS}/' + prjctHome)
        fileContent = fileContent.replace('$LMSRC', '${IDP_WS}/' + prjctHome + '/' + testStepElem.rootDir)
        fileContent = fileContent.replace('$LMLIB', '${IDP_WS}/' + prjctHome + '/' + libPath)

        fileContent = fileContent.replace('$SeleniumJarPath', selJarPath)
        fileContent = fileContent.replace('$JunitJarPath', jUnitJarPath)
        fileContent = fileContent.replace('$SelJPath', selJarPath.substring(0, selJarPath.lastIndexOf('/')))
        fileContent = fileContent.replace('$JunitJPath', jUnitJarPath.substring(0, jUnitJarPath.lastIndexOf('/')))

        if (testStepElem.folderUrl && testStepElem.folderUrl != '') {
            fileContent = fileContent.replace('$AppUrl', testStepElem.folderUrl)
        } else {
            fileContent = fileContent.replace('$AppUrl', ' ')
        }

        if (testStepElem.testSuiteName && testStepElem.testSuiteName != '' && testStepElem.testSuiteName.endsWith(".java"))
            fileContent = fileContent.replace('$TestCase', testStepElem.testSuiteName.minus(".java"))
        else if (testStepElem.testSuiteName && testStepElem.testSuiteName != '')
            fileContent = fileContent.replace('$TestCase', testStepElem.testSuiteName)

        def jHome = envVar.JENKINS_HOME
        def basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
        WriteFile.run(jHome + '/userContent/' + basepath + '_' + testEnvElem.envName + '_' + testEnvElem.testSteps.getAt(stepIndex).stepName + '_selenium_junit_build.xml', fileContent)
    }
}
