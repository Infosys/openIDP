package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SonarDetailsLocmeasures {
	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	@SerializedName("metric")
	@Expose
	private String metric;
	@SerializedName("value")
	@Expose
	private int value;
}
