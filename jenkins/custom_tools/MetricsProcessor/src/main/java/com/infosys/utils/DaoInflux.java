/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utils;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DaoInflux {
	private List<String> usedJsonFiles = new ArrayList<>();
	private List<String> unUsedJsonFiles = new ArrayList<>();
	private static final Logger logger = Logger.getLogger(DaoInflux.class);

	public void readmethod(String appName, String pipeline, String jsonFileLocation, String postgresServiceURL,
			String pgsunames, String pgspwd) throws IOException {
		List<String> filePath = getFilePath(jsonFileLocation);
		String temp1 = filePath.get(0);
		String temp2 = filePath.get(1);
		File dir1 = new File(temp1); // JSON source folder..
		File dir2 = new File(temp2); // processed JSON folder ..
		fileExtraction(dir1, dir2);
		List<String> fileNames = getFileName(unUsedJsonFiles, appName + "_" + pipeline);
		for (String filename : fileNames) {
			if (!usedJsonFiles.contains(filename)) {
				try {
					PGService p = new PGService();
					boolean value = p.postJSON(filename, postgresServiceURL, appName, pipeline, temp1, pgsunames,
							pgspwd);
					if (value)
						System.out.println("Json Posted to desired service");
					else
						System.out.println("Some error occured");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				processedFiles(dir2, filename);
			} else {
				logger.info("Data is already pushed to service.");
			}
		}
		logger.info("Json is uploaded into DataBase");
	}

	private List<String> getFileName(List<String> unUsedJsonFiles, String string) {
		List<String> fileNames = new ArrayList<>();
		for (String fileName : unUsedJsonFiles) {
			if (fileName.contains(string)) {
				fileNames.add(fileName);
			}
		}
		return fileNames;
	}

	private List<String> getFilePath(String jsonFileLocation) {
		List<String> filePath = new ArrayList<>();
		filePath.add(jsonFileLocation);
		filePath.add(jsonFileLocation + "/ProcessedFile");
		return filePath;
	}

	private void processedFiles(File dir2, String child) throws IOException {
		usedJsonFiles.add(child);
		String temp = dir2.getPath();
		temp = temp.replace("\\\\", "/");
		temp = temp.replace("\\", "/");
		File nf = new File(temp + "/" + child);
		nf.createNewFile();
	}

	private void fileExtraction(File dir1, File dir2) {
		File[] listofprocessedfiles;
		File[] listofallfiles;
		listofallfiles = dir1.listFiles();
		listofprocessedfiles = dir2.listFiles();
		for (File child : listofprocessedfiles) {
			usedJsonFiles.add(child.getName());
		}
		for (File child : listofallfiles) {
			unUsedJsonFiles.add(child.getName());
		}
	}
}
