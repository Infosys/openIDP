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
 * This class has the method to configure build timeout for each job of jenkins
 *
 */

class BuildTimeout {

    /*
     * This method invokes buildtimeoutwrapper to set the timeout option for each jenkins job
     */

    public static void invokeBuildTimeoutWrapper(context) {

        context.with {

            buildTimeoutWrapper {

                strategy { absoluteTimeOutStrategy { timeoutMinutes('180') } }
                operationList { abortOperation() }
                timeoutEnvVar('')
            }
        }
    }
}
