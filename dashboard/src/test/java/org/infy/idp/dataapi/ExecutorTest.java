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
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class ExecutorTest {

	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;
	
	@Spy
	@Autowired
	private ConfigurationManager configmanager;
	
	@InjectMocks
	private Executor executor;
	
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
	public void insertUsersTest() {
		try {

			Integer temp = executor.insertUsers("testUser;testOrg");

			assertNotNull(temp);
		} catch (Exception e) {

		}
	}
	
	@Test
	public void insertApplicationTest() {
		try {

			Integer temp = executor.insertApplication("testApp;testOrg");

			assertNotNull(temp);
		} catch (Exception e) {

		}
	}
	
	@Test
	public void insertAppDetailsTest() {
		try {

			Integer temp = executor.insertAppDetails(1,"testapp","{}");

			assertNotNull(temp);
		} catch (Exception e) {

		}
	}
	
	/*@Test
	public void insertICQAdetailsTest() {
		try {

			Integer temp = executor.insertICQAdetails(1,"test","test","test","test");

			assertNotNull(temp);
		} catch (Exception e) {

		}
	}*/
}
