/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.json;

public class ICQAObject {
	private String className;
	private String appid;
	private String mi;
	private String cp;
	private String dp;

	public ICQAObject(String appid, String className, String mi, String cp, String dp) {
		this.appid = appid;
		this.className = className;
		this.mi = mi;
		this.cp = cp;
		this.dp = dp;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getmi() {
		return mi;
	}

	public void setmi(String mi) {
		this.mi = mi;
	}

	public String getcp() {
		return cp;
	}

	public void setcp(String cp) {
		this.cp = cp;
	}

	public String getdp() {
		return dp;
	}

	public void setdp(String dp) {
		this.dp = dp;
	}
}
