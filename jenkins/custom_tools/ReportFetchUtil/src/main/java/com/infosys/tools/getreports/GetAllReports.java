/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.tools.getreports;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
public class GetAllReports {
	private static final Logger logger = Logger.getLogger(GetAllReports.class);
	private GetAllReports() {
	}

	

	public static void main(String[] args) throws IOException {
		
		BasicConfigurator.configure();
		SSLUtilities.trustAllHostnames();
		SSLUtilities.trustAllHttpsCertificates();
		logger.info("Report fetch util Frozen");
		String server = args[0];
		String username = args[1];
		String password = args[2];
		String time = new SimpleDateFormat("dd-MMM-yyyy hh-mm a").format(new Date());
		String rootPath = args[3].replaceAll("\\\\", "/") + "/";
		String destPath = makeDir(rootPath);
		String jobName = args[4];
		int jobIndex = jobName.indexOf('/');
		String appName = (jobIndex == -1) ? jobName : jobName.substring(0, jobIndex);
		List<String> list = new ArrayList<>();
		int count = 0;
		for (int j = 6; j < args.length; j++) {
			String[] utilityPath = args[j].split(":");
			String ext = utilityPath[1].substring(utilityPath[1].lastIndexOf('.') + 1);
			if (!"*".equals(ext)) {
				list.add(ext);
			} else {
				count = 1;
			}
		}
		ArrayList<String> extensions = new ArrayList<>(new HashSet<>(list));
		File subDir = new File(rootPath);
		if (!(subDir.isDirectory())) {
			return;
		}
		String buildId = CreateChangeLog.createChangeLog(server, username, password, jobName, destPath, time, appName);
		CodeCoverage.createcodeCoverage(server, username, password, jobName, destPath, time, appName);
		JobDetails.createJobDetails(server, username, password, jobName, destPath, time, appName);
		CreateBuildLog.createBuildLog(server, username, password, jobName, destPath, time, buildId, appName);
		String[] extensions1 = null;
		if (count == 0) {
			extensions1 = extensions.toArray(new String[0]);
			perform(args, destPath, jobName, time, buildId, subDir, extensions1, appName);
		} else {
			perform(args, destPath, jobName, time, buildId, subDir, extensions1, appName);
		}
		DeleteUtilityChangeLog.del(destPath);
	}

	public static String makeDir(String rootpath) {
		String path = rootpath + "IDP_DevopsJSON_Integration/Jenkins_Reports";
		if (new File(path).mkdirs()) {
			logger.info("Directory is created!");
		} else {
			logger.info("Directory already exists!");
		}
		return path;
	}

	public static Boolean copyReports(String srcPath, String destPath, String name, String time, String id,
			String appName) {
		Boolean status = false;
		File srcFile = new File(srcPath);
		File destFolder = new File(destPath);
		// make sure source exists
		if (!srcFile.exists()) {
			logger.error("File does not exist.");
			return status;
		} else {
			try {
				status = CopyFolderUtility.copyFolder(srcFile, destFolder, name, id, appName);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				return status;
			}
		}
		return status;
	}

	public static void copy(String srcPath, String destPath, String name, String time, String id, String utility,
			String appName) {
		Boolean status = copyReports(srcPath, destPath, name, time, id, appName);
		if (status) {
			logger.info(utility + " report successfully copied..!!");
		} else {
			logger.error("Error in copying report for " + utility);
		}
	}

	public static void perform(String[] args, String destPath, String name, String time, String buildId, File subDir,
			String[] extensions, String appName) throws IOException {
		List<File> files = (List<File>) FileUtils.listFiles(subDir, extensions, true);
		for (File i : files) {
			for (int j = 6; j < args.length; j++) {
				String[] utilityPath = args[j].split(":");
				String utility = utilityPath[0];
				String fileName = utilityPath[1];
				String srcPath = i.getCanonicalPath();
				
				String fileToBeCopied=i.getName().substring(i.getName().indexOf("//")+1);
				fileToBeCopied=fileToBeCopied.substring(0, fileToBeCopied.lastIndexOf("."));
				
				if ("put".equals(utility) && ((i.getName()).toLowerCase().contains("pythontest")
						&& i.getName().toLowerCase().endsWith(".xml"))) {
					copy(i.getCanonicalPath(), destPath, name, time, buildId, utility, appName);
				} else if ("test".equals(utility) && ((i.getName()).toUpperCase().startsWith("TEST-")
						|| i.getName().equalsIgnoreCase("testresult.xml"))) {
					copy(i.getCanonicalPath(), destPath, name, time, buildId, utility, appName);
				} else if ("tng".equals(utility) && (i.getName()).toLowerCase().contains("test-output")
						&& i.getName().equalsIgnoreCase("testng-results.xml")) {
					copy(i.getCanonicalPath(), destPath, name, time, buildId, utility, appName);
				} else if ("if".equals(utility) && (i.getName()).endsWith("Fastest.json")) {
					copy(i.getCanonicalPath(), destPath, name, time, buildId, utility, appName);
				} else if ("jcc".equals(utility) && (i.getName().toLowerCase().contains("jacoco"))) {
					copy(i.getCanonicalPath(), destPath, name, time, buildId, utility, appName);
				} else if ("alt".equals(utility) && (i.getName().toLowerCase().contains("lint")) && (i.getName().toLowerCase().contains("result"))) {
					copy(i.getCanonicalPath(), destPath, name, time, buildId, utility, appName);
				} else if (!fileName.startsWith(".")) {
					String fileN = fileName.substring(0, fileName.lastIndexOf('.'));
					String fileE = fileName.substring(fileName.lastIndexOf('.') + 1);
					if (fileName.equalsIgnoreCase(i.getName().toLowerCase())) {
						copy(srcPath, destPath, name, time, buildId, utility, appName);
					} else if ("*".equals(fileN) && ((i.getName().split("\\.")[1]).equals(fileN) || "*".equals(fileE)) || (fileN.endsWith("*") && !fileN.startsWith("*")&& (i.getName().contains(fileN.substring(0,fileN.indexOf("*")))) && !(fileToBeCopied.equals(fileN.substring(0,fileN.indexOf("*")))))) {
						
							copy(srcPath, destPath, name, time, buildId, utility, appName);
						
							
					}
				}
			}
		}
	}
}
