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

	/**
	 * method to parse sapunit reports
	 * @param inputPath
	 * @param tc
	 */
	public static void convert(String inputPath,List<TestCaseResult> tc) {

		

		try (BufferedReader in = new BufferedReader(new FileReader(inputPath));){
			//File file = new File(inputPath);
			
			
			String line = null;
			
			String arrayF;
			while((line = in.readLine()) != null)
			{

				String[]array1 ;
				//System.out.println(line);
			    arrayF = line.replaceAll("\\s+","");
			    array1 = arrayF.split("[|]"); 
			    
			    for (int index =0; index < array1.length; index++){
				    array1[index] = array1[index].replace('"',' ');
				}
			    
			    
			 if (array1.length>2 && (array1[6].startsWith("N")|| array1[6].startsWith("E") || array1[6].startsWith("W")))
			 {
				
				TestCaseResult t=new TestCaseResult();
				 if(array1[6].startsWith("N")) {
						t.setStatus("SUCCESS");
						t.setMessage("Testcase passed");
					}
				 else if(array1[6].startsWith("E")) {
						t.setStatus("FAILURE");
						t.setMessage("Testcase failed");
					}
				 else if(array1[6].startsWith("W")){
					
					 t.setStatus("FAILURE");
						t.setMessage("Testcase failed");
				}

					t.setCategory("SAP_UNIT");
					
					t.setId(array1[9]);
					t.settestSuiteName(array1[3]);
					tc.add(t);
			 	
			}
			
			}
			in.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
