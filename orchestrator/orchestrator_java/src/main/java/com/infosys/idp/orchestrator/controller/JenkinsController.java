/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.idp.orchestrator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infosys.idp.orchestrator.utils.JenkinsConnectorUtil;

/**
 * JenkinsController
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class JenkinsController {
	
	private final Logger logger = LoggerFactory.getLogger(JenkinsController.class);
	

    @Autowired
    private JenkinsConnectorUtil jenkinsConnectorUtil;
    
    @Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
    
    /**
	 * 
	 * @param message
	 */
	@KafkaListener(topics = "ci_ops" , groupId = "orchestrator") 
	public void listen(String message) {
		logger.info("Received Messasge in group orchestrator: {}", message);
		jenkinsConnectorUtil.jenkinsConnectionForKafakMessage(message);

	}
    
    @PostMapping("/orchestrator/kafka/test")
    public ResponseEntity<String> kafkatest(@RequestBody String jsonData) {
    	logger.info("kafka data : {}" ,  jsonData);
    	kafkaTemplate.send("ci_ops", jsonData);
    	return new ResponseEntity<>("success", HttpStatus.OK);
    	
    }
    
    

    @PostMapping("/orchestrator/pipeline/create")
    public ResponseEntity<String> createPipeline(@RequestBody String jsonData) {
        return jenkinsConnectorUtil.routeconnection("/job/create", jsonData);
    }

    @PostMapping("/orchestrator/pipeline/edit")
    public ResponseEntity<String> editPipeline(@RequestBody String jsonData) {
        return jenkinsConnectorUtil.routeconnection("/job/create", jsonData);
    }

    @PostMapping("/orchestrator/pipeline/trigger")
    public ResponseEntity<String> triggerPipeline(@RequestBody String jsonData) {
        return jenkinsConnectorUtil.routeconnection("/job/build", jsonData);
    }

    @PostMapping("/orchestrator/artifacts/add")
    public ResponseEntity<String> addArtifactoryRepoGlobConf(@RequestBody String jsonData) {
        return jenkinsConnectorUtil.routeconnection("/artifacts/add", jsonData);
    }

    @PostMapping("/orchestrator/artifacts/download")
    public ResponseEntity<String> downloadArtifacts(@RequestBody String jsonData) {
        return jenkinsConnectorUtil.routeconnection("/artifacts/download", jsonData);
    }

    @PostMapping("/orchestrator/config/add/alm/config")
    public ResponseEntity<String> addALMConfig(@RequestBody String jsonData) {
        return jenkinsConnectorUtil.routeconnection("/config/add/alm/config", jsonData);
    }

    @PostMapping("/orchestrator/config/get/stageviewurl")
    public ResponseEntity<String> getStageViewUrl(@RequestBody String jsonData) {
        return jenkinsConnectorUtil.routeconnection("/config/get/stageviewurl", jsonData);
    }

    @PostMapping("/orchestrator/config/get/jobjson")
    public ResponseEntity<String> getJobJSON(@RequestBody String jsonData) {
        return jenkinsConnectorUtil.routeconnection("/config/get/jobjson", jsonData);
    }

    @PostMapping("/orchestrator/job/disableJob")
    public ResponseEntity<String> disableJob(@RequestBody String jsonData) {
        return jenkinsConnectorUtil.routeconnection("/job/disableJob", jsonData);
    }

    @PostMapping("/orchestrator/job/approve")
    public ResponseEntity<String> apprRejectJobs(@RequestBody String jsonData) {
        return jenkinsConnectorUtil.routeconnection("/job/approve", jsonData);
    }

    @PostMapping("/orchestrator/job/get/status")
    public ResponseEntity<String> getBuildStatus(@RequestBody String jsonData) {
        return jenkinsConnectorUtil.routeconnection("/job/get/status", jsonData);
    }

    @PostMapping("/orchestrator/role/create")
    public ResponseEntity<String> createRole(@RequestBody String jsonData) {
        return jenkinsConnectorUtil.routeconnection("/role/create", jsonData);
    }

    @PostMapping("/orchestrator/slave/copy")
    public ResponseEntity<String> copySlave(@RequestBody String jsonData) {
        return jenkinsConnectorUtil.routeconnection("/slave/copy", jsonData);
    }

    @PostMapping("/orchestrator/slave/get/status")
    public ResponseEntity<String> getSlaveStatus(@RequestBody String jsonData) {
        return jenkinsConnectorUtil.routeconnection("/slave/get/status", jsonData);
    }

}