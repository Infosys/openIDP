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
 * This class has the method to copy files to slave
 *
 */

class CopyToSlave {

    /*
     * This method can be used to copy files form jenkins master fro jenkins slave workspace
     */

    public static void invokeCopyToSlave(context, includePattern) {

        context.with {

            copyToSlaveBuildWrapper {

                // Comma-separated list of patterns of files/directories to be copied.
                includes(includePattern)

                // Optionally specify the 'excludes' pattern, such as "**/TEST-*.xml".
                excludes('')

                // Normally directory structure is preserved when files are copied.
                flatten(false)

                // Ant has some default excludes.
                includeAntExcludes(false)

                // Specify the base location for the paths that are specified in the Includes and Excludes fields.
                relativeTo('userContent')

                hudsonHomeRelative(false)

            }
        }
    }
}


