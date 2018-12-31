
package com.infosys.utilities.changeset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.infosys.utilities.changeset.ChangeSet.Culprits;

public class ChangeSetTest {

	@Test
	public void testEquals()
	{
		ChangeSet changeSet=new ChangeSet();
		List<Culprits> culprits=new ArrayList<>();
		
		
		changeSet.setAppName("appName");
		changeSet.setBuildId("buildId");
		changeSet.setBuildStatus("buildStatus");
		changeSet.setDuration("duration");
		changeSet.setScmurl("scmurl");
		changeSet.setTimestamp("timestamp");
		changeSet.setCulprits(culprits);
		
		assertEquals("appName",changeSet.getAppName());
		assertEquals("buildId",changeSet.getBuildId());
		assertEquals("buildStatus",changeSet.getBuildStatus());
		assertEquals("duration",changeSet.getDuration());
		assertEquals("scmurl",changeSet.getScmurl());
		assertEquals("timestamp",changeSet.getTimestamp());
		assertEquals(0, changeSet.getCulprits().size());
		assertEquals(null, changeSet.getItem());
		
	}
	public void testNotEquals()
	{
		ChangeSet changeSet=new ChangeSet();
		List<Culprits> culprits=new ArrayList<>();
		
		
		changeSet.setAppName("appName");
		changeSet.setBuildId("buildId");
		changeSet.setBuildStatus("buildStatus");
		changeSet.setDuration("duration");
		changeSet.setScmurl("scmurl");
		changeSet.setTimestamp("timestamp");
		changeSet.setCulprits(culprits);
		
		assertNotEquals("appName1",changeSet.getAppName());
		assertNotEquals("buildId1",changeSet.getBuildId());
		assertNotEquals("buildStatus1",changeSet.getBuildStatus());
		assertNotEquals("duration1",changeSet.getDuration());
		assertNotEquals("scmurl1",changeSet.getScmurl());
		assertNotEquals("timestamp1",changeSet.getTimestamp());
		assertNotEquals(1, changeSet.getCulprits().size());
		
		
	}
}
