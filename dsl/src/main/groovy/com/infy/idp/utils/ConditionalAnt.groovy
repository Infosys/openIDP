/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.utils

import com.infy.idp.creators.*

/**
 *
 * This class has the method to add ConditionalAnt statement
 *
 */
class ConditionalAnt {

    /*
     * this method is used to invoke conditional ant commands
     */

    public static void invokeConditionalAnt(context, targetsArg, buildFileArg, antOptsArg, propertiesArg) {

        context.with {

            ant {

                targets(targetsArg)

                // If your build requires a custom ANT_OPTS, specify it here.
                antOpts(antOptsArg)

                // Properties needed by your ant build can be specified here (in standard <a href="http://download.oracle.com/javase/6/docs/api/java/util/Properties.html#load%28java.io.Reader%29" target="_new">properties file format): # comment name1=value1 name2=$VAR2 These are passed to Ant like "-Dname1=value1 -Dname2=value2".
                properties(propertiesArg)

                //buildFile('dir1/build.xml')
                buildFile(buildFileArg)

                antName('ANT')
            }
        }
    }
}


