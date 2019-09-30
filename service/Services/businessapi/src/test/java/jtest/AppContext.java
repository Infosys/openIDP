package jtest;

import org.infy.idp.utils.ConfigurationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContext {


	@Bean
	public ConfigurationManager configurationManager() {
		ConfigurationManager configurationManager = new ConfigurationManager();
		configurationManager.setPostgresqldatabase("IDP");
		configurationManager.setPostgresqlinitialsize("5");
		configurationManager.setPostgresqlpassword("root");
		configurationManager.setPostgresqlschemaname("public");
		configurationManager.setPostgresqlusername("postgres");
		configurationManager.setPostgresqlinitialsize("5");
		configurationManager.setUrl("jdbc:postgresql://localhost:5432/IDP");
		configurationManager.setEmaildomain("@domain.com");
		configurationManager.setEmailusername("idpadmin@xyz.com");
		configurationManager.setEmailpassword("dummyuser");
		configurationManager.setEmailsmtphost("email_host");
		configurationManager.setEmailsmtpport("25");
		configurationManager.setJenkinsurl("http://localhost:8085/jenkins");
		configurationManager.setJenkinsstageviewurl("http://localhost:8085/jenkins");
		configurationManager.setUserID("idpadmin");
		configurationManager.setPassword("dummyuser");
		return configurationManager;
	}
	

	
}