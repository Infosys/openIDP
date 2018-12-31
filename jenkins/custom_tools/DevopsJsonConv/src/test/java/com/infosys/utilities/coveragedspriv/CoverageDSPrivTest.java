
package com.infosys.utilities.coveragedspriv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.infosys.utilities.coveragedspriv.CoverageDSPriv.Module;
import com.infosys.utilities.coveragedspriv.CoverageDSPriv.Module.NamespaceTable;

public class CoverageDSPrivTest {

	@Test
	public void testEquals()
	{
		CoverageDSPriv coverageDSPriv= new CoverageDSPriv();
		
		Module module=new Module();
		
		NamespaceTable namespaceTable=new NamespaceTable();
		
		namespaceTable.setBlocksCovered(new Byte((byte) 1));
		namespaceTable.setBlocksNotCovered((short)1);
		namespaceTable.setLinesCovered(new Byte((byte) 1));
		namespaceTable.setLinesNotCovered((short)1);
		namespaceTable.setLinesPartiallyCovered(new Byte((byte) 1));
		namespaceTable.setModuleName("name");
		namespaceTable.setNamespaceKeyName("name");
		namespaceTable.setNamespaceName("name");
		
		module.setBlocksCovered(new Byte((byte) 1));
		module.setBlocksNotCovered((short)1);
		module.setImageLinkTime(new Byte((byte) 1));
		module.setImageSize(1);
		module.setLinesCovered(new Byte((byte) 1));
		module.setLinesNotCovered((short)1);
		module.setLinesPartiallyCovered(new Byte((byte) 1));
		module.setModuleName("name");
		module.setNamespaceTable(namespaceTable);
		
		coverageDSPriv.setModule(module);
		
		assertEquals(0,coverageDSPriv.getSourceFileNames().size());
		
		assertEquals((byte) 1,coverageDSPriv.getModule().getBlocksCovered());
		assertEquals((short)1,coverageDSPriv.getModule().getBlocksNotCovered());
		assertEquals((byte) 1,coverageDSPriv.getModule().getImageLinkTime());
		assertEquals(1,coverageDSPriv.getModule().getImageSize());
		assertEquals((byte) 1,coverageDSPriv.getModule().getLinesCovered());
		assertEquals((short)1,coverageDSPriv.getModule().getLinesNotCovered());
		assertEquals((byte) 1,coverageDSPriv.getModule().getLinesPartiallyCovered());
		assertEquals("name",coverageDSPriv.getModule().getModuleName());
		
		assertEquals((byte) 1,coverageDSPriv.getModule().getNamespaceTable().getBlocksCovered());
		assertEquals((short)1,coverageDSPriv.getModule().getNamespaceTable().getBlocksNotCovered());
		assertEquals((byte) 1,coverageDSPriv.getModule().getNamespaceTable().getLinesCovered());
		assertEquals((short)1,coverageDSPriv.getModule().getNamespaceTable().getLinesNotCovered());
		assertEquals((byte) 1,coverageDSPriv.getModule().getNamespaceTable().getLinesPartiallyCovered());
		assertEquals("name",coverageDSPriv.getModule().getNamespaceTable().getModuleName());
		assertEquals("name",coverageDSPriv.getModule().getNamespaceTable().getNamespaceKeyName());
		assertEquals("name",coverageDSPriv.getModule().getNamespaceTable().getNamespaceName());
		assertEquals(0,coverageDSPriv.getModule().getNamespaceTable().getClazz().size());
		
		NamespaceTable.Class clazz=new NamespaceTable.Class();
		clazz.setBlocksCovered(new Byte((byte) 1));
		clazz.setBlocksNotCovered(new Byte((byte) 1));
		clazz.setClassKeyName("name");
		clazz.setClassName("name");
		clazz.setLinesCovered(new Byte((byte) 1));
		clazz.setLinesNotCovered(new Byte((byte) 1));
		clazz.setLinesPartiallyCovered(new Byte((byte) 1));
		clazz.setNamespaceKeyName("name");
		
		assertEquals((byte) 1,clazz.getBlocksCovered());
		assertEquals((byte) 1,clazz.getBlocksNotCovered());
		assertEquals("name",clazz.getClassKeyName());
		assertEquals("name",clazz.getClassName());
		assertEquals((byte) 1,clazz.getLinesCovered());
		assertEquals((byte) 1,clazz.getLinesNotCovered());
		assertEquals((byte) 1,clazz.getLinesPartiallyCovered());
		assertEquals("name",clazz.getNamespaceKeyName());
		assertEquals(0,clazz.getMethod().size());
		
		NamespaceTable.Class.Method method=new NamespaceTable.Class.Method();
		
		method.setBlocksCovered(new Byte((byte) 1));
		method.setBlocksNotCovered(new Byte((byte) 1));
		method.setLinesCovered(new Byte((byte) 1));
		method.setLinesNotCovered(new Byte((byte) 1));
		method.setLinesPartiallyCovered(new Byte((byte) 1));
		method.setMethodFullName("name");
		method.setMethodKeyName("name");
		method.setMethodName("name");
		
		assertEquals((byte) 1,method.getBlocksCovered());
		assertEquals((byte) 1,method.getBlocksNotCovered());
		assertEquals((byte) 1,method.getLinesCovered());
		assertEquals((byte) 1,method.getLinesNotCovered());
		assertEquals((byte) 1,method.getLinesPartiallyCovered());
		assertEquals("name",method.getMethodFullName());
		assertEquals("name",method.getMethodKeyName());
		assertEquals("name",method.getMethodName());
		assertEquals(0,method.getLines().size());
		
		NamespaceTable.Class.Method.Lines lines=new NamespaceTable.Class.Method.Lines();
		
		lines.setColEnd((short)1);
		lines.setColStart(new Byte((byte) 1));
		lines.setCoverage(new Byte((byte) 1));
		lines.setLineID((short)1);
		lines.setLnEnd(new Byte((byte) 1));
		lines.setLnStart(new Byte((byte) 1));
		lines.setSourceFileID(new Byte((byte) 1));
		
		assertEquals((short)1,lines.getColEnd());
		assertEquals((byte)1,lines.getColStart());
		assertEquals((byte)1,lines.getCoverage());
		assertEquals((short)1,lines.getLineID());
		assertEquals((byte)1,lines.getLnEnd());
		assertEquals((byte)1,lines.getLnStart());
		assertEquals((byte)1,lines.getSourceFileID());
	}
	
	@Test
	public void testNotEquals()
	{
		CoverageDSPriv coverageDSPriv= new CoverageDSPriv();
		
		Module module=new Module();
		
		NamespaceTable namespaceTable=new NamespaceTable();
		
		namespaceTable.setBlocksCovered(new Byte((byte) 1));
		namespaceTable.setBlocksNotCovered((short)1);
		namespaceTable.setLinesCovered(new Byte((byte) 1));
		namespaceTable.setLinesNotCovered((short)1);
		namespaceTable.setLinesPartiallyCovered(new Byte((byte) 1));
		namespaceTable.setModuleName("name");
		namespaceTable.setNamespaceKeyName("name");
		namespaceTable.setNamespaceName("name");
		
		module.setBlocksCovered(new Byte((byte) 1));
		module.setBlocksNotCovered((short)1);
		module.setImageLinkTime(new Byte((byte) 1));
		module.setImageSize(1);
		module.setLinesCovered(new Byte((byte) 1));
		module.setLinesNotCovered((short)1);
		module.setLinesPartiallyCovered(new Byte((byte) 1));
		module.setModuleName("name");
		module.setNamespaceTable(namespaceTable);
		
		coverageDSPriv.setModule(module);
		
		assertEquals(0,coverageDSPriv.getSourceFileNames().size());
		
		assertNotEquals((byte) 2,coverageDSPriv.getModule().getBlocksCovered());
		assertNotEquals((short)2,coverageDSPriv.getModule().getBlocksNotCovered());
		assertNotEquals((byte) 2,coverageDSPriv.getModule().getImageLinkTime());
		assertNotEquals(2,coverageDSPriv.getModule().getImageSize());
		assertNotEquals((byte) 2,coverageDSPriv.getModule().getLinesCovered());
		assertNotEquals((short)2,coverageDSPriv.getModule().getLinesNotCovered());
		assertNotEquals((byte) 2,coverageDSPriv.getModule().getLinesPartiallyCovered());
		assertNotEquals("name2",coverageDSPriv.getModule().getModuleName());
		
		assertNotEquals((byte) 2,coverageDSPriv.getModule().getNamespaceTable().getBlocksCovered());
		assertNotEquals((short)2,coverageDSPriv.getModule().getNamespaceTable().getBlocksNotCovered());
		assertNotEquals((byte) 2,coverageDSPriv.getModule().getNamespaceTable().getLinesCovered());
		assertNotEquals((short)2,coverageDSPriv.getModule().getNamespaceTable().getLinesNotCovered());
		assertNotEquals((byte) 2,coverageDSPriv.getModule().getNamespaceTable().getLinesPartiallyCovered());
		assertNotEquals("name2",coverageDSPriv.getModule().getNamespaceTable().getModuleName());
		assertNotEquals("name2",coverageDSPriv.getModule().getNamespaceTable().getNamespaceKeyName());
		assertNotEquals("name2",coverageDSPriv.getModule().getNamespaceTable().getNamespaceName());
		assertNotEquals(2,coverageDSPriv.getModule().getNamespaceTable().getClazz().size());
		
		NamespaceTable.Class clazz=new NamespaceTable.Class();
		clazz.setBlocksCovered(new Byte((byte) 1));
		clazz.setBlocksNotCovered(new Byte((byte) 1));
		clazz.setClassKeyName("name");
		clazz.setClassName("name");
		clazz.setLinesCovered(new Byte((byte) 1));
		clazz.setLinesNotCovered(new Byte((byte) 1));
		clazz.setLinesPartiallyCovered(new Byte((byte) 1));
		clazz.setNamespaceKeyName("name");
		
		assertNotEquals((byte) 2,clazz.getBlocksCovered());
		assertNotEquals((byte) 2,clazz.getBlocksNotCovered());
		assertNotEquals("name2",clazz.getClassKeyName());
		assertNotEquals("name2",clazz.getClassName());
		assertNotEquals((byte) 2,clazz.getLinesCovered());
		assertNotEquals((byte) 2,clazz.getLinesNotCovered());
		assertNotEquals((byte) 2,clazz.getLinesPartiallyCovered());
		assertNotEquals("name2",clazz.getNamespaceKeyName());
		assertNotEquals(2,clazz.getMethod().size());
		
		NamespaceTable.Class.Method method=new NamespaceTable.Class.Method();
		
		method.setBlocksCovered(new Byte((byte) 1));
		method.setBlocksNotCovered(new Byte((byte) 1));
		method.setLinesCovered(new Byte((byte) 1));
		method.setLinesNotCovered(new Byte((byte) 1));
		method.setLinesPartiallyCovered(new Byte((byte) 1));
		method.setMethodFullName("name");
		method.setMethodKeyName("name");
		method.setMethodName("name");
		
		assertNotEquals((byte) 2,method.getBlocksCovered());
		assertNotEquals((byte) 2,method.getBlocksNotCovered());
		assertNotEquals((byte) 2,method.getLinesCovered());
		assertNotEquals((byte) 2,method.getLinesNotCovered());
		assertNotEquals((byte) 2,method.getLinesPartiallyCovered());
		assertNotEquals("name2",method.getMethodFullName());
		assertNotEquals("name2",method.getMethodKeyName());
		assertNotEquals("name2",method.getMethodName());
		assertNotEquals(2,method.getLines().size());
		
		NamespaceTable.Class.Method.Lines lines=new NamespaceTable.Class.Method.Lines();
		
		lines.setColEnd((short)1);
		lines.setColStart(new Byte((byte) 1));
		lines.setCoverage(new Byte((byte) 1));
		lines.setLineID((short)1);
		lines.setLnEnd(new Byte((byte) 1));
		lines.setLnStart(new Byte((byte) 1));
		lines.setSourceFileID(new Byte((byte) 1));
		
		assertNotEquals((short)2,lines.getColEnd());
		assertNotEquals((byte)2,lines.getColStart());
		assertNotEquals((byte)2,lines.getCoverage());
		assertNotEquals((short)2,lines.getLineID());
		assertNotEquals((byte)2,lines.getLnEnd());
		assertNotEquals((byte)2,lines.getLnStart());
		assertNotEquals((byte)2,lines.getSourceFileID());
	}
}
