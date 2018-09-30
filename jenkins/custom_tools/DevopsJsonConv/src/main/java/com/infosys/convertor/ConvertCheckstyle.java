/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.infosys.json.CodeAnalysis;
import com.infosys.utilities.checkstyle.Checkstyle;
import com.infosys.utilities.checkstyle.Checkstyle.File.Error;

/**
 * 
 * class ConvertCheckstyle has methods for parsing checkstyle reports
 *
 */
public class ConvertCheckstyle {
	private static int major;
	private static int minor;
	private static int blocker;
	private static int critical;
	private static int info;

	private static final Logger logger = Logger.getLogger(ConvertCheckstyle.class);

	private ConvertCheckstyle() {
	}

	/**
	 * returns list of codeanalysis after parsing reports
	 * 
	 * @param inputPath
	 * @param ruleToValue
	 * @param ca
	 * @param prefixForId
	 * @return
	 */
	public static List<CodeAnalysis> convert(String inputPath, Map<String, String> ruleToValue, List<CodeAnalysis> ca,
			String prefixForId) {
		try {
			EditDocType.edit(inputPath);
			File file = new File(inputPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(Checkstyle.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Checkstyle c = (Checkstyle) jaxbUnmarshaller.unmarshal(file);
			List<CodeAnalysis> localca;
			if (c.getFile() != null) {
				localca = iterateList(ruleToValue, c, ca, prefixForId);
				return localca;
			}

			logger.info("Report Converted Successfully..!!");

		} catch (Exception e) {
			logger.error("Conversion error for " + inputPath + "Error: " + e);
		}
		return ca;

	}

	private static List<CodeAnalysis> iterateList(Map<String, String> ruleToValue, Checkstyle c, List<CodeAnalysis> ca,
			String prefixForId) {
		List<CodeAnalysis> localca = new ArrayList<>();
		List<Checkstyle.File> list = c.getFile();
		for (Checkstyle.File f : list) {
			if (f.getError() == null)
				continue;
			List<Checkstyle.File.Error> error = f.getError();
			for (Checkstyle.File.Error e : error) {
				CodeAnalysis e1 = getCodeAnalysisObject();
				setLineFunc(e1, e);

				if (e.getSource() != null) {
					int num = e.getSource().lastIndexOf('.');
					String rule = e.getSource().substring(num + 1);
					e1.setruleName(rule);
					e1.setRecommendation((ruleToValue.get(rule)) == null ? "No description available at this time"
							: ruleToValue.get(rule));
				}
				e1.setMessage(e.getMessage());
				e1.setcategory("checkstyle");

				setSeverityFunc(e1, e);

				String id = f.getName();
				String[] fname = id.split("\\.java");
				String name = null;
				if (fname[0].contains("src\\main\\java\\"))
					name = fname[0].split("src.main.java.")[1].replace("\\", ".");
				else if (fname[0].contains("src\\"))
					name = fname[0].split("src.")[1].replace("\\", ".");
				else
					name = fname[0].replace("\\", ".");

				e1.setId(prefixForId + name.replace(".", "_"));

				localca = updateCA(ca, e1);
			}
		}

		return localca;
	}

	private static CodeAnalysis getCodeAnalysisObject() {
		return new CodeAnalysis();
	}

	private static void setSeverityFunc(CodeAnalysis e1, Error e) {
		if (e.getSeverity() != null && e.getSeverity().equalsIgnoreCase("error")) {
			major++;
			e1.setSeverity("high");

		} else if (e.getSeverity() != null && e.getSeverity().equalsIgnoreCase("warning")) {
			minor++;
			e1.setSeverity("medium");

		} else if (e.getSeverity() != null) {
			info++;
			e1.setSeverity("low");

		} else {
			e1.setSeverity("low");
		}

	}

	private static void setLineFunc(CodeAnalysis e1, Error e) {
		if (e.getLine() != null) {
			e1.setLine(e.getLine().toString());
		}
	}

	private static List<CodeAnalysis> updateCA(List<CodeAnalysis> ca, CodeAnalysis e1) {
		int flag = 0;
		if (!ca.isEmpty()) {
			for (CodeAnalysis i : ca) {
				if (i.getId().equals(e1.getId()) && i.getMessage().equals(e1.getMessage())) {
					flag = 1;
					break;
				}
			}
		}
		if (flag == 0) {

			ca.add(e1);
		}

		return ca;

	}

	public static List<Integer> getcheckStyleSeverity() {
		List<Integer> severityCount = new ArrayList<>();
		severityCount.add(critical);
		severityCount.add(blocker);
		severityCount.add(major);
		severityCount.add(minor);
		severityCount.add(info);
		critical = 0;
		blocker = 0;
		major = 0;
		minor = 0;
		info = 0;
		return severityCount;
	}
}