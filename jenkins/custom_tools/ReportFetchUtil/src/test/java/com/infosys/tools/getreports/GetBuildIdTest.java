package com.infosys.tools.getreports;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GetBuildIdTest {

	@InjectMocks
	private GetBuildId getBuildId;
	
	@Test
	public void createChangeLogTest()
	{
		try {
			
			getBuildId.getId("string","string","string","string");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
