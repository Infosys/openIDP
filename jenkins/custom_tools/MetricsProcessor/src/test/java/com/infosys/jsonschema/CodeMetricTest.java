package com.infosys.jsonschema;

import org.junit.Test;

import junit.framework.Assert;

public class CodeMetricTest {

	@Test
	public void indextest() {
		
		CodeMetric cm = new CodeMetric();
		
		cm.setChangePronenessIndex("index");
		
		Assert.assertEquals("index", cm.getChangePronenessIndex());
	}
	@Test
	public void coveragetest() {
		
		CodeMetric cm = new CodeMetric();
		
		cm.setcoverageMetric(100);
		
		Assert.assertEquals((double)100, cm.getcoverageMetric());
	}
	@Test
	public void complexitytest() {
		
		CodeMetric cm = new CodeMetric();
		
		cm.setcyclomaticComplexity("complexity");
		
		Assert.assertEquals("complexity", cm.getcyclomaticComplexity());
	}
	@Test
	public void proindextest() {
		
		CodeMetric cm = new CodeMetric();
		
		cm.setDefectPronenessIndex("proindex");
		
		Assert.assertEquals("proindex", cm.getDefectPronenessIndex());
	}
	@Test
	public void idtest() {
		
		CodeMetric cm = new CodeMetric();
		
		cm.setID("1");
		
		Assert.assertEquals("1", cm.getID());
	}
	@Test
	public void maintainabilitytest() {
		
		CodeMetric cm = new CodeMetric();
		
		cm.setMaintainabilityIndex("0");
		
		Assert.assertEquals("0", cm.getMaintainabilityIndex());
	}
}
