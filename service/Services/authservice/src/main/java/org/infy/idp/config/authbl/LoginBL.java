/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config.authbl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.CommunicationException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.infy.idp.auth.datalayer.Executor;
import org.infy.idp.config.entities.LoginRequest;
import org.infy.idp.config.utils.ConfigurationManager;
import org.keycloak.RSATokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * The class LoginBL contains business logic for login based on LDAP or keycloak
 * @author Infosys
 */

@Component
public class LoginBL {
	private static final Logger logger = LoggerFactory.getLogger(LoginBL.class);

	@Autowired
	private ConfigurationManager configmanager;

	@Autowired
	private Executor executor;

	private String token = "false";

	/**
	 * Perform login
	 * 
	 * @param logindetails the LoginRequest
	 * @return boolean
	 */
	public String performLogin(LoginRequest logindetails) {
		int iStatus;
		iStatus = executor.performUserCheck(logindetails);
		if (iStatus == 0) {
			if (configmanager.getAuthProvider().equalsIgnoreCase("ldap")) {
				iStatus = performLDAPAuth(logindetails);
			} else {
				iStatus = performKeyCloakAuth(logindetails);
			}
		}
		logger.info("Auth Status : " + iStatus);
		if (iStatus == 0) {
			logger.info("authenticated!!!!");
			return token;
		} else {
			logger.info("unauthenticated!!!");
			return "false";
		}
	}

	/**
	 * Perform ldap authentication
	 * 
	 * @param logindetails the LoginRequest
	 * 
	 * @return int
	 */
	public int performLDAPAuth(LoginRequest logindetails) {
		Map<String, String> env = new Hashtable<>();
		String sSecurityPrincipal;
		if (StringUtils.isEmpty(configmanager.getLdapBase())) {
			sSecurityPrincipal = "itlinfosys\\" + logindetails.getUsername();
		} else {
			sSecurityPrincipal = "cn=" + logindetails.getUsername() + configmanager.getLdapBase();
		}

		env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
		// set security credentials

		env.put("java.naming.security.principal", sSecurityPrincipal);
		env.put("java.naming.security.credentials", logindetails.getPassword().trim());

		env.put("java.naming.provider.url", configmanager.getLdapurl().trim());
		try {
			// Create the initial directory context
			DirContext ctx = new InitialDirContext((Hashtable<String, String>) env);
			ctx.close();
			token = "true";
			return 0;

		} catch (AuthenticationException e) {
			logger.error(e.getMessage(), e);
			return 1;
		} catch (CommunicationException e) {
			logger.error(e.getMessage(), e);
			return 2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 3;
		}
	}

	/**
	 * Perform KeyCloak authentication
	 * 
	 * @param logindetails the LoginRequest
	 * 
	 * @return int
	 */
	public int performKeyCloakAuth(LoginRequest logindetails) {
		String accesstoken = getAccessToken();
		if (StringUtils.isNotEmpty(accesstoken)) {
			try {
				AccessToken rsaToken = RSATokenVerifier.create(accesstoken).getToken();
				if (rsaToken != null) {
					token = accesstoken;
					return 0;
				}
				return 1;
			} catch (VerificationException e) {
				logger.error("Invalid KeyCloak Token", e);
				return 1;
			}
		}
		String request = configmanager.getKeyCloakBase() + "/realms/" + configmanager.getKeyCloakRealm()
				+ "/protocol/openid-connect/token";
		BufferedReader in = null;
		DataOutputStream wr = null;
		try {
			String urlParameters = "username=" + URLEncoder.encode(logindetails.getUsername().trim(), "UTF-8")
					+ "&password=" + URLEncoder.encode(logindetails.getPassword().trim(), "UTF-8")
					+ "&grant_type=password";
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			URL url = new URL(request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String userpassword = "admin-cli:admin";
			String encodedAuth = Base64.encodeBase64String(userpassword.getBytes(Charset.forName("US-ASCII")));
			conn.setRequestProperty("Authorization", "Basic " + encodedAuth);

			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
			conn.setUseCaches(false);

			wr = new DataOutputStream(conn.getOutputStream());
			wr.write(postData);
			wr.flush();

			if (conn.getResponseCode() == 200) {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				token = response.toString();
				return 0;
			} else {
				throw new Exception("Authentication Failed");
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			return 1;
		} catch (ProtocolException e) {
			logger.error(e.getMessage(), e);
			return 2;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			return 3;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 4;
		} finally {
			if (wr != null) {
				try {
					wr.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 
	 * @return String
	 */
	private String getAccessToken() {
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getHeader("accessflow");
	}
}
