package com.infosys.utilities.robot;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RobotTest {

	@Test
	public void testEquals()
	{
		Robot robot=new Robot();
		List<Suite> suite=new ArrayList<>();
		Errors errors=new Errors();
		Statistics statistics=new Statistics();
		
		robot.setErrors(errors);
		robot.setGenerated("value");
		robot.setGenerator("value");
		robot.setStatistics(statistics);
		robot.setSuite(suite);
		
		assertEquals("value",robot.getGenerated());
		assertEquals("value",robot.getGenerator());
	}
	
	@Test
	public void testNotEquals()
	{
		Robot robot=new Robot();
		List<Suite> suite=new ArrayList<>();
		Errors errors=new Errors();
		Statistics statistics=new Statistics();
		
		robot.setErrors(errors);
		robot.setGenerated("value");
		robot.setGenerator("value");
		robot.setStatistics(statistics);
		robot.setSuite(suite);
		
		assertNotEquals("value1",robot.getGenerated());
		assertNotEquals("value1",robot.getGenerator());
	}
	
}
