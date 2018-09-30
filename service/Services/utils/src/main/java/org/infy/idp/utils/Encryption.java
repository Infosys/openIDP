/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package org.infy.idp.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;

/**
 * Added for Encryption for SAP Application.
 * 
 * 
 */

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
 * The class Encryption contains methods for encryption and decryption of strings  
 *
 */
public class Encryption {
	private static final String UTF_8 = "UTF-8";
	private static final String KEYSTR = "Bardummy5Bardummy5"; // 128 bit key
	private static final String INIT_VECTOR = "RandomInitVector"; // 16 bytes
	private static final Logger logger = LoggerFactory.getLogger(Encryption.class);

	/**
	 * This method ciphers the data based on the cipher sent
	 *
	 * @param cipher ,data
	 * @return Result data, byte
	 */

	public static String encryptDecrypt(String value, String encrypt) {
		if (encrypt.equalsIgnoreCase("encrypt")) {
			return encrypt(value);
		} else {
			return decrypt(value);
		}
	}

	/**
	 * This method decrypts the ciphered data based on the cipher sent
	 *
	 * @param cipher
	 * @return Result data, byte
	 */
	public static String encrypt(String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(KEYSTR.getBytes(UTF_8), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());

			return Base64.encodeBase64String(encrypted);
		} catch (BadPaddingException | UnsupportedEncodingException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | NoSuchAlgorithmException
				| InvalidKeyException ex) {
			logger.error(ex.getMessage(), ex);
		}

		return null;
	}

	public static String decrypt(String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(KEYSTR.getBytes(UTF_8), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}

		return null;
	}

}
