
package com.infosys.utilities.export;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class SavedManifestTest {

	@Test
	public void testEquals()
	{
		SavedManifest savedManifest=new SavedManifest();
		
		
		savedManifest.setChoiceLists("choicelists");
		savedManifest.setFDMVersion("version");
		savedManifest.setPrimaryObjectStore("object");
		
		assertEquals("choicelists",savedManifest.getChoiceLists());
		assertEquals("version",savedManifest.getFDMVersion());
		assertEquals("object",savedManifest.getPrimaryObjectStore());
		assertEquals(0,savedManifest.getClassDefinitions().size());
		assertEquals(0,savedManifest.getDocuments().size());
		assertEquals(0,savedManifest.getEventActions().size());
		assertEquals(0,savedManifest.getFolders().size());
		assertEquals(0,savedManifest.getLifeCycleActions().size());
		assertEquals(0,savedManifest.getLifeCyclePolicies().size());
		assertEquals(0,savedManifest.getOthers().size());
		assertEquals(0,savedManifest.getPropertyTemplates().size());
		
		
		
	}
	
	@Test
	public void testNotEquals()
	{
		SavedManifest savedManifest=new SavedManifest();
		
		
		savedManifest.setChoiceLists("choicelists");
		savedManifest.setFDMVersion("version");
		savedManifest.setPrimaryObjectStore("object");
		
		assertNotEquals("choicelists",savedManifest.getChoiceLists());
		assertNotEquals("version",savedManifest.getFDMVersion());
		assertNotEquals("object",savedManifest.getPrimaryObjectStore());
		assertNotEquals(1,savedManifest.getClassDefinitions().size());
		assertNotEquals(1,savedManifest.getDocuments().size());
		assertNotEquals(1,savedManifest.getEventActions().size());
		assertNotEquals(1,savedManifest.getFolders().size());
		assertNotEquals(1,savedManifest.getLifeCycleActions().size());
		assertNotEquals(1,savedManifest.getLifeCyclePolicies().size());
		assertNotEquals(1,savedManifest.getOthers().size());
		assertNotEquals(1,savedManifest.getPropertyTemplates().size());
		
		
		
	}
}
