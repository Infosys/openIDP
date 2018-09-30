package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileNet {

	@SerializedName("fileNetExport")
	@Expose
	private FileNetExport fileNetExport;

	@SerializedName("fileNetImport")
	@Expose
	private FileNetImport fileNetImport;

	@SerializedName("env")
	@Expose
	private String env;

	@SerializedName("triggerId")
	@Expose
	private Integer triggerId;

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public Integer getTriggerId() {
		return triggerId;
	}

	public void setTriggerId(Integer triggerId) {
		this.triggerId = triggerId;
	}

	public FileNetImport getFileNetImport() {
		return fileNetImport;
	}

	public void setFileNetImport(FileNetImport fileNetImport) {
		this.fileNetImport = fileNetImport;
	}

	public FileNetExport getFileNetExport() {
		return fileNetExport;
	}

	public void setFileNetExport(FileNetExport fileNetExport) {
		this.fileNetExport = fileNetExport;
	}

	@Override
	public String toString() {
		return "FileNet [fileNetExport=" + fileNetExport + ", fileNetImport=" + fileNetImport + ", env=" + env
				+ ", triggerId=" + triggerId + "]";
	}

}