/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Functional {
	@SerializedName("python")
	@Expose
	private PythonUT python;


	@SerializedName("accleratest")
	@Expose
	private Accleratest accleratest;
	@SerializedName("protractor")
	@Expose
	private Protractor protractor;
	@SerializedName("itops")
	@Expose
	private Itops itops;
	@SerializedName("robot")
	@Expose
	private RobotJson robot;
	@SerializedName("selenium")
	@Expose
	private Selenium selenium;
	@SerializedName("ecatt")
	@Expose
	private Ecatt ecatt;
	@SerializedName("soapUIReport")
	@Expose
	private SoapUIReport soapUIReport;
	@SerializedName("qualitia")
	@Expose
	private Qualitia qualitia;
	@SerializedName("jUnit")
	@Expose
	private JUnit jUnit;
	@SerializedName("parasoftSOATest")
	@Expose
	private ParasoftSOATest parasoftSOATest;
	@SerializedName("sahiReport")
	@Expose
	private SahiReport sahiReport;
	@SerializedName("hpUft")
	@Expose
	private HpUft hpUft;


	

	public PythonUT getPython() {
		return python;
	}

	public void setPython(PythonUT python) {
		this.python = python;
	}
	public JUnit getjUnit() {
		return jUnit;
	}

	public void setjUnit(JUnit jUnit) {
		this.jUnit = jUnit;
	}

	public RobotJson getRobot() {
		return robot;
	}

	public void setRobot(RobotJson robot) {
		this.robot = robot;
	}

	public Qualitia getQualitia() {
		return qualitia;
	}

	public void setQualitia(Qualitia qualitia) {
		this.qualitia = qualitia;
	}

	public Accleratest getAccleratest() {
		return accleratest;
	}

	public void setAccleratest(Accleratest accleratest) {
		this.accleratest = accleratest;
	}

	public Itops getItops() {
		return itops;
	}

	public void setItops(Itops itops) {
		this.itops = itops;
	}

	public Selenium getSelenium() {
		return selenium;
	}

	public void setSelenium(Selenium selenium) {
		this.selenium = selenium;
	}

	public SoapUIReport getSoapUIReport() {
		return soapUIReport;
	}

	public void setSoapUIReport(SoapUIReport soapUIReport) {
		this.soapUIReport = soapUIReport;
	}

	public Ecatt getEcatt() {
		return ecatt;
	}

	public void setEcatt(Ecatt ecatt) {
		this.ecatt = ecatt;
	}

	public Protractor getProtractor() {
		return protractor;
	}

	public void setProtractor(Protractor protractor) {
		this.protractor = protractor;
	}

	public ParasoftSOATest getParasoftSOATest() {
		return parasoftSOATest;
	}

	public void setParasoftSOATest(ParasoftSOATest parasoftSOATest) {
		this.parasoftSOATest = parasoftSOATest;
	}
	
	public SahiReport getSahiReport() {
		return sahiReport;
	}

	public void setSahiReport(SahiReport sahiReport) {
		this.sahiReport = sahiReport;
	}

	public HpUft getHpUft() {
		return hpUft;
	}

	public void setHpUft(HpUft hpUft) {
		this.hpUft = hpUft;
	}
}
