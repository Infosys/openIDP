/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.entities;

public class QueryTimeSeriesResponse {
private String value;
private String timestamp;
public QueryTimeSeriesResponse(String value){
	this.value=value;
	this.timestamp="1450754160000";
}
public String getTimestamp() {
	return timestamp;
}
public void setTimestamp(String timestamp) {
	this.timestamp = timestamp;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
@Override
public String toString(){
	return "[\"" + 1223 + "\"," + getTimestamp() +"]";
}
}
