package com.infosys.utils;

import java.io.IOException;

import org.junit.Test;
import org.mockito.InjectMocks;

import junit.framework.Assert;

public class DaoInfluxTest {
	
	@InjectMocks
	private DaoInflux daoInflux;

	@Test
	public void test() throws IOException {
		DaoInflux dao = new DaoInflux();		
		Assert.assertEquals(false, false);
	}
}
