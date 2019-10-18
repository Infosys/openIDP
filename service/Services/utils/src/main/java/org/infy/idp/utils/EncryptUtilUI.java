/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;

/**
 *  The class EncryptUtilUI contains methods for encryption and decryption of data send to UI 
 * 
 * @author Infosys
 *
 */
public class EncryptUtilUI {

	private static final String ALGO = "AES/CBC/NoPadding";

	private static final byte[] keyForIDP = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09,
			0x0B, 0x0C, 0x0A, 0x0D, 0x0F, 0x0E };
	private static final byte[] ivValueForIDP = new byte[] { 0x0F, 0x0E, 0x0D, 0x0C, 0x0B, 0x0A, 0x09, 0x08, 0x07, 0x06,
			0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };
	private static final IvParameterSpec idpivspec = new IvParameterSpec(ivValueForIDP);
	private static final SecretKeySpec idpkeyspec = new SecretKeySpec(keyForIDP, "AES");

	final protected static char[] hexList = "0dummy56789ABCDEF".toCharArray();

	/**
	 * Constructor
	 */
	private EncryptUtilUI() {
		// constructor
	}

	/**
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String source) throws Exception {
		String paddedString = padString(source);
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, idpkeyspec, idpivspec);
		byte[] encVal = c.doFinal(paddedString.getBytes());
		String encryptedData = new String(Base64.getEncoder().encode(encVal));
		encryptedData = encryptedData.replaceAll("(?:\\r\\n|\\n\\r|\\n|\\r)", "");
		return encryptedData;
	}

	/**
	 * 
	 * @param encryptedData
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encryptedData) throws Exception {
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, idpkeyspec, idpivspec);
		byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedData = new String(decValue);
		return decryptedData;
	}

	/**
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		char[] hexCharacters = new char[bytes.length * 2];
		int v;
		for (int j = 0; j < bytes.length; j++) {
			v = bytes[j] & 0xFF;
			hexCharacters[j * 2] = hexList[v >>> 4];
			hexCharacters[j * 2 + 1] = hexList[v & 0x0F];
		}
		return new String(hexCharacters);
	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	private static String padString(String data) {
		char paddingChar = ' ';
		int size = 16;
		int x = data.length() % size;
		int padLength = size - x;
		String paddedData = data;
		for (int i = 0; i < padLength; i++) {
			paddedData += paddingChar;
		}
		return paddedData;
	}

}
