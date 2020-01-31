/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.idp.connector.jenkins.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CliTest {
	
	@TestConfiguration
	static class CliTestContextConfiguration {
		
		@Bean
		public Cli cli() {
			return new Cli();
		}
	}
	
	@Autowired
	private Cli cli;
	
	@MockBean
	private ConfigurationManager configurationManager;
	
	
	// Unit Test
	
	@Test
	public void executeCommandTest() {
		List<String> command = new ArrayList<>();
		command.add("hostname");
		try {
			cli.executeCommand(command);
		} catch (IOException | InterruptedException e) {
			assertFalse("Process Execution Failed." , true);
		}
			
	}
	
	@Test
	public void getConnection_Test() {
		try {
			List<String> command = cli.getConnection();
			if(command.contains("-jar") && command.contains("-auth"))
				assertTrue(true);
			else
				assertFalse(true);
		} catch (IOException e) {
			assertFalse("Download jenkins-cli.jar file failed.", true);
		}
	}
	
	// Integration Test
	
	@Test
	public void downloadFileTest() {
	
		Mockito.when(configurationManager.getJenkinsurl()).thenReturn("http://serverqltyrh06/jenkins");
		try {
			cli.downloadFile();
		} catch (IOException e) {
			assertFalse("Download jenkins-cli.jar file failed.", true);
		}
	}

}
