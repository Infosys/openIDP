package com.infosys.json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Codecoverage {
	
	@SerializedName("jacoco")
	@Expose
	private Jacoco jacoco;
	
	@SerializedName("istanbul")
	@Expose
	private Istanbul istanbul;
	
	@SerializedName("cobertura")
	@Expose
	private Cobertura cobertura;
	
	@SerializedName("dsPriv")
	@Expose
	private CoverageDSPrivJson dsPriv;

	public Jacoco getJacoco() {
		return jacoco;
	}

	public Istanbul getIstanbul() {
		return istanbul;
	}

	public void setIstanbul(Istanbul istanbul) {
		this.istanbul = istanbul;
	}

	public void setJacoco(Jacoco jacoco) {
		this.jacoco = jacoco;
	}

	public Cobertura getCobertura() {
		return cobertura;
	}

	public void setCobertura(Cobertura cobertura) {
		this.cobertura = cobertura;
	}

	public CoverageDSPrivJson getDsPriv() {
		return dsPriv;
	}

	public void setDsPriv(CoverageDSPrivJson dsPriv) {
		this.dsPriv = dsPriv;
	}	
	
}
