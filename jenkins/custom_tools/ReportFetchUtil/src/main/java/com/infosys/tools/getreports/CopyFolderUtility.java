/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.tools.getreports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class CopyFolderUtility {
	private static final Logger logger = Logger.getLogger(CopyFolderUtility.class);

	private CopyFolderUtility() {
	};

	public static Boolean copyFolder(File src, File dest, String name, String id, String appName) throws IOException {
		Boolean status = false;
		
		if (src.isFile()) {
			String fileName = src.getName();
			String fileN = fileName.substring(0, fileName.lastIndexOf('.'));
			String fileE = fileName.substring(fileName.lastIndexOf('.') + 1);
			if(fileN.toLowerCase().contains("lint") && fileN.toLowerCase().contains("result") && fileE.equalsIgnoreCase("xml"))
				fileN="lintreport";
			else if (fileN.toUpperCase().startsWith("TEST-") && src.getPath().toLowerCase().contains("reports"))
				fileN = "sui_" + fileN;
			else if (fileN.toUpperCase().startsWith("TEST-") && src.getPath().toLowerCase().contains("devdebug"))
				fileN = "android_" + fileN;
			else if (fileN.startsWith("JUnit_"))
				fileN = "goreportunit" + fileN;
			else if (fileN.toUpperCase().startsWith("TEST-"))
				fileN = "junit_" + fileN;
			else if (fileN.contains("TestResult"))
				fileN = "nunit_" + fileN;
			else if (fileN.toLowerCase().contains("result.txt"))
				fileN = "TSLint_test_" + fileN;
			else if (fileN.toUpperCase().startsWith("TEST-") && src.getPath().toLowerCase().contains("SeleniumJunit_Report"))
				fileN = "Selenium_report" + fileN;
			else if (fileN.toLowerCase().contains("junittestreport1.xml"))
				fileN = "JUnit_test_Angular_" + fileN;
			else if (fileN.toLowerCase().contains("coberturacoverage.xml"))
				fileN = "cobertura_coverage_Angular_" + fileN;
			else if (fileN.contains("Results") && fileE.equals("xml"))
				fileN= "HpUft_test";
			if (src.getPath().contains("test-output") && "testng-results".equals(fileN))
				fileN = "selenium-testng_" + fileN;
			else if ("testng-results".equals(fileN))
				fileN = "itops_" + fileN;
			try {
				File afile = new File(src.getCanonicalPath());
				List<String> files = new ArrayList<>();
				if (dest.exists() && dest.isDirectory()) {
					File[] folder = dest.listFiles();
					for (int i = 0; i < folder.length; i++) {
						if (folder[i].isFile() && (folder[i].getName().endsWith(fileE)
								&& folder[i].getName().startsWith(name + "_" + fileN))) {
							files.add(folder[i].getName());
						}
					}
					if (!files.isEmpty()) {
						for (String i : files) {
							for (File f : folder) {
								if (f.getName().equals(i)) {
									f.delete();
								}
							}
						}
					}
				}
				File bfile = null;
				String timeNew = new SimpleDateFormat("dd-MMM-yyyy hh-mm-ss-SSS a").format(new Date());
				if (id != null) {
					bfile = new File(dest + "/" + appName + "_" + fileN + "_id_" + id + "_" + timeNew + "." + fileE);
				} else {
					bfile = new File(dest + "/" + appName + "_" + fileN + "_" + timeNew + "." + fileE);
				}
				
				try(InputStream inStream = new FileInputStream(afile);
						OutputStream outStream = new FileOutputStream(bfile)){
					
					byte[] buffer = new byte[1024];
					int length;
					// copy the file content in bytes
					while ((length = inStream.read(buffer)) > 0) {
						outStream.write(buffer, 0, length);
					}
					
				}
				
				
				boolean status1;
				status1 = afile.delete();
				if (status1) {
					logger.info(afile.getName() + " is deleted!");
				}
				//
				//
				status = true;
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				status = false;
			}
		}
		return status;
	}
}
