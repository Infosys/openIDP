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
 * This class implements IPLuginBase interface to publish Pmd reports
 *
 */

class PmdPublisher implements IPluginBase {

    private String pattern = ''

    /*
     * This function implements add method of IPluginBase interface
     */

    public void add(Object context, IDPJob idpJobObj) {

        def data = this.performMapping(idpJobObj)

        if (data.get('pattern') == '')
            return

        this.addOptions(context, data);
    }

    /*
    * This function implements performMapping method of IPluginBase interface
    */

    public HashMap<String, String> performMapping(IDPJob idpJobObj) {
        IDPJob job = idpJobObj
        HashMap<String, String> data = new HashMap<String, String>()

        data.put('canComputeNew', false)
        data.put('canResolveRelativePaths', false)
        data.put('canRunOnFailed', false)
        data.put('pattern', this.pattern)
        data.put('shouldDetectModules', false)
        data.put('thresholdLimit', 'low')
        data.put('useDeltaValues', false)
        data.put('useStableBuildAsReference', false)
        data.put('healthy', '')
        data.put('unHealthy', '')
        data.put('defaultEncoding', '')

        return data
    }
    /*
     * This method is used to generate the pattern for Pmd reports
     */

    public String generatePattern(jsonData) {

        for (int i = 0; i < jsonData.buildInfo.modules.size(); i++) {

            def relativePath = jsonData.buildInfo.modules.getAt(i).relativePath.replace('\\', '/')
            def moduleDir
            if (relativePath.contains('/')) {
                moduleDir = relativePath.substring(0, relativePath.lastIndexOf('/'))
            } else {
                moduleDir = relativePath
            }

            if (jsonData.buildInfo.modules.getAt(i).codeAnalysis.contains(Constants.PMD))
                this.pattern += (',' + moduleDir + '/PMDReports/*.xml')
        }
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            pmd {
                if (data.containsKey('canComputeNew')) canComputeNew(data.get('canComputeNew'))
                if (data.containsKey('canResolveRelativePaths')) canResolveRelativePaths(data.get('canResolveRelativePaths'))
                if (data.containsKey('canRunOnFailed')) canRunOnFailed(data.get('canRunOnFailed'))
                if (data.containsKey('pattern')) pattern(data.get('pattern'))
                if (data.containsKey('shouldDetectModules')) shouldDetectModules(data.get('shouldDetectModules'))
                if (data.containsKey('thresholdLimit')) thresholdLimit(data.get('thresholdLimit'))
                if (data.containsKey('useDeltaValues')) useDeltaValues(data.get('useDeltaValues'))
                if (data.containsKey('useStableBuildAsReference')) useStableBuildAsReference(data.get('useStableBuildAsReference'))
                if (data.containsKey('healthy')) healthy(data.get('healthy'))
                if (data.containsKey('unHealthy')) unHealthy(data.get('unHealthy'))
                if (data.containsKey('defaultEncoding')) defaultEncoding(data.get('defaultEncoding'))
            }
        }
    }
}
