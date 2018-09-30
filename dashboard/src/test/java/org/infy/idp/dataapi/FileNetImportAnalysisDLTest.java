/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.infy.idp.entities.FileNet;
import org.infy.idp.entities.FileNetExport;
import org.infy.idp.utils.ConfigurationManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

/*This test case is used for Import analysis*/
@RunWith(MockitoJUnitRunner.class)
public class FileNetImportAnalysisDLTest {


	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@Spy
	@InjectMocks
	@Autowired
	private IDPPostGreSqlDbContext idpPostGreSqlDbContext;
	
	@Spy
	@Autowired
	private ConfigurationManager configurationManager;
	
	@InjectMocks
	private FileNetImportAnalysisDL fileNetImportAnalysisDL;
	
	@Mock
	private Connection connection;
	
	@Mock
	private PreparedStatement preparedStatement;
	
	@Before
	public void setUp() throws Exception {

		try {

			MockitoAnnotations.initMocks(this);
			Method postConstruct = PostGreSqlDbContext.class.getDeclaredMethod("init", null); // methodName,parameters
			postConstruct.setAccessible(true);
			postConstruct.invoke(postGreSqlDbContext);

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void insertFileNetImportAnalysisDetailsTest() throws Throwable {
		try 
		{
			
			FileNet fileNet=new FileNet();
			
			int temp=fileNetImportAnalysisDL.insertFileNetImportAnalysisDetails(fileNet);
			assertEquals(1, temp);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
