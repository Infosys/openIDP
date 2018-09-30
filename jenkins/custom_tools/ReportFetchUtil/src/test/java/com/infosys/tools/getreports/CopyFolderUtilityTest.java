package com.infosys.tools.getreports;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CopyFolderUtilityTest {

	@InjectMocks
	private CopyFolderUtility copyFolderUtility;
	
	@Test
	public void copyFolderTest()
	{
		try {
			
			File file=new File("test");
			File file1=new File("test");
			copyFolderUtility.copyFolder(file,file1,"string","string","string");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
