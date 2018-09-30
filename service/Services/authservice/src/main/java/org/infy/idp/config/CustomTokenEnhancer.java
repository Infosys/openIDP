/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.config;

import java.util.HashMap;
import java.util.Map;

import org.infy.idp.auth.datalayer.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * 
 * This class allows customization of token
 * @author Infosys
 * 
 */

public class CustomTokenEnhancer implements TokenEnhancer {
	/**
	 * {@inheritDoc}
	 */
  @Autowired
  private Executor executor;
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("organization", executor.getOrgName(authentication.getName().toLowerCase()));
        additionalInfo.put("keycloaktoken", ((CustomAuthentication)authentication.getUserAuthentication()).getKeyCloakToken());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }

}
