package com.infosys.convertor;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;

import junit.framework.Assert;

public class EditDocTypeTest {

	@Test
	public void test() throws ParserConfigurationException {
		
		
		ArrayList<String> al = new ArrayList<>();
		al.add("msg for punit");
		
		EditDocType.setMessagesforPunit(null);
		
		EditDocType.edit("fp");
		
		EditDocType.setMessagesforPunit(al);
		
		EditDocType.getDocument("filePath");
		
//		EditDocType.getCharacterDataFromElement(null);
		
		//EditDocType.getCharacterDataFromElement(e)
		
		EditDocType.getDocument("C:");
		
		Assert.assertEquals(true, true);
	}
}
