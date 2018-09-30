/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp;

import java.util.List;

import org.infy.idp.bl.InsertInfoBL;
import org.infy.idp.dataapi.Executor;
import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class AuthorizationServerApplication contains methods to configure the Dashboard
 * */
@EnableDiscoveryClient
@SpringBootApplication
public class DashboardServerApplication extends SpringBootServletInitializer {

	private static final  Logger localLogger=LoggerFactory.getLogger(DashboardServerApplication.class);
	
	@Autowired
	private Executor executor;
	
	@Autowired
	private InsertInfoBL insertBL;

	/**
	 * Main method

	 * @param args the String Array
	 * 

	 * */

	public static void main(String[] args) {
        SpringApplication.run(DashboardServerApplication.class, args);
    
    }
	
		/**
	 * listen method

	 * @param message the String
	 * 

	 * */
	
	@KafkaListener(topics = "idpdashboard_users", group = "idpdashboard")
	public void listenUsers(String message) {
	  localLogger.info("Received Messasge in group idpdashboard_users: ");
	  localLogger.info(message);
	  try {
      executor.insertUsers(message);
    } catch (SQLException | IOException e) {
    	localLogger.error(e.getMessage(),e);
    }
	}
	
	@KafkaListener(topics = "idpdashboard_app", group = "idpdashboard")
	public void listenApp(String message) {
	  localLogger.info("Received Messasge in group idpdashboard_app: ");
	  localLogger.info(message);
	  try {
      executor.insertApplication(message);
    } catch (SQLException | IOException e) {
    	localLogger.error(e.getMessage(),e);
    }
	}
	
	@KafkaListener(topics = "idpAppDetails", group = "idpdashboard")
	public void listenAppData(String message) {
	  localLogger.info("Received Message in topic idpAppDetails: ");
	  localLogger.info(message);
	  try {
		insertBL.updateAppDetails(message);
	} catch (JSONException e) {
		localLogger.error(e.getMessage(),e);
	}
	}

}
@RestController
class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }
}