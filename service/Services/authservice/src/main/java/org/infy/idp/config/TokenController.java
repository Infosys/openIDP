/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The class TokenController is REST service for auth
 * 
 * @author Infosys
 */

@Controller
public class TokenController {

	@Resource(name = "tokenServices")
	private ConsumerTokenServices tokenServices;

	@Resource(name = "tokenStore")
	private TokenStore tokenStore;

	/**
	 * method revokeToken
	 *
	 * @param request the HttpServletRequest
	 * @param tokenId the String
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/oauth/token/revokeById/{tokenId}")
	@ResponseBody
	public void revokeToken(HttpServletRequest request, @PathVariable String tokenId) {
		tokenServices.revokeToken(tokenId);
	}

	/**
	 * 
	 * @return List<String>
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/tokens")
	@ResponseBody

	public List<String> getTokens() {
		List<String> tokenValues = new ArrayList<>();
		Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId("idpclient");
		if (tokens != null) {
			for (OAuth2AccessToken token : tokens) {
				tokenValues.add(token.getValue());
			}
		}
		return tokenValues;
	}

	/**
	 * method revokeRefreshToken
	 *
	 * @param tokenId the String
	 *
	 * @return tokenId the String
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/tokens/revokeRefreshToken/{tokenId:.*}")
	@ResponseBody
	public String revokeRefreshToken(@PathVariable String tokenId) {
		if (tokenStore instanceof JdbcTokenStore) {
			((JdbcTokenStore) tokenStore).removeRefreshToken(tokenId);
		}
		return tokenId;
	}

}