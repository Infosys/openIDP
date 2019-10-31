package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SahiReport {
	
	@SerializedName("total")
	@Expose
	private Integer total;
	@SerializedName("pass")
	@Expose
	private Integer pass;
	@SerializedName("fail")
	@Expose
	private Integer fail;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPass() {
		return pass;
	}

	public void setPass(Integer pass) {
		this.pass = pass;
	}

	public Integer getFail() {
		return fail;
	}

	public void setFail(Integer fail) {
		this.fail = fail;
	}

}
