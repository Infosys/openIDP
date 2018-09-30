/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.config;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.infy.idp.auth.datalayer.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class AuthorizationServerApplication
 * @author Infosys
 * */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"org.infy.idp.config", "org.infy.idp.auth"})
public class AuthorizationServerApplication extends SpringBootServletInitializer {
	
  private static final  Logger logger=LoggerFactory.getLogger(AuthorizationServerApplication.class);
  
  @Autowired
  private Executor executor;
	/**
	 * Main method

	 * @param args the String Array
	 * 

	 * */
	
	public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
    }

	/**
	 * listen method

	 * @param message the String
	 * 

	 * */
	
	@KafkaListener(topics = "idpoauth", group = "idpoauth")
	public void listen(String message) {
	  logger.info("Received Messasge in group idpoauth: " + message);
	  try {
		  executor.insertUsers(message);
		} catch (SQLException | IOException e) {
			logger.error(e.getMessage(),e);
		}
	}
}

/**
 * ServiceInstanceRestController class
 * 
 * */

@RestController
class ServiceInstanceRestController {   

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * serviceInstancesByApplicationName method
     * @param applicationName as String
     * 
     * */
    
    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }
}