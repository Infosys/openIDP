
package com.infosys.utilities.export;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class IncludeOptionsTest {

	@Test
	public void testEquals()
	{
		IncludeOptions includeOptions=new IncludeOptions();
		
		includeOptions.setIncludeAnnotations("value");
		includeOptions.setIncludeChangePreprocessorActions("value");
		includeOptions.setIncludeClasses("value");
		includeOptions.setIncludeCompoundDocs("value");
		includeOptions.setIncludeContentOfFolder("value");
		includeOptions.setIncludeCVLs("value");
		includeOptions.setIncludeDocs("value");
		includeOptions.setIncludeDocumentComments("value");
		includeOptions.setIncludeDocVers("value");
		includeOptions.setIncludeEventActions("value");
		includeOptions.setIncludeFollowers("value");
		includeOptions.setIncludeGlobalPropDefs("value");
		includeOptions.setIncludeLifeCycleActions("value");
		includeOptions.setIncludeLifeCyclePolicies("value");
		includeOptions.setIncludeMarkingSets("value");
		includeOptions.setIncludeObjects("value");
		includeOptions.setIncludeParentFolders("value");
		includeOptions.setIncludeRCRs("value");
		includeOptions.setIncludeRecommendations("value");
		includeOptions.setIncludeRelationshipsToContainingFolders("value");
		includeOptions.setIncludeSecurityObjects("value");
		includeOptions.setIncludeSubClasses("value");
		includeOptions.setIncludeSubFolders("value");
		includeOptions.setIncludeSubscriptions("value");
		includeOptions.setIncludeSystemClasses("value");
		includeOptions.setIncludeTags("value");
		includeOptions.setIncludeUserThumbnails("value");
		
		assertEquals("value",includeOptions.getIncludeAnnotations());
		assertEquals("value",includeOptions.getIncludeChangePreprocessorActions());
		assertEquals("value",includeOptions.getIncludeClasses());
		assertEquals("value",includeOptions.getIncludeCompoundDocs());
		assertEquals("value",includeOptions.getIncludeContentOfFolder());
		assertEquals("value",includeOptions.getIncludeCVLs());
		assertEquals("value",includeOptions.getIncludeDocs());
		assertEquals("value",includeOptions.getIncludeDocumentComments());
		assertEquals("value",includeOptions.getIncludeDocVers());
		assertEquals("value",includeOptions.getIncludeEventActions());
		assertEquals("value",includeOptions.getIncludeFollowers());
		assertEquals("value",includeOptions.getIncludeGlobalPropDefs());
		assertEquals("value",includeOptions.getIncludeLifeCycleActions());
		assertEquals("value",includeOptions.getIncludeLifeCyclePolicies());
		assertEquals("value",includeOptions.getIncludeMarkingSets());
		assertEquals("value",includeOptions.getIncludeObjects());
		assertEquals("value",includeOptions.getIncludeParentFolders());
		assertEquals("value",includeOptions.getIncludeRCRs());
		assertEquals("value",includeOptions.getIncludeRecommendations());
		assertEquals("value",includeOptions.getIncludeRelationshipsToContainingFolders());
		assertEquals("value",includeOptions.getIncludeSecurityObjects());
		assertEquals("value",includeOptions.getIncludeSubClasses());
		assertEquals("value",includeOptions.getIncludeSubFolders());
		assertEquals("value",includeOptions.getIncludeSubscriptions());
		assertEquals("value",includeOptions.getIncludeSystemClasses());
		assertEquals("value",includeOptions.getIncludeTags());
		assertEquals("value",includeOptions.getIncludeUserThumbnails());
	}
	
	@Test
	public void testNotEquals()
	{
		IncludeOptions includeOptions=new IncludeOptions();
		
		includeOptions.setIncludeAnnotations("value");
		includeOptions.setIncludeChangePreprocessorActions("value");
		includeOptions.setIncludeClasses("value");
		includeOptions.setIncludeCompoundDocs("value");
		includeOptions.setIncludeContentOfFolder("value");
		includeOptions.setIncludeCVLs("value");
		includeOptions.setIncludeDocs("value");
		includeOptions.setIncludeDocumentComments("value");
		includeOptions.setIncludeDocVers("value");
		includeOptions.setIncludeEventActions("value");
		includeOptions.setIncludeFollowers("value");
		includeOptions.setIncludeGlobalPropDefs("value");
		includeOptions.setIncludeLifeCycleActions("value");
		includeOptions.setIncludeLifeCyclePolicies("value");
		includeOptions.setIncludeMarkingSets("value");
		includeOptions.setIncludeObjects("value");
		includeOptions.setIncludeParentFolders("value");
		includeOptions.setIncludeRCRs("value");
		includeOptions.setIncludeRecommendations("value");
		includeOptions.setIncludeRelationshipsToContainingFolders("value");
		includeOptions.setIncludeSecurityObjects("value");
		includeOptions.setIncludeSubClasses("value");
		includeOptions.setIncludeSubFolders("value");
		includeOptions.setIncludeSubscriptions("value");
		includeOptions.setIncludeSystemClasses("value");
		includeOptions.setIncludeTags("value");
		includeOptions.setIncludeUserThumbnails("value");
		
		assertNotEquals("value",includeOptions.getIncludeAnnotations());
		assertNotEquals("value",includeOptions.getIncludeChangePreprocessorActions());
		assertNotEquals("value",includeOptions.getIncludeClasses());
		assertNotEquals("value",includeOptions.getIncludeCompoundDocs());
		assertNotEquals("value",includeOptions.getIncludeContentOfFolder());
		assertNotEquals("value",includeOptions.getIncludeCVLs());
		assertNotEquals("value",includeOptions.getIncludeDocs());
		assertNotEquals("value",includeOptions.getIncludeDocumentComments());
		assertNotEquals("value",includeOptions.getIncludeDocVers());
		assertNotEquals("value",includeOptions.getIncludeEventActions());
		assertNotEquals("value",includeOptions.getIncludeFollowers());
		assertNotEquals("value",includeOptions.getIncludeGlobalPropDefs());
		assertNotEquals("value",includeOptions.getIncludeLifeCycleActions());
		assertNotEquals("value",includeOptions.getIncludeLifeCyclePolicies());
		assertNotEquals("value",includeOptions.getIncludeMarkingSets());
		assertNotEquals("value",includeOptions.getIncludeObjects());
		assertNotEquals("value",includeOptions.getIncludeParentFolders());
		assertNotEquals("value",includeOptions.getIncludeRCRs());
		assertNotEquals("value",includeOptions.getIncludeRecommendations());
		assertNotEquals("value",includeOptions.getIncludeRelationshipsToContainingFolders());
		assertNotEquals("value",includeOptions.getIncludeSecurityObjects());
		assertNotEquals("value",includeOptions.getIncludeSubClasses());
		assertNotEquals("value",includeOptions.getIncludeSubFolders());
		assertNotEquals("value",includeOptions.getIncludeSubscriptions());
		assertNotEquals("value",includeOptions.getIncludeSystemClasses());
		assertNotEquals("value",includeOptions.getIncludeTags());
		assertNotEquals("value",includeOptions.getIncludeUserThumbnails());
	}
}
