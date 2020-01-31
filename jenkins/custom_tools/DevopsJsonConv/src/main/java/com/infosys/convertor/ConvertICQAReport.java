///***********************************************************************************************
//*
//* Copyright 2018 Infosys Ltd.
//* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
//* https://opensource.org/licenses/MIT.
//*
//***********************************************************************************************/
package com.infosys.convertor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.infosys.json.ICQAObject;



public class ConvertICQAReport {
	
	

	
	private ConvertICQAReport() {
	}

	public static void convert(String path,String appname,String pipename,String dashboardURL ){
		String appid = "";
		ArrayList<ICQAObject> alICQA = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(path))){
			br.readLine();
			br.readLine();
			
			String str;
			String strSplit[];
			int splitLength;
			while((str = br.readLine()) != null){
				if(!" ".equals(str)){
					strSplit = str.split("\\s+");
					splitLength = strSplit.length;
					try{
		                  String requestUrl=dashboardURL+appname+"/"+pipename+"/fetch";
		              
						URL url = new URL(requestUrl);

						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("POST");

						
						int responseCode = conn.getResponseCode();
						System.out.println("\nSending 'POST' request to URL : " + url);
						System.out.println("Response Code : " + responseCode);

						String inputLine;
						StringBuffer response = new StringBuffer();
						try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
							while ((inputLine = in.readLine()) != null) {
								response.append(inputLine);
							}

						}
		                  
		                  //print result
		                  appid = response.toString();
		                 
		            conn.disconnect();
		            
		            System.out.println("done");
		          //Extracting data from json
		            }
		            catch(Exception e)
		            {
		                   System.out.println("Error message for icqa is : "+e);
		            }

					
					
					alICQA.add(new ICQAObject(appid,strSplit[0],strSplit[1],strSplit[2],strSplit[3],strSplit[4],strSplit[5],strSplit[6],strSplit[7],strSplit[8],strSplit[9],strSplit[10],strSplit[11],strSplit[12],strSplit[13],strSplit[14],strSplit[15],strSplit[16],strSplit[splitLength-3],strSplit[splitLength-2],strSplit[splitLength-1]));
					System.out.println(appid+" "+strSplit[0]+"  "+strSplit[1]+"  "+strSplit[2]+"  "+strSplit[3]+"  "+strSplit[4]+"  "+strSplit[5]+"  "+strSplit[6]+"  "+strSplit[7]+"  "+strSplit[8]+"  "+strSplit[9]+"  "+strSplit[10]+"  "+strSplit[11]+"  "+strSplit[12]+"  "+strSplit[13]+"  "+strSplit[14]+"  "+strSplit[15]+"  "+strSplit[16]+"  "+strSplit[splitLength-3]+"  "+strSplit[splitLength-2]+"  "+strSplit[splitLength-1]);
					
					
					try{
		                  String requestUrl=dashboardURL+appid+"/"+strSplit[0]+"/"+strSplit[1]+"/"+strSplit[2]+"/"+strSplit[3]+"/"+strSplit[4]+"/"+strSplit[5]+"/"+strSplit[6]+"/"+strSplit[7]+"/"+strSplit[8]+"/"+strSplit[9]+"/"+strSplit[10]+"/"+strSplit[11]+"/"+strSplit[12]+"/"+strSplit[13]+"/"+strSplit[14]+"/"+strSplit[15]+"/"+strSplit[16]+"/"+strSplit[splitLength-3]+"/"+strSplit[splitLength-2]+"/"+strSplit[splitLength-1]+"/update";
		              
		              URL url = new URL(requestUrl);
		             
		              HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		              conn.setRequestMethod("POST");

		                  //add request header
		                  //conn.setRequestProperty("User-Agent", USER_AGENT);
		              	  
		                  int responseCode = conn.getResponseCode();
		                  System.out.println("\nSending 'POST' request to URL : " + url);
		                  System.out.println("Response Code : " + responseCode);
		                  String inputLine;
		                  StringBuffer response = new StringBuffer();
		                  try( BufferedReader in = new BufferedReader(
		                          new InputStreamReader(conn.getInputStream()))){
		                	  while ((inputLine = in.readLine()) != null) {
			                        response.append(inputLine);
			                  }
		                  }

		                  //print result		                 
		            conn.disconnect();
		            
		            System.out.println("done");
		          //Extracting data from json
		            }
		            catch(Exception e)
		            {
		                  System.out.println("Error message for icqa is : "+e);
		            }
				}
			}
		}
		
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
