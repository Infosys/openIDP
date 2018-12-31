
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class ReportsTest {

	@Test
	public void accetest(){
		Reports r = new Reports();
		r.setAccelerateReports(null);
		
		Assert.assertEquals(null, r.getAccelerateReports());
	}
	
	@Test
	public void reportstest(){
		Reports r = new Reports();
		r.setCheckmarxReports(null);
		
		Assert.assertEquals(null, r.getCheckmarxReports());
	}
	
	@Test
	public void creportstest(){
		Reports r = new Reports();
		r.setCheckReports(null);
		
		Assert.assertEquals(null, r.getCheckReports());
	}
	
	@Test
	public void coberturatest(){
		Reports r = new Reports();
		r.setCoberturareports(null);
		
		Assert.assertEquals(null, r.getCoberturareports());
	}
	
	@Test
	public void findbugstest(){
		Reports r = new Reports();
		r.setFindBugsReports(null);
		
		Assert.assertEquals(null, r.getFindBugsReports());
	}
	
	@Test
	public void iFastreportstest(){
		Reports r = new Reports();
		r.setiFastReports(null);
		
		Assert.assertEquals(null, r.getiFastReports());
	}
	
	@Test
	public void itopstest(){
		Reports r = new Reports();
		r.setiTopsReps(null);
		
		Assert.assertEquals(null, r.getiTopsReps());
	}
	
	@Test
	public void meterreportstest(){
		Reports r = new Reports();
		r.setjMeterReports(null);
		
		Assert.assertEquals(null, r.getjMeterReports());
	}
	
	@Test
	public void junittest(){
		Reports r = new Reports();
		r.setJunitReports(null);
		
		Assert.assertEquals(null, r.getJunitReports());
	}
	
	@Test
	public void junitrepotest(){
		Reports r = new Reports();
		r.setJunitreports(null);
		
		Assert.assertEquals(null, r.getJunitreports());
	}
	
	@Test
	public void pmdtest(){
		Reports r = new Reports();
		r.setPMDReports(null);
		
		Assert.assertEquals(null, r.getPMDReports());
	}
	
	@Test
	public void qualitiatest(){
		Reports r = new Reports();
		r.setQualitia(null);
		
		Assert.assertEquals(null, r.getQualitia());
	}
	
	@Test
	public void robottest(){
		Reports r = new Reports();
		r.setRobotReports(null);
		
		Assert.assertEquals(null, r.getRobotReports());
	}
	
	@Test
	public void seltest(){
		Reports r = new Reports();
		r.setSeleniumReports(null);
		
		Assert.assertEquals(null, r.getSeleniumReports());
	}
}
