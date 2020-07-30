/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.scm

import com.infy.idp.creators.*
import com.infy.idp.tools.*
import com.infy.idp.utils.*
import org.infy.idp.entities.jobs.IDPJob
import org.infy.idp.entities.jobs.code.Scm

/**
 *
 * This class implements IPLuginBase interface to add TFSSCM option in jenkins job
 *
 */

class TfsScm {

    private indexToMap = -1

    private HashMap<Integer, Scm> scmMap;


    public HashMap<Integer, Scm> getScmMap() {
        return scmMap;
    }

    public void setScmMap(HashMap<Integer, Scm> scmMap) {
        this.scmMap = scmMap;
    }

    /*
     * This function implements add method of IPluginBase interface
     */

    public void add(Object context, IDPJob idpJobObj) {

        def scmArr = null;
        if (idpJobObj.code != null && idpJobObj.code.scm != null) {
            scmArr = idpJobObj.code.scm
        } else {
            scmArr = idpJobObj.tesAsset.scm
        }

        for (Map.Entry<Integer, Scm> entry : this.scmMap.entrySet()) {
            int key = entry.getKey();
            if (scmArr.getAt(key).type == Constants.TFSSCM) {
                indexToMap = key
                this.addOptions(context, this.performMapping(idpJobObj));
            }


        }

    }

    /*
     * This function implements performMapping method of IPluginBase interface
     */

    public HashMap<String, String> performMapping(IDPJob idpJobObj) {

        HashMap<String, String> data = new HashMap<String, String>()

        def scmSection = null;
        if (idpJobObj.code != null && idpJobObj.code.scm != null) {
            scmSection = idpJobObj.code.scm.getAt(indexToMap)
        } else {
            scmSection = idpJobObj.tesAsset.scm.getAt(indexToMap)
        }
        data.put('projectPath', '$SCM_BRANCH_' + indexToMap)


        data.put('serverUrl', scmSection.url)
        //data.put('projectPath', scmSection.projPath)
        data.put('localPath', scmSection.projPath.lastIndexOf('/') == -1 ? scmSection.projPath : scmSection.projPath.substring(scmSection.projPath.lastIndexOf('/') + 1))
        //data.put('localPath', '.')
        data.put('workspaceName', 'Hudson-${JOB_NAME}-${NODE_NAME}')
        data.put('userName', scmSection.userName)
        data.put('password', scmSection.password)

        return data
    }

    /*
    * This function implements addOptions method of IPluginBase interface
    */

    public void addOptions(context, HashMap<String, String> data) {

        context.with {

            configure {

                def mscm = it / scm / scms

                mscm << 'hudson.plugins.tfs.TeamFoundationServerScm'(plugin: 'tfs@5.3.4') {

                    if (data.containsKey('serverUrl')) serverUrl(data.get('serverUrl'))
                    if (data.containsKey('projectPath')) projectPath(data.get('projectPath'))
                    if (data.containsKey('localPath')) localPath(data.get('localPath'))
                    if (data.containsKey('workspaceName')) workspaceName(data.get('workspaceName'))
                    if (data.containsKey('userName')) userName(data.get('userName'))
                    if (data.containsKey('password')) password(hudson.util.Secret.fromString(data.get('password')).encryptedValue)
                }
            }
        }
    }
}
