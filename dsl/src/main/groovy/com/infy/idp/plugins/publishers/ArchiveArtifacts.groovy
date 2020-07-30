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
 * This class implements IPLuginBase interface to archive the artifacts generates
 *
 */

class ArchiveArtifacts implements IPluginBase {

    private String pattern = ''
	private Boolean allowEmpty = "false"

    public String updatePattern(pattern) {
        this.pattern += (pattern + ',')
    }

    public String setPattern(pattern) {
        this.pattern = pattern
    }

    public String getPattern() {
        return pattern;
    }

	public String setAllowEmpty(allowEmpty) {
        this.allowEmpty = allowEmpty
    }

    public String getAllowEmpty() {
        return allowEmpty;
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

        println idpJobObj
        HashMap<String, String> data = new HashMap<String, String>()

        //data.put('allowEmpty', "false");
		data.put('allowEmpty', this.allowEmpty);
        data.put('defaultExcludes', "true");
        data.put("exclude", "");
        data.put('fingerprint', "false");
        data.put('onlyIfSuccessful', "false");
        data.put('pattern', this.pattern);

        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            archiveArtifacts {

                // If set, does not fail the build if archiving returns nothing.
                if (data.containsKey('allowEmpty')) allowEmpty(data.get('allowEmpty').toBoolean())

                // Uses default excludes.
                if (data.containsKey('defaultExcludes')) defaultExcludes(data.get('defaultExcludes').toBoolean())

                // Specifies files that will not be archived.
                if (data.containsKey('exclude')) exclude(data.get('exclude'))

                // Fingerprints all archived artifacts.
                if (data.containsKey('fingerprint')) fingerprint(data.get('fingerprint').toBoolean())

                // Archives artifacts only if the build is successful.
                if (data.containsKey('onlyIfSuccessful')) onlyIfSuccessful(data.get('onlyIfSuccessful').toBoolean())

                // Specifies the files to archive.
                if (data.containsKey('pattern')) pattern(data.get('pattern'))
            }
        }
    }
}
