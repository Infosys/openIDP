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

/*This class is for testing  jacoco*/
public class JacocoTest {
	@Test
	public void branchCoveragetest(){
		Jacoco jc = new Jacoco();
		
		jc.setBranchCoverage("100");
		
		Assert.assertEquals("100", jc.getBranchCoverage());
	}
	
	@Test
	public void Classcoveragetest(){
		Jacoco jc = new Jacoco();
		
		jc.setClassCoverage("100");
		
		Assert.assertEquals("100", jc.getClassCoverage());
	}
	
	@Test
	public void complexitytest(){
		Jacoco jc = new Jacoco();
		
		jc.setComplexityScore("100");
		
		Assert.assertEquals("100", jc.getComplexityScore());
	}
	
	@Test
	public void Instructiontest(){
		Jacoco jc = new Jacoco();
		
		jc.setInstructionCoverage("100");
		
		Assert.assertEquals("100", jc.getInstructionCoverage());
	}
	
	@Test
	public void lineCoveragetest(){
		Jacoco jc = new Jacoco();
		
		jc.setLineCoverage("100");
		
		Assert.assertEquals("100", jc.getLineCoverage());
	}
	
	@Test
	public void methodCoveragetest(){
		Jacoco jc = new Jacoco();
		
		jc.setMethodCoverage("100");
		
		Assert.assertEquals("100", jc.getMethodCoverage());
	}
}
