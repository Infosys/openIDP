/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.convertor;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.infosys.json.CodeAnalysis;
import com.infosys.json.JsonClass;

public class ConvertSAPCast {
	private ConvertSAPCast() {
	}

	public static void convert(JsonClass json, List<CodeAnalysis> ca, String SAPWarName) {
		try {
			CastReport rep = new CastReport();
			String castoutput = rep.readCastReport(SAPWarName);
			// System.out.println("ASed");
			// JSONObject jsonobj = new JSONObject(castoutput);
			JSONArray jsonarray = new JSONArray(castoutput);
			String totalCriticalViolations = null;
			String ruleName = null;
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONArray AppRes = jsonarray.getJSONObject(i).getJSONArray("applicationResults");
				JSONObject AppResult = AppRes.getJSONObject(0);
				AppResult.getJSONObject("result").getJSONObject("evolutionSummary")
						.optString("addedCriticalViolations");
				AppResult.getJSONObject("result").getJSONObject("evolutionSummary")
						.optString("removedCriticalViolations");
				AppResult.getJSONObject("result").getJSONObject("evolutionSummary")
						.optString("criticalViolationsInNewAndModifiedCode");
				totalCriticalViolations = AppResult.getJSONObject("result").getJSONObject("evolutionSummary")
						.optString("totalCriticalViolations");
				AppResult.getJSONObject("result").getJSONObject("evolutionSummary").optString("addedViolations");
				AppResult.getJSONObject("result").getJSONObject("evolutionSummary").optString("removedViolations");
				AppResult.getJSONObject("result").getJSONObject("evolutionSummary").optString("totalViolations");
				AppResult.getJSONObject("result").getJSONObject("evolutionSummary")
						.optString("violationsInNewAndModifiedCode");
				AppResult.getJSONObject("result").optString("grade");
				ruleName = AppResult.getJSONObject("reference").optString("name");
			}
			int tcv = Integer.parseInt(totalCriticalViolations);
			for (int i = 0; i < tcv; i++) {
				CodeAnalysis c = new CodeAnalysis();
				c.setSeverity("high");
				c.setruleName(ruleName);
				c.setcategory("SAP Cast");
				ca.add(c);
			}
			json.setCodeAnalysis(ca);
			System.out.println("SAP Cast report parsed succcessfully");
		} catch (Exception e) {
			System.out.println("Conversion error for SAP Cast" + e);
		}
	}
}
