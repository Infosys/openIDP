/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.utils

import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*

/**
 *
 * This class has the method to add credentials in jenkins
 *
 */

class AddCredentials {

    /*
     * This method adds the credentials in jenkins job
     */

    public static void run(id, userName, password) {

        Credentials c = (Credentials) new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL, id, '', userName, password)
        SystemCredentialsProvider.getInstance().getStore().addCredentials(Domain.global(), c)
    }
}
