/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.buildsteps

//import java.util.HashMap
import org.infy.idp.entities.jobs.IDPJob
import com.infy.idp.interfaces.IPluginBase
//import com.infy.idp.utils.Constants

class Gradle implements IPluginBase {

	private String tasksVal = ''
	private String rootBuildScriptDir = ''
	private String buildFile = 'build.gradle'
	private String systemProperties = ''
	private String projectProperties = ''

	public void setTasksVal(String tasksVal) {
		this.tasksVal = tasksVal
	}

	public void setRootBuildScriptDir(String rootBuildScriptDir) {
		this.rootBuildScriptDir = rootBuildScriptDir
	}

	public void setBuildFile(String buildFile) {
		this.buildFile = buildFile
	}
	public void setProjectProperties(String projectProperties) {
		this.projectProperties = projectProperties
	}

	public void setSystemProperties(String systemProperties) {
		this.systemProperties = systemProperties
	}

	@Override
	public void add(context,IDPJob dataObj) {
		this.addOptions(context, this.performMapping(dataObj))
	}

	@Override
	public HashMap<String, String> performMapping(IDPJob dataObj) {

		HashMap<String, String> data = new HashMap<String, String>()

		data.put('buildFile', this.buildFile)
		// data.put('fromRootBuildScriptDir', false)
		data.put('gradleName', '(Default)')
		data.put('makeExecutable', true)
		// data.put('passAsProperties', false)
		data.put('passAllAsSystemProperties', false)
		data.put('passAllAsProjectProperties', false)
		data.put('rootBuildScriptDir', this.rootBuildScriptDir)
		data.put('switches', '')
		data.put('tasks', this.tasksVal)
		data.put('useWorkspaceAsHome', false)
		data.put('useWrapper', true)
		data.put('wrapperLocation',this.rootBuildScriptDir )
		data.put('projectProperties', this.projectProperties)
		data.put('systemProperties', this.systemProperties)
		
		return data
	}

	@Override
	public void addOptions(jobContext, HashMap<String, String> data) {

		Gradle.addGeneralBuildSettings(jobContext, data)
		//context.with{

		//gradle {

		//Gradle.addGeneralBuildSettings(delegate, data)
		//}
		//}
	}

	private static void addGeneralBuildSettings(jobContext,data){

		jobContext.with {

			/*			configure {
			 if(data.containsKey('passAllAsSystemProperties')) passAllAsSystemProperties(data.get('passAllAsSystemProperties'))
			 if(data.containsKey('passAllAsProjectProperties')) passAllAsProjectProperties(data.get('passAllAsProjectProperties'))
			 }
			 if(data.containsKey('buildFile')) buildFile(data.get('buildFile'))
			 if(data.containsKey('fromRootBuildScriptDir')) fromRootBuildScriptDir(data.get('fromRootBuildScriptDir'))
			 if(data.containsKey('gradleName')) gradleName(data.get('gradleName'))
			 if(data.containsKey('makeExecutable')) makeExecutable(data.get('makeExecutable'))
			 if(data.containsKey('passAsProperties')) passAsProperties(data.get('passAsProperties'))
			 if(data.containsKey('rootBuildScriptDir')) rootBuildScriptDir(data.get('rootBuildScriptDir'))
			 if(data.containsKey('switches')) switches(data.get('switches'))
			 if(data.containsKey('tasks')) tasks(data.get('tasks'))
			 if(data.containsKey('useWorkspaceAsHome')) useWorkspaceAsHome(data.get('useWorkspaceAsHome'))
			 if(data.containsKey('useWrapper')) useWrapper(data.get('useWrapper'))*/

			configure {

				it / builders << 'hudson.plugins.gradle.Gradle' {

					if(data.containsKey('buildFile')) buildFile(data.get('buildFile'))
					if(data.containsKey('gradleName')) gradleName(data.get('gradleName'))
					if(data.containsKey('makeExecutable')) makeExecutable(data.get('makeExecutable'))
					if(data.containsKey('rootBuildScriptDir')) rootBuildScriptDir(data.get('rootBuildScriptDir'))
					if(data.containsKey('switches')) switches(data.get('switches'))
					if(data.containsKey('tasks')) tasks(data.get('tasks'))
					if(data.containsKey('useWorkspaceAsHome')) useWorkspaceAsHome(data.get('useWorkspaceAsHome'))
					if(data.containsKey('useWrapper')) useWrapper(data.get('useWrapper'))
					if(data.containsKey('wrapperLocation')) wrapperLocation(data.get('wrapperLocation'))
					if(data.containsKey('passAllAsSystemProperties')) passAllAsSystemProperties(data.get('passAllAsSystemProperties'))
					if(data.containsKey('passAllAsProjectProperties')) passAllAsProjectProperties(data.get('passAllAsProjectProperties'))
					if(data.containsKey('projectProperties')) projectProperties(data.get('projectProperties'))
					if(data.containsKey('systemProperties')) systemProperties(data.get('systemProperties'))
				}
			}
		}
	}
}
