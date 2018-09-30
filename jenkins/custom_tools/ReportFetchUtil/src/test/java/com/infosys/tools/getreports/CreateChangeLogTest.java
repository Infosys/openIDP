package com.infosys.tools.getreports;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)

public class CreateChangeLogTest {

	@InjectMocks
	private CreateChangeLog createChangeLog;
	
	@Test
	public void createChangeLogTest()
	{
		try {
			
			createChangeLog.createChangeLog("string","string","string","string","string","string","string");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
