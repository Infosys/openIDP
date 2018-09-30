/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.utils;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = EncryptionUtil.class)
@RunWith(SpringRunner.class)
public class EncryptionUtilTest {

	@Autowired
	EncryptionUtil encrypt;

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testGenerateSalt() {
		String result = "";
		try {
			result = EncryptionUtil.generateSalt();
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testEncrypt() {
		String result = EncryptionUtil.encrypt("content");
		assertEquals("83q6Paar2ZtDni4q9hmwiA==", result);
	}

	@Test
	public void testDecrypt_forInvalidInput() {
		String result = EncryptionUtil.decrypt("encryptedContent");
		assertEquals("", result);
	}

	@Test
	public void testDecrypt_forValidInput() {
		String result = EncryptionUtil.decrypt("83q6Paar2ZtDni4q9hmwiA==");
		assertEquals("content", result);
	}

}