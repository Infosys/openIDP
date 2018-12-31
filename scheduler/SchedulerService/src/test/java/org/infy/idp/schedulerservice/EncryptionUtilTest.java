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
				"{\"applicationName\":\"TCM\",\"pipelineAdmins\":\"userName\",\"releaseManager\":\"dummyuser,userName\",\"ibmRQMServerDetails\":{},\"slavesDetails\":[{\"deploy\":\"on\",\"labels\":\"TCM_SLAVE\",\"slaveName\":\"TCM_Slave\",\"buildServerOS\":\"windows\",\"build\":\"on\",\"workspacePath\":\"D:/IDPWSNew\",\"test\":\"on\",\"slaveUsage\":\"both\",\"createNewSlave\":\"on\"},{\"deploy\":\"on\",\"labels\":\"FileNet_Slave\",\"slaveName\":\"fileNet1\",\"buildServerOS\":\"windows\",\"build\":\"on\",\"workspacePath\":\"\",\"test\":\"on\",\"slaveUsage\":\"both\",\"createNewSlave\":\"\"},{\"deploy\":\"on\",\"labels\":\"Informatica_Slave\",\"slaveName\":\"Informatica_Slave\",\"buildServerOS\":\"linux\",\"build\":\"on\",\"workspacePath\":\"/home/admin/slave\",\"test\":\"on\",\"slaveUsage\":\"both\",\"createNewSlave\":\"on\"}],\"virtualServiceServerDetails\":{},\"developers\":\"KrishnaKanth_BN,karthik_easwaran,jatin.bhatia03\",\"sapApplication\":\"off\",\"environmentOwnerDetails\":[{\"qa\":\"idpadmin01,dummyuser,userName\",\"environmentName\":\"MRE\",\"environmentOwners\":\"userName,aishwarya.chauhan,hardik.rathod01\",\"dBOwners\":\"userName\"},{\"qa\":\"dummyuser,userName\",\"environmentName\":\"DVP\",\"environmentOwners\":\"idpadmin01,userName,aishwarya.chauhan,idpadmin,saurabh.singh48\"},{\"qa\":\"dummyuser,userName\",\"environmentName\":\"EDP\",\"environmentOwners\":\"idpadmin01,userName\"},{\"qa\":\"dummyuser,userName\",\"environmentName\":\"SUP\",\"environmentOwners\":\"dummyuser,userName\"},{\"qa\":\"idpadmin01,userName\",\"environmentName\":\"INT\",\"environmentOwners\":\"idpadmin,KrishnaKanth_BN,saurabh.singh48,userName\"},{\"qa\":\"userName,idpadmin01,userName\",\"environmentName\":\"UAT\",\"environmentOwners\":\"idpadmin,chirag.sharma,saurabh.singh48,userName\"},{\"qa\":\"userName,idpadmin01\",\"environmentName\":\"TRG\",\"environmentOwners\":\"idpadmin01,userName,shivam.bhagat\"},{\"qa\":\"idpadmin01,jatin.bhatia03,userName\",\"environmentName\":\"PROD\",\"environmentOwners\":\"idpadmin,jatin.bhatia03,KrishnaKanth_BN,userName\"}],\"artifactToStage\":{\"artifactRepo\":{\"repoURL\":\"dummyNexus.domain.com:8081\",\"repoUsername\":\"admin\",\"repoPassword\":\"dummy\",\"repoName\":\"idp_Nexus\"},\"artifactRepoName\":\"nexus\"}}");
		assertNotNull(encrypt);

	}

	@Test
	public void testDecrypt() {
		String decrypt = encryptionUtil.decrypt(
				"1ieubNRhRV6WXshb7W7tIQ==");
		assertNotNull(decrypt);
	}

}
