/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package idp.utils


class IDPJobInfo implements Serializable {
	def script
	
	IDPJobInfo(script){
		this.script = script
	}
	
	@NonCPS
	def jobNames(basename) {
	  def project = Jenkins.instance.getItemByFullName(basename)
	  def childItems = project.items
	  def targets = []
	  for (int i = 0; i < childItems.size(); i++) {
		  def childItem = childItems[i]
		  if (!childItem instanceof AbstractProject) continue;
		  if (childItem.fullName == project.fullName) continue;
		  targets.add(childItem.fullName)
	  }
	  return targets
	}

	@NonCPS
	def GetLastPara(paramterName){
		def r = null
		echo "${this.script.env.JOB_BASE_NAME}"
		r = jenkins.model.Jenkins.instance.getItemByFullName("${this.script.env.JOB_NAME}")
		def b
		TaskListener listener=null
		for(def bld in r.builds){
			if(bld.getEnvironment(listener).get(paramterName) != "NA"){
				b = bld
				break
			}
		}
		def a='NA'
		if(b != null){
			a = b.getEnvironment(listener).get(paramterName)
		}
		b = null
		r = null
		return a
	}
}
