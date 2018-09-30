/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.subscription.utils;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class StreamUtilsTest {
	private StreamUtils<String> testObj = new StreamUtils<String>();

	@Test
	public void testconvertObjectToByteArray() {
		byte[] arr = testObj.convertObjectToByteArray("inputString");
		assertNotNull(arr);

	}

	@Test
	public void testOrgInfoNull() {
		String str = testObj.convertStringToObject("inputString");
		assertNotNull(str);
	}
}
