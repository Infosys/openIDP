/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config.utils;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The class contains methods to add users or clients for keycloak access
 * @author Infosys
 */

@Component
public class KeyCloakManagement {
	private static final Logger logger = LoggerFactory.getLogger(KeyCloakManagement.class);

	@Autowired
	private ConfigurationManager configManager;

	/**
	 * CONSTRUCTOR
	 */
	private KeyCloakManagement() {

	}

	/**
	 * 
	 * @param userName
	 * @param password
	 * @param setTemp
	 * @param orgName
	 * @return boolean
	 */
	public boolean addUser(String userName, String password, boolean setTemp, String orgName) {
		try {
			Keycloak kc = Keycloak.getInstance(configManager.getKeyCloakBase(), configManager.getKeyCloakRealm(),
					configManager.getKeycloakusername(), configManager.getKeycloakpassword(), "admin-cli");
			CredentialRepresentation credential = new CredentialRepresentation();
			credential.setType(CredentialRepresentation.PASSWORD);
			credential.setValue(password);
			credential.setTemporary(setTemp);
			UserRepresentation user = new UserRepresentation();
			user.setUsername(userName);
			user.setFirstName(userName);
			user.setEmail(userName.toLowerCase() + "@" + orgName.toLowerCase() + ".com");
			user.setEnabled(true);
			user.setCredentials(Arrays.asList(credential));
			kc.realm(configManager.getKeyCloakRealm()).users().create(user);
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		}
	}

	/**
	 * 
	 * @param clientName
	 * @return boolean
	 */
	public boolean addClient(String clientName) {
		try {
			Keycloak kc = Keycloak.getInstance(configManager.getKeyCloakBase(), configManager.getKeyCloakRealm(),
					configManager.getKeycloakusername(), configManager.getKeycloakpassword(), "admin-cli");
			ClientRepresentation client = new ClientRepresentation();
			client.setRedirectUris(Arrays.asList("*"));
			client.setClientId(clientName);
			client.setDirectAccessGrantsEnabled(true);
			client.setPublicClient(true);
			client.setWebOrigins(Arrays.asList("*"));
			kc.realm(configManager.getKeyCloakRealm()).clients().create(client);
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		}
	}

	/**
	 * post constructor
	 */
	@PostConstruct
	public void onBoardIdp() {
		this.addClient("idp");
		this.addUser("idpadmin", "idpadmin@123", false, "INFOSYS");
	}
}
