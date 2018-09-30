/**
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.”
*
**/
package org.infy.subscription.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @param <T>
 */
public class StreamUtils<T> {
	private static final Logger logger = LoggerFactory.getLogger(StreamUtils.class);

	/**
	 * 
	 * @param input
	 * @return T
	 */
	public T convertStringToObject(String input) {
		byte [] b = Base64.getDecoder().decode(input.getBytes());
		ByteArrayInputStream bi = new ByteArrayInputStream(b);
		ObjectInputStream si;
		try {
			si = new ObjectInputStream(bi);
			return (T) si.readObject();
		} catch (IOException e) {
			logger.error("Exception in convertStringToObject", e);
		} catch (ClassNotFoundException e) {
			logger.error("Exception in type cast", e);
		}
		return null;

	}

	/**
	 * 
	 * @param input
	 * @return byte[]
	 */
	public byte[] convertObjectToByteArray(T input) {
		try {
			ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(dataStream);
			out.writeObject(input);
			out.flush();
			byte[] data = dataStream.toByteArray();
			out.close();
			return data;
		} catch (Exception ex) {
			logger.error("Exception in convertObjectToByteArray", ex);
		}
		return new byte [0];
	}

}
