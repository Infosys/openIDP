/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.config.utils;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Entity which contains all configuration details
 * @author Infosys
 */
@ConfigurationProperties
@Component
@SuppressWarnings("PMD.MissingStaticMethodInNonInstantiatableClass")
public class ConfigurationManager {
	
	private String authProvider;
    private String keyCloakBase;
    private String keyCloakRealm;
	private String keycloakusername;
	private String keycloakpassword;
    private String kafkahost;
	private String kafkaport;
	private String batchSize;
	private String driverClassName;
	private String url;
	private String postgresqlusername;
	private String postgresqlpassword;
	private String postgresqldatabase;
	private String postgresqlinitialsize;
	private String postgresqlschemaname;
	private String clientid;
	private String skipAuthentication;
	private String appsecret;
	private String appversion;
	private String ldapurl;
	private String ldapBase;
	private Integer accessTokenValidity;
	private Integer refreshTokenValidity;

	/**
	 * Private constructor
	 */
	private ConfigurationManager() {

	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getSkipAuthentication() {
		return skipAuthentication;
	}

	public void setSkipAuthentication(String skipAuthentication) {
		this.skipAuthentication = skipAuthentication;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getAppversion() {
		return appversion;
	}

	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	public String getLdapurl() {
		return ldapurl;
	}

	public void setLdapurl(String ldapurl) {
		this.ldapurl = ldapurl;
	}

	public String getLdapBase() {
		return ldapBase;
	}

	public void setLdapBase(String ldapBase) {
		this.ldapBase = ldapBase;
	}

	public Integer getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public void setAccessTokenValidity(Integer accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public Integer getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	public void setRefreshTokenValidity(Integer refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}
	
	public String getDriverClassName() {
    return driverClassName;
  }

  public void setDriverClassName(String driverClassName) {
    this.driverClassName = driverClassName;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPostgresqlusername() {
    return postgresqlusername;
  }

  public void setPostgresqlusername(String postgresqlusername) {
    this.postgresqlusername = postgresqlusername;
  }

  public String getPostgresqlpassword() {
    return postgresqlpassword;
  }

  public void setPostgresqlpassword(String postgresqlpassword) {
    this.postgresqlpassword = postgresqlpassword;
  }

  public String getPostgresqldatabase() {
    return postgresqldatabase;
  }

  public void setPostgresqldatabase(String postgresqldatabase) {
    this.postgresqldatabase = postgresqldatabase;
  }

  public String getPostgresqlinitialsize() {
    return postgresqlinitialsize;
  }
  
  public void setPostgresqlinitialsize(String postgresqlinitialsize) {
    this.postgresqlinitialsize = postgresqlinitialsize;
  }

  public String getBatchSize() {
    return batchSize;
  }

  public void setBatchSize(String batchSize) {
    this.batchSize = batchSize;
  }

  public String getKafkahost() {
    return kafkahost;
  }

  public void setKafkahost(String kafkahost) {
    this.kafkahost = kafkahost;
  }

  public String getKafkaport() {
    return kafkaport;
  }

  public void setKafkaport(String kafkaport) {
    this.kafkaport = kafkaport;
  }


  public String getPostgresqlschemaname() {
    return postgresqlschemaname;
  }

  public void setPostgresqlschemaname(String postgresqlschemaname) {
    this.postgresqlschemaname = postgresqlschemaname;
  }

  public String getKeyCloakBase() {
    return keyCloakBase;
  }

  public void setKeyCloakBase(String keyCloakBase) {
    this.keyCloakBase = keyCloakBase;
  }

  public String getKeyCloakRealm() {
    return keyCloakRealm;
  }

  public void setKeyCloakRealm(String keyCloakRealm) {
    this.keyCloakRealm = keyCloakRealm;
  }
  
  public String getKeycloakusername() {
    return keycloakusername;
  }

  public void setKeycloakusername(String keycloakusername) {
    this.keycloakusername = keycloakusername;
  }   
  
  public String getKeycloakpassword() {
    return keycloakpassword;
  }

  public void setKeycloakpassword(String keycloakpassword) {
    this.keycloakpassword = keycloakpassword;
  } 
  
  public String getAuthProvider() {
    return authProvider;
  }

  public void setAuthProvider(String authProvider) {
    this.authProvider = authProvider;
  }
}
