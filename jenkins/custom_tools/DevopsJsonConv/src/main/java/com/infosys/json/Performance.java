package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Performance {
	
	@SerializedName("jMeter")
	@Expose
	private JMeter jMeter;

	public JMeter getjMeter() {
		return jMeter;
	}

	public void setjMeter(JMeter jMeter) {
		this.jMeter = jMeter;
	}	
}