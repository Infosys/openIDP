/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CodeQuality {
	@SerializedName("sonar")
	@Expose
	private Sonar sonar;
	@SerializedName("pmd")
	@Expose
	private Pmd pmd;

	public Sonar getSonar() {
		return sonar;
	}

	public void setSonar(Sonar sonar) {
		this.sonar = sonar;
	}

	public Pmd getPmd() {
		return pmd;
	}

	public void setPmd(Pmd pmd) {
		this.pmd = pmd;
	}
}
