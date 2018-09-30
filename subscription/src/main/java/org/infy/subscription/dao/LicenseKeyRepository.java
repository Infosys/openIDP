/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.subscription.dao;

import java.util.List;

import org.infy.subscription.entities.licencekeymanagement.LicenseKey;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;


/**
 * 
 * @author kruti.vyas
 *
 */
public interface LicenseKeyRepository extends JpaRepository<LicenseKey, String> {
	/**
	 * 
	 * @param licenseKeyparam
	 * @return int
	 */
	
	@Query("SELECT COUNT(license) FROM LicenseKey WHERE license like :licenseKeyValue  AND expiryDate>=current_date")
	int countLicenseKeyAndActive(@Param("licenseKeyValue") byte[] licenseKeyparam);

	
	/**
	 * 
	 * @return List<String>
	 */
	@Query("SELECT l FROM LicenseKey l WHERE expiryDate>=current_date AND org_name like :orgName")
	List<LicenseKey> getAllActiveLicense(@Param("orgName") String orgName);

}
