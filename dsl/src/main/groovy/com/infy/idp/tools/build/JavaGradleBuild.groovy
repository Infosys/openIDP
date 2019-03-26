/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.build



import com.infy.idp.plugins.buildsteps.Gradle
import com.infy.idp.plugins.publishers.ArchiveArtifacts
import com.infy.idp.plugins.publishers.JacocoCC
import com.infy.idp.tools.JUnitArchive
import com.infy.idp.utils.Constants



class JavaGradleBuild {
	
	static String basepath

	public static void javaGradleBuildJobCreation(context, jsonData, basepath) {
		
		context.with {
		
			//Standard IDP Settings
			configure { it / canRoam(false) }
			concurrentBuild(true)

			//Configurable Settings of Job
			//addWrappers(delegate, jsonData)
			addSteps(delegate, context, jsonData)

			// Post Build Actions
			addPublishers(delegate, jsonData)
			addPostBuildSteps(delegate, jsonData)

			//Optional settings of job
			String customWS = 'workspace/' + basepath + Constants.WORKSPACENAMEPOSTFIX
			if(customWS)	customWorkspace('$IDP_WS')
		}
				

	}


	
	private static void addSteps(context, jobContext, jsonData) {
		
		context.with {
			
			steps {


				def buildFileLocation = jsonData.buildInfo.modules.getAt(0).relativePath.replace('\\', '/')

				Gradle gradle = new Gradle()
				gradle.setTasksVal(getTasks(jsonData))
				gradle.setRootBuildScriptDir(buildFileLocation)
				def sonarPropPath= jsonData.buildInfo.sonarProperties

				//gradle.setWrapperLocation(buildFileLocation)
				//gradle.setBuildFile(buildFileName)

				if (jsonData.buildInfo.modules.getAt(0).codeAnalysis.contains(Constants.SONAR)) {
					def sonarPropertiesStr = ''
					def sonarProjectKey = jsonData.buildInfo.sonarProjectKey
					if (sonarProjectKey == null || sonarProjectKey == "") {
						sonarProjectKey = jsonData.basicInfo.applicationName + '_' + jsonData.basicInfo.pipelineName
						sonarPropertiesStr += 'sonar.projectKey=' + sonarProjectKey + '\n'

					} else {
						sonarPropertiesStr += 'sonar.projectKey=' + sonarProjectKey + '\n'
					}
					if(null != jsonData.buildInfo.sonarUrl && !''.equalsIgnoreCase(jsonData.buildInfo.sonarUrl)) {
						sonarPropertiesStr  += 'sonar.host.url=' + jsonData.buildInfo.sonarUrl + '\n'
					}
					if (null != jsonData.buildInfo.sonarUserName && !''.equalsIgnoreCase(jsonData.buildInfo.sonarUserName)) {
						sonarPropertiesStr +='sonar.login=' + jsonData.buildInfo.sonarUserName + '\n'

					}
					if(null != jsonData.buildInfo.sonarPassword && !''.equalsIgnoreCase(jsonData.buildInfo.sonarPassword))
					{
						sonarPropertiesStr += 'sonar.password=' + jsonData.buildInfo.sonarPassword+ '\n'
					}
					gradle.setSystemProperties(sonarPropertiesStr)
				}

				if(jsonData.buildInfo.modules.getAt(0).MVNOPTS && jsonData.buildInfo.modules.getAt(0).MVNOPTS!=''){
					gradle.setProjectProperties(jsonData.buildInfo.modules.getAt(0).MVNOPTS  + '')
				}
				
				gradle.add(jobContext, jsonData)
				

			}
		}
	}

	public static void addPublishers(context, jsonData) {

		context.with {

			publishers {
				
				//(new DiscardBuild()).add(delegate, jsonData)

				ArchiveArtifacts archiveArtifacts;
				if (jsonData.buildInfo.modules.getAt(0).unitTesting == 'on') {
					 archiveArtifacts = new ArchiveArtifacts()
					JUnitArchive jUnitArchive = new JUnitArchive()
					jUnitArchive.updatePattern('**/build/test-results/**/TEST-*.xml')
					jUnitArchive.invokeJUnitArchive(delegate,jsonData)
					archiveArtifacts.updatePattern('**/build/test-results/**/TEST*.xml,**/build/reports/tests/**/*.*')

				}

				if (jsonData.buildInfo.modules.getAt(0).unitTesting == 'on' && jsonData.buildInfo.modules.getAt(0).codeCoverage == 'on') {
					if( archiveArtifacts == null ){
						archiveArtifacts = new ArchiveArtifacts()
					}
					(new JacocoCC()).add(delegate, jsonData)
					archiveArtifacts.updatePattern('**/build/reports/jacoco/**/*.*')
				}

				if( archiveArtifacts != null){
					archiveArtifacts.add(delegate, jsonData)
				}

				
			}
		}
	}

		private static void addPostBuildSteps(context, jsonData) {
			def sonarPropPath= jsonData.buildInfo.sonarProperties

			def sonarProjectKey = jsonData.buildInfo.sonarProjectKey
			if (jsonData.buildInfo.modules.getAt(0).codeAnalysis.contains(Constants.SONAR) && (sonarPropPath != null && sonarPropPath != '')) {
				def propertiesStr = '';
				if (null != jsonData.buildInfo.sonarUserName && !''.equalsIgnoreCase(jsonData.buildInfo.sonarUserName)) {

					propertiesStr = 'sonar.login=' + jsonData.buildInfo.sonarUserName + '\n'
				}
				if (null != jsonData.buildInfo.sonarPassword && !''.equalsIgnoreCase(jsonData.buildInfo.sonarPassword)) {
					propertiesStr += 'sonar.password=' + '\$SONAR_PASSWORD' + '\n'
				}
				if (jsonData.buildInfo.sonarUrl != null || jsonData.buildInfo.sonarUrl != '') {
					propertiesStr += 'sonar.host.url=' + jsonData.buildInfo.sonarUrl + '\n'

				}
				if (sonarProjectKey != null && sonarProjectKey != '') {
					propertiesStr += 'sonar.projectKey=' + sonarProjectKey + '\n'

				}
				sonarRunnerBuilder {

					javaOpts('')
					jdk('')
					project(sonarPropPath)
					properties(propertiesStr)
					sonarScannerName('IDP_sonar')
					task('')
				}


			}
		}
	
	
	
	
	private static String getTasks(jsonData){

		String tasks = ''
		
		// Goal for Unit Testing and Code Coverage
		 if(jsonData.buildInfo.modules.getAt(0).clean == 'on')
			tasks += 'clean '
		if(jsonData.buildInfo.modules.getAt(0).install == 'on')
			tasks += 'build '
		if(jsonData.buildInfo.modules.getAt(0).customTasks && jsonData.buildInfo.modules.getAt(0).customTasks!=''){
			tasks += (jsonData.buildInfo.modules.getAt(0).customTasks  + ' ')
		}
		
		def coverageGoal = jsonData.buildInfo.modules.getAt(0).args
		if(coverageGoal && coverageGoal != '' && jsonData.buildInfo.modules.getAt(0).codeCoverage == 'on')
			tasks += (coverageGoal + ' ')
		else if(jsonData.buildInfo.modules.getAt(0).unitTesting == 'on')
			tasks += 'test '
			
		// Goal for Sonar Code Analysis
		if(codeQualityOn(jsonData.buildInfo.modules.getAt(0).codeAnalysis, Constants.SONAR))
			tasks += 'sonarqube '			
	
		return tasks
	}

		
	public static def codeQualityOn(caArr, tool) {
		caArr.contains(tool)
	}
}
