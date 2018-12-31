/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

import com.infy.idp.creators.*
import com.infy.idp.stages.*
import com.infy.idp.utils.*
import groovy.json.JsonSlurper
import org.infy.idp.entities.jobs.IDPJob 

/*
 * This is the entry point for the pipeline creation
 */

//Set Props Base Path
PropReader.dirPath = (new File(__FILE__)).getAbsolutePath().substring(0, (new File(__FILE__)).getAbsolutePath().lastIndexOf(File.separator));

// Parse Input Json File
def jsonData = new IDPJob(new JsonSlurper().parseText(JSON_Input))

Preparation prepare = new Preparation()
prepare.run(this, jsonData)


Build build = new Build()
build.run(this, jsonData, binding.variables)

Scm scm = new Scm()
scm.run(this, jsonData)

Scm_Deploy scmd = new Scm_Deploy()
scmd.run(this, jsonData)

Scm_Test scmt = new Scm_Test()
scmt.run(this, jsonData)

GitTagging gitTag = new GitTagging()
gitTag.run(this, jsonData)

Pipeline pipeline = new Pipeline()
pipeline.run(this, jsonData, Pipeline_Script_Path)

Test test = new Test()
test.run(this, jsonData, binding.variables)

Deploy deploy = new Deploy()
deploy.run(this, jsonData, binding.variables)






