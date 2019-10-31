/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.utilities.csvparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.infosys.jsonschema.CSVInfo;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class CSVParser {
	private static final Logger logger = Logger.getLogger(CSVParser.class);

	public List<CSVInfo> parse(String inputpath) throws IOException, ClassNotFoundException {
		List<CSVInfo> csv = null;
		
		try {
			if (inputpath != null) {
				String csvOut = "./sampleCSV.txt";
				String pathToCsvFile = writeCsv(inputpath, csvOut);
				final File file = new File(inputpath);
				if (!file.exists()) {
					logger.error("The file you specified does not exist. path=" + pathToCsvFile);
				}
				Map<String, String> columnMapping = new HashMap<String, String>();
				columnMapping.put("Type", "packageName");
				columnMapping.put("CC", "CC");
				columnMapping.put("MI", "MI");
				columnMapping.put("CP", "CP");
				columnMapping.put("DP", "DP");
				HeaderColumnNameTranslateMappingStrategy<Bean> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
				strategy.setType(Bean.class);
				strategy.setColumnMapping(columnMapping);
				CsvToBean<Bean> csvToBean = new CsvToBean<>();
				
				try(CSVReader r = new CSVReader(new FileReader(pathToCsvFile), ',')){
					List<Bean> beanList = csvToBean.parse(strategy, r);
					csv = new ArrayList<>();
					for (Bean b : beanList) {
						CSVInfo c = getCSVInfoObject();
						c.setName(b.getPackageName());
						c.setCc(b.getCC() == null ? "0.00" : b.getCC());
						c.setMi(b.getMI() == null ? "0.00" : b.getMI());
						c.setDp(b.getDP() == null ? "0.00" : b.getDP());
						c.setCp(b.getCP() == null ? "0.00" : b.getCP());
						if (c.getName() != null && isDouble(c.getCc()) && isDouble(c.getMi()) && isDouble(c.getCp())
								&& isDouble(c.getDp())) {
							csv.add(c);
						}
					}
					File f = new File(csvOut);
					f.delete();
				}
				
			} else {
				logger.error("No path specified");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return csv;
	}

	private static CSVInfo getCSVInfoObject() {
		return new CSVInfo();
	}

	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String writeCsv(String csvIn, String csvOut) throws IOException {
		String path = null;
		CSVWriter writer = null;
		BufferedReader br = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(csvOut);
			writer = new CSVWriter(fw);
			List<String[]> database = new ArrayList<>();
			br = new BufferedReader(new FileReader(csvIn));
			String line;
			while ((line = br.readLine()) != null) {
				String[] column = line.split("\\t");
				for (int i = 0; i < column.length; i++) {
					if (column[i].contains("Type Names")) {
						column[i] = "Type";
						break;
					}
				}
				database.add(column);
			}
			writer.writeAll(database);
			path = csvOut;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (writer != null && br != null && fw != null) {
				writer.close();
				br.close();
				fw.close();
			}
		}
		return path;
	}
}
