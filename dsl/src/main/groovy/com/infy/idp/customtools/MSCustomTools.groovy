/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.customtools

import com.infy.idp.utils.*

/**
 *
 * This class includes the method for adding MSCustomTools
 *
 */

class MSCustomTools {

    /*
     * This function is used to add the commands to run MSCustomTools
     */

    public static void invokeTool(context, jsonData) {

        def command


       
        command = """ "%IDP_WS%/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/MSCustomTools/msxsl.exe" "%IDP_WS%/data.xml" "%IDP_WS%/../../tools/com.cloudbees.jenkins.plugins.customtools.CustomTool/MSCustomTools/MSTestCoverageToEmma.xsl" -o "%IDP_WS%/Coverage.xml" """
        ExecuteCmd.invokeCmd(context, command, jsonData.basicInfo.buildServerOS)
    }


}
