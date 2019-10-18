/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.schedulerservice;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;

import org.infy.idp.configure.AppContext;
import org.infy.idp.configure.ConfigurationManager;
import org.infy.idp.configure.PostGreSqlDbContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class EncryptionUtilTest {

	@Spy
	@Autowired
	private ConfigurationManager configmanager;

	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@InjectMocks
	private EncryptionUtil encryptionUtil;

	@Before
	public void setup() {
		try {

			MockitoAnnotations.initMocks(this);
			Method postConstruct = PostGreSqlDbContext.class.getDeclaredMethod("init", null); // methodName,parameters
			postConstruct.setAccessible(true);
			postConstruct.invoke(postGreSqlDbContext);

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGenerateSalt() {
		try {
			String salt = encryptionUtil.generateSalt();
			assertNotNull(salt);
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
	}

	@Test
	public void testEncrypt() {
		String encrypt = encryptionUtil.encrypt(
				"{\"applicationName\":\"TCM\",\"pipelineAdmins\":\"dummyuser\",\"releaseManager\":\"dummyuser,dummyuser\",\"ibmRQMServerDetails\":{},\"slavesDetails\":[{\"deploy\":\"on\",\"labels\":\"TCM_SLAVE\",\"slaveName\":\"TCM_Slave\",\"buildServerOS\":\"windows\",\"build\":\"on\",\"workspacePath\":\"D:/IDPWSNew\",\"test\":\"on\",\"slaveUsage\":\"both\",\"createNewSlave\":\"on\"},{\"deploy\":\"on\",\"labels\":\"FileNet_Slave\",\"slaveName\":\"fileNet1\",\"buildServerOS\":\"windows\",\"build\":\"on\",\"workspacePath\":\"\",\"test\":\"on\",\"slaveUsage\":\"both\",\"createNewSlave\":\"\"},{\"deploy\":\"on\",\"labels\":\"Informatica_Slave\",\"slaveName\":\"Informatica_Slave\",\"buildServerOS\":\"linux\",\"build\":\"on\",\"workspacePath\":\"/home/admin/slave\",\"test\":\"on\",\"slaveUsage\":\"both\",\"createNewSlave\":\"on\"}],\"virtualServiceServerDetails\":{},\"developers\":\"dummyuser,dummyuser,dummyuser\",\"sapApplication\":\"off\",\"environmentOwnerDetails\":[{\"qa\":\"idpadmin,dummyuser,dummyuser\",\"environmentName\":\"MRE\",\"environmentOwners\":\"dummyuser,dummyuser,dummyuser\",\"dBOwners\":\"dummyuser\"},{\"qa\":\"dummyuser,dummyuser\",\"environmentName\":\"DVP\",\"environmentOwners\":\"idpadmin,dummyuser,dummyuser,idpadmin,dummyuser\"},{\"qa\":\"dummyuser,dummyuser\",\"environmentName\":\"EDP\",\"environmentOwners\":\"idpadmin,dummyuser\"},{\"qa\":\"dummyuser,dummyuser\",\"environmentName\":\"SUP\",\"environmentOwners\":\"dummyuser,dummyuser\"},{\"qa\":\"idpadmin,dummyuser\",\"environmentName\":\"INT\",\"environmentOwners\":\"idpadmin,dummyuser,dummyuser,dummyuser\"},{\"qa\":\"dummyuser,idpadmin,dummyuser\",\"environmentName\":\"UAT\",\"environmentOwners\":\"idpadmin,dummyuser,dummyuser,dummyuser\"},{\"qa\":\"dummyuser,idpadmin\",\"environmentName\":\"TRG\",\"environmentOwners\":\"idpadmin,dummyuser,dummyuser\"},{\"qa\":\"idpadmin,dummyuser,dummyuser\",\"environmentName\":\"PROD\",\"environmentOwners\":\"idpadmin,dummyuser,dummyuser,dummyuser\"}],\"artifactToStage\":{\"artifactRepo\":{\"repoURL\":\"xyz.com:8081\",\"repoUsername\":\"admin\",\"repoPassword\":\"dummy\",\"repoName\":\"idp_Nexus\"},\"artifactRepoName\":\"nexus\"}}");
		assertNotNull(encrypt);

	}

	@Test
	public void testDecrypt() {
		String decrypt = encryptionUtil.decrypt(
				"1ieubNRhRV6WXshb7W7tIQ==");
		assertNotNull(decrypt);
	}

}
