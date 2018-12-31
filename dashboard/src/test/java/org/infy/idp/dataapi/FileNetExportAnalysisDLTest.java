/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.infy.idp.AppContext;
import org.infy.idp.entities.FileNet;
import org.infy.idp.entities.FileNetExport;
import org.infy.idp.entities.FileNetExportClassDefinitionType;
import org.infy.idp.entities.FileNetExportOtherType;
import org.infy.idp.entities.FileNetExportPropertyType;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/*This test case is used for File test case Analysis*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class FileNetExportAnalysisDLTest {



	@Spy
	@InjectMocks
	private IDPPostGreSqlDbContext idpPostGreSqlDbContext;
	
	@Spy
	@Autowired
	private ConfigurationManager configmanager;
	
	@InjectMocks
	private FileNetExportAnalysisDL fileNetExportAnalysisDL;
	
	@Mock
	private Connection connection;
	
	@Mock
	private PreparedStatement preparedStatement;
	
	
	@Before
	public void setUp() throws Exception {

		try {

			MockitoAnnotations.initMocks(this);
			Method postConstruct = IDPPostGreSqlDbContext.class.getDeclaredMethod("init", null); // methodName,parameters
			postConstruct.setAccessible(true);
			postConstruct.invoke(idpPostGreSqlDbContext);

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void insertPropertyTemplateListTest() throws Throwable {
		try 
		{
			FileNetExport fileNetExport=new FileNetExport();
			FileNet fileNet=new FileNet();
			fileNet.setTriggerId(0);
			FileNetExportPropertyType fileNetExportPropertyType=new FileNetExportPropertyType();
			
			fileNetExportPropertyType.setId("id");
			fileNetExportPropertyType.setName("name");
			fileNetExportPropertyType.setObjectStore("objectStore");
			fileNetExportPropertyType.setObjectType("objectType");
			
			List<FileNetExportPropertyType> propertyTypeList=new ArrayList<>();
			propertyTypeList.add(fileNetExportPropertyType);
			fileNetExport.setPropertyTypeList(propertyTypeList);
			
			
			
			fileNetExportAnalysisDL.insertPropertyTemplateList(fileNetExport,fileNet,connection,preparedStatement,"env");
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void insertcalssDefinationList() throws Throwable {
		try 
		{
			FileNetExport fileNetExport=new FileNetExport();
			FileNet fileNet=new FileNet();
			fileNet.setTriggerId(0);
			FileNetExportClassDefinitionType fileNetExportClassDefinitionType=new FileNetExportClassDefinitionType();
			fileNetExportClassDefinitionType.setId("id");
			fileNetExportClassDefinitionType.setName("name");
			fileNetExportClassDefinitionType.setObjectStore("objectStore");
			fileNetExportClassDefinitionType.setObjectType("objectType");
			
			List<FileNetExportClassDefinitionType> classDefinitionTypeList=new ArrayList<>();
			classDefinitionTypeList.add(fileNetExportClassDefinitionType);
			fileNetExport.setClassDefinitionTypeList(classDefinitionTypeList);
			fileNetExportAnalysisDL.insertcalssDefinationList(fileNetExport,fileNet,connection,preparedStatement,"env");
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void insertOthersList() throws Throwable {
		try 
		{
			FileNetExport fileNetExport=new FileNetExport();
			FileNet fileNet=new FileNet();
			fileNet.setTriggerId(0);
			
			FileNetExportOtherType fileNetExportOtherType=new FileNetExportOtherType();
			fileNetExportOtherType.setId("id");
			fileNetExportOtherType.setName("name");
			fileNetExportOtherType.setObjectStore("objectStore");
			fileNetExportOtherType.setObjectType("objectType");
			List<FileNetExportOtherType> otherTypeList=new ArrayList<>();
			otherTypeList.add(fileNetExportOtherType);
			fileNetExport.setOtherTypeList(otherTypeList);
			fileNetExportAnalysisDL.insertOthersList(fileNetExport,fileNet,connection,preparedStatement,"env");
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void getEnvironment() throws Throwable {
		try 
		{
			String temp=fileNetExportAnalysisDL.getEnvironment(1);
			assertNotNull(temp);
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void insertFileNetExportAnalysisDetails() throws Throwable {
		try 
		{
			FileNet fileNet=new FileNet();
			
			int temp=fileNetExportAnalysisDL.insertFileNetExportAnalysisDetails(1,fileNet);
			assertEquals(1, temp);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
