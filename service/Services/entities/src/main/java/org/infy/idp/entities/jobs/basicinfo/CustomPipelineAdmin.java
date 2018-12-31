/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.entities.jobs.basicinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Infosys
 *
 */
public class CustomPipelineAdmin  {
	@SerializedName("pipelineId")
    @Expose
    private int pipelineId;
	
	@SerializedName("adminName")
    @Expose
    private String adminName;
	
	@SerializedName("editBasic")
    @Expose
    private String editBasic;
	
	@SerializedName("editCode")
    @Expose
    private String editCode;
	
	@SerializedName("editBuild")
    @Expose
    private String editBuild;
	
	@SerializedName("editDeploy")
    @Expose
    private String editDeploy;
	
	@SerializedName("editTest")
    @Expose
    private String editTest;

	public int getPipelineId() {
		return pipelineId;
	}

	public void setPipelineId(int pipelineId) {
		this.pipelineId = pipelineId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getEditBasic() {
		return editBasic;
	}

	public void setEditBasic(String editBasic) {
		this.editBasic = editBasic;
	}

	public String getEditCode() {
		return editCode;
	}

	public void setEditCode(String editCode) {
		this.editCode = editCode;
	}

	public String getEditBuild() {
		return editBuild;
	}

	public void setEditBuild(String editBuild) {
		this.editBuild = editBuild;
	}

	public String getEditDeploy() {
		return editDeploy;
	}

	public void setEditDeploy(String editDeploy) {
		this.editDeploy = editDeploy;
	}

	public String getEditTest() {
		return editTest;
	}

	public void setEditTest(String editTest) {
		this.editTest = editTest;
	}
	
	
	
}
