/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.cleanup;

import java.io.File;

import org.apache.log4j.Logger;

public class BackupFiles {

	private static final Logger logger = Logger.getLogger(BackupFiles.class);

	private BackupFiles() {
	}

	public static void backup(String srcPath, String destPath) {
		File folder1 = new File(srcPath);
		if (!(folder1.exists() && folder1.isDirectory()))
			return;

		File folder2 = new File(destPath);
		createDestFolder(folder2);
		if (!(folder2.exists() && folder2.isDirectory()))
			return;

		moveSrcFiles(folder1, srcPath, destPath);

		int lastIndex = srcPath.lastIndexOf('/') > srcPath.lastIndexOf("\\") ? srcPath.lastIndexOf('/') : srcPath.lastIndexOf("\\");
		String newSrcPath = srcPath.substring(0, lastIndex);
		newSrcPath += "/ChangeLogs";

		File folder3 = new File(srcPath);
		if (!(folder3.exists() && folder3.isDirectory()) || folder3.listFiles().length <= 0)
			return;
		
		File[] list2 = folder3.listFiles();
		moveFilesForChangeLogs(list2, newSrcPath, destPath);
	}

	private static void createDestFolder(File folder2) {
		if (!folder2.exists()) {
			folder2.mkdir();
		}
	}

	private static void moveSrcFiles(File folder1, String srcPath, String destPath) {
		File[] list1 = folder1.listFiles();
		if (list1.length > 0) {
			for (File i : list1) {
				if (i.getName().endsWith("json"))
					continue;
				String source = srcPath + "/" + i.getName();
				String destination = destPath + "/" + i.getName();
				Boolean status = MoveFiles.move(source, destination);
				if (status) {
					logger.info("File " + i.getName() + " successfully backuped..!!");
				}
			}
		}
	}

	private static void moveFilesForChangeLogs(File[] list2, String srcPath, String destPath) {
		for (File i : list2) {
			if (i.getName().endsWith("csv")) {
				String source = srcPath + "/" + i.getName();
				String destination = destPath + "/" + i.getName();
				Boolean status = MoveFiles.move(source, destination);
				if (status) {
					logger.info("File " + i.getName() + " successfully backuped..!!");
				}
			}
		}
	}

}
