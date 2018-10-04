/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/


package org.infy.idp.schedulerservice;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The Class EncryptionUtil.
 */
@Component
public class EncryptionUtil {

	/** The Constant LOGGER. */
	private static Logger logger= LoggerFactory.getLogger(EncryptionUtil.class);

	/**
	 * Generate salt.
	 *
	 * @return the string
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 */
	public static String generateSalt() throws NoSuchAlgorithmException {
		
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		
		return Arrays.toString(salt);

	}

	/**
	 * Encrypt.
	 *
	 * @param content
	 *            the content
	 * @return the string
	 */
	public static String encrypt(String content) {
		
		String encryptedContent = "";
		try {
			SecretKeySpec skeySpec = getSecret();
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			byte[] encrypted = cipher.doFinal(content.getBytes("UTF-8"));
			encryptedContent = new String(Base64.getEncoder().encode(encrypted));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException | UnsupportedEncodingException e) {
			logger.debug(e.getMessage());
		}
		
		return encryptedContent;
	}

	/**
	 * Decrypt.
	 *
	 * @param encryptedContent
	 *            the encrypted content
	 * @return the string
	 */
	public static String decrypt(String encryptedContent) {
		
		byte[] content = Base64.getDecoder().decode(encryptedContent.getBytes());
		String originalContent = "";
		try {
			Cipher cipher = Cipher.getInstance("AES");
			SecretKeySpec skeySpec = getSecret();
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			byte[] original = cipher.doFinal(content);
			originalContent = new String(original);

		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException
				| IllegalBlockSizeException | BadPaddingException e) {
			logger.debug(e.getMessage());
			
		}
		
		return originalContent;
	}

	/**
	 * Gets the secret.
	 *
	 * @return the secret
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	private static SecretKeySpec getSecret() throws UnsupportedEncodingException {
		
		SecretKeySpec skeySpec = new SecretKeySpec("ANic3W@yToEncryp".getBytes("UTF-8"), "AES");
		
		return skeySpec;
	}

}
