package org.infy.idp.dataapi;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.List;

import org.infy.idp.AppContext;
import org.infy.idp.entities.QueryResponse;
import org.infy.idp.utils.ConfigurationManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class DBQueryTest {

	@Spy
	@InjectMocks
	private IDPPostGreSqlDbContext idpPostGreSqlDbContext;
	
	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;
	
	@Spy
	@Autowired
	private ConfigurationManager configmanager;
	
	@InjectMocks
	private DBQuery dbQuery;
	
	@Spy
	private DBConstants dbConstants;
	
	@Spy
	private DBTableConstants dbTableConstants;
	
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
	public void runQueryTest() {
		try {

			List<QueryResponse> temp = dbQuery.runQuery("pipelineIdForApplication+TIBCO_13");

			assertNotNull(temp);
		} catch (Exception e) {

		}
	}
	
	@Test
	public void runTableQueryTest()
	{
		try {
			
			QueryResponse temp=dbQuery.runTableQuery("appCommitsPerEngineer+TIBCO_13","idposs");
			
			assertNotNull(temp);
		}
		catch(Exception e)
		{
			
		}
	}
}
