/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.tools.getreports;

import java.io.File;

import org.apache.log4j.Logger;
/**
 * the class deleteutilitychnagelog has method to clean dest directory
 * @author shivam.bhagat
 *
 */
public class DeleteUtilityChangeLog {
	private DeleteUtilityChangeLog(){};
	
	private static final Logger logger = Logger.getLogger(DeleteUtilityChangeLog.class);
	public static void del(String destpath){
		File file = new File(destpath);
        File[] files = file.listFiles();
        if (files.length>0){
        	  for(File f: files){
                  if (f.getName().startsWith("IDP_DevopsJSON_Integration")){
                  	logger.info(f.getName());
                  	f.delete();
                  }
              }	
        }
      

	}

}
