
package com.infosys.utilities.lint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.infosys.utilities.lint.Issues.Issue;
import com.infosys.utilities.lint.Issues.Issue.Location;

public class IssuesTest {

	@Test
	public void testEquals()
	{
		Issues issues=new Issues();
		
		
		issues.setBy("value");
		issues.setFormat(1);
		
		assertEquals("value",issues.getBy());
		assertEquals((Integer)1,issues.getFormat());
		assertEquals(0,issues.getIssue().size());
		
		Issue issue=new Issue();
		Location location=new Location();
		
		location.setColumn(1);
		location.setFile("value");
		location.setLine(1);
		
		issue.setCategory("value");
		issue.setErrorLine1("value");
		issue.setErrorLine2("value");
		issue.setExplanation("value");
		issue.setId("value");
		issue.setLocation(location);
		issue.setMessage("value");
		issue.setPriority(1);
		issue.setQuickfix("value");
		issue.setSeverity("value");
		issue.setSummary("value");
		issue.setUrl("value");
		issue.setUrls("value");
		
		assertEquals("value",issue.getCategory());
		assertEquals("value",issue.getErrorLine1());
		assertEquals("value",issue.getErrorLine2());
		assertEquals("value",issue.getExplanation());
		assertEquals("value",issue.getId());
		assertEquals("value",issue.getMessage());
		assertEquals((Integer)1,issue.getPriority());
		assertEquals("value",issue.getQuickfix());
		assertEquals("value",issue.getSeverity());
		assertEquals("value",issue.getSummary());
		assertEquals("value",issue.getUrl());
		assertEquals("value",issue.getUrls());
		assertEquals((Integer)1,issue.getLocation().getColumn());
		assertEquals("value",issue.getLocation().getFile());
		assertEquals((Integer)1,issue.getLocation().getLine());
		
	}
	
	@Test
	public void testNotEquals()
	{
		Issues issues=new Issues();
		
		
		issues.setBy("value");
		issues.setFormat(1);
		
		assertNotEquals("value",issues.getBy());
		assertNotEquals((Integer)1,issues.getFormat());
		assertNotEquals(0,issues.getIssue().size());
		
		Issue issue=new Issue();
		Location location=new Location();
		
		location.setColumn(1);
		location.setFile("value");
		location.setLine(1);
		
		issue.setCategory("value");
		issue.setErrorLine1("value");
		issue.setErrorLine2("value");
		issue.setExplanation("value");
		issue.setId("value");
		issue.setLocation(location);
		issue.setMessage("value");
		issue.setPriority(1);
		issue.setQuickfix("value");
		issue.setSeverity("value");
		issue.setSummary("value");
		issue.setUrl("value");
		issue.setUrls("value");
		
		assertNotEquals("value",issue.getCategory());
		assertNotEquals("value",issue.getErrorLine1());
		assertNotEquals("value",issue.getErrorLine2());
		assertNotEquals("value",issue.getExplanation());
		assertNotEquals("value",issue.getId());
		assertNotEquals("value",issue.getMessage());
		assertNotEquals((Integer)1,issue.getPriority());
		assertNotEquals("value",issue.getQuickfix());
		assertNotEquals("value",issue.getSeverity());
		assertNotEquals("value",issue.getSummary());
		assertNotEquals("value",issue.getUrl());
		assertNotEquals("value",issue.getUrls());
		assertNotEquals((Integer)1,issue.getLocation().getColumn());
		assertNotEquals("value",issue.getLocation().getFile());
		assertNotEquals((Integer)1,issue.getLocation().getLine());
		
	}
}
