/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuerySeriesResponse {

  
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("target")
    @Expose
    private String target;
   @SerializedName("datapoints")
    @Expose
    private double[][] datapoints = null;
   
  

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

  public double[][] getDatapoints() {
        return datapoints;
    }
  

    public void setDatapoints(double[][] datapoints) {
  this.datapoints = datapoints;
    }

}
