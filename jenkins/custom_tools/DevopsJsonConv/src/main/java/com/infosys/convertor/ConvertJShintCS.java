/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/

package com.infosys.convertor;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.infosys.json.CodeAnalysis;
import com.infosys.json.JsonClass;
import com.infosys.utilities.checkstyle.Checkstyle;
import com.infosys.utilities.checkstyle.Checkstyle.File.Error;

public class ConvertJShintCS {

	static final Logger logger = Logger.getLogger(ConvertJShintCS.class);

	private ConvertJShintCS() {
	}

	/**
	 * method to parse Jshintcs Reports
	 * 
	 * @param inputPath
	 * @param ruleToValue
	 * @param ca
	 */
	public static void convert(String inputPath, Map<String, String> ruleToValue, List<CodeAnalysis> ca) {

		try {

			File file = new File(inputPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(Checkstyle.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Checkstyle c = (Checkstyle) jaxbUnmarshaller.unmarshal(file);

			if (c.getFile() == null)
				return;

			iterateList(ruleToValue, c, ca);

			logger.info("JShint Report Converted Successfully..!!");

		} catch (Exception e) {
			logger.error("Conversion error for " + inputPath + "Error: " + e);
		}
	}

	private static void iterateList(Map<String, String> ruleToValue, Checkstyle c, List<CodeAnalysis> ca) {

		List<Checkstyle.File> list = c.getFile();

		for (Checkstyle.File f : list) {

			if (f.getError() == null)
				continue;

			List<Checkstyle.File.Error> error = f.getError();

			for (Checkstyle.File.Error e : error) {

				CodeAnalysis e1 = new CodeAnalysis();
				setLineFunc(e1, e);

				if (e.getSource() != null) {

					int num = e.getSource().lastIndexOf('.');
					String rule = e.getSource().substring(num + 1);
					e1.setruleName(rule);
					e1.setRecommendation((ruleToValue.get(rule)) == null ? "No description available at this time"
							: ruleToValue.get(rule));
				}

				e1.setMessage(e.getMessage());
				e1.setcategory("jshint");
				setSeverityFunc(e1, e);

				String id = f.getName();
				String[] fname = id.split("\\.js");
				String name = null;
				name = fname[0].replace("\\", "_");
				e1.setId(name);

				ca.add(e1);
			}
		}
	}

	private static void setSeverityFunc(CodeAnalysis e1, Error e) {

		if (e.getSeverity() != null && e.getSeverity().equalsIgnoreCase("error")) {
			e1.setSeverity("high");
		} else if (e.getSeverity() != null && e.getSeverity().toLowerCase().equalsIgnoreCase("warning")) {
			e1.setSeverity("medium");
		} else {
			e1.setSeverity("low");
		}
	}

	private static void setLineFunc(CodeAnalysis e1, Error e) {

		if (e.getLine() != null)
			e1.setLine(e.getLine().toString());
	}

	public static com.infosys.jsonschema.Checkstyle getCheckStyleCodeQuality(JsonClass jsonClass) {

		List<CodeAnalysis> caArr = jsonClass.getCodeAnalysis();

		if (caArr == null)
			return null;

		com.infosys.jsonschema.Checkstyle csCodeQualityObj = new com.infosys.jsonschema.Checkstyle();
		int major = 0;
		int minor = 0;
		int blocker = 0;
		int critical = 0;
		int info = 0;

		for (CodeAnalysis ca : caArr) {
			if (!ca.getcategory().equals("jshint"))
				continue;

			if (ca.getSeverity().equals("high"))
				major++;
			if (ca.getSeverity().equals("medium"))
				minor++;

		}

		csCodeQualityObj.setMajor(major);
		csCodeQualityObj.setMinor(minor);
		csCodeQualityObj.setBlocker(blocker);
		csCodeQualityObj.setCritical(critical);
		csCodeQualityObj.setInfo(info);

		return csCodeQualityObj;
	}

}
