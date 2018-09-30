package com.infosys.tools.getreports;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateBuildLogTest {

	@InjectMocks
	private CreateBuildLog createBuildLog;
	
	@Test
	public void copyFolderTest()
	{
		try {
			
			createBuildLog.createBuildLog("string","string","string","string","string","string","string","string");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
}
