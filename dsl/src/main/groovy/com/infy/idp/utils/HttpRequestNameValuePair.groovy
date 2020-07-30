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
 * This class is the bean class for adding custom headers type and value pair for http request
 *
 */

public class HttpRequestNameValuePair {

    private String name = ''
    private String value = ''
    private boolean maskValue = false


    public String getName() {
        return name
    }

    public void setName(String name) {
        this.name = name
    }

    public String getValue() {
        return value
    }

    public void setValue(String value) {
        this.value = value
    }

    public boolean getMaskValue() {
        return maskValue
    }


}
