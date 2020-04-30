/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;



/**
 * 
 * class Utils provides methods for data type conversion
 *
 */
public class Utils {
	protected static Logger logger = LoggerFactory.getLogger(Utils.class);

	Utils() {

	}

	public static long convertDate(String dateval) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		long unixTime = 0L;
		try {
			Date date = new Date();
			if (StringUtils.isNotEmpty(dateval)) {
				date = dateFormat.parse(dateval);
			}
			unixTime = date.getTime();
		} catch (ParseException e) {
			logger.error("Exception in ConvertDate", e);

		}

		return unixTime;
	}

	public static Double convertString(String statusval) {
		logger.info("Conver to String");
		if (NumberUtils.isNumber(statusval)) {
			logger.info("val " + statusval + " long " + Double.parseDouble(statusval));
			return Double.parseDouble(statusval);
		}
		if ("SUCCESS".equals(statusval)) {
			return 1.0;
		}
		if (StringUtils.isNotEmpty(statusval) && statusval.equalsIgnoreCase("unstable")) {
			return -1.0;
		}
		return 0.0;
	}

	public static String getUserFromJWT(String jwtToken){
		DecodedJWT keycloakToken = JWT.decode(jwtToken);
		return keycloakToken.getClaim("user_name").asString();
		
    }
}
