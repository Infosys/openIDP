
package com.infosys.jsonschema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CodeMetricTest {

	@Test
	public void testEquals()
	{
		CodeMetric codeMetric=new CodeMetric();
		codeMetric.setChangePronenessIndex("changePronenessIndex");
		codeMetric.setcoverageMetric(5);
		codeMetric.setcyclomaticComplexity("cyclomaticComplexity");
		codeMetric.setDefectPronenessIndex("defectPronenessIndex");
		codeMetric.setID("id");
		codeMetric.setMaintainabilityIndex("maintainabilityIndex");
		
		assertEquals("changePronenessIndex",codeMetric.getChangePronenessIndex());
		assertEquals(5,codeMetric.getcoverageMetric(),0.01);
		assertEquals("cyclomaticComplexity",codeMetric.getcyclomaticComplexity());
		assertEquals("defectPronenessIndex",codeMetric.getDefectPronenessIndex());
		assertEquals("id",codeMetric.getID());
		assertEquals("maintainabilityIndex",codeMetric.getMaintainabilityIndex());
	}
	@Test
	public void testNotEquals()
	{
		CodeMetric codeMetric=new CodeMetric();
		codeMetric.setChangePronenessIndex("changePronenessIndex");
		codeMetric.setcoverageMetric(5);
		codeMetric.setcyclomaticComplexity("cyclomaticComplexity");
		codeMetric.setDefectPronenessIndex("defectPronenessIndex");
		codeMetric.setID("id");
		codeMetric.setMaintainabilityIndex("maintainabilityIndex");
		
		assertNotEquals("changePronenessIndex1",codeMetric.getChangePronenessIndex());
		assertNotEquals(4,codeMetric.getcoverageMetric(),0.01);
		assertNotEquals("cyclomaticComplexity1",codeMetric.getcyclomaticComplexity());
		assertNotEquals("defectPronenessIndex1",codeMetric.getDefectPronenessIndex());
		assertNotEquals("id1",codeMetric.getID());
		assertNotEquals("maintainabilityIndex1",codeMetric.getMaintainabilityIndex());
	}
}
