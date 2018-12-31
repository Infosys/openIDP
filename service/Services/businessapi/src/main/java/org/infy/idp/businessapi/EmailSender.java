/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.businessapi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.infy.entities.triggerinputs.TriggerJobName;
import org.infy.idp.dataapi.services.JobAdditionalDetailsDL;
import org.infy.idp.dataapi.services.JobInfoDL;
import org.infy.idp.entities.jobs.IDPJob;
import org.infy.idp.entities.jobs.applicationinfo.ApplicationInfo;
import org.infy.idp.utils.ConfigurationManager;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The class EmailSender consists of methods to send the email on multiple
 * operation
 * 
 * @author Infosys
 */
@Component
public class EmailSender {

	private static final String SENDGRID = "sendgrid";

	private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);

	private static final String MAIL_TEMPLATE_PATH = "Mail.html";
	private static final String CREATE_APP_MAIL_TEMPLATE_PATH = "CreateAppMail.html";
	private static final String RELEASE_MAIL_TEMPLATE_PATH = "ReleaseMail.html";
	private static final String DASHBOARD_URL = "DASHBOARD_URL";
	private static final String IDP_LINK = "IDP_LINK";
	private static final String IDP_PIPELINE_CONFIGURATIONS = "IDP_PIPELINE_CONFIGURATIONS";
	private static final String USER = "USER";
	private static final String PIPELINE = "PIPELINE";
	private static final String APPLICATION = "APPLICATION";
	private static final String RELEASE = "RELEASE";
	private static final String METHOD = "METHOD";
	private static final String DEVELOPER = "DEVELOPER";
	private static final String PIPELINE_ADMIN = "PIPELINE_ADMIN";
	private static final String RELEASE_MANAGER = "RELEASE_MANAGER";
	private static final String ENVIRONMENT_DETAILS = "ENVIRONMENT_DETAILS";

	@Autowired
	private ConfigurationManager configmanager;
	@Autowired
	private JobInfoDL getJobDetails;
	@Autowired
	private JobAdditionalDetailsDL jobaddDetailsDL;

	@Autowired
	private FetchJobDetails fetchJobDetails;
	@Autowired
	private JobsAdditionalInfo jobsaddInfo;
	EmailSender() {

	}

	/**
	 * Method is used to send the job creation success mail
	 * 
	 * jobCreationSuccessMail
	 * 
	 * @param triggerJobName
	 * @param user
	 * @return true if email was sent successfully
	 */
	public boolean jobCreationSuccessMail(TriggerJobName triggerJobName, String user) {
		boolean status = true;
		String sub = "Pipeline";
		List<String> permissions = jobsaddInfo.getAllPermissionforApp(triggerJobName.getApplicationName(), user);
		if (!permissions.contains("CREATE_PIPELINE")) {
			status = false;
			return status;
		}
		List<String> emails = getUsersFromApplication(triggerJobName, user);
		logger.info("emails in job creation success mail" + emails.toString());
		triggerJobName.setMailBody(createMailBody(user, triggerJobName.getPipelineName(),
				triggerJobName.getApplicationName(), triggerJobName.getMethod()));
		String emailserver = configmanager.getEmailserver();
		if (emailserver.equalsIgnoreCase(SENDGRID)) {
			status = sendEmailSendGrid(sub, triggerJobName, user, emails);
		} else {
			status = sendEmail(sub, triggerJobName, user, emails);
		}
		return status;

	}

	/**
	 * 
	 * @param triggerJobName
	 * @param user
	 * @param applicationTeam
	 * @param additionalEmail
	 * @return boolean
	 */
	public boolean releaseUpdateSuccessMail(TriggerJobName triggerJobName, String user, String applicationTeam,
			String additionalEmail) {
		String sub = "Release";
		boolean status = true;
		List<String> emails = new ArrayList<String>();
		if ("on".equalsIgnoreCase(applicationTeam)) {
			emails = getUsersFromApplication(triggerJobName, user);
			logger.info("when application on : " + emails.toString());
		}
		String[] additionalEmails = additionalEmail.split(",");
		if (additionalEmails.length != 0) {
			for (String email : additionalEmails) {
				if (!"".equals(email))
					emails.add(email);
			}
		}
		emails = getUserEmailIds(emails);
		logger.info("additionalEmails : " + emails.toString());

		triggerJobName.setMailBody(createReleaseMailBody(user, triggerJobName.getPipelineName(),
				triggerJobName.getApplicationName(), triggerJobName.getMethod(), triggerJobName.getReleaseNumber()));

		String emailserver = configmanager.getEmailserver();
		if (emailserver != null && emailserver.equalsIgnoreCase(SENDGRID)) {
			status = sendEmailSendGrid(sub, triggerJobName, user, emails);
		} else {
			status = sendEmail(sub, triggerJobName, user, emails);
		}
		return status;
	}

	/**
	 * Method is used to send the job creation success mail
	 * 
	 * @param triggerJobName
	 * @param user
	 * @return boolean
	 */
	public boolean appCreationSuccessMail(TriggerJobName triggerJobName, String user) {
		boolean status = true;
		List<String> permissions = jobsaddInfo.getAllPermission(user);
		if (!permissions.contains("CREATE_APPLICATION")) {
			status = false;
			return status;
		}
		List<String> emails = getUsersForApplication(triggerJobName, user);
		try {
			String sub = "Application";
			ApplicationInfo app = getJobDetails.getApplication(triggerJobName.getApplicationName());
			triggerJobName.setMailBody(
					createAppMailBody(user, triggerJobName.getApplicationName(), triggerJobName.getMethod(), app));
			String emailserver = configmanager.getEmailserver();
			if (emailserver.equalsIgnoreCase(SENDGRID)) {
				status = sendEmailSendGrid(sub, triggerJobName, user, emails);
			} else {
				status = sendEmail(sub, triggerJobName, user, emails);
			}

		} catch (SQLException | NullPointerException e) {
			status = false;
			logger.error(e.getMessage(), e);
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

	public boolean sendEmailSendGrid(String sub, TriggerJobName triggerJobName, String user, List<String> emails) {

		logger.info("Inside send mail");
		String to;
		String domain = getJobDetails.getDomainName(user);
		logger.info("Emails are gonna send to : " + emails.toString());
		if (!user.contains(domain)) {
			to = user + "@" + domain;
		} else {
			to = user;
		}

		String cc = getCC(emails);
		String from = "";
		String username = configmanager.getEmailusername();
		String senderid = configmanager.getEmailsenderid();
		if (senderid.contains("@")) {
			from = configmanager.getEmailsenderid();
		} else {
			from = configmanager.getEmailsenderid() + configmanager.getEmaildomain();
		}

		String host = configmanager.getEmailsmtphost();
		String port = configmanager.getEmailsmtpport();

		Properties properties = new Properties();

		properties.setProperty("mail.smtp.host", host);
		Session session = null;
		if (!("".equals(configmanager.getEmailpassword()))) {
			logger.info("Tryiny to send email with Authentication... ");

			String password = configmanager.getEmailpassword();
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

			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
			message.setSubject("IDP: " + sub + " configuration details ");
			message.setContent(triggerJobName.getMailBody(), "text/html");

			logger.info(triggerJobName.getMailBody());
			logger.info(message.getContent().toString());

			Transport transport = session.getTransport();
			transport.connect();
			transport.send(message);
			transport.close();
			logger.info("Mail sent successfully");
		}

		catch (MessagingException | IOException e) {
			logger.info(triggerJobName.getMailBody());

			logger.error("mail could not be sent", e);
			logger.debug(e.getMessage(), e);

		}

		return true;
	}

	/**
	 * 
	 * Method is used to send the job creation success mail
	 * 
	 * @param triggerJobName the TriggerJobName
	 * @param user           the String
	 * @param email          the List
	 * 
	 * @return List<String>
	 * 
	 *         
	 *
	 */

	public boolean sendEmail(String sub, TriggerJobName triggerJobName, String user, List<String> emails) {

		logger.info("Inside send mail");
		String to;
		String domain = getJobDetails.getDomainName(user);
		logger.info("Emails are gonna send to : " + emails.toString());
		if (!user.contains(domain) && (!user.contains("@"))) {
			to = user + "@" + domain;
		} else {
			to = user;
		}

		String cc = getCC(emails);
		String from = "";
		String username = configmanager.getEmailusername();
		if (username.contains("@")) {
			from = configmanager.getEmailusername();
		} else {
			from = configmanager.getEmailusername() + configmanager.getEmaildomain();
		}

		String host = configmanager.getEmailsmtphost();
		String port = configmanager.getEmailsmtpport();
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		Session session = null;
		if (!("".equals(configmanager.getEmailpassword()))) {
			logger.info("Tryiny to send email with Authentication... ");

			String password = configmanager.getEmailpassword();
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

			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
			message.setSubject("IDP: " + sub + " configuration details ");
			message.setContent(triggerJobName.getMailBody(), "text/html");

			logger.info(triggerJobName.getMailBody());
			logger.info(message.getContent().toString());

			Transport.send(message);
			logger.info("Mail sent successfully");
		}

		catch (MessagingException | IOException e) {
			logger.info(triggerJobName.getMailBody());

			logger.error("mail could not be sent", e);
			logger.debug(e.getMessage(), e);

		}

		return true;
	}

	/**
	 * Creates string of users in CC 
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
	 * Returns the user list of the specified app & pipeline 
	 * 
	 * @param triggerJobName the TriggerJobName
	 * @param user           the String
	 * 
	 * @return List<String>
	 * 
	 *         
	 *
	 */
	public List<String> getUsersFromApplication(String applicationName, String pipelineName, String user) {

		TriggerJobName triggerJobName = new TriggerJobName();
		triggerJobName.setApplicationName(applicationName);
		triggerJobName.setPipelineName(pipelineName);
		triggerJobName.setUserName(user);
		List<String> users = getUsersFromApplication(triggerJobName, user);
		return users;

	}

	/**
	 * Returns the user list of the specified job
	 * @param triggerJobName
	 * @param user
	 * @return List<String>
	 * 
	 */
	public List<String> getUsersFromApplication(TriggerJobName triggerJobName, String user) {

		List<String> emails = new ArrayList<>();
		try {
			boolean team = true;

			ApplicationInfo app = getJobDetails.getApplication(triggerJobName.getApplicationName());
			IDPJob idp = jobaddDetailsDL.getPipelineInfo(triggerJobName.getApplicationName(),
					triggerJobName.getPipelineName());
			if (idp.getBasicInfo().getAdditionalMailRecipients() != null
					&& idp.getBasicInfo().getAdditionalMailRecipients().getApplicationTeam() != null
					&& idp.getBasicInfo().getAdditionalMailRecipients().getApplicationTeam().equalsIgnoreCase("On")) {
				emails = fetchJobDetails.getEmailRecipients(app);
				team = false;
			}
			if (team) {
				emails = fetchJobDetails.getPipelineAdmins(app);
			}
			if (idp.getBasicInfo().getAdditionalMailRecipients() != null
					&& idp.getBasicInfo().getAdditionalMailRecipients().getEmailIds() != null
					&& !("".equals(idp.getBasicInfo().getAdditionalMailRecipients().getEmailIds()))) {
				emails = fetchJobDetails.splitUsers(idp.getBasicInfo().getAdditionalMailRecipients().getEmailIds(),
						emails);
			}

			emails = getUserEmailIds(emails);

		} catch (SQLException e) {
			logger.error("Error in getting aplication", e);
			logger.debug("Error in getting application : " + triggerJobName.getApplicationName());
		}
		return emails;

	}

	/**
	 * 
	 * Method is used to send the job creation success mail
	 * 
	 * @param triggerJobName the TriggerJobName
	 * @param user           the String
	 * 
	 * @return List<String>
	 *
	 */
	public List<String> getUsersForApplication(TriggerJobName triggerJobName, String user) {

		List<String> emails = new ArrayList<>();
		try {
			ApplicationInfo app = getJobDetails.getApplication(triggerJobName.getApplicationName());
			emails = fetchJobDetails.getEmailRecipients(app);
			emails = getUserEmailIds(emails);

		} catch (SQLException e) {
			logger.error("Error in getting aplication", e);
			logger.debug("Error in getting application : " + triggerJobName.getApplicationName());
		}
		return emails;

	}

	/**
	 * 
	 * Method is used to send the job creation success mail
	 * 
	 * @param users the List<String>
	 * 
	 * @return List<String>
	 * 
	 *
	 */

	public List<String> getUserEmailIds(List<String> users) {

		String domain = configmanager.getEmaildomain();
		List<String> emails = new ArrayList<>();
		int userSize = users.size();
		for (int i = 0; i < userSize; i++) {
			if (!users.get(i).contains(domain) && !users.get(i).contains("@")) {
				emails.add(users.get(i) + domain);
			} else {
				emails.add(users.get(i));
			}
		}
		return emails;

	}

	/**
	 * Method is used to create mail body
	 * 
	 * @param user
	 * @param applicationName
	 * @param method
	 * @param app
	 * @return
	 */
	public String createAppMailBody(String user, String applicationName, String method, ApplicationInfo app) {

		String idpLink = configmanager.getIdplink();
		StringBuilder envDetaills = new StringBuilder();

		int appEnvOwnerDetailsSize = app.getEnvironmentOwnerDetails().size();
		for (int i = 0; i < appEnvOwnerDetailsSize; i++) {
			envDetaills.append("<B> &nbsp &nbsp Environment name: </B> "
					+ app.getEnvironmentOwnerDetails().get(i).getEnvironmentName());
			envDetaills.append("<B> &nbsp &nbsp Environment Owners: </B> "
					+ app.getEnvironmentOwnerDetails().get(i).getEnvironmentOwners());
			if (null != app.getEnvironmentOwnerDetails().get(i).getdBOwners()
					&& !"".equals(app.getEnvironmentOwnerDetails().get(i).getdBOwners())) {
				envDetaills.append(
						"<B> &nbsp &nbsp DB Owners: </B> " + app.getEnvironmentOwnerDetails().get(i).getdBOwners());
			}
			if (null != app.getEnvironmentOwnerDetails().get(i).getQa()
					&& !"".equals(app.getEnvironmentOwnerDetails().get(i).getQa())) {
				envDetaills.append("<B> &nbsp &nbsp QA: </B> " + app.getEnvironmentOwnerDetails().get(i).getQa());
			}
			envDetaills.append("<BR/>");
		}

		String tMethod = method;
		if ("create".equalsIgnoreCase(method)) {
			tMethod = "created";
		} else if ("edit".equalsIgnoreCase(method)) {
			tMethod = "edited";
		}
		JtwigTemplate template = JtwigTemplate.classpathTemplate(CREATE_APP_MAIL_TEMPLATE_PATH);
		JtwigModel model = JtwigModel.newModel().with(USER, user).with(APPLICATION, applicationName)
				.with(METHOD, tMethod).with(DEVELOPER, app.getDevelopers())
				.with(PIPELINE_ADMIN, app.getPipelineAdmins()).with(RELEASE_MANAGER, app.getReleaseManager())
				.with(ENVIRONMENT_DETAILS, envDetaills.toString()).with(IDP_LINK, idpLink);
		return template.render(model);

	}

	/**
	 * Method is used to create mail body
	 * 
	 * @param user
	 * @param pipeline
	 * @param applicationName
	 * @param methodType
	 * @return
	 */
	public String createMailBody(String user, String pipeline, String applicationName, String methodType) {

		String idpLink = configmanager.getIdplink();
		StringBuilder successPageLink = new StringBuilder();
		successPageLink.append(configmanager.getSuccesspage());
		successPageLink.append("/");
		successPageLink.append(applicationName);
		successPageLink.append("/");
		successPageLink.append(pipeline);
		String method = "";
		if ("create".equalsIgnoreCase(methodType)) {
			method = "created";
		} else if ("edit".equalsIgnoreCase(methodType)) {
			method = "edited";
		}

		String dashboardLink = configmanager.getDashboardurl();
		JtwigTemplate template = JtwigTemplate.classpathTemplate(MAIL_TEMPLATE_PATH);

		JtwigModel model = JtwigModel.newModel().with(USER, user).with(METHOD, method)
				.with(DASHBOARD_URL, dashboardLink).with(PIPELINE, pipeline).with(IDP_LINK, idpLink)
				.with(IDP_PIPELINE_CONFIGURATIONS, successPageLink);
		return template.render(model);

	}

	/**
	 * Method is used to create release mail body
	 * 
	 * @param user
	 * @param pipeline
	 * @param applicationName
	 * @param method
	 * @param release
	 * @return String
	 */

	public String createReleaseMailBody(String user, String pipeline, String applicationName, String methodType,
			String release) {

		String idpLink = configmanager.getIdplink();
		StringBuilder successPageLink = new StringBuilder();
		successPageLink.append(configmanager.getSuccesspage());
		successPageLink.append("/");
		successPageLink.append(applicationName);
		successPageLink.append("/");
		successPageLink.append(pipeline);
		String method = "";
		if ("add".equalsIgnoreCase(methodType)) {
			method = "Added";
		} else if ("update".equalsIgnoreCase(methodType)) {
			method = "Updated";
		}

		String dashboardLink = configmanager.getDashboardurl();
		JtwigTemplate template = JtwigTemplate.classpathTemplate(RELEASE_MAIL_TEMPLATE_PATH);

		JtwigModel model = JtwigModel.newModel().with(USER, user).with(METHOD, method)
				.with(DASHBOARD_URL, dashboardLink).with(PIPELINE, pipeline).with(IDP_LINK, idpLink)
				.with(RELEASE, release).with(APPLICATION, applicationName)
				.with(IDP_PIPELINE_CONFIGURATIONS, successPageLink);
		return template.render(model);

	}

}
