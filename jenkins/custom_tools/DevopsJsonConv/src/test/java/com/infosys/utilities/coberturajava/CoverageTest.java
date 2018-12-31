
package com.infosys.utilities.coberturajava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.infosys.utilities.coberturajava.Coverage.Packages;
import com.infosys.utilities.coberturajava.Coverage.Packages.Package.Classes.Class.Lines.Line;
import com.infosys.utilities.coberturajava.Coverage.Packages.Package.Classes.Class.Methods.Method;
import com.infosys.utilities.coberturajava.Coverage.Sources;

public class CoverageTest {

	@Test
	public void testEquals()
	{
		Coverage coverage=new Coverage();
		Sources sources=new Sources();
		Packages packages=new Packages();
		
		
		coverage.setBranchesCovered(new Byte((byte) 1));
		coverage.setBranchesValid(new Byte((byte) 1));
		coverage.setComplexity((float)1);
		coverage.setBranchRate((float)1);
		coverage.setLineRate((float)1);
		coverage.setLinesCovered(new Byte((byte) 1));
		coverage.setLinesValid(new Byte((byte) 1));
		coverage.setTimestamp((long)1);
		coverage.setVersion("1");
		coverage.setPackages(packages);
		coverage.setSources(sources);
		
		assertEquals(new Byte((byte) 1),coverage.getBranchesCovered());
		assertEquals(new Byte((byte) 1),coverage.getBranchesValid());
		assertEquals((float)1, (float)coverage.getComplexity(),0.01);
		assertEquals((float)1, (float)coverage.getBranchRate(),0.01);
		assertEquals((float)1, (float)coverage.getLineRate(),0.01);
		assertEquals(new Byte((byte) 1),coverage.getLinesCovered());
		assertEquals(new Byte((byte) 1),coverage.getLinesValid());
		assertEquals((long)1, (long)coverage.getTimestamp());
		assertEquals("1", coverage.getVersion());
		assertEquals(null, coverage.getPackages()._package);
		assertEquals(null, coverage.getSources().source);
		
		Coverage.Packages.Package packageI=new Coverage.Packages.Package();
		Coverage.Packages.Package.Classes classes=new Coverage.Packages.Package.Classes();
		
		
		
		packageI.setBranchRate((float)1);
		packageI.setComplexity((float)1);
		packageI.setLineRate((float)1);
		packageI.setName("name");
		packageI.setClasses(classes);
		
		assertEquals((float)1, (float)packageI.getBranchRate(),0.01);
		assertEquals((float)1, (float)packageI.getComplexity(),0.01);
		assertEquals((float)1, (float)packageI.getLineRate(),0.01);
		assertEquals("name", packageI.getName());
		assertEquals(null,packageI.getClasses().clazz);
		
		Coverage.Packages.Package.Classes.Class clazz=new Coverage.Packages.Package.Classes.Class();
		Coverage.Packages.Package.Classes.Class.Methods methods=new Coverage.Packages.Package.Classes.Class.Methods();
		Coverage.Packages.Package.Classes.Class.Lines lines=new Coverage.Packages.Package.Classes.Class.Lines();
		
		
		clazz.setBranchRate((float)1);
		clazz.setComplexity(1.0);
		clazz.setFilename("name");
		clazz.setLineRate((float)1);
		clazz.setName("name");
		clazz.setLines(lines);
		clazz.setMethods(methods);
		
		assertEquals((float)1, (float)clazz.getBranchRate(),0.01);
		assertEquals(1.0, (double)clazz.getComplexity(),0.01);
		assertEquals("name", clazz.getFilename());
		assertEquals((float)1, (float)clazz.getLineRate(),0.01);
		assertEquals("name", clazz.getName());
		assertEquals(null,clazz.getLines().line);
		assertEquals(null,clazz.getMethods().method);
		
		Line line=new Line();
		
		line.setBranch("branch");
		line.setConditionCoverage("value");
		line.setHits(new Byte((byte) 1));
		line.setNumber(new Byte((byte) 1));
		
		assertEquals("branch",line.getBranch());
		assertEquals("value",line.getConditionCoverage());
		assertEquals(new Byte((byte) 1),line.getHits());
		assertEquals(new Byte((byte) 1),line.getNumber());
		assertEquals(0,line.getContent().size());
		
		Method method=new Method();
		Method.Lines mLines=new Method.Lines();
		
		method.setBranchRate((float)1);
		method.setLineRate((float)1);
		method.setName("name");
		method.setSignature("value");
		method.setLines(mLines);
		
		assertEquals((float)1, (float)method.getBranchRate(),0.01);
		assertEquals((float)1, (float)method.getLineRate(),0.01);
		assertEquals("name",method.getName());
		assertEquals("value",method.getSignature());
		assertEquals(null,method.getLines().line);
		
		Method.Lines.Line mline=new Method.Lines.Line();
		
		mline.setBranch("branch");
		mline.setConditionCoverage("value");
		mline.setHits(new Byte((byte) 1));
		mline.setNumber(new Byte((byte) 1));
		
		assertEquals("branch",mline.getBranch());
		assertEquals("value",mline.getConditionCoverage());
		assertEquals(new Byte((byte) 1),mline.getHits());
		assertEquals(new Byte((byte) 1),mline.getNumber());
		assertEquals(0,mline.getContent().size());
	}
	
	@Test
	public void testNotEquals()
	{
		Coverage coverage=new Coverage();
		Sources sources=new Sources();
		Packages packages=new Packages();
		
		
		coverage.setBranchesCovered(new Byte((byte) 1));
		coverage.setBranchesValid(new Byte((byte) 1));
		coverage.setComplexity((float)1);
		coverage.setBranchRate((float)1);
		coverage.setLineRate((float)1);
		coverage.setLinesCovered(new Byte((byte) 1));
		coverage.setLinesValid(new Byte((byte) 1));
		coverage.setTimestamp((long)1);
		coverage.setVersion("1");
		coverage.setPackages(packages);
		coverage.setSources(sources);
		
		assertNotEquals(new Byte((byte) 2),coverage.getBranchesCovered());
		assertNotEquals(new Byte((byte) 2),coverage.getBranchesValid());
		assertNotEquals((float)2, (float)coverage.getComplexity(),0.01);
		assertNotEquals((float)2, (float)coverage.getBranchRate(),0.01);
		assertNotEquals((float)2, (float)coverage.getLineRate(),0.01);
		assertNotEquals(new Byte((byte) 2),coverage.getLinesCovered());
		assertNotEquals(new Byte((byte) 2),coverage.getLinesValid());
		assertNotEquals((long)2, (long)coverage.getTimestamp());
		assertNotEquals("2", coverage.getVersion());
		assertNotEquals(2, coverage.getPackages()._package);
		assertNotEquals(2, coverage.getSources().source);
		
		Coverage.Packages.Package packageI=new Coverage.Packages.Package();
		Coverage.Packages.Package.Classes classes=new Coverage.Packages.Package.Classes();
		
		
		
		packageI.setBranchRate((float)1);
		packageI.setComplexity((float)1);
		packageI.setLineRate((float)1);
		packageI.setName("name");
		packageI.setClasses(classes);
		
		assertNotEquals((float)2, (float)packageI.getBranchRate(),0.01);
		assertNotEquals((float)2, (float)packageI.getComplexity(),0.01);
		assertNotEquals((float)2, (float)packageI.getLineRate(),0.01);
		assertNotEquals("name2", packageI.getName());
		assertNotEquals(2,packageI.getClasses().clazz);
		
		Coverage.Packages.Package.Classes.Class clazz=new Coverage.Packages.Package.Classes.Class();
		Coverage.Packages.Package.Classes.Class.Methods methods=new Coverage.Packages.Package.Classes.Class.Methods();
		Coverage.Packages.Package.Classes.Class.Lines lines=new Coverage.Packages.Package.Classes.Class.Lines();
		
		
		clazz.setBranchRate((float)1);
		clazz.setComplexity(1.0);
		clazz.setFilename("name");
		clazz.setLineRate((float)1);
		clazz.setName("name");
		clazz.setLines(lines);
		clazz.setMethods(methods);
		
		assertNotEquals((float)2, (float)clazz.getBranchRate(),0.01);
		assertNotEquals(1.2, (double)clazz.getComplexity(),0.01);
		assertNotEquals("name2", clazz.getFilename());
		assertNotEquals((float)2, (float)clazz.getLineRate(),0.01);
		assertNotEquals("name2", clazz.getName());
		assertNotEquals(2,clazz.getLines().line);
		assertNotEquals(2,clazz.getMethods().method);
		
		Line line=new Line();
		
		line.setBranch("branch");
		line.setConditionCoverage("value");
		line.setHits(new Byte((byte) 1));
		line.setNumber(new Byte((byte) 1));
		
		assertNotEquals("branch2",line.getBranch());
		assertNotEquals("value2",line.getConditionCoverage());
		assertNotEquals(new Byte((byte) 2),line.getHits());
		assertNotEquals(new Byte((byte) 2),line.getNumber());
		assertNotEquals(2,line.getContent().size());
		
		Method method=new Method();
		Method.Lines mLines=new Method.Lines();
		
		method.setBranchRate((float)1);
		method.setLineRate((float)1);
		method.setName("name");
		method.setSignature("value");
		method.setLines(mLines);
		
		assertNotEquals((float)2, (float)method.getBranchRate(),0.01);
		assertNotEquals((float)2, (float)method.getLineRate(),0.01);
		assertNotEquals("name2",method.getName());
		assertNotEquals("value2",method.getSignature());
		assertNotEquals(1,method.getLines().line);
		
		Method.Lines.Line mline=new Method.Lines.Line();
		
		mline.setBranch("branch");
		mline.setConditionCoverage("value");
		mline.setHits(new Byte((byte) 1));
		mline.setNumber(new Byte((byte) 1));
		
		assertNotEquals("branch2",mline.getBranch());
		assertNotEquals("value2",mline.getConditionCoverage());
		assertNotEquals(new Byte((byte) 2),mline.getHits());
		assertNotEquals(new Byte((byte) 2),mline.getNumber());
		assertNotEquals(1,mline.getContent().size());
	}
}
