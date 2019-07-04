package org.infy.idp;

import org.infy.idp.utils.ConfigurationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppContext {


	@Bean
	public ConfigurationManager configurationManager() {
		ConfigurationManager configurationManager = new ConfigurationManager();
		configurationManager.setIdpurl("jdbc:postgresql://idptestServerHost:5432/IDP");
		configurationManager.setPostgresqldatabase("i2p");
		configurationManager.setPostgresqlinitialsize("5");
		configurationManager.setPostgresqlpassword("root");
		configurationManager.setPostgresqlschemaname("public");
		configurationManager.setPostgresqlusername("postgres");
		configurationManager.setPostgresqlinitialsize("5");
		configurationManager.setIdppostgresqlinitialsize("5");
		configurationManager.setIdppostgresqldatabase("IDP");
		configurationManager.setIdppostgresqlusername("postgres");
		configurationManager.setIdppostgresqlpassword("root");
		configurationManager.setIdppostgresqlschemaname("public");
		configurationManager.setBatchSize("1");
		configurationManager.setUrl("jdbc:postgresql://idptestServerHost:5432/i2p");
		configurationManager.setJenkinsID("userName");
		configurationManager.setJenkinsPassword("pwddummy");
		configurationManager.setJenkinsURL("https://idptestServerHost:8085/jenkins");
		return configurationManager;
	}

}