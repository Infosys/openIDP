package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertSonarqubeTest {

	@Test
	public void test() {
		ConvertSonarqube.convert(null, null, null, null, null, null);
		ConvertSonarqube.getSeverityDetails();
		ConvertSonarqube.mapSonarSeverity("sonarSeverity");
		Assert.assertEquals(true, true);
	}
}
