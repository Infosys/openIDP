/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.plugins.buildsteps

/**
 *
 * This class implements IPLuginBase interface to configure job for SSH connection to remote machine
 *
 */

class SshBuilder {
	public flattenFilePath = "true";
     
	 def rmv=""
	 public void setRemove(removeprefix){
	 this.rmv=removeprefix
 }
	
	 public String getFlattenFilePath() {
			return this.flattenFilePath
	}

	public void setFlattenFilePath(String flattenFilePath) {
		this.flattenFilePath = flattenFilePath
	}
	public void invokeSshBuilder(context, serverName, userName, password, srcFiles, remoteDir, execCmd,key,keyPath) {
		this.addOptions(context, this.performMapping(srcFiles, serverName, userName, password, remoteDir, execCmd,key, keyPath));
	}

	/*
	 * This method is used to perform mapping in order to configure various parameteres
	 */

	public HashMap<String, String> performMapping(srcFiles, serverName, userName, password, remoteDir, execCmd, key,keyPath) {		
		HashMap<String, String> data = new HashMap<String, String>()
		
		data.put("configName", serverName)
		data.put("verbose","false")
		data.put("sourceFiles", srcFiles)
		data.put("excludes","")
		data.put("remoteDirectory", remoteDir)
		data.put("removePrefix",rmv)
		data.put("remoteDirectorySDF","false")
		data.put("flatten",this.flattenFilePath)
		data.put("execCommand", execCmd)
		data.put("execTimeout","0")
		data.put("usePty","false")
		data.put("noDefaultExcludes","false")
		data.put("makeEmptyDirs","false")
		data.put("patternSeparator","[, ]+")
		data.put("useAgentForwarding","")
		data.put("useWorkspaceInPromotion","false")
		data.put("usePromotionTimestamp","false")
		data.put("retries","1")
		data.put("retryDelay","1")
		data.put("label","")
		data.put("username", userName)
		data.put("encryptedPassphrase","")
		data.put("key",key)
		if( password.length() < 100){
			data.put("encryptedPassphrase", hudson.util.Secret.fromString(password).encryptedValue)
		} else{
			data.put("key",password)
		}
		data.put("keyPath",keyPath)
		data.put("continueOnError","false")
		data.put("failOnError","false")
		data.put("alwaysPublishFromMaster","false")
		data.put("masterNodeName","")
		data.put("parameterName","")
		return data
	}

	/*
	 * This method is used to add options in SSH builder
	 */

	public void addOptions(context, HashMap<String, String> data) {
		context.with{
			bapSshBuilderPlugin {
				SshBuilder.addSshBuild(delegate, data)
			}
		}
	}

	/*
	 * This mehtod is used to confiugre ssh build in jenkins job
	 */
	private static void addSshBuild(context,data){

		context.with{
			publishers{
				bapSshPublisher{
					if(data.containsKey("configName")) configName(data.get("configName"));
					if(data.containsKey("verbose")) verbose(data.get("verbose").toBoolean());
					transfers{
						bapSshTransfer{
							if(data.containsKey("sourceFiles")) sourceFiles(data.get("sourceFiles"));
							if(data.containsKey("excludes")) excludes(data.get("excludes"));
							if(data.containsKey("remoteDirectory")) remoteDirectory(data.get("remoteDirectory"));
							if(data.containsKey("removePrefix")) removePrefix(data.get("removePrefix"));
							if(data.containsKey("remoteDirectorySDF")) remoteDirectorySDF(data.get("remoteDirectorySDF").toBoolean());
							if(data.containsKey("flatten")) flatten(data.get("flatten").toBoolean());
							if(data.containsKey("execCommand")) execCommand(data.get("execCommand"));
							if(data.containsKey("execTimeout")) execTimeout(data.get("execTimeout").toInteger());
							if(data.containsKey("usePty")) usePty(data.get("usePty").toBoolean());
							if(data.containsKey("noDefaultExcludes")) noDefaultExcludes(data.get("noDefaultExcludes").toBoolean());
							if(data.containsKey("makeEmptyDirs")) makeEmptyDirs(data.get("makeEmptyDirs").toBoolean());
							if(data.containsKey("patternSeparator")) patternSeparator(data.get("patternSeparator"));
							if(data.containsKey("useAgentForwarding")) useAgentForwarding(data.get("useAgentForwarding").toBoolean());
						}
					}
					if(data.containsKey("useWorkspaceInPromotion")) useWorkspaceInPromotion(data.get("useWorkspaceInPromotion").toBoolean());
					if(data.containsKey("usePromotionTimestamp")) usePromotionTimestamp(data.get("usePromotionTimestamp").toBoolean());
					sshRetry{
						if(data.containsKey("retries")) retries(data.get("retries").toInteger());
						if(data.containsKey("retryDelay")) retryDelay(Long.parseLong(data.get("retryDelay"),10));
					}
					sshLabel{
						if(data.containsKey("label")) label(data.get("label"));
					}
					sshCredentials{
						if(data.containsKey("username")) username(data.get("username"));
						if(data.containsKey("encryptedPassphrase")) encryptedPassphrase(data.get("encryptedPassphrase"));
						if(data.containsKey("key")) key(data.get("key"));
						if(data.containsKey("keyPath")) keyPath(data.get("keyPath"));
					}
				}
			}
			if(data.containsKey("continueOnError")) continueOnError(data.get("continueOnError").toBoolean());
			if(data.containsKey("failOnError")) failOnError(data.get("failOnError").toBoolean());
			if(data.containsKey("alwaysPublishFromMaster")) alwaysPublishFromMaster(data.get("alwaysPublishFromMaster").toBoolean());
			if(data.containsKey("masterNodeName")) masterNodeName(data.get("masterNodeName"));
			paramPublish{
				if(data.containsKey("parameterName")) parameterName(data.get("parameterName"));
			}
		}
	}
}
