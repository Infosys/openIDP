/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.entities;

public class QueryTableResponse {
private String response;

public String getResponse() {
	return response;
}

public void setResponse(String response) {
	this.response = response;
}
@Override 
public String toString(){
	return "["+getResponse()+"]";
}
}
