/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.dataapi.services;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

import org.infy.entities.artifact.Artifact;
import org.infy.idp.dataapi.base.PostGreSqlDbContext;
import org.infy.idp.entities.packagecontent.PackageContent;
import org.infy.idp.utils.ConfigurationManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jtest.AppContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppContext.class)
public class EnvironmentDetailsTest {

	@Spy
	@InjectMocks
	private PostGreSqlDbContext postGreSqlDbContext;

	@Spy
	@Autowired
	private ConfigurationManager configurationManager;

	@InjectMocks
	private EnvironmentDetails testedObject;

	@Test(expected = Test.None.class)
	public void testInsertEnvironment() {
		try {
			testedObject.insertEnvironment("QA", 11);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Test(expected = Test.None.class)
	public void testInsertEnvironmentOwners() {
		try {
			testedObject.insertEnvironmentOwners(1, "idpadmin", "QA");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGetEnvironmentOwners() {
		List<String> list = null;
		try {
			list = testedObject.getEnvironmentOwners("idpadmin", "QA", "DemoAppT");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertNotNull(list);
		assertNotEquals(list.size(), 0);
	}

	@Test(expected = Test.None.class)
	public void testGetEnvironmentId() {
		int id = -1;
		try {
			id = testedObject.getEnvironmentId("QA", 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = Test.None.class)
	public void testGetPathCount() {
		int id = -1;
		try {
			id = testedObject.getPathCount(5);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void testGetArtifactList() {
		List<String> list = null;
		try {
			list = testedObject.getArtifactList(11, 1, "approved");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteEnvironmentOwners() {
		try {
			testedObject.deleteEnvironmentOwners(11);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetArtifactDetails() {
		Artifact artifact = null;
		try {
			artifact = testedObject.getArtifactDetails("JFrogTest_JFrogTest2");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetPackageContent() {
		PackageContent pkgContent = null;
		try {
			pkgContent = testedObject.getPackageContent("JFrogTest_JFrogTest2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetParentEnvId() {
		List<Integer> list = null;
		try {
			list = testedObject.getParentEnvId(11, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetFirstEnv() {
		List<String> list = null;
		try {
			list = testedObject.getFirstEnv(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInsertArtifact() {
		try {
			testedObject.insertArtifact(11, 1, "JFrogTest", "approved");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateArtifactStatus() {
		try {
			testedObject.updateArtifactStatus(11, 1, "JFrogTest", "approved");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetEnvCount() {
		int count = 0;
		try {
			count = testedObject.getEnvCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotEquals(0, count);
	}

	@Test
	public void testGetArtifactId() {
		int count = 0;
		try {
			count = testedObject.getArtifactId("JFrogTest1");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testInsertArtifactDetails() {
		try {
			testedObject.insertArtifactDetails(1, "approved", "Approved!!", "QA", "idpadmin");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

}
