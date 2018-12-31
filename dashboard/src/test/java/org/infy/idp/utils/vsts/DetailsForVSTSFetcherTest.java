package org.infy.idp.utils.vsts;

import java.lang.reflect.Method;

import org.infy.idp.AppContext;
import org.infy.idp.dataapi.IDPPostGreSqlDbContext;
import org.infy.idp.dataapi.InsertFetchVSTS;
import org.infy.idp.utils.ConfigurationManager;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.mockito.Matchers;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class DetailsForVSTSFetcherTest {
	
	@Spy
	@InjectMocks
	private IDPPostGreSqlDbContext idppostGreSqlDbContext;

	@Spy
	@Autowired
	private ConfigurationManager configmanager;
	
	@Spy
	@InjectMocks
	private InsertFetchVSTS insertFetchVSTS;
	
	@InjectMocks
	private DetailsForVSTSFetcher detailsForVSTSFetcher;

	@Before
	public void setUp() throws Exception {

		try {

			MockitoAnnotations.initMocks(this);
			Method postConstruct = IDPPostGreSqlDbContext.class.getDeclaredMethod("init", null); // methodName,parameters
			postConstruct.setAccessible(true);
			postConstruct.invoke(idppostGreSqlDbContext);

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void test() {
		
		try {
			JSONObject json = new JSONObject("{}") ;

			
			
			detailsForVSTSFetcher.createVSTSUpdateList("5395","1");
		}
		catch(Exception e)
		{
			
		}
		
	}
}
