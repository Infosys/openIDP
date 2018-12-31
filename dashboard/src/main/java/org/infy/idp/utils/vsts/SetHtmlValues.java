/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.utils.vsts;

import java.text.SimpleDateFormat;

import org.infy.idp.entities.VSTSDataBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * 
 *The class SetHtmlValues has methods for updating html values
 *
 */
public class SetHtmlValues {
	
	private SetHtmlValues() {
		
	}
	
	private static SimpleDateFormat sdfTimeStamp = new SimpleDateFormat("yyyy-MM-dd 'T'HH:mm:ss");
	
	public static String setNewTableValues(String inputHtml, VSTSDataBean mainObj, String inputHtmlNew){
		String outputHtml = "";
		// fetching and modifying table row values
		// document fetched from VSTS server
		Document docOld = Jsoup.parse(inputHtml,"utf-8");
		
		// new last table
		Element tableLast = docOld.select("table").last();
		
		// Data inserted for new table
		Document html = Jsoup.parse(inputHtmlNew,"utf-8");
		Element table = html.select("table").first();
		Element caption = table.select("caption").first();
		Element tableBody = table.select("tbody").first();
		Element row1 =  tableBody.select("tr").get(0);
		Elements columns = row1.select("td");
		
		// setting value for caption
		String captionStr = "Details for: "+mainObj.getPipelineName();
		caption.text(captionStr);

		// setting values for each column in the first row
		String execStr = "<a href=\""+mainObj.getExecNoLink()+"\">"+mainObj.getExecNo()+"</a>";
		columns.get(0).html(execStr);
		columns.get(1).html(mainObj.getUser());
		columns.get(2).html(mainObj.getScmBranch());
		String artiStr = "NA".equalsIgnoreCase(mainObj.getArtivalue())?mainObj.getArtivalue():"<a href=\""+mainObj.getArtilink()+"\">"+mainObj.getArtivalue()+"</a>";
		columns.get(3).html(artiStr);
		columns.get(4).html(mainObj.getEnv());
		columns.get(5).html(mainObj.getBldstatus());
		columns.get(5).attr("class", mainObj.getBldstatus());
		columns.get(6).html(mainObj.getDepStatus());
		columns.get(6).attr("class", mainObj.getDepStatus());
		columns.get(7).html(mainObj.getTstStatus());
		columns.get(7).attr("class", mainObj.getTstStatus());
		String timeStamp = sdfTimeStamp.format(mainObj.getInsertTimestamp());
		columns.get(8).html(timeStamp);
		
		// update new table in the fetched code
		tableLast.after(table);
		
		outputHtml = docOld.toString();

		return outputHtml;
	}
	
	public static String addEntireHTMLValues(VSTSDataBean mainObj, String inputPipelineHtmlNew){
		String outputHtml = "";
		
		String captionStr = "Details for: "+mainObj.getPipelineName();
		// fetching and modifying table row values
		Document html = Jsoup.parse(inputPipelineHtmlNew,"utf-8");
		
		Element caption = html.select("table>caption").first();
		Element table = html.select("table>tbody").first();
		
		// setting caption 
		caption.text(captionStr);
		
		Element row1 =  table.select("tr").get(0);
		
		Elements columns = row1.select("td");
		
		// setting values for each column in the first row
		String execStr = "<a href=\""+mainObj.getExecNoLink()+"\">"+mainObj.getExecNo()+"</a>";
		columns.get(0).html(execStr);
		columns.get(2).html(mainObj.getScmBranch());
		String artiStr = "NA".equalsIgnoreCase(mainObj.getArtivalue())?mainObj.getArtivalue():"<a href=\""+mainObj.getArtilink()+"\">"+mainObj.getArtivalue()+"</a>";
		columns.get(3).html(artiStr);
		columns.get(4).html(mainObj.getEnv());
		columns.get(5).html(mainObj.getBldstatus());
		columns.get(5).attr("class", mainObj.getBldstatus());
		columns.get(6).html(mainObj.getDepStatus());
		columns.get(6).attr("class", mainObj.getDepStatus());
		columns.get(7).html(mainObj.getTstStatus());
		columns.get(7).attr("class", mainObj.getTstStatus());
		String timeStamp = sdfTimeStamp.format(mainObj.getInsertTimestamp());
		columns.get(8).html(timeStamp);
		
		outputHtml = html.toString();
		
		
		return outputHtml;
	}

	public static String addOrUpdateRow(String inputHtml, VSTSDataBean mainObj, String inputRow){
		boolean execNotPresent = true;
		String outputHtml = "";
		
		// fetching table row values
		Document html = Jsoup.parse(inputHtml, "utf-8");
		Elements tables = html.select("table");
		
		// iterate through tables and select the correct pipeline table
		for (Element table: tables){
			Element caption = table.select("caption").first();
			String captionTxt = caption.text();
			if(captionTxt.contains(mainObj.getPipelineName())){
				
				Element tableEntry = table.select("tbody").first();
				
				for (Element row : tableEntry.select("tr")) {
					Elements tds = row.select("td");
					if (tds.size() > 1 && (tds.first().text().equalsIgnoreCase(mainObj.getExecNo()))) {
						execNotPresent = false;
						String execStr = "<a href=\""+mainObj.getExecNoLink()+"\">"+mainObj.getExecNo()+"</a>";
						tds.get(0).html(execStr);
						
						tds.get(1).html(mainObj.getUser());
						tds.get(2).html(mainObj.getScmBranch());
						String artiStr = "NA".equalsIgnoreCase(mainObj.getArtivalue())?mainObj.getArtivalue():"<a href=\""+mainObj.getArtilink()+"\">"+mainObj.getArtivalue()+"</a>";
						tds.get(3).html(artiStr);
						tds.get(4).html(mainObj.getEnv());
						tds.get(5).html(mainObj.getBldstatus());
						tds.get(5).attr("class", mainObj.getBldstatus());
						tds.get(6).html(mainObj.getDepStatus());
						tds.get(6).attr("class", mainObj.getDepStatus());
						tds.get(7).html(mainObj.getTstStatus());
						tds.get(7).attr("class", mainObj.getTstStatus());
						String timeStamp = sdfTimeStamp.format(mainObj.getInsertTimestamp());
						tds.get(8).html(timeStamp);
						break;
					}
				}
				// add a new row in the table
				if(execNotPresent){
					String tr = "tr";
					String trTag = "<tr></tr>";
					
					tableEntry.select(tr).first().before(trTag);
					Element rowL =  tableEntry.select(tr).first().html(inputRow);
					Elements columns = rowL.select("td");
					
					String execStr = "<a href=\""+mainObj.getExecNoLink()+"\">"+mainObj.getExecNo()+"</a>";
					columns.get(0).html(execStr);
					columns.get(1).html(mainObj.getUser());
					columns.get(2).html(mainObj.getScmBranch());
					String artiStr = "NA".equalsIgnoreCase(mainObj.getArtivalue())?mainObj.getArtivalue():"<a href=\""+mainObj.getArtilink()+"\">"+mainObj.getArtivalue()+"</a>";
					columns.get(3).html(artiStr);
					columns.get(4).html(mainObj.getEnv());
					columns.get(5).html(mainObj.getBldstatus());
					columns.get(5).attr("class", mainObj.getBldstatus());
					columns.get(6).html(mainObj.getDepStatus());
					columns.get(6).attr("class", mainObj.getDepStatus());
					columns.get(7).html(mainObj.getTstStatus());
					columns.get(7).attr("class", mainObj.getTstStatus());
					String timeStamp = sdfTimeStamp.format(mainObj.getInsertTimestamp());
					columns.get(8).html(timeStamp);
				}
				break;
			}//if pipeline name exists ends
			
		}// table iterator
		
		
		outputHtml = html.toString();

		return outputHtml;

	}

}
