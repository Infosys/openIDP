/**
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.”
*
**/
package org.infy.subscription.business;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class EncryptionManager {

	private static final int BUF_SIZE = 4096;
	private static final Logger logger = LoggerFactory.getLogger(EncryptionManager.class);
	private PublicKey publicKey;

	/**
	 * Constructor
	 */
	EncryptionManager() {
		try {
			byte[] pubdata = readAll(new ClassPathResource("pubkey.der").getInputStream());
			process(pubdata);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	/**
	 * 
	 * @param publicKey
	 * @param privateKey
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public void process(byte[] publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		if (publicKey == null) {
			throw new IllegalArgumentException("publicKey");
		}
		X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		this.publicKey = kf.generatePublic(spec);
	}

	/**
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static byte[] readAll(InputStream input) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUF_SIZE);
		byte[] buf = new byte[BUF_SIZE];
		int size;
		while ((size = input.read(buf)) != -1) {
			out.write(buf, 0, size);
		}
		return out.toByteArray();
	}

	/**
	 * 
	 * @param inputInfo
	 * @param signature
	 * @return
	 */
	public boolean verifySignature(byte[] inputInfo, byte[] signature) {
		try {
			Signature rsaSignature = Signature.getInstance("SHA1withRSA");
			rsaSignature.initVerify(publicKey);
			rsaSignature.update(inputInfo);
			return rsaSignature.verify(signature);
		} catch (InvalidKeyException e) {
			logger.error("Key is invalid ", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("No such Alogrithm implemenation", e);
		} catch (SignatureException e) {
			logger.error("Signature exception", e);
		}
		return false;
	}
}
