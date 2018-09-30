/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class CodeCoverageDetailsTest {
	
	
	@Test
	
	public void branchCoveragetest(){
		
		CodeCoverageDetails ccd = new CodeCoverageDetails();
		
		ccd.setBranchCoverage("10");
		
		Assert.assertEquals("10", ccd.getBranchCoverage());
	}
	
@Test
	
	public void classCoveragetest(){
		
		CodeCoverageDetails ccd = new CodeCoverageDetails();
		
		ccd.setClassCoverage("10");
		
		Assert.assertEquals("10", ccd.getClassCoverage());
	}

@Test

public void instructionCoveragetest(){
	
	CodeCoverageDetails ccd = new CodeCoverageDetails();
	
	ccd.setInstructionCoverage("10");
	
	Assert.assertEquals("10", ccd.getInstructionCoverage());
}


@Test

public void lineCoveragetest(){
	
	CodeCoverageDetails ccd = new CodeCoverageDetails();
	
	ccd.setLineCoverage("10");
	
	Assert.assertEquals("10", ccd.getLineCoverage());
}

@Test

public void methodCoveragetest(){
	
	CodeCoverageDetails ccd = new CodeCoverageDetails();
	
	ccd.setMethodCoverage("10");
	
	Assert.assertEquals("10", ccd.getMethodCoverage());
}
}
