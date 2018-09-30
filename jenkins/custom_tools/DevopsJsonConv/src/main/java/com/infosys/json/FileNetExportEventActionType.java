package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileNetExportEventActionType {
	@SerializedName("objectStore")
	@Expose
	private String objectStore;

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("id")
	@Expose
	private String id;
	
	@SerializedName("objectType")
	@Expose
	private String objectType="Event Action";

	
	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectStore() {
		return objectStore;
	}

	public void setObjectStore(String objectStore) {
		this.objectStore = objectStore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "FileNetExportEventActionType [objectStore=" + objectStore + ", name=" + name + ", id=" + id
				+ ", objectType=" + objectType + "]";
	}

}
