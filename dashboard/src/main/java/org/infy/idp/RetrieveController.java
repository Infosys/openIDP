/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp;





import java.util.List;

import org.infy.idp.bl.RetrieveInfoBL;

import org.infy.idp.entities.AnnotationRequest;
import org.infy.idp.entities.AnnotationResponse;
import org.infy.idp.entities.QueryRequest;

import org.infy.idp.entities.SearchRequest;
import org.infy.idp.utils.SSLUtilities;
import org.infy.idp.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;



/**

 * class RetrieveController to handle request from grafana
 *
 */
@Controller
public class RetrieveController {
	/** The logger. */
	protected Logger logger;
	
	
	@Autowired
    private RetrieveInfoBL retrieveBL;
	
	
	/**

	 * constructor FooController
	 *
	 */
    public RetrieveController() {
        super();
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    // API - read
    

	/**

	 * method findById
	 * 
	 *@param auth the OAuth2Authentication
	 *@param id the long
	 *
	 *@return Foo
	 */
    
    
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    
    public String findById(@PathVariable final long id) {
        return "id is "+ id;
    }
    @RequestMapping(method = RequestMethod.GET, value = "/")
    @ResponseBody
    public void defaultid() {
      //This method is required by the Grafana Simple JSON Plugin
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/search")
    @ResponseBody
    public List<String> search(@CookieValue("access_token") String accessToken, @RequestBody SearchRequest request) {
    	
    	return retrieveBL.searchInfo(request, Utils.getUserFromJWT(accessToken));
        
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/{appname}/{pipelinename}/fetch")
    @ResponseBody
    public String fetch(@PathVariable("appname") final String appname,@PathVariable("pipelinename") final String pipelinename) {
    	logger.debug("fetching appid for: "+appname+"::"+pipelinename);
    	return retrieveBL.fetchInfo(appname,pipelinename);
        
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/query")
    @ResponseBody
   public Object query(@CookieValue("access_token") String accessToken, @RequestBody QueryRequest queryRequest) {
    	
    	SSLUtilities.trustAllHostnames();
		SSLUtilities.trustAllHttpsCertificates();
    	logger.debug("Target is " + queryRequest.getTargets().get(0).getTarget());
    	return retrieveBL.queryInfo(queryRequest, Utils.getUserFromJWT(accessToken)); 
    }


    
    @RequestMapping(method = RequestMethod.GET, value = "/annotations")
    @ResponseBody
    public AnnotationResponse annotations(@RequestBody AnnotationRequest annotationRequest) {
    	//Default response
    	AnnotationResponse response = new AnnotationResponse();
    	response.setAnnotation(annotationRequest.getAnnotation());
    	response.setTime("1450754160000");
    	response.setTitle("IDP Dashboard");
        return response;
    }
    // API - write
    
    /**

	 * method create
	 * 
	 *@param foo the Foo
	 *
	 *@return foo the Foo
	 */
    
    @RequestMapping(method = RequestMethod.POST, value = "/foos")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String create(@RequestBody final String foo) {
    	
        return "Processed " + foo;
    }

}
