/*******************************************************************************
* © 2018 Infosys Limited, Bangalore, India. All Rights Reserved. 
 * IDP_PaaS
 * 
 * Except for any free or open source software components embedded in this 
 * Infosys proprietary software program, this Program is protected 
 * by copyright laws, international treaties and other pending or existing 
 * intellectual property rights in India, the United States and other countries. 
 * Except as expressly permitted, any unauthorized reproduction, storage, 
 * transmission in any form or by any means (including without limitation 
 * electronic, mechanical, printing, photocopying, recording or otherwise), 
 * or any distribution of this Program, or any portion of it, may result in 
 * severe civil and criminal penalties, and will be prosecuted to the maximum 
 * extent possible under the law.
 ******************************************************************************/
package org.infy.subscription.business;

import java.util.ArrayList;
import java.util.List;

import org.infy.subscription.Constants;
import org.infy.subscription.dao.OrgRegistrationRepository;
import org.infy.subscription.entities.models.org.OrgInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * 
 * @author Infosys
 *
 */
@Component
public class OrgBL {

	@Autowired
	OrgRegistrationRepository orgRegistrationRepository;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value("${kafkaorgtopic}")
	private String topic;

	/**
	 * 
	 * @param orgInfo
	 * @return String
	 */
	public String registerOrg(OrgInfo orgInfo) {
		Gson gson = new Gson();
		orgRegistrationRepository.save(orgInfo);
		kafkaTemplate.send(topic, gson.toJson(orgInfo));
		return Constants.SUCCESS;
	}

	public List<OrgInfo> getOrgList() {
		List<OrgInfo> orgList = new ArrayList<>();
		orgList = orgRegistrationRepository.findAllOrgList();

		return orgList;
	}

	public int updateOrg(OrgInfo orgInfo) {
		int result = orgRegistrationRepository.updateOrg(orgInfo.getOrgName(), orgInfo.getOrgAdmin(),
				orgInfo.getDomain());
		return result;
	}

	public String findDomain(String orgName) {
		return orgRegistrationRepository.findDomain(orgName);
	}

}
