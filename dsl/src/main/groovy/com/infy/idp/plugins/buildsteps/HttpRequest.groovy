/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.plugins.buildsteps

import com.infy.idp.interfaces.IPluginBase
import com.infy.idp.utils.HttpRequestNameValuePair
import org.infy.idp.entities.jobs.IDPJob

/**
 *
 * This class implements IPLuginBase interface to configure job for making http requests from jenkins job
 *
 */

class HttpRequest implements IPluginBase {

    private String url
    private String httpMode
    private String validResponseCodes = ''
    private String contentType = 'NOT_SET'
    private String outputFile
    private String requestBody
    private String acceptType = 'NOT_SET'
    private String authentication = ''


    private HttpRequestNameValuePair customHeaders = null


    private Boolean ignoreSslErrors

    public Boolean getIgnoreSslErrors() {
        return ignoreSslErrors
    }

    public void setIgnoreSslErrors(Boolean ignoreSslErrors) {
        this.ignoreSslErrors = ignoreSslErrors
    }

    public String getUrl() {
        return url
    }

    public void setUrl(String url) {
        this.url = url
    }

    public String getAcceptType() {
        return acceptType
    }

    public void setAcceptType(String acceptType) {
        this.acceptType = acceptType
    }

    public String getHttpMode() {
        return httpMode
    }

    public void setHttpMode(String httpMode) {
        this.httpMode = httpMode
    }

    public String getValidResponseCodes() {
        return validResponseCodes
    }

    public void setValidResponseCodes(String validResponseCodes) {
        this.validResponseCodes = validResponseCodes
    }

    public String getContentType() {
        return contentType
    }

    public void setContentType(String contentType) {
        this.contentType = contentType
    }

    public String getOutputFile() {
        return outputFile
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile
    }

    public String getRequestBody() {
        return requestBody
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody
    }

    public String getAuthentication() {
        return authentication
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication
    }

    public HttpRequestNameValuePair getCustomHeaders() {
        return customHeaders;
    }

    public void setCustomHeaders(HttpRequestNameValuePair customHeaders) {
        this.customHeaders = customHeaders;
    }

    /*
     * This function implements add method of IPluginBase interface
     */

    @Override
    public void add(context, IDPJob dataObj) {
        this.addOptions(context, this.performMapping(dataObj))
    }

    /*
    * This function implements performMapping method of IPluginBase interface
    */

    @Override
    public HashMap<String, String> performMapping(IDPJob dataObj) {
        HashMap<String, String> data = new HashMap<String, String>()
        data.put('url', this.url)
        data.put('acceptType', this.acceptType)

        data.put('ignoreSslErrors', false)

        if (dataObj.buildInfo != null && dataObj.buildInfo.artifactToStage != null && dataObj.buildInfo.artifactToStage.artifactRepo != null && dataObj.buildInfo.artifactToStage.artifactRepo.repoUsername != null) {
            data.put('authentication', this.authentication)
        }

        data.put('consoleLogResponseBody', false)
        data.put('contentType', this.contentType)

        data.put('httpMode', this.httpMode)

        data.put('outputFile', this.outputFile)
        data.put('passBuildParameters', false)
        data.put('requestBody', this.requestBody)
        data.put('timeout', 0)
        data.put('validResponseCodes', this.validResponseCodes)


        return data
    }

    /*
     * This function implements addOptions method of IPluginBase interface
     */

    @Override
    public void addOptions(context, HashMap<String, String> data) {
        context.with {
            conditionalBuilder {
                runCondition {
                    alwaysRun()
                }
                runner { fail() }
                conditionalbuilders {
                    httpRequest {
                        HttpRequest.addHttpRequest(delegate, data)
                    }
                }
            }
        }
    }

    /*
     * This method is used to add httprequests in jenkins job
     */

    private static void addHttpRequest(context, data) {
        context.with {

            if (data.containsKey('url')) url(data.get('url'))
            if (data.containsKey('acceptType')) acceptType(data.get('acceptType'))
            if (data.containsKey('authentication')) authentication(data.get('authentication'))
            if (data.containsKey('consoleLogResponseBody')) consoleLogResponseBody(data.get('consoleLogResponseBody'))
            if (data.containsKey('contentType')) contentType(data.get('contentType'))
            if (data.containsKey('customHeaders') && (data.get('customHeaders') != null)) customHeaders {

                httpRequestNameValuePair {
                    name(data.get('customHeaders').getName())
                    value(data.get('customHeaders').getValue())
                    maskValue(false)
                }

            }
            if (data.containsKey('httpMode')) httpMode(data.get('httpMode'))
            if (data.containsKey('ignoreSslErrors')) ignoreSslErrors(data.get('ignoreSslErrors'))
            if (data.containsKey('outputFile')) outputFile(data.get('outputFile'))
            if (data.containsKey('passBuildParameters')) passBuildParameters(data.get('passBuildParameters'))
            if (data.containsKey('requestBody')) requestBody(data.get('requestBody'))
            if (data.containsKey('timeout')) timeout(data.get('timeout'))
            if (data.containsKey('validResponseCodes')) validResponseCodes(data.get('validResponseCodes'))
            if (data.containsKey('validResponseContent')) validResponseContent(data.get('validResponseContent'))


        }
    }
}
