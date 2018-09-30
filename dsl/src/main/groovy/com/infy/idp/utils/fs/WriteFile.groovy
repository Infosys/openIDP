/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.utils.fs

/**
 *
 * This class has the method to write to a file
 *
 */
class WriteFile {

    /*
     * This method prepares the command to write to a file
     */

    public static String run(String fileName, String fileContent) {

        def values = fileName.split('/')

        if (values[0].equalsIgnoreCase("null")) {

            fileName = ''
            def env = System.getenv();
            values[0] = env['JENKINS_HOME']

            fileName = values[0]
            for (int i = 1; i < values.size(); i++) {
                fileName += '/'
                fileName += values[i]
            }
        }

        def file = new File(fileName)
        file.getParentFile().mkdirs()

        file.newWriter().withWriter { w ->
            w << fileContent
        }

    }
}
