/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.subscription.dao;


import java.util.List;

import org.infy.subscription.entities.licencekeymanagement.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * 
 * @author Infosys
 *
 */
public interface ServiceRepository extends JpaRepository<Service, String> {
	/**
	 * 
	 * @return List<Object []>
	 */
	//@Query(nativeQuery = true, value="SELECT service_name,MAX(expiry_date) FROM tservices WHERE expiry_date>=current_date GROUP BY service_name")
	@Query(nativeQuery = true, value="SELECT service_name,MAX(expiry_date) FROM tservices WHERE expiry_date>=current_date AND org_name like :orgName GROUP BY service_name")
	List<Object[]> getAllActiveServices(@Param("orgName") String orgName);
	

}
