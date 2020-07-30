/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.properties

import com.infy.idp.creators.*
import com.infy.idp.interfaces.IPluginBase
import com.infy.idp.utils.*
import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This class implements IPLuginBase interface to add environments in jenkins job
 *
 */

class EnvInject implements IPluginBase {

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

        def proxy = idpJobObj.code.scm.getAt(0).proxy;
        def port = idpJobObj.code.scm.getAt(0).proxyPort;

        data.put('http_proxy', proxy)
        data.put('http_port', port)

        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    public void addOptions(context, HashMap<String, String> data) {


        context.with {
            envInjectJobProperty {
                info {

                    propertiesFilePath('')
                    propertiesContent("http_proxy=" + data.get('http_proxy') + ":" + data.get('http_port') + "\nhttps_proxy=" + data.get('http_proxy') + ":" + data.get('http_port'))
                    scriptFilePath('')
                    scriptContent('')

                    secureGroovyScript {
                        script('')
                        sandbox(false)
                    }
                    loadFilesFromMaster(false)
                }
                keepBuildVariables(true)
                keepJenkinsSystemVariables(true)
                on(true)


            }


        }
    }
}
			
	
	
	
