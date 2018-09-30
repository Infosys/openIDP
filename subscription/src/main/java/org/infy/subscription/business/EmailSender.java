/**
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.”
*
**/
package org.infy.subscription.business;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.infy.subscription.entities.licencekeymanagement.OrganisationInfo;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class EmailSender.
 */
@Component
public class EmailSender {

	private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

	private static final String CREATE_ORG_MAIL_TEMPLATE_PATH = "CreateOrgMail.html";
	private static final String IDP_LINK = "IDP_LINK";
	private static final String USER = "USER";
	private static final String ORGANISATION = "ORGANIZATION";
	private static final String METHOD = "METHOD";
	private static final String LICENSE_SERVICES = "LICENSE_SERVICES";
	private static final String LICENSE_EXPIRY_DATE = "LICENSE_EXPIRY_DATE";

	@Value("${idplink}")
	private String idpLink;

	@Value("${emailserver}")
	private String emailServer;

	@Value("${emaildomain}")
	private String emailDomain;

	@Value("${emailusername}")
	private String emailUserName;

	@Value("${emailpassword}")
	private String emailPassword;

	@Value("${emailsmtphost}")
	private String emailSmtpHost;

	@Value("${emailsmtpport}")
	private String emailSmtpPort;

	@Value("${emailsenderid}")
	private String emailSenderId;

	@Autowired
	private OrgBL organisationBL;

	EmailSender() {
	}

	/**
	 * 
	 * jobCreationSuccessMail
	 * 
	 * @param triggerJobName the TriggerJobName
	 * 
	 * @return boolean
	 * 
	 *         Method is used to send the org creation success mail
	 *
	 */
	public boolean orgCreationSuccessMail(OrganisationInfo orgInfo) {
		boolean status = true;
		String sub = "organisation";
		orgInfo.setMailBody(createOrgMailBody(orgInfo));
		String emailserver = this.emailServer;
		if (emailserver.equalsIgnoreCase("sendgrid")) {
			status = sendEmailSendGrid(sub, orgInfo);
		} else {
			status = sendEmail(sub, orgInfo);
		}
		return status;
	}

	/**
	 * 
	 * licenseCreationSuccessMail
	 * 
	 * @param triggerJobName the TriggerJobName
	 * 
	 * @return boolean
	 * 
	 *         Method is used to send the org creation success mail
	 *
	 */
	public boolean licenseCreationSuccessMail(OrganisationInfo orgInfo) {
		boolean status = true;
		String sub = "License";
		orgInfo.setMailBody(createLicenseMailBody(orgInfo));
		String emailserver = this.emailServer;
		String orgDomain = organisationBL.findDomain(orgInfo.getOrgName());
		orgInfo.setDomain(orgDomain);
		if (emailserver.equalsIgnoreCase("sendgrid")) {
			status = sendEmailSendGrid(sub, orgInfo);
		} else {
			status = sendEmail(sub, orgInfo);
		}
		return status;
	}

	/**
	 * 
	 * sendEmail SendGrid
	 * 
	 * @param triggerJobName the TriggerJobName
	 * @param user           the String
	 * @param email          the List
	 * 
	 * @return List<String>
	 * 
	 *         Method is used to send the job creation success mail through send
	 *         grid in azure
	 *
	 */
	@SuppressWarnings("static-access")
	public boolean sendEmailSendGrid(String sub, OrganisationInfo orgInfo) {
		logger.info("Inside send mail");
		String to;
		to = orgInfo.getUserName() + "@" + orgInfo.getDomain();
		String from = "";
		String username = this.emailUserName;
		String senderid = this.emailSenderId;
		if (senderid.contains("@")) {
			from = this.emailSenderId;
		} else {
			from = this.emailSenderId + this.emailDomain;
		}

		String host = this.emailSmtpHost;
		String port = this.emailSmtpPort;

		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", host);
		Session session = null;
		if (!("".equals(this.emailPassword))) {
			logger.info("Tryiny to send email with Authentication... ");
			String password = this.emailPassword;
			Authenticator authenticator = new Authenticator(username, password);
			properties.setProperty("mail.smtp.port", port);
			properties.put("mail.transport.protocol", "smtp");
			properties.setProperty("mail.smtp.auth", "true");
			properties.put("mail.smtp.timeout", "60000");
			properties.put("mail.smtp.connectiontimeout", "60000");
			properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
			session = Session.getDefaultInstance(properties, authenticator);
		} else {
			logger.info("Tryiny to send email without Authentication...");
			session = Session.getDefaultInstance(properties);
		}
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("IDP: " + sub + " configuration details ");
			message.setContent(orgInfo.getMailBody(), "text/html");

			logger.info(orgInfo.getMailBody());
			logger.info(message.getContent().toString());

			Transport transport = session.getTransport();
			transport.connect();
			transport.send(message);
			transport.close();
			logger.info("Mail sent successfully");
		}

		catch (MessagingException | IOException e) {
			logger.info(orgInfo.getMailBody());

			logger.error("mail could not be sent", e);
			logger.debug(e.getMessage(), e);

		}

		return true;
	}

	/**
	 * 
	 * sendEmail
	 * 
	 * @param triggerJobName the TriggerJobName
	 * @param user           the String
	 * @param email          the List
	 * 
	 * @return List<String>
	 * 
	 *         Method is used to send the job creation success mail
	 *
	 */

	public boolean sendEmail(String sub, OrganisationInfo orgInfo) {

		logger.info("Inside send mail");
		String to;
		to = orgInfo.getUserName() + "@" + orgInfo.getDomain();
		String from = "";
		String username = this.emailUserName;
		if (username.contains("@")) {
			from = this.emailUserName;
		} else {
			from = this.emailUserName + this.emailDomain;
		}

		String host = this.emailSmtpHost;
		String port = this.emailSmtpPort;
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = null;
		if (!("".equals(this.emailPassword))) {
			logger.info("Trying to send email with Authentication... ");

			String password = this.emailPassword;
			Authenticator authenticator = new Authenticator(username, password);
			properties.setProperty("mail.smtp.port", port);

			properties.setProperty("mail.smtp.auth", "true");
			properties.setProperty("mail.smtp.submitter", authenticator.getPasswordAuthentication().getUserName());
			session = Session.getInstance(properties, authenticator);
		} else {
			logger.info("Tryiny to send email without Authentication...");
			session = Session.getDefaultInstance(properties);
		}
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject("IDP: " + sub + " configuration details ");
			message.setContent(orgInfo.getMailBody(), "text/html");

			logger.info(orgInfo.getMailBody());
			logger.info(message.getContent().toString());

			Transport.send(message);
			logger.info("Mail sent successfully");
		}

		catch (MessagingException | IOException e) {
			logger.info(orgInfo.getMailBody());

			logger.error("mail could not be sent", e);
			logger.debug(e.getMessage(), e);

		}

		return true;
	}

	/**
	 * 
	 * @param emails
	 * @return String
	 */
	public String getCC(List<String> emails) {
		StringBuilder cc = new StringBuilder();
		int emailsSize = emails.size();
		for (int i = 0; i < emailsSize; i++) {
			cc.append(emails.get(i));
			cc.append(",");
		}
		return cc.toString();
	}

	/**
	 * 
	 * createOrgMailBody
	 * 
	 * @param idp the IDPJob>
	 * 
	 * @return String
	 * 
	 *         Method is used to create mail body
	 *
	 */

	public String createOrgMailBody(OrganisationInfo org) {

		String idpLink = this.idpLink;
		String method = "";

		if ("create".equalsIgnoreCase(org.getMethod())) {
			method = "created";
		} else if ("edit".equalsIgnoreCase(org.getMethod())) {
			method = "edited";
		}

		JtwigTemplate template = JtwigTemplate.classpathTemplate(CREATE_ORG_MAIL_TEMPLATE_PATH);
		JtwigModel model = JtwigModel.newModel().with(USER, org.getOrgAdmin()).with(ORGANISATION, org.getOrgName())
				.with(METHOD, method).with(IDP_LINK, idpLink);
		return template.render(model);

	}

	/**
	 * 
	 * createOrgMailBody
	 * 
	 * @param idp the IDPJob>
	 * 
	 * @return String
	 * 
	 *         Method is used to create mail body
	 *
	 */

	public String createLicenseMailBody(OrganisationInfo org) {

		String idpLink = this.idpLink;

		JtwigTemplate template = JtwigTemplate.classpathTemplate(CREATE_ORG_MAIL_TEMPLATE_PATH);
		JtwigModel model = JtwigModel.newModel().with(USER, org.getOrgAdmin())
				.with(LICENSE_SERVICES, org.getLicenseServices()).with(LICENSE_EXPIRY_DATE, org.getLicenseExpiryDate())
				.with(IDP_LINK, idpLink);
		return template.render(model);

	}

}
