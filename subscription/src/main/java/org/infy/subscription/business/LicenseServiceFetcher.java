package org.infy.subscription.business;

import java.sql.Date;

import java.util.ArrayList;
import java.util.List;

import org.infy.subscription.entities.licencekeymanagement.License;
import org.infy.subscription.entities.licencekeymanagement.LicenseKey;

import org.infy.subscription.entities.licencekeymanagement.Service;
import org.infy.subscription.entities.licencekeymanagement.ServiceIdentity;
import org.infy.subscription.entities.licencekeymanagement.ServiceType;

import org.springframework.stereotype.Component;

/**
 * All the json manipulation will be handled here
 * 
 * @author Infosys
 *
 */
@Component
public class LicenseServiceFetcher {

	/**
	 * 
	 * @param license
	 * @param licenseKeyStr
	 * @return LicenseKey
	 */
	public LicenseKey createLicenseKeyObject(License license, String licenseKeyStr, String orgName) {
		LicenseKey licenseKey = new LicenseKey();
		licenseKey.setExpiryDate(license.getExpirydate());
		licenseKey.setLicenseKey(licenseKeyStr);
		licenseKey.setLicenseType(license.getLicenseType());
		licenseKey.setOrgName(orgName);
		return licenseKey;
	}

	/**
	 * 
	 * @param licenseKey
	 * @param licenseId
	 * @return List<Service>
	 */
	public List<Service> createServiceObjects(License license, long licenseId, String orgName) {

		List<Service> services = new ArrayList<>();
		List<ServiceType> serviceTypes = license.getServiceList();

		for (ServiceType serviceType : serviceTypes) {
			services.add(createServiceObject(license.getExpirydate(), serviceType.name(), licenseId, orgName));
		}
		return services;
	}

	/**
	 * 
	 * @param expieryDate
	 * @param serviceName
	 * @param licenseId
	 * @return Service
	 */
	public Service createServiceObject(Date expieryDate, String serviceName, long licenseId, String orgName) {
		Service service = new Service();
		service.setExpiryDate(expieryDate);
		ServiceIdentity serviceIdentity = new ServiceIdentity();
		serviceIdentity.setServiceName(serviceName);
		serviceIdentity.setLicenseId(licenseId);
		service.setServiceIdentity(serviceIdentity);
		service.setOrgName(orgName);
		return service;
	}

	List<Service> createServicesListfromObjectLists(List<Object[]> objects, String orgName) {
		List<Service> services = new ArrayList<>();
		for (int i = 0; i < objects.size(); i++) {
			services.add(createServiceObject((Date) objects.get(i)[1], (String) objects.get(i)[0], 0, orgName));
		}
		return services;
	}
}
