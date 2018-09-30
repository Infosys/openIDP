/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.entities;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueryResponse {

    @SerializedName("columns")
    @Expose
    private List<Column> columns = null;
    @SerializedName("rows")
    @Expose
    private List<List<String>> rows = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("target")
    @Expose
    private String target;
   @SerializedName("datapoints")
    @Expose
    private double[][] datapoints = null;
   
    @SerializedName("series")
    @Expose
    private String[][] series=null;
    
    
   public String[][] getSeries() {
		return series;
	}

	public void setSeries(String[][] series) {
		this.series = series;
	}

	
	

	public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }


    public List<List<String>> getRows() {
		return rows;
	}

	public void setRows(List<List<String>> rows) {
		this.rows = rows;
	}

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
