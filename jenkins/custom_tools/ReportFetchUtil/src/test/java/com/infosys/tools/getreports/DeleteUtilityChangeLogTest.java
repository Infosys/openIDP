package com.infosys.tools.getreports;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeleteUtilityChangeLogTest {

	@InjectMocks
	private DeleteUtilityChangeLog deleteUtilityChangeLog;
	
	@Test
	public void createChangeLogTest()
	{
		try {
			
			deleteUtilityChangeLog.del("string");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
