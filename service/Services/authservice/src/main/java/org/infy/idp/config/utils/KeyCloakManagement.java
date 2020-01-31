/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package org.infy.idp.config.utils;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientScopesResource;
import org.keycloak.admin.client.resource.ProtocolMappersResource;
import org.keycloak.representations.idm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The class contains methods to add users or clients for keycloak access
 *
 * @author Infosys
 */

@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class KeyCloakManagement {
    private static final Logger logger = LoggerFactory.getLogger(KeyCloakManagement.class);
    
    private Keycloak keycloak;
    
    @Autowired
    private ConfigurationManager configManager;

	/**
	 * CONSTRUCTOR
	 */
    private KeyCloakManagement() {

    }

    public KeyCloakManagement(ConfigurationManager configManager) {
        this.configManager = configManager;
    }

    


    /**
     * @param userName
     * @param password
     * @param setTemp
     * @param orgName
     * @return boolean
     */
    public boolean addUser(String userName, String password, boolean setTemp, String orgName) {
        try {
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(password);
            credential.setTemporary(setTemp);
            UserRepresentation user = new UserRepresentation();
            user.setUsername(userName);
            user.setFirstName(userName);
            user.setEmail(userName.toLowerCase() + "@xyz.com");
            user.setEnabled(true);
            user.setCredentials(Arrays.asList(credential));
            keycloak.realm(configManager.getKeyCloakRealm()).users().create(user);
            return true;
        } catch (Exception e) {
            logger.info(e.getMessage());
            return false;
        }
    }

    /**
     * @param clientName
     * @throws URISyntaxException
     */
    public void configureKeycloakClient(String clientName) throws URISyntaxException {
        ClientRepresentation client = keycloak.realm(configManager.getKeyCloakRealm())
                .clients().findByClientId(clientName).stream().findFirst().orElse(new ClientRepresentation());
        client.setRedirectUris(Arrays.asList("*"));
        client.setClientId(clientName);
        client.setDirectAccessGrantsEnabled(true);
        client.setImplicitFlowEnabled(true);
        client.setPublicClient(true);
        client.setWebOrigins(Arrays.asList("*"));
        ClientScopeRepresentation clientScope = keycloak.realm(configManager.getKeyCloakRealm()).clientScopes().findAll()
                .stream().filter(cs -> "idp_scope".equals(cs.getName())).findAny().orElse(new ClientScopeRepresentation());
        String clientScopeId = createOrUpdateClientScope(clientName, clientScope);
        if(client.getId() != null) {
            URI clientURI = new URI(configManager.getKeyCloakBase() + "/admin/realms/" + configManager.getKeyCloakRealm() + "/clients/" + client.getId());
            keycloak.proxy(ClientResource.class, clientURI).update(client);
        }else{
            keycloak.realm(configManager.getKeyCloakRealm()).clients().create(client);
            String clientId = keycloak.realm(configManager.getKeyCloakRealm())
                    .clients().findByClientId(clientName).stream().findFirst().orElse(new ClientRepresentation()).getId();
            client.setId(clientId);
        }
        if (clientScopeId != null) {
            URI clientURI = new URI(configManager.getKeyCloakBase() + "/admin/realms/" + configManager.getKeyCloakRealm() + "/clients/" + client.getId());
            keycloak.proxy(ClientResource.class, clientURI).addDefaultClientScope(clientScopeId);
        }
    }

    /**
     * This method just initializes the Keycloak client library.
     * This method is called from @PostConstruct method.
     * This method also can be used from the caller in case if this bean is not created from spring container.
     */
    public void initKeycloak() {
        if (this.keycloak == null) {
            this.keycloak = Keycloak.getInstance(configManager.getKeyCloakBase(), configManager.getKeyCloakRealm(),
                    configManager.getKeycloakusername(), configManager.getKeycloakpassword(), "admin-cli");
        }
    }

    /**
     * @param clientName
     * @param clientScope
     * @return
     * @throws URISyntaxException This method will create the client scope with the name `<clientName>_scope` and adds all the mappers required
     *                            for IDP to work.
     */
    private String createOrUpdateClientScope(String clientName, ClientScopeRepresentation clientScope) throws URISyntaxException {
        if (clientScope.getId() == null) {
            logger.info("Client '{0}' not found. Now creating it in Keycloak ", getClientScopeName(clientName));
            URI clientURI = new URI(configManager.getKeyCloakBase() + "/admin/realms/" + configManager.getKeyCloakRealm() + "/client-scopes");
            clientScope.setName(getClientScopeName(clientName));
            clientScope.setProtocolMappers(getMappers(clientName));
            clientScope.setProtocol("openid-connect");
            keycloak.proxy(ClientScopesResource.class, clientURI).create(clientScope);
            logger.info("created client scope {0}", clientScope);
        } else {
            logger.info("Updating the client '{0}'", getClientScopeName(clientName));
            List<ProtocolMapperRepresentation> requiredList = getMappers(clientName);
            List<ProtocolMapperRepresentation> availableList = clientScope.getProtocolMappers();
            List<ProtocolMapperRepresentation> protocolMappersList;
            if (availableList == null) {
                protocolMappersList = requiredList;
            } else {
                protocolMappersList = requiredList.stream().filter(e -> {
                    return availableList.stream().filter(f -> e.getName().equals(f.getName())).findAny().orElse(null) == null;
                }).collect(Collectors.toList());
            }
            if (protocolMappersList.size() > 0) {
                logger.info("There are few required protocol mappers not present in keycloak {0}. Hence adding them in Keycloak", protocolMappersList);
                URI clientURI = new URI(configManager.getKeyCloakBase() + "/admin/realms/" + configManager.getKeyCloakRealm() + "/client-scopes/" + clientScope.getId() + "/protocol-mappers");
                keycloak.proxy(ProtocolMappersResource.class, clientURI).createMapper(protocolMappersList);
                logger.info("Added the protocol mappers {0} successfully", protocolMappersList);
            }
        }
        return keycloak.realm(configManager.getKeyCloakRealm()).clientScopes().findAll()
                .stream().filter(cs -> "idp_scope".equals(cs.getName())).findAny().orElse(null).getId();
    }

    private String getClientScopeName(String clientName) {
        return clientName + "_scope";
    }

    private List<ProtocolMapperRepresentation> getMappers(String clientName) {
        List<ProtocolMapperRepresentation> mappers = new ArrayList<>();
        mappers.add(getIdpEmailMapper());
        mappers.add(getGroupsClaimsMapper());
        mappers.add(getAudienceClaimMapper(clientName));
        return mappers;

    }

    /**
     * @return This mapper is required by Jenkins for logging in.
     * Jenkins should be configured to use the claim idp_email.
     * Because of this mapper, the filed idp_email will be added in JWT returned by Keycloak
     */
    private ProtocolMapperRepresentation getIdpEmailMapper() {
        ProtocolMapperRepresentation idpEmailMapper = new ProtocolMapperRepresentation();
        idpEmailMapper.setName("idp_email");
        idpEmailMapper.setProtocol("openid-connect");
        idpEmailMapper.setProtocolMapper("oidc-script-based-protocol-mapper");
        Map<String, String> config = new HashMap<>();
        config.put("userinfo.token.claim", "true");
        config.put("id.token.claim", "true");
        config.put("access.token.claim", "true");
        config.put("claim.name", "idp_email");
        config.put("jsonType.label", "String");
        config.put("script", "/**\n" +
                " * Available variables: \n" +
                " * user - the current user\n" +
                " * realm - the current realm\n" +
                " * token - the current token\n" +
                " * userSession - the current userSession\n" +
                " * keycloakSession - the current userSession\n" +
                " */\n" +
                "\n" +
                "\n" +
                "//insert your code here...\n" +
                "var email = user.getEmail();\n" +
                "var idp_email = email.substring(0 , email.indexOf('@'));\n" +
                "exports = idp_email;");
        idpEmailMapper.setConfig(config);
        return idpEmailMapper;
    }

    /**
     * @return This mapper will add a claim in JWT called `groups` which will be the list of the groups for which the user
     * has membership.
     */
    private ProtocolMapperRepresentation getGroupsClaimsMapper() {
        ProtocolMapperRepresentation groupsClaimsMapper = new ProtocolMapperRepresentation();
        groupsClaimsMapper.setName("groups-membership");
        groupsClaimsMapper.setProtocol("openid-connect");
        groupsClaimsMapper.setProtocolMapper("oidc-group-membership-mapper");
        Map<String, String> config = new HashMap<>();
        config.put("full.path", "true");
        config.put("id.token.claim", "true");
        config.put("access.token.claim", "true");
        config.put("claim.name", "groups");
        config.put("userinfo.token.claim", "true");
        groupsClaimsMapper.setConfig(config);
        return groupsClaimsMapper;
    }

    /**
     * @param clientName
     * @return This mapper will add a claim called `aud` with `clientName` as one of the audience.
     */
    private ProtocolMapperRepresentation getAudienceClaimMapper(String clientName) {
        ProtocolMapperRepresentation audienceClaimMapper = new ProtocolMapperRepresentation();
        audienceClaimMapper.setName("audience-claim");
        audienceClaimMapper.setProtocol("openid-connect");
        audienceClaimMapper.setProtocolMapper("oidc-audience-mapper");
        Map<String, String> config = new HashMap<>();
        config.put("included.client.audience", clientName);
        config.put("id.token.claim", "true");
        config.put("access.token.claim", "true");
        audienceClaimMapper.setConfig(config);
        return audienceClaimMapper;

    }


    /**
     * post constructor
     */
    @PostConstruct
    public void bootstrapKeycloak() {
        initKeycloak();
        try {
            logger.info("Configuring Keycloak");
            this.configureKeycloakClient("idp");
        } catch (URISyntaxException e) {
            logger.info("Error while creating the keycloak client", e);
        }
        this.addUser("idpadmin", "idpadmin@123", false, "INFOSYS");
    }
}
