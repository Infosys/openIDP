/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools

import com.infy.idp.customtools.DevopsJsonConversion
import com.infy.idp.customtools.MetricsProcessor
import com.infy.idp.customtools.ReportFetchUtility

/**
 *
 * This class has the method to invoke dashboard utilities for grafana
 *
 */
class NiaIntegStage {

    /*
     * This method can be used to add dashboard utilities as a part of post build action
     */

    static void run(context, jsonData, stageName) {

        ReportFetchUtility.invokeTool(context, jsonData)
        DevopsJsonConversion.invokeTool(context, jsonData, stageName, 'N')
        MetricsProcessor.invokeTool(context, jsonData)
    }

}
