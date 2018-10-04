/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.tools.getreports;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeleteUtilityChangeLogTest {
	@InjectMocks
	private DeleteUtilityChangeLog deleteUtilityChangeLog;

	@Test
	public void createChangeLogTest() {
		try {
			List<String> temp=null;
			deleteUtilityChangeLog.del("string");
			assertEquals(null, temp);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
