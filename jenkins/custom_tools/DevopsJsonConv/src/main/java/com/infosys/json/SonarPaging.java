package com.infosys.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SonarPaging {

	@JsonProperty("pageIndex")
	private String pageIndex;
	@JsonProperty("pageSize")
	private String pageSize;
	@JsonProperty("total")
	private String total;
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
}
