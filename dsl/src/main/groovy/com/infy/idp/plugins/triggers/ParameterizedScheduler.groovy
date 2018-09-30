/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.triggers

/**
 *
 * This class is used to add parameterized scheduler option in jenkins job
 *
 */

class ParameterizedScheduler {

    /*
     * This method is used to add the parameterized schedule option
     */

    public void add(Object context, String spec) {
        this.addOptions(context, this.performMapping(spec))
    }

    /*
     * this method is used to perform mapping for parameterized option
     */

    private HashMap<String, String> performMapping(String spec) {


        HashMap<String, String> data = new HashMap<String, String>()
        data.put('parameterizedSpecification', spec)

        return data
    }

    /*
     * This method is used to add options in jenkins job
     */

    private void addOptions(Object context, HashMap<String, String> data) {
        context.with {
            parameterizedCron {
                if (data.containsKey('parameterizedSpecification')) parameterizedSpecification(data.get('parameterizedSpecification'))
            }
        }
    }
}
