/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import java.util.ArrayList;
import java.util.List;

public class Reports {
	private ArrayList<CheckStyleReports> checkReports = null;
	private ArrayList<PMDReports> pmdReports = null;
	private ArrayList<FindBugsReports> findBugsReports = null;
	private ArrayList<QualitiaReports> qualitia = null;
	private ArrayList<RobotReports> robotReports = null;
	private ArrayList<SeleniumReports> seleniumReports = null;
	private ArrayList<ITopsReports> iTopsReps = null;
	private ArrayList<CheckmarxReports> checkmarkreports = null;
	private ArrayList<JUnitReports> junitReports = null;
	private ArrayList<AccelerateReports> accelerateReports = null;
	private ArrayList<CoberturaReports> coberturareports = null;
	private ArrayList<JMeterReports> jMeterReports = null;
	private ArrayList<JunitReportsclass> jUnitReports = null;

	public void setJunitreports(ArrayList<JunitReportsclass> jUnitReports) {
		this.jUnitReports = jUnitReports;
	}

	public List<JunitReportsclass> getJunitreports() {
		return jUnitReports;
	}

	public void addJUnitReports(String url) {
		if (this.jUnitReports == null) {
			jUnitReports = new ArrayList<>();
		}
		JunitReportsclass rpo = new JunitReportsclass();
		rpo.setURL(url);
		this.jUnitReports.add(rpo);
	}

	public void setjMeterReports(ArrayList<JMeterReports> jMeterReports) {
		this.jMeterReports = jMeterReports;
	}

	public List<JMeterReports> getjMeterReports() {
		return jMeterReports;
	}

	public void addJMeterReports(String url) {
		if (this.jMeterReports == null) {
			jMeterReports = new ArrayList<>();
		}
		JMeterReports rpo = new JMeterReports();
		rpo.setURL(url);
		this.jMeterReports.add(rpo);
	}

	public void setCoberturareports(ArrayList<CoberturaReports> coberturareports) {
		this.coberturareports = coberturareports;
	}

	public List<CoberturaReports> getCoberturareports() {
		return coberturareports;
	}

	public void addCoberturaReport(String url) {
		if (this.coberturareports == null) {
			coberturareports = new ArrayList<>();
		}
		CoberturaReports rpo = new CoberturaReports();
		rpo.setURL(url);
		this.coberturareports.add(rpo);
	}

	public void addAccelerateReport(String url) {
		if (this.accelerateReports == null) {
			accelerateReports = new ArrayList<>();
		}
		AccelerateReports rpo = new AccelerateReports();
		rpo.setURL(url);
		this.accelerateReports.add(rpo);
	}

	public void setAccelerateReports(ArrayList<AccelerateReports> accelerateReport) {
		accelerateReports = accelerateReport;
	}

	public List<AccelerateReports> getAccelerateReports() {
		return accelerateReports;
	}

	//
	public void setJunitReports(ArrayList<JUnitReports> junitReport) {
		junitReports = junitReport;
	}

	public List<JUnitReports> getJunitReports() {
		return junitReports;
	}

	public void addJUnitReport(String url) {
		if (this.junitReports == null) {
			junitReports = new ArrayList<>();
		}
		JUnitReports rpo = new JUnitReports();
		rpo.setURL(url);
		this.junitReports.add(rpo);
	}

	public void setCheckmarxReports(ArrayList<CheckmarxReports> checkmarxReports) {
		this.checkmarkreports = checkmarxReports;
	}

	public List<CheckmarxReports> getCheckmarxReports() {
		return this.checkmarkreports;
	}

	public void addCheckmarxReport(String url) {
		if (this.checkmarkreports == null) {
			checkmarkreports = new ArrayList<>();
		}
		CheckmarxReports rpo = new CheckmarxReports();
		rpo.setURL(url);
		this.checkmarkreports.add(rpo);
	}

	//
	public void setCheckReports(ArrayList<CheckStyleReports> checkReports) {
		this.checkReports = checkReports;
	}

	public List<CheckStyleReports> getCheckReports() {
		return checkReports;
	}

	public void addCheckStyleReport(String url) {
		if (this.checkReports == null) {
			checkReports = new ArrayList<>();
		}
		CheckStyleReports rpo = new CheckStyleReports();
		rpo.setURL(url);
		this.checkReports.add(rpo);
	}

	public List<ITopsReports> getiTopsReps() {
		return iTopsReps;
	}

	public void setiTopsReps(ArrayList<ITopsReports> iTopsReps) {
		this.iTopsReps = iTopsReps;
	}

	public void addiTopsReport(String url) {
		if (iTopsReps == null) {
			iTopsReps = new ArrayList<>();
		}
		ITopsReports rpo = new ITopsReports();
		rpo.setURL(url);
		this.iTopsReps.add(rpo);
	}

	public List<SeleniumReports> getSeleniumReports() {
		return seleniumReports;
	}

	public void setSeleniumReports(ArrayList<SeleniumReports> seleniumReports) {
		this.seleniumReports = seleniumReports;
	}

	public void addSeleniumReport(String url) {
		if (seleniumReports == null) {
			seleniumReports = new ArrayList<>();
		}
		SeleniumReports rpo = new SeleniumReports();
		rpo.setURL(url);
		this.seleniumReports.add(rpo);
	}

	public void setRobotReports(ArrayList<RobotReports> robotReports) {
		this.robotReports = robotReports;
	}

	public List<RobotReports> getRobotReports() {
		return robotReports;
	}

	public void addRobotReports(String url) {
		if (robotReports == null) {
			robotReports = new ArrayList<>();
		}
		RobotReports rpo = new RobotReports();
		rpo.setURL(url);
		this.robotReports.add(rpo);
	}

	public void setQualitia(ArrayList<QualitiaReports> qualitia) {
		this.qualitia = qualitia;
	}

	public List<QualitiaReports> getQualitia() {
		return qualitia;
	}

	public void addQualitiaReports(String url) {
		if (qualitia == null) {
			qualitia = new ArrayList<>();
		}
		QualitiaReports rpo = new QualitiaReports();
		rpo.setURL(url);
		this.qualitia.add(rpo);
	}

	public void setFindBugsReports(ArrayList<FindBugsReports> findBugsReports) {
		this.findBugsReports = findBugsReports;
	}

	public List<FindBugsReports> getFindBugsReports() {
		return findBugsReports;
	}

	public void addFindBugsReport(String url) {
		if (findBugsReports == null) {
			findBugsReports = new ArrayList<>();
		}
		FindBugsReports rpo = new FindBugsReports();
		rpo.setURL(url);
		this.findBugsReports.add(rpo);
	}

	public void setPMDReports(ArrayList<PMDReports> pMDReports) {
		this.pmdReports = pmdReports;
	}

	public List<PMDReports> getPMDReports() {
		return pmdReports;
	}

	public void addPMDReport(String url) {
		if (pmdReports == null) {
			pmdReports = new ArrayList<>();
		}
		PMDReports rpo = new PMDReports();
		rpo.setURL(url);
		this.pmdReports.add(rpo);
	}
}
