/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package idp.stages


class RebaseStage implements Serializable {
	def script

	RebaseStage(script){
		this.script = script
	}

	def execute(jsonData, customWS, nodeObject, jobBuildId, buildLabel, basePath,transReq){

		if(jsonData["rebase"]) {
			this.rebaseJobExecution(jsonData,basePath,nodeObject,jobBuildId,customWS,buildLabel)
		}
	}

	def rebaseJobExecution(jsonData,basePath,nodeObject,jobBuildId,customWS,buildLabel){
		try{
			this.script.build job: basePath +"_Rebase",
			parameters:[
				[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS ],
				[$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: jsonData["releaseNumber"]],
				[$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
				[$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
				[$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
				[$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel],
				[$class: 'StringParameterValue', name: 'TARGET_LANDSCAPE', value: jsonData["rebase"]["targetEnvSelected"]],
				[$class: 'StringParameterValue', name: 'TRANS_REQ', value: jsonData["rebase"]["bugFixTR"]],
				[$class: 'StringParameterValue', name: 'TRANSPORT_OBJECT', value: jsonData["rebase"]["transportObject"]],
				[$class: 'StringParameterValue', name: 'TRANSPORT_OBJECT_TYPE', value: jsonData["rebase"]["transportObjectType"]]
			]
		}
		catch(Exception e){
			if(e.toString().contains("FAILURE")){
				throw e
			}
		}
	}
}