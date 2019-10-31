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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.net.util.Base64;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.infosys.json.CsvUpload;
import com.infosys.json.Informatica;
import com.infosys.json.InformaticaObject;
import com.infosys.json.JsonClass;
import com.infosys.json.Module;
import com.infosys.json.PackageContent;

public class ConvertBuildLog {
	private static final Logger logger = Logger.getLogger(ConvertBuildLog.class);
	public static final String APIJSONPRETTYTRUE = "/api/json?pretty=true";
	public static final String JOB = "/job/";
	public static final String AUTHORIZATION = "Authorization";
	public static final String BASIC = "Basic ";
	public static final String PIPELINE = "_Pipeline/";
	public static final String ACTIONS = "actions";
	public static final String BUILD = "build";
	public static final String HTTPS = "https://";
	public static final String JSONINPUT = "json_input";
	public static final String MODULE = "module";
	public static final String PARAMETERS = "parameters";
	public static final String VALUE = "value";

	private ConvertBuildLog() {
	}

	public static void convert(String inputPath, JsonClass jsonClass, String [] args) {
		String technologyName="";	
		if (args.length > 18 && !args[18].equals("NA"))technologyName=args[18];
		String serviceUrl="";
		if (args.length > 14 && !args[14].equals("NA"))serviceUrl=args[14]+":8889";
		String artifactName="";
		if (args.length > 17 && !args[17].equals("NA"))artifactName=args[17];
		String jobName=args[1]+"_"+args[5];
		String jenkinsServer="";
		if (args.length > 14 && !args[14].equals("NA"))technologyName=args[14]+":8085/jenkins";
		String pipelineId="";
		if (args.length > 8 && !args[8].equals("NA"))technologyName=args[8];
		String jenkinsUserName="";
		if (args.length > 15 && !args[15].equals("NA"))technologyName=args[15];
		String jenkinsPassword="";
		if (args.length > 16 && !args[16].equals("NA"))technologyName=args[16];
		String stage="";
		if (args.length > 19 && !args[19].equals("NA"))technologyName=args[19];
		try {
			jsonClass.setLog(new String(Files.readAllBytes(Paths.get(inputPath)), Charset.defaultCharset()));
			
			//Package Contents
			if(technologyName.toLowerCase().equals("informatica") && stage.toLowerCase().equals("build"))
			{
				convertInformatica(inputPath,jsonClass,serviceUrl,artifactName);
			}
			if((technologyName.toLowerCase().contains("dotnet") || technologyName.toLowerCase().contains("net")) && stage.toLowerCase().equals("build"))
			{
				convertDotNet(serviceUrl,jobName,jenkinsServer,pipelineId,artifactName,jenkinsUserName,jenkinsPassword);
			}
			if(technologyName.toLowerCase().contains("ant") && stage.toLowerCase().equals("build"))
			{
				convertAnt(serviceUrl,jobName,jenkinsServer,pipelineId,artifactName,jenkinsUserName,jenkinsPassword);
			}
			if(technologyName.toLowerCase().contains("bigdata") && stage.toLowerCase().equals("build"))
			{
				convertBigData(serviceUrl,jobName,jenkinsServer,pipelineId,artifactName,jenkinsUserName,jenkinsPassword);

			}
			
			
			logger.info("Report Converted Successfully..!!");
		} catch (Exception e) {
			logger.error("Conversion error for " + inputPath + "Error: " + e);
		}
	}
	
	private static void convertInformatica(String inputPath,JsonClass jsonClass,String serviceUrl,String artifactName)
	{
		PackageContent packageContent=new PackageContent();
		packageContent.setArtifactName(artifactName);
		Informatica informatica=new Informatica();
		List<InformaticaObject> informaticaObjects=new ArrayList<>();
		try(BufferedReader in = new BufferedReader(new FileReader(inputPath))){
			
			
			String line = in.readLine();
			
			while(true)
			{
				boolean flag=false;
				if(line.toLowerCase().contains("exporting selected objects in folder "))
				{
					///
					
					InformaticaObject informaticaobject=new InformaticaObject();
					String folderName=line.substring(line.indexOf("folder")+7,line.indexOf(" ..."));
					informaticaobject.setFolderName(folderName);
					List<String> sourceDefinition=new ArrayList<>();
					List<String> targetDefinition=new ArrayList<>();
					List<String> mapping=new ArrayList<>();
					List<String> sessionconfig=new ArrayList<>();
					List<String> workflow=new ArrayList<>();
					List<String> mapplet=new ArrayList<>();
					List<String> session=new ArrayList<>();
					List<String> sequence=new ArrayList<>();
					List<String> worklet=new ArrayList<>();
					while((line=in.readLine()) != null)
						{
							
							if(line.toLowerCase().contains("export is completed"))break;
							if(line.toLowerCase().contains("exporting selected objects in folder"))
							{
								flag=true;
								break;
							}
							else if(line.toLowerCase().contains("exporting") && !line.toLowerCase().contains("exporting selected objects in folder"))
							{
								String fileName=line.substring(line.indexOf("[")+1, line.indexOf("]"));
								if (line.toLowerCase().contains("sessionconfig"))
								{
									sessionconfig.add(fileName);
								}
								else if (line.toLowerCase().contains("source definition"))
								{
									sourceDefinition.add(fileName);
								}
								else if (line.toLowerCase().contains("target definition"))
								{
									targetDefinition.add(fileName);
								}
								else if (line.toLowerCase().contains("sequence"))
								{
									sequence.add(fileName);
								}
								else if (line.toLowerCase().contains("mapplet"))
								{
									mapplet.add(fileName);
								}
								else if (line.toLowerCase().contains("mapping"))
								{
									mapping.add(fileName);
								}
								else if (line.toLowerCase().contains("session"))
								{
									session.add(fileName);
								}
								else if (line.toLowerCase().contains("workflow"))
								{
									workflow.add(fileName);
								}
								else if (line.toLowerCase().contains("worklet"))
								{
									worklet.add(fileName);
								}
								
							}
						
					}
					informaticaobject.setMapping(mapping);
					informaticaobject.setMapplet(mapplet);
					informaticaobject.setSequence(sequence);
					informaticaobject.setSession(session);
					informaticaobject.setSessionconfig(sessionconfig);
					informaticaobject.setSourceDefinition(sourceDefinition);
					informaticaobject.setTargetDefinition(targetDefinition);
					informaticaobject.setWorkflow(workflow);
					informaticaobject.setWorklet(worklet);
					informaticaObjects.add(informaticaobject);
				}
				if(!flag)
				{
					if((line=in.readLine())==null)break;
				}
				
			}
			in.close();
			informatica.setInfoObject(informaticaObjects);
			packageContent.setInformatica(informatica);
			
			Gson gson = new Gson();
			String packageInString=gson.toJson(packageContent);
			
			sendPackageContent(packageInString,serviceUrl);
			logger.info("Informatica package content parsed");
			
		}
		catch(Exception e)
		{
			logger.error("Exception while parsing informatica buildlog",e);
		}
	}
	@SuppressWarnings("resource")
	public static List<CsvUpload> convertCSV(String inputPath) throws IOException {
		Pattern p = Pattern.compile("\\w+.xml");
		System.out.println(p);
		System.out.println(inputPath);
		List<CsvUpload> value = new ArrayList<>();
		List<String> fileNameList = new ArrayList<>();
		List<String> statusList = new ArrayList<>();
		BufferedReader in = new BufferedReader(new FileReader(inputPath));

		try {
			// in = new BufferedReader(new FileReader(inputPath));
			boolean hasFile=false;
			String filename1=""; 
			String status="";
			String line = in.readLine();
//			boolean hasFile=false;
			while ((line = in.readLine()) != null) {
				
//				hasFile=false;
//				if ((line.contains("Done creating")) && (line.contains(".xml"))) {
					String comparstr = "ReferenceTransmissionNo>";
					String str1 = "TransmissionAckStatus>";
					String filename = "file>";
					String timeout="TITLE>";
					if (line.contains(comparstr)) {

						String str = line.substring((line.indexOf(comparstr) + 24), (line.lastIndexOf(comparstr) - 2));
              		  		System.out.println(str);
//						
//						
						
							status=str;
							statusList.add(status);
						hasFile=true;
					}

					 if (line.contains(str1)) {
						 String str2 = line.substring((line.indexOf(str1) + 22), (line.lastIndexOf(str1) - 2));
              		  			 System.out.println(str2);
//              		  			 
              		  			
              		  				 status=str2;
              		  				statusList.add(status);
              		  			 hasFile=true;
						
					} 
					 if(line.contains(timeout)) 
					 {
						 String str3 = line.substring((line.indexOf(timeout) + 6), (line.lastIndexOf(timeout) - 2));
      		  		     System.out.println(str3);
      		  		     status="Failure of Web Server bridge";
      		  		     System.out.println("timeout values"+status);
      		  		     statusList.add(status);
      		  		     hasFile=true;
					}				 
					  if (line.contains(filename)) {
//              		  			 
						if (line.indexOf(filename) >= 0) {
							
							String str = line.toString().trim().substring((line.indexOf(filename)),
									(line.lastIndexOf(filename))-11);
							
							filename1 = str.replaceFirst("file>", "");
//							
							fileNameList.add(filename1);
							hasFile=true;
						}
					}
			}
							for(int i=0,j=0;i<fileNameList.size() && j<statusList.size();i++,j++) {
								CsvUpload csvUpload = new CsvUpload();
								csvUpload.setFilename(fileNameList.get(i));								
								csvUpload.setAck_status(statusList.get(j));
//								
								value.add(csvUpload);
								
							}
							
//							

						
						
		}

		catch (Exception e) {
			logger.error("Exception while parsing CSV upload buildlog", e);
		} finally {
			in.close();
		}

		return value;

	}

	
	
	private static void convertDotNet(String serviceUrl,String jobName,String jenkinsServer,String pipelineId,String artifactName,String jenkinsUserName,String jenkinsPassword)
	{
		
		PackageContent packageContent=new PackageContent();
		packageContent.setArtifactName(artifactName);
		Module module=new Module();
		List<String> modules=new ArrayList<>();

		try{
			
			
			
			String webPage = "https://" + jenkinsServer + "/job/" + jobName + "/job/" + jobName + "_Pipeline/" + pipelineId + "/api/json?pretty=true";
			
			String authString = jenkinsUserName + ":" + jenkinsPassword;

			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);

			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			 StringBuilder sb = new StringBuilder();
	        String line1;
	        try(BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))){
	        	while ((line1 = br.readLine()) != null) {
		            sb.append(line1+"\n");
		        }
	        }
	        
	      
	        JSONObject json = new JSONObject(sb.toString());
			JSONArray actions=json.getJSONArray("actions");
			boolean flag=false;
			for(int i=0;i<actions.length();i++)
			{
				if(!flag)
				{
					try{
						JSONObject jObject=actions.getJSONObject(i);
						JSONArray parameters=jObject.getJSONArray("parameters");
						String name=parameters.getJSONObject(0).getString("name");
						String value=parameters.getJSONObject(0).getString("value");
						if(name.equalsIgnoreCase("json_input"))
						{
							flag=true;
							JSONObject valueJson=new JSONObject(value);
							JSONObject build=valueJson.getJSONObject("build");
							JSONArray modulelist=build.getJSONArray("module");
							for(int j=0;j<modulelist.length();j++)
							{
								modules.add(modulelist.getString(j));
							}
							module.setModuleName(modules);
						}
					}
					catch(Exception e)
					{
						
					}
					
				}
			}
			packageContent.setDotNet(module);
			Gson gson = new Gson();
			String packageInString=gson.toJson(packageContent);
			
			sendPackageContent(packageInString,serviceUrl);
			logger.info("DotNet package content parsed");
		}
		catch(Exception e)
		{
			logger.error(e);
		}

	
	}
	
	private static void convertAnt(String serviceUrl,String jobName,String jenkinsServer,String pipelineId,String artifactName,String jenkinsUserName,String jenkinsPassword)
	{
		
		PackageContent packageContent=new PackageContent();
		packageContent.setArtifactName(artifactName);
		Module module=new Module();
		List<String> modules=new ArrayList<>();

		try{
			
			
			String webPage = "https://" + jenkinsServer + "/job/" + jobName + "/job/" + jobName + "_Pipeline/" + pipelineId + "/api/json?pretty=true";
			
			String authString = jenkinsUserName + ":" + jenkinsPassword;

			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);

			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			
	        StringBuilder sb = new StringBuilder();
	        String line1;
	        try(BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))){
	        	while ((line1 = br.readLine()) != null) {
		            sb.append(line1+"\n");
		        }
	        }
	        JSONObject json = new JSONObject(sb.toString());
			JSONArray actions=json.getJSONArray("actions");
			boolean flag=false;
			for(int i=0;i<actions.length();i++)
			{
				if(!flag)
				{
					try{
						JSONObject jObject=actions.getJSONObject(i);
						JSONArray parameters=jObject.getJSONArray("parameters");
						String name=parameters.getJSONObject(0).getString("name");
						String value=parameters.getJSONObject(0).getString("value");
						if(name.toLowerCase().equals("json_input"))
						{
							flag=true;
							JSONObject valueJson=new JSONObject(value);
							JSONObject build=valueJson.getJSONObject("build");
							JSONArray modulelist=build.getJSONArray("module");
							for(int j=0;j<modulelist.length();j++)
							{
								modules.add(modulelist.getString(j));
							}
							module.setModuleName(modules);
						}
					}
					catch(Exception e)
					{
						
					}
					
				}
			}
			packageContent.setAnt(module);
			Gson gson = new Gson();
			String packageInString=gson.toJson(packageContent);
			
			sendPackageContent(packageInString,serviceUrl);
			logger.info("Ant package content parsed");
		}
		catch(Exception e)
		{
			logger.error(e);
		}

	
	}

	private static void convertBigData(String serviceUrl,String jobName,String jenkinsServer,String pipelineId,String artifactName,String jenkinsUserName,String jenkinsPassword)
	{
		
		PackageContent packageContent=new PackageContent();
		packageContent.setArtifactName(artifactName);
		Module module=new Module();
		List<String> modules=new ArrayList<>();

		try{
			
			
			
			String webPage = "https://" + jenkinsServer + "/job/" + jobName + "/job/" + jobName + "_Pipeline/" + pipelineId + "/api/json?pretty=true";
			String authString = jenkinsUserName + ":" + jenkinsPassword;
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);

			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
			
	        StringBuilder sb = new StringBuilder();
	        String line1;
	        try(BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))){
	        	 while ((line1 = br.readLine()) != null) {
	 	            sb.append(line1+"\n");
	 	        }
	        }
	       
	        JSONObject json = new JSONObject(sb.toString());
			JSONArray actions=json.getJSONArray("actions");
			boolean flag=false;
			for(int i=0;i<actions.length();i++)
			{
				if(!flag)
				{
					try{
						JSONObject jObject=actions.getJSONObject(i);
						JSONArray parameters=jObject.getJSONArray("parameters");
						String name=parameters.getJSONObject(0).getString("name");
						String value=parameters.getJSONObject(0).getString("value");
						if(name.toLowerCase().equals("json_input"))
						{
							flag=true;
							JSONObject valueJson=new JSONObject(value);
							JSONObject build=valueJson.getJSONObject("build");
							JSONArray modulelist=build.getJSONArray("module");
							for(int j=0;j<modulelist.length();j++)
							{
								modules.add(modulelist.getString(j));
							}
							module.setModuleName(modules);
						}
					}
					catch(Exception e)
					{
						
					}
					
				}
			}
			packageContent.setBigData(module);
			Gson gson = new Gson();
			String packageInString=gson.toJson(packageContent);
			
			sendPackageContent(packageInString,serviceUrl);
			logger.info("BigData package content parsed");
		}
		catch(Exception e)
		{
			logger.error(e);
		}

	
	}
	
	private static void sendPackageContent(String packageContent,String serviceUrl)
	{
		try{
			String requestUrl="https://"+serviceUrl+"/idprest/releaseService/release/insert/package%26dummy";
	        
	        URL url = new URL(requestUrl);
	       
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(packageContent.getBytes("UTF-8"));
            os.close();
            
            conn.getInputStream();
            
			StringBuffer jsonString = new StringBuffer();
			String line;
			try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				while ((line = br.readLine()) != null) {
					jsonString.append(line);
				}
			}

            conn.disconnect();
            
           
		}
		catch(Exception e)
		{
			logger.error("Exception while sending packageContent",e);
		}
	}
}
