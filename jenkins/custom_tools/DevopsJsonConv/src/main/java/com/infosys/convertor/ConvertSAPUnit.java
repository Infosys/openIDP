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

import com.infosys.json.TestCaseResult;

public class ConvertSAPUnit {
	private ConvertSAPUnit() {
	}

	public static void convert(String inputPath, List<TestCaseResult> tc) {
		try (BufferedReader in = new BufferedReader(new FileReader(inputPath));) {
			// File file = new File(inputPath);
			int total=0;
			int tError=0;
			String line = null;
			String arrayF;
			while ((line = in.readLine()) != null) {
				String[] array1;
				// System.out.println(line);
				arrayF = line.replaceAll("\\s+", "");
				
				if(arrayF.contains("Total_Unit_Test"))
				{
					String value=arrayF.substring(arrayF.indexOf("=")+1);
					total=Integer.valueOf(value);
					continue;
				}
				
				array1 = arrayF.split("[|]");
				for (int index = 0; index < array1.length; index++) {
					array1[index] = array1[index].replace('"', ' ');
				}
				if (array1.length > 2
						&& (array1[6].startsWith("E"))) {
					TestCaseResult t = new TestCaseResult();
					tError++;
					t.setStatus("FAILURE");
					String temp=array1[10];
					if(temp!=null && !"".equals(temp))t.setMessage(temp);
					else t.setMessage("Testcase failed");
					
					t.setCategory("SAP_UNIT");
					t.setId(array1[9]);
					t.settestSuiteName(array1[3]);
					t.setTestToolName("ABAP UNIT");
					tc.add(t);
				}
			}
			in.close();
			for(int i=0;i<(total-tError);i++)
			{
				TestCaseResult t = new TestCaseResult();
				t.setStatus("SUCCESS");
				t.setMessage("Testcase Passed");
				t.setCategory("SAP_UNIT");
				//t.setId(array1[9]);
				//t.settestSuiteName(array1[3]);
				tc.add(t);
			}
			System.out.println("ABAP report parsed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
