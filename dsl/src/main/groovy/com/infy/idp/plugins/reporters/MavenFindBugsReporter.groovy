/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.reporters

import com.infy.idp.interfaces.IPluginBase
import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This class implements IPLuginBase interface to generate maven findbugs reports
 *
 */

class MavenFindBugsReporter implements IPluginBase {

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
        IDPJob job = idpJobObj
        HashMap<String, String> data = new HashMap<String, String>()

        data.put('healthy', '')
        data.put('unHealthy', '')
        data.put('pluginName', '[FINDBUGS]')
        data.put('thresholdLimit', 'low')
        data.put('canRunOnFailed', false)
        data.put('useDeltaValues', false)
        data.put('unstableTotalAll', '')
        data.put('unstableTotalHigh', '')
        data.put('unstableTotalNormal', '')
        data.put('unstableTotalLow', '')
        data.put('unstableNewAll', '')
        data.put('unstableNewHigh', '')
        data.put('unstableNewNormal', '')
        data.put('unstableNewLow', '')
        data.put('failedTotalAll', '')
        data.put('failedTotalHigh', '')
        data.put('failedTotalNormal', '')
        data.put('failedTotalLow', '')
        data.put('failedNewAll', '')
        data.put('failedNewHigh', '')
        data.put('failedNewNormal', '')
        data.put('failedNewLow', '')
        data.put('dontComputeNew', true)
        data.put('usePreviousBuildAsReference', false)
        data.put('useStableBuildAsReference', false)
        data.put('isRankActivated', false)
        data.put('excludePattern', '')
        data.put('includePattern', '')

        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            configure {

                def rep = it / reporters

                rep << 'hudson.plugins.findbugs.FindBugsReporter'(plugin: "findbugs@4.69") {

                    if (data.containsKey('healthy')) healthy(data.get('healthy'))
                    if (data.containsKey('unHealthy')) unHealthy(data.get('unHealthy'))
                    if (data.containsKey('pluginName')) pluginName(data.get('pluginName'))
                    if (data.containsKey('thresholdLimit')) thresholdLimit(data.get('thresholdLimit'))
                    if (data.containsKey('canRunOnFailed')) canRunOnFailed(data.get('canRunOnFailed'))
                    if (data.containsKey('useDeltaValues')) useDeltaValues(data.get('useDeltaValues'))

                    'thresholds'(plugin: "analysis-core@1.86") {

                        if (data.containsKey('unstableTotalAll')) unstableTotalAll(data.get('unstableTotalAll'))
                        if (data.containsKey('unstableTotalHigh')) unstableTotalHigh(data.get('unstableTotalHigh'))
                        if (data.containsKey('unstableTotalNormal')) unstableTotalNormal(data.get('unstableTotalNormal'))
                        if (data.containsKey('unstableTotalLow')) unstableTotalLow(data.get('unstableTotalLow'))
                        if (data.containsKey('unstableNewAll')) unstableNewAll(data.get('unstableNewAll'))
                        if (data.containsKey('unstableNewHigh')) unstableNewHigh(data.get('unstableNewHigh'))
                        if (data.containsKey('unstableNewNormal')) unstableNewNormal(data.get('unstableNewNormal'))
                        if (data.containsKey('unstableNewLow')) unstableNewLow(data.get('unstableNewLow'))
                        if (data.containsKey('failedTotalAll')) failedTotalAll(data.get('failedTotalAll'))
                        if (data.containsKey('failedTotalHigh')) failedTotalHigh(data.get('failedTotalHigh'))
                        if (data.containsKey('failedTotalNormal')) failedTotalNormal(data.get('failedTotalNormal'))
                        if (data.containsKey('failedTotalLow')) failedTotalLow(data.get('failedTotalLow'))
                        if (data.containsKey('failedNewAll')) failedNewAll(data.get('failedNewAll'))
                        if (data.containsKey('failedNewHigh')) failedNewHigh(data.get('failedNewHigh'))
                        if (data.containsKey('failedNewNormal')) failedNewNormal(data.get('failedNewNormal'))
                        if (data.containsKey('failedNewLow')) failedNewLow(data.get('failedNewLow'))
                    }

                    if (data.containsKey('dontComputeNew')) dontComputeNew(data.get('dontComputeNew'))
                    if (data.containsKey('usePreviousBuildAsReference')) usePreviousBuildAsReference(data.get('usePreviousBuildAsReference'))
                    if (data.containsKey('useStableBuildAsReference')) useStableBuildAsReference(data.get('useStableBuildAsReference'))
                    if (data.containsKey('isRankActivated')) isRankActivated(data.get('isRankActivated'))
                    if (data.containsKey('excludePattern')) excludePattern(data.get('excludePattern'))
                    if (data.containsKey('includePattern')) includePattern(data.get('includePattern'))
                }
            }
        }
    }
}
