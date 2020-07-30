/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.testing
import java.util.List;
import java.util.Map;
import org.infy.idp.entities.jobs.common.AntProperties;
import com.infy.idp.plugins.buildsteps.SshBuilder
import com.infy.idp.utils.*
import com.infy.idp.tools.*
import com.infy.idp.customtools.*
import jenkins.model.*
import com.infy.idp.plugins.publishers.ArchiveArtifacts;
//import com.infy.idp.plugins.publishers.RobotPublisher
import com.infy.idp.plugins.wrappers.Xvfb
import com.infy.idp.plugins.wrappers.BuildNameSetter
import com.infy.idp.plugins.wrappers.*


/**
 *
 * This class has the method to add AcceleraFT testing tool
 *
 */

class TestJobCreator {

    static ArchiveArtifacts archiveArtifacts = new ArchiveArtifacts()

    /*
     * This method is used to create test job for the selected test tool
     */

    public static void testJobCreator(context, jsonData, envIndex, stepIndex, envVar) {

        String basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName

        context.with {

            //Standard IDP Settings
            configure { it / canRoam(false) }
            concurrentBuild(true)



            addProperties(delegate, jsonData, envIndex, stepIndex)

            addWrappers(delegate, context, jsonData, envIndex, stepIndex)
            addSteps(delegate, jsonData, envIndex, stepIndex, envVar)
            addPublishers(delegate, jsonData, envIndex, stepIndex)

            //Optional settings of job
            String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
            if (customWS) {
                customWorkspace('$IDP_WS')
            }
        }
    }

    /*
     * This method is used to add properties for parameterized build
     */

    private static void addProperties(context, jsonData, envIndex, stepIndex) {

        context.with {

            properties {

                PropertiesAdder.addGitLabConnection(delegate)
                PropertiesAdder.addRebuild(delegate)
                PropertiesAdder.addThrottleJobProperty(delegate)
            }
            configure {

                it / 'properties' / 'hudson.model.ParametersDefinitionProperty' / 'parameterDefinitions' {

                    def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
                    def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test

                    PropertiesAdder.addStringParam(delegate, 'IDP_WS', 'NA', 'CUSTOM Workspace')
                    PropertiesAdder.addStringParam(delegate, 'ARTIFACT_NAME', 'NA', '')
                    PropertiesAdder.addStringParam(delegate, 'TEST_PARAM', 'NA', 'This parameter describe extra test parameter')
                    PropertiesAdder.addStringParam(delegate, 'BUILD_NO', 'NA', '')
                    PropertiesAdder.addStringParam(delegate, 'RELEASE_NO', 'NA', 'This parameter tells what is the release number')
                    PropertiesAdder.addStringParam(delegate, 'ENVIRONMENT', 'NA', 'This parameter tells in which we have to test')
                    PropertiesAdder.addStringParam(delegate, 'BUILD_MODE', 'Debug', 'This parameter tells what is the release number')
                    //Adding pipeline build id
                    PropertiesAdder.addStringParam(delegate, 'PIPELINE_BUILD_ID', 'NA', '')
                    PropertiesAdder.addStringParam(delegate, 'BUILD_LABEL', 'NA', '')
                    //Adding job build  id
                    PropertiesAdder.addStringParam(delegate, 'JOB_BUILD_ID', 'NA', '')
                    PropertiesAdder.addNodeParam(delegate, 'SLAVE_NODE', ['ldaproleapp_Jmvn1_14Mr1_Slave'], '')

					if(jsonData.code.technology.toString().equalsIgnoreCase(Constants.SAP))
					{
						PropertiesAdder.addStringParam(delegate, 'APP_SERVER', 'NA', 'This parameter tells the application server name')
						PropertiesAdder.addStringParam(delegate, 'INSTANCE', 'NA', 'This parameter tells the instance name')
						PropertiesAdder.addStringParam(delegate, 'CLIENT', 'NA', 'This parameter tells the client name')
						PropertiesAdder.addStringParam(delegate, 'SAP_USERID', 'NA', 'This parameter tells the SAP User Name')
						PropertiesAdder.addStringParam(delegate, 'SAP_PASSWORD', 'NA', 'This parameter tells the SAP password')
						PropertiesAdder.addStringParam(delegate, 'LANGUAGE', 'NA', 'This parameter tells which ')
					}
                    if (jsonData.testInfo && jsonData.testInfo.testEnv && jsonData.testInfo.testEnv[envIndex]) {
                        if (jsonData.testInfo.testEnv[envIndex].testSteps && jsonData.testInfo.testEnv[envIndex].testSteps[stepIndex] && jsonData.testInfo.testEnv[envIndex].testSteps[stepIndex].test) {
                            def stepName = jsonData.testInfo.testEnv[envIndex].testSteps[stepIndex].stepName

                            def testStepValues = jsonData.testInfo.testEnv[envIndex].testSteps[stepIndex].test

                            if (testStepValues.testTypeName.equalsIgnoreCase(Constants.MTM)) {
                                PropertiesAdder.addStringParam(delegate, 'MTM_TESTSUITE_ID', 'NA', 'This parameter is for comma separated test suit ids ')
                                PropertiesAdder.addStringParam(delegate, 'MTM_TEST_PLAN_ID', 'NA', 'This parameter is for test plan which ')
                                PropertiesAdder.addStringParam(delegate, 'MTM_PROJECT_NAME', testStepValues.projectName, 'This parameter is for projectName')
                                PropertiesAdder.addStringParam(delegate, 'MTM_BUILD_DEF_ID', testStepValues.buildDefId, 'This parameter is for build def id')
                                PropertiesAdder.addStringParam(delegate, 'REPORT_PATH', testStepValues.path, 'This parameter is for report path')
                                PropertiesAdder.addStringParam(delegate, 'STEP_NAME', stepName, 'This parameter is for step name')
                                PropertiesAdder.addStringParam(delegate, 'APPLICATION_NAME', jsonData.basicInfo.applicationName, 'This parameter is for application name ')
                                PropertiesAdder.addStringParam(delegate, 'IDP_PROJECT_NAME', 'NA', 'This parameter is only for MTM')
                            }
                        }
                    }


                    if (testStepElem && !testStepElem.toString().equals('{}') && testStepElem.testTypeName.toString().equalsIgnoreCase(Constants.SAHI)) {


                        PropertiesAdder.addStringParam(delegate, 'SahiTestSuitePath', testStepElem.path, 'This is the path of sahi test suite')

                        PropertiesAdder.addStringParam(delegate, 'SahiTestURL', testStepElem.serverUrl, 'This is the URL against which test cases will get executed')
                        PropertiesAdder.addStringParam(delegate, 'SahiTestBrowser', testStepElem.browserName, 'This is the browser name in which test will get executed')

                    }

                    if (testStepElem && !testStepElem.toString().equals('{}') && testStepElem.testTypeName.toString().equalsIgnoreCase(Constants.CADEVTEST)) {


                        PropertiesAdder.addStringParam(delegate, 'CA_USER', testStepElem.userName, 'CA server User Name')
                        PropertiesAdder.addPasswordParam(delegate, 'CA_PASS', testStepElem.password, 'CA server Credential')

                    }


                }
            }
        }
    }

    /*
     * This method is used to add the steps to configure test job
     */

    public static void addSteps(context, jsonData, envIndex, stepIndex, envVar) {

        addRunScriptSteps(context, jsonData, envIndex, stepIndex)
        addTestSteps(context, jsonData, envIndex, stepIndex, envVar)
    }

    /*
     * This method is used to add run scripts in test job
     */

    public static void addRunScriptSteps(context, jsonData, envIndex, stepIndex) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def runScriptElem = testEnvElem.testSteps.getAt(stepIndex).runScript

        if (!runScriptElem || runScriptElem.toString().equals('{}'))
            return
        context.with {
            steps {
                switch (runScriptElem.scriptType.toString()) {

                    case Constants.ANTSCRIPT:
                        def properties = ConvertArrtoString(runScriptElem.antPropertiesArr);
                        // REPLACE WITH A FUNCTION CALL LATER FOR ADDING THE WRAPPER
                        context.with {
                            wrappers {
                                toolEnvBuildWrapper { vars('ANT_HOME') }
                            }
                        }
                        Ant.invokeAnt(delegate, runScriptElem.targets, runScriptElem.pathOfFile, properties, runScriptElem.javaOptions)
                        break
                    case Constants.BATCHSCRIPT:
                        batchFile(runScriptElem.pathOfFile)
                        break
                    case Constants.SHELLSCRIPT:
                        shell(runScriptElem.pathOfFile)
                        break
                    case Constants.PSSCRIPT:
                        powerShell(runScriptElem.pathOfFile)
                        break
                    case Constants.SSH_EXECUTION:
//						context.with{
//							steps{
                        AddCredentials.run(runScriptElem.host, runScriptElem.userName, runScriptElem.password)
                        SshBuilder ssh = new SshBuilder()
                        if (runScriptElem.flattenFilePath != null && runScriptElem.flattenFilePath == "off") {
                            ssh.flattenFilePath = "false"
                        }
                        addHost(runScriptElem.host, runScriptElem.host, runScriptElem.userName)
                        ssh.invokeSshBuilder(delegate, runScriptElem.host, runScriptElem.userName, runScriptElem.password, runScriptElem.pathToFiles, runScriptElem.destinationDir, runScriptElem.script, runScriptElem.sshKey, runScriptElem.sshKeyPath)
//							}
//						}
                        break
                }
            }
        }
    }

    /*
     * this method is used to add host details in jenkins job
     */

    private static void addHost(name, hostname, username) {
        def inst = Jenkins.getInstance()
        def publish_ssh = inst.getDescriptor("jenkins.plugins.publish_over_ssh.BapSshPublisherPlugin")
        publish_ssh.removeHostConfiguration(name)
        def configuration = publish_ssh.class.classLoader.loadClass("jenkins.plugins.publish_over_ssh.BapSshHostConfiguration").newInstance(name, hostname, username, '', '/', 22, 300000, false, '', '', false)
        publish_ssh.addHostConfiguration(configuration)
        publish_ssh.save()
    }

    /*
     * This method is used to add test steps corresponding to test tool
     */

    public static void addTestSteps(context, jsonData, envIndex, stepIndex, envVar) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test

        if (!testStepElem || testStepElem.toString().equals('{}'))
            return

        context.with {

            steps {

                switch (testStepElem.testTypeName.toString()) {

                    case Constants.SOAPUI:
                        SoapUIST.addSteps(delegate, jsonData, envIndex, stepIndex)
                        break
                    case Constants.JMETER:
                        JMeter.invokeJMeter(delegate, testStepElem.testPlan, jsonData.basicInfo.buildServerOS, testStepElem.externalFilePath)
                        break
                    case Constants.SELENIUM:

                        SeleniumFT.addSteps(delegate, jsonData, envIndex, stepIndex, envVar)

                        break
                    case Constants.APPSCAN:
                        AppScanAutomatorCT.invokeAppScanAutomatorCT(delegate, testStepElem.folderUrl, testStepElem.userName, testStepElem.password, testStepElem.projectName, jsonData, envVar, envIndex, stepIndex)
                        break
                    case Constants.OATS:
                        Oats.addSteps(delegate, jsonData, testStepElem)
                        break
                    case Constants.MONKEYTALK:
                        MonkeyTalk.addSteps(delegate, jsonData, testStepElem, envVar, envIndex, stepIndex)
                        break
                    case Constants.ROBOT:
                        RobotFT.addSteps(delegate, testStepElem.rootDir, testStepElem.testCase, jsonData.basicInfo.buildServerOS)
                        break
                    case Constants.ACCELERA:
                        AcceleraFT.addSteps(delegate, testStepElem.path, testStepElem.testCase)
                        break
                    case Constants.QUALITIA:
                        QualitiaFT.addSteps(delegate, jsonData.basicInfo.buildServerOS, testStepElem.rootDir, testStepElem.testPlan, testStepElem.browserName, jsonData.ssoId, testStepElem.version, testStepElem.projectName)
                        break
                    case Constants.ITOPS:
                        ITopsFT.addSteps(delegate, testStepElem.projectLocation, testStepElem.folderUrl, testStepElem.parameters)
                        break
                    case Constants.IFAST:
                        IFastST.addSteps(delegate, testStepElem.projectName, testStepElem.userName, jsonData, envIndex, stepIndex)
                        break

                    case Constants.ECATT:
                        ECATT.eCATTJobCreation(context, jsonData, envIndex, stepIndex)
                        break
                    case Constants.PROTRATCOR:
                        Protractor.addSteps(delegate, testStepElem.testPlan, jsonData.basicInfo.buildServerOS)
                        break
                    case Constants.SAHI:
                        SAHI.invokeSahiDashboard(delegate, jsonData, jsonData.basicInfo.buildServerOS)
                        SAHI.invokeSahiTestrunner(delegate, jsonData, jsonData.basicInfo.buildServerOS)
                        break
                    case Constants.MTM:
                        MTM.MTMJobCreation(delegate)
                        break
                    case Constants.RPT:
                        RPT.addSteps(context, jsonData, envIndex, stepIndex, envVar)
                        break
                    case Constants.HPUFT:
                        HpUftFT.addSteps(context, jsonData, envIndex, stepIndex);
                        HpUftFT.addPublishers(context, jsonData)
                        break;

                    case Constants.CADEVTEST:
                        CADevTest.addSteps(delegate, jsonData, envIndex, stepIndex, envVar)
                        break
                    case Constants.PARASOFTSOA:
                        ParasoftSOATest.addSteps(delegate, jsonData, envIndex, stepIndex)
                        break
                    case Constants.ALM:
                        HpAlmFT.addSteps(context, jsonData, envIndex, stepIndex)
                        break
                    case Constants.RFT:
                        RFT.addSteps(delegate, jsonData, envIndex, stepIndex, envVar)
                        break

                }
            }
        }

    }

    /*
     * This method is used to add required wrappers
     */

    private static void addWrappers(context, jobContext, jsonData, envIndex, stepIndex) {


        addTestWrappers(context, jobContext, jsonData, envIndex, stepIndex)
    }

    /*
     * This method is used to add required test wrappers
     */

    private static void addTestWrappers(context, jobContext, jsonData, envIndex, stepIndex) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test

        if (!testStepElem || testStepElem.toString().equals('{}'))
            return

        context.with {

            wrappers {

			BuildEnv env = new BuildEnv()
				env.add(delegate, jsonData)
                // add X11 support for linux environment
                if (jsonData.basicInfo.buildServerOS == Constants.UNIXOS && testStepElem.testCategory == Constants.FUNCTEST && testStepElem.testTypeName.toString() != Constants.MONKEYTALK)
                    new Xvfb().add(delegate, jsonData)

                switch (testStepElem.testTypeName.toString()) {

                    case Constants.SELENIUM:

                        SeleniumFT.addWrappers(delegate, jsonData, envIndex, stepIndex)

                        break
                    case Constants.APPSCAN:
                        AppScanAutomatorCT.addWrappers(delegate, jsonData, envIndex, stepIndex)
                        break
                    case Constants.MONKEYTALK:
                        MonkeyTalk.addWrappers(delegate, jsonData, envIndex, stepIndex)
                        break

                    case Constants.ACCELERA:
                        AcceleraFT.addWrappers(delegate)
                        break
                    case Constants.PROTRATCOR:
                        Protractor.addWrappers(delegate, jsonData)
                        break
                    case Constants.MTM:
                        MTM.addWrappers(delegate)
                        break;
                    case Constants.RPT:
                        RPT.addWrappers(delegate, jsonData)
                        break
                    case Constants.CADEVTEST:
                        CADevTest.addWrappers(delegate, jsonData, envIndex, stepIndex)
                        break

                    case Constants.RFT:
                        RFT.addWrappers(delegate, jsonData, envIndex, stepIndex)
                        break

                }
            }
        }

        // adding displayName attribute in xvfb plugin

        jobContext.with {

            if (jsonData.basicInfo.buildServerOS == Constants.UNIXOS && testStepElem.testCategory == Constants.FUNCTEST && testStepElem.testTypeName.toString() != Constants.MONKEYTALK) {

                configure {
                    (it / buildWrappers / 'org.jenkinsci.plugins.xvfb.XvfbBuildWrapper').appendNode('displayName', '4')
                }
            }
        }
    }

    /*
     * This method is used to add required publishers
     */

    private static void addPublishers(context, jsonData, envIndex, stepIndex) {

        addRunScriptPublishers(context, jsonData, envIndex, stepIndex)
        addTestPublishers(context, jsonData, envIndex, stepIndex)
    }

    /*
    * This method is used to add required run script publishers
    */

    private static void addRunScriptPublishers(context, jsonData, envIndex, stepIndex) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def runScriptElem = testEnvElem.testSteps.getAt(stepIndex).runScript

        if (!runScriptElem || runScriptElem.toString().equals('{}'))
            return
        context.with {
            publishers {

                if (runScriptElem.pathOfReports && runScriptElem.pathOfReports != "") {

                    ArchiveArtifacts archiveArtifacts = new ArchiveArtifacts()
                    archiveArtifacts.updatePattern(runScriptElem.pathOfReports + '**/*.*')
                    archiveArtifacts.add(delegate, jsonData)
                }
            }
        }
    }

    /*
    * This method is used to add required test publishers
    */

    private static void addTestPublishers(context, jsonData, envIndex, stepIndex) {

        def testEnvElem = jsonData.testInfo.testEnv.getAt(envIndex)
        def testStepElem = testEnvElem.testSteps.getAt(stepIndex).test

        if (!testStepElem || testStepElem.toString().equals('{}'))
            return

        context.with {

            publishers {

                switch (testStepElem.testTypeName.toString()) {

                    case Constants.SOAPUI:
                        SoapUIST.addPublishers(delegate, jsonData)
                        break
                    case Constants.QUALITIA:
                        QualitiaFT.addPublishers(delegate, jsonData, envIndex, stepIndex)
                        break
                    case Constants.ROBOT:
                        RobotFT.addPublishers(delegate, jsonData, envIndex, stepIndex)
                        break
                    case Constants.ITOPS:
                        ITopsFT.addPublishers(delegate, jsonData)
                        break
                    case Constants.IFAST:
                        IFastST.addPublishers(delegate, jsonData, envIndex, stepIndex)
                        break
                    case Constants.SELENIUM:

                        SeleniumFT.addPublishers(delegate, jsonData, envIndex, stepIndex)

                        break
                    case Constants.ACCELERA:
                        AcceleraFT.addPublishers(delegate, jsonData, envIndex, stepIndex)
                        break
                    case Constants.JMETER:
                        JMeter.addPublishers(delegate, jsonData, envIndex, stepIndex)
                        break
                    case Constants.PROTRATCOR:
                        Protractor.addPublishers(delegate, jsonData)
                        break
                    case Constants.RPT:
                        RPT.addPublishers(delegate, jsonData, envIndex, stepIndex)
                        break
                    case Constants.CADEVTEST:
                        CADevTest.addPublishers(delegate, jsonData, envIndex, stepIndex)
                        break

                }
            }
        }
    }

    /*
     * This method is used to convert array to string map
     */

    private static Map<String, String> ConvertArrtoString(List<AntProperties> antPropArr) {

        Map<String, String> map = new HashMap<String, String>();

        if (null != antPropArr) {
            for (def i = 0; i < antPropArr.size(); i++) {

                map.put(antPropArr[i].antKey, antPropArr[i].antValue);


            }
        }


        return map;
    }
}
