/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/


package org.infy.subscription.business;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.infy.subscription.dao.LicenseKeyRepository;
import org.infy.subscription.dao.ServiceRepository;
import org.infy.subscription.entities.licencekeymanagement.License;
import org.infy.subscription.entities.licencekeymanagement.LicenseKey;
import org.infy.subscription.entities.licencekeymanagement.OrganisationInfo;
import org.infy.subscription.entities.licencekeymanagement.Subscription;
import org.infy.subscription.exception.InvalidKeyException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
public class LicenseServiceBLTest {
	@Mock
	private LicenseKeyRepository licenseKeyRepository;
	@Mock
	private KafkaTemplate<String, String> kafkaTemplate;
	@Mock
	private LicenseManager licensemanager;
	@Mock
	private LicenseServiceFetcher licenseServiceFetcher;
	@Mock
	private ServiceRepository serviceRepository;
	@Spy
	@InjectMocks
	private LicenseServiceBL licenseServiceBL;

	@Test
	public void testCreateOrgMailBody() {

		OrganisationInfo orgInfo = new OrganisationInfo();
		orgInfo.setMethod("create");
		orgInfo.setOrgAdmin("idpadmin");
		orgInfo.setOrgName("INFOSYS");
		Boolean status = null;
		License validlicense = new License();
		LicenseKey license = new LicenseKey();
		validlicense.organization = "INFOSYS";
		Mockito.when(licensemanager.readLicenseInfo("jkey")).thenReturn(validlicense);
		Mockito.when(licenseServiceFetcher.createLicenseKeyObject(validlicense, "jkey", "INFOSYS")).thenReturn(license);
		Mockito.when(licenseKeyRepository.save(license)).thenReturn(license);
		try {
			status = licenseServiceBL.addLicense("jkey", "INFOSYS");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		assertNotNull(status);
	}

	@Test
	public void testGetAllActiveSubscriptions() {

		List<LicenseKey> keys = new ArrayList<>();
		LicenseKey license = new LicenseKey();
		keys.add(license);
		Mockito.when(licenseKeyRepository.getAllActiveLicense("INFOSYS")).thenReturn(keys);
		Subscription sub = licenseServiceBL.getAllActiveSubscriptions("INFOSYS");
		assertNotNull(sub);
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(licenseServiceBL, "topic", "topic");
	}
}
