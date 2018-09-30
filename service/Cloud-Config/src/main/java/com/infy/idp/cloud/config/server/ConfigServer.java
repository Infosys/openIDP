/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infy.idp.cloud.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
/**
 * 
 * @author kruti.vyas
 *
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServer {
	/**
	 * 
	 * @param args
	 */
    public static void main(String[] args) {
        SpringApplication.run(ConfigServer.class, args);
    }
}
