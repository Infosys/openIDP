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
import java.util.ArrayList;
import java.util.List;

import org.infy.idp.AppContext;
import org.infy.idp.entities.CodeAnalysis;
import org.infy.idp.utils.ConfigurationManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*This test case is used for code analysis*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class CodeAnalysisDLTest {

	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@Spy
	@Autowired
	private ConfigurationManager configmanager;
	
	@InjectMocks
	private CodeAnalysisDL codeAnalysisDL;
	
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
	public void insertbuilddetailsTest() throws Throwable {
		try {
			
			CodeAnalysis codeAnalysis=new CodeAnalysis();
			codeAnalysis.setcategory("testsuites");
			codeAnalysis.setClassName("className");
			codeAnalysis.setId("application");
			codeAnalysis.setLine("5");
			codeAnalysis.setMessage("testsuites");
			codeAnalysis.setRecommendation("recommendation");
			codeAnalysis.setruleName("testsuites");
			codeAnalysis.setSeverity("codeCoverage");
			
			List<CodeAnalysis> codeAnalysisList=new ArrayList<>();
			codeAnalysisList.add(codeAnalysis);
			int returnValue=codeAnalysisDL.insertCodeAnalysisDetails(1, 1, 1,codeAnalysisList);
			assertEquals(1, returnValue);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
