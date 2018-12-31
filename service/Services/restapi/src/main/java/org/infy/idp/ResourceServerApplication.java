/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp;

import java.util.List;

import org.infy.idp.businessapi.OrgInfoBL;
import org.infy.idp.businessapi.SubscriptionBL;
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
 * 
 * The class ResourceServerApplication contains methods to configure the application 
 * @author Infosys
 *
 */
@EnableDiscoveryClient
@SpringBootApplication

public class ResourceServerApplication extends SpringBootServletInitializer {

	@Autowired
	private SubscriptionBL subscriptionBL;

	private final Logger logger = LoggerFactory.getLogger(ResourceServerApplication.class);

	@Autowired
	private OrgInfoBL orgInfoBL;


	/**
	 * 
	 * method main
	 *
	 * @param args the String[]
	 */

	public static void main(String[] args) {
		SpringApplication.run(ResourceServerApplication.class, args);

	}

	/**
	 * 
	 * @param message
	 */
	@KafkaListener(topics = "IDPlicense", group = "IDPlicense")
	public void listen(String message) {
		logger.info("Received Messasge in group IDPlicense: {}", message);
		subscriptionBL.updateSubScriptionList(message);

	}

	/**
	 * 
	 * @param message
	 */
	@KafkaListener(topics = "IDPorgInfo", group = "IDPorgInfo")
	public void listenOrgInfo(String message) {
		logger.info("Received Messasge in group IDPorgInfo: {}", message);
		orgInfoBL.updateOrgInfo(message);

	}
	
}

/**
 * 
 * @author Infosys
 *
 */
@RestController
class ServiceInstanceRestController {

	@Autowired
	private DiscoveryClient discoveryClient;

	/**
	 * 
	 * @param applicationName
	 * @return List<ServiceInstance>
	 */
	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}
}