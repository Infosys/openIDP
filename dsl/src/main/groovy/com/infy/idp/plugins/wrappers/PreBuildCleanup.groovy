/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.wrappers

import com.infy.idp.interfaces.IPluginBase
import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This class implements IPLuginBase interface to add prebuild clean up option
 *
 */

class PreBuildCleanup implements IPluginBase {

    private String cleanupParameter = ""
    private String deleteCommand = ""
    private Boolean deleteDirectories = true
    private String excludePattern = ""
    private String includePattern = ".metadata"

    public String getIncludePattern() {
        return includePattern
    }

    public void setIncludePattern(String includePattern) {
        this.includePattern = includePattern
    }

    public String getExcludePattern() {
        return excludePattern
    }

    public void setExcludePattern(String excludePattern) {
        this.excludePattern = excludePattern
    }

    public Boolean getDeleteDirectories() {
        return deleteDirectories
    }

    public void setDeleteDirectories(Boolean deleteDirectories) {
        this.deleteDirectories = deleteDirectories
    }

    public String getCleanupParameter() {
        return cleanupParameter
    }

    public void setCleanupParameter(String cleanupParameter) {
        this.cleanupParameter = cleanupParameter
    }

    public String getDeleteCommand() {
        return deleteCommand
    }

    public void setDeleteCommand(String deleteCommand) {
        this.deleteCommand = deleteCommand
    }

    /*
    * This function implements add method of IPluginBase interface
    */

    @Override
    public void add(context, IDPJob dataObj) {
        this.addOptions(context, this.performMapping(dataObj));
    }

    /*
     * This function implements performMapping method of IPluginBase interface
     */

    @Override
    public HashMap<String, String> performMapping(IDPJob dataObj) {
        HashMap<String, String> data = new HashMap<String, String>()
        data.put("cleanupParameter", this.cleanupParameter);
        data.put("deleteCommand", this.deleteCommand);
        data.put("deleteDirectories", this.deleteDirectories);
        data.put("excludePattern", this.excludePattern);
        data.put("includePattern", this.includePattern);
        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    @Override
    public void addOptions(context, HashMap<String, String> data) {
        context.with {
            preBuildCleanup {
                PreBuildCleanup.addGeneralBuildSettings(delegate, data)

            }
        }
    }

    /*
     * This method is used to add general settings for prebuild clean up in jenkins job
     */

    private static void addGeneralBuildSettings(context, data) {
        context.with {
            if (data.containsKey("cleanupParameter")) cleanupParameter(data.get("cleanupParameter"));
            if (data.containsKey("deleteCommand")) deleteCommand(data.get("deleteCommand"));
            if (data.containsKey("deleteDirectories")) deleteDirectories(data.get("deleteDirectories").toBoolean());
            if (data.containsKey("excludePattern")) excludePattern(data.get("excludePattern"));
            if (data.containsKey("includePattern")) includePattern(data.get("includePattern"));

        }
    }

}
