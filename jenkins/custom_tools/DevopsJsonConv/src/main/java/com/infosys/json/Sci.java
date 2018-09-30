package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sci {

	@SerializedName("high")
    @Expose
    private Integer high;
	@SerializedName("medium")
    @Expose
    private Integer medium;
	@SerializedName("low")
    @Expose
    private Integer low;
	public Integer getHigh() {
		return high;
	}
	public void setHigh(Integer high) {
		this.high = high;
	}
	public Integer getMedium() {
		return medium;
	}
	public void setMedium(Integer medium) {
		this.medium = medium;
	}
	public Integer getLow() {
		return low;
	}
	public void setLow(Integer low) {
		this.low = low;
	}
	
}
