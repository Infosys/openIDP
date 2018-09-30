/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.utils

/**
 *
 * This class has the method to add property reader for additional pipeline parameters
 *
 */
class PropReader {

    public static String dirPath;

    /*
     * This method is used to read the value of a parameter
     */

    public static String readProperty(String propFileName, String propName) {
        def props = new Properties()
        new File(dirPath + File.separator + propFileName).withInputStream {
            stream -> props.load(stream)
        }
        return props[propName]
    }
}


