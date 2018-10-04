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

import com.infosys.json.CoverageDSPrivJson;
import com.infosys.json.CoverageDetails;
import com.infosys.json.JsonClass;
import com.infosys.utilities.coveragedspriv.CoverageDSPriv;

public class ConvertDSPriv {
	private ConvertDSPriv() {
	}

	public static CoverageDSPrivJson convert(String inputPath, JsonClass json) {
		try {
			EditDocType.edit(inputPath);
			File file = new File(inputPath);
			JAXBContext jaxbContext = JAXBContext.newInstance(CoverageDSPriv.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			CoverageDSPrivJson fdspjson = new CoverageDSPrivJson();
			CoverageDSPriv c = (CoverageDSPriv) jaxbUnmarshaller.unmarshal(file);
			fdspjson.setModuleName(c.getModule().getModuleName());
			int lc = c.getModule().getLinesCovered();
			int lpc = c.getModule().getLinesPartiallyCovered();
			int lnc = c.getModule().getLinesNotCovered();
			int bc = c.getModule().getBlocksCovered();
			int bnc = c.getModule().getBlocksNotCovered();
			fdspjson.setLineCoverage(Double.toString((double) lc / ((double) lc + (double) lnc + (double) lpc)));
			fdspjson.setBlockCoverage(Double.toString((double) bc / ((double) bc + (double) bnc)));
			json.setCoverageDetails();
			List<CoverageDSPriv.Module.NamespaceTable.Class> cd1 = c.getModule().getNamespaceTable().getClazz();
			for (CoverageDSPriv.Module.NamespaceTable.Class cd2 : cd1) {
				CoverageDetails temp = new CoverageDetails();
				temp.setClassName(cd2.getClassName());
				temp.setCategory("emma");
				temp.setBranchCoverage(null);
				temp.setPckage(null);
				temp.setLineCoverage(
						Double.toString((double) (cd2.getLinesCovered()) / ((double) (cd2.getLinesCovered())
								+ (double) (cd2.getLinesNotCovered()) + (double) (cd2.getLinesPartiallyCovered()))));
				json.addCoverageDetails(temp);
			}
			return fdspjson;
		} catch (Exception e) {
			CoverageDSPrivJson cj;
			cj = null;
			return cj;
		}
	}
}
