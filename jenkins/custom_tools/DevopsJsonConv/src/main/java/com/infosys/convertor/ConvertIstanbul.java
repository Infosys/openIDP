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
import com.infosys.json.Istanbul;
import com.infosys.json.JsonClass;
import com.infosys.utilities.coberturajava.Coverage;

public class ConvertIstanbul {
	private ConvertIstanbul() {
	}

	public static void convert(String inputPath, JsonClass jsonClass) {
		EditDocType.edit(inputPath);
		File file = new File(inputPath);
		JAXBContext jaxbContext;
		Unmarshaller jaxbUnmarshaller;
		Coverage.Packages packages = new Coverage.Packages();
		try {
			jaxbContext = JAXBContext.newInstance(Coverage.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Coverage coverageObj = (Coverage) jaxbUnmarshaller.unmarshal(file);
			if (coverageObj == null || coverageObj.getPackages() == null
					|| coverageObj.getPackages().getPackage() == null
					|| coverageObj.getPackages().getPackage().isEmpty())
				return;
			packages = coverageObj.getPackages();
			Codecoverage cc = jsonClass.getCodecoverage();
			if (cc == null)
				cc = new Codecoverage();
			Istanbul istan = new Istanbul();
			// istan.setBranchCoverage(String.valueOf(coverageObj.getBranchRate()*100));
			istan.setLineCoverage(String.valueOf(coverageObj.getLineRate() * 100));
			cc.setIstanbul(istan);
			jsonClass.setCodecoverage(cc);
			for (Coverage.Packages.Package p : packages.getPackage()) {
				List<Coverage.Packages.Package.Classes.Class> c = p.getClasses().getClazz();
				jsonClass.setCoverageDetails();
				for (Coverage.Packages.Package.Classes.Class class1 : c) {
					//
					CoverageDetails cd1 = createCoverageDetails();
					// cd1.setBranchCoverage(String.valueOf(class1.getBranchRate()*100));
					cd1.setCategory("istanbul");
					cd1.setClassName(class1.getName());
					cd1.setLineCoverage(String.valueOf(class1.getLineRate() * 100));
					//
					cd1.setPckage(p.getName());
					jsonClass.addCoverageDetails(cd1);
				}
			}
			System.out.println("istanbul coverage converted ");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static CoverageDetails createCoverageDetails() {
		return new CoverageDetails();
	}
}
