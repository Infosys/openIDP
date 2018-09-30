/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.testing

import com.infy.idp.plugins.publishers.ArchiveArtifacts
import com.infy.idp.plugins.wrappers.ToolEnvBuildWrapper
import com.infy.idp.utils.*

/**
 *
 * This class has the method to add Protractor testing tool
 *
 */

class Protractor {

    /*
     * This method is used to add the steps to configure Protractor
     */

    public static void addSteps(context, configPath, os) {

        //Call custom tool to update file
        invokeTool(context, configPath, os)

        //Update web driver
        //ExecuteCmd.invokeCmd(context, "webdriver-manager update", os)

        //Start web driver
        //ExecuteCmd.invokeCmd(context, "webdriver-manager start", os)

        //Install html reporter package
        ExecuteCmd.invokeCmd(context, "npm install protractor-jasmine2-html-reporter --save-dev", os)

        //Install xml reporter package
        ExecuteCmd.invokeCmd(context, "npm install --save-dev jasmine-reporters@^2.0.0", os)

        //Calling test cases
        def paths = configPath.split("/")
        String pathVal = paths[-1]
        String folders = ''
        for (int index = 0; index < paths.length - 1; index++) {
            if (index > 0)
                folders += '/'
            folders += paths[index]
        }
        ExecuteCmd.invokeCmd(context, "cd " + folders + " \nprotractor " + pathVal, os, '1')
    }

    /**
     * Adder protractor Jar
     * @param context
     * @param filePath
     * @param os
     */
    public static void invokeTool(context, filePath, os) {

        filePath = filePath.replace('\\', '/')

        def moduleDir
        if (filePath.contains('/')) {
            moduleDir = filePath.substring(0, filePath.lastIndexOf('/'))
        } else {
            moduleDir = filePath
        }

//		String csReportDir;
        StringBuilder sb = new StringBuilder();
        if (os.compareToIgnoreCase(Constants.WINDOWSOS) == 0) {
            sb.append('java -jar "%IDP_WS%/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/protractor/Protractor.jar" ' + filePath + ' %IDP_WS% || exit 0')
        } else {
            sb.append('java -jar "$IDP_WS/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/protractor/Protractor.jar" ' + filePath + ' $IDP_WS')
        }

        ExecuteCmd.invokeCmd(context, sb.toString(), os)
    }

    /*
     * This method is used to add required publishers
     */

    public static void addWrappers(context, jsonData) {
        def tools = 'PROTRACTOR_HOME'

        if (jsonData.basicInfo.buildServerOS.compareToIgnoreCase(Constants.UNIXOS) == 0) {
            tools += ',XVFB_HOME'
        }

        (new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(context, tools)
    }

    /*
     * This method is used to add required publishers
     */

    public static void addPublishers(context, jsonData) {


        ArchiveArtifacts archiveArtifacts = new ArchiveArtifacts()

        archiveArtifacts.setPattern('**/ProtractorReports/**')
        archiveArtifacts.add(context, jsonData)
    }
}
