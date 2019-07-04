package org.infy.idp.configure;

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
		configurationManager.setJenkinsurl("http://server411214d:8085/jenkins");
		configurationManager.setJenkinspassword("pwddummy");
		configurationManager.setJenkinsuserid("userName");
		
		
		return configurationManager;
	}

}