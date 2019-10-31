/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utils;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
public class Application implements CommandLineRunner {
	private static final Logger logger = Logger.getLogger(Application.class);

	@Override
	public void run(final String... args) {
		logger.info("Started Metrics Processor..");
	}

	public static void main(String[] args) {
		SSLUtilities.trustAllHostnames();
		SSLUtilities.trustAllHttpsCertificates();
		BasicConfigurator.configure();
		//String[] args =
		// {"http://idposs:8184/idpdashboard/","admin","dfg","EnablingCalendarDemo","asdfasdf","D:\\jenkins\\Devops_Json"};
		System.out.println("MetricsProcessor util frozen");
		String postgresServiceURL = args[0];
		String PGServiceuserName = args[1];
		String PGServiceuserPassword = args[2];
		String appName = args[3];
		String pipeline = args[4];
		String jsonFileLocation = args[5].replaceAll("\\\\", "/");
		String grafana = null;
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = Application.class.getResourceAsStream("/dashboard.properties");
			prop.load(input);
			grafana = prop.getProperty("grafana");
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		if ("enabled".equalsIgnoreCase(grafana)) {
			logger.info("Grafana is enabled");
			try {
				//
				String path = args[5] + "/ProcessedFile";
				if (new File(path).mkdirs()) {
					logger.info("Processed Folder is created!");
				} else {
					logger.info("Processed Folder is already there!");
				}
				System.out.println("Hitting the service....");
				new DaoInflux().readmethod(appName, pipeline, jsonFileLocation, postgresServiceURL, PGServiceuserName,
						PGServiceuserPassword);
				System.out.println("Skipping Exec Level writing ");
			} catch (Exception e) {
				logger.error("error", e);
			}
		} else if ("disabled".equalsIgnoreCase(grafana)) {
			logger.info("connecting to ge web service..");
			new GewebService().jsonPost(appName, pipeline, jsonFileLocation);
		}
	}
}
