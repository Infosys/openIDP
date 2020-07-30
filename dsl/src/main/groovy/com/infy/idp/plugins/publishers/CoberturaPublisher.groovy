/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.publishers

import com.infy.idp.creators.*
import com.infy.idp.interfaces.IPluginBase
import com.infy.idp.utils.*
import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This class implements IPLuginBase interface to publish cobertura reports
 *
 */

class CoberturaPublisher implements IPluginBase {

    private String pattern = ''

    public String updatePattern(pattern) {
        this.pattern += (pattern + ',')
    }

    /*
     * This function implements add method of IPluginBase interface
     */

    public void add(Object context, IDPJob idpJobObj) {
        this.addOptions(context, this.performMapping(idpJobObj));
    }

    /*
     * This function implements performMapping method of IPluginBase interface
     */

    public HashMap<String, String> performMapping(IDPJob idpJobObj) {

        HashMap<String, String> data = new HashMap<String, String>()

        data.put('reportFile', this.pattern)
        data.put('autoUpdateHealth', false)
        data.put('autoUpdateStability', true)
        data.put('conditionalTargetHealthy', 70)
        data.put('conditionalTargetUnhealthy', 0)
        data.put('conditionalTargetFailing', 0)
        data.put('failNoReports', false)
        data.put('failUnhealthy', false)
        data.put('failUnstable', false)
        data.put('lineTargetHealthy', 80)
        data.put('lineTargetUnhealthy', 0)
        data.put('lineTargetFailing', 0)
        data.put('methodTargetHealthy', 80)
        data.put('methodTargetUnhealthy', 0)
        data.put('methodTargetFailing', 0)
        data.put('onlyStable', false)
        data.put('sourceEncoding', 'ASCII')
        data.put('zoomCoverageChart', false)

        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    public void addOptions(context, HashMap<String, String> data) {
        context.with {

            cobertura(data.get('reportFile')) {

                // Automatically updates the threshold for health on successful builds.
                if (data.containsKey('autoUpdateHealth')) autoUpdateHealth(data.get('autoUpdateHealth'))

                // Automatically updates the threshold for stability on successful builds.
                if (data.containsKey('autoUpdateStability')) autoUpdateStability(data.get('autoUpdateStability'))

                // Sets health reporting thresholds for conditional level converage.
                if (data.containsKey('conditionalTargetHealthy') && data.containsKey('conditionalTargetUnhealthy ') && data.containsKey('conditionalTargetFailing'))
                    conditionalTarget(data.get('conditionalTargetHealthy'), data.get('conditionalTargetUnhealthy'), data.get('conditionalTargetFailing'))

                // Fails builds if no coverage reports are found.
                if (data.containsKey('failNoReports')) failNoReports(data.get('failNoReports'))

                // Fails unhealthy builds.
                if (data.containsKey('failUnhealthy')) failUnhealthy(data.get('failUnhealthy'))

                // Fails unstable builds.
                if (data.containsKey('failUnstable')) failUnstable(data.get('failUnstable'))

                // Sets health reporting thresholds for line level converage.
                if (data.containsKey('lineTargetHealthy') && data.containsKey('lineTargetUnhealthy ') && data.containsKey('lineTargetFailing'))
                    lineTarget(data.get('lineTargetHealthy'), data.get('lineTargetUnhealthy'), data.get('lineTargetFailing'))

                // Sets health reporting thresholds for method level converage.
                if (data.containsKey('methodTargetHealthy') && data.containsKey('methodTargetUnhealthy ') && data.containsKey('methodTargetFailing'))
                    methodTarget(data.get('methodTargetHealthy'), data.get('methodTargetUnhealthy'), data.get('methodTargetFailing'))

                // Considers only stable builds.
                if (data.containsKey('onlyStable')) onlyStable(data.get('onlyStable'))

                // Specifies the encoding to use when showing files.
                if (data.containsKey('sourceEncoding')) sourceEncoding(data.get('sourceEncoding'))

                // Zooms the coverage chart and crop area below the minimum and above the maximum coverage of the past reports.
                if (data.containsKey('zoomCoverageChart')) zoomCoverageChart(data.get('zoomCoverageChart'))
            }
        }
    }
}
