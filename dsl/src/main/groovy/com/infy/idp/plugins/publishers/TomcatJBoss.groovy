/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.publishers

import com.infy.idp.interfaces.IPluginBase
import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This class implements IPLuginBase interface to configure Tomcat and JBoss details in jenkins job
 *
 */

class TomcatJBoss implements IPluginBase {

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
        data.put("UrlJBoss", "\$JBOSS_URL");
        data.put("passwordJBoss", "\$JBOSS_PASSWORD");
        data.put("UserNameJBoss", "\$JBOSS_USERNAME");
        data.put("UrlTomcat", "\$TOMCAT_URL");
        data.put("PasswordTomcat", "\$TOMCAT_PASSWORD");
        data.put("UserNameTomcat", "\$TOMCAT_USERNAME");
        data.put("war", "\$WAR");
        data.put("contextPath", "\$CONTEXTPATH");
        data.put("onFailure", "false");
        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    @Override
    public void addOptions(context, HashMap<String, String> data) {
        context.with {
            deployPublisher {
                adapters {
                    jBoss7xAdapter {
                        if (data.containsKey("UrlJBoss")) url(data.get("UrlJBoss"));
                        if (data.containsKey("passwordJBoss")) password(data.get("passwordJBoss"));
                        if (data.containsKey("UserNameJBoss")) userName(data.get("UserNameJBoss"));
                    }
                    tomcat7xAdapter {
                        if (data.containsKey("UrlTomcat")) url(data.get("UrlTomcat"));
                        if (data.containsKey("PasswordTomcat")) password(data.get("PasswordTomcat"));
                        if (data.containsKey("UserNameTomcat")) userName(data.get("UserNameTomcat"));
                    }
                }
                if (data.containsKey("war")) war(data.get("war"));
                if (data.containsKey("contextPath")) contextPath(data.get("contextPath"));
                if (data.containsKey("onFailure")) onFailure(data.get("onFailure").toBoolean());
            }
        }
    }
}
