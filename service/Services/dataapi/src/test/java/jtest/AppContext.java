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
		return configurationManager;
	}

}