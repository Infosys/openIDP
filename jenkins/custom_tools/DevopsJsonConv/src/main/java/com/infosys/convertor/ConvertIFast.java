/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.io.FileReader;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.infosys.json.IFast;
import com.infosys.json.JsonClass;
import com.infosys.json.ServiceTest;

public class ConvertIFast {

	static final Logger logger = Logger.getLogger(ConvertIFast.class);

	private ConvertIFast() {
	}

	/**
	 * method to parse ifast report
	 * 
	 * @param inputPath
	 * @param json_class
	 * @param app
	 * @param prefixForId
	 * @param tfsPath
	 */
	public static void convert(String inputPath, JsonClass json_class, String app, String prefixForId, String tfsPath) {

		JSONParser parser = new JSONParser();

		try {

			JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(inputPath));

			if (jsonObj == null)
				return;

			String statsStr = (String) jsonObj.get("ExecutionStats");

			if (statsStr == null)
				return;

			JSONParser statsParser = new JSONParser();
			JSONObject statsObj = (JSONObject) statsParser.parse(statsStr);

			String totalWithNoRun = (String) statsObj.get("TotalCount");
			String noRun = (String) statsObj.get("NoRunCount");
			String pass = (String) statsObj.get("PassCount");
			String fail = (String) statsObj.get("FailCount");

			if (totalWithNoRun == null || noRun == null || pass == null || fail == null)
				return;

			IFast iFast = new IFast();
			iFast.setTotalTest(Integer.parseInt(totalWithNoRun) - Integer.parseInt(noRun));
			iFast.setPass(Integer.parseInt(pass));
			iFast.setFail(Integer.parseInt(fail));

			ServiceTest sTest = json_class.getServiceTest();
			if (sTest == null)
				sTest = new ServiceTest();

			sTest.setiFast(iFast);
			json_class.setServiceTest(sTest);

			logger.info("iFast file in converted .");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
