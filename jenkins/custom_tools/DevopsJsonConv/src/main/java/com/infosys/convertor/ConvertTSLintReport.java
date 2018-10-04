/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.infosys.json.CodeAnalysis;

public class ConvertTSLintReport {
	

	private static Integer lowViolations = 0;
	private static Integer highViolations = 0;
	private static Integer mediumViolations = 0;

	private ConvertTSLintReport() {
	}
	
	public static void setTotalSeverities(List<CodeAnalysis> ca) {
		for (CodeAnalysis codeAnalysis : ca) {
			if (codeAnalysis.getSeverity().equalsIgnoreCase("low")
					&& codeAnalysis.getcategory().equalsIgnoreCase("tslint"))
				lowViolations++;
			if (codeAnalysis.getSeverity().equalsIgnoreCase("medium")
					&& codeAnalysis.getcategory().equalsIgnoreCase("tslint"))
				mediumViolations++;
			if (codeAnalysis.getSeverity().equalsIgnoreCase("high")
					&& codeAnalysis.getcategory().equalsIgnoreCase("tslint"))
				highViolations++;
		}
	}

	public static Integer getHighViolations() {
		return highViolations;
	}

	public static Integer getLowViolations() {
		return lowViolations;
	}

	public static Integer getMediumViolations() {
		return mediumViolations;
	}

	public static List<CodeAnalysis> convert(String inputPath) {
		List<CodeAnalysis> ca = new ArrayList<>();
		File file = new File(inputPath);
		try {
			String string = FileUtils.readFileToString(file);
			String[] ar;
			CodeAnalysis c;
			if (string.contains("All files pass linting")) {
				System.out.println("All files pass linting");
				return null;
			}
			string = string.replaceAll("ERROR: ", " ");
			string = string.replaceAll(":", "");
			string = string.replaceAll("\\n", "-");
			string = string.replaceAll("\\r", "-");
			string = string.replaceAll("Lint errors found in the listed files", "");
			ar = string.split("- ");
			String lineNumber;
			String message;
			String id;
			String temporary = "";
			for (int i = 1; i <= ar.length - 1; i += 1) {
				if (ar[i].toLowerCase().contains("(https//")) {
					ar[i] = ar[i].substring(0, ar[i].indexOf("(https/"));
				}
				c = new CodeAnalysis();
				c.setcategory("tsLint");
				lineNumber = ar[i].substring(ar[i].indexOf('[') + 1, ar[i].indexOf(','));
				message = ar[i].substring(ar[i].indexOf(']') + 1);
				message = message.replaceAll("-", "");
				temporary = ar[i].substring(0, ar[i].lastIndexOf('[') + 1);
				id = temporary.substring(temporary.lastIndexOf('/') + 1, temporary.lastIndexOf('['));
				c.setMessage(message);
				c.setLine(lineNumber);
				c.setId(id);
				ca.add(c);
			}
		} catch (IOException e) {
			System.out.println("Error in converting TSLint reports");
			e.printStackTrace();
		}
		System.out.println("TSLint reports converted successfully");
		return ca;
	}
}
