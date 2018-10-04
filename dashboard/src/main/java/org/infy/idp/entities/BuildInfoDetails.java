package org.infy.idp.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BuildInfoDetails {

	@JsonProperty("appid")
	private Integer appid;
	@JsonProperty("buildtime")
	private Double buildtime;
	@JsonProperty("buildstatus")
	private String buildstatus;
	@JsonProperty("buildid")
	private Integer buildid;
	@JsonProperty("lastcompletebuildid")
	private Integer lastcompletebuildid;
	@JsonProperty("lastsuccessfulbuildid")
	private Integer lastsuccessfulbuildid;
	@JsonProperty("lastunstablebuildid")
	private Integer lastunstablebuildid;
	@JsonProperty("lastunsuccessfulbuildid")
	private Integer lastunsuccessfulbuildid;
	@JsonProperty("lastfailedbuildid")
	private Integer lastfailedbuildid;
	@JsonProperty("pipelinebuildno")
	private Integer pipelinebuildno;
	@JsonProperty("score")
	private Double score;
	@JsonProperty("stagename")
	private String stagename;
	public Integer getAppid() {
		return appid;
	}
	public void setAppid(Integer appid) {
		this.appid = appid;
	}
	public Double getBuildtime() {
		return buildtime;
	}
	public void setBuildtime(Double buildtime) {
		this.buildtime = buildtime;
	}
	public String getBuildstatus() {
		return buildstatus;
	}
	public void setBuildstatus(String buildstatus) {
		this.buildstatus = buildstatus;
	}
	public Integer getBuildid() {
		return buildid;
	}
	public void setBuildid(Integer buildid) {
		this.buildid = buildid;
	}
	public Integer getLastcompletebuildid() {
		return lastcompletebuildid;
	}
	public void setLastcompletebuildid(Integer lastcompletebuildid) {
		this.lastcompletebuildid = lastcompletebuildid;
	}
	public Integer getLastsuccessfulbuildid() {
		return lastsuccessfulbuildid;
	}
	public void setLastsuccessfulbuildid(Integer lastsuccessfulbuildid) {
		this.lastsuccessfulbuildid = lastsuccessfulbuildid;
	}
	public Integer getLastunstablebuildid() {
		return lastunstablebuildid;
	}
	public void setLastunstablebuildid(Integer lastunstablebuildid) {
		this.lastunstablebuildid = lastunstablebuildid;
	}
	public Integer getLastunsuccessfulbuildid() {
		return lastunsuccessfulbuildid;
	}
	public void setLastunsuccessfulbuildid(Integer lastunsuccessfulbuildid) {
		this.lastunsuccessfulbuildid = lastunsuccessfulbuildid;
	}
	public Integer getLastfailedbuildid() {
		return lastfailedbuildid;
	}
	public void setLastfailedbuildid(Integer lastfailedbuildid) {
		this.lastfailedbuildid = lastfailedbuildid;
	}
	public Integer getPipelinebuildno() {
		return pipelinebuildno;
	}
	public void setPipelinebuildno(Integer pipelinebuildno) {
		this.pipelinebuildno = pipelinebuildno;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public String getStagename() {
		return stagename;
	}
	public void setStagename(String stagename) {
		this.stagename = stagename;
	}
	
	
}
