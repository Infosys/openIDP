/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.buildsteps

import com.infy.idp.interfaces.IPluginBase
import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This class implements IPLuginBase interface to configure job for MSBuild option
 *
 */

class MSBuild implements IPluginBase {

    private String cmdArgs = ''
    private String buildFile = ''
    private String msBuildVersion = ''

    public void setCmdArgs(String cmdArgs) {
        this.cmdArgs = cmdArgs;
    }

    public void setBuildFile(String buildFile) {
        this.buildFile = buildFile;
    }

    public void setMsBuildVersion(String msBuildVersion) {
        this.msBuildVersion = msBuildVersion;
    }

    /*
     * This function implements add method of IPluginBase interface
     */

    @Override
    public void add(context, IDPJob dataObj) {
        this.addOptions(context, this.performMapping(dataObj))
    }

    /*
    * This function implements performMapping method of IPluginBase interface
    */

    @Override
    public HashMap<String, String> performMapping(IDPJob dataObj) {

        HashMap<String, String> data = new HashMap<String, String>()

        data.put('args', this.cmdArgs)
        data.put('buildFile', this.buildFile)
        data.put('continueOnBuildFailure', 'false')
        data.put('msBuildInstallation', this.msBuildVersion)
        data.put('passBuildVariables', 'false')
        data.put('unstableIfWarnings', 'false')

        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    @Override
    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            msBuild {

                MSBuild.addMsBuild(delegate, data)
            }
        }
    }

    /*
     * This method is used to add MSBuild option in jenkins job
     */

    private static void addMsBuild(context, data) {

        context.with {

            if (data.containsKey('args')) args(data.get('args'))
            if (data.containsKey('buildFile')) buildFile(data.get('buildFile'))
            if (data.containsKey('continueOnBuildFailure')) continueOnBuildFailure(data.get('continueOnBuildFailure').toBoolean())
            if (data.containsKey('msBuildInstallation')) msBuildInstallation(data.get('msBuildInstallation'))
            if (data.containsKey('passBuildVariables')) passBuildVariables(data.get('passBuildVariables').toBoolean())
            if (data.containsKey('unstableIfWarnings')) unstableIfWarnings(data.get('unstableIfWarnings').toBoolean())
        }
    }
}
