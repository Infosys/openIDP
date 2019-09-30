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

public class ICQAObject {
	
	@SerializedName("className")
	@Expose
	private String className;
	
	@SerializedName("appid")
	@Expose
	private String appid;
	
	@SerializedName("mi")
	@Expose
	private String mi;
	
	@SerializedName("cp")
	@Expose
	private String cp;
	
	@SerializedName("dp")
	@Expose
	private String dp;
	
	@SerializedName("cbo")
	@Expose
	private String cbo;
	
	@SerializedName("cr")
		@Expose
		private String cr;
		
		@SerializedName("doth")
		@Expose
		private String doth;
	
	
		@SerializedName("loc")
		@Expose
		private String loc;
		
		@SerializedName("locom1")
		@Expose
		private String locom1;
		
		@SerializedName("mnol")
		@Expose
		private String mnol;
		
		@SerializedName("wmpc1")
		@Expose
		private String wmpc1;
		
		@SerializedName("mnop")
		@Expose
		private String mnop;
		
		@SerializedName("noa")
		@Expose
		private String noa;
		
		@SerializedName("noam")
		@Expose
		private String noam;
		
		@SerializedName("nocon")
		@Expose
		private String nocon;
		
		@SerializedName("noo")
		@Expose
		private String noo;
		
		@SerializedName("norm")
		@Expose
		private String norm;
		
		@SerializedName("rfc")
		@Expose
		private String rfc;
		
		@SerializedName("cc")
		@Expose
		private String cc;
		
		@SerializedName("noom")
		@Expose
		private String noom;  
		
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

		public String getMi() {
			return mi;
		}

		public void setMi(String mi) {
			this.mi = mi;
		}

		public String getCp() {
			return cp;
		}

		public void setCp(String cp) {
			this.cp = cp;
		}

		public String getDp() {
			return dp;
		}

		public void setDp(String dp) {
			this.dp = dp;
		}

		public String getCbo() {
			return cbo;
		}

		public void setCbo(String cbo) {
			this.cbo = cbo;
		}

		public String getCr() {
			return cr;
		}

		public void setCr(String cr) {
			this.cr = cr;
		}

		public String getDoth() {
			return doth;
		}

		public void setDoth(String doth) {
			this.doth = doth;
		}

		public String getLoc() {
			return loc;
		}

		public void setLoc(String loc) {
			this.loc = loc;
		}

		public String getLocom1() {
			return locom1;
		}

		public void setLocom1(String locom1) {
			this.locom1 = locom1;
		}

		public String getMnol() {
			return mnol;
		}

		public void setMnol(String mnol) {
			this.mnol = mnol;
		}

		public String getWmpc1() {
			return wmpc1;
		}

		public void setWmpc1(String wmpc1) {
			this.wmpc1 = wmpc1;
		}

		public String getMnop() {
			return mnop;
		}

		public void setMnop(String mnop) {
			this.mnop = mnop;
		}

		public String getNoa() {
			return noa;
		}

		public void setNoa(String noa) {
			this.noa = noa;
		}

		public String getNoam() {
			return noam;
		}

		public void setNoam(String noam) {
			this.noam = noam;
		}

		public String getNocon() {
			return nocon;
		}

		public void setNocon(String nocon) {
			this.nocon = nocon;
		}

		public String getNoo() {
			return noo;
		}

		public void setNoo(String noo) {
			this.noo = noo;
		}

		public String getNorm() {
			return norm;
		}

		public void setNorm(String norm) {
			this.norm = norm;
		}

		public String getRfc() {
			return rfc;
		}

		public void setRfc(String rfc) {
			this.rfc = rfc;
		}

		public String getCc() {
			return cc;
		}

		public void setCc(String cc) {
			this.cc = cc;
		}

		public String getNoom() {
			return noom;
		}

		public void setNoom(String noom) {
			this.noom = noom;
		}

	 

	/*String className,appid;
	String mi,cp,dp,cbo,cr,doth,loc,locom1,mnol,mnop,noa,noam,nocon,noo,noom,norm,rfc,cc,wmpc1;
	
	public ICQAObject(String appid,String className,String cbo,String cr,String doth,String loc,String locom1,String mnol,String mnop,String noa,String noam,String nocon,String noo,String noom,String norm,String rfc,String cc,String wmpc1,String mi,String cp,String dp){
		this.appid = appid;
		this.className = className;
		this.mi = mi;
		this.cp = cp;
		this.dp = dp;
		this.cbo = cbo;
		this.cr = cr;
		this.doth = doth;
		this.loc = loc;
		this.locom1 = locom1;
		this.mnol = mnol;
		this.mnop = mnop;
		this.noa = noa;
		this.noam = noam;
		this.nocon = nocon;
		this.noo = noo;
		this.noom = noom;
		this.norm = norm;
		this.rfc = rfc;
		this.cc = cc;
		this.wmpc1 = wmpc1;
	}
	public String getMi() {
		return mi;
	}
	public void setMi(String mi) {
		this.mi = mi;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public String getDp() {
		return dp;
	}
	public void setDp(String dp) {
		this.dp = dp;
	}
	public String getCbo() {
		return cbo;
	}
	public void setCbo(String cbo) {
		this.cbo = cbo;
	}
	public String getCr() {
		return cr;
	}
	public void setCr(String cr) {
		this.cr = cr;
	}
	public String getDoth() {
		return doth;
	}
	public void setDoth(String doth) {
		this.doth = doth;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getLocom1() {
		return locom1;
	}
	public void setLocom1(String locom1) {
		this.locom1 = locom1;
	}
	public String getMnol() {
		return mnol;
	}
	public void setMnol(String mnol) {
		this.mnol = mnol;
	}
	public String getMnop() {
		return mnop;
	}
	public void setMnop(String mnop) {
		this.mnop = mnop;
	}
	public String getNoa() {
		return noa;
	}
	public void setNoa(String noa) {
		this.noa = noa;
	}
	public String getNoam() {
		return noam;
	}
	public void setNoam(String noam) {
		this.noam = noam;
	}
	public String getNocon() {
		return nocon;
	}
	public void setNocon(String nocon) {
		this.nocon = nocon;
	}
	public String getNoo() {
		return noo;
	}
	public void setNoo(String noo) {
		this.noo = noo;
	}
	public String getNoom() {
		return noom;
	}
	public void setNoom(String noom) {
		this.noom = noom;
	}
	public String getNorm() {
		return norm;
	}
	public void setNorm(String norm) {
		this.norm = norm;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getWmpc1() {
		return wmpc1;
	}
	public void setWmpc1(String wmpc1) {
		this.wmpc1 = wmpc1;
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
	}*/
	
	
}