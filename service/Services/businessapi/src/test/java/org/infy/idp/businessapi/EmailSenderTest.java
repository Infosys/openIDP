/*
 * EmailSenderTest.java
 * Created by Jtest on 9/27/17 4:11:20 PM.
 */

package org.infy.idp.businessapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.dataapi.services.JobDetailsDL;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.entities.jobs.applicationinfo.EnvironmentOwnerDetail;
import org.infy.idp.entities.jobs.applicationinfo.SlavesDetail;
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

import jtest.AppContext;

/**
 * EmailSenderTest is a test class for EmailSender
 *
 * @see org.infy.idp.businessapi.EmailSender
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class EmailSenderTest  {

	@InjectMocks
	private EmailSender emailsender;

	@Spy
	@InjectMocks
	private TriggerJobName triggerJobName;
	@Spy
	@InjectMocks
	private JobDetailsDL getJobDetails;
	@Spy
	@InjectMocks
	private FetchJobDetails fetchJobDetails;

	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@Spy
	@Autowired
	private ConfigurationManager configurationManager;

	@Spy
	@InjectMocks
	private JobsBL jobsBL;
	private ApplicationInfo app;

	public EmailSenderTest() {
		/*
		 * This constructor should not be modified. Any initialization code should be
		 * placed in the setUp() method instead.
		 */

	}

	@Test
	public void testGetUsersForApplication() {
		TriggerJobName tjn = new TriggerJobName();
		tjn.setApplicationName("DemoAppT");
		List<String> list = emailsender.getUsersForApplication(tjn, "idpadmin");
		assertNotNull(list);
	}

	@Test
	public void testGetUserEmailIds() {
		List<String> users = new ArrayList<String>();
		users.add("idpadmin");
		List<String> list = emailsender.getUserEmailIds(users);
		assertNotNull(list);
	}

	@Test
	public void testCreateAppMailBody() {
		String eBody = emailsender.createAppMailBody("idpadmin", "DemoAppT", "create", app);
		assertNotNull(eBody);
	}

	@Test
	public void testCreateMailBody() {
		String eBody = emailsender.createMailBody("idpadmin", "TC1_Maven", "DemoAppT", "create");
		assertNotNull(eBody);
	}

	@Test
	public void testCreateReleaseMailBody() {
		String eBody = emailsender.createReleaseMailBody("idpadmin", "TC1_Maven", "DemoAppT", "add", "1.0");
		assertNotNull(eBody);
	}

	@Test
	public void testCreationSuccessMail() throws Throwable {

		triggerJobName.setApplicationName("DemoAppT");
		triggerJobName.setPipelineName("TC1_Maven");
		triggerJobName.setMethod("edited");
		triggerJobName.setMailBody("Pipeline edited!");
		assertEquals(true, emailsender.sendEmail("create", triggerJobName, "idpadmin",
				emailsender.getUsersFromApplication(triggerJobName, "idpadmin")));
	}

	/**
	 * Used to set up the test. This method is called by JUnit before each of the
	 * tests are executed.
	 * 
	 */
	@Before
	public void setUp() throws Exception {
		/*
		 * Add any necessary initialization code here (e.g., open a socket). Call
		 * Repository.putTemporary() to provide initialized instances of objects to be
		 * used when testing.
		 */
		try {
			app = new ApplicationInfo();
			app.setApplicationName("DemoAppT");
			app.setDevelopers("app_1");
			app.setPipelineAdmins("app_2");
			app.setReleaseManager("app_3");
			List<EnvironmentOwnerDetail> environmentOwnerDetails = new ArrayList<EnvironmentOwnerDetail>();
			EnvironmentOwnerDetail environmentOwnerDetail_value1 = new EnvironmentOwnerDetail();
			environmentOwnerDetail_value1.setEnvironmentName("environmentName");
			environmentOwnerDetail_value1.setdBOwners("dBOwners");
			environmentOwnerDetail_value1.setEnvironmentOwners("environmentOwners");
			environmentOwnerDetails.add(environmentOwnerDetail_value1);
			app.setEnvironmentOwnerDetails(environmentOwnerDetails);
			ArrayList<SlavesDetail> slavesDetails = new ArrayList<>();
			SlavesDetail sd = new SlavesDetail();
			sd.setBuild("on");
			sd.setTest("on");
			sd.setDeploy("on");
			sd.setLabels("DSlave");
			sd.setSlaveName("DSlave");
			sd.setSlaveUsage("both");
			slavesDetails.add(sd);

			app.setSlavesDetails(slavesDetails);
			MockitoAnnotations.initMocks(this);
			Method postConstruct = PostGreSqlDbContext.class.getDeclaredMethod("init", null); // methodName,parameters
			postConstruct.setAccessible(true);
			postConstruct.invoke(postGreSqlDbContext);

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}


	/**
	 * Utility main method. Runs the test cases defined in this test class.
	 * 
	 * Usage: java EmailSenderTest
	 * 
	 * @param args command line arguments are not needed
	 */
	public static void main(String[] args) {

		org.junit.runner.JUnitCore.main("org.infy.idp.businessapi.EmailSenderTest");
	}

	/**
	 * Get the class object of the class which will be tested.
	 * 
	 * @return the class which will be tested
	 */
	public Class getTestedClass() {
		return EmailSender.class;
	}
}
