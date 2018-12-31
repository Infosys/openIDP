/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package idp.stages
import java.text.SimpleDateFormat

class CASTStage implements Serializable {
	def script

	CASTStage(script){
		this.script = script
	}

	def execute(jsonData, customWS, nodeObject, jobBuildId, buildLabel, basePath,transReq){
		if(jsonData["build"] && jsonData["build"]["cast"] && jsonData["build"]["cast"].equalsIgnoreCase("on")) {
			def moduleList = 'NA'
			def date =new Date()
			def sdf = new SimpleDateFormat("yyyyMMddHHmm")
			def currentDate
			currentDate = sdf.format(date)
			if(jsonData["build"]["module"])
				moduleList = ';' + jsonData["build"]["module"].join(';').toString() + ';'

			this.castJobExecution(jsonData,basePath,nodeObject,jobBuildId,customWS,transReq,buildLabel,currentDate,moduleList)
		}
	}

	def castJobExecution(jsonData,basePath,nodeObject,jobBuildId,customWS,transReq,buildLabel,currentDate,moduleList){
		try{
			this.script.build job: basePath +"_Cast",
			parameters:[
				[$class: 'StringParameterValue', name: 'IDP_WS', value: customWS ],
				[$class: 'StringParameterValue', name: 'MODULE_LIST', value: moduleList],
				[$class: 'StringParameterValue', name: 'RELEASE_IDENTIFIER', value: jsonData["releaseNumber"]],
				[$class: 'LabelParameterValue', name: 'SLAVE_NODE', label: nodeObject.slaveName],
				[$class: 'StringParameterValue', name: 'PIPELINE_BUILD_ID', value: this.script.BUILD_NUMBER],
				[$class: 'StringParameterValue', name: 'JOB_BUILD_ID', value: jobBuildId],
				[$class: 'StringParameterValue', name: 'CURRENT_DATE_TIME', value: currentDate],
				[$class: 'StringParameterValue', name: 'OLD_VERSION', value: jsonData["build"]["oldVersion"]],
				[$class: 'StringParameterValue', name: 'NEW_VERSION', value: jsonData["build"]["newVersion"]],
				[$class: 'StringParameterValue', name: 'APP_SERVER', value: jsonData["systemName"]],
				[$class: 'StringParameterValue', name: 'INSTANCE', value: jsonData["instance"]],
				[$class: 'StringParameterValue', name: 'CLIENT', value: jsonData["client"]],
				[$class: 'StringParameterValue', name: 'SYSTEM_ID', value: jsonData["systemId"]],
				[$class: 'StringParameterValue', name: 'SAP_USERID', value: jsonData["sapUserName"]],
				[$class: 'StringParameterValue', name: 'SAP_PASSWORD', value: jsonData["password"]],
				[$class: 'StringParameterValue', name: 'LANGUAGE', value: jsonData["language"]],
				[$class: 'StringParameterValue', name: 'TRANS_REQ', value: transReq],
				[$class: 'StringParameterValue', name: 'BUILD_LABEL', value: buildLabel]
			]
		}
		catch(Exception e){
			if(e.toString().contains("FAILURE")){
				throw e
			}
		}
	}
}