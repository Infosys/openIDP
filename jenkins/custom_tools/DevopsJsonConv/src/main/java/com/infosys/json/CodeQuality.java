package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infosys.jsonschema.Checkstyle;

public class CodeQuality {
	

	@SerializedName("sonar")
	@Expose
	private Sonar sonar=null;
	
	@SerializedName("fxCopReport")
	@Expose
	private FxCopReportJson fxCopReport=null;
	
	@SerializedName("sci")
	@Expose
	private Sci sci=null;
	
	@SerializedName("pmd")
	@Expose
	private Pmd pmd=null;

	@SerializedName("checkstyle")
	@Expose
	private Checkstyle checkstyle=null;
	@SerializedName("tsLint")
	@Expose
	private TSLint tsLint = null;

	@SerializedName("lint")
	@Expose
	private Lint lint = null;
	
	@SerializedName("findBugs")
	@Expose
	private FindBugs findbugs = null;

	
	
	public Lint getLint() {
		return lint;
	}

	public void setLint(Lint lint) {
		this.lint = lint;
	}

	public void setFindbugs(FindBugs findbugs) {
		this.findbugs = findbugs;
	}
	 public FindBugs getFindbugs() {
		return findbugs;
	}
	public Checkstyle getCheckstyle() {
		return checkstyle;
	}
	public TSLint getTsLint() {
		return tsLint;
	}
	public void setTsLint(TSLint tsLint) {
		this.tsLint = tsLint;
	}
	
	public void setCheckstyle(Checkstyle checkstyle) {
		this.checkstyle = checkstyle;
	}

	public Sonar getSonar() {
		return sonar;
	}

	public void setSonar(Sonar sonar) {
		this.sonar = sonar;
	}
	public Pmd getPmd() {
		return pmd;
	}

	public void setPmd(Pmd pmd) {
		this.pmd = pmd;
	}
	public FxCopReportJson getFxCopReport() {
		return fxCopReport;
	}
	public void setFxCopReport(FxCopReportJson fxCopReport) {
		this.fxCopReport = fxCopReport;
	}
	public Sci getSci() {
		return sci;
	}
	public void setSci(Sci sci) {
		this.sci = sci;
	}
	
	
}
