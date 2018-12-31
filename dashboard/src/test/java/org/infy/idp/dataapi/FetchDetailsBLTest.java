package org.infy.idp.dataapi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
public class FetchDetailsBLTest {
	
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
	private FetchDetailsBL fetchDetailsBL;
	
	@Mock
	private DBQuery dbQuery;
	
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
	public void runQueryTest()
	{
		try {
			List<QueryResponse> temp=fetchDetailsBL.runQuery("test","id");
			
			assertNotNull(temp);
		}
		catch(Exception e)
		{
			
		}
	}
	
	@Test
	public void runTableQueryTest()
	{
		try {
			QueryResponse temp=fetchDetailsBL.runTableQuery("test","id");
			
			assertNull(temp);
		}
		catch(Exception e)
		{
			
		}
	}

	@Test
	public void runTableQuerySCMTest()
	{
		try {
			QueryResponse temp=fetchDetailsBL.runTableQuerySCM("Select distinct application_name from appinfo");
			
			assertNotNull(temp);
		}
		catch(Exception e)
		{
			
		}
	}
	
	@Test
	public void runFetchQueryTest()
	{
		try {
			String temp=fetchDetailsBL.runFetchQuery("TIBCO_13","Tibco_8");
			
			assertNotNull(temp);
		}
		catch(Exception e)
		{
			
		}
	}
	
	@Test
	public void runSearchQueryTest1()
	{
		try {
			List<String> temp=fetchDetailsBL.runSearchQuery("select application_name from appinfo","admin");
			
			assertNotNull(temp);
		}
		catch(Exception e)
		{
			
		}
	}
	
	@Test
	public void runSearchQueryTest2()
	{
		try {
			List<String> temp=fetchDetailsBL.runSearchQuery("select application_name from tapplication_info","admin");
			
			assertNotNull(temp);
		}
		catch(Exception e)
		{
			
		}
	}
	
}
