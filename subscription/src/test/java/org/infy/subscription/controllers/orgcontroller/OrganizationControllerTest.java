/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.subscription.controllers.orgcontroller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;

import org.infy.subscription.TokenUtils;
import org.infy.subscription.business.EmailSender;
import org.infy.subscription.business.LicenseServiceBL;
import org.infy.subscription.business.OrgBL;
import org.infy.subscription.controllers.LicenseServiceController;
import org.infy.subscription.dao.OrgRegistrationRepository;
import org.infy.subscription.entities.models.ResourceResponse;
import org.infy.subscription.entities.models.org.OrgInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

@SpringBootTest(classes = LicenseServiceController.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ActiveProfiles("mvc")
public class OrganizationControllerTest {
	@InjectMocks
	private OrganizationController organizationController;
	@MockBean
	private OAuth2Authentication authBean;
	@MockBean
	private EmailSender emailSender;
	@MockBean
	private OrgBL orgRegistrationBL;
	@MockBean
	private LicenseServiceBL licenseServiceBL;
	@Mock
	private KafkaTemplate<String, String> kafkaTemplate;
	@Mock
	private OrgRegistrationRepository orgRegistrationRepository;
	@Test
	public void testRegisteration() {

		Mockito.when(authBean.getPrincipal()).thenReturn("idpadmin");
		Mockito.spy(TokenUtils.class);
		OAuth2AuthenticationDetails oAuth = Mockito.mock(OAuth2AuthenticationDetails.class);
		Mockito.when(authBean.getDetails()).thenReturn(oAuth);
		Mockito.when(oAuth.getDecodedDetails()).thenReturn(new HashMap<String, Object>() {
			{
				put("organization", "INFOSYS");
			}
		});
		String resourceResponse = "SUCCESS";
		OrgInfo orgInfo = new OrgInfo();
		Mockito.when(orgRegistrationBL.registerOrg(orgInfo)).thenReturn(resourceResponse);

		ResourceResponse<String> response = organizationController.registeration(orgInfo, authBean);
		assertNotNull(response);
		assertEquals("SUCCESS", response.getStatus());
		assertEquals("SUCCESS", response.getResource());
		assertNull(response.getErrorMessage());

	}
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(orgRegistrationBL, "topic", "topic");
	}
}
