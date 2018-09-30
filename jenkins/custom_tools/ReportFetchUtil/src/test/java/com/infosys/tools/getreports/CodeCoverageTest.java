package com.infosys.tools.getreports;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CodeCoverageTest {

	@InjectMocks
	private CodeCoverage codeCoverage;
	
	@Test
	public void createcodeCoverageTest()
	{
		try {
			codeCoverage.createcodeCoverage("string","string","string","string","string","string","string");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
}
