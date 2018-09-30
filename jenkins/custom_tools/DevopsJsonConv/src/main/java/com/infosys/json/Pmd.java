package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pmd {
	@SerializedName("critical")
	@Expose
	private Integer critical;
	@SerializedName("blocker")
	@Expose
	private Integer blocker;
	@SerializedName("major")
	@Expose
	private Integer major;
	@SerializedName("minor")
	@Expose
	private Integer minor;
	@SerializedName("info")
	@Expose
	private Integer info;


	public Integer getCritical() {
		return critical;
	}

	public void setCritical(Integer critical) {
		this.critical = critical;
	}

	public Integer getBlocker() {
		return blocker;
	}

	public void setBlocker(Integer blocker) {
		this.blocker = blocker;
	}

	public Integer getMajor() {
		return major;
	}

	public void setMajor(Integer major) {
		this.major = major;
	}

	public Integer getMinor() {
		return minor;
	}

	public void setMinor(Integer minor) {
		this.minor = minor;
	}

	public Integer getInfo() {
		return info;
	}

	public void setInfo(Integer info) {
		this.info = info;
	}

}
