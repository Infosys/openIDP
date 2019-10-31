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

import com.infosys.json.JsonClass;
import com.infosys.json.TestCaseResult;

public class ConvertOATS {
	
	
	
	private ConvertOATS() {
		// TODO Auto-generated constructor stub
	}

	public static void convert(String path, JsonClass json) {
		try {
			EditDocType.edit(path);
			File file = new File(path);
			JAXBContext jaxbContext = JAXBContext.newInstance(com.infosys.utilities.oats.Script.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			List<TestCaseResult> tr;
			if (json.getTestCaseResult() != null) {
				tr = json.getTestCaseResult();
			} else {
				tr = new ArrayList<>();
			}
			TestCaseResult tcr = new TestCaseResult();
			com.infosys.utilities.oats.Script c = (com.infosys.utilities.oats.Script) jaxbUnmarshaller.unmarshal(file);
			tcr.setDuration(c.getDuration().toString());
			tcr.setCategory("Functional");
			tcr.setId(c.getIterationNum().toString());
			tcr.setMessage(c.getName());
			tcr.setStatus(c.getResult());
			tcr.setTestToolName("OATS");
			tr.add(tcr);
			json.setTestCaseResult(tr);
		} catch (Exception e) {
			System.out.println("Conversion error for " + path + "Error: " + e);
		}
	}
}
