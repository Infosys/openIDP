/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/


package org.infy.idp.configure;

import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Database context class for PostGreSQL databases
 */
@Component
public class PostGreSqlDbContext {
	
	@Autowired
	private ConfigurationManager configmanager;

	

	  private  String POSTGRES_CONN_URL;
	  private  String POSTGRES_USERNAME;
	  private  String POSTGRES_PASSWORD;
	  private  String POSTGRES_DRIVER_CLASS;
	  private  int POSTGRES_INITIAL_SIZE;
	

	private BasicDataSource connectionPool;
	private Logger logger = LoggerFactory.getLogger(PostGreSqlDbContext.class);

	
	
	private PostGreSqlDbContext(){
		
		
	}
	@PostConstruct
	private void init(){
		POSTGRES_CONN_URL = configmanager.getUrl();
		System.out.println("url"+configmanager.getUrl());
	    POSTGRES_USERNAME = configmanager.getPostgresqlusername();
	    POSTGRES_PASSWORD = configmanager.getPostgresqlpassword();
	    POSTGRES_DRIVER_CLASS = "org.postgresql.Driver";
	    POSTGRES_INITIAL_SIZE = Integer.parseInt(configmanager.getPostgresqlinitialsize());
	    connectionPool = createDataSource();
	}

	/**
	 * Returns new PostGreSQL connection.
	 *
	 * @return PostGreSQL Connection object
	 */
	public Connection getConnection() {
		try {
			Class.forName(POSTGRES_DRIVER_CLASS);
			return connectionPool.getConnection();
		} catch (ClassNotFoundException | SQLException exception) {
			System.out.println("exception"+exception);
			logger.error("Connection can not be created to postgres", exception);

		}
		return null;
	}

	private BasicDataSource createDataSource() {

		 connectionPool = new BasicDataSource();
		    connectionPool.setUsername(POSTGRES_USERNAME);
		    connectionPool.setPassword(POSTGRES_PASSWORD);
		    connectionPool.setDriverClassName(POSTGRES_DRIVER_CLASS);
		    connectionPool.setUrl(POSTGRES_CONN_URL);
		    connectionPool.setInitialSize(POSTGRES_INITIAL_SIZE);
		System.out.println("connectionpool"+connectionPool);
		return connectionPool;

	}
}
