/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.dataapi;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.infy.idp.AppContext;
import org.infy.idp.jsonschema.SCMInfo;
import org.infy.idp.utils.ConfigurationManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*This test case is used for SCM information Test*/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class SCMInfoDLTest {

	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;
	
	@Spy
	@Autowired
	private ConfigurationManager configmanager;
	
	@InjectMocks
	private SCMInfoDL scmInfoDL;
	
	@Before
	public void setUp() throws Exception {

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
	public void insertSCMInfoTest() throws Throwable {
		try 
		{
			SCMInfo scmInfo=new SCMInfo();
			scmInfo.setCommitMessage("commitMessage");
			scmInfo.setGetAffectedPath("getAffectedPath");
			scmInfo.setId("id");
			scmInfo.setLastModified("string");
			scmInfo.setLastModifiedBy("id");
			scmInfo.setLatestFileVersion("id");
			scmInfo.setRemoteUrl("remoteUrl");
			 List<SCMInfo> scminfolist=new ArrayList<>();
			 scminfolist.add(scmInfo);
			Integer temp=scmInfoDL.insertSCMInfo(1, 1, scminfolist);
			assertEquals((Integer)1,temp);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
