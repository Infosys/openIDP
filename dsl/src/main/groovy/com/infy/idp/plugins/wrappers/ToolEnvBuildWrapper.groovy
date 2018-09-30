/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.wrappers

import com.infy.idp.creators.*
import com.infy.idp.utils.*

/**
 *
 * This class has the method to add the tool environments
 *
 */

class ToolEnvBuildWrapper {

    /*
     * This method is used to add custom tools in jenkins job
     */

    public void invokeToolEnvBuildWrapper(context, varList) {

        context.with {
            toolEnvBuildWrapper { vars(varList + ',REPORTFETCHUTIL_HOME,DEVOPSJSONCONV_HOME,METRICSPROCESSOR_HOME') }
        }
    }


    public void invokeToolEnvBuildWrapper_Test(context, varList) {

        context.with {
            toolEnvBuildWrapper {
                vars(varList + ',REPORTFETCHUTIL_TEST_HOME,DEVOPSJSONCONV_TEST_HOME,METRICSPROCESSOR_TEST_HOME')
            }
        }
    }
}
