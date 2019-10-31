/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import com.infosys.json.CodeAnalysis;
import com.infosys.json.CodeQuality;
import com.infosys.json.Sci;

public class ConvertSci {
	private ConvertSci() {
	}

	public static void convert(String inputPath, List<CodeAnalysis> ca, CodeQuality cq) {
		try (BufferedReader in = new BufferedReader(new FileReader(inputPath));) {
			String line = null;
			int notiCount = 0;
			int errorCount = 0;
			int warningCount = 0;
			String arrayF;
			while ((line = in.readLine()) != null) {
				String[] array1;
				arrayF = line.replaceAll("\\s+", "");
				array1 = arrayF.split("[|]");
				for (int index = 0; index < array1.length; index++) {
					array1[index] = array1[index].replace('"', ' ');
				}
				if (array1.length > 3
						&& (array1[6].startsWith("N") || array1[6].startsWith("W") || array1[6].startsWith("E"))) {
					CodeAnalysis c = new CodeAnalysis();
					if (array1[6].startsWith("N")) {
						notiCount++;
						c.setSeverity("low");
					}
					if (array1[6].startsWith("E")) {
						errorCount++;
						c.setSeverity("high");
					}
					if (array1[6].startsWith("W")) {
						warningCount++;
						c.setSeverity("medium");
					}
					c.setCategory("Sci");
					c.setLine(array1[7]);
					c.setruleName(array1[9]);
					c.setMessage(array1[10]);
					c.setId(array1[3]);
					c.setClassName(array1[4]);
					ca.add(c);
				}
			}
			Sci sci = new Sci();
			sci.setHigh(errorCount);
			sci.setMedium(notiCount);
			sci.setLow(warningCount);
			cq.setSci(sci);
		} catch (Exception e) {
			System.out.println("Error in convertSCI :" + e);
		}
	}
}
