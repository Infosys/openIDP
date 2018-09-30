/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = BaseResource.class)
@RunWith(SpringRunner.class)
public class BaseResourceTest {

	@Autowired
	private BaseResource testedObject;

	@Test
	public void testBaseResource() throws Throwable {
		testedObject = new BaseResource();
		assertNotNull(testedObject);
	}

}