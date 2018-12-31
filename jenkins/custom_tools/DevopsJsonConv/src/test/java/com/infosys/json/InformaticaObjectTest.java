package com.infosys.json;

import org.junit.Test;

import junit.framework.Assert;

public class InformaticaObjectTest {

	@Test
	public void foldernametest() {
		InformaticaObject io = new InformaticaObject();
		
		io.setFolderName("fn");
		
		Assert.assertEquals("fn", io.getFolderName());
	}
	
	@Test
	public void mappingtest() {
		InformaticaObject io = new InformaticaObject();
		
		io.setMapping(null);
		
		Assert.assertEquals(null, io.getMapping());
	}
	
	@Test
	public void Mapplettest() {
		InformaticaObject io = new InformaticaObject();
		
		io.setMapplet(null);
		
		Assert.assertEquals(null, io.getMapplet());
	}

	@Test
	public void  seqtest() {
		InformaticaObject io = new InformaticaObject();
		
		io.setSequence(null);
		
		Assert.assertEquals(null, io.getSequence());
	}

	@Test
	public void sessiontest() {
		InformaticaObject io = new InformaticaObject();
		
		io.setSession(null);
		
		Assert.assertEquals(null, io.getSession());
	}
	
	@Test
	public void sessionconfigttest() {
		InformaticaObject io = new InformaticaObject();
		
		io.setSessionconfig(null);
		
		Assert.assertEquals(null, io.getSessionconfig());
	}
	
	@Test
	public void sourcedeftest() {
		InformaticaObject io = new InformaticaObject();
		
		io.setSourceDefinition(null);
		
		Assert.assertEquals(null, io.getSourceDefinition());
	}
	
	@Test
	public void targettest() {
		InformaticaObject io = new InformaticaObject();
		
		io.setTargetDefinition(null);
		
		Assert.assertEquals(null, io.getTargetDefinition());
	}
	
	@Test
	public void flowtest() {
		InformaticaObject io = new InformaticaObject();
		
		io.setWorkflow(null);
		
		Assert.assertEquals(null, io.getWorkflow());
	}
	
	@Test
	public void worklettest() {
		InformaticaObject io = new InformaticaObject();
		
		io.setWorklet(null);
		
		Assert.assertEquals(null, io.getWorklet());
	}
}

