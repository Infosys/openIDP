
package com.infosys.utilities.coberturago;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.infosys.utilities.coberturago.Coverage.Packages;
import com.infosys.utilities.coberturago.Coverage.Packages.Package.Classes;

public class CoverageTest {

	@Test
	public void testEquals()
	{
		Coverage coverage=new Coverage();
		List<Packages.Package> packageL=new ArrayList();
		
		Classes classes=new Classes();
		
		Packages.Package packageI=new Packages.Package();
		packageI.setBranchRate(1);
		packageI.setComplexity(1.0);
		packageI.setLineRate(1);
		packageI.setName("name");
		packageI.setClasses(classes);
		packageL.add(packageI);
		
		Packages packages=new Packages();
		
		packages.setPackage(packageL);
		
		coverage.setBranchRate(1.0);
		coverage.setLineRate(1.0);
		coverage.setPackages(packages);
		coverage.setTimestamp(BigDecimal.valueOf(100.0));
		coverage.setVersion("1");
		
		assertEquals((Double)1.0,coverage.getBranchRate());
		assertEquals((Double)1.0,coverage.getLineRate());
		assertEquals(BigDecimal.valueOf(100.0),coverage.getTimestamp());
		assertEquals("1",coverage.getVersion());
		assertEquals(1,coverage.getPackages().getPackage().size());
		assertEquals((Integer)1,coverage.getPackages().getPackage().get(0).getBranchRate());
		assertEquals((Double)1.0,coverage.getPackages().getPackage().get(0).getComplexity());
		assertEquals((Integer)1,coverage.getPackages().getPackage().get(0).getLineRate());
		assertEquals("name",coverage.getPackages().getPackage().get(0).getName());
		
		assertEquals(null,coverage.getPackages().getPackage().get(0).getClasses().clazz);
		
		Packages.Package.Classes.Class classI=new Packages.Package.Classes.Class();
		Packages.Package.Classes.Class.Methods methods=new Packages.Package.Classes.Class.Methods();
		Packages.Package.Classes.Class.Lines lines=new Packages.Package.Classes.Class.Lines();
		
		
		classI.setBranchRate(1);
		classI.setComplexity(1);
		classI.setFilename("name");
		classI.setLineRate(1);
		classI.setName("name");
		classI.setLines(lines);
		classI.setMethods(methods);
		
		assertEquals((Integer)1,classI.getBranchRate());
		assertEquals((Integer)1,classI.getComplexity());
		assertEquals("name",classI.getFilename());
		assertEquals((Integer)1,classI.getLineRate());
		assertEquals("name",classI.getName());
		assertEquals(null, classI.getLines().line);
		assertEquals(null,classI.getMethods().method);
	}
	
	@Test
	public void testNotEquals()
	{
		Coverage coverage=new Coverage();
		List<Packages.Package> packageL=new ArrayList();
		
		Classes classes=new Classes();
		
		Packages.Package packageI=new Packages.Package();
		packageI.setBranchRate(1);
		packageI.setComplexity(1.0);
		packageI.setLineRate(1);
		packageI.setName("name");
		packageI.setClasses(classes);
		packageL.add(packageI);
		
		Packages packages=new Packages();
		
		packages.setPackage(packageL);
		
		coverage.setBranchRate(1.0);
		coverage.setLineRate(1.0);
		coverage.setPackages(packages);
		coverage.setTimestamp(BigDecimal.valueOf(100.0));
		coverage.setVersion("1");
		
		assertNotEquals((Double)1.1,coverage.getBranchRate());
		assertNotEquals((Double)1.1,coverage.getLineRate());
		assertNotEquals(BigDecimal.valueOf(101.0),coverage.getTimestamp());
		assertNotEquals("11",coverage.getVersion());
		assertNotEquals(11,coverage.getPackages().getPackage().size());
		assertNotEquals((Integer)11,coverage.getPackages().getPackage().get(0).getBranchRate());
		assertNotEquals((Double)1.1,coverage.getPackages().getPackage().get(0).getComplexity());
		assertNotEquals((Integer)11,coverage.getPackages().getPackage().get(0).getLineRate());
		assertNotEquals("name1",coverage.getPackages().getPackage().get(0).getName());
		
		assertNotEquals(1,coverage.getPackages().getPackage().get(0).getClasses().clazz);
		
		Packages.Package.Classes.Class classI=new Packages.Package.Classes.Class();
		Packages.Package.Classes.Class.Methods methods=new Packages.Package.Classes.Class.Methods();
		Packages.Package.Classes.Class.Lines lines=new Packages.Package.Classes.Class.Lines();
		
		
		classI.setBranchRate(1);
		classI.setComplexity(1);
		classI.setFilename("name");
		classI.setLineRate(1);
		classI.setName("name");
		classI.setLines(lines);
		classI.setMethods(methods);
		
		assertNotEquals((Integer)11,classI.getBranchRate());
		assertNotEquals((Integer)11,classI.getComplexity());
		assertNotEquals("name1",classI.getFilename());
		assertNotEquals((Integer)11,classI.getLineRate());
		assertNotEquals("name1",classI.getName());
		assertNotEquals(1, classI.getLines().line);
		assertNotEquals(1,classI.getMethods().method);
	}
}
