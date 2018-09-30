/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * 
 * The class TokenUtils contains method to get organization for REST after authentication
 * @author Infosys
 *
 */
public class TokenUtils {
	private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

	/**
	 * 
	 * @param auth
	 * @return String
	 */
	public static String getOrganization(OAuth2Authentication auth) {
		OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
		Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		logger.info("User organization is " + details.get("organization"));
		return details.get("organization").toString();
	}
}
