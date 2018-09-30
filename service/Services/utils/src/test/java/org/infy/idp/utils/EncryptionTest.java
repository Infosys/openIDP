/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Encryption.class)
@RunWith(SpringRunner.class)
public class EncryptionTest {

	@Autowired
	Encryption encrypt;

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}


	@Test
	public void testEncrypt() {
		String result = Encryption.encrypt("content");
		assertEquals("/zhjEPPklcGrm5B8aehA1Q==", result);
	}

	@Test
	public void testDecrypt_forValidInput() {
		String result = Encryption.decrypt("/zhjEPPklcGrm5B8aehA1Q==");
		assertEquals("content", result);
	}

	@Test
	public void testDecrypt_forInvalidInput() {
		String result = Encryption.decrypt("contect");
		assertNull(result);
	}

}