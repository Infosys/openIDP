
package com.infosys.utilities.changeset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class JobDetailsTest {

	@Test
	public void testEquals()
	{
		JobDetails jobDetails=new JobDetails();
		
		jobDetails.setLastCompletedBuildId("lastCompletedBuildId");
		jobDetails.setLastFailedBuildId("lastFailedBuildId");
		jobDetails.setLastSuccessfulBuildId("lastSuccessfulBuildId");
		jobDetails.setLastUnstableBuildId("lastUnstableBuildId");
		jobDetails.setLastUnsuccessfulBuildId("lastUnsuccessfulBuildId");
		jobDetails.setNumber("number");
		jobDetails.setScore("score");
		jobDetails.setUrl("url");
		
		assertEquals("lastCompletedBuildId",jobDetails.getLastCompletedBuildId());
		assertEquals("lastFailedBuildId",jobDetails.getLastFailedBuildId());
		assertEquals("lastSuccessfulBuildId",jobDetails.getLastSuccessfulBuildId());
		assertEquals("lastUnstableBuildId",jobDetails.getLastUnstableBuildId());
		assertEquals("lastUnsuccessfulBuildId",jobDetails.getLastUnsuccessfulBuildId());
		assertEquals("number",jobDetails.getNumber());
		assertEquals("score",jobDetails.getScore());
		assertEquals("url",jobDetails.getUrl());
		
	}
	
	@Test
	public void testNotEquals()
	{
		JobDetails jobDetails=new JobDetails();
		
		jobDetails.setLastCompletedBuildId("lastCompletedBuildId");
		jobDetails.setLastFailedBuildId("lastFailedBuildId");
		jobDetails.setLastSuccessfulBuildId("lastSuccessfulBuildId");
		jobDetails.setLastUnstableBuildId("lastUnstableBuildId");
		jobDetails.setLastUnsuccessfulBuildId("lastUnsuccessfulBuildId");
		jobDetails.setNumber("number");
		jobDetails.setScore("score");
		jobDetails.setUrl("url");
		
		assertNotEquals("lastCompletedBuildId1",jobDetails.getLastCompletedBuildId());
		assertNotEquals("lastFailedBuildId1",jobDetails.getLastFailedBuildId());
		assertNotEquals("lastSuccessfulBuildId1",jobDetails.getLastSuccessfulBuildId());
		assertNotEquals("lastUnstableBuildId1",jobDetails.getLastUnstableBuildId());
		assertNotEquals("lastUnsuccessfulBuildId1",jobDetails.getLastUnsuccessfulBuildId());
		assertNotEquals("number1",jobDetails.getNumber());
		assertNotEquals("score1",jobDetails.getScore());
		assertNotEquals("url1",jobDetails.getUrl());
		
	}
}
