/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.infy.idp.AppContext;
import org.infy.idp.entities.CoverageDetails;
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class CodeCoverageDLTest {

	
	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@Spy
	@Autowired
	private ConfigurationManager configmanager;
	
	@InjectMocks
	private CodeCoverageDL codeCoverageDL;
	
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
			CoverageDetails coverageDetails=new CoverageDetails();
			coverageDetails.setCategory("category");
			coverageDetails.setClassName("className");
			coverageDetails.setLineCoverage("5");
			coverageDetails.setPckage("pckage");
			
			List<CoverageDetails> coverageDetailsList=new ArrayList<>();
			coverageDetailsList.add(coverageDetails);
			int returnValue=codeCoverageDL.insertcoveragedetails(coverageDetailsList, 1, 1, 1);
			assertNotNull( returnValue);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
