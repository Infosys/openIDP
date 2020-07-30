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
 * This class implements IPLuginBase interface to publish Jacoco reports
 *
 */

class JacocoCC implements IPluginBase {

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

        data.put('changeBuildStatus', false)

        data.put('classPattern', '**/intermediates/classes/dev/debug')
        data.put('exclusionPattern', '**/*Test*.class')
        data.put('execPattern', '**/**.exec')
        data.put('inclusionPattern', '**/*.class')
        data.put('maximumBranchCoverage', '0')
        data.put('maximumClassCoverage', '0')
        data.put('maximumComplexityCoverage', '0')
        data.put('maximumInstructionCoverage', '0')
        data.put('maximumLineCoverage', '0')
        data.put('maximumMethodCoverage', '0')
        data.put('minimumBranchCoverage', '0')
        data.put('minimumClassCoverage', '0')
        data.put('minimumComplexityCoverage', '0')
        data.put('minimumInstructionCoverage', '0')
        data.put('minimumLineCoverage', '0')
        data.put('minimumMethodCoverage', '0')
        //data.put('sourcePattern', '**/src')
        data.put('sourcePattern', '**/src/main/java')

        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            jacocoCodeCoverage {

                // If set, changes the build status according to the thresholds.
                if (data.containsKey('changeBuildStatus')) changeBuildStatus(data.get('changeBuildStatus'))

                // Sets the path to the class directories.
                if (data.containsKey('classPattern')) classPattern(data.get('classPattern'))

                // Allows to exclude certain classes.
                if (data.containsKey('exclusionPattern')) exclusionPattern(data.get('exclusionPattern'))

                // Sets the path to the exec files.
                if (data.containsKey('execPattern')) execPattern(data.get('execPattern'))

                // Allows to include certain classes.
                if (data.containsKey('inclusionPattern')) inclusionPattern(data.get('inclusionPattern'))

                // Reports health as 100% if branch coverage is greater than specified.
                if (data.containsKey('maximumBranchCoverage')) maximumBranchCoverage(data.get('maximumBranchCoverage'))

                // Reports health as 100% if class coverage is greater than specified.
                if (data.containsKey('maximumClassCoverage')) maximumClassCoverage(data.get('maximumClassCoverage'))

                // Reports health as 100% if complexity coverage is greater than specified.
                if (data.containsKey('maximumComplexityCoverage')) maximumComplexityCoverage(data.get('maximumComplexityCoverage'))

                // Reports health as 100% if instruction coverage is greater than specified.
                if (data.containsKey('maximumInstructionCoverage')) maximumInstructionCoverage(data.get('maximumInstructionCoverage'))

                // Reports health as 100% if line coverage is greater than specified.
                if (data.containsKey('maximumLineCoverage')) maximumLineCoverage(data.get('maximumLineCoverage'))

                // Reports health as 100% if method coverage is greater than specified.
                if (data.containsKey('maximumMethodCoverage')) maximumMethodCoverage(data.get('maximumMethodCoverage'))

                // Reports health as 0% if branch coverage is less than specified.
                if (data.containsKey('minimumBranchCoverage')) minimumBranchCoverage(data.get('minimumBranchCoverage'))

                // Reports health as 0% if class coverage is less than specified.
                if (data.containsKey('minimumClassCoverage')) minimumClassCoverage(data.get('minimumClassCoverage'))

                // Reports health as 0% if complexity coverage is less than specified.
                if (data.containsKey('minimumComplexityCoverage')) minimumComplexityCoverage(data.get('minimumComplexityCoverage'))

                // Reports health as 0% if instruction coverage is less than specified.
                if (data.containsKey('minimumInstructionCoverage')) minimumInstructionCoverage(data.get('minimumInstructionCoverage'))

                // Reports health as 0% if line coverage is less than specified.
                if (data.containsKey('minimumLineCoverage')) minimumLineCoverage(data.get('minimumLineCoverage'))

                // Reports health as 0% if method coverage is less than specified.
                if (data.containsKey('minimumMethodCoverage')) minimumMethodCoverage(data.get('minimumMethodCoverage'))

                // Sets the path to the source directories.
                if (data.containsKey('sourcePattern')) sourcePattern(data.get('sourcePattern'))
            }
        }
    }
}
