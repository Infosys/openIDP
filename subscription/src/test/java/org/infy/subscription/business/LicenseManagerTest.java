/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.subscription.business;

import static org.junit.Assert.assertNotNull;

import org.infy.subscription.entities.licencekeymanagement.License;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class LicenseManagerTest {

	@Spy
	private EncryptionManager encryptionManager;
	@InjectMocks
	private LicenseManager licenseManager;

	@Test
	public void testReadLicenseInfo() {
		License license = licenseManager.readLicenseInfo(
				"rO0ABXNyADtvcmcuaW5meS5zdWJzY3JpcHRpb24uZW50aXRpZXMubGljZW5jZWtleW1hbmFnZW1lbnQuTGljZW5zZVid21EZZuRGAgAGTAAHZW1haWxpZHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wACmV4cGlyeWRhdGV0AA9MamF2YS9zcWwvRGF0ZTtMAAtsaWNlbnNlVHlwZXEAfgABTAAMb3JnYW5pemF0aW9ucQB+AAFMAAtzZXJ2aWNlTGlzdHQAEExqYXZhL3V0aWwvTGlzdDtMAAlzaWduYXR1cmVxAH4AAXhwdAAWY2lwbGF0Zm9ybUBpbmZvc3lzLmNvbXNyAA1qYXZhLnNxbC5EYXRlFPpGaD81ZpcCAAB4cgAOamF2YS51dGlsLkRhdGVoaoEBS1l0GQMAAHhwdwgAAAFkfFZ8AHh0AAdQUkVNSVVNdAAHaW5mb3N5c3NyABNqYXZhLnV0aWwuQXJyYXlMaXN0eIHSHZnHYZ0DAAFJAARzaXpleHAAAAADdwQAAAADfnIAP29yZy5pbmZ5LnN1YnNjcmlwdGlvbi5lbnRpdGllcy5saWNlbmNla2V5bWFuYWdlbWVudC5TZXJ2aWNlVHlwZQAAAAAAAAAAEgAAeHIADmphdmEubGFuZy5FbnVtAAAAAAAAAAASAAB4cHQAAkNJfnEAfgANdAACQ0R+cQB+AA10AAJDVHh0AVhMYXVHOFBQK2drcW0rdm1RL1NIWGIxWm01TCtCUjRCZEdCYTNvVEdnUy81WTJuN0hlTXAvR0VvcWp4VnRlODhmQXJzTzkxVXhvUmxUMGJJWlJIN0JHMUQwaVI3bzk4SW5tSzVLdURqS0JMZ0hTMDhGZDdUSUZsTWlSZ1ZET2NGWTFzWmNxWnc4SlY2NmtQa2d4RUFVUldzKzJoL0dZUUxmREREcThJbWhoS2p1T0Zva3B5Mk16ZnpqVlZzOGlRZFpMQ0VkOUVPK2N4dUozOXIwRHhqTVRGeGJZWmFWUEo2ZUpVN3FKMmlqMkpLall1NklkdFRkdUtac24rSVhNVlVSQXl6YVZkaDIvQjlvL3dMdTZpZVhqR2pOai9tQTMwUXA1cEVxdmtkQkNSODBCOExSUkQvcWxGYStBQjlrRTgwWGsxbFJOdWV0aWhrS2Z6Skp5RWFtdlE9PQ==");

		assertNotNull(license);
	}

	@Before
	public void setUp() {
		encryptionManager = new EncryptionManager();
	}

}
