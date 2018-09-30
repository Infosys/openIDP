/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.plugins.buildsteps

/**
 *
 * This class is used to implement the methods of IPluginBase
 *
 */

class EnvInjectBuilder {

    /*
	 * This function implements add method of IPluginBase interface
	 */

    public static void add(Object context) {
        this.addOptions(context, this.performMapping())
    }


    /*
	 * This function implements performMapping method of IPluginBase interface
	 */

    public static HashMap<String, String> performMapping() {

        HashMap<String, String> data = new HashMap<String, String>()

        data.put('propertiesFilePath', '$IDP_TEST_WS/JaiGanesha.properties')

        return data
    }

    /*
	 * This function implements addOptions method of IPluginBase interface
	 */

    public static void addOptions(context, HashMap<String, String> data) {

        context.with {

            envInjectBuilder {
                if (data.containsKey("propertiesFilePath")) propertiesFilePath(data.get("propertiesFilePath"));
                propertiesContent('')
            }
        }

    }
}
