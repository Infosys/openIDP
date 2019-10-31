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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

import com.infosys.json.JsonClass;
import com.infosys.jsonschema.CodeMetric;
import com.infosys.utilities.coberturajava.Coverage;

public class ConvertCoverage {
	private static final Logger logger = Logger.getLogger(ConvertCoverage.class);

	private ConvertCoverage() {
	}

	public static JsonClass convert(String inputPath, JsonClass jsonClass) {
		try {
			EditDocType.edit(inputPath);
			File file = new File(inputPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(Coverage.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Coverage c = (Coverage) jaxbUnmarshaller.unmarshal(file);
			List<CodeMetric> listCov = new ArrayList<>();
			if (!(c.getPackages() != null && c.getPackages().getPackage() != null))
				return jsonClass;
			List<Coverage.Packages.Package> p1 = c.getPackages().getPackage();
			for (Coverage.Packages.Package p2 : p1) {
				if (!(p2.getClasses() != null && p2.getClasses().getClazz() != null))
					return jsonClass;
				// Salman
				// iterate Classes
				iterateClasses(p2, listCov);
			}
			jsonClass.setCodeMetric(listCov);
			logger.info("Report Converted Successfully..!!");
			return jsonClass;
		} catch (Exception e) {
			logger.error("Conversion error for " + inputPath + "Error: " + e);
		}
		return jsonClass;
	}

	private static void iterateClasses(Coverage.Packages.Package p2, List<CodeMetric> listCov) {
		List<Coverage.Packages.Package.Classes.Class> cls1 = p2.getClasses().getClazz();
		for (Coverage.Packages.Package.Classes.Class cls2 : cls1) {
			CodeMetric c1 = getCodeMetricObject();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(roundOff(cls2.getComplexity()));
			stringBuilder.append("(11)");
			c1.setcyclomaticComplexity(stringBuilder.toString());
			c1.setID(cls2.getName().replace(".", "_"));
			c1.setcoverageMetric(roundOffFloat(cls2.getLineRate()));
			int flag = checkId(listCov, c1);
			if (flag == 0) {
				listCov.add(c1);
			}
		}
	}

	private static CodeMetric getCodeMetricObject() {
		return new CodeMetric();
	}

	private static double roundOffFloat(Float lineRate) {
		return Math.round(lineRate * 100.0) / 100.0;
	}

	private static String roundOff(Double num) {
		String val = num.toString();
		int decPtIndex = val.indexOf('.');
		// Appending "0" at the end if value is not upto 2 precision point
		if (val.substring(decPtIndex + 1).length() < 2)
			val += "0";
		return val.substring(0, val.indexOf('.') + 3);
	}

	private static int checkId(List<CodeMetric> listCov, CodeMetric c1) {
		int flag = 0;
		if (!listCov.isEmpty()) {
			for (CodeMetric i : listCov) {
				if (i.getID().equals(c1.getID())) {
					flag = 1;
					break;
				}
			}
		}
		return flag;
	}
}
