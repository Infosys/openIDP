
package com.infosys.utilities.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.infosys.utilities.imports.PairConfig.DataFiles;
import com.infosys.utilities.imports.PairConfig.DataFiles.DataFile;
import com.infosys.utilities.imports.PairConfig.DataFiles.DataFile.SubMap;
import com.infosys.utilities.imports.PairConfig.Destination;
import com.infosys.utilities.imports.PairConfig.Source;

public class PairConfigTest {

	@Test
	public void testEquals()
	{
		PairConfig pairConfig=new PairConfig();
		
		
		pairConfig.setDescription("value");
		pairConfig.setVersion("value");
		
		assertEquals(0,pairConfig.getDataFiles().size());
		assertEquals(0,pairConfig.getDestination().size());
		assertEquals(0,pairConfig.getSource().size());
		assertEquals("value",pairConfig.getDescription());
		assertEquals("value",pairConfig.getVersion());
		
		Source source=new Source();
		source.setName("value");
		assertEquals("value",source.getName());
		
		Destination destination=new Destination();
		destination.setName("value");
		assertEquals("value", destination.getName());
		
		DataFiles dataFiles=new DataFiles();
		assertEquals(0, dataFiles.getDataFile().size());
		
		DataFile dataFile=new DataFile();
		dataFile.setEntries("value");
		dataFile.setTimeStamp("value");
		dataFile.setType("value");
		dataFile.setUnmapped("value");
		dataFile.setUse("value");
		
		assertEquals("value", dataFile.getEntries());
		assertEquals("value", dataFile.getTimeStamp());
		assertEquals(0, dataFile.getSubMap().size());
		assertEquals("value", dataFile.getType());
		assertEquals("value", dataFile.getUnmapped());
		assertEquals("value", dataFile.getUse());
		
		SubMap subMap=new SubMap();
		subMap.setEntries("value");
		subMap.setType("value");
		subMap.setUnmapped("value");
		subMap.setUse("value");
		
		assertEquals("value", subMap.getEntries());
		assertEquals("value", subMap.getType());
		assertEquals("value", subMap.getUnmapped());
		assertEquals("value", subMap.getUse());
		
	}
	
	@Test
	public void testNotEquals()
	{
		PairConfig pairConfig=new PairConfig();
		
		
		pairConfig.setDescription("value");
		pairConfig.setVersion("value");
		
		assertNotEquals(0,pairConfig.getDataFiles().size());
		assertNotEquals(0,pairConfig.getDestination().size());
		assertNotEquals(0,pairConfig.getSource().size());
		assertNotEquals("value",pairConfig.getDescription());
		assertNotEquals("value",pairConfig.getVersion());
		
		Source source=new Source();
		source.setName("value");
		assertNotEquals("value",source.getName());
		
		Destination destination=new Destination();
		destination.setName("value");
		assertNotEquals("value", destination.getName());
		
		DataFiles dataFiles=new DataFiles();
		assertNotEquals(0, dataFiles.getDataFile().size());
		
		DataFile dataFile=new DataFile();
		dataFile.setEntries("value");
		dataFile.setTimeStamp("value");
		dataFile.setType("value");
		dataFile.setUnmapped("value");
		dataFile.setUse("value");
		
		assertNotEquals("value", dataFile.getEntries());
		assertNotEquals("value", dataFile.getTimeStamp());
		assertNotEquals(0, dataFile.getSubMap().size());
		assertNotEquals("value", dataFile.getType());
		assertNotEquals("value", dataFile.getUnmapped());
		assertNotEquals("value", dataFile.getUse());
		
		SubMap subMap=new SubMap();
		subMap.setEntries("value");
		subMap.setType("value");
		subMap.setUnmapped("value");
		subMap.setUse("value");
		
		assertNotEquals("value", subMap.getEntries());
		assertNotEquals("value", subMap.getType());
		assertNotEquals("value", subMap.getUnmapped());
		assertNotEquals("value", subMap.getUse());
		
	}
	
}
