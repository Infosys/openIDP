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

import org.apache.log4j.Logger;

import com.infosys.json.Cobertura;
import com.infosys.json.Codecoverage;
import com.infosys.json.CoverageDetails;
import com.infosys.json.JsonClass;
import com.infosys.utilities.coberturago.Coverage.Packages.Package;
import com.infosys.utilities.coberturago.Coverage.Packages.Package.Classes.Class;
import com.infosys.utilities.coberturago.Coverage.Packages.Package.Classes.Class.Lines.Line;
import com.infosys.utilities.coberturajava.Coverage;

public class ConvertCobertura {

	private static final Logger logger = Logger.getLogger(ConvertCobertura.class);

	private ConvertCobertura() {
	}

	/**
	 * converts cobertura reports
	 * 
	 * @param inputPath
	 * @param jsonClass
	 */
	public static void convert(String inputPath, JsonClass jsonClass) {

		EditDocType.edit(inputPath);
		File file = new File(inputPath);

		JAXBContext jaxbContext;
		Unmarshaller jaxbUnmarshaller;
		Cobertura cobertura = null;

		try {

			jaxbContext = JAXBContext.newInstance(Coverage.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			com.infosys.utilities.coberturago.Coverage coverageObj = (com.infosys.utilities.coberturago.Coverage) jaxbUnmarshaller
					.unmarshal(file);

			if (coverageObj == null || coverageObj.getPackages() == null
					|| coverageObj.getPackages().getPackage() == null
					|| coverageObj.getPackages().getPackage().isEmpty())
				return;

			CoverageDetails cd;
			int linecovered = 0;
			double linecoverage = 0;
			double tempcount = 0;
			for (Package pck : coverageObj.getPackages().getPackage()) {
				tempcount = 0;

				for (Class c : pck.getClasses().getClazz()) {
					cd = new CoverageDetails();
					cd.setCategory("cobertura go");
					cd.setClassName(c.getFilename());
					cd.setPckage(pck.getName());
					for (Line l : c.getLines().getLine()) {
						if (l.getHits() == 1)
							linecovered++;
						tempcount++;
					}
					linecoverage = linecovered / tempcount * 100;
					cd.setLineCoverage(String.valueOf(linecoverage));
					jsonClass.setCoverageDetails();
					jsonClass.AddCoverageDetails(cd);
				}
			}

		} catch (Exception e) {

			try {

				jaxbContext = JAXBContext.newInstance(com.infosys.utilities.coberturajava.Coverage.class);
				jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				com.infosys.utilities.coberturajava.Coverage coverageObj = (com.infosys.utilities.coberturajava.Coverage) jaxbUnmarshaller
						.unmarshal(file);

				if (coverageObj == null || coverageObj.getPackages() == null
						|| coverageObj.getPackages().getPackage() == null
						|| coverageObj.getPackages().getPackage().isEmpty())
					return;

				cobertura = new Cobertura();
				cobertura.setLineCoverage(roundOff(Double.valueOf(coverageObj.getLineRate() * 100)));

				for (Coverage.Packages.Package p : coverageObj.getPackages().getPackage()) {
					List<Coverage.Packages.Package.Classes.Class> c = p.getClasses().getClazz();
					jsonClass.setCoverageDetails();
					for (Coverage.Packages.Package.Classes.Class class1 : c) {
						//
						CoverageDetails cd1 = createCoverageDetails();

						cd1.setCategory("cobertura");
						cd1.setClassName(class1.getName());
						cd1.setLineCoverage(String.valueOf(class1.getLineRate() * 100));
						//
						cd1.setPckage(p.getName());
						jsonClass.setCoverageDetails();
						jsonClass.AddCoverageDetails(cd1);
					}

				}

			} catch (Exception e1) {
				logger.error("Conversion error for " + inputPath + e);
				return;
			}
		}

		Codecoverage cc = jsonClass.getCodecoverage();
		if (cc == null)
			cc = new Codecoverage();
		cc.setCobertura(cobertura);
		jsonClass.setCodecoverage(cc);

		logger.info("Cobertura report Converted Successfully..!!");

	}

	private static String roundOff(Double num) {

		return num.toString();

	}

	private static CoverageDetails createCoverageDetails() {
		return new CoverageDetails();
	}
}
