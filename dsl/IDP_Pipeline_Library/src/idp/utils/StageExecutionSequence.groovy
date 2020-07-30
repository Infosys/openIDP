/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package idp.utils

import java.io.Serializable;

import groovy.json.JsonBuilder

class StageExecutionSequence implements Serializable{
	def script

	StageExecutionSequence(script){
		this.script = script
	}

	def execute(jsonData) {

		for(int seq=0; seq<jsonData["pipelines"].size(); seq++){

			def pipeList = jsonData["pipelines"][seq]

			def jobpath = pipeList["applicationName"]+'_'+pipeList["pipelineName"]+'/'+pipeList["applicationName"]+'_'+pipeList["pipelineName"]+'_'+"Pipeline"
			def jobName = pipeList["applicationName"]+'_'+pipeList["pipelineName"]+'_'+"Pipeline"

			def jsonString = new JsonBuilder(pipeList).toString();
			def status = this.script.build job: jobpath, parameters: [
				[$class: 'StringParameterValue', name: 'JSON_Input', value: jsonString ]
			]
			def values = status.getBuildVariables()

		}
	}
	
	def executeBuildWithCastOnDifferentSlaves(iterationCount,waitForSlave,jsonData,basePath,buildStage,deployStage,testStage,castStage,stageFlag){
		def mainNodeObject = [:]
		def labelName = jsonData["slaveName"]
		def testLabel = jsonData["testSlaveName"]
		IDPSlaveConfiguration idpSlaveConfiguration = new IDPSlaveConfiguration(this)
		
		this.script.parallel build:{
			int i = 0
			// mutex locking and slave assignment Build
			def mainCustomWS
			for(;i<iterationCount;++i){
			
				
				mainNodeObject = idpSlaveConfiguration.executeAssignment(labelName,basePath,mainNodeObject)
				
				if( mainNodeObject.slaveActive ==null ){
					waitForSlave()
					this.script.echo 'Retrying to find an active slave with label: '+labelName
					continue;
				}
				
				if( mainNodeObject.slaveActive !=null && mainNodeObject.slaveActive && 
				mainNodeObject.slaveName!=null && mainNodeObject.slaveWorkspace!=null && mainNodeObject.token!=null) {
					this.script.lock(resource: mainNodeObject.token, inversePrecedence: false){
						this.script.echo 'Slave selected for execution: '+mainNodeObject
						//checkLockAvailability(mainNodeObject.token)
						mainCustomWS = (String)mainNodeObject.slaveWorkspace
						this.script.echo "Custom workspace: "+(String)mainNodeObject.slaveWorkspace
						buildStage(mainCustomWS,mainNodeObject)
						if(jsonData["deploy"] && stageFlag[1] == 'off'){
							stageFlag[1] = 'on'
							deployStage(mainCustomWS,mainNodeObject)
						}
						if( labelName==testLabel ){
							if(jsonData["testSelected"] && jsonData["testSelected"].equalsIgnoreCase("on") && stageFlag[2] == 'off') {
								stageFlag[2] = 'on'
								testStage(mainCustomWS,mainNodeObject)
							}
						}
					}
					break;
				}
			}
		},
		cast:{		
			if(jsonData["build"] && jsonData["build"]["cast"]){
				int i = 0;
				// mutex locking and slave assignment Build
				def castCustomWS
				for(;i<iterationCount;++i){
				
					castNodeObject=idpSlaveConfiguration.executeAssignment(castLabel,basePath,mainNodeObject)
					
					if( castNodeObject.slaveActive ==null ){
						waitForSlave()
						this.script.echo 'Retrying to find an active slave with label: '+castLabel
						continue;
					}
					
					if( castNodeObject.slaveActive !=null && castNodeObject.slaveActive && 
					castNodeObject.slaveName!=null && castNodeObject.slaveWorkspace!=null && castNodeObject.token!=null) {
					
						this.script.lock(resource: castNodeObject.token, inversePrecedence: false){
							this.script.echo 'Slave selected for execution: '+castNodeObject
							//checkLockAvailability(castNodeObject.token)
							castCustomWS = (String)castNodeObject.slaveWorkspace
							castStage(castCustomWS, castNodeObject)
						}
						break;
					}
				}
			}
		}
		
		return stageFlag
	}
	
	def executeBuildAndCastOnSameSlaves(iterationCount,waitForSlave,jsonData,basePath,buildStage,deployStage,testStage,castStage,stageFlag){
		int i = 0;
		def mainNodeObject = [:]
		def castNodeObject = [:]
		def labelName = jsonData["slaveName"]
		def testLabel = jsonData["testSlaveName"]
		def castLabel = jsonData["castSlaveName"]		
		IDPSlaveConfiguration idpSlaveConfiguration = new IDPSlaveConfiguration(this)
		
		// mutex locking and slave assignment Build
		for(;i<iterationCount;++i){
			mainNodeObject=idpSlaveConfiguration.executeAssignment(labelName,basePath,mainNodeObject)
			if( mainNodeObject.slaveActive ==null ){
				waitForSlave()
				this.script.echo 'Retrying to find an active slave with label: '+labelName
				continue;
			}
			if( mainNodeObject.slaveActive !=null && mainNodeObject.slaveActive && 
			mainNodeObject.slaveName!=null && mainNodeObject.slaveWorkspace!=null && mainNodeObject.token!=null) {
				def mainCustomWS
			
				this.script.lock(resource: mainNodeObject.token, inversePrecedence: false){
					this.script.echo 'Slave selected for execution: '+mainNodeObject
					//checkLockAvailability(mainNodeObject.token)
					mainCustomWS = (String)mainNodeObject.slaveWorkspace
					this.script.parallel build:{
						buildStage(mainCustomWS,mainNodeObject)
						if(jsonData["deploy"] && stageFlag[1] == 'off'){
							stageFlag[1] = 'on'
							deployStage(mainCustomWS,mainNodeObject)
						}
						if( labelName==testLabel ){
							if(jsonData["testSelected"] && jsonData["testSelected"].equalsIgnoreCase("on") && stageFlag[2] == 'off') {
								stageFlag[2] = 'on'
								testStage(mainCustomWS,mainNodeObject)
							}
						}
					},
					cast:{
						if (jsonData["build"] && jsonData["build"]["cast"]){
							castStage(mainCustomWS,mainNodeObject)
						}
					}
					
				}
				castSlave=mainNodeObject.slaveName
				break;
			}
			buildSlave=mainNodeObject.slaveName
			deploySlave=mainNodeObject.slaveName
			testSlave=mainNodeObject.slaveName
		}
		if(i == iterationCount){
			def errorStr = 'Total number of retries ('+iterationCount+') expired to find an active slave with label: '+labelName 			
			this.script.error(errorStr)
		}
		
		return stageFlag
	}
}
