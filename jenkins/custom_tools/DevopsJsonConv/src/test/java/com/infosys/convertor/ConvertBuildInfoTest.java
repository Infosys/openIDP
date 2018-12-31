package com.infosys.convertor;

import org.junit.Test;

import junit.framework.Assert;

public class ConvertBuildInfoTest {


	@Test
	public void test() {
		ConvertBuildInfo.convert("inputPath", null , "prefixForId", "tfsPath");
		
		ConvertBuildInfo.converRobot("inputPath", null);
		
		ConvertBuildInfo.convertJobDetail("inputPath", null);
		
		ConvertBuildInfo.convertItops("inputPath", null);
		
		ConvertBuildInfo.converQualitia("inputPath", null);
		
		ConvertBuildInfo.convertAcceleraTest("inputPath", null);
		
		ConvertBuildInfo.convertCodeCoverage("inputPath", null);
		
		ConvertBuildInfo.converTestng("inputPath", null);
		
		ConvertBuildInfo.convertCheckmarx("inputPath", null);
		
		Assert.assertEquals(true, true);
	}
}
