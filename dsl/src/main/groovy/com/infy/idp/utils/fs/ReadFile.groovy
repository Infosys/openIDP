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
 * This class has the method to read file
 *
 */
class ReadFile {

    /*
     * This method prepares the command to read a file
     */

    public static String run(String fileName) {

        String fileContent
        def env = System.getenv();
        if (ReadFile.class.getClassLoader().getResource(fileName)) {
            fileContent = ReadFile.class.getClassLoader().getResource(fileName).text
        } else {
            fileContent = new File(env['basePath'] + fileName).text
        }
        return fileContent
    }
}
