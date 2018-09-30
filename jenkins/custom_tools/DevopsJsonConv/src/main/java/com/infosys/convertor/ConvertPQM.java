/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

import com.infosys.json.JsonClass;
import com.infosys.jsonschema.CSVInfo;
import com.infosys.jsonschema.CodeMetric;
import com.infosys.utilities.csvparser.CSVParser;

public class ConvertPQM {
	private static final Logger logger = Logger.getLogger(ConvertPQM.class);

	private ConvertPQM() {
	}

	/**
	 * returns list of codemetrics object after parsing PQM report 
	 * @param inputPath
	 * @param pathToCsvDir
	 * @param jsonClass
	 * @param prefixForId
	 * @return
	 */
	public static List<CodeMetric> convert(String inputPath, String pathToCsvDir, JsonClass jsonClass, String prefixForId) {
		
		jsonClass.setCodeMetric(new ArrayList<CodeMetric>());
		List<CodeMetric> cov = new ArrayList<>();	
		HashMap<String, String> nsClassMapForDotNet = mapNsClass(pathToCsvDir.replaceAll("\\\\", "/"));
		try {
			List<CSVInfo> csv = new CSVParser().parse(inputPath);			

			if (!jsonClass.getCodeMetric().isEmpty()) {
				cov = jsonClass.getCodeMetric();
				for (CSVInfo c : csv)
					iterageCodeMatrics(cov, c, prefixForId, nsClassMapForDotNet);
			} else {
				cov = new ArrayList<>();
				for (CSVInfo c : csv) {
					CodeMetric c1 = getCodeMetricObject();
					c1.setcyclomaticComplexity(roundOff(c.getCc()) + "(11)");
					c1.setMaintainabilityIndex(roundOff(c.getMi()) + "(5)");
					c1.setDefectPronenessIndex(roundOff(c.getDp()) + "(0)");
					c1.setChangePronenessIndex(roundOff(c.getCp()) + "(0)");
					if (c.getName().contains("."))
						c1.setID(prefixForId + c.getName().replace(".", "_"));
					else if(nsClassMapForDotNet.containsKey(c.getName()) && !nsClassMapForDotNet.get(c.getName()).equals(""))
						c1.setID(prefixForId + nsClassMapForDotNet.get(c.getName()));
					else
						c1.setID(prefixForId + c.getName());
					if (c1.getID() != null)
						cov.add(c1);
				}
			}

			if (cov != null && cov.isEmpty())
				cov = null;
			jsonClass.setCodeMetric(cov);

			logger.info("Report Converted Successfully..!!");
			return cov;
		} catch (Exception e) {
			logger.error("Conversion error for " + inputPath + "Error: " + e + e.getStackTrace());
		}

		return cov;
	}
	
	private static CodeMetric getCodeMetricObject() {
		return new CodeMetric();
	}

	/**
	 * returns a map containing nsclass for dotnet
	 * @param pathToCsvDir
	 * @return
	 */
	private static HashMap<String, String> mapNsClass(String pathToCsvDir) {
		HashMap<String, String> nsClassMapForDotNet = new HashMap<>();
		if (new File(pathToCsvDir.split("/IDP_DevopsJSON_Integration/Jenkins_Reports")[0] + "/NamespaceClassMap.csv").isFile())
			nsClassMapForDotNet = readCsvFileInWs(pathToCsvDir.split("/IDP_DevopsJSON_Integration/Jenkins_Reports")[0] + "/NamespaceClassMap.csv");
		else if (new File(pathToCsvDir.split("\\\\IDP_DevopsJSON_Integration\\\\Jenkins_Reports")[0] + "/NamespaceClassMap.csv").isFile())
			nsClassMapForDotNet = readCsvFileInWs(pathToCsvDir.split("\\\\IDP_DevopsJSON_Integration\\\\Jenkins_Reports")[0] + "/NamespaceClassMap.csv");
		else 
			logger.info("NamespacecClassMap.csv in not required/available !!");
		return nsClassMapForDotNet;
	}
	
	/**
	 * returns a map containing nsclass for dotnet
	 * @param pathToCsvDir
	 * @return
	 */
	private static HashMap<String, String> readCsvFileInWs(String filePath) {
		HashMap<String, String> nsClassMapForDotNet = new HashMap<>();
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(filePath));
			while ((line = br.readLine()) != null) {
				String[] kv = line.split(cvsSplitBy);				
				if(kv.length > 2)
					nsClassMapForDotNet.put(kv[2], kv[1]);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return nsClassMapForDotNet;
	}

	
	
	private static String roundOff(String val) {
		int decPtIndex = val.indexOf('.');

		// Appending "0" at the end if value is not upto 2 precision point
		if (decPtIndex == -1)
			val += ".00";
		else if (val.substring(decPtIndex + 1).length() < 2)
			val += "0";

		return val.substring(0, val.indexOf('.') + 3);
	}

	private static void iterageCodeMatrics(List<CodeMetric> cov, CSVInfo c, String prefixForId, HashMap<String, String> nsClassMapForDotNet) {
		for (CodeMetric c1 : cov) {
			if (c1.getID().equals(c.getName().replace(".", "_"))) {
				c1.setcyclomaticComplexity(roundOff(c.getCc()) + "(11)");
				c1.setMaintainabilityIndex(roundOff(c.getMi()) + "(5)");
				c1.setDefectPronenessIndex(roundOff(c.getDp()) + "(0)");
				c1.setChangePronenessIndex(roundOff(c.getCp()) + "(0)");
				if (c.getName().contains("."))
					c1.setID(prefixForId + c.getName().replace(".", "_"));
				else if(nsClassMapForDotNet.containsKey(c.getName()) && !nsClassMapForDotNet.get(c.getName()).equals(""))
					c1.setID(prefixForId + nsClassMapForDotNet.get(c.getName()));
				else
					c1.setID(prefixForId + c.getName());			
			}
		}
	}

	public static boolean isDouble(String str) {
		return NumberUtils.isNumber(str);
	}
}
