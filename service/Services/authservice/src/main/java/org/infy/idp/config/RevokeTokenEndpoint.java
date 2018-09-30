/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.config;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;

/**
 * This class is used for revoking token
 * @author Infosys
*/

@FrameworkEndpoint
public class RevokeTokenEndpoint {

	@Resource(name = "tokenServices")
	private ConsumerTokenServices tokenServices;

	/**
	 * method revokeToken
	 *
	 * @param request the HttpServletRequest.
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
	@ResponseBody
	public void revokeToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		if (authorization != null && authorization.contains("Bearer")) {
			String tokenId = authorization.substring("Bearer".length() + 1);
			tokenServices.revokeToken(tokenId);
		}
	}

}