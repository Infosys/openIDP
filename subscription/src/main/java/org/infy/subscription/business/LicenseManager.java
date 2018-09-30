/**
*
* Copyright 2018 Infosys Ltd. 
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at 
* https://opensource.org/licenses/MIT.”
*
**/
package org.infy.subscription.business;

import java.util.Base64;
import java.util.Map;

import org.infy.subscription.entities.licencekeymanagement.License;
import org.infy.subscription.utils.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Infosys
 *
 */
@Component
public class LicenseManager {
	@Autowired
	private EncryptionManager encryptionManager;

	private static final Logger logger = LoggerFactory.getLogger(LicenseManager.class);

	/**
	 * 
	 * @param licensekey
	 * @return License
	 */
	public License readLicenseInfo(String licensekey) {

		try {

			License lic = new StreamUtils<License>().convertStringToObject(licensekey);
			String signature = lic.signature;

			if (signature == null) {
				logger.warn("No signature was found");
				return null;
			}
			byte[] sig = Base64.getDecoder().decode(signature.getBytes());

			byte[] data = new StreamUtils<Map<String, String>>().convertObjectToByteArray(lic.getProperty());

			if (!encryptionManager.verifySignature(data, sig)) {
				logger.warn("invalid license signature");
				return null;
			}

			return lic;
		} catch (Exception ex) {
			logger.error("Read License Exception ", ex);

		}
		return null;

	}

}
