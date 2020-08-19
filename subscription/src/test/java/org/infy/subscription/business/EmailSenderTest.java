/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/


package org.infy.subscription.business;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Transport;

import org.infy.subscription.entities.licencekeymanagement.OrganisationInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmailSenderTest {
	@InjectMocks
	private OrgBL organisationBL;
	@Mock
	private Transport transport;

	@Spy
	EmailSender emailSender;

	@Test
	public void testCreateOrgMailBody() {

		OrganisationInfo orgInfo = new OrganisationInfo();
		orgInfo.setMethod("create");
		orgInfo.setOrgAdmin("idpadmin");
		orgInfo.setOrgName("INFOSYS");
		String mailBody = emailSender.createOrgMailBody(orgInfo);
		assertNotNull(mailBody);
	}

	@Test
	public void testCreateOrgMailBodyEdit() {
		OrganisationInfo orgInfo = new OrganisationInfo();
		orgInfo.setMethod("edit");
		orgInfo.setOrgAdmin("idpadmin");
		orgInfo.setOrgName("INFOSYS");
		String mailBody = emailSender.createOrgMailBody(orgInfo);
		assertNotNull(mailBody);
	}

	@Test
	public void testCreateLicenseMailBody() {
		OrganisationInfo orgInfo = new OrganisationInfo();
		orgInfo.setMethod("edit");
		orgInfo.setOrgAdmin("idpadmin");
		orgInfo.setLicenseExpiryDate("2019/12/21");
		orgInfo.setOrgName("INFOSYS");
		String mailBody = emailSender.createLicenseMailBody(orgInfo);
		assertNotNull(mailBody);
	}

	@Test
	public void testSendEmail() {
		OrganisationInfo orgInfo = new OrganisationInfo();
		orgInfo.setMethod("edit");
		orgInfo.setOrgAdmin("idpadmin");
		orgInfo.setLicenseExpiryDate("2019/12/21");
		orgInfo.setOrgName("INFOSYS");
		orgInfo.setDomain("infosys.com");
		orgInfo.setUserName("idpadmin");
		boolean status = emailSender.sendEmail("subject", orgInfo);
		assertNotNull(status);
	}
	
	@Test
	public void testSendEmailFailure() {
		OrganisationInfo orgInfo = new OrganisationInfo();
		orgInfo.setMethod("edit");
		orgInfo.setOrgAdmin("idpadmin");
		orgInfo.setLicenseExpiryDate("2019/12/21");
		orgInfo.setOrgName("INFOSYS");
		orgInfo.setDomain("@abc.com");
		orgInfo.setUserName("idpadmin");
		boolean status = emailSender.sendEmail("subject", orgInfo);
		assertNotNull(status);
	}
	@Test
	public void testSendEmailSendGrid() {
		OrganisationInfo orgInfo = new OrganisationInfo();
		orgInfo.setMethod("edit");
		orgInfo.setOrgAdmin("idpadmin");
		orgInfo.setLicenseExpiryDate("2019/12/21");
		orgInfo.setOrgName("INFOSYS");
		orgInfo.setDomain("@abc.com");
		orgInfo.setUserName("idpadmin");
		boolean status = emailSender.sendEmailSendGrid("subject", orgInfo);
		assertNotNull(status);
	}

	@Test
	public void testGetCC() {
		List<String> idList = new ArrayList<>();
		idList.add("idpadmin");
		idList.add("idpadmin2");
		String cc = emailSender.getCC(idList);
		assertNotNull(cc);
	}
	@Test
	public void testLicenseCreationSuccessMail() {
		OrganisationInfo orgInfo = new OrganisationInfo();
		orgInfo.setMethod("edit");
		orgInfo.setOrgAdmin("idpadmin");
		orgInfo.setLicenseExpiryDate("2019/12/21");
		orgInfo.setOrgName("INFOSYS");
		orgInfo.setDomain("@abc.com");
		orgInfo.setUserName("idpadmin");
		boolean status = emailSender.licenseCreationSuccessMail(orgInfo);
		assertNotNull(status);
	}
	
	@Test
	public void testOrgCreationSuccessMail() {
		OrganisationInfo orgInfo = new OrganisationInfo();
		orgInfo.setMethod("edit");
		orgInfo.setOrgAdmin("idpadmin");
		orgInfo.setLicenseExpiryDate("2019/12/21");
		orgInfo.setOrgName("INFOSYS");
		orgInfo.setDomain("@abc.com");
		orgInfo.setUserName("idpadmin");
		boolean status = emailSender.orgCreationSuccessMail(orgInfo);
		assertNotNull(status);
	}
	@Before
	public void setup() {
		emailSender = new EmailSender();
		ReflectionTestUtils.setField(emailSender, "emailUserName", "idpadmin");
		ReflectionTestUtils.setField(emailSender, "emailPassword", "dummyuser");
		ReflectionTestUtils.setField(emailSender, "emailSmtpHost", "localhost");
		ReflectionTestUtils.setField(emailSender, "emailSmtpPort", "587");
		ReflectionTestUtils.setField(emailSender, "emailServer", "sendgrid");
		ReflectionTestUtils.setField(emailSender, "emailSenderId", "idpadmin@xyz.com");
	}
}
