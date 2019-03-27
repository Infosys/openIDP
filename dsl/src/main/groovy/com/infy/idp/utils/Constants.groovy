/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.utils

/**
 *
 * This class defines all the constants for comparison
 *
 */
class Constants {

    static final GITSCM = "git"
    static final TFSSCM = "tfs"
    static final SVNSCM = "svn"
    static final RTCSCM = "rtc"
    static final CLEARCASE = "clearcase"
    static final CCBASE = "base"
    static final CCUCM = "ucm"
    static final IBMRTCSCM = "ibmrtc"
    static final IBMRTCSTREAM = "stream"
    static final IBMRTCBWS = "repositoryWorkspace"
    static final IBMRTCBD = "buildDefinition"
    static final GITLABREPOBROWSER = "gitlab"
    static final GITHUBREPOBROWSER = "github"
    static final GITBLITREPOBROWSER = "gitblit"
    static final BITBUCKET = "bitbucket"
    static final ITAWFUNCTIONAL = "functional"
    static final ITAWCOMPONENT = "component"
    static final ITAW = "itaw"
    static final HPALM = "hpalm"
    static final RIT = "rit"
    static final JENKINS = "jenkins"
    static final IDP = "idp"


    static final TESTASSET = "TESTASSET"
    static final PRETEST = "PRETEST"
    static final POSTTEST = "POSTTEST"
    static final TESTASSETJOBNAMEPOSTFIX = "_TestAsset"
    static final PRETESTJOBNAME = "_PreTest"
    static final POSTTESTJOBNAME = "_PostTest"

    static final FILESYSTEMSCM = "filesystem"
    static final WINDOWSOS = "windows"
    static final UNIXOS = "linux"
    static final FILENET = "fileNet"
    static final JAVAMAVENTECH = "J2EE/Maven"
    static final JAVA_GRADLE_TECH = "J2EE/Gradle"
    static final OATS = "oats"
    static final MONKEYTALK = "monkeyTalk"
    static final JAVAANTTECH = "J2EE/Ant"
    static final CSDOTNETTECH = "dotNetCsharp"
    static final VBDOTNETTECH = "dotNetVb"
    static final NODEJSTECH = "nodeJs"
    static final BIGDATATECH = "bigData"
    static final GODATATECH = "go"
    static final SIEBELTECH = "siebel"
    static final ANDROIDGRADLETECH = "androidGradle"
    static final DBDEPLOY = "dbDeploy"
    static final IISDEPLOY = "iis"
    static final IBMBPM = "IBMBPM"
    static final TIBCO = "tibco"
    static final IBMSterling = "IBMSterlingOMS"
    static final MAXIMO = "maximo"
    static final SAPPI = "SAPPI"
    static final IIBTECH = "iib"
    static final PEGATECH = "pega"
    static final MWEBTECH = "mWeb"
    static final MULEESB = "muleESB"
    static final ORACLEEBS = "oracleEBS"
    static final IOSOBJECTIVEC = "iOS(Objective C)"
    static final IOSSWIFT = "iOS(Swift)"
    static final ANGULARJSTECH = "angular"
    static final REACTJSTECH = "reactjs"
    static final PYTHON = "python"
    static final MSSQL = "mssql"
    static final IBMSI = "ibmsi"
    static final INFORMATICA = "informatica"
    static final SAP = "SapNonCharm"
    static final NEXUS = "nexus"
    static final GENERAL = "general"
    static final PHP = "php"
    static final SAHI = "sahi"
    static final EARDEPLOY = "earDeploy"
    static final SALESFORCE = "salesforce"
    static final HPUFT = "hpUft"
    static final FXCOP = "fxcop"
    static final SONAR = "sonar"
    static final PMD = "pmd"
    static final ICQA = "icqa"
    static final CHECKSTYLE = "checkStyle"
    static final FINDBUGS = "findBugs"
    static final ANDROIDLINT = "androidLint"
    static final JMETER = "jMeter"
    static final SELENIUM = "selenium"
    static final JUNIT = "jUnit"
    static final TESTNG = "testNG"
    static final APPSCAN = "appscan"
    static final ACCELERA = "accelera"
    static final ROBOT = "robot"
    static final QUALITIA = "qualitia"
    static final ITOPS = "iTops"
    static final IFAST = "iFast"
    static final SOAPUI = "soapUI"
    static final ANTSCRIPT = "ant"
    static final SHELLSCRIPT = "shellScript"
    static final BATCHSCRIPT = "batchScript"
    static final PSSCRIPT = "powerShell"
    static final SSH_EXECUTION = "sshExecution"
    static final BATCH = "batch"
    static final PS = "power"
    static final SHELL = "shell"
    static final ECATT = "eCATT"
    static final RPT = "rpt"
    static final ALM = "hpAlm"
    static final PROTRATCOR = "protractor"
    static final MTM = "mtm"
    static final CADEVTEST = "caDevTest"
    static final CASV = "casv"
    static final PARASOFTSOA = "parasoftsoa"
    static final RFT = "rft"
    static final IBMRQMTEST = "ibmrqm"


    static final FUNCTEST = "functional"
    static final SRVCTEST = "service"
    static final PRFMNCTEST = "performance"

    static final CHROME = "chrome"
    static final IE = "ie"
    static final FIREFOX = "firefox"

    static final BDHIVE = "hive"
    static final BDPIG = "pig"
    static final BDSCALA = "scala"
    static final BDSCALACS = "scalaStyle"

    static final SCM = "SCM"
    static final BUILD = "Build"
    static final DEPLOY = "Deploy"
    static final TEST = "Test"
    static final STROY = "story"
    static final CAST = "Cast"
    static final REBASE = "Rebase"

    static final SCMJOBNAMEPOSTFIX = "_SCM"
    static final STORYJOBNAMEPOSTFIX = "_STORY"
    static final SCM_DEPLOY_JOBNAMEPOSTFIX = "_SCM_DEPLOY"
    static final SCM_TEST_JOBNAMEPOSTFIX = "_SCM_TEST"
    static final GITTAGJOBNAMEPOSTFIX = "_GitTag"
    static final BUILDJOBNAMEPOSTFIX = "_Build"
    static final CASTJOBNAMEPOSTFIX = "_Cast"
    static final ARTIFACTDOWNLOADJOBNAMEPOSTFIX = "_Artifact_Download"
    static final DEPLOYJOBNAMEPOSTFIX = "_Deploy"
    static final TESTJOBNAMEPOSTFIX = "_Test"
    static final PIPELINEJOBNAMEPOSTFIX = "_Pipeline"
    static final DASHBOARDJOBNAMEPOSTFIX = "_Dashboard"
    static final WORKSPACENAMEPOSTFIX = "_WS"
    static final REBASEJOBNAMEPOSTFIX = "_Rebase"

    static final PREBUILD = "preBuild"
    static final POSTBUILD = "postBuild"

    static final PMDREPORTFORMAT = "net.sourceforge.pmd.renderers.XMLRenderer"
    static final ICQAREPORTFORMAT = "net.sourceforge.pmd.renderers.TGHTMLRenderer"
    static
    final PMDRULES = "rulesets/jsp/basic-jsf.xml,rulesets/jsp/basic.xml,rulesets/java/android.xml,rulesets/java/basic.xml,rulesets/java/braces.xml,rulesets/java/clone.xml,rulesets/java/codesize.xml,rulesets/java/comments.xml,rulesets/java/controversial.xml,rulesets/java/coupling.xml,rulesets/java/empty.xml,rulesets/java/finalizers.xml,rulesets/java/imports.xml,rulesets/java/j2ee.xml,rulesets/java/javabeans.xml,rulesets/java/junit.xml,rulesets/java/migrating.xml,rulesets/java/naming.xml,rulesets/java/optimizations.xml,rulesets/java/strictexception.xml,rulesets/java/strings.xml,rulesets/java/sunsecure.xml,rulesets/java/typeresolution.xml,rulesets/java/unnecessary.xml,rulesets/java/unusedcode.xml"
    static final ICQARULES = "rulesets/java/infymetrics.xml"
    static final CSCONFIGFN = "config/sun_checks.xml"
    static final CSREPORTFORMAT = "xml"
    static final MAINFRAME = "mainframe"
    static final CUSTOMTOOLFN = "CustomTool.properties"
    static final CUSTOMTOOLPATH = "TOOLPATH"
    static final PMDCTNM = "PMD"
    static final CSCTNM = "CHECKSTYLE"
    static final CSCONVERT = "CHECKSTYLE_REPORT"
    static final FBCTNM = "FINDBUGS"
    static final SELENIUMCTJARNM = "SELENIUMJAR"
    static final JUNITCTJARNM = "JUNITJAR"
    static final RFTJARNM = "RFTJAR"
    static final IBMRQMJARNM = "IBMRQMTESTTOOL"
    static final SONARURL = "SONAR_URL"
    static final SONARUSERNAME = "SONAR_USERNAME"
    static final SONARPASSWORD = "SONAR_PASSWORD"

    static final REPORTNAMESFN = "ReportName.properties"
    static final PMDREPORTNAME = "PMDREPORT"
    static final CSREPORTNAME = "CHECKSTYLEREPORT"
    static final FBREPORTNAME = "FINDBUGSREPORT"
    static final SELENIUMREPORTNAME = "SELENIUMJUNITREPORT"
    static final JUNITREPORTNAME = "JUNITREPORT"
    static final COBERTURAXMLREPORT = "COBERTURAXMLREPORTS"
    static final COBERTURAHTMLREPORT = "COBERTURAHTMLREPORTS"


    static final CONFIGFN = "Config.properties"

    static final DSLPROPSFN = "DSL.properties"
    static final CHECKMARX_SERVER_URL = "CHECKMARX_SERVER_URL"
    //Constants for grunt file
    static final GRUNT_FUNC_START = "module.exports = function(grunt) {\ngrunt.initConfig({\n"
    static
    final GRUNT_MOCHE = "node_mocha: {\n\n  without_coverage : {\nsrc : ['\$SRC_PATH_TEST'],\noptions : {\nmochaOptions : {\n globals : ['expect'],\n timeout : 3000,\n" +
            "ignoreLeaks : false,\n ui : 'bdd',\n reporter : 'landing'\n }\n }\n} ," +
            "with_coverage: {\n src : ['\$SRC_PATH_TEST'],\n options : {\n mochaOptions : {\n globals : ['expect'],\n timeout : 3000,\n  ignoreLeaks : false,\n" +
            "ui : 'bdd',\n },\n reportFormats : ['cobertura','html'],\n runCoverage : true ,\n output: 'coverage/cobertura.xml'\n}\n}\n}\n";
    static final GRUNT_JSHINT = "jshint: {\n one:{\n" +
            "all:['\$SRC_PATH_JS', '\$SRC_PATH_HTML'],\noptions: {\njshintrc: true,\nignores: ['\$SRC_NODE_PATH],\n" +
            "reporter: require('jshint-jenkins-checkstyle-reporter'),\nreporterOutput: 'jshint-report/report-jshint-checkstyle.xml'\n}},\n\n" +
            "\n two:{\n" +
            "all:['\$SRC_PATH_JS', '\$SRC_PATH_HTML'],\noptions: {\njshintrc: true,\nignores: ['\$SRC_NODE_PATH],\n" +
            "reporter: require('jshint-html-reporter'),\nreporterOutput: 'jshint-report/report-jshint-checkstyle.html'\n}}\n\n" +
            "}"
    static
    final GRUNT_UGLYFY = "uglify: {\nscripts: {\nexpand: true,\nsrc: '\$SRC_PATH_UGLIFY',\ndest: 'build/',\n ext: '.min.js'\n},\n}"
    static final GRUNT_CONFIG_END = "});\n\n"
    static final GRUNT_JSHIN_LOAD = "grunt.loadNpmTasks('grunt-contrib-jshint');\n" +
            "grunt.loadNpmTasks('jshint-jenkins-checkstyle-reporter');\n" +
            "grunt.loadNpmTasks('jshint-html-reporter');\n"
    static final GRUNT_MOCHA_LOAD = "grunt.loadNpmTasks('grunt-node-mocha');\n"
    static final GRUNT_UGLYFY_LOAD = "grunt.loadNpmTasks('grunt-contrib-uglify');\n"
    static final GRUNT_FUNC_END = "\n};\n"
    //Variables for periodically build trigger
    static final HOURS = 'Hours'
    static final MINUTES = 'Minutes'
    static final DAY_OF_MONTH = 'DayOfMonth'
    static final DAY_OF_WEEK = 'DayOfWeek'
    static final MONTH = 'Month'
    static final SUDO = 'sudo'

    //Variables for RPT analysis
    static final ECLIPSEPRODUCT = 'com.ibm.rational.rpt.product.ide'
    static final RUNNERPLUGIN = 'org.eclipse.ant.core.antRunner'
    static final RPTDUMMYPROJECT = 'dummy'
    static final RPTDUMMYSCHEDULEPATH = 'Schedules/sc1'
}
