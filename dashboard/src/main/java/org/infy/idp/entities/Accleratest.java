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

public class Accleratest {

    @SerializedName("totalTest")
    @Expose
    private Integer totalTest;
    @SerializedName("passed")
    @Expose
    private Integer passed;
    @SerializedName("failed")
    @Expose
    private Integer failed;

    

    public Integer getTotalTest() {
		return totalTest;
	}

	public void setTotalTest(Integer totalTest) {
		this.totalTest = totalTest;
	}

	public Integer getPassed() {
        return passed;
    }

    public void setPassed(Integer passed) {
        this.passed = passed;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

}
