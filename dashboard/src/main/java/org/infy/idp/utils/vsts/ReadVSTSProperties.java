/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.utils.vsts;

import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * The ReadIDPlatformProperties class is used to read the key-value pairs from
 * the IDPlatform.properties file
 * 
 */
public class ReadVSTSProperties {
	protected Logger logger=LoggerFactory.getLogger(ReadVSTSProperties.class);
	private String exceptionStr = "Exception thrown: ";
    private String serverURL;
    private String apiVersion;
    private String authorizationToken;
    private String customFieldName;
    private String proxyRequired;
    private String proxyip;
    private String proxyport;
    private String proxyuser;
    private String proxypass;
    private String projectName;
    private String daysRecordFetchMongo;
    private String domainStringForBug;
    private String domainInfornt;

    private String vstsUser;
    private String vstsPassword;

    /**
     * This method read the properties file and return the object of Properties
     * 
     * @return Properties object of Properties
     * @throws InvalidInputException
     *             if Exception occur while reading file
     */
    public Properties getPropertiesFile() {
    	Properties propertiesObj = null;
    	
        try {
        	logger.info("Method call initiated ");
        	
        	propertiesObj = new Properties();
            
            logger.info(ReadVSTSProperties.class.getResourceAsStream("/idpservicesVSTS.properties").toString());
            propertiesObj.load(ReadVSTSProperties.class.getResourceAsStream("/idpservicesVSTS.properties"));

            logger.info("Method executed successfully ");

        } catch (Exception eX) {

        	logger.error(exceptionStr , eX);
        }
       
        // Return the Properties object
        return propertiesObj;
    }
    public void setAllPropertyValues(){
    	ReadVSTSProperties properties = new ReadVSTSProperties();

		Properties prop = properties.getPropertiesFile();
		
		// set properties
		this.serverURL = prop.getProperty("serverURL");
		this.apiVersion = prop.getProperty("apiVersion");
		this.authorizationToken = prop.getProperty("authorizationToken");
		this.customFieldName = prop.getProperty("customFieldName");
		this.proxyRequired = prop.getProperty("proxyRequired");
		this.proxyip = prop.getProperty("proxyip");
		this.proxyport = prop.getProperty("proxyport");
		this.proxyuser = prop.getProperty("proxyuser");
		this.proxypass = prop.getProperty("proxypass");
		this.projectName = prop.getProperty("projectName");
		this.daysRecordFetchMongo = prop.getProperty("daysOfRecordtoUpdate");
		this.domainStringForBug = prop.getProperty("domainStringForBug");
		this.domainInfornt = prop.getProperty("domainInfornt");

		this.vstsUser=prop.getProperty("vstsUser");
		this.vstsPassword=prop.getProperty("vstsPassword");
		
    }
    // getters
    
	public String getServerURL() {
		return serverURL;
	}
	public String getProjectName() {
		return projectName;
	}
	public String getApiVersion() {
		return apiVersion;
	}
	public String getAuthorizationToken() {
		return authorizationToken;
	}
	public String getCustomFieldName() {
		return customFieldName;
	}
	public String getProxyRequired() {
		return proxyRequired;
	}
	public String getProxyip() {
		return proxyip;
	}
	public String getProxyport() {
		return proxyport;
	}
	public String getProxyuser() {
		return proxyuser;
	}
	public String getProxypass() {
		return proxypass;
	}
	public String getDaysRecordFetchMongo() {
		return daysRecordFetchMongo;
	}
	public String getDomainStringForBug() {
		return domainStringForBug;
	}
	public String getDomainInfornt() {
		return domainInfornt;
	}

	public String getVstsUser() {
		return vstsUser;
	}
	public void setVstsUser(String vstsUser) {
		this.vstsUser = vstsUser;
	}
	public String getVstsPassword() {
		return vstsPassword;
	}
	public void setVstsPassword(String vstsPassword) {
		this.vstsPassword = vstsPassword;
	}
    
}
