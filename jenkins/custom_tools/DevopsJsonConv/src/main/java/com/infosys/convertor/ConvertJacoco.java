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

import com.infosys.json.Codecoverage;
import com.infosys.json.CoverageDetails;
import com.infosys.json.Jacoco;
import com.infosys.json.JsonClass;
import com.infosys.utilities.coveragejacoco.Report;
import com.infosys.utilities.coveragejacoco.Report.Counter;

public class ConvertJacoco {
	
	
	
	private ConvertJacoco() {
		// TODO Auto-generated constructor stub
	}

	public static void convert(String inputPath, List<CoverageDetails> cdList, JsonClass json) {
		System.out.println("converting Jacoco");
		EditDocType.edit(inputPath);
		File file = new File(inputPath);
		JAXBContext jaxbContext;
		Unmarshaller jaxbUnmarshaller;
		try {
			jaxbContext = JAXBContext.newInstance(Report.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Report t = (Report) jaxbUnmarshaller
					.unmarshal(file);
			Jacoco j = new Jacoco();
			List<Counter> l = t.getCounter();
			float temp1;
			float temp2;
			float rate;
			for (Counter counter : l) {
				temp1 = counter.getCovered();
				temp2 = counter.getMissed();
				rate = temp1 / (temp1 + temp2);
				rate = rate * 100;
				if (counter.getType().equalsIgnoreCase("line"))
					j.setLineCoverage(String.valueOf(rate));
				if (counter.getType().equalsIgnoreCase("method"))
					j.setMethodCoverage(String.valueOf(rate));
				if (counter.getType().equalsIgnoreCase("branch"))
					j.setBranchCoverage(String.valueOf(rate));
				if (counter.getType().equalsIgnoreCase("class"))
					j.setClassCoverage(String.valueOf(rate));
				if (counter.getType().equalsIgnoreCase("complexity"))
					j.setComplexityScore(String.valueOf(rate));
				if (counter.getType().equalsIgnoreCase("instruction"))
					j.setInstructionCoverage(String.valueOf(rate));
			}
			Codecoverage cc = json.getCodecoverage();
			if (cc == null)
				cc = new Codecoverage();
			cc.setJacoco(j);
			json.setCodecoverage(cc);
			// detailed info
			List<Report.Package> lp = t.getPackage();
			for (Report.Package package1 : lp) {
				json.setCoverageDetails();
				List<Report.Package.Class> clist = package1.getClazz();
				for (Report.Package.Class class1 : clist) {
					//
					List<Report.Package.Class.Counter> counterlist = class1.getCounter();
					float temp3;
					float temp4;
					float rate1;
					for (Report.Package.Class.Counter counter : counterlist) {
						if (counter.getType().equalsIgnoreCase("line")
								|| counter.getType().equalsIgnoreCase("branch")) {
							CoverageDetails cd = createCoverageDetails();
							cd.setPckage(package1.getName());
							cd.setClassName(class1.getName());
							temp3 = counter.getCovered();
							temp4 = counter.getMissed();
							rate1 = temp3 / (temp3 + temp4);
							rate1 = rate1 * 100;
							if (counter.getType().equalsIgnoreCase("line"))
								cd.setLineCoverage(String.valueOf(rate1));
							else
								cd.setBranchCoverage(String.valueOf(rate1));
							cd.setCategory("Jacoco");
							json.addCoverageDetails(cd);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Exception in converting Jacoco Code Coverage ");
		}
	}

	private static CoverageDetails createCoverageDetails() {
		return new CoverageDetails();
	}
}
