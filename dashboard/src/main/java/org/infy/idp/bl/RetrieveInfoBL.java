/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.bl;

import java.util.ArrayList;
import java.util.List;
import org.infy.idp.entities.QuerySeriesResponse;
import org.apache.commons.lang.StringUtils;
import org.infy.idp.dataapi.FetchDetailsBL;
import org.infy.idp.entities.QueryRequest;
import org.infy.idp.entities.QueryResponse;
import org.infy.idp.entities.SearchRequest;
import org.infy.idp.entities.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * The class RetrieveInfoBL to send retrieve data from database and return it to Grafana
 * 
 * @author shivam.bhagat
 *
 */
@Component
public class RetrieveInfoBL {
	@Autowired
	private FetchDetailsBL jobDL;
	/** The //logger */
	protected Logger logger;
	RetrieveInfoBL() {
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
	/**
	 * Method to handle panel based queries from Grafana
	 * 
	 * @param queryRequest
	 * @return
	 */
	
	
	public Object queryInfo(QueryRequest queryRequest,String userid) 
	{
		List<QueryResponse> responelist = new ArrayList();
		for (Target target : queryRequest.getTargets()) 
		{
			QueryResponse response = new QueryResponse();
			if(target.getType().toLowerCase().contains("timeserie"))
			{
				List<QuerySeriesResponse> responsetime=new ArrayList();
				//response.setTarget(target.getTarget());
				logger.info("Entering timeseries");
								
				
				
				responelist = jobDL.runQuery(target.getTarget(),userid);
				//return responelist;
				for(QueryResponse respo : responelist) {
					QuerySeriesResponse newresponse = new QuerySeriesResponse();
					newresponse.setDatapoints(respo.getDatapoints());
					newresponse.setType(respo.getType());
					newresponse.setTarget(target.getTarget());
					responsetime.add(newresponse);
				}
				
				return responsetime;


			
			}
			else if(target.getType().toLowerCase().contains("table"))
			{

				response.setTarget(target.getTarget());
				logger.info("Entering table request target");//testanalysis
				if(target.getTarget().contains("scminfo")|| target.getTarget().contains("codeanalysis")||target.getTarget().contains("appinfo")||target.getTarget().contains("filename_export")||target.getTarget().contains("buildinfo") ){
					response=jobDL.runTableQuerySCM(target.getTarget());
				}else{
					response=jobDL.runTableQuery(target.getTarget(),userid);
				}
				responelist.add(response);
			}
			
		}
		return responelist;
	
	}


	/**
	 * Method to handle templating/variable related queries from Grafana
	 * 
	 * @param request
	 * @param userid
	 * @return
	 */
	public List<String> searchInfo(SearchRequest request,String userid) {
		
		if (StringUtils.isNotEmpty(request.getTarget())) {
			return jobDL.runSearchQuery(request.getTarget(),userid);
		}
		return new ArrayList();
	}
	
public String fetchInfo(String appname,String pipelinename) {
	
		return jobDL.runFetchQuery(appname,pipelinename);
	}
}
