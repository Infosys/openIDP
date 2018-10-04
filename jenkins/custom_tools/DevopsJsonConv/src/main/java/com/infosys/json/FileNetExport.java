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

public class FileNetExport {
	@SerializedName("triggerId")
	@Expose
	private String triggerId;
	@SerializedName("enviornment")
	@Expose
	private String enviornment;
	@SerializedName("propertyTypeList")
	@Expose
	private List<FileNetExportPropertyType> propertyTypeList;
	@SerializedName("lifeCyclePolicyTypeList")
	@Expose
	private List<FileNetExportLifeCyclePolicyType> lifeCyclePolicyTypeList;
	@SerializedName("lifeCycleActionTypeList")
	@Expose
	private List<FileNetExportLifeCycleActionType> lifeCycleActionTypeList;
	@SerializedName("choiceListTypeList")
	@Expose
	private List<FileNetExportChoiceListType> choiceListTypeList;
	@SerializedName("classDefinitionTypeList")
	@Expose
	private List<FileNetExportClassDefinitionType> classDefinitionTypeList;
	@SerializedName("folderTypeList")
	@Expose
	private List<FileNetExportFolderType> folderTypeList;
	@SerializedName("documentTypeList")
	@Expose
	private List<FileNetExportDocumentType> documentTypeList;
	@SerializedName("eventActionTypeList")
	@Expose
	private List<FileNetExportEventActionType> eventActionTypeList;
	@SerializedName("otherTypeList")
	@Expose
	private List<FileNetExportOtherType> otherTypeList;

	public List<FileNetExportPropertyType> getPropertyTypeList() {
		return propertyTypeList;
	}

	public void setPropertyTypeList(List<FileNetExportPropertyType> propertyTypeList) {
		this.propertyTypeList = propertyTypeList;
	}

	public List<FileNetExportLifeCyclePolicyType> getLifeCyclePolicyTypeList() {
		return lifeCyclePolicyTypeList;
	}

	public void setLifeCyclePolicyTypeList(List<FileNetExportLifeCyclePolicyType> lifeCyclePolicyTypeList) {
		this.lifeCyclePolicyTypeList = lifeCyclePolicyTypeList;
	}

	public List<FileNetExportLifeCycleActionType> getLifeCycleActionTypeList() {
		return lifeCycleActionTypeList;
	}

	public void setLifeCycleActionTypeList(List<FileNetExportLifeCycleActionType> lifeCycleActionTypeList) {
		this.lifeCycleActionTypeList = lifeCycleActionTypeList;
	}

	public List<FileNetExportChoiceListType> getChoiceListTypeList() {
		return choiceListTypeList;
	}

	public void setChoiceListTypeList(List<FileNetExportChoiceListType> choiceListTypeList) {
		this.choiceListTypeList = choiceListTypeList;
	}

	public List<FileNetExportClassDefinitionType> getClassDefinitionTypeList() {
		return classDefinitionTypeList;
	}

	public void setClassDefinitionTypeList(List<FileNetExportClassDefinitionType> classDefinitionTypeList) {
		this.classDefinitionTypeList = classDefinitionTypeList;
	}

	public List<FileNetExportFolderType> getFolderTypeList() {
		return folderTypeList;
	}

	public void setFolderTypeList(List<FileNetExportFolderType> folderTypeList) {
		this.folderTypeList = folderTypeList;
	}

	public List<FileNetExportDocumentType> getDocumentTypeList() {
		return documentTypeList;
	}

	public void setDocumentTypeList(List<FileNetExportDocumentType> documentTypeList) {
		this.documentTypeList = documentTypeList;
	}

	public List<FileNetExportEventActionType> getEventActionTypeList() {
		return eventActionTypeList;
	}

	public void setEventActionTypeList(List<FileNetExportEventActionType> eventActionTypeList) {
		this.eventActionTypeList = eventActionTypeList;
	}

	public List<FileNetExportOtherType> getOtherTypeList() {
		return otherTypeList;
	}

	public void setOtherTypeList(List<FileNetExportOtherType> otherTypeList) {
		this.otherTypeList = otherTypeList;
	}

	public String getTriggerId() {
		return triggerId;
	}

	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}

	public String getEnviornment() {
		return enviornment;
	}

	public void setEnviornment(String enviornment) {
		this.enviornment = enviornment;
	}
}