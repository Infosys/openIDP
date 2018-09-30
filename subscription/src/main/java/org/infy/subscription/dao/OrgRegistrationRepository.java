/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.subscription.dao;


import java.util.List;

import org.infy.subscription.entities.models.org.OrgInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Infosys
 *
 */
public interface OrgRegistrationRepository extends JpaRepository<OrgInfo, String> {

	
	 @Query("SELECT o FROM OrgInfo o")
	 public List<OrgInfo> findAllOrgList();
	 
	 @Transactional
	 @Modifying
	 @Query("UPDATE OrgInfo o set o.orgAdmin=:orgAdmin,o.domain=:domain where o.orgName=:orgName")
	 public int updateOrg(@Param("orgName") String orgName,@Param("orgAdmin") String orgAdmin,@Param("domain") String domain);
	 
	 
	 @Query("SELECT o.orgAdmin FROM OrgInfo o where o.orgName=:orgName")
	 public String findDomain(@Param("orgName") String orgName);
	
}
