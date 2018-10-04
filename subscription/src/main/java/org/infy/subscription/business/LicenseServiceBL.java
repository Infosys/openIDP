/**
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.”
*
**/
package org.infy.subscription.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.infy.subscription.dao.LicenseKeyRepository;
import org.infy.subscription.dao.ServiceRepository;
import org.infy.subscription.entities.licencekeymanagement.License;
import org.infy.subscription.entities.licencekeymanagement.LicenseKey;
import org.infy.subscription.entities.licencekeymanagement.Licenses;
import org.infy.subscription.entities.licencekeymanagement.Service;
import org.infy.subscription.entities.licencekeymanagement.Subscription;

import org.infy.subscription.exception.InvalidKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

/**
 * 
 * Class LicenseServiceBL has all the business logic
 * 
 * @author Infosys
 * 
 * 
 */
@Component
public class LicenseServiceBL {

	@Autowired
	private LicenseKeyRepository licenseKeyRepository;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private LicenseManager licensemanager;
	@Autowired
	private LicenseServiceFetcher licenseServiceFetcher;

	@Autowired
	private ServiceRepository serviceRepository;

	@Value("${kafkalicensetopic}")
	private String topic;

	private static final Logger logger = LoggerFactory.getLogger(LicenseServiceBL.class);

	private static final String INVALIDKEY = "Invalid License Key";
	private static final String DUPLICATEKEY = "Duplicate License Key";

	/**
	 * 
	 * @param licenseKey
	 * @return String
	 * @throws InvalidKeyException , DuplicateKeyException
	 */
	public boolean addLicense(String licenseKey, String orgName) throws InvalidKeyException {
		logger.info("adding license");
		try {
			LicenseKey license = null;
			License validlicense = null;
			validlicense = licensemanager.readLicenseInfo(licenseKey);

			if (null == validlicense) {
				throw new InvalidKeyException(INVALIDKEY);
			}

			logger.info(validlicense.emailid + " " + validlicense.organization);
			license = licenseServiceFetcher.createLicenseKeyObject(validlicense, licenseKey, orgName);
			license.setLicenseType("PREMIUM");
			validlicense.setLicenseType("PREMIUM");

			long licenseId = 0;

			licenseId = licenseKeyRepository.save(license).getLicenseId();
			logger.info("license Key added in tlicense_management table with id : " + licenseId);

			List<Service> services = licenseServiceFetcher.createServiceObjects(validlicense, licenseId, orgName);

			insertServices(services);
			updateSubscription(orgName);
			return true;
		} catch (InvalidKeyException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new InvalidKeyException(DUPLICATEKEY);
		}

	}

	/**
	 * 
	 * @param services
	 */
	public void insertServices(List<Service> services) {
		for (Service service : services) {
			serviceRepository.save(service);
		}
	}

	/**
	 * 
	 * @param licenseKey
	 * @return boolean
	 */
	public boolean validateLicense(String licenseKey) {
		logger.info("validating license");
		int count = licenseKeyRepository.countLicenseKeyAndActive(licenseKey.getBytes());

		return (count == 1);

	}

	/**
	 * update subscriptions
	 */
	public void updateSubscription(String orgName) {
		Gson gson = new Gson();
		Subscription subscription = getSubscriptions(orgName);
		kafkaTemplate.send(topic, gson.toJson(subscription));

	}

	/**
	 * get all subscriptions
	 */
	public Subscription getAllActiveSubscriptions(String orgName) {
		List<LicenseKey> license = licenseKeyRepository.getAllActiveLicense(orgName);
		logger.info("all the license" + license.toString());
		return getSubscriptions(orgName);

	}

	/**
	 * 
	 * @return Subscription
	 */
	public Subscription getSubscriptions(String orgName) {
		Subscription subscription = new Subscription();
		List<Service> services = licenseServiceFetcher
				.createServicesListfromObjectLists(serviceRepository.getAllActiveServices(orgName), orgName);

		HashMap<String, Integer> serviceMap = new HashMap<>();

		serviceMap.put("CI", 1);
		serviceMap.put("CT", 2);
		serviceMap.put("CD", 3);

		Collections.sort(services, new Comparator<Service>() {

			@Override
			public int compare(Service s1, Service s2) {
				if (s1 != null && s2 != null) {
					return serviceMap.get(s1.getServiceIdentity().getServiceName().toUpperCase())
							- serviceMap.get(s2.getServiceIdentity().getServiceName().toUpperCase());
				}
				return 0;
			}
		});
		subscription.setSubscriptionTypes(services);
		return subscription;
	}

	/**
	 * 
	 * @return Licenses
	 */
	public Licenses getAllActiveLicenses(String orgName) {
		List<LicenseKey> license = licenseKeyRepository.getAllActiveLicense(orgName);
		return getLicense(license);
	}

	/**
	 * 
	 * @param licensePram
	 * @return Licenses
	 */
	public Licenses getLicense(List<LicenseKey> licensePram) {
		Licenses licenses = new Licenses();
		License license;
		List<License> licenseList = new ArrayList<>();
		for (LicenseKey licenseKey : licensePram) {
			license = licensemanager.readLicenseInfo(new String(licenseKey.getLicenseKey()));
			licenseList.add(license);
		}
		licenses.setLicenses(licenseList);
		return licenses;
	}

}
