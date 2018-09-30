/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools

import com.infy.idp.creators.*
import com.infy.idp.utils.*

/**
 *
 * This class has the method to invoke prebuid clean up task
 *
 */

class InvokePreBuildCleanup {

    /*
     * This method can be used to add prebuild clean up option
     */

    public static void invokepreBuildCleanup(context, HashMap<String, String> data) {
        context.with {
            preBuildCleanup {
                InvokePreBuildCleanup.addGeneralsettings(delegate, data)
            }
        }
    }
    /*
     * This method can be used for adding general settings for pre build clean up
     */

    private static void addGeneralsettings(context, data) {
        context.with {
            if (data.containsKey("cleanupParameter")) cleanupParameter(data.get("cleanupParameter"));
            if (data.containsKey("deleteCommand")) deleteCommand(data.get("deleteCommand"));
            if (data.containsKey("deleteDirectories")) deleteDirectories(data.get("deleteDirectories").toBoolean());
            if (data.containsKey("excludePattern")) excludePattern(data.get("excludePattern"));
            if (data.containsKey("includePattern")) includePattern(data.get("includePattern"));
        }
    }
}
