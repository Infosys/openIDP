/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.stages

import com.infy.idp.creators.*
import com.infy.idp.utils.*
import javaposse.jobdsl.dsl.DslFactory

/**
 *
 * This class has the method to set the name of the job to be created in jenkins
 *
 */

class Preparation {

    String basepath

    /*
     * This method is used to call other child jobs to set the name of the job
     */

    void run(DslFactory factory, jsonData) {
        basepath = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
        baseFolderCreation(factory)
    }

    /*
     * This method creates the base folder in jenkins
     */

    void baseFolderCreation(factory) {
        new BaseFolderCreator(folderName: basepath).create(factory)
    }
}
