
package com.infosys.utilities.findbugs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.infosys.utilities.findbugs.BugCollection.BugCategory;
import com.infosys.utilities.findbugs.BugCollection.BugCode;
import com.infosys.utilities.findbugs.BugCollection.BugPattern;
import com.infosys.utilities.findbugs.BugCollection.Errors;
import com.infosys.utilities.findbugs.BugCollection.FindBugsSummary;
import com.infosys.utilities.findbugs.BugCollection.FindBugsSummary.FindBugsProfile;
import com.infosys.utilities.findbugs.BugCollection.Project;

public class BugCollectionTest {

	@Test
	public void testEquals()
	{
		BugCollection bugCollection=new BugCollection();
		
		Project project=new Project();
		project.setJar("jar");
		project.setProjectName("name");
		
		Errors errors=new Errors();
		errors.setErrors(new Byte((byte) 1));
		errors.setMissingClass(null);
		errors.setMissingClasses(new Byte((byte) 1));
		
		FindBugsSummary findBugsSummary=new FindBugsSummary();
		FindBugsProfile findBugsProfile=new FindBugsProfile();
		findBugsSummary.setAllocMbytes((float)1);
		findBugsSummary.setClockSeconds((float)1);
		findBugsSummary.setCpuSeconds((float)1);
		findBugsSummary.setFindBugsProfile(findBugsProfile);
		findBugsSummary.setGcSeconds((float)1);
		findBugsSummary.setJavaVersion("value");
		findBugsSummary.setNumPackages(new Byte((byte) 1));
		findBugsSummary.setPeakMbytes((float)1);
		findBugsSummary.setPriority1(new Byte((byte) 1));
		findBugsSummary.setPriority2(new Byte((byte) 1));
		findBugsSummary.setReferencedClasses(new Byte((byte) 1));
		findBugsSummary.setTimestamp("value");
		findBugsSummary.setTotalBugs(new Byte((byte) 1));
		findBugsSummary.setTotalClasses(new Byte((byte) 1));
		findBugsSummary.setTotalSize((short)1);
		findBugsSummary.setVmVersion("value");
		
		bugCollection.setAnalysisTimestamp((long)1);
		bugCollection.setClassFeatures("feature");
		bugCollection.setErrors(errors);
		bugCollection.setFindBugsSummary(findBugsSummary);
		bugCollection.setHistory("history");
		bugCollection.setProject(project);
		bugCollection.setRelease("release");
		bugCollection.setSequence(new Byte((byte) 1));
		bugCollection.setTimestamp((long)1);
		bugCollection.setVersion("version");
		
		assertEquals(1,(long)bugCollection.getAnalysisTimestamp());
		assertEquals("feature",bugCollection.getClassFeatures());
		assertEquals("history",bugCollection.getHistory());
		assertEquals("release",bugCollection.getRelease());
		assertEquals(new Byte((byte) 1),bugCollection.getSequence());
		assertEquals((long)1,(long)bugCollection.getTimestamp());
		assertEquals("version",bugCollection.getVersion());
		assertEquals(0,bugCollection.getBugCategory().size());
		assertEquals(0,bugCollection.getBugCode().size());
		assertEquals(0,bugCollection.getBugInstance().size());
		assertEquals(0,bugCollection.getBugPattern().size());
		assertEquals(new Byte((byte) 1),bugCollection.getErrors().getErrors());
		assertEquals(new Byte((byte) 1),bugCollection.getErrors().getErrors());
		assertEquals(0,bugCollection.getErrors().getMissingClass().size());
		assertEquals(1,(float)bugCollection.getFindBugsSummary().getAllocMbytes(),0.01);
		assertEquals(1,(float)bugCollection.getFindBugsSummary().getClockSeconds(),0.01);
		assertEquals(1,(float)bugCollection.getFindBugsSummary().getCpuSeconds(),0.01);
		assertEquals(null,bugCollection.getFindBugsSummary().getFindBugsProfile().classProfile);
		assertEquals(1,(float)bugCollection.getFindBugsSummary().getGcSeconds(),0.01);
		assertEquals("value",bugCollection.getFindBugsSummary().getJavaVersion());
		assertEquals(new Byte((byte) 1),bugCollection.getFindBugsSummary().getNumPackages());
		assertEquals(1,(float)bugCollection.getFindBugsSummary().getPeakMbytes(),0.01);
		assertEquals(new Byte((byte) 1),bugCollection.getFindBugsSummary().getPriority1());
		assertEquals(new Byte((byte) 1),bugCollection.getFindBugsSummary().getPriority2());
		assertEquals(new Byte((byte) 1),bugCollection.getFindBugsSummary().getReferencedClasses());
		assertEquals("value",bugCollection.getFindBugsSummary().getTimestamp());
		assertEquals(new Byte((byte) 1),bugCollection.getFindBugsSummary().getTotalBugs());
		assertEquals(new Byte((byte) 1),bugCollection.getFindBugsSummary().getTotalClasses());
		assertEquals(1,(short)bugCollection.getFindBugsSummary().getTotalSize());
		assertEquals("value",bugCollection.getFindBugsSummary().getVmVersion());
		assertEquals(0,bugCollection.getFindBugsSummary().getFileStats().size());
		assertEquals(0,bugCollection.getFindBugsSummary().getPackageStats().size());
		assertEquals("jar",bugCollection.getProject().getJar());
		assertEquals("name",bugCollection.getProject().getProjectName());
		
		BugInstance bugInstance=new BugInstance();
		bugInstance.setAbbrev("value");
		bugInstance.setCategory("value");
		bugInstance.setClazz(null);
		bugInstance.setCweid((short)1);
		bugInstance.setField(null);
		bugInstance.setInstanceHash("value");
		bugInstance.setInstanceOccurrenceMax(new Byte((byte) 1));
		bugInstance.setInstanceOccurrenceNum(new Byte((byte) 1));
		bugInstance.setInts(null);
		bugInstance.setLocalVariable("value");
		bugInstance.setLongMessage("value");
		bugInstance.setMethod(null);
		bugInstance.setPriority(new Byte((byte) 1));
		bugInstance.setProject(null);
		bugInstance.setRank(new Byte((byte) 1));
		bugInstance.setShortMessage("value");
		
		bugInstance.setSourceLine(null);
		bugInstance.setString(null);
		bugInstance.setType("value");
		bugInstance.setTypelist(null);
		
		assertEquals("value",bugInstance.getAbbrev());
		assertEquals("value",bugInstance.getCategory());
		assertEquals("value",bugInstance.getLocalVariable());
		assertEquals("value",bugInstance.getLongMessage());
		assertEquals("value",bugInstance.getShortMessage());
		assertEquals("value",bugInstance.getType());
		assertEquals("value",bugInstance.getInstanceHash());
		assertEquals(0,bugInstance.getClazz().size());
		assertEquals(0,bugInstance.getField().size());
		assertEquals(0,bugInstance.getInts().size());
		assertEquals(0,bugInstance.getMethod().size());
		assertEquals(0,bugInstance.getProject().size());
		assertEquals(0,bugInstance.getSourceLine().size());
		assertEquals(0,bugInstance.getString().size());
		assertEquals(0,bugInstance.getTypelist().size());
		assertEquals((short)1,(short)bugInstance.getCweid());
		assertEquals(new Byte((byte) 1),bugInstance.getInstanceOccurrenceMax());
		assertEquals(new Byte((byte) 1),bugInstance.getInstanceOccurrenceNum());
		assertEquals(new Byte((byte) 1),bugInstance.getPriority());
		assertEquals(new Byte((byte) 1),bugInstance.getRank());
		
		BugCategory bugCategory=new BugCategory();
		bugCategory.setCategory("value");
		bugCategory.setDescription("value");
		
		assertEquals("value",bugCategory.getCategory());
		assertEquals("value",bugCategory.getDescription());
		
		BugPattern bugPattern=new BugPattern();
		bugPattern.setAbbrev("value");
		bugPattern.setCategory("value");
		bugPattern.setCweid((short)1);
		bugPattern.setDetails("value");
		bugPattern.setShortDescription("value");
		bugPattern.setType("value");
		
		assertEquals("value",bugPattern.getAbbrev());
		assertEquals("value",bugPattern.getCategory());
		assertEquals("value",bugPattern.getDetails());
		assertEquals("value",bugPattern.getShortDescription());
		assertEquals("value",bugPattern.getType());
		assertEquals((short)1,(short)bugPattern.getCweid());
		
		BugCode bugCode=new BugCode();
		bugCode.setAbbrev("value");
		bugCode.setCweid((short)1);
		bugCode.setDescription("value");
		
		assertEquals("value",bugCode.getAbbrev());
		assertEquals((short)1,(short)bugCode.getCweid());
		assertEquals("value",bugCode.getDescription());
	}
	
	@Test
	public void testNotEquals()
	{
		BugCollection bugCollection=new BugCollection();
		
		Project project=new Project();
		project.setJar("jar");
		project.setProjectName("name");
		
		Errors errors=new Errors();
		errors.setErrors(new Byte((byte) 1));
		errors.setMissingClass(null);
		errors.setMissingClasses(new Byte((byte) 1));
		
		FindBugsSummary findBugsSummary=new FindBugsSummary();
		FindBugsProfile findBugsProfile=new FindBugsProfile();
		findBugsSummary.setAllocMbytes((float)1);
		findBugsSummary.setClockSeconds((float)1);
		findBugsSummary.setCpuSeconds((float)1);
		findBugsSummary.setFindBugsProfile(findBugsProfile);
		findBugsSummary.setGcSeconds((float)1);
		findBugsSummary.setJavaVersion("value");
		findBugsSummary.setNumPackages(new Byte((byte) 1));
		findBugsSummary.setPeakMbytes((float)1);
		findBugsSummary.setPriority1(new Byte((byte) 1));
		findBugsSummary.setPriority2(new Byte((byte) 1));
		findBugsSummary.setReferencedClasses(new Byte((byte) 1));
		findBugsSummary.setTimestamp("value");
		findBugsSummary.setTotalBugs(new Byte((byte) 1));
		findBugsSummary.setTotalClasses(new Byte((byte) 1));
		findBugsSummary.setTotalSize((short)1);
		findBugsSummary.setVmVersion("value");
		
		bugCollection.setAnalysisTimestamp((long)1);
		bugCollection.setClassFeatures("feature");
		bugCollection.setErrors(errors);
		bugCollection.setFindBugsSummary(findBugsSummary);
		bugCollection.setHistory("history");
		bugCollection.setProject(project);
		bugCollection.setRelease("release");
		bugCollection.setSequence(new Byte((byte) 1));
		bugCollection.setTimestamp((long)1);
		bugCollection.setVersion("version");
		
		assertNotEquals(2,(long)bugCollection.getAnalysisTimestamp());
		assertNotEquals("feature2",bugCollection.getClassFeatures());
		assertNotEquals("history2",bugCollection.getHistory());
		assertNotEquals("release2",bugCollection.getRelease());
		assertNotEquals(new Byte((byte) 2),bugCollection.getSequence());
		assertNotEquals((long)2,(long)bugCollection.getTimestamp());
		assertNotEquals("version2",bugCollection.getVersion());
		assertNotEquals(2,bugCollection.getBugCategory().size());
		assertNotEquals(2,bugCollection.getBugCode().size());
		assertNotEquals(2,bugCollection.getBugInstance().size());
		assertNotEquals(2,bugCollection.getBugPattern().size());
		assertNotEquals(new Byte((byte) 2),bugCollection.getErrors().getErrors());
		assertNotEquals(new Byte((byte) 21),bugCollection.getErrors().getErrors());
		assertNotEquals(22,bugCollection.getErrors().getMissingClass().size());
		assertNotEquals(2,(float)bugCollection.getFindBugsSummary().getAllocMbytes(),0.01);
		assertNotEquals(2,(float)bugCollection.getFindBugsSummary().getClockSeconds(),0.01);
		assertNotEquals(2,(float)bugCollection.getFindBugsSummary().getCpuSeconds(),0.01);
		assertNotEquals(2,bugCollection.getFindBugsSummary().getFindBugsProfile().classProfile);
		assertNotEquals(2,(float)bugCollection.getFindBugsSummary().getGcSeconds(),0.01);
		assertNotEquals("value2",bugCollection.getFindBugsSummary().getJavaVersion());
		assertNotEquals(new Byte((byte) 2),bugCollection.getFindBugsSummary().getNumPackages());
		assertNotEquals(2,(float)bugCollection.getFindBugsSummary().getPeakMbytes(),0.01);
		assertNotEquals(new Byte((byte) 2),bugCollection.getFindBugsSummary().getPriority1());
		assertNotEquals(new Byte((byte) 2),bugCollection.getFindBugsSummary().getPriority2());
		assertNotEquals(new Byte((byte) 2),bugCollection.getFindBugsSummary().getReferencedClasses());
		assertNotEquals("value2",bugCollection.getFindBugsSummary().getTimestamp());
		assertNotEquals(new Byte((byte) 2),bugCollection.getFindBugsSummary().getTotalBugs());
		assertNotEquals(new Byte((byte) 21),bugCollection.getFindBugsSummary().getTotalClasses());
		assertNotEquals(2,(short)bugCollection.getFindBugsSummary().getTotalSize());
		assertNotEquals("value2",bugCollection.getFindBugsSummary().getVmVersion());
		assertNotEquals(2,bugCollection.getFindBugsSummary().getFileStats().size());
		assertNotEquals(2,bugCollection.getFindBugsSummary().getPackageStats().size());
		assertNotEquals("jar2",bugCollection.getProject().getJar());
		assertNotEquals("name2",bugCollection.getProject().getProjectName());
		
		BugInstance bugInstance=new BugInstance();
		bugInstance.setAbbrev("value");
		bugInstance.setCategory("value");
		bugInstance.setClazz(null);
		bugInstance.setCweid((short)1);
		bugInstance.setField(null);
		bugInstance.setInstanceHash("value");
		bugInstance.setInstanceOccurrenceMax(new Byte((byte) 1));
		bugInstance.setInstanceOccurrenceNum(new Byte((byte) 1));
		bugInstance.setInts(null);
		bugInstance.setLocalVariable("value");
		bugInstance.setLongMessage("value");
		bugInstance.setMethod(null);
		bugInstance.setPriority(new Byte((byte) 1));
		bugInstance.setProject(null);
		bugInstance.setRank(new Byte((byte) 1));
		bugInstance.setShortMessage("value");
		
		bugInstance.setSourceLine(null);
		bugInstance.setString(null);
		bugInstance.setType("value");
		bugInstance.setTypelist(null);
		
		assertNotEquals("value2",bugInstance.getAbbrev());
		assertNotEquals("value2",bugInstance.getCategory());
		assertNotEquals("value2",bugInstance.getLocalVariable());
		assertNotEquals("value2",bugInstance.getLongMessage());
		assertNotEquals("value22",bugInstance.getShortMessage());
		assertNotEquals("value2",bugInstance.getType());
		assertNotEquals("value2",bugInstance.getInstanceHash());
		assertNotEquals(2,bugInstance.getClazz().size());
		assertNotEquals(2,bugInstance.getField().size());
		assertNotEquals(2,bugInstance.getInts().size());
		assertNotEquals(2,bugInstance.getMethod().size());
		assertNotEquals(2,bugInstance.getProject().size());
		assertNotEquals(2,bugInstance.getSourceLine().size());
		assertNotEquals(2,bugInstance.getString().size());
		assertNotEquals(2,bugInstance.getTypelist().size());
		assertNotEquals((short)2,(short)bugInstance.getCweid());
		assertNotEquals(new Byte((byte) 2),bugInstance.getInstanceOccurrenceMax());
		assertNotEquals(new Byte((byte) 2),bugInstance.getInstanceOccurrenceNum());
		assertNotEquals(new Byte((byte) 2),bugInstance.getPriority());
		assertNotEquals(new Byte((byte) 2),bugInstance.getRank());
		
		BugCategory bugCategory=new BugCategory();
		bugCategory.setCategory("value");
		bugCategory.setDescription("value");
		
		assertNotEquals("value2",bugCategory.getCategory());
		assertNotEquals("value22",bugCategory.getDescription());
		
		BugPattern bugPattern=new BugPattern();
		bugPattern.setAbbrev("value");
		bugPattern.setCategory("value");
		bugPattern.setCweid((short)1);
		bugPattern.setDetails("value");
		bugPattern.setShortDescription("value");
		bugPattern.setType("value");
		
		assertNotEquals("value2",bugPattern.getAbbrev());
		assertNotEquals("value2",bugPattern.getCategory());
		assertNotEquals("value2",bugPattern.getDetails());
		assertNotEquals("value2",bugPattern.getShortDescription());
		assertNotEquals("value2",bugPattern.getType());
		assertNotEquals((short)2,(short)bugPattern.getCweid());
		
		BugCode bugCode=new BugCode();
		bugCode.setAbbrev("value");
		bugCode.setCweid((short)1);
		bugCode.setDescription("value");
		
		assertNotEquals("value2",bugCode.getAbbrev());
		assertNotEquals((short)2,(short)bugCode.getCweid());
		assertNotEquals("value2",bugCode.getDescription());
	}
}
