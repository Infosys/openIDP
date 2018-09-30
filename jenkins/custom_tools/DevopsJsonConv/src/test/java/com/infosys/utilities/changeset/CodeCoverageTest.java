/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.changeset;

import static org.junit.Assert.*;

import org.junit.Test;

public class CodeCoverageTest {

	@Test
	public void testEquals()
	{
		CodeCoverage codeCoverage=new CodeCoverage();
		
		codeCoverage.setBranchCoverage("branchCoverage");
		codeCoverage.setClassCoverage("classCoverage");
		codeCoverage.setComplexityScore("complexityScore");
		codeCoverage.setInstructionCoverage("instructionCoverage");
		codeCoverage.setLineCoverage("lineCoverage");
		codeCoverage.setMethodCoverage("methodCoverage");
		
		assertEquals("branchCoverage",codeCoverage.getBranchCoverage());
		assertEquals("classCoverage",codeCoverage.getClassCoverage());
		assertEquals("complexityScore",codeCoverage.getComplexityScore());
		assertEquals("instructionCoverage",codeCoverage.getInstructionCoverage());
		assertEquals("lineCoverage",codeCoverage.getLineCoverage());
		assertEquals("methodCoverage",codeCoverage.getMethodCoverage());
		
	}
	
	@Test
	public void testNotEquals()
	{
		CodeCoverage codeCoverage=new CodeCoverage();
		
		codeCoverage.setBranchCoverage("branchCoverage");
		codeCoverage.setClassCoverage("classCoverage");
		codeCoverage.setComplexityScore("complexityScore");
		codeCoverage.setInstructionCoverage("instructionCoverage");
		codeCoverage.setLineCoverage("lineCoverage");
		codeCoverage.setMethodCoverage("methodCoverage");
		
		assertNotEquals("branchCoverage1",codeCoverage.getBranchCoverage());
		assertNotEquals("classCoverage1",codeCoverage.getClassCoverage());
		assertNotEquals("complexityScore1",codeCoverage.getComplexityScore());
		assertNotEquals("instructionCoverage1",codeCoverage.getInstructionCoverage());
		assertNotEquals("lineCoverage1",codeCoverage.getLineCoverage());
		assertNotEquals("methodCoverage1",codeCoverage.getMethodCoverage());
		
	}
}
