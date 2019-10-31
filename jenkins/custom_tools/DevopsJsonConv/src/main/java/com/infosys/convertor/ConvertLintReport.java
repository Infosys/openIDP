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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.infosys.json.CodeAnalysis;
import com.infosys.json.CodeQuality;
import com.infosys.json.Lint;
import com.infosys.utilities.lint.Issues;

public class ConvertLintReport {
	

	private static int highViolations = 0;
	private static int lowViolations = 0;
	private static int mediumViolations = 0;
	private ConvertLintReport() {
	}
	public static void convert(String inputPath, Lint lint, CodeQuality cq, List<CodeAnalysis> ca) {
		EditDocType.edit(inputPath);
		File file = new File(inputPath);
		JAXBContext jaxbContext;
		Unmarshaller jaxbUnmarshaller;
		try {
			jaxbContext = JAXBContext.newInstance(Issues.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Issues issues = (Issues) jaxbUnmarshaller.unmarshal(file);
			List<Issues.Issue> i1 = issues.getIssue();
			for (Issues.Issue i2 : i1) {
				CodeAnalysis c = new CodeAnalysis();
				c.setId(i2.getId());
				c.setMessage(i2.getMessage());
				Integer line=i2.getLocation().getLine();
				if(line!=null)c.setLine(Integer.toString(line));
				c.setruleName(i2.getSummary());
				c.setcategory("Android_Lint");
				c.setClassName(i2.getLocation().getFile());
				c.setRecommendation(i2.getExplanation());
				if (i2.getSeverity().equalsIgnoreCase("warning")) {
					c.setSeverity("low");
					lowViolations++;
				} else if (i2.getSeverity().equalsIgnoreCase("error")) {
					c.setSeverity("medium");
					mediumViolations++;
				} else if (i2.getSeverity().equalsIgnoreCase("fatal")) {
					c.setSeverity("high");
					highViolations++;
				}
				ca.add(c);
			}
			lint.setHighViolations(highViolations);
			lint.setLowViolations(lowViolations);
			lint.setMediumViolations(mediumViolations);
			cq.setLint(lint);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Exception in converting Lint Report ");
		}
		System.out.println("Android Lint report successfully parsed");
	}
}
