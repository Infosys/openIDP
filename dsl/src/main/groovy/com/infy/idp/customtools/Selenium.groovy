/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.customtools

import com.infy.idp.creators.*
import com.infy.idp.utils.*
import com.infy.idp.tools.*

/**
 *
 * This class includes the method for adding Selenium
 *
 */

class Selenium {

	/*
    * This function is used to add the commands to run Selenium
    */

	public static void invokeTool(context, prjctName, testCase, os) {
		String seleniumBaseDir
		String seleniumClasspath;
		String  jUnitClasspath;
		if (os.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
			seleniumBaseDir = '%IDP_WS%/' + prjctName
			seleniumClasspath = seleniumBaseDir + '/bin;%IDP_WS%/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/Selenium/Selenium/selenium-server-standalone-2.43.0.jar;%IDP_WS%/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/Junit/junit-4.10.jar'
			jUnitClasspath = seleniumClasspath + ';%IDP_WS%/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/Junit/reporting-junit-runner-2.jar;%IDP_WS%/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/Junit/org.apache.commons.io.jar;%IDP_WS%/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/Junit/dom4j-1.5.jar;%IDP_WS%/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/Junit/commons-cli-1.0.jar'
		}
		else{
			seleniumBaseDir = '$IDP_WS/' + prjctName
			seleniumClasspath = seleniumBaseDir + '/bin;$IDP_WS/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/Selenium/Selenium/selenium-server-standalone-2.43.0.jar;$IDP_WS/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/Junit/junit-4.10.jar'
			jUnitClasspath = seleniumClasspath + ';$IDP_WS/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/Junit/reporting-junit-runner-2.jar;$IDP_WS/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/Junit/org.apache.commons.io.jar;$IDP_WS/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/Junit/dom4j-1.5.jar;$IDP_WS/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/Junit/commons-cli-1.0.jar'
		}
		
		
		StringBuilder sb = new StringBuilder()				
		sb.append('javac ')
		sb.append('-cp ' + seleniumClasspath + ' ')
		sb.append('-d ' + seleniumBaseDir + '/bin ')
		sb.append(seleniumBaseDir + '/src')
				
		StringBuilder jUnitSB = new StringBuilder()
		jUnitSB.append('java ')
		jUnitSB.append('-cp ' + jUnitClasspath + ' ')
		jUnitSB.append('junitrunner.Runner -o ')
		jUnitSB.append(seleniumBaseDir + '/SeleniumJunit_Report ')
		jUnitSB.append(testCase)
		
		context.with {
			steps {
				ExecuteCmd.invokeCmd(delegate, 'mkdir ' + seleniumBaseDir + '/bin', os)
				ExecuteCmd.invokeCmd(delegate, sb.toString(), os)
				ExecuteCmd.invokeCmd(delegate, 'del ' + seleniumBaseDir + '/SeleniumJunit_Report', os) 
				ExecuteCmd.invokeCmd(delegate, 'mkdir ' + seleniumBaseDir + '/SeleniumJunit_Report', os)
				ExecuteCmd.invokeCmd(delegate, jUnitSB.toString(), os)		
			}
		}
	}
}
