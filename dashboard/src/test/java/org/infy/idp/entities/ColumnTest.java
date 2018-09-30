package org.infy.idp.entities;

import org.junit.Test;

import junit.framework.Assert;

public class ColumnTest {

	@Test
	public void texttest(){
		Column c = new Column();
		
		c.setText("infosys");
		
		Assert.assertEquals("infosys", c.getText());
	}
	
	@Test
	public void typetest(){
		Column c = new Column();
		
		c.setType("infosys");
		
		Assert.assertEquals("infosys", c.getType());
	}
}
