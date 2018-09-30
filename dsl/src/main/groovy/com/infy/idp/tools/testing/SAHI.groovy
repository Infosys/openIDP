/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.testing

import com.infy.idp.utils.*
import com.infy.idp.utils.Constants

/**
 *
 * This class has the method to add SAHI testing tool
 *
 */
class SAHI {

    /*
     * This method is used to add steps to invoke SAHI dashboard
     */

    public static void invokeSahiDashboard(delegate, jsonData, os) {

        def executable



        if (os == Constants.WINDOWSOS) {

            executable = 'start cmd.exe @cmd /k "cd /d %SAHI_HOME% & start_dashboard.bat"'
            ExecuteCmd.invokeCmd(delegate, executable, jsonData.basicInfo.buildServerOS)
        } else {

            executable = 'start cmd.exe @cmd /k "cd /d $SAHI_HOME & start_dashboard.sh"'
            ExecuteCmd.invokeCmd(delegate, executable, jsonData.basicInfo.buildServerOS)
        }


    }

    /*
     * This method is used to add steps for invoking sahi test runner
     */

    public static void invokeSahiTestrunner(delegate, jsonData, os) {
        def executable

        if (os == Constants.WINDOWSOS) {

            executable = 'cd /d %SAHI_HOME%' + "\n" + 'testrunner.bat %SahiTestSuitePath% %SahiTestURL% %SahiTestBrowser% %IDP_WS%'
            ExecuteCmd.invokeCmd(delegate, executable, jsonData.basicInfo.buildServerOS)


        } else {

            executable = 'cd /d $SAHI_HOME' + "\n" + 'testrunner.sh $SahiTestSuitePath $SahiTestURL $SahiTestBrowser $IDP_WS'
            ExecuteCmd.invokeCmd(delegate, executable, jsonData.basicInfo.buildServerOS)

        }

    }


}
