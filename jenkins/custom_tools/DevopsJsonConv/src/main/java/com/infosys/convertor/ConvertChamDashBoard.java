package com.infosys.convertor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.infosys.json.ChamDashBoard;
import com.infosys.json.CodeQuality;
import com.infosys.json.SapFailData;
import com.infosys.utilities.EntryHome;

public class ConvertChamDashBoard {
	
	
	private static final String JSON = ".json";
	private static final Logger logger = Logger.getLogger(EntryHome.class);
	private static CodeQuality codeQuality = new CodeQuality();
	public static final String CONTENTTYPE="Content-Type";
	public static final String APPLICATIONJSON="application/json";
	private ConvertChamDashBoard() {
	}

	
	
	public static List<ChamDashBoard> extractData(String inputPath, String[] arg ) {
		//EditDocType.edit(inputPath);
		File file = new File(inputPath);
//		String csvFile = "D:\\reports/Book1 - Copy.csv";
		String line = "";
		String cvsSplitBy = ",";
//		File file = new File(csvFile);
		
		List<ChamDashBoard> list =new ArrayList<>();
		List<SapFailData> failData=new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {

			int i = 0;
			while ((line = br.readLine()) != null) {
	
				if (i == 0)
					{
					i++;
					
					continue;
					}
				i++;
	
				// use comma as separator
				
				String[] mapping = line.split(cvsSplitBy, -1);
				
				ChamDashBoard chamDashBoard = new ChamDashBoard();
				
				if(mapping.length>0) chamDashBoard.setCrNumber(mapping[0].replaceAll("^\"|\"$", ""));
				if(mapping.length>1) chamDashBoard.setCrDescription(mapping[1].replaceAll("^\"|\"$", ""));
				if(mapping.length>2) chamDashBoard.setCrStatus(mapping[2].replaceAll("^\"|\"$", ""));
				if(mapping.length>3) chamDashBoard.setCdNumber(mapping[3].replaceAll("^\"|\"$", ""));
				if(mapping.length>4) chamDashBoard.setCdType(mapping[4].replaceAll("^\"|\"$", ""));
				if(mapping.length>5) chamDashBoard.setCdDescription(mapping[5].replaceAll("^\"|\"$", ""));
				if(mapping.length>6) chamDashBoard.setCdFromStatus(mapping[6].replaceAll("^\"|\"$", ""));
				if(mapping.length>7) chamDashBoard.setCdToStatus(mapping[7].replaceAll("^\"|\"$", ""));
				if(mapping.length>8) chamDashBoard.setTrNumber(mapping[8].replaceAll("^\"|\"$", ""));
				if(mapping.length>9) chamDashBoard.setTrDescription(mapping[9].replaceAll("^\"|\"$", ""));
				if(mapping.length>10) chamDashBoard.setTrStatus(mapping[10].replaceAll("^\"|\"$", ""));
				if(mapping.length>11) chamDashBoard.setLogs(mapping[11].replaceAll("^\"|\"$", ""));
				if(mapping.length>12) chamDashBoard.setStatus(mapping[12].replaceAll("^\"|\"$", ""));
				
				if(chamDashBoard.getStatus().equalsIgnoreCase("FAIL")) {
				
			 		SapFailData sapFailData =new SapFailData();
					
					sapFailData.setCdNumber(chamDashBoard.getCdNumber());
					System.out.println(sapFailData.getCdFromStatus());
					sapFailData.setCdFromStatus(chamDashBoard.getCdFromStatus());
					sapFailData.setCdToStatus(chamDashBoard.getCdToStatus());
					sapFailData.setLogs(chamDashBoard.getLogs());
					sapFailData.setStatus(chamDashBoard.getStatus());
					
					failData.add(sapFailData);
					System.out.println(failData);
				}
				
				list.add(chamDashBoard);
				
			}

			
			
			String finalUrlString= arg[11]+"/"+arg[1]+"/"+arg[5]+"/"+arg[20]+"/SAP/Email";
			URL url = new URL(finalUrlString);
			Gson gson=new Gson();
			String str=gson.toJson(failData);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty(CONTENTTYPE, APPLICATIONJSON);
			try (OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream())) {

				writer.write(str);

			}

		    logger.info("connected rest service");
			logger.info(connection.getResponseCode());
			
			
		
		}

		catch(IOException e)
		{
			e.printStackTrace();
		}
		return list;
		
	}

	

}
