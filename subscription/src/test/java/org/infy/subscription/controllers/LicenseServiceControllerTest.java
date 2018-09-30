/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.subscription.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;

import org.infy.subscription.TokenUtils;
import org.infy.subscription.business.LicenseServiceBL;
import org.infy.subscription.entities.licencekeymanagement.Licenses;
import org.infy.subscription.entities.licencekeymanagement.Subscription;
import org.infy.subscription.entities.models.ResourceResponse;
import org.infy.subscription.exception.InvalidKeyException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = LicenseServiceController.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ActiveProfiles("mvc")
public class LicenseServiceControllerTest {
	@Autowired
	private LicenseServiceController licenseServiceController;
	@MockBean
	private OAuth2Authentication authBean;
	@MockBean
	private LicenseServiceBL licenseServiceBL;

	@Test
	public void testAddLicense() {

		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.spy(TokenUtils.class);
		OAuth2AuthenticationDetails oAuth = Mockito.mock(OAuth2AuthenticationDetails.class);
		Mockito.when(authBean.getDetails()).thenReturn(oAuth);
		Mockito.when(oAuth.getDecodedDetails()).thenReturn(new HashMap<String, Object>() {
			{
				put("organization", "INFOSYS");
			}
		});

		try {
			Mockito.when(licenseServiceBL.addLicense("licensekey", "INFOSYS")).thenReturn(true);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ResourceResponse<String> response = licenseServiceController.addLicense("licensekey", authBean);
		assertNotNull(response);
		assertEquals("SUCCESS", response.getStatus());
		assertEquals("SUCCESS", response.getResource());
		assertNull(response.getErrorMessage());

	}

	@Test
	public void testValidateLicenseSuccess() {

		Mockito.when(licenseServiceBL.validateLicense("licensekey")).thenReturn(true);

		ResourceResponse<String> response = licenseServiceController.validateLicense("licensekey");
		assertNotNull(response);
		assertEquals("SUCCESS", response.getStatus());
		assertEquals("SUCCESS", response.getResource());
		assertNull(response.getErrorMessage());

	}

	@Test
	public void testValidateLicenseFailure() {

		Mockito.when(licenseServiceBL.validateLicense("licensekey2")).thenReturn(false);

		ResourceResponse<String> response = licenseServiceController.validateLicense("licensekey2");
		assertNotNull(response);
		assertEquals("FAILURE", response.getStatus());
		assertEquals("FAILURE", response.getResource());
		assertNull(response.getErrorMessage());

	}

	@Test
	public void testGetAllActiveServices() {

		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.spy(TokenUtils.class);
		OAuth2AuthenticationDetails oAuth = Mockito.mock(OAuth2AuthenticationDetails.class);
		Mockito.when(authBean.getDetails()).thenReturn(oAuth);
		Mockito.when(oAuth.getDecodedDetails()).thenReturn(new HashMap<String, Object>() {
			{
				put("organization", "INFOSYS");
			}
		});
		Subscription subscription = new Subscription();
		Mockito.when(licenseServiceBL.getSubscriptions("INFOSYS")).thenReturn(subscription);

		ResourceResponse<String> response = licenseServiceController.getAllActiveServices(authBean);
		assertNotNull(response);
		assertNotNull(response.getStatus());
		assertEquals("SUCCESS", response.getResource());

	}
	
	@Test
	public void testGetAllActiveLicense() {

		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.spy(TokenUtils.class);
		OAuth2AuthenticationDetails oAuth = Mockito.mock(OAuth2AuthenticationDetails.class);
		Mockito.when(authBean.getDetails()).thenReturn(oAuth);
		Mockito.when(oAuth.getDecodedDetails()).thenReturn(new HashMap<String, Object>() {
			{
				put("organization", "INFOSYS");
			}
		});
		Licenses licenses = new Licenses();
		Mockito.when(licenseServiceBL.getAllActiveLicenses("INFOSYS")).thenReturn(licenses);
		ResourceResponse<String> response = licenseServiceController.getAllActiveLicense(authBean);
		assertNotNull(response);
		assertNotNull(response.getStatus());
		assertEquals("SUCCESS", response.getResource());

	}
}
