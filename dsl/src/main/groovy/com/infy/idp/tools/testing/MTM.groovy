/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.testing

import com.infy.idp.creators.*
import com.infy.idp.customtools.*
import com.infy.idp.plugins.buildsteps.*
import com.infy.idp.plugins.publishers.*
import com.infy.idp.plugins.wrappers.*
import com.infy.idp.tools.*
import com.infy.idp.utils.*
import com.infy.idp.utils.fs.*

/**
 *
 * This class has the method to add MTM testing tool
 *
 */

class MTM {

    static String basepath

    /*
    * This method is used to add the steps to configure JMeter
    */

    private static void addSteps(context) {


        Powershell ps = new Powershell()
        String cmd = "";

        cmd = 'powershell.exe $env:IDP_PROJECT_NAME/MTM_AutomationScripts/idp_mtm_execution.ps1';
        ps.add(context, cmd);


    }

    /*
     * This method is used to create job with Microsoft Test Manager with the added steps
     */

    public static void MTMJobCreation(context) {


        addSteps(context)


    }

    /*
     * This method is used to add required publishers
     */

    public static void addWrappers(context) {

        def toolList = ''
        (new ToolEnvBuildWrapper()).invokeToolEnvBuildWrapper(context, toolList)


    }


}	
	
