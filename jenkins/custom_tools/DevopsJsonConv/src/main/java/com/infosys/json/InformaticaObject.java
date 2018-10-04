/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InformaticaObject {
	@SerializedName("folderName")
	@Expose
	private String folderName;
	@SerializedName("sourceDefinition")
	@Expose
	private List<String> sourceDefinition;
	@SerializedName("targetDefinition")
	@Expose
	private List<String> targetDefinition;
	@SerializedName("mapping")
	@Expose
	private List<String> mapping;
	@SerializedName("sessionconfig")
	@Expose
	private List<String> sessionconfig;
	@SerializedName("workflow")
	@Expose
	private List<String> workflow;
	@SerializedName("mapplet")
	@Expose
	private List<String> mapplet;

	@SerializedName("session")
	@Expose
	private List<String> session;
	@SerializedName("sequence")
	@Expose
	private List<String> sequence;
	@SerializedName("worklet")
	@Expose
	private List<String> worklet;
	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public List<String> getSourceDefinition() {
		return sourceDefinition;
	}

	public void setSourceDefinition(List<String> sourceDefinition) {
		this.sourceDefinition = sourceDefinition;
	}

	public List<String> getTargetDefinition() {
		return targetDefinition;
	}

	public void setTargetDefinition(List<String> targetDefinition) {
		this.targetDefinition = targetDefinition;
	}

	public List<String> getMapping() {
		return mapping;
	}

	public void setMapping(List<String> mapping) {
		this.mapping = mapping;
	}

	public List<String> getSessionconfig() {
		return sessionconfig;
	}

	public void setSessionconfig(List<String> sessionconfig) {
		this.sessionconfig = sessionconfig;
	}

	public List<String> getWorkflow() {
		return workflow;
	}

	public void setWorkflow(List<String> workflow) {
		this.workflow = workflow;
	}

	public List<String> getMapplet() {
		return mapplet;
	}

	public void setMapplet(List<String> mapplet) {
		this.mapplet = mapplet;
	}

	public List<String> getSession() {
		return session;
	}

	public void setSession(List<String> session) {
		this.session = session;
	}

	public List<String> getSequence() {
		return sequence;
	}

	public void setSequence(List<String> sequence) {
		this.sequence = sequence;
	}

	public List<String> getWorklet() {
		return worklet;
	}

	public void setWorklet(List<String> worklet) {
		this.worklet = worklet;
	}

	
}
