
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;


public class QualitisReportsTest {
	
	@Test
	public void testurl(){
		
		QualitiaReports qr = new QualitiaReports();
		
		qr.setURL("infosys.com");
		Assert.assertEquals("infosys.com", qr.getURL());
		
	}
}
