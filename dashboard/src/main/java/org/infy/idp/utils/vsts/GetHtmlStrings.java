/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.utils.vsts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 
 * The class GetHtmlStrings
 *
 */
public class GetHtmlStrings {

	
	public static String getTableHtml(){
		String inputHtmlNew = "";
		//String fileName = "src/newTable.html";
		InputStream is = GetHtmlStrings.class.getResourceAsStream("/newTable_Mod.html");
		inputHtmlNew=readfile(is);
		return inputHtmlNew;
	}
	public static String getPipelineTableHtml(){
		String inputTableNew = "";
		//String fileName = "src/newTable.html";
		InputStream is = GetHtmlStrings.class.getResourceAsStream("/newPipelineTable.html");
		inputTableNew=readfile(is);
		return inputTableNew;
	}
	public static String getRowHtml(){
		String inputHtmlRow = "";
		//String fileName = "src/newRow.html";
		InputStream is = GetHtmlStrings.class.getResourceAsStream("/newRow.html");
		inputHtmlRow=readfile(is);
		return inputHtmlRow;
	}
	
	public static String readfile(InputStream fileStream){
		String outStr = "";
		
		BufferedReader reader = null;

		try{
			try{
				
				reader = new BufferedReader(new InputStreamReader(fileStream));
				String line = null;
				while ( (line = reader.readLine()) != null) {
					outStr+=line;
				}
			}finally{
				if(reader != null){
					reader.close();
				}
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return outStr;
	}

	/*public static void main(String args []){
		System.out.println(new StringValueResources().getRowHtml());
	}*/
}
