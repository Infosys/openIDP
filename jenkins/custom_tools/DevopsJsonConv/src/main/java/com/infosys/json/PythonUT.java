/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import com.google.gson.annotations.SerializedName;

public class PythonUT {
	@SerializedName("total")
	private int total;
	@SerializedName("error")
	private int error;
	@SerializedName("failed")
	private int failed;
	@SerializedName("skipped")
	private int skipped;
	@SerializedName("passed")
	private int passed;

	public int getTotal() {
		return total;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public int getFailed() {
		return failed;
	}

	public void setFailed(int failed) {
		this.failed = failed;
	}

	public int getSkipped() {
		return skipped;
	}

	public void setSkipped(int skipped) {
		this.skipped = skipped;
	}

	public int getPassed() {
		return passed;
	}

	public void setPassed(int passed) {
		this.passed = passed;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
