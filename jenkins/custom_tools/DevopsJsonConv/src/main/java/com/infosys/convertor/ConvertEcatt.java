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

import org.apache.log4j.Logger;

import com.infosys.json.Ecatt;
import com.infosys.json.Functional;
import com.infosys.json.TestCaseResult;

public class ConvertEcatt {
	private static final Logger logger = Logger.getLogger(ConvertEcatt.class);
	private static Integer totalTest = 0;
	private static Integer fail = 0;
	private static Integer pass = 0;

	private ConvertEcatt() {
	}

	public static void convert(String inputPath, Functional fn, List<TestCaseResult> tc) {
		try (BufferedReader in = new BufferedReader(new FileReader(inputPath));) {
			Ecatt ec = new Ecatt();
			String line = null;
			while ((line = in.readLine()) != null) {
				Integer[] array = new Integer[3];
				array[0] = Integer.valueOf(line.split("|")[0]);
				array[1] = Integer.valueOf(line.split("|")[2]);
				array[2] = array[0] - array[1];
				pass += array[2];
				fail += array[1];
				totalTest += array[0];
				for (int i = 0; i < array[2]; i++) {
					TestCaseResult t = new TestCaseResult();
					t.setCategory("functional_Ecatt");
					t.setMessage("Testcase passed");
					t.setStatus("SUCCESS");
					t.setTestToolName("ECATT");
					tc.add(t);
				}
				for (int i = 0; i < array[1]; i++) {
					TestCaseResult t = new TestCaseResult();
					t.setCategory("functional_Ecatt");
					t.setMessage("Testcase failed");
					t.setStatus("FAILURE");
					t.setTestToolName("ECATT");
					tc.add(t);
				}
			}
			ec.setFail(fail);
			ec.setPass(pass);
			ec.setTotalTest(totalTest);
			fn.setEcatt(ec);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
