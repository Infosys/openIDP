/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi;


import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.apache.commons.dbcp2.BasicDataSource;
import org.infy.idp.utils.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Database context class for PostGreSQL databases
 */
@Component
public class IDPPostGreSqlDbContext {
	
	@Autowired
	private ConfigurationManager configmanager;

	

	private  String connectionURL;
	private  String username;
	

	private  String password;
	private  String driverClass;
	private  int initialSize;

	private BasicDataSource connectionPool;
	private Logger logger = LoggerFactory.getLogger(IDPPostGreSqlDbContext.class);

	
	
	private IDPPostGreSqlDbContext(){
		
		
	}
	@PostConstruct
	private void init(){
		connectionURL = configmanager.getIdpurl();
		username = configmanager.getIdppostgresqlusername();
		

		password = configmanager.getIdppostgresqlpassword();
		driverClass = "org.postgresql.Driver";
		initialSize = Integer.parseInt(configmanager.getIdppostgresqlinitialsize());
		connectionPool = createDataSource();
	}

	/**
	 * Returns new PostGreSQL connection.
	 *
	 * @return PostGreSQL Connection object
	 */
	public Connection getConnection() {
		try {
			Class.forName(driverClass);
			return connectionPool.getConnection();
		} catch (ClassNotFoundException | SQLException exception) {
			logger.error("Connection can not be created to postgres", exception);

		}
		return null;
	}

	private BasicDataSource createDataSource() {

		BasicDataSource tempconnectionPool = new BasicDataSource();
		tempconnectionPool.setUsername(username);
		tempconnectionPool.setPassword(password);
		tempconnectionPool.setDriverClassName(driverClass);
		tempconnectionPool.setUrl(connectionURL);
		tempconnectionPool.setInitialSize(initialSize);
		tempconnectionPool.setTestOnBorrow(true);
		tempconnectionPool.setTestWhileIdle(true);
		tempconnectionPool.setTimeBetweenEvictionRunsMillis(60000);
		tempconnectionPool.setValidationQuery("select 1");

		return tempconnectionPool;
	}
}
