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

public class ConvertPmd {
	public static int major;
	public static int minor;
	public static int blocker;
	public static int critical;
	public static int info;
	private final static Logger logger = Logger.getLogger(ConvertPmd.class);

	private ConvertPmd() {
	}

	public static List<CodeAnalysis> convert(String inputPath, Map<String, String> ruleToValue,
			String prefixForId) {
		List<CodeAnalysis> ca = new ArrayList<>();
		try {
			EditDocType.edit(inputPath);
			File file = new File(inputPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(com.infosys.utilities.pmd.Pmd.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			com.infosys.utilities.pmd.Pmd c = (com.infosys.utilities.pmd.Pmd) jaxbUnmarshaller.unmarshal(file);
			if (c.getFile() == null) {
				logger.info("Report Converted Successfully.!!");
				return ca;
			}
			List<com.infosys.utilities.pmd.Pmd.File> files = c.getFile();
			for (com.infosys.utilities.pmd.Pmd.File f : files) {
				if (f.getViolation() == null)
					continue;
				iterateViolations(f, ruleToValue, ca, prefixForId);
			}
			logger.info("Report Converted Successfully..!!");
			return ca;
		} catch (Exception e) {
			logger.error("Conversion error for " + inputPath + "Error: " + e);
		}
		logger.info("Report Converted Successfully..!!");
		return ca;
	}

	private static void iterateViolations(com.infosys.utilities.pmd.Pmd.File f, Map<String, String> ruleToValue,
			List<CodeAnalysis> ca, String prefixForId) {
		List<com.infosys.utilities.pmd.Pmd.File.Violation> violations = f.getViolation();
		for (com.infosys.utilities.pmd.Pmd.File.Violation v : violations) {
			CodeAnalysis v1 = getCodeAnalysisObject();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(prefixForId);
			stringBuilder.append(v.getPackage().replace(".", "_"));
			if (v.getClazz() != null) {		
				stringBuilder.append("_");
				stringBuilder.append(v.getClazz().replace(".", "_"));
				v1.setId(stringBuilder.toString());
			} else {
				v1.setId(stringBuilder.toString());
			}
			if (v.getPriority() != null) {
				if (v.getPriority() <= 2) {
					v1.setSeverity("high");
					critical++;
				} else if (v.getPriority() == 3) {
					blocker++;
					v1.setSeverity("medium");
				} else if (v.getPriority() > 3) {
					major++;
					v1.setSeverity("low");
				}
			} else {
				v1.setSeverity("low");
			}
			v1.setMessage(v.getValue().trim());
			v1.setLine(v.getBeginline().toString());
			v1.setruleName(v.getRule());
			// Value is decided based on Rule
			v1.setRecommendation((ruleToValue.get(v.getRule())) == null ? "No description available at this time"
					: ruleToValue.get(v.getRule()));
			v1.setcategory("pmd");
			updateCA(v1, ca);
		}
	}

	private static CodeAnalysis getCodeAnalysisObject() {
		return new CodeAnalysis();
	}

	private static void updateCA(CodeAnalysis v1, List<CodeAnalysis> ca) {
		int flag = 0;
		if (!ca.isEmpty()) {
			for (CodeAnalysis i : ca) {
				if (i.getId().equals(v1.getId()) && i.getMessage().equals(v1.getMessage())) {
					flag = 1;
					break;
				}
			}
		}
		if (flag == 0) {
			ca.add(v1);
		}
	}

	public static List<Integer> getPmdSeverity() {
		List<Integer> severityCount = new ArrayList<Integer>();
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
