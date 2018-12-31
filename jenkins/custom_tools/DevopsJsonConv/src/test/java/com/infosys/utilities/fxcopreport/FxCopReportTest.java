package com.infosys.utilities.fxcopreport;

import org.junit.Test;

import junit.framework.Assert;

public class FxCopReportTest {

	@Test
	public void test() {
		FxCopReport fcx = new FxCopReport();
		fcx.setExceptions(null);
		fcx.setLocalized(null);
		fcx.setRules(null);
		fcx.setTargets(null);
		fcx.setVersion(1.0f);
		
		Assert.assertEquals(null, fcx.getExceptions());
		Assert.assertEquals(null, fcx.getLocalized());
		Assert.assertEquals(null, fcx.getRules());
		Assert.assertEquals(null, fcx.getTargets());
		Assert.assertEquals((float)1.0f,fcx.getVersion() );
	}
}
